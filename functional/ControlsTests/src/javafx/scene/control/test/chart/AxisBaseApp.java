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

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;

public abstract class AxisBaseApp extends BaseApp {

    public enum Pages {
        Animated, AutoRanging, Label, Side, TickLabelFill, TickLabelFont, TickLabelGap, TickLabelRotation, TickLength
    }

    protected static final Double[] GAP = {0.0, 5.0, 15.0};
    protected static final Double[] ROTATION = {0.0, 45.0, 180.0, 270.0};
    protected static final Double[] LENGTH_DOUBLE = {-15.0, 0.0, 5.0, 15.0};

    protected AxisBaseApp(String title, boolean showAdditionalActionButton) {
        super(800, 500, title, showAdditionalActionButton);
        SLOT_HEIGHT = 230;
    }

    protected class AxisWrapNode extends TestNode {
        protected TestNode x_axis;
        protected TestNode y_axis = null;

        public AxisWrapNode(TestNode x_axis) {
            this.x_axis = x_axis;
        }
        public AxisWrapNode(TestNode x_axis, TestNode y_axis) {
            this.x_axis = x_axis;
            this.y_axis = y_axis;
        }
        @Override
        public Node drawNode() {
            Node chart = createWrap((Axis)x_axis.drawNode(), (Axis) (y_axis == null ? x_axis : y_axis).drawNode(), SLOT_WIDTH, SLOT_HEIGHT);
            return chart;
        }
    }

    protected Node createWrap(Axis x_axis, Axis y_axis, double width, double height) {
        return (Node)ScatterChartApp.createObject(x_axis, y_axis, width, height);
    }

    @Override
    protected TestNode setup() {

        setupBoolean(Pages.Animated.name());

        setupBoolean(Pages.AutoRanging.name());

        setupLabel(Pages.Label.name(), "New Label");

        setupSide(Pages.Side.name());

        setupTickLabelFill(Pages.TickLabelFill.name(), new Color(0, 1, 0, 1));

        setupTickLabelFont(Pages.TickLabelFont.name(), new Font(20));

        setupDouble(Pages.TickLabelGap.name(), GAP);

        setupDoubleNoAutoRanging(Pages.TickLabelRotation.name(), ROTATION);

        setupDouble(Pages.TickLength.name(), LENGTH_DOUBLE);

        setupAxisCSSPages();

        return rootTestNode;
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupDoubleNoAutoRanging(String name, Double values[]) {
        if(isEmbedded()){
            for (int i = 0; i < values.length; i++) {
                TestNode node = new AxisWrapNode(new StandardDoubleSetterNode(name, values[i]) {
                    @Override
                    public Node create() {
                        Axis axis = (Axis)super.create();
                        axis.setAutoRanging(false);
                        return axis;
                    }
                });
                pageSetup(name+getSuffix(i), node, values[i]);
            }
        } else {
            TestNode nodes[] = new TestNode[values.length];
            for (int i = 0; i < values.length; i++) {
                nodes[i] = new AxisWrapNode(new StandardDoubleSetterNode(name, values[i]) {
                    @Override
                    public Node create() {
                        Axis axis = (Axis)super.create();
                        axis.setAutoRanging(false);
                        return axis;
                    }
                });
            }
            pageSetup(name, nodes, values);
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupSide(String page_name) {
        Side x_side[] = {Side.BOTTOM, Side.TOP};
        Side y_side[] = {Side.LEFT, Side.RIGHT};
        if(isEmbedded()){
            int k = 0;
            for (int i = 0; i < x_side.length; i++) {
                for (int j = 0; j < y_side.length; j++) {
                    String description = x_side[i] + " " + y_side[j];
                    TestNode node = new AxisWrapNode(new StandardObjectSetterNode(page_name, x_side[i]), new StandardObjectSetterNode(page_name, y_side[j]));
                    super.pageSetup(page_name+getSuffix(k), node, description);
                    k++;
                }
            }
        }else {
            TestNode nodes[] = new TestNode[x_side.length * y_side.length];
            String description[] = new String[nodes.length];
            int k = 0;
            for (int i = 0; i < x_side.length; i++) {
                for (int j = 0; j < y_side.length; j++) {
                    description[k] = x_side[i] + " " + y_side[j];
                    nodes[k++] = new AxisWrapNode(new StandardObjectSetterNode(Pages.Side.name(), x_side[i]), new StandardObjectSetterNode(Pages.Side.name(), y_side[j]));
                }
            }
            super.pageSetup(Pages.Side.name(), nodes, description);
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    @Override
    protected void setupBoolean(String name) {
        if(isEmbedded()){
            pageSetup(name+"First",
                    new AxisWrapNode(new StandardBooleanSetterNode(name, true)),
                    "true");
            pageSetup(name+"Second",
                    new AxisWrapNode(new StandardBooleanSetterNode(name, false)),
                    "false");
        }else{
            pageSetup(name,
                    new AxisWrapNode(new StandardBooleanSetterNode(name, true)),
                    new AxisWrapNode(new StandardBooleanSetterNode(name, false)),
                    "true",
                    "false");
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    @Override
    protected void setupDouble(String name, Double values[]) {
        if(isEmbedded()){
            for (int i = 0; i < values.length; i++) {
                TestNode node = new AxisWrapNode(new StandardDoubleSetterNode(name, values[i]));
                pageSetup(name+getSuffix(i), node, values[i]);
            }
        } else {
            TestNode nodes[] = new TestNode[values.length];
            for (int i = 0; i < values.length; i++) {
                nodes[i] = new AxisWrapNode(new StandardDoubleSetterNode(name, values[i]));
            }
            pageSetup(name, nodes, values);
        }
    }

    protected void setupLabel(String pageName, String node_name){
        pageSetup(pageName, new AxisWrapNode(new StandardStringSetterNode(pageName, node_name)));
    }

    protected void setupTickLabelFill(String pageName, Color color) {
        pageSetup(pageName, new AxisWrapNode(new StandardObjectSetterNode(pageName, color)));
    }

    protected void setupTickLabelFont(String pageName, Font font) {
        pageSetup(pageName, new AxisWrapNode(new StandardObjectSetterNode(pageName, font)));
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupInteger(String name, Integer values[]) {
        if(isEmbedded()){
            for (int i = 0; i < values.length; i++) {
                TestNode node = new AxisWrapNode(new StandardIntegerSetterNode(name, values[i]));
                pageSetup(name+getSuffix(i), node, values[i]);
            }
        } else {
            TestNode nodes[] = new TestNode[values.length];
            for (int i = 0; i < values.length; i++) {
                nodes[i] = new AxisWrapNode(new StandardIntegerSetterNode(name, values[i]));
            }
            pageSetup(name, nodes, values);
        }
    }

    @Override
    protected void pageSetup(String pageName, String node_name, TestNode node, int index) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        if(index == 0)
            page.add(new AxisWrapNode(createEmptyNode()), "Nothing");
        else if(index == 1)
            page.add(node, node_name);
        rootTestNode.add(page);
    }

    @Override
    protected void pageSetup(String pageName, String node_name, TestNode node) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        page.add(new AxisWrapNode(createEmptyNode()), "Nothing");
        page.add(node, node_name);
        rootTestNode.add(page);
    }

    @Override
    protected void pageSetup(String pageName, TestNode nodes[], String names[]) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int i = 0; i < nodes.length; i++) {
            page.add(new AxisWrapNode(nodes[i]), names[i]);
        }
        rootTestNode.add(page);
    }

    protected static final String[] SIDE = {"top", "bottom", "left", "right"};
    protected static final String[] BOOL = {"true", "false"};
    protected static final String[] PAINT = {"red", "linear (0%,0%) to (100%,100%) stops (0.0,red) (1.0,black)"};
    protected static final String[] LENGTH = {"0", "5", "10"};
    protected static final String[] FONT = {"italic 20 serif", "italic 40 serif"};

    public enum AxisCSS {
        CSSSide("-fx-side", SIDE),
        CSSTickLength("-fx-tick-length", LENGTH),
        CSSTickLabelFont("-fx-tick-label-font", FONT),
        CSSTickLabelFill("-fx-tick-label-fill", PAINT),
        CSSTickMarkVisible("-fx-tick-mark-visible", BOOL),
        CSSTickLabelsVisible("-fx-tick-labels-visible", BOOL),
        ;
        public String[] style;
        public String[][] keys;

        AxisCSS(String style, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
        }

        AxisCSS(String[] style, String[]... keys) {
            this.style = style;
            this.keys = keys;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupAxisCSSPages() {
        String x_side[] = {"BOTTOM", "TOP"};
        String y_side[] = {"LEFT", "RIGHT"};
        if(isEmbedded()){
            int k = 0;
            for (int i = 0; i < x_side.length; i++) {
                for (int j = 0; j < y_side.length; j++) {
                    String description = x_side[i] + " " + y_side[j];
                    TestNode side_node = new AxisWrapNode(new CSSNode(AxisCSS.CSSSide.style, new String[] {x_side[i]}),
                                                    new CSSNode(AxisCSS.CSSSide.style, new String[] {y_side[j]}));
                    pageSetup(AxisCSS.CSSSide.name()+getSuffix(k), side_node, description);
                    k++;
                }
            }

            for (AxisCSS page : AxisCSS.values()) {
                if (page == AxisCSS.CSSSide) {
                    continue;
                }
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
                    pageSetup(page.name()+getSuffix(i), new AxisWrapNode(nodes[i]), page.keys[page.keys.length - 1][i]);
                }
            }
        } else {
            TestNode side_nodes[] = new TestNode[x_side.length * y_side.length];
            String description[] = new String[side_nodes.length];
            int k = 0;
            for (int i = 0; i < x_side.length; i++) {
                for (int j = 0; j < y_side.length; j++) {
                    description[k] = x_side[i] + " " + y_side[j];
                    side_nodes[k++] = new AxisWrapNode(new CSSNode(AxisCSS.CSSSide.style, new String[] {x_side[i]}),
                                                    new CSSNode(AxisCSS.CSSSide.style, new String[] {y_side[j]}));
                }
            }
            super.pageSetup(AxisCSS.CSSSide.name(), side_nodes, description);

            for (AxisCSS page : AxisCSS.values()) {
                if (page == AxisCSS.CSSSide) {
                    continue;
                }
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

    protected abstract Object createObject();
    protected abstract Object createObject(double width, double height);
}
