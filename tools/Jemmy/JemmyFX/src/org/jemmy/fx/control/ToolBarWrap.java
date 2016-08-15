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
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.action.FutureAction;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.SceneWrap;
import org.jemmy.interfaces.*;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

import java.util.ArrayList;
import java.util.List;

@ControlType({ToolBar.class})
@ControlInterfaces(value = {Selectable.class}, encapsulates = Node.class)
public class ToolBarWrap<CONTROL extends ToolBar> extends NodeWrap<CONTROL> {
    public static final String VERTICAL_PROP_NAME = "vertical";

    private Selectable<Node> objectSelectable = new ToolBarSelectable();

    private State<Boolean> focusedState = this::isFocused;

    private Focus focus = () -> {
        if (!isFocused()) {
            mouse().click(1, new Point(1, 1));
        }
        waitState(focusedState, true);
    };

    /**
     * @param env
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public ToolBarWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    public Node getSelectedItem() {
        return new FutureAction<>(getEnvironment(), () -> {
            for (Node item : getToolBar().getItems()) {
                if (item.isFocused()) {
                    return item;
                }
            }
            return null;
        }).get();
    }

    public Integer getSelectedItemIndex() {
        return new FutureAction<>(getEnvironment(), () -> {
            ObservableList<Node> list = getToolBar().getItems();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isFocused()) {
                    return i;
                }
            }
            return null;
        }).get();
    }

    @Property(ToolBarWrap.VERTICAL_PROP_NAME)
    public boolean vertical() {
        return new FutureAction<>(getEnvironment(), () -> getControl().getOrientation() == Orientation.VERTICAL).get();
    }

    @Override
    public <INTERFACE extends ControlInterface> boolean is(Class<INTERFACE> interfaceClass) {
        // Default Parent is Parent<Node> which is super
        if (Selectable.class.equals(interfaceClass)) {
            return true;
        }
        return super.is(interfaceClass);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.equals(interfaceClass)
                && !Node.class.equals(type)) {
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
        // Default Parent is Parent<Node> which is super
        if (Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) objectSelectable;
        }
        return super.as(interfaceClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>>
    INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Selectable.class.equals(interfaceClass)) {
            return (INTERFACE) new ToolBarSelectable();
        }
        return super.as(interfaceClass, type);
    }

    @Override
    public Focus focuser() {
        return focus;
    }

    private class ToolBarSelectable implements Selectable<Node>, Selector<Node> {

        @Override
        public List<Node> getStates() {
            return new GetAction<ArrayList<Node>>() {
                @Override
                public void run(Object... parameters) {
                    setResult(new ArrayList<>(getToolBar().getItems()));
                }

                @Override
                public String toString() {
                    return "Fetching all data items from " + ToolBarSelectable.this;
                }
            }.dispatch(getEnvironment());
        }

        @Override
        @Property(Selectable.STATE_PROP_NAME)
        public Node getState() {
            return getSelectedItem();
        }

        public Selector<Node> selector() {
            return this;
        }

        public Class getType() {
            return Node.class;
        }

        public void select(final Node state) {
            final Scene selected_scene = new FutureAction<Scene>(getEnvironment(), state::getScene).get();
            if (selected_scene != scene) {
                Boolean visible = false;
                if (selected_scene != null) {
                    visible = new FutureAction<Boolean>(getEnvironment(), () -> selected_scene.getWindow().isShowing()).get();
                }
                if (!visible) {
                    Wrap<Node> expandMenu = as(Parent.class, Node.class).lookup(control -> control.getClass().getName().contentEquals("com.sun.javafx.scene.control.skin.ToolBarSkin$ToolBarOverflowMenu")).wrap(0);
                    expandMenu.mouse().click();
                }
                Scene new_selected_scene = new FutureAction<Scene>(getEnvironment(), state::getScene).get();
                if (new_selected_scene == null) {
                    throw new JemmyException("The menu bar can not be expanded", getControl());
                }
                Wrap<? extends Scene> popup_scene_wrap = new SceneWrap(getEnvironment(), new_selected_scene);
                Wrap<Node> item = popup_scene_wrap.as(Parent.class, Node.class).lookup(control -> control.equals(state)).wrap(0);
                item.mouse().click();
            } else {
                Boolean found = new GetAction<Boolean>() {
                    @Override
                    public void run(Object... parameters) {
                        for (Node item : getToolBar().getItems()) {
                            if (item.equals(state)) {
                                setResult(true);
                                return;
                            }
                        }
                        setResult(false);
                    }
                }.dispatch(getEnvironment());

                if (!found) {
                    throw new JemmyException("The menu bar does not contain an item", getControl());
                }

                new NodeWrap(getEnvironment(), state).mouse().click();
            }

            new Waiter(WAIT_STATE_TIMEOUT).waitValue(state, new State<Node>() {
                @Override
                public Node reached() {
                    return getSelectedItem();
                }

                @Override
                public String toString() {
                    return "Checking that selected item [" + getSelectedItem()
                            + "] is " + state;
                }
            });
        }
    }

    public CONTROL getToolBar() {
        return getControl();
    }
}