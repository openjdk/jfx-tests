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

import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class MenuButtonApp extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TestPane";
    public final static String CLEAR_BTN_ID = "Clear";
    public final static String RESET_BTN_ID = "Reset";
    public final static String ADD_SINGLE_BTN_ID = "Add single item";
    public final static String ADD_SINGLE_AT_POS_BTN_ID = "Add single item at pos";
    public final static String REMOVE_SINGLE_AT_POS_BTN_ID = "Remove single item at pos";
    public final static String REMOVE_POS_EDIT_ID = "Remove at pos";
    public final static String ADD_POS_EDIT_ID = "Add at pos";
    public final static String LAST_SELECTED_ID = "Last selected";
    public final static String SIDE_CB_ID = "Side";
    public final static String MENU_ITEM = "Menu item ";
    public final static int MENU_ITEMS_NUM = 3;
    protected Label lastSelected;

    public static void main(String[] args) {
        Utils.launch(MenuButtonApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "MenuButtonTestApp");
        return new MenuBarAppScene();
    }

    public class MenuBarAppScene extends Scene {

        protected MenuButton menuButton;
        protected ChoiceBox<Side> sideChoiceBox;
        protected VBox controls;

        protected void createMenuButton() {
            menuButton = new MenuButton("Menu Button");
        }

        public MenuBarAppScene() {
            super(new VBox());

            Utils.addBrowser(this);

            Pane testPane = new Pane();
            testPane.setId(TEST_PANE_ID);
            testPane.setMinSize(600, 400);
            testPane.setPrefSize(600, 400);
            testPane.setMaxSize(600, 400);

            createMenuButton();

            menuButton.setTranslateX(200);
            menuButton.setTranslateY(150);
            testPane.getChildren().add(menuButton);

            Button clearButton = new Button(CLEAR_BTN_ID) {
                @Override
                public void fire() {
                    menuButton.getItems().clear();
                }
            };

            Button resetButton = new Button(RESET_BTN_ID) {
                @Override
                public void fire() {
                    reset();
                }
            };

            Button addButton = new Button(ADD_SINGLE_BTN_ID) {
                @Override
                public void fire() {
                    menuButton.getItems().add(new ActionItem(MENU_ITEM + menuButton.getItems().size()));
                }
            };

            final TextField addPosition = new TextField("0");
            addPosition.setId(ADD_POS_EDIT_ID);
            Button addButonPos = new Button(ADD_SINGLE_AT_POS_BTN_ID) {
                @Override
                public void fire() {
                    menuButton.getItems().add(Integer.valueOf(addPosition.getText()), new ActionItem(MENU_ITEM + menuButton.getItems().size()));
                }
            };
            HBox addPositionBox = new HBox(5);
            addPositionBox.getChildren().addAll(addButonPos, new Label("at"), addPosition);

            final TextField removePosition = new TextField("0");
            removePosition.setId(REMOVE_POS_EDIT_ID);
            Button removeButton = new Button(REMOVE_SINGLE_AT_POS_BTN_ID) {
                @Override
                public void fire() {
                    menuButton.getItems().remove(Integer.valueOf(removePosition.getText()).intValue());
                }
            };
            HBox removePositionBox = new HBox(5);
            removePositionBox.getChildren().addAll(removeButton, new Label("at"), removePosition);

            sideChoiceBox = new ChoiceBox<Side>();
            sideChoiceBox.setId(SIDE_CB_ID);
            sideChoiceBox.getItems().addAll(Arrays.asList(Side.values()));
            sideChoiceBox.getSelectionModel().select(Side.BOTTOM);

            sideChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                public void changed(ObservableValue ov, Object t, Object t1) {
                    Side side = sideChoiceBox.getSelectionModel().getSelectedItem();
                    menuButton.setPopupSide(side);
                }
            });
            sideChoiceBox.setFocusTraversable(false);

            HBox lastSelectedBox = new HBox(5);

            lastSelected = new Label();
            lastSelected.setId(LAST_SELECTED_ID);
            lastSelectedBox.getChildren().addAll(new Label(LAST_SELECTED_ID), lastSelected);

            controls = new VBox(5);
            controls.getChildren().addAll(clearButton, resetButton, addButton,
                    addPositionBox, removePositionBox, sideChoiceBox, lastSelectedBox);

            ((VBox) getRoot()).getChildren().addAll(testPane, controls);

            reset();
        }

        protected void reset() {
            menuButton.getItems().clear();
            for (int i = 0; i < MENU_ITEMS_NUM; i++) {
                menuButton.getItems().add(new ActionItem(MENU_ITEM + i));
            }
            sideChoiceBox.getSelectionModel().select(Side.BOTTOM);
            menuButton.setPopupSide(Side.BOTTOM);
        }
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
}