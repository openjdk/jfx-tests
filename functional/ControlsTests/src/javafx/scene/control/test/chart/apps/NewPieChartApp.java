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
package javafx.scene.control.test.chart.apps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class NewPieChartApp extends InteroperabilityApp implements ChartIDsInterface {

    public final static String REMOVE_ITEM_POS_TEXT_FIELD_ID = "REMOVE_ITEM_POS_TEXT_FIELD_ID";
    public final static String REMOVE_BUTTON_ID = "REMOVE_BUTTON_ID";
    public final static String ADD_ITEM_VALUE_TEXT_FIELD_ID = "ADD_ITEM_VALUE_TEXT_FIELD_ID";
    public final static String ADD_ITEM_TEXT_FIELD_ID = "ADD_ITEM_TEXT_FIELD_ID";
    public final static String ADD_ITEM_POSITION_TEXT_FIELD_ID = "ADD_ITEM_POSITION_TEXT_FIELD_ID";
    public final static String ADD_ITEM_BUTTON_ID = "ADD_ITEM_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(NewPieChartApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "PieChartTestApp");
        return new PieChartScene();
    }

    class PieChartScene extends CommonPropertiesScene {

        //VBox which contain tested PieChart.
        Pane pane;
        PropertiesTable tb;
        //PieChart to be tested.
        PieChart testedPieChart;
        double controlContainerWidth = 600;
        double controlContainerHeight = 600;
        ObservableList<Data> data;

        public PieChartScene() {
            super("PieChart", 1300, 800);
        }

        @Override
        final protected void prepareScene() {
            Utils.addBrowser(this);
            pane = new Pane();
            testedPieChart = getNewChart();
            testedPieChart.setId(TESTED_CHART_ID);

            tb = new PropertiesTable(testedPieChart);
            PropertyTablesFactory.explorePropertiesList(testedPieChart, tb);
            SpecialTablePropertiesProvider.provideForControl(testedPieChart, tb);

            pane.setMinSize(controlContainerWidth, controlContainerHeight);
            pane.setPrefSize(controlContainerWidth, controlContainerHeight);
            pane.setStyle("-fx-border-color : red;");
            pane.getChildren().add(testedPieChart);

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

            Button hardResetButton = ButtonBuilder.create().id(HARD_RESET_BUTTON_ID).text("Hard reset").build();
            hardResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button softResetButton = ButtonBuilder.create().id(SOFT_RESET_BUTTON_ID).text("Soft reset").build();
            softResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });

            Button b = new Button("Add");
            b.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    for (Data data : testedPieChart.getData()) {
                        Label label = new Label("Label");
                        label.setLabelFor(data.getNode());
                    }
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton, b);
            vb.getChildren().addAll(resetButtonsHBox, getAddItemHBox(), getRemoveDataDialog());

            ScrollPane sp = new ScrollPane();
            sp.setContent(tb);
            sp.setPannable(true);
            sp.setMinWidth(1000);
            sp.setMinHeight(800);

            setTestedControl(testedPieChart);
            setPropertiesContent(sp);
            setTestedControlContainerSize(500, 500);
            setControllersContent(vb);
        }

        public HBox getRemoveDataDialog() {
            HBox hb = new HBox();
            Label lb = new Label("From position");
            final TextField tf = TextFieldBuilder.create().text("0").prefWidth(50).id(REMOVE_ITEM_POS_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Remove!").id(REMOVE_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tf.getText());
                    ((PieChart) testedPieChart).getData().remove(index);
                }
            });
            hb.getChildren().addAll(lb, tf, bt);
            return hb;
        }

        public HBox getAddItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("Add item");
            final TextField tf = TextFieldBuilder.create().prefWidth(50).id(ADD_ITEM_VALUE_TEXT_FIELD_ID).build();
            Label namedLabel = new Label(" named ");
            final TextField name = TextFieldBuilder.create().prefWidth(50).id(ADD_ITEM_TEXT_FIELD_ID).build();
            Label atLb = new Label("at pos");
            final TextField tfPos = TextFieldBuilder.create().prefWidth(50).id(ADD_ITEM_POSITION_TEXT_FIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Add!").id(ADD_ITEM_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tfPos.getText());

                    Data newData = new Data("".equals(name.getText()) ? String.valueOf(index) : name.getText(), Double.parseDouble(tf.getText()));

                    ((PieChart) testedPieChart).getData().add(index, newData);

                    tb.addDoublePropertyLine(newData.pieValueProperty(), -10, 10000, 100, newData);
                }
            });
            hb.getChildren().addAll(lb, tf, namedLabel, name, atLb, tfPos, bt);
            return hb;
        }

        public PieChart getNewChart() {
            data = FXCollections.<Data>observableArrayList();
            //Don't change numbers here.
            for (int i = 0; i < 4; i++) {
                data.add(new Data("Data item " + i, 100));
            }
            PieChart chart = new PieChart(data);
            chart.setTitle("PieChart");
            chart.setStyle("-fx-border-color: darkgray;");
            return chart;
        }
    }
}