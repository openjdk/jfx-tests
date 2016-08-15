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
package javafx.scene.control.test.nchart;

import javafx.geometry.Side;
import javafx.scene.shape.Line;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public class Geometry {

    /**
     * Evaluated starting from Ox axis in clockwise direction.
     *
     * @param line
     * @return angle in grads
     */
    static double getLineAngle(Line line) {
        double OY = line.getEndY() - line.getStartY();
        double OX = line.getEndX() - line.getStartX();
        return Math.atan(-OY / OX) / Math.PI * 180 + (OX < 0 ? 180 : 0);
    }

    /**
     * Angles equality modulo full rotation.
     */
    static boolean areAnglesEqual(double angle1, double angle2) {
        angle1 = reductionInto0to360(angle1);
        angle2 = reductionInto0to360(angle2);
        double min = Math.min(Math.min(Math.abs(angle1 - angle2 - 360), Math.abs(angle1 - angle2 + 360)), Math.abs(angle1 - angle2));
        return min < 3;//Found experimentlly. 2 - could be not enough.
    }

    static boolean areRectanglesEqual(Rectangle rec1, Rectangle rec2, double epsilon) {
        return (Math.abs(rec1.y - rec2.y) < epsilon)
                && (Math.abs(rec1.x - rec2.x) < epsilon)
                && (Math.abs(rec1.height - rec2.height) < epsilon)
                && (Math.abs(rec1.width - rec2.width) < epsilon);
    }

    static boolean areRectanglesEqual(Rectangle rec1, Rectangle rec2) {
        return areRectanglesEqual(rec1, rec2, 1);
    }

    static double reductionInto0to360(double angle) {
        angle %= 360.0;
        return (angle < 0.0) ? angle + 360.0 : angle;
    }

    static double lineLenght(Line line) {
        return Math.sqrt((line.getEndX() - line.getStartX()) * (line.getEndX() - line.getStartX()) + (line.getEndY() - line.getStartY()) * (line.getEndY() - line.getStartY()));
    }

    static Point getRectangleSideCenter(Rectangle rec, Side side) {
        switch (side) {
            case LEFT:
                return new Point(rec.x, rec.height / 2 + rec.y);
            case RIGHT:
                return new Point(rec.x + rec.width, rec.height / 2 + rec.y);
            case BOTTOM:
                return new Point(rec.x + rec.width / 2, rec.height);
            case TOP:
                return new Point(rec.x + rec.width / 2, rec.height + rec.y);
            default:
                return null;
        }
    }

    static double distance(Point p1, Point p2) {
        return lineLenght(new Line(p1.x, p1.y, p2.x, p2.y));
    }

    static double minDistance(Wrap relativeWrap, Line line, Wrap wrap) {
        Point p1 = new Point(line.getStartX(), line.getStartY());
        Point p2 = new Point(line.getEndX(), line.getEndY());
        Point p3 = getRectangleSideCenter(wrap.getScreenBounds(), Side.LEFT);
        p3.translate(-relativeWrap.getScreenBounds().x, -relativeWrap.getScreenBounds().y);
        Point p4 = getRectangleSideCenter(wrap.getScreenBounds(), Side.RIGHT);
        p4.translate(-relativeWrap.getScreenBounds().x, -relativeWrap.getScreenBounds().y);
        return Math.min(Math.min(distance(p1, p3), distance(p1, p4)), Math.min(distance(p2, p3), distance(p2, p4)));
    }

    static void printLine(Line line) {
        System.out.println(getLineDescription(line));
    }

    static String getLineDescription(Line line) {
        if (line == null) {
            return "null";
        } else {
            return "Line : startX " + line.getStartX() + " startY " + line.getStartY() + " endX " + line.getEndX() + " endY " + line.getEndY();
        }
    }
}