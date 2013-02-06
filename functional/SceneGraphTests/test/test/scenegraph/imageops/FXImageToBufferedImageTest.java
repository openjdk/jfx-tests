/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.imageops;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Alexander Petrov
 */
public class FXImageToBufferedImageTest {

    @Test
    public void testRed() {
        BufferedImage testImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Red.getGoldImagePath()), testImage);

        testImage(testImage, ColorComponents.Red);
    }

    @Test
    public void testGreen() {
        BufferedImage testImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Green.getGoldImagePath()), testImage);

        testImage(testImage, ColorComponents.Green);
    }

    @Test
    public void testBlue() {
        BufferedImage testImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Blue.getGoldImagePath()), testImage);

        testImage(testImage, ColorComponents.Blue);
    }

    @Test
    public void testOpacity() {
        BufferedImage testImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Opacity.getGoldImagePath()), testImage);

        testImage(testImage, ColorComponents.Opacity);
    }

    private void testImage(BufferedImage image, ColorComponentProvider provider) {

        int samePixelsCounter = 0;
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                if (provider.getComponent(image.getRGB(i, j))
                        == provider.getComponent(image.getRGB(i + 1, j))) {
                    samePixelsCounter++;
                }

                ReadImageTestBase.testGradientImage(
                        provider.getComponent(image.getRGB(i, j)),
                        provider.getComponent(image.getRGB(i - 1, j)),
                        provider.getComponent(image.getRGB(i + 1, j)),
                        provider.getComponent(image.getRGB(i, j - 1)),
                        provider.getComponent(image.getRGB(i, j + 1)));
            }
        }

        assertTrue("More than half of the pixels the same color.",
                samePixelsCounter < 256 * 256 / 2);
    }
}
