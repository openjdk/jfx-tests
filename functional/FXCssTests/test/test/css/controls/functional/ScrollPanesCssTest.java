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
import static test.css.controls.ControlPage.ScrollPanes;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class ScrollPanesCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(ScrollPanes);
    }

    /**
     * test  ScrollPane with css: -fx-background-color
     */
    @Test
    public void ScrollPanes_BACKGROUND_COLOR() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-COLOR", true);
    }

    /**
     * test  ScrollPane with css: -fx-background-image
     */
    @Test
    public void ScrollPanes_BACKGROUND_IMAGE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-IMAGE", true);
    }

    /**
     * test  ScrollPane with css: -fx-background-inset
     */
    @Test
    public void ScrollPanes_BACKGROUND_INSET() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-INSET", true);
    }

    /**
     * test  ScrollPane with css: -fx-background-position
     */
    @Test
    public void ScrollPanes_BACKGROUND_POSITION() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-POSITION", true);
    }

    /**
     * test  ScrollPane with css: -fx-background-repeat-round
     */
    @Test
    public void ScrollPanes_BACKGROUND_REPEAT_ROUND() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-REPEAT-ROUND", true);
    }

    /**
     * test  ScrollPane with css: -fx-background-repeat-space
     */
    @Test
    public void ScrollPanes_BACKGROUND_REPEAT_SPACE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-REPEAT-SPACE", true);
    }

    /**
     * test  ScrollPane with css: -fx-background-repeat-x-y
     */
    @Test
    public void ScrollPanes_BACKGROUND_REPEAT_X_Y() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-REPEAT-X-Y", true);
    }

    /**
     * test  ScrollPane with css: -fx-background-size
     */
    @Test
    public void ScrollPanes_BACKGROUND_SIZE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BACKGROUND-SIZE", true);
    }

    /**
     * test  ScrollPane with css: -fx-blend-mode
     */
    @Test
    public void ScrollPanes_BLEND_MODE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BLEND-MODE", true);
    }

    /**
     * test  ScrollPane with css: -fx-border-color
     */
    @Test
    public void ScrollPanes_BORDER_COLOR() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BORDER-COLOR", true);
    }

    /**
     * test  ScrollPane with css: -fx-border-inset
     */
    @Test
    public void ScrollPanes_BORDER_INSET() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BORDER-INSET", true);
    }

    /**
     * test  ScrollPane with css: -fx-border-style-dashed
     */
    @Test
    public void ScrollPanes_BORDER_STYLE_DASHED() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BORDER-STYLE-DASHED", true);
    }

    /**
     * test  ScrollPane with css: -fx-border-style-dotted
     */
    @Test
    public void ScrollPanes_BORDER_STYLE_DOTTED() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BORDER-STYLE-DOTTED", true);
    }

    /**
     * test  ScrollPane with css: -fx-border-width
     */
    @Test
    public void ScrollPanes_BORDER_WIDTH() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BORDER-WIDTH", true);
    }

    /**
     * test  ScrollPane with css: -fx-border-width-dashed
     */
    @Test
    public void ScrollPanes_BORDER_WIDTH_dashed() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BORDER-WIDTH-dashed", true);
    }

    /**
     * test  ScrollPane with css: -fx-border-width-dotted
     */
    @Test
    public void ScrollPanes_BORDER_WIDTH_dotted() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "BORDER-WIDTH-dotted", true);
    }

    /**
     * test  ScrollPane with css: -fx-drop-shadow
     */
    @Test
    public void ScrollPanes_DROP_SHADOW() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "DROP_SHADOW", true);
    }

    /**
     * test  ScrollPane with css: -fx-fit-to-height
     */
    @Test
    public void ScrollPanes_FIT_TO_HEIGHT() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "FIT-TO-HEIGHT", true);
    }

    /**
     * test  ScrollPane with css: -fx-fit-to-width
     */
    @Test
    public void ScrollPanes_FIT_TO_WIDTH() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "FIT-TO-WIDTH", true);
    }

    /**
     * test  ScrollPane with css: -fx-hbar-policy-always
     */
    @Test
    public void ScrollPanes_HBAR_POLICY_ALWAYS() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "HBAR_POLICY_ALWAYS", true);
    }

    /**
     * test  ScrollPane with css: -fx-hbar-policy-as-needed
     */
    @Test
    public void ScrollPanes_HBAR_POLICY_AS_NEEDED() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "HBAR_POLICY_AS_NEEDED", true);
    }

    /**
     * test  ScrollPane with css: -fx-hbar-policy-never
     */
    @Test
    public void ScrollPanes_HBAR_POLICY_NEVER() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "HBAR_POLICY_NEVER", true);
    }

    /**
     * test  ScrollPane with css: -fx-image-border
     */
    @Test
    public void ScrollPanes_IMAGE_BORDER() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "IMAGE-BORDER", true);
    }

    /**
     * test  ScrollPane with css: -fx-image-border-insets
     */
    @Test
    public void ScrollPanes_IMAGE_BORDER_INSETS() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "IMAGE-BORDER-INSETS", true);
    }

    /**
     * test  ScrollPane with css: -fx-image-border-no-repeat
     */
    @Test
    public void ScrollPanes_IMAGE_BORDER_NO_REPEAT() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "IMAGE-BORDER-NO-REPEAT", true);
    }

    /**
     * test  ScrollPane with css: -fx-image-border-repeat-x
     */
    @Test
    public void ScrollPanes_IMAGE_BORDER_REPEAT_X() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "IMAGE-BORDER-REPEAT-X", true);
    }

    /**
     * test  ScrollPane with css: -fx-image-border-repeat-y
     */
    @Test
    public void ScrollPanes_IMAGE_BORDER_REPEAT_Y() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "IMAGE-BORDER-REPEAT-Y", true);
    }

    /**
     * test  ScrollPane with css: -fx-image-border-round
     */
    @Test
    public void ScrollPanes_IMAGE_BORDER_ROUND() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "IMAGE-BORDER-ROUND", true);
    }

    /**
     * test  ScrollPane with css: -fx-image-border-space
     */
    @Test
    public void ScrollPanes_IMAGE_BORDER_SPACE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "IMAGE-BORDER-SPACE", true);
    }

    /**
     * test  ScrollPane with css: -fx-inner-shadow
     */
    @Test
    public void ScrollPanes_INNER_SHADOW() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "INNER_SHADOW", true);
    }

    /**
     * test  ScrollPane with css: -fx-max-height
     */
    @Test
    public void ScrollPanes_MAX_HEIGHT() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "MAX-HEIGHT", true);
    }

    /**
     * test  ScrollPane with css: -fx-max-width
     */
    @Test
    public void ScrollPanes_MAX_WIDTH() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "MAX-WIDTH", true);
    }

    /**
     * test  ScrollPane with css: -fx-min-height
     */
    @Test
    public void ScrollPanes_MIN_HEIGHT() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "MIN-HEIGHT", true);
    }

    /**
     * test  ScrollPane with css: -fx-min-width
     */
    @Test
    public void ScrollPanes_MIN_WIDTH() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "MIN-WIDTH", true);
    }

    /**
     * test  ScrollPane with css: -fx-opacity
     */
    @Test
    public void ScrollPanes_OPACITY() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "OPACITY", true);
    }

    /**
     * test  ScrollPane with css: -fx-padding
     */
    @Test
    public void ScrollPanes_PADDING() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "PADDING", true);
    }

    /**
     * test  ScrollPane with css: -fx-position-scale-shape
     */
    @Test
    public void ScrollPanes_POSITION_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "POSITION-SCALE-SHAPE", true);
    }

    /**
     * test  ScrollPane with css: -fx-pref-height
     */
    @Test
    public void ScrollPanes_PREF_HEIGHT() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "PREF-HEIGHT", true);
    }

    /**
     * test  ScrollPane with css: -fx-pref-width
     */
    @Test
    public void ScrollPanes_PREF_WIDTH() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "PREF-WIDTH", true);
    }

    /**
     * test  ScrollPane with css: -fx-rotate
     */
    @Test
    public void ScrollPanes_ROTATE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "ROTATE", true);
    }

    /**
     * test  ScrollPane with css: -fx-scale-shape
     */
    @Test
    public void ScrollPanes_SCALE_SHAPE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "SCALE-SHAPE", true);
    }

    /**
     * test  ScrollPane with css: -fx-scale-x
     */
    @Test
    public void ScrollPanes_SCALE_X() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "SCALE-X", true);
    }

    /**
     * test  ScrollPane with css: -fx-scale-y
     */
    @Test
    public void ScrollPanes_SCALE_Y() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "SCALE-Y", true);
    }

    /**
     * test  ScrollPane with css: -fx-shape
     */
    @Test
    public void ScrollPanes_SHAPE() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "SHAPE", true);
    }

    /**
     * test  ScrollPane with css: -fx-snap-to-pixel
     */
    @Test
    public void ScrollPanes_SNAP_TO_PIXEL() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "SNAP-TO-PIXEL", true);
    }

    /**
     * test  ScrollPane with css: -fx-translate-x
     */
    @Test
    public void ScrollPanes_TRANSLATE_X() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "TRANSLATE-X", true);
    }

    /**
     * test  ScrollPane with css: -fx-translate-y
     */
    @Test
    public void ScrollPanes_TRANSLATE_Y() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  ScrollPane with css: -fx-vbar-policy-always
     */
    @Test
    public void ScrollPanes_VBAR_POLICY_ALWAYS() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "VBAR_POLICY_ALWAYS", true);
    }

    /**
     * test  ScrollPane with css: -fx-vbar-policy-as-needed
     */
    @Test
    public void ScrollPanes_VBAR_POLICY_AS_NEEDED() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "VBAR_POLICY_AS_NEEDED", true);
    }

    /**
     * test  ScrollPane with css: -fx-vbar-policy-never
     */
    @Test
    public void ScrollPanes_VBAR_POLICY_NEVER() throws Exception {
       testAdditionalAction(ScrollPanes.name(), "VBAR_POLICY_NEVER", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
