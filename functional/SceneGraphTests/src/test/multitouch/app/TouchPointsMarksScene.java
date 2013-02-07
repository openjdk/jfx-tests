/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.multitouch.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class TouchPointsMarksScene extends Scene {

    private final double touchedCircleRadius = 40;
    private final double touchedCircleStrokeWidth = 7;
    private final Color pressedColor = Color.RED;
    private final Color movedColor = Color.GREEN;
    private final Color stationaryColor = Color.BLUE;
    private final Color releaseColor = Color.DARKGRAY;
    private final double fadeDurationMs = 4000;
    private Group root;
    private Parent realSceneRoot;
    Map<Integer, Circle> mapTouch = new HashMap<Integer, Circle>();
    ArrayList<Line> lines = new ArrayList<Line>();
    Circle lineCenter = new Circle();

    class NodeEraser implements EventHandler<ActionEvent> {

        private Node n;

        public NodeEraser(Node n) {
            this.n = n;
        }

        @Override
        public void handle(ActionEvent t) {
            root.getChildren().remove(n);
        }
    }

    public TouchPointsMarksScene(Parent parent) {
        super(new Group(parent));
        realSceneRoot = parent;
        init();
    }

    public TouchPointsMarksScene(Parent parent, double d, double d1) {
        super(new Group(parent), d, d1);
        realSceneRoot = parent;
        init();
    }

    public TouchPointsMarksScene(Parent parent, Paint paint) {
        super(new Group(parent), paint);
        realSceneRoot = parent;
        init();
    }

    public TouchPointsMarksScene(Parent parent, double d, double d1, Paint paint) {
        super(new Group(parent), d, d1, paint);
        realSceneRoot = parent;
        init();
    }

    public TouchPointsMarksScene(Parent parent, double d, double d1, boolean bln) {
        super(new Group(parent), d, d1, bln);
        realSceneRoot = parent;
        init();
    }

    public void clearMarks() {
        mapTouch.clear();
        ArrayList<Node> rm = new ArrayList<Node>();
        for (Node n : root.getChildren()) {
            if ((n != realSceneRoot) && (n != lineCenter) && !lines.contains(n)) {
                rm.add(n);
            }
        }
        root.getChildren().removeAll(rm);
    }
    private static int MaxTouchPoints = 100;

    private void init() {
        root = (Group) getRoot();

        for (int i = 0; i < MaxTouchPoints; ++i) {
            Line l = new Line();
            l.setOpacity(0.5);
            l.setVisible(false);
            l.setMouseTransparent(true);
            lines.add(l);
        }

        lineCenter.setRadius(5);
        //lineCenter.setOpacity(0.5);
        lineCenter.setVisible(false);
        lineCenter.setMouseTransparent(true);

        for(Line l : lines) {
            root.getChildren().add(l);
        }
        root.getChildren().add(lineCenter);

        widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            }
        });

        heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
            }
        });

        ////////////////////////////////////////////////////////////
        /// Raw multitouch
        addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {

            @Override
            public void handle(TouchEvent e) {
                for (TouchPoint p : e.getTouchPoints()) {
                    paintTouchedCircle(p);
                }
                if (e.getTouchPoints().size() > 1) {
                    paintLine(e.getTouchPoints());
                } else {
                    for (Line l : lines) {
                        l.setVisible(false);;
                    }
                    lineCenter.setVisible(false);
                }
            }
        });
    }

    private void paintLine(List<TouchPoint> points) {
        double centerX = 0.0;
        double centerY = 0.0;
        for (TouchPoint p : points) {
            centerX += root.sceneToLocal(p.getSceneX(), p.getSceneY()).getX() / (double) points.size();
            centerY += root.sceneToLocal(p.getSceneX(), p.getSceneY()).getY() / (double) points.size();
        }
        for (int i = 0; i < points.size(); ++i) {
            lines.get(i).setStartX(centerX);
            lines.get(i).setStartY(centerY);
            lines.get(i).setEndX(root.sceneToLocal(points.get(i).getSceneX(), points.get(i).getSceneY()).getX());
            lines.get(i).setEndY(root.sceneToLocal(points.get(i).getSceneX(), points.get(i).getSceneY()).getY());
            lines.get(i).setVisible(true);
        }
        lineCenter.setCenterX(centerX);
        lineCenter.setCenterY(centerY);
        lineCenter.setVisible(true);
    }

    private void paintTouchedCircle(TouchPoint p) {
        Circle c = null;

        switch (p.getState()) {
            case PRESSED:
                // Create pressed marker witch stay on press point and fade
                Circle pressedCircle = new Circle(touchedCircleRadius);
                pressedCircle.setMouseTransparent(true);
                root.getChildren().add(pressedCircle);
                pressedCircle.setCenterX(root.sceneToLocal(p.getSceneX(), p.getSceneY()).getX());
                pressedCircle.setCenterY(root.sceneToLocal(p.getSceneX(), p.getSceneY()).getY());
                pressedCircle.setStrokeWidth(touchedCircleStrokeWidth);
                pressedCircle.setFill(null);
                pressedCircle.setStrokeLineCap(StrokeLineCap.ROUND);
                pressedCircle.setStroke(getColorByTouchState(p.getState()));

                FadeTransition ftPressed = new FadeTransition(Duration.millis(fadeDurationMs), pressedCircle);
                ftPressed.setFromValue(1.0);
                ftPressed.setToValue(0);
                ftPressed.setOnFinished(new NodeEraser(pressedCircle));
                ftPressed.play();

                // Create pressed point 
                c = mapTouch.get(p.getId());
                if (c == null) {
                    c = new Circle(touchedCircleRadius);
                    c.setMouseTransparent(true);
                    mapTouch.put(p.getId(), c);
                    root.getChildren().add(c);
                }
                break;
            case MOVED:
                c = mapTouch.get(p.getId());
                break;
            case STATIONARY:
                c = mapTouch.get(p.getId());
                break;
            case RELEASED:
                c = mapTouch.get(p.getId());
                if (c != null) {
                    mapTouch.remove(p.getId());

                    FadeTransition ftReleased = new FadeTransition(Duration.millis(fadeDurationMs), c);
                    ftReleased.setFromValue(1.0);
                    ftReleased.setToValue(0);
                    ftReleased.setOnFinished(new NodeEraser(c));
                    ftReleased.play();
                }
                break;
        }

        if (c == null) {
            return;
        }

        c.setCenterX(root.sceneToLocal(p.getSceneX(), p.getSceneY()).getX());
        c.setCenterY(root.sceneToLocal(p.getSceneX(), p.getSceneY()).getY());
        c.setStrokeWidth(touchedCircleStrokeWidth);
        c.setFill(null);
        c.setStrokeLineCap(StrokeLineCap.ROUND);
        c.setStroke(getColorByTouchState(p.getState()));
    }

    private Color getColorByTouchState(TouchPoint.State state) {
        switch (state) {
            case PRESSED:
                return pressedColor;
            case MOVED:
                return movedColor;
            case STATIONARY:
                return stationaryColor;
            case RELEASED:
                return releaseColor;
        }
        return null;
    }
}
