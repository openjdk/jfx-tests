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

import com.sun.javafx.charts.Legend;
import com.sun.javafx.charts.Legend.LegendItem;
import java.util.ArrayList;
import javafx.geometry.Side;
import test.javaclient.shared.TestNode;

public abstract class ChartBaseApp extends BaseApp {

    public enum Pages {
        Animated, Legend, LegendSide, Title, TitleSide, LegendVisible
    }

    protected ChartBaseApp(String title, boolean showAdditionalActionButton) {
        super(800, 500, title, showAdditionalActionButton);
        SLOT_HEIGHT = 230;
    }

    @Override
    protected TestNode setup() {

        setupChartCSSPages();

        setupBoolean(Pages.Animated.name());

        setupObject(Pages.LegendSide.name(), Side.values());

        setupTitle(Pages.Title.name(), "New Title");

        setupObject(Pages.TitleSide.name(), Side.values());

        setupBoolean(Pages.LegendVisible.name());

        return rootTestNode;
    }

    protected abstract Object createObject();
    protected abstract Object createObject(double width, double height);

    protected class NamedLegend extends Legend {
        protected String name;
        public NamedLegend(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    protected ArrayList<NamedLegend> getLegends() {
        ArrayList<NamedLegend> legends  = new ArrayList<NamedLegend>();
        Boolean vertical[] = {false, true};
        int items_num[] = {0, 1, 5, 100};
        for (int i = 0; i < vertical.length; i++) {
            for (int j = 0; j < items_num.length; j++) {
                NamedLegend legend = new NamedLegend(vertical[i] ? "vert" : "false" + " " + items_num[j]);
                for (int k = 0; k < items_num[j]; k++) {
                    legend.getItems().add(new LegendItem("Item " + k));
                }
                legends.add(legend);
            }
        }
        return legends;
    }

    protected static final String[] SIDE = {"top", "bottom", "left", "right"};
    protected static final String[] BOOL = {"true", "false"};
    protected static final String[] WIDTH = {"0", "0.1", "1", "3"};
    protected static final String[] PAINT = {"red", "linear (0%,0%) to (100%,100%) stops (0.0,red) (1.0,black)"};
    protected static final String[] GAP = {"0", "5", "10"};
    protected static final String[] FONT = {"italic 20 serif", "italic 40 serif"};

    protected static final String CSS_RED_RECTANGLE = ChartBaseApp.class.getResource("red-rectangle.png").toExternalForm();
    protected static final String CSS_GREEN_RECTANGLE = ChartBaseApp.class.getResource("green-rectangle.png").toExternalForm();

    public enum ChartCSS {
        CSSTitleSide("-fx-title-side", SIDE),
        CSSLegendSide("-fx-legend-side", SIDE),
        CSSLegendVisible("-fx-legend-visible", BOOL),

//        CSSBackgroundColor("-fx-background-color", PAINT),
//        CSSBackgroundInsets(new String[] {"-fx-background-color", "-fx-background-insets"}, PAINT, new String[] {"0", "5", "10", "0 5 10 15"}),
//        CSSBackgroundRadius(new String[] {"-fx-background-color", "-fx-background-radius"}, PAINT, new String[] {"0", "5", "10", "0 5 10 15"}),
//        CSSBackgroundImage("-fx-background-image", "url(\""+CSS_RED_RECTANGLE+"\")"),
//        CSSBackgroundPosition(new String[] {"-fx-background-image",
//                                            "-fx-background-repeat",
//                                            "-fx-background-position"},
//                                            new String[] {"url(\""+CSS_RED_RECTANGLE+"\")"},
//                                            new String[] {"no-repeat"},
//                                            new String[] {"right bottom", "left top", "center center"}),
//        CSSBackgroundRepeat(new String[] {"-fx-background-image",
//                                            "-fx-background-repeat"},
//                                            new String[] {"url(\""+CSS_RED_RECTANGLE+"\")"},
//                                            new String[] {"no-repeat", "repeat", "space", "stretch"}),
        ;
        public String[] style;
        public String[][] keys;

        ChartCSS(String style, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
        }

        ChartCSS(String[] style, String[]... keys) {
            this.style = style;
            this.keys = keys;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupChartCSSPages() {
        if(isEmbedded()){
            for (ChartCSS page : ChartCSS.values()) {
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
            for (ChartCSS page : ChartCSS.values()) {
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
}
