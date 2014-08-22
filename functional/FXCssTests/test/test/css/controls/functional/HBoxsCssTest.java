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
import static test.css.controls.ControlPage.HBoxs;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class HBoxsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(HBoxs);
    }

    /**
     * test  HBox with css: -fx-alignment-baseline-center
     */
    @Test
    public void HBoxs_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  HBox with css: -fx-alignment-baseline-left
     */
    @Test
    public void HBoxs_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  HBox with css: -fx-alignment-baseline-right
     */
    @Test
    public void HBoxs_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  HBox with css: -fx-alignment-bottom-center
     */
    @Test
    public void HBoxs_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  HBox with css: -fx-alignment-bottom-left
     */
    @Test
    public void HBoxs_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  HBox with css: -fx-alignment-bottom-right
     */
    @Test
    public void HBoxs_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  HBox with css: -fx-alignment-center
     */
    @Test
    public void HBoxs_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  HBox with css: -fx-alignment-center-left
     */
    @Test
    public void HBoxs_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  HBox with css: -fx-alignment-center-right
     */
    @Test
    public void HBoxs_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  HBox with css: -fx-alignment-top-center
     */
    @Test
    public void HBoxs_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  HBox with css: -fx-alignment-top-left
     */
    @Test
    public void HBoxs_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  HBox with css: -fx-alignment-top-right
     */
    @Test
    public void HBoxs_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(HBoxs.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  HBox with css: -fx-background-color
     */
    @Test
    public void HBoxs_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  HBox with css: -fx-background-image
     */
    @Test
    public void HBoxs_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  HBox with css: -fx-background-inset
     */
    @Test
    public void HBoxs_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  HBox with css: -fx-background-position
     */
    @Test
    public void HBoxs_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  HBox with css: -fx-background-repeat-round
     */
    @Test
    public void HBoxs_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  HBox with css: -fx-background-repeat-space
     */
    @Test
    public void HBoxs_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  HBox with css: -fx-background-repeat-x-y
     */
    @Test
    public void HBoxs_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  HBox with css: -fx-background-size
     */
    @Test
    public void HBoxs_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(HBoxs.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  HBox with css: -fx-blend-mode
     */
    @Test
    public void HBoxs_BLEND_MODE() throws Exception {
       testAdditionalAction(HBoxs.name(), "BLEND-MODE", true);
    }

    /**
     * test  HBox with css: -fx-border-color
     */
    @Test
    public void HBoxs_BORDER_COLOR() throws Exception {
       testAdditionalAction(HBoxs.name(), "BORDER-COLOR", true);
    }

    /**
     * test  HBox with css: -fx-border-inset
     */
    @Test
    public void HBoxs_BORDER_INSET() throws Exception {
       testAdditionalAction(HBoxs.name(), "BORDER-INSET", true);
    }

    /**
     * test  HBox with css: -fx-border-style-dashed
     */
    @Test
    public void HBoxs_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(HBoxs.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  HBox with css: -fx-border-style-dotted
     */
    @Test
    public void HBoxs_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(HBoxs.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  HBox with css: -fx-border-width
     */
    @Test
    public void HBoxs_BORDER_WIDTH() throws Exception {
       testAdditionalAction(HBoxs.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  HBox with css: -fx-border-width-dashed
     */
    @Test
    public void HBoxs_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(HBoxs.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  HBox with css: -fx-border-width-dotted
     */
    @Test
    public void HBoxs_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(HBoxs.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  HBox with css: -fx-drop-shadow
     */
    @Test
    public void HBoxs_DROP_SHADOW() throws Exception {
       testAdditionalAction(HBoxs.name(), "DROP_SHADOW", true);
    }

    /**
     * test  HBox with css: -fx-fill-height
     */
    @Test
    @Smoke
    public void HBoxs_FILL_HEIGHT() throws Exception {
       testAdditionalAction(HBoxs.name(), "FILL-HEIGHT", true);
    }

    /**
     * test  HBox with css: -fx-image-border
     */
    @Test
    public void HBoxs_IMAGE_BORDER() throws Exception {
       testAdditionalAction(HBoxs.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  HBox with css: -fx-image-border-insets
     */
    @Test
    public void HBoxs_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(HBoxs.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  HBox with css: -fx-image-border-no-repeat
     */
    @Test
    public void HBoxs_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(HBoxs.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  HBox with css: -fx-image-border-repeat-x
     */
    @Test
    public void HBoxs_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(HBoxs.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  HBox with css: -fx-image-border-repeat-y
     */
    @Test
    public void HBoxs_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(HBoxs.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  HBox with css: -fx-image-border-round
     */
    @Test
    public void HBoxs_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(HBoxs.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  HBox with css: -fx-image-border-space
     */
    @Test
    public void HBoxs_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(HBoxs.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  HBox with css: -fx-inner-shadow
     */
    @Test
    public void HBoxs_INNER_SHADOW() throws Exception {
       testAdditionalAction(HBoxs.name(), "INNER_SHADOW", true);
    }

    /**
     * test  HBox with css: -fx-opacity
     */
    @Test
    public void HBoxs_OPACITY() throws Exception {
       testAdditionalAction(HBoxs.name(), "OPACITY", true);
    }

    /**
     * test  HBox with css: -fx-padding
     */
    @Test
    public void HBoxs_PADDING() throws Exception {
       testAdditionalAction(HBoxs.name(), "PADDING", true);
    }

    /**
     * test  HBox with css: -fx-position-scale-shape
     */
    @Test
    public void HBoxs_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(HBoxs.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  HBox with css: -fx-rotate
     */
    @Test
    public void HBoxs_ROTATE() throws Exception {
       testAdditionalAction(HBoxs.name(), "ROTATE", true);
    }

    /**
     * test  HBox with css: -fx-scale-shape
     */
    @Test
    public void HBoxs_SCALE_SHAPE() throws Exception {
       testAdditionalAction(HBoxs.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  HBox with css: -fx-scale-x
     */
    @Test
    public void HBoxs_SCALE_X() throws Exception {
       testAdditionalAction(HBoxs.name(), "SCALE-X", true);
    }

    /**
     * test  HBox with css: -fx-scale-y
     */
    @Test
    public void HBoxs_SCALE_Y() throws Exception {
       testAdditionalAction(HBoxs.name(), "SCALE-Y", true);
    }

    /**
     * test  HBox with css: -fx-shape
     */
    @Test
    public void HBoxs_SHAPE() throws Exception {
       testAdditionalAction(HBoxs.name(), "SHAPE", true);
    }

    /**
     * test  HBox with css: -fx-snap-to-pixel
     */
    @Test
    public void HBoxs_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(HBoxs.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  HBox with css: -fx-spacing
     */
    @Test
    public void HBoxs_SPACING() throws Exception {
       testAdditionalAction(HBoxs.name(), "SPACING", true);
    }

    /**
     * test  HBox with css: -fx-translate-x
     */
    @Test
    public void HBoxs_TRANSLATE_X() throws Exception {
       testAdditionalAction(HBoxs.name(), "TRANSLATE-X", true);
    }

    /**
     * test  HBox with css: -fx-translate-y
     */
    @Test
    public void HBoxs_TRANSLATE_Y() throws Exception {
       testAdditionalAction(HBoxs.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
