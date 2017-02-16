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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
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
public class NewBarChartApp extends InteroperabilityApp implements ChartIDsInterface {

    public final static String REMOVE_ITEM_POS_TEXT_FIELD_ID = "REMOVE_ITEM_POS_TEXT_FIELD_ID";
    public final static String REMOVE_BUTTON_ID = "REMOVE_BUTTON_ID";
    public final static String ADD_ITEM_TEXT_FIELD_ID = "ADD_ITEM_TEXT_FIELD_ID";
    public final static String ADD_ITEM_POSITION_TEXT_FIELD_ID = "ADD_ITEM_POSITION_TEXT_FIELD_ID";
    public final static String ADD_ITEM_BUTTON_ID = "ADD_ITEM_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(NewBarChartApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "ScatterChartTestApp");
        return new NewBarChartApp.BarChartScene();
    }

    class BarChartScene extends CommonPropertiesScene {

        //BarChart to be tested.
        BarChart testedBarChart;
        NumberAxis axis2;
        ObservableList<String> existingCategories;
        CategoryAxis axis1;
        TabPaneWithControl pane;

        public BarChartScene() {
            super("BarChart", 1300, 800);
        }

        @Override
        final protected void prepareScene() {
            Utils.addBrowser(this);

            existingCategories = FXCollections.observableArrayList();
            existingCategories.addAll("category1", "category2", "category3");
            axis1 = new CategoryAxis(existingCategories);
            axis2 = new NumberAxis(0, 100, 10);
            testedBarChart = getNewChart();
            testedBarChart.setId(TESTED_CHART_ID);

            Button hardResetButton = new Button("Hard reset");
            hardResetButton.setId(HARD_RESET_BUTTON_ID);
            hardResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button softResetButton = new Button("Soft reset");
            softResetButton.setId(SOFT_RESET_BUTTON_ID);
            softResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    //throw new UnsupportedOperationException("Not supported yet.");
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton);

            VBox vb = new VBox(5);
            vb.getChildren().addAll(resetButtonsHBox, getAddItemHBox(), getRemoveDataDialog(), getAddCategoryDialog(), getRemoveCategoryDialog());

            pane = getPaneFor(testedBarChart, CHART_TAB_NAME, axis1, AXIS_1_TAB_NAME, axis2, AXIS_2_TAB_NAME);

            setTestedControlContainerSize(500, 500);
            setTestedControl(testedBarChart);
            setPropertiesContent(pane);
            setControllersContent(vb);
        }

        public HBox getRemoveDataDialog() {
            HBox hb = new HBox();
            Label lb = new Label("From position");
            final TextField tf = new TextField("0");
            tf.setPrefWidth(50);
            tf.setId(REMOVE_ITEM_POS_TEXT_FIELD_ID);
            Button bt = new Button("Remove!");
            bt.setId(REMOVE_BUTTON_ID);
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tf.getText());
                    testedBarChart.getData().remove(index);
                }
            });
            hb.getChildren().addAll(lb, tf, bt);
            return hb;
        }

        public HBox getRemoveCategoryDialog() {
            HBox hb = new HBox();
            Label lb = new Label("From index");
            final TextField tf = new TextField("0");
            tf.setPrefWidth(50);
            Button bt = new Button("Remove category!");
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tf.getText());
                    existingCategories.remove(index);
                    axis1.getCategories().remove(index);
                }
            });
            hb.getChildren().addAll(lb, tf, bt);
            return hb;
        }

        public HBox getAddCategoryDialog() {
            HBox hb = new HBox();
            Label lb = new Label("Category");
            final TextField tf = new TextField("");
            tf.setPrefWidth(50);

            Label lind = new Label("to index");
            final TextField tfind = new TextField("0");
            tfind.setPrefWidth(50);

            Button bt = new Button("Add!");
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tfind.getText());
                    existingCategories.add(index, tf.getText());
                    axis1.getCategories().add(index, tf.getText());
                }
            });
            hb.getChildren().addAll(lb, tf, lind, tfind, bt);
            return hb;
        }

        public HBox getAddItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("Add series named ");
            final TextField tf = new TextField();
            tf.setPrefWidth(50);
            tf.setId(ADDED_SERIES_NAME_TEXTFIELD_ID);

            Label minLabel = new Label(" min ");
            final TextField minText = new TextField();
            minText.setPrefWidth(50);
            minText.setId(ADDED_SERIES_MIN_VALUE_TEXTFIELD_ID);

            Label maxLabel = new Label(" max ");
            final TextField maxText = new TextField();
            maxText.setPrefWidth(50);
            maxText.setId(ADDED_SERIES_MAX_VALUE_TEXTFIELD_ID);

            Label amountLabel = new Label(" amount ");
            final TextField amountText = new TextField();
            amountText.setPrefWidth(50);
            amountText.setId(ADDED_SERIES_DOTS_COUNT_TEXTFIELD_ID);

            Button bt = new Button("Add!");
            bt.setId(ADD_SERIES_COMMAND_BUTTON_ID);
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    String serieName = tf.getText();
                    double min = Double.parseDouble(minText.getText());
                    double max = Double.parseDouble(maxText.getText());
                    int amount = Integer.parseInt(amountText.getText());

                    ObservableList list = FXCollections.observableArrayList();

                    XYChart.Series serie = new XYChart.Series(serieName, list);

                    for (int i = 0; i < amount; i++) {
                        XYChart.Data newData = new XYChart.Data();
                        String category = existingCategories.get(new Random().nextInt(existingCategories.size()));
                        Double value = new Random().nextDouble() * (max - min) + min;
                        newData.setYValue(category);
                        newData.setXValue(value);
                        list.add(newData);
                    }

                    testedBarChart.getData().add(serie);
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

                table.addDoublePropertyLine(intermediateY, min, max, (Double) data.getXValue(), data);
            }

            return table;
        }

        public BarChart getNewChart() {
            BarChart chart = new BarChart(axis2, axis1);
            chart.setTitle("BarChart");
            chart.setStyle("-fx-border-color: darkgray;");
            return chart;
        }
    }
}