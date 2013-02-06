/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

/**
 *
 * @author Alexander Petrov
 */
public abstract class WriteArrayARGBImageTestBase extends ReadImageTestBase{

    @Override
    protected Image getImage() {
        WritableImage image = new WritableImage(256, 256); 
        
        int[] imageInArray = new int[256 * 256];

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int color = i + j;
                
                imageInArray[i + j * 256] = getColorComponentProvider().getARGBColor(
                        ((color > 255) ? 255 : color));
                        
            }
        }
        
        image.getPixelWriter().setPixels(0, 0, 256, 256, PixelFormat.getIntArgbInstance(), imageInArray, 0, 256);
               
        return image;
    }
}
