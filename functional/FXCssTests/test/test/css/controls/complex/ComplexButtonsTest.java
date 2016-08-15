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
package test.css.controls.complex;

import org.junit.BeforeClass;
import org.junit.Test;
import test.css.controls.ComplexButtonCssTests;
import test.css.controls.ComplexButtonCssTests.Pages.*;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author sergey lugovoy <sergey.lugovoy@oracle.com>
 */
public class ComplexButtonsTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    /**
     * Common test workflow. <p>Second, test verify output with golden
     * screenshot.
     *
     * @param clazz
     */
    //@RunUI
    @BeforeClass
    public static void runUI() {
        ComplexButtonCssTests.main(null);
    }

    @Test
    public void Buttons_WINDOWS7() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.WINDOWS7.name());
    }

    @Test
    public void Buttons_WINDOWS7_DEFAULT() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.WINDOWS7_DEFAULT.name());
    }

    @Test
    public void Buttons_LION() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.LION.name());
    }

    @Test
    public void Buttons_LION_DEFAULT() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.LION_DEFAULT.name());
    }

    @Test
    public void Buttons_IPAD_GREY() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.IPAD_GREY.name());
    }

    @Test
    public void Buttons_IPAD_DARK_GREY() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.IPAD_DARK_GREY.name());
    }

    @Test
    public void Buttons_IPHONE() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.IPHONE.name());
    }

    @Test
    public void Buttons_IPHONE_TOOLBAR() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.IPHONE_TOOLBAR.name());
    }

    @Test
    public void Buttons_BIG_YELLOW() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.BIG_YELLOW.name());
    }

    @Test
    public void Buttons_RICH_BLUE() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.RICH_BLUE.name());
    }

    @Test
    public void Buttons_RECORD_SALES() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.DARK_BLUE.name());
    }

    @Test
    public void Buttons_DARK_BLUE() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.DARK_BLUE.name());
    }

    @Test
    public void Buttons_GREEN() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.GREEN.name());
    }

    @Test
    public void Buttons_ROUND_RED() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.ROUND_RED.name());
    }

    @Test
    public void Buttons_BEVEL_GREY() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.BEVEL_GREY.name());
    }

    @Test
    public void Buttons_GLASS_GREY() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.GLASS_GREY.name());
    }

    @Test
    public void Buttons_SHINY_ORANGE() throws InterruptedException {
        testCommon(ComplexButtonCssTests.Pages.SHINY_ORANGE.name());
    }
}
