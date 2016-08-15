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
package javafx.draganddrop;

import client.test.Smoke;
import static javafx.draganddrop.ExtendedDragApplication.TITLE1;
import static javafx.draganddrop.SimpleDragApplication.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByTitleSceneLookup;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Nazarov
 */
@RunWith(FilteredTestRunner.class)
public class DragEventOneStageTest extends TestBase {

    static Wrap<? extends Scene> scene;
    Point fromPoint;
    Point toPoint;
    Wrap<? extends Node> from;
    Wrap<? extends Node> to;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Utils.launch(SimpleDragApplication.class, null);
        scene = Root.ROOT.lookup(new DragDropWithControlsBase.ByRootNodeIdLookup<Scene>(TITLE1)).wrap();
    }

    @Before
    public void findSourceTarget() {
        from = Lookups.byID(scene, ID_RECTANGLE, Node.class);
        to = Lookups.byID(scene, ID_ELLIPSE, Node.class);
        fromPoint = from.getClickPoint();
        toPoint = to.getClickPoint();
    }

    /**
     * tests drag event getScreenX getScreenY
     */
    @Smoke
    @Test(timeout = 300000)
    public void testScreenXY() throws InterruptedException {
        dnd(from, fromPoint, to, toPoint);
        assertEquals(to.toAbsolute(toPoint).getX(), (int) lastDragOverEvent.getScreenX());
        assertEquals(to.toAbsolute(toPoint).getY(), (int) lastDragOverEvent.getScreenY());
    }

    /**
     * tests drag event getSceneX getSceneY
     */
    @Smoke
    @Test(timeout = 300000)
    public void testSceneXY() throws InterruptedException {
        dnd(from, fromPoint, to, toPoint);
        assertEquals((to.toAbsolute(toPoint).x - scene.toAbsolute(new Point(0, 0)).x), (int) lastDragOverEvent.getSceneX());
        assertEquals((to.toAbsolute(toPoint).y - scene.toAbsolute(new Point(0, 0)).y), (int) lastDragOverEvent.getSceneY());
    }

    /**
     * tests drag event getX getY
     */
    @Smoke
    @Test(timeout = 300000)
    public void testXY() throws InterruptedException {
        dnd(from, fromPoint, to, toPoint);
        assertEquals((to.toAbsolute(toPoint).x - from.toAbsolute(new Point(0, 0)).x), (int) lastDragOverEvent.getX());
        assertEquals((to.toAbsolute(toPoint).y - from.toAbsolute(new Point(0, 0)).y), (int) lastDragOverEvent.getY());
    }
}
