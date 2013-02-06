/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.transparency;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.jemmy.Dimension;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.ControlDock;
import org.jemmy.image.AWTImage;
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
        if (image instanceof AWTImage) {

            BufferedImage bufferedImage = ((AWTImage)image).getTheImage();
            
            Color color = new Color(bufferedImage.getRGB(
                    bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2));
            
            greenComponentValue = color.getGreen();

        } else if (image instanceof GlassImage) {

            GlassImage glassImage = ((GlassImage)image);
            
            Component[] supportedComponents = glassImage.getSupported();
            int idxGreen = asList(supportedComponents).indexOf(Raster.Component.GREEN);
            int compCount = supportedComponents.length;
            
            double[] colors = new double[compCount];
            Dimension size = glassImage.getSize();
            glassImage.getColors(size.width / 2, size.height / 2, colors);

            greenComponentValue = (int)(colors[idxGreen] * 0xFF);

        } else {
            Assert.fail("Unknown image type: " + image.getClass().getName());
        }
        
        return greenComponentValue;
     }
}
