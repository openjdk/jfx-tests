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
import static test.css.controls.ControlPage.SplitMenuButtons;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class SplitMenuButtonsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(SplitMenuButtons);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-baseline-center
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-baseline-left
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-baseline-right
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-bottom-center
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-bottom-left
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-bottom-right
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-center
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-center-left
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-center-right
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-top-center
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-top-left
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-alignment-top-right
     */
    @Test
    public void SplitMenuButtons_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-color
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-image
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-inset
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-position
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-repeat-round
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-repeat-space
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-repeat-x-y
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-background-size
     */
    @Test
    public void SplitMenuButtons_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-blend-mode
     */
    @Test
    public void SplitMenuButtons_BLEND_MODE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BLEND-MODE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-border-color
     */
    @Test
    public void SplitMenuButtons_BORDER_COLOR() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BORDER-COLOR", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-border-inset
     */
    @Test
    public void SplitMenuButtons_BORDER_INSET() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BORDER-INSET", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-border-style-dashed
     */
    @Test
    public void SplitMenuButtons_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-border-style-dotted
     */
    @Test
    public void SplitMenuButtons_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-border-width
     */
    @Test
    public void SplitMenuButtons_BORDER_WIDTH() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-border-width-dashed
     */
    @Test
    public void SplitMenuButtons_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-border-width-dotted
     */
    @Test
    public void SplitMenuButtons_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-content-display-bottom
     */
    @Test
    public void SplitMenuButtons_CONTENT_DISPLAY_BOTTOM() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "CONTENT_DISPLAY_BOTTOM", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-content-display-center
     */
    @Test
    public void SplitMenuButtons_CONTENT_DISPLAY_CENTER() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "CONTENT_DISPLAY_CENTER", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-content-display-graphic-only
     */
    @Test
    public void SplitMenuButtons_CONTENT_DISPLAY_GRAPHIC_ONLY() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "CONTENT_DISPLAY_GRAPHIC_ONLY", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-content-display-left
     */
    @Test
    public void SplitMenuButtons_CONTENT_DISPLAY_LEFT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "CONTENT_DISPLAY_LEFT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-content-display-right
     */
    @Test
    public void SplitMenuButtons_CONTENT_DISPLAY_RIGHT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "CONTENT_DISPLAY_RIGHT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-content-display-text-only
     */
    @Test
    public void SplitMenuButtons_CONTENT_DISPLAY_TEXT_ONLY() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "CONTENT_DISPLAY_TEXT_ONLY", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-content-display-top
     */
    @Test
    public void SplitMenuButtons_CONTENT_DISPLAY_TOP() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "CONTENT_DISPLAY_TOP", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-drop-shadow
     */
    @Test
    public void SplitMenuButtons_DROP_SHADOW() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "DROP_SHADOW", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-ellipsis-string
     */
    @Test
    public void SplitMenuButtons_ELLIPSIS_STRING() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ELLIPSIS-STRING", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-font-family
     */
    @Test
    public void SplitMenuButtons_FONT_FAMILY() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "FONT-FAMILY", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-font-size
     */
    @Test
    public void SplitMenuButtons_FONT_SIZE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "FONT-SIZE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-font-style
     */
    @Test
    public void SplitMenuButtons_FONT_STYLE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "FONT-STYLE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-font-weight
     */
    @Test
    public void SplitMenuButtons_FONT_WEIGHT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-graphic
     */
    @Test
    public void SplitMenuButtons_GRAPHIC() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "GRAPHIC", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-graphic-text-gap
     */
    @Test
    public void SplitMenuButtons_GRAPHIC_TEXT_GAP() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "GRAPHIC-TEXT-GAP", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-image-border
     */
    @Test
    public void SplitMenuButtons_IMAGE_BORDER() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-image-border-insets
     */
    @Test
    public void SplitMenuButtons_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-image-border-no-repeat
     */
    @Test
    public void SplitMenuButtons_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-image-border-repeat-x
     */
    @Test
    public void SplitMenuButtons_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-image-border-repeat-y
     */
    @Test
    public void SplitMenuButtons_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-image-border-round
     */
    @Test
    public void SplitMenuButtons_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-image-border-space
     */
    @Test
    public void SplitMenuButtons_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-inner-shadow
     */
    @Test
    public void SplitMenuButtons_INNER_SHADOW() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "INNER_SHADOW", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-label-padding
     */
    @Test
    public void SplitMenuButtons_LABEL_PADDING() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "LABEL-PADDING", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-opacity
     */
    @Test
    public void SplitMenuButtons_OPACITY() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "OPACITY", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-padding
     */
    @Test
    public void SplitMenuButtons_PADDING() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "PADDING", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-position-scale-shape
     */
    @Test
    public void SplitMenuButtons_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-rotate
     */
    @Test
    public void SplitMenuButtons_ROTATE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "ROTATE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-scale-shape
     */
    @Test
    public void SplitMenuButtons_SCALE_SHAPE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-scale-x
     */
    @Test
    public void SplitMenuButtons_SCALE_X() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "SCALE-X", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-scale-y
     */
    @Test
    public void SplitMenuButtons_SCALE_Y() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "SCALE-Y", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-shape
     */
    @Test
    public void SplitMenuButtons_SHAPE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "SHAPE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-snap-to-pixel
     */
    @Test
    public void SplitMenuButtons_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-fill
     */
    @Test
    public void SplitMenuButtons_TEXT_FILL() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT-FILL", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-alignment-center
     */
    @Test
    public void SplitMenuButtons_TEXT_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_ALIGNMENT_CENTER", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-alignment-justify
     */
    @Test
    public void SplitMenuButtons_TEXT_ALIGNMENT_JUSTIFY() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_ALIGNMENT_JUSTIFY", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-alignment-left
     */
    @Test
    public void SplitMenuButtons_TEXT_ALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_ALIGNMENT_LEFT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-alignment-right
     */
    @Test
    public void SplitMenuButtons_TEXT_ALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_ALIGNMENT_RIGHT", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-overrun-center-ellipsis
     */
    @Test
    public void SplitMenuButtons_TEXT_OVERRUN_CENTER_ELLIPSIS() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_OVERRUN_CENTER_ELLIPSIS", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-overrun-center-word-ellipsis
     */
    @Test
    public void SplitMenuButtons_TEXT_OVERRUN_CENTER_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_OVERRUN_CENTER_WORD_ELLIPSIS", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-overrun-clip
     */
    @Test
    public void SplitMenuButtons_TEXT_OVERRUN_CLIP() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_OVERRUN_CLIP", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-overrun-ellipsis
     */
    @Test
    public void SplitMenuButtons_TEXT_OVERRUN_ELLIPSIS() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_OVERRUN_ELLIPSIS", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-overrun-leading-ellipsis
     */
    @Test
    public void SplitMenuButtons_TEXT_OVERRUN_LEADING_ELLIPSIS() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_OVERRUN_LEADING_ELLIPSIS", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-overrun-leading-word-ellipsis
     */
    @Test
    public void SplitMenuButtons_TEXT_OVERRUN_LEADING_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_OVERRUN_LEADING_WORD_ELLIPSIS", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-text-overrun-word-ellipsis
     */
    @Test
    public void SplitMenuButtons_TEXT_OVERRUN_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TEXT_OVERRUN_WORD_ELLIPSIS", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-translate-x
     */
    @Test
    public void SplitMenuButtons_TRANSLATE_X() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TRANSLATE-X", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-translate-y
     */
    @Test
    public void SplitMenuButtons_TRANSLATE_Y() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-underline
     */
    @Test
    public void SplitMenuButtons_UNDERLINE() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "UNDERLINE", true);
    }

    /**
     * test  SplitMenuButton with css: -fx-wrap-text
     */
    @Test
    public void SplitMenuButtons_WRAP_TEXT() throws Exception {
       testAdditionalAction(SplitMenuButtons.name(), "WRAP-TEXT", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
