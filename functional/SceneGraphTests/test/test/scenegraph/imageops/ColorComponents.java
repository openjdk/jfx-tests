/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

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

        public String getGoldImagePath() {
            return getClass().getResource("red.png").toExternalForm();
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

        public String getGoldImagePath() {
            return getClass().getResource("green.png").toExternalForm();
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

        public String getGoldImagePath() {
            return getClass().getResource("blue.png").toExternalForm();
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

        public String getGoldImagePath() {
            return getClass().getResource("opacity.png").toExternalForm();
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

    public String getGoldImagePath() {
        return provider.getGoldImagePath();
    }
    
}
