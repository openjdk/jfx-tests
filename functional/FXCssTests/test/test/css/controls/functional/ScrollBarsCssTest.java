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
import static test.css.controls.ControlPage.ScrollBars;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class ScrollBarsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(ScrollBars);
    }

    /**
     * test  ScrollBar with css: -fx-background-color
     */
    @Test
    public void ScrollBars_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  ScrollBar with css: -fx-background-image
     */
    @Test
    public void ScrollBars_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  ScrollBar with css: -fx-background-inset
     */
    @Test
    public void ScrollBars_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  ScrollBar with css: -fx-background-position
     */
    @Test
    public void ScrollBars_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  ScrollBar with css: -fx-background-repeat-round
     */
    @Test
    public void ScrollBars_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  ScrollBar with css: -fx-background-repeat-space
     */
    @Test
    public void ScrollBars_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  ScrollBar with css: -fx-background-repeat-x-y
     */
    @Test
    public void ScrollBars_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  ScrollBar with css: -fx-background-size
     */
    @Test
    public void ScrollBars_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  ScrollBar with css: -fx-blend-mode
     */
    @Test
    public void ScrollBars_BLEND_MODE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BLEND-MODE", true);
    }

    /**
     * test  ScrollBar with css: -fx-border-color
     */
    @Test
    public void ScrollBars_BORDER_COLOR() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BORDER-COLOR", true);
    }

    /**
     * test  ScrollBar with css: -fx-border-inset
     */
    @Test
    public void ScrollBars_BORDER_INSET() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BORDER-INSET", true);
    }

    /**
     * test  ScrollBar with css: -fx-border-style-dashed
     */
    @Test
    public void ScrollBars_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  ScrollBar with css: -fx-border-style-dotted
     */
    @Test
    public void ScrollBars_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  ScrollBar with css: -fx-border-width
     */
    @Test
    public void ScrollBars_BORDER_WIDTH() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  ScrollBar with css: -fx-border-width-dashed
     */
    @Test
    public void ScrollBars_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  ScrollBar with css: -fx-border-width-dotted
     */
    @Test
    public void ScrollBars_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(ScrollBars.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  ScrollBar with css: -fx-drop-shadow
     */
    @Test
    public void ScrollBars_DROP_SHADOW() throws Exception {
       testAdditionalAction(ScrollBars.name(), "DROP_SHADOW", true);
    }

    /**
     * test  ScrollBar with css: -fx-image-border
     */
    @Test
    public void ScrollBars_IMAGE_BORDER() throws Exception {
       testAdditionalAction(ScrollBars.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  ScrollBar with css: -fx-image-border-insets
     */
    @Test
    public void ScrollBars_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(ScrollBars.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  ScrollBar with css: -fx-image-border-no-repeat
     */
    @Test
    public void ScrollBars_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(ScrollBars.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  ScrollBar with css: -fx-image-border-repeat-x
     */
    @Test
    public void ScrollBars_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(ScrollBars.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  ScrollBar with css: -fx-image-border-repeat-y
     */
    @Test
    public void ScrollBars_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(ScrollBars.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  ScrollBar with css: -fx-image-border-round
     */
    @Test
    public void ScrollBars_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(ScrollBars.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  ScrollBar with css: -fx-image-border-space
     */
    @Test
    public void ScrollBars_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  ScrollBar with css: -fx-inner-shadow
     */
    @Test
    public void ScrollBars_INNER_SHADOW() throws Exception {
       testAdditionalAction(ScrollBars.name(), "INNER_SHADOW", true);
    }

    /**
     * test  ScrollBar with css: -fx-max-height
     */
    @Test
    public void ScrollBars_MAX_HEIGHT() throws Exception {
       testAdditionalAction(ScrollBars.name(), "MAX-HEIGHT", true);
    }

    /**
     * test  ScrollBar with css: -fx-max-width
     */
    @Test
    public void ScrollBars_MAX_WIDTH() throws Exception {
       testAdditionalAction(ScrollBars.name(), "MAX-WIDTH", true);
    }

    /**
     * test  ScrollBar with css: -fx-min-height
     */
    @Test
    public void ScrollBars_MIN_HEIGHT() throws Exception {
       testAdditionalAction(ScrollBars.name(), "MIN-HEIGHT", true);
    }

    /**
     * test  ScrollBar with css: -fx-min-width
     */
    @Test
    public void ScrollBars_MIN_WIDTH() throws Exception {
       testAdditionalAction(ScrollBars.name(), "MIN-WIDTH", true);
    }

    /**
     * test  ScrollBar with css: -fx-opacity
     */
    @Test
    public void ScrollBars_OPACITY() throws Exception {
       testAdditionalAction(ScrollBars.name(), "OPACITY", true);
    }

    /**
     * test  ScrollBar with css: -fx-orientation-horizontal
     */
    @Test
    public void ScrollBars_ORIENTATION_HORIZONTAL() throws Exception {
       testAdditionalAction(ScrollBars.name(), "ORIENTATION_HORIZONTAL", true);
    }

    /**
     * test  ScrollBar with css: -fx-orientation-vertical
     */
    @Test
    public void ScrollBars_ORIENTATION_VERTICAL() throws Exception {
       testAdditionalAction(ScrollBars.name(), "ORIENTATION_VERTICAL", true);
    }

    /**
     * test  ScrollBar with css: -fx-padding
     */
    @Test
    public void ScrollBars_PADDING() throws Exception {
       testAdditionalAction(ScrollBars.name(), "PADDING", true);
    }

    /**
     * test  ScrollBar with css: -fx-position-scale-shape
     */
    @Test
    public void ScrollBars_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  ScrollBar with css: -fx-pref-height
     */
    @Test
    public void ScrollBars_PREF_HEIGHT() throws Exception {
       testAdditionalAction(ScrollBars.name(), "PREF-HEIGHT", true);
    }

    /**
     * test  ScrollBar with css: -fx-pref-width
     */
    @Test
    public void ScrollBars_PREF_WIDTH() throws Exception {
       testAdditionalAction(ScrollBars.name(), "PREF-WIDTH", true);
    }

    /**
     * test  ScrollBar with css: -fx-rotate
     */
    @Test
    public void ScrollBars_ROTATE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "ROTATE", true);
    }

    /**
     * test  ScrollBar with css: -fx-scale-shape
     */
    @Test
    public void ScrollBars_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  ScrollBar with css: -fx-scale-x
     */
    @Test
    public void ScrollBars_SCALE_X() throws Exception {
       testAdditionalAction(ScrollBars.name(), "SCALE-X", true);
    }

    /**
     * test  ScrollBar with css: -fx-scale-y
     */
    @Test
    public void ScrollBars_SCALE_Y() throws Exception {
       testAdditionalAction(ScrollBars.name(), "SCALE-Y", true);
    }

    /**
     * test  ScrollBar with css: -fx-shape
     */
    @Test
    public void ScrollBars_SHAPE() throws Exception {
       testAdditionalAction(ScrollBars.name(), "SHAPE", true);
    }

    /**
     * test  ScrollBar with css: -fx-snap-to-pixel
     */
    @Test
    public void ScrollBars_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(ScrollBars.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  ScrollBar with css: -fx-translate-x
     */
    @Test
    public void ScrollBars_TRANSLATE_X() throws Exception {
       testAdditionalAction(ScrollBars.name(), "TRANSLATE-X", true);
    }

    /**
     * test  ScrollBar with css: -fx-translate-y
     */
    @Test
    public void ScrollBars_TRANSLATE_Y() throws Exception {
       testAdditionalAction(ScrollBars.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
