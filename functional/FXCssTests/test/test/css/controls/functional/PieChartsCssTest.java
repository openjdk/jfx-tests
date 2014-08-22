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
import static test.css.controls.ControlPage.PieCharts;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class PieChartsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(PieCharts);
    }

    /**
     * test  PieChart with css: -fx-background-color
     */
    @Test
    public void PieCharts_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  PieChart with css: -fx-background-image
     */
    @Test
    public void PieCharts_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  PieChart with css: -fx-background-inset
     */
    @Test
    public void PieCharts_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  PieChart with css: -fx-background-position
     */
    @Test
    public void PieCharts_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  PieChart with css: -fx-background-repeat-round
     */
    @Test
    public void PieCharts_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  PieChart with css: -fx-background-repeat-space
     */
    @Test
    public void PieCharts_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  PieChart with css: -fx-background-repeat-x-y
     */
    @Test
    public void PieCharts_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  PieChart with css: -fx-background-size
     */
    @Test
    public void PieCharts_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(PieCharts.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  PieChart with css: -fx-blend-mode
     */
    @Test
    public void PieCharts_BLEND_MODE() throws Exception {
       testAdditionalAction(PieCharts.name(), "BLEND-MODE", true);
    }

    /**
     * test  PieChart with css: -fx-border-color
     */
    @Test
    public void PieCharts_BORDER_COLOR() throws Exception {
       testAdditionalAction(PieCharts.name(), "BORDER-COLOR", true);
    }

    /**
     * test  PieChart with css: -fx-border-inset
     */
    @Test
    public void PieCharts_BORDER_INSET() throws Exception {
       testAdditionalAction(PieCharts.name(), "BORDER-INSET", true);
    }

    /**
     * test  PieChart with css: -fx-border-style-dashed
     */
    @Test
    public void PieCharts_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(PieCharts.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  PieChart with css: -fx-border-style-dotted
     */
    @Test
    public void PieCharts_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(PieCharts.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  PieChart with css: -fx-border-width
     */
    @Test
    public void PieCharts_BORDER_WIDTH() throws Exception {
       testAdditionalAction(PieCharts.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  PieChart with css: -fx-border-width-dashed
     */
    @Test
    public void PieCharts_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(PieCharts.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  PieChart with css: -fx-border-width-dotted
     */
    @Test
    public void PieCharts_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(PieCharts.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  PieChart with css: -fx-clockwise
     */
    @Test
    public void PieCharts_CLOCKWISE() throws Exception {
       testAdditionalAction(PieCharts.name(), "CLOCKWISE", true);
    }

    /**
     * test  PieChart with css: -fx-drop-shadow
     */
    @Test
    public void PieCharts_DROP_SHADOW() throws Exception {
       testAdditionalAction(PieCharts.name(), "DROP_SHADOW", true);
    }

    /**
     * test  PieChart with css: -fx-image-border
     */
    @Test
    public void PieCharts_IMAGE_BORDER() throws Exception {
       testAdditionalAction(PieCharts.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  PieChart with css: -fx-image-border-insets
     */
    @Test
    public void PieCharts_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(PieCharts.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  PieChart with css: -fx-image-border-no-repeat
     */
    @Test
    public void PieCharts_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(PieCharts.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  PieChart with css: -fx-image-border-repeat-x
     */
    @Test
    public void PieCharts_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(PieCharts.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  PieChart with css: -fx-image-border-repeat-y
     */
    @Test
    public void PieCharts_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(PieCharts.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  PieChart with css: -fx-image-border-round
     */
    @Test
    public void PieCharts_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(PieCharts.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  PieChart with css: -fx-image-border-space
     */
    @Test
    public void PieCharts_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(PieCharts.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  PieChart with css: -fx-inner-shadow
     */
    @Test
    public void PieCharts_INNER_SHADOW() throws Exception {
       testAdditionalAction(PieCharts.name(), "INNER_SHADOW", true);
    }

    /**
     * test  PieChart with css: -fx-label-line-length
     */
    @Test
    public void PieCharts_LABEL_LINE_LENGTH() throws Exception {
       testAdditionalAction(PieCharts.name(), "LABEL-LINE-LENGTH", true);
    }

    /**
     * test  PieChart with css: -fx-legend-visible
     */
    @Test
    public void PieCharts_LEGEND_VISIBLE() throws Exception {
       testAdditionalAction(PieCharts.name(), "LEGEND-VISIBLE", true);
    }

    /**
     * test  PieChart with css: -fx-legend-side-bottom
     */
    @Test
    public void PieCharts_LEGEND_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(PieCharts.name(), "LEGEND_SIDE_BOTTOM", true);
    }

    /**
     * test  PieChart with css: -fx-legend-side-left
     */
    @Test
    public void PieCharts_LEGEND_SIDE_LEFT() throws Exception {
       testAdditionalAction(PieCharts.name(), "LEGEND_SIDE_LEFT", true);
    }

    /**
     * test  PieChart with css: -fx-legend-side-right
     */
    @Test
    public void PieCharts_LEGEND_SIDE_RIGHT() throws Exception {
       testAdditionalAction(PieCharts.name(), "LEGEND_SIDE_RIGHT", true);
    }

    /**
     * test  PieChart with css: -fx-legend-side-top
     */
    @Test
    public void PieCharts_LEGEND_SIDE_TOP() throws Exception {
       testAdditionalAction(PieCharts.name(), "LEGEND_SIDE_TOP", true);
    }

    /**
     * test  PieChart with css: -fx-opacity
     */
    @Test
    public void PieCharts_OPACITY() throws Exception {
       testAdditionalAction(PieCharts.name(), "OPACITY", true);
    }

    /**
     * test  PieChart with css: -fx-padding
     */
    @Test
    public void PieCharts_PADDING() throws Exception {
       testAdditionalAction(PieCharts.name(), "PADDING", true);
    }

    /**
     * test  PieChart with css: -fx-pie-label-visible
     */
    @Test
    @Smoke
    public void PieCharts_PIE_LABEL_VISIBLE() throws Exception {
       testAdditionalAction(PieCharts.name(), "PIE-LABEL-VISIBLE", true);
    }

    /**
     * test  PieChart with css: -fx-position-scale-shape
     */
    @Test
    public void PieCharts_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(PieCharts.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  PieChart with css: -fx-rotate
     */
    @Test
    public void PieCharts_ROTATE() throws Exception {
       testAdditionalAction(PieCharts.name(), "ROTATE", true);
    }

    /**
     * test  PieChart with css: -fx-scale-shape
     */
    @Test
    public void PieCharts_SCALE_SHAPE() throws Exception {
       testAdditionalAction(PieCharts.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  PieChart with css: -fx-scale-x
     */
    @Test
    public void PieCharts_SCALE_X() throws Exception {
       testAdditionalAction(PieCharts.name(), "SCALE-X", true);
    }

    /**
     * test  PieChart with css: -fx-scale-y
     */
    @Test
    public void PieCharts_SCALE_Y() throws Exception {
       testAdditionalAction(PieCharts.name(), "SCALE-Y", true);
    }

    /**
     * test  PieChart with css: -fx-shape
     */
    @Test
    public void PieCharts_SHAPE() throws Exception {
       testAdditionalAction(PieCharts.name(), "SHAPE", true);
    }

    /**
     * test  PieChart with css: -fx-snap-to-pixel
     */
    @Test
    public void PieCharts_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(PieCharts.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  PieChart with css: -fx-start-angle
     */
    @Test
    public void PieCharts_START_ANGLE() throws Exception {
       testAdditionalAction(PieCharts.name(), "START-ANGLE", true);
    }

    /**
     * test  PieChart with css: -fx-title-side-bottom
     */
    @Test
    public void PieCharts_TITLE_SIDE_BOTTOM() throws Exception {
       testAdditionalAction(PieCharts.name(), "TITLE_SIDE_BOTTOM", true);
    }

    /**
     * test  PieChart with css: -fx-title-side-left
     */
    @Test
    public void PieCharts_TITLE_SIDE_LEFT() throws Exception {
       testAdditionalAction(PieCharts.name(), "TITLE_SIDE_LEFT", true);
    }

    /**
     * test  PieChart with css: -fx-title-side-right
     */
    @Test
    public void PieCharts_TITLE_SIDE_RIGHT() throws Exception {
       testAdditionalAction(PieCharts.name(), "TITLE_SIDE_RIGHT", true);
    }

    /**
     * test  PieChart with css: -fx-title-side-top
     */
    @Test
    public void PieCharts_TITLE_SIDE_TOP() throws Exception {
       testAdditionalAction(PieCharts.name(), "TITLE_SIDE_TOP", true);
    }

    /**
     * test  PieChart with css: -fx-translate-x
     */
    @Test
    public void PieCharts_TRANSLATE_X() throws Exception {
       testAdditionalAction(PieCharts.name(), "TRANSLATE-X", true);
    }

    /**
     * test  PieChart with css: -fx-translate-y
     */
    @Test
    public void PieCharts_TRANSLATE_Y() throws Exception {
       testAdditionalAction(PieCharts.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
