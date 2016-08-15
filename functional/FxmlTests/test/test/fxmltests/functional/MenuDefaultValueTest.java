/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package test.fxmltests.functional;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import test.fxmltests.app.MenuDefaultValue;
import test.fxmltests.app.MenuDefaultValue.Pages;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;

/**
 *
 * @author cementovoz
 */
public class MenuDefaultValueTest extends TestBase {

    @BeforeClass
    public static void runUI() {
        Utils.launch(MenuDefaultValue.class, null);
    }

    /**
     * this test verified creation menu with MenuItems from xml, RT-19007
     */
    @Test
    public void testMenu() throws InterruptedException {
        testCommon(Pages.menuPage.name(), null, false, true);
        checkMenu();
    }

    /**
     * this test verified creation menuBar and menu width MenuItems from xml,RT-19007
     */
    @Test
    public void testMenuBar() throws InterruptedException {
        testCommon(Pages.menuBarPage.name(), null, false, true);
        checkMenu();
    }


    /**
     * this test verified creation menu with custom children from xml, RT-19007
     */
    @Test
    public void testMenuItemCustom() throws InterruptedException {
        testCommon(Pages.menuItemCustomPage.name(), null, false, true);
        checkMenu();
    }

    private void checkMenu () {
        Wrap<? extends Scene> sceneWrap = Root.ROOT.lookup(Scene.class).wrap();
        Wrap<? extends Menu> menuWrap = getMenu(sceneWrap, MenuDefaultValue.MENU_FILE_ID);
        expand(menuWrap);
        Wrap<? extends Scene> scenePopup = Root.ROOT.lookup(Scene.class).wrap(0);//todo
        Parent<MenuItem> menuParent = menuWrap.as(Parent.class, MenuItem.class);
        Assert.assertEquals(MenuDefaultValue.COUNT_MENUITEMS, menuParent.lookup().size());
        for (int i=0; i < menuParent.lookup().size(); i++) {
            Wrap<? extends MenuItem> menuItemWrap = menuParent.lookup().wrap(i);
            Assert.assertNotNull(menuItemWrap);
            Wrap<? extends Node> menuItem = lookupByMenuItem(scenePopup, menuItemWrap.getControl());
            Assert.assertNotNull(menuItem);
        }
        sceneWrap.mouse().move(new Point(0, 0));
        sceneWrap.mouse().click();
    }

    private Wrap<? extends Menu> getMenu (Wrap<? extends Scene> scene, String text) {
        Parent<Node> sceneParent = scene.as(Parent.class, Node.class);
        Wrap<? extends MenuBar> menuBar = sceneParent.lookup(MenuBar.class).wrap(0);
        Assert.assertNotNull(menuBar);
        StringMenuOwner<? extends Menu> menuBarParent  = menuBar.as(StringMenuOwner.class, Menu.class);
        Assert.assertNotNull(menuBarParent);
        final Wrap<? extends Menu> menu  = menuBarParent.select(MenuDefaultValue.MENU_FILE_ID);
        Assert.assertNotNull(menu);
        return menu;
    }

    protected Wrap lookupByMenuItem(Wrap<? extends Scene> scene, final MenuItem menu) {
        final Wrap<Node> item = scene.as(Parent.class, Node.class).lookup(Node.class, new LookupCriteria<Node>() {
                public boolean check(Node node) {
                    if (node.getProperties().get(MenuItem.class) == menu) {
                        return true;
                    }
                    return false;
                }
            }).wrap();
        return item;
    }

    private static void expand(final Wrap<? extends Menu> menu) {
        if (new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(menu.getControl().isShowing());
            }
        }.dispatch(Root.ROOT.getEnvironment()) == Boolean.FALSE) {
            menu.mouse().click();
        }
        menu.waitState(new State() {
            public Object reached() {
                return (menu.getControl().isShowing() ? Boolean.TRUE : null);
        }
        });
    }
}
