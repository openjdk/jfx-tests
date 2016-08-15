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
package org.jemmy.samples.input;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This small FX app is used in input JemmyFX samples. It displays two mouse
 * targets at the bottom: red and blue squares. Should a mouse event happen on
 * any of the targets or a key event happen anywhere on the stage, details of
 * the event is shown in the list.
 * @author shura
 *
 */
public class InputApp extends Application {

    public static final String ALT = "ALT+";
    public static final String CONTROL = "CONTROL+";
    public static final String META = "META+";
    public static final String PRESSED = "PRESSED ";
    public static final String RELEASED = "RELEASED ";
    public static final String SHIFT = "SHIFT+";
    public static final String TYPED = "TYPED ";
    public static final String CLICKED = "CLICKED ";
    public static final String DRAGGED = "DRAGGED ";
    public static final String MOVED = "MOVED ";
    public static final String SCROLL = "SCROLL ";
    public static final List<Event> events = Collections.synchronizedList(new LinkedList<>());

    public static void main(String[] args) {

        launch();

    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox root = new VBox();

        final TextArea lbl = new TextArea();

        lbl.setId("events");

        final Rectangle target1 = new Rectangle(100, 100);

        target1.setFill(Color.BLUE);

        target1.setId("BLUE");

        final Rectangle target2 = new Rectangle(100, 100);

        target2.setFill(Color.RED);

        target2.setId("RED");

        Button clean = new Button("clean");

        clean.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {

                lbl.clear();

                events.clear();

            }
        });

        final CheckBox ignoreMotion = new CheckBox("ignore motion");

        root.getChildren().add(clean);

        root.getChildren().add(ignoreMotion);

        root.getChildren().add(lbl);

        HBox targets = new HBox();

        targets.getChildren().add(target1);

        targets.getChildren().add(target2);

        root.getChildren().add(targets);

        EventHandler<MouseEvent> mouseHandler = t -> {

            if (!ignoreMotion.isSelected()
                    || !(t.getEventType().equals(MouseEvent.MOUSE_MOVED)
                    || t.getEventType().equals(MouseEvent.MOUSE_DRAGGED))) {

                events.add(t);

                StringBuilder sb = new StringBuilder(((Node) t.getSource()).getId() + " ");

                if (t.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {

                    sb.append(PRESSED);

                } else if (t.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {

                    sb.append(RELEASED);

                } else if (t.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {

                    sb.append(CLICKED);

                } else if (t.getEventType().equals(MouseEvent.MOUSE_MOVED)) {

                    sb.append(MOVED);

                } else if (t.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {

                    sb.append(DRAGGED);

                } else {

                    return;

                }

                sb.append(coords(t.getX(), t.getY()));

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

                sb.append(t.getButton()).append(" ").append(t.getClickCount()).append("\n");

                lbl.appendText(sb.toString());

            }

        };

        target1.addEventHandler(MouseEvent.ANY, mouseHandler);

        target2.addEventHandler(MouseEvent.ANY, mouseHandler);

        EventHandler<ScrollEvent> scrollHandler = t -> {

            events.add(t);

            StringBuilder sb = new StringBuilder(((Node) t.getSource()).getId() + " ");

            if (t.getEventType().equals(ScrollEvent.SCROLL)) {

                sb.append(SCROLL);

            } else {

                return;

            }

            sb.append(coords(t.getDeltaX(), t.getDeltaY()));

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

            sb.append("\n");

            lbl.appendText(sb.toString());

        };

        target1.addEventHandler(ScrollEvent.ANY, scrollHandler);

        target2.addEventHandler(ScrollEvent.ANY, scrollHandler);

        Scene scene = new Scene(root);

        scene.addEventFilter(KeyEvent.ANY, t -> {

            events.add(t);

            if (t.getEventType().equals(KeyEvent.KEY_TYPED)) {

                if (!t.isAltDown() && !t.isControlDown() && !t.isMetaDown()) {

                    lbl.appendText(TYPED + t.getCharacter() + "\n");

                }

            } else if (t.getEventType().equals(KeyEvent.KEY_RELEASED)
                    || t.getEventType().equals(KeyEvent.KEY_PRESSED)) {

                StringBuilder sb = new StringBuilder(t.getEventType().
                        equals(KeyEvent.KEY_PRESSED) ? PRESSED : RELEASED);

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

        stage.setScene(scene);

        stage.setX(200);

        stage.setY(200);

        stage.show();

    }
    private static final DecimalFormat df = new DecimalFormat("#");

    static String coords(double x, double y) {

        return "(" + df.format(x) + "," + df.format(y) + ") ";

    }
}
