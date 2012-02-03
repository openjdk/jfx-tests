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
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import org.jemmy.JemmyException;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.SceneWrap;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.ControlInterface;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;

@ControlType({ToolBar.class})
@ControlInterfaces(value={Selectable.class}, encapsulates=Node.class)
public class ToolBarWrap<CONTROL extends ToolBar> extends NodeWrap<CONTROL> {
    public static final String VERTICAL_PROP_NAME = "vertical";

    private Selectable<Node> objectSelectable = new ToolBarSelectable();

    private Focus focus = new Focus() {
        public void focus() {
            if (!isFocused()) {
                mouse().click(1, new Point(1, 1));
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
    public ToolBarWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    public Node getSelectedItem() {
        return new GetAction<Node>() {

            @Override
            public void run(Object... parameters) {
                for (Node item : getToolBar().getItems()) {
                    if (item.isFocused()) {
                        setResult(item);
                    }
                }
            }
        }.dispatch(getEnvironment());
    }

    public Integer getSelectedItemIndex() {
        return new GetAction<Integer>() {

            @Override
            public void run(Object... parameters) {
                ObservableList<Node> list = getToolBar().getItems();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isFocused()) {
                        setResult(i);
                    }
                }
            }
        }.dispatch(getEnvironment());
    }

    @Property(ToolBarWrap.VERTICAL_PROP_NAME)
    public boolean vertical() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(getControl().getOrientation() == Orientation.VERTICAL);
            }
        }.dispatch(getEnvironment());
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
                    setResult(new ArrayList<Node>(getToolBar().getItems()));
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
            final Scene selected_scene = new GetAction<Scene>() {
                    @Override
                    public void run(Object... parameters) {
                        setResult(state.getScene());
                    }
                }.dispatch(getEnvironment());
            if (selected_scene != scene) {
                Boolean visible = false;
                if (selected_scene != null) {
                    visible = new GetAction<Boolean>() {
                        @Override
                        public void run(Object... parameters) {
                            setResult(selected_scene.getWindow().isShowing());
                        }
                    }.dispatch(getEnvironment());
                }
                if (!visible) {
                    Wrap<Node> expandMenu = as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                        @Override
                        public boolean check(Node control) {
                            return control.getClass().getName().contentEquals("com.sun.javafx.scene.control.skin.ToolBarSkin$ToolBarOverflowMenu");
                        }
                    }).wrap(0);
                    expandMenu.mouse().click();
                }
                Scene new_selected_scene = new GetAction<Scene>() {
                    @Override
                    public void run(Object... parameters) {
                        setResult(state.getScene());
                    }
                }.dispatch(getEnvironment());
                if (new_selected_scene == null) {
                    throw new JemmyException("The menu bar can not be expanded", getControl());
                }
                Wrap<? extends Scene> popup_scene_wrap = new SceneWrap(getEnvironment(), new_selected_scene);
                Wrap<Node> item = popup_scene_wrap.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                    @Override
                    public boolean check(Node control) {
                        return control.equals(state);
                    }
                }).wrap(0);
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