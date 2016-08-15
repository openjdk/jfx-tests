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
package test.fxmltests.app;

import com.sun.javafx.runtime.VersionInfo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.*;


public class MenuDefaultValue extends BasicButtonChooserApp {

    private static int WIDTH = 400;
    private static int HEIGHT = 200;

    private static String FXML_MENU_RESOURCE = "/test/fxmltests/resources/menu-RT-19007.fxml";
    private static String FXML_MENUBAR_RESOURCE = "/test/fxmltests/resources/menubar-RT-19007.fxml";
    private static String FXML_MENUITEMCUSTOM_RESOURCE = "/test/fxmltests/resources/menuitemcustom-RT-19007.fxml";

    public static String MENU_FILE_ID = "File";
    public static String MENUITEM_ADD_ID = "add";
    public static String MENUITEM_OPEN_ID = "open";

    public static int COUNT_MENUITEMS = 2;

    private static Rectangle red= new Rectangle(10, 10) {
                    {
                        setFill(Color.RED);
                    }
                };

    public enum Pages {
        menuPage, menuBarPage, menuItemCustomPage
    }

    public MenuDefaultValue() {
        super(WIDTH, HEIGHT, VersionInfo.getRuntimeVersion(), false);
    }


    @Override
    protected TestNode setup() {
        TestNode root = new TestNode();

        PageWithSlots pageMenu = new PageWithSlots(Pages.menuPage.name(), HEIGHT - 15, WIDTH - 10);
        pageMenu.add(new FXMLMenuPage(), Pages.menuPage.name());

        PageWithSlots pageMenuBar = new PageWithSlots(Pages.menuBarPage.name(), HEIGHT - 15, WIDTH - 10);
        pageMenuBar.add(new FXMLMenuBarPage(), Pages.menuBarPage.name());

        PageWithSlots pageMenuItemCustom = new PageWithSlots(Pages.menuItemCustomPage.name(), HEIGHT - 15, WIDTH - 10);
        pageMenuItemCustom.add(new FXMLMenuCustomItemPage(), Pages.menuItemCustomPage.name());

        root.add(pageMenu);
        root.add(pageMenuBar);
        root.add(pageMenuItemCustom);
        return root;
    }


    private class FXMLMenuPage extends TestNode {
        @Override
        public Node drawNode() {
            MenuBar menuBar= new MenuBar();
            Menu id  = null;
            try {
                menuBar.getMenus().add((Menu) FXMLLoader.load(getClass().getResource(FXML_MENU_RESOURCE)));
            } catch (Exception exc) {
                exc.printStackTrace();
                reportGetterFailure(exc.getMessage());
                return red;
            }
            return menuBar;
        }
    }

    private class FXMLMenuBarPage extends TestNode {
        @Override
        public Node drawNode() {
            MenuBar menuBar= null;
            try {
                menuBar = (MenuBar) FXMLLoader.load(getClass().getResource(FXML_MENUBAR_RESOURCE));
            } catch (Exception exc) {
                exc.printStackTrace();
                reportGetterFailure(exc.getMessage());
                return red;
            }
            return menuBar;
        }
    }

    private class FXMLMenuCustomItemPage extends TestNode {
        @Override
        public Node drawNode() {
            MenuBar menuBar= new MenuBar();
            try {
                menuBar.getMenus().add((Menu) FXMLLoader.load(getClass().getResource(FXML_MENUITEMCUSTOM_RESOURCE)));
            } catch (Exception exc) {
                exc.printStackTrace();
                reportGetterFailure(exc.getMessage());
                return red;
            }
            return menuBar;
        }
    }

    public static void main(String[] args) {
        Utils.launch(MenuDefaultValue.class, null);
    }
}
