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
 * questions.
 */
package org.jemmy.image.glass;

import java.io.IOException;
import java.io.InputStream;

import org.jemmy.Dimension;
import org.jemmy.env.Environment;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.WriteableRaster;

/**
 * Allows to load PNG graphical file.
 * @author Alexandre Iline
 */
public class PNGLoader extends org.jemmy.image.pixel.PNGLoader {

    /**
     * Constructs a PNGDecoder object.
     * @param in input stream to read PNG image from.
     */
    public PNGLoader(InputStream in) {
        super(in);
    }

    /**
     * Decodes image from an input stream passed into constructor.
     * @return a BufferedImage object
     * @throws IOException
     */
    @Override
    public GlassImage decode() throws IOException {
        return (GlassImage)super.decode(true);
    }

    /**
     * Decodes image from an input stream passed into constructor.
     * @return a BufferedImage object
     * @param closeStream requests method to close the stream after the image is read
     * @throws IOException
     */
    @Override
    public GlassImage decode(boolean closeStream) throws IOException {
        return (GlassImage)super.decode(closeStream);
    }

    @Override
    protected WriteableRaster createRaster(int width, int height) {
        return new GlassImage(Environment.getEnvironment(), new Dimension(width, height));
    }

}
