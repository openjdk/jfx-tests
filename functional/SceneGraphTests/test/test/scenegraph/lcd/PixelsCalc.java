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

import java.awt.image.BufferedImage;
import org.jemmy.image.AWTImage;
import org.jemmy.image.GlassImage;
import org.jemmy.image.Image;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.Raster.Component;
import static java.util.Arrays.asList;
import org.jemmy.Dimension;

/**
 * Calculate color, gray, green and white pixels in image. For get result use
 * get methods.
 *
 * @author Alexander Petrov
 */
public class PixelsCalc {
    public static final int COLOR_PIXEL_THRESHOLD = 25;
    public static final int GREEN_PIXEL_THRESHOLD = 20;
    private static final int WHITE_PIXEL_THRESHOLD = 200;
    private int colorPixelsCount = 0;
    private int whitePixelsCount = 0;
    private int grayPixelsCount = 0;
    private int greenPixelsCount = 0;

    /**
     * Reset result. Use before new calculation.
     */
    public void reset() {
        colorPixelsCount = 0;
        whitePixelsCount = 0;
        grayPixelsCount = 0;
        greenPixelsCount = 0;
    }

    public void calculate(Image image, boolean reset) {
        if (image instanceof GlassImage) {
            if (reset) {
                this.reset();
            }
            calculateGlass(image);
        } else if (image instanceof AWTImage) {
            if (reset) {
                this.reset();
            }
            calculateAWT(image);
        }
    }

    /**
     * Calculate
     * @param img input image
     */
    public void calculateAWT(Image image){
        AWTImage awtImage = (AWTImage) image;
        BufferedImage img = awtImage.getTheImage();
        for(int i = 0; i < img.getWidth(); i++){
            for(int j = 0; j < img.getHeight(); j++){
                int color = img.getRGB(i, j);
                int red = (0xFF0000 & color) >> 16;
                int green = (0x00FF00 & color) >> 8;
                int blue = 0x0000FF & color;
                checkColorComponents(red, green, blue);
            }
        }
    }

    public void calculateGlass(Image img) {
        GlassImage image = (GlassImage) img;

        Component[] supportedComponents = image.getSupported();

        int idxRed = asList(supportedComponents).indexOf(Raster.Component.RED);
        int idxGreen = asList(supportedComponents).indexOf(Raster.Component.GREEN);
        int idxBlue = asList(supportedComponents).indexOf(Raster.Component.BLUE);

        int compCount = supportedComponents.length;
        double[] colors = new double[compCount];

        Dimension d = image.getSize();

        for (int i = 0; i < d.height; i++) {
            for (int j = 0; j < d.width; j++) {

                image.getColors(j, i, colors);

                double red = colors[idxRed];
                double green = colors[idxGreen];
                double blue = colors[idxBlue];

                checkColorComponents((int)(red * 0xFF), (int)(green * 0xFF), (int)(blue * 0xFF));
            }
        }
    }

    /**
     * Get color pixel count
     */
    public int getColorPixelCount() {
        return colorPixelsCount;
    }

    /**
     * Get gray pixel count
     */
    public int getGrayPixelCount() {
        return grayPixelsCount;
    }

    /**
     * Get white pixel count.
     */
    public int getWhitePixelCount() {
        return whitePixelsCount;
    }

    /**
     * Get green pixel count.
     */
    public int getGreenPixelCount() {
        return greenPixelsCount;
    }

    /**
     * This method uses empirical formula
     * to check whether pixel belongs to
     * particular group (white, gray, colored)
     *
     * @param red
     * @param green
     * @param blue
     */
    private void checkColorComponents(int red, int green, int blue) {
        if((Math.abs(red - green) + Math.abs(red - blue) + Math.abs(green - blue)) > COLOR_PIXEL_THRESHOLD){
            colorPixelsCount++;
            if((red < GREEN_PIXEL_THRESHOLD) && (blue < GREEN_PIXEL_THRESHOLD)){
                greenPixelsCount ++;
            }
        } else if(red > WHITE_PIXEL_THRESHOLD){
            whitePixelsCount++;
        } else {
            grayPixelsCount++;
        }
    }
}