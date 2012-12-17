/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexander Petrov
 */
public abstract class WriteArrayBGRABytesImageTestBase extends ReadImageTestBase{

    @Override
    protected Image getImage() {
        WritableImage image = new WritableImage(256, 256); 
        
        byte[] imageInArray = new byte[256 * 256 * 4];

        //Create byte image array
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int color = i + j;
                
                int intValue = getColorComponentProvider().getARGBColor(
                        ((color > 255) ? 255 : color));
                for (int k = 0; k < 4; k++) {
                    imageInArray[i * 4 + j * 256 * 4 + k] = 
                           (byte)((intValue >>> (k * 8))  & (0x000000FF));
                }        
            }
        }
        
        //Copy bytes to image
        image.getPixelWriter().setPixels(0, 0, 256, 256, PixelFormat.getByteBgraInstance(), imageInArray, 0, 256 * 4);
        
        return image;
    }
}
