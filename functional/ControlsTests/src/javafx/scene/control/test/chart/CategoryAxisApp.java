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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class CategoryAxisApp extends AxisBaseApp {
    public enum Pages {
        Constructors, EndMargin, GapStartAndEnd
    }

    protected static final Double[] END_MARGIN = {5.0, 10.0, 30.0};

    public CategoryAxisApp() {
        super("CategoryAxis", false); // "true" stands for "additionalActionButton = "
    }

    protected class Constructor1Node extends TestNode {
        public Constructor1Node() {
        }
        @Override
        public Node drawNode() {
            CategoryAxis axis = new CategoryAxis();
            axis.setCategories(FXCollections.<String>observableArrayList("First", "Second", "Third"));
            return axis;
        }
    }

    protected class Constructor2Node extends TestNode {
        public Constructor2Node() {
        }
        @Override
        public Node drawNode() {
            Node node = (Node)createObject();
            return node;
        }
    }

    @Override
    protected TestNode setup() {
        super.setup();

        setupConstructors(Pages.Constructors.name());

        setupDouble(Pages.EndMargin.name(), END_MARGIN);

        setupBoolean(Pages.GapStartAndEnd.name());

        setupCathegoryAxisCSSPages();

        return rootTestNode;
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupConstructors(String name){
        if(isEmbedded()){
            pageSetup(name+"First",
                    new AxisWrapNode(new Constructor1Node()),
                    "CategoryAxis()");
            pageSetup(name+"Second",
                    new AxisWrapNode(new Constructor2Node()),
                    "CategoryAxis(cathegories)");
        }else{
            pageSetup(Pages.Constructors.name(), new AxisWrapNode(new Constructor1Node()), new AxisWrapNode(new Constructor2Node()), "CategoryAxis()", "CategoryAxis(cathegories)");
        }
    }

    @Override
    protected Node createWrap(Axis x_axis, Axis y_axis, double width, double height) {
        Series s1 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data("First", "First"),
                    new XYChart.Data("Second", "Second"),
                    new XYChart.Data("Fourth", "Third")
                ));
        s1.setName("Set 1");
        Series s2 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data("First", "Third"),
                    new XYChart.Data("Second", "First"),
                    new XYChart.Data("Fourth", "Second")
                ));
        s2.setName("Set 2");
        Series s3 = new Series( FXCollections.observableArrayList(
                    new XYChart.Data("First", "Second"),
                    new XYChart.Data("Second", "Third"),
                    new XYChart.Data("Third", "First")
                ));
        s3.setName("Set 3");
        ObservableList data = FXCollections.observableArrayList(s1, s2, s3);
        Chart chart =  new ScatterChart(x_axis, y_axis, data);
        chart.setMaxSize(width, height);
        chart.setPrefSize(width, height);
        chart.setTitle("ScatterChart");
        chart.setStyle("-fx-border-color: darkgray;");
        return chart;
    }

    protected static final String[] MARGIN = {"0", "10", "30"};

    public enum CathegoryAxisCSS {
        CSSStartMargin("-fx-start-margin", MARGIN),
        CSSGapStartAndEnd("-fx-gap-start-and-end", BOOL),
        CSSEndMargin("-fx-end-margin", MARGIN),
        ;
        public String[] style;
        public String[][] keys;

        CathegoryAxisCSS(String style, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
        }

        CathegoryAxisCSS(String[] style, String[]... keys) {
            this.style = style;
            this.keys = keys;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupCathegoryAxisCSSPages() {
        if(isEmbedded()){
            for (CathegoryAxisCSS page : CathegoryAxisCSS.values()) {
                int length = page.keys[page.keys.length - 1].length;
                for (int i = 0; i < length; i++) {
                    int size = page.keys.length;
                    String[] params = new String[size];
                    for (int j = 0; j < params.length - 1; j++) {
                        params[j] = page.keys[j][0];
                    }
                    params[size - 1] = page.keys[size - 1][i];
                    TestNode node = new CSSNode(page.style, params);
                    pageSetup(page.name()+getSuffix(i), new AxisWrapNode(node), page.keys[page.keys.length - 1][i]);
                }
            }
        }else{
            for (CathegoryAxisCSS page : CathegoryAxisCSS.values()) {
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

    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT);
    }

    protected Object createObject(double width, double height) {
        Axis axis = new CategoryAxis(FXCollections.<String>observableArrayList("First", "Second", "Third"));
        return axis;
    }

    public static void main(String[] args) {
        Utils.launch(CategoryAxisApp.class, args);
    }
}