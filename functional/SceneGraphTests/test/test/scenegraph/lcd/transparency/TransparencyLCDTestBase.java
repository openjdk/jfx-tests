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
package test.scenegraph.lcd.transparency;

import org.jemmy.Dimension;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ControlDock;
import org.jemmy.image.GlassImage;
import org.jemmy.image.Image;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.Raster.Component;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Before;
import test.javaclient.shared.JemmyUtils;
import test.scenegraph.lcd.LcdUtils;
import test.scenegraph.lcd.PixelsCalc;
import static java.util.Arrays.asList;
import org.junit.Assert;

/**
 *
 * @author Alexander Petrov
 */
public class TransparencyLCDTestBase {

    static {
        test.javaclient.shared.Utils.initializeAwt();
    }
    private static final double LOWER_THRESHOLD = 0.008;
    private static final double UPPER_THRESHOLD = 0.95;
    private PixelsCalc calc = new PixelsCalc();

    @Before
    public void before() {
        if (LcdUtils.isApplicablePlatform()) {
            //prepare env
            JemmyUtils.initJemmy();
            JemmyUtils.setJemmyRoughComparator(0.0001);

            SceneDock scene = new SceneDock();
            this.applyButton = new ControlDock(scene.asParent(), TransparencyLCDTextTestApp.APPLY_BUTTON_ID);
            this.actionButton = new ControlDock(scene.asParent(), TransparencyLCDTextTestApp.ACTION_BUTTON_ID);
            this.rightPane = new NodeDock(scene.asParent(), TransparencyLCDTextTestApp.RIGHT_PANE_ID);
            applyIndicator = new NodeDock(scene.asParent(), TransparencyLCDTextTestApp.APPLY_INDICATOR_ID);
            actionIndicator = new NodeDock(scene.asParent(), TransparencyLCDTextTestApp.ACTION_INDICATOR_ID);
        }
    }
    private NodeDock rightPane;
    private NodeDock applyIndicator;
    private NodeDock actionIndicator;
    private ControlDock applyButton;
    private ControlDock actionButton;

    public void commonTest() {
        if (LcdUtils.isApplicablePlatform()) {

            this.applyButton.wrap().mouse().move();
            this.applyButton.wrap().mouse().click();

            this.rightPane.wrap().waitState(new State() {
                public Object reached() {
                    return getGreenFromTheMiddleOfIndicator(applyIndicator) >= 127;
                }
            }, true);

            Image lcdImage = this.rightPane.wrap().getScreenImage();

            assertTrue(
                    "LCD text test fail( LCD Text is "
                    + String.valueOf(TransparencyLCDTextTestApp.testingFactory.isLCDWork())
                    + ")",
                    testLCD(lcdImage, TransparencyLCDTextTestApp.testingFactory.isLCDWork()));

            //Process action.
            this.actionButton.wrap().mouse().move();
            this.actionButton.wrap().mouse().click();

            this.rightPane.wrap().waitState(new State() {
                public Object reached() {
                    return getGreenFromTheMiddleOfIndicator(actionIndicator) >= 127;
                }
            }, true);

            lcdImage = this.rightPane.wrap().getScreenImage();

            assertTrue(
                    "LCD text test fail( LCD Text is "
                    + String.valueOf(TransparencyLCDTextTestApp.testingFactory.isLCDWork())
                    + ")",
                    testLCD(lcdImage, TransparencyLCDTextTestApp.testingFactory.isLCDWork()));
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

    private int getGreenFromTheMiddleOfIndicator(NodeDock indicator) {

        assertTrue(indicator != null);

        int greenComponentValue = -1;

        Image image = indicator.wrap().getScreenImage();
        if (image instanceof GlassImage) {

            GlassImage glassImage = ((GlassImage)image);

            Component[] supportedComponents = glassImage.getSupported();
            int idxGreen = asList(supportedComponents).indexOf(Raster.Component.GREEN);
            int compCount = supportedComponents.length;

            double[] colors = new double[compCount];
            Dimension size = glassImage.getSize();
            glassImage.getColors(size.width / 2, size.height / 2, colors);

            greenComponentValue = (int)(colors[idxGreen] * 0xFF);

        } else if (image instanceof org.jemmy.image.AWTImage) {

            int rgb = ((org.jemmy.image.AWTImage)image).getTheImage().getRGB(
                    ((org.jemmy.image.AWTImage)image).getTheImage().getWidth() / 2, ((org.jemmy.image.AWTImage)image).getTheImage().getHeight() / 2);

            greenComponentValue = (rgb >> 8) & 0xFF;

        } else {
            Assert.fail("Unknown image type: " + image.getClass().getName());
        }

        return greenComponentValue;
     }
}
