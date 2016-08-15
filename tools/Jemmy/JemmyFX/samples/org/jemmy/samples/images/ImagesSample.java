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
 * questions.
 */
package org.jemmy.samples.images;

import javafx.scene.control.Button;
import org.jemmy.TimeoutExpiredException;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.fx.control.ToggleButtonDock;
import org.jemmy.image.AWTImage;
import org.jemmy.image.BufferedImageComparator;
import org.jemmy.image.GlassImage;
import org.jemmy.image.GlassPixelImageComparator;
import org.jemmy.image.Image;
import org.jemmy.image.pixel.AverageDistanceComparator;
import org.jemmy.image.pixel.MaxDistanceComparator;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import org.jemmy.image.pixel.PixelImageComparator;
import org.jemmy.image.pixel.RasterComparator;
import static org.jemmy.resources.StringComparePolicy.*;
import org.jemmy.samples.SampleBase;
import org.jemmy.samples.buttons.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * How to use images to check state of a Node.
 *
 * @author shura
 */
public class ImagesSample extends SampleBase {

    private static SceneDock scene;
    public static final String SCENE_PNG = "scene.png";
    public static final String RADIO_PNG = "radio.golden.png";
    public static final String TOGGLE_PNG = "toggle.golden.png";
    public static final String RADIO_RES_PNG = "radio.res.png";
    public static final String TOGGLE_RES_PNG = "toggle.res.png";
    public static final String RADIO_DIFF_PNG = "radio.diff.png";
    public static final String TOGGLE_DIFF_PNG = "toggle.diff.png";

    @BeforeClass
    public static void startApp() throws InterruptedException {
        startApp(ButtonsApp.class);
        scene = new SceneDock();
    }
    private ToggleButtonDock radio;
    private ToggleButtonDock toggle;
    private LabeledDock button;
    private String WAIT_BEFORE_DIFF = "wait.before.diff";
    final private Timeout BEFORE_DIFF_TIMEOUT = new Timeout(WAIT_BEFORE_DIFF, 500); //wait for redraw

    /**
     * Let's find a radio and a toggle, save their images and use a reference
     * images.
     */
    @Before
    public void lookup() throws InterruptedException {
        radio = new ToggleButtonDock(scene.asParent(), "radio2", EXACT);
        toggle = new ToggleButtonDock(scene.asParent(), "toggle2", EXACT);
        //need something to get focus away
        button = new LabeledDock(scene.asParent(), Button.class);
        button.mouse().click();
        //save images as golden. Of course, in real testing you would have
        //to store reference images somehwre with tests
        Thread.sleep(1000);
        radio.wrap().getScreenImage().save(RADIO_PNG);
        toggle.wrap().getScreenImage().save(TOGGLE_PNG);
        scene.wrap().getScreenImage().save(SCENE_PNG);
    }

    /**
     * Compare using default comparator.
     */
    @Test
    public void defaultComparator() {
        //let's use comparison logic, which is
        //comparing all pixels for equality.
        //this is, also, a default comparator
        Environment.getEnvironment().setProperty(RasterComparator.class, new PixelEqualityRasterComparator(0));
        //since the UI is in original state, images should be the same
        button.mouse().click();
        radio.waitImage(RADIO_PNG, "default.0." + RADIO_RES_PNG, "default.0." + RADIO_DIFF_PNG);
        toggle.waitImage(TOGGLE_PNG, "default.0." + TOGGLE_RES_PNG, "default.0." + TOGGLE_DIFF_PNG);
        //let's click on both and check that the images do not match
        radio.mouse().click();
        try {
            radio.waitImage(RADIO_PNG, "default.1." + RADIO_RES_PNG, "default.1." + RADIO_DIFF_PNG);
            throw new IllegalStateException("images are expected to be different!");
        } catch (TimeoutExpiredException e) {
        }
        toggle.mouse().click();
        BEFORE_DIFF_TIMEOUT.sleep();
        try {
            toggle.waitImage(TOGGLE_PNG, "default.1." + TOGGLE_RES_PNG, "default.1." + TOGGLE_DIFF_PNG);
            throw new IllegalStateException("images are expected to be different!");
        } catch (TimeoutExpiredException e) {
        }
        //after running this you could go and check the diff images

        //revert state - unselect selected
        toggle.mouse().click();
        //steal focus
        button.mouse().click();
    }

    /**
     * Check pressed radio with a relaxed comparator.
     */
    @Test
    public void radioWithRelaxedComparator() {
        //let's use a relaxed comparison logic, which is
        //comparing only a half of pixels for equality.
        Environment.getEnvironment().setProperty(RasterComparator.class, new PixelEqualityRasterComparator(.5));
        //let's click on the radio and compare
        //should be ok as less than a half of images is off
        radio.mouse().click();
        button.mouse().click();
        radio.waitImage(RADIO_PNG, "relaxed." + RADIO_RES_PNG, "relaxed." + RADIO_DIFF_PNG);

        //revert state - unselect selected
        radio.mouse().click();
        //get the focus off
        button.mouse().click();
    }

    /**
     * Check pressed toggle with a relaxed comparator.
     */
    @Test
    public void toggleWithRelaxedComparator() {
        //same comparator
        Environment.getEnvironment().setProperty(RasterComparator.class, new PixelEqualityRasterComparator(.5));
        //let's click on the toggle and compare
        //should not be ok as more than a half of images is off
        toggle.mouse().click();
        button.mouse().click();
        BEFORE_DIFF_TIMEOUT.sleep();
        try {
            toggle.waitImage(TOGGLE_PNG, "relaxed." + TOGGLE_RES_PNG, "relaxed." + TOGGLE_DIFF_PNG);
            throw new IllegalStateException("images are expected to be different!");
        } catch (TimeoutExpiredException e) {
        }

        //revert state - unselect selected
        toggle.mouse().click();
        //get the focus off
        button.mouse().click();
    }

    /**
     * Check pressed radio with a relaxed comparator.
     */
    @Test
    public void useColorDistanceComparator() {
        //there is another algorythm which could compare a "color distance"
        //between two pixels (r1,g1,b1) and (r2,g2,b2):
        //sqrt((r2-r1)^2+(g2-g1)^2+(b2-b1)^2)
        //the treshold is between 0 and Math.sqrt(3)
        //first use average (over all pixels) distance
        Environment.getEnvironment().setProperty(RasterComparator.class, new AverageDistanceComparator(.3));
        //let's click on the radio and compare
        //should be ok as images are close
        radio.mouse().click();
        button.mouse().click();
        radio.waitImage(RADIO_PNG, "distance.0." + RADIO_RES_PNG, "distance.0." + RADIO_DIFF_PNG);

        //now use maximum (over all pixels) distance
        Environment.getEnvironment().setProperty(RasterComparator.class, new MaxDistanceComparator(.3));
        //let's click on the radio and compare
        //should not be as some color are too far away
        radio.mouse().click();
        button.mouse().click();
        BEFORE_DIFF_TIMEOUT.sleep();
        try {
            radio.waitImage(RADIO_PNG, "distance.1." + RADIO_RES_PNG, "distance.1." + RADIO_DIFF_PNG);
            throw new IllegalStateException("images are expected to be different!");
        } catch (TimeoutExpiredException e) {
        }

        //revert state - unselect selected
        radio.mouse().click();
        //get the focus off
        button.mouse().click();
    }

    /**
     * Direct image comparison
     */
    @Test
    public void compareDirectly() {
        //load images to compare
        Image reference = Root.ROOT.getEnvironment().getImageLoader().load(RADIO_PNG);
        radio.mouse().click();
        button.mouse().click();
        Image selected = radio.wrap().getScreenImage();
        //org.jemmy.image.pixel.RasterComparator is a platform independent abstraction
        RasterComparator rasterComparator = new PixelEqualityRasterComparator(.6);
        //org.jemmy.image.ImageComparator implementations could be platform dependent
        PixelImageComparator imageComparator;
        if (reference instanceof GlassImage) {
            imageComparator = new GlassPixelImageComparator(rasterComparator);
        } else if (reference instanceof AWTImage) {
            imageComparator = new BufferedImageComparator(rasterComparator);
        } else {
            //something unknown at the time of writing the sample
            //hopefully something good
            return;
        }
        //an image is equal to itself
        assertNull(imageComparator.compare(reference, reference));
        //an image is not equal to a different image
        assertNull(imageComparator.compare(reference, selected));
        //revert state - unselect selected
        radio.mouse().click();
        //get the focus off
        button.mouse().click();
    }
}
