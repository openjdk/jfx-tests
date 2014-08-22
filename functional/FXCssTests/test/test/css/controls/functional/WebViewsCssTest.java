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
import static test.css.controls.ControlPage.WebViews;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * Generated test
 */
public class WebViewsCssTest extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    @BeforeClass
    public static void runUI() {
        test.css.controls.ControlsCSSApp.main(null);
    }

    @Before
    public void createPage () {
        ((test.css.controls.ControlsCSSApp)getApplication()).open(WebViews);
    }

    /**
     * test  WebView with css: -fx-blend-mode
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_BLEND_MODE() throws Exception {
       testAdditionalAction(WebViews.name(), "BLEND-MODE", true);
    }

    /**
     * test  WebView with css: -fx-drop-shadow
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_DROP_SHADOW() throws Exception {
       testAdditionalAction(WebViews.name(), "DROP_SHADOW", true);
    }

    /**
     * test  WebView with css: -fx-font-scale
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_FONT_SCALE() throws Exception {
       testAdditionalAction(WebViews.name(), "FONT-SCALE", true);
    }

    /**
     * test  WebView with css: -fx-font-smoothing-type-gray
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_FONT_SMOOTHING_TYPE_GRAY() throws Exception {
       testAdditionalAction(WebViews.name(), "FONT_SMOOTHING_TYPE_GRAY", true);
    }

    /**
     * test  WebView with css: -fx-font-smoothing-type-lcd
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_FONT_SMOOTHING_TYPE_LCD() throws Exception {
       testAdditionalAction(WebViews.name(), "FONT_SMOOTHING_TYPE_LCD", true);
    }

    /**
     * test  WebView with css: -fx-inner-shadow
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_INNER_SHADOW() throws Exception {
       testAdditionalAction(WebViews.name(), "INNER_SHADOW", true);
    }

    /**
     * test  WebView with css: -fx-max-height
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_MAX_HEIGHT() throws Exception {
       testAdditionalAction(WebViews.name(), "MAX-HEIGHT", true);
    }

    /**
     * test  WebView with css: -fx-max-width
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_MAX_WIDTH() throws Exception {
       testAdditionalAction(WebViews.name(), "MAX-WIDTH", true);
    }

    /**
     * test  WebView with css: -fx-min-height
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_MIN_HEIGHT() throws Exception {
       testAdditionalAction(WebViews.name(), "MIN-HEIGHT", true);
    }

    /**
     * test  WebView with css: -fx-min-width
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_MIN_WIDTH() throws Exception {
       testAdditionalAction(WebViews.name(), "MIN-WIDTH", true);
    }

    /**
     * test  WebView with css: -fx-opacity
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_OPACITY() throws Exception {
       testAdditionalAction(WebViews.name(), "OPACITY", true);
    }

    /**
     * test  WebView with css: -fx-pref-height
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_PREF_HEIGHT() throws Exception {
       testAdditionalAction(WebViews.name(), "PREF-HEIGHT", true);
    }

    /**
     * test  WebView with css: -fx-pref-width
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_PREF_WIDTH() throws Exception {
       testAdditionalAction(WebViews.name(), "PREF-WIDTH", true);
    }

    /**
     * test  WebView with css: -fx-rotate
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_ROTATE() throws Exception {
       testAdditionalAction(WebViews.name(), "ROTATE", true);
    }

    /**
     * test  WebView with css: -fx-scale-x
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_SCALE_X() throws Exception {
       testAdditionalAction(WebViews.name(), "SCALE-X", true);
    }

    /**
     * test  WebView with css: -fx-scale-y
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_SCALE_Y() throws Exception {
       testAdditionalAction(WebViews.name(), "SCALE-Y", true);
    }

    /**
     * test  WebView with css: -fx-translate-x
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_TRANSLATE_X() throws Exception {
       testAdditionalAction(WebViews.name(), "TRANSLATE-X", true);
    }

    /**
     * test  WebView with css: -fx-translate-y
     */
    @Test
    @Keywords(keywords="webkit")
    public void WebViews_TRANSLATE_Y() throws Exception {
       testAdditionalAction(WebViews.name(), "TRANSLATE-Y", true);
    }



    public String getName() {
        return "ControlCss";
    }
}
