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
package javafx.scene.control.test.splitpane;

import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.NodesChoserFactory;
import javafx.scene.control.test.utils.ptables.NodeControllerFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class SplitPaneApp extends InteroperabilityApp {

    public final static String TESTED_SPLIPANE_ID = "TESTED_SPLIPANE_ID";
    public final static String RESET_BUTTON_ID = "RESET_SPLIPANE_BUTTON_ID";
    public final static String SPLITPANE_ADD_INDEX_TEXT_FIELD_ID = "SPLITPANE_ADD_INDEX_TEXT_FIELD_ID";

    public static void main(String[] args) {
        Utils.launch(SplitPaneApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "SplitPaneTestApp");
        return new SplitPaneApp.SplitPaneScene();
    }

    class SplitPaneScene extends Scene {

        //VBox which contain tested split pane.
        Pane pane;
        //Splipane to be tested.
        SplitPane testedSplitPane;

        public SplitPaneScene() {
            super(new HBox(), 800, 600);

            prepareScene();
        }

        private void prepareScene() {
            pane = new Pane();
            testedSplitPane = new SplitPane();
            testedSplitPane.setId(TESTED_SPLIPANE_ID);

            PropertiesTable tb = new PropertiesTable(testedSplitPane);
            PropertyTablesFactory.explorePropertiesList(testedSplitPane, tb);

            final TabPaneWithControl tabPane = new TabPaneWithControl("Accordion", tb);

            final TextField tf = new TextField("0");
            tf.setId(SPLITPANE_ADD_INDEX_TEXT_FIELD_ID);
            tf.setPrefWidth(40);

            HBox nodeshb = new NodesChoserFactory("Add!", new NodesChoserFactory.NodeAction<Node>() {

                @Override
                public void execute(Node node) {
                    testedSplitPane.getItems().add(Integer.parseInt(tf.getText()), node);
                    tabPane.addPropertiesTable(node.getClass().getSimpleName(), NodeControllerFactory.createFullController(node, tabPane));
                }
            }, tf);

            pane.setMinSize(220, 220);
            pane.setPrefSize(220, 220);
            pane.setStyle("-fx-border-color : red;");
            pane.getChildren().add(testedSplitPane);

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

            Button resetButton = new Button("Reset");
            resetButton.setId(RESET_BUTTON_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareScene();
                }
            });

            vb.getChildren().addAll(new Label("Pane with tested Accordion"), pane, resetButton, nodeshb);

            hb.getChildren().addAll(vb, tabPane);
        }
    }
}
