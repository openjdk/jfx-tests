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

import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class NumberAxisApp extends ValueAxisApp {
    public enum Pages {
        Constructors, ForceZeroInRange, TickUnit, UpperBoundOverflow
    }

    protected static final double TOO_LARGE_VALUE = 73529411764706d;
    protected static final Double[] TICK_UNIT_D = {5.0, 10.0, 30.0};
    protected static final String[] TICK_UNIT_S = {"5", "10", "30"};
    protected static final int CONSTRUCTORS = 3;

    public NumberAxisApp() {
        super("NumberAxis", false); // "true" stands for "additionalActionButton = "
    }

    protected class Constructor1Node extends TestNode {
        public Constructor1Node() {
        }
        @Override
        public Node drawNode() {
            NumberAxis axis = new NumberAxis();
            return axis;
        }
    }

    protected class Constructor2Node extends TestNode {
        public Constructor2Node() {
        }
        @Override
        public Node drawNode() {
            NumberAxis axis = new NumberAxis(-50.0, 50.0, 10.0);
            return axis;
        }
    }

    protected class Constructor3Node extends TestNode {
        public Constructor3Node() {
        }
        @Override
        public Node drawNode() {
            NumberAxis axis = new NumberAxis("New Axis", -50.0, 50.0, 10.0);
            return axis;
        }
    }

    @Override
    protected TestNode setup() {
        super.setup();

        setupConstructors(Pages.Constructors.name());

        setupBoolean(Pages.ForceZeroInRange.name());

        setupDoubleNoAutoRanging(Pages.TickUnit.name(), TICK_UNIT_D);

        setupNumberAxisCSSPages();

        setupUpperBoundOverflow(Pages.UpperBoundOverflow.name(), TOO_LARGE_VALUE);

        return rootTestNode;
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupConstructors(String name) {
        if(isEmbedded()){
            pageSetup(Pages.Constructors.name()+"First",
                    new AxisWrapNode(new Constructor1Node()),
                    "NumberAxis()");
            pageSetup(Pages.Constructors.name()+"Second",
                    new AxisWrapNode(new Constructor2Node()),
                    "NumberAxis(min, max, tick)");
            pageSetup(Pages.Constructors.name()+"Third",
                    new AxisWrapNode(new Constructor3Node()),
                    "NumberAxis(mark, min, max, tick)");
        }else{
            pageSetup(Pages.Constructors.name(),
                    new AxisWrapNode(new Constructor1Node()),
                    new AxisWrapNode(new Constructor2Node()),
                    new AxisWrapNode(new Constructor3Node()),
                    "NumberAxis()",
                    "NumberAxis(min, max, tick)",
                    "NumberAxis(mark, min, max, tick)");
        }
    }

    public enum NumberAxisCSS {
        CSSTickUnit("-fx-tick-unit", TICK_UNIT_S),
        ;
        public String[] style;
        public String[][] keys;

        NumberAxisCSS(String style, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
        }

        NumberAxisCSS(String[] style, String[]... keys) {
            this.style = style;
            this.keys = keys;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupNumberAxisCSSPages() {
        if(isEmbedded()){
            for (NumberAxisCSS page : NumberAxisCSS.values()) {
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
                            Axis axis = (Axis)super.create();
                            axis.setAutoRanging(false);
                            return axis;
                        }
                    };
                    pageSetup(page.name()+getSuffix(i), new AxisWrapNode(node), page.keys[page.keys.length - 1][i]);
                }
            }
        }else{
            for (NumberAxisCSS page : NumberAxisCSS.values()) {
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
                            Axis axis = (Axis)super.create();
                            axis.setAutoRanging(false);
                            return axis;
                        }
                    };
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
        Axis axis = new NumberAxis();
        return axis;
    }

    public static void main(String[] args) {
        Utils.launch(NumberAxisApp.class, args);
    }

    private void setupUpperBoundOverflow(String name, double d) {
        pageSetup(Pages.UpperBoundOverflow.name(), new TestNode(name) {
            @Override
            public Node drawNode() {
                NumberAxis y = new NumberAxis();
                NumberAxis x = new NumberAxis();
                y.setUpperBound(d);
                x.setUpperBound(5);
                Chart chart = new ScatterChart(x, y);
                chart.maxWidthProperty().set(300);
                chart.maxHeightProperty().set(200);
                return chart;
            }
        });
    }
}
