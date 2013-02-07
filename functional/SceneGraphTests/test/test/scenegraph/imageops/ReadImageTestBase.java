/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Alexander Petrov
 */
public abstract class ReadImageTestBase {
    
    protected abstract ColorComponentProvider getColorComponentProvider();
    protected abstract Image getImage();

    @Test
    public void readSinglyColor(){
        ColorComponentProvider provider = getColorComponentProvider();
        
        Image testImage = getImage();
        PixelReader reader = testImage.getPixelReader();
        
        assertFalse("Open testing image", testImage.isError());
        
        int samePixelsCounterColor = 0;
        int samePixelsCounterArgb = 0;
        
        for (int i = 1; i < testImage.getHeight() - 1; i++) {
            for (int j = 1; j < testImage.getWidth() - 1; j++) {
                
                if(provider.getComponent(reader.getColor(i, j)) ==
                        provider.getComponent(reader.getColor(i + 1, j)))
                    samePixelsCounterColor++;
                
                if(provider.getComponent(reader.getArgb(i, j)) ==
                        provider.getComponent(reader.getArgb(i + 1, j)))
                    samePixelsCounterArgb++;
                
                testGradientImage(
                        provider.getComponent(reader.getColor(i, j)),
                        provider.getComponent(reader.getColor(i - 1 , j)),
                        provider.getComponent(reader.getColor(i + 1 , j)),
                        provider.getComponent(reader.getColor(i, j - 1)),
                        provider.getComponent(reader.getColor(i, j + 1)));
                
                testGradientImage(
                        provider.getComponent(reader.getArgb(i, j)),
                        provider.getComponent(reader.getArgb(i - 1 , j)),
                        provider.getComponent(reader.getArgb(i + 1 , j)),
                        provider.getComponent(reader.getArgb(i , j - 1)),
                        provider.getComponent(reader.getArgb(i , j + 1)));
            }
        } 
        
        assertTrue("More than half of the pixels the same color(Color).", 
                samePixelsCounterColor < 256 * 256 / 2);
        
        assertTrue("More than half of the pixels the same color(ARGB).", 
                samePixelsCounterArgb < 256 * 256 / 2);
    }
    
    @Test
    public void readBlockColor(){
        ColorComponentProvider provider = getColorComponentProvider();
        
        Image testImage = getImage();
        PixelReader reader = testImage.getPixelReader();
        
        assertFalse("Open testing image", testImage.isError());
        
        int[] buf = new int[256 * 256];
        
        reader.getPixels(0, 0, 256, 256, PixelFormat.getIntArgbInstance(), buf, 0, 256);
        
        int samePixelsCounter = 0;
        
        for (int i = 1; i < testImage.getHeight() - 1; i++) {
            for (int j = 1; j < testImage.getWidth() - 1; j++) {
                
                if(provider.getComponent(buf[i * 256 + j]) ==
                        provider.getComponent(buf[(i + 1) * 256 + j]))
                    samePixelsCounter++;
                
                testGradientImage(
                        provider.getComponent(buf[i * 256 + j]),
                        provider.getComponent(buf[i * 256 + (j - 1)]),
                        provider.getComponent(buf[(i + 1) * 256 + j]),
                        provider.getComponent(buf[i * 256 + (j - 1)]),
                        provider.getComponent(buf[i * 256 + (j + 1)]));
            }
        } 
        
        assertTrue("More than half of the pixels the same color.", 
                samePixelsCounter < 256 * 256 / 2);
    }
    
    
    public static void testGradientImage(double center, double left, 
            double right, double upper, double lower){
        assertTrue(center <= right);
        assertTrue(center <= lower);
        assertTrue(left <= center);
        assertTrue(upper <= center);
    }
}
