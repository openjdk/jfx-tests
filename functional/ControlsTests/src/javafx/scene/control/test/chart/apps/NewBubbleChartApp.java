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
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
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
public class NewBubbleChartApp extends InteroperabilityApp implements ChartIDsInterface {

    public final static String REMOVE_ITEM_POS_TEXT_FIELD_ID = "REMOVE_ITEM_POS_TEXT_FIELD_ID";
    public final static String REMOVE_BUTTON_ID = "REMOVE_BUTTON_ID";
    public final static String ADD_ITEM_TEXT_FIELD_ID = "ADD_ITEM_TEXT_FIELD_ID";
    public final static String ADD_ITEM_POSITION_TEXT_FIELD_ID = "ADD_ITEM_POSITION_TEXT_FIELD_ID";
    public final static String ADD_ITEM_BUTTON_ID = "ADD_ITEM_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(NewBubbleChartApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "BubbleChartTestApp");
        return new NewBubbleChartApp.BubbleChartScene();
    }

    class BubbleChartScene extends CommonPropertiesScene {

        //BubbleChart to be tested.
        BubbleChart testedBubbleChart;
        NumberAxis axis1;
        NumberAxis axis2;
        TabPaneWithControl pane;

        public BubbleChartScene() {
            super("BubbleChart", 1300, 800);
        }

        @Override
        final protected void prepareScene() {
            Utils.addBrowser(this);

            axis1 = new NumberAxis(0, 100, 10);
            axis2 = new NumberAxis(0, 100, 10);
            testedBubbleChart = getNewChart();
            testedBubbleChart.setId(TESTED_CHART_ID);

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
            vb.getChildren().addAll(resetButtonsHBox, getAddItemHBox(), getAddBubbleToSerieDialog(), getRemoveDataDialog(), getRemoveDataFromSerieDialog());

            pane = getPaneFor(testedBubbleChart, CHART_TAB_NAME, axis1, AXIS_1_TAB_NAME, axis2, AXIS_2_TAB_NAME);

            setTestedControlContainerSize(500, 500);
            setTestedControl(testedBubbleChart);
            setPropertiesContent(pane);
            setControllersContent(vb);
        }

        public HBox getRemoveDataDialog() {
            HBox hb = new HBox();
            Label lb = new Label("From position");
            final TextField tf = new TextField("0");
            tf.setPrefWidth(50);
            tf.setId(REMOVE_ITEM_POS_TEXT_FIELD_ID);
            Button bt = new Button("Remove serie!");
            bt.setId(REMOVE_BUTTON_ID);
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int index = Integer.parseInt(tf.getText());
                    testedBubbleChart.getData().remove(index);
                }
            });
            hb.getChildren().addAll(lb, tf, bt);
            return hb;
        }

        public HBox getRemoveDataFromSerieDialog() {
            HBox hb = new HBox();
            Label lb1 = new Label("From serie");
            final TextField tf1 = new TextField("0");
            tf1.setPrefWidth(50);

            Label lb2 = new Label("from index");
            final TextField tf2 = new TextField("0");
            tf2.setPrefWidth(50);

            Button bt = new Button("remove bubble!");
            bt.setId(REMOVE_BUTTON_ID);
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int serie = Integer.parseInt(tf1.getText());
                    int index = Integer.parseInt(tf2.getText());
                    ((Series) testedBubbleChart.getData().get(serie)).getData().remove(index);
                }
            });
            hb.getChildren().addAll(lb1, tf1, lb2, tf2, bt);
            return hb;
        }

        public HBox getAddBubbleToSerieDialog() {
            HBox hb = new HBox();
            Label lb1 = new Label("To serie");
            final TextField tf1 = new TextField("0");
            tf1.setPrefWidth(50);

            Label lb2 = new Label("to index");
            final TextField tf2 = new TextField("0");
            tf2.setPrefWidth(50);

            Label lb3 = new Label("X");
            final TextField tf3 = new TextField("0");
            tf3.setPrefWidth(50);

            Label lb4 = new Label("Y");
            final TextField tf4 = new TextField("0");
            tf4.setPrefWidth(50);

            Label lb5 = new Label("R");
            final TextField tf5 = new TextField("0");
            tf5.setPrefWidth(50);

            Button bt = new Button("add bubble!");
            bt.setId(REMOVE_BUTTON_ID);
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    int serie = Integer.parseInt(tf1.getText());
                    int index = Integer.parseInt(tf2.getText());
                    double X = Double.parseDouble(tf3.getText());
                    double Y = Double.parseDouble(tf4.getText());
                    double R = Double.parseDouble(tf5.getText());

                    XYChart.Data newData = new XYChart.Data();
                    newData.setXValue(X);
                    newData.setYValue(Y);
                    newData.setExtraValue(R);

                    ((Series) testedBubbleChart.getData().get(serie)).getData().add(index, newData);
                }
            });
            hb.getChildren().addAll(lb1, tf1, lb2, tf2, lb3, tf3, lb4, tf4, lb5, tf5, bt);
            return hb;
        }

        public HBox getAddItemHBox() {
            HBox hb = new HBox();
            Label lb = new Label("Add series named ");
            final TextField tf = new TextField();
            tf.setPrefWidth(50);
            tf.setId(ADDED_SERIES_NAME_TEXTFIELD_ID);

            Label minLabel = new Label(" min X,Y ");
            final TextField minText = new TextField();
            minText.setPrefWidth(50);
            minText.setId(ADDED_SERIES_MIN_VALUE_TEXTFIELD_ID);

            Label maxLabel = new Label(" max X,Y ");
            final TextField maxText = new TextField();
            maxText.setPrefWidth(50);
            maxText.setId(ADDED_SERIES_MAX_VALUE_TEXTFIELD_ID);

            Label amountLabel = new Label(" add ");
            final TextField amountText = new TextField();
            amountText.setPrefWidth(50);
            amountText.setId(ADDED_SERIES_DOTS_COUNT_TEXTFIELD_ID);

            Button bt = new Button(" bubbles!");
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
                        newData.setXValue(new Random().nextDouble() * (max - min) + min);
                        newData.setYValue(new Random().nextDouble() * (max - min) + min);
                        newData.setExtraValue(new Random().nextDouble() * (max - min) / 8 + min);
                        list.add(newData);
                    }

                    testedBubbleChart.getData().add(serie);
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
                final DoubleProperty intermediateExtra = new SimpleDoubleProperty(null, "ExtraValue");

                data.XValueProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        if (!intermediateX.isBound()) {
                            intermediateX.setValue((Double) data.XValueProperty().getValue());
                        }
                    }
                });

                data.YValueProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        if (!intermediateY.isBound()) {
                            intermediateY.setValue((Double) data.YValueProperty().getValue());
                        }
                    }
                });

                data.extraValueProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        if (!intermediateExtra.isBound()) {
                            intermediateExtra.setValue((Double) data.extraValueProperty().getValue());
                        }
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

                intermediateExtra.addListener(new ChangeListener() {
                    public void changed(ObservableValue ov, Object t, Object t1) {
                        data.setExtraValue(t1);
                    }
                });

                table.addDoublePropertyLine(intermediateX, min, max, (Double) data.getXValue(), data);
                table.addDoublePropertyLine(intermediateY, min, max, (Double) data.getYValue(), data);
                table.addDoublePropertyLine(intermediateExtra, min, max, (Double) data.getYValue(), data);
            }

            return table;
        }

        public BubbleChart getNewChart() {
            BubbleChart chart = new BubbleChart(axis1, axis2);
            chart.setTitle("BubbleChart");
            chart.setStyle("-fx-border-color: darkgray;");
            return chart;
        }
    }
}