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

import java.util.LinkedList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import org.jemmy.action.GetAction;
import org.jemmy.fx.ByText;
import org.jemmy.fx.NodeParent;
import org.jemmy.fx.Root;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.SelectorImpl;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.Any;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;

/**
 *
 * @param <T>
 * @author shura
 */
@ControlType({ToggleButton.class})
@ControlInterfaces(value={Selectable.class}, encapsulates={Boolean.class})
public class ToggleButtonWrap<T extends ToggleButton> extends TextControlWrap<T> implements Selectable<Boolean> {
    
    List<Boolean> stateList = new LinkedList<Boolean>();
    Selector<Boolean> selector = new SelectorImpl<ToggleButton, Boolean>(this, this);

    /**
     *
     * @param scene
     * @param node
     */
    public ToggleButtonWrap(Environment env, T node) {
        super(env, node);
        stateList.add(false);
        stateList.add(true);
    }

    public static ToggleButtonWrap<ToggleButton> find(NodeParent parent, LookupCriteria<ToggleButton> criteria) {
        return new ToggleButtonWrap<ToggleButton>(parent.getEnvironment(),
                parent.getParent().lookup(ToggleButton.class, criteria).get());
    }

    public static ToggleButtonWrap<ToggleButton> find(NodeParent parent, String text) {
        return find(parent, new ByText<ToggleButton>(text, parent.getEnvironment().
                getProperty(Root.LOOKUP_STRING_COMPARISON, StringComparePolicy.EXACT)));
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        if(Selectable.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if(Selectable.class.equals(interfaceClass) && Boolean.class.equals(type)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if(Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) this;
        }
        return super.as(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if(Selectable.class.equals(interfaceClass) && Boolean.class.equals(type)) {
            return (INTERFACE) this;
        }
        return super.as(interfaceClass, type);
    }

    @Override
    public List<Boolean> getStates() {
        return stateList;
    }

    @Override
    public Selector<Boolean> selector() {
        return selector;
    }

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Property(SELECTED_PROP_NAME)
    @Override
    public Boolean getState() {
        return new GetAction<Boolean>() {

            @Override
            public void run(Object... parameters) {
                setResult(getControl().isSelected());
            }

            @Override
            public String toString() {
                return null;
            }

        }.dispatch(getEnvironment());
    }

}
