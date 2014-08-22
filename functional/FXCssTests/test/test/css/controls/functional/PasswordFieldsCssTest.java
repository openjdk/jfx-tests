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
import static test.css.controls.ControlPage.PasswordFields;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class PasswordFieldsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(PasswordFields);
    }

    /**
     * test  PasswordField with css: -fx-alignment-baseline-center
     */
    @Test
    public void PasswordFields_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-baseline-left
     */
    @Test
    public void PasswordFields_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-baseline-right
     */
    @Test
    public void PasswordFields_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-bottom-center
     */
    @Test
    public void PasswordFields_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-bottom-left
     */
    @Test
    public void PasswordFields_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-bottom-right
     */
    @Test
    public void PasswordFields_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-center
     */
    @Test
    public void PasswordFields_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-center-left
     */
    @Test
    public void PasswordFields_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-center-right
     */
    @Test
    public void PasswordFields_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-top-center
     */
    @Test
    public void PasswordFields_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-top-left
     */
    @Test
    public void PasswordFields_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  PasswordField with css: -fx-alignment-top-right
     */
    @Test
    public void PasswordFields_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  PasswordField with css: -fx-background-color
     */
    @Test
    public void PasswordFields_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  PasswordField with css: -fx-background-image
     */
    @Test
    public void PasswordFields_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  PasswordField with css: -fx-background-inset
     */
    @Test
    public void PasswordFields_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  PasswordField with css: -fx-background-position
     */
    @Test
    public void PasswordFields_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  PasswordField with css: -fx-background-repeat-round
     */
    @Test
    public void PasswordFields_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  PasswordField with css: -fx-background-repeat-space
     */
    @Test
    public void PasswordFields_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  PasswordField with css: -fx-background-repeat-x-y
     */
    @Test
    public void PasswordFields_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  PasswordField with css: -fx-background-size
     */
    @Test
    public void PasswordFields_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  PasswordField with css: -fx-blend-mode
     */
    @Test
    public void PasswordFields_BLEND_MODE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BLEND-MODE", true);
    }

    /**
     * test  PasswordField with css: -fx-border-color
     */
    @Test
    public void PasswordFields_BORDER_COLOR() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BORDER-COLOR", true);
    }

    /**
     * test  PasswordField with css: -fx-border-inset
     */
    @Test
    public void PasswordFields_BORDER_INSET() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BORDER-INSET", true);
    }

    /**
     * test  PasswordField with css: -fx-border-style-dashed
     */
    @Test
    public void PasswordFields_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  PasswordField with css: -fx-border-style-dotted
     */
    @Test
    public void PasswordFields_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  PasswordField with css: -fx-border-width
     */
    @Test
    public void PasswordFields_BORDER_WIDTH() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  PasswordField with css: -fx-border-width-dashed
     */
    @Test
    public void PasswordFields_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  PasswordField with css: -fx-border-width-dotted
     */
    @Test
    public void PasswordFields_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(PasswordFields.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  PasswordField with css: -fx-drop-shadow
     */
    @Test
    public void PasswordFields_DROP_SHADOW() throws Exception {
       testAdditionalAction(PasswordFields.name(), "DROP_SHADOW", true);
    }

    /**
     * test  PasswordField with css: -fx-image-border
     */
    @Test
    public void PasswordFields_IMAGE_BORDER() throws Exception {
       testAdditionalAction(PasswordFields.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  PasswordField with css: -fx-image-border-insets
     */
    @Test
    public void PasswordFields_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(PasswordFields.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  PasswordField with css: -fx-image-border-no-repeat
     */
    @Test
    public void PasswordFields_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(PasswordFields.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  PasswordField with css: -fx-image-border-repeat-x
     */
    @Test
    public void PasswordFields_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(PasswordFields.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  PasswordField with css: -fx-image-border-repeat-y
     */
    @Test
    public void PasswordFields_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(PasswordFields.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  PasswordField with css: -fx-image-border-round
     */
    @Test
    public void PasswordFields_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(PasswordFields.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  PasswordField with css: -fx-image-border-space
     */
    @Test
    public void PasswordFields_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  PasswordField with css: -fx-inner-shadow
     */
    @Test
    public void PasswordFields_INNER_SHADOW() throws Exception {
       testAdditionalAction(PasswordFields.name(), "INNER_SHADOW", true);
    }

    /**
     * test  PasswordField with css: -fx-opacity
     */
    @Test
    public void PasswordFields_OPACITY() throws Exception {
       testAdditionalAction(PasswordFields.name(), "OPACITY", true);
    }

    /**
     * test  PasswordField with css: -fx-padding
     */
    @Test
    public void PasswordFields_PADDING() throws Exception {
       testAdditionalAction(PasswordFields.name(), "PADDING", true);
    }

    /**
     * test  PasswordField with css: -fx-position-scale-shape
     */
    @Test
    public void PasswordFields_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  PasswordField with css: -fx-rotate
     */
    @Test
    public void PasswordFields_ROTATE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "ROTATE", true);
    }

    /**
     * test  PasswordField with css: -fx-scale-shape
     */
    @Test
    public void PasswordFields_SCALE_SHAPE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  PasswordField with css: -fx-scale-x
     */
    @Test
    public void PasswordFields_SCALE_X() throws Exception {
       testAdditionalAction(PasswordFields.name(), "SCALE-X", true);
    }

    /**
     * test  PasswordField with css: -fx-scale-y
     */
    @Test
    public void PasswordFields_SCALE_Y() throws Exception {
       testAdditionalAction(PasswordFields.name(), "SCALE-Y", true);
    }

    /**
     * test  PasswordField with css: -fx-shape
     */
    @Test
    public void PasswordFields_SHAPE() throws Exception {
       testAdditionalAction(PasswordFields.name(), "SHAPE", true);
    }

    /**
     * test  PasswordField with css: -fx-snap-to-pixel
     */
    @Test
    public void PasswordFields_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(PasswordFields.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  PasswordField with css: -fx-translate-x
     */
    @Test
    public void PasswordFields_TRANSLATE_X() throws Exception {
       testAdditionalAction(PasswordFields.name(), "TRANSLATE-X", true);
    }

    /**
     * test  PasswordField with css: -fx-translate-y
     */
    @Test
    public void PasswordFields_TRANSLATE_Y() throws Exception {
       testAdditionalAction(PasswordFields.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
