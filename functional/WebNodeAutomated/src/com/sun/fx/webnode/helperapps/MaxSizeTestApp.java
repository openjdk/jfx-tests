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
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Irina Latysheva
 */
public class MaxSizeTestApp extends Application {

    public static final double MAX_HEIGHT = 89.;
    public static final double MAX_WIDTH = 99.;
    public static final String VIEW_ID = "MaxSizeTestView";

    public void start(Stage stage){
        WebView view = new WebView();
        WebEngine e = view.getEngine();
        e.loadContent("<div style='height: 1024px; width: 1024; background-color: limegreen;'>Big div</div>");
        view.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
        view.setId(VIEW_ID);

        HBox box = new HBox();
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
        test.javaclient.shared.Utils.launch(MaxSizeTestApp.class, args);
    }
}
