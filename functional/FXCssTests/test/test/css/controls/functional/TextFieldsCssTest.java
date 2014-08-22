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
import static test.css.controls.ControlPage.TextFields;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class TextFieldsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(TextFields);
    }

    /**
     * test  TextField with css: -fx-alignment-baseline-center
     */
    @Test
    public void TextFields_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  TextField with css: -fx-alignment-baseline-left
     */
    @Test
    public void TextFields_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  TextField with css: -fx-alignment-baseline-right
     */
    @Test
    public void TextFields_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  TextField with css: -fx-alignment-bottom-center
     */
    @Test
    public void TextFields_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  TextField with css: -fx-alignment-bottom-left
     */
    @Test
    public void TextFields_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  TextField with css: -fx-alignment-bottom-right
     */
    @Test
    public void TextFields_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  TextField with css: -fx-alignment-center
     */
    @Test
    public void TextFields_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  TextField with css: -fx-alignment-center-left
     */
    @Test
    public void TextFields_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  TextField with css: -fx-alignment-center-right
     */
    @Test
    public void TextFields_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  TextField with css: -fx-alignment-top-center
     */
    @Test
    public void TextFields_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  TextField with css: -fx-alignment-top-left
     */
    @Test
    public void TextFields_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  TextField with css: -fx-alignment-top-right
     */
    @Test
    public void TextFields_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  TextField with css: -fx-background-color
     */
    @Test
    public void TextFields_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  TextField with css: -fx-background-image
     */
    @Test
    public void TextFields_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  TextField with css: -fx-background-inset
     */
    @Test
    public void TextFields_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  TextField with css: -fx-background-position
     */
    @Test
    public void TextFields_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  TextField with css: -fx-background-repeat-round
     */
    @Test
    public void TextFields_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  TextField with css: -fx-background-repeat-space
     */
    @Test
    public void TextFields_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  TextField with css: -fx-background-repeat-x-y
     */
    @Test
    public void TextFields_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  TextField with css: -fx-background-size
     */
    @Test
    public void TextFields_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(TextFields.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  TextField with css: -fx-blend-mode
     */
    @Test
    public void TextFields_BLEND_MODE() throws Exception {
       testAdditionalAction(TextFields.name(), "BLEND-MODE", true);
    }

    /**
     * test  TextField with css: -fx-border-color
     */
    @Test
    public void TextFields_BORDER_COLOR() throws Exception {
       testAdditionalAction(TextFields.name(), "BORDER-COLOR", true);
    }

    /**
     * test  TextField with css: -fx-border-inset
     */
    @Test
    public void TextFields_BORDER_INSET() throws Exception {
       testAdditionalAction(TextFields.name(), "BORDER-INSET", true);
    }

    /**
     * test  TextField with css: -fx-border-style-dashed
     */
    @Test
    public void TextFields_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(TextFields.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  TextField with css: -fx-border-style-dotted
     */
    @Test
    public void TextFields_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(TextFields.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  TextField with css: -fx-border-width
     */
    @Test
    public void TextFields_BORDER_WIDTH() throws Exception {
       testAdditionalAction(TextFields.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  TextField with css: -fx-border-width-dashed
     */
    @Test
    public void TextFields_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(TextFields.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  TextField with css: -fx-border-width-dotted
     */
    @Test
    public void TextFields_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(TextFields.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  TextField with css: -fx-drop-shadow
     */
    @Test
    public void TextFields_DROP_SHADOW() throws Exception {
       testAdditionalAction(TextFields.name(), "DROP_SHADOW", true);
    }

    /**
     * test  TextField with css: -fx-font-family
     */
    @Test
    public void TextFields_FONT_FAMILY() throws Exception {
       testAdditionalAction(TextFields.name(), "FONT-FAMILY", true);
    }

    /**
     * test  TextField with css: -fx-font-size
     */
    @Test
    public void TextFields_FONT_SIZE() throws Exception {
       testAdditionalAction(TextFields.name(), "FONT-SIZE", true);
    }

    /**
     * test  TextField with css: -fx-font-style
     */
    @Test
    public void TextFields_FONT_STYLE() throws Exception {
       testAdditionalAction(TextFields.name(), "FONT-STYLE", true);
    }

    /**
     * test  TextField with css: -fx-font-weight
     */
    @Test
    public void TextFields_FONT_WEIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  TextField with css: -fx-image-border
     */
    @Test
    public void TextFields_IMAGE_BORDER() throws Exception {
       testAdditionalAction(TextFields.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  TextField with css: -fx-image-border-insets
     */
    @Test
    public void TextFields_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(TextFields.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  TextField with css: -fx-image-border-no-repeat
     */
    @Test
    public void TextFields_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(TextFields.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  TextField with css: -fx-image-border-repeat-x
     */
    @Test
    public void TextFields_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(TextFields.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  TextField with css: -fx-image-border-repeat-y
     */
    @Test
    public void TextFields_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(TextFields.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  TextField with css: -fx-image-border-round
     */
    @Test
    public void TextFields_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(TextFields.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  TextField with css: -fx-image-border-space
     */
    @Test
    public void TextFields_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(TextFields.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  TextField with css: -fx-inner-shadow
     */
    @Test
    public void TextFields_INNER_SHADOW() throws Exception {
       testAdditionalAction(TextFields.name(), "INNER_SHADOW", true);
    }

    /**
     * test  TextField with css: -fx-max-height
     */
    @Test
    public void TextFields_MAX_HEIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "MAX-HEIGHT", true);
    }

    /**
     * test  TextField with css: -fx-max-width
     */
    @Test
    public void TextFields_MAX_WIDTH() throws Exception {
       testAdditionalAction(TextFields.name(), "MAX-WIDTH", true);
    }

    /**
     * test  TextField with css: -fx-min-height
     */
    @Test
    public void TextFields_MIN_HEIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "MIN-HEIGHT", true);
    }

    /**
     * test  TextField with css: -fx-min-width
     */
    @Test
    public void TextFields_MIN_WIDTH() throws Exception {
       testAdditionalAction(TextFields.name(), "MIN-WIDTH", true);
    }

    /**
     * test  TextField with css: -fx-opacity
     */
    @Test
    public void TextFields_OPACITY() throws Exception {
       testAdditionalAction(TextFields.name(), "OPACITY", true);
    }

    /**
     * test  TextField with css: -fx-padding
     */
    @Test
    public void TextFields_PADDING() throws Exception {
       testAdditionalAction(TextFields.name(), "PADDING", true);
    }

    /**
     * test  TextField with css: -fx-position-scale-shape
     */
    @Test
    public void TextFields_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(TextFields.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  TextField with css: -fx-pref-height
     */
    @Test
    public void TextFields_PREF_HEIGHT() throws Exception {
       testAdditionalAction(TextFields.name(), "PREF-HEIGHT", true);
    }

    /**
     * test  TextField with css: -fx-pref-width
     */
    @Test
    public void TextFields_PREF_WIDTH() throws Exception {
       testAdditionalAction(TextFields.name(), "PREF-WIDTH", true);
    }

    /**
     * test  TextField with css: -fx-prompt-text-fill
     */
    @Test
    public void TextFields_PROMPT_TEXT_FILL() throws Exception {
       testAdditionalAction(TextFields.name(), "PROMPT-TEXT-FILL", true);
    }

    /**
     * test  TextField with css: -fx-rotate
     */
    @Test
    public void TextFields_ROTATE() throws Exception {
       testAdditionalAction(TextFields.name(), "ROTATE", true);
    }

    /**
     * test  TextField with css: -fx-scale-shape
     */
    @Test
    public void TextFields_SCALE_SHAPE() throws Exception {
       testAdditionalAction(TextFields.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  TextField with css: -fx-scale-x
     */
    @Test
    public void TextFields_SCALE_X() throws Exception {
       testAdditionalAction(TextFields.name(), "SCALE-X", true);
    }

    /**
     * test  TextField with css: -fx-scale-y
     */
    @Test
    public void TextFields_SCALE_Y() throws Exception {
       testAdditionalAction(TextFields.name(), "SCALE-Y", true);
    }

    /**
     * test  TextField with css: -fx-shape
     */
    @Test
    public void TextFields_SHAPE() throws Exception {
       testAdditionalAction(TextFields.name(), "SHAPE", true);
    }

    /**
     * test  TextField with css: -fx-snap-to-pixel
     */
    @Test
    public void TextFields_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(TextFields.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  TextField with css: -fx-text-fill
     */
    @Test
    public void TextFields_TEXT_FILL() throws Exception {
       testAdditionalAction(TextFields.name(), "TEXT-FILL", true);
    }

    /**
     * test  TextField with css: -fx-translate-x
     */
    @Test
    public void TextFields_TRANSLATE_X() throws Exception {
       testAdditionalAction(TextFields.name(), "TRANSLATE-X", true);
    }

    /**
     * test  TextField with css: -fx-translate-y
     */
    @Test
    public void TextFields_TRANSLATE_Y() throws Exception {
       testAdditionalAction(TextFields.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
