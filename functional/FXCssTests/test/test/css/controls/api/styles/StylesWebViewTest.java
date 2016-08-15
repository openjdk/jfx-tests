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
package test.css.controls.api.styles;

import javafx.css.CssMetaData;
import java.util.Map;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.scene.shape.*;
import javafx.scene.web.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jemmy.fx.Root;
import org.jemmy.action.GetAction;
import client.test.Keywords;
import com.sun.javafx.scene.control.skin.*;
import javafx.scene.paint.Paint;
import javafx.geometry.Insets;
import com.sun.javafx.scene.layout.region.RepeatStruct;
import com.sun.javafx.scene.layout.region.BorderImageSlices;


/**
 * Generation test
 * @author sergey.lugovoy@oracle.com
 */
public class StylesWebViewTest extends BaseStyleNodeTest {

    @Override
    Node getControl() {
        return new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                WebView control = new WebView();

                setResult(control);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxblendmode () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-blend-mode"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-blend-mode");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxcontextmenuenabled () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-context-menu-enabled"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-context-menu-enabled");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxcursor () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-cursor"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-cursor");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxeffect () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-effect"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-effect");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxfontscale () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font-scale"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font-scale");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxfontsmoothingtype () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font-smoothing-type"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font-smoothing-type");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.text.FontSmoothingType.LCD);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxmaxheight () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-max-height"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-max-height");
        Assert.assertEquals(data.getInitialValue(getControl()), Double.MAX_VALUE);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxmaxwidth () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-max-width"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-max-width");
        Assert.assertEquals(data.getInitialValue(getControl()), Double.MAX_VALUE);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxminheight () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-min-height"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-min-height");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxminwidth () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-min-width"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-min-width");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxopacity () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-opacity"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-opacity");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxprefheight () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-pref-height"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-pref-height");
        Assert.assertEquals(data.getInitialValue(getControl()), 600.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxprefwidth () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-pref-width"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-pref-width");
        Assert.assertEquals(data.getInitialValue(getControl()), 800.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxrotate () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-rotate"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-rotate");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxscalex () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-scale-x"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-scale-x");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxscaley () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-scale-y"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-scale-y");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxscalez () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-scale-z"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-scale-z");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxtranslatex () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-translate-x"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-translate-x");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxtranslatey () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-translate-y"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-translate-y");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testfxtranslatez () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-translate-z"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-translate-z");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    @Keywords(keywords="webkit")
    public void testvisibility () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("visibility"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("visibility");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
    }


}
