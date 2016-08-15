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
package javafx.scene.control.test.tabpane;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import javafx.scene.control.test.tabpane.TabPaneApp.Pages;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;

@RunWith(FilteredTestRunner.class)
public class TabPaneTest extends TestBase {

    //@RunUI
    @BeforeClass
    public static void runUI() {
        TabPaneApp.main(null);
    }

    /**
     * Test for TabPane setRotateGraphic API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void rotateGraphicTest() throws InterruptedException {
        testCommon(Pages.RotateGraphic.name());
    }

    /**
     * Test for TabPane setSelection API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectionTest() throws InterruptedException {
        testCommon(Pages.Selection.name());
    }

    /**
     * Test for TabPane setSide API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void sideTest() throws InterruptedException {
        testCommon(Pages.Side.name(), null, true, false, Side.values().length);
    }

    /**
     * Test for TabPane setTabMaxHeight API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tabMaxHeightTest() throws InterruptedException {
        testCommon(Pages.TabMaxHeight.name());
    }

    /**
     * Test for TabPane setTabMaxWidth API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tabMaxWidthTest() throws InterruptedException {
        testCommon(Pages.TabMaxWidth.name());
    }

    /**
     * Test for TabPane setTabMinHeight API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tabMinHeightTest() throws InterruptedException {
        testCommon(Pages.TabMinHeight.name());
    }

    /**
     * Test for TabPane setTabMinWidth API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tabMinWidthTest() throws InterruptedException {
        testCommon(Pages.TabMinWidth.name());
    }

    /**
     * Test for TabPane setTabClosingPolicy API
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void tabClosingPolicyTest() throws InterruptedException {
        testCommon(Pages.TabClosingPolicy.name(), null, true, false, TabPane.TabClosingPolicy.values().length);
    }
}
