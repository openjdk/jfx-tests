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
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Red.getGoldImageInputStream()), testImage);

        testImage(testImage, ColorComponents.Red);
    }

    @Test
    public void testGreen() {
        BufferedImage testImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Green.getGoldImageInputStream()), testImage);

        testImage(testImage, ColorComponents.Green);
    }

    @Test
    public void testBlue() {
        BufferedImage testImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Blue.getGoldImageInputStream()), testImage);

        testImage(testImage, ColorComponents.Blue);
    }

    @Test
    public void testOpacity() {
        BufferedImage testImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(new Image(ColorComponents.Opacity.getGoldImageInputStream()), testImage);

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
