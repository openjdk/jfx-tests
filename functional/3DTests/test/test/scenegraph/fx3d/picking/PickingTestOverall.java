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
import javafx.scene.paint.Color;
import junit.framework.Assert;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.junit.Test;
import test.scenegraph.fx3d.utils.PickingTestCase.TranslationMode;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class PickingTestOverall extends PickingTestFunctions {

    /**
     * eps - maximum expected distance between Picking result and expected
     * value.
     */
    protected static double eps = 0.02;

    protected static void setDefaultEps() {
        eps = 0.02;
    }

    protected Point getNotNullClickPoint() {
        Rectangle rect = getAppRectangle();
        return new Point((rect.width + rect.x) / 2, (rect.height + rect.y) / 2);
    }

    protected Point getNullClickPoint() {
        Rectangle rect = getAppRectangle();
        return new Point(rect.width + rect.x - 1, rect.height + rect.y - 1);
    }

    protected abstract int getLeftX();

    protected abstract int getTopY();

    /**
     * Test for support movable Camera (equals of Intersected Points).
     */
    @Test(timeout = 600000)
    public void cameraSupportTest() {
        int delta = 50;
        Rectangle rect = getAppRectangle();
        Point3D groupPoints[][] = new Point3D[rect.width / delta][rect.height / delta];
        Point3D camPoints[][] = new Point3D[rect.width / delta][rect.height / delta];
        setTranslationMode(TranslationMode.GroupTranslation);
        for (int x = 0; x + 1 < rect.width; x += delta) {
            for (int y = 0; y + 1 < rect.height; y += delta) {
                PickResult pr = click(new Point(rect.x + x + 1, rect.y + y + 1));
                if (pr == null) {
                    groupPoints[x / delta][y / delta] = null;
                } else {
                    groupPoints[x / delta][y / delta] = pr.getIntersectedPoint();
                }
            }
        }
        setTranslationMode(TranslationMode.CameraTranslation);
        for (int x = 0; x + 1 < rect.width; x += delta) {
            for (int y = 0; y + 1 < rect.height; y += delta) {
                PickResult pr = click(new Point(rect.x + x + 1, rect.y + y + 1));
                if (pr == null) {
                    camPoints[x / delta][y / delta] = null;
                } else {
                    camPoints[x / delta][y / delta] = pr.getIntersectedPoint();
                }
            }
        }
        Point3D expected = new Point3D(getXTranslation(), getYTranslation(), getZTranslation());
        Point3D zero = new Point3D(0, 0, 0);

        for (int x = 0; x + 1 < rect.width; x += delta) {
            for (int y = 0; y + 1 < rect.height; y += delta) {
                Point3D cPoint = camPoints[x / delta][y / delta];
                Point3D gPoint = groupPoints[x / delta][y / delta];
                if (cPoint != null && gPoint != null) {
                    Point3D diff = new Point3D(gPoint.getX() - cPoint.getX(),
                            gPoint.getY() - cPoint.getY(), gPoint.getZ() - cPoint.getZ());
                    Assert.assertTrue("Points has diff " + diff + ", expected " + expected, isEqualsPoints(expected, diff) || isEqualsPoints(diff, zero));
                } else if (cPoint != null || gPoint != null) {
                    Assert.fail("CPoint: " + cPoint + ", but gPoint: " + gPoint);
                }

            }

        }
    }

    /**
     * Test check that PickResult not save last result.
     */
    @Test(timeout = 10000)
    public void pickResultTest() {
        click(getNotNullClickPoint());
        PickResult pr = click(getNullClickPoint());
        Assert.assertNull("MouseEvent save last result", pr.getIntersectedNode());
    }

    /**
     * Test check that picking works with any fill.
     */
    @Test(timeout = 10000)
    public void fillTest() {
        PickResult pr[][] = new PickResult[2][2];
        pr[0][0] = click(getNotNullClickPoint());
        pr[0][1] = click(getNullClickPoint());
        setFill(Color.RED);
        pr[1][0] = click(getNotNullClickPoint());
        pr[1][1] = click(getNullClickPoint());
        Assert.assertTrue("PickResult is differs in Not Null Popint!", isEqualsPickResults(pr[0][0], pr[1][0]));
        Assert.assertTrue("PickResult is differs in Null Popint!", isEqualsPickResults(pr[0][1], pr[1][1]));
    }

    private static boolean isEqualsPickResults(PickResult pr1, PickResult pr2) {

        if (pr1.getIntersectedDistance() != pr2.getIntersectedDistance()
                || pr1.getIntersectedFace() != pr2.getIntersectedFace()
                || pr1.getIntersectedNode() != pr2.getIntersectedNode()) {
            return false;
        }
        if (pr1.getIntersectedPoint() != null) {
            if (!pr1.getIntersectedPoint().equals(pr2.getIntersectedPoint())) {
                return false;
            }
        } else if (pr2.getIntersectedPoint() != null) {
            return false;
        }
        if (pr1.getIntersectedTexCoord() != null) {
            if (!pr1.getIntersectedTexCoord().equals(pr2.getIntersectedTexCoord())) {
                return false;
            }
        } else if (pr2.getIntersectedTexCoord() != null) {
            return false;
        }
        return true;
    }

    public static boolean isEqualsPoints(Point3D pt1, Point3D pt2) {
        if (Math.abs(pt1.getX() - pt2.getX()) > eps
                || Math.abs(pt1.getY() - pt2.getY()) > eps
                || Math.abs(pt1.getZ() - pt2.getZ()) > eps) {
            return false;
        }
        return true;
    }
}
