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
import javafx.scene.chart.Chart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class StackedBarChartApp extends XYChartBaseApp {

    public enum Pages {
        CategoryGap
    }

    protected static final Double[] GAP_STACKED = {0.0, 5.0, 15.0};

    public StackedBarChartApp() {
        super("StackedBarChart", false);
    }

    @Override
    protected TestNode setup() {
        super.setup();

        setupDouble(Pages.CategoryGap.name(), GAP_STACKED);

        List<Series> data = new ArrayList<Series>();
        data.add(new Series("Additional Set 1", FXCollections.observableArrayList(
                    new XYChart.Data("First", 1),
                    new XYChart.Data("Second", 3),
                    new XYChart.Data("Fourth", 2)
                )));
        data.add(new Series("Additional Set 2", FXCollections.observableArrayList(
                    new XYChart.Data("First", 3),
                    new XYChart.Data("Second", 1),
                    new XYChart.Data("Fourth", 1)
                )));
        data.add(new Series("Additional Set 3", FXCollections.observableArrayList(
                    new XYChart.Data("First", 2),
                    new XYChart.Data("Second", 1),
                    new XYChart.Data("Fourth", 2)
                )));
        TestNode add_series_node = new ChangeSeriesNode(data, true);
        pageSetup(XYChartBaseApp.Pages.AddSeries.name(), add_series_node);

        List<XYChart.Data> series_data = new ArrayList<XYChart.Data>();
        series_data.add(new  XYChart.Data("Third", 1));
        series_data.add(new  XYChart.Data("Third", 3));
        series_data.add(new  XYChart.Data("Third", 1));
        TestNode add_series_data_node = new ChangeSeriesDataNode(series_data, true);
        pageSetup(XYChartBaseApp.Pages.AddSeriesData.name(), add_series_data_node);

        TestNode change_series_data_node = new ChangeSeriesDataNode(series_data, false);
        pageSetup(XYChartBaseApp.Pages.ChangeSeriesData.name(), change_series_data_node);

        return rootTestNode;
    }

    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT);
    }

    @Override
    protected Object createObject(double width, double height) {
        CategoryAxis x_axis = new CategoryAxis(FXCollections.<String>observableArrayList("First", "Second", "Third", "Fourth"));
        NumberAxis y_axis = new NumberAxis("Y-Axis", 0.0d, 16.0d, 2.0d);
        return createObject(x_axis, y_axis, width, height);
    }

    @Override
    protected Object createObject(Axis x_axis, Axis y_axis) {
        return createObject(x_axis, y_axis, SLOT_WIDTH, SLOT_HEIGHT);
    }

    public static Chart createObject(Axis x_axis, Axis y_axis, double width, double height) {

        Series s1 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data("First", 4),
                    new XYChart.Data("Second", 5),
                    new XYChart.Data("Fourth", 6)
                ));
        s1.setName("Set 1");
        Series s2 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data("First", 4),
                    new XYChart.Data("Second", 3),
                    new XYChart.Data("Fourth", 2)
                ));
        s2.setName("Set 2");
        Series s3 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data("First", 4),
                    new XYChart.Data("Second", 6),
                    new XYChart.Data("Fourth", 8)
                ));
        s3.setName("Set 3");
        ObservableList data = FXCollections.observableArrayList(s1, s2, s3);
        StackedBarChart chart = new StackedBarChart(x_axis, y_axis, data);
        chart.setMaxSize(width, height);
        chart.setPrefSize(width, height);
        chart.setTitle("StackedBarChart");
        chart.setStyle("-fx-border-color: darkgray;");
        return chart;
    }

    public static void main(String[] args) {
        Utils.launch(StackedBarChartApp.class, args);
    }
}
