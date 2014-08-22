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
import static test.css.controls.ControlPage.ChoiceBoxes;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class ChoiceBoxesCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(ChoiceBoxes);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-color
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-image
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-inset
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-position
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-repeat-round
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-repeat-space
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-repeat-x-y
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-background-size
     */
    @Test
    public void ChoiceBoxes_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-blend-mode
     */
    @Test
    public void ChoiceBoxes_BLEND_MODE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BLEND-MODE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-border-color
     */
    @Test
    public void ChoiceBoxes_BORDER_COLOR() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BORDER-COLOR", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-border-inset
     */
    @Test
    public void ChoiceBoxes_BORDER_INSET() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BORDER-INSET", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-border-style-dashed
     */
    @Test
    public void ChoiceBoxes_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-border-style-dotted
     */
    @Test
    public void ChoiceBoxes_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-border-width
     */
    @Test
    public void ChoiceBoxes_BORDER_WIDTH() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-border-width-dashed
     */
    @Test
    public void ChoiceBoxes_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-border-width-dotted
     */
    @Test
    public void ChoiceBoxes_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-drop-shadow
     */
    @Test
    public void ChoiceBoxes_DROP_SHADOW() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "DROP_SHADOW", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-image-border
     */
    @Test
    public void ChoiceBoxes_IMAGE_BORDER() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-image-border-insets
     */
    @Test
    public void ChoiceBoxes_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-image-border-no-repeat
     */
    @Test
    public void ChoiceBoxes_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-image-border-repeat-x
     */
    @Test
    public void ChoiceBoxes_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-image-border-repeat-y
     */
    @Test
    public void ChoiceBoxes_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-image-border-round
     */
    @Test
    public void ChoiceBoxes_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-image-border-space
     */
    @Test
    public void ChoiceBoxes_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-inner-shadow
     */
    @Test
    public void ChoiceBoxes_INNER_SHADOW() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "INNER_SHADOW", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-opacity
     */
    @Test
    public void ChoiceBoxes_OPACITY() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "OPACITY", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-padding
     */
    @Test
    public void ChoiceBoxes_PADDING() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "PADDING", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-position-scale-shape
     */
    @Test
    public void ChoiceBoxes_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-rotate
     */
    @Test
    public void ChoiceBoxes_ROTATE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "ROTATE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-scale-shape
     */
    @Test
    public void ChoiceBoxes_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-scale-x
     */
    @Test
    public void ChoiceBoxes_SCALE_X() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "SCALE-X", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-scale-y
     */
    @Test
    public void ChoiceBoxes_SCALE_Y() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "SCALE-Y", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-shape
     */
    @Test
    public void ChoiceBoxes_SHAPE() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "SHAPE", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-snap-to-pixel
     */
    @Test
    public void ChoiceBoxes_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-translate-x
     */
    @Test
    public void ChoiceBoxes_TRANSLATE_X() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "TRANSLATE-X", true);
    }

    /**
     * test  ChoiceBoxe with css: -fx-translate-y
     */
    @Test
    public void ChoiceBoxes_TRANSLATE_Y() throws Exception {
       testAdditionalAction(ChoiceBoxes.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
