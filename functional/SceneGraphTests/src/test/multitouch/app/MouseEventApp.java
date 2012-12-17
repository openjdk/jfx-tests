/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.multitouch.app;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
public class MouseEventApp extends InteroperabilityApp {
//public class MouseEventApp extends Application {

    Scene scene;
    Pane root;
    CheckBox cbNativeReleased = new CheckBox("MOUSE_RELEASED");
    CheckBox cbNativePressed = new CheckBox("MOUSE_PRESSED");
    CheckBox cbNativeClicked = new CheckBox("MOUSE_CLICKED");
    CheckBox cbNativeEnteredTarget = new CheckBox("MOUSE_ENTERED_TARGET");
    CheckBox cbNativeEntered = new CheckBox("MOUSE_ENTERED");
    CheckBox cbNativeExitedTarget = new CheckBox("MOUSE_EXITED_TARGET");
    CheckBox cbNativeExited = new CheckBox("MOUSE_EXITED");
    CheckBox cbNativeMoved = new CheckBox("MOUSE_MOVED");
    CheckBox cbNativeDragged = new CheckBox("MOUSE_DRAGGED");
    CheckBox cbNativeDragDetected = new CheckBox("DRAG_DETECTED");
    CheckBox cbSynthesizedPressed = new CheckBox("MOUSE_PRESSED");
    CheckBox cbSynthesizedReleased = new CheckBox("MOUSE_RELEASED");
    CheckBox cbSynthesizedClicked = new CheckBox("MOUSE_CLICKED");
    CheckBox cbSynthesizedEnteredTarget = new CheckBox("MOUSE_ENTERED_TARGET");
    CheckBox cbSynthesizedEntered = new CheckBox("MOUSE_ENTERED");
    CheckBox cbSynthesizedExitedTarget = new CheckBox("MOUSE_EXITED_TARGET");
    CheckBox cbSynthesizedExited = new CheckBox("MOUSE_EXITED");
    CheckBox cbSynthesizedMoved = new CheckBox("MOUSE_MOVED");
    CheckBox cbSynthesizedDragged = new CheckBox("MOUSE_DRAGGED");
    CheckBox cbSynthesizedDragDetected = new CheckBox("DRAG_DETECTED");
    Map<EventType<MouseEvent>, CheckBox> mapNative = new HashMap<EventType<MouseEvent>, CheckBox>();
    Map<EventType<MouseEvent>, CheckBox> mapSynthesized = new HashMap<EventType<MouseEvent>, CheckBox>();

    public MouseEventApp() {
        mapNative.put(MouseEvent.MOUSE_RELEASED, cbNativeReleased);
        mapNative.put(MouseEvent.MOUSE_PRESSED, cbNativePressed);
        mapNative.put(MouseEvent.MOUSE_CLICKED, cbNativeClicked);
        mapNative.put(MouseEvent.MOUSE_ENTERED_TARGET, cbNativeEnteredTarget);
        mapNative.put(MouseEvent.MOUSE_ENTERED, cbNativeEntered);
        mapNative.put(MouseEvent.MOUSE_EXITED_TARGET, cbNativeExitedTarget);
        mapNative.put(MouseEvent.MOUSE_EXITED, cbNativeExited);
        mapNative.put(MouseEvent.MOUSE_MOVED, cbNativeMoved);
        mapNative.put(MouseEvent.MOUSE_DRAGGED, cbNativeDragged);
        mapNative.put(MouseEvent.DRAG_DETECTED, cbNativeDragDetected);

        mapSynthesized.put(MouseEvent.MOUSE_RELEASED, cbSynthesizedReleased);
        mapSynthesized.put(MouseEvent.MOUSE_PRESSED, cbSynthesizedPressed);
        mapSynthesized.put(MouseEvent.MOUSE_CLICKED, cbSynthesizedClicked);
        mapSynthesized.put(MouseEvent.MOUSE_ENTERED_TARGET, cbSynthesizedEnteredTarget);
        mapSynthesized.put(MouseEvent.MOUSE_ENTERED, cbSynthesizedEntered);
        mapSynthesized.put(MouseEvent.MOUSE_EXITED_TARGET, cbSynthesizedExitedTarget);
        mapSynthesized.put(MouseEvent.MOUSE_EXITED, cbSynthesizedExited);
        mapSynthesized.put(MouseEvent.MOUSE_MOVED, cbSynthesizedMoved);
        mapSynthesized.put(MouseEvent.MOUSE_DRAGGED, cbSynthesizedDragged);
        mapSynthesized.put(MouseEvent.DRAG_DETECTED, cbSynthesizedDragDetected);
        
        for(CheckBox cb : mapNative.values()) {
            cb.setDisable(true);
            cb.setOpacity(0.7);
        }

        for(CheckBox cb : mapSynthesized.values()) {
            cb.setDisable(true);
            cb.setOpacity(1);
        }
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle(this.getClass().getSimpleName());
//        scene = getScene();
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    protected Scene getScene() {

        root = new Pane();
        scene = new Scene(root, 600, 400, Color.WHITE);

        final HBox hb = new HBox();
        final VBox vbMouseNative = new VBox();
        final VBox vbMouseSynthesized = new VBox();

        vbMouseNative.getChildren().addAll(
                new Label("Native Events"),
                cbNativeReleased,
                cbNativePressed,
                cbNativeClicked,
                cbNativeEnteredTarget,
                cbNativeEntered,
                cbNativeExitedTarget,
                cbNativeExited,
                cbNativeMoved,
                cbNativeDragged,
                cbNativeDragDetected);

        vbMouseSynthesized.getChildren().addAll(
                new Label("Synthesized Events"),
                cbSynthesizedReleased,
                cbSynthesizedPressed,
                cbSynthesizedClicked,
                cbSynthesizedEnteredTarget,
                cbSynthesizedEntered,
                cbSynthesizedExitedTarget,
                cbSynthesizedExited,
                cbSynthesizedMoved,
                cbSynthesizedDragged,
                cbSynthesizedDragDetected);

        Button endTest = new Button("EndTest");
        final Label testResult = new Label("");

        hb.getChildren().addAll(
                vbMouseNative,
                vbMouseSynthesized,
                endTest,
                testResult);
        root.getChildren().add(hb);
        hb.setPadding(new Insets(5, 5, 5, 5));
        hb.setSpacing(5);

        final Group touchableShapesGroup = new Group();
        root.getChildren().add(touchableShapesGroup);

        Rectangle r = new Rectangle(200, 200, 200, 200);
        r.setFill(Color.LIGHTGREEN);

        touchableShapesGroup.getChildren().add(r);

        r.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            public void handle(MouseEvent e) {
                Map<EventType<MouseEvent>, CheckBox> map = null;
                if (e.isSynthesized()) {
                    map = mapSynthesized;
                } else {
                    map = mapNative;
                }

                map.get(e.getEventType()).setSelected(true);
            }
        });

        endTest.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                if (mapSynthesized.get(MouseEvent.MOUSE_CLICKED).isSelected()
                        && mapSynthesized.get(MouseEvent.MOUSE_PRESSED).isSelected()
                        && mapSynthesized.get(MouseEvent.MOUSE_RELEASED).isSelected()
                        && mapSynthesized.get(MouseEvent.MOUSE_ENTERED).isSelected()
                        && mapSynthesized.get(MouseEvent.MOUSE_EXITED).isSelected()
                        && mapSynthesized.get(MouseEvent.MOUSE_DRAGGED).isSelected()
                        && mapSynthesized.get(MouseEvent.DRAG_DETECTED).isSelected()) {
                    testResult.setText("Test passed");
                } else {
                    testResult.setText("Test failed");
                }
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                hb.setPrefWidth(scene.getWidth());
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                hb.setPrefHeight(scene.getHeight());
            }
        });

        Utils.addBrowser(scene);
        return scene;
    }

    public static void main(String[] args) {
        Utils.launch(MouseEventApp.class, args);
        //Application.launch(MouseEventApp.class, args);
    }
}
