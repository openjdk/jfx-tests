/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */
package org.jemmy.fx.control;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeWrap;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.MenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;

@ControlType({MenuBar.class})
@ControlInterfaces(value = {Parent.class, MenuOwner.class},
encapsulates = {MenuItem.class, MenuItem.class},name={"asMenuParent"})

public class MenuBarWrap<CONTROL extends MenuBar> extends NodeWrap<CONTROL> {

    private StringMenuOwnerImpl menuOwner = new StringMenuOwnerImpl(this, this.as(Parent.class, Menu.class));
    
    private Focus focus = new Focus() {
        public void focus() {
            if (!isFocused()) {
                // pressKey()/releaseKey() are used to prevent an attempt to get focus in pushKey()
                keyboard().pressKey(KeyboardButtons.F10);
                getEnvironment().getTimeout(keyboard().PUSH.getName());
                keyboard().releaseKey(KeyboardButtons.F10);
            }
            waitState(focusedState, true);
        }
    };

    private State<Boolean> focusedState = new State<Boolean>() {
        public Boolean reached() {
            return isFocused();
        }
    };

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public MenuBarWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (MenuItem.class.isAssignableFrom(type)) {
            if (Parent.class.isAssignableFrom(interfaceClass)) {
                return true;
            }
            if (MenuOwner.class.isAssignableFrom(interfaceClass)) {
                return true;
            }
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (MenuItem.class.isAssignableFrom(type)) {
            if (Parent.class.equals(interfaceClass)) {
                return (INTERFACE) new AbstractMenuItemsParent(this, type) {
                    @Override
                    protected List getControls() {
                        return getControl().getMenus();
                    }
                };
            }
            if (MenuOwner.class.isAssignableFrom(interfaceClass)) {
                return (INTERFACE) menuOwner;
            }
        }
        return super.as(interfaceClass, type);
    }

    @Override
    public Focus focuser() {
        return focus;
    }
}