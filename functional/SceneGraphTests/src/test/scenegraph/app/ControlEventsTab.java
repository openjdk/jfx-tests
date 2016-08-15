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

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import test.scenegraph.app.ControlEventsApp.EventTypes;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ControlEventsTab extends Tab
{

    public ControlEventsTab(String text, Control control, List<Class<?>> eventsDeclaringClasses)
    {
        super(text);
        this.control = control;
        control.setTooltip(dragTooltip);
        this.eventsDeclaringClasses = eventsDeclaringClasses;
        init();
    }

    private void init()
    {
        ScrollPane sp = new ScrollPane();
        final GridPane gp = new GridPane();

        ColumnConstraints activeArea = new ColumnConstraints();
        activeArea.setPercentWidth(65);
        ColumnConstraints eventTypesList = new ColumnConstraints();
        eventTypesList.setPercentWidth(35);
        RowConstraints chiefControl = new RowConstraints();
        chiefControl.setPercentHeight(65);
        RowConstraints helperControls = new RowConstraints();
        helperControls.setPercentHeight(35);
        gp.getColumnConstraints().addAll(activeArea, eventTypesList);
        gp.getRowConstraints().addAll(chiefControl, helperControls);

        StackPane controlPane = new StackPane();
        controlPane.setPadding(new Insets(ControlEventsApp.INSETS));
        controlPane.getChildren().add(control);

        GridPane.setHalignment(controlPane, HPos.CENTER);
        gp.add(controlPane, 0, 0);
        eventTypes = new VBox(1);
        final ToggleGroup eventSwitch = new ToggleGroup();

        //List<String> fieldNames = new LinkedList<String>();
        List<Object> events = new LinkedList<Object>();
            for(Class<?> eventClass: eventsDeclaringClasses)
            {
                for(Field field: eventClass.getDeclaredFields())
                {
                    //fieldNames.add(field.getName());
                    try
                    {
                        if(!field.isAccessible())
                        {
                            field.setAccessible(true);
                        }
                        if(field.getType().equals(EventType.class))
                        {
                            events.add(field.get(eventClass));
                        }
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }

        for(EventTypes et: EventTypes.values())
        {

            if(!events.contains(et.getType()))
            {
                continue;
            }
            final RadioButton rb = new RadioButton(et.toString());
            eventTypes.getChildren().add(rb);
            rb.setToggleGroup(eventSwitch);

            //eventSwitch.getSelectedToggle().
            rb.setStyle(NOT_HANDLED_STYLE);
            rb.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    EventType type = EventTypes.valueOf(rb.getText()).getType();
                    if(!type.getName().endsWith("_TARGET"))
                    {
                        control.addEventHandler(type, new EventHandler<Event>() {

                            public void handle(Event t) {
                                System.out.println(t.getEventType() + " handled by " + control.
                                        getClass().getSimpleName());

                                rb.setStyle(HANDLED_STYLE);
                                control.removeEventHandler(t.getEventType(), this);
                            }
                        });
                    }
                    else
                    {
                        control.getScene().addEventHandler(type, new EventHandler<Event>() {

                            public void handle(Event t) {
                                if(t.getTarget().equals(control))
                                {
                                    System.out.println(t.getEventType() + " handled by " + control.
                                            getScene().getClass().getSimpleName());

                                    rb.setStyle(HANDLED_STYLE);
                                    control.getScene().removeEventHandler(t.getEventType(), this);
                                }
                            }
                        });
                    }
                }
            });
        }
        sp.setContent(eventTypes);
        gp.add(sp, 1, 0, 1, 2);

        resetButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {

                dragTooltip.setText(ControlEventsTab.DRAG_WAIT);
                dragTarget.setText("");

                final RadioButton trb = (RadioButton)eventSwitch.getSelectedToggle();
                trb.setSelected(false);

                for(Node n: eventTypes.getChildren())
                {
                    n.setStyle(NOT_HANDLED_STYLE);

                    //dragTooltip.setText(ControlEventsTab.DRAG_WAIT);
                    eventSwitch.selectToggle(null);
                    //dragTarget.setText("");
                }
            }
        });

        FlowPane fp = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        fp.getChildren().addAll(dragField, dragTarget, resetButton);
        GridPane.setHalignment(fp, HPos.CENTER);
        gp.add(fp, 0, 1);

        dragField.setOnDragDetected(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                final Dragboard db = dragField.startDragAndDrop(TransferMode.ANY);
                final ClipboardContent content = new ClipboardContent();
                content.putString(dragField.getText());
                db.setContent(content);
                event.consume();
            }
        });

        control.setOnDragDetected(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                final Dragboard db = control.startDragAndDrop(TransferMode.ANY);
                final ClipboardContent content = new ClipboardContent();
                content.putString(control.getClass().getSimpleName());
                db.setContent(content);
                event.consume();
            }
        });

        control.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });

        control.setOnDragDropped(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    dragTooltip.setText(db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
            }
        });

        dragTarget.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });

        dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    dragTarget.setText(db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
            }
        });

        gp.addEventHandler(MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {

            public void handle(MouseEvent arg0) {
                gp.startFullDrag();
            }
        });

        gp.setAlignment(Pos.CENTER);
        setContent(gp);
        setClosable(false);

        setId(getText());
        control.setId(ControlEventsApp.CONTROL_ID);
        for(Node rb: eventTypes.getChildren())
        {
            rb.setId(((RadioButton) rb).getText());
        }
        dragField.setId(ControlEventsApp.DRAG_FIELD_ID);
        dragTarget.setId(ControlEventsApp.DRAG_TARGET_ID);

    }

    private Control control;
    private VBox eventTypes;
    private List<Class<?>> eventsDeclaringClasses;
    private TextField dragField = new TextField("Drag text");
    private TextField dragTarget = new TextField();
    private Tooltip dragTooltip = new Tooltip(DRAG_WAIT);
    private Button resetButton = new Button("Reset");

    public static final String NOT_HANDLED_STYLE = "-fx-text-fill: red;";
    public static final String HANDLED_STYLE = "-fx-text-fill: green;";
    public static final String DRAG_WAIT = "Waiting for a drug";

}
