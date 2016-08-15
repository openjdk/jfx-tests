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
package test.scenegraph.lcd.controls;

import javafx.scene.text.Text;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.TextDock;
import org.jemmy.fx.control.ControlDock;
import org.jemmy.image.Image;
import org.jemmy.lookup.Lookup;
import static org.junit.Assert.*;
import org.junit.Before;
import test.javaclient.shared.JemmyUtils;
import test.scenegraph.lcd.LcdUtils;
import test.scenegraph.lcd.PixelsCalc;

/**
 *
 * @author Alexander Petrov, updated by Victor Shubov
 */
public class LCDControlsTestBase {
    public static final int MAX_COLOR_PIXELS_COUNT = 40;
    public static final int MAX_GREEN_PIXELS_COUNT = 10;
    private static final double LOWER_THRESHOLD = 0.25;
    private static final double UPPER_THRESHOLD = 0.95;

    static {
        test.javaclient.shared.Utils.initializeAwt();
    }
    private SceneDock scene;
    private ControlDock buttonApply;

    @Before
    public void before() {
         if (LcdUtils.isApplicablePlatform()) {
             //prepare env
             JemmyUtils.initJemmy();
             JemmyUtils.setJemmyRoughComparator(0.0001);
             this.scene = new SceneDock();
             this.buttonApply = new ControlDock(scene.asParent(), LCDControlsTestApp.BUTTON_APPLY_ID);
        }
    }

    public void commonTest() {
        if (LcdUtils.isApplicablePlatform()) {
            buttonApply.mouse().click();

            NodeDock rightPaneDock = new NodeDock(scene.asParent(), LCDControlsTestApp.RIGHT_PANE_ID);

            buttonApply.mouse().move();

            // wait until LCD rendered:
            // here should be a loop:
            // check image every 300ms and wait until image become stable
            try { Thread.sleep(400);} catch(Exception e){}
            checkAllTextImages(rightPaneDock,LCDControlsTestApp.action.isLCDWork());
       }
    }

    private  void checkAllTextImages(final NodeDock dock, final boolean _lcdWork) {
         System.out.println("___________________________________ action=" + LCDControlsTestApp.action);

        Lookup textLookup = dock.asParent().lookup(Text.class);
        for (int i = 0; i < textLookup.size(); i++) {
            final TextDock text = new TextDock(textLookup, i);
            final String strtext = text.getText();
            System.out.println("text=" + strtext );
            if (strtext.length() > 0 )
            try {
                final Image image = text.wrap().getScreenImage();
                testImage(image, _lcdWork);
            } catch (Exception ex) {
                System.err.println("Warning: node doesn't visible.");
                ex.printStackTrace();
            }
        }
    }

    private void testImage(final Image lcdImage, final boolean lcdWork) {
        if(!JemmyUtils.usingGlassRobot()){
            PixelsCalc lcdPixelsCalc = new PixelsCalc();

            lcdPixelsCalc.calculate(lcdImage, false);

            assertTrue("LCD text out of the control's bounds", greenPixelsTest(lcdPixelsCalc));

            assertTrue("LCD test fail", lcdTest(lcdPixelsCalc, lcdWork));

        } else
            System.out.println("testImage method for Glass robot is not yet implemented");

    }

    private boolean greenPixelsTest(final PixelsCalc calc) {
        final int greenPixels = calc.getGreenPixelCount();
        boolean bResult = greenPixels < MAX_GREEN_PIXELS_COUNT;
        System.out.println(" greenPixelsTest:" + (bResult?"Passed":"FAILED")  +  "  ( actual:" + greenPixels + " MAX_GREEN_PIXELS_COUNT=" + MAX_GREEN_PIXELS_COUNT);
        return  bResult;
    }

    private boolean lcdTest(final PixelsCalc calc, final boolean lcd) {
        final int colorPixelCount = calc.getColorPixelCount();
        if (lcd) {
            final int greyPixelCount = calc.getGrayPixelCount();
            final double sumPixelCount = colorPixelCount + greyPixelCount;
            System.out.println(" colorPixelCount=" + colorPixelCount + " greyPixelCount=" + greyPixelCount);
            if ( 0 != colorPixelCount) {
                final double percent = colorPixelCount  / sumPixelCount;
                System.out.println(" percent = colorPixelCount  / sumPixelCount =" + percent
                                 + "[  < " + UPPER_THRESHOLD + " && >" + LOWER_THRESHOLD);
                return (percent < UPPER_THRESHOLD) && (percent > LOWER_THRESHOLD);
            }
            return false;
        } else {
            System.out.println(" lcd = false, return colorPixelCount=" + colorPixelCount + "<= MAX_COLOR_PIXELS_COUNT" + MAX_COLOR_PIXELS_COUNT);
            return colorPixelCount <= MAX_COLOR_PIXELS_COUNT;
        }
    }
}
