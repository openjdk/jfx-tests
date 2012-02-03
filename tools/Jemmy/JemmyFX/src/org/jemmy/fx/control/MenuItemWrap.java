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
import org.jemmy.Rectangle;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import org.jemmy.action.GetAction;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneWrap;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;

@ControlType({MenuItem.class})
@ControlInterfaces(value = {Parent.class},
        encapsulates = {MenuItem.class}, name={"asMenuParent"})

public class MenuItemWrap<ITEM extends MenuItem> extends Wrap<ITEM> {

    protected Wrap menuWrap;

    /**
     *
     * @param env
     * @param item
     * @param parentMenuWrap
     */
    public MenuItemWrap(ITEM item, Wrap parentMenuWrap) {
        super(parentMenuWrap.getEnvironment(), item);
        this.menuWrap = parentMenuWrap;
    }

    @Override
    public Rectangle getScreenBounds() {
        return getPlaceholder().getScreenBounds();
    }

    private Wrap<Node> getPlaceholder() {
        if (MenuBar.class.isAssignableFrom(menuWrap.getControl().getClass())) {
            return ((Parent)menuWrap.as(Parent.class, Node.class)).lookup(Node.class, new LookupCriteria<Node>() {
                public boolean check(Node node) {
                    if (node.getProperties().get(MenuItem.class) == getControl()) {
                        return true;
                    }
                    if (MenuButton.class.isAssignableFrom(node.getClass()) && ((MenuButton) node).getItems().containsAll(((Menu)getControl()).getItems())) {
                        return true;
                    }
                    return false;
                }
            }).wrap();
            } else {
            if (MenuButton.class.isAssignableFrom(menuWrap.getControl().getClass())) {
                if (new GetAction<Boolean>() {
                    @Override
                    public void run(Object... parameters) {
                        setResult(!((MenuButton)menuWrap.getControl()).isShowing());
                    }
                }.dispatch(getEnvironment())) {
                if (SplitMenuButton.class.isAssignableFrom(menuWrap.getControl().getClass())) {
                    Parent parent = (Parent) menuWrap.as(Parent.class, Node.class);
                    parent.lookup(Node.class, new ByStyleClass<Node>("arrow-button")).wrap().mouse().click();
                } else {
                    menuWrap.mouse().click();
                }
            }
        }

        Wrap<? extends Scene> popup_scene_wrap = Root.ROOT.lookup(new LookupCriteria<Scene>() {
            public boolean check(Scene scene) {
                if (!(scene.getWindow() instanceof ContextMenu)) {
                    return false;
                }
                Wrap<Scene> scene_wrap = new SceneWrap(getEnvironment(), scene);
                Parent<Node> parent = scene_wrap.as(Parent.class, Node.class);
                return parent.lookup(Node.class, new LookupCriteria<Node>() {
                    public boolean check(Node node) {
                        if (node.getProperties().get(Menu.class) == getControl().getParentMenu()) {
                            return true;
                        }
                        return false;
                    }
                }).size() > 0;
            }
        }).wrap();

        return popup_scene_wrap.as(Parent.class, Node.class).lookup(Node.class, new LookupCriteria<Node>() {
                public boolean check(Node node) {
                    if (node.getProperties().get(MenuItem.class) == getControl()) {
                        return true;
                    }
                    return false;
                }
            }).wrap();
    }
                }

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
}
