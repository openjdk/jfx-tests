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
import static javafx.draganddrop.ExtendedDragApplication.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
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
 * @author Victor Shubov http://javafx-jira.kenai.com/browse/RT-16916
 */
@RunWith(FilteredTestRunner.class)
public class ExtendedDragTest extends TestBase {

    static Wrap<? extends Scene> scene;
    Point fromPoint;
    Point toEllipsePoint;
    Point toCirclePoint;
    Point toParentPoint;
    Wrap<? extends Node> from;
    Wrap<? extends Node> toEllipse;
    Wrap<? extends Node> toCircle;
    Wrap<? extends Node> toParentNode;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Utils.launch(ExtendedDragApplication.class, null);
        //Commented out: interoperability doesn't support lookup by title.
        //scene = Root.ROOT.lookup(new ByTitleSceneLookup<Scene>(TITLE1)).wrap();
        scene = Root.ROOT.lookup(new DragDropWithControlsBase.ByRootNodeIdLookup<Scene>(TITLE1)).wrap();
    }

    @Before
    public void findSourceTarget() {
        from = Lookups.byID(scene, ID_RECTANGLE, Node.class);
        fromPoint = from.getClickPoint();

        toEllipse = Lookups.byID(scene, ID_ELLIPSE, Node.class);
        toEllipsePoint = toEllipse.getClickPoint();
        toEllipsePoint.x = (int) (toEllipsePoint.x
                - ((Ellipse) toEllipse.getControl()).getRadiusX() + 2);

        toCircle = Lookups.byID(scene, ID_CIRCLEOVERELLIPSE, Node.class);
        toCirclePoint = toCircle.getClickPoint();

        toParentNode = Lookups.byID(scene, ID_VB, Node.class);
        toParentPoint = toParentNode.getClickPoint();

        toParentPoint.x = (int) (toParentPoint.x
                - ((Pane) toParentNode.getControl()).getWidth() / 2 + 3);
        toParentPoint.y = (int) (toParentPoint.y
                - ((Pane) toParentNode.getControl()).getHeight() / 2 + 3);
    }

    /**
     * basic test drag'n'drop gesture
     */
    //@Smoke
    @Test(timeout = 300000)
    public void testScreenXY1() throws InterruptedException {
        clearEvents();
        dnd(from, fromPoint, toEllipse, toEllipsePoint);
        assertEquals((Object) from.getControl(), lastEvents.get(EventType.drop).getGestureSource());
        assertEquals((Object) toEllipse.getControl(), lastEvents.get(EventType.drop).getGestureTarget());
    }

    /**
     * test drag'n'drop gesture to some parent node container
     */
    //@Smoke
    @Test(timeout = 300000)
    public void testScreenXY2() throws InterruptedException {
        clearEvents();
        dnd(from, fromPoint, toParentNode, toParentPoint);
        assertEquals((Object) from.getControl(), lastEvents.get(EventType.drop).getGestureSource());
        assertEquals((Object) toParentNode.getControl(), lastEvents.get(EventType.drop).getGestureTarget());
    }

    /**
     * test drag'n'drop gesture to some overlapped node
     */
    //@Smoke
    @Test(timeout = 300000)
    public void testScreenXY3() throws InterruptedException {
        clearEvents();
        dnd(from, fromPoint, toCircle, toCirclePoint);
        assertEquals((Object) from.getControl(), lastEvents.get(EventType.drop).getGestureSource());
        assertEquals((Object) toCircle.getControl(), lastEvents.get(EventType.drop).getGestureTarget());
    }
}
