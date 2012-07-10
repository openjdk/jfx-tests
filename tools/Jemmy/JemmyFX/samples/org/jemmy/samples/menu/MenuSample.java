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


import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.stage.Popup;
import org.jemmy.fx.ByText;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.*;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.lookup.Lookup;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.samples.SampleBase;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author KAM
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

        /**
        * Looking up for MenuBar. There is just one MenuBar in the scene so
        * no criteria is specified.
        */
        menuBar = new MenuBarDock(scene.asParent());
    }

    /**
     * Pushing menu item in menuBar.
     */ 
    @Test
    public void pushMenu() {
        
        // Pressing Action > Run menu item. Items are looked up by text which
        // also includes mnemonics so it is better to use ids as in pushMenuOption
        menuBar.menu().push(
                new ByTextMenuItem("A_ction", StringComparePolicy.EXACT), 
                new ByTextMenuItem("Run", StringComparePolicy.SUBSTRING));
    }
    
    /**
     * Pushing menu to change selected radio option.
     */
    @Test
    public void pushMenuOption() {
        
        // Pressing File > Options > Option 2 menu item. Items are looked up by 
        // ids which is the best approach.
        menuBar.menu().push(
                new ByIdMenuItem("file"), 
                new ByIdMenuItem("options"),
                new ByIdMenuItem("option2"));
    }
    
    /**
     * Checking whether menu item is checked.
     * http://javafx-jira.kenai.com/browse/JMY-177
     */
    @Test
    public void getCheckedMenuItem() {
        
        // Calling Menu.select() method to open parent menu so that 
        // necessary menu item is visible
        MenuItemDock options = new MenuItemDock(menuBar.menu().select(
                new ByIdMenuItem("file"), new ByIdMenuItem("options")));
        
        // Looking up for necessary menu item by text
        MenuItemDock menuItem = new MenuItemDock(options.asMenuParent(), 
                new ByTextMenuItem("Enabled", StringComparePolicy.SUBSTRING));
        
        // Getting selected property of menu item
        Boolean selected = menuItem.wrap().getProperty(Boolean.class, "isSelected");
        System.out.println("Enabled menu item is selected = " + selected);
        
        // Closing a menu via several ESC key presses
        // http://javafx-jira.kenai.com/browse/JMY-181
        menuItem.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);
        menuItem.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);
    }
    
    /**
     * Checking what menu option is selected.
     * http://javafx-jira.kenai.com/browse/JMY-177
     */
    @Test
    public void getSelectedRadioMenuItem() {
        
        // Calling Menu.select() method to open parent menu so that 
        // necessary menu item is visible
        MenuItemDock options = new MenuItemDock(menuBar.menu().select(
                new ByIdMenuItem("file"), new ByIdMenuItem("options")));
        
        // Geting all the option menu items
        Lookup<MenuItem> lookup = options.asMenuParent().lookup(new ByTextMenuItem<MenuItem>("Option", StringComparePolicy.SUBSTRING));
        System.out.println("Found " + lookup.size() + " option menu items.");
        MenuItemDock selectedMenuItem = null;
        for (int i = 0; i < lookup.size(); i++) {
            MenuItemDock menuItem = new MenuItemDock(lookup.wrap(i));
            Boolean selected = menuItem.wrap().getProperty(Boolean.class, "isSelected");
            if (selected) {
                selectedMenuItem = menuItem;
            }
            System.out.println(menuItem.wrap().getProperty("getText") + " menu item is selected = " + selected);
        }
        
        // Selecting checked item to close the menu
        selectedMenuItem.mouse().click();
    }
    
    /**
     * Push ContextMenuItem
     * 
     * TODO: Doesn't seem to be possible in current JemmyFX version
     * http://javafx-jira.kenai.com/browse/JMY-180
     */
    @Test @Ignore // TODO: Fix this sample one the issue is fixed
    public void pushContextMenuItem() {
        
        // Obtaining a nodeDock for the ScrollPane which has a context menu
        NodeDock scrollPane = new NodeDock(scene.asParent(), ScrollPane.class);
        
        // Clicking right mouse button once
        // http://javafx-jira.kenai.com/browse/JMY-179
        scrollPane.mouse().click(1, scrollPane.wrap().getClickPoint(), MouseButtons.BUTTON3);
        
        // Obtaining a popup scene
        SceneDock popupScene = new SceneDock(new ByWindowType<Scene>(Popup.class));
        
//        scene2.asParent().lookup().dump(System.out);
//        MenuDock menu = new MenuDock(scene2.asParent());
//        menu.menu().push(new ByTextMenuItem("My Menu Item", StringComparePolicy.EXACT));
    }
    
    /**
     * Push MenuButton menu item.
     */
    @Test
    public void pushMenuButtonMenuItem() {
        
        // Obtaining MenuButtonDock by its text.
        MenuButtonDock menuButton = new MenuButtonDock(scene.asParent(), new ByText<MenuButton>("_Menu Button"));
        
        // JemmyDock API is not sufficient in this area so we're switching to plain old wrap stuff
        // http://javafx-jira.kenai.com/browse/JMY-178
        StringMenuOwner button_menu_owner = menuButton.wrap().as(StringMenuOwner.class, MenuItem.class);
        
        // Pushing menu item by text
        button_menu_owner.push("_Two");
    }
}
