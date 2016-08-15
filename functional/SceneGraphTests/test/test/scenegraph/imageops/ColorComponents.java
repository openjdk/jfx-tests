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
package test.scenegraph.imageops;

import java.io.InputStream;
import javafx.scene.paint.Color;

/**
 *
 * @author Alexander Petrov
 */
public enum ColorComponents implements ColorComponentProvider{

    Red(new ColorComponentProvider() {

        public double getComponent(Color color) {
            return color.getRed();
        }

        public double getComponent(int sRGBColor) {
            int colorComponent = (sRGBColor & 0xFF0000) >>> 16;
            return (double) colorComponent / 255;
        }

        public Color getColor(double componentValue) {
            return Color.color(componentValue, 0.0, 0.0, 1.0);
        }

        public int getARGBColor(int componentValue) {
            return 0xFF000000 + (((componentValue > 255) ? 255 : componentValue) << 16);
        }

        public InputStream getGoldImageInputStream() {
            return getClass().getResourceAsStream("red.png");
        }
    }),
    Green(new ColorComponentProvider() {

        public double getComponent(Color color) {
            return color.getGreen();
        }

        public double getComponent(int sRGBColor) {
            int colorComponent = (sRGBColor & 0x00FF00) >>> 8;
            return (double) colorComponent / 255;
        }

        public Color getColor(double componentValue) {
            return Color.color(0.0, componentValue, 0.0, 1.0);
        }

        public int getARGBColor(int componentValue) {
            return 0xFF000000 + (((componentValue > 255) ? 255 : componentValue) << 8);
        }

        public InputStream getGoldImageInputStream() {
            return getClass().getResourceAsStream("green.png");
        }
    }),
    Blue(new ColorComponentProvider() {

        public double getComponent(Color color) {
            return color.getBlue();
        }

        public double getComponent(int sRGBColor) {

            int colorComponent = sRGBColor & 0x0000FF;
            return (double) colorComponent / 255;
        }

        public Color getColor(double componentValue) {
            return Color.color(0.0, 0.0, componentValue, 1.0);
        }

        public int getARGBColor(int componentValue) {
            return 0xFF000000 + ((componentValue > 255) ? 255 : componentValue);
        }

        public InputStream getGoldImageInputStream() {
            return getClass().getResourceAsStream("blue.png");
        }
    }),
    Opacity(new ColorComponentProvider() {

        public double getComponent(Color color) {
            return color.getOpacity();
        }

        public double getComponent(int sRGBColor) {
            int colorComponent = (sRGBColor & 0xFF000000) >>> 24;
            return (double) colorComponent / 255;
        }

        public Color getColor(double componentValue) {
            return Color.color(0.0, 0.0, 0.0, componentValue);
        }

        public int getARGBColor(int componentValue) {
            return 0x00000000 + (((componentValue > 255) ? 255 : componentValue) << 24);
        }

        public InputStream getGoldImageInputStream() {
            return getClass().getResourceAsStream("opacity.png");
        }
    });


    private ColorComponentProvider provider;

    private ColorComponents(ColorComponentProvider provider){
        this.provider = provider;
    }

    public double getComponent(Color color) {
        return provider.getComponent(color);
    }

    public Color getColor(double componentValue) {
        return provider.getColor(componentValue);
    }

    public double getComponent(int sRGBColor) {
        return provider.getComponent(sRGBColor);
    }

    public int getARGBColor(int componentValue) {
        return provider.getARGBColor(componentValue);
    }

    public InputStream getGoldImageInputStream() {
        return provider.getGoldImageInputStream();
    }

}
