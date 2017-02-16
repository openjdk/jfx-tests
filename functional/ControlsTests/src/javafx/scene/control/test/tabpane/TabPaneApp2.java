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

package javafx.scene.control.test.tabpane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class TabPaneApp2 extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TestPane";
    public final static String CLEAR_BTN_ID = "Clear";
    public final static String RESET_BTN_ID = "Reset";
    public final static String ADD_SINGLE_BTN_ID = "Add single item";
    public final static String ADD_SINGLE_AT_POS_BTN_ID = "Add single item at pos";
    public final static String DISABLE_SINGLE_AT_POS_BTN_ID = "Disable single item at pos";
    public final static String REMOVE_SINGLE_AT_POS_BTN_ID = "Remove single item at pos";
    public final static String REMOVE_POS_EDIT_ID = "Remove at pos";
    public final static String ADD_POS_EDIT_ID = "Add at pos";
    public final static String DISABLE_POS_EDIT_ID = "Disable at pos";
    public final static String LAST_SELECTED_ID = "Last selected";
    public final static String SHOWN_CHECK_ID = "Shown";
    public final static String SHOWING_CHECK_ID = "Showing";
    public final static String SIDE_CB_ID = "Side";
    public final static String ERROR_ID = "Error";
    public final static String EVENT_LIST_ID = "Event list";
    public final static String TAB_ITEM = "Tab ";
    public final static int TABS_NUM = 10;

    VBox root;
    Label last_selected;
    Label error;
    ListView<String> eventList;

    public static void main(String[] args) {
        Utils.launch(TabPaneApp2.class, args);
    }

    @Override
    protected Scene getScene() {
        return new TabPaneAppScene();
    }

    public class TabPaneAppScene extends Scene {
        final TabPane tabPane;

        public TabPaneAppScene() {
            super(root = new VBox());

            Utils.addBrowser(this);

            Pane testPane = new Pane();
            testPane.setId(TEST_PANE_ID);
            testPane.setMinSize(600, 400);
            testPane.setPrefSize(600, 400);
            testPane.setMaxSize(600, 400);
            root.getChildren().add(testPane);

            tabPane = new TabPane();
            tabPane.setTranslateX(200);
            tabPane.setTranslateY(150);
            tabPane.setPrefSize(200, 200);
            testPane.getChildren().add(tabPane);

            HBox control_pane = new HBox();
            root.getChildren().add(control_pane);

            VBox controls = new VBox(5);
            control_pane.getChildren().add(controls);

            error = new Label();
            error.setId(ERROR_ID);

            Button temp;

            temp = new Button(CLEAR_BTN_ID);
            temp.setId(CLEAR_BTN_ID);
            temp.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            tabPane.getTabs().clear();
                        }
            });
            controls.getChildren().add(temp);

            temp = new Button(RESET_BTN_ID);
            temp.setId(RESET_BTN_ID);
            temp.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            reset();
                        }
            });
            controls.getChildren().add(temp);

            temp = new Button(ADD_SINGLE_BTN_ID);
            temp.setId(ADD_SINGLE_BTN_ID);
            temp.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            tabPane.getTabs().add(new NamedTab(TAB_ITEM + tabPane.getTabs().size()));
                        }
            });
            controls.getChildren().add(temp);

            HBox add_position_box = new HBox(5);
            controls.getChildren().add(add_position_box);

            final TextField add_position = new TextField("0");
            add_position.setId(ADD_POS_EDIT_ID);

            temp = new Button(ADD_SINGLE_AT_POS_BTN_ID);
            temp.setId(ADD_SINGLE_AT_POS_BTN_ID);
            temp.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            tabPane.getTabs().add(new NamedTab("Tab " + tabPane.getTabs().size()));
                        }
            });
            add_position_box.getChildren().add(temp);

            final Label add_label = new Label("at");
            add_position_box.getChildren().add(add_label);

            add_position_box.getChildren().add(add_position);


            HBox disable_position_box = new HBox(5);
            controls.getChildren().add(disable_position_box);

            final TextField disable_position = new TextField("0");
            disable_position.setId(DISABLE_POS_EDIT_ID);

            temp = new Button(DISABLE_SINGLE_AT_POS_BTN_ID);
            temp.setId(DISABLE_SINGLE_AT_POS_BTN_ID);
            temp.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            Integer pos = Integer.valueOf(disable_position.getText());
                            Tab tab = tabPane.getTabs().get(pos.intValue());
                            tab.setDisable(!tab.isDisable());
                        }
            });
            disable_position_box.getChildren().add(temp);

            final Label disable_label = new Label("at");
            disable_position_box.getChildren().add(disable_label);

            disable_position_box.getChildren().add(disable_position);


            HBox remove_position_box = new HBox(5);
            controls.getChildren().add(remove_position_box);

            final TextField remove_position = new TextField("0");
            remove_position.setId(REMOVE_POS_EDIT_ID);

            temp = new Button(REMOVE_SINGLE_AT_POS_BTN_ID);
            temp.setId(REMOVE_SINGLE_AT_POS_BTN_ID);
            temp.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            tabPane.getTabs().remove(Integer.valueOf(remove_position.getText()).intValue());
                        }
            });
            remove_position_box.getChildren().add(temp);

            final Label remove_label = new Label("at");
            remove_position_box.getChildren().add(remove_label);

            remove_position_box.getChildren().add(remove_position);

            final ChoiceBox<Side> cb = new ChoiceBox<Side>();
            cb.setId(SIDE_CB_ID);
            cb.getItems().addAll(Side.values());
            cb.getSelectionModel().select(tabPane.getSide());
            cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                public void changed(ObservableValue ov, Object t, Object t1) {
                    tabPane.setSide(cb.getSelectionModel().getSelectedItem());
                }
            });

            controls.getChildren().add(cb);

            HBox last_selected_box = new HBox(5);
            controls.getChildren().add(last_selected_box);

            Label prompt_last_selected = new Label(LAST_SELECTED_ID);
            last_selected = new Label();
            last_selected.setId(LAST_SELECTED_ID);
            last_selected_box.getChildren().add(prompt_last_selected);
            last_selected_box.getChildren().add(last_selected);
            last_selected.setText(String.valueOf(tabPane.getSelectionModel().getSelectedIndex()));

            tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {
                public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                    last_selected.setText(t1.toString());
                }
            });

            controls.getChildren().add(error);

            eventList = new ListView<String>();
            eventList.setId(EVENT_LIST_ID);
            eventList.setMaxSize(320, 170);

            control_pane.getChildren().add(eventList);

            reset();
        }

        protected void reset() {
            tabPane.getTabs().clear();
            eventList.getItems().clear();
            for (int i = 0; i < TABS_NUM; i++) {
                Tab tab = new NamedTab("Tab " + i);
                tab.setTooltip(new Tooltip("Tab " + i));
                ContextMenu menu = new ContextMenu();
                for (int j = 0; j < 3; j++) {
                    menu.getItems().add(new MenuItem("Tab " + i + " menu item " + j));
                }
                tab.setContextMenu(menu);
                if (tab.getContextMenu() != menu) {
                    error.setText("tab.setContextMenu() fails");
                }
                tabPane.getTabs().add(tab);
            }
            tabPane.setSide(Side.TOP);
            error.setText("");
        }

        class NamedTab extends Tab {
            public NamedTab(String name) {
                super(name);
                Label label = new Label(name + " content");
                setContent(label);
                EventHandler handler = new EventHandler<Event>() {
                    public void handle(Event t) {
                        eventList.getItems().add(((Tab)t.getTarget()).getText());
                    }
                };
                setOnSelectionChanged(handler);
                if (getOnSelectionChanged() != handler) {
                    error.setText("tab.setOnSelectionChanged() fails");
                }
            }
        }
    }
}
