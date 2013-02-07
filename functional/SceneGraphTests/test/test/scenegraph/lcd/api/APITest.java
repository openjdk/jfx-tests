/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.api;

import test.scenegraph.lcd.LcdAPITestApp;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.image.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import test.scenegraph.lcd.LcdUtils;
import test.scenegraph.lcd.PixelsCalc;
import test.javaclient.shared.JemmyUtils;

/**
 *
 * @author Alexander Petrov
 */
public class APITest {

    private static final double LOWER_THRESHOLD = 0.25;
    private static final double UPPER_THRESHOLD = 0.95;

    static {
        test.javaclient.shared.Utils.initializeAwt();
    }
    private NodeDock apiGrayCssGrayText;
    private NodeDock apiGrayCssLcdText;
    private NodeDock apiLcdCssGrayText;
    private NodeDock apiLcdCssLcdText;

    @BeforeClass
    public static void beforeClass() {
        LcdAPITestApp.main(null);
    }

    @Before
    public void before() {
        if (LcdUtils.isApplicablePlatform()) {
            JemmyUtils.initJemmy();
            JemmyUtils.setJemmyRoughComparator(0.0001);
            SceneDock scene = new SceneDock();
            this.apiGrayCssGrayText = new NodeDock(scene.asParent(), "GrayGray");
            this.apiGrayCssLcdText = new NodeDock(scene.asParent(), "GrayLCD");
            this.apiLcdCssGrayText = new NodeDock(scene.asParent(), "LCDGray");
            this.apiLcdCssLcdText = new NodeDock(scene.asParent(), "LCDLCD");
        }
    }

    @Test
    public void grayGrayTest() {
        if (LcdUtils.isApplicablePlatform()) {
            boolean mustBeLcd = false;
            assertTrue("LCD text work (css: gray, api: gray)",
                    testLCD(apiGrayCssGrayText.wrap().getScreenImage(), mustBeLcd));
        }
    }

    @Test
    public void grayLCDTest() {
        if (LcdUtils.isApplicablePlatform()) {
            boolean mustBeLcd = true;
            assertTrue("LCD text doesn't work (css: gray, api: lcd)",
                    testLCD(apiGrayCssLcdText.wrap().getScreenImage(), mustBeLcd));
        }
    }

    @Test
    public void lcdGrayTest() {
        if (LcdUtils.isApplicablePlatform()) {
            boolean mustBeLcd = false;
            assertTrue("LCD text work (css: lcd, api: gray)",
                    testLCD(apiLcdCssGrayText.wrap().getScreenImage(), mustBeLcd));
        }
    }

    @Test
    public void lcdLCDTest() {
        if (LcdUtils.isApplicablePlatform()) {
            boolean mustBeLcd = true;
            assertTrue("LCD text doesn't work (css: lcd, api: lcd)",
                    testLCD(apiLcdCssLcdText.wrap().getScreenImage(), mustBeLcd));
        }
    }

    private boolean testLCD(Image image, boolean lcd) {
        PixelsCalc calc = new PixelsCalc();
        
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
