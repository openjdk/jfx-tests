/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jemmy.image;

import org.jemmy.image.pixel.PNGFileImageStore;
import com.sun.glass.ui.Application;
import com.sun.glass.ui.Pixels;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;
import org.jemmy.Dimension;
import org.jemmy.JemmyException;
import org.jemmy.env.Environment;
import org.jemmy.image.pixel.PixelImageComparator;
import org.jemmy.image.pixel.WriteableRaster;

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
 * questions.
 */
public class GlassImage implements Image, WriteableRaster {

    static {
        Environment.getEnvironment().setPropertyIfNotSet(ImageComparator.class,
                new GlassPixelImageComparator(Environment.getEnvironment()));
        Environment.getEnvironment().setPropertyIfNotSet(ImageStore.class, new PNGFileImageStore());
        try {
            Class.forName(PixelImageComparator.class.getName());
        } catch(ClassNotFoundException e) {}
    }

    private final Pixels image;
    private final ByteBuffer data;
    private final Component[] supported;
    private int bytesPerPixel;
    private int bytesPerComponent;
    private double maxColorComponent;
    private final Dimension size;
    private boolean ignoreAlpha = true;
    private final Environment env;

    public GlassImage(Environment env, Pixels data) {
        this.image = data;
        switch (Pixels.getNativeFormat()) {
            case Pixels.Format.BYTE_BGRA_PRE:
                supported = new Component[]{Component.BLUE, Component.GREEN, Component.RED, Component.ALPHA};
                break;
            case Pixels.Format.BYTE_ARGB:
                supported = new Component[]{Component.ALPHA, Component.RED, Component.GREEN, Component.BLUE};
                break;
            default:
                throw new IllegalArgumentException("Unknown image format: " + Pixels.getNativeFormat());
        }
        bytesPerPixel = data.getBytesPerComponent(); //yeah, well ...
        bytesPerComponent = bytesPerPixel / supported.length;
        maxColorComponent = Math.pow(2, bytesPerComponent * 8) - 1;
        this.data = getInitialData();
        size = getInitialSize();
        this.env = env;
    }

    GlassImage(GlassImage orig) {
        this(orig, orig.size);
    }

    GlassImage(GlassImage orig, Dimension size) {
        this.image = Application.GetApplication().createPixels(size.width, size.height,
                ByteBuffer.allocate(size.width * size.height * 4)); //same logic as in Pixels
        supported = orig.supported;
        bytesPerPixel = orig.bytesPerPixel;
        bytesPerComponent = orig.bytesPerComponent;
        maxColorComponent = orig.maxColorComponent;
        data = getInitialData();
        this.size = size;
        env = Environment.getEnvironment();
    }

    GlassImage(int width, int height, int bytesPerPixel, Component... comps) {
        this.image = Application.GetApplication().createPixels(width, height,
                ByteBuffer.allocate(width * height * bytesPerPixel));
        supported = comps;
        this.bytesPerPixel = bytesPerPixel;
        bytesPerComponent = bytesPerPixel / supported.length;
        maxColorComponent = Math.pow(2, bytesPerComponent * 8) - 1;
        data = getInitialData();
        size = getInitialSize();
        env = Environment.getEnvironment();
    }

    GlassImage(int width, int height) {
        this(width, height, 4, Component.BLUE, Component.GREEN, Component.RED, Component.ALPHA);
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    public Pixels getImage() {
        return image;
    }

    public ByteBuffer getData() {
        return data;
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

    private ByteBuffer getInitialData() {
        final AtomicReference<ByteBuffer> dataRef = new AtomicReference<ByteBuffer>();
        Application.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                dataRef.set(image.asByteBuffer());
            }
        });
        return dataRef.get();
    }

    private int getBufferOffset(int x, int y) {
        return y * size.width * bytesPerPixel + x * bytesPerPixel;
    }

    private void setNumber(int position, int componentIndex, long value) {
        position += bytesPerComponent * componentIndex;
        for (int i = bytesPerComponent - 1; i >= 0; i--) {
            long mask = 0xFF << (8 * i);
            getData().put(position + i, (byte) ((value & mask) >> (8 * i)));
        }
    }

    private long getNumber(int position, int componentIndex) {
        long res = 0;
        position += bytesPerComponent * componentIndex;
        for (int i = 0; i < bytesPerComponent; i++) {
            res = (res << 8) | (getData().get(position + i) & 0xFF);
        }
        return res;
    }

    @Override
    public void setColors(int x, int y, double[] colors) {
        int pixelOffset = getBufferOffset(x, y);
        for (int i = 0; i < getSupported().length; i++) {
            setNumber(pixelOffset, i, (long) (colors[i] * maxColorComponent));
        }
    }

    @Override
    public void getColors(int x, int y, double[] colors) {
        int pixelOffset = getBufferOffset(x, y);
        for (int i = 0; i < getSupported().length; i++) {
            if (ignoreAlpha && getSupported()[i] == Component.ALPHA) {
                colors[i] = 1;
            } else {
                colors[i] = ((double) getNumber(pixelOffset, i)) / maxColorComponent;
            }
        }
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
