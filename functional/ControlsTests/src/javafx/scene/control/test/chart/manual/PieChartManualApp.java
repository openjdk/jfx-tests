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
package javafx.scene.control.test.chart.manual;

import javafx.scene.control.test.utils.ptables.WeakPropertyValueController;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.PieChart;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.geometry.Side;
import javafx.scene.layout.HBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import javafx.scene.layout.VBox;
import javafx.geometry.Orientation;

/**
 * @author Alexander Kirov
 */
public class PieChartManualApp extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(PieChartManualApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "PieChartTestApp");
        return new PieChartScene();
    }

    class PieChartScene extends CommonPropertiesScene {

        //PieChart to be tested.
        PieChart testedPieChart;
        int controlContainerWidth = 400;
        int controlContainerHeight = 400;
        ObservableList<PieChart.Data> data;
        VBox properties;

        public PieChartScene() {
            super("PieChart", 900, 630);

            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            testedPieChart = getNewChart();
            testedPieChart.setPrefSize(controlContainerHeight, controlContainerWidth);
            properties = new VBox(10);

            Button addDataButton = new Button("Add data at index 0");
            addDataButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    testedPieChart.getData().add(0, new PieChart.Data("Added data", Math.random() * 100));
                }
            });

            Button removeDataButton = new Button("Remove data from index 0");
            removeDataButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    if (testedPieChart.getData().size() > 0) {
                        testedPieChart.getData().remove(0);
                    }
                }
            });

            Button changeDataButton = new Button("Change data at index 0");
            changeDataButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    if (testedPieChart.getData().size() > 0) {
                        PieChart.Data data = testedPieChart.getData().get(0);
                        data.setPieValue(Math.max(0, data.getPieValue() + (Math.random() - 0.5) * 200));
                    }
                }
            });

            Button hardResetButton = ButtonBuilder.create().text("Reset").build();
            hardResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            VBox vb = new VBox(5);
            vb.getChildren().addAll(hardResetButton,
                    new Separator(Orientation.HORIZONTAL), new Label("Add new data : "), addDataButton,
                    new Separator(Orientation.HORIZONTAL), new Label("Remove existing data : "), removeDataButton,
                    new Separator(Orientation.HORIZONTAL), new Label("Change existing data : "), changeDataButton);

            addController(new WeakPropertyValueController(testedPieChart.animatedProperty(), testedPieChart));
            addController(new WeakPropertyValueController(testedPieChart.clockwiseProperty(), testedPieChart));
            addController(new WeakPropertyValueController(testedPieChart.legendVisibleProperty(), testedPieChart));
            addController(new WeakPropertyValueController(testedPieChart.startAngleProperty(), testedPieChart, -300.0, 0.0, 300.0));
            addController(new WeakPropertyValueController(testedPieChart.labelLineLengthProperty(), testedPieChart, -20.0, 10.0, 30.0));
            addController(new WeakPropertyValueController(testedPieChart.labelsVisibleProperty(), testedPieChart));
            addController(new WeakPropertyValueController(testedPieChart.titleProperty(), testedPieChart, "New title"));
            addController(new WeakPropertyValueController(testedPieChart.titleSideProperty(), testedPieChart, Arrays.asList(Side.values())));
            addController(new WeakPropertyValueController(testedPieChart.legendSideProperty(), testedPieChart, Arrays.asList(Side.values())));
            addController(new WeakPropertyValueController(testedPieChart.prefWidthProperty(), testedPieChart, 100.0, 400.0, 400.0));
            addController(new WeakPropertyValueController(testedPieChart.prefHeightProperty(), testedPieChart, 100.0, 400.0, 400.0));

            setTestedControlContainerSize(400, 400);
            setTestedControl(testedPieChart);
            setControllersContent(vb);
            setPropertiesContent(properties);
        }

        private void addController(WeakPropertyValueController controller) {
            properties.getChildren().addAll(new Separator(Orientation.HORIZONTAL), controller);
        }

        public PieChart getNewChart() {
            data = FXCollections.<PieChart.Data>observableArrayList();
            for (int i = 0; i < 4; i++) {
                data.add(new PieChart.Data("Data item " + i, 50));
            }
            PieChart chart = new PieChart(data);
            chart.setTitle("PieChart");
            chart.setStyle("-fx-border-color: darkgray;");
            return chart;
        }
    }
}