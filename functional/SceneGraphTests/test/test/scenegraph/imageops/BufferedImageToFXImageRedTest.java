/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import java.io.IOException;
import java.net.URL;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import junit.framework.Assert;

/**
 *
 * @author Alexander Petrov
 */
public class BufferedImageToFXImageRedTest extends ReadImageRedTest{

    @Override
    protected Image getImage() {
        WritableImage testingImage = new WritableImage(256, 256);
        try {
            SwingFXUtils.toFXImage(
                    ImageIO.read(new URL(getColorComponentProvider().getGoldImagePath())),
                    testingImage);
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
        
        return testingImage;
    }
    
    
}
