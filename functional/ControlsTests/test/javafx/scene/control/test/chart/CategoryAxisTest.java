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
import javafx.scene.control.test.chart.CategoryAxisApp.CathegoryAxisCSS;
import org.junit.Test;
import javafx.scene.control.test.chart.CategoryAxisApp.Pages;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class CategoryAxisTest extends AxisBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        CategoryAxisApp.main(null);
    }

    /**
     * Test for CategoryAxis constructors
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void constructorsTest() throws InterruptedException {
        testCommon(Pages.Constructors.name(), null, true, true, CategoryAxisApp.BOOL.length);
    }

    /**
     * Test for CategoryAxis setEndMargin API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void endMarginTest() throws InterruptedException {
        testCommon(Pages.EndMargin.name(), null, true, true, CategoryAxisApp.END_MARGIN.length);
    }

    /**
     * Test for CategoryAxis setStartAndEndProperty API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void startAndEndPropertyTest() throws InterruptedException {
        testCommon(Pages.GapStartAndEnd.name(), null, true, true, CategoryAxisApp.BOOL.length);
    }

    /**
     * Test for CathegoryAxis -fx-start-margin CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssStartMargin() throws InterruptedException {
        testCommon(CathegoryAxisCSS.CSSStartMargin.name(), null, true, true, CategoryAxisApp.MARGIN.length);
    }

    /**
     * Test for CathegoryAxis -fx-gap-start-and-end CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssGapStartAndEnd() throws InterruptedException {
        testCommon(CathegoryAxisCSS.CSSGapStartAndEnd.name(), null, true, true, CategoryAxisApp.BOOL.length);
    }

    /**
     * Test for CathegoryAxis -fx-end-margin CSS
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cssEndMargin() throws InterruptedException {
        testCommon(CathegoryAxisCSS.CSSEndMargin.name(), null, true, true, CategoryAxisApp.MARGIN.length);
    }
}