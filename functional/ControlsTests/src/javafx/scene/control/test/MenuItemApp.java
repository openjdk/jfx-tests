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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class MenuItemApp extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TestPane";
    public final static String CONSTRUCTORS_ID = "Constructors";
    public final static String MIXED_ID = "Mixed";
    public final static String SIMPLE_ID = "Simple";
    public final static String RADIO_ID = "Radio";
    public final static String CHECK_ID = "Check";
    public final static String NODE_ID = "Node";
    public final static String INVISIBLE_ID = "Invisible";
    public final static String ALL_INVISIBLE_ID = "All Invisible";
    public final static String EVENT_CB_ID = "Event listener set";
    public final static String LAST_EVENT_ID = "Last event";
    public final static String LAST_EVENT_CLEAR_BTN_ID = "Clear last event";
    public final static String DISABLE_CB_ID = "Disable all items";
    public final static String HIDE_CB_ID = "Hide all items";
    public final static String LAST_PRESSED_ID = "Last pressed item ID";
    public final static String LAST_PRESSED_CLEAR_BTN_ID = "Clear last pressed ID";
    public final static String MENU_ITEM_VOID_ID = "VoidMenuItem()";
    public final static String MENU_ITEM_STRING_ID = "StringMenuItem(name)";
    public final static String MENU_ITEM_GRAPHICS_ID = "GraphicsMenuItem(name, node)";
    public final static KeyCombination MENU_ITEM_ACCELERATOR = KeyCombination.keyCombination("A");
    public final static String CHECK_MENU_ITEM_VOID_ID = "CheckMenuItem()";
    public final static String CHECK_MENU_ITEM_ID = "CheckMenuItem(name)";
    public final static String CHECK_MENU_ITEM_GRAPHICS_ID = "CheckMenuItem(name, node)";
    public final static KeyCombination CHECK_MENU_ITEM_ACCELERATOR = KeyCombination.keyCombination("B");
    public final static String RADIO_MENU_ITEM_ID = "RadioMenuItem(name)";
    public final static String RADIO_MENU_ITEM_GRAPHICS_ID = "RadioMenuItem(name, node)";
    public final static String RADIO_MENU_ITEM_PARAMETERLESS_ID = "RadioMenuItem()";
    public final static KeyCombination RADIO_MENU_ITEM_ACCELERATOR = KeyCombination.keyCombination("C");
    public final static String NODE_MENU_ITEM_VOID_ID = "CustomMenuItem()";
    public final static String NODE_MENU_ITEM_ID = "CustomMenuItem(node)";
    public final static String NODE_MENU_ITEM_BOOL_ID = "CustomMenuItem(node, hide)";
    public final static KeyCombination NODE_MENU_ITEM_ACCELERATOR = KeyCombination.keyCombination("D");
    public final static String SEPARATOR_MENU_ITEM_VOID_ID = "SepartorMenuItem()";
    VBox root;

    public static void main(String[] args) {
        Utils.launch(MenuItemApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new MenuBarAppScene();
    }

    public class MenuBarAppScene extends Scene {

        final MenuBar bar;
        Label lastPressed;
        Label lastEvent;
        Menu invisibleMenu;
        Menu allInvisibleMenu;
        EventHandler handler;

        class Constructors extends Menu {

            public Constructors() {
                super(CONSTRUCTORS_ID);
                setId(CONSTRUCTORS_ID);

                MenuItem void_menu_item = new MenuItem();
                void_menu_item.setId(MENU_ITEM_VOID_ID);
                MenuItem string_menu_item = new MenuItem(MENU_ITEM_STRING_ID);
                string_menu_item.setId(MENU_ITEM_STRING_ID);
                MenuItem graphics_menu_item = new MenuItem(MENU_ITEM_GRAPHICS_ID, new Rectangle(10, 10));
                graphics_menu_item.setId(MENU_ITEM_GRAPHICS_ID);

                CheckMenuItem void_check_menu_item = new CheckMenuItem();
                void_check_menu_item.setId(CHECK_MENU_ITEM_VOID_ID);
                CheckMenuItem string_check_menu_item = new CheckMenuItem(CHECK_MENU_ITEM_ID);
                string_check_menu_item.setId(CHECK_MENU_ITEM_ID);
                CheckMenuItem graphics_check_menu_item = new CheckMenuItem(CHECK_MENU_ITEM_GRAPHICS_ID, new Rectangle(10, 10));
                graphics_check_menu_item.setId(CHECK_MENU_ITEM_GRAPHICS_ID);

                RadioMenuItem string_radio_menu_item_parameterless = new RadioMenuItem();
                string_radio_menu_item_parameterless.setId(RADIO_MENU_ITEM_PARAMETERLESS_ID);
                RadioMenuItem string_radio_menu_item = new RadioMenuItem(RADIO_MENU_ITEM_ID);
                string_radio_menu_item.setId(RADIO_MENU_ITEM_ID);
                RadioMenuItem graphics_radio_menu_item = new RadioMenuItem(RADIO_MENU_ITEM_GRAPHICS_ID, new Rectangle(10, 10));
                graphics_radio_menu_item.setId(RADIO_MENU_ITEM_GRAPHICS_ID);

                CustomMenuItem void_node_menu_item = new CustomMenuItem();
                HBox void_node_box = new HBox();
                void_node_box.getChildren().addAll(new Rectangle(10, 10), new Label(NODE_MENU_ITEM_VOID_ID));
                void_node_menu_item.setContent(void_node_box);
                void_node_menu_item.setId(NODE_MENU_ITEM_VOID_ID);
                HBox node_box = new HBox();
                node_box.getChildren().addAll(new Rectangle(10, 10), new Label(NODE_MENU_ITEM_ID));
                CustomMenuItem node_menu_item = new CustomMenuItem(node_box);
                node_menu_item.setId(NODE_MENU_ITEM_ID);
                HBox node_box_bool = new HBox();
                node_box_bool.getChildren().addAll(new Rectangle(10, 10), new Label(NODE_MENU_ITEM_BOOL_ID));
                CustomMenuItem graphics_node_menu_item = new CustomMenuItem(node_box_bool, true);
                graphics_node_menu_item.setId(NODE_MENU_ITEM_BOOL_ID);

                SeparatorMenuItem separator_menu_item = new SeparatorMenuItem();
                separator_menu_item.setId(SEPARATOR_MENU_ITEM_VOID_ID);

                getItems().addAll(void_menu_item, string_menu_item, graphics_menu_item,
                        void_check_menu_item, string_check_menu_item, graphics_check_menu_item,
                        string_radio_menu_item_parameterless, string_radio_menu_item, graphics_radio_menu_item,
                        void_node_menu_item, node_menu_item, graphics_node_menu_item,
                        separator_menu_item);
            }
        }

        class MixedItemsMenu extends EventsMenu {

            public MixedItemsMenu() {
                super(MIXED_ID);
                setId(MIXED_ID);

                MenuItem graphics_menu_item = new MenuItem(MENU_ITEM_GRAPHICS_ID, new Rectangle(10, 10));
                graphics_menu_item.setId(MENU_ITEM_GRAPHICS_ID);
                graphics_menu_item.setAccelerator(MENU_ITEM_ACCELERATOR);

                CheckMenuItem graphics_check_menu_item = new CheckMenuItem(CHECK_MENU_ITEM_GRAPHICS_ID, new Rectangle(10, 10));
                graphics_check_menu_item.setId(CHECK_MENU_ITEM_GRAPHICS_ID);
                graphics_check_menu_item.setAccelerator(CHECK_MENU_ITEM_ACCELERATOR);

                RadioMenuItem graphics_radio_menu_item = new RadioMenuItem(RADIO_MENU_ITEM_GRAPHICS_ID, new Rectangle(10, 10));
                graphics_radio_menu_item.setId(RADIO_MENU_ITEM_GRAPHICS_ID);
                graphics_radio_menu_item.setAccelerator(RADIO_MENU_ITEM_ACCELERATOR);

                HBox node_box_bool = new HBox();
                node_box_bool.getChildren().addAll(new Rectangle(10, 10), new Label(NODE_MENU_ITEM_BOOL_ID));
                CustomMenuItem graphics_node_menu_item = new CustomMenuItem(node_box_bool, true);
                graphics_node_menu_item.setId(NODE_MENU_ITEM_BOOL_ID);
                graphics_node_menu_item.setAccelerator(NODE_MENU_ITEM_ACCELERATOR);

                SeparatorMenuItem separator_menu_item = new SeparatorMenuItem();
                separator_menu_item.setId(SEPARATOR_MENU_ITEM_VOID_ID);

                getItems().addAll(graphics_menu_item,
                        graphics_check_menu_item,
                        graphics_radio_menu_item,
                        graphics_node_menu_item,
                        separator_menu_item);

                setEventHandlers();
            }
        }

        class SimpleItemsMenu extends EventsMenu {

            public SimpleItemsMenu() {
                super(SIMPLE_ID);
                setId(SIMPLE_ID);

                for (int i = 0; i < 3; i++) {
                    MenuItem item = new MenuItem("Item " + i, new Rectangle(10, 10));
                    item.setId(item.getText());
                    getItems().add(item);
                }
                setEventHandlers();
            }
        }

        class RadioItemsMenu extends EventsMenu {

            public RadioItemsMenu() {
                super(RADIO_ID);
                setId(RADIO_ID);

                ToggleGroup group = new ToggleGroup();

                for (int i = 0; i < 3; i++) {
                    RadioMenuItem item = new RadioMenuItem("Item " + i, new Rectangle(10, 10));
                    item.setId(item.getText());
                    item.setToggleGroup(group);
                    getItems().add(item);
                }
                setEventHandlers();
            }
        }

        class CheckItemsMenu extends EventsMenu {

            public CheckItemsMenu() {
                super(CHECK_ID);
                setId(CHECK_ID);

                for (int i = 0; i < 3; i++) {
                    CheckMenuItem item = new CheckMenuItem("Item " + i, new Rectangle(10, 10));
                    item.setId(item.getText());
                    getItems().add(item);
                }
                setEventHandlers();
            }
        }

        class NodeItemsMenu extends EventsMenu {

            public NodeItemsMenu() {
                super(NODE_ID);
                setId(NODE_ID);

                for (int i = 0; i < 3; i++) {
                    CustomMenuItem item = new CustomMenuItem(new Label("Item " + i));
                    item.setId("Item " + i);
                    getItems().add(item);
                }

                setEventHandlers();
            }
        }

        class InvisibleItemsMenu extends EventsMenu {

            public InvisibleItemsMenu() {
                super(INVISIBLE_ID);
                setId(INVISIBLE_ID);

                getItems().addAll(
                        new MenuItem("Menu Item", new Rectangle(10, 10)),
                        new MenuItem("Invisible Menu Item", new Rectangle(10, 10)),
                        new CheckMenuItem("Check Item", new Rectangle(10, 10)),
                        new CheckMenuItem("Invisible Check Item", new Rectangle(10, 10)),
                        new RadioMenuItem("Radio Item", new Rectangle(10, 10)),
                        new RadioMenuItem("Invisible Radio Item", new Rectangle(10, 10)),
                        new CustomMenuItem(new Label("Custom Item")),
                        new CustomMenuItem(new Label("Invisible Custom Item")));

                setEventHandlers();
            }
        }

        class AllInvisibleItemsMenu extends EventsMenu {

            public AllInvisibleItemsMenu() {
                super(ALL_INVISIBLE_ID);
                setId(ALL_INVISIBLE_ID);

                getItems().addAll(
                        new MenuItem("Invisible Menu Item", new Rectangle(10, 10)),
                        new CheckMenuItem("Invisible Check Item", new Rectangle(10, 10)),
                        new RadioMenuItem("Invisible Radio Item", new Rectangle(10, 10)),
                        new CustomMenuItem(new Label("Invisible Custom Item")));

                setEventHandlers();
            }
        }

        class EventsMenu extends Menu {

            public EventsMenu(String str) {
                super(str);
            }

            public void setEventHandlers() {
                for (final MenuItem item : getItems()) {
                    item.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            lastPressed.setText(item.getId());
                        }
                    });
                }
            }
        }

        public MenuBarAppScene() {
            super(root = new VBox(5));

            Utils.addBrowser(this);

            Pane test_pane = new Pane();
            test_pane.setId(TEST_PANE_ID);
            test_pane.setPrefSize(600, 400);
            test_pane.setMaxSize(600, 400);
            root.getChildren().add(test_pane);

            bar = new MenuBar();
            invisibleMenu = new InvisibleItemsMenu();
            allInvisibleMenu = new AllInvisibleItemsMenu();
            bar.getMenus().addAll(new Constructors(), new MixedItemsMenu(), new RadioItemsMenu(), new CheckItemsMenu(), new NodeItemsMenu(), new SimpleItemsMenu(), invisibleMenu, allInvisibleMenu);
            test_pane.getChildren().add(bar);

            CheckBox event = new CheckBox(EVENT_CB_ID);
            event.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    event(t1);
                }
            });
            root.getChildren().add(event);

            CheckBox disable = new CheckBox(DISABLE_CB_ID);
            disable.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    disable(t1);
                }
            });
            root.getChildren().add(disable);

            CheckBox hide = new CheckBox(HIDE_CB_ID);
            hide.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    visible(!t1);
                }
            });
            root.getChildren().add(hide);

            HBox last_pressed_box = new HBox(5);
            lastPressed = new Label();
            lastPressed.setMinWidth(200);
            lastPressed.setPrefWidth(200);
            lastPressed.setId(LAST_PRESSED_ID);
            Button last_pressed_clear = new Button(LAST_PRESSED_CLEAR_BTN_ID);
            last_pressed_clear.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    lastPressed.setText("");
                }
            });
            last_pressed_clear.setId(LAST_PRESSED_CLEAR_BTN_ID);
            last_pressed_box.getChildren().addAll(new Label("Last pressed ID"), lastPressed, last_pressed_clear);

            root.getChildren().add(last_pressed_box);

            HBox event_box = new HBox(5);
            lastEvent = new Label();
            lastEvent.setMinWidth(200);
            lastEvent.setPrefWidth(200);
            lastEvent.setId(LAST_EVENT_ID);
            Button last_action_clear = new Button(LAST_EVENT_CLEAR_BTN_ID);
            last_action_clear.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    lastEvent.setText("");
                }
            });
            last_action_clear.setId(LAST_EVENT_CLEAR_BTN_ID);
            event_box.getChildren().addAll(new Label("Last event"), lastEvent, last_action_clear);

            handler = new EventHandler<Event>() {
                public void handle(Event t) {
                    lastEvent.setText(((MenuItem) t.getTarget()).getId());
                }
            };

            root.getChildren().add(event_box);
        }

        protected void disable(boolean disable) {
            for (Menu menu : bar.getMenus()) {
                for (MenuItem item : menu.getItems()) {
                    item.setDisable(disable);
                }
            }
        }

        protected void event(boolean set) {
            for (Menu menu : bar.getMenus()) {
                for (MenuItem item : menu.getItems()) {
                    if (set) {
                        item.addEventHandler(Event.ANY, handler);
                    } else {
                        item.removeEventHandler(Event.ANY, handler);
                    }
                }
            }
        }

        protected void visible(boolean visible) {
            for (MenuItem item : allInvisibleMenu.getItems()) {
                item.setVisible(visible);
            }
            if (visible) {
                for (MenuItem item : invisibleMenu.getItems()) {
                    item.setVisible(visible);
                }
            } else {
                for (MenuItem item : invisibleMenu.getItems()) {
                    visible = !visible;
                    item.setVisible(visible);
                }
            }
        }
    }
}