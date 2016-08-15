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
