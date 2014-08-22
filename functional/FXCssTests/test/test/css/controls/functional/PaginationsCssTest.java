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
import static test.css.controls.ControlPage.Paginations;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class PaginationsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(Paginations);
    }

    /**
     * test  Pagination with css: -fx-arrows-visible
     */
    @Test
    @Smoke
    public void Paginations_ARROWS_VISIBLE() throws Exception {
       testAdditionalAction(Paginations.name(), "ARROWS_VISIBLE", true);
    }

    /**
     * test  Pagination with css: -fx-background-color
     */
    @Test
    public void Paginations_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  Pagination with css: -fx-background-image
     */
    @Test
    public void Paginations_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  Pagination with css: -fx-background-inset
     */
    @Test
    public void Paginations_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  Pagination with css: -fx-background-position
     */
    @Test
    public void Paginations_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  Pagination with css: -fx-background-repeat-round
     */
    @Test
    public void Paginations_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  Pagination with css: -fx-background-repeat-space
     */
    @Test
    public void Paginations_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  Pagination with css: -fx-background-repeat-x-y
     */
    @Test
    public void Paginations_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  Pagination with css: -fx-background-size
     */
    @Test
    public void Paginations_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(Paginations.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  Pagination with css: -fx-blend-mode
     */
    @Test
    public void Paginations_BLEND_MODE() throws Exception {
       testAdditionalAction(Paginations.name(), "BLEND-MODE", true);
    }

    /**
     * test  Pagination with css: -fx-border-color
     */
    @Test
    public void Paginations_BORDER_COLOR() throws Exception {
       testAdditionalAction(Paginations.name(), "BORDER-COLOR", true);
    }

    /**
     * test  Pagination with css: -fx-border-inset
     */
    @Test
    public void Paginations_BORDER_INSET() throws Exception {
       testAdditionalAction(Paginations.name(), "BORDER-INSET", true);
    }

    /**
     * test  Pagination with css: -fx-border-style-dashed
     */
    @Test
    public void Paginations_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(Paginations.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  Pagination with css: -fx-border-style-dotted
     */
    @Test
    public void Paginations_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(Paginations.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  Pagination with css: -fx-border-width
     */
    @Test
    public void Paginations_BORDER_WIDTH() throws Exception {
       testAdditionalAction(Paginations.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  Pagination with css: -fx-border-width-dashed
     */
    @Test
    public void Paginations_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(Paginations.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  Pagination with css: -fx-border-width-dotted
     */
    @Test
    public void Paginations_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(Paginations.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  Pagination with css: -fx-drop-shadow
     */
    @Test
    public void Paginations_DROP_SHADOW() throws Exception {
       testAdditionalAction(Paginations.name(), "DROP_SHADOW", true);
    }

    /**
     * test  Pagination with css: -fx-image-border
     */
    @Test
    public void Paginations_IMAGE_BORDER() throws Exception {
       testAdditionalAction(Paginations.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  Pagination with css: -fx-image-border-insets
     */
    @Test
    public void Paginations_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(Paginations.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  Pagination with css: -fx-image-border-no-repeat
     */
    @Test
    public void Paginations_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(Paginations.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  Pagination with css: -fx-image-border-repeat-x
     */
    @Test
    public void Paginations_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(Paginations.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  Pagination with css: -fx-image-border-repeat-y
     */
    @Test
    public void Paginations_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(Paginations.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  Pagination with css: -fx-image-border-round
     */
    @Test
    public void Paginations_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(Paginations.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  Pagination with css: -fx-image-border-space
     */
    @Test
    public void Paginations_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(Paginations.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  Pagination with css: -fx-inner-shadow
     */
    @Test
    public void Paginations_INNER_SHADOW() throws Exception {
       testAdditionalAction(Paginations.name(), "INNER_SHADOW", true);
    }

    /**
     * test  Pagination with css: -fx-max-page-indicator-count
     */
    @Test
    @Smoke
    public void Paginations_MAX_PAGE_INDICATOR_COUNT() throws Exception {
       testAdditionalAction(Paginations.name(), "MAX-PAGE-INDICATOR-COUNT", true);
    }

    /**
     * test  Pagination with css: -fx-opacity
     */
    @Test
    public void Paginations_OPACITY() throws Exception {
       testAdditionalAction(Paginations.name(), "OPACITY", true);
    }

    /**
     * test  Pagination with css: -fx-padding
     */
    @Test
    public void Paginations_PADDING() throws Exception {
       testAdditionalAction(Paginations.name(), "PADDING", true);
    }

    /**
     * test  Pagination with css: -fx-page-information-visible
     */
    @Test
    @Smoke
    public void Paginations_PAGE_INFORMATION_VISIBLE() throws Exception {
       testAdditionalAction(Paginations.name(), "PAGE-INFORMATION-VISIBLE", true);
    }

    /**
     * test  Pagination with css: -fx-page-information-alignment-bottom
     */
    @Test
    @Smoke
    public void Paginations_PAGE_INFORMATION_ALIGNMENT_BOTTOM() throws Exception {
       testAdditionalAction(Paginations.name(), "PAGE_INFORMATION_ALIGNMENT_BOTTOM", true);
    }

    /**
     * test  Pagination with css: -fx-page-information-alignment-left
     */
    @Test
    @Smoke
    public void Paginations_PAGE_INFORMATION_ALIGNMENT_LEFT() throws Exception {
       testAdditionalAction(Paginations.name(), "PAGE_INFORMATION_ALIGNMENT_LEFT", true);
    }

    /**
     * test  Pagination with css: -fx-page-information-alignment-right
     */
    @Test
    @Smoke
    public void Paginations_PAGE_INFORMATION_ALIGNMENT_RIGHT() throws Exception {
       testAdditionalAction(Paginations.name(), "PAGE_INFORMATION_ALIGNMENT_RIGHT", true);
    }

    /**
     * test  Pagination with css: -fx-page-information-alignment-top
     */
    @Test
    @Smoke
    public void Paginations_PAGE_INFORMATION_ALIGNMENT_TOP() throws Exception {
       testAdditionalAction(Paginations.name(), "PAGE_INFORMATION_ALIGNMENT_TOP", true);
    }

    /**
     * test  Pagination with css: -fx-position-scale-shape
     */
    @Test
    public void Paginations_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Paginations.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  Pagination with css: -fx-rotate
     */
    @Test
    public void Paginations_ROTATE() throws Exception {
       testAdditionalAction(Paginations.name(), "ROTATE", true);
    }

    /**
     * test  Pagination with css: -fx-scale-shape
     */
    @Test
    public void Paginations_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Paginations.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  Pagination with css: -fx-scale-x
     */
    @Test
    public void Paginations_SCALE_X() throws Exception {
       testAdditionalAction(Paginations.name(), "SCALE-X", true);
    }

    /**
     * test  Pagination with css: -fx-scale-y
     */
    @Test
    public void Paginations_SCALE_Y() throws Exception {
       testAdditionalAction(Paginations.name(), "SCALE-Y", true);
    }

    /**
     * test  Pagination with css: -fx-shape
     */
    @Test
    public void Paginations_SHAPE() throws Exception {
       testAdditionalAction(Paginations.name(), "SHAPE", true);
    }

    /**
     * test  Pagination with css: -fx-snap-to-pixel
     */
    @Test
    public void Paginations_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(Paginations.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  Pagination with css: -fx-translate-x
     */
    @Test
    public void Paginations_TRANSLATE_X() throws Exception {
       testAdditionalAction(Paginations.name(), "TRANSLATE-X", true);
    }

    /**
     * test  Pagination with css: -fx-translate-y
     */
    @Test
    public void Paginations_TRANSLATE_Y() throws Exception {
       testAdditionalAction(Paginations.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
