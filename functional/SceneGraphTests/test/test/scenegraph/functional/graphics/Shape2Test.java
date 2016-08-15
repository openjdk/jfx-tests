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
 */
package test.scenegraph.functional.graphics;

import org.junit.Test;
import org.junit.BeforeClass;
import test.scenegraph.app.Effects;

import static test.scenegraph.app.Shapes2App.Pages.*;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.Shapes2App;


/**
 * This test uses {@link test.scenegraph.app.ShapesApp} to verify drawing of
 * Shapes using <code>Strokes</code> and <code>Paints</code> modificators.
 *
 * Each test opens a page at app with specified shapes being drawn with
 * different settings ({@link test.scenegraph.app.Effects}). Test validates
 * presence of each Shape on Scene and compares results with screenshot.
 *
 * @see test.scenegraph.app.ShapesApp
 * @see test.scenegraph.app.Effects
 * @author Sergey Grinev
 */
public class Shape2Test extends TestBase {

    /**
     * Runs UI.
     */
    //@RunUI
    @BeforeClass
    public static void runUI() {
        Shapes2App.main(null);
    }

    public static void exitUI() {
    }

/**
 * test Circle
 * with setFill(Color.BISQUE)
 */
    @Test
    public void Circle_FILL() throws InterruptedException {
        testCommon(Circle.name(), Effects.FILL.name());
    }

    /**
 * test Circle
 *
 */
    @Test
    public void Circle_INTERSECT() throws InterruptedException {
        testCommon(Circle.name(), Effects.INTERSECT.name());
    }

       /**
 * test Circle
 *
 */
    @Test
    public void Circle_SUBTRACT() throws InterruptedException {
        testCommon(Circle.name(), Effects.SUBTRACT.name());
    }
       /**
 * test Circle
 *
 */
    @Test
    public void Circle_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(Circle.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test Circle
 *
 */
    @Test
    public void Circle_UNION() throws InterruptedException {
        testCommon(Circle.name(), Effects.UNION.name());
    }
       /**
 * test Circle
 *
 */
    @Test
    public void Circle_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(Circle.name(), Effects.UN_WT_TRANSF.name());
    }

        /**
 * test Arc
 *
 */
    @Test
    public void Arc_INTERSECT() throws InterruptedException {
        testCommon(Arc.name(), Effects.INTERSECT.name());
    }

       /**
 * test Arc
 *
 */
    @Test
    public void Arc_SUBTRACT() throws InterruptedException {
        testCommon(Arc.name(), Effects.SUBTRACT.name());
    }
       /**
 * test Arc
 *
 */
    @Test
    public void Arc_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(Arc.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test Arc
 *
 */
    @Test
    public void Arc_UNION() throws InterruptedException {
        testCommon(Arc.name(), Effects.UNION.name());
    }
       /**
 * test Arc
 */
    @Test
    public void Arc_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(Arc.name(), Effects.UN_WT_TRANSF.name());
    }

            /**
 * test CubicCurve
 *
 */
    @Test
    public void CubicCurve_INTERSECT() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.INTERSECT.name());
    }

       /**
 * test CubicCurve
 *
 */
    @Test
    public void CubicCurve_SUBTRACT() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.SUBTRACT.name());
    }
       /**
 * test CubicCurve
 *
 */
    @Test
    public void CubicCurve_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test CubicCurve
 *
 */
    @Test
    public void CubicCurve_UNION() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.UNION.name());
    }
       /**
 * test CubicCurve
 */
    @Test
    public void CubicCurve_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.UN_WT_TRANSF.name());
    }


            /**
 * test Ellipse
 *
 */
    @Test
    public void Ellipse_INTERSECT() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.INTERSECT.name());
    }

       /**
 * test Ellipse
 *
 */
    @Test
    public void Ellipse_SUBTRACT() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.SUBTRACT.name());
    }
       /**
 * test Ellipse
 *
 */
    @Test
    public void Ellipse_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test Ellipse
 *
 */
    @Test
    public void Ellipse_UNION() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.UNION.name());
    }
       /**
 * test Ellipse
 */
    @Test
    public void Ellipse_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.UN_WT_TRANSF.name());
    }

              /**
 * test Line
 *
 */
    @Test
    public void Line_INTERSECT() throws InterruptedException {
        testCommon(Line.name(), Effects.INTERSECT.name());
    }

       /**
 * test Line
 *
 */
    @Test
    public void Line_SUBTRACT() throws InterruptedException {
        testCommon(Line.name(), Effects.SUBTRACT.name());
    }
       /**
 * test Line
 *
 */
    @Test
    public void Line_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(Line.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test Line
 *
 */
    @Test
    public void Line_UNION() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.UNION.name());
    }
       /**
 * test Line
 */
    @Test
    public void Line_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.UN_WT_TRANSF.name());
    }

              /**
 * test Polygon
 *
 */
    @Test
    public void Polygon_INTERSECT() throws InterruptedException {
        testCommon(Polygon.name(), Effects.INTERSECT.name());
    }

       /**
 * test Polygon
 *
 */
    @Test
    public void Polygon_SUBTRACT() throws InterruptedException {
        testCommon(Polygon.name(), Effects.SUBTRACT.name());
    }
       /**
 * test Polygon
 *
 */
    @Test
    public void Polygon_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(Polygon.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test Polygon
 *
 */
    @Test
    public void Polygon_UNION() throws InterruptedException {
        testCommon(Polygon.name(), Effects.UNION.name());
    }
       /**
 * test Polygon
 */
    @Test
    public void Polygon_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(Polygon.name(), Effects.UN_WT_TRANSF.name());
    }

                  /**
 * test Polyline
 *
 */
    @Test
    public void Polyline_INTERSECT() throws InterruptedException {
        testCommon(Polyline.name(), Effects.INTERSECT.name());
    }

       /**
 * test Polyline
 *
 */
    @Test
    public void Polyline_SUBTRACT() throws InterruptedException {
        testCommon(Polyline.name(), Effects.SUBTRACT.name());
    }
       /**
 * test Polyline
 *
 */
    @Test
    public void Polyline_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(Polyline.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test Polyline
 *
 */
    @Test
    public void Polyline_UNION() throws InterruptedException {
        testCommon(Polyline.name(), Effects.UNION.name());
    }
       /**
 * test Polyline
 */
    @Test
    public void Polyline_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(Polyline.name(), Effects.UN_WT_TRANSF.name());
    }

                  /**
 * test QuadCurve
 *
 */
    @Test
    public void QuadCurve_INTERSECT() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.INTERSECT.name());
    }

       /**
 * test QuadCurve
 *
 */
    @Test
    public void QuadCurve_SUBTRACT() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.SUBTRACT.name());
    }
       /**
 * test QuadCurve
 *
 */
    @Test
    public void QuadCurve_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test QuadCurve
 *
 */
    @Test
    public void QuadCurve_UNION() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.UNION.name());
    }
       /**
 * test QuadCurve
 */
    @Test
    public void QuadCurve_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.UN_WT_TRANSF.name());
    }

                  /**
 * test Rectangle
 *
 */
    @Test
    public void Rectangle_INTERSECT() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.INTERSECT.name());
    }

       /**
 * test Rectangle
 *
 */
    @Test
    public void Rectangle_SUBTRACT() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.SUBTRACT.name());
    }
       /**
 * test Rectangle
 *
 */
    @Test
    public void Rectangle_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test Rectangle
 *
 */
    @Test
    public void Rectangle_UNION() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.UNION.name());
    }
       /**
 * test Rectangle
 */
    @Test
    public void Rectangle_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.UN_WT_TRANSF.name());
    }

                      /**
 * test ShapeIntersectBevel
 *
 */
    @Test
    public void ShapeIntersectBevel_INTERSECT() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.INTERSECT.name());
    }

       /**
 * test ShapeIntersectBevel
 *
 */
    @Test
    public void ShapeIntersectBevel_SUBTRACT() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.SUBTRACT.name());
    }
       /**
 * test ShapeIntersectBevel
 *
 */
    @Test
    public void ShapeIntersectBevel_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test ShapeIntersectBevel
 *
 */
    @Test
    public void ShapeIntersectBevel_UNION() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.UNION.name());
    }
       /**
 * test ShapeIntersectBevel
 */
    @Test
    public void ShapeIntersectBevel_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.UN_WT_TRANSF.name());
    }

                      /**
 * test ShapeIntersectRound
 *
 */
    @Test
    public void ShapeIntersectRound_INTERSECT() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.INTERSECT.name());
    }

       /**
 * test ShapeIntersectRound
 *
 */
    @Test
    public void ShapeIntersectRound_SUBTRACT() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.SUBTRACT.name());
    }
       /**
 * test ShapeIntersectRound
 *
 */
    @Test
    public void ShapeIntersectRound_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test ShapeIntersectRound
 *
 */
    @Test
    public void ShapeIntersectRound_UNION() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.UNION.name());
    }
       /**
 * test ShapeIntersectRound
 */
    @Test
    public void ShapeIntersectRound_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.UN_WT_TRANSF.name());
    }
                          /**
 * test ShapeIntersect
 *
 */
    @Test
    public void ShapeIntersect_INTERSECT() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.INTERSECT.name());
    }

       /**
 * test ShapeIntersect
 *
 */
    @Test
    public void ShapeIntersect_SUBTRACT() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.SUBTRACT.name());
    }
       /**
 * test ShapeIntersect
 *
 */
    @Test
    public void ShapeIntersect_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test ShapeIntersect
 *
 */
    @Test
    public void ShapeIntersect_UNION() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.UNION.name());
    }
       /**
 * test ShapeIntersect
 */
    @Test
    public void ShapeIntersect_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.UN_WT_TRANSF.name());
    }

                              /**
 * test ShapeSubtract
 *
 */
    @Test
    public void ShapeSubtract_INTERSECT() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.INTERSECT.name());
    }

       /**
 * test ShapeSubtract
 *
 */
    @Test
    public void ShapeSubtract_SUBTRACT() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.SUBTRACT.name());
    }
       /**
 * test ShapeSubtract
 *
 */
    @Test
    public void ShapeSubtract_SUBTRACT_WT_DASH() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.SUB_WT_DASH.name());
    }
       /**
 * test ShapeSubtract
 *
 */
    @Test
    public void ShapeSubtract_UNION() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.UNION.name());
    }
       /**
 * test ShapeSubtract
 */
    @Test
    public void ShapeSubtract_UNION_WT_TRANSFORM() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.UN_WT_TRANSF.name());
    }
    //==============================================================================
/**
 * test Circle
 * with setFill(new LinearGradient(...
 */
    @Test
    public void Circle_LINEAR_GRAD() throws InterruptedException {
        testCommon(Circle.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test Circle
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void Circle_STROKE() throws InterruptedException {
        testCommon(Circle.name(), Effects.STROKE.name());
    }

/**
 * test Circle
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void Circle_STROKE_GRAD() throws InterruptedException {
        testCommon(Circle.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test Circle
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void Circle_TRANSPARENT() throws InterruptedException {
        testCommon(Circle.name(), Effects.TRANSPARENT.name());
    }

/**
 * test Circle
 * with setFill(RadialGradient(....
 */
    @Test
    public void Circle_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Circle.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test Circle
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void Circle_STROKE_DASH() throws InterruptedException {
        testCommon(Circle.name(), Effects.STROKE_DASH.name());
    }

/**
 * test Circle
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void Circle_STROKE_CAP() throws InterruptedException {
        testCommon(Circle.name(), Effects.STROKE_CAP.name());
    }

/**
 * test Rectangle
 * with setFill(Color.BISQUE)
 */
    @Test
    public void Rectangle_FILL() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.FILL.name());
    }

/**
 * test Rectangle
 * with setFill(new LinearGradient(...
 */
    @Test
    public void Rectangle_LINEAR_GRAD() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test Rectangle
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void Rectangle_STROKE() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.STROKE.name());
    }

/**
 * test Rectangle
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void Rectangle_STROKE_GRAD() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test Rectangle
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void Rectangle_TRANSPARENT() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.TRANSPARENT.name());
    }

/**
 * test Rectangle
 * with setFill(RadialGradient(....
 */
    @Test
    public void Rectangle_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test Rectangle
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void Rectangle_STROKE_DASH() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.STROKE_DASH.name());
    }

/**
 * test Rectangle
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void Rectangle_STROKE_CAP() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.STROKE_CAP.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setFill(Color.BISQUE)
 */
    @Test
    public void CubicCurve_FILL() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.FILL.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setFill(new LinearGradient(...
 */
    @Test
    public void CubicCurve_LINEAR_GRAD() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void CubicCurve_STROKE() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.STROKE.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void CubicCurve_STROKE_GRAD() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void CubicCurve_TRANSPARENT() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.TRANSPARENT.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setFill(RadialGradient(....
 */
    @Test
    public void CubicCurve_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void CubicCurve_STROKE_DASH() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.STROKE_DASH.name());
    }

/**
 * test javafx.scene.shape.CubicCurve
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void CubicCurve_STROKE_CAP() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.STROKE_CAP.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setFill(Color.BISQUE)
 */
    @Test
    public void QuadCurve_FILL() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.FILL.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setFill(new LinearGradient(...
 */
    @Test
    public void QuadCurve_LINEAR_GRAD() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void QuadCurve_STROKE() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.STROKE.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void QuadCurve_STROKE_GRAD() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void QuadCurve_TRANSPARENT() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.TRANSPARENT.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setFill(RadialGradient(....
 */
    @Test
    public void QuadCurve_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void QuadCurve_STROKE_DASH() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.STROKE_DASH.name());
    }

/**
 * test javafx.scene.shape.QuadCurve
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void QuadCurve_STROKE_CAP() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.STROKE_CAP.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setFill(Color.BISQUE)
 */
    @Test
    public void Polygon_FILL() throws InterruptedException {
        testCommon(Polygon.name(), Effects.FILL.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setFill(new LinearGradient(...
 */
    @Test
    public void Polygon_LINEAR_GRAD() throws InterruptedException {
        testCommon(Polygon.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void Polygon_STROKE() throws InterruptedException {
        testCommon(Polygon.name(), Effects.STROKE.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void Polygon_STROKE_GRAD() throws InterruptedException {
        testCommon(Polygon.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void Polygon_TRANSPARENT() throws InterruptedException {
        testCommon(Polygon.name(), Effects.TRANSPARENT.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setFill(RadialGradient(....
 */
    @Test
    public void Polygon_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Polygon.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void Polygon_STROKE_DASH() throws InterruptedException {
        testCommon(Polygon.name(), Effects.STROKE_DASH.name());
    }

/**
 * test javafx.scene.shape.Polygon
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void Polygon_STROKE_CAP() throws InterruptedException {
        testCommon(Polygon.name(), Effects.STROKE_CAP.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setFill(Color.BISQUE)
 */
    @Test
    public void ShapeIntersect_FILL() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.FILL.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setFill(new LinearGradient(...
 */
    @Test
    public void ShapeIntersect_LINEAR_GRAD() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void ShapeIntersect_STROKE() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.STROKE.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void ShapeIntersect_STROKE_GRAD() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ShapeIntersect_TRANSPARENT() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.TRANSPARENT.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setFill(RadialGradient(....
 */
    @Test
    public void ShapeIntersect_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void ShapeIntersect_STROKE_DASH() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.STROKE_DASH.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.MITER)
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void ShapeIntersect_STROKE_CAP() throws InterruptedException {
        testCommon(ShapeIntersect.name(), Effects.STROKE_CAP.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setFill(Color.BISQUE)
 */
    @Test
    public void ShapeIntersectRound_FILL() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.FILL.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setFill(new LinearGradient(...
 */
    @Test
    public void ShapeIntersectRound_LINEAR_GRAD() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void ShapeIntersectRound_STROKE() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.STROKE.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void ShapeIntersectRound_STROKE_GRAD() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ShapeIntersectRound_TRANSPARENT() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.TRANSPARENT.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setFill(RadialGradient(....
 */
    @Test
    public void ShapeIntersectRound_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void ShapeIntersectRound_STROKE_DASH() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.STROKE_DASH.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.ROUND)
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void ShapeIntersectRound_STROKE_CAP() throws InterruptedException {
        testCommon(ShapeIntersectRound.name(), Effects.STROKE_CAP.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setFill(Color.BISQUE)
 */
    @Test
    public void ShapeIntersectBevel_FILL() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.FILL.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setFill(new LinearGradient(...
 */
    @Test
    public void ShapeIntersectBevel_LINEAR_GRAD() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void ShapeIntersectBevel_STROKE() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.STROKE.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void ShapeIntersectBevel_STROKE_GRAD() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ShapeIntersectBevel_TRANSPARENT() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.TRANSPARENT.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setFill(RadialGradient(....
 */
    @Test
    public void ShapeIntersectBevel_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void ShapeIntersectBevel_STROKE_DASH() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.STROKE_DASH.name());
    }

/**
 * test ShapeIntersect with setStrokeLineJoin(StrokeLineJoin.BEVEL)
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void ShapeIntersectBevel_STROKE_CAP() throws InterruptedException {
        testCommon(ShapeIntersectBevel.name(), Effects.STROKE_CAP.name());
    }


/**
 * test ShapeSubtract
 * with setFill(Color.BISQUE)
 */
    @Test
    public void ShapeSubtract_FILL() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.FILL.name());
    }

/**
 * test ShapeSubtract
 * with setFill(new LinearGradient(...
 */
    @Test
    public void ShapeSubtract_LINEAR_GRAD() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test ShapeSubtract
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void ShapeSubtract_STROKE() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.STROKE.name());
    }

/**
 * test ShapeSubtract
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void ShapeSubtract_STROKE_GRAD() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test ShapeSubtract
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ShapeSubtract_TRANSPARENT() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.TRANSPARENT.name());
    }

/**
 * test ShapeSubtract
 * with setFill(RadialGradient(....
 */
    @Test
    public void ShapeSubtract_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test ShapeSubtract
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void ShapeSubtract_STROKE_DASH() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.STROKE_DASH.name());
    }

/**
 * test ShapeSubtract
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void ShapeSubtract_STROKE_CAP() throws InterruptedException {
        testCommon(ShapeSubtract.name(), Effects.STROKE_CAP.name());
    }

/**
 * test Ellipse
 * with setFill(Color.BISQUE)
 */
    @Test
    public void Ellipse_FILL() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.FILL.name());
    }

/**
 * test Ellipse
 * with setFill(new LinearGradient(...
 */
    @Test
    public void Ellipse_LINEAR_GRAD() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test Ellipse
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void Ellipse_STROKE() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.STROKE.name());
    }

/**
 * test Ellipse
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void Ellipse_STROKE_GRAD() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test Ellipse
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void Ellipse_TRANSPARENT() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.TRANSPARENT.name());
    }

/**
 * test Ellipse
 * with setFill(RadialGradient(....
 */
    @Test
    public void Ellipse_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test Ellipse
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void Ellipse_STROKE_DASH() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.STROKE_DASH.name());
    }

/**
 * test Ellipse
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void Ellipse_STROKE_CAP() throws InterruptedException {
        testCommon(Ellipse.name(), Effects.STROKE_CAP.name());
    }



/**
 * test javafx.scene.shape.Arc
 * with setFill(Color.BISQUE)
 */
    @Test
    public void Arc_FILL() throws InterruptedException {
        testCommon(Arc.name(), Effects.FILL.name());
    }

/**
 * test javafx.scene.shape.Arc
 * with setFill(new LinearGradient(...
 */
    @Test
    public void Arc_LINEAR_GRAD() throws InterruptedException {
        testCommon(Arc.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test javafx.scene.shape.Arc
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void Arc_STROKE() throws InterruptedException {
        testCommon(Arc.name(), Effects.STROKE.name());
    }

/**
 * test javafx.scene.shape.Arc
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void Arc_STROKE_GRAD() throws InterruptedException {
        testCommon(Arc.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test javafx.scene.shape.Arc
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void Arc_TRANSPARENT() throws InterruptedException {
        testCommon(Arc.name(), Effects.TRANSPARENT.name());
    }

/**
 * test javafx.scene.shape.Arc
 * with setFill(RadialGradient(....
 */
    @Test
    public void Arc_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Arc.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test javafx.scene.shape.Arc
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void Arc_STROKE_DASH() throws InterruptedException {
        testCommon(Arc.name(), Effects.STROKE_DASH.name());
    }

/**
 * test javafx.scene.shape.Arc
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void Arc_STROKE_CAP() throws InterruptedException {
        testCommon(Arc.name(), Effects.STROKE_CAP.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setFill(Color.BISQUE)
 */
    @Test
    public void Line_FILL() throws InterruptedException {
        testCommon(Line.name(), Effects.FILL.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setFill(new LinearGradient(...
 */
    @Test
    public void Line_LINEAR_GRAD() throws InterruptedException {
        testCommon(Line.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void Line_STROKE() throws InterruptedException {
        testCommon(Line.name(), Effects.STROKE.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void Line_STROKE_GRAD() throws InterruptedException {
        testCommon(Line.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void Line_TRANSPARENT() throws InterruptedException {
        testCommon(Line.name(), Effects.TRANSPARENT.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setFill(RadialGradient(....
 */
    @Test
    public void Line_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Line.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void Line_STROKE_DASH() throws InterruptedException {
        testCommon(Line.name(), Effects.STROKE_DASH.name());
    }

/**
 * test javafx.scene.shape.Line
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void Line_STROKE_CAP() throws InterruptedException {
        testCommon(Line.name(), Effects.STROKE_CAP.name());
    }



/**
 * test javafx.scene.shape.Polyline
 * with setFill(Color.BISQUE)
 */
    @Test
    public void Polyline_FILL() throws InterruptedException {
        testCommon(Polyline.name(), Effects.FILL.name());
    }

/**
 * test javafx.scene.shape.Polyline
 * with setFill(new LinearGradient(...
 */
    @Test
    public void Polyline_LINEAR_GRAD() throws InterruptedException {
        testCommon(Polyline.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test javafx.scene.shape.Polyline
 * with setStroke(Color.GREEN),setFill(Color.YELLOW);
 */
    @Test
    public void Polyline_STROKE() throws InterruptedException {
        testCommon(Polyline.name(), Effects.STROKE.name());
    }

/**
 * test javafx.scene.shape.Polyline
 * with setStroke(new LinearGradient(...)/setStrokeWidth(8f)/setStrokeDashOffset(10f)/setFill(Color.LIGHTGRAY)
 */
    @Test
    public void Polyline_STROKE_GRAD() throws InterruptedException {
        testCommon(Polyline.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test javafx.scene.shape.Polyline
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void Polyline_TRANSPARENT() throws InterruptedException {
        testCommon(Polyline.name(), Effects.TRANSPARENT.name());
    }

/**
 * test javafx.scene.shape.Polyline
 * with setFill(RadialGradient(....
 */
    @Test
    public void Polyline_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Polyline.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test javafx.scene.shape.Polyline
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(4.)/getStrokeDashArray().add(1.)/
 * getStrokeDashArray().add(4.)/setFill(Color.LAVENDERBLUSH);
 */
    @Test
    public void Polyline_STROKE_DASH() throws InterruptedException {
        testCommon(Polyline.name(), Effects.STROKE_DASH.name());
    }

/**
 * test javafx.scene.shape.Polyline
 * with setStroke(Color.GREEN)/getStrokeDashArray().add(10.)/getStrokeDashArray().add(8.)/
 * setStrokeWidth(4)/setStrokeLineCap(StrokeLineCap.ROUND)/setFill(Color.WHITE)
 */
    @Test
    public void Polyline_STROKE_CAP() throws InterruptedException {
        testCommon(Polyline.name(), Effects.STROKE_CAP.name());
    }

    @Override
    public void testCommon(String toplevel_name, String innerlevel_name) {

     boolean commonTestPassed = true;
     setWaitImageDelay(300);
        try {
            testCommon(toplevel_name, innerlevel_name, true, false);
        } catch (org.jemmy.TimeoutExpiredException ex) {
            commonTestPassed = false;
        }
        testRenderSceneToImage (toplevel_name, innerlevel_name);
        if (!commonTestPassed) {
            throw new org.jemmy.TimeoutExpiredException("testCommon failed:" + toplevel_name + "-" + innerlevel_name);
        }
    }

    @Override
    protected String getName() {
        return "Shape2Test";
    }
}
