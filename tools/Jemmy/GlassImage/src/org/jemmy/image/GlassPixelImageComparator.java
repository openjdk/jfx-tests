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
package org.jemmy.image;

import org.jemmy.Dimension;
import org.jemmy.env.Environment;
import org.jemmy.image.pixel.PixelImageComparator;
import org.jemmy.image.pixel.Raster;
import org.jemmy.image.pixel.RasterComparator;
import org.jemmy.image.pixel.WriteableRaster;

/**
 *
 * @author shura
 */
public class GlassPixelImageComparator extends PixelImageComparator {

    public GlassPixelImageComparator(RasterComparator comparator) {
        super(comparator);
    }

    public GlassPixelImageComparator(Environment env) {
        super(env);
    }

    @Override
    protected Image toImage(Raster raster) {
        return (GlassImage)raster;
    }

    @Override
    protected Raster toRaster(Image image) {
        return (GlassImage)image;
    }

    @Override
    protected WriteableRaster createDiffRaster(Raster raster0, Raster raster1) {
        Dimension size0 = raster0.getSize();
        Dimension size1 = raster1.getSize();
        return new GlassImage((GlassImage)raster0, new Dimension(
                Math.max(size0.width, size1.width),
                Math.max(size0.height, size1.height)
                ));
    }


}
