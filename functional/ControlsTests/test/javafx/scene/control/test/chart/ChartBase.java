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
import javafx.scene.control.test.chart.ChartBaseApp.ChartCSS;
import javafx.scene.control.test.chart.ChartBaseApp.Pages;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class ChartBase extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUIBase() {
        BaseApp.showButtonsPane(false);
    }

    @Before
    public void initComparator() {
        ScreenshotUtils.setComparatorDistance(0.001f);
    }

    /**
     * Test for Chart setAnimated API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void animatedTest() throws InterruptedException {
        testCommon(Pages.Animated.name(), null, true, true, ChartBaseApp.BOOL.length);
    }

//    /**
//     * Test for Chart setLegend API
//     */
//    @ScreenshotCheck
//    @Test(timeout=300000)
//    public void legendTest() throws InterruptedException {
//        testCommon(Pages.Legend.name(), null, true, true);
//    }
    /**
     * Test for Chart setLegendSide API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void legendSideTest() throws InterruptedException {
        testCommon(Pages.LegendSide.name(), null, true, true, ChartBaseApp.SIDE.length);
    }

    /**
     * Test for Chart setTitle API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void titleTest() throws InterruptedException {
        testCommon(Pages.Title.name(), null, true, true, ChartBaseApp.BOOL.length);
    }

    /**
     * Test for Chart setTitleSide API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void titleSideTest() throws InterruptedException {
        testCommon(Pages.TitleSide.name(), null, true, true, ChartBaseApp.SIDE.length);
    }

    /**
     * Test for Chart setLegendVisible API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void legendVisibleTest() throws InterruptedException {
        testCommon(Pages.LegendVisible.name(), null, true, true, ChartBaseApp.BOOL.length);
    }

    /**
     * Test for -fx-title-side CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssTitleSide() throws InterruptedException {
        testCommon(ChartCSS.CSSTitleSide.name(), null, true, true, ChartBaseApp.SIDE.length);
    }

    /**
     * Test for -fx-legend-side CSS
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void cssLegendSide() throws InterruptedException {
        testCommon(ChartCSS.CSSLegendSide.name(), null, true, true, ChartBaseApp.SIDE.length);
    }

    /**
     * Test for -fx-legend-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssLegendVisible() throws InterruptedException {
        testCommon(ChartCSS.CSSLegendVisible.name(), null, true, true, ChartBaseApp.BOOL.length);
    }
}