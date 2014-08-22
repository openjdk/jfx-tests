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
import static test.css.controls.ControlPage.XYChars;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class XYCharsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(XYChars);
    }

    /**
     * test  XYChar with css: -fx-alternative-column-fill-visible
     */
    @Test
    public void XYChars_ALTERNATIVE_COLUMN_FILL_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "ALTERNATIVE-COLUMN-FILL-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-alternative-row-fill-visible
     */
    @Test
    public void XYChars_ALTERNATIVE_ROW_FILL_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "ALTERNATIVE-ROW-FILL-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-background-color
     */
    @Test
    public void XYChars_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  XYChar with css: -fx-background-image
     */
    @Test
    public void XYChars_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  XYChar with css: -fx-background-inset
     */
    @Test
    public void XYChars_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  XYChar with css: -fx-background-position
     */
    @Test
    public void XYChars_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  XYChar with css: -fx-background-repeat-round
     */
    @Test
    public void XYChars_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  XYChar with css: -fx-background-repeat-space
     */
    @Test
    public void XYChars_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  XYChar with css: -fx-background-repeat-x-y
     */
    @Test
    public void XYChars_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  XYChar with css: -fx-background-size
     */
    @Test
    public void XYChars_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(XYChars.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  XYChar with css: -fx-blend-mode
     */
    @Test
    public void XYChars_BLEND_MODE() throws Exception {
       testAdditionalAction(XYChars.name(), "BLEND-MODE", true);
    }

    /**
     * test  XYChar with css: -fx-border-color
     */
    @Test
    public void XYChars_BORDER_COLOR() throws Exception {
       testAdditionalAction(XYChars.name(), "BORDER-COLOR", true);
    }

    /**
     * test  XYChar with css: -fx-border-inset
     */
    @Test
    public void XYChars_BORDER_INSET() throws Exception {
       testAdditionalAction(XYChars.name(), "BORDER-INSET", true);
    }

    /**
     * test  XYChar with css: -fx-border-style-dashed
     */
    @Test
    public void XYChars_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(XYChars.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  XYChar with css: -fx-border-style-dotted
     */
    @Test
    public void XYChars_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(XYChars.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  XYChar with css: -fx-border-width
     */
    @Test
    public void XYChars_BORDER_WIDTH() throws Exception {
       testAdditionalAction(XYChars.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  XYChar with css: -fx-border-width-dashed
     */
    @Test
    public void XYChars_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(XYChars.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  XYChar with css: -fx-border-width-dotted
     */
    @Test
    public void XYChars_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(XYChars.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  XYChar with css: -fx-create-symbols
     */
    @Test
    public void XYChars_CREATE_SYMBOLS() throws Exception {
       testAdditionalAction(XYChars.name(), "CREATE-SYMBOLS", true);
    }

    /**
     * test  XYChar with css: -fx-drop-shadow
     */
    @Test
    public void XYChars_DROP_SHADOW() throws Exception {
       testAdditionalAction(XYChars.name(), "DROP_SHADOW", true);
    }

    /**
     * test  XYChar with css: -fx-horizontal-grid-lines-visible
     */
    @Test
    public void XYChars_HORIZONTAL_GRID_LINES_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "HORIZONTAL-GRID-LINES-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-horizontal-zero-line-visible
     */
    @Test
    public void XYChars_HORIZONTAL_ZERO_LINE_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "HORIZONTAL-ZERO-LINE-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-image-border
     */
    @Test
    public void XYChars_IMAGE_BORDER() throws Exception {
       testAdditionalAction(XYChars.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  XYChar with css: -fx-image-border-insets
     */
    @Test
    public void XYChars_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(XYChars.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  XYChar with css: -fx-image-border-no-repeat
     */
    @Test
    public void XYChars_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(XYChars.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  XYChar with css: -fx-image-border-repeat-x
     */
    @Test
    public void XYChars_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(XYChars.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  XYChar with css: -fx-image-border-repeat-y
     */
    @Test
    public void XYChars_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(XYChars.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  XYChar with css: -fx-image-border-round
     */
    @Test
    public void XYChars_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(XYChars.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  XYChar with css: -fx-image-border-space
     */
    @Test
    public void XYChars_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(XYChars.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  XYChar with css: -fx-inner-shadow
     */
    @Test
    public void XYChars_INNER_SHADOW() throws Exception {
       testAdditionalAction(XYChars.name(), "INNER_SHADOW", true);
    }

    /**
     * test  XYChar with css: -fx-legend-visible
     */
    @Test
    public void XYChars_LEGEND_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "LEGEND-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-legend-side-bottom
     */
    @Test
    public void XYChars_LEGEND_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(XYChars.name(), "LEGEND_SIDE_BOTTOM", true);
    }

    /**
     * test  XYChar with css: -fx-legend-side-left
     */
    @Test
    public void XYChars_LEGEND_SIDE_LEFT() throws Exception {
       testAdditionalAction(XYChars.name(), "LEGEND_SIDE_LEFT", true);
    }

    /**
     * test  XYChar with css: -fx-legend-side-right
     */
    @Test
    public void XYChars_LEGEND_SIDE_RIGHT() throws Exception {
       testAdditionalAction(XYChars.name(), "LEGEND_SIDE_RIGHT", true);
    }

    /**
     * test  XYChar with css: -fx-legend-side-top
     */
    @Test
    public void XYChars_LEGEND_SIDE_TOP() throws Exception {
       testAdditionalAction(XYChars.name(), "LEGEND_SIDE_TOP", true);
    }

    /**
     * test  XYChar with css: -fx-minor-tick-count
     */
    @Test
    public void XYChars_MINOR_TICK_COUNT() throws Exception {
       testAdditionalAction(XYChars.name(), "MINOR-TICK-COUNT", true);
    }

    /**
     * test  XYChar with css: -fx-minor-tick-length
     */
    @Test
    public void XYChars_MINOR_TICK_LENGTH() throws Exception {
       testAdditionalAction(XYChars.name(), "MINOR-TICK-LENGTH", true);
    }

    /**
     * test  XYChar with css: -fx-minor-tick-visible
     */
    @Test
    public void XYChars_MINOR_TICK_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "MINOR-TICK-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-opacity
     */
    @Test
    public void XYChars_OPACITY() throws Exception {
       testAdditionalAction(XYChars.name(), "OPACITY", true);
    }

    /**
     * test  XYChar with css: -fx-padding
     */
    @Test
    public void XYChars_PADDING() throws Exception {
       testAdditionalAction(XYChars.name(), "PADDING", true);
    }

    /**
     * test  XYChar with css: -fx-position-scale-shape
     */
    @Test
    public void XYChars_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(XYChars.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  XYChar with css: -fx-rotate
     */
    @Test
    public void XYChars_ROTATE() throws Exception {
       testAdditionalAction(XYChars.name(), "ROTATE", true);
    }

    /**
     * test  XYChar with css: -fx-scale-shape
     */
    @Test
    public void XYChars_SCALE_SHAPE() throws Exception {
       testAdditionalAction(XYChars.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  XYChar with css: -fx-scale-x
     */
    @Test
    public void XYChars_SCALE_X() throws Exception {
       testAdditionalAction(XYChars.name(), "SCALE-X", true);
    }

    /**
     * test  XYChar with css: -fx-scale-y
     */
    @Test
    public void XYChars_SCALE_Y() throws Exception {
       testAdditionalAction(XYChars.name(), "SCALE-Y", true);
    }

    /**
     * test  XYChar with css: -fx-shape
     */
    @Test
    public void XYChars_SHAPE() throws Exception {
       testAdditionalAction(XYChars.name(), "SHAPE", true);
    }

    /**
     * test  XYChar with css: -fx-side-bottom
     */
    @Test
    public void XYChars_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(XYChars.name(), "SIDE_BOTTOM", true);
    }

    /**
     * test  XYChar with css: -fx-side-left
     */
    @Test
    public void XYChars_SIDE_LEFT() throws Exception {
       testAdditionalAction(XYChars.name(), "SIDE_LEFT", true);
    }

    /**
     * test  XYChar with css: -fx-side-right
     */
    @Test
    public void XYChars_SIDE_RIGHT() throws Exception {
       testAdditionalAction(XYChars.name(), "SIDE_RIGHT", true);
    }

    /**
     * test  XYChar with css: -fx-side-top
     */
    @Test
    public void XYChars_SIDE_TOP() throws Exception {
       testAdditionalAction(XYChars.name(), "SIDE_TOP", true);
    }

    /**
     * test  XYChar with css: -fx-snap-to-pixel
     */
    @Test
    public void XYChars_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(XYChars.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  XYChar with css: -fx-tick-label-fill
     */
    @Test
    public void XYChars_TICK_LABEL_FILL() throws Exception {
       testAdditionalAction(XYChars.name(), "TICK-LABEL-FILL", true);
    }

    /**
     * test  XYChar with css: -fx-tick-label-font
     */
    @Test
    public void XYChars_TICK_LABEL_FONT() throws Exception {
       testAdditionalAction(XYChars.name(), "TICK-LABEL-FONT", true);
    }

    /**
     * test  XYChar with css: -fx-tick-label-gap
     */
    @Test
    public void XYChars_TICK_LABEL_GAP() throws Exception {
       testAdditionalAction(XYChars.name(), "TICK-LABEL-GAP", true);
    }

    /**
     * test  XYChar with css: -fx-tick-labels-visible
     */
    @Test
    public void XYChars_TICK_LABELS_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "TICK-LABELS-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-tick-length
     */
    @Test
    public void XYChars_TICK_LENGTH() throws Exception {
       testAdditionalAction(XYChars.name(), "TICK-LENGTH", true);
    }

    /**
     * test  XYChar with css: -fx-tick-mark-visible
     */
    @Test
    public void XYChars_TICK_MARK_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "TICK-MARK-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-tick-unit
     */
    @Test
    public void XYChars_TICK_UNIT() throws Exception {
       testAdditionalAction(XYChars.name(), "TICK-UNIT", true);
    }

    /**
     * test  XYChar with css: -fx-title-side-bottom
     */
    @Test
    public void XYChars_TITLE_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(XYChars.name(), "TITLE_SIDE_BOTTOM", true);
    }

    /**
     * test  XYChar with css: -fx-title-side-left
     */
    @Test
    public void XYChars_TITLE_SIDE_LEFT() throws Exception {
       testAdditionalAction(XYChars.name(), "TITLE_SIDE_LEFT", true);
    }

    /**
     * test  XYChar with css: -fx-title-side-right
     */
    @Test
    public void XYChars_TITLE_SIDE_RIGHT() throws Exception {
       testAdditionalAction(XYChars.name(), "TITLE_SIDE_RIGHT", true);
    }

    /**
     * test  XYChar with css: -fx-title-side-top
     */
    @Test
    public void XYChars_TITLE_SIDE_TOP() throws Exception {
       testAdditionalAction(XYChars.name(), "TITLE_SIDE_TOP", true);
    }

    /**
     * test  XYChar with css: -fx-translate-x
     */
    @Test
    public void XYChars_TRANSLATE_X() throws Exception {
       testAdditionalAction(XYChars.name(), "TRANSLATE-X", true);
    }

    /**
     * test  XYChar with css: -fx-translate-y
     */
    @Test
    public void XYChars_TRANSLATE_Y() throws Exception {
       testAdditionalAction(XYChars.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  XYChar with css: -fx-vertical-grid-lines-visible
     */
    @Test
    public void XYChars_VERTICAL_GRID_LINES_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "VERTICAL-GRID-LINES-VISIBLE", true);
    }

    /**
     * test  XYChar with css: -fx-vertical-zero-line-visible
     */
    @Test
    public void XYChars_VERTICAL_ZERO_LINE_VISIBLE() throws Exception {
       testAdditionalAction(XYChars.name(), "VERTICAL-ZERO-LINE-VISIBLE", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
