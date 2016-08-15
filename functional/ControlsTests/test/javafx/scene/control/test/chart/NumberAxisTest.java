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
import javafx.scene.control.test.chart.NumberAxisApp.NumberAxisCSS;
import org.junit.Test;
import javafx.scene.control.test.chart.NumberAxisApp.Pages;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class NumberAxisTest extends ValueAxisBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        NumberAxisApp.main(null);
    }

    /**
     * Test for NumberAxis constructors
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void constructorsTest() throws InterruptedException {
        testCommon(Pages.Constructors.name(), null, true, true, NumberAxisApp.CONSTRUCTORS);
    }

    /**
     * Test for NumberAxis setForceZeroInRange API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void forceZeroInRangeTest() throws InterruptedException {
        testCommon(Pages.ForceZeroInRange.name(), null, true, true, NumberAxisApp.BOOL.length);
    }

    /**
     * Test for NumberAxis setTickUnit API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tickUnitTest() throws InterruptedException {
        testCommon(Pages.TickUnit.name(), null, true, true, NumberAxisApp.TICK_UNIT_D.length);
    }

    /**
     * Test for NumberAxis -fx-tick-unit CSS
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void cssTickUnit() throws InterruptedException {
        testCommon(NumberAxisCSS.CSSTickUnit.name(), null, true, true, NumberAxisApp.TICK_UNIT_S.length);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tickValueOverflowTest() throws InterruptedException {
        testCommon(Pages.UpperBoundOverflow.name());
    }
}