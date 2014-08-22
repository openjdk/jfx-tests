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
import static test.css.controls.ControlPage.Menubars;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class MenubarsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(Menubars);
    }

    /**
     * test  Menubar with css: -fx-background-color
     */
    @Test
    public void Menubars_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  Menubar with css: -fx-background-image
     */
    @Test
    public void Menubars_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  Menubar with css: -fx-background-inset
     */
    @Test
    public void Menubars_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  Menubar with css: -fx-background-position
     */
    @Test
    public void Menubars_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  Menubar with css: -fx-background-repeat-round
     */
    @Test
    public void Menubars_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  Menubar with css: -fx-background-repeat-space
     */
    @Test
    public void Menubars_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  Menubar with css: -fx-background-repeat-x-y
     */
    @Test
    public void Menubars_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  Menubar with css: -fx-background-size
     */
    @Test
    public void Menubars_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(Menubars.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  Menubar with css: -fx-blend-mode
     */
    @Test
    public void Menubars_BLEND_MODE() throws Exception {
       testAdditionalAction(Menubars.name(), "BLEND-MODE", true);
    }

    /**
     * test  Menubar with css: -fx-border-color
     */
    @Test
    public void Menubars_BORDER_COLOR() throws Exception {
       testAdditionalAction(Menubars.name(), "BORDER-COLOR", true);
    }

    /**
     * test  Menubar with css: -fx-border-inset
     */
    @Test
    public void Menubars_BORDER_INSET() throws Exception {
       testAdditionalAction(Menubars.name(), "BORDER-INSET", true);
    }

    /**
     * test  Menubar with css: -fx-border-style-dashed
     */
    @Test
    public void Menubars_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(Menubars.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  Menubar with css: -fx-border-style-dotted
     */
    @Test
    public void Menubars_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(Menubars.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  Menubar with css: -fx-border-width
     */
    @Test
    public void Menubars_BORDER_WIDTH() throws Exception {
       testAdditionalAction(Menubars.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  Menubar with css: -fx-border-width-dashed
     */
    @Test
    public void Menubars_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(Menubars.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  Menubar with css: -fx-border-width-dotted
     */
    @Test
    public void Menubars_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(Menubars.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  Menubar with css: -fx-drop-shadow
     */
    @Test
    public void Menubars_DROP_SHADOW() throws Exception {
       testAdditionalAction(Menubars.name(), "DROP_SHADOW", true);
    }

    /**
     * test  Menubar with css: -fx-image-border
     */
    @Test
    public void Menubars_IMAGE_BORDER() throws Exception {
       testAdditionalAction(Menubars.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  Menubar with css: -fx-image-border-insets
     */
    @Test
    public void Menubars_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(Menubars.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  Menubar with css: -fx-image-border-no-repeat
     */
    @Test
    public void Menubars_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(Menubars.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  Menubar with css: -fx-image-border-repeat-x
     */
    @Test
    public void Menubars_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(Menubars.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  Menubar with css: -fx-image-border-repeat-y
     */
    @Test
    public void Menubars_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(Menubars.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  Menubar with css: -fx-image-border-round
     */
    @Test
    public void Menubars_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(Menubars.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  Menubar with css: -fx-image-border-space
     */
    @Test
    public void Menubars_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(Menubars.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  Menubar with css: -fx-inner-shadow
     */
    @Test
    public void Menubars_INNER_SHADOW() throws Exception {
       testAdditionalAction(Menubars.name(), "INNER_SHADOW", true);
    }

    /**
     * test  Menubar with css: -fx-opacity
     */
    @Test
    public void Menubars_OPACITY() throws Exception {
       testAdditionalAction(Menubars.name(), "OPACITY", true);
    }

    /**
     * test  Menubar with css: -fx-padding
     */
    @Test
    public void Menubars_PADDING() throws Exception {
       testAdditionalAction(Menubars.name(), "PADDING", true);
    }

    /**
     * test  Menubar with css: -fx-position-scale-shape
     */
    @Test
    public void Menubars_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Menubars.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  Menubar with css: -fx-rotate
     */
    @Test
    public void Menubars_ROTATE() throws Exception {
       testAdditionalAction(Menubars.name(), "ROTATE", true);
    }

    /**
     * test  Menubar with css: -fx-scale-shape
     */
    @Test
    public void Menubars_SCALE_SHAPE() throws Exception {
       testAdditionalAction(Menubars.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  Menubar with css: -fx-scale-x
     */
    @Test
    public void Menubars_SCALE_X() throws Exception {
       testAdditionalAction(Menubars.name(), "SCALE-X", true);
    }

    /**
     * test  Menubar with css: -fx-scale-y
     */
    @Test
    public void Menubars_SCALE_Y() throws Exception {
       testAdditionalAction(Menubars.name(), "SCALE-Y", true);
    }

    /**
     * test  Menubar with css: -fx-shape
     */
    @Test
    public void Menubars_SHAPE() throws Exception {
       testAdditionalAction(Menubars.name(), "SHAPE", true);
    }

    /**
     * test  Menubar with css: -fx-snap-to-pixel
     */
    @Test
    public void Menubars_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(Menubars.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  Menubar with css: -fx-translate-x
     */
    @Test
    public void Menubars_TRANSLATE_X() throws Exception {
       testAdditionalAction(Menubars.name(), "TRANSLATE-X", true);
    }

    /**
     * test  Menubar with css: -fx-translate-y
     */
    @Test
    public void Menubars_TRANSLATE_Y() throws Exception {
       testAdditionalAction(Menubars.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
