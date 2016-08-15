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
package javafx.scene.control.test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class RichTextEditorApp extends InteroperabilityApp {

    public final static String TEST_PANE_ID = "TEST_PANE_ID";
    public final static String RESET_BUTTON_ID = "RESET_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(RichTextEditorApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "HTMLEditorTestApp");
        return new MenuBarAppScene();
    }

    public class MenuBarAppScene extends Scene {

        static final double width = 800, height = 680;
        HTMLEditor editor;

        public MenuBarAppScene() {
            super(new VBox(5), width + 10, height + 30);

            Utils.addBrowser(this);

            getRoot().setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent ke) {
                    if (ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.H) {
                        System.out.println(((HTMLEditor) content.getChildren().get(0)).getHtmlText());
                    }
                }
            });

            content = new VBox();
            content.setId(TEST_PANE_ID);
            content.setPrefSize(width, height);
            content.setMinSize(width, height);
            content.setMaxSize(width, height);

            reset();

            Button clearBtn = new Button("Reset");
            clearBtn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    reset();
                }
            });
            clearBtn.setId(RESET_BUTTON_ID);

            Button flushButton = new Button("Flush");
            flushButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    System.out.println(editor.getHtmlText());
                }
            });

            ((VBox) getRoot()).getChildren().addAll(content, clearBtn, flushButton);
        }

        private void reset() {
            content.getChildren().clear();
            editor = new HTMLEditor();
            content.getChildren().add(editor);
        }
        private VBox content;
    }
}