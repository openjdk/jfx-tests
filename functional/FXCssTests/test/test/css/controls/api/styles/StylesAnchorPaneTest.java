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
public class StylesAnchorPaneTest extends BaseStyleNodeTest {

    @Override
    Node getControl() {
        return new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                AnchorPane control = new AnchorPane();

                setResult(control);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Test
    public void testfxbackgroundcolor () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-background-color"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-background-color");
        Assert.assertArrayEquals((Paint[])data.getInitialValue(getControl()), new Paint[]{Paint.valueOf("transparent")});
    }


    @Test
    public void testfxbackgroundimage () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-background-image"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-background-image");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxbackgroundinsets () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-background-insets"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-background-insets");
        Assert.assertArrayEquals((Insets[])data.getInitialValue(getControl()), new Insets[]{new Insets(0.0, 0.0, 0.0, 0.0)});
    }

    @Test
    public void testfxbackgroundposition () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-background-position"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-background-position");
        Assert.assertTrue(checkBackgroundPosition((BackgroundPosition[]) data.getInitialValue(getControl()), new BackgroundPosition[]{new BackgroundPosition(javafx.geometry.Side.LEFT,0.0,true,javafx.geometry.Side.TOP,0.0,true)}));
    }

    @Test
    public void testfxbackgroundradius () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-background-radius"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-background-radius");
        Assert.assertArrayEquals((CornerRadii[])data.getInitialValue(getControl()),
                new CornerRadii[]{new CornerRadii(0.0)});
    }

    @Test
    public void testfxbackgroundrepeat () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-background-repeat"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-background-repeat");
        Assert.assertTrue(checkRepeatStruct((RepeatStruct[]) data.getInitialValue(getControl()), new RepeatStruct[]{new RepeatStruct(javafx.scene.layout.BackgroundRepeat.REPEAT,javafx.scene.layout.BackgroundRepeat.REPEAT)}));
    }

    @Test
    public void testfxbackgroundsize () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-background-size"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-background-size");
        Assert.assertArrayEquals((BackgroundSize[])data.getInitialValue(getControl()), new BackgroundSize[]{new BackgroundSize(-1.0,-1.0,true,true,false,false)});
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
    public void testfxbordercolor () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-color"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-color");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxborderimageinsets () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-image-insets"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-image-insets");
        Assert.assertArrayEquals((Insets[])data.getInitialValue(getControl()), new Insets[]{new Insets(0.0, 0.0, 0.0, 0.0)});
    }

    @Test
    public void testfxborderimagerepeat () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-image-repeat"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-image-repeat");
        Assert.assertTrue(checkRepeatStruct((RepeatStruct[]) data.getInitialValue(getControl()), new RepeatStruct[]{new RepeatStruct(javafx.scene.layout.BackgroundRepeat.REPEAT,javafx.scene.layout.BackgroundRepeat.REPEAT)}));
    }

    @Test
    public void testfxborderimageslice () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-image-slice"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-image-slice");
        Assert.assertTrue(checkBorderImageSlices((BorderImageSlices[]) data.getInitialValue(getControl()), new BorderImageSlices[]{new BorderImageSlices(new BorderWidths(1.0,1.0,1.0,1.0,true,true,true,true),false)}));
    }

    @Test
    public void testfxborderimagesource () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-image-source"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-image-source");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxborderimagewidth () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-image-width"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-image-width");
        Assert.assertArrayEquals((BorderWidths[])data.getInitialValue(getControl()), new BorderWidths[]{new BorderWidths(0, 0, 0, 0)});
    }

    @Test
    public void testfxborderinsets () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-insets"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-insets");
        Assert.assertArrayEquals((Insets[])data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxborderradius () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-radius"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-radius");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxborderstyle () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-style"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-style");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxborderwidth () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-border-width"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-border-width");
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
    public void testfxopacity () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-opacity"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-opacity");
        Assert.assertEquals(data.getInitialValue(getControl()), 1.0);
    }

    @Test
    public void testfxpadding () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-padding"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-padding");
        Assert.assertEquals(data.getInitialValue(getControl()), new javafx.geometry.Insets(0.0,0.0,0.0,0.0));
    }

    @Test
    public void testfxpositionshape () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-position-shape"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-position-shape");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
    }

    @Test
    public void testfxregionbackground () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-region-background"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-region-background");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxregionborder () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-region-border"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-region-border");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
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
    public void testfxscaleshape () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-scale-shape"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-scale-shape");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
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
    public void testfxshape () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-shape"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-shape");
        Assert.assertEquals(data.getInitialValue(getControl()), null);
    }

    @Test
    public void testfxsnaptopixel () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-snap-to-pixel"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-snap-to-pixel");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
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
