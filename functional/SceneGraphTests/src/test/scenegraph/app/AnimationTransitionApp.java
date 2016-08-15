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

import java.text.DecimalFormat;
import java.util.Set;
import javafx.animation.*;
import javafx.animation.PathTransition.OrientationType;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.util.Duration;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */

public class AnimationTransitionApp extends BasicButtonChooserApp {

    private final static String cuepointName = "cuename1";
    private Animation timeline;
    private final Text currentState = new Text("");
    private StdButtons stdButtons = new StdButtons();
    private Shape circle = new Rectangle(250, 50, 60, 20) {{setFill(Color.GREEN);}};
    private KeyValue keyValue3;
    public final static long typicalDuration = 500;

    public static enum Pages {
        TransitionSvgPath, TransitionPath, TransitionFadeFromDefaultToValue,
        TransitionFadeFromPredefinedByValue,
        TransitionRotateByAngle, TransitionRotateToAngle, TransitionTranslateTo,
        TransitionTranslateBy, TransitionScaleTo, TransitionScaleBy,
        TransitionParallel,TransitionSequential, TransitionStroke, TransitionFill
    }

    public static enum CheckedIds {
        currentX, currentY, onFinishFloatCounter, currentState
    }

    public AnimationTransitionApp() {
        super(800, 800, "AnimationTransition", false); // "false" stands for "additionalActionButton = "
    }

    private EventHandler<ActionEvent> onFinish = new EventHandler<ActionEvent>() {

        public void handle(ActionEvent t) {
            currentState.setText(timeline.getStatus().toString());
            if (!currentTestNode.checkStep2()) {
                reportGetterFailure("step2 failed");
            }
        }
    };

    private boolean ColorsAreCloseEnough(Color a, Color b) {
        double coef = 6d/255d;
        boolean result = false;
        /*
        double a1 =  (Math.abs( a.getGreen() - b.getGreen() ));
        double a2 =  (Math.abs( a.getBlue() - b.getBlue() ));
        double a3 =  (Math.abs( a.getRed() - b.getRed() ));
        */
        if ( (Math.abs( a.getGreen() - b.getGreen() ) < coef)
                && (Math.abs( a.getBlue() - b.getBlue() ) < coef)
                && (Math.abs( a.getRed() - b.getRed() ) < coef)
                )
            result = true;

        return result;
    }

    private Pane pre(Animation _a) {
        if (null != timeline) {
            timeline.stop();
        }
        circle.setRotate(0);
        circle.setTranslateX(0);
        circle.setTranslateY(0);
        circle.setScaleX(1);
        circle.setScaleY(1);
        circle.setOpacity(1);
        circle.setStroke(null);
        circle.setFill(Color.GREEN);

        VBox vb = new VBox();
        vb.setTranslateY(150);
        stdButtons.addButtonsToSequence(vb.getChildren());
        Pane p = new Pane();
        p.getChildren().add(circle);
        p.getChildren().add(vb);
        timeline = _a;
        timeline.setOnFinished(onFinish);
        fillInfoFrame(vb);
        return p;
    }

/*    TODO use in next test version
    private VBox vbObject;
    private WritableObjectValue<String> writableObjectString1 = new StringProperty("begin1");
    private WritableObjectValue<String> writableObjectString2 = new StringProperty("begin2");

    private EventHandler<ActionEvent> onFinishObject = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            Text txtEnd1 = new Text(writableObjectString1.getValue());
            Text txtEnd2 = new Text(writableObjectString2.getValue());
            txtEnd1.setId("txtEnd1");
            txtEnd2.setId("txtEnd2");
            vbObject.getChildren().add(txtEnd1);
            vbObject.getChildren().add(txtEnd2);
        }
    };
*/

    private class SelfcheckedTestNode extends TestNode {

        public boolean checkStep1() {
            return false;
        }

        public boolean checkStep2() {
            return false;
        }
    }

    private SelfcheckedTestNode currentTestNode = null;

    private class TransitionSvgPath extends SelfcheckedTestNode {

        @Override
        public Node drawNode() {
            currentTestNode = this;
            PathTransition pathTransition = new PathTransition();
            Pane p = pre(pathTransition);

            SVGPath path = new SVGPath();
            path.setContent("M40,60 C42,148 144,30 25,32");
            path.setStrokeWidth(2);
            path.setStroke(Color.RED);
            p.getChildren().add(path);
            path.setFill(Color.TRANSPARENT);

//            pathTransition.setDuration(Duration.valueOf(typicalDuration));
            pathTransition.setDuration(new Duration(typicalDuration));
            pathTransition.setNode(circle);
            circle.setRotate(30);
            //pathTransition.setPath(AnimationPath.createFromPath(path));
            pathTransition.setPath(path);
            pathTransition.setOrientation(OrientationType.NONE);

            timeline.setCycleCount(3);
            timeline.setAutoReverse(true);
            return p;
        }
        @Override
        public boolean checkStep1() {
            return true;
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs((-255) - circle.getTranslateX()) < 2e-1
                    && Math.abs((-28) - circle.getTranslateY()) < 2e-1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionPath extends SelfcheckedTestNode {

        @Override
        public Node drawNode() {
            currentTestNode = this;
            PathTransition pathTransition = new PathTransition();
            Pane p = pre(pathTransition);

            Path path = createPath();
            path.setStrokeWidth(2);
            path.setStroke(Color.RED);
            p.getChildren().add(path);
            path.setFill(Color.TRANSPARENT);

            pathTransition.setDuration(Duration.millis(typicalDuration));
            pathTransition.setNode(circle);
            //pathTransition.setPath(AnimationPath.createFromPath(path));
            pathTransition.setPath(path);
            pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

            timeline.setCycleCount(3);
            timeline.setAutoReverse(true);

            return p;
        }
        @Override
        public boolean checkStep1() {
            return true;
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs((-265) - circle.getTranslateX()) < 2
                    && Math.abs((45) - circle.getTranslateY()) < 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionFadeFromDefaultToValue extends SelfcheckedTestNode {
        final double fromFadeValue = 1;
        final double targetFadeValue = 0.2;

        @Override
        public Node drawNode() {
            currentTestNode = this;

            FadeTransition transition = new FadeTransition(Duration.millis(typicalDuration),circle);
            Pane p = pre(transition);

            circle.setOpacity(fromFadeValue);
            transition.setCycleCount(Timeline.INDEFINITE);
            transition.setAutoReverse(true);
            //transition.setFromValue(fromFadeValue);
            transition.setToValue(targetFadeValue);

            // test TransitionFade.getToValue() method
            if ( targetFadeValue != transition.getToValue()) {
                reportGetterFailure("TransitionFade.getToValue()");
            }

            // test TransitionFade.getNode method
            if ( circle != transition.getNode()) {
                reportGetterFailure("TransitionFade.getNode()");
            }

            // test TransitionFade.getDuration method
            double tst1 = transition.getDuration().toMillis();
            if ( (typicalDuration - tst1) > 1e-6 ) {
                reportGetterFailure("TransitionFade.getFromValue()");
            }

            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);

            return p;
        }
        @Override
        public boolean checkStep1() {
            return true;
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(targetFadeValue - circle.getOpacity()) < 1e-2) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionFadeFromPredefinedByValue extends SelfcheckedTestNode {
        final double fromFadeValue = 1;
        final double byFadeValue = -0.8;

        @Override
        public Node drawNode() {
            currentTestNode = this;

            FadeTransition transition = new FadeTransition(Duration.millis(typicalDuration),circle);
            Pane p = pre(transition);

            circle.setOpacity(fromFadeValue);
            transition.setCycleCount(Timeline.INDEFINITE);
            transition.setAutoReverse(true);
            transition.setFromValue(fromFadeValue);
            transition.setByValue(byFadeValue);

            // test TransitionFade.getToValue() method
            if ( byFadeValue != transition.getByValue()) {
                reportGetterFailure("TransitionFade.getByValue()");
            }

            // test TransitionFade.getDuration method
            double tst1 = transition.getDuration().toMillis();
            if ( (typicalDuration - tst1) > 1e-6 ) {
                reportGetterFailure("TransitionFade.getFromValue()");
            }

            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);

            return p;
        }
        @Override
        public boolean checkStep1() {
            return true;
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs((fromFadeValue+byFadeValue) - circle.getOpacity()) < 2e-1) {
                return true;
            } else {
                return false;
            }
        }

    }
    private class TransitionRotateToAngle extends SelfcheckedTestNode {
        final double dFrom = 10d;
        final double dTo = 350d;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            RotateTransition transition = new RotateTransition(Duration.millis(typicalDuration),circle);
            Pane p = pre(transition);

            circle.setRotate(dFrom);
            transition.setFromAngle(dFrom);
            transition.setToAngle(dTo);
            transition.setInterpolator(Interpolator.LINEAR);

            transition.setCycleCount(2);
            transition.setAutoReverse(true);

            return p;
        }
        @Override
        public boolean checkStep1() {
            if (Math.abs(dTo - circle.getRotate()) < 16) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(dFrom - circle.getRotate()) < 4e-1) {
                return true;
            } else {
                return false;
            }
        }

    }

    private class TransitionRotateByAngle extends SelfcheckedTestNode {
        final double dFrom = 90d;
        final double dBy = 90d;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            RotateTransition transition = new RotateTransition();
            Pane p = pre(transition);

            transition.setDuration(Duration.millis(typicalDuration));
            transition.setNode(circle);

            circle.setRotate(dFrom);
            transition.setFromAngle(dFrom);
            transition.setByAngle(dBy);

            transition.setCycleCount(1);
            transition.setAutoReverse(true);

            Point3D p3d = new Point3D(0,0,100);
            transition.setAxis(p3d);
            if (!p3d.equals(transition.getAxis()))  {
                reportGetterFailure("TransitionRotate.getAxis()");
            }

            return p;
        }
        @Override
        public boolean checkStep1() {
            return true;
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs((dFrom+dBy) - circle.getRotate()) < 2e-1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionTranslateBy extends SelfcheckedTestNode {
        final double dShift = 50;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            TranslateTransition transition = new TranslateTransition();
            Pane p = pre(transition);

            transition.setDuration(Duration.millis(typicalDuration));
            transition.setNode(circle);

            transition.setByX(dShift);
            transition.setByY(dShift);
            if ((dShift - transition.getByX()) > 1e-6) {
                reportGetterFailure("getByX()");
            }
            if ((dShift - transition.getByY()) > 1e-6) {
                reportGetterFailure("getByY()");
            }
            if ((0 - transition.getByZ()) > 1e-6) {
                reportGetterFailure("getByZ()");
            }

            transition.setCycleCount(2);
            transition.setAutoReverse(true);

            return p;
        }
        @Override
        public boolean checkStep1() {
            if ( Math.abs(dShift - circle.getTranslateX()) < 8e-1
                    && Math.abs(dShift - circle.getTranslateY()) < 8e-1) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(0 - circle.getTranslateX()) < 1e-1
                    && Math.abs(0 - circle.getTranslateY()) < 1e-1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionTranslateTo extends SelfcheckedTestNode {
        final double dShift = 50;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            TranslateTransition transition = new TranslateTransition(Duration.millis(typicalDuration));
            Pane p = pre(transition);

            transition.setNode(circle);

            transition.setToX(dShift);
            transition.setToY(dShift);

            if ((dShift - transition.getToX()) > 1e-5) {
                reportGetterFailure("getToX()");
            }
            if ((dShift - transition.getToY()) > 1e-5) {
                reportGetterFailure("getToY()");
            }
            if ((0 - transition.getToZ()) > 1e-5) {
                reportGetterFailure("getToZ()");
            }

            transition.setCycleCount(2);
            transition.setAutoReverse(true);

            return p;
        }
        @Override
        public boolean checkStep1() {
            if ( Math.abs(dShift  - circle.getTranslateX()) < 2e-1 &&
                    Math.abs(dShift  - circle.getTranslateY()) < 2e-1) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(0  - circle.getTranslateX()) < 1e-2
                    && Math.abs(0  - circle.getTranslateY()) < 1e-2) {
                return true;
            } else {
                return false;
            }
        }

    }

    private class TransitionScaleTo extends SelfcheckedTestNode {
        final double dShift = 4;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            ScaleTransition transition = new ScaleTransition(Duration.millis(typicalDuration));
            Pane p = pre(transition);

            transition.setNode(circle);

            transition.setToX(dShift);
            transition.setToY(dShift);

            if ((dShift - transition.getToX()) > 1e-6) {
                reportGetterFailure("getToX()");
            }
            if ((dShift - transition.getToY()) > 1e-6) {
                reportGetterFailure("getToY()");
            }
            if ((0 - transition.getToZ()) > 1e-6) {
                reportGetterFailure("getToZ()");
            }

            transition.setCycleCount(2);
            transition.setAutoReverse(true);

            return p;
        }
        @Override
        public boolean checkStep1() {
            if ( Math.abs(dShift  - circle.getScaleX()) < 1e-1
                    && Math.abs(dShift  - circle.getScaleY()) < 1e-1) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(1  - circle.getScaleX()) < 1e-2
                    && Math.abs(1  - circle.getScaleY()) < 1e-2) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionScaleBy extends SelfcheckedTestNode {
        final double dShift = 4;
        final double dFrom = 0.3d;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            ScaleTransition transition = new ScaleTransition(Duration.millis(typicalDuration), circle);
            Pane p = pre(transition);

            transition.setFromX(dFrom);
            transition.setFromY(dFrom);
            transition.setByX(dShift);
            transition.setByY(dShift);

            if ((dFrom - transition.getFromX()) > 1e-6) {
                reportGetterFailure("getFromX()");
            }
            if ((dFrom - transition.getFromY()) > 1e-6) {
                reportGetterFailure("getFromY()");
            }
            if ((0 - transition.getFromZ()) > 1e-6) {
                reportGetterFailure("getFromZ()");
            }

            if ((dShift - transition.getByX()) > 1e-6) {
                reportGetterFailure("getByX()");
            }
            if ((dShift - transition.getByY()) > 1e-6) {
                reportGetterFailure("getByY()");
            }
            if ((0 - transition.getByZ()) > 1e-6) {
                reportGetterFailure("getByZ()");
            }

            transition.setCycleCount(2);
            transition.setAutoReverse(true);

            return p;
        }
        @Override
        public boolean checkStep1() {
            if ( Math.abs((dShift+dFrom)  - circle.getScaleX()) < 5e-2
                    && Math.abs((dShift+dFrom)  - circle.getScaleY()) < 5e-2) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(dFrom  - circle.getScaleX()) < 5e-2
                    && Math.abs(dFrom  - circle.getScaleY()) < 5e-2) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionStroke extends SelfcheckedTestNode {
        final Color dFrom = Color.RED;
        final Color dTo = Color.BLUE;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            StrokeTransition transition = new StrokeTransition(Duration.millis(typicalDuration), circle);
            Pane p = pre(transition);
            circle.setStrokeWidth(20);
            circle.setStrokeType(StrokeType.OUTSIDE);


            transition.setFromValue(dFrom);
            transition.setToValue(dTo);

            if (dFrom != transition.getFromValue()) {
                reportGetterFailure("getFrom()");
            }
            if (dTo != transition.getToValue()) {
                reportGetterFailure("getTo()");
            }

            transition.setCycleCount(2);
            transition.setAutoReverse(true);

            return p;
        }
        @Override
        public boolean checkStep1() {
            return ColorsAreCloseEnough(dTo, (Color)circle.getStroke());
        }
        @Override
        public boolean checkStep2() {
            return ColorsAreCloseEnough(dFrom, (Color)circle.getStroke());
        }
    }

    private class TransitionFill extends SelfcheckedTestNode {
        final Color dFrom = Color.RED;
        final Color dTo = Color.BLUE;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            FillTransition transition = new FillTransition(Duration.millis(typicalDuration), circle);
            Pane p = pre(transition);

            transition.setFromValue(dFrom);
            transition.setToValue(dTo);

            if (dFrom != transition.getFromValue()) {
                reportGetterFailure("getFrom()");
            }
            if (dTo != transition.getToValue()) {
                reportGetterFailure("getTo()");
            }

            transition.setCycleCount(2);
            transition.setAutoReverse(false);

            return p;
        }
        @Override
        public boolean checkStep1() {
            return ColorsAreCloseEnough(dTo, (Color)circle.getFill());
        }
        @Override
        public boolean checkStep2() {
            return ColorsAreCloseEnough(dTo, (Color)circle.getFill());
        }
    }

    private class TransitionParallel extends SelfcheckedTestNode {
        final Double dFrom = 0d;
        final Double dTo = 50d;
        final double dByAngle = 180d;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            ParallelTransition transition = new ParallelTransition();
            Pane p = pre(transition);

            Duration dur = new Duration(typicalDuration);
            TranslateTransition tt = new TranslateTransition(dur);
            tt.setFromY(dFrom);
            tt.setToY(dTo);
            tt.setFromX(dFrom);
            tt.setToX(dTo);
            tt.setCycleCount(1);
            tt.setAutoReverse(false);

            RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(typicalDuration));
            rotateTransition.setByAngle(dByAngle);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);

            transition.setNode(circle);
            if ( circle != transition.getNode()) {
                reportGetterFailure("getNode");
            }

            transition.getChildren().addAll(rotateTransition);
            transition.getChildren().addAll(tt);
            transition.setCycleCount(1);
            transition.setAutoReverse(false);

            return p;
        }
        @Override
        public boolean checkStep1() {
                return true;
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(dTo  - circle.getTranslateX()) < 1e-2
                    && Math.abs(dTo  - circle.getTranslateY()) <1e-2
                    && Math.abs(dByAngle - circle.getRotate()) <1e-2 ) {
                return true;
            } else {
                return false;
            }
        }
    }

    private class TransitionSequential extends SelfcheckedTestNode {
        final double dFrom = 0d;
        final double dTo = 50d;
        final double dByAngle = 180d;

        @Override
        public Node drawNode() {
            currentTestNode = this;
            SequentialTransition transition = new SequentialTransition();
            Pane p = pre(transition);

            final Duration dur = new Duration(typicalDuration);
            TranslateTransition tt = new TranslateTransition(dur);
            tt.setFromY(dFrom);
            tt.setToY(dTo);
            tt.setCycleCount(1);
            tt.setAutoReverse(false);

            PauseTransition tpause1 = new PauseTransition(dur);
            if ( !dur.equals(tpause1.getDuration()) ) {
                reportGetterFailure("pause.getDuration()");
            }
            PauseTransition tpause2 = new PauseTransition();
            tpause2.setDuration(dur);

            TranslateTransition tt2 = new TranslateTransition(dur);
            tt2.setFromX(dFrom);
            tt2.setToX(dTo);
            tt2.setCycleCount(1);
            tt2.setAutoReverse(false);

            RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(typicalDuration), circle);
            rotateTransition.setByAngle(dByAngle);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);

            transition.setNode(circle);
            if ( circle != transition.getNode()) {
                reportGetterFailure("getNode");
            }

            transition.getChildren().addAll(rotateTransition);
            transition.getChildren().addAll(tpause1);
            transition.getChildren().addAll(tt2);
            transition.getChildren().addAll(tpause2);
            transition.getChildren().addAll(tt);
            transition.setCycleCount(1);
            transition.setAutoReverse(false);

            return p;
        }
        @Override
        public boolean checkStep1() {
                return true;
        }
        @Override
        public boolean checkStep2() {
            if ( Math.abs(dTo  - circle.getTranslateX()) < 1e-2
                    && Math.abs(dTo  - circle.getTranslateY()) <1e-2
                    && Math.abs(dByAngle - circle.getRotate()) <1e-2) {
                return true;
            } else {
                return false;
            }
        }
    }

    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();

        // =========================
        final TestNode transitionSvgPath = new TransitionSvgPath();
        final TestNode transitionPath = new TransitionPath();
        final TestNode transitionFadeFromDefaultToValue = new TransitionFadeFromDefaultToValue();
        final TestNode transitionFadeFromPredefinedByValue = new TransitionFadeFromPredefinedByValue();
        final TestNode transitionRotateTo = new TransitionRotateToAngle();
        final TestNode transitionRotateBy = new TransitionRotateByAngle();
        final TestNode transitionTranslateTo = new TransitionTranslateTo();
        final TestNode transitionTranslateBy = new TransitionTranslateBy();
        final TestNode transitionScaleTo  = new TransitionScaleTo();
        final TestNode transitionScaleBy  = new TransitionScaleBy();
        final TestNode transitionParallel = new TransitionParallel();
        final TestNode transitionSequential  = new TransitionSequential();
        // ========= root tests list ==============
        rootTestNode.add(transitionSvgPath,     Pages.TransitionSvgPath.name());
        rootTestNode.add(transitionPath,        Pages.TransitionPath.name());
        rootTestNode.add(transitionFadeFromDefaultToValue, Pages.TransitionFadeFromDefaultToValue.name());
        rootTestNode.add(transitionFadeFromPredefinedByValue, Pages.TransitionFadeFromPredefinedByValue.name());
        rootTestNode.add(transitionRotateTo,    Pages.TransitionRotateToAngle.name());
        rootTestNode.add(transitionRotateBy,    Pages.TransitionRotateByAngle.name());
        rootTestNode.add(transitionTranslateBy, Pages.TransitionTranslateBy.name());
        rootTestNode.add(transitionTranslateTo, Pages.TransitionTranslateTo.name());
        rootTestNode.add(transitionScaleTo,     Pages.TransitionScaleTo.name());
        rootTestNode.add(transitionScaleBy,     Pages.TransitionScaleBy.name());
        rootTestNode.add(transitionParallel,    Pages.TransitionParallel.name());
        rootTestNode.add(transitionSequential, Pages.TransitionSequential.name());
        rootTestNode.add(new TransitionStroke(), Pages.TransitionStroke.name());
        rootTestNode.add(new TransitionFill(), Pages.TransitionFill.name());

        return rootTestNode;
    }

    private void fillInfoFrame(Pane vb) {
        final Text currentFocused = new Text("");
        currentFocused.setWrappingWidth(230);
        vb.getChildren().add(currentFocused);

        final Text currentX = new Text("");
        currentX.setId(CheckedIds.currentX.name());
        vb.getChildren().add(currentX);
        final Text currentY = new Text("");
        currentY.setId(CheckedIds.currentY.name());
        vb.getChildren().add(currentY);
        currentState.setId(CheckedIds.currentState.name());
        vb.getChildren().add(currentState);

        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {

                boolean bF = circle.isFocused();
                if (null != keyValue3) {
                    currentFocused.setText(keyValue3.toString() + "\n     Focused = " + bF);
                } else {
                    currentFocused.setText("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
                }

                double trX = circle.getTranslateX();
                String str1 = new DecimalFormat("###").format(trX);
                currentX.setText("Translate X = " + str1);

                double trY = circle.getTranslateY();
                String str2 = new DecimalFormat("###").format(trY);
                currentY.setText("Translate Y = " + str2);

                currentState.setText(timeline.getStatus().toString());

                //SelfcheckedTestNode
                double curTime = timeline.getCurrentTime().toMillis();
                double curDura = timeline.getCycleDuration().toMillis();
                if ( Math.abs(curTime - curDura) <1 ) {
                    if (!currentTestNode.checkStep1()) {
                        reportGetterFailure("step1 failed");
                    }
                }
            }
        });

        final Text currentRate = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                //String str = new DecimalFormat("###").format(circle.getTranslateX());

                currentRate.setText("Rate = " + timeline.getCurrentRate());
            }
        });
        vb.getChildren().add(currentRate);

        final Text currentDura = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {

                currentDura.setText("cycleDuration = " + timeline.getCycleDuration().toString());
            }
        });
        vb.getChildren().add(currentDura);

        final Text currentVis = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                currentVis.setText("visible = " + circle.isVisible());
            }
        });
        vb.getChildren().add(currentVis);

        final Text txtRate = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {

                txtRate.setText("rate = " + timeline.getRate());
            }
        });
        vb.getChildren().add(txtRate);

        final Text txtCue = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {

                Set<String> setstr = timeline.getCuePoints().keySet();
                String xxx = "";
                for (String s : setstr) {
                    xxx = xxx + " [" + s + "]";
                }
                txtCue.setText("cue = " + xxx);
            }
        });
        vb.getChildren().add(txtCue);


        final Text txtTime = new Text("");
        timeline.currentTimeProperty().addListener(new InvalidationListener() {

            public void invalidated(Observable ov) {
                String str = new DecimalFormat("#########").format(timeline.currentTimeProperty().get().toMillis());
                txtTime.setText("Time = " + str);
            }
        });
        vb.getChildren().add(txtTime);

    }


    class StdButtons {

       //final Button buttonStart = new ButtonBuilder().name("Start").id(getText()).build();

        final Button buttonStart = new Button("Start") {{
                setId(getText());
            }};
        Button buttonStop = new Button("Stop") {{
                setId(getText());
            }};
        Button buttonPause = new Button("Pause") {{
                setId(getText());
            }};
        Button buttonRestart = new Button("Restart") {{
                setId(getText());
            }};
        Button buttonCuepoint = new Button("PlayFromCuepoint") {{
                setId(getText());
            }};
        Button buttonDuration = new Button("PlayFromDuration") {{
                setId(getText());
            }};

        StdButtons(ObservableList<Node> _sn) {
            addButtonsToSequence(_sn);
        }

        final public void addButtonsToSequence(ObservableList<Node> _sn) {
            Pane p = new TilePane();
            p.setMinWidth(160);
            p.setPrefWidth(240);
            p.getChildren().addAll(buttonStop, buttonStart, buttonPause, buttonRestart,
                    buttonCuepoint, buttonDuration);
            _sn.add(p);
        }

        StdButtons() {
            buttonStart.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    //start timeline
                    timeline.play();
                }
            });
            buttonCuepoint.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    //start timeline
                    timeline.playFrom(cuepointName);
                }
            });

            buttonStop.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    //stop timeline
                    timeline.stop();
                }
            });
            buttonPause.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    //stop timeline
                    timeline.pause();
                    try {
                        Thread.sleep(50);
                    } catch (Exception e) {
                    }
                    currentState.setText(timeline.getStatus().toString());
                }
            });
            buttonRestart.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    //stop timeline
                    timeline.playFromStart();
                }
            });
            buttonDuration.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    //stop timeline
                    timeline.playFrom(new Duration(1000));
                }
            });
        }
    }

    private Path createPath() {
        return (Path) ShapePathElements2App.drawPath(140.0, 140.0, 0);
    }

    public static void main(String args[]) {
        Utils.launch(AnimationTransitionApp.class, args);
    }
}
