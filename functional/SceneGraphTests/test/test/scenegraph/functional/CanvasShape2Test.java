/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import org.junit.Test;
import org.junit.BeforeClass;
import test.scenegraph.app.Effects;

import static test.scenegraph.app.CanvasShapes2App.Pages.*;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.CanvasShapes2App;


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
public class CanvasShape2Test extends TestBase {

    /**
     * Runs UI.
     */
    //@RunUI
    @BeforeClass
    public static void runUI() {
        CanvasShapes2App.main(null);
    }

    public static void exitUI() {
    }


/**
 * test fillOval
 */
    @Test
    public void FillOval_FILL() throws InterruptedException {
        testCommon(FillOval.name(), Effects.FILL.name());
    }

/**
 * test fillOval
 */
    @Test
    public void FillOval_LinearGrad() throws InterruptedException {
        testCommon(FillOval.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test fillOval
 */
    @Test
    public void FillOval_Stroke() throws InterruptedException {
        testCommon(FillOval.name(), Effects.STROKE.name());
    }

/**
 * test fillOval
 */
    @Test
    public void FillOval_Stroke_GRAD() throws InterruptedException {
        testCommon(FillOval.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test fillOval
 */
    @Test
    public void FillOval_TRANSPARENT() throws InterruptedException {
        testCommon(FillOval.name(), Effects.TRANSPARENT.name());
    }
    
/**
 * test fillOval
 */
    @Test
    public void FillOval_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(FillOval.name(), Effects.RADIAL_GRADIENT.name());
    }
    
 /**
 * test strokeOval
 * 
 */
    @Test
    public void StrokeOval_STROKE_GRAD() throws InterruptedException {
        testCommon(StrokeOval.name(), Effects.STROKE_GRAD.name());
    }
    
 /**
 * test strokeOval
 * 
 */
    @Test
    public void StrokeOval_TRANSPARENT() throws InterruptedException {
        testCommon(StrokeOval.name(), Effects.TRANSPARENT.name());
    }
    
/**
 * test CubicCurve
 */
    @Test
    public void CubicCurve_FILL() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.FILL.name());
    }
    
/**
 * test CubicCurve
 */
    @Test
    public void CubicCurve_LINEAR_GRAD() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test CubicCurve
 */
    @Test
    public void CubicCurve_STROKE() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.STROKE.name());
    }
    
/**
 * test CubicCurve
 */
    @Test
    public void CubicCurve_STROKE_GRAD() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.STROKE_GRAD.name());
    }
    
/**
 * test CubicCurve
 */
    @Test
    public void CubicCurve_TRANSPARENT() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.TRANSPARENT.name());
    }
    
/**
 * test CubicCurve
 */
    @Test
    public void CubicCurve_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(CubicCurve.name(), Effects.RADIAL_GRADIENT.name());
    }
    
/**
 * test QuadCurve
 */
    @Test
    public void QuadCurve_FILL() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.FILL.name());
    }
    
/**
 * test QuadCurve
 */
    @Test
    public void QuadCurve_LINEAR_GRAD() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test QuadCurve
 */
    @Test
    public void QuadCurve_STROKE() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.STROKE.name());
    }
    
/**
 * test QuadCurve
 */
    @Test
    public void QuadCurve_STROKE_GRAD() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.STROKE_GRAD.name());
    }
    
/**
 * test QuadCurve
 */
    @Test
    public void QuadCurve_TRANSPARENT() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.TRANSPARENT.name());
    }
    
/**
 * test QuadCurve
 */
    @Test
    public void QuadCurve_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(QuadCurve.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test Polygon
 */
    @Test
    public void Polygon_FILL() throws InterruptedException {
        testCommon(Polygon.name(), Effects.FILL.name());
    }

/**
 * test Polygon
 */
    @Test
    public void Polygon_LINEAR_GRAD() throws InterruptedException {
        testCommon(Polygon.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test Polygon
 */
    @Test
    public void Polygon_STROKE() throws InterruptedException {
        testCommon(Polygon.name(), Effects.STROKE.name());
    }

/**
 * test Polygon
 */
    @Test
    public void Polygon_STROKE_GRAD() throws InterruptedException {
        testCommon(Polygon.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test Polygon
 */
    @Test
    public void Polygon_TRANSPARENT() throws InterruptedException {
        testCommon(Polygon.name(), Effects.TRANSPARENT.name());
    }

/**
 * test Polygon
 */
    @Test
    public void Polygon_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Polygon.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test Rectangle
 */
    @Test
    public void Rectangle_FILL() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.FILL.name());
    }

/**
 * test Rectangle
 */
    @Test
    public void Rectangle_LINEAR_GRAD() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test Rectangle
 */
    @Test
    public void Rectangle_STROKE() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.STROKE.name());
    }

/**
 * test Rectangle
 */
    @Test
    public void Rectangle_STROKE_GRAD() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test Rectangle
 */
    @Test
    public void Rectangle_TRANSPARENT() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.TRANSPARENT.name());
    }

/**
 * test Rectangle
 */
    @Test
    public void Rectangle_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Rectangle.name(), Effects.RADIAL_GRADIENT.name());
    }

/**
 * test Text
 */
    @Test
    public void Text_FILL() throws InterruptedException {
        testCommon(Text.name(), Effects.FILL.name());
    }

/**
 * test Text
 */
    @Test
    public void Text_LINEAR_GRAD() throws InterruptedException {
        testCommon(Text.name(), Effects.LINEAR_GRAD.name());
    }

/**
 * test Text
 */
    @Test
    public void Text_STROKE() throws InterruptedException {
        testCommon(Text.name(), Effects.STROKE.name());
    }

/**
 * test Text
 */
    @Test
    public void Text_STROKE_GRAD() throws InterruptedException {
        testCommon(Text.name(), Effects.STROKE_GRAD.name());
    }

/**
 * test Text
 */
    @Test
    public void Text_TRANSPARENT() throws InterruptedException {
        testCommon(Text.name(), Effects.TRANSPARENT.name());
    }

/**
 * test Text
 */
    @Test
    public void Text_RADIAL_GRADIENT() throws InterruptedException {
        testCommon(Text.name(), Effects.RADIAL_GRADIENT.name());
    }


/**
 * test Line
 * 
 */
    @Test
    public void Line_STROKE() throws InterruptedException {
        testCommon(Line.name(), Effects.STROKE.name());
    }
    
/**
 * test Line
 *
 */
    @Test
    public void Line_STROKE_GRAD() throws InterruptedException {
        testCommon(Line.name(), Effects.STROKE_GRAD.name());
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
        return "CanvasShape2Test";
    }
}
