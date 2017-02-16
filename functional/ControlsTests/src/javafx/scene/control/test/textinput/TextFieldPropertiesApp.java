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
package javafx.scene.control.test.textinput;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static javafx.scene.control.test.textinput.TextControlApp.ADD_TEXT_BUTTON_ID;
import javafx.scene.layout.Pane;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class TextFieldPropertiesApp extends TextControlApp {
    public final static String SET_ON_ACTION_BUTTON_ID = "SET_ON_ACTION_BUTTON_ID";
    public final static String ON_ACTION_COUNTER = "ON_ACTION_COUNTER";

    public static void main(String[] args) {
        Utils.launch(TextFieldPropertiesApp.class, args);
    }

    @Override
    protected Scene getScene() {
        setTitle();
        return new TextFieldScene();
    }

    public class TextFieldScene extends TextInputScene {

        @Override
        public void setNewControl() {
            testedTextInput = new TextField();
        }

        @Override
        protected void addControlDependentProperties() {
            tb.addCounter(ON_ACTION_COUNTER);
        }

        @Override
        public void resetControl() {
            TextField fresh = new TextField();
            TextField tested = (TextField) testedTextInput;
            tested.setPrefWidth(fresh.getPrefWidth());
            tested.setPrefHeight(fresh.getPrefHeight());
            tested.setDisable(fresh.isDisable());
            tested.setEditable(fresh.isEditable());
            tested.setPrefColumnCount(fresh.getPrefColumnCount());
            tested.setText(fresh.getText());
            tested.setPromptText(fresh.getPromptText());
            tested.setFocusTraversable(fresh.isFocusTraversable());
        }

        @Override
        public void addControlSpecificButtons(Pane pane) {
            Button setOnActionListenerButton = new Button("Set on action listener");
            setOnActionListenerButton.setId(SET_ON_ACTION_BUTTON_ID);
            setOnActionListenerButton.setOnAction(new EventHandler() {

                public void handle(Event t) {
                    ((TextField) testedTextInput).onActionProperty().setValue(new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent t) {
                            tb.incrementCounter(ON_ACTION_COUNTER);
                        }
                    });
                }
            });

            Button addTextButton = new Button("Add \"sometext\"");
            addTextButton.setId(ADD_TEXT_BUTTON_ID);
            addTextButton.setOnAction(new EventHandler() {

                public void handle(Event t) {
                    testedTextInput.setText(testedTextInput.getText() + "some\ntext");
                }
            });

            pane.getChildren().addAll(setOnActionListenerButton, addTextButton);
        }
    }
}