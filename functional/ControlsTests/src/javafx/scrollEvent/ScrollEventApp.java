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
package javafx.scrollEvent;

import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.factory.ControlsFactory;
import javafx.factory.NodeFactory;
import javafx.factory.Panes;
import javafx.factory.Shapes;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
import static org.junit.Assert.*;

/**
 *
 * @author andrey
 * @author Alexander Kirov
 */
public class ScrollEventApp extends InteroperabilityApp {

    public static final String ID_NODE_CHOOSER = "nodeChooser";
    public static final String ID_TARGET_NODE = "target";
    public static final String EVENT_COME_INDICATOR_TEXT_FIELD_ID = "EVENT_COME_INDICATOR_TEXT_FIELD_ID";
    public static final String RESET_BUTTON_ID = "RESET_BUTTON_ID";
    public static final String COME_EVENT_INDICATION = "Come!";
    public static final String CONTEXT_MENU_ID = "CONTEXT_MENU_ID";
    public static final String ENABLE_CONTEXT_MENU_CHECK_BOX_ID = "CONTEXT_MENU_CHECK_BOX_ID";

    private VBox spaceForNode;
    private FlowPane spaceForListeners;
    private HashMap<String, TextField> hm;
    private ContextMenu contextMenu;
    private CheckBox enableContextMenuTest;

    private static String LISTENER_TEXT_FIELD_SUFFIX = "_LISTENER_TEXT_FIELD_ID";

    public static void main(String[] args) {
        Utils.launch(ScrollEventApp.class, args);
    }

    public static String getContextMenuOnShownCounterID() {
        return getListenerTextFieldID(Options.contextMenuOnShown.toString());
    }

    @Override
    protected Scene getScene() {
        Scene scene = new Scene(getContent(), 700, 300);
        return scene;
    }

    private Node createNodeChooser() {
        VBox vb = new VBox(5);
        Label scrollEventCame = new Label("Scroll event came : ");
        final TextField eventComeIndicator = TextFieldBuilder.create().text("None").id(EVENT_COME_INDICATOR_TEXT_FIELD_ID).build();
        HBox hb = new HBox();
        hb.getChildren().addAll(scrollEventCame, eventComeIndicator);

        Button resetButton = ButtonBuilder.create().text("Reset").id(RESET_BUTTON_ID).build();
        resetButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                eventComeIndicator.setText("None");
                clearListenersState();
            }
        });

        ChoiceBox<NodeFactory> cb = new ChoiceBox<NodeFactory>();
        cb.setId(ID_NODE_CHOOSER);
        cb.getItems().addAll(ControlsFactory.filteredValues());
        cb.getItems().addAll(Shapes.values());
        cb.getItems().addAll(Panes.values());

        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NodeFactory>() {

            @Override
            public void changed(ObservableValue<? extends NodeFactory> ov, NodeFactory t, NodeFactory t1) {
                final Node node = t1.createNode();

                if (!enableContextMenuTest.isSelected()) {
                    node.setOnScroll(new EventHandler<ScrollEvent>() {

                        public void handle(ScrollEvent t) {
                            eventComeIndicator.setText(COME_EVENT_INDICATION);

                            renewListenerValue("deltaX", t.getDeltaX());
                            renewListenerValue("deltaY", t.getDeltaY());
                            renewListenerValue("textDeltaX", t.getTextDeltaX());
                            renewListenerValue("textDeltaY", t.getTextDeltaY());
                            renewListenerValue("textDeltaXUnits", t.getTextDeltaXUnits());
                            renewListenerValue("textDeltaYUnits", t.getTextDeltaYUnits());
                            renewListenerValue("eventType", t.getEventType());
                            renewListenerValue("consumed", t.isConsumed());
                            renewListenerValue("x", t.getX());
                            renewListenerValue("y", t.getY());
                        }
                    });
                } else {
                    node.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                        public void handle(ContextMenuEvent t) {
                            renewListenerValue("eventType", t.getEventType());
                            renewListenerValue("consumed", t.isConsumed());
                            renewListenerValue("x", t.getX());
                            renewListenerValue("y", t.getY());
                            getContextMenu().show(node, t.getScreenX(), t.getScreenY());
                            t.consume();
                        }
                    });
                }

                node.setId(ID_TARGET_NODE);
                spaceForNode.getChildren().clear();
                spaceForNode.getChildren().add(node);

                clearListenersState();
            }
        });

        vb.getChildren().addAll(new Label("Choose tested control : "), cb, resetButton, hb);
        return vb;
    }

    private Parent getContent() {
        hm = new HashMap<String, TextField>();

        spaceForNode = new VBox();
        spaceForNode.setAlignment(Pos.CENTER);
        spaceForNode.setPrefWidth(300);
        spaceForNode.setPrefHeight(300);

        spaceForListeners = new FlowPane();
        spaceForListeners.setPrefHeight(300);
        spaceForListeners.setPrefWidth(300);
        for (Options s : Options.values()) {
            spaceForListeners.getChildren().add(getListener(s.toString()));
        }

        enableContextMenuTest = new CheckBox("Context menu test");
        enableContextMenuTest.setId(ENABLE_CONTEXT_MENU_CHECK_BOX_ID);

        VBox controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().add(enableContextMenuTest);
        controls.getChildren().add(createNodeChooser());

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(spaceForNode, spaceForListeners, controls);
        return hBox;
    }

    private HBox getListener(String name) {
        HBox hb = new HBox();
        Label label = new Label(name + " : ");
        TextField tf = TextFieldBuilder.create().id(getListenerTextFieldID(name)).build();
        hm.put(name, tf);

        hb.getChildren().addAll(label, tf);
        return hb;
    }

    private void renewListenerValue(String name, Object newValue) {
        TextField tf = hm.get(name);
        if (tf != null) {
            tf.setText(newValue.toString());
        }
    }

    private void clearListenersState() {
        for (TextField tf : hm.values()) {
            tf.setText("");
        }
    }

    private void initContextMenu() {
        contextMenu = new ContextMenu();
        contextMenu.setId(CONTEXT_MENU_ID);
        for(int i = 1; i <= 5; i++) {
            contextMenu.getItems().add(new MenuItem("item - " + i));
        }

        contextMenu.setOnShown(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent t) {
                TextField tfCounter = hm.get(Options.contextMenuOnShown.toString());
                assertTrue(tfCounter != null);

                String text = tfCounter.getText();
                if (text.equals("")) {
                    tfCounter.setText("1");
                } else {
                    int val = Integer.parseInt(text);
                    val++;
                    tfCounter.setText(String.valueOf(val));
                }
            }
        });
    }

    private ContextMenu getContextMenu() {
        if (contextMenu == null ) {
            initContextMenu();
        }
        return contextMenu;
    }

    private static String getListenerTextFieldID(String name) {
        return name.toUpperCase() + LISTENER_TEXT_FIELD_SUFFIX;
    }

    private enum Options {
        eventType, consumed, deltaX, deltaY, textDeltaXUnits, textDeltaX, textDeltaYUnits, textDeltaY, x, y,
        contextMenuOnShown
    };
}
