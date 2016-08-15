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
package javafx.scene.control.test.manual;

import java.util.Arrays;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/*
 * @author Alexander Petrov, Alexander Kirov, Dmitry Zinkevich
 */
public class MenuBarAppManual extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TestPane";
    public final static String CLEAR_BTN_ID = "Clear";
    public final static String RESET_BTN_ID = "Reset";
    public final static String USESYSTEM_BTN_ID = "Use system menu bar";
    public final static String USESYSTEMCSS_BTN_ID = "Use system menu bar (using CSS)";
    public final static String ADD_SINGLE_AT_POS_BTN_ID = "Add Menu";
    public final static String REMOVE_SINGLE_AT_POS_BTN_ID = "Remove Menu";
    public final static String REMOVE_POS_EDIT_ID = "Remove at pos";
    public final static String ADD_POS_EDIT_ID = "Add at pos";
    public final static String MENU_STR = "Menu ";
    public final static int MENUS_NUM = 3;
    public final static int MENUS_DEPTH = 3;
    public final static String CLEAR_EVENT_ID = "Clear Events";
    private ShortcutGenerator shortcutGenerator = new ShortcutGenerator();
    private boolean useTwoMenuBars = false;

    public static void main(String[] args) {
        Utils.launch(MenuBarAppManual.class, args);
    }

    @Override
    protected Scene getScene() {
        Application.Parameters parameters = getParameters();
        useTwoMenuBars = parameters.getUnnamed().contains("useTwoMenuBars");

        return new MenuBarAppScene("", stage);
    }

    public class MenuBarAppScene extends Scene {

        private String sceneTag = "";
        private int childCount;
        private int childShift;
        private Stage parentStage;

        public MenuBarAppScene(String aTag, Stage aStage) {
            super(new VBox(20d));

            sceneTag = aTag; //prefix to distinguish different stages
            parentStage = aStage; //stage on which current scene is rendered

            Utils.addBrowser(this);

            //Add controls to create model windows with different modality states
            HBox controlsContainer = new HBox(5d);
            if (useTwoMenuBars) {
                controlsContainer.getChildren().addAll(
                        new MenuControls(sceneTag + "#1"),
                        new MenuControls(sceneTag + "#2"));
            } else {
                controlsContainer.getChildren().add(new MenuControls(sceneTag));
            }
            ((VBox) getRoot()).getChildren().add(controlsContainer);

            //Add controls to create modal windows with diffent modalities
            HBox createChildWindows = new HBox(5d);

            Label lblChildMod = new Label("Child's modality: ");

            final ComboBox<Modality> cmbModalities = new ComboBox<Modality>();
            cmbModalities.getItems().addAll(Arrays.asList(Modality.values()));
            cmbModalities.setValue(Modality.NONE);

            Button btnCreateChild = new Button("Create child");
            btnCreateChild.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    Stage newStage = new Stage();
                    newStage.initModality(cmbModalities.getValue());
                    newStage.setTitle("Modality: " + cmbModalities.getValue().name());
                    newStage.initOwner(parentStage);
                    newStage.setScene(new MenuBarAppScene(getModalWindowPrefix(), newStage));
                    childShift += 100;
                    newStage.setX(parentStage.getX() + childShift);
                    newStage.showAndWait();
                }
            });

            if (useTwoMenuBars) {
                createChildWindows.getChildren().addAll(lblChildMod, cmbModalities, btnCreateChild);
                ((VBox) getRoot()).getChildren().add(createChildWindows);
            }

            //If this scene belongs to the main stage
            if (useTwoMenuBars && parentStage == stage) {
                //Add button to create new non-modal window
                final Button btnCloneWindow = new Button("Clone window");
                btnCloneWindow.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        Stage newStage = new Stage();
                        newStage.setTitle("Second window");
                        newStage.setX(parentStage.getX() + 200);
                        newStage.setScene(new MenuBarAppScene("Clone", newStage));
                        newStage.show();
                        //no more stages
                        btnCloneWindow.setOnAction(null);
                    }
                });
                ((VBox) getRoot()).getChildren().addAll(new Separator(), btnCloneWindow);
            }
        }

        /**
         * This method generates prefix for modal window which helps to
         * distinguish it from other modal window in case there are many
         *
         * @return modal window prefix
         */
        private String getModalWindowPrefix() {
            StringBuilder stringBuilder = new StringBuilder(sceneTag);

            if (!sceneTag.equals("")) {
                stringBuilder.append("->");
            }
            stringBuilder.append("Ch#");
            stringBuilder.append(++childCount);

            return stringBuilder.toString();
        }
    }

    private class MenuControls extends VBox {

        private MenuBar menuBar;
        private Label lastVisited;
        private TextArea eventLog;
        private String prefix;
        private ShortcutGenerator initialShortcutGen;
        ToggleButton useSystem;
        ToggleButton useSystemCss;
        ToggleButton disableSomeMenusButton;
        ToggleButton setGraphicsToggle;
        ToggleButton disableMenuBarButton;

        public MenuControls(String prefix) {
            setSpacing(5d);
            this.prefix = prefix;
            this.initialShortcutGen = shortcutGenerator.clone();

            menuBar = new MenuBar();
            lastVisited = new Label();
            eventLog = new TextArea();

            Pane testPane = new Pane();
            testPane.setPrefSize(600, 300);
            testPane.setMaxSize(600, 300);
            getChildren().add(testPane);

            Label lblTitle = new Label("Panel " + prefix);

            testPane.getChildren().add(menuBar);

            Button clearButton = new Button(CLEAR_BTN_ID);
            clearButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    menuBar.getMenus().clear();
                }
            });

            Button resetButton = new Button(RESET_BTN_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    reset();
                }
            });

            final TextField addPosition = new TextField("0");

            Button addButtonPos = new Button(ADD_SINGLE_AT_POS_BTN_ID);
            addButtonPos.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    int pos = Integer.valueOf(addPosition.getText());
                    if (pos > menuBar.getMenus().size()) {
                        pos = menuBar.getMenus().size();
                    }
                    menuBar.getMenus().add(pos,
                            new BarItem(MENU_STR + menuBar.getMenus().size(), MENUS_DEPTH, eventLog, lastVisited));
                }
            });

            final Label addLabel = new Label("at");

            HBox addPositionBox = new HBox(5);
            addPositionBox.getChildren().addAll(addButtonPos, addLabel, addPosition);

            final TextField removePosition = new TextField("0");

            Button removeButton = new Button(REMOVE_SINGLE_AT_POS_BTN_ID);
            removeButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    if (menuBar.getMenus().size() > 0) {
                        int pos = Integer.valueOf(removePosition.getText());
                        if (pos > menuBar.getMenus().size() - 1) {
                            pos = menuBar.getMenus().size() - 1;
                        }
                        menuBar.getMenus().remove(pos);
                    }
                }
            });
            final Label removeLabel = new Label("at");

            HBox removePositionBox = new HBox(5);
            removePositionBox.getChildren().addAll(removeButton, removeLabel, removePosition);

            useSystem = new ToggleButton(USESYSTEM_BTN_ID);
            useSystem.setId(USESYSTEM_BTN_ID);
            useSystem.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    menuBar.setUseSystemMenuBar(t1);
                }
            });

            useSystemCss = new ToggleButton(USESYSTEMCSS_BTN_ID);
            useSystemCss.setId(USESYSTEMCSS_BTN_ID);
            useSystemCss.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    menuBar.setStyle("-fx-use-system-menu-bar: " + (t1 ? "true" : "false") + ";");
                }
            });

            setGraphicsToggle = new ToggleButton("Set graphics");
            setGraphicsToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1) {
                        menuBar.getMenus().get(1).setGraphic(new Rectangle(10, 10, Color.BLUE));
                        menuBar.getMenus().get(0).getItems().get(0).setGraphic(new Rectangle(10, 10, Color.RED));
                        menuBar.getMenus().get(2).getItems().get(2).setGraphic(new Rectangle(10, 10, Color.GREEN));
                    } else {
                        menuBar.getMenus().get(1).setGraphic(null);
                        menuBar.getMenus().get(0).getItems().get(0).setGraphic(null);
                        menuBar.getMenus().get(2).getItems().get(2).setGraphic(null);
                    }
                }
            });

            Button renamingButton = new Button("Rename");
            renamingButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    menuBar.getMenus().get(1).setText("Renamed " + menuBar.getMenus().get(1).getText());
                    menuBar.getMenus().get(0).getItems().get(0).setText("Renamed " + menuBar.getMenus().get(0).getItems().get(0).getText());
                    menuBar.getMenus().get(2).getItems().get(2).setText("Renamed " + menuBar.getMenus().get(2).getItems().get(2).getText());
                }
            });

            disableMenuBarButton = new ToggleButton("MenuBar disable");
            disableMenuBarButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    menuBar.setDisable(t1);
                }
            });

            HBox lastSelectedBox = new HBox(5);
            lastSelectedBox.getChildren().add(new Label("Last selected item:"));
            lastSelectedBox.getChildren().add(lastVisited);

            HBox eventsBox = new HBox(5);
            Button clear = new Button(CLEAR_EVENT_ID);
            clear.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    eventLog.clear();
                }
            });

            disableSomeMenusButton = new ToggleButton("Some menus disable");

            reset();

            final BooleanProperty mainDisablingProperty = new SimpleBooleanProperty(false);
            menuBar.getMenus().get(1).disableProperty().bind(mainDisablingProperty);
            menuBar.getMenus().get(0).getItems().get(0).disableProperty().bind(mainDisablingProperty);
            menuBar.getMenus().get(2).getItems().get(2).disableProperty().bind(mainDisablingProperty);
            disableSomeMenusButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    mainDisablingProperty.setValue(t1);
                }
            });

            eventsBox.getChildren().add(eventLog);
            eventsBox.getChildren().add(clear);

            VBox controls = new VBox(5);
            getChildren().add(controls);
            controls.getChildren().addAll(lblTitle,
                    clearButton, resetButton,
                    addPositionBox, removePositionBox, useSystem,
                    useSystemCss, disableMenuBarButton,
                    disableSomeMenusButton, setGraphicsToggle, renamingButton,
                    lastSelectedBox, eventsBox);

        }

        private void reset() {
            useSystem.setSelected(false);
            useSystemCss.setSelected(false);
            disableSomeMenusButton.setSelected(false);
            setGraphicsToggle.setSelected(false);
            disableMenuBarButton.setSelected(false);

            //restore shortcut generator to reproduce exactly the same menu shortcuts
            shortcutGenerator = initialShortcutGen.clone();

            menuBar.getMenus().clear();
            for (int i = 0; i < MENUS_NUM; i++) {
                menuBar.getMenus().add(new BarItem(prefix + " " + MENU_STR + i, MENUS_DEPTH, eventLog, lastVisited));
            }
        }
    }

    private class BarItem extends Menu {

        private TextArea log;
        private Label lastSelected;

        public BarItem(String str, int depth, TextArea log, Label lastSelected) {
            super(str);
            this.log = log;
            this.lastSelected = lastSelected;
            addSubMenu(this, depth - 1);
            setLog(this, this.log);
        }

        private void addSubMenu(MenuItem root, int level) {
            for (int i = 0; i < MENUS_NUM; i++) {
                final MenuItem item;
                if (level > 0) {
                    item = new Menu(root.getText() + " SubMenu " + i);
                    setLog((Menu) item, this.log);
                } else {
                    item = new MenuItem(root.getText() + " MenuItem " + i);
                }
                item.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        lastSelected.setText(item.getText());
                    }
                });
                ((Menu) root).getItems().add(item);
                if (level > 0) {
                    addSubMenu(item, level - 1);
                } else {
                    item.setAccelerator(shortcutGenerator.getShortcut());
                }
            }
        }

        protected void setLog(final Menu menu, final TextArea log) {
            menu.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    log.appendText("OnAction event from " + menu.getText() + "\n");
                    log.end();
                }
            });
            menu.setOnHiding(new EventHandler<Event>() {
                public void handle(Event t) {
                    log.appendText("OnHiding event from " + menu.getText() + "\n");
                    log.end();
                }
            });
            menu.setOnHidden(new EventHandler<Event>() {
                public void handle(Event t) {
                    log.appendText("OnHidden event from " + menu.getText() + "\n");
                    log.end();
                }
            });
            menu.setOnShowing(new EventHandler<Event>() {
                public void handle(Event t) {
                    log.appendText("OnShowing event from " + menu.getText() + "\n");
                    log.end();
                }
            });
            menu.setOnShown(new EventHandler<Event>() {
                public void handle(Event t) {
                    log.appendText("OnShown event from " + menu.getText() + "\n");
                    log.end();
                }
            });
        }
    }

    private class ShortcutGenerator {

        private char c;
        private int mask;
        private String mods[] = new String[]{"ctrl", "alt", "shift"};

        public ShortcutGenerator() {
            c = 'A';
            mask = 0;
        }

        public ShortcutGenerator(char c, int mask) {
            this.c = c;
            this.mask = mask;
        }

        public KeyCombination getShortcut() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mods.length; i++) {
                if (((mask >> i) & 1) != 0) {
                    stringBuilder.append(mods[i]).append("+");
                }
            }
            stringBuilder.append(c);

            if (++c > 'Z') {
                c = 'A';
                mask++;
            }

            KeyCombination kk = KeyCombination.keyCombination(stringBuilder.toString());
            return kk;
        }

        @Override
        public ShortcutGenerator clone() {
            return new ShortcutGenerator(c, mask);
        }
    }
}
