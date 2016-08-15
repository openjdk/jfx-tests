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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import static javafx.scene.control.test.chart.apps.CommonFunctions.*;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class NewStackedAreaChartApp extends InteroperabilityApp implements ChartIDsInterface {

    public static void main(String[] args) {
        Utils.launch(NewStackedAreaChartApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "StackedAreaChartTestApp");
        return new NewStackedAreaChartApp.AreaChartScene();
    }

    class AreaChartScene extends CommonPropertiesScene {

        //AreaChart to be tested.
        StackedAreaChart testedStackedAreaChart;
        NumberAxis axis1;
        NumberAxis axis2;
        TabPaneWithControl pane;

        public AreaChartScene() {
            super("StackedAreaChartApp", 1300, 800);
        }

        @Override
        final protected void prepareScene() {
            Utils.addBrowser(this);

            axis1 = new NumberAxis(0, 100, 10);
            axis2 = new NumberAxis(0, 100, 10);
            testedStackedAreaChart = getNewChart();
            testedStackedAreaChart.setId(TESTED_CHART_ID);

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
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton);

            VBox vb = new VBox(5);
            vb.getChildren().addAll(resetButtonsHBox, getAddItemHBox(), getRemoveDataDialog());

            pane = getPaneFor(testedStackedAreaChart, CHART_TAB_NAME, axis1, AXIS_1_TAB_NAME, axis2, AXIS_2_TAB_NAME);

            setTestedControlContainerSize(500, 500);
            setTestedControl(testedStackedAreaChart);
            setPropertiesContent(pane);
            setControllersContent(vb);
        }

        public HBox getRemoveDataDialog() {
            HBox hb = new HBox();
            Label lb = new Label("From position");
            final TextField tf = TextFieldBuilder.create().text("0").prefWidth(50).id(REMOVE_AREA_INDEX_TEXTFIELD_ID).build();
            Button bt = ButtonBuilder.create().text("Remove!").id(REMOVE_AREA_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tf.getText());
                    testedStackedAreaChart.getData().remove(index);
                }
            });
            hb.getChildren().addAll(lb, tf, bt);
            return hb;
        }

        public HBox getAddItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("Add series named ");
            final TextField tf = TextFieldBuilder.create().prefWidth(50).id(ADDED_SERIES_NAME_TEXTFIELD_ID).build();

            Label minLabel = new Label(" min ");
            final TextField minText = TextFieldBuilder.create().prefWidth(50).id(ADDED_SERIES_MIN_VALUE_TEXTFIELD_ID).build();

            Label maxLabel = new Label(" max ");
            final TextField maxText = TextFieldBuilder.create().prefWidth(50).id(ADDED_SERIES_MAX_VALUE_TEXTFIELD_ID).build();

            Label amountLabel = new Label(" amount ");
            final TextField amountText = TextFieldBuilder.create().prefWidth(50).id(ADDED_SERIES_DOTS_COUNT_TEXTFIELD_ID).build();

            Button bt = ButtonBuilder.create().text("Add!").id(ADD_SERIES_COMMAND_BUTTON_ID).build();
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    String serieName = tf.getText();
                    double min = Double.parseDouble(minText.getText());
                    double max = Double.parseDouble(maxText.getText());
                    int amount = Integer.parseInt(amountText.getText());

                    ObservableList list = FXCollections.observableArrayList();

                    for (int i = 0; i < amount; i++) {
                        XYChart.Data newData = new XYChart.Data();
                        newData.setXValue(new Random().nextDouble() * (max - min) + min);
                        newData.setYValue(new Random().nextDouble() * (max - min) / 4 + min);
                        list.add(newData);
                    }
                    Object[] array = list.toArray();
                    Arrays.sort(array, new Comparator() {
                        public int compare(Object t, Object t1) {
                            return (int) Math.round(((Double) ((XYChart.Data) t).getXValue()) - ((Double) ((XYChart.Data) t1).getXValue()));
                        }
                    });

                    XYChart.Series serie = new XYChart.Series(serieName, FXCollections.observableArrayList(array));

                    testedStackedAreaChart.getData().add(serie);
                    pane.addPropertiesTable(serieName, getTableForProperty(serie, min, max).getVisualRepresentation());
                }
            });
            hb.getChildren().addAll(lb, tf, minLabel, minText, maxLabel, maxText, amountLabel, amountText, bt);
            return hb;
        }

        protected PropertiesTable getTableForProperty(XYChart.Series serie, double min, double max) {
            PropertiesTable table = new PropertiesTable(serie);

            table.addSimpleListener(serie.chartProperty(), serie);
            table.addSimpleListener(serie.nameProperty(), serie);
            table.addSimpleListener(serie.dataProperty(), serie);

            for (Iterator it = serie.getData().iterator(); it.hasNext();) {
                final XYChart.Data data = (XYChart.Data) it.next();

                final DoubleProperty intermediateX = new SimpleDoubleProperty(null, "XValue");
                final DoubleProperty intermediateY = new SimpleDoubleProperty(null, "YValue");

                data.XValueProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        intermediateX.setValue((Double) data.XValueProperty().getValue());
                    }
                });

                data.YValueProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        intermediateY.setValue((Double) data.YValueProperty().getValue());
                    }
                });

                intermediateX.addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        data.setXValue(t1);
                    }
                });

                intermediateY.addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        data.setYValue(t1);
                    }
                });

                table.addDoublePropertyLine(intermediateX, min, max, (Double) data.getXValue(), data);
                table.addDoublePropertyLine(intermediateY, min, max, (Double) data.getYValue(), data);
            }

            return table;
        }

        public StackedAreaChart getNewChart() {
            StackedAreaChart chart = new StackedAreaChart(axis1, axis2);
            chart.setTitle("StackedAreaChart");
            chart.setStyle("-fx-border-color: darkgray;");
            return chart;
        }
    }
}