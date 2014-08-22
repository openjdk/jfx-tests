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
import static test.css.controls.ControlPage.TitledPanes;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class TitledPanesCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(TitledPanes);
    }

    /**
     * test  TitledPane with css: -fx-alignment-baseline-center
     */
    @Test
    public void TitledPanes_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-baseline-left
     */
    @Test
    public void TitledPanes_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-baseline-right
     */
    @Test
    public void TitledPanes_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-bottom-center
     */
    @Test
    public void TitledPanes_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-bottom-left
     */
    @Test
    public void TitledPanes_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-bottom-right
     */
    @Test
    public void TitledPanes_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-center
     */
    @Test
    public void TitledPanes_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-center-left
     */
    @Test
    public void TitledPanes_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-center-right
     */
    @Test
    public void TitledPanes_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-top-center
     */
    @Test
    public void TitledPanes_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-top-left
     */
    @Test
    public void TitledPanes_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  TitledPane with css: -fx-alignment-top-right
     */
    @Test
    public void TitledPanes_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-background-color
     */
    @Test
    public void TitledPanes_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  TitledPane with css: -fx-background-image
     */
    @Test
    public void TitledPanes_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  TitledPane with css: -fx-background-inset
     */
    @Test
    public void TitledPanes_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  TitledPane with css: -fx-background-position
     */
    @Test
    public void TitledPanes_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  TitledPane with css: -fx-background-repeat-round
     */
    @Test
    public void TitledPanes_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  TitledPane with css: -fx-background-repeat-space
     */
    @Test
    public void TitledPanes_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  TitledPane with css: -fx-background-repeat-x-y
     */
    @Test
    public void TitledPanes_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  TitledPane with css: -fx-background-size
     */
    @Test
    public void TitledPanes_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  TitledPane with css: -fx-blend-mode
     */
    @Test
    public void TitledPanes_BLEND_MODE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BLEND-MODE", true);
    }

    /**
     * test  TitledPane with css: -fx-border-color
     */
    @Test
    public void TitledPanes_BORDER_COLOR() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BORDER-COLOR", true);
    }

    /**
     * test  TitledPane with css: -fx-border-inset
     */
    @Test
    public void TitledPanes_BORDER_INSET() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BORDER-INSET", true);
    }

    /**
     * test  TitledPane with css: -fx-border-style-dashed
     */
    @Test
    public void TitledPanes_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  TitledPane with css: -fx-border-style-dotted
     */
    @Test
    public void TitledPanes_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  TitledPane with css: -fx-border-width
     */
    @Test
    public void TitledPanes_BORDER_WIDTH() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  TitledPane with css: -fx-border-width-dashed
     */
    @Test
    public void TitledPanes_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  TitledPane with css: -fx-border-width-dotted
     */
    @Test
    public void TitledPanes_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(TitledPanes.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  TitledPane with css: -fx-content-display-bottom
     */
    @Test
    public void TitledPanes_CONTENT_DISPLAY_BOTTOM() throws Exception {
       testAdditionalAction(TitledPanes.name(), "CONTENT_DISPLAY_BOTTOM", true);
    }

    /**
     * test  TitledPane with css: -fx-content-display-center
     */
    @Test
    public void TitledPanes_CONTENT_DISPLAY_CENTER() throws Exception {
       testAdditionalAction(TitledPanes.name(), "CONTENT_DISPLAY_CENTER", true);
    }

    /**
     * test  TitledPane with css: -fx-content-display-graphic-only
     */
    @Test
    public void TitledPanes_CONTENT_DISPLAY_GRAPHIC_ONLY() throws Exception {
       testAdditionalAction(TitledPanes.name(), "CONTENT_DISPLAY_GRAPHIC_ONLY", true);
    }

    /**
     * test  TitledPane with css: -fx-content-display-left
     */
    @Test
    public void TitledPanes_CONTENT_DISPLAY_LEFT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "CONTENT_DISPLAY_LEFT", true);
    }

    /**
     * test  TitledPane with css: -fx-content-display-right
     */
    @Test
    public void TitledPanes_CONTENT_DISPLAY_RIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "CONTENT_DISPLAY_RIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-content-display-text-only
     */
    @Test
    public void TitledPanes_CONTENT_DISPLAY_TEXT_ONLY() throws Exception {
       testAdditionalAction(TitledPanes.name(), "CONTENT_DISPLAY_TEXT_ONLY", true);
    }

    /**
     * test  TitledPane with css: -fx-content-display-top
     */
    @Test
    public void TitledPanes_CONTENT_DISPLAY_TOP() throws Exception {
       testAdditionalAction(TitledPanes.name(), "CONTENT_DISPLAY_TOP", true);
    }

    /**
     * test  TitledPane with css: -fx-drop-shadow
     */
    @Test
    public void TitledPanes_DROP_SHADOW() throws Exception {
       testAdditionalAction(TitledPanes.name(), "DROP_SHADOW", true);
    }

    /**
     * test  TitledPane with css: -fx-ellipsis-string
     */
    @Test
    public void TitledPanes_ELLIPSIS_STRING() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ELLIPSIS-STRING", true);
    }

    /**
     * test  TitledPane with css: -fx-font-family
     */
    @Test
    public void TitledPanes_FONT_FAMILY() throws Exception {
       testAdditionalAction(TitledPanes.name(), "FONT-FAMILY", true);
    }

    /**
     * test  TitledPane with css: -fx-font-size
     */
    @Test
    public void TitledPanes_FONT_SIZE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "FONT-SIZE", true);
    }

    /**
     * test  TitledPane with css: -fx-font-style
     */
    @Test
    public void TitledPanes_FONT_STYLE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "FONT-STYLE", true);
    }

    /**
     * test  TitledPane with css: -fx-font-weight
     */
    @Test
    public void TitledPanes_FONT_WEIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-graphic
     */
    @Test
    public void TitledPanes_GRAPHIC() throws Exception {
       testAdditionalAction(TitledPanes.name(), "GRAPHIC", true);
    }

    /**
     * test  TitledPane with css: -fx-graphic-text-gap
     */
    @Test
    public void TitledPanes_GRAPHIC_TEXT_GAP() throws Exception {
       testAdditionalAction(TitledPanes.name(), "GRAPHIC-TEXT-GAP", true);
    }

    /**
     * test  TitledPane with css: -fx-image-border
     */
    @Test
    public void TitledPanes_IMAGE_BORDER() throws Exception {
       testAdditionalAction(TitledPanes.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  TitledPane with css: -fx-image-border-insets
     */
    @Test
    public void TitledPanes_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(TitledPanes.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  TitledPane with css: -fx-image-border-no-repeat
     */
    @Test
    public void TitledPanes_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  TitledPane with css: -fx-image-border-repeat-x
     */
    @Test
    public void TitledPanes_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(TitledPanes.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  TitledPane with css: -fx-image-border-repeat-y
     */
    @Test
    public void TitledPanes_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(TitledPanes.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  TitledPane with css: -fx-image-border-round
     */
    @Test
    public void TitledPanes_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(TitledPanes.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  TitledPane with css: -fx-image-border-space
     */
    @Test
    public void TitledPanes_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  TitledPane with css: -fx-inner-shadow
     */
    @Test
    public void TitledPanes_INNER_SHADOW() throws Exception {
       testAdditionalAction(TitledPanes.name(), "INNER_SHADOW", true);
    }

    /**
     * test  TitledPane with css: -fx-label-padding
     */
    @Test
    public void TitledPanes_LABEL_PADDING() throws Exception {
       testAdditionalAction(TitledPanes.name(), "LABEL-PADDING", true);
    }

    /**
     * test  TitledPane with css: -fx-max-height
     */
    @Test
    public void TitledPanes_MAX_HEIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "MAX-HEIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-max-width
     */
    @Test
    public void TitledPanes_MAX_WIDTH() throws Exception {
       testAdditionalAction(TitledPanes.name(), "MAX-WIDTH", true);
    }

    /**
     * test  TitledPane with css: -fx-min-height
     */
    @Test
    public void TitledPanes_MIN_HEIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "MIN-HEIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-min-width
     */
    @Test
    public void TitledPanes_MIN_WIDTH() throws Exception {
       testAdditionalAction(TitledPanes.name(), "MIN-WIDTH", true);
    }

    /**
     * test  TitledPane with css: -fx-opacity
     */
    @Test
    public void TitledPanes_OPACITY() throws Exception {
       testAdditionalAction(TitledPanes.name(), "OPACITY", true);
    }

    /**
     * test  TitledPane with css: -fx-padding
     */
    @Test
    public void TitledPanes_PADDING() throws Exception {
       testAdditionalAction(TitledPanes.name(), "PADDING", true);
    }

    /**
     * test  TitledPane with css: -fx-position-scale-shape
     */
    @Test
    public void TitledPanes_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  TitledPane with css: -fx-pref-height
     */
    @Test
    public void TitledPanes_PREF_HEIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "PREF-HEIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-pref-width
     */
    @Test
    public void TitledPanes_PREF_WIDTH() throws Exception {
       testAdditionalAction(TitledPanes.name(), "PREF-WIDTH", true);
    }

    /**
     * test  TitledPane with css: -fx-rotate
     */
    @Test
    public void TitledPanes_ROTATE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "ROTATE", true);
    }

    /**
     * test  TitledPane with css: -fx-scale-shape
     */
    @Test
    public void TitledPanes_SCALE_SHAPE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  TitledPane with css: -fx-scale-x
     */
    @Test
    public void TitledPanes_SCALE_X() throws Exception {
       testAdditionalAction(TitledPanes.name(), "SCALE-X", true);
    }

    /**
     * test  TitledPane with css: -fx-scale-y
     */
    @Test
    public void TitledPanes_SCALE_Y() throws Exception {
       testAdditionalAction(TitledPanes.name(), "SCALE-Y", true);
    }

    /**
     * test  TitledPane with css: -fx-shape
     */
    @Test
    public void TitledPanes_SHAPE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "SHAPE", true);
    }

    /**
     * test  TitledPane with css: -fx-snap-to-pixel
     */
    @Test
    public void TitledPanes_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(TitledPanes.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  TitledPane with css: -fx-text-fill
     */
    @Test
    public void TitledPanes_TEXT_FILL() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT-FILL", true);
    }

    /**
     * test  TitledPane with css: -fx-text-alignment-center
     */
    @Test
    public void TitledPanes_TEXT_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_ALIGNMENT_CENTER", true);
    }

    /**
     * test  TitledPane with css: -fx-text-alignment-justify
     */
    @Test
    public void TitledPanes_TEXT_ALIGNMENT_JUSTIFY() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_ALIGNMENT_JUSTIFY", true);
    }

    /**
     * test  TitledPane with css: -fx-text-alignment-left
     */
    @Test
    public void TitledPanes_TEXT_ALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_ALIGNMENT_LEFT", true);
    }

    /**
     * test  TitledPane with css: -fx-text-alignment-right
     */
    @Test
    public void TitledPanes_TEXT_ALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_ALIGNMENT_RIGHT", true);
    }

    /**
     * test  TitledPane with css: -fx-text-overrun-center-ellipsis
     */
    @Test
    public void TitledPanes_TEXT_OVERRUN_CENTER_ELLIPSIS() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_OVERRUN_CENTER_ELLIPSIS", true);
    }

    /**
     * test  TitledPane with css: -fx-text-overrun-center-word-ellipsis
     */
    @Test
    public void TitledPanes_TEXT_OVERRUN_CENTER_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_OVERRUN_CENTER_WORD_ELLIPSIS", true);
    }

    /**
     * test  TitledPane with css: -fx-text-overrun-clip
     */
    @Test
    public void TitledPanes_TEXT_OVERRUN_CLIP() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_OVERRUN_CLIP", true);
    }

    /**
     * test  TitledPane with css: -fx-text-overrun-ellipsis
     */
    @Test
    public void TitledPanes_TEXT_OVERRUN_ELLIPSIS() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_OVERRUN_ELLIPSIS", true);
    }

    /**
     * test  TitledPane with css: -fx-text-overrun-leading-ellipsis
     */
    @Test
    public void TitledPanes_TEXT_OVERRUN_LEADING_ELLIPSIS() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_OVERRUN_LEADING_ELLIPSIS", true);
    }

    /**
     * test  TitledPane with css: -fx-text-overrun-leading-word-ellipsis
     */
    @Test
    public void TitledPanes_TEXT_OVERRUN_LEADING_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_OVERRUN_LEADING_WORD_ELLIPSIS", true);
    }

    /**
     * test  TitledPane with css: -fx-text-overrun-word-ellipsis
     */
    @Test
    public void TitledPanes_TEXT_OVERRUN_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TEXT_OVERRUN_WORD_ELLIPSIS", true);
    }

    /**
     * test  TitledPane with css: -fx-translate-x
     */
    @Test
    public void TitledPanes_TRANSLATE_X() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TRANSLATE-X", true);
    }

    /**
     * test  TitledPane with css: -fx-translate-y
     */
    @Test
    public void TitledPanes_TRANSLATE_Y() throws Exception {
       testAdditionalAction(TitledPanes.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  TitledPane with css: -fx-underline
     */
    @Test
    public void TitledPanes_UNDERLINE() throws Exception {
       testAdditionalAction(TitledPanes.name(), "UNDERLINE", true);
    }

    /**
     * test  TitledPane with css: -fx-wrap-text
     */
    @Test
    public void TitledPanes_WRAP_TEXT() throws Exception {
       testAdditionalAction(TitledPanes.name(), "WRAP-TEXT", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
