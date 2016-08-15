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
public class StylesTextTest extends BaseStyleNodeTest {

    @Override
    Node getControl() {
        return new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                Text control = new Text();

                setResult(control);
            }
        }.dispatch(Root.ROOT.getEnvironment());
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
    public void testfxfontfamily () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font-family"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font-family");
        Assert.assertEquals(data.getInitialValue(getControl()), "System");
    }

    @Test
    public void testfxfontsize () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font-size"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font-size");
        Assert.assertEquals(data.getInitialValue(getControl()), Font.getDefault().getSize());
    }

    @Test
    public void testfxfontsmoothingtype () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font-smoothing-type"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font-smoothing-type");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.text.FontSmoothingType.GRAY);
    }

    @Test
    public void testfxfontstyle () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font-style"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font-style");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.text.FontPosture.REGULAR);
    }

    @Test
    public void testfxfontweight () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-font-weight"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-font-weight");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.scene.text.FontWeight.NORMAL);
    }

    @Test
    public void testfxstrikethrough () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-strikethrough"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-strikethrough");
        Assert.assertEquals(data.getInitialValue(getControl()), false);
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
    public void testfxtextorigin () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-text-origin"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-text-origin");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.geometry.VPos.BASELINE);
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


}
