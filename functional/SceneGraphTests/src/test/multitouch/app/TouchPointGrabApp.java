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
package test.multitouch.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
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
public class TouchPointGrabApp extends InteroperabilityApp {
//public class MouseEventApp extends Application {

    private double rectSize = 150.0;
    Scene scene;
    Pane root;
    Rectangle rectSrc = new Rectangle(rectSize, rectSize);
    Rectangle rectTarget0 = new Rectangle(rectSize, rectSize);
    Rectangle rectTarget1 = new Rectangle(rectSize, rectSize);
    Rectangle rectTarget2 = new Rectangle(rectSize, rectSize);
    TestTouchEventHandler srcHandler;
    TestTouchEventHandler target0Handler;
    TestTouchEventHandler target1Handler;
    TestTouchEventHandler target2Handler;

    class TouchEventHandlerHistoryItem {

        public Node node;
        public EventType type;
        public EventHandler<TouchEvent> handler;
    }
    List<TouchEventHandlerHistoryItem> lstEventHandlers = new ArrayList<TouchEventHandlerHistoryItem>();

    class TestTouchEventHandler implements EventHandler<TouchEvent> {

        int firstSetId = -1;
        int lastSetId = -1;
        EventType lastEventType = TouchEvent.ANY;
        EventType firstEventType = TouchEvent.ANY;
        String tag = "";

        TestTouchEventHandler(String tag) {
            this.tag = tag;
        }

        public void handle(TouchEvent e) {
            if (firstSetId < 0) {
                firstSetId = e.getEventSetId();
                firstEventType = e.getEventType();
            }

            lastSetId = e.getEventSetId();
            lastEventType = e.getEventType();

            System.out.println(tag + ": " + touchEvent2String(e));
        }

        EventType getLastEventType() {
            return lastEventType;
        }

        EventType getFirstEventType() {
            return firstEventType;
        }

        int getFirstSetId() {
            return firstSetId;
        }

        int getLastSetId() {
            return lastSetId;
        }
    }

    private static class GrabAllPointsToAfterNEventsHandler implements EventHandler<TouchEvent> {

        int maxEventsCount = 0;
        int eventsCount = 0;
        boolean grabbed = false;
        EventTarget target;

        private GrabAllPointsToAfterNEventsHandler(int maxEventsCount, EventTarget target) {
            this.maxEventsCount = maxEventsCount;
            this.target = target;
        }

        public void handle(TouchEvent e) {
            if (grabbed) {
                return;
            }
            if (eventsCount >= maxEventsCount) {
                for (TouchPoint p : e.getTouchPoints()) {
                    p.grab(target);
                    grabbed = true;
                }
            }
            ++eventsCount;
        }
    }

    private static class UngrabAllPointsAfterNEventsHandler implements EventHandler<TouchEvent> {

        int maxEventsCount = 0;
        int eventsCount = 0;

        private UngrabAllPointsAfterNEventsHandler(int maxEventsCount) {
            this.maxEventsCount = maxEventsCount;
        }

        public void handle(TouchEvent e) {
            if (eventsCount >= maxEventsCount) {
                for (TouchPoint p : e.getTouchPoints()) {
                    p.ungrab();
                }
            }
            ++eventsCount;
        }
    }

    private static class UngrabEventsHandler implements EventHandler<TouchEvent> {

        public void handle(TouchEvent e) {
            for (TouchPoint p : e.getTouchPoints()) {
                p.ungrab();
            }
        }
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle(this.getClass().getSimpleName());
//        scene = getScene();
//        stage.setScene(scene);
//        stage.show();
//    }
    private static String touchEvent2String(TouchEvent e) {
        StringBuilder b = new StringBuilder();
        b.append(e.toString()).append("; TouchPoints = [");

        for (TouchPoint p : e.getTouchPoints()) {
            b.append("<").append(p.toString()).append(">, ");
        }
        b.append("];");

        return b.toString();
    }

    private void removeAllEventHandler() {
        for (TouchEventHandlerHistoryItem item : lstEventHandlers) {
            item.node.removeEventHandler(item.type, item.handler);
        }

        lstEventHandlers.clear();
    }

    private void addEventHandler4Node(Node node, EventType type, EventHandler<TouchEvent> handler) {
        TouchEventHandlerHistoryItem item = new TouchEventHandlerHistoryItem();
        item.node = node;
        item.handler = handler;
        item.type = type;

        node.addEventHandler(type, handler);

        lstEventHandlers.add(item);
    }

    @Override
    protected Scene getScene() {

        root = new Pane();
//        scene = new Scene(root, 600, 400, Color.WHITE);
        scene = new Scene(root, 600, 400);

        final VBox vbTop = new VBox();
        HBox hbControls = new HBox();
        VBox vbTest1 = new VBox();
        VBox vbTest2 = new VBox();
        VBox vbTest3 = new VBox();
        VBox vbTest4 = new VBox();

        Button doTest1 = new Button("Begin");
        Button endTest1 = new Button("End");
        final Label resultTest1 = new Label("-");
        vbTest1.getChildren().addAll(new Label("Test1"), doTest1, endTest1, resultTest1);

        Button doTest2 = new Button("Begin");
        Button endTest2 = new Button("End");
        final Label resultTest2 = new Label("-");
        vbTest2.getChildren().addAll(new Label("Test2"), doTest2, endTest2, resultTest2);

        Button doTest3 = new Button("Begin");
        Button endTest3 = new Button("End");
        final Label resultTest3 = new Label("-");
        vbTest3.getChildren().addAll(new Label("Test3"), doTest3, endTest3, resultTest3);

        Button doTest4 = new Button("Begin");
        Button endTest4 = new Button("End");
        final Label resultTest4 = new Label("-");
        vbTest4.getChildren().addAll(new Label("Test4"), doTest4, endTest4, resultTest4);

        hbControls.getChildren().addAll(vbTest1, vbTest2, vbTest3, vbTest4);

        HBox hbRect12 = new HBox();
        HBox hbRect34 = new HBox();

        rectSrc.setFill(Color.LIGHTGREEN);
        rectTarget0.setFill(Color.PINK);
        rectTarget1.setFill(Color.YELLOW);
        rectTarget2.setFill(Color.BLACK);

        hbRect12.getChildren().addAll(rectSrc, rectTarget0);
        hbRect34.getChildren().addAll(rectTarget1, rectTarget2);

        vbTop.getChildren().addAll(hbControls, hbRect12, hbRect34);

        root.getChildren().add(vbTop);
        vbTop.setPadding(new Insets(5, 5, 5, 5));
        hbControls.setSpacing(5);

        ////////////////////////////////////////////////////////////////////////
        // Test 1 (Grab all points after first event)
        doTest1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                System.out.println("Test1");
                removeAllEventHandler();
                srcHandler = new TestTouchEventHandler("GREEN");
                target0Handler = new TestTouchEventHandler("PINK");

                addEventHandler4Node(rectSrc, TouchEvent.ANY,
                        new GrabAllPointsToAfterNEventsHandler(0, rectTarget0));
                addEventHandler4Node(rectSrc, TouchEvent.ANY, srcHandler);
                addEventHandler4Node(rectTarget0, TouchEvent.ANY, target0Handler);
            }
        });

        endTest1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                removeAllEventHandler();

                if ((srcHandler != null)
                        && (target0Handler != null)
                        && (srcHandler.getFirstSetId() == 1)
                        && (srcHandler.getLastEventType() == TouchEvent.TOUCH_PRESSED)
                        && (target0Handler.getFirstSetId() == 2)
                        && (target0Handler.getLastEventType() == TouchEvent.TOUCH_RELEASED)) {
                    resultTest1.setText("Test1 passed");
                } else {
                    resultTest1.setText("Test1 failed");
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////
        // Test 2 (Grab sequence)
        doTest2.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                System.out.println("\nTest2");
                removeAllEventHandler();
                srcHandler = new TestTouchEventHandler("GREEN");
                target0Handler = new TestTouchEventHandler("PINK");
                target1Handler = new TestTouchEventHandler("YELLOW");
                target2Handler = new TestTouchEventHandler("BLACK");

                addEventHandler4Node(rectSrc, TouchEvent.ANY,
                        new GrabAllPointsToAfterNEventsHandler(0, rectTarget0));
                addEventHandler4Node(rectTarget0, TouchEvent.ANY,
                        new GrabAllPointsToAfterNEventsHandler(0, rectTarget1));
                addEventHandler4Node(rectTarget1, TouchEvent.ANY,
                        new GrabAllPointsToAfterNEventsHandler(10, rectTarget2));

                addEventHandler4Node(rectSrc, TouchEvent.ANY, srcHandler);
                addEventHandler4Node(rectTarget0, TouchEvent.ANY, target0Handler);
                addEventHandler4Node(rectTarget1, TouchEvent.ANY, target1Handler);
                addEventHandler4Node(rectTarget2, TouchEvent.ANY, target2Handler);
            }
        });

        endTest2.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                removeAllEventHandler();

                if ((srcHandler != null)
                        && (target0Handler != null)
                        && (target1Handler != null)
                        && (target2Handler != null)
                        && (srcHandler.getFirstSetId() == 1)
                        && (target0Handler.getFirstSetId() == 2)
                        && (target1Handler.getFirstSetId() == 3)
                        && (target2Handler.getFirstSetId() == 14)
                        && (srcHandler.getLastEventType() == TouchEvent.TOUCH_PRESSED)
                        && (target0Handler.getLastEventType() != TouchEvent.ANY)
                        && (target1Handler.getLastEventType() != TouchEvent.ANY)
                        && (target2Handler.getLastEventType() == TouchEvent.TOUCH_RELEASED)) {
                    resultTest2.setText("Test2 passed");
                } else {
                    resultTest2.setText("Test2 failed");
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////
        // Test 3 (Ungrab after grab)
        doTest3.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                System.out.println("\nTest3");
                removeAllEventHandler();
                srcHandler = new TestTouchEventHandler("GREEN");
                target0Handler = new TestTouchEventHandler("PINK");

                addEventHandler4Node(rectSrc, TouchEvent.ANY,
                        new GrabAllPointsToAfterNEventsHandler(0, rectTarget0));
                addEventHandler4Node(rectTarget0, TouchEvent.ANY,
                        new UngrabAllPointsAfterNEventsHandler(2));

                addEventHandler4Node(rectSrc, TouchEvent.ANY, srcHandler);
                addEventHandler4Node(rectTarget0, TouchEvent.ANY, target0Handler);
            }
        });

        endTest3.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                removeAllEventHandler();

                if ((srcHandler != null)
                        && (target0Handler != null)
                        && (srcHandler.getFirstSetId() == 1)
                        && (target0Handler.getFirstSetId() == 2)
                        && (srcHandler.getLastEventType() == TouchEvent.TOUCH_RELEASED)
                        && (target0Handler.getLastEventType() != TouchEvent.ANY)) {
                    resultTest3.setText("Test3 passed");
                } else {
                    resultTest3.setText("Test3 failed");
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////
        // Test 4 (Ungrab allways)
        doTest4.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                System.out.println("\nTest4");
                removeAllEventHandler();
                srcHandler = new TestTouchEventHandler("GREEN");
                target0Handler = new TestTouchEventHandler("PINK");
                target1Handler = new TestTouchEventHandler("YELLOW");
                target2Handler = new TestTouchEventHandler("BLACK");

                addEventHandler4Node(rectSrc, TouchEvent.ANY,
                        new UngrabEventsHandler());
                addEventHandler4Node(rectTarget0, TouchEvent.ANY,
                        new UngrabEventsHandler());
                addEventHandler4Node(rectTarget1, TouchEvent.ANY,
                        new UngrabEventsHandler());
                addEventHandler4Node(rectTarget2, TouchEvent.ANY,
                        new UngrabEventsHandler());

                addEventHandler4Node(rectSrc, TouchEvent.ANY, srcHandler);
                addEventHandler4Node(rectTarget0, TouchEvent.ANY, target0Handler);
                addEventHandler4Node(rectTarget1, TouchEvent.ANY, target1Handler);
                addEventHandler4Node(rectTarget2, TouchEvent.ANY, target2Handler);
            }
        });

        endTest4.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                removeAllEventHandler();

                if ((srcHandler != null)
                        && (target0Handler != null)
                        && (target1Handler != null)
                        && (target2Handler != null)
                        && (srcHandler.getFirstSetId() < srcHandler.getLastSetId())
                        && (srcHandler.getLastSetId() < target0Handler.getFirstSetId())
                        && (target0Handler.getFirstSetId() < target0Handler.getLastSetId())
                        && (target0Handler.getLastSetId() < target2Handler.getFirstSetId())
                        && (target2Handler.getFirstSetId() < target2Handler.getLastSetId())
                        && (target2Handler.getLastSetId() < target1Handler.getFirstSetId())
                        && (target1Handler.getFirstSetId() < target1Handler.getLastSetId())
                        && (srcHandler.getFirstEventType() == TouchEvent.TOUCH_PRESSED)
                        && (target0Handler.getFirstEventType() != TouchEvent.ANY)
                        && (target2Handler.getFirstEventType() != TouchEvent.ANY)
                        && (target1Handler.getLastEventType() == TouchEvent.TOUCH_RELEASED)) {
                    resultTest4.setText("Test4 passed");
                } else {
                    resultTest4.setText("Test4 failed");
                }
            }
        });

        
        Utils.addBrowser(scene);
        return scene;
    }

    public static void main(String[] args) {
        Utils.launch(TouchPointGrabApp.class, args);
        //Application.launch(MouseEventApp.class, args);
    }
}
