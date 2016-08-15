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
package test.scenegraph.fx3d.picking;

import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Point3D;
import javafx.scene.input.PickResult;
import junit.framework.Assert;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.junit.Test;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class ShapesPickingTests extends PickingTestOverall {

    protected abstract void setShape(ShapesPickingAbstractApp.Shape shape);

    /**
     * Test for getting Intersected Point from center of Sphere.
     */
    @Test(timeout = 10000)
    public void sphereCenterTest() {
        setShape(ShapesPickingAbstractApp.Shape.Sphere);
        centerTestCase();
    }

    /**
     * Test for getting Intersected Point from center of Box.
     */
    @Test(timeout = 10000)
    public void boxCenterTest() {
        setShape(ShapesPickingAbstractApp.Shape.Box);
        centerTestCase();
    }

    /**
     * Test for getting Intersected Point from center of Cylinder.
     */
    @Test(timeout = 10000)
    public void cylinderCenterTest() {
        setShape(ShapesPickingAbstractApp.Shape.Cylinder);
        centerTestCase();
    }

    /**
     * Tests for correctness of the boundaries determination.
     */
    @Test(timeout = 10000)
    public void sphereOutsideBorderTest() {
        setShape(ShapesPickingAbstractApp.Shape.Sphere);
        sphereBorderTestCase(false);
    }

    /**
     * Tests for correctness of the boundaries determination.
     */
    @Test(timeout = 10000)
    public void sphereInsideBorderTest() {
        setShape(ShapesPickingAbstractApp.Shape.Sphere);
        sphereBorderTestCase(true);
    }

    /**
     * Tests for correctness of the boundaries determination.
     */
    @Test(timeout = 10000)
    public void cylinderOutsideBorderTest() {
        setShape(ShapesPickingAbstractApp.Shape.Cylinder);
        cylinderBorderTestCase(false);
    }

    /**
     * Tests for correctness of the boundaries determination.
     */
    @Test(timeout = 10000)
    public void cylinderInsideBorderTest() {
        setShape(ShapesPickingAbstractApp.Shape.Cylinder);
        cylinderBorderTestCase(true);
    }

    /**
     * Tests for correctness of the boundaries determination.
     */
    @Test(timeout = 10000)
    public void boxOutsideBorderTest() {
        setShape(ShapesPickingAbstractApp.Shape.Box);
        boxBorderTestCase(false);
    }

    /**
     * Tests for correctness of the boundaries determination.
     */
    @Test(timeout = 10000)
    public void boxInsideBorderTest() {
        setShape(ShapesPickingAbstractApp.Shape.Box);
        boxBorderTestCase(true);
    }

    /**
     * Tests for correctness of the distance determination.
     */
    @Test(timeout = 40000)
    public void boxDistanceTest() {
        eps = 8;
        setShape(ShapesPickingAbstractApp.Shape.Box);
        List<Point> clickPoints = new LinkedList<>();
        int delta = 10;
        int edge = 100;
        Rectangle rect = getAppRectangle();

        int leftX = rect.x + rect.width / 2 - edge / 2;
        int topY = rect.y + rect.height / 2 - edge / 2;
        for (int dx = 0; dx <= edge; dx += delta) {
            for (int dy = 0; dy <= edge; dy += delta) {
                clickPoints.add(new Point(leftX + dx, topY + dy));
            }
        }
        distanceTestCase(clickPoints.toArray(new Point[0]));
    }

    /**
     * Tests for correctness of the distance determination.
     */
    @Test(timeout = 10000)
    public void cylinderDistanceTest() {
        eps = 8;
        setShape(ShapesPickingAbstractApp.Shape.Cylinder);
        List<Point> clickPoints = new LinkedList<>();
        int height = 100;
        int delta = 10;
        Rectangle rect = getAppRectangle();
        int x = rect.x + rect.width / 2;
        int topY = rect.y + rect.height / 2 - height / 2;
        for (int dy = 0; dy <= height; dy += delta) {
            clickPoints.add(new Point(x, topY + dy));
        }
        distanceTestCase(clickPoints.toArray(new Point[0]));
    }

    /**
     * Tests for correctness of the distance determination.
     */
    @Test(timeout = 10000)
    public void sphereDistanceTest() {
        eps = 8;
        setShape(ShapesPickingAbstractApp.Shape.Sphere);
        Rectangle rect = getAppRectangle();
        Point[] clickPoints = new Point[]{
            new Point(rect.x + rect.width / 2, rect.y + rect.height / 2)
        };
        distanceTestCase(clickPoints);
    }

    private void distanceTestCase(Point[] clickPoints) {
        for (int i = 0; i < clickPoints.length; i++) {
            PickResult pr = click(clickPoints[i]);
//            Assert.fail("rewrite");
            Rectangle rect = getAppRectangle();
            double expected = getDistanceExpectedValue(100, Math.abs(rect.x + rect.width / 2 - clickPoints[i].x), Math.abs(rect.y + rect.height / 2 - clickPoints[i].y));
            Assert.assertTrue("Intersected Distance error: expected "
                    + expected + ", but was " + pr.getIntersectedDistance()
                    + " in point " + clickPoints[i],
                    Math.abs(expected - pr.getIntersectedDistance()) <= eps);
            //System.out.println(pr.getIntersectedDistance());
            //System.out.println(application.getZTranslation() + cameraFixedEyePositionCorrection + SCALE + eps);
        }
    }

    private void centerTestCase() {
        Point3D expected = new Point3D(0, 0, -1.0);
        Rectangle rect = getAppRectangle();
        PickResult pr = click(new Point(rect.x + rect.width / 2 - 1, rect.y + rect.height / 2 - 1));
        if (!isEqualsPoints(expected, pr.getIntersectedPoint())) {
            Assert.fail("expected " + expected + "but was " + pr.getIntersectedPoint());
        }
    }

    private void cylinderBorderTestCase(boolean inside) {
        int radius = 50;
        int height = 100;
        Rectangle rect = getAppRectangle();
        int leftX = getLeftX() + rect.x;
        int topY = getTopY() + rect.y;
        if (inside) {
            leftX += 2;
            topY += 2;
            radius -= 4;
            height -= 4;
        } else {
            leftX -= 2;
            topY -= 2;
            radius += 2;
            height += 6;
        }
        for (int dx = 0; dx <= 2 * radius; dx += radius) {
            for (int dy = 0; dy <= height; dy += height / 2) {
                if (!(dx == height / 2 && dy == radius)) {
                    Point clickPoint = new Point(leftX + dx, topY + dy);
                    if (dx == radius) {
                        clickPoint.x += (dy > height / 2) ? 2 : -2;
                    }
                    if (inside) {
                        Assert.assertNotNull("PickResult point to null from inside:(" + (clickPoint.x) + "," + (clickPoint.y) + ")", click(clickPoint).getIntersectedNode());
                    } else {
                        if (!(dx == radius && dy == height / 2)) {
                            Assert.assertNull("PickResult point to Cylinder from outside:(" + (clickPoint.x) + "," + (clickPoint.y) + ")", click(clickPoint).getIntersectedNode());
                        }
                    }
                }
            }
        }
    }

    private void boxBorderTestCase(boolean inside) {
        int edge = 100;
        Rectangle rect = getAppRectangle();
        int leftX = getLeftX() + rect.x;
        int topY = getTopY() + rect.y;
        if (!inside) {
            edge += 4;
            leftX--;
            topY--;
        }

        for (int dx = 0; dx <= edge; dx += edge / 2) {
            for (int dy = 0; dy <= edge; dy += edge / 2) {
                if (!(dx == edge / 2 && dy == edge / 2)) {
                    Point clickPoint = new Point(leftX + dx, topY + dy);
                    if (inside) {
                        Assert.assertNotNull("PickResult point to null from inside:(" + (leftX + dx) + "," + (topY + dy) + ")", click(clickPoint).getIntersectedNode());
                    } else {
                        System.out.println(click(clickPoint));
                        Assert.assertNull("PickResult point to Box from outside:(" + (leftX + dx) + "," + (topY + dy) + ")", click(clickPoint).getIntersectedNode());
                    }
                }
            }
        }
    }

    private void sphereBorderTestCase(boolean inside) {
        Rectangle rect = getAppRectangle();
        int leftX = getLeftX() + rect.x;
        int topY = getTopY() + rect.y;
        int sphereRad = 50;
        if (inside) {
            leftX += 2;
            topY += 2;
            sphereRad--;
        } else {
            leftX -= 2;
            topY -= 2;
            sphereRad += 4;
        }


        for (int dx = 0; dx <= sphereRad * 2; dx += sphereRad) {
            for (int dy = 0; dy <= sphereRad * 2; dy += sphereRad) {
                if (inside) {
                    if (!((dx == 0 || dx == sphereRad * 2) && (dy == 0 || dy == sphereRad * 2))) {
                        if (null == click(new Point(leftX + dx, topY + dy)).getIntersectedNode()) {
                            Assert.fail("PickResult point to null from inside:(" + (leftX + dx) + "," + (topY + dy) + ")");
                        }
                    }
                } else {
                    if (dx != dy) {

                        if (null != click(new Point(leftX + dx, topY + dy)).getIntersectedNode()) {
                            Assert.fail("PickResult point to shape from outside:(" + (leftX + dx) + "," + (topY + dy) + ")");
                        }
                    }
                }
            }
        }
    }
}
