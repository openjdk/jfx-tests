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

package javafx.scene.control.test.chart;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class ScatterChartApp extends XYChartBaseApp {

    public ScatterChartApp() {
        super("ScatterChart", false);
    }

    @Override
    protected TestNode setup() {
        super.setup();

        List<Series> data = new ArrayList<Series>();
        data.add(new Series("Additional Set 1", FXCollections.observableArrayList(
                    new XYChart.Data(2, 5),
                    new XYChart.Data(2, 7),
                    new XYChart.Data(3, 2)
                )));
        data.add(new Series("Additional Set 2", FXCollections.observableArrayList(
                    new XYChart.Data(2, 3),
                    new XYChart.Data(6, 7),
                    new XYChart.Data(5, 5)
                )));
        data.add(new Series("Additional Set 3", FXCollections.observableArrayList(
                    new XYChart.Data(3, 6),
                    new XYChart.Data(2, 2),
                    new XYChart.Data(1, 9)
                )));
        TestNode add_series_node = new ChangeSeriesNode(data, true);
        pageSetup(Pages.AddSeries.name(), add_series_node);

        List<XYChart.Data> series_data = new ArrayList<XYChart.Data>();
        series_data.add(new  XYChart.Data(5, 5));
        series_data.add(new  XYChart.Data(2, 4));
        series_data.add(new  XYChart.Data(8, 6));
        TestNode add_series_data_node = new ChangeSeriesDataNode(series_data, true);
        pageSetup(Pages.AddSeriesData.name(), add_series_data_node);

        TestNode change_series_data_node = new ChangeSeriesDataNode(series_data, false);
        pageSetup(Pages.ChangeSeriesData.name(), change_series_data_node);

        return rootTestNode;
    }

    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT);
    }

    @Override
    protected Object createObject(double width, double height) {
        NumberAxis x_axis = new NumberAxis("Y-Axis", 0.0d, 10.0d, 2.0d);
        NumberAxis y_axis = new NumberAxis("Y-Axis", 0.0d, 10.0d, 2.0d);
        return createObject(x_axis, y_axis, width, height);
    }

    @Override
    protected Object createObject(Axis x_axis, Axis y_axis) {
        return createObject(x_axis, y_axis, SLOT_WIDTH, SLOT_HEIGHT);
    }

    public static Object createObject(Axis x_axis, Axis y_axis, double width, double height) {

        Series s1 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data(3, 4),
                    new XYChart.Data(1, 5),
                    new XYChart.Data(4, 6)
                ));
        s1.setName("Set 1");
        Series s2 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data(1, 4),
                    new XYChart.Data(2, 3),
                    new XYChart.Data(4, 2)
                ));
        s2.setName("Set 2");
        Series s3 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data(5, 4),
                    new XYChart.Data(1, 6),
                    new XYChart.Data(3, 8)
                ));
        s3.setName("Set 3");
        ObservableList data = FXCollections.observableArrayList(s1, s2, s3);
        ScatterChart chart = new ScatterChart(x_axis, y_axis, data);
        chart.setMaxSize(width, height);
        chart.setPrefSize(width, height);
        chart.setTitle("ScatterChart");
        chart.setStyle("-fx-border-color: darkgray;");
        return chart;
    }

    public static void main(String[] args) {
        Utils.launch(ScatterChartApp.class, args);
    }
}
