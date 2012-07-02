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
 * questions.
 */
package org.jemmy.samples.text;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is a sample app to demonstrate Jemmy functionality. It provides a few
 * text controls to type in.
 *
 * @author shura
 */
public class TextApp extends Application {

    Text text;
    TextField singleLine;
    TextArea multiLine;

    @Override
    public void start(Stage stage) throws Exception {
        text = new Text();
        EventHandler<KeyEvent> textUpdate = new EventHandler<KeyEvent>() {

            public void handle(final KeyEvent t) {
                Platform.runLater(new Runnable() {

                    public void run() {
                        updateText((TextInputControl) t.getSource());
                    }
                });
            }
        };
        singleLine = new TextField();
        singleLine.addEventHandler(KeyEvent.KEY_TYPED, textUpdate);
        singleLine.setId("single");
        multiLine = new TextArea();
        multiLine.addEventHandler(KeyEvent.KEY_TYPED, textUpdate);
        reset();
        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                reset();
            }
        });
        VBox content = new VBox();
        content.getChildren().add(reset);
        content.getChildren().addAll(singleLine, multiLine, text);
        stage.setScene(new Scene(content));
        stage.show();
    }

    private void updateText(TextInputControl textInputControl) {
        text.setText("uneditable text:\n" + textInputControl.getText());
    }

    private void reset() {
        singleLine.setText("single line text");
        multiLine.setText("multi\nline\ntext\n");
        updateText(multiLine);
    }

    public static void main(String[] args) {
        launch();
    }
}
