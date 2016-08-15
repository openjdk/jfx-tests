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
package javafx.scene.control.test.nchart;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import static javafx.scene.control.test.chart.apps.ChartIDsInterface.*;
import static javafx.scene.control.test.nchart.Utils.*;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.layout.StackPane;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public abstract class AreaStackedAreaChartsCommon extends XYChartsBase {

    public static final String CHART_AREA_SYMBOL_STYLECLASS = "chart-area-symbol";

    @Test(timeout = 60000)
    public void createSymbolsCSSTest() {
        populateChart();

        checkTextFieldText(AreaTestsProperties.createSymbols, "true");
        checkDotsCount(30);

        applyStyle(testedControl, "-fx-create-symbols", "false");
        checkTextFieldText(AreaTestsProperties.createSymbols, "false");
        checkDotsCount(0);

        applyStyle(testedControl, "-fx-create-symbols", "true");
        checkTextFieldText(AreaTestsProperties.createSymbols, "true");
        checkDotsCount(30);
    }

    @Test(timeout = 60000)
    public void createSymbolsPropertyTest() {
        assertTrue(new AreaChart(new NumberAxis(), new NumberAxis()).getCreateSymbols());
        assertTrue(new StackedAreaChart(new NumberAxis(), new NumberAxis()).getCreateSymbols());
        populateChart();

        checkTextFieldText(AreaTestsProperties.createSymbols, "true");
        checkDotsCount(30);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, AreaTestsProperties.createSymbols, false);
        checkTextFieldText(AreaTestsProperties.createSymbols, "false");
        checkDotsCount(0);

        setPropertyByToggleClick(SettingType.SETTER, AreaTestsProperties.createSymbols, true);
        checkTextFieldText(AreaTestsProperties.createSymbols, "true");
        checkDotsCount(30);

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, AreaTestsProperties.createSymbols, false);
        checkTextFieldText(AreaTestsProperties.createSymbols, "false");
        checkDotsCount(0);

        removeFromIndex(2, 1, 0);
        checkDotsCount(0);

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, AreaTestsProperties.createSymbols, true);

        addSeries("Series C", 0, 100, 30);
        checkDotsCount(30);

        removeFromIndex(0);
        checkDotsCount(0);

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, AreaTestsProperties.createSymbols, false);

        checkDotsCount(0);
        addSeries("Series C", 0, 100, 30);

        checkDotsCount(0);
    }

    /**
     * @param minValue minX and minY.
     * @param maxValue maxX and maxY.
     */
    protected void addSeries(String seriesName, int minValue, int maxValue, int dotsCount) {
        setText(findTextField(ADDED_SERIES_NAME_TEXTFIELD_ID), seriesName);
        setText(findTextField(ADDED_SERIES_MIN_VALUE_TEXTFIELD_ID), String.valueOf(minValue));
        setText(findTextField(ADDED_SERIES_MAX_VALUE_TEXTFIELD_ID), String.valueOf(maxValue));
        setText(findTextField(ADDED_SERIES_DOTS_COUNT_TEXTFIELD_ID), String.valueOf(dotsCount));
        clickButtonForTestPurpose(ADD_SERIES_COMMAND_BUTTON_ID);
    }

    protected void removeFromIndex(int... indices) {
        for (int index : indices) {
            setText(findTextField(REMOVE_AREA_INDEX_TEXTFIELD_ID), String.valueOf(index));
            clickButtonForTestPurpose(REMOVE_AREA_BUTTON_ID);
        }
    }

    protected void checkDotsCount(int expectedCount) {
        new Waiter(new Timeout("Waiting chart-are-symbols count", 2000)).ensureValue(expectedCount, new State<Integer>() {
            public Integer reached() {
                return testedControl.as(Parent.class, Node.class).lookup(StackPane.class, new ByStyleClass(CHART_AREA_SYMBOL_STYLECLASS)).size();
            }
        });
    }

    void populateChart() {
        addSeries("Series A", 0, 100, 5);
        addSeries("Series B", 0, 100, 10);
        addSeries("Series C", 0, 100, 15);
    }

    protected enum AreaTestsProperties {

        createSymbols
    };
}