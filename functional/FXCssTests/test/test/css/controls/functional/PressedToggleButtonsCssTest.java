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
import static test.css.controls.ControlPage.PressedToggleButtons;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class PressedToggleButtonsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(PressedToggleButtons);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-baseline-center
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-baseline-left
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-baseline-right
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-bottom-center
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-bottom-left
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-bottom-right
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-center
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-center-left
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-center-right
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-top-center
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-top-left
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-alignment-top-right
     */
    @Test
    public void PressedToggleButtons_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-color
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-image
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-inset
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-position
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-repeat-round
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-repeat-space
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-repeat-x-y
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-background-size
     */
    @Test
    public void PressedToggleButtons_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-blend-mode
     */
    @Test
    public void PressedToggleButtons_BLEND_MODE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BLEND-MODE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-border-color
     */
    @Test
    public void PressedToggleButtons_BORDER_COLOR() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BORDER-COLOR", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-border-inset
     */
    @Test
    public void PressedToggleButtons_BORDER_INSET() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BORDER-INSET", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-border-style-dashed
     */
    @Test
    public void PressedToggleButtons_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-border-style-dotted
     */
    @Test
    public void PressedToggleButtons_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-border-width
     */
    @Test
    public void PressedToggleButtons_BORDER_WIDTH() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-border-width-dashed
     */
    @Test
    public void PressedToggleButtons_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-border-width-dotted
     */
    @Test
    public void PressedToggleButtons_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-content-display-bottom
     */
    @Test
    public void PressedToggleButtons_CONTENT_DISPLAY_BOTTOM() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "CONTENT_DISPLAY_BOTTOM", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-content-display-center
     */
    @Test
    public void PressedToggleButtons_CONTENT_DISPLAY_CENTER() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "CONTENT_DISPLAY_CENTER", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-content-display-graphic-only
     */
    @Test
    public void PressedToggleButtons_CONTENT_DISPLAY_GRAPHIC_ONLY() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "CONTENT_DISPLAY_GRAPHIC_ONLY", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-content-display-left
     */
    @Test
    public void PressedToggleButtons_CONTENT_DISPLAY_LEFT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "CONTENT_DISPLAY_LEFT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-content-display-right
     */
    @Test
    public void PressedToggleButtons_CONTENT_DISPLAY_RIGHT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "CONTENT_DISPLAY_RIGHT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-content-display-text-only
     */
    @Test
    public void PressedToggleButtons_CONTENT_DISPLAY_TEXT_ONLY() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "CONTENT_DISPLAY_TEXT_ONLY", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-content-display-top
     */
    @Test
    public void PressedToggleButtons_CONTENT_DISPLAY_TOP() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "CONTENT_DISPLAY_TOP", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-drop-shadow
     */
    @Test
    public void PressedToggleButtons_DROP_SHADOW() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "DROP_SHADOW", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-ellipsis-string
     */
    @Test
    public void PressedToggleButtons_ELLIPSIS_STRING() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ELLIPSIS-STRING", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-font-family
     */
    @Test
    public void PressedToggleButtons_FONT_FAMILY() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "FONT-FAMILY", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-font-size
     */
    @Test
    public void PressedToggleButtons_FONT_SIZE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "FONT-SIZE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-font-style
     */
    @Test
    public void PressedToggleButtons_FONT_STYLE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "FONT-STYLE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-font-weight
     */
    @Test
    public void PressedToggleButtons_FONT_WEIGHT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-graphic
     */
    @Test
    public void PressedToggleButtons_GRAPHIC() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "GRAPHIC", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-graphic-text-gap
     */
    @Test
    public void PressedToggleButtons_GRAPHIC_TEXT_GAP() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "GRAPHIC-TEXT-GAP", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-image-border
     */
    @Test
    public void PressedToggleButtons_IMAGE_BORDER() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-image-border-insets
     */
    @Test
    public void PressedToggleButtons_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-image-border-no-repeat
     */
    @Test
    public void PressedToggleButtons_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-image-border-repeat-x
     */
    @Test
    public void PressedToggleButtons_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-image-border-repeat-y
     */
    @Test
    public void PressedToggleButtons_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-image-border-round
     */
    @Test
    public void PressedToggleButtons_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-image-border-space
     */
    @Test
    public void PressedToggleButtons_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-inner-shadow
     */
    @Test
    public void PressedToggleButtons_INNER_SHADOW() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "INNER_SHADOW", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-label-padding
     */
    @Test
    public void PressedToggleButtons_LABEL_PADDING() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "LABEL-PADDING", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-opacity
     */
    @Test
    public void PressedToggleButtons_OPACITY() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "OPACITY", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-padding
     */
    @Test
    public void PressedToggleButtons_PADDING() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "PADDING", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-position-scale-shape
     */
    @Test
    public void PressedToggleButtons_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-rotate
     */
    @Test
    public void PressedToggleButtons_ROTATE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "ROTATE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-scale-shape
     */
    @Test
    public void PressedToggleButtons_SCALE_SHAPE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-scale-x
     */
    @Test
    public void PressedToggleButtons_SCALE_X() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "SCALE-X", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-scale-y
     */
    @Test
    public void PressedToggleButtons_SCALE_Y() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "SCALE-Y", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-shape
     */
    @Test
    public void PressedToggleButtons_SHAPE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "SHAPE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-snap-to-pixel
     */
    @Test
    public void PressedToggleButtons_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-fill
     */
    @Test
    public void PressedToggleButtons_TEXT_FILL() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT-FILL", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-alignment-center
     */
    @Test
    public void PressedToggleButtons_TEXT_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_ALIGNMENT_CENTER", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-alignment-justify
     */
    @Test
    public void PressedToggleButtons_TEXT_ALIGNMENT_JUSTIFY() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_ALIGNMENT_JUSTIFY", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-alignment-left
     */
    @Test
    public void PressedToggleButtons_TEXT_ALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_ALIGNMENT_LEFT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-alignment-right
     */
    @Test
    public void PressedToggleButtons_TEXT_ALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_ALIGNMENT_RIGHT", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-overrun-center-ellipsis
     */
    @Test
    public void PressedToggleButtons_TEXT_OVERRUN_CENTER_ELLIPSIS() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_OVERRUN_CENTER_ELLIPSIS", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-overrun-center-word-ellipsis
     */
    @Test
    public void PressedToggleButtons_TEXT_OVERRUN_CENTER_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_OVERRUN_CENTER_WORD_ELLIPSIS", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-overrun-clip
     */
    @Test
    public void PressedToggleButtons_TEXT_OVERRUN_CLIP() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_OVERRUN_CLIP", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-overrun-ellipsis
     */
    @Test
    public void PressedToggleButtons_TEXT_OVERRUN_ELLIPSIS() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_OVERRUN_ELLIPSIS", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-overrun-leading-ellipsis
     */
    @Test
    public void PressedToggleButtons_TEXT_OVERRUN_LEADING_ELLIPSIS() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_OVERRUN_LEADING_ELLIPSIS", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-overrun-leading-word-ellipsis
     */
    @Test
    public void PressedToggleButtons_TEXT_OVERRUN_LEADING_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_OVERRUN_LEADING_WORD_ELLIPSIS", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-text-overrun-word-ellipsis
     */
    @Test
    public void PressedToggleButtons_TEXT_OVERRUN_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TEXT_OVERRUN_WORD_ELLIPSIS", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-translate-x
     */
    @Test
    public void PressedToggleButtons_TRANSLATE_X() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TRANSLATE-X", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-translate-y
     */
    @Test
    public void PressedToggleButtons_TRANSLATE_Y() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-underline
     */
    @Test
    public void PressedToggleButtons_UNDERLINE() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "UNDERLINE", true);
    }

    /**
     * test  PressedToggleButton with css: -fx-wrap-text
     */
    @Test
    public void PressedToggleButtons_WRAP_TEXT() throws Exception {
       testAdditionalAction(PressedToggleButtons.name(), "WRAP-TEXT", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
