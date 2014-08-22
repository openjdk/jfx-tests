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
import static test.css.controls.ControlPage.Sliders;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class SlidersCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(Sliders);
    }

    /**
     * test  Slider with css: -fx-background-color
     */
    @Test
    public void Sliders_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  Slider with css: -fx-background-image
     */
    @Test
    public void Sliders_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  Slider with css: -fx-background-inset
     */
    @Test
    public void Sliders_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  Slider with css: -fx-background-position
     */
    @Test
    public void Sliders_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  Slider with css: -fx-background-repeat-round
     */
    @Test
    public void Sliders_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  Slider with css: -fx-background-repeat-space
     */
    @Test
    public void Sliders_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  Slider with css: -fx-background-repeat-x-y
     */
    @Test
    public void Sliders_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  Slider with css: -fx-background-size
     */
    @Test
    public void Sliders_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(Sliders.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  Slider with css: -fx-blend-mode
     */
    @Test
    public void Sliders_BLEND_MODE() throws Exception {
       testAdditionalAction(Sliders.name(), "BLEND-MODE", true);
    }

    /**
     * test  Slider with css: -fx-block-increment
     */
    @Test
    public void Sliders_BLOCK_INCREMENT() throws Exception {
       testAdditionalAction(Sliders.name(), "BLOCK-INCREMENT", true);
    }

    /**
     * test  Slider with css: -fx-border-color
     */
    @Test
    public void Sliders_BORDER_COLOR() throws Exception {
       testAdditionalAction(Sliders.name(), "BORDER-COLOR", true);
    }

    /**
     * test  Slider with css: -fx-border-inset
     */
    @Test
    public void Sliders_BORDER_INSET() throws Exception {
       testAdditionalAction(Sliders.name(), "BORDER-INSET", true);
    }

    /**
     * test  Slider with css: -fx-border-style-dashed
     */
    @Test
    public void Sliders_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(Sliders.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  Slider with css: -fx-border-style-dotted
     */
    @Test
    public void Sliders_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(Sliders.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  Slider with css: -fx-border-width
     */
    @Test
    public void Sliders_BORDER_WIDTH() throws Exception {
       testAdditionalAction(Sliders.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  Slider with css: -fx-border-width-dashed
     */
    @Test
    public void Sliders_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(Sliders.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  Slider with css: -fx-border-width-dotted
     */
    @Test
    public void Sliders_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(Sliders.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  Slider with css: -fx-drop-shadow
     */
    @Test
    public void Sliders_DROP_SHADOW() throws Exception {
       testAdditionalAction(Sliders.name(), "DROP_SHADOW", true);
    }

    /**
     * test  Slider with css: -fx-image-border
     */
    @Test
    public void Sliders_IMAGE_BORDER() throws Exception {
       testAdditionalAction(Sliders.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  Slider with css: -fx-image-border-insets
     */
    @Test
    public void Sliders_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(Sliders.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  Slider with css: -fx-image-border-no-repeat
     */
    @Test
    public void Sliders_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(Sliders.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  Slider with css: -fx-image-border-repeat-x
     */
    @Test
    public void Sliders_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(Sliders.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  Slider with css: -fx-image-border-repeat-y
     */
    @Test
    public void Sliders_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(Sliders.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  Slider with css: -fx-image-border-round
     */
    @Test
    public void Sliders_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(Sliders.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  Slider with css: -fx-image-border-space
     */
    @Test
    public void Sliders_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(Sliders.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  Slider with css: -fx-inner-shadow
     */
    @Test
    public void Sliders_INNER_SHADOW() throws Exception {
       testAdditionalAction(Sliders.name(), "INNER_SHADOW", true);
    }

    /**
     * test  Slider with css: -fx-major-tick-unit
     */
    @Test
    public void Sliders_MAJOR_TICK_UNIT() throws Exception {
       testAdditionalAction(Sliders.name(), "MAJOR-TICK-UNIT", true);
    }

    /**
     * test  Slider with css: -fx-minor-tick-count
     */
    @Test
    public void Sliders_MINOR_TICK_COUNT() throws Exception {
       testAdditionalAction(Sliders.name(), "MINOR-TICK-COUNT", true);
    }

    /**
     * test  Slider with css: -fx-opacity
     */
    @Test
    public void Sliders_OPACITY() throws Exception {
       testAdditionalAction(Sliders.name(), "OPACITY", true);
    }

    /**
     * test  Slider with css: -fx-orientation-horizontal
     */
    @Test
    public void Sliders_ORIENTATION_HORIZONTAL() throws Exception {
       testAdditionalAction(Sliders.name(), "ORIENTATION_HORIZONTAL", true);
    }

    /**
     * test  Slider with css: -fx-orientation-vertical
     */
    @Test
    public void Sliders_ORIENTATION_VERTICAL() throws Exception {
       testAdditionalAction(Sliders.name(), "ORIENTATION_VERTICAL", true);
    }

    /**
     * test  Slider with css: -fx-padding
     */
    @Test
    public void Sliders_PADDING() throws Exception {
       testAdditionalAction(Sliders.name(), "PADDING", true);
    }

    /**
     * test  Slider with css: -fx-position-scale-shape
     */
    @Test
    public void Sliders_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Sliders.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  Slider with css: -fx-rotate
     */
    @Test
    public void Sliders_ROTATE() throws Exception {
       testAdditionalAction(Sliders.name(), "ROTATE", true);
    }

    /**
     * test  Slider with css: -fx-scale-shape
     */
    @Test
    public void Sliders_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Sliders.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  Slider with css: -fx-scale-x
     */
    @Test
    public void Sliders_SCALE_X() throws Exception {
       testAdditionalAction(Sliders.name(), "SCALE-X", true);
    }

    /**
     * test  Slider with css: -fx-scale-y
     */
    @Test
    public void Sliders_SCALE_Y() throws Exception {
       testAdditionalAction(Sliders.name(), "SCALE-Y", true);
    }

    /**
     * test  Slider with css: -fx-shape
     */
    @Test
    public void Sliders_SHAPE() throws Exception {
       testAdditionalAction(Sliders.name(), "SHAPE", true);
    }

    /**
     * test  Slider with css: -fx-show-tick-labels
     */
    @Test
    public void Sliders_SHOW_TICK_LABELS() throws Exception {
       testAdditionalAction(Sliders.name(), "SHOW-TICK-LABELS", true);
    }

    /**
     * test  Slider with css: -fx-show-tick-marks
     */
    @Test
    public void Sliders_SHOW_TICK_MARKS() throws Exception {
       testAdditionalAction(Sliders.name(), "SHOW-TICK-MARKS", true);
    }

    /**
     * test  Slider with css: -fx-snap-to-pixel
     */
    @Test
    public void Sliders_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(Sliders.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  Slider with css: -fx-snap-to-ticks
     */
    @Test
    public void Sliders_SNAP_TO_TICKS() throws Exception {
       testAdditionalAction(Sliders.name(), "SNAP-TO-TICKS", true);
    }

    /**
     * test  Slider with css: -fx-translate-x
     */
    @Test
    public void Sliders_TRANSLATE_X() throws Exception {
       testAdditionalAction(Sliders.name(), "TRANSLATE-X", true);
    }

    /**
     * test  Slider with css: -fx-translate-y
     */
    @Test
    public void Sliders_TRANSLATE_Y() throws Exception {
       testAdditionalAction(Sliders.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
