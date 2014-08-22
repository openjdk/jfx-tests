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
import static test.css.controls.ControlPage.BarCharts;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class BarChartsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(BarCharts);
    }

    /**
     * test  BarChart with css: -fx-alternative-column-fill-visible
     */
    @Test
    @Smoke
    public void BarCharts_ALTERNATIVE_COLUMN_FILL_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "ALTERNATIVE-COLUMN-FILL-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-alternative-row-fill-visible
     */
    @Test
    @Smoke
    public void BarCharts_ALTERNATIVE_ROW_FILL_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "ALTERNATIVE-ROW-FILL-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-background-color
     */
    @Test
    public void BarCharts_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  BarChart with css: -fx-background-image
     */
    @Test
    public void BarCharts_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  BarChart with css: -fx-background-inset
     */
    @Test
    public void BarCharts_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  BarChart with css: -fx-background-position
     */
    @Test
    public void BarCharts_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  BarChart with css: -fx-background-repeat-round
     */
    @Test
    public void BarCharts_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  BarChart with css: -fx-background-repeat-space
     */
    @Test
    public void BarCharts_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  BarChart with css: -fx-background-repeat-x-y
     */
    @Test
    public void BarCharts_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  BarChart with css: -fx-background-size
     */
    @Test
    public void BarCharts_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(BarCharts.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  BarChart with css: -fx-bar-gap
     */
    @Test
    @Smoke
    public void BarCharts_BAR_GAP() throws Exception {
       testAdditionalAction(BarCharts.name(), "BAR-GAP", true);
    }

    /**
     * test  BarChart with css: -fx-blend-mode
     */
    @Test
    public void BarCharts_BLEND_MODE() throws Exception {
       testAdditionalAction(BarCharts.name(), "BLEND-MODE", true);
    }

    /**
     * test  BarChart with css: -fx-border-color
     */
    @Test
    public void BarCharts_BORDER_COLOR() throws Exception {
       testAdditionalAction(BarCharts.name(), "BORDER-COLOR", true);
    }

    /**
     * test  BarChart with css: -fx-border-inset
     */
    @Test
    public void BarCharts_BORDER_INSET() throws Exception {
       testAdditionalAction(BarCharts.name(), "BORDER-INSET", true);
    }

    /**
     * test  BarChart with css: -fx-border-style-dashed
     */
    @Test
    public void BarCharts_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(BarCharts.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  BarChart with css: -fx-border-style-dotted
     */
    @Test
    public void BarCharts_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(BarCharts.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  BarChart with css: -fx-border-width
     */
    @Test
    public void BarCharts_BORDER_WIDTH() throws Exception {
       testAdditionalAction(BarCharts.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  BarChart with css: -fx-border-width-dashed
     */
    @Test
    public void BarCharts_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(BarCharts.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  BarChart with css: -fx-border-width-dotted
     */
    @Test
    public void BarCharts_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(BarCharts.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  BarChart with css: -fx-category-gap
     */
    @Test
    @Smoke
    public void BarCharts_CATEGORY_GAP() throws Exception {
       testAdditionalAction(BarCharts.name(), "CATEGORY-GAP", true);
    }

    /**
     * test  BarChart with css: -fx-drop-shadow
     */
    @Test
    public void BarCharts_DROP_SHADOW() throws Exception {
       testAdditionalAction(BarCharts.name(), "DROP_SHADOW", true);
    }

    /**
     * test  BarChart with css: -fx-end-margin
     */
    @Test
    @Smoke
    public void BarCharts_END_MARGIN() throws Exception {
       testAdditionalAction(BarCharts.name(), "END-MARGIN", true);
    }

    /**
     * test  BarChart with css: -fx-gap-start-and-end
     */
    @Test
    @Smoke
    public void BarCharts_GAP_START_AND_END() throws Exception {
       testAdditionalAction(BarCharts.name(), "GAP-START-AND-END", true);
    }

    /**
     * test  BarChart with css: -fx-horizontal-grid-lines-visible
     */
    @Test
    @Smoke
    public void BarCharts_HORIZONTAL_GRID_LINES_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "HORIZONTAL-GRID-LINES-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-horizontal-zero-line-visible
     */
    @Test
    @Smoke
    public void BarCharts_HORIZONTAL_ZERO_LINE_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "HORIZONTAL-ZERO-LINE-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-image-border
     */
    @Test
    public void BarCharts_IMAGE_BORDER() throws Exception {
       testAdditionalAction(BarCharts.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  BarChart with css: -fx-image-border-insets
     */
    @Test
    public void BarCharts_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(BarCharts.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  BarChart with css: -fx-image-border-no-repeat
     */
    @Test
    public void BarCharts_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(BarCharts.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  BarChart with css: -fx-image-border-repeat-x
     */
    @Test
    public void BarCharts_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(BarCharts.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  BarChart with css: -fx-image-border-repeat-y
     */
    @Test
    public void BarCharts_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(BarCharts.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  BarChart with css: -fx-image-border-round
     */
    @Test
    public void BarCharts_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(BarCharts.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  BarChart with css: -fx-image-border-space
     */
    @Test
    public void BarCharts_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(BarCharts.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  BarChart with css: -fx-inner-shadow
     */
    @Test
    public void BarCharts_INNER_SHADOW() throws Exception {
       testAdditionalAction(BarCharts.name(), "INNER_SHADOW", true);
    }

    /**
     * test  BarChart with css: -fx-legend-visible
     */
    @Test
    @Smoke
    public void BarCharts_LEGEND_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "LEGEND-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-legend-side-bottom
     */
    @Test
    @Smoke
    public void BarCharts_LEGEND_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(BarCharts.name(), "LEGEND_SIDE_BOTTOM", true);
    }

    /**
     * test  BarChart with css: -fx-legend-side-left
     */
    @Test
    @Smoke
    public void BarCharts_LEGEND_SIDE_LEFT() throws Exception {
       testAdditionalAction(BarCharts.name(), "LEGEND_SIDE_LEFT", true);
    }

    /**
     * test  BarChart with css: -fx-legend-side-right
     */
    @Test
    @Smoke
    public void BarCharts_LEGEND_SIDE_RIGHT() throws Exception {
       testAdditionalAction(BarCharts.name(), "LEGEND_SIDE_RIGHT", true);
    }

    /**
     * test  BarChart with css: -fx-legend-side-top
     */
    @Test
    @Smoke
    public void BarCharts_LEGEND_SIDE_TOP() throws Exception {
       testAdditionalAction(BarCharts.name(), "LEGEND_SIDE_TOP", true);
    }

    /**
     * test  BarChart with css: -fx-opacity
     */
    @Test
    public void BarCharts_OPACITY() throws Exception {
       testAdditionalAction(BarCharts.name(), "OPACITY", true);
    }

    /**
     * test  BarChart with css: -fx-padding
     */
    @Test
    public void BarCharts_PADDING() throws Exception {
       testAdditionalAction(BarCharts.name(), "PADDING", true);
    }

    /**
     * test  BarChart with css: -fx-position-scale-shape
     */
    @Test
    public void BarCharts_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(BarCharts.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  BarChart with css: -fx-rotate
     */
    @Test
    public void BarCharts_ROTATE() throws Exception {
       testAdditionalAction(BarCharts.name(), "ROTATE", true);
    }

    /**
     * test  BarChart with css: -fx-scale-shape
     */
    @Test
    public void BarCharts_SCALE_SHAPE() throws Exception {
       testAdditionalAction(BarCharts.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  BarChart with css: -fx-scale-x
     */
    @Test
    public void BarCharts_SCALE_X() throws Exception {
       testAdditionalAction(BarCharts.name(), "SCALE-X", true);
    }

    /**
     * test  BarChart with css: -fx-scale-y
     */
    @Test
    public void BarCharts_SCALE_Y() throws Exception {
       testAdditionalAction(BarCharts.name(), "SCALE-Y", true);
    }

    /**
     * test  BarChart with css: -fx-shape
     */
    @Test
    public void BarCharts_SHAPE() throws Exception {
       testAdditionalAction(BarCharts.name(), "SHAPE", true);
    }

    /**
     * test  BarChart with css: -fx-side-bottom
     */
    @Test
    @Smoke
    public void BarCharts_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(BarCharts.name(), "SIDE_BOTTOM", true);
    }

    /**
     * test  BarChart with css: -fx-side-left
     */
    @Test
    @Smoke
    public void BarCharts_SIDE_LEFT() throws Exception {
       testAdditionalAction(BarCharts.name(), "SIDE_LEFT", true);
    }

    /**
     * test  BarChart with css: -fx-side-right
     */
    @Test
    @Smoke
    public void BarCharts_SIDE_RIGHT() throws Exception {
       testAdditionalAction(BarCharts.name(), "SIDE_RIGHT", true);
    }

    /**
     * test  BarChart with css: -fx-side-top
     */
    @Test
    @Smoke
    public void BarCharts_SIDE_TOP() throws Exception {
       testAdditionalAction(BarCharts.name(), "SIDE_TOP", true);
    }

    /**
     * test  BarChart with css: -fx-snap-to-pixel
     */
    @Test
    public void BarCharts_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(BarCharts.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  BarChart with css: -fx-start-margin
     */
    @Test
    @Smoke
    public void BarCharts_START_MARGIN() throws Exception {
       testAdditionalAction(BarCharts.name(), "START-MARGIN", true);
    }

    /**
     * test  BarChart with css: -fx-tick-label-fill
     */
    @Test
    @Smoke
    public void BarCharts_TICK_LABEL_FILL() throws Exception {
       testAdditionalAction(BarCharts.name(), "TICK-LABEL-FILL", true);
    }

    /**
     * test  BarChart with css: -fx-tick-label-font
     */
    @Test
    @Smoke
    public void BarCharts_TICK_LABEL_FONT() throws Exception {
       testAdditionalAction(BarCharts.name(), "TICK-LABEL-FONT", true);
    }

    /**
     * test  BarChart with css: -fx-tick-label-gap
     */
    @Test
    @Smoke
    public void BarCharts_TICK_LABEL_GAP() throws Exception {
       testAdditionalAction(BarCharts.name(), "TICK-LABEL-GAP", true);
    }

    /**
     * test  BarChart with css: -fx-tick-labels-visible
     */
    @Test
    @Smoke
    public void BarCharts_TICK_LABELS_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "TICK-LABELS-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-tick-length
     */
    @Test
    @Smoke
    public void BarCharts_TICK_LENGTH() throws Exception {
       testAdditionalAction(BarCharts.name(), "TICK-LENGTH", true);
    }

    /**
     * test  BarChart with css: -fx-tick-mark-visible
     */
    @Test
    @Smoke
    public void BarCharts_TICK_MARK_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "TICK-MARK-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-title-side-bottom
     */
    @Test
    @Smoke
    public void BarCharts_TITLE_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(BarCharts.name(), "TITLE_SIDE_BOTTOM", true);
    }

    /**
     * test  BarChart with css: -fx-title-side-left
     */
    @Test
    @Smoke
    public void BarCharts_TITLE_SIDE_LEFT() throws Exception {
       testAdditionalAction(BarCharts.name(), "TITLE_SIDE_LEFT", true);
    }

    /**
     * test  BarChart with css: -fx-title-side-right
     */
    @Test
    @Smoke
    public void BarCharts_TITLE_SIDE_RIGHT() throws Exception {
       testAdditionalAction(BarCharts.name(), "TITLE_SIDE_RIGHT", true);
    }

    /**
     * test  BarChart with css: -fx-title-side-top
     */
    @Test
    @Smoke
    public void BarCharts_TITLE_SIDE_TOP() throws Exception {
       testAdditionalAction(BarCharts.name(), "TITLE_SIDE_TOP", true);
    }

    /**
     * test  BarChart with css: -fx-translate-x
     */
    @Test
    public void BarCharts_TRANSLATE_X() throws Exception {
       testAdditionalAction(BarCharts.name(), "TRANSLATE-X", true);
    }

    /**
     * test  BarChart with css: -fx-translate-y
     */
    @Test
    public void BarCharts_TRANSLATE_Y() throws Exception {
       testAdditionalAction(BarCharts.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  BarChart with css: -fx-vertical-grid-lines-visible
     */
    @Test
    @Smoke
    public void BarCharts_VERTICAL_GRID_LINES_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "VERTICAL-GRID-LINES-VISIBLE", true);
    }

    /**
     * test  BarChart with css: -fx-vertical-zero-line-visible
     */
    @Test
    @Smoke
    public void BarCharts_VERTICAL_ZERO_LINE_VISIBLE() throws Exception {
       testAdditionalAction(BarCharts.name(), "VERTICAL-ZERO-LINE-VISIBLE", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
