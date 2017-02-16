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
import javafx.scene.control.skin.*;
import javafx.scene.paint.Paint;
import javafx.geometry.Insets;
import com.sun.javafx.scene.layout.region.RepeatStruct;
import com.sun.javafx.scene.layout.region.BorderImageSlices;


/**
 * Generation test
 * @author sergey.lugovoy@oracle.com
 */
public class StylesPaginationTest extends BaseStyleNodeTest {

    @Override
    Node getControl() {
        return new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                Pagination control = new Pagination();
                control.setSkin(new PaginationSkin(control));
                setResult(control);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Test
    public void testfxarrowsvisible () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-arrows-visible"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-arrows-visible");
        Assert.assertEquals(data.getInitialValue(getControl()), false);
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
    public void testfxmaxpageindicatorcount () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-max-page-indicator-count"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-max-page-indicator-count");
        Assert.assertEquals(data.getInitialValue(getControl()), 10);
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
    public void testfxpageinformationalignment () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-page-information-alignment"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-page-information-alignment");
        Assert.assertEquals(data.getInitialValue(getControl()), javafx.geometry.Side.BOTTOM);
    }

    @Test
    public void testfxpageinformationvisible () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-page-information-visible"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-page-information-visible");
        Assert.assertEquals(data.getInitialValue(getControl()), false);
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
    public void testfxtooltipvisible () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-tooltip-visible"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-tooltip-visible");
        Assert.assertEquals(data.getInitialValue(getControl()), false);
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
    public void testvisibility () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("visibility"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("visibility");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
    }


}
