/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */
package test.scenegraph.modality;

import org.jemmy.control.Wrap;
import org.junit.Before;
import test.javaclient.shared.AppLauncher;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * @author Sergey Grinev
 * @author Alexander Kirov
 */
public class Utils {

    static {
        test.javaclient.shared.Utils.initializeAwt();
    }

    @Before
    public void delay() {
        try {
            Thread.sleep(AppLauncher.getInstance().getTestDelay());
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check, whether the saved in golden images screenshot is the same as now.
     *
     * @param name
     * @param node
     */
    protected void checkScreenshot(String name, Wrap node) {
        try {
            ScreenshotUtils.checkScreenshot(name, node);
        } catch (Throwable th) {
            if (screenshotError == null) {
                screenshotError = th;
            }
        }
    }

    /**
     * This method checks, that applied screenshot is not such as in a picture
     * file
     *
     * @param name - name of file
     * @param node - node wrapper
     */
    protected void negativeCheckScreenshot(String name, Wrap node) {
        try {
            ScreenshotUtils.checkScreenshot(name, node);
            screenshotError = new IllegalStateException(
                    "When comparing pictures \"" + name
                    + "\" was found that they are equal, but that must not be");
        } catch (Throwable th) {
        }
    }

    /**
     * Throws local variable which stores the first appeared exception
     *
     * @throws Throwable
     */
    protected void throwScreenshotError() throws Throwable {
        if (screenshotError != null) {
            Throwable throwable = screenshotError;
            screenshotError = null;
            throw throwable;
        }
    }
    /**
     * The first appeared error
     */
    protected Throwable screenshotError = null;
}
