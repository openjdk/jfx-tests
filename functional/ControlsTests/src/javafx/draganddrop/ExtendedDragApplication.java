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
package javafx.draganddrop;

import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Andrey Nazarov
 * @author Victor Shubov http://javafx-jira.kenai.com/browse/RT-16916
 */
public class ExtendedDragApplication extends InteroperabilityApp {

    public static final String TITLE1 = "DragEventTest";
    public static final String ID_ELLIPSE = "Ellipse";
    public static final String ID_RECTANGLE = "Rectangle";
    public static final String ID_VB = "StackPane";
    public static final String ID_CIRCLEOVERELLIPSE = "CircleOverEllipse";

    static {
        System.setProperty("prism.lcdtext", "false");
    }

    @Override
    protected String getFirstStageName() {
        return TITLE1;
    }

    @Override
    protected Scene getScene() {
        final Parent content = getContent();
        content.setId(TITLE1);
        return new Scene(content, 400, 400);
    }

    public static enum EventType {

        enter, over, exit, drop
    };
    public static HashMap<EventType, DragEvent> previousEvents = new HashMap();
    public static HashMap<EventType, DragEvent> lastEvents = new HashMap();

    public class RegisteredEvent {

        EventType eventType = null;
        DragEvent event = null;

        RegisteredEvent(EventType _eventType, DragEvent _event) {
            eventType = _eventType;
            event = _event;
        }
    }

    public static void registerEvent(RegisteredEvent _e) {
        EventType et = _e.eventType;
        previousEvents.remove(et);
        DragEvent e = lastEvents.remove(et);
        previousEvents.put(et, e);
        lastEvents.put(et, _e.event);
    }

    private Parent getContent() {
        final Group list = new Group();
        final Rectangle rect = new Rectangle(50, 50, Color.BLUE);
        final VBox vbCommon = new VBox();
        final Pane vb = new StackPane();
        vb.setId(ID_VB);
        list.getChildren().add(vbCommon);
        vbCommon.getChildren().add(vb);
        rect.setId(ID_RECTANGLE);
        rect.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Dragboard db = rect.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello World");
                db.setContent(content);
                t.consume();
            }
        });
        vbCommon.getChildren().addAll(rect);

        final Ellipse ellipse = new Ellipse(120, 40, 20, 30);
        ellipse.setId(ID_ELLIPSE);
        ellipse.setFill(Color.BLUEVIOLET);

        ellipse.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.enter, t));
            }
        });
        ellipse.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.over, t));
                t.acceptTransferModes(TransferMode.ANY);
                ellipse.setFill(Color.AQUA);
            }
        });
        ellipse.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.exit, t));
                t.acceptTransferModes(TransferMode.ANY);
                ellipse.setFill(Color.BLUEVIOLET);
            }
        });
        ellipse.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.drop, t));
                t.setDropCompleted(true);
                ellipse.setFill(Color.CORAL);
                System.out.println("ellipse  DROP   src:" + t.getGestureSource() + "    target:" + t.getGestureTarget());
                t.consume();
            }
        });
        vb.getChildren().add(ellipse);

        final Circle circle = new Circle(10);
        circle.setId(ID_CIRCLEOVERELLIPSE);
        circle.setFill(Color.RED);

        circle.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.enter, t));
                System.out.println("circle onDragEntered");
            }
        });
        circle.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.over, t));
                t.acceptTransferModes(TransferMode.ANY);
                circle.setFill(Color.ROSYBROWN);
            }
        });
        circle.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.exit, t));
                circle.setFill(Color.RED);
            }
        });
        circle.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.drop, t));
                t.setDropCompleted(true);
                circle.setFill(Color.BLACK);
                System.out.println("circle  DROP");
                t.consume();

            }
        });
        vb.getChildren().add(circle);

        vb.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.enter, t));
                System.out.println("vbox  onDragEntered");
                vb.setStyle("-fx-background-color: red;");
            }
        });

        vb.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.over, t));
                if (!t.isAccepted()) {
                    t.acceptTransferModes(TransferMode.ANY);
                }
            }
        });
        vb.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.exit, t));
                t.acceptTransferModes(TransferMode.ANY);
                System.out.println("vbox  onDragExited");
                vb.setStyle("-fx-background-color: white;");
            }
        });

        vb.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent t) {
                registerEvent(new RegisteredEvent(EventType.drop, t));
                t.acceptTransferModes(TransferMode.ANY);
                vb.setStyle("-fx-border-color: yellow;");
                System.out.println("vbox  DROP   src:" + t.getGestureSource() + "    target:" + t.getGestureTarget());
                System.out.println("lastEvents:" + lastEvents.get(EventType.drop)
                        + " / getSrc  " + lastEvents.get(EventType.drop).getGestureSource()
                        + " / getTrgt" + lastEvents.get(EventType.drop).getGestureTarget()
                        + " / " + lastEvents.get(EventType.drop).toString());
                if (null != previousEvents.get(EventType.drop)) {
                    System.out.println("previousEvents:" + previousEvents.get(EventType.drop)
                            + " / getSrc  " + previousEvents.get(EventType.drop).getGestureSource()
                            + " / getTrgt  " + previousEvents.get(EventType.drop).getGestureTarget()
                            + " / " + previousEvents.get(EventType.drop).toString());
                    System.out.println("previousEvents:" + previousEvents.get(EventType.drop));
                }
            }
        });

        return list;
    }

//Commented out, as Interoperability app was implemented.
//    @Override
//    public void start(Stage stage) {
//        stage.setX(100);
//        stage.setY(100);
//        stage.setWidth(200);
//        stage.setHeight(200);
//        stage.setTitle(TITLE1);
//        Scene scene = getScene();
//        stage.setScene(scene);
//        stage.show();
//    }
    public static void clearEvents() {

        previousEvents.clear();
        lastEvents.clear();
    }

    public static void main(String[] args) {
        Utils.launch(ExtendedDragApplication.class, args);
    }
}