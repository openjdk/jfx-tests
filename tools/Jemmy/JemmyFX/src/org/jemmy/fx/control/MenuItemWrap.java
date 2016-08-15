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

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.jemmy.Rectangle;
import org.jemmy.action.FutureAction;
import org.jemmy.control.*;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.*;
import org.jemmy.interfaces.*;
import org.jemmy.lookup.ControlHierarchy;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;

import java.util.List;

/**
 * This represents a single menu item within menu hierarchy. It could be looked
 * up starting from a menu bar, a menu button or a context menu.
 *
 * @param <ITEM>
 * @author shura
 * @see MenuBarWrap
 */
@ControlType(MenuItem.class)
@MethodProperties({"getId", "getStyle", "getText", "getUserData"})
public class MenuItemWrap<ITEM extends MenuItem> extends Wrap<ITEM> {

    /**
     * First mouse is moved into a menu component. If node is not activated
     * during this timeout, click is performed.
     */
    public static final Timeout BEFORE_CLICK_TO_EXPAND_SLEEP = new Timeout("menu.before.click.to.expand", 500);
    /**
     *
     */
    public static final String IS_SELECTED_PROP_NAME = "isSelected";

    static {
        Environment.getEnvironment().initTimeout(BEFORE_CLICK_TO_EXPAND_SLEEP);
    }

    /**
     * Converts a text sample and a text comparison logic to lookup criteria.
     * Looking for a MenuItem by text content is the most logical approach.
     *
     * @param <B>
     * @param tp     Text subclass
     * @param text   a text sample
     * @param policy defines how to compare
     * @return
     */
    @ObjectLookup("text and comparison policy")
    public static <B extends MenuItem> LookupCriteria<B> textLookup(Class<B> tp, String text,
                                                                    StringComparePolicy policy) {
        return new ByText<>(text, policy);
    }

    /**
     * Constructs lookup criteria by an id value. By-id lookup is the most basic
     * and the most reliable approach.
     *
     * @param <B>
     * @param tp  Node type
     * @param id  expected node id
     * @return
     * @see ByID
     */
    @ObjectLookup("id")
    public static <B extends MenuItem> LookupCriteria<B> idLookup(Class<B> tp, String id) {
        return new ByID<>(id);
    }

    private Wrap<?> placeholder = null;
    private MenuShowable menuShowable = new MenuShowable();
    private final Wrapper wrapper;

    /**
     * @param env
     * @param item
     */
    public MenuItemWrap(Environment env, ITEM item) {
        super(env, item);
        wrapper = new WrapperDelegate(NodeWrap.WRAPPER, env);
    }

    @Override
    public Rectangle getScreenBounds() {
        show();
        return placeholder.getScreenBounds();
    }

    public Menu getParentMenu() {
        return getParentMenu(getControl());
    }

    /**
     * While a node been shown, all parent items are expanded so that the node
     * is visible and represented by a node.
     *
     * @return
     */
    @As
    public Showable asShowable() {
        return menuShowable;
    }

    private void show() {
        Menu parent = getControl().getParentMenu();
        if (parent != null) {
            expand(parent);
        }
        placeholder = findWrap(getControl());
    }

    private Menu getParentMenu(final MenuItem item) {
        return new FutureAction<>(getEnvironment(), item::getParentMenu).get();
    }

    private Wrap<? extends Node> findWrap(final MenuItem item) {
        final Node[] menuControl = new Node[1];
        final Menu parent = getParentMenu(item);
        Root.ROOT.lookup(new LookupCriteria<Scene>() {
            public boolean check(Node node, ControlHierarchy hierarchy) {
                if (node instanceof MenuButton && parent == null) {
                    MenuButton mb = ((MenuButton) node);
                    //menu button could be in menu bar or by itself
                    if (item instanceof Menu) {
                        //if in menu bar, it contains the same item
                        //the item is not exposed, however, so, I have to compare
                        //first child
                        if (mb.getItems().size() > 0
                                && mb.getItems().get(0) == ((Menu) item).getItems().get(0)) {
                            menuControl[0] = node;
                            return true;
                        }
                    }
                    //else, if the menu item is on it's own, it does not have
                    //a menu associated. Hence a top-level menu item
                    //would be within its items
                    for (MenuItem mi : ((MenuButton) node).getItems()) {
                        if (mi == item) {
                            //if so, expanf the menu button and keep loking
                            wrapper.wrap(MenuButton.class, mb).as(Expandable.class).expand();
                        }
                    }
                } else if (node.getProperties().get(MenuItem.class) == item) {
                    menuControl[0] = node;
                    return true;
                }
                return check(hierarchy.getChildren(node), hierarchy);
            }

            public boolean check(List<?> nodes, ControlHierarchy hierarchy) {
                if (nodes != null) {
                    for (Object n : nodes) {
                        if (check((Node) n, hierarchy)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            public boolean check(final Scene cntrl) {
                if (parent != null && !(cntrl.getWindow() instanceof ContextMenu)) {
                    return false;
                }
                AbstractNodeHierarchy hierarchy = new AbstractNodeHierarchy(getEnvironment()) {
                    public List<?> getControls() {
                        return getChildren(cntrl.getRoot());
                    }
                };
                return check(hierarchy.getControls(), hierarchy);
            }

            @Override
            public String toString() {
                return "scene containing a node which has Menu.class property equal to " + item.getText() + " menu";
            }
        }).wait(1);
        return wrapper.wrap(Node.class, menuControl[0]);
    }

    /**
     * Sleeps are applied in this method, because of RT-28683. We need to wait,
     * until a popup is really shown, but cannot determine that, in JFX.
     */
    private void expand(final Menu menu) {
        if (!MenuWrap.isShowing(menu, getEnvironment())) {
            final Menu parent = getParentMenu(menu);
            if (parent != null) {
                expand(parent);
                getEnvironment().getTimeout(BEFORE_CLICK_TO_EXPAND_SLEEP).sleep();
                waitState(() -> MenuWrap.isParentShown(menu, getEnvironment()), true);
            }
            Wrap<? extends Node> mWrap = findWrap(menu);
            if (mWrap.getControl() instanceof MenuButton) {
                mWrap.mouse().move();
                getEnvironment().getTimeout(BEFORE_CLICK_TO_EXPAND_SLEEP).sleep();
                if (!MenuWrap.isShowing(menu, getEnvironment())) {
                    mWrap.mouse().click();
                }
            } else {
                mWrap.mouse().move();
                getEnvironment().getTimeout(BEFORE_CLICK_TO_EXPAND_SLEEP).sleep();
            }
        }
    }

    /**
     * @deprecated Use docks
     */
    public static class MenuByText implements LookupCriteria<MenuItem> {

        String str = null;

        public MenuByText(String str) {
            this.str = str;
        }

        @Override
        public boolean check(MenuItem item) {
            return item.getText() != null && item.getText().contentEquals(str);
        }
    }

    protected class SelectableImpl implements Selectable<Boolean>, Selector<Boolean> {

        public List<Boolean> getStates() {
            return CheckBoxWrap.booleanStates;
        }

        public Boolean getState() {
            return getProperty(Boolean.class, IS_SELECTED_PROP_NAME);
        }

        public Selector<Boolean> selector() {
            return this;
        }

        public Class<Boolean> getType() {
            return Boolean.class;
        }

        public void select(Boolean state) {
            if (!state.equals(getState())) {
                mouse().click();
                waitProperty(IS_SELECTED_PROP_NAME, state);
            }
        }
    }

    private class MenuShowable implements Showable, Show {

        public MenuShowable() {
        }

        public Show shower() {
            return this;
        }

        public void show() {
            MenuItemWrap.this.show();
        }
    }
}
