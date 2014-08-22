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
import static test.css.controls.ControlPage.TilePanes;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class TilePanesCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(TilePanes);
    }

    /**
     * test  TilePane with css: -fx-alignment-baseline-center
     */
    @Test
    public void TilePanes_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-baseline-left
     */
    @Test
    public void TilePanes_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-baseline-right
     */
    @Test
    public void TilePanes_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-bottom-center
     */
    @Test
    public void TilePanes_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-bottom-left
     */
    @Test
    public void TilePanes_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-bottom-right
     */
    @Test
    public void TilePanes_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-center
     */
    @Test
    public void TilePanes_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-center-left
     */
    @Test
    public void TilePanes_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-center-right
     */
    @Test
    public void TilePanes_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-top-center
     */
    @Test
    public void TilePanes_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-top-left
     */
    @Test
    public void TilePanes_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-alignment-top-right
     */
    @Test
    public void TilePanes_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-background-color
     */
    @Test
    public void TilePanes_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  TilePane with css: -fx-background-image
     */
    @Test
    public void TilePanes_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  TilePane with css: -fx-background-inset
     */
    @Test
    public void TilePanes_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  TilePane with css: -fx-background-position
     */
    @Test
    public void TilePanes_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  TilePane with css: -fx-background-repeat-round
     */
    @Test
    public void TilePanes_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  TilePane with css: -fx-background-repeat-space
     */
    @Test
    public void TilePanes_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  TilePane with css: -fx-background-repeat-x-y
     */
    @Test
    public void TilePanes_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  TilePane with css: -fx-background-size
     */
    @Test
    public void TilePanes_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(TilePanes.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  TilePane with css: -fx-blend-mode
     */
    @Test
    public void TilePanes_BLEND_MODE() throws Exception {
       testAdditionalAction(TilePanes.name(), "BLEND-MODE", true);
    }

    /**
     * test  TilePane with css: -fx-border-color
     */
    @Test
    public void TilePanes_BORDER_COLOR() throws Exception {
       testAdditionalAction(TilePanes.name(), "BORDER-COLOR", true);
    }

    /**
     * test  TilePane with css: -fx-border-inset
     */
    @Test
    public void TilePanes_BORDER_INSET() throws Exception {
       testAdditionalAction(TilePanes.name(), "BORDER-INSET", true);
    }

    /**
     * test  TilePane with css: -fx-border-style-dashed
     */
    @Test
    public void TilePanes_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(TilePanes.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  TilePane with css: -fx-border-style-dotted
     */
    @Test
    public void TilePanes_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(TilePanes.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  TilePane with css: -fx-border-width
     */
    @Test
    public void TilePanes_BORDER_WIDTH() throws Exception {
       testAdditionalAction(TilePanes.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  TilePane with css: -fx-border-width-dashed
     */
    @Test
    public void TilePanes_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(TilePanes.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  TilePane with css: -fx-border-width-dotted
     */
    @Test
    public void TilePanes_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(TilePanes.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  TilePane with css: -fx-drop-shadow
     */
    @Test
    public void TilePanes_DROP_SHADOW() throws Exception {
       testAdditionalAction(TilePanes.name(), "DROP_SHADOW", true);
    }

    /**
     * test  TilePane with css: -fx-hgap
     */
    @Test
    public void TilePanes_HGAP() throws Exception {
       testAdditionalAction(TilePanes.name(), "HGAP", true);
    }

    /**
     * test  TilePane with css: -fx-image-border
     */
    @Test
    public void TilePanes_IMAGE_BORDER() throws Exception {
       testAdditionalAction(TilePanes.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  TilePane with css: -fx-image-border-insets
     */
    @Test
    public void TilePanes_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(TilePanes.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  TilePane with css: -fx-image-border-no-repeat
     */
    @Test
    public void TilePanes_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(TilePanes.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  TilePane with css: -fx-image-border-repeat-x
     */
    @Test
    public void TilePanes_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(TilePanes.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  TilePane with css: -fx-image-border-repeat-y
     */
    @Test
    public void TilePanes_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(TilePanes.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  TilePane with css: -fx-image-border-round
     */
    @Test
    public void TilePanes_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(TilePanes.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  TilePane with css: -fx-image-border-space
     */
    @Test
    public void TilePanes_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(TilePanes.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  TilePane with css: -fx-inner-shadow
     */
    @Test
    public void TilePanes_INNER_SHADOW() throws Exception {
       testAdditionalAction(TilePanes.name(), "INNER_SHADOW", true);
    }

    /**
     * test  TilePane with css: -fx-opacity
     */
    @Test
    public void TilePanes_OPACITY() throws Exception {
       testAdditionalAction(TilePanes.name(), "OPACITY", true);
    }

    /**
     * test  TilePane with css: -fx-orientation-horizontal
     */
    @Test
    public void TilePanes_ORIENTATION_HORIZONTAL() throws Exception {
       testAdditionalAction(TilePanes.name(), "ORIENTATION_HORIZONTAL", true);
    }

    /**
     * test  TilePane with css: -fx-orientation-vertical
     */
    @Test
    public void TilePanes_ORIENTATION_VERTICAL() throws Exception {
       testAdditionalAction(TilePanes.name(), "ORIENTATION_VERTICAL", true);
    }

    /**
     * test  TilePane with css: -fx-padding
     */
    @Test
    public void TilePanes_PADDING() throws Exception {
       testAdditionalAction(TilePanes.name(), "PADDING", true);
    }

    /**
     * test  TilePane with css: -fx-position-scale-shape
     */
    @Test
    public void TilePanes_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(TilePanes.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  TilePane with css: -fx-pref-columns
     */
    @Test
    public void TilePanes_PREF_COLUMNS() throws Exception {
       testAdditionalAction(TilePanes.name(), "PREF-COLUMNS", true);
    }

    /**
     * test  TilePane with css: -fx-pref-rows
     */
    @Test
    public void TilePanes_PREF_ROWS() throws Exception {
       testAdditionalAction(TilePanes.name(), "PREF-ROWS", true);
    }

    /**
     * test  TilePane with css: -fx-pref-tile-height
     */
    @Test
    public void TilePanes_PREF_TILE_HEIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "PREF-TILE-HEIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-pref-tile-width
     */
    @Test
    public void TilePanes_PREF_TILE_WIDTH() throws Exception {
       testAdditionalAction(TilePanes.name(), "PREF-TILE-WIDTH", true);
    }

    /**
     * test  TilePane with css: -fx-rotate
     */
    @Test
    public void TilePanes_ROTATE() throws Exception {
       testAdditionalAction(TilePanes.name(), "ROTATE", true);
    }

    /**
     * test  TilePane with css: -fx-scale-shape
     */
    @Test
    public void TilePanes_SCALE_SHAPE() throws Exception {
       testAdditionalAction(TilePanes.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  TilePane with css: -fx-scale-x
     */
    @Test
    public void TilePanes_SCALE_X() throws Exception {
       testAdditionalAction(TilePanes.name(), "SCALE-X", true);
    }

    /**
     * test  TilePane with css: -fx-scale-y
     */
    @Test
    public void TilePanes_SCALE_Y() throws Exception {
       testAdditionalAction(TilePanes.name(), "SCALE-Y", true);
    }

    /**
     * test  TilePane with css: -fx-shape
     */
    @Test
    public void TilePanes_SHAPE() throws Exception {
       testAdditionalAction(TilePanes.name(), "SHAPE", true);
    }

    /**
     * test  TilePane with css: -fx-snap-to-pixel
     */
    @Test
    public void TilePanes_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(TilePanes.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-baseline-center
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_BASELINE_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_BASELINE_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-baseline-left
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_BASELINE_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_BASELINE_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-baseline-right
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_BASELINE_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_BASELINE_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-bottom-center
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_BOTTOM_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_BOTTOM_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-bottom-left
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_BOTTOM_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_BOTTOM_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-bottom-right
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_BOTTOM_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_BOTTOM_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-center
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-center-left
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_CENTER_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_CENTER_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-center-right
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_CENTER_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_CENTER_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-top-center
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_TOP_CENTER() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_TOP_CENTER", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-top-left
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_TOP_LEFT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_TOP_LEFT", true);
    }

    /**
     * test  TilePane with css: -fx-tile-alignment-top-right
     */
    @Test
    public void TilePanes_TILE_ALIGNMENT_TOP_RIGHT() throws Exception {
       testAdditionalAction(TilePanes.name(), "TILE_ALIGNMENT_TOP_RIGHT", true);
    }

    /**
     * test  TilePane with css: -fx-translate-x
     */
    @Test
    public void TilePanes_TRANSLATE_X() throws Exception {
       testAdditionalAction(TilePanes.name(), "TRANSLATE-X", true);
    }

    /**
     * test  TilePane with css: -fx-translate-y
     */
    @Test
    public void TilePanes_TRANSLATE_Y() throws Exception {
       testAdditionalAction(TilePanes.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  TilePane with css: -fx-vgap
     */
    @Test
    public void TilePanes_VGAP() throws Exception {
       testAdditionalAction(TilePanes.name(), "VGAP", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
