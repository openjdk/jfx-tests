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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class SceneEventHandlersApp extends InteroperabilityApp
{

    public static void main(String... args)
    {
        Utils.launch(SceneEventHandlersApp.class, args);
        //System.out.println("fx: " + VersionInfo.getRuntimeVersion());
    }

    @Override
    protected Scene getScene()
    {
        eventCombo.setId(EVENTS_COMBO_ID);
        actionButton.setId(ACTION_BUTTON_ID);
        dragField.setId(DRAG_SOURCE_ID);
        dropField.setId(DROP_TARGET_ID);

        eventCombo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                EventType<?> type = eventCombo.getValue().getType();
                scene.addEventHandler(type, new TestHandler(type));
            }
        });

        eventCombo.setItems(FXCollections.observableArrayList(EventTypes.values()));

        dragField.setOnDragDetected(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                final Dragboard db = dragField.startDragAndDrop(TransferMode.ANY);
                final ClipboardContent content = new ClipboardContent();
                content.putString(dragField.getText());
                db.setContent(content);
                event.consume();
            }
        });

//        dragField.setOnDragExited(new EventHandler<DragEvent>() {
//
//            public void handle(DragEvent arg0) {
//                System.out.println("Drag exited drag source.");
//            }
//        });

        dropField.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY);
            }
        });

        dropField.setOnDragDropped(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    dropField.setText(db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
            }
        });

        for(EventTypes t: EventTypes.values())
        {
            Label lb = new Label(t.toString());
            lb.setId(t.toString());
            lb.setStyle(NOT_HANDLED_STYLE);
            controlLabels.add(lb);
        }

        BorderPane root = new BorderPane();
        FlowPane flow = new FlowPane(Orientation.VERTICAL);
        flow.setVgap(30);
        flow.setHgap(1);
        flow.setPadding(new Insets(10, 10, 10, 10));
        flow.getChildren().addAll(actionButton, dragField, dropField);
        root.setTop(eventCombo);
        root.setCenter(flow);
        VBox temp = new VBox();
        temp.getChildren().addAll(controlLabels);
        temp.setPadding(new Insets(10, 10, 10, 10));
        root.setLeft(temp);
        scene = new Scene(root);

        scene.addEventHandler(MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {

            public void handle(MouseEvent arg0) {
                //System.out.println("startFullDrag handled");
                scene.startFullDrag();
            }
        });

        return scene;
    }

    public Map<EventType<Event>, Boolean> getResults()
    {
        return handled;
    }

    private ComboBox<EventTypes> eventCombo = new ComboBox<EventTypes>();
    private Button actionButton = new Button("Action");
    private List<Label> controlLabels = new ArrayList<Label>();
    private TextField dragField = new TextField("Drag source");
    private TextField dropField = new TextField("Drop target");
    private Scene scene;

    private Map<EventType<Event>, Boolean> handled = new HashMap<EventType<Event>, Boolean>();

    public static final String NOT_HANDLED_STYLE = "-fx-text-fill: red;";
    public static final String HANDLED_STYLE = "-fx-text-fill: green;";
    public static final String EVENTS_COMBO_ID = "events_combo";
    public static final String ACTION_BUTTON_ID = "action_button";
    public static final String DRAG_SOURCE_ID = "drag_field";
    public static final String DROP_TARGET_ID = "drop_field";

    public static enum EventTypes
    {

        ACTION(ActionEvent.ACTION),
        CONTEXT_MENU_REQUESTED(ContextMenuEvent.CONTEXT_MENU_REQUESTED),
        DRAG_DONE(DragEvent.DRAG_DONE),
        DRAG_DROPPED(DragEvent.DRAG_DROPPED),
        DRAG_ENTERED(DragEvent.DRAG_ENTERED),
        DRAG_ENTERED_TARGET(DragEvent.DRAG_ENTERED_TARGET),
        DRAG_EXITED(DragEvent.DRAG_EXITED),
        DRAG_EXITED_TARGET(DragEvent.DRAG_EXITED_TARGET),
        DRAG_OVER(DragEvent.DRAG_OVER),
        //INPUT_METHOD_TEXT_CHANGED(InputMethodEvent.INPUT_METHOD_TEXT_CHANGED),
        KEY_PRESSED(KeyEvent.KEY_PRESSED),
        KEY_RELEASED(KeyEvent.KEY_RELEASED),
        KEY_TYPED(KeyEvent.KEY_TYPED),
        DRAG_DETECTED(MouseEvent.DRAG_DETECTED),
        MOUSE_CLICKED(MouseEvent.MOUSE_CLICKED),
        MOUSE_DRAGGED(MouseEvent.MOUSE_DRAGGED),
        MOUSE_ENTERED(MouseEvent.MOUSE_ENTERED),
        MOUSE_ENTERED_TARGET(MouseEvent.MOUSE_ENTERED_TARGET),
        MOUSE_EXITED(MouseEvent.MOUSE_EXITED),
        MOUSE_EXITED_TARGET(MouseEvent.MOUSE_EXITED_TARGET),
        MOUSE_MOVED(MouseEvent.MOUSE_MOVED),
        MOUSE_PRESSED(MouseEvent.MOUSE_PRESSED),
        MOUSE_RELEASED(MouseEvent.MOUSE_RELEASED),
        MOUSE_DRAG_ENTERED(MouseDragEvent.MOUSE_DRAG_ENTERED),
        MOUSE_DRAG_ENTERED_TARGET(MouseDragEvent.MOUSE_DRAG_ENTERED_TARGET),
        MOUSE_DRAG_EXITED(MouseDragEvent.MOUSE_DRAG_EXITED),
        MOUSE_DRAG_EXITED_TARGET(MouseDragEvent.MOUSE_DRAG_EXITED_TARGET),
        MOUSE_DRAG_OVER(MouseDragEvent.MOUSE_DRAG_OVER),
        MOUSE_DRAG_RELEASED(MouseDragEvent.MOUSE_DRAG_RELEASED),
        SCROLL(ScrollEvent.SCROLL),
        //MEDIA_ERROR(MediaErrorEvent.MEDIA_ERROR),
        //ALERT(WebEvent.ALERT),
        //RESIZED(WebEvent.RESIZED),
        //TATUS_CHANGED(WebEvent.STATUS_CHANGED),
        //VISIBILITY_CHANGED(WebEvent.VISIBILITY_CHANGED),
        //WINDOW_CLOSE_REQUEST(WindowEvent.WINDOW_CLOSE_REQUEST),
        //WINDOW_HIDDEN(WindowEvent.WINDOW_HIDDEN),
        //WINDOW_HIDING(WindowEvent.WINDOW_HIDING),
        //WINDOW_SHOWING(WindowEvent.WINDOW_SHOWING),
        //WINDOW_SHOWN(WindowEvent.WINDOW_SHOWN),
        //WORKER_STATE_CANCELLED(WorkerStateEvent.WORKER_STATE_CANCELLED),
        //WORKER_STATE_FAILED(WorkerStateEvent.WORKER_STATE_FAILED),
        //WORKER_STATE_READY(WorkerStateEvent.WORKER_STATE_READY),
        //WORKER_STATE_RUNNING(WorkerStateEvent.WORKER_STATE_RUNNING),
        //WORKER_STATE_SCHEDULED(WorkerStateEvent.WORKER_STATE_SCHEDULED),
        //WORKER_STATE_SUCCEEDED(WorkerStateEvent.WORKER_STATE_SUCCEEDED)
        ;

        private EventTypes(EventType<? extends Event> type)
        {
            this.type = type;
        }

        public EventType<? extends Event> getType()
        {
            return type;
        }

        public static EventTypes get(EventType<? extends Event> type)
        {
            for(EventTypes t: EventTypes.values())
            {
                if(t.type.equals(type))
                {
                    return t;
                }
            }
            return null;
        }

        private EventType<? extends Event> type;

    }

    private class TestHandler<T extends EventType<Event>> implements EventHandler<Event>
    {

        public TestHandler(T eventType)
        {
            this.eventType = eventType;
            handled.put(eventType, Boolean.FALSE);
        }

        @Override
        public void handle(Event event)
        {
            handled.put(eventType, Boolean.TRUE);
            System.out.println(event + " handled.");
            controlLabels.get(EventTypes.get(event.getEventType()).ordinal()).setStyle(HANDLED_STYLE);
            scene.removeEventHandler(event.getEventType(), this);
        }

        private EventType<Event> eventType;

    }

}
