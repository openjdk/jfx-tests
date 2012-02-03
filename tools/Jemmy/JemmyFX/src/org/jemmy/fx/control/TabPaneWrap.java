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

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.fx.NodeWrap;
import org.jemmy.control.MethodProperties;
import org.jemmy.env.Environment;
import org.jemmy.fx.FXClickFocus;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.timing.State;

@ControlType({TabPane.class})
@ControlInterfaces( value = {Selectable.class,Parent.class}, 
                    encapsulates = {Tab.class,Tab.class}, 
                    name= {"asSelectable", "asTabParent"})
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
            return (INTERFACE) new TabPaneSelectable();
        }
        return super.as(interfaceClass, type);
    }

    @Override
    public Focus focuser() {        
        return new Focus() {
/*TODO We can't simulate user input in order to switch tabs because there is no way to get real node corresponding to Tab.
 * http://javafx-jira.kenai.com/browse/RT-18230
 * So focus is requested and tabs are switched by keyboard*/
            public void focus() {
                new GetAction<Void>(){

                    @Override
                    public void run(Object... parameters) throws Exception {
                        getControl().requestFocus();
                    }
                }.dispatch(getEnvironment());
            }
        };        
    }

    private class TabPaneSelectable implements Selectable<Tab>, Selector<Tab> {

        @Override
        public List<Tab> getStates() {
            return new GetAction<ArrayList<Tab>>() {

                @Override
                public void run(Object... parameters) {
                    setResult(new ArrayList<Tab>(getItems()));
                }

                @Override
                public String toString() {
                    return "Fetching all data items from " + TabPaneSelectable.this;
                }
            }.dispatch(getEnvironment());
        }

        @Override
        @Property(Selectable.STATE_PROP_NAME)
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

            int targetIndex = getItems().indexOf(state);
            if (targetIndex < 0) {
                throw new JemmyException("This TabPane does not contain the requested tab", getControl());
            }
            int selectedIndex;
            while ((selectedIndex = getSelectedIndex()) != targetIndex) {
                keyboard().pushKey((selectedIndex < targetIndex) ? KeyboardButtons.RIGHT
                        : KeyboardButtons.LEFT);
            }
            waitState(new State<Tab>() {

                public Tab reached() {
                    return getSelectedItem();
                }
            }, state);
        }
    }

    @Property(SELECTED_INDEX_PROP)
    public int getSelectedIndex() {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getSelectionModel().getSelectedIndex());
            }
        }.dispatch(getEnvironment());
    }

    @Property(SELECTED_ITEM_PROP)
    public Tab getSelectedItem() {
        return new GetAction<Tab>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getSelectionModel().getSelectedItem());
            }
        }.dispatch(getEnvironment());
    }

    @Property(ITEMS_PROP)
    public ObservableList<Tab> getItems() {
        return new GetAction<ObservableList<Tab>>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getControl().getTabs());
            }
        }.dispatch(getEnvironment());
    }
}