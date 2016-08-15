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
public class StylesToggleButtonTest extends BaseStyleNodeTest {

    @Override
    Node getControl() {
        return new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                ToggleButton control = new ToggleButton();
                control.setSkin(new ToggleButtonSkin(control));
                setResult(control);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Test
    public void testfxalignment () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-alignment"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-alignment");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.geometry.Pos.CENTER);
    }

    @Test
    public void testfxblendmode () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-blend-mode"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-blend-mode");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxcontentdisplay () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-content-display"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-content-display");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.control.ContentDisplay.LEFT);
    }

    @Test
    public void testfxcursor () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-cursor"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-cursor");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxeffect () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-effect"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-effect");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxellipsisstring () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-ellipsis-string"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-ellipsis-string");
        Assert.assertEquals(data.getInitialValue(getControl()), "...");
    }

    @Test
    public void testfxfont () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.text.Font.getDefault());
    }

    @Test
    public void testfxgraphic () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-graphic"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-graphic");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxgraphictextgap () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-graphic-text-gap"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-graphic-text-gap");
        Assert.assertEquals(data.getInitialValue(getControl()), 4.0);
    }

    @Test
    public void testfxlabelpadding () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-label-padding"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-label-padding");
        Assert.assertEquals(data.getInitialValue(getControl()), new javafx.geometry.Insets(0.0,0.0,0.0,0.0));
    }

    @Test
    public void testfxopacity () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-opacity"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-opacity");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    public void testfxrotate () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-rotate"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-rotate");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    public void testfxscalex () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-scale-x"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-scale-x");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    public void testfxscaley () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-scale-y"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-scale-y");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    public void testfxscalez () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-scale-z"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-scale-z");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    public void testfxskin () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-skin"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-skin");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxtextalignment () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-text-alignment"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-text-alignment");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.text.TextAlignment.LEFT);
    }

    @Test
    public void testfxtextfill () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-text-fill"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-text-fill");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.paint.Color.valueOf("0x000000ff"));
    }

    @Test
    public void testfxtextoverrun () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-text-overrun"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-text-overrun");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.control.OverrunStyle.ELLIPSIS);
    }

    @Test
    public void testfxtranslatex () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-translate-x"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-translate-x");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    public void testfxtranslatey () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-translate-y"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-translate-y");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    public void testfxtranslatez () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-translate-z"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-translate-z");
        Assert.assertEquals(data.getInitialValue(getControl()), 0.0);
    }

    @Test
    public void testfxunderline () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-underline"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-underline");
        Assert.assertEquals(data.getInitialValue(getControl()), false);
    }

    @Test
    public void testfxwraptext () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-wrap-text"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-wrap-text");
        Assert.assertEquals(data.getInitialValue(getControl()), false);
    }

    @Test
    public void testvisibility () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("visibility"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("visibility");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
    }


}
