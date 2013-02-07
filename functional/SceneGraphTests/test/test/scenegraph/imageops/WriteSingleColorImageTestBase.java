/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * @author Alexander Petrov
 */
public abstract class WriteSingleColorImageTestBase extends ReadImageTestBase{

    
    @Override
    protected Image getImage() {
        WritableImage image = new WritableImage(256, 256);
        
        PixelWriter pixelWriter = image.getPixelWriter();
                
        
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                double color = i + j;
                pixelWriter.setColor(i, j, 
                        getColorComponentProvider().getColor(
                        ((color > 255) ? 255.0 : color) / 255));
                        
            }
        }
        
        return image;
    }
}
