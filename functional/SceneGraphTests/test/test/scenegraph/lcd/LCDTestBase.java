/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
