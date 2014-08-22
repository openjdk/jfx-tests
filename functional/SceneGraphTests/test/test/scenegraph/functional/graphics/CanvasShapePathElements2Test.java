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
import test.javaclient.shared.TestBase;
import test.scenegraph.app.Effects;
import test.scenegraph.app.CanvasShapePathElements2App;

import static test.scenegraph.app.ShapePathElementsApp.Pages.*;
/**
 *
 * @author shubov
 */
public class CanvasShapePathElements2Test extends TestBase {


    /**
     * Runs UI.
     */
    //@RunUI
    @BeforeClass
    public static void runUI() {
        CanvasShapePathElements2App.main(null);
    }

    public static void exitUI() {
    }

    // ----------------------------------  ArcOpenSQUARE
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.SQUARE)
 * with setFill(Color.BISQUE);
 */
    @Test
    public void ArcOpenSquareFILL() throws InterruptedException {
        testCommon(ArcOpenSQUARE.name(),Effects.FILL.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.SQUARE)
 * with setFill(new LinearGradient...
 */
    @Test
    public void ArcOpenSquareLINEAR_GRAD() throws InterruptedException {
        testCommon(ArcOpenSQUARE.name(),Effects.LINEAR_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.SQUARE)
 * with setStroke(Color.GREEN) setFill(Color.YELLOW)
 */
    @Test
    public void ArcOpenSquareSTROKE() throws InterruptedException {
        testCommon(ArcOpenSQUARE.name(),Effects.STROKE.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.SQUARE)
 * with setFill(new RadialGradient...
 */
    @Test
    public void ArcOpenSquareRADIAL_GRADIENT() throws InterruptedException {
        testCommon(ArcOpenSQUARE.name(),Effects.RADIAL_GRADIENT.name());
    }

/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.SQUARE)
 * with setStroke(new LinearGradient(....)/setStrokeWidth(8f)/setStrokeDashOffset(10f)
 */
    @Test
    public void ArcOpenSquareSTROKE_GRAD() throws InterruptedException {
        testCommon(ArcOpenSQUARE.name(),Effects.STROKE_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.SQUARE)
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ArcOpenSquareTRANSPARENT() throws InterruptedException {
        testCommon(ArcOpenSQUARE.name(),Effects.TRANSPARENT.name());
    }
    // ---------------------------------- ArcOpenROUND
    @Test
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.ROUND)
 * with setFill(Color.BISQUE);
 */
    public void ArcOpenROUNDFILL() throws InterruptedException {
        testCommon(ArcOpenROUND.name(),Effects.FILL.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.ROUND)
 * with setFill(new LinearGradient...
 */
    @Test
    public void ArcOpenROUNDLINEAR_GRAD() throws InterruptedException {
        testCommon(ArcOpenROUND.name(),Effects.LINEAR_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.ROUND)
 * with setFill(new RadialGradient...
 */
    @Test
    public void ArcOpenROUNDRADIAL_GRADIENT() throws InterruptedException {
        testCommon(ArcOpenROUND.name(),Effects.RADIAL_GRADIENT.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.ROUND)
 * with setStroke(Color.GREEN) setFill(Color.YELLOW)
 */
    @Test
    public void ArcOpenROUNDSTROKE() throws InterruptedException {
        testCommon(ArcOpenROUND.name(),Effects.STROKE.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.ROUND)
 * with setStroke(new LinearGradient(....)/setStrokeWidth(8f)/setStrokeDashOffset(10f)
 */
    @Test
    public void ArcOpenROUNDSTROKE_GRAD() throws InterruptedException {
        testCommon(ArcOpenROUND.name(),Effects.STROKE_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and setStrokeLineCap(StrokeLineCap.ROUND)
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ArcOpenROUNDTRANSPARENT() throws InterruptedException {
        testCommon(ArcOpenROUND.name(),Effects.TRANSPARENT.name());
    }

    // ---------------------------------- ArcOpenBT

/**
 * test Arc with setType(ArcType.OPEN) and arc.setStrokeLineCap(StrokeLineCap.BUTT);
 * with setFill(Color.BISQUE);
 */
    @Test
    public void ArcOpenBTFILL() throws InterruptedException {
        testCommon(ArcOpenBT.name(),Effects.FILL.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and arc.setStrokeLineCap(StrokeLineCap.BUTT);
 * with setFill(new LinearGradient...
 */
    @Test
    public void ArcOpenBTLINEAR_GRAD() throws InterruptedException {
        testCommon(ArcOpenBT.name(),Effects.LINEAR_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and arc.setStrokeLineCap(StrokeLineCap.BUTT);
 * with setFill(new RadialGradient...
 */
    @Test
    public void ArcOpenBTRADIAL_GRADIENT() throws InterruptedException {
        testCommon(ArcOpenBT.name(),Effects.RADIAL_GRADIENT.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and arc.setStrokeLineCap(StrokeLineCap.BUTT);
 * with setStroke(Color.GREEN) setFill(Color.YELLOW)
 */
    @Test
    public void ArcOpenBTSTROKE() throws InterruptedException {
        testCommon(ArcOpenBT.name(),Effects.STROKE.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and arc.setStrokeLineCap(StrokeLineCap.BUTT);
 * with setStroke(new LinearGradient(....)/setStrokeWidth(8f)/setStrokeDashOffset(10f)
 */
    @Test
    public void ArcOpenBTSTROKE_GRAD() throws InterruptedException {
        testCommon(ArcOpenBT.name(),Effects.STROKE_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.OPEN) and arc.setStrokeLineCap(StrokeLineCap.BUTT);
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ArcOpenBTTRANSPARENT() throws InterruptedException {
        testCommon(ArcOpenBT.name(),Effects.TRANSPARENT.name());
    }

    // ---------------------------------- ArcChord
/**
 * test Arc with setType(ArcType.CHORD);
 * with setFill(Color.BISQUE);
 */
    @Test
    public void ArcChordFILL() throws InterruptedException {
        testCommon(ArcChord.name(),Effects.FILL.name());
    }
/**
 * test Arc with setType(ArcType.CHORD);
 * with setFill(new LinearGradient...
 */
    @Test
    public void ArcChordLINEAR_GRAD() throws InterruptedException {
        testCommon(ArcChord.name(),Effects.LINEAR_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.CHORD);
 * with setFill(new RadialGradient...
 */
    @Test
    public void ArcChordRADIAL_GRADIENT() throws InterruptedException {
        testCommon(ArcChord.name(),Effects.RADIAL_GRADIENT.name());
    }
/**
 * test Arc with setType(ArcType.CHORD);
 * with setStroke(Color.GREEN) setFill(Color.YELLOW)
 */
    @Test
    public void ArcChordSTROKE() throws InterruptedException {
        testCommon(ArcChord.name(),Effects.STROKE.name());
    }
/**
 * test Arc with setType(ArcType.CHORD);
 * with setStroke(new LinearGradient(....)/setStrokeWidth(8f)/setStrokeDashOffset(10f)
 */
    @Test
    public void ArcChordSTROKE_GRAD() throws InterruptedException {
        testCommon(ArcChord.name(),Effects.STROKE_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.CHORD);
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ArcChordTRANSPARENT() throws InterruptedException {
        testCommon(ArcChord.name(),Effects.TRANSPARENT.name());
    }

    // ---------------------------------- ArcRound
/**
 * test Arc with setType(ArcType.ROUND);
 * with setFill(Color.BISQUE);
 */
    @Test
    public void ArcRoundFILL() throws InterruptedException {
        testCommon(ArcRound.name(),Effects.FILL.name());
    }
/**
 * test Arc with setType(ArcType.ROUND);
 * with setFill(new LinearGradient...
 */
    @Test
    public void ArcRoundLINEAR_GRAD() throws InterruptedException {
        testCommon(ArcRound.name(),Effects.LINEAR_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.ROUND);
 * with setFill(new RadialGradient...
 */
    @Test
    public void ArcRoundRADIAL_GRADIENT() throws InterruptedException {
        testCommon(ArcRound.name(),Effects.RADIAL_GRADIENT.name());
    }
/**
 * test Arc with setType(ArcType.ROUND);
 * with setStroke(Color.GREEN) setFill(Color.YELLOW)
 */
    @Test
    public void ArcRoundSTROKE() throws InterruptedException {
        testCommon(ArcRound.name(),Effects.STROKE.name());
    }
/**
 * test Arc with setType(ArcType.ROUND);
 * with setStroke(new LinearGradient(....)/setStrokeWidth(8f)/setStrokeDashOffset(10f)
 */
    @Test
    public void ArcRoundSTROKE_GRAD() throws InterruptedException {
        testCommon(ArcRound.name(),Effects.STROKE_GRAD.name());
    }
/**
 * test Arc with setType(ArcType.ROUND);
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void ArcRoundTRANSPARENT() throws InterruptedException {
        testCommon(ArcRound.name(),Effects.TRANSPARENT.name());
    }


    // ---------------------------------- PathROUNDNonZeroFillRuleFILL
/**
 * test Path with childs created using MoveTo,LineTo,HLineTo,VLineTo,QuadCurveTo,
 * ArcTo, CubicCurveTo, ClosePath with setStrokeLineCap(StrokeLineCap.ROUND)
 * and setFillRule(FillRule.NON_ZERO) and
 * with setFill(Color.BISQUE);
 */
    @Test
    public void PathROUNDNonZeroFillRuleFILL() throws InterruptedException {
        testCommon(PathROUNDNonZeroFillRule.name(),Effects.FILL.name());
    }
/**
 * test Path with childs created using MoveTo,LineTo,HLineTo,VLineTo,QuadCurveTo,
 * ArcTo, CubicCurveTo, ClosePath with setStrokeLineCap(StrokeLineCap.ROUND)
 * and setFillRule(FillRule.NON_ZERO) and
 * with setFill(new LinearGradient...
 */
    @Test
    public void PathROUNDNonZeroFillRuleLINEAR_GRAD() throws InterruptedException {
        testCommon(PathROUNDNonZeroFillRule.name(),Effects.LINEAR_GRAD.name());
    }
/**
 * test Path with childs created using MoveTo,LineTo,HLineTo,VLineTo,QuadCurveTo,
 * ArcTo, CubicCurveTo, ClosePath with setStrokeLineCap(StrokeLineCap.ROUND)
 * and setFillRule(FillRule.NON_ZERO) and
 * with setFill(new RadialGradient...
 */
    @Test
    public void PathROUNDNonZeroFillRuleRADIAL_GRADIENT() throws InterruptedException {
        testCommon(PathROUNDNonZeroFillRule.name(),Effects.RADIAL_GRADIENT.name());
    }
/**
 * test Path with childs created using MoveTo,LineTo,HLineTo,VLineTo,QuadCurveTo,
 * ArcTo, CubicCurveTo, ClosePath with setStrokeLineCap(StrokeLineCap.ROUND)
 * and setFillRule(FillRule.NON_ZERO) and
 * with setStroke(Color.GREEN) setFill(Color.YELLOW)
 */
    @Test
    public void PathROUNDNonZeroFillRuleSTROKE() throws InterruptedException {
        testCommon(PathROUNDNonZeroFillRule.name(),Effects.STROKE.name());
    }
/**
 * test Path with childs created using MoveTo,LineTo,HLineTo,VLineTo,QuadCurveTo,
 * ArcTo, CubicCurveTo, ClosePath with setStrokeLineCap(StrokeLineCap.ROUND)
 * and setFillRule(FillRule.NON_ZERO) and
 * with setStroke(new LinearGradient(....)/setStrokeWidth(8f)/setStrokeDashOffset(10f)
 */
    @Test
    public void PathROUNDNonZeroFillRuleSTROKE_GRAD() throws InterruptedException {
        testCommon(PathROUNDNonZeroFillRule.name(),Effects.STROKE_GRAD.name());
    }
/**
 * test Path with childs created using MoveTo,LineTo,HLineTo,VLineTo,QuadCurveTo,
 * ArcTo, CubicCurveTo, ClosePath with setStrokeLineCap(StrokeLineCap.ROUND)
 * and setFillRule(FillRule.NON_ZERO) and
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void PathROUNDNonZeroFillRuleTRANSPARENT() throws InterruptedException {
        testCommon(PathROUNDNonZeroFillRule.name(),Effects.TRANSPARENT.name());
    }

    // ---------------------------------- PathSVG
/**
 * test  SVGPath() with setContent("some predefined content")
 * with setFill(Color.BISQUE);
 */
    @Test
    public void PathSVGFILL() throws InterruptedException {
        testCommon(SVG.name(),Effects.FILL.name());
    }
/**
 * test  SVGPath() with setContent("some predefined content")
 * with setFill(new LinearGradient...
 */
    @Test
    public void PathSVGLINEAR_GRAD() throws InterruptedException {
        testCommon(SVG.name(),Effects.LINEAR_GRAD.name());
    }
/**
 * test  SVGPath() with setContent("some predefined content")
 * with setFill(new RadialGradient...
 */
    @Test
    public void PathSVGRADIAL_GRADIENT() throws InterruptedException {
        testCommon(SVG.name(),Effects.RADIAL_GRADIENT.name());
    }
/**
 * test  SVGPath() with setContent("some predefined content")
 * with setStroke(Color.GREEN) setFill(Color.YELLOW)
 */
    @Test
    public void PathSVGSTROKE() throws InterruptedException {
        testCommon(SVG.name(),Effects.STROKE.name());
    }
/**
 * test  SVGPath() with setContent("some predefined content")
 * with setStroke(new LinearGradient(....)/setStrokeWidth(8f)/setStrokeDashOffset(10f)
 */
    @Test
    public void PathSVGSTROKE_GRAD() throws InterruptedException {
        testCommon(SVG.name(),Effects.STROKE_GRAD.name());
    }
/**
 * test  SVGPath() with setContent("some predefined content")
 * with setFill(Color.TRANSPARENT)
 */
    @Test
    public void PathSVGTRANSPARENT() throws InterruptedException {
        testCommon(SVG.name(),Effects.TRANSPARENT.name());
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
        return "CanvasShapePathElements2Test";
    }

}
