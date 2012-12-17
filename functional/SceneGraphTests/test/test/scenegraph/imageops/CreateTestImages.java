/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexander Petrov
 */
public class CreateTestImages {
    public static void main(String[] args) throws IOException {
        BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB_PRE);
        
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int color = i + j;
                
                image.setRGB(i, j, 0xFF000000 + ((color > 255) ? 255 : color));
            }
        }
        
        ImageIO.write(image, "png", new File("blue.png"));
        
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB_PRE);
        
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int color = i + j;
                
                image.setRGB(i, j, 0xFF000000 + (((color > 255) ? 255 : color) << 8)) ;
            }
        }
        
        ImageIO.write(image, "png", new File("green.png"));
        
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB_PRE);
        
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int color = i + j;
                
             
                image.setRGB(i, j, 0xFF000000 + (((color > 255) ? 255 : color) << 16)) ;
            }
        }
        
        ImageIO.write(image, "png", new File("red.png"));
        
        image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB_PRE);
        
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int color = i + j;

                
                image.setRGB(i, j, 0x00000000 + ((color > 255) ? 255 : color) << 24) ;
            }
        }
        
        ImageIO.write(image, "png", new File("opacity.png"));
    }
}
