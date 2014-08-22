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
package javafx.scene.control.test;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.scene.control.test.SeparatorApp.Pages;
import javafx.scene.control.test.SeparatorApp.SeparatorsPages;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Basic Separator control API test Uses {
 *
 * @javafx.scene.control.test.SeparatorApp} to render controls
 */
public class SeparatorsTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.001f);
    }

    /**
     * Constructor test
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void constructorsTest() throws InterruptedException {
        testCommon(Pages.Constructors.name(), false, false);
    }

//    @Test(timeout=300000)
//    @ScreenshotCheck
//    public void nodesTest() throws InterruptedException {
//        testCommons(Pages.Nodes.name());
//    }
    /**
     * Test for isVertical API of horizontal separator
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void isVerticalHTest() throws InterruptedException {

        testCommon(Pages.HSeparator.name() + "-" + SeparatorsPages.isVertical.name(), false, false);
    }

    /**
     * Test for setHalignment API of horizontal separator
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setHalignmentHTest() throws InterruptedException {
        test(Pages.HSeparator.name(), SeparatorsPages.setHalignment.name());
    }

    /**
     * Test for setValignment API of horizontal separator
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void setValignmentHTest() throws InterruptedException {
        test(Pages.HSeparator.name(), SeparatorsPages.setValignment.name());
    }

    /**
     * Test for isVertical API of vertical separator
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void isVerticalVTest() throws InterruptedException {
        test(Pages.VSeparator.name(), SeparatorsPages.isVertical.name());
    }

    /**
     * Test for setHalignment API of vertical separator
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void setHalignmentVTest() throws InterruptedException {
        test(Pages.VSeparator.name(), SeparatorsPages.setHalignment.name());
    }

    /**
     * Test for setValignment API of vertical separator
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setValignmentVTest() throws InterruptedException {
        test(Pages.VSeparator.name(), SeparatorsPages.setValignment.name());

    }

    protected void test(String name, String subname) {
        testCommon(name + "-" + subname, true, true);
    }

    //Util
    @BeforeClass
    public static void runUI() {
        SeparatorApp.main(null);
    }

    @Override
    protected String getName() {
        return "SeparatorsTest";
    }

    private void testCommon(String name, boolean shoots, boolean valuable_rect) {
        testCommon(name, null, shoots, valuable_rect);
    }
}