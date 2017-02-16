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

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.factory.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.*;
import javafx.scene.control.test.utils.ptables.NodeControllerFactory.NodesStorage;
import static javafx.scene.control.test.utils.ptables.NodesChoserFactory.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class NewTabPaneApp extends InteroperabilityApp {

    public final static String TESTED_TABPANE_ID = "TESTED_TABPANE_ID";
    public final static String RESET_BUTTON_ID = "RESET_TABPANE_BUTTON_ID";
    public final static String RESET_SOFTLY_BUTTON_ID = "RESET_SOFTLY_BUTTON_ID";
    public final static String TABPANE_ADD_INDEX_TEXT_FIELD_ID = "TABPANE_ADD_INDEX_TEXT_FIELD_ID";
    public final static String NEW_TAB_ID_TEXT_FIELD_ID = "TAB_ID";
    public final static String FOCUS_RECEIVING_BUTTON_ID = "FOCUS_RECEIVING_BUTTON_ID";
    public final static String TAB_CLOSE_REQUEST_EVENT = "TAB_CLOSE_REQUEST_EVENT";
    public final static String VETO_CHECKBOX_ID = "VETO_CHECKBOX_ID";

    public static void main(String[] args) {
        Utils.launch(NewTabPaneApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "TabPaneTestApp");
        NewTabPaneApp.TabPaneScene tabPaneScene = new NewTabPaneApp.TabPaneScene();
        Utils.addBrowser(tabPaneScene);
        return tabPaneScene;
    }

    class TabPaneScene extends CommonPropertiesScene {

        //VBox which contain tested TabPane.
        Pane pane;
        //TabPane to be tested.
        TabPane testedTabPane;
        TabPaneWithControl tabPane;
        private PropertiesTable tb;

        public TabPaneScene() {
            super("Tab pane tests", 800, 600);

            prepareScene();
        }

        @Override
        public final void prepareScene() {
            pane = new Pane();
            pane.setMinSize(220, 220);
            pane.setPrefSize(220, 220);
            pane.setStyle("-fx-border-color : red;");

            testedTabPane = new TabPane();
            testedTabPane.setId(TESTED_TABPANE_ID);
            pane.getChildren().add(testedTabPane);

            tb = new PropertiesTable(testedTabPane);
            PropertyTablesFactory.explorePropertiesList(testedTabPane, tb);

            Button btnResetHardly = new Button("Reset");
            btnResetHardly.setId(RESET_BUTTON_ID);
            btnResetHardly.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button btnResetSoftly = new Button("Reset softly");
            btnResetSoftly.setId(RESET_SOFTLY_BUTTON_ID);
            btnResetSoftly.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {

                    tb.refresh();

                    TabPane newInstance = new TabPane();
                    while (testedTabPane.getTabs().size() != 0) {
                        testedTabPane.getTabs().remove(0);
                    }

                    testedTabPane.setSide(newInstance.getSide());
                    testedTabPane.setPrefHeight(newInstance.getPrefHeight());
                    testedTabPane.setMinHeight(newInstance.getMinHeight());
                    testedTabPane.setMaxHeight(newInstance.getMaxHeight());
                    testedTabPane.setPrefWidth(newInstance.getPrefWidth());
                    testedTabPane.setMinWidth(newInstance.getMinWidth());
                    testedTabPane.setMaxWidth(newInstance.getMaxWidth());
                    testedTabPane.setVisible(newInstance.isVisible());
                    testedTabPane.setDisable(newInstance.isDisable());
                    testedTabPane.setContextMenu(newInstance.getContextMenu());

                    tabPane.removePropertiesTablesExceptFirstOnes(1);
                }
            });

            tabPane = new TabPaneWithControl("TabPane", tb);



            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.getChildren().addAll(
                    btnResetHardly,
                    btnResetSoftly,
                    getControlsForTabCreation(tabPane),
                    getButtonForFocus());

            setTestedControl(testedTabPane);
            setControllersContent(vb);
            setPropertiesContent(tabPane);
        }

        private Button getButtonForFocus() {
            Button b = new Button("empty button");
            b.setId("FOCUS_RECEIVING_BUTTON_ID");
            return b;
        }

        /**
         *
         * @return controls which set parameters and create the new tab
         */
        private Pane getControlsForTabCreation(final TabPaneWithControl tabPane) {

            GridPane grid = new GridPane();

            grid.setStyle("-fx-border-color:DARKBLUE");
            grid.setHgap(5);
            grid.setVgap(5);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(40);
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setPercentWidth(60);

            grid.getColumnConstraints().addAll(column1, column2);
            //row #1
            grid.add(new Label("Tab ID"), 0, 0);
            final TextField newTabId = new TextField("ID");
            newTabId.setPrefWidth(40d);
            newTabId.setId(NEW_TAB_ID_TEXT_FIELD_ID);
            grid.add(newTabId, 1, 0);

            //row #2
            grid.add(new Label("Index"), 0, 1);
            final TextField newTabIndex = new TextField("0");
            newTabIndex.setId(TABPANE_ADD_INDEX_TEXT_FIELD_ID);
            newTabIndex.setPrefWidth(40);
            grid.add(newTabIndex, 1, 1);

            //row #3
            grid.add(new Label("Content"), 0, 2);
            final ChoiceBox<NodeFactory> cb = new ChoiceBox<NodeFactory>();
            cb.setId(NODE_CHOSER_CHOICE_BOX_ID);
            cb.getItems().addAll(ControlsFactory.filteredValues());
            cb.getItems().addAll(Shapes.values());
            cb.getItems().addAll(Panes.values());
            grid.add(cb, 1, 2);

            //row #4
            /*
             * If true then there will be created a properties pane
             * for tab content each time a tab is added
             */
            final CheckBox createPropsTab = new CheckBox("Create additional panes\n with content properties");
            grid.add(createPropsTab, 0, 3, 2, 1);

            //row #5
            /*
             * If true then there will be created a properties pane
             * for tab content each time a tab is added
             */
            final CheckBox vetoOnClose = new CheckBox("Veto tab request on close.");
            vetoOnClose.setId(VETO_CHECKBOX_ID);
            grid.add(vetoOnClose, 0, 4, 2, 1);

            //row #6
            Button actionButton = new Button("Create new tab");
            actionButton.setId(NODE_CHOOSER_ACTION_BUTTON_ID);

            final NodesChoserFactory.NodeAction<Node> handler = new NodesChoserFactory.NodeAction<Node>() {
                @Override
                public void execute(Node node) {
                    Tab tab = new Tab();

                    tab.setId(newTabId.getText());
                    tab.setText(newTabId.getText());
                    tab.setContent(node);

                    final NodesStorage fullController = NodeControllerFactory.createFullController(tab, tabPane);
                    fullController.pt.addCounter(TAB_CLOSE_REQUEST_EVENT);

                    tab.setOnCloseRequest(new EventHandler<Event>() {
                        private boolean useVeto = vetoOnClose.isSelected();

                        public void handle(Event t) {
                            fullController.pt.incrementCounter(TAB_CLOSE_REQUEST_EVENT);

                            if (useVeto) {
                                t.consume();
                            }
                        }
                    });

                    tabPane.addPropertiesTable(newTabId.getText(), fullController);
                    testedTabPane.getTabs().add(Integer.parseInt(newTabIndex.getText()), tab);

                    if (createPropsTab.isSelected()) {
                        tabPane.addPropertiesTable(node.getClass().getSimpleName(), NodeControllerFactory.createFullController(node, tabPane));
                    }
                }
            };

            actionButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    handler.execute(cb.getSelectionModel().getSelectedItem().createNode());
                }
            });
            grid.add(actionButton, 0, 5, 2, 1);

            return grid;
        }
    }
}
