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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.VLineTo;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;



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
public class ShapePathElementsApp extends BasicButtonChooserApp {

    public ShapePathElementsApp() {
        super(600, 410, "ShapePathElements",false);
    }

    public static void main(String args[]) {
        test.javaclient.shared.Utils.launch(ShapePathElementsApp.class, args);
    }

    /**
     * For each tested shape the ShapeFactory instance should be added
     * @see ShapesApp#setup()
     */
    private static abstract class ShapeFactory {
        protected final String name;

        public ShapeFactory(String name) {
            this.name = name;
        }

        /**
         * Adds current shape to Region with custom effect.
         * </p> Optional.
         * @param container container to add shape to
         * @param shiftX horizontal position (for translateX)
         * @param shiftY vertical position (for translateY)
         */
        public void addCustomShapes(Region container, int shiftX, int shiftY) {
            // intentionally do nothing
        }

        /**
         * @return current shape instance
         */
        abstract Shape createShape();
    }

    /**
     * This methods allows to add new shape to the list of presented ones.
     * @param content ShapeFactory instance, which creates shape for common effects and can specify custom ones
     */
    private void register(final ShapeFactory content) {
        register(content, 90);
    }

    private void register(final ShapeFactory content, final int slotsize) {
        addPage(content.name, new Runnable() {
            public void run() {
                SLOTSIZEX = slotsize;
                SLOTSIZEY = slotsize;

                for (Effects effect : Effects.values()) {
                    //Region slot = new Region();
                    Pane slot = new Pane();
                    Shape shape = content.createShape();
                    effect.setEffect(shape, slot); // will add shape to scene at the appropriate moment
                    addSlot(effect.name(), slot);
                }

                content.addCustomShapes(getPageContent(), shiftX, shiftY);
            }
        });
    }

    public enum Pages {
        ArcOpenSQUARE, ArcOpenROUND, ArcOpenBT, ArcChord, ArcRound, PathBT,
        PathSQUARE, PathROUNDNonZeroFillRule, SVG

    }


    private int SLOTSIZEX;
    private int SLOTSIZEY;
 /**
     * Next free slot horizontal position
     */
    protected int shiftX = 0;
    /**
     * Next free slot vertical position
     */
    protected int shiftY = 0;

    private void addSlot(String name, Node field) {
        VBox slot = new VBox();
        slot.getChildren().addAll(new Label(name), field);
        slot.setTranslateX(shiftX);
        slot.setTranslateY(shiftY);
        int stepX = SLOTSIZEX + 1;
        int stepY = SLOTSIZEY + 20;
        shiftX += stepX;     // SLOTSIZE + 1;
        if ((shiftX + SLOTSIZEX) > width) {
            shiftX = 0;
            shiftY += stepY; //SLOTSIZE + 20;
        }
        getPageContent().getChildren().add(slot);
    }
    private Pane pageContent;
    private TestNode root;

    protected Pane getPageContent() {
        return pageContent;
    }

    private class RunTestNode extends TestNode {

        protected Runnable runnable;

        public RunTestNode(Runnable runnable) {
            this.runnable = runnable;
        }
    }

    private void addPage(String name, Runnable runnable) {
        TestNode page = new RunTestNode(runnable) {
            @Override
            public Node drawNode() {
                pageContent = new Pane();
                runnable.run();
                shiftX = 0;
                shiftY = 0;
                return getPageContent();
            }
        };
        root.add(page, name);
    }

    @Override
    protected TestNode setup() {
        root = new TestNode();

    final double DEFAULT_ARC_X = 50;
    final double DEFAULT_ARC_Y = DEFAULT_ARC_X;
    final double DEFAULT_ARC_RADIUS_X = 25;
    final double DEFAULT_ARC_RADIUS_Y = DEFAULT_ARC_RADIUS_X;
    final double DEFAULT_ARC_START_ANGLE = 45;
    final double DEFAULT_ARC_LENGTH = 270;

        // register shapes
        /**
         * Create Arc
         * - with StrokeLineCap type StrokeLineCap.SQUARE
         * - using constructor with arguments
         * - with ArcType.OPEN
         */
        register(new ShapeFactory(Pages.ArcOpenSQUARE.name()) {
            Shape createShape() {
                Arc arc = new Arc(DEFAULT_ARC_X, DEFAULT_ARC_Y, DEFAULT_ARC_RADIUS_X,
                        DEFAULT_ARC_RADIUS_X, DEFAULT_ARC_START_ANGLE, DEFAULT_ARC_LENGTH);
                arc.setType(ArcType.OPEN);
                arc.setStrokeLineCap(StrokeLineCap.SQUARE);
                return arc;
            }
        });

        /**
         * Create Arc
         * - with StrokeLineCap type StrokeLineCap.ROUND
         * - using constructor with arguments
         * - with ArcType.OPEN
         */
        register(new ShapeFactory(Pages.ArcOpenROUND.name()) {
            Shape createShape() {
                Arc arc = new Arc(DEFAULT_ARC_X, DEFAULT_ARC_Y, DEFAULT_ARC_RADIUS_X,
                        DEFAULT_ARC_RADIUS_Y, DEFAULT_ARC_START_ANGLE, DEFAULT_ARC_LENGTH);
                arc.setType(ArcType.OPEN);
                arc.setStrokeLineCap(StrokeLineCap.ROUND);
                return arc;
            }
        });

        /**
         * Create Arc
         * - with StrokeLineCap type StrokeLineCap.BUTT
         * - using constructor without arguments.
         * - using setters to set arc size/position
         * - with ArcType.OPEN
         */
        register(new ShapeFactory(Pages.ArcOpenBT.name()) {
            Shape createShape() {
                Arc arc = new Arc();
                arc.setCenterX(DEFAULT_ARC_X);
                arc.setCenterY(DEFAULT_ARC_Y);
                arc.setRadiusX(DEFAULT_ARC_RADIUS_X);
                arc.setRadiusY(DEFAULT_ARC_RADIUS_Y);
                arc.setStartAngle(DEFAULT_ARC_START_ANGLE);
                arc.setLength(DEFAULT_ARC_LENGTH);
                arc.setType(ArcType.OPEN);
                arc.setStrokeLineCap(StrokeLineCap.BUTT);
                return arc;
            }
        });

        /**
         * Create Arc
         * - with default StrokeLineCap
         * - using constructor without arguments.
         * - using setters to set arc size/position
         * - with ArcType.CHORD
         */
        register(new ShapeFactory(Pages.ArcChord.name()) {
            Shape createShape() {
                Arc arc = new Arc();
                arc.setCenterX(DEFAULT_ARC_X);
                arc.setCenterY(DEFAULT_ARC_Y);
                arc.setRadiusX(DEFAULT_ARC_RADIUS_X);
                arc.setRadiusY(DEFAULT_ARC_RADIUS_Y);
                arc.setStartAngle(DEFAULT_ARC_START_ANGLE);
                arc.setLength(DEFAULT_ARC_LENGTH);
                arc.setType(ArcType.CHORD);
                return arc;
            }
        });

        /**
         * Create Arc
         * - with default StrokeLineCap
         * - using constructor with arguments
         * - with ArcType.ROUND
         */
        register(new ShapeFactory(Pages.ArcRound.name()) {
            Shape createShape() {
                Arc arc = new Arc(DEFAULT_ARC_X, DEFAULT_ARC_Y, DEFAULT_ARC_RADIUS_X,
                        DEFAULT_ARC_RADIUS_X, DEFAULT_ARC_START_ANGLE, DEFAULT_ARC_LENGTH);
                arc.setType(ArcType.ROUND);
                return arc;
            }
        });

        /**
         * Create Path (using all possible PathElements, in relative coordinates)
         * - with StrokeLineCap.BUTT
         */
        register(new ShapeFactory(Pages.PathBT.name()) {
            Shape createShape() {
            Shape ss = drawPath(140.0,140.0,0);
            ss.setStrokeLineCap(StrokeLineCap.BUTT);
            return  ss;
            }
        }, 149);

        /**
         * Create Path (using all possible PathElements, in relative coordinates)
         * - with StrokeLineCap.SQUARE
         */
        register(new ShapeFactory(Pages.PathSQUARE.name()) {
            Shape createShape() {
            Shape ss = drawPath(140.0,140.0,0);
            ss.setStrokeLineCap(StrokeLineCap.SQUARE);
            return  ss;
            }
        }, 149);

        /**
         * Create Path (using all possible PathElements, in absolute)
         * - with StrokeLineCap.ROUND
         */
        register(new ShapeFactory(Pages.PathROUNDNonZeroFillRule.name()) {
            Shape createShape() {
            Shape ss = drawPath(140.0, 140.0, 1);
            ss.setStrokeLineCap(StrokeLineCap.ROUND);
            return  ss;
            }
        }, 149);

        register(new ShapeFactory(Pages.SVG.name()) {
            Shape createShape() {
            SVGPath svg = new SVGPath();
            svg.setContent("M40,60 C42,148 144,30 25,32");
            return  svg;
            }
        }, 149);
        return root;
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
    public Shape drawPath(double w, double h, int k)
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
    public ObservableList<Double> getXYparams(double kX, double kY, double w, double h, double space) {
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

