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
import static test.css.controls.ControlPage.VBoxs;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class VBoxsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(VBoxs);
    }

    /**
     * test  VBox with css: -fx-alignment-baseline-center
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  VBox with css: -fx-alignment-baseline-left
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  VBox with css: -fx-alignment-baseline-right
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  VBox with css: -fx-alignment-bottom-center
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  VBox with css: -fx-alignment-bottom-left
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  VBox with css: -fx-alignment-bottom-right
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  VBox with css: -fx-alignment-center
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  VBox with css: -fx-alignment-center-left
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  VBox with css: -fx-alignment-center-right
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  VBox with css: -fx-alignment-top-center
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  VBox with css: -fx-alignment-top-left
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  VBox with css: -fx-alignment-top-right
     */
    @Test
    @Smoke
    public void VBoxs_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(VBoxs.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  VBox with css: -fx-background-color
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  VBox with css: -fx-background-image
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  VBox with css: -fx-background-inset
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  VBox with css: -fx-background-position
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  VBox with css: -fx-background-repeat-round
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  VBox with css: -fx-background-repeat-space
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  VBox with css: -fx-background-repeat-x-y
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  VBox with css: -fx-background-size
     */
    @Test
    @Smoke
    public void VBoxs_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(VBoxs.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  VBox with css: -fx-blend-mode
     */
    @Test
    @Smoke
    public void VBoxs_BLEND_MODE() throws Exception {
       testAdditionalAction(VBoxs.name(), "BLEND-MODE", true);
    }

    /**
     * test  VBox with css: -fx-border-color
     */
    @Test
    @Smoke
    public void VBoxs_BORDER_COLOR() throws Exception {
       testAdditionalAction(VBoxs.name(), "BORDER-COLOR", true);
    }

    /**
     * test  VBox with css: -fx-border-inset
     */
    @Test
    @Smoke
    public void VBoxs_BORDER_INSET() throws Exception {
       testAdditionalAction(VBoxs.name(), "BORDER-INSET", true);
    }

    /**
     * test  VBox with css: -fx-border-style-dashed
     */
    @Test
    @Smoke
    public void VBoxs_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(VBoxs.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  VBox with css: -fx-border-style-dotted
     */
    @Test
    @Smoke
    public void VBoxs_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(VBoxs.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  VBox with css: -fx-border-width
     */
    @Test
    @Smoke
    public void VBoxs_BORDER_WIDTH() throws Exception {
       testAdditionalAction(VBoxs.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  VBox with css: -fx-border-width-dashed
     */
    @Test
    @Smoke
    public void VBoxs_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(VBoxs.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  VBox with css: -fx-border-width-dotted
     */
    @Test
    @Smoke
    public void VBoxs_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(VBoxs.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  VBox with css: -fx-drop-shadow
     */
    @Test
    @Smoke
    public void VBoxs_DROP_SHADOW() throws Exception {
       testAdditionalAction(VBoxs.name(), "DROP_SHADOW", true);
    }

    /**
     * test  VBox with css: -fx-fill-width
     */
    @Test
    @Smoke
    public void VBoxs_FILL_WIDTH() throws Exception {
       testAdditionalAction(VBoxs.name(), "FILL-WIDTH", true);
    }

    /**
     * test  VBox with css: -fx-image-border
     */
    @Test
    @Smoke
    public void VBoxs_IMAGE_BORDER() throws Exception {
       testAdditionalAction(VBoxs.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  VBox with css: -fx-image-border-insets
     */
    @Test
    @Smoke
    public void VBoxs_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(VBoxs.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  VBox with css: -fx-image-border-no-repeat
     */
    @Test
    @Smoke
    public void VBoxs_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(VBoxs.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  VBox with css: -fx-image-border-repeat-x
     */
    @Test
    @Smoke
    public void VBoxs_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(VBoxs.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  VBox with css: -fx-image-border-repeat-y
     */
    @Test
    @Smoke
    public void VBoxs_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(VBoxs.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  VBox with css: -fx-image-border-round
     */
    @Test
    @Smoke
    public void VBoxs_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(VBoxs.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  VBox with css: -fx-image-border-space
     */
    @Test
    @Smoke
    public void VBoxs_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(VBoxs.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  VBox with css: -fx-inner-shadow
     */
    @Test
    @Smoke
    public void VBoxs_INNER_SHADOW() throws Exception {
       testAdditionalAction(VBoxs.name(), "INNER_SHADOW", true);
    }

    /**
     * test  VBox with css: -fx-opacity
     */
    @Test
    @Smoke
    public void VBoxs_OPACITY() throws Exception {
       testAdditionalAction(VBoxs.name(), "OPACITY", true);
    }

    /**
     * test  VBox with css: -fx-padding
     */
    @Test
    @Smoke
    public void VBoxs_PADDING() throws Exception {
       testAdditionalAction(VBoxs.name(), "PADDING", true);
    }

    /**
     * test  VBox with css: -fx-position-scale-shape
     */
    @Test
    @Smoke
    public void VBoxs_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(VBoxs.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  VBox with css: -fx-rotate
     */
    @Test
    @Smoke
    public void VBoxs_ROTATE() throws Exception {
       testAdditionalAction(VBoxs.name(), "ROTATE", true);
    }

    /**
     * test  VBox with css: -fx-scale-shape
     */
    @Test
    @Smoke
    public void VBoxs_SCALE_SHAPE() throws Exception {
       testAdditionalAction(VBoxs.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  VBox with css: -fx-scale-x
     */
    @Test
    @Smoke
    public void VBoxs_SCALE_X() throws Exception {
       testAdditionalAction(VBoxs.name(), "SCALE-X", true);
    }

    /**
     * test  VBox with css: -fx-scale-y
     */
    @Test
    @Smoke
    public void VBoxs_SCALE_Y() throws Exception {
       testAdditionalAction(VBoxs.name(), "SCALE-Y", true);
    }

    /**
     * test  VBox with css: -fx-shape
     */
    @Test
    @Smoke
    public void VBoxs_SHAPE() throws Exception {
       testAdditionalAction(VBoxs.name(), "SHAPE", true);
    }

    /**
     * test  VBox with css: -fx-snap-to-pixel
     */
    @Test
    @Smoke
    public void VBoxs_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(VBoxs.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  VBox with css: -fx-spacing
     */
    @Test
    @Smoke
    public void VBoxs_SPACING() throws Exception {
       testAdditionalAction(VBoxs.name(), "SPACING", true);
    }

    /**
     * test  VBox with css: -fx-translate-x
     */
    @Test
    @Smoke
    public void VBoxs_TRANSLATE_X() throws Exception {
       testAdditionalAction(VBoxs.name(), "TRANSLATE-X", true);
    }

    /**
     * test  VBox with css: -fx-translate-y
     */
    @Test
    @Smoke
    public void VBoxs_TRANSLATE_Y() throws Exception {
       testAdditionalAction(VBoxs.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
