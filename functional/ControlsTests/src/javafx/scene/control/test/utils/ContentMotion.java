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
package javafx.scene.control.test.utils;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Shape;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

/**
 * @author Alexander Kirov
 *
 * A class, which is used to make any node to do complex motion.
 */
public class ContentMotion {
    public final static int motionDuration = 5000;

    private Animation timeline;
    private Path path;

    public Path getPath() {
        return path;
    }

    public Animation getTimeline() {
        return timeline;
    }

    public void applyTransition(Node node) {
        PathTransition pathTransition = new PathTransition();
        timeline = pathTransition;

        path = (Path) drawPath(140.0, 140.0, 0);
        path.setStrokeWidth(2);
        path.setStroke(Color.RED);

        path.setFill(Color.TRANSPARENT);

        pathTransition.setDuration(Duration.millis(motionDuration));
        pathTransition.setNode(node);
        //pathTransition.setPath(AnimationPath.createFromPath(path));
        pathTransition.setPath(path);
        pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
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
    private Shape drawPath(double w, double h, int k) {
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
        if (0 == k) {
            return Path_a;
        } else {
            return Path_b;
        }
    }

    // COPYPASTED from JavaFXCompatibility graphics.api.common GraphicsTest.java
    private ObservableList<Double> getXYparams(double kX, double kY, double w, double h, double space) {
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
