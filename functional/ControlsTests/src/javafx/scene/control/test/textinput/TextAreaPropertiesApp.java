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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class TextAreaPropertiesApp extends TextControlApp {
    public static void main(String[] args) {
        Utils.launch(TextAreaPropertiesApp.class, args);
    }

    @Override
    protected Scene getScene() {
        setTitle();
        return new TextAreaScene();
    }

    public class TextAreaScene extends TextInputScene {

        @Override
        public void setNewControl() {
            testedTextInput = new TextArea();
        }

        @Override
        public void resetControl() {
            TextArea fresh = new TextArea();
            TextArea tested = (TextArea) testedTextInput;
            tested.setPrefWidth(fresh.getPrefWidth());
            tested.setPrefHeight(fresh.getPrefHeight());
            tested.setDisable(fresh.isDisable());
            tested.setEditable(fresh.isEditable());
            tested.setPrefColumnCount(fresh.getPrefColumnCount());
            tested.setPrefRowCount(fresh.getPrefRowCount());
            tested.setText(fresh.getText());
            tested.setFocusTraversable(fresh.isFocusTraversable());
            tested.setWrapText(fresh.isWrapText());
        }

        @Override
        public void addControlSpecificButtons(Pane pane) {
            Button button = ButtonBuilder.create().id(ADD_TEXT_BUTTON_ID).text("Add much text").build();
            button.setOnAction(new EventHandler() {

                public void handle(Event t) {
                    for (int i = 0; i < 1000; i++) {
                        TextArea ta = (TextArea) testedTextInput;
                        ta.setText(ta.getText() + " some text");
                        if (i % 10 == 0) {
                            ta.setText(ta.getText() + "\n");
                        }
                    }
                }
            });
            pane.getChildren().add(button);
        }

        @Override
        protected void addControlDependentProperties() {
            TextArea textArea = (TextArea) testedTextInput;
            tb.addSimpleListener(textArea.focusedProperty(), textArea);
        }
    }
}
