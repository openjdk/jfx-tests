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
import static test.css.controls.ControlPage.ListViews;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class ListViewsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(ListViews);
    }

    /**
     * test  ListView with css: -fx-background-color
     */
    @Test
    public void ListViews_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  ListView with css: -fx-background-image
     */
    @Test
    public void ListViews_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  ListView with css: -fx-background-inset
     */
    @Test
    public void ListViews_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  ListView with css: -fx-background-position
     */
    @Test
    public void ListViews_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  ListView with css: -fx-background-repeat-round
     */
    @Test
    public void ListViews_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  ListView with css: -fx-background-repeat-space
     */
    @Test
    public void ListViews_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  ListView with css: -fx-background-repeat-x-y
     */
    @Test
    public void ListViews_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  ListView with css: -fx-background-size
     */
    @Test
    public void ListViews_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(ListViews.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  ListView with css: -fx-blend-mode
     */
    @Test
    public void ListViews_BLEND_MODE() throws Exception {
       testAdditionalAction(ListViews.name(), "BLEND-MODE", true);
    }

    /**
     * test  ListView with css: -fx-border-color
     */
    @Test
    public void ListViews_BORDER_COLOR() throws Exception {
       testAdditionalAction(ListViews.name(), "BORDER-COLOR", true);
    }

    /**
     * test  ListView with css: -fx-border-inset
     */
    @Test
    public void ListViews_BORDER_INSET() throws Exception {
       testAdditionalAction(ListViews.name(), "BORDER-INSET", true);
    }

    /**
     * test  ListView with css: -fx-border-style-dashed
     */
    @Test
    public void ListViews_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(ListViews.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  ListView with css: -fx-border-style-dotted
     */
    @Test
    public void ListViews_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(ListViews.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  ListView with css: -fx-border-width
     */
    @Test
    public void ListViews_BORDER_WIDTH() throws Exception {
       testAdditionalAction(ListViews.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  ListView with css: -fx-border-width-dashed
     */
    @Test
    public void ListViews_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(ListViews.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  ListView with css: -fx-border-width-dotted
     */
    @Test
    public void ListViews_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(ListViews.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  ListView with css: -fx-drop-shadow
     */
    @Test
    public void ListViews_DROP_SHADOW() throws Exception {
       testAdditionalAction(ListViews.name(), "DROP_SHADOW", true);
    }

    /**
     * test  ListView with css: -fx-image-border
     */
    @Test
    public void ListViews_IMAGE_BORDER() throws Exception {
       testAdditionalAction(ListViews.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  ListView with css: -fx-image-border-insets
     */
    @Test
    public void ListViews_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(ListViews.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  ListView with css: -fx-image-border-no-repeat
     */
    @Test
    public void ListViews_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(ListViews.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  ListView with css: -fx-image-border-repeat-x
     */
    @Test
    public void ListViews_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(ListViews.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  ListView with css: -fx-image-border-repeat-y
     */
    @Test
    public void ListViews_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(ListViews.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  ListView with css: -fx-image-border-round
     */
    @Test
    public void ListViews_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(ListViews.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  ListView with css: -fx-image-border-space
     */
    @Test
    public void ListViews_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(ListViews.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  ListView with css: -fx-inner-shadow
     */
    @Test
    public void ListViews_INNER_SHADOW() throws Exception {
       testAdditionalAction(ListViews.name(), "INNER_SHADOW", true);
    }

    /**
     * test  ListView with css: -fx-opacity
     */
    @Test
    public void ListViews_OPACITY() throws Exception {
       testAdditionalAction(ListViews.name(), "OPACITY", true);
    }

    /**
     * test  ListView with css: -fx-orientation-horizontal
     */
    @Test
    @Smoke
    public void ListViews_ORIENTATION_HORIZONTAL() throws Exception {
       testAdditionalAction(ListViews.name(), "ORIENTATION_HORIZONTAL", true);
    }

    /**
     * test  ListView with css: -fx-orientation-vertical
     */
    @Test
    @Smoke
    public void ListViews_ORIENTATION_VERTICAL() throws Exception {
       testAdditionalAction(ListViews.name(), "ORIENTATION_VERTICAL", true);
    }

    /**
     * test  ListView with css: -fx-padding
     */
    @Test
    public void ListViews_PADDING() throws Exception {
       testAdditionalAction(ListViews.name(), "PADDING", true);
    }

    /**
     * test  ListView with css: -fx-position-scale-shape
     */
    @Test
    public void ListViews_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ListViews.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  ListView with css: -fx-rotate
     */
    @Test
    public void ListViews_ROTATE() throws Exception {
       testAdditionalAction(ListViews.name(), "ROTATE", true);
    }

    /**
     * test  ListView with css: -fx-scale-shape
     */
    @Test
    public void ListViews_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ListViews.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  ListView with css: -fx-scale-x
     */
    @Test
    public void ListViews_SCALE_X() throws Exception {
       testAdditionalAction(ListViews.name(), "SCALE-X", true);
    }

    /**
     * test  ListView with css: -fx-scale-y
     */
    @Test
    public void ListViews_SCALE_Y() throws Exception {
       testAdditionalAction(ListViews.name(), "SCALE-Y", true);
    }

    /**
     * test  ListView with css: -fx-shape
     */
    @Test
    public void ListViews_SHAPE() throws Exception {
       testAdditionalAction(ListViews.name(), "SHAPE", true);
    }

    /**
     * test  ListView with css: -fx-snap-to-pixel
     */
    @Test
    public void ListViews_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(ListViews.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  ListView with css: -fx-translate-x
     */
    @Test
    public void ListViews_TRANSLATE_X() throws Exception {
       testAdditionalAction(ListViews.name(), "TRANSLATE-X", true);
    }

    /**
     * test  ListView with css: -fx-translate-y
     */
    @Test
    public void ListViews_TRANSLATE_Y() throws Exception {
       testAdditionalAction(ListViews.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
