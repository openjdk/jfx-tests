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
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class ContextMenuApp extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TestPane";
    public final static String CLEAR_BTN_ID = "Clear";
    public final static String RESET_BTN_ID = "Reset";
    public final static String ADD_SINGLE_BTN_ID = "Add single item";
    public final static String ADD_SINGLE_AT_POS_BTN_ID = "Add single item at pos";
    public final static String REMOVE_SINGLE_AT_POS_BTN_ID = "Remove single item at pos";
    public final static String REMOVE_POS_EDIT_ID = "Remove at pos";
    public final static String ADD_POS_EDIT_ID = "Add at pos";
    public final static String LAST_SELECTED_ID = "Last selected";
    public final static String LAST_SELECTED_ACTION_ID = "Last selected action";
    public final static String SHOWN_CHECK_ID = "Shown";
    public final static String ERROR_ID = "Error";
    public final static String SHOWING_CHECK_ID = "Showing";
    public final static String MENU_ITEM = "Menu item ";
    public final static int BUTONS_NUM = 3;
    public final static String MENU_ID = "Menu";

    VBox root;
    Label lastSelected;
    Label lastSelectedAction;
    public static ContextMenu menu;

    public static void main(String[] args) {
        Utils.launch(ContextMenuApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new MenuBarAppScene();
    }

    class ActionItem extends MenuItem {
        public ActionItem(String str) {
            super(str);
            setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    lastSelected.setText(getText());
                }
            });
        }
    }

    public class MenuBarAppScene extends Scene {

        public MenuBarAppScene() {
            super(root = new VBox(5));

            Pane testPane = new Pane();
            testPane.setId(TEST_PANE_ID);
            testPane.setMinSize(600, 300);
            testPane.setPrefSize(600, 300);
            testPane.setMaxSize(600, 300);
            root.getChildren().add(testPane);

            menu = new ContextMenu();
            final Label menu_label = new Label(MENU_ID);
            menu_label.setId(MENU_ID);
            menu_label.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    if (menu.isShowing()) {
                        System.out.println("hide");
                        menu.hide();
                    } else {
                        System.out.println("show");
                        menu.show(menu_label, Side.BOTTOM, 0, 0);
                    }
                }
            });

            testPane.getChildren().add(menu_label);

            reset();

            VBox controls = new VBox(5);
            root.getChildren().add(controls);

            Button clear_buton = new Button(CLEAR_BTN_ID) {
                @Override
                public void fire() {
                    menu.getItems().clear();
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
                    menu.getItems().add(new ActionItem(MENU_ITEM + menu.getItems().size()));
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
                    menu.getItems().add(Integer.valueOf(add_position.getText()), new ActionItem(MENU_ITEM + menu.getItems().size()));
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
                    menu.getItems().remove(Integer.valueOf(remove_position.getText()).intValue());
                }
            };
            remove_position_box.getChildren().add(remove_buton);

            final Label remove_label = new Label("at");
            remove_position_box.getChildren().add(remove_label);

            remove_position_box.getChildren().add(remove_position);

            HBox last_selected_box = new HBox(5);
            controls.getChildren().add(last_selected_box);

            Label prompt_last_selected = new Label(LAST_SELECTED_ID);
            lastSelected = new Label();
            lastSelected.setId(LAST_SELECTED_ID);
            last_selected_box.getChildren().add(prompt_last_selected);
            last_selected_box.getChildren().add(lastSelected);

            Label prompt_shown = new Label(SHOWN_CHECK_ID);
            final CheckBox shown = new CheckBox();
            shown.setId(SHOWN_CHECK_ID);
            menu.setOnShown(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent t) {
                    shown.setSelected(true);
                }
            });
            menu.setOnHidden(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent t) {
                    shown.setSelected(false);
                }
            });
            last_selected_box.getChildren().add(prompt_shown);
            last_selected_box.getChildren().add(shown);

            Label prompt_showing = new Label(SHOWING_CHECK_ID);
            final CheckBox showing = new CheckBox();
            showing.setId(SHOWING_CHECK_ID);
            menu.setOnShowing(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent t) {
                    showing.setSelected(true);
                }
            });
            menu.setOnHiding(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent t) {
                    showing.setSelected(false);
                }
            });

            last_selected_box.getChildren().add(prompt_showing);
            last_selected_box.getChildren().add(showing);

            HBox last_selected_action_box = new HBox(5);
            controls.getChildren().add(last_selected_action_box);

            Label prompt_last_selected_action = new Label(LAST_SELECTED_ACTION_ID);
            lastSelectedAction = new Label();
            lastSelectedAction.setId(LAST_SELECTED_ACTION_ID);
            last_selected_action_box.getChildren().add(prompt_last_selected_action);
            last_selected_action_box.getChildren().add(lastSelectedAction);

            Label error = new Label();
            error.setId(ERROR_ID);
            controls.getChildren().add(error);

            EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    lastSelectedAction.setText(((MenuItem)t.getTarget()).getText());
                }
            };
            menu.setOnAction(handler);
            if (menu.getOnAction() != handler) {
                error.setText("menu.setOnAction() fails");
            }
        }

        protected void reset() {
            menu.getItems().clear();
            for (int i = 0; i < BUTONS_NUM; i++) {
                menu.getItems().add(new ActionItem(MENU_ITEM + i));
            }
        }
    }
}