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
public class StylesProgressBarTest extends BaseStyleNodeTest {

    @Override
    Node getControl() {
        return new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                ProgressBar control = new ProgressBar();
                control.setSkin(new ProgressBarSkin(control));
                setResult(control);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Test
    public void testfxindeterminatebaranimationtime () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-indeterminate-bar-animation-time"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-indeterminate-bar-animation-time");
        Assert.assertEquals(data.getInitialValue(getControl()), 2.0);
    }

    @Test
    public void testfxindeterminatebarescape () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-indeterminate-bar-escape"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-indeterminate-bar-escape");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
    }

    @Test
    public void testfxindeterminatebarflip () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-indeterminate-bar-flip"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-indeterminate-bar-flip");
        Assert.assertEquals(data.getInitialValue(getControl()), true);
    }

    @Test
    public void testfxindeterminatebarlength () {
        Set<String> styleNames = getStyleNames();
        Assert.assertNotNull(styleNames);
        Assert.assertTrue(styleNames.contains("-fx-indeterminate-bar-length"));
        Map<String, CssMetaData> styles = getStyles();
        CssMetaData data = styles.get("-fx-indeterminate-bar-length");
        Assert.assertEquals(data.getInitialValue(getControl()), 60.0);
    }


}
