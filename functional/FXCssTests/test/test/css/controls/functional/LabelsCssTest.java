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
import static test.css.controls.ControlPage.Labels;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class LabelsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(Labels);
    }

    /**
     * test  Label with css: -fx-alignment-baseline-center
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  Label with css: -fx-alignment-baseline-left
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  Label with css: -fx-alignment-baseline-right
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  Label with css: -fx-alignment-bottom-center
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  Label with css: -fx-alignment-bottom-left
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  Label with css: -fx-alignment-bottom-right
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  Label with css: -fx-alignment-center
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  Label with css: -fx-alignment-center-left
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  Label with css: -fx-alignment-center-right
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  Label with css: -fx-alignment-top-center
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  Label with css: -fx-alignment-top-left
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  Label with css: -fx-alignment-top-right
     */
    @Test
    @Smoke
    public void Labels_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(Labels.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  Label with css: -fx-background-color
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  Label with css: -fx-background-image
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  Label with css: -fx-background-inset
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  Label with css: -fx-background-position
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  Label with css: -fx-background-repeat-round
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  Label with css: -fx-background-repeat-space
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  Label with css: -fx-background-repeat-x-y
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  Label with css: -fx-background-size
     */
    @Test
    @Smoke
    public void Labels_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(Labels.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  Label with css: -fx-blend-mode
     */
    @Test
    @Smoke
    public void Labels_BLEND_MODE() throws Exception {
       testAdditionalAction(Labels.name(), "BLEND-MODE", true);
    }

    /**
     * test  Label with css: -fx-border-color
     */
    @Test
    @Smoke
    public void Labels_BORDER_COLOR() throws Exception {
       testAdditionalAction(Labels.name(), "BORDER-COLOR", true);
    }

    /**
     * test  Label with css: -fx-border-inset
     */
    @Test
    @Smoke
    public void Labels_BORDER_INSET() throws Exception {
       testAdditionalAction(Labels.name(), "BORDER-INSET", true);
    }

    /**
     * test  Label with css: -fx-border-style-dashed
     */
    @Test
    @Smoke
    public void Labels_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(Labels.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  Label with css: -fx-border-style-dotted
     */
    @Test
    @Smoke
    public void Labels_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(Labels.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  Label with css: -fx-border-width
     */
    @Test
    @Smoke
    public void Labels_BORDER_WIDTH() throws Exception {
       testAdditionalAction(Labels.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  Label with css: -fx-border-width-dashed
     */
    @Test
    @Smoke
    public void Labels_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(Labels.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  Label with css: -fx-border-width-dotted
     */
    @Test
    @Smoke
    public void Labels_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(Labels.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  Label with css: -fx-content-display-bottom
     */
    @Test
    @Smoke
    public void Labels_CONTENT_DISPLAY_BOTTOM() throws Exception {
       testAdditionalAction(Labels.name(), "CONTENT_DISPLAY_BOTTOM", true);
    }

    /**
     * test  Label with css: -fx-content-display-center
     */
    @Test
    @Smoke
    public void Labels_CONTENT_DISPLAY_CENTER() throws Exception {
       testAdditionalAction(Labels.name(), "CONTENT_DISPLAY_CENTER", true);
    }

    /**
     * test  Label with css: -fx-content-display-graphic-only
     */
    @Test
    @Smoke
    public void Labels_CONTENT_DISPLAY_GRAPHIC_ONLY() throws Exception {
       testAdditionalAction(Labels.name(), "CONTENT_DISPLAY_GRAPHIC_ONLY", true);
    }

    /**
     * test  Label with css: -fx-content-display-left
     */
    @Test
    @Smoke
    public void Labels_CONTENT_DISPLAY_LEFT() throws Exception {
       testAdditionalAction(Labels.name(), "CONTENT_DISPLAY_LEFT", true);
    }

    /**
     * test  Label with css: -fx-content-display-right
     */
    @Test
    @Smoke
    public void Labels_CONTENT_DISPLAY_RIGHT() throws Exception {
       testAdditionalAction(Labels.name(), "CONTENT_DISPLAY_RIGHT", true);
    }

    /**
     * test  Label with css: -fx-content-display-text-only
     */
    @Test
    @Smoke
    public void Labels_CONTENT_DISPLAY_TEXT_ONLY() throws Exception {
       testAdditionalAction(Labels.name(), "CONTENT_DISPLAY_TEXT_ONLY", true);
    }

    /**
     * test  Label with css: -fx-content-display-top
     */
    @Test
    @Smoke
    public void Labels_CONTENT_DISPLAY_TOP() throws Exception {
       testAdditionalAction(Labels.name(), "CONTENT_DISPLAY_TOP", true);
    }

    /**
     * test  Label with css: -fx-drop-shadow
     */
    @Test
    @Smoke
    public void Labels_DROP_SHADOW() throws Exception {
       testAdditionalAction(Labels.name(), "DROP_SHADOW", true);
    }

    /**
     * test  Label with css: -fx-ellipsis-string
     */
    @Test
    @Smoke
    public void Labels_ELLIPSIS_STRING() throws Exception {
       testAdditionalAction(Labels.name(), "ELLIPSIS-STRING", true);
    }

    /**
     * test  Label with css: -fx-font-family
     */
    @Test
    @Smoke
    public void Labels_FONT_FAMILY() throws Exception {
       testAdditionalAction(Labels.name(), "FONT-FAMILY", true);
    }

    /**
     * test  Label with css: -fx-font-size
     */
    @Test
    @Smoke
    public void Labels_FONT_SIZE() throws Exception {
       testAdditionalAction(Labels.name(), "FONT-SIZE", true);
    }

    /**
     * test  Label with css: -fx-font-style
     */
    @Test
    @Smoke
    public void Labels_FONT_STYLE() throws Exception {
       testAdditionalAction(Labels.name(), "FONT-STYLE", true);
    }

    /**
     * test  Label with css: -fx-font-weight
     */
    @Test
    @Smoke
    public void Labels_FONT_WEIGHT() throws Exception {
       testAdditionalAction(Labels.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  Label with css: -fx-font-smoothing-type-gray
     */
    @Test
    @Smoke
    public void Labels_FONT_SMOOTHING_TYPE_GRAY() throws Exception {
       testAdditionalAction(Labels.name(), "FONT_SMOOTHING_TYPE_GRAY", true);
    }

    /**
     * test  Label with css: -fx-font-smoothing-type-lcd
     */
    @Test
    @Smoke
    public void Labels_FONT_SMOOTHING_TYPE_LCD() throws Exception {
       testAdditionalAction(Labels.name(), "FONT_SMOOTHING_TYPE_LCD", true);
    }

    /**
     * test  Label with css: -fx-graphic
     */
    @Test
    @Smoke
    public void Labels_GRAPHIC() throws Exception {
       testAdditionalAction(Labels.name(), "GRAPHIC", true);
    }

    /**
     * test  Label with css: -fx-graphic-text-gap
     */
    @Test
    @Smoke
    public void Labels_GRAPHIC_TEXT_GAP() throws Exception {
       testAdditionalAction(Labels.name(), "GRAPHIC-TEXT-GAP", true);
    }

    /**
     * test  Label with css: -fx-image-border
     */
    @Test
    @Smoke
    public void Labels_IMAGE_BORDER() throws Exception {
       testAdditionalAction(Labels.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  Label with css: -fx-image-border-insets
     */
    @Test
    @Smoke
    public void Labels_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(Labels.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  Label with css: -fx-image-border-no-repeat
     */
    @Test
    @Smoke
    public void Labels_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(Labels.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  Label with css: -fx-image-border-repeat-x
     */
    @Test
    @Smoke
    public void Labels_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(Labels.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  Label with css: -fx-image-border-repeat-y
     */
    @Test
    @Smoke
    public void Labels_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(Labels.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  Label with css: -fx-image-border-round
     */
    @Test
    @Smoke
    public void Labels_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(Labels.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  Label with css: -fx-image-border-space
     */
    @Test
    @Smoke
    public void Labels_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(Labels.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  Label with css: -fx-inner-shadow
     */
    @Test
    @Smoke
    public void Labels_INNER_SHADOW() throws Exception {
       testAdditionalAction(Labels.name(), "INNER_SHADOW", true);
    }

    /**
     * test  Label with css: -fx-label-padding
     */
    @Test
    @Smoke
    public void Labels_LABEL_PADDING() throws Exception {
       testAdditionalAction(Labels.name(), "LABEL-PADDING", true);
    }

    /**
     * test  Label with css: -fx-opacity
     */
    @Test
    @Smoke
    public void Labels_OPACITY() throws Exception {
       testAdditionalAction(Labels.name(), "OPACITY", true);
    }

    /**
     * test  Label with css: -fx-padding
     */
    @Test
    @Smoke
    public void Labels_PADDING() throws Exception {
       testAdditionalAction(Labels.name(), "PADDING", true);
    }

    /**
     * test  Label with css: -fx-position-scale-shape
     */
    @Test
    @Smoke
    public void Labels_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Labels.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  Label with css: -fx-rotate
     */
    @Test
    @Smoke
    public void Labels_ROTATE() throws Exception {
       testAdditionalAction(Labels.name(), "ROTATE", true);
    }

    /**
     * test  Label with css: -fx-scale-shape
     */
    @Test
    @Smoke
    public void Labels_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Labels.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  Label with css: -fx-scale-x
     */
    @Test
    @Smoke
    public void Labels_SCALE_X() throws Exception {
       testAdditionalAction(Labels.name(), "SCALE-X", true);
    }

    /**
     * test  Label with css: -fx-scale-y
     */
    @Test
    @Smoke
    public void Labels_SCALE_Y() throws Exception {
       testAdditionalAction(Labels.name(), "SCALE-Y", true);
    }

    /**
     * test  Label with css: -fx-shape
     */
    @Test
    @Smoke
    public void Labels_SHAPE() throws Exception {
       testAdditionalAction(Labels.name(), "SHAPE", true);
    }

    /**
     * test  Label with css: -fx-snap-to-pixel
     */
    @Test
    @Smoke
    public void Labels_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(Labels.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  Label with css: -fx-strikethrough
     */
    @Test
    @Smoke
    public void Labels_STRIKETHROUGH() throws Exception {
       testAdditionalAction(Labels.name(), "STRIKETHROUGH", true);
    }

    /**
     * test  Label with css: -fx-text-fill
     */
    @Test
    @Smoke
    public void Labels_TEXT_FILL() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT-FILL", true);
    }

    /**
     * test  Label with css: -fx-text-alignment-center
     */
    @Test
    @Smoke
    public void Labels_TEXT_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ALIGNMENT_CENTER", true);
    }

    /**
     * test  Label with css: -fx-text-alignment-justify
     */
    @Test
    @Smoke
    public void Labels_TEXT_ALIGNMENT_JUSTIFY() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ALIGNMENT_JUSTIFY", true);
    }

    /**
     * test  Label with css: -fx-text-alignment-left
     */
    @Test
    @Smoke
    public void Labels_TEXT_ALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ALIGNMENT_LEFT", true);
    }

    /**
     * test  Label with css: -fx-text-alignment-right
     */
    @Test
    @Smoke
    public void Labels_TEXT_ALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ALIGNMENT_RIGHT", true);
    }

    /**
     * test  Label with css: -fx-text-origin-baseline
     */
    @Test
    @Smoke
    public void Labels_TEXT_ORIGIN_BASELINE() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ORIGIN_BASELINE", true);
    }

    /**
     * test  Label with css: -fx-text-origin-bottom
     */
    @Test
    @Smoke
    public void Labels_TEXT_ORIGIN_BOTTOM() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ORIGIN_BOTTOM", true);
    }

    /**
     * test  Label with css: -fx-text-origin-center
     */
    @Test
    @Smoke
    public void Labels_TEXT_ORIGIN_CENTER() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ORIGIN_CENTER", true);
    }

    /**
     * test  Label with css: -fx-text-origin-top
     */
    @Test
    @Smoke
    public void Labels_TEXT_ORIGIN_TOP() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_ORIGIN_TOP", true);
    }

    /**
     * test  Label with css: -fx-text-overrun-center-ellipsis
     */
    @Test
    @Smoke
    public void Labels_TEXT_OVERRUN_CENTER_ELLIPSIS() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_OVERRUN_CENTER_ELLIPSIS", true);
    }

    /**
     * test  Label with css: -fx-text-overrun-center-word-ellipsis
     */
    @Test
    @Smoke
    public void Labels_TEXT_OVERRUN_CENTER_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_OVERRUN_CENTER_WORD_ELLIPSIS", true);
    }

    /**
     * test  Label with css: -fx-text-overrun-clip
     */
    @Test
    @Smoke
    public void Labels_TEXT_OVERRUN_CLIP() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_OVERRUN_CLIP", true);
    }

    /**
     * test  Label with css: -fx-text-overrun-ellipsis
     */
    @Test
    @Smoke
    public void Labels_TEXT_OVERRUN_ELLIPSIS() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_OVERRUN_ELLIPSIS", true);
    }

    /**
     * test  Label with css: -fx-text-overrun-leading-ellipsis
     */
    @Test
    @Smoke
    public void Labels_TEXT_OVERRUN_LEADING_ELLIPSIS() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_OVERRUN_LEADING_ELLIPSIS", true);
    }

    /**
     * test  Label with css: -fx-text-overrun-leading-word-ellipsis
     */
    @Test
    @Smoke
    public void Labels_TEXT_OVERRUN_LEADING_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_OVERRUN_LEADING_WORD_ELLIPSIS", true);
    }

    /**
     * test  Label with css: -fx-text-overrun-word-ellipsis
     */
    @Test
    @Smoke
    public void Labels_TEXT_OVERRUN_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(Labels.name(), "TEXT_OVERRUN_WORD_ELLIPSIS", true);
    }

    /**
     * test  Label with css: -fx-translate-x
     */
    @Test
    @Smoke
    public void Labels_TRANSLATE_X() throws Exception {
       testAdditionalAction(Labels.name(), "TRANSLATE-X", true);
    }

    /**
     * test  Label with css: -fx-translate-y
     */
    @Test
    @Smoke
    public void Labels_TRANSLATE_Y() throws Exception {
       testAdditionalAction(Labels.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  Label with css: -fx-underline
     */
    @Test
    @Smoke
    public void Labels_UNDERLINE() throws Exception {
       testAdditionalAction(Labels.name(), "UNDERLINE", true);
    }

    /**
     * test  Label with css: -fx-wrap-text
     */
    @Test
    @Smoke
    public void Labels_WRAP_TEXT() throws Exception {
       testAdditionalAction(Labels.name(), "WRAP-TEXT", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
