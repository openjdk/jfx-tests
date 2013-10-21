/*
 * Copyright (c) 2009-2013, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.fx.control;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
//import org.jemmy.fx.Browser;

/**
 * @author shura
 */
public class ComboBoxApp extends Application {

    public static void main(String[] args) throws AWTException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);

//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            boolean browserStarted = false;
//            public void handle(KeyEvent ke) {
//                if (!browserStarted && ke.isControlDown() && ke.isShiftDown() && ke.getCode() == KeyCode.B) {
//                    browserStarted = true;
//                    javafx.application.Platform.runLater(new Runnable() {
//                        public void run() {
//                            try {
//                                Browser.runBrowser();
//                            } catch (AWTException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }
//        });


        ComboBox combo = new ComboBox();
        for (int i = 0; i < 20; i++) {
            combo.getItems().add("Item " + i);
        }

        combo.setEditable(true);

        Popup popup = new Popup();
        popup.getContent().add(combo);

        //box.getChildren().add(combo);

        stage.setScene(scene);

        stage.setWidth(300);
        stage.setHeight(300);

        stage.show();

        popup.show(scene.getWindow());
    }
}