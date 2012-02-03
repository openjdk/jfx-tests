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
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.env.Environment;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.MenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Tree;
import org.jemmy.interfaces.TypeControlInterface;

@ControlType({MenuButton.class})
//@ControlInterfaces(value = {Parent.class, StringMenuOwner.class},
//encapsulates = {Menu.class, Menu.class, MenuItem.class})

public class MenuButtonWrap<CONTROL extends MenuButton> extends TextControlWrap<CONTROL> {

    private StringMenuOwnerImpl menuOwner = new StringMenuOwnerImpl(this, this.as(Parent.class, Menu.class));

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public MenuButtonWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (MenuItem.class.isAssignableFrom(type)) {
            if (Parent.class.equals(interfaceClass)) {
                return true;
            }
            if (MenuOwner.class.isAssignableFrom(interfaceClass)) {
                return true;
            }
            if (Tree.class.isAssignableFrom(interfaceClass)) {
                return true;
            }
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>>
           INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (MenuItem.class.isAssignableFrom(type)) {
            if (Parent.class.equals(interfaceClass)) {
                return (INTERFACE) new AbstractMenuItemsParent(this, type) {
                    @Override
                    protected List getControls() {
                        return getControl().getItems();
                    }
                };
            }
            if (MenuOwner.class.isAssignableFrom(interfaceClass)) {
                return (INTERFACE) menuOwner;
            }
        }
        return super.as(interfaceClass, type);
    }
}