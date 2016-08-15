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
package org.jemmy.samples.lookup;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This small FX app is used in lookup JemmyFX samples. It displays a few labels
 * with different text and, should the mouse be place over a label, displays the
 * label's text at the bottom of the stage.
 * @author shura
 */
public class LookupApp extends Application {

    static String mouseOverText = "";

    @Override
    public void start(Stage stage) throws Exception {
        final Label mouseOverLabel = new Label();
        EventHandler<MouseEvent> mouseOverHandler = t -> {
            if(t.getSource() instanceof Label) {
                mouseOverText = Label.class.cast(t.getSource()).getText();
                mouseOverLabel.setText(mouseOverText);
            }
        };
        GridPane buttonGrid = new GridPane();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label lbl = new Label("(" + i + "," + j + ")");
                lbl.setId("lbl_" + i + j);
                lbl.addEventHandler(MouseEvent.MOUSE_MOVED, mouseOverHandler);
                buttonGrid.add(lbl, i, j);
            }
        }
        VBox content = new VBox();
        content.getChildren().add(buttonGrid);
        content.getChildren().add(mouseOverLabel);
        Scene scene = new MyScene(content);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    class MyScene extends Scene {

        public MyScene(Parent parent) {
            super(parent);
        }
    }
}
