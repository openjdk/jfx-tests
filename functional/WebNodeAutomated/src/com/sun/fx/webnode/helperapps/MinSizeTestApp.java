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

package com.sun.fx.webnode.helperapps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Irina Latysheva
 */
public class MinSizeTestApp extends Application {

    public static final double MIN_HEIGHT = 130.;
    public static final double MIN_WIDTH = 120.;
    public static final String VIEW_ID = "MinSizeTestView2";

    public void start(Stage stage){
        WebView view = new WebView();
        WebEngine e = view.getEngine();
        e.loadContent("<div style='height: 30px; width: 30; background-color: limegreen;'>Small div</div>");
        view.setMinSize(MIN_WIDTH, MIN_HEIGHT);
        view.setId(VIEW_ID);

        VBox box = new VBox();
        box.setMaxWidth(MIN_WIDTH - 10);
        box.setPrefWidth(MIN_WIDTH - 10);
        box.setMinWidth(MIN_WIDTH - 10);
        box.setMaxHeight(MIN_HEIGHT - 10);
        box.setPrefHeight(MIN_HEIGHT - 10);
        box.setMinHeight(MIN_HEIGHT - 10);

        box.getChildren().add(view);

        final Scene scene = new Scene(box);
        stage.setTitle(VIEW_ID);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        System.out.println("Width:" + view.getWidth());
        System.out.println("Height:" + view.getHeight());
    }

    public static void main(String []args) {
        test.javaclient.shared.Utils.launch(MinSizeTestApp.class, args);
    }
}
