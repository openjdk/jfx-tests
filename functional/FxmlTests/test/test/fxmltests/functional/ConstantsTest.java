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

package test.fxmltests.functional;


import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import junit.framework.Assert;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.BeforeClass;
import org.junit.Test;
import test.fxmltests.app.FxmlConstantApp;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;

public class ConstantsTest extends TestBase{

    @BeforeClass
    public static void runUI () {
        Utils.launch(FxmlConstantApp.class, null);
    }

    /**
     * Testing default property
     */
    @Test
    public void testDefaultProperty () {
        testCommon(FxmlConstantApp.Pages.property.name(), null, false, true);
        Wrap<? extends Scene> sceneWrap = Root.ROOT.lookup(Scene.class).wrap();
        Parent<Node> sceneParent = sceneWrap.as(Parent.class, Node.class);
        Wrap<? extends AnchorPane> pane1 = sceneParent.lookup(AnchorPane.class, new ByID<AnchorPane>("anchorPane")).wrap();
        Assert.assertNotNull(pane1);
        Assert.assertEquals(100.0, pane1.getControl().getPrefWidth());
        Assert.assertEquals(160.0, pane1.getControl().getHeight());
    }

    /**
     * Testing constant property
     * @testableAssertId constant
     */
    @Test
    public void testConstantProperty () {
        testCommon(FxmlConstantApp.Pages.property.name(), null, false, true);
        Wrap<? extends Scene> sceneWrap = Root.ROOT.lookup(Scene.class).wrap();
        Parent<Node> sceneParent = sceneWrap.as(Parent.class, Node.class);
        Wrap<? extends Rectangle> rectangle = sceneParent.lookup(Rectangle.class, new ByID<Rectangle>("rectangle")).wrap();
        Assert.assertNotNull(rectangle.getControl().getFill());
        Assert.assertEquals(Color.YELLOW, Color.valueOf(rectangle.getControl().getFill().toString()));
        Wrap<? extends AnchorPane> anchorPane = Lookups.byID(sceneParent, "anchorPane1", AnchorPane.class);
        Assert.assertNotNull(anchorPane);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, anchorPane.getControl().getMaxHeight());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, anchorPane.getControl().getMinHeight());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, anchorPane.getControl().getMinWidth());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, anchorPane.getControl().getMaxWidth());
        Wrap<? extends Button> button = Lookups.byID(sceneParent, "button", Button.class);
        Assert.assertNotNull(button);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, button.getControl().getMinHeight());
        Assert.assertEquals(Double.NEGATIVE_INFINITY, button.getControl().getMinWidth());
    }
}
