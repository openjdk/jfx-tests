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
package org.jemmy.samples.menu;


import javafx.scene.control.ScrollPane;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.*;
import org.jemmy.input.AWTRobotInputFactory;
import org.jemmy.input.glass.GlassInputFactory;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This sample shows how to use JavaFX menu. There are two generic ways to do so:
 * through <code>MenuOwner</code> interface, when only basic menu operations are
 * needed, and performing lookup and more complicated steps through <code>MenuItemDock</code>
 * class.
 *
 * Attention : http://javafx-jira.kenai.com/browse/RT-24873 according to this
 * issue, popup may do not appear at first click, so some of this samples may
 * fail.
 *
 * Some samples may fail due to RT-28743.
 *
 * @author KAM, shura
 */
public class MenuSample extends SampleBase {
    private static SceneDock scene;
    private static MenuBarDock menuBar;

    @BeforeClass
    public static void launch() throws InterruptedException {
        // Running the test application
        startApp(MenuApp.class);

        // Obtaining a Dock for scene
        scene = new SceneDock();

        //Looking up for MenuBar. There is just one MenuBar in the scene so
        //no criteria is specified.
        menuBar = new MenuBarDock(scene.asParent());
    }

    /**
     * Push a menu.
     */
    @Test
    public void pushMenu() {

        //it is possible to push menu hierarchycally.
        //Underscores in the item names mark mnemonics
        menuBar.asMenuOwner().push("A_ction", "_Run");

        //it is also possible to find the menu item first
        //and then click it with mouse.
        //it will itself expand the path to become visible
        final MenuItemDock menuItemDock = new MenuItemDock(menuBar.asMenuParent(), "Option _1", StringComparePolicy.EXACT);
        menuItemDock.mouse().click();

        //We must close Menu, because otherwise an additional click is needed,
        //to close the menu (see RT-19955), the click, which could be used for
        //another action, which could lead to tests fails.
        menuItemDock.keyboard().pushKey(KeyboardButtons.ESCAPE);
    }

    /**
     * Push a menu while identifying items by ids.
     */
    @Test
    public void pushMenuById() {
        //you could find menu components by id
        menuBar.asMenuOwner().push(
                new ByIdMenuItem("file"),
                new ByIdMenuItem("options"),
                new ByIdMenuItem("option1"));

        //or using MenuItemDock
        final MenuItemDock menuItemDock = new MenuItemDock(menuBar.asMenuParent(), "action");
        menuItemDock.mouse().click();

        //We must close Menu, because otherwise an additional click is needed,
        //to close the menu (see RT-19955), the click, which could be used for
        //another action, which could lead to tests fails.
        menuItemDock.keyboard().pushKey(KeyboardButtons.ESCAPE);
    }

    /**
     * Select a <code>CheckMenuItem</code>.
     */
    @Test
    public void checkMenuItem() {

        //find a CheckMenuItem with text "Enabled"
        CheckMenuItemDock checkMenuItem = new CheckMenuItemDock(menuBar.asMenuParent(),
                "Enabled", StringComparePolicy.SUBSTRING);

        //you could do a siple mouse click to select the checkbox,
        //but of course you would want
        //to verify what the current selection is and so on.
        //instead just do this
        checkMenuItem.asSelectable().selector().select(false);

        //you could check if the item is selected
        Boolean selected = checkMenuItem.isSelected();

    }

    /**
     * Select a <code>RadioMenuItem</code>.
     */
    @Test
    public void radioMenuItem() {

        //for radio button, same approach as above would work, but click also works
        //as you do not need to ckech the value - whether it selected or not
        new RadioMenuItemDock(menuBar.asMenuParent(), "Option _2", StringComparePolicy.EXACT).mouse().click();

    }

    /**
     * Work with context menu.
     *
     */
    @Test
    public void pushContextMenuItem() {
        NodeDock scrollPane = new NodeDock(scene.asParent(), ScrollPane.class);

        //right click to call the popup
        // http://javafx-jira.kenai.com/browse/JMY-179
        scrollPane.mouse().click(1, scrollPane.wrap().getClickPoint(), MouseButtons.BUTTON3);

        //pushing a context menu is just like pushing main menu
        new ContextMenuDock().asMenuOwner().push("item _2");


        scrollPane.mouse().click(1, scrollPane.wrap().getClickPoint(), MouseButtons.BUTTON3);

        //or
        new MenuItemDock(new ContextMenuDock().asMenuParent(), "sub-", StringComparePolicy.SUBSTRING).mouse().click();
    }

    /**
     * Push MenuButton menu item.
     */
    @Test
    public void pushMenuButtonMenuItem() {

        MenuButtonDock menuButton = new MenuButtonDock(scene.asParent(), "_Menu Button", StringComparePolicy.EXACT);

        //same as other menus
        new MenuItemDock(menuButton.asMenuParent(), "_Eleven", StringComparePolicy.EXACT).mouse().click();

        //or
        menuButton.asMenuOwner().push("_Two");
    }

    /**
     * Finally, how to collapse
     */
    @Test
    public void collapse() {
        //expand
        new MenuItemDock(menuBar.asMenuParent(), "option1").mouse().move();

        //and collapse
        menuBar.asCollapsible().collapse();
    }

    @After
    public void after() throws InterruptedException {
        //wait for everything to be totally collapsed after every test
        new MenuDock(menuBar.asMenuParent(), 0).wrap().waitProperty("isShowing", false);
        new MenuDock(menuBar.asMenuParent(), 1).wrap().waitProperty("isShowing", false);
        Thread.sleep(200);
    }
}