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
package test.css.scenegraph.functional;

import test.css.scenegraph.Shapes2App;
import test.css.scenegraph.CssEffects;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import static test.javaclient.shared.JemmyUtils.setJemmyComparatorByDistance;
import static test.css.scenegraph.Shapes2App.Pages.*;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author Andrey Nazarov
 */
public class ShapesCss2Test extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }

    /**
     * Common test workflow.
     * <p>Second, test verify output with golden screenshot.
     *
     * @param clazz
     */
    //@RunUI
    @BeforeClass
    public static void runUI() {
        Shapes2App.main(null);

    }

    /**
     * test CSS "-fx-fill" property on circle
     */
    @Test
    public void Circle_FILL() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on circle
     */
    @Test
    public void Circle_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on circle
     */
    @Test
    public void Circle_STROKE() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on circle
     */
    @Test
    public void Circle_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on circle
     */
    @Test
    public void Circle_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on circle
     */
    @Test
    public void Circle_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on circle
     */
    @Test
    public void Circle_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on circle
     */
    @Test
    public void Circle_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on circle
     */
    @Test
    public void Circle_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on circle
     */
    @Test
    public void Circle_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on circle
     */
    @Test
    public void Circle_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on circle
     */
    @Test
    public void Circle_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on circle
     */
    @Test
    public void Circle_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on circle
//     */
//    @Test
//    public void Circle_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Circle.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on circle
     */
    @Test
    public void Circle_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on circle
     */
    @Test
    public void Circle_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on circle
     */
    @Test
    public void Circle_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on circle
     */
    @Test
    public void Circle_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on circle
     */
    @Test
    public void Circle_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(Circle.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on rectangle
     */
    @Test
    public void Rectangle_FILL() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on rectangle
     */
    @Test
    public void Rectangle_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on rectangle
     */
    @Test
    public void Rectangle_STROKE() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on rectangle
     */
    @Test
    public void Rectangle_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on rectangle
     */
    @Test
    public void Rectangle_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on rectangle
     */
    @Test
    public void Rectangle_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on rectangle
     */
    @Test
    public void Rectangle_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on rectangle
     */
    @Test
    public void Rectangle_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: bevel " property on rectangle
     */
    @Test
    public void Rectangle_STROKE_JOIN_B() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_JOIN_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: miter" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_JOIN_M() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_JOIN_M.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: round" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_JOIN_R() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_JOIN_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-miter-limit:" property on rectangle
     */
    @Test
    public void Rectangle_MITER_LIMIT() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.MITER_LIMIT.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on rectangle
     */
    @Test
    public void Rectangle_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on rectangle
     */
    @Test
    public void Rectangle_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on rectangle
//     */
//    @Test
//    public void Rectangle_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Rectangle.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on rectangle
     */
    @Test
    public void Rectangle_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on rectangle
     */
    @Test
    public void Rectangle_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-arc-height" and "-fx-arc-width" properties on rectangle
     */
    @Test
    public void Rectangle_ARC_HEIGHT_WIDTH() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.ARC_HEIGHT_WIDTH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on rectangle
     */
    @Test
    public void Rectangle_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(Rectangle.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on CubicCurve
     */
    @Test
    public void CubicCurve_FILL() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on CubicCurve
     */
    @Test
    public void CubicCurve_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on CubicCurve
     */
    @Test
    public void CubicCurve_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on CubicCurve
     */
    @Test
    public void CubicCurve_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on CubicCurve
     */
    @Test
    public void CubicCurve_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on CubicCurve
     */
    @Test
    public void CubicCurve_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on CubicCurve
     */
    @Test
    public void CubicCurve_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on CubicCurve
     */
    @Test
    public void CubicCurve_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on CubicCurve
//     */
//    @Test
//    public void CubicCurve_SMOOTH() throws InterruptedException {
//        testAdditionalAction(CubicCurve.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on CubicCurve
     */
    @Test
    public void CubicCurve_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on CubicCurve
     */
    @Test
    public void CubicCurve_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(CubicCurve.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on QuadCurve
     */
    @Test
    public void QuadCurve_FILL() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on QuadCurve
     */
    @Test
    public void QuadCurve_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on QuadCurve
     */
    @Test
    public void QuadCurve_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on QuadCurve
     */
    @Test
    public void QuadCurve_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on QuadCurve
     */
    @Test
    public void QuadCurve_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on QuadCurve
     */
    @Test
    public void QuadCurve_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on QuadCurve
     */
    @Test
    public void QuadCurve_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on QuadCurve
     */
    @Test
    public void QuadCurve_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on QuadCurve
//     */
//    @Test
//    public void QuadCurve_SMOOTH() throws InterruptedException {
//        testAdditionalAction(QuadCurve.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on QuadCurve
     */
    @Test
    public void QuadCurve_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on QuadCurve
     */
    @Test
    public void QuadCurve_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(QuadCurve.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on Polygon
     */
    @Test
    public void Polygon_FILL() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on Polygon
     */
    @Test
    public void Polygon_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on Polygon
     */
    @Test
    public void Polygon_STROKE() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on Polygon
     */
    @Test
    public void Polygon_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on Polygon
     */
    @Test
    public void Polygon_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on Polygon
     */
    @Test
    public void Polygon_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on Polygon
     */
    @Test
    public void Polygon_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on Polygon
     */
    @Test
    public void Polygon_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on Polygon
     */
    @Test
    public void Polygon_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on Polygon
     */
    @Test
    public void Polygon_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on Polygon
     */
    @Test
    public void Polygon_STROKE_CAP_BUTT() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on Polygon
     */
    @Test
    public void Polygon_STROKE_CAP_ROUND() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on Polygon
     */
    @Test
    public void Polygon_STROKE_CAP_SQUARE() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on Polygon
     */
    @Test
    public void Polygon_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: bevel" property on Polygon
     */
    @Test
    public void Polygon_STROKE_JOIN_BEVEL() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_JOIN_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: miter" property on Polygon
     */
    @Test
    public void Polygon_STROKE_JOIN_MITER() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_JOIN_M.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: round" property on Polygon
     */
    @Test
    public void Polygon_STROKE_JOIN_ROUND() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.STROKE_JOIN_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-miter-limit:" property on Polygon
     */
    @Test
    public void Polygon_MITER_LIMIT() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.MITER_LIMIT.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on Polygon
     */
    @Test
    public void Polygon_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on Polygon
     */
    @Test
    public void Polygon_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on Polygon
//     */
//    @Test
//    public void Polygon_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Polygon.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on Polygon
     */
    @Test
    public void Polygon_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on Polygon
     */
    @Test
    public void Polygon_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Polygon.name(), CssEffects.INNER_SHADOW.name(), true);
    }

// disabling those till intersect is back
//    /**
//     * test CSS "-fx-fill" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_FILL() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.FILL.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear..." property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_LINEAR_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.LINEAR_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke:" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_STROKE() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.STROKE.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke: linear..." property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_STROKE_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.STROKE_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: transparent" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_TRANSPARENT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.TRANSPARENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: radial " property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_RADIAL_GRADIENT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.RADIAL_GRADIENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-dash-array: " property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_STROKE_DASH() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.STROKE_DASH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-cap: round" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_STROKE_CAP() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.STROKE_CAP.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-dash-offset:" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_DASH_OFFSET() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.DASH_OFFSET.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-join: " property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_STROKE_JOIN() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.STROKE_JOIN.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-miter-limit:" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_MITER_LIMIT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.MITER_LIMIT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
//     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
//     * 1,rgba(100%,50%,100%,0.7), true);
//     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_COLOR_RGBA_HSBA() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_COLOR_RGB() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.COLOR_RGB.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-smooth: false" property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_SMOOTH() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.SMOOTH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: dropshadow..." property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_DROP_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.DROP_SHADOW.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: innershadow..." property on ShapeIntersect
//     */
//    @Test
//    public void ShapeIntersect_INNER_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeIntersect.name(), CssEffects.INNER_SHADOW.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_FILL() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.FILL.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear..." property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_LINEAR_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.LINEAR_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke:" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_STROKE() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.STROKE.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke: linear..." property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_STROKE_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.STROKE_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: transparent" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_TRANSPARENT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.TRANSPARENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: radial " property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_RADIAL_GRADIENT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.RADIAL_GRADIENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-dash-array: " property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_STROKE_DASH() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.STROKE_DASH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-cap: round" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_STROKE_CAP() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.STROKE_CAP.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-dash-offset:" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_DASH_OFFSET() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.DASH_OFFSET.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-join: " property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_STROKE_JOIN() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.STROKE_JOIN.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-miter-limit:" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_MITER_LIMIT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.MITER_LIMIT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
//     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
//     * 1,rgba(100%,50%,100%,0.7), true);
//     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_COLOR_RGBA_HSBA() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_COLOR_RGB() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.COLOR_RGB.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-smooth: false" property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_SMOOTH() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.SMOOTH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: dropshadow..." property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_DROP_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.DROP_SHADOW.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: innershadow..." property on ShapeIntersectRound
//     */
//    @Test
//    public void ShapeIntersectRound_INNER_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectRound.name(), CssEffects.INNER_SHADOW.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_FILL() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.FILL.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear..." property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_LINEAR_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.LINEAR_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke:" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_STROKE() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.STROKE.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke: linear..." property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_STROKE_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.STROKE_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: transparent" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_TRANSPARENT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.TRANSPARENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: radial " property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_RADIAL_GRADIENT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.RADIAL_GRADIENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-dash-array: " property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_STROKE_DASH() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.STROKE_DASH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-cap: round" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_STROKE_CAP() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.STROKE_CAP.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-dash-offset:" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_DASH_OFFSET() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.DASH_OFFSET.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-join: " property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_STROKE_JOIN() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.STROKE_JOIN.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-miter-limit:" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_MITER_LIMIT() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.MITER_LIMIT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
//     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
//     * 1,rgba(100%,50%,100%,0.7), true);
//     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_COLOR_RGBA_HSBA() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_COLOR_RGB() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.COLOR_RGB.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-smooth: false" property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_SMOOTH() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.SMOOTH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: dropshadow..." property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_DROP_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.DROP_SHADOW.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: innershadow..." property on ShapeIntersectBevel
//     */
//    @Test
//    public void ShapeIntersectBevel_INNER_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeIntersectBevel.name(), CssEffects.INNER_SHADOW.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_FILL() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.FILL.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear..." property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_LINEAR_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.LINEAR_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke:" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_STROKE() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.STROKE.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke: linear..." property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_STROKE_GRAD() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.STROKE_GRAD.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: transparent" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_TRANSPARENT() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.TRANSPARENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: radial " property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_RADIAL_GRADIENT() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.RADIAL_GRADIENT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-dash-array: " property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_STROKE_DASH() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.STROKE_DASH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-cap: round" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_STROKE_CAP() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.STROKE_CAP.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-dash-offset:" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_DASH_OFFSET() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.DASH_OFFSET.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-line-join: " property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_STROKE_JOIN() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.STROKE_JOIN.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-stroke-miter-limit:" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_MITER_LIMIT() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.MITER_LIMIT.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
//     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
//     * 1,rgba(100%,50%,100%,0.7), true);
//     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_COLOR_RGBA_HSBA() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_COLOR_RGB() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.COLOR_RGB.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-smooth: false" property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_SMOOTH() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.SMOOTH.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: dropshadow..." property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_DROP_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.DROP_SHADOW.name(), true);
//    }
//
//    /**
//     * test CSS "-fx-effect: innershadow..." property on ShapeSubtract
//     */
//    @Test
//    public void ShapeSubtract_INNER_SHADOW() throws InterruptedException {
//        testAdditionalAction(ShapeSubtract.name(), CssEffects.INNER_SHADOW.name(), true);
//    }
//
    /**
     * test CSS "-fx-fill" property on Ellipse
     */
    @Test
    public void Ellipse_FILL() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on Ellipse
     */
    @Test
    public void Ellipse_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on Ellipse
     */
    @Test
    public void Ellipse_STROKE() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on Ellipse
     */
    @Test
    public void Ellipse_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on Ellipse
     */
    @Test
    public void Ellipse_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on Ellipse
     */
    @Test
    public void Ellipse_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on Ellipse
     */
    @Test
    public void Ellipse_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on Ellipse
     */
    @Test
    public void Ellipse_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on Ellipse
     */
    @Test
    public void Ellipse_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on Ellipse
//     */
//    @Test
//    public void Ellipse_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Ellipse.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on Ellipse
     */
    @Test
    public void Ellipse_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on Ellipse
     */
    @Test
    public void Ellipse_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Ellipse.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on Ellipse
     */
    @Test
    public void Arc_FILL() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on Arc
     */
    @Test
    public void Arc_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on Arc
     */
    @Test
    public void Arc_STROKE() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on Arc
     */
    @Test
    public void Arc_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on Arc
     */
    @Test
    public void Arc_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on Arc
     */
    @Test
    public void Arc_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on Arc
     */
    @Test
    public void Arc_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on Arc
     */
    @Test
    public void Arc_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on Arc
     */
    @Test
    public void Arc_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on Arc
     */
    @Test
    public void Arc_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on Arc
     */
    @Test
    public void Arc_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on Arc
     */
    @Test
    public void Arc_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on Arc
     */
    @Test
    public void Arc_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on Arc
     */
    @Test
    public void Arc_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on Arc
     */
    @Test
    public void Arc_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on Arc
     */
    @Test
    public void Arc_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on Arc
//     */
//    @Test
//    public void Arc_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Arc.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on Arc
     */
    @Test
    public void Arc_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on Arc
     */
    @Test
    public void Arc_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Arc.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on Arc
     */
    @Test
    public void Line_FILL() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on Line
     */
    @Test
    public void Line_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on Line
     */
    @Test
    public void Line_STROKE() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on Line
     */
    @Test
    public void Line_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on Line
     */
    @Test
    public void Line_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on Line
     */
    @Test
    public void Line_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on Line
     */
    @Test
    public void Line_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on Line
     */
    @Test
    public void Line_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on Line
     */
    @Test
    public void Line_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on Line
     */
    @Test
    public void Line_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on Line
     */
    @Test
    public void Line_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on Line
     */
    @Test
    public void Line_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on Line
     */
    @Test
    public void Line_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on Line
//     */
//    @Test
//    public void Line_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Line.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on Line
     */
    @Test
    public void Line_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on Line
     */
    @Test
    public void Line_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Line.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on PolylineLine
     */
    @Test
    public void Polyline_FILL() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on PolylineLine
     */
    @Test
    public void Polyline_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on PolylineLine
     */
    @Test
    public void Polyline_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on PolylineLine
     */
    @Test
    public void Polyline_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on PolylineLine
     */
    @Test
    public void Polyline_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: miter " property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_JOIN_M() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_JOIN_M.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: bevel" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_JOIN_B() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_JOIN_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: round" property on PolylineLine
     */
    @Test
    public void Polyline_STROKE_JOIN_R() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.STROKE_JOIN_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-miter-limit:" property on PolylineLine
     */
    @Test
    public void Polyline_MITER_LIMIT() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.MITER_LIMIT.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on PolylineLine
     */
    @Test
    public void Polyline_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on PolylineLine
     */
    @Test
    public void Polyline_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on PolylineLine
//     */
//    @Test
//    public void Polyline_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Polyline.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on PolylineLine
     */
    @Test
    public void Polyline_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on PolylineLine
     */
    @Test
    public void Polyline_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Polyline.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on PathLine
     */
    @Test
    public void Path_FILL() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on PathLine
     */
    @Test
    public void Path_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on PathLine
     */
    @Test
    public void Path_STROKE() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on PathLine
     */
    @Test
    public void Path_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on PathLine
     */
    @Test
    public void Path_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on PathLine
     */
    @Test
    public void Path_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on PathLine
     */
    @Test
    public void Path_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on PathLine
     */
    @Test
    public void Path_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on PathLine
     */
    @Test
    public void Path_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on PathLine
     */
    @Test
    public void Path_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on PathLine
     */
    @Test
    public void Path_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on PathLine
     */
    @Test
    public void Path_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on PathLine
     */
    @Test
    public void Path_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on PathLine
     */
    @Test
    public void Path_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: miter " property on PathLine
     */
    @Test
    public void Path_STROKE_JOIN_M() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_JOIN_M.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: bevel" property on PathLine
     */
    @Test
    public void Path_STROKE_JOIN_B() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_JOIN_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: round" property on PathLine
     */
    @Test
    public void Path_STROKE_JOIN_R() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.STROKE_JOIN_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-miter-limit:" property on PathLine
     */
    @Test
    public void Path_MITER_LIMIT() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.MITER_LIMIT.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on PathLine
     */
    @Test
    public void Path_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on PathLine
     */
    @Test
    public void Path_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on PathLine
//     */
//    @Test
//    public void Path_SMOOTH() throws InterruptedException {
//        testAdditionalAction(Path.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on PathLine
     */
    @Test
    public void Path_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on PathLine
     */
    @Test
    public void Path_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(Path.name(), CssEffects.INNER_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-fill" property on SVGPathLine
     */
    @Test
    public void SVGPath_FILL() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.FILL.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear..." property on SVGPathLine
     */
    @Test
    public void SVGPath_LINEAR_GRAD() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.LINEAR_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-stroke:" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE.name(), true);
    }

    /**
     * test CSS "-fx-stroke: linear..." property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_GRAD() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_GRAD.name(), true);
    }

    /**
     * test CSS "-fx-fill: transparent" property on SVGPathLine
     */
    @Test
    public void SVGPath_TRANSPARENT() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.TRANSPARENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: centered" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_TYPE_CENTERED() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_TYPE_CENTERED.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: inside" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_TYPE_INSIDE() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_TYPE_INSIDE.name(), true);
    }

    /**
     * test CSS "-fx-stroke-type: outside" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_TYPE_OUTSIDE() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_TYPE_OUTSIDE.name(), true);
    }

    /**
     * test CSS "-fx-fill: radial " property on SVGPathLine
     */
    @Test
    public void SVGPath_RADIAL_GRADIENT() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.RADIAL_GRADIENT.name(), true);
    }

    /**
     * test CSS "-fx-stroke-dash-array: " property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_DASH() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_DASH.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: round" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_CAP_R() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_CAP_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: butt" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_CAP_B() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_CAP_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-cap: square" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_CAP_S() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_CAP_S.name(), true);
    }

    /**
     * test CSS "-fx-dash-offset:" property on SVGPathLine
     */
    @Test
    public void SVGPath_DASH_OFFSET() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.DASH_OFFSET.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: miter " property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_JOIN_M() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_JOIN_M.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: bevel" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_JOIN_B() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_JOIN_B.name(), true);
    }

    /**
     * test CSS "-fx-stroke-line-join: round" property on SVGPathLine
     */
    @Test
    public void SVGPath_STROKE_JOIN_R() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.STROKE_JOIN_R.name(), true);
    }

    /**
     * test CSS "-fx-stroke-miter-limit:" property on SVGPathLine
     */
    @Test
    public void SVGPath_MITER_LIMIT() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.MITER_LIMIT.name(), true);
    }

    /**
     * test CSS "-fx-fill: linear (0%,0%) to (100%,100%)
     * stops (0,rgba(100%,0%,0%,1))(0.5,rgba(0,255,0,0.7))
     * 1,rgba(100%,50%,100%,0.7), true);
     * -fx-stroke: hsba( 300 , 10% , 50% , 0.8 );" property on SVGPathLine
     */
    @Test
    public void SVGPath_COLOR_RGBA_HSBA() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.COLOR_RGBA_HSBA.name(), true);
    }

    /**
     * test CSS "-fx-fill: rgb(100%,50%,10%);" property on SVGPathLine
     */
    @Test
    public void SVGPath_COLOR_RGB() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.COLOR_RGB.name(), true);
    }

//    /**
//     * test CSS "-fx-smooth: false" property on SVGPathLine
//     */
//    @Test
//    public void SVGPath_SMOOTH() throws InterruptedException {
//        testAdditionalAction(SVGPath.name(), CssEffects.SMOOTH.name(), true);
//    }

    /**
     * test CSS "-fx-effect: dropshadow..." property on SVGPathLine
     */
    @Test
    public void SVGPath_DROP_SHADOW() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.DROP_SHADOW.name(), true);
    }

    /**
     * test CSS "-fx-effect: innershadow..." property on SVGPathLine
     */
    @Test
    public void SVGPath_INNER_SHADOW() throws InterruptedException {
        testAdditionalAction(SVGPath.name(), CssEffects.INNER_SHADOW.name(), true);
    }


    @Override
    protected String getName() {
        return "Shapes2Css";
    }
}
