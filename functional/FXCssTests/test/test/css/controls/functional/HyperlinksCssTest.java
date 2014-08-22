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
import static test.css.controls.ControlPage.Hyperlinks;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class HyperlinksCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(Hyperlinks);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-baseline-center
     */
    @Test
    public void Hyperlinks_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-baseline-left
     */
    @Test
    public void Hyperlinks_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-baseline-right
     */
    @Test
    public void Hyperlinks_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-bottom-center
     */
    @Test
    public void Hyperlinks_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-bottom-left
     */
    @Test
    public void Hyperlinks_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-bottom-right
     */
    @Test
    public void Hyperlinks_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-center
     */
    @Test
    public void Hyperlinks_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-center-left
     */
    @Test
    public void Hyperlinks_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-center-right
     */
    @Test
    public void Hyperlinks_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-top-center
     */
    @Test
    public void Hyperlinks_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-top-left
     */
    @Test
    public void Hyperlinks_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  Hyperlink with css: -fx-alignment-top-right
     */
    @Test
    public void Hyperlinks_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-color
     */
    @Test
    public void Hyperlinks_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-image
     */
    @Test
    public void Hyperlinks_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-inset
     */
    @Test
    public void Hyperlinks_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-position
     */
    @Test
    public void Hyperlinks_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-repeat-round
     */
    @Test
    public void Hyperlinks_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-repeat-space
     */
    @Test
    public void Hyperlinks_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-repeat-x-y
     */
    @Test
    public void Hyperlinks_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  Hyperlink with css: -fx-background-size
     */
    @Test
    public void Hyperlinks_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  Hyperlink with css: -fx-blend-mode
     */
    @Test
    public void Hyperlinks_BLEND_MODE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BLEND-MODE", true);
    }

    /**
     * test  Hyperlink with css: -fx-border-color
     */
    @Test
    public void Hyperlinks_BORDER_COLOR() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BORDER-COLOR", true);
    }

    /**
     * test  Hyperlink with css: -fx-border-inset
     */
    @Test
    public void Hyperlinks_BORDER_INSET() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BORDER-INSET", true);
    }

    /**
     * test  Hyperlink with css: -fx-border-style-dashed
     */
    @Test
    public void Hyperlinks_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  Hyperlink with css: -fx-border-style-dotted
     */
    @Test
    public void Hyperlinks_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  Hyperlink with css: -fx-border-width
     */
    @Test
    public void Hyperlinks_BORDER_WIDTH() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  Hyperlink with css: -fx-border-width-dashed
     */
    @Test
    public void Hyperlinks_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  Hyperlink with css: -fx-border-width-dotted
     */
    @Test
    public void Hyperlinks_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  Hyperlink with css: -fx-content-display-bottom
     */
    @Test
    public void Hyperlinks_CONTENT_DISPLAY_BOTTOM() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "CONTENT_DISPLAY_BOTTOM", true);
    }

    /**
     * test  Hyperlink with css: -fx-content-display-center
     */
    @Test
    public void Hyperlinks_CONTENT_DISPLAY_CENTER() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "CONTENT_DISPLAY_CENTER", true);
    }

    /**
     * test  Hyperlink with css: -fx-content-display-graphic-only
     */
    @Test
    public void Hyperlinks_CONTENT_DISPLAY_GRAPHIC_ONLY() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "CONTENT_DISPLAY_GRAPHIC_ONLY", true);
    }

    /**
     * test  Hyperlink with css: -fx-content-display-left
     */
    @Test
    public void Hyperlinks_CONTENT_DISPLAY_LEFT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "CONTENT_DISPLAY_LEFT", true);
    }

    /**
     * test  Hyperlink with css: -fx-content-display-right
     */
    @Test
    public void Hyperlinks_CONTENT_DISPLAY_RIGHT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "CONTENT_DISPLAY_RIGHT", true);
    }

    /**
     * test  Hyperlink with css: -fx-content-display-text-only
     */
    @Test
    public void Hyperlinks_CONTENT_DISPLAY_TEXT_ONLY() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "CONTENT_DISPLAY_TEXT_ONLY", true);
    }

    /**
     * test  Hyperlink with css: -fx-content-display-top
     */
    @Test
    public void Hyperlinks_CONTENT_DISPLAY_TOP() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "CONTENT_DISPLAY_TOP", true);
    }

    /**
     * test  Hyperlink with css: -fx-drop-shadow
     */
    @Test
    public void Hyperlinks_DROP_SHADOW() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "DROP_SHADOW", true);
    }

    /**
     * test  Hyperlink with css: -fx-ellipsis-string
     */
    @Test
    public void Hyperlinks_ELLIPSIS_STRING() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ELLIPSIS-STRING", true);
    }

    /**
     * test  Hyperlink with css: -fx-font-family
     */
    @Test
    public void Hyperlinks_FONT_FAMILY() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "FONT-FAMILY", true);
    }

    /**
     * test  Hyperlink with css: -fx-font-size
     */
    @Test
    public void Hyperlinks_FONT_SIZE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "FONT-SIZE", true);
    }

    /**
     * test  Hyperlink with css: -fx-font-style
     */
    @Test
    public void Hyperlinks_FONT_STYLE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "FONT-STYLE", true);
    }

    /**
     * test  Hyperlink with css: -fx-font-weight
     */
    @Test
    public void Hyperlinks_FONT_WEIGHT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  Hyperlink with css: -fx-graphic
     */
    @Test
    public void Hyperlinks_GRAPHIC() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "GRAPHIC", true);
    }

    /**
     * test  Hyperlink with css: -fx-graphic-text-gap
     */
    @Test
    public void Hyperlinks_GRAPHIC_TEXT_GAP() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "GRAPHIC-TEXT-GAP", true);
    }

    /**
     * test  Hyperlink with css: -fx-image-border
     */
    @Test
    public void Hyperlinks_IMAGE_BORDER() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  Hyperlink with css: -fx-image-border-insets
     */
    @Test
    public void Hyperlinks_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  Hyperlink with css: -fx-image-border-no-repeat
     */
    @Test
    public void Hyperlinks_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  Hyperlink with css: -fx-image-border-repeat-x
     */
    @Test
    public void Hyperlinks_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  Hyperlink with css: -fx-image-border-repeat-y
     */
    @Test
    public void Hyperlinks_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  Hyperlink with css: -fx-image-border-round
     */
    @Test
    public void Hyperlinks_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  Hyperlink with css: -fx-image-border-space
     */
    @Test
    public void Hyperlinks_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  Hyperlink with css: -fx-inner-shadow
     */
    @Test
    public void Hyperlinks_INNER_SHADOW() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "INNER_SHADOW", true);
    }

    /**
     * test  Hyperlink with css: -fx-label-padding
     */
    @Test
    public void Hyperlinks_LABEL_PADDING() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "LABEL-PADDING", true);
    }

    /**
     * test  Hyperlink with css: -fx-opacity
     */
    @Test
    public void Hyperlinks_OPACITY() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "OPACITY", true);
    }

    /**
     * test  Hyperlink with css: -fx-padding
     */
    @Test
    public void Hyperlinks_PADDING() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "PADDING", true);
    }

    /**
     * test  Hyperlink with css: -fx-position-scale-shape
     */
    @Test
    public void Hyperlinks_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  Hyperlink with css: -fx-rotate
     */
    @Test
    public void Hyperlinks_ROTATE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "ROTATE", true);
    }

    /**
     * test  Hyperlink with css: -fx-scale-shape
     */
    @Test
    public void Hyperlinks_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  Hyperlink with css: -fx-scale-x
     */
    @Test
    public void Hyperlinks_SCALE_X() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "SCALE-X", true);
    }

    /**
     * test  Hyperlink with css: -fx-scale-y
     */
    @Test
    public void Hyperlinks_SCALE_Y() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "SCALE-Y", true);
    }

    /**
     * test  Hyperlink with css: -fx-shape
     */
    @Test
    public void Hyperlinks_SHAPE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "SHAPE", true);
    }

    /**
     * test  Hyperlink with css: -fx-snap-to-pixel
     */
    @Test
    public void Hyperlinks_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-fill
     */
    @Test
    public void Hyperlinks_TEXT_FILL() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT-FILL", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-alignment-center
     */
    @Test
    public void Hyperlinks_TEXT_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_ALIGNMENT_CENTER", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-alignment-justify
     */
    @Test
    public void Hyperlinks_TEXT_ALIGNMENT_JUSTIFY() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_ALIGNMENT_JUSTIFY", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-alignment-left
     */
    @Test
    public void Hyperlinks_TEXT_ALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_ALIGNMENT_LEFT", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-alignment-right
     */
    @Test
    public void Hyperlinks_TEXT_ALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_ALIGNMENT_RIGHT", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-overrun-center-ellipsis
     */
    @Test
    public void Hyperlinks_TEXT_OVERRUN_CENTER_ELLIPSIS() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_OVERRUN_CENTER_ELLIPSIS", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-overrun-center-word-ellipsis
     */
    @Test
    public void Hyperlinks_TEXT_OVERRUN_CENTER_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_OVERRUN_CENTER_WORD_ELLIPSIS", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-overrun-clip
     */
    @Test
    public void Hyperlinks_TEXT_OVERRUN_CLIP() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_OVERRUN_CLIP", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-overrun-ellipsis
     */
    @Test
    public void Hyperlinks_TEXT_OVERRUN_ELLIPSIS() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_OVERRUN_ELLIPSIS", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-overrun-leading-ellipsis
     */
    @Test
    public void Hyperlinks_TEXT_OVERRUN_LEADING_ELLIPSIS() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_OVERRUN_LEADING_ELLIPSIS", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-overrun-leading-word-ellipsis
     */
    @Test
    public void Hyperlinks_TEXT_OVERRUN_LEADING_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_OVERRUN_LEADING_WORD_ELLIPSIS", true);
    }

    /**
     * test  Hyperlink with css: -fx-text-overrun-word-ellipsis
     */
    @Test
    public void Hyperlinks_TEXT_OVERRUN_WORD_ELLIPSIS() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TEXT_OVERRUN_WORD_ELLIPSIS", true);
    }

    /**
     * test  Hyperlink with css: -fx-translate-x
     */
    @Test
    public void Hyperlinks_TRANSLATE_X() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TRANSLATE-X", true);
    }

    /**
     * test  Hyperlink with css: -fx-translate-y
     */
    @Test
    public void Hyperlinks_TRANSLATE_Y() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  Hyperlink with css: -fx-underline
     */
    @Test
    public void Hyperlinks_UNDERLINE() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "UNDERLINE", true);
    }

    /**
     * test  Hyperlink with css: -fx-wrap-text
     */
    @Test
    public void Hyperlinks_WRAP_TEXT() throws Exception {
       testAdditionalAction(Hyperlinks.name(), "WRAP-TEXT", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
