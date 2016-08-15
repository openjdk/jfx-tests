/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
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

    List<Boolean> stateList = new LinkedList<>();
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
        return new ToggleButtonWrap<>(parent.getEnvironment(),
                parent.getParent().lookup(ToggleButton.class, criteria).get());
    }

    public static ToggleButtonWrap<ToggleButton> find(NodeParent parent, String text) {
        return find(parent, new ByText<>(text, (StringComparePolicy)parent.getEnvironment().
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
