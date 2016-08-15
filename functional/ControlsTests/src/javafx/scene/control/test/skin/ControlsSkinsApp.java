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
package javafx.scene.control.test.skin;

import java.lang.reflect.Constructor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.factory.ControlsFactory;
import javafx.factory.NodeFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 *
 * See https://javafx-jira.kenai.com/browse/RT-33520
 */
public class ControlsSkinsApp extends InteroperabilityApp {

    public static final String SELECTED_NODE_ID = "SELECTED_NODE_ID";
    public static final String NODE_CHOOSER_ID = "NODE_CHOOSER_ID";
    public static final String REPLACE_SKIN_BUTTON_ID = "REPLACE_SKIN_BUTTON_ID";
    private final static int sceneSize = 500;
    private final static int paneSize = 400;
    private final static int controlSize = 200;

    public static void main(String[] args) {
        Utils.launch(ControlsSkinsApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "ControlsSkinsApp");
        return new ControlsSkinsScene();
    }

    class ControlsSkinsScene extends Scene {

        StackPane newControlPane = new StackPane();
        Control currentlySelectedControl;

        public ControlsSkinsScene() {
            super(new VBox(), sceneSize, sceneSize);

            newControlPane.setMinSize(paneSize, paneSize);
            newControlPane.setPrefSize(paneSize, paneSize);
            newControlPane.setMaxSize(paneSize, paneSize);
            newControlPane.setAlignment(Pos.CENTER);
            newControlPane.setStyle("-fx-border-color : black;");

            Button skinReplacementButton = new Button("Replace skin");
            skinReplacementButton.setId(REPLACE_SKIN_BUTTON_ID);
            skinReplacementButton.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent t) {
                    if (currentlySelectedControl != null) {
                        final Class<? extends Skin> skinClass = currentlySelectedControl.getSkin().getClass();
                        final Constructor<?>[] constructors = skinClass.getConstructors();
                        Constructor<?> selectedConstructor = constructors[0];
                        if (constructors.length != 1) {
                            for (Constructor<?> c : constructors) {
                                if (c.getParameterCount() == 1) {
                                    selectedConstructor = c;
                                }
                            }
                        }

                        Skin newSkin = null;

                        try {
                            newSkin = (Skin) selectedConstructor.newInstance(currentlySelectedControl);
                        } catch (Throwable ex) {
                            throw new IllegalStateException("Exception on contructor call.", ex);
                        }

                        try {
                            currentlySelectedControl.setSkin(newSkin);
                        } catch (Throwable ex) {
                            throw new IllegalStateException("Skin replacement failed", ex);
                        }
                    }
                }
            });

            ((VBox) getRoot()).getChildren().addAll(createNodeChooser(), newControlPane, skinReplacementButton);
        }

        private Node createNodeChooser() {
            VBox vb = new VBox(5);

            ChoiceBox<NodeFactory> cb = new ChoiceBox<NodeFactory>();
            cb.setId(NODE_CHOOSER_ID);
            cb.getItems().addAll(ControlsFactory.values());

            cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NodeFactory>() {

                @Override
                public void changed(ObservableValue<? extends NodeFactory> ov, NodeFactory t, NodeFactory t1) {
                    final Node createNode = t1.createNode();
                    if (createNode instanceof Control) {
                        currentlySelectedControl = (Control) createNode;
                        currentlySelectedControl.setId(SELECTED_NODE_ID);
                        currentlySelectedControl.setMinSize(controlSize, controlSize);
                        currentlySelectedControl.setPrefSize(controlSize, controlSize);
                        currentlySelectedControl.setMaxSize(controlSize, controlSize);
                        newControlPane.getChildren().clear();
                        newControlPane.getChildren().add(currentlySelectedControl);
                    } else {
                        System.out.println("Nothing");
                    }
                }
            });

            vb.getChildren().addAll(new Label("Choose tested control : "), cb);
            return vb;
        }
    }
}
