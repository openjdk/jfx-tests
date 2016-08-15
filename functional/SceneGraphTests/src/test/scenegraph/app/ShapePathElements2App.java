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
package test.scenegraph.app;

/**
 *
 * @author shubov
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.VLineTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.SVGPath;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 * UI application for Shapes test from scenegraph tests suite.
 * See <a href="https://sunspace.sfbay.sun.com/display/JAVASE/Library-SG">spec</a> for details.
 *
 * here is tests for:
 * 1. classes derived from javafx.scene.shape.PathElement:
 * ArcTo, CubicCurveTo, HLineTo, LineTo, MoveTo, QuadCurveTo, VLineTo, ClosePath.
 * 2. StrokeLineCap enum (controls way unclosed subpaths drawn)
 * 3. ArcType enum (defines the closure type for the arc)
 * 4. FillRule enum (controls path fill rule)
 *
 * @author Victor Shubov
 */
public class ShapePathElements2App extends BasicButtonChooserApp {

    public ShapePathElements2App() {
        super(600, 410, "ShapePathElements", false);
    }

    public static void main(String args[]) {
        Utils.launch(ShapePathElements2App.class, args);
    }

    public enum Pages {
        ArcOpenSQUARE, ArcOpenROUND, ArcOpenBT, ArcChord, ArcRound, PathBT,
        PathSQUARE, PathROUNDNonZeroFillRule, SVG
    }

    final double DEFAULT_ARC_X = 50;
    final double DEFAULT_ARC_Y = DEFAULT_ARC_X;
    final double DEFAULT_ARC_RADIUS_X = 25;
    final double DEFAULT_ARC_RADIUS_Y = DEFAULT_ARC_RADIUS_X;
    final double DEFAULT_ARC_START_ANGLE = 45;
    final double DEFAULT_ARC_LENGTH = 270;

    private class ShapeTestNode extends TestNode {
        public ShapeTestNode(String name) {
            super(name);
        }
    }
    /**
     * Create Arc
     * - with StrokeLineCap type StrokeLineCap.SQUARE
     * - using constructor with arguments
     * - with ArcType.OPEN
     */
    private class arcShapeTestNodeStrokeLineCapSQUARE extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodeStrokeLineCapSQUARE(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            Arc arc = new Arc(DEFAULT_ARC_X, DEFAULT_ARC_Y, DEFAULT_ARC_RADIUS_X,
                    DEFAULT_ARC_RADIUS_X, DEFAULT_ARC_START_ANGLE, DEFAULT_ARC_LENGTH);
            arc.setType(ArcType.OPEN);
            arc.setStrokeLineCap(StrokeLineCap.SQUARE);
            Pane slot = new Pane();
            effect.setEffect(arc, slot);
            return slot;
        }
    }
    private class arcShapeTestNodeStrokeLineCapROUND extends ShapeTestNode {
        private Effects effect;
        public arcShapeTestNodeStrokeLineCapROUND(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }
        @Override
        public Node drawNode() {
            //VBox vbox = baseFill(new VBox());
            Arc arc = new Arc(DEFAULT_ARC_X, DEFAULT_ARC_Y, DEFAULT_ARC_RADIUS_X,
                    DEFAULT_ARC_RADIUS_X, DEFAULT_ARC_START_ANGLE, DEFAULT_ARC_LENGTH);
            arc.setType(ArcType.OPEN);
            arc.setStrokeLineCap(StrokeLineCap.ROUND);
            Pane slot = new Pane();
            effect.setEffect(arc, slot);
            return slot;
        }
    }
    private class arcShapeTestNodeStrokeLineCapBUTT extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodeStrokeLineCapBUTT(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            Arc arc = new Arc();
            arc.setCenterX(DEFAULT_ARC_X);
            arc.setCenterY(DEFAULT_ARC_Y);
            arc.setRadiusX(DEFAULT_ARC_RADIUS_X);
            arc.setRadiusY(DEFAULT_ARC_RADIUS_Y);
            arc.setStartAngle(DEFAULT_ARC_START_ANGLE);
            arc.setLength(DEFAULT_ARC_LENGTH);
            arc.setType(ArcType.OPEN);
            arc.setStrokeLineCap(StrokeLineCap.BUTT);

            Pane slot = new Pane();
            effect.setEffect(arc, slot);
            return slot;
        }
    }
    private class arcShapeTestNodeCHORD extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodeCHORD(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            Arc arc = new Arc();
            arc.setCenterX(DEFAULT_ARC_X);
            arc.setCenterY(DEFAULT_ARC_Y);
            arc.setRadiusX(DEFAULT_ARC_RADIUS_X);
            arc.setRadiusY(DEFAULT_ARC_RADIUS_Y);
            arc.setStartAngle(DEFAULT_ARC_START_ANGLE);
            arc.setLength(DEFAULT_ARC_LENGTH);
            arc.setType(ArcType.CHORD);

            Pane slot = new Pane();
            effect.setEffect(arc, slot);
            return slot;
        }
    }
    private class arcShapeTestNodeROUND extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodeROUND(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            Arc arc = new Arc(DEFAULT_ARC_X, DEFAULT_ARC_Y, DEFAULT_ARC_RADIUS_X,
                    DEFAULT_ARC_RADIUS_X, DEFAULT_ARC_START_ANGLE, DEFAULT_ARC_LENGTH);
            arc.setType(ArcType.ROUND);

            Pane slot = new Pane();
            effect.setEffect(arc, slot);
            return slot;
        }
    }
    private class arcShapeTestNodePathBT extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodePathBT(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            Shape ss = drawPath(140.0,140.0,0);
            ss.setStrokeLineCap(StrokeLineCap.BUTT);

            Pane slot = new Pane();
            effect.setEffect(ss, slot);
            return slot;
        }
    }
    private class arcShapeTestNodePathSQUARE extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodePathSQUARE(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            Shape ss = drawPath(140.0,140.0,0);
            ss.setStrokeLineCap(StrokeLineCap.SQUARE);

            Pane slot = new Pane();
            effect.setEffect(ss, slot);
            return slot;
        }
    }

    private class arcShapeTestNodePathROUNDNonZeroFillRule extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodePathROUNDNonZeroFillRule(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            Shape ss = drawPath(140.0, 140.0, 1);
            ss.setStrokeLineCap(StrokeLineCap.ROUND);

            Pane slot = new Pane();
            effect.setEffect(ss, slot);
            return slot;
        }
    }
    private class arcShapeTestNodeSVG extends ShapeTestNode {

        private Effects effect;

        public arcShapeTestNodeSVG(Effects _effect) {
            super(_effect.name());
            effect = _effect;
        }

        @Override
        public Node drawNode() {
            SVGPath svg = new SVGPath();
            svg.setContent("M40,60 C42,148 144,30 25,32");
            Pane slot = new Pane();
            effect.setEffect(svg, slot);
            return slot;
        }
    }


    @Override
    public TestNode setup() {
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = 800;
        final int widthPageContentPane = 610;

        final PageWithSlots arcPage = new PageWithSlots(Pages.ArcOpenSQUARE.name(), heightPageContentPane, widthPageContentPane);
        arcPage.setSlotSize(90, 90);
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.FILL));
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.LINEAR_GRAD));
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.STROKE));
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.STROKE_GRAD));
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.TRANSPARENT));
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.RADIAL_GRADIENT));
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.STROKE_DASH));
        arcPage.add(new arcShapeTestNodeStrokeLineCapSQUARE(Effects.STROKE_CAP));
        rootTestNode.add(arcPage);

        final PageWithSlots arcPage2 = new PageWithSlots(Pages.ArcOpenROUND.name(), heightPageContentPane, widthPageContentPane);
        arcPage2.setSlotSize(90, 90);
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.FILL));
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.LINEAR_GRAD));
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.STROKE));
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.STROKE_GRAD));
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.TRANSPARENT));
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.RADIAL_GRADIENT));
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.STROKE_DASH));
        arcPage2.add(new arcShapeTestNodeStrokeLineCapROUND(Effects.STROKE_CAP));
        rootTestNode.add(arcPage2);

        final PageWithSlots arcPage3 = new PageWithSlots(Pages.ArcOpenBT.name(), heightPageContentPane, widthPageContentPane);
        arcPage3.setSlotSize(90, 90);
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.FILL));
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.LINEAR_GRAD));
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.STROKE));
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.STROKE_GRAD));
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.TRANSPARENT));
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.RADIAL_GRADIENT));
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.STROKE_DASH));
        arcPage3.add(new arcShapeTestNodeStrokeLineCapBUTT(Effects.STROKE_CAP));
        rootTestNode.add(arcPage3);

        final PageWithSlots arcPage4 = new PageWithSlots(Pages.ArcChord.name(), heightPageContentPane, widthPageContentPane);
        arcPage4.setSlotSize(90, 90);
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.FILL));
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.LINEAR_GRAD));
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.STROKE));
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.STROKE_GRAD));
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.TRANSPARENT));
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.RADIAL_GRADIENT));
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.STROKE_DASH));
        arcPage4.add(new arcShapeTestNodeCHORD(Effects.STROKE_CAP));
        rootTestNode.add(arcPage4);

        final PageWithSlots arcPage5 = new PageWithSlots(Pages.ArcRound.name(), heightPageContentPane, widthPageContentPane);
        arcPage5.setSlotSize(90, 90);
        arcPage5.add(new arcShapeTestNodeROUND(Effects.FILL));
        arcPage5.add(new arcShapeTestNodeROUND(Effects.LINEAR_GRAD));
        arcPage5.add(new arcShapeTestNodeROUND(Effects.STROKE));
        arcPage5.add(new arcShapeTestNodeROUND(Effects.STROKE_GRAD));
        arcPage5.add(new arcShapeTestNodeROUND(Effects.TRANSPARENT));
        arcPage5.add(new arcShapeTestNodeROUND(Effects.RADIAL_GRADIENT));
        arcPage5.add(new arcShapeTestNodeROUND(Effects.STROKE_DASH));
        arcPage5.add(new arcShapeTestNodeROUND(Effects.STROKE_CAP));
        rootTestNode.add(arcPage5);

        final PageWithSlots arcPage6 = new PageWithSlots(Pages.PathBT.name(), heightPageContentPane, widthPageContentPane);
        arcPage6.setSlotSize(149, 149);
        arcPage6.add(new arcShapeTestNodePathBT(Effects.FILL));
        arcPage6.add(new arcShapeTestNodePathBT(Effects.LINEAR_GRAD));
        arcPage6.add(new arcShapeTestNodePathBT(Effects.STROKE));
        arcPage6.add(new arcShapeTestNodePathBT(Effects.STROKE_GRAD));
        arcPage6.add(new arcShapeTestNodePathBT(Effects.TRANSPARENT));
        arcPage6.add(new arcShapeTestNodePathBT(Effects.RADIAL_GRADIENT));
        arcPage6.add(new arcShapeTestNodePathBT(Effects.STROKE_DASH));
        arcPage6.add(new arcShapeTestNodePathBT(Effects.STROKE_CAP));
        rootTestNode.add(arcPage6);

        final PageWithSlots arcPage7 = new PageWithSlots(Pages.PathSQUARE.name(), heightPageContentPane, widthPageContentPane);
        arcPage7.setSlotSize(149, 149);
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.FILL));
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.LINEAR_GRAD));
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.STROKE));
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.STROKE_GRAD));
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.TRANSPARENT));
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.RADIAL_GRADIENT));
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.STROKE_DASH));
        arcPage7.add(new arcShapeTestNodePathSQUARE(Effects.STROKE_CAP));
        rootTestNode.add(arcPage7);

        final PageWithSlots arcPage8 = new PageWithSlots(Pages.PathROUNDNonZeroFillRule.name(), heightPageContentPane, widthPageContentPane);
        arcPage8.setSlotSize(149, 149);
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.FILL));
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.LINEAR_GRAD));
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.STROKE));
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.STROKE_GRAD));
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.TRANSPARENT));
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.RADIAL_GRADIENT));
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.STROKE_DASH));
        arcPage8.add(new arcShapeTestNodePathROUNDNonZeroFillRule(Effects.STROKE_CAP));
        rootTestNode.add(arcPage8);

        final PageWithSlots arcPage9 = new PageWithSlots(Pages.SVG.name(), heightPageContentPane, widthPageContentPane);
        arcPage9.setSlotSize(149, 149);
        arcPage9.add(new arcShapeTestNodeSVG(Effects.FILL));
        arcPage9.add(new arcShapeTestNodeSVG(Effects.LINEAR_GRAD));
        arcPage9.add(new arcShapeTestNodeSVG(Effects.STROKE));
        arcPage9.add(new arcShapeTestNodeSVG(Effects.STROKE_GRAD));
        arcPage9.add(new arcShapeTestNodeSVG(Effects.TRANSPARENT));
        arcPage9.add(new arcShapeTestNodeSVG(Effects.RADIAL_GRADIENT));
        arcPage9.add(new arcShapeTestNodeSVG(Effects.STROKE_DASH));
        arcPage9.add(new arcShapeTestNodeSVG(Effects.STROKE_CAP));
        rootTestNode.add(arcPage9);

        return rootTestNode;
    }


    // COPYPASTED from JavaFXCompatibility graphics.api.shape PathElement_1a.java
    /**
     * Create Path (using all possible PathElements)
     * within given height/width
     * @param w path width
     * @param h path height
     * @param k 0 - absolute coordinates 1 - relative coordinates
     * @return path as shape instance
     */
    public static Shape drawPath(double w, double h, int k)
    {
        double space = 10.0;
        ObservableList<Double> XYparams = getXYparams(1, 1, w, h, space);
        double z = XYparams.get(0);
        double sX = XYparams.get(1);
        double sY = XYparams.get(2);
        double kX = XYparams.get(3);
        double kY = XYparams.get(4);
        double centerXZ = ((kX + 1) * sX + z * kX) / (2 * kX);
        double centerYZ = ((kY + 1) * sY + z * kY) / (2 * kY);
        //Variables for the Path elements
        double MTx = centerXZ - z / 2;
        double MTy = centerYZ + z / 4;
        double LTx = z / 4;
        double LTy = -z / 4;
        double HLTx = -z / 4;
        double VLTy = -z / 4;
        double QCTx = 3 * z / 4;
        double QCTy = 0.0;
        double QCTpx = z / 2;
        double QCTpy = -z / 4;
        double ATx = 0.0;
        double ATy = z / 2;
        double ATrx = z / 4;
        double ATry = z / 4;
        double CCTcx1 = -z / 4;
        double CCTcy1 = z / 4;
        double CCTcx2 = -z / 2;
        double CCTcy2 = -z / 4;
        double CCTx = -(3 * z / 4);
        double CCTy = 0.0;
        Path Path_a = new Path();
        Path_a.setFill(Color.BROWN);
        Path_a.setFillRule(FillRule.EVEN_ODD);

        Path_a.setStrokeWidth(2.0);
        Path_a.setStroke(Color.RED);
        MoveTo moveto = new MoveTo(MTx + sX / 2, MTy + sY / 2);
        //TODO: this needs to be fixed at later point in time.
        //set absolute cause exception
//        moveto.setAbsolute(false);
        LineTo lineto = new LineTo(LTx, LTy);
        lineto.setAbsolute(false);
        HLineTo hlineto = new HLineTo(HLTx);
        hlineto.setAbsolute(false);
        VLineTo vlineto = new VLineTo(VLTy);
        vlineto.setAbsolute(false);
        QuadCurveTo quadcurveto = new QuadCurveTo(QCTpx, QCTpy, QCTx, QCTy);
        quadcurveto.setAbsolute(false);
        ArcTo arcto = new ArcTo();
        arcto.setX(ATx);
        arcto.setY(ATy);
        arcto.setRadiusX(ATrx);
        arcto.setRadiusY(ATry);
        arcto.setSweepFlag(true);
        arcto.setAbsolute(false);
        CubicCurveTo cubiccurveto = new CubicCurveTo(CCTcx1, CCTcy1, CCTcx2, CCTcy2, CCTx + 10, CCTy + 10);
        cubiccurveto.setAbsolute(false);
        ClosePath closepath = new ClosePath();
        closepath.setAbsolute(false);
        Path_a.getElements().clear();
        Path_a.getElements().addAll(moveto, lineto, hlineto, vlineto, quadcurveto, arcto, cubiccurveto, closepath);
        double MTx_a = centerXZ - z / 2;
        double MTy_a = centerYZ + z / 4;
        double LTx_a = centerXZ - z / 4;
        double LTy_a = centerYZ;
        double HLTx_a = centerXZ - z / 2;
        double VLTy_a = centerYZ - z / 4;
        double QCTx_a = centerXZ + z / 4;
        double QCTy_a = centerYZ - z / 4;
        double QCTpx_a = centerXZ;
        double QCTpy_a = centerYZ - z / 2;
        double ATx_a = centerXZ + z / 4;
        double ATy_a = centerYZ + z / 4;
        double ATrx_a = z / 4;
        double ATry_a = z / 4;
        double CCTcx1_a = centerXZ;
        double CCTcy1_a = centerYZ + z / 2;
        double CCTcx2_a = centerXZ - z / 4;
        double CCTcy2_a = centerYZ;
        double CCTx_a = centerXZ - z / 2;
        double CCTy_a = centerYZ + z / 4;
        Path Path_b = new Path();
        Path_b.setFill(null);
        Path_b.setStrokeWidth(2.0);
        Path_b.setStroke(Color.BLUE);
        Path_b.setFillRule(FillRule.NON_ZERO);
        ArcTo arcto2 = new ArcTo();
        arcto2.setX(ATx_a);
        arcto2.setY(ATy_a);
        arcto2.setRadiusX(ATrx_a);
        arcto2.setRadiusY(ATry_a);
        arcto2.setSweepFlag(true);
        Path_b.getElements().clear();
        Path_b.getElements().addAll(new MoveTo(MTx_a, MTy_a), new LineTo(LTx_a, LTy_a), new HLineTo(HLTx_a), new VLineTo(VLTy_a), new QuadCurveTo(QCTpx_a, QCTpy_a, QCTx_a, QCTy_a), arcto2, new CubicCurveTo(CCTcx1_a, CCTcy1_a, CCTcx2_a, CCTcy2_a, CCTx_a + 10, CCTy_a + 10), new ClosePath());
        //Group xx = new Group();
        //xx.getChildren().addAll(Path_a, Path_b);
        //return xx;
        if ( 0 == k )
            return Path_a;
        else
            return Path_b;
    }

    // COPYPASTED from JavaFXCompatibility graphics.api.common GraphicsTest.java
    public static ObservableList<Double> getXYparams(double kX, double kY, double w, double h, double space) {
        /*
         * The Node is a square
         * result [0] - z width/height of Node
         * result [1] - space between Nodes in X
         * result [2] - space between Nodes in Y
         * result [3] - number of Nodes in X
         * result [4] - number of Nodes in Y
         * result [5] - width of the Stage
         * result [6] - height of the Stage
         */
        ObservableList<Double> result = FXCollections.observableArrayList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        result.set(3, kX);
        result.set(4, kY);
        result.set(5, w);
        result.set(6, h);
        double zX = (w - (kX + 1) * space) / kX;
        double zY = (h - (kY + 1) * space) / kY;
        double z = zX;
        if (zX > zY) {
            z = zY;
        }
        double spaceX = (w - z * kX) / (kX + 1);
        double spaceY = (h - z * kY) / (kY + 1);
        result.set(1, spaceX);
        result.set(2, spaceY);
        result.set(0, z);
        return result;
    }

}

