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

package com.sun.fx.webnode.tests.customizable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Irina Grineva
 */
public class CustomizableLauncher extends Application {

    protected WebEngine engine;
    protected WebView view;

    @Override
    public void start(Stage stage) throws Exception {
        view = new WebView();
        engine = view.getEngine();

        engine.load(CustomizableLauncher.class.getResource("resources/testpage.html").toExternalForm());

        VBox container = new VBox();
        container.getChildren().addAll(buttons(), view);
        stage.setScene(new Scene(container));
        stage.show();
    }

    public static void run() {
        final String[] args = new String[] {};
        new Thread(new Runnable() {
            public void run() {
                Application.launch(CustomizableLauncher.class, args);
            }
        }, "FXSQE app launch thread").start();
    }

    protected HBox buttons() {
        Button disableJS = new Button("Disable JavaScript");
        disableJS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.setJavaScriptEnabled(false);
            }
        });
        Button enableJS = new Button("Enable JavaScript");
        enableJS.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.setJavaScriptEnabled(true);
            }
        });
        Button disableContextMenu = new Button("Disable context menu");
        disableContextMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                view.setContextMenuEnabled(false);
            }
        });
        Button enableContextMenu = new Button("Enable context menu");
        enableContextMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                view.setContextMenuEnabled(true);
            }
        });
        Button attachStyleSheet = new Button("Attach user stylesheet");
        attachStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.setUserStyleSheetLocation(CustomizableLauncher.class.getResource("resources/user.css").toExternalForm());
            }
        });
        Button detachStyleSheet = new Button("Detach user stylesheet");
        detachStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.setUserStyleSheetLocation(null);
            }
        });
        Button attachExternalStyleSheet = new Button("Attach user stylesheet (external URL)");
        attachExternalStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.setUserStyleSheetLocation("http://www.iana.org/_css/2008.1/screen.css");
            }
        });
        Button attachMalformedStyleSheet = new Button("Attach user stylesheet (malformed URL)");
        attachMalformedStyleSheet.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                engine.setUserStyleSheetLocation("ololo");
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(disableJS, enableJS, disableContextMenu, enableContextMenu, attachStyleSheet, detachStyleSheet, attachExternalStyleSheet, attachMalformedStyleSheet);

        return buttons;
    }
}
