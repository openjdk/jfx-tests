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
package com.sun.fx.webnode.tests.zoom;

import com.sun.fx.webnode.tests.css.CSSLauncher;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Dmitry Ginzburg
 */
public class ZoomLauncher extends Application{
    protected final static double zoom = 2;
    protected final static int size = 200;
    protected Scene scene;
    protected WebView view;

    protected Button zoomingButton = new Button("Enable zooming");

    private EventHandler <ActionEvent> enableZoom = new EventHandler <ActionEvent>() {
        public void handle (ActionEvent t) {
            view.setZoom(zoom);
            zoomingButton.setText("Disable zooming");
            zoomingButton.setOnAction(disableZoom);
        }
    };

    private EventHandler <ActionEvent> disableZoom = new EventHandler <ActionEvent>() {
        public void handle (ActionEvent t) {
            view.setZoom(1);
            zoomingButton.setText("Enable zooming");
            zoomingButton.setOnAction(enableZoom);
        }
    };

    protected void setHandlers() {
        zoomingButton.setOnAction(enableZoom);
    }

    public void start(Stage stage) {
        StackPane container = new StackPane();

        scene = new Scene(container, 800, 800);

        view = new WebView ();
        WebEngine engine = view.getEngine();
        //using CSSLauncher's test.html
        engine.load(CSSLauncher.class.getResource("resources/test.html").toExternalForm());

        setHandlers();

        GridPane viewPane = new GridPane();
        viewPane.setMaxSize(size, size);
        viewPane.getChildren().add(view);

        container.getChildren().addAll(viewPane, zoomingButton);

        stage.setTitle("Launcher");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void run() {
        new Thread(new Runnable() {
            public void run() {
                Application.launch(ZoomLauncher.class, new String[0]);
            }
        }, "FXSQE app launch thread").start();
    }
}
