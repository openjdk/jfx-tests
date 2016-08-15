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
import javafx.scene.control.test.chart.ValueAxisApp.Pages;
import javafx.scene.control.test.chart.ValueAxisApp.ValueAxisCSS;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class ValueAxisBase extends AxisBase {

    /**
     * Test for ValueAxis setLowerBound API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void lowerBoundTest() throws InterruptedException {
        testCommon(Pages.LowerBound.name(), null, true, true, ValueAxisApp.BOUNDS.length);
    }

    /**
     * Test for ValueAxis setMinorTickCount API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void minorTickCountTest() throws InterruptedException {
        testCommon(Pages.MinorTickCount.name(), null, true, true, ValueAxisApp.MINOR_TICK_COUNT.length);
    }

    /**
     * Test for ValueAxis setMinorTickLength API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void minorTickLengthTest() throws InterruptedException {
        testCommon(Pages.MinorTickLength.name(), null, true, true, ValueAxisApp.MINOR_TICK_LENGTH.length);
    }

    /**
     * Test for ValueAxis setTickLabelFormatter API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tickLabelFormatterTest() throws InterruptedException {
        testCommon(Pages.TickLabelFormatter.name(), null, true, true, ValueAxisApp.BOOL.length);
    }

    /**
     * Test for ValueAxis setUpperBound API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void upperBoundTest() throws InterruptedException {
        testCommon(Pages.UpperBound.name(), null, true, true, ValueAxisApp.BOUNDS.length);
    }

    /**
     * Test for ValueAxis -fx-minor-tick-length CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssMinorTickLength() throws InterruptedException {
        testCommon(ValueAxisCSS.CSSMinorTickLength.name(), null, true, true, ValueAxisApp.LENGTH.length);
    }

    /**
     * Test for ValueAxis -fx-minor-tick-count CSS
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void cssMinorTickCount() throws InterruptedException {
        testCommon(ValueAxisCSS.CSSMinorTickCount.name(), null, true, true, ValueAxisApp.LENGTH.length);
    }

    /**
     * Test for ValueAxis -fx-minor-tick-visible CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssMinorTickVisible() throws InterruptedException {
        testCommon(ValueAxisCSS.CSSMinorTickVisible.name(), null, true, true, ValueAxisApp.BOOL.length);
    }
}