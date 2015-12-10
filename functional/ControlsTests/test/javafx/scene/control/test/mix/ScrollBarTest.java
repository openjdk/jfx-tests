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
package javafx.scene.control.test.mix;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.scene.control.test.ScrollBarApp.Pages;
import javafx.scene.control.test.scrollbar.ScrollBarApp;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;

@RunWith(FilteredTestRunner.class)
public class ScrollBarTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        ScrollBarApp.main(null);
    }

    /**
     * Test for Slider constructor
     */
    @ScreenshotCheck
    @Test(timeout = 3000000)
    public void constructorTest() throws InterruptedException {
        testCommon(Pages.Constructor.name());
    }

    /**
     * Test for ScrollBar setAdjustValue API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void adjustValueHorizontalTest() throws InterruptedException {
        testCommon(Pages.AdjustValueHorizontal.name());
    }

    /**
     * Test for ScrollBar setAdjustValue API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void adjustValueVerticalTest() throws InterruptedException {
        testCommon(Pages.AdjustValueVertical.name());
    }

    /**
     * Test for ScrollBar decrement API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void decrementTest() throws InterruptedException {
        testCommon(Pages.Decrement.name());
    }

    /**
     * Test for ScrollBar increment API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void incrementTest() throws InterruptedException {
        testCommon(Pages.Increment.name());
    }

    /**
     * Test for ScrollBar setValue, setMin, setMax API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void positionVerticalTest() throws InterruptedException {
        testCommon(Pages.PositionsHorizontal.name());
    }

    /**
     * Test for ScrollBar setValue, setMin, setMax API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void positionHorizontalTest() throws InterruptedException {
        testCommon(Pages.PositionsVertical.name());
    }

    /**
     * Test for ScrollBar setVisibleAmount API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void visibleAmountTest() throws InterruptedException {
        testCommon(Pages.VisibleAmount.name());
    }

    protected void testCommon(String name) {
        testCommon(name, null, true, false);
    }
}