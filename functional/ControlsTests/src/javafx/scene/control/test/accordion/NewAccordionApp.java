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
package javafx.scene.control.test.Accordion;

import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.NodesChoserFactory;
import javafx.scene.control.test.utils.ptables.NodeControllerFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class NewAccordionApp extends InteroperabilityApp {

    public final static String TESTED_ACCORDION_ID = "TESTED_ACCORDION_ID";
    public final static String RESET_BUTTON_ID = "RESET_ACCORDION_BUTTON_ID";
    public final String ACCORDION_ADD_INDEX_TEXT_FIELD_ID = "ACCORDION_ADD_INDEX_TEXT_FIELD_ID";

    public static void main(String[] args) {
        Utils.launch(NewAccordionApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "AccordionTestApp");
        return new NewAccordionApp.AccordionScene();
    }

    class AccordionScene extends Scene {

        //VBox which contain tested Accordion.
        Pane pane;
        //Accordion to be tested.
        Accordion testedAccordion;

        public AccordionScene() {
            super(new HBox(), 800, 600);

            prepareScene();
        }

        private void prepareScene() {
            pane = new Pane();
            testedAccordion = new Accordion();
            testedAccordion.setId(TESTED_ACCORDION_ID);

            PropertiesTable tb = new PropertiesTable(testedAccordion);
            PropertyTablesFactory.explorePropertiesList(testedAccordion, tb);

            final TabPaneWithControl tabPane = new TabPaneWithControl("Accordion", tb);

            final TextField tf = new TextField("0");
            tf.setId(ACCORDION_ADD_INDEX_TEXT_FIELD_ID);
            tf.setPrefWidth(40);

            HBox nodeshb = new NodesChoserFactory("Add!", new NodesChoserFactory.NodeAction<Node>() {

                @Override
                public void execute(Node node) {
                    TitledPane tp = new TitledPane(node.getClass().getSimpleName(), node);
                    testedAccordion.getPanes().add(Integer.parseInt(tf.getText()), tp);
                    tabPane.addPropertiesTable(node.getClass().getSimpleName(), NodeControllerFactory.createFullController(node, tabPane));
                    tabPane.addPropertiesTable(tp.getClass().getSimpleName(), NodeControllerFactory.createFullController(tp, tabPane));
                }
            }, tf);

            pane.setMinSize(220, 220);
            pane.setPrefSize(220, 220);
            pane.setStyle("-fx-border-color : red;");
            pane.getChildren().add(testedAccordion);

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

            final ToggleButton compactStateToggle = new ToggleButton("Compact");
            compactStateToggle.setSelected(true);
            compactStateToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {

                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (t1) {
                        compactStateToggle.setText("Compact");
                        pane.setMinSize(220, 220);
                        pane.setPrefSize(220, 220);
                        AccordionScene.this.getWindow().setWidth(1200);
                        AccordionScene.this.getWindow().setHeight(700);
                    } else {
                        compactStateToggle.setText("Free");
                        pane.setMinSize(600, 600);
                        pane.setPrefSize(600, 600);
                        AccordionScene.this.getWindow().setWidth(800);
                        AccordionScene.this.getWindow().setHeight(600);
                    }
                }
            });


            vb.getChildren().addAll(new Label("Pane with tested Accordion"), pane, resetButton, compactStateToggle, nodeshb);

            hb.getChildren().addAll(vb, tabPane);
        }
    }
}
