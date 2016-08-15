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
package test.scenegraph.lcd;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.image.Image;
import static org.junit.Assert.*;
import org.junit.Before;
import test.javaclient.shared.AppLauncher;
import test.javaclient.shared.JemmyUtils;

/**
 *
 * @author Alexander Petrov
 */
public class LCDTestBase {
    private static final double LOWER_THRESHOLD = 0.2;
    private static final double UPPER_THRESHOLD = 0.95;
    private PixelsCalc calc = new PixelsCalc();

    static {
        test.javaclient.shared.Utils.initializeAwt();
    }
    private Wrap<? extends Scene> scene;
    private Wrap<? extends Button> btnApply;
    private Wrap leftPane;

    @Before
    public void before() {
        if (LcdUtils.isApplicablePlatform()) {

            //prepare env
            JemmyUtils.initJemmy();
            JemmyUtils.setJemmyRoughComparator(0.0001);
            scene = Root.ROOT.lookup(Scene.class).wrap(0);
            btnApply = Lookups.byID(scene, "btnApply", Button.class);
            leftPane = Lookups.byID(scene, "leftPane", Parent.class);
        }
    }

    public void commonTest(TestAction action) {
        if (LcdUtils.isApplicablePlatform()) {
            LCDTextTestApp.testAction = action;
            btnApply.mouse().click();
            try {
                Thread.sleep(AppLauncher.getInstance().getTestDelay());
                //TODO: change font is not sync...investigate
            } catch (InterruptedException ex) {
            }

            Image lcdImage = leftPane.getScreenImage();

            if (action.isLCDWork()) {
                assertTrue("LCD test fail: " + action.toString(), testLCD(lcdImage, true));
            } else {
                assertTrue("No LCD test fail: " + action.toString(), testLCD(lcdImage, false));
            }
        }
    }

    private boolean testLCD(Image image, boolean lcd) {
        calc.calculate(image, true);
        if (lcd) {
            if (calc.getColorPixelCount() != 0) {
                double percent = (double) calc.getColorPixelCount()
                                / (calc.getColorPixelCount() + calc.getGrayPixelCount());
                return (percent < UPPER_THRESHOLD) && (percent > LOWER_THRESHOLD);
            }
            return false;
        } else {
            return calc.getColorPixelCount() == 0;
        }
    }
}
