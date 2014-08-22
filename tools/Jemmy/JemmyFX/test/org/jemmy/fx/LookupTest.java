/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.fx;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author shura
 */
public class LookupTest {

    public LookupTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AppExecutor.executeNoBlock(TestApp.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void scenes() {
        assertEquals(50, Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<>("title0")).get(0).getWindow().getX(), .0);
        assertEquals(200, Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<>("title1")).get(0).getWindow().getX(), .0);
    }

    @Test
    public void circle() {
        Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<>("title0")).
                as(Parent.class, Node.class).
                lookup(Ellipse.class).wait(1);
    }

    @Test
    public void square() {
        Wrap<? extends Group> group = Root.ROOT.
                lookup(Scene.class, new ByTitleSceneLookup<>("title1")).wrap().
                as(Parent.class, Node.class).
                lookup(Group.class).wrap();
        group.as(Parent.class, Node.class).
                lookup(Rectangle.class).wait(1);
    }

    @Test
    public void byId() {
        Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<>("title1")).
                as(Parent.class, Node.class).lookup(Rectangle.class,
                new ByID<Rectangle>("rect1")).wrap();
    }

    @Test
    public void byTitle() {
        Wrap<? extends Scene> scene = Root.ROOT.
                lookup(Scene.class, new ByTitleSceneLookup<>("title1")).wrap();

        Wrap<? extends Scene> scene2 = Root.ROOT.lookup(Scene.class).wrap(0);
        assertEquals(scene.getControl(), scene2.getControl());
    }

    @Test
    public void ByText() {
        Wrap<? extends Text> w = Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<>("title1")).as(Parent.class, Node.class).
                lookup(Text.class, new ByText("text1")).wrap();
        w.as(org.jemmy.interfaces.Text.class).text();
    }
}
