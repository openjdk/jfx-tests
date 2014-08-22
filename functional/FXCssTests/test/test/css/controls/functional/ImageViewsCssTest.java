/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package test.css.controls.functional;

import org.junit.Test;
import client.test.Keywords;
import client.test.Smoke;
import org.junit.BeforeClass;
import org.junit.Before;
import test.javaclient.shared.TestBase;
import static test.css.controls.ControlPage.ImageViews;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class ImageViewsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(ImageViews);
    }

    /**
     * test  ImageView with css: -fx-blend-mode
     */
    @Test
    public void ImageViews_BLEND_MODE() throws Exception {
       testAdditionalAction(ImageViews.name(), "BLEND-MODE", true);
    }

    /**
     * test  ImageView with css: -fx-drop-shadow
     */
    @Test
    public void ImageViews_DROP_SHADOW() throws Exception {
       testAdditionalAction(ImageViews.name(), "DROP_SHADOW", true);
    }

    /**
     * test  ImageView with css: -fx-image
     */
    @Test
    @Smoke
    public void ImageViews_IMAGE() throws Exception {
       testAdditionalAction(ImageViews.name(), "IMAGE", true);
    }

    /**
     * test  ImageView with css: -fx-inner-shadow
     */
    @Test
    public void ImageViews_INNER_SHADOW() throws Exception {
       testAdditionalAction(ImageViews.name(), "INNER_SHADOW", true);
    }

    /**
     * test  ImageView with css: -fx-opacity
     */
    @Test
    public void ImageViews_OPACITY() throws Exception {
       testAdditionalAction(ImageViews.name(), "OPACITY", true);
    }

    /**
     * test  ImageView with css: -fx-rotate
     */
    @Test
    public void ImageViews_ROTATE() throws Exception {
       testAdditionalAction(ImageViews.name(), "ROTATE", true);
    }

    /**
     * test  ImageView with css: -fx-scale-x
     */
    @Test
    public void ImageViews_SCALE_X() throws Exception {
       testAdditionalAction(ImageViews.name(), "SCALE-X", true);
    }

    /**
     * test  ImageView with css: -fx-scale-y
     */
    @Test
    public void ImageViews_SCALE_Y() throws Exception {
       testAdditionalAction(ImageViews.name(), "SCALE-Y", true);
    }

    /**
     * test  ImageView with css: -fx-translate-x
     */
    @Test
    public void ImageViews_TRANSLATE_X() throws Exception {
       testAdditionalAction(ImageViews.name(), "TRANSLATE-X", true);
    }

    /**
     * test  ImageView with css: -fx-translate-y
     */
    @Test
    public void ImageViews_TRANSLATE_Y() throws Exception {
       testAdditionalAction(ImageViews.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
