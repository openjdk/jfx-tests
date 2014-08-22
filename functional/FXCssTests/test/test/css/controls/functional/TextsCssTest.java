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
import static test.css.controls.ControlPage.Texts;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class TextsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(Texts);
    }

    /**
     * test  Text with css: -fx-blend-mode
     */
    @Test
    public void Texts_BLEND_MODE() throws Exception {
       testAdditionalAction(Texts.name(), "BLEND-MODE", true);
    }

    /**
     * test  Text with css: -fx-drop-shadow
     */
    @Test
    public void Texts_DROP_SHADOW() throws Exception {
       testAdditionalAction(Texts.name(), "DROP_SHADOW", true);
    }

    /**
     * test  Text with css: -fx-font-family
     */
    @Test
    public void Texts_FONT_FAMILY() throws Exception {
       testAdditionalAction(Texts.name(), "FONT-FAMILY", true);
    }

    /**
     * test  Text with css: -fx-font-size
     */
    @Test
    public void Texts_FONT_SIZE() throws Exception {
       testAdditionalAction(Texts.name(), "FONT-SIZE", true);
    }

    /**
     * test  Text with css: -fx-font-style
     */
    @Test
    public void Texts_FONT_STYLE() throws Exception {
       testAdditionalAction(Texts.name(), "FONT-STYLE", true);
    }

    /**
     * test  Text with css: -fx-font-weight
     */
    @Test
    public void Texts_FONT_WEIGHT() throws Exception {
       testAdditionalAction(Texts.name(), "FONT-WEIGHT", true);
    }

    /**
     * test  Text with css: -fx-font-smoothing-type-gray
     */
    @Test
    public void Texts_FONT_SMOOTHING_TYPE_GRAY() throws Exception {
       testAdditionalAction(Texts.name(), "FONT_SMOOTHING_TYPE_GRAY", true);
    }

    /**
     * test  Text with css: -fx-font-smoothing-type-lcd
     */
    @Test
    public void Texts_FONT_SMOOTHING_TYPE_LCD() throws Exception {
       testAdditionalAction(Texts.name(), "FONT_SMOOTHING_TYPE_LCD", true);
    }

    /**
     * test  Text with css: -fx-inner-shadow
     */
    @Test
    public void Texts_INNER_SHADOW() throws Exception {
       testAdditionalAction(Texts.name(), "INNER_SHADOW", true);
    }

    /**
     * test  Text with css: -fx-opacity
     */
    @Test
    public void Texts_OPACITY() throws Exception {
       testAdditionalAction(Texts.name(), "OPACITY", true);
    }

    /**
     * test  Text with css: -fx-rotate
     */
    @Test
    public void Texts_ROTATE() throws Exception {
       testAdditionalAction(Texts.name(), "ROTATE", true);
    }

    /**
     * test  Text with css: -fx-scale-x
     */
    @Test
    public void Texts_SCALE_X() throws Exception {
       testAdditionalAction(Texts.name(), "SCALE-X", true);
    }

    /**
     * test  Text with css: -fx-scale-y
     */
    @Test
    public void Texts_SCALE_Y() throws Exception {
       testAdditionalAction(Texts.name(), "SCALE-Y", true);
    }

    /**
     * test  Text with css: -fx-strikethrough
     */
    @Test
    public void Texts_STRIKETHROUGH() throws Exception {
       testAdditionalAction(Texts.name(), "STRIKETHROUGH", true);
    }

    /**
     * test  Text with css: -fx-translate-x
     */
    @Test
    public void Texts_TRANSLATE_X() throws Exception {
       testAdditionalAction(Texts.name(), "TRANSLATE-X", true);
    }

    /**
     * test  Text with css: -fx-translate-y
     */
    @Test
    public void Texts_TRANSLATE_Y() throws Exception {
       testAdditionalAction(Texts.name(), "TRANSLATE-Y", true);
    }

    /**
     * test  Text with css: -fx-underline
     */
    @Test
    public void Texts_UNDERLINE() throws Exception {
       testAdditionalAction(Texts.name(), "UNDERLINE", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
