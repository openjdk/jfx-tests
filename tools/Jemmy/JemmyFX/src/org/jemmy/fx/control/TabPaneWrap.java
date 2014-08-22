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

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jemmy.JemmyException;
import org.jemmy.action.FutureAction;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeWrap;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.*;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;

import java.util.ArrayList;
import java.util.List;

@ControlType({TabPane.class})
@ControlInterfaces(value = {Selectable.class, Parent.class},
        encapsulates = {Tab.class, Tab.class},
        name = {"asSelectable", "asTabParent"})
public class TabPaneWrap<CONTROL extends TabPane> extends NodeWrap<CONTROL> {

    public static final String SELECTED_INDEX_PROP = "selectedIndex";
    public static final String SELECTED_ITEM_PROP = "selectedItem";
    public static final String ITEMS_PROP = "tabs";
    private Selectable<Tab> objectSelectable = new TabPaneSelectable();

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public TabPaneWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        // Default Parent is Parent<Node> which is super
        if (Selectable.class.equals(interfaceClass)) {
            return true;
        }
        if (interfaceClass.isAssignableFrom(AbstractScroll.class)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass)
                && Tab.class.isAssignableFrom(type)) {
            return true;
        }
        if (Selectable.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <INTERFACE extends ControlInterface> INTERFACE as(Class<INTERFACE> interfaceClass) {
        if (Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) objectSelectable;
        }
        return super.as(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass)
                && Tab.class.isAssignableFrom(type)) {
            return (INTERFACE) new TabParent(this, type);
        }
        if (Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) objectSelectable;
        }
        return super.as(interfaceClass, type);
    }

    @Override
    public Focus focuser() {
        /*TODO We can't simulate user input in order to switch tabs because there is no way to get real node corresponding to Tab.
             * http://javafx-jira.kenai.com/browse/RT-18230
             * So focus is requested and tabs are switched by keyboard*/
        return () -> {
            new FutureAction<>(getEnvironment(), () -> getControl().requestFocus());

        };
    }

    private class TabPaneSelectable implements Selectable<Tab>, Selector<Tab> {

        @Override
        public List<Tab> getStates() {
            return new GetAction<ArrayList<Tab>>() {

                @Override
                public void run(Object... parameters) {
                    setResult(new ArrayList<>(getItems()));
                }

                @Override
                public String toString() {
                    return "Fetching all data items from " + TabPaneSelectable.this;
                }
            }.dispatch(getEnvironment());
        }

        @Override
        public Tab getState() {
            return getSelectedItem();
        }

        public Selector<Tab> selector() {
            return this;
        }

        public Class getType() {
            return Tab.class;
        }

        public void select(final Tab state) {
            TabWrap.checkNotDisabledState(state, getEnvironment());

            int targetIndex = getItems().indexOf(state);
            if (targetIndex < 0) {
                throw new JemmyException("This TabPane does not contain the requested tab", getControl());
            }
            int selectedIndex;
            while ((selectedIndex = getSelectedIndex()) != targetIndex) {
                keyboard().pushKey((selectedIndex < targetIndex) ? KeyboardButtons.RIGHT
                        : KeyboardButtons.LEFT);
            }
            waitState(() -> getSelectedItem(), state);
        }
    }

    @Property(SELECTED_INDEX_PROP)
    public int getSelectedIndex() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getSelectionModel().getSelectedIndex()).get();
    }

    @Property(SELECTED_ITEM_PROP)
    public Tab getSelectedItem() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getSelectionModel().getSelectedItem()).get();
    }

    @Property(ITEMS_PROP)
    public ObservableList<Tab> getItems() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getTabs()).get();

    }
}