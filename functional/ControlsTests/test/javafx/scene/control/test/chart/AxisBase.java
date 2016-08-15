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
import javafx.scene.control.test.chart.AxisBaseApp.AxisCSS;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import javafx.scene.control.test.chart.AxisBaseApp.Pages;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public abstract class AxisBase extends TestBase {

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
     * Test for Axis setAnimated API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void animatedTest() throws InterruptedException {
        testCommon(Pages.Animated.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis setAutoRanging API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void autoRangingTest() throws InterruptedException {
        testCommon(Pages.AutoRanging.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis setLabel API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void labelTest() throws InterruptedException {
        testCommon(Pages.Label.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis setSide API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void sideTest() throws InterruptedException {
        testCommon(Pages.Side.name(), null, true, true, AxisBaseApp.SIDE.length);
    }

    /**
     * Test for Axis setTickLabelFill API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tickLabelFillTest() throws InterruptedException {
        testCommon(Pages.TickLabelFill.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis setTickLabelFont API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void tickLabelFontTest() throws InterruptedException {
        testCommon(Pages.TickLabelFont.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis setTickLabelGap API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tickLabelGapTest() throws InterruptedException {
        testCommon(Pages.TickLabelGap.name(), null, true, true, AxisBaseApp.GAP.length);
    }

    /**
     * Test for Axis setTickLabelRotation API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tickLabelRotationTest() throws InterruptedException {
        testCommon(Pages.TickLabelRotation.name(), null, true, true, AxisBaseApp.ROTATION.length);
    }

    /**
     * Test for Axis setTickLabelRotation API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tickLengthTest() throws InterruptedException {
        testCommon(Pages.TickLength.name(), null, true, true, AxisBaseApp.LENGTH_DOUBLE.length);
    }

    /**
     * Test for Axis -fx-side CSS
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void cssSide() throws InterruptedException {
        testCommon(AxisCSS.CSSSide.name(), null, true, true, AxisBaseApp.SIDE.length);
    }

    /**
     * Test for Axis -fx-tick-length CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssTickLength() throws InterruptedException {
        testCommon(AxisCSS.CSSTickLength.name(), null, true, true, AxisBaseApp.LENGTH.length);
    }

    /**
     * Test for Axis -fx-tick-label-font CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssTickLabelFont() throws InterruptedException {
        testCommon(AxisCSS.CSSTickLabelFont.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis -fx-tick-label-fill CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssTickLabelFill() throws InterruptedException {
        testCommon(AxisCSS.CSSTickLabelFill.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis -fx-tick-mark-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssTickMarkVisible() throws InterruptedException {
        testCommon(AxisCSS.CSSTickMarkVisible.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    /**
     * Test for Axis -fx-tick-labels-visible CSS
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void cssTickLabelsVisible() throws InterruptedException {
        testCommon(AxisCSS.CSSTickLabelsVisible.name(), null, true, true, AxisBaseApp.BOOL.length);
    }

    protected void testCommon(String name) {
        testCommon(name, null);
    }
}