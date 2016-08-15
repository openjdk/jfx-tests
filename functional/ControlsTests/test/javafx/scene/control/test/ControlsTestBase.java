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

import org.jemmy.control.Wrap;
import org.jemmy.image.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.JemmyUtils;
import test.javaclient.shared.SWTRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author Sergey Grinev
 */

@RunWith(SWTRunner.class)
public class ControlsTestBase {

    static {
        test.javaclient.shared.Utils.initializeAwt();
    }
    private static boolean jemmyStuffConfigured = false;

    @BeforeClass
    public static void setUpClassBefore() {
        jemmyStuffConfigured = false;
    }
    private float comparatorDistance = 0.001f;
    public static final float STRICT_COMPARATOR = 0f;

    /**
     * Set image comparator distance. Should be called from constructor or {}
     * class initialization block.
     *
     * @param comparatorDistance distance or TestBase.STRICT_COMPARATOR to use
     * strict comparator
     */
    public void setJemmyComparatorByDistance(float comparatorDistance) {
        System.out.println("javafx.scene.control.test.ControlsTestBase.setJemmyComparatorByDistance " + comparatorDistance);
        JemmyUtils.setJemmyComparatorByDistance(comparatorDistance);
    }

    @Before
    public void before() {
        if (!jemmyStuffConfigured) {
            //prepare env
            JemmyUtils.initJemmy();
            jemmyStuffConfigured = true;
        }
    }

    public static int getMouseSmoothness(){
        return JemmyUtils.getMouseSmoothness();
    }

    public static void setMouseSmoothness(int value){
        JemmyUtils.setMouseSmoothness(value);
    }

    public static void runInOtherJVM(boolean value){
        JemmyUtils.runInOtherJVM(value);
    }

    protected static void checkScreenshot(String name, Wrap node) {
        try {
            ScreenshotUtils.checkScreenshot(name, node);
        } catch (Throwable th) {
            if (screenshotError == null) {
                screenshotError = th;
            }
        }
    }

    public static double[] getColors(Image image){
        return JemmyUtils.getColors(image);
    }

    protected static void throwScreenshotError() throws Throwable {
        if (screenshotError != null) {
            Throwable throwable = screenshotError;
            screenshotError = null;
            throw throwable;
        }
    }
    protected static Throwable screenshotError = null;
}
