/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package org.jemmy.fx;

import org.jemmy.fx.ByID;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.ByTitleSceneLookup;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        assertEquals(50, Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<Scene>("title0")).get(0).getWindow().getX(), .0);
        assertEquals(200, Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<Scene>("title1")).get(0).getWindow().getX(), .0);
    }

    @Test
    public void circle() {
        Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<Scene>("title0")).
                as(Parent.class, Node.class).
                lookup(Ellipse.class).wait(1);
    }

    @Test
    public void square() {
        Wrap<? extends Group> group = Root.ROOT.
                lookup(Scene.class, new ByTitleSceneLookup<Scene>("title1")).wrap().
                as(Parent.class, Node.class).
                lookup(Group.class).wrap();
        group.as(Parent.class, Node.class).
                lookup(Rectangle.class).wait(1);
    }

    @Test
    public void byId() {
        Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<Scene>("title1")).
                as(Parent.class, Node.class).lookup(Rectangle.class,
                new ByID<Rectangle>("rect1")).wrap();
    }

    @Test
    public void byTitle() {
        Wrap<? extends Scene> scene = Root.ROOT.
                lookup(Scene.class, new ByTitleSceneLookup<Scene>("title1")).wrap();

        Wrap<? extends Scene> scene2 = Root.ROOT.lookup(Scene.class).wrap(0);
        assertEquals(scene.getControl(), scene2.getControl());
    }

    @Test
    public void ByText() {
        Root.ROOT.lookup(Scene.class, new ByTitleSceneLookup<Scene>("title1")).as(Parent.class, Node.class).
                lookup(Text.class, new ByText("text1")).wrap();
    }
}
