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
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class KeyboardInputApp extends Application {
            public static final String ALT = "ALT+";
            public static final String CONTROL = "CONTROL+";
            public static final String META = "META+";
            public static final String PUSHED = "PUSHED ";
            public static final String PRESSED = "PRESSED ";
            public static final String RELEASED = "RELEASED ";
            public static final String SHIFT = "SHIFT+";
            public static final String TYPED = "TYPED ";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        final TextArea lbl = new TextArea();
        lbl.setId("events");
        final TextArea txt = new TextArea();
        txt.setId("text");
        txt.addEventHandler(KeyEvent.ANY, t -> {
            if (t.getEventType().equals(KeyEvent.KEY_TYPED)) {
                if (!t.isAltDown() && !t.isControlDown() && !t.isMetaDown()) {
                    lbl.appendText(TYPED + t.getCharacter() + "\n");
                    System.out.println((int) t.getCharacter().charAt(0));
                }
            } else if (t.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                    StringBuilder sb = new StringBuilder(PUSHED);
                    if (t.isAltDown()) {
                        sb.append(ALT);
                    }
                    if (t.isShiftDown()) {
                        sb.append(SHIFT);
                    }
                    if (t.isControlDown()) {
                        sb.append(CONTROL);
                    }
                    if (t.isMetaDown()) {
                        sb.append(META);
                    }
                    sb.append(t.getCode().name());
                    lbl.appendText(sb.toString() + "\n");
            }
        });
        Button clean = new Button("clean");
        clean.addEventHandler(ActionEvent.ACTION, t -> {
            System.out.println("lala");
            lbl.clear();
            txt.clear();
            txt.requestFocus();
        });
        root.getChildren().add(clean);
        root.getChildren().add(txt);
        root.getChildren().add(lbl);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(400);
        stage.setHeight(400);
        stage.setX(200);
        stage.setY(200);
        stage.show();
    }
}
