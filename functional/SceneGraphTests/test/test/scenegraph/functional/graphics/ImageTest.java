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
package test.scenegraph.functional.graphics;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.JemmyUtils;
import test.scenegraph.app.ImagesApp;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class ImageTest extends TestBase {
    @Override
    protected String getName() {
        return "ImageTest";
    }

    /**
     * Runs UI.
     */
    @BeforeClass
    public static void runUI() {
        ImagesApp.main(null);
    }

    @Override
    @Before
    public void before()
    {
        super.before();
        if(Utils.isMacOS())
        {
            JemmyUtils.setJemmyComparatorByDistance(0.05f);
        }
    }

    @Test
    public void Image_defaults() {
        testCommon("Image", "defaults");
    }

    @Test
    public void Image_resize() {
        testCommon("Image", "resize");
    }

    @Test
    public void Image_resize_preserve() {
        testCommon("Image", "resize preserve");
    }

    @Test
    public void Image_resize_smooth() {
        testCommon("Image", "resize smooth");
    }

    @Test
    public void Image_placeholder__may_blink_() {
        testCommon("Image", "placeholder (may blink)");
    }

    @Test
    public void ImageView_as_is() {
        testCommon("ImageView", "as is");
    }

    @Test
    public void ImageView_fit_width_40() {
        testCommon("ImageView", "fit width 40");
    }

    @Test
    public void ImageView_fit_smoth() {
        testCommon("ImageView", "fit smoth");
    }

    @Test
    public void ImageView_fit_preserve() {
        testCommon("ImageView", "fit preserve");
    }

    @Test
    public void ImageView_fit_height() {
        testCommon("ImageView", "fit height");
    }

    @Test
    public void ImageView_viewport() {
        testCommon("ImageView", "viewport");
    }

    @Test
    public void ImageView_rotate() {
        testCommon("ImageView", "rotate");
    }

    @Test
    public void ImageView_canvas() {
        testCommon("ImageView", "canvas");
    }

    @Test
    public void ImageInEffects_Lighting_Identity() {
        testCommon("ImageInEffects", "Lighting-Identity");
    }

    @Test
    public void ImageInEffects_SepiaTone() {
        testCommon("ImageInEffects", "SepiaTone");
    }

    @Test
    public void ImagePatternDefaults()
    {
        testCommon("ImagePattern", "defaults");
    }

    @Test
    public void ImagePatternResize()
    {
        testCommon("ImagePattern", "resize");
    }

    @Test
    public void ImagePatternEffectsStroke()
    {
        testCommon("ImagePattern with effects", "stroke");
    }

    @Test
    public void ImagePatternEffectsDropshadow()
    {
        testCommon("ImagePattern with effects", "dropshadow");
    }
}
