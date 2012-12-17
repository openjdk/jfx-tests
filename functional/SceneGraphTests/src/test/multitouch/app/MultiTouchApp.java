/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.multitouch.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
//public class MultiTouchApp extends Application {
public class MultiTouchApp extends InteroperabilityApp {

    private static Random rnd = new Random();
    TouchPointsMarksScene scene;
    Pane root;
    List<Shape> touchableShapes = new ArrayList<Shape>();
    TextArea log;
    ChoiceBox cbEventTransport = new ChoiceBox();
    ToggleButton tbtnDirect = new ToggleButton("Direct");
    ToggleButton tbtnInertia = new ToggleButton("Inertia");
    
    class EventLogger<T extends Event> implements EventHandler<T> {

        ToggleButton btn;
        TextArea log;

        public EventLogger(TextArea log, ToggleButton btn) {
            this.log = log;
            this.btn = btn;
        }

        @Override
        public void handle(T e) {
            if (btn.isSelected()) {
                double pos = log.getScrollTop();
                log.insertText(log.getLength(), "\n" + e.toString());
                log.setScrollTop(pos);
            }
        }
    }
    
    class MouseEventLogger<T extends MouseEvent> implements EventHandler<T> {

        ToggleButton btn;
        TextArea log;

        public MouseEventLogger(TextArea log, ToggleButton btn) {
            this.log = log;
            this.btn = btn;
        }

        @Override
        public void handle(T e) {
            if (btn.isSelected()) {
                String mouseSynthesizedMsg = null;
                if(e.isSynthesized()) {
                    mouseSynthesizedMsg = "Synthesized: ";
                } else {
                    mouseSynthesizedMsg = "Native: ";
                }
                double pos = log.getScrollTop();
                log.insertText(log.getLength(), "\n" + mouseSynthesizedMsg + e.toString());
                log.setScrollTop(pos);
            }
        }
    }
    

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle(this.getClass().getSimpleName());
//        scene = (TouchPointsMarksScene) getScene();
//        stage.setScene(scene);
//        stage.show();
//    }
    @Override
    protected Scene getScene() {
        //System.out.println(com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());

        root = new Pane();
        scene = new TouchPointsMarksScene(root, 600, 400, Color.WHITE);

        final VBox vb = new VBox();
        FlowPane fpEventsLogBtns = new FlowPane();

        Button btnClearScene = new Button("ClearScene");
        Button btnClearLog = new Button("ClearLog");
        ToggleButton tbtnScrollStartFinish = new ToggleButton("Log ScrollStart/Finish");
        ToggleButton tbtnRotateStartFinish = new ToggleButton("Log RotateStart/Finish");
        ToggleButton tbtnZoomStartFinsh = new ToggleButton("Log ZoomStart/Finish");
        ToggleButton tbtnTouchPressRelease = new ToggleButton("Log TouchPress/Release");
        ToggleButton tbtnScroll = new ToggleButton("Log Scroll (!too slow)");
        ToggleButton tbtnRotate = new ToggleButton("Log Rotate");
        ToggleButton tbtnZoom = new ToggleButton("Log Zoom");
        ToggleButton tbtnSwipe = new ToggleButton("Log Swipe");
        ToggleButton tbtnTouch = new ToggleButton("Log Touch");
        ToggleButton tbtnMouse = new ToggleButton("Log Mouse");

        fpEventsLogBtns.getChildren().addAll(btnClearScene, btnClearLog,
                tbtnScrollStartFinish, tbtnRotateStartFinish, tbtnZoomStartFinsh,
                tbtnTouchPressRelease,
                tbtnSwipe,
                tbtnScroll, tbtnRotate, tbtnZoom, tbtnTouch, tbtnMouse);
        vb.getChildren().add(fpEventsLogBtns);

        FlowPane fpShapes = new FlowPane();
        Button btnAddArc = new Button("AddArc");
        Button btnAddCircle = new Button("AddCircle");
        Button btnAddCubicCurve = new Button("AddCubicCurve ");
        Button btnAddEllipse = new Button("AddEllipse");
        Button btnAddLine = new Button("AddLine");
        Button btnAddPolyline = new Button("AddPolyline");
        Button btnAddPath = new Button("AddPath");
        Button btnAddPolygon = new Button("AddPolygon");
        Button btnAddQuadCurve = new Button("AddQuadCurve");
        Button btnAddRectangle = new Button("AddRectangle");
        Button btnAddText = new Button("AddText");

        for (TouchableShapeFactory.EventTransport t : TouchableShapeFactory.EventTransport.values()) {
            cbEventTransport.getItems().add(t.toString());
        }
        cbEventTransport.getSelectionModel().selectFirst();

        fpShapes.getChildren().addAll(cbEventTransport, tbtnDirect, tbtnInertia,
                btnAddArc, btnAddCubicCurve, btnAddCircle,
                btnAddEllipse, btnAddLine, btnAddPolyline,
                btnAddPath, btnAddPolygon, btnAddQuadCurve,
                btnAddRectangle, btnAddText);
        
        vb.getChildren().add(fpShapes);

        log = new TextArea();
        log.setEditable(false);
        vb.getChildren().add(log);
        root.getChildren().add(vb);

        final Group touchableShapesGroup = new Group();
        root.getChildren().add(touchableShapesGroup);

        btnClearScene.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                touchableShapesGroup.getChildren().clear();
                scene.clearMarks();
            }
        });
        btnClearLog.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                log.setText("");
            }
        });

        btnAddArc.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Arc a = createRandomArc();

                TouchableShapeFactory.makeTouchable(a, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(a);
                touchableShapesGroup.getChildren().add(a);
            }
        });

        btnAddCircle.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Circle c = createRandomCircle();

                TouchableShapeFactory.makeTouchable(c, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(c);
                touchableShapesGroup.getChildren().add(c);
            }
        });

        btnAddCubicCurve.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                CubicCurve c = createRandomCubicCurve();

                TouchableShapeFactory.makeTouchable(c, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(c);
                touchableShapesGroup.getChildren().add(c);
            }
        });

        btnAddEllipse.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Ellipse e = createRandomEllipse();

                TouchableShapeFactory.makeTouchable(e, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(e);
                touchableShapesGroup.getChildren().add(e);
            }
        });

        btnAddLine.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Line l = createRandomLine();

                TouchableShapeFactory.makeTouchable(l, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(l);
                touchableShapesGroup.getChildren().add(l);
            }
        });

        btnAddPath.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Path p = createRandomPath();

                TouchableShapeFactory.makeTouchable(p, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(p);
                touchableShapesGroup.getChildren().add(p);
            }
        });

        btnAddPolygon.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Polygon p = createRandomPolygon();

                TouchableShapeFactory.makeTouchable(p, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(p);
                touchableShapesGroup.getChildren().add(p);
            }
        });

        btnAddPolyline.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Polyline p = createRandomPolyline();

                TouchableShapeFactory.makeTouchable(p, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(p);
                touchableShapesGroup.getChildren().add(p);
            }
        });

        btnAddQuadCurve.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                QuadCurve q = createRandomQuadCurve();

                TouchableShapeFactory.makeTouchable(q, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(q);
                touchableShapesGroup.getChildren().add(q);
            }
        });

        btnAddRectangle.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Rectangle r = createRandomRectangle();

                TouchableShapeFactory.makeTouchable(r, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                touchableShapes.add(r);
                touchableShapesGroup.getChildren().add(r);
            }
        });

        btnAddText.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                Text txt = createText();

                TouchableShapeFactory.makeTouchable(txt, getEventTransportByButttons(), 
                        tbtnDirect.selectedProperty(), tbtnInertia.selectedProperty());
                txt.setStrokeWidth(1);
                touchableShapes.add(txt);
                touchableShapesGroup.getChildren().add(txt);
            }
        });

        scene.setOnZoom(new EventLogger<ZoomEvent>(log, tbtnZoom));
        scene.setOnZoomStarted(new EventLogger<ZoomEvent>(log, tbtnZoomStartFinsh));
        scene.setOnZoomFinished(new EventLogger<ZoomEvent>(log, tbtnZoomStartFinsh));
        scene.setOnRotate(new EventLogger<RotateEvent>(log, tbtnRotate));
        scene.setOnRotationStarted(new EventLogger<RotateEvent>(log, tbtnRotateStartFinish));
        scene.setOnRotationFinished(new EventLogger<RotateEvent>(log, tbtnRotateStartFinish));
        scene.setOnScroll(new EventLogger<ScrollEvent>(log, tbtnScroll));
        scene.setOnScrollStarted(new EventLogger<ScrollEvent>(log, tbtnScrollStartFinish));
        scene.setOnScrollFinished(new EventLogger<ScrollEvent>(log, tbtnScrollStartFinish));
        scene.setOnSwipeLeft(new EventLogger<SwipeEvent>(log, tbtnSwipe));
        scene.setOnSwipeRight(new EventLogger<SwipeEvent>(log, tbtnSwipe));
        scene.setOnSwipeUp(new EventLogger<SwipeEvent>(log, tbtnSwipe));
        scene.setOnSwipeDown(new EventLogger<SwipeEvent>(log, tbtnSwipe));

        scene.setOnTouchPressed(new EventLogger<TouchEvent>(log, tbtnTouchPressRelease));
        scene.setOnTouchReleased(new EventLogger<TouchEvent>(log, tbtnTouchPressRelease));
        scene.setOnTouchMoved(new EventLogger<TouchEvent>(log, tbtnTouch));
        scene.setOnTouchStationary(new EventLogger<TouchEvent>(log, tbtnTouch));

        scene.addEventHandler(MouseEvent.ANY, new MouseEventLogger<MouseEvent>(log, tbtnMouse));

        scene.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                vb.setPrefWidth(scene.getWidth());
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                vb.setPrefHeight(scene.getHeight());
            }
        });

        Utils.addBrowser(scene);
        return scene;
    }

    private TouchableShapeFactory.EventTransport getEventTransportByButttons() {
        for (TouchableShapeFactory.EventTransport t : TouchableShapeFactory.EventTransport.values()) {
            if (cbEventTransport.getSelectionModel().isSelected(t.ordinal())) {
                return t;
            }
        }
        return TouchableShapeFactory.EventTransport.ADD_HANDLER;
    }

    private Arc createRandomArc() {
        double centerX = scene.getWidth() * rnd.nextDouble() * 0.9;
        double centerY = scene.getHeight() * rnd.nextDouble() * 0.9;
        double radiusX = 20 + rnd.nextDouble() * scene.getWidth() / 5;
        double radiusY = 20 + rnd.nextDouble() * scene.getHeight() / 5;
        double startAngle = 20 + rnd.nextDouble() * 360;
        double len = rnd.nextDouble() * 360;
        Arc a = new Arc(centerX, centerY, radiusX, radiusY, startAngle, len);
        a.setType(ArcType.ROUND);
        System.out.printf("Arc a = new new Arc(%s, %s, %s, %s, %s, %s);\n", centerX, centerY, radiusX, radiusY, startAngle, len);
        System.out.printf("a.setType(ArcType.ROUND);\n");

        return a;
    }

    private Circle createRandomCircle() {
        double centerX = scene.getWidth() * rnd.nextDouble() * 0.9;
        double centerY = scene.getHeight() * rnd.nextDouble() * 0.9;
        double radius = 20 + rnd.nextDouble() * scene.getHeight() / 5;

        Circle c = new Circle(centerX, centerY, radius);

        System.out.printf("Circle c = new Circle(%s, %s, %s);\n", centerX, centerY, radius);
        return c;
    }

    private CubicCurve createRandomCubicCurve() {
        double startX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double startY = (scene.getHeight() * rnd.nextDouble() * 0.9);
        double controlX1 = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double controlY1 = (scene.getHeight() * rnd.nextDouble() * 0.9);
        double controlX2 = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double controlY2 = (scene.getHeight() * rnd.nextDouble() * 0.9);
        double endX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double endY = (scene.getHeight() * rnd.nextDouble() * 0.9);

        CubicCurve c = new CubicCurve(startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);
        System.out.printf("CubicCurve c = new CubicCurve(%s, %s, %s, %s, %s, %s, %s, %s);\n",
                startX, startY, controlX1, controlY1, controlX2, controlY2, endX, endY);

        return c;
    }

    private Ellipse createRandomEllipse() {
        double centerX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double centerY = (scene.getHeight() * rnd.nextDouble() * 0.9);
        double radiusX = (20 + rnd.nextDouble() * scene.getWidth() / 5);
        double radiusY = (20 + rnd.nextDouble() * scene.getHeight() / 5);
        Ellipse e = new Ellipse(centerX, centerY, radiusX, radiusY);
        System.out.printf("Ellipse e = new Ellipse(%s, %s, %s, %s);\n",
                centerX, centerY, radiusX, radiusY);
        return e;
    }

    private Line createRandomLine() {
        double startX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double startY = (scene.getHeight() * rnd.nextDouble() * 0.9);
        double endX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double endY = (scene.getHeight() * rnd.nextDouble() * 0.9);
        Line l = new Line(startX, startY, endX, endY);
        System.out.printf("Line l = new Line(%s, %s, %s, %s);\n",
                startX, startY, endX, endY);
        return l;
    }

    private Path createRandomPath() {
        Path p = new Path();
        System.out.printf("Path p = new Path();\n");

        double moveToX = scene.getWidth() * rnd.nextDouble() * 0.9;
        double moveToY = scene.getHeight() * rnd.nextDouble() * 0.9;

        p.getElements().add(new MoveTo(moveToX, moveToY));
        System.out.printf("p.getElements().add(new MoveTo(%s, %s));\n", moveToX, moveToY);

        int nElements = 3 + Math.abs(rnd.nextInt(5));
        for (int i = 0; i < nElements; ++i) {
            PathElement pe = null;
            int pathElementId = Math.abs(rnd.nextInt(5));
            switch (pathElementId) {
                case 0: {
                    double radiusX = 20 + rnd.nextDouble() * scene.getWidth() / 5;
                    double radiusY = 20 + rnd.nextDouble() * scene.getHeight() / 5;
                    double xAxRot = scene.getWidth() * rnd.nextDouble() * 0.9;
                    double yAxRot = scene.getHeight() * rnd.nextDouble() * 0.9;

                    pe = new ArcTo(
                            radiusX,
                            radiusY,
                            0,
                            xAxRot,
                            yAxRot,
                            false, false);
                    System.out.printf("PathElement pe%s = new ArcTo(%s, %s, 0, %s, %s, false, false);\n",
                            i, radiusX, radiusY, xAxRot, yAxRot);
                    break;
                }
                case 1: {
                    double x1 = scene.getWidth() * rnd.nextDouble() * 0.9;
                    double y1 = scene.getHeight() * rnd.nextDouble() * 0.9;
                    double x2 = scene.getWidth() * rnd.nextDouble() * 0.9;
                    double y2 = scene.getHeight() * rnd.nextDouble() * 0.9;
                    double x3 = scene.getWidth() * rnd.nextDouble() * 0.9;
                    double y3 = scene.getHeight() * rnd.nextDouble() * 0.9;
                    pe = new CubicCurveTo(x1, y1, x2, y2, x3, y3);
                    System.out.printf("PathElement pe%s = new CubicCurveTo(%s, %s, %s, %s, %s, %s);\n",
                            i, x1, y1, x2, y2, x3, y3);
                    break;
                }
                case 2: {
                    double x = scene.getWidth() * rnd.nextDouble() * 0.9;
                    pe = new HLineTo(x);
                    System.out.printf("PathElement pe%s = new HLineTo(%s);\n", i, x);
                    break;
                }
                case 3: {
                    double x = scene.getWidth() * rnd.nextDouble() * 0.9;
                    double y = scene.getHeight() * rnd.nextDouble() * 0.9;
                    pe = new LineTo(x, y);
                    System.out.printf("PathElement pe%s = new LineTo(%s, %s);\n", i, x, y);
                    break;
                }
                case 4: {
                    double x1 = scene.getWidth() * rnd.nextDouble() * 0.9;
                    double y1 = scene.getHeight() * rnd.nextDouble() * 0.9;
                    double x2 = scene.getWidth() * rnd.nextDouble() * 0.9;
                    double y2 = scene.getHeight() * rnd.nextDouble() * 0.9;
                    pe = new QuadCurveTo(x1, y1, x2, y2);
                    System.out.printf("PathElement pe%s = new QuadCurveTo(%s, %s, %s, %s);\n", i, x1, y1, x2, y2);
                    break;
                }
                case 5: {
                    double y = scene.getHeight() * rnd.nextDouble() * 0.9;
                    pe = new VLineTo(y);
                    System.out.printf("PathElement pe%s = new VLineTo(%s);\n", i, y);
                    break;
                }
                default:
                    continue;
            }
            p.getElements().add(pe);
            System.out.printf("p.getElements().add(pe%s);\n", i);
        }
        if (rnd.nextBoolean()) {
            p.getElements().add(new ClosePath());
            System.out.printf("p.getElements().add(new ClosePath());\n");
        }
        return p;
    }

    private Polygon createRandomPolygon() {
        Polygon p = new Polygon();
        System.out.printf("Polygon p = new Polygon();\n");
        int nPoints = 3 + Math.abs(rnd.nextInt(5));
        for (int i = 0; i < nPoints; ++i) {
            double x = scene.getWidth() * rnd.nextDouble() * 0.9;
            double y = scene.getHeight() * rnd.nextDouble() * 0.9;
            p.getPoints().add(x);
            p.getPoints().add(y);
            System.out.printf("p.getPoints().add(%s);\n", x);
            System.out.printf("p.getPoints().add(%s);\n", y);
        }
        return p;
    }

    private Polyline createRandomPolyline() {
        Polyline p = new Polyline();
        System.out.printf("Polyline p = new Polyline();\n");
        int nPoints = 3 + Math.abs(rnd.nextInt(5));
        for (int i = 0; i < nPoints; ++i) {
            double x = scene.getWidth() * rnd.nextDouble() * 0.9;
            double y = scene.getHeight() * rnd.nextDouble() * 0.9;
            p.getPoints().add(x);
            p.getPoints().add(y);
            System.out.printf("p.getPoints().add(%s);\n", x);
            System.out.printf("p.getPoints().add(%s);\n", y);
        }
        return p;
    }

    private QuadCurve createRandomQuadCurve() {
        double startX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double startY = (scene.getHeight() * rnd.nextDouble() * 0.9);
        double controlX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double controlY = (scene.getHeight() * rnd.nextDouble() * 0.9);
        double endX = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double endY = (scene.getHeight() * rnd.nextDouble() * 0.9);
        QuadCurve q = new QuadCurve(startX, startY, controlX, controlY, endX, endY);
        System.out.printf("QuadCurve q = new QuadCurve(%s, %s, %s, %s, %s, %s);\n",
                startX, startY, controlX, controlY, endX, endY);
        return q;
    }

    private Rectangle createRandomRectangle() {
        double width = (20 + rnd.nextDouble() * scene.getWidth() / 5);
        double height = (20 + rnd.nextDouble() * scene.getHeight() / 5);
        double x = (scene.getWidth() * rnd.nextDouble() * 0.9);
        double y = (scene.getHeight() * rnd.nextDouble() * 0.9);

        Rectangle r = new Rectangle(x, y, width, height);
        System.out.printf("Rectangle r = new Rectangle(%s, %s, %s, %s);\n",
                x, y, width, height);
        return r;
    }

    private Text createText() {
        Text txt = new Text("JavaFX");
        txt.setFont(new Font(20));
        txt.setLayoutX(scene.getWidth() / 2);
        txt.setLayoutY(scene.getHeight() / 2);
        txt.setScaleX(5);
        txt.setScaleY(5);
        
        System.out.printf("Text txt = new Text(\"JavaFX\");\n");
        System.out.printf("txt.setFont(new Font(20));\n");
        System.out.printf("txt.setLayoutX(%s);\n", scene.getWidth() / 2);
        System.out.printf("txt.setLayoutY(%s);\n", scene.getHeight() / 2);
        System.out.printf("txt.setScaleX(5);\n");
        System.out.printf("txt.setScaleY(5);\n");
        return txt;
    }

    public static void main(String[] args) {
        Utils.launch(MultiTouchApp.class, args);
        //Application.launch(MultiTouchApp.class, args);
    }
}
