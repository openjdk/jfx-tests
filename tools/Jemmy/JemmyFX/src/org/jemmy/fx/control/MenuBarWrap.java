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

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.jemmy.action.FutureAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeWrap;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Collapsible;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Parent;

import java.util.List;

/**
 * Menu is supported in two different form.<br/>
 * One is by using <code>MenuOwner</code> control interface. There you could
 * perform basic menu pushing or selecting. Select operation returns a wrap,
 * which you could pass into a constructor of a dock or use directly.<br/>
 * The other approach is to use MenuItemDock which allows advanced lookup and
 * more input operations.<br/>
 * Please consult <a href="../../samples/menu">samples</a> for more info.
 *
 * @param <CONTROL>
 * @author shura
 * @see MenuItemWrap
 * @see StringMenuOwner
 * @see MenuBarDock
 */
@ControlType({MenuBar.class})
@ControlInterfaces(value = {Parent.class, StringMenuOwner.class, Collapsible.class},
        encapsulates = {MenuItem.class, MenuItem.class}, name = {"asMenuParent", "asMenuOwner"})
public class MenuBarWrap<CONTROL extends MenuBar> extends NodeWrap<CONTROL> {

    private StringMenuOwnerImpl menuOwner = null;
    private Parent<MenuItem> parent = null;
    private Focus focus = ThemeDriverFactory.getThemeFactory().menuBarFocuser(this);

    /**
     * @param env
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public MenuBarWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    /**
     * Turns into a parent which you could use to look for menu items within. <br/>
     * Notice that the lookup is performed through the whole hierarchy. You only
     * need to specify criteria for a single menu item no matter how deep in the
     * hierarchy it resides. <br/>
     * Notice also that menus are sometimes dynamic - that is,
     * sub-items are only loaded when a parent is expanded. If a node is not loaded,
     * this lookup would not find it. You need to find a parent node in question,
     * expand it, and use it as a root for further search.
     *
     * @return
     * @see MenuWrap#asMenuParent()
     */
    @As(MenuItem.class)
    public Parent<MenuItem> asMenuParent() {
        if (parent == null) {
            parent = new MenuItemParent(this) {

                @Override
                protected List getControls() {
                    return new FutureAction<>(getEnvironment(),()-> getControl().getMenus()).get();
                }
            };
        }
        return parent;
    }

    /**
     * Allows to perform simple push and selection operations on the menu.
     *
     * @return
     */
    @As(MenuItem.class)
    public StringMenuOwner<MenuItem> asMenuOwner() {
        if (menuOwner == null) {
            menuOwner = new StringMenuOwnerImpl(this, this.as(Parent.class, Menu.class));
        }
        return menuOwner;
    }

    private Collapsible collapsible = null;

    /**
     * Collapses ass the sub-menus.
     *
     * @return
     */
    @As
    public Collapsible asCollapsible() {
        if (collapsible == null) {
            collapsible = () -> new FutureAction<>(getEnvironment(),() -> getControl().getMenus().stream().forEach(m -> m.hide()));
        }
        return collapsible;
    }

    @Override
    public Focus focuser() {
        return focus;
    }

    @Override
    public boolean isFocused() {
        return true; // stab: currently no way to detemine pseudo-focused state
    }
}