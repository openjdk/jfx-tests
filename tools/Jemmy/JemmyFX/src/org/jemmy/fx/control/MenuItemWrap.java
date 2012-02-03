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
