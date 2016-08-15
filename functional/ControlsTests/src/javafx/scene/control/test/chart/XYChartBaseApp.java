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

import java.util.List;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import test.javaclient.shared.TestNode;

public abstract class XYChartBaseApp extends ChartBaseApp {
    public enum Pages {
        AddSeries, AddSeriesData, ChangeSeriesData, RemoveSeries, RemoveSeriesData, Axis, HorizontalGridLinesVisible, AlternativeColumnFillVisible, AlternativeRowFillVisible, HorizontalZeroLineVisible,
        VerticalGridLinesVisible, VerticalZeroLineVisible, PlotDataOrder
    }

    protected XYChartBaseApp(String title, boolean showAdditionalActionButton) {
        super(title, showAdditionalActionButton);
    }

    protected class ChangeSeriesNode extends TestNode {
        List<Series> data;
        boolean add;
        public ChangeSeriesNode(List<Series> data, boolean add) {
            this.data = data;
            this.add = add;
        }
        @Override
        public Node drawNode() {
            final XYChart chart = (XYChart)createObject();
            Platform.runLater(new Runnable() {
                public void run() {
                    int counter = 0;
                    chart.setAnimated(true);
                    for (Series series : data) {
                        if (add) {
                            if (counter > chart.getData().size()) {
                                chart.getData().add(series);
                            } else {
                                chart.getData().add(counter, series);
                            }
                            counter+= 2;
                        } else {
                            if (counter++ < chart.getData().size()) {
                                chart.getData().set(counter, series);
                            }
                        }
                    }
                }
            });
            return chart;
        }
    }

    protected class ChangeSeriesDataNode extends TestNode {
        List<XYChart.Data> data;
        boolean add;
        public ChangeSeriesDataNode(List<XYChart.Data> data, boolean add) {
            this.data = data;
            this.add = add;
        }
        @Override
        public Node drawNode() {
            final XYChart chart = (XYChart)createObject();
            Platform.runLater(new Runnable() {
                public void run() {
                    chart.setAnimated(true);
                    int overall_counter = 0;
                    end:
                    for (Series series : (ObservableList<Series>)chart.getData()) {
                        int size = series.getData().size();
                        for (int i = 0; i < size/2; i++) {
                            if (overall_counter >= data.size()) {
                                break end;
                            }
                            if (add) {
                                series.getData().add(i * 2 + 1, data.get(overall_counter++));
                            } else {
                                series.getData().set(i * 2 + 1, data.get(overall_counter++));
                            }
                        }
                    }
                }
            });
            return chart;
        }
    }

    protected class RemoveSeriesNode extends TestNode {
        public RemoveSeriesNode() {
        }
        @Override
        public Node drawNode() {
            final XYChart chart = (XYChart)createObject();
            Platform.runLater(new Runnable() {
                public void run() {
                    int counter = 1;
                    while (counter < chart.getData().size()) {
                        chart.getData().remove(counter++);
                    }
                }
            });
            return chart;
        }
    }

    protected class RemoveSeriesDataNode extends TestNode {
        public RemoveSeriesDataNode() {
        }
        @Override
        public Node drawNode() {
            final XYChart chart = (XYChart)createObject();
            Platform.runLater(new Runnable() {
                public void run() {
                    for (Series series : (ObservableList<Series>)chart.getData()) {
                        int counter = 1;
                        while (counter < series.getData().size()) {
                            series.getData().remove(counter++);
                        }
                    }
                }
            });
            return chart;
        }
    }

    protected class AxisNode extends TestNode {
        protected ValueAxis xAxis;
        protected ValueAxis yAxis;

        public AxisNode(ValueAxis xAxis, ValueAxis yAxis) {
            this.xAxis = xAxis;
            this.yAxis = yAxis;
        }

        @Override
        public Node drawNode() {
            XYChart chart = (XYChart)createObject(xAxis, yAxis);

            if (chart.getXAxis() != xAxis) {
                reportGetterFailure("XYChart.getXAxis()");
            }
            if (chart.getYAxis() != yAxis) {
                reportGetterFailure("XYChart.getYAxis()");
            }
            return chart;
        }
    }

    protected class PlotDataOrder extends TestNode {

        @Override
        public Node drawNode() {
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            final LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(8, 45));
            series.getData().add(new XYChart.Data(9, 43));
            series.getData().add(new XYChart.Data(10, 17));
            series.getData().add(new XYChart.Data(11, 29));
            series.getData().add(new XYChart.Data(12, 25));
            series.getData().add(new XYChart.Data(1, 23));
            series.getData().add(new XYChart.Data(2, 14));
            series.getData().add(new XYChart.Data(3, 15));
            series.getData().add(new XYChart.Data(4, 24));
            series.getData().add(new XYChart.Data(5, 34));
            series.getData().add(new XYChart.Data(6, 36));
            series.getData().add(new XYChart.Data(7, 22));
            chart.getData().add(series);
            return chart;
        }
    }

    @Override
    protected TestNode setup() {
        super.setup();

        setupXYChartCSSPages();

        TestNode remove_series_node = new RemoveSeriesNode();
        pageSetup(Pages.RemoveSeries.name(), remove_series_node);

        TestNode remove_series_data_node = new RemoveSeriesDataNode();
        pageSetup(Pages.RemoveSeriesData.name(), remove_series_data_node);

        setupBoolean(Pages.HorizontalGridLinesVisible.name());

        setupBoolean(Pages.AlternativeColumnFillVisible.name());

        setupBoolean(Pages.AlternativeRowFillVisible.name());

        setupHorizontalZeroLineVisible(Pages.HorizontalZeroLineVisible.name());

        setupBoolean(Pages.VerticalGridLinesVisible.name());

        setupVerticalZeroLineVisible(Pages.VerticalZeroLineVisible.name());

        pageSetup(Pages.PlotDataOrder.name(),
                new TestNode[]{new PlotDataOrder()}, new String[]{Pages.PlotDataOrder.name()});
        return rootTestNode;
    }

        public abstract static class Changer {
        public abstract void change(Object obj);
    }

    public enum XYChartCSS {
        CSSVerticalGridLinesVisible("-fx-vertical-grid-lines-visible", null, BOOL),
        CSSHorizontalGridLinesVisible("-fx-horizontal-grid-lines-visible", null, BOOL),
        CSSAlternativeColumnFillVisible("-fx-alternative-column-fill-visible", null, BOOL),
        CSSAlternativeRowFillVisible("-fx-alternative-row-fill-visible", null, BOOL),
        CSSVerticalZeroLineVisible("-fx-vertical-zero-line-visible", new Changer() {
                @Override
                public void change(Object obj) {
                    ((XYChart)obj).getYAxis().setSide(Side.RIGHT);
                }
            }, BOOL),
        CSSHorizontalZeroLineVisible("-fx-horizontal-zero-line-visible", new Changer() {
                @Override
                public void change(Object obj) {
                    ((XYChart)obj).getXAxis().setSide(Side.TOP);
                }
            }, BOOL),
        ;
        public String[] style;
        public String[][] keys;
        public Changer changer;

        XYChartCSS(String style, Changer changer, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
            this.changer = changer;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupXYChartCSSPages() {
        if(isEmbedded()){
            for (final XYChartCSS page : XYChartCSS.values()) {
                int length = page.keys[page.keys.length - 1].length;
                for (int i = 0; i < length; i++) {
                    int size = page.keys.length;
                    String[] params = new String[size];
                    for (int j = 0; j < params.length - 1; j++) {
                        params[j] = page.keys[j][0];
                    }
                    params[size - 1] = page.keys[size - 1][i];
                    TestNode node = new CSSNode(page.style, params) {
                        @Override
                        public Object create() {
                            Object node = super.create();
                            if (page.changer != null) {
                                page.changer.change(node);
                            }
                            return node;
                        }
                    };
                    pageSetup(page.name()+getSuffix(i), node, page.keys[page.keys.length - 1][i]);
                }
            }
        }else{
            for (final XYChartCSS page : XYChartCSS.values()) {
                int length = page.keys[page.keys.length - 1].length;
                TestNode nodes[] = new TestNode[length];
                for (int i = 0; i < length; i++) {
                    int size = page.keys.length;
                    String[] params = new String[size];
                    for (int j = 0; j < params.length - 1; j++) {
                        params[j] = page.keys[j][0];
                    }
                    params[size - 1] = page.keys[size - 1][i];
                    nodes[i] = new CSSNode(page.style, params) {
                        @Override
                        public Object create() {
                            Object node = super.create();
                            if (page.changer != null) {
                                page.changer.change(node);
                            }
                            return node;
                        }
                    };
                }
                pageSetup(page.name(),
                    nodes,
                    page.keys[page.keys.length - 1]);
            }
        }
    }

    protected abstract Object createObject(Axis x_axis, Axis y_axis);
}
