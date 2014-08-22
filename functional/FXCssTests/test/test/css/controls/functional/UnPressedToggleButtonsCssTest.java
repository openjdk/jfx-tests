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
import static test.css.controls.ControlPage.UnPressedToggleButtons;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class UnPressedToggleButtonsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(UnPressedToggleButtons);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-baseline-center
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-baseline-left
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-baseline-right
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-bottom-center
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-bottom-left
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-bottom-right
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-center
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-center-left
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-center-right
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-top-center
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-top-left
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-alignment-top-right
     */
    @Test
    public void UnPressedToggleButtons_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-color
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-image
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-inset
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-position
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-repeat-round
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-repeat-space
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-repeat-x-y
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-background-size
     */
    @Test
    public void UnPressedToggleButtons_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-blend-mode
     */
    @Test
    public void UnPressedToggleButtons_BLEND_MODE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BLEND-MODE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-border-color
     */
    @Test
    public void UnPressedToggleButtons_BORDER_COLOR() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BORDER-COLOR", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-border-inset
     */
    @Test
    public void UnPressedToggleButtons_BORDER_INSET() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BORDER-INSET", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-border-style-dashed
     */
    @Test
    public void UnPressedToggleButtons_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-border-style-dotted
     */
    @Test
    public void UnPressedToggleButtons_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-border-width
     */
    @Test
    public void UnPressedToggleButtons_BORDER_WIDTH() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-border-width-dashed
     */
    @Test
    public void UnPressedToggleButtons_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-border-width-dotted
     */
    @Test
    public void UnPressedToggleButtons_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-content-display-bottom
     */
    @Test
    public void UnPressedToggleButtons_CONTENT_DISPLAY_BOTTOM() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "CONTENT_DISPLAY_BOTTOM", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-content-display-center
     */
    @Test
    public void UnPressedToggleButtons_CONTENT_DISPLAY_CENTER() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "CONTENT_DISPLAY_CENTER", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-content-display-graphic-only
     */
    @Test
    public void UnPressedToggleButtons_CONTENT_DISPLAY_GRAPHIC_ONLY() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "CONTENT_DISPLAY_GRAPHIC_ONLY", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-content-display-left
     */
    @Test
    public void UnPressedToggleButtons_CONTENT_DISPLAY_LEFT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "CONTENT_DISPLAY_LEFT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-content-display-right
     */
    @Test
    public void UnPressedToggleButtons_CONTENT_DISPLAY_RIGHT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "CONTENT_DISPLAY_RIGHT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-content-display-text-only
     */
    @Test
    public void UnPressedToggleButtons_CONTENT_DISPLAY_TEXT_ONLY() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "CONTENT_DISPLAY_TEXT_ONLY", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-content-display-top
     */
    @Test
    public void UnPressedToggleButtons_CONTENT_DISPLAY_TOP() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "CONTENT_DISPLAY_TOP", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-drop-shadow
     */
    @Test
    public void UnPressedToggleButtons_DROP_SHADOW() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "DROP_SHADOW", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-ellipsis-string
     */
    @Test
    public void UnPressedToggleButtons_ELLIPSIS_STRING() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ELLIPSIS-STRING", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-font-family
     */
    @Test
    public void UnPressedToggleButtons_FONT_FAMILY() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "FONT-FAMILY", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-font-size
     */
    @Test
    public void UnPressedToggleButtons_FONT_SIZE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "FONT-SIZE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-font-style
     */
    @Test
    public void UnPressedToggleButtons_FONT_STYLE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "FONT-STYLE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-font-weight
     */
    @Test
    public void UnPressedToggleButtons_FONT_WEIGHT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-graphic
     */
    @Test
    public void UnPressedToggleButtons_GRAPHIC() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "GRAPHIC", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-graphic-text-gap
     */
    @Test
    public void UnPressedToggleButtons_GRAPHIC_TEXT_GAP() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "GRAPHIC-TEXT-GAP", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-image-border
     */
    @Test
    public void UnPressedToggleButtons_IMAGE_BORDER() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-image-border-insets
     */
    @Test
    public void UnPressedToggleButtons_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-image-border-no-repeat
     */
    @Test
    public void UnPressedToggleButtons_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-image-border-repeat-x
     */
    @Test
    public void UnPressedToggleButtons_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-image-border-repeat-y
     */
    @Test
    public void UnPressedToggleButtons_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-image-border-round
     */
    @Test
    public void UnPressedToggleButtons_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-image-border-space
     */
    @Test
    public void UnPressedToggleButtons_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-inner-shadow
     */
    @Test
    public void UnPressedToggleButtons_INNER_SHADOW() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "INNER_SHADOW", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-label-padding
     */
    @Test
    public void UnPressedToggleButtons_LABEL_PADDING() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "LABEL-PADDING", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-opacity
     */
    @Test
    public void UnPressedToggleButtons_OPACITY() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "OPACITY", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-padding
     */
    @Test
    public void UnPressedToggleButtons_PADDING() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "PADDING", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-position-scale-shape
     */
    @Test
    public void UnPressedToggleButtons_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-rotate
     */
    @Test
    public void UnPressedToggleButtons_ROTATE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "ROTATE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-scale-shape
     */
    @Test
    public void UnPressedToggleButtons_SCALE_SHAPE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-scale-x
     */
    @Test
    public void UnPressedToggleButtons_SCALE_X() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "SCALE-X", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-scale-y
     */
    @Test
    public void UnPressedToggleButtons_SCALE_Y() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "SCALE-Y", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-shape
     */
    @Test
    public void UnPressedToggleButtons_SHAPE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "SHAPE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-snap-to-pixel
     */
    @Test
    public void UnPressedToggleButtons_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-fill
     */
    @Test
    public void UnPressedToggleButtons_TEXT_FILL() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT-FILL", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-alignment-center
     */
    @Test
    public void UnPressedToggleButtons_TEXT_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_ALIGNMENT_CENTER", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-alignment-justify
     */
    @Test
    public void UnPressedToggleButtons_TEXT_ALIGNMENT_JUSTIFY() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_ALIGNMENT_JUSTIFY", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-alignment-left
     */
    @Test
    public void UnPressedToggleButtons_TEXT_ALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_ALIGNMENT_LEFT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-alignment-right
     */
    @Test
    public void UnPressedToggleButtons_TEXT_ALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_ALIGNMENT_RIGHT", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-overrun-center-ellipsis
     */
    @Test
    public void UnPressedToggleButtons_TEXT_OVERRUN_CENTER_ELLIPSIS() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_OVERRUN_CENTER_ELLIPSIS", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-overrun-center-word-ellipsis
     */
    @Test
    public void UnPressedToggleButtons_TEXT_OVERRUN_CENTER_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_OVERRUN_CENTER_WORD_ELLIPSIS", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-overrun-clip
     */
    @Test
    public void UnPressedToggleButtons_TEXT_OVERRUN_CLIP() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_OVERRUN_CLIP", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-overrun-ellipsis
     */
    @Test
    public void UnPressedToggleButtons_TEXT_OVERRUN_ELLIPSIS() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_OVERRUN_ELLIPSIS", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-overrun-leading-ellipsis
     */
    @Test
    public void UnPressedToggleButtons_TEXT_OVERRUN_LEADING_ELLIPSIS() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_OVERRUN_LEADING_ELLIPSIS", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-overrun-leading-word-ellipsis
     */
    @Test
    public void UnPressedToggleButtons_TEXT_OVERRUN_LEADING_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_OVERRUN_LEADING_WORD_ELLIPSIS", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-text-overrun-word-ellipsis
     */
    @Test
    public void UnPressedToggleButtons_TEXT_OVERRUN_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TEXT_OVERRUN_WORD_ELLIPSIS", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-translate-x
     */
    @Test
    public void UnPressedToggleButtons_TRANSLATE_X() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TRANSLATE-X", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-translate-y
     */
    @Test
    public void UnPressedToggleButtons_TRANSLATE_Y() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-underline
     */
    @Test
    public void UnPressedToggleButtons_UNDERLINE() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "UNDERLINE", true);
    }

    /**
     * test  UnPressedToggleButton with css: -fx-wrap-text
     */
    @Test
    public void UnPressedToggleButtons_WRAP_TEXT() throws Exception {
       testAdditionalAction(UnPressedToggleButtons.name(), "WRAP-TEXT", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
