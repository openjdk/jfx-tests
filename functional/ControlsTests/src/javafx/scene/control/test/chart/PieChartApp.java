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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class PieChartApp extends ChartBaseApp {
    public enum Pages {
        LabelLineLength, LabelsVisible, StartAngle, Clockwise, AddData, RemoveData
    }

    protected static final Double[] LENGTH_D = {0.0, 5.0, 30.0};
    protected static final String[] LENGTH_S = {"0", "5", "15"};
    protected static final Double[] START_ANGLE_D = {-10.0, 0.0, 10.0, 500.0};
    protected static final String[] START_ANGLE_S = {"0", "1", "3", "-1"};

    public PieChartApp() {
        super("PieChart", false); // "true" stands for "additionalActionButton = "
    }

    protected class AddDataNode extends TestNode {
        List<Data> data;
        public AddDataNode(List<Data> data) {
            this.data = data;
        }
        @Override
        public Node drawNode() {
            final PieChart chart = (PieChart)createObject();
            Platform.runLater(new Runnable() {
                public void run() {
                    int counter = 0;
                    chart.setAnimated(true);
                    for (Data item : data) {
                        if (counter > chart.getData().size()) {
                            chart.getData().add(item);
                        } else {
                            chart.getData().add(counter, item);
                        }
                        counter+= 2;
                    }
                }
            });
            return chart;
        }
    }
    protected class RemoveDataNode extends TestNode {
        public RemoveDataNode() {
        }
        @Override
        public Node drawNode() {
            final PieChart chart = (PieChart)createObject();
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

    @Override
    protected TestNode setup() {
        super.setup();

        setupPieChartCSSPages();

        setupDouble(Pages.LabelLineLength.name(), LENGTH_D);

        setupBoolean(Pages.LabelsVisible.name());

        setupDouble(Pages.StartAngle.name(), START_ANGLE_D);

        setupBoolean(Pages.Clockwise.name());

        ObservableList<Data> data = FXCollections.<Data>observableArrayList();
        data.add(new Data("New item", 500));
        TestNode add_data_node = new AddDataNode(data);
        pageSetup(Pages.AddData.name(), add_data_node);

        TestNode remove_data_node = new RemoveDataNode();
        pageSetup(Pages.RemoveData.name(), remove_data_node);

        return rootTestNode;
    }

    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT);
    }

    protected Object createObject(double width, double height) {
        ObservableList<Data> data = FXCollections.<Data>observableArrayList();
        for (int i = 0; i < 10; i++) {
            data.add(new Data("Data item " + i, i * 100));
        }
        PieChart chart = new PieChart(data);
        chart.setMaxSize(width, height);
        chart.setPrefSize(width, height);
        chart.setTitle("PieChart");
        chart.setStyle("-fx-border-color: darkgray;");
        return chart;
    }

    public enum PieChartCSS {
        CSSClockwise("-fx-clockwise", BOOL),
        CSSPieLabelVisible("-fx-pie-label-visible", BOOL),
        CSSLabelLineLength("-fx-label-line-length", LENGTH_S),
        CSSStartAngle("-fx-start-angle", START_ANGLE_S),
        ;
        public String[] style;
        public String[][] keys;

        PieChartCSS(String style, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
        }

        PieChartCSS(String style[], String[]... keys) {
            this.style = style;
            this.keys = keys;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupPieChartCSSPages() {
        if(isEmbedded()){
            for (PieChartCSS page : PieChartCSS.values()) {
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
            for (PieChartCSS page : PieChartCSS.values()) {
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
        Utils.launch(PieChartApp.class, args);
    }
}