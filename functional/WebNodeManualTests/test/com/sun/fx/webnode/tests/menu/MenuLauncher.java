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

package com.sun.fx.webnode.tests.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Irina Grineva
 */
public class MenuLauncher extends Application {

    private WebEngine web = null;
    private WebView view = null;
    private WebView view2 = null;

    private Scene createScene(String url) {
        view = new WebView();
        view2 = new WebView();
        web = view.getEngine();

        web.load(url);
        web.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
            @Override
            public WebEngine call(PopupFeatures config) {
                return view2.getEngine();
            }
        });

        view2.getEngine().load(MenuLauncher.class.getResource("resources/empty.html").toExternalForm());

        final VBox box = new VBox();
        view.setMaxHeight(300);
        view.setMinHeight(300);
        view2.setMaxHeight(300);
        view2.setMinHeight(300);
        box.getChildren().addAll(view, view2);

        final Scene scene = new Scene(box);

        scene.setFill(Color.GRAY);
        return scene;
    }

    @Override
    public void start(Stage stage) {
        final Scene scene = createScene(getParameters().getRaw().get(0));
        stage.setTitle("Launcher");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.sizeToScene();
        stage.show();
    }

    public static void run(final String url) {
        final String[] args = new String[] {url};
        new Thread(new Runnable() {

            public void run() {
                Application.launch(MenuLauncher.class, args);
            }
        }, "FXSQE app launch thread").start();
    }

}
