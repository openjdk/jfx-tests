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
package test.css.stylesheets;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.Utils;

/**
 *
 * @author sergey.lugovoy@oracle.com
 */
public class RT_17348Test {

    @BeforeClass
    public static void runUI() {
        Utils.launch(StylesheetApp.class, null);
    }

    @Test
    public void changeStylesheetTest() {
        Wrap<? extends Scene> scene = Root.ROOT.lookup(Scene.class).wrap();
        Parent<Node> sceneParent = scene.as(Parent.class, Node.class);
        final Wrap<Region> pane = (Wrap<Region>) sceneParent.lookup(new ByID<Node>(StylesheetApp.EXAMPLE_ID)).wrap();
        Assert.assertNotNull(pane);
        Assert.assertNotNull(pane.getControl().backgroundProperty().get());
        Assert.assertEquals(pane.getControl().backgroundProperty().get(), new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        Assert.assertNotSame(pane.getControl().backgroundProperty().get(), new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Wrap<? extends Text> button = sceneParent.lookup(Text.class).wrap();
        button.mouse().click();
        pane.waitState(new State() {
            @Override
            public Object reached() {
                return !pane.getControl().backgroundProperty().get().equals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))) ? true : null;
            }
        });
        Assert.assertNotNull(pane.getControl().backgroundProperty().get());
        Assert.assertEquals(pane.getControl().backgroundProperty().get(), new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Assert.assertNotSame(pane.getControl().backgroundProperty().get(), new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
