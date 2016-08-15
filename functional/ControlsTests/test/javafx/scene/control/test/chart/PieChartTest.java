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
import javafx.scene.control.test.chart.PieChartApp.Pages;
import javafx.scene.control.test.chart.PieChartApp.PieChartCSS;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class PieChartTest extends ChartBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        PieChartApp.main(null);
    }

    /**
     * Test for PieChart setLabelLineLength API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void labelLineLengthTest() throws InterruptedException {
        testCommon(Pages.LabelLineLength.name(), null, true, true, PieChartApp.LENGTH_D.length);
    }

    /**
     * Test for PieChart setLabelsVisible API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void labelsVisibleTest() throws InterruptedException {
        testCommon(Pages.LabelsVisible.name(), null, true, true, PieChartApp.BOOL.length);
    }

    /**
     * Test for PieChart setStartAngle API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void startAngleTest() throws InterruptedException {
        testCommon(Pages.StartAngle.name(), null, true, true, PieChartApp.START_ANGLE_D.length);
    }

    /**
     * Test for PieChart setPieLabelFont API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void clockwiseTest() throws InterruptedException {
        testCommon(Pages.Clockwise.name(), null, true, true, PieChartApp.BOOL.length);
    }

    /**
     * Test for PieChart adding data
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void addDataTest() throws InterruptedException {
        testCommon(Pages.AddData.name(), null, true, true, PieChartApp.BOOL.length);
    }

    /**
     * Test for PieChart removing data
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void removeDataTest() throws InterruptedException {
        testCommon(Pages.RemoveData.name(), null, true, true, PieChartApp.BOOL.length);
    }

    /**
     * Test for -fx-clockwise CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssClockwise() throws InterruptedException {
        testCommon(PieChartCSS.CSSClockwise.name(), null, true, true, PieChartApp.BOOL.length);
    }

    /**
     * Test for -fx-pie-label-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssPieLabelVisible() throws InterruptedException {
        testCommon(PieChartCSS.CSSPieLabelVisible.name(), null, true, true, PieChartApp.BOOL.length);
    }

    /**
     * Test for -fx-label-line-length CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssLabelLineLength() throws InterruptedException {
        testCommon(PieChartCSS.CSSLabelLineLength.name(), null, true, true, PieChartApp.LENGTH_S.length);
    }

    /**
     * Test for -fx-start-angle CSS
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void cssStartAngle() throws InterruptedException {
        testCommon(PieChartCSS.CSSStartAngle.name(), null, true, true, PieChartApp.START_ANGLE_S.length);
    }
}
