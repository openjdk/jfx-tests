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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class LineChartApp extends XYChartBaseApp {

    public enum Pages {
        CreateSymbols
    }

    public LineChartApp() {
        super("LineChart", false);
    }

    @Override
    protected TestNode setup() {
        super.setup();

        setupLineChartCSSPages();

        setupBoolean(Pages.CreateSymbols.name());

        List<Series> data = new ArrayList<Series>();
        data.add(new Series("Additional Set 1", FXCollections.observableArrayList(
                    new XYChart.Data(1, 5),
                    new XYChart.Data(4, 4),
                    new XYChart.Data(9, 1)
                )));
        data.add(new Series("Additional Set 2", FXCollections.observableArrayList(
                    new XYChart.Data(0, 3),
                    new XYChart.Data(3, 1),
                    new XYChart.Data(8, 5)
                )));
        data.add(new Series("Additional Set 3", FXCollections.observableArrayList(
                    new XYChart.Data(2, 2),
                    new XYChart.Data(5, 7),
                    new XYChart.Data(9, 1)
                )));
        TestNode add_series_node = new ChangeSeriesNode(data, true);
        pageSetup(XYChartBaseApp.Pages.AddSeries.name(), add_series_node);

        List<XYChart.Data> series_data = new ArrayList<XYChart.Data>();
        series_data.add(new  XYChart.Data(2, 4));
        series_data.add(new  XYChart.Data(1, 5));
        series_data.add(new  XYChart.Data(3, 6));
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
        NumberAxis x_axis = new NumberAxis("Y-Axis", 0.0d, 10.0d, 2.0d);
        NumberAxis y_axis = new NumberAxis("Y-Axis", 0.0d, 10.0d, 2.0d);
        return createObject(x_axis, y_axis, width, height);
    }

    @Override
    protected Object createObject(Axis x_axis, Axis y_axis) {
        return createObject(x_axis, y_axis, SLOT_WIDTH, SLOT_HEIGHT);
    }

    protected Object createObject(Axis x_axis, Axis y_axis, double width, double height) {

        Series s1 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data(1, 4),
                    new XYChart.Data(4, 5),
                    new XYChart.Data(9, 6)
                ));
        s1.setName("Set 1");
        Series s2 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data(0, 4),
                    new XYChart.Data(3, 3),
                    new XYChart.Data(8, 2)
                ));
        s2.setName("Set 2");
        Series s3 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data(2, 4),
                    new XYChart.Data(5, 6),
                    new XYChart.Data(9, 8)
                ));
        s3.setName("Set 3");
        ObservableList data = FXCollections.observableArrayList(s1, s2, s3);
        LineChart chart = new LineChart(x_axis, y_axis, data);
        chart.setMaxSize(width, height);
        chart.setPrefSize(width, height);
        chart.setTitle("LineChart");
        chart.setStyle("-fx-border-color: darkgray;");
        return chart;
    }

    public enum LineChartCSS {
        CSSSymbolVisible("-fx-create-symbols", BOOL),
        ;
        public String[] style;
        public String[][] keys;

        LineChartCSS(String style, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
        }

        LineChartCSS(String[] style, String[]... keys) {
            this.style = style;
            this.keys = keys;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupLineChartCSSPages() {
        if(isEmbedded()){
            for (LineChartCSS page : LineChartCSS.values()) {
                int length = page.keys[page.keys.length - 1].length;
                for (int i = 0; i < length; i++) {
                    int size = page.keys.length;
                    String[] params = new String[size];
                    for (int j = 0; j < params.length - 1; j++) {
                        params[j] = page.keys[j][0];
                    }
                    params[size - 1] = page.keys[size - 1][i];
                    TestNode node = new CSSNode(page.style, params);
                    pageSetup(page.name()+getSuffix(i), node, page.keys[page.keys.length - 1][i]);
                }
            }
        }else{
            for (LineChartCSS page : LineChartCSS.values()) {
                int length = page.keys[page.keys.length - 1].length;
                TestNode nodes[] = new TestNode[length];
                for (int i = 0; i < length; i++) {
                    int size = page.keys.length;
                    String[] params = new String[size];
                    for (int j = 0; j < params.length - 1; j++) {
                        params[j] = page.keys[j][0];
                    }
                    params[size - 1] = page.keys[size - 1][i];
                    nodes[i] = new CSSNode(page.style, params);
                }
                pageSetup(page.name(),
                    nodes,
                    page.keys[page.keys.length - 1]);
            }
        }
    }

    public static void main(String[] args) {
        Utils.launch(LineChartApp.class, args);
    }
}
