/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
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
package javafx.commons;

import client.test.ScreenshotCheck;
import javafx.scene.Node;
import org.jemmy.fx.ByID;
import org.jemmy.image.Image;
import org.jemmy.interfaces.Selectable;
import org.junit.Test;
import static javafx.commons.ControlChooserApp.*;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.env.Environment;
import org.jemmy.env.Timeout;
import org.jemmy.image.AWTImage;
import org.jemmy.image.GlassImage;
import org.jemmy.image.ImageComparator;
import org.jemmy.image.pixel.MaxDistanceComparator;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.RasterComparator;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Assert;
import test.javaclient.shared.screenshots.GoldenImageManager;
/**
 *
 * @author Dmitry Zinkevich <dmitry.zinkevich@oracle.com>
 */
@RunWith(FilteredTestRunner.class)
public class ControlsTest extends ControlsBase {

    /**
     * Checks opacity of controls by comparing two screenshots:
     * first - control having 40% opacity,
     * second - initial control screenshot with applied 40% opacity
     */
    @ScreenshotCheck
    //Test//Switched off.
    public void opacityTest() throws InterruptedException, Throwable {

        final MaxDistanceComparator maxDistanceComparator = new MaxDistanceComparator(0.0);
        final double THRESHOLD = 0.03 * Math.sqrt(3);

        /*
         * Change comparator because it is called by
         */
        final RasterComparator rasterComparator = Environment.getEnvironment().getProperty(RasterComparator.class);
        Environment.getEnvironment().setProperty(RasterComparator.class, new MaxDistanceComparator(THRESHOLD));

        String nodeName;

        for (Object node : nodeChooser.as(Selectable.class).getStates()) {
            nodeChooser.as(Selectable.class).selector().select(node);

            nodeName = node.toString();
            System.out.println("node = " + nodeName);

            for (SettingType settingType : SettingType.values()) {
                System.out.println("settingType = " + settingType);

                checkSimpleListenerValue(TestedProperties.focused, "false");

                testedControl = parent.lookup(Node.class, new ByID<Node>(TESTED_CONTROL_ID)).wrap();

                final Image imgInitial = testedControl.getScreenImage();
                final Image imgOpacity40 = testedControl.getScreenImage();

                Assert.assertTrue("Two initial state screenshots have differences",
                        maxDistanceComparator.compare((Raster) imgInitial, (Raster) imgOpacity40));

                if (imgOpacity40 instanceof AWTImage) {
                    setOpacity(0.4, (AWTImage) imgOpacity40);
                } else if (imgOpacity40 instanceof GlassImage) {
                    setOpacity(0.4, (GlassImage) imgOpacity40);
                } else {
                    throw new Exception("Unknown image format");
                }

                /*
                 * Decrease control opacity by x percent
                 * and check that the image distance between opaque and translucent states is less or equal x
                 */
                final double OPACITY = 0.4;
                setPropertyBySlider(settingType, TestedProperties.opacity, OPACITY);

                maxDistanceComparator.setThreshold(THRESHOLD);
                try {
                    new Waiter(new Timeout("", 2000)).ensureState(new State<Boolean>() {
                        public Boolean reached() {
                            Image imgTranslucent = testedControl.getScreenImage();
                            Boolean isDifference = !maxDistanceComparator.compare((Raster) imgOpacity40, (Raster) imgTranslucent);
                            return isDifference ? null : Boolean.valueOf(true);
                        }
                    });
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    Image imgTranslucent = testedControl.getScreenImage();

                    String screenshotPath = GoldenImageManager.getScreenshotPath(String.format("%s_img_opacity40", nodeName));
                    imgOpacity40.save(screenshotPath);

                    screenshotPath = GoldenImageManager.getScreenshotPath(String.format("%s_control_opacity40", nodeName));
                    imgTranslucent.save(screenshotPath);

                    Image diff = Environment.getEnvironment().getProperty(ImageComparator.class).compare(imgOpacity40, imgTranslucent);

                    Assert.assertTrue(diff != null);

                    screenshotPath = GoldenImageManager.getScreenshotPath(String.format("%s_diff", nodeName));
                    diff.save(screenshotPath);
                }
                setPropertyBySlider(settingType, TestedProperties.opacity, 1);
            }
            clearCache();
        }

        /*
         * Restore comparator
         */
        if (rasterComparator != null) {
            Environment.getEnvironment().setProperty(RasterComparator.class, rasterComparator);
        }
    }
}