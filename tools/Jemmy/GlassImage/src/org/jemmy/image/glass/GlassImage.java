/*
 * Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.image.glass;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.jemmy.Point;
import org.jemmy.image.Image;
import org.jemmy.image.ImageComparator;
import org.jemmy.image.ImageStore;
import org.jemmy.image.pixel.PNGFileImageStore;
import com.sun.glass.ui.Application;
import com.sun.glass.ui.Pixels;

import java.util.concurrent.atomic.AtomicReference;
import org.jemmy.Dimension;
import org.jemmy.JemmyException;
import org.jemmy.env.Environment;
import org.jemmy.image.pixel.PixelImageComparator;
import org.jemmy.image.pixel.WriteableRaster;

import static java.lang.Math.min;

public class GlassImage implements Image, WriteableRaster {

    static {
        Environment.getEnvironment().setPropertyIfNotSet(ImageComparator.class,
                new GlassPixelImageComparator(Environment.getEnvironment()));
        Environment.getEnvironment().setPropertyIfNotSet(ImageStore.class, new PNGFileImageStore());
        try {
            Class.forName(PixelImageComparator.class.getName());
        } catch(ClassNotFoundException e) {}
    }

    private final WritableImage image;
    private final PixelReader reader;
    private final PixelWriter writer;
    private final Component[] supported;
    private final Dimension size;
    private boolean ignoreAlpha = true;
    private final Environment env;

    public GlassImage(Environment environment, WritableImage capture) {
        this.env = environment;
        this.image = capture;
        reader = capture.getPixelReader();
        writer = capture.getPixelWriter();
        supported = new Component[]{Component.RED, Component.GREEN, Component.BLUE, Component.ALPHA};
        size = new Dimension(capture.getWidth(), capture.getHeight());
    }


//    public GlassImage(Environment env, Pixels data) {
//        this.image = data;
//        switch (Pixels.getNativeFormat()) {
//            case Pixels.Format.BYTE_BGRA_PRE:
//                supported = new Component[]{Component.BLUE, Component.GREEN, Component.RED, Component.ALPHA};
//                break;
//            case Pixels.Format.BYTE_ARGB:
//                supported = new Component[]{Component.ALPHA, Component.RED, Component.GREEN, Component.BLUE};
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown image format: " + Pixels.getNativeFormat());
//        }
//        bytesPerPixel = data.getBytesPerComponent(); //yeah, well ...
//        bytesPerComponent = bytesPerPixel / supported.length;
//        maxColorComponent = Math.pow(2, bytesPerComponent * 8) - 1;
//        this.data = getInitialData();
//        size = getInitialSize();
//        this.env = env;
//    }

//    GlassImage(GlassImage orig) {
//        this(orig, orig.size);
//    }

    GlassImage(GlassImage orig, Point start, Dimension size) {
        this(orig.env, new WritableImage(size.width, size.height));
        double[] colors = new double[supported.length];
        for (int i = 0; i < min(start.x + size.width, orig.getSize().width); i++) {
            for (int j = 0; j < min(start.y + size.height, orig.size.height); j++) {
                getColors(i + start.x, j + start.y, colors);
                setColors(i, j, colors);
            }
        }
    }

//    GlassImage(int width, int height, int bytesPerPixel, Component... comps) {
//        this.image = Application.GetApplication().createPixels(width, height,
//                ByteBuffer.allocate(width * height * bytesPerPixel));
//        supported = comps;
//        this.bytesPerPixel = bytesPerPixel;
//        bytesPerComponent = bytesPerPixel / supported.length;
//        maxColorComponent = Math.pow(2, bytesPerComponent * 8) - 1;
//        data = getInitialData();
//        size = getInitialSize();
//        env = Environment.getEnvironment();
//    }

    GlassImage(Environment env, Dimension size) {
        this(env, new WritableImage(size.width, size.height));
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    public PixelReader getReader() {
        return reader;
    }

    public WritableImage getImage() {
        return image;
    }

    private Dimension getInitialSize() {
        final AtomicReference<Dimension> sizeRef = new AtomicReference<Dimension>();
        Application.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                sizeRef.set(new Dimension(image.getWidth(), image.getHeight()));
            }
        });
        return sizeRef.get();
    }

    @Override
    public void setColors(int x, int y, double[] colors) {
        var color = new Color(colors[0], colors[1], colors[2], colors[3]);
        writer.setColor(x, y, color);
    }

    @Override
    public void getColors(int x, int y, double[] colors) {
        var color = reader.getColor(x, y);
        colors[0] = color.getRed();
        colors[1] = color.getGreen();
        colors[2] = color.getBlue();
        colors[3] = color.getOpacity();
    }

    @Override
    public Image compareTo(Image image) {
        return env.getProperty(ImageComparator.class).compare(image, this);
    }

    @Override
    public void save(String string) {
        try {
            env.getProperty(ImageStore.class).save(this, string);
        } catch (Exception ex) {
            throw new JemmyException("Unable to save image", ex);
        }
    }

    @Override
    public Component[] getSupported() {
        return supported;
    }
}
