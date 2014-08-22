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

package test.javaclient.shared;

import com.sun.glass.ui.Pixels;
import java.io.File;
import java.util.Arrays;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jemmy.action.AbstractExecutor;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.env.TestOut;
import org.jemmy.fx.Root;
import org.jemmy.image.*;
import org.jemmy.image.pixel.AverageDistanceComparator;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.RasterComparator;
import org.jemmy.input.AWTRobotInputFactory;
import org.jemmy.input.RobotDriver;
import org.jemmy.input.glass.GlassInputFactory;
import org.junit.Assert;

/**
 *
 * @author Stanislav Smirnov <stanislav.smirnov@oracle.com>
 */
public abstract class JemmyUtils {

    public static float comparatorDistance = 0.006f;

    public static boolean usingGlassRobot(){
        return "glass".equals(Environment.getEnvironment().getProperty("javafx.robot"))
            || "true".equals(System.getProperty("glassRobot"));
    }

    public static void initJemmy(){
        setJemmyComparatorByDistance(comparatorDistance);

        if (usingGlassRobot()) {
            Root.useGlassRobot(Root.ROOT.getEnvironment());
            Root.useGlassRobot(Environment.getEnvironment());
        } else  {
            Root.useAWTRobot(Root.ROOT.getEnvironment());
            Root.useAWTRobot(Environment.getEnvironment());
        }

        Root.ROOT.getEnvironment().setOutput(AbstractExecutor.NON_QUEUE_ACTION_OUTPUT, TestOut.getNullOutput());
        Root.ROOT.getEnvironment().setTimeout(Wrap.WAIT_STATE_TIMEOUT, 5000l);
    }

    public static void setJemmyComparatorByDistance(float comparatorDistance) {
        System.out.println("Using image comparator distance: " + comparatorDistance);
        if (comparatorDistance != 0f) {
            Root.ROOT.getEnvironment().setProperty(RasterComparator.class, new AverageDistanceComparator(comparatorDistance));
            Environment.getEnvironment().setProperty(RasterComparator.class, new AverageDistanceComparator(comparatorDistance));
        }
        else {
            Root.ROOT.getEnvironment().setProperty(RasterComparator.class, new PixelEqualityRasterComparator(0f));
            Environment.getEnvironment().setProperty(RasterComparator.class, new PixelEqualityRasterComparator(0f));
        }
    }

    public static void setJemmyRoughComparator(double comparatorDistance) {
        Environment.getEnvironment().setProperty(RasterComparator.class, new PixelEqualityRasterComparator(comparatorDistance));
    }

    public static void setJemmyComparator(ImageComparator comparator) {
        if(usingGlassRobot()){
            //TODO: set Glass comparator
        }
        else {
            AWTImage.setComparator(comparator);
        }
    }

    public static int getMouseSmoothness(){
        return GlassInputFactory.getMouseSmoothness();
    }

    public static void setMouseSmoothness(int value){
        GlassInputFactory.setMouseSmoothness(value);
        AWTRobotInputFactory.setMouseSmoothness(value);
    }

    public static void runInOtherJVM(boolean value){
        if(!usingGlassRobot()){
            AWTRobotInputFactory.runInOtherJVM(value);
        }
    }

    public static double[] getColors(Image image){
        if(usingGlassRobot()){
            GlassImage img = ((GlassImage) image);
            double[] colors = new double[img.getSupported().length];

            img.getColors(img.getImage().getWidth() / 2, img.getImage().getWidth() / 2, colors);

            return new double[]{
                colors[GlassPixelImageComparator.arrayIndexOf(img.getSupported(), Raster.Component.RED)],
                colors[GlassPixelImageComparator.arrayIndexOf(img.getSupported(), Raster.Component.GREEN)],
                colors[GlassPixelImageComparator.arrayIndexOf(img.getSupported(), Raster.Component.BLUE)]
            };
        }else{
            return SwingAWTUtils.getColors(image);
        }
    }

    public static Object getRGBColors(Image image, int x, int y){
        if(usingGlassRobot()){
            GlassImage img = ((GlassImage) image);
            double[] colors = new double[img.getSupported().length];
            img.getColors(x, y, colors);
            return colors;
        }else{
            return SwingAWTUtils.getRGBColors(image, x, y);
        }
    }

    public static void comparePopUpRGB(Image image, Rectangle rec, CheckBox chB, int smallRectSize){
        Assert.assertTrue("Internal error: Image is not of proper type AWTImage/GlassImage", usingGlassRobot() ? image instanceof GlassImage : image instanceof AWTImage);

        Object rgbOnPopupAndGreenRect = getRGBColors(image, (int)(rec.getX()) + 1, (int)(rec.getY()) + 1);
        Object rgbOnPopup = getRGBColors(image, (int)(rec.getX()) - 1, (int)(rec.getY()) + 1);
        Object rgbOutsidePopup = getRGBColors(image, (int)(rec.getX()) - smallRectSize - 1, (int)(rec.getY()));

        Assert.assertTrue("Internal Error: Popup is not showed", usingGlassRobot() ? !Arrays.equals((double[]) rgbOnPopup, (double[]) rgbOutsidePopup) : ((Integer) rgbOnPopup).intValue() != ((Integer) rgbOutsidePopup).intValue());

        if (chB.isSelected()) {
            // Transparent winow must be supported
            Assert.assertFalse("Transparency is not supported but Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW) == true",
                    usingGlassRobot() ? Arrays.equals((double[]) rgbOnPopup,(double[]) rgbOnPopupAndGreenRect) : ((Integer) rgbOnPopup).intValue() == ((Integer) rgbOnPopupAndGreenRect).intValue());
        } else {
            // Transparent winow isn't supported
            Assert.assertTrue("Transparency is supported but Platform.isSupported(ConditionalFeature.TRANSPARENT_WINDOW) == false",
                    usingGlassRobot() ? Arrays.equals((double[]) rgbOnPopup,(double[]) rgbOnPopupAndGreenRect) : ((Integer) rgbOnPopup).intValue() == ((Integer) rgbOnPopupAndGreenRect).intValue());
        }
    }

    public static void robotExit(){
        if(usingGlassRobot())
            GlassInputFactory.getRobot().destroy();
        else
            RobotDriver.exit();
    }

    public static void verifyColor(Image image, int x, int y){
        if(usingGlassRobot()){
            GlassImage sceneImage = (GlassImage)image;
            double[] colors = new double[sceneImage.getSupported().length];
            sceneImage.getColors(x, y, colors);
            double[] orig = new double[]{Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue()};
            double[] actual = new double[]{
                colors[GlassPixelImageComparator.arrayIndexOf(sceneImage.getSupported(), Raster.Component.RED)],
                colors[GlassPixelImageComparator.arrayIndexOf(sceneImage.getSupported(), Raster.Component.GREEN)],
                colors[GlassPixelImageComparator.arrayIndexOf(sceneImage.getSupported(), Raster.Component.BLUE)]
            };
            Assert.assertTrue("Pixel in the screen center shouldn't be black (actual rgb= " + actual + ")", !Arrays.equals(actual, orig));
        }else{
            SwingAWTUtils.verifyColor(image, x, y);
        }
    }

    public static Image getScreenCapture(org.jemmy.Rectangle rect){
        if(usingGlassRobot()){
            Pixels screenCapture = GlassImageCapturer.getRobot().getScreenCapture(rect.x, rect.y, rect.width, rect.height);
            return (Image) screenCapture;
        }else{
            Image screenshot = RobotDriver.createScreenCapture(rect);
            return screenshot;
        }
    }

    public static boolean isDuplicateImages(File file1, File file2) {
        if(usingGlassRobot()){
            if (file1.exists() && file2.exists()) {
                Image image1 = Root.ROOT.getEnvironment().getProperty(ImageLoader.class).load(file1.getAbsolutePath());
                Image image2 = Root.ROOT.getEnvironment().getProperty(ImageLoader.class).load(file2.getAbsolutePath());
                return image1.compareTo(image2) == null ? true : false;
            }
        } else {
            if (file1.exists() && file2.exists()) {
                AWTImage image1 = new AWTImage(PNGDecoder.decode(file1.getAbsolutePath()));
                AWTImage image2 = new AWTImage(PNGDecoder.decode(file2.getAbsolutePath()));
                return image1.compareTo(image2) == null ? true : false;
            }
        }
        return false;
    }
}
