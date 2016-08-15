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
package javafx.scene.control.test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class MenuBarApp extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TestPane";
    public final static String CLEAR_BTN_ID = "Clear";
    public final static String RESET_BTN_ID = "Reset";
    public final static String ADD_SINGLE_BTN_ID = "Add single item";
    public final static String ADD_SINGLE_AT_POS_BTN_ID = "Add single item at pos";
    public final static String REMOVE_SINGLE_AT_POS_BTN_ID = "Remove single item at pos";
    public final static String REMOVE_POS_EDIT_ID = "Remove at pos";
    public final static String ADD_POS_EDIT_ID = "Add at pos";
    public final static String MENU_STR = "Menu ";
    public final static int BUTONS_NUM = 3;
    VBox root;
    Label lastSelected;

    public static void main(String[] args) {
        Utils.launch(MenuBarApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new MenuBarAppScene();
    }

    class BarItem extends Menu {

        public BarItem(String str) {
            super(str);
            setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    lastSelected.setText(getText());
                }
            });
            for (int i = 0; i < BUTONS_NUM; i++) {
                final MenuItem item = new MenuItem("Item " + i);
                item.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        lastSelected.setText(item.getText());
                    }
                });
                this.getItems().add(item);
            }
        }
    }

    public class MenuBarAppScene extends Scene {

        final MenuBar bar;

        public MenuBarAppScene() {
            super(root = new VBox(5));

            Utils.addBrowser(this);

            lastSelected = new Label();

            Pane test_pane = new Pane();
            test_pane.setId(TEST_PANE_ID);
            test_pane.setMinSize(600, 300);
            test_pane.setPrefSize(600, 300);
            test_pane.setMaxSize(600, 300);
            root.getChildren().add(test_pane);

            bar = new MenuBar();
            test_pane.getChildren().add(bar);
            reset();

            VBox controls = new VBox(5);
            root.getChildren().add(controls);

            Button clear_buton = new Button(CLEAR_BTN_ID) {
                @Override
                public void fire() {
                    bar.getMenus().clear();
                }
            };
            controls.getChildren().add(clear_buton);

            Button reset_buton = new Button(RESET_BTN_ID) {
                @Override
                public void fire() {
                    reset();
                }
            };
            controls.getChildren().add(reset_buton);

            Button add_buton = new Button(ADD_SINGLE_BTN_ID) {
                @Override
                public void fire() {
                    bar.getMenus().add(new BarItem(MENU_STR + bar.getMenus().size()));
                }
            };
            controls.getChildren().add(add_buton);

            HBox add_position_box = new HBox(5);
            controls.getChildren().add(add_position_box);

            final TextField add_position = new TextField("0");
            add_position.setId(ADD_POS_EDIT_ID);

            Button add_buton_pos = new Button(ADD_SINGLE_AT_POS_BTN_ID) {
                @Override
                public void fire() {
                    bar.getMenus().add(Integer.valueOf(add_position.getText()), new BarItem(MENU_STR + bar.getMenus().size()));
                }
            };
            add_position_box.getChildren().add(add_buton_pos);

            final Label add_label = new Label("at");
            add_position_box.getChildren().add(add_label);

            add_position_box.getChildren().add(add_position);

            HBox remove_position_box = new HBox(5);
            controls.getChildren().add(remove_position_box);

            final TextField remove_position = new TextField("0");
            remove_position.setId(REMOVE_POS_EDIT_ID);

            Button remove_buton = new Button(REMOVE_SINGLE_AT_POS_BTN_ID) {
                @Override
                public void fire() {
                    bar.getMenus().remove(Integer.valueOf(remove_position.getText()).intValue());
                }
            };
            remove_position_box.getChildren().add(remove_buton);

            final Label remove_label = new Label("at");
            remove_position_box.getChildren().add(remove_label);

            remove_position_box.getChildren().add(remove_position);

            controls.getChildren().add(lastSelected);
        }

        protected void reset() {
            bar.getMenus().clear();
            for (int i = 0; i < BUTONS_NUM; i++) {
                bar.getMenus().add(new BarItem(MENU_STR + i));
            }
            lastSelected.setText("");
        }
    }
}