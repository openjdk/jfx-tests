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
import static test.css.controls.ControlPage.FlowPanes;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class FlowPanesCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(FlowPanes);
    }

    /**
     * test  FlowPane with css: -fx-alignment-baseline-center
     */
    @Test
    public void FlowPanes_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-baseline-left
     */
    @Test
    public void FlowPanes_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-baseline-right
     */
    @Test
    public void FlowPanes_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-bottom-center
     */
    @Test
    public void FlowPanes_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-bottom-left
     */
    @Test
    public void FlowPanes_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-bottom-right
     */
    @Test
    public void FlowPanes_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-center
     */
    @Test
    public void FlowPanes_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-center-left
     */
    @Test
    public void FlowPanes_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-center-right
     */
    @Test
    public void FlowPanes_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-top-center
     */
    @Test
    public void FlowPanes_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-top-left
     */
    @Test
    public void FlowPanes_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  FlowPane with css: -fx-alignment-top-right
     */
    @Test
    public void FlowPanes_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  FlowPane with css: -fx-background-color
     */
    @Test
    public void FlowPanes_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  FlowPane with css: -fx-background-image
     */
    @Test
    public void FlowPanes_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  FlowPane with css: -fx-background-inset
     */
    @Test
    public void FlowPanes_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  FlowPane with css: -fx-background-position
     */
    @Test
    public void FlowPanes_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  FlowPane with css: -fx-background-repeat-round
     */
    @Test
    public void FlowPanes_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  FlowPane with css: -fx-background-repeat-space
     */
    @Test
    public void FlowPanes_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  FlowPane with css: -fx-background-repeat-x-y
     */
    @Test
    public void FlowPanes_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  FlowPane with css: -fx-background-size
     */
    @Test
    public void FlowPanes_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  FlowPane with css: -fx-blend-mode
     */
    @Test
    public void FlowPanes_BLEND_MODE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BLEND-MODE", true);
    }

    /**
     * test  FlowPane with css: -fx-border-color
     */
    @Test
    public void FlowPanes_BORDER_COLOR() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BORDER-COLOR", true);
    }

    /**
     * test  FlowPane with css: -fx-border-inset
     */
    @Test
    public void FlowPanes_BORDER_INSET() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BORDER-INSET", true);
    }

    /**
     * test  FlowPane with css: -fx-border-style-dashed
     */
    @Test
    public void FlowPanes_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  FlowPane with css: -fx-border-style-dotted
     */
    @Test
    public void FlowPanes_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  FlowPane with css: -fx-border-width
     */
    @Test
    public void FlowPanes_BORDER_WIDTH() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  FlowPane with css: -fx-border-width-dashed
     */
    @Test
    public void FlowPanes_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  FlowPane with css: -fx-border-width-dotted
     */
    @Test
    public void FlowPanes_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(FlowPanes.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  FlowPane with css: -fx-column-halignment-center
     */
    @Test
    @Smoke
    public void FlowPanes_COLUMN_HALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(FlowPanes.name(), "COLUMN_HALIGNMENT_CENTER", true);
    }

    /**
     * test  FlowPane with css: -fx-column-halignment-left
     */
    @Test
    @Smoke
    public void FlowPanes_COLUMN_HALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "COLUMN_HALIGNMENT_LEFT", true);
    }

    /**
     * test  FlowPane with css: -fx-column-halignment-right
     */
    @Test
    @Smoke
    public void FlowPanes_COLUMN_HALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "COLUMN_HALIGNMENT_RIGHT", true);
    }

    /**
     * test  FlowPane with css: -fx-drop-shadow
     */
    @Test
    public void FlowPanes_DROP_SHADOW() throws Exception {
       testAdditionalAction(FlowPanes.name(), "DROP_SHADOW", true);
    }

    /**
     * test  FlowPane with css: -fx-hgap
     */
    @Test
    @Smoke
    public void FlowPanes_HGAP() throws Exception {
       testAdditionalAction(FlowPanes.name(), "HGAP", true);
    }

    /**
     * test  FlowPane with css: -fx-image-border
     */
    @Test
    public void FlowPanes_IMAGE_BORDER() throws Exception {
       testAdditionalAction(FlowPanes.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  FlowPane with css: -fx-image-border-insets
     */
    @Test
    public void FlowPanes_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(FlowPanes.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  FlowPane with css: -fx-image-border-no-repeat
     */
    @Test
    public void FlowPanes_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(FlowPanes.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  FlowPane with css: -fx-image-border-repeat-x
     */
    @Test
    public void FlowPanes_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(FlowPanes.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  FlowPane with css: -fx-image-border-repeat-y
     */
    @Test
    public void FlowPanes_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(FlowPanes.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  FlowPane with css: -fx-image-border-round
     */
    @Test
    public void FlowPanes_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(FlowPanes.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  FlowPane with css: -fx-image-border-space
     */
    @Test
    public void FlowPanes_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  FlowPane with css: -fx-inner-shadow
     */
    @Test
    public void FlowPanes_INNER_SHADOW() throws Exception {
       testAdditionalAction(FlowPanes.name(), "INNER_SHADOW", true);
    }

    /**
     * test  FlowPane with css: -fx-opacity
     */
    @Test
    public void FlowPanes_OPACITY() throws Exception {
       testAdditionalAction(FlowPanes.name(), "OPACITY", true);
    }

    /**
     * test  FlowPane with css: -fx-orientation-horizontal
     */
    @Test
    public void FlowPanes_ORIENTATION_HORIZONTAL() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ORIENTATION_HORIZONTAL", true);
    }

    /**
     * test  FlowPane with css: -fx-orientation-vertical
     */
    @Test
    public void FlowPanes_ORIENTATION_VERTICAL() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ORIENTATION_VERTICAL", true);
    }

    /**
     * test  FlowPane with css: -fx-padding
     */
    @Test
    public void FlowPanes_PADDING() throws Exception {
       testAdditionalAction(FlowPanes.name(), "PADDING", true);
    }

    /**
     * test  FlowPane with css: -fx-position-scale-shape
     */
    @Test
    public void FlowPanes_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  FlowPane with css: -fx-rotate
     */
    @Test
    public void FlowPanes_ROTATE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ROTATE", true);
    }

    /**
     * test  FlowPane with css: -fx-row-valignment-baseline
     */
    @Test
    @Smoke
    public void FlowPanes_ROW_VALIGNMENT_BASELINE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ROW_VALIGNMENT_BASELINE", true);
    }

    /**
     * test  FlowPane with css: -fx-row-valignment-bottom
     */
    @Test
    @Smoke
    public void FlowPanes_ROW_VALIGNMENT_BOTTOM() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ROW_VALIGNMENT_BOTTOM", true);
    }

    /**
     * test  FlowPane with css: -fx-row-valignment-center
     */
    @Test
    @Smoke
    public void FlowPanes_ROW_VALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ROW_VALIGNMENT_CENTER", true);
    }

    /**
     * test  FlowPane with css: -fx-row-valignment-top
     */
    @Test
    @Smoke
    public void FlowPanes_ROW_VALIGNMENT_TOP() throws Exception {
       testAdditionalAction(FlowPanes.name(), "ROW_VALIGNMENT_TOP", true);
    }

    /**
     * test  FlowPane with css: -fx-scale-shape
     */
    @Test
    public void FlowPanes_SCALE_SHAPE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  FlowPane with css: -fx-scale-x
     */
    @Test
    public void FlowPanes_SCALE_X() throws Exception {
       testAdditionalAction(FlowPanes.name(), "SCALE-X", true);
    }

    /**
     * test  FlowPane with css: -fx-scale-y
     */
    @Test
    public void FlowPanes_SCALE_Y() throws Exception {
       testAdditionalAction(FlowPanes.name(), "SCALE-Y", true);
    }

    /**
     * test  FlowPane with css: -fx-shape
     */
    @Test
    public void FlowPanes_SHAPE() throws Exception {
       testAdditionalAction(FlowPanes.name(), "SHAPE", true);
    }

    /**
     * test  FlowPane with css: -fx-snap-to-pixel
     */
    @Test
    public void FlowPanes_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(FlowPanes.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  FlowPane with css: -fx-translate-x
     */
    @Test
    public void FlowPanes_TRANSLATE_X() throws Exception {
       testAdditionalAction(FlowPanes.name(), "TRANSLATE-X", true);
    }

    /**
     * test  FlowPane with css: -fx-translate-y
     */
    @Test
    public void FlowPanes_TRANSLATE_Y() throws Exception {
       testAdditionalAction(FlowPanes.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  FlowPane with css: -fx-vgap
     */
    @Test
    @Smoke
    public void FlowPanes_VGAP() throws Exception {
       testAdditionalAction(FlowPanes.name(), "VGAP", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
