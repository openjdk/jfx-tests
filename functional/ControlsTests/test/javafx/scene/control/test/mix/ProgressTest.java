/*
 * Copyright (c) 2014, 2023, Oracle and/or its affiliates. All rights reserved.
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

import javafx.scene.control.test.ProgressApp;
import javafx.scene.control.test.ProgressApp.Pages;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;

/**
 *
 * @author Andrey Glushchenko
 */
public class ProgressTest extends TestBase{
/**
     * Test for ProgressBar and ProgressIndicator constructors
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void constructorsTest() throws InterruptedException {
        testCommon(Pages.Constructors.name(), true, true);
    }

    /**
     * Test for ProgressBar and ProgressIndicator constructors with indetermined
     * initial state
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void indeterminedConstructorsTest() throws InterruptedException {
        testCommon(Pages.IndeterminedConstructors.name(), false, false);
    }

    /**
     * Test for ProgressIndicator setProgress API
     */
    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void progressIndicatorsTest() throws InterruptedException {
        testCommon(Pages.ProgressIndicator.name(), true, true);
    }

    /**
     * Test for ProgressBar setProgress API
     */
    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void progressBarTest() throws InterruptedException {
        testCommon(Pages.ProgressBar.name(), true, true);
    }

    //Util
    @BeforeClass
    public static void runUI() {
        ProgressApp.main(null);
    }

    @Override
    protected String getName() {
        return "ProgressTest";
    }
    private void testCommon(String name,boolean shoots,boolean valuable_rect){
        testCommon(name,null,shoots,valuable_rect);
    }
}
