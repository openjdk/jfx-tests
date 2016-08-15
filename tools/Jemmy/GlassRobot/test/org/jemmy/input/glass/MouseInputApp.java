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
package org.jemmy.input.glass;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.text.DecimalFormat;

/**
 *
 * @author shura
 */
public class MouseInputApp extends Application {

    public static final String CLICKED = "CLICKED ";
    public static final String DRAGGED = "DRAGGED ";
    public static final String MOVED = "MOVED ";
            public static final String SCROLL = "SCROLL ";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        final TextArea lbl = new TextArea();
        lbl.setId("events");
        final Label txt = new Label();
        txt.setText("mouse target");
        txt.setId("text");
        txt.getTransforms().add(new Scale(3, 3, 0, 0));
        Button clean = new Button("clean");
        clean.addEventHandler(ActionEvent.ACTION, t -> {
            lbl.clear();
            txt.requestFocus();
        });
        final CheckBox ignoreMotion = new CheckBox("ignore motion");
        root.getChildren().add(clean);
        root.getChildren().add(ignoreMotion);
        root.getChildren().add(lbl);
        root.getChildren().add(txt);
        txt.addEventHandler(MouseEvent.ANY, t -> {
            if (!ignoreMotion.isSelected()
                    || !(t.getEventType().equals(MouseEvent.MOUSE_MOVED)
                    || t.getEventType().equals(MouseEvent.MOUSE_DRAGGED))) {
                StringBuilder sb = new StringBuilder();
                if (t.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                    sb.append(KeyboardInputApp.PRESSED);
                } else if (t.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                    sb.append(KeyboardInputApp.RELEASED);
                } else if (t.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                    sb.append(CLICKED);
                } else if (t.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
                    sb.append(MOVED);
                    System.out.println(t.getClass().getName());
                } else if (t.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
                    sb.append(DRAGGED);
                } else {
                    return;
                }
                sb.append(coords(t.getX(), t.getY()));
                if (t.isAltDown()) {
                    sb.append(KeyboardInputApp.ALT);
                }
                if (t.isShiftDown()) {
                    sb.append(KeyboardInputApp.SHIFT);
                }
                if (t.isControlDown()) {
                    sb.append(KeyboardInputApp.CONTROL);
                }
                if (t.isMetaDown()) {
                    sb.append(KeyboardInputApp.META);
                }
                sb.append(t.getButton()).append(" ").append(t.getClickCount()).append("\n");
                lbl.appendText(sb.toString());
            }
        });
        txt.addEventHandler(ScrollEvent.ANY, t -> {
                StringBuilder sb = new StringBuilder();
                if (t.getEventType().equals(ScrollEvent.SCROLL)) {
                    sb.append(SCROLL);
                } else {
                    return;
                }
                sb.append(coords(t.getDeltaX(), t.getDeltaY()));
                if (t.isAltDown()) {
                    sb.append(KeyboardInputApp.ALT);
                }
                if (t.isShiftDown()) {
                    sb.append(KeyboardInputApp.SHIFT);
                }
                if (t.isControlDown()) {
                    sb.append(KeyboardInputApp.CONTROL);
                }
                if (t.isMetaDown()) {
                    sb.append(KeyboardInputApp.META);
                }
                sb.append("\n");
                lbl.appendText(sb.toString());
        });
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(400);
        stage.setHeight(400);
        stage.setX(200);
        stage.setY(200);
        stage.show();
    }

    private static final DecimalFormat df = new DecimalFormat("#");
    static String coords(double x, double y) {
        return "(" + df.format(x) + "," + df.format(y) + ") ";
    }
}
