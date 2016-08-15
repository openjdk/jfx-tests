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
import javafx.scene.chart.ValueAxis;
import javafx.util.StringConverter;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public abstract class ValueAxisApp extends AxisBaseApp {
    public enum Pages {
        LowerBound, MinorTickCount, MinorTickLength, TickLabelFormatter, UpperBound/*, ZeroPosition*/
    }

    protected static final Double[] BOUNDS = {-5.0, 0.0, 5.533};
    protected static final Integer[] MINOR_TICK_COUNT = {-5, 0, 5, 10};
    protected static final Double[] MINOR_TICK_LENGTH = {-5.0, 0.0, 5.0, 15.0};

    protected ValueAxisApp(String title, boolean showAdditionalActionButton) {
        super(title, showAdditionalActionButton);
    }

    @Override
    protected TestNode setup() {
        super.setup();

        setupDoubleNoAutoRanging(Pages.LowerBound.name(), BOUNDS);

        setupInteger(Pages.MinorTickCount.name(), MINOR_TICK_COUNT);

        setupDouble(Pages.MinorTickLength.name(), MINOR_TICK_LENGTH);

        StringConverter converter = new StringConverter() {
            @Override
            public String toString(Object t) {
                return String.valueOf(((Double)t).intValue());
            }
            @Override
            public Object fromString(String string) {
                return Integer.valueOf(string);
            }
        };
        pageSetup(Pages.TickLabelFormatter.name(), new AxisWrapNode(new StandardObjectSetterNode(Pages.TickLabelFormatter.name(), converter)));

        setupDoubleNoAutoRanging(Pages.UpperBound.name(), BOUNDS);

        /*Double zero_position[] = {-15.0, 0.0, 0.225, 15.0};
        setupDoubleNoAutoRanging(Pages.ZeroPosition.name(), zero_position);*/

        setupValueAxisCSSPages();

        return rootTestNode;
    }

    public enum ValueAxisCSS {
        CSSMinorTickLength("-fx-minor-tick-length", LENGTH),
        CSSMinorTickCount("-fx-minor-tick-count", LENGTH),
        CSSMinorTickVisible("-fx-minor-tick-visible", BOOL),
        ;
        public String[] style;
        public String[][] keys;

        ValueAxisCSS(String style, String... keys) {
            this.style = new String[] {style};
            this.keys = new String[][] {keys};
        }

        ValueAxisCSS(String[] style, String[]... keys) {
            this.style = style;
            this.keys = keys;
        }
    }

    /**
     * For embedded purpose, we have a ramification "if(isEmbedded())".
     * We need it, because if it is an embedded mode we have not one page, for all controls variations, but several
     * pages, one for each of the variations, in the application, PageNameFirst(Second, Third ... see TestUtil SUFFIXES String array).
     */
    protected void setupValueAxisCSSPages() {
        if(isEmbedded()){
            for (ValueAxisCSS page : ValueAxisCSS.values()) {
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
            for (ValueAxisCSS page : ValueAxisCSS.values()) {
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