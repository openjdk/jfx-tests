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

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.scene.control.test.chart.XYChartBaseApp.Pages;
import javafx.scene.control.test.chart.XYChartBaseApp.XYChartCSS;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class XYChartBase extends ChartBase {

    /**
     * Test for XYChart setData API
     */
//    @ScreenshotCheck
//    @Test(timeout=30000)
//    public void dataTest() throws InterruptedException {
//        testCommon(Pages.Data.name(), null, true, true);
//    }
    /**
     * Test for XYChart setAxis API
     */
//    @ScreenshotCheck
//    @Test(timeout=30000)
//    public void axisTest() throws InterruptedException {
//        testCommon(Pages.Axis.name(), null, true, true);
//    }
    /**
     * Test for XYChart setHorizontalGridLineVisible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void horizontalGridLineVisibleTest() throws InterruptedException {
        testCommon(Pages.HorizontalGridLinesVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart setAlternativeColumnFillVisible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void alternativeColumnFillVisibleTest() throws InterruptedException {
        testCommon(Pages.AlternativeColumnFillVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart setAlternativeRowFillVisible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void alternativeRowFillVisibleTest() throws InterruptedException {
        testCommon(Pages.AlternativeRowFillVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart setHorizontalZeroLineVisible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void horizontalZeroLineVisibleTest() throws InterruptedException {
        testCommon(Pages.HorizontalZeroLineVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart setVerticalGridLineVisible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void verticalGridLineVisibleTest() throws InterruptedException {
        testCommon(Pages.VerticalGridLinesVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart setVerticalZeroLineVisible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void verticalZeroLineVisible() throws InterruptedException {
        testCommon(Pages.VerticalZeroLineVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart adding series
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void addSeriesTest() throws InterruptedException {
        testCommon(Pages.AddSeries.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart adding series data
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void addSeriesDataTest() throws InterruptedException {
        testCommon(Pages.AddSeriesData.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart changing series data
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void changeSeriesDataTest() throws InterruptedException {
        testCommon(Pages.ChangeSeriesData.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart adding series
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void removeSeriesTest() throws InterruptedException {
        testCommon(Pages.RemoveSeries.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for XYChart adding series data
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void removeSeriesDataTest() throws InterruptedException {
        testCommon(Pages.RemoveSeriesData.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for -fx-vertical-grid-lines-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssVerticalGridLinesVisible() throws InterruptedException {
        testCommon(XYChartCSS.CSSVerticalGridLinesVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for -fx-horizontal-grid-lines-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssHorizontalGridLinesVisible() throws InterruptedException {
        testCommon(XYChartCSS.CSSHorizontalGridLinesVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for -fx-alternative-column-fill-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssAlternativeColumnFillVisible() throws InterruptedException {
        testCommon(XYChartCSS.CSSAlternativeColumnFillVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for -fx-alternative-row-fill-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssAlternativeRowFillVisible() throws InterruptedException {
        testCommon(XYChartCSS.CSSAlternativeRowFillVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for -fx-vertical-zero-line-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssVerticalZeroLineVisible() throws InterruptedException {
        testCommon(XYChartCSS.CSSVerticalZeroLineVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for -fx-horizontal-zero-line-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssHorizontalZeroLineVisible() throws InterruptedException {
        testCommon(XYChartCSS.CSSHorizontalZeroLineVisible.name(), null, true, true, XYChartBaseApp.BOOL.length);
    }

    /**
     * Test for RT-38221: [LineChart] Javafx 8 Line Chart does not plot data in order
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void testPlotDataOrder() throws InterruptedException {
        testCommon(Pages.PlotDataOrder.name(), null, true, true, XYChartBaseApp.BOOL.length);
}
}
