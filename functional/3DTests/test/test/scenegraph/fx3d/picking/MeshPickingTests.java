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
public abstract class MeshPickingTests extends PickingTestOverall {

    @Test(timeout = 20000)
    public void extremumTest() {
        int leftX = getLeftX();
        int topY = getTopY();
        int delta = getDelta();
        Rectangle rect = getAppRectangle();
        double e = 1;
        for (int dx = 0; dx < 2 * delta; dx += delta) {
            for (int dy = 0; dy < 2 * delta; dy += delta) {
                PickResult pr = click(new Point(rect.x + leftX + dx, rect.y + topY + dy));
                Assert.assertTrue("Extremum is not " + e + " in " + pr.getIntersectedPoint(),
                        Math.abs(e - pr.getIntersectedPoint().getZ()) < eps);
                e = -e;
            }
            e = -e;
        }
    }

    /**
     * Test Check that Intersected Points is correct in not extremum points.
     */
    @Test(timeout = 20000)
    public void valuesTest() {
        eps = 0.1;
        Rectangle rect = getAppRectangle();
        int leftX = getLeftX() + rect.x;
        int topY = getTopY() + rect.y;
        int delta = getDelta();
        int iterations = getIterations();

        final double miniDelta = ((double) delta) / ((double) iterations);
        Point3D points[] = new Point3D[iterations * 4];
        fillPathPointsArray(leftX, topY, miniDelta, 0D, points, 0, iterations);
        fillPathPointsArray(leftX + delta, topY, 0D, miniDelta, points, iterations, iterations);
        fillPathPointsArray(leftX + delta, topY + delta, -miniDelta, 0D, points, 2 * iterations, iterations);
        fillPathPointsArray(leftX, topY + delta, 0D, -miniDelta, points, 3 * iterations, iterations);
        for (int i = 0; i < 4 * iterations; i++) {
            double z = points[i].getZ();
            double expected = Math.sin(Math.PI / 2 + Math.PI / ((double) iterations) * (double) i);
//            System.out.println(expected + "  " + z);
            Assert.assertTrue("Expected " + expected + ", but was " + z, Math.abs(z - expected) < eps);
        }
    }

    /**
     * Test check that Intersected Distance is correct.
     */
    @Test(timeout = 20000)
    public void distanceTest() {
        eps = 15;
        Rectangle rect = getAppRectangle();
        int leftX = getLeftX() + rect.x;
        int topY = getTopY() + rect.y;
        int delta = getDelta();
        int iterations = getIterations();
        final double miniDelta = ((double) delta) / ((double) iterations);
//        double points[][] = new double[iterations * 4][4];
        double points[][] = new double[iterations * 4][3];

        fillPathDistanceArray(leftX, topY, miniDelta, 0D, points, 0, iterations);

        fillPathDistanceArray(leftX + delta, topY, 0D, miniDelta, points, iterations, iterations);
        fillPathDistanceArray(leftX + delta, topY + delta, -miniDelta, 0D, points, 2 * iterations, iterations);
        fillPathDistanceArray(leftX, topY + delta, 0D, -miniDelta, points, 3 * iterations, iterations);
        for (int i = 0; i < 4 * iterations; i++) {

            double expectedVal = -Math.sin(Math.PI / 2 + Math.PI / ((double) iterations) * (double) i);
            double expectedDistance = getDistanceExpectedValue(100 * expectedVal, points[i][1], points[i][2]);
            Assert.assertTrue("Expected " + expectedDistance + ", but was " + points[i][0], Math.abs(points[i][0] - expectedDistance) < eps);
        }
    }

    private void fillPathPointsArray(int startX, int startY, double dx, double dy, Point3D points[], int start, int iterations) {
        for (int i = 0; i < iterations; i++) {
            Point clickPoint = new Point(startX + (i * dx), startY + (i * dy));
            PickResult pr = click(clickPoint);
            points[start + i] = pr.getIntersectedPoint();
        }
    }

    private void fillPathDistanceArray(int startX, int startY, double dx, double dy, double dist[][], int start, int iterations) {
        Rectangle rect = getAppRectangle();
        for (int i = 0; i < iterations; i++) {
            Point clickPoint = new Point(startX + (i * dx), startY + (i * dy));
            PickResult pr = click(clickPoint);
            dist[start + i][0] = pr.getIntersectedDistance();
//            dist[start + i][1] = pr.getIntersectedPoint().getX();
//            dist[start + i][2] = pr.getIntersectedPoint().getY();
//            dist[start + i][3] = pr.getIntersectedPoint().getZ();
            dist[start + i][1] = Math.abs(clickPoint.x - rect.width / 2);
            dist[start + i][2] = Math.abs(clickPoint.y - rect.height / 2);
        }
    }

    protected abstract int getDelta();//136

    protected abstract int getIterations();//10
}
