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
package javafx.scene.control.test.Mnemonics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class MenuMnemonicsApp extends InteroperabilityApp {

    public static final String CHECK_MENU_PARSE_ID = "check.menu.parse.id";

    public static void main(String[] args) {
        Utils.launch(MenuMnemonicsApp.class, args); // args go to /dev/null/ for the moment
    }

    @Override
    public Scene getScene() {
        return new MnemonicsScene();
    }

    protected VBox box = new VBox(10);

    public class MnemonicsScene extends Scene {
        public MnemonicsScene() {
            super(box, 600, 400);
            Utils.addBrowser(this);

            final MenuBar bar = new MenuBar();
            generateMenus(bar.getMenus(), 2, 2, 0);

            CheckBox check = new CheckBox("setMnemonicParse()");
            check.setSelected(true);
            check.setId(CHECK_MENU_PARSE_ID);
            check.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    setMnemocicParse((ObservableList)bar.getMenus(), t1);
                }
            });

            box.getChildren().addAll(bar, check);
        }

        void setMnemocicParse(ObservableList<MenuItem> list, Boolean value) {
            for (MenuItem item : list) {
                if (item instanceof Menu) {
                    ((Menu)item).setMnemonicParsing(value);
                    setMnemocicParse(((Menu)item).getItems(), value);
                }
            }
        }

        char letter = 'A';
        void generateMenus(ObservableList list, int width, int depth, int level) {
            for (int i = 0; i < width; i++) {
                String name = "Menu " + level + " " + i + " _" + letter++;
                if (level < depth) {
                    Menu menu = new Menu(name);
                    generateMenus(menu.getItems(), width, depth, level + 1);
                    list.add(menu);
                } else {
                    MenuItem menu = new MenuItem(name);
                    list.add(menu);
                }
            }
        }
    }
}
