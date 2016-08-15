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

package com.sun.fx.webnode.tests.history;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Irina Grineva
 */
public class HistoryLauncher extends Application {

    protected WebEngine web = null;
    protected Text currIndex = new Text();
    protected Text currHistorySize = new Text();
    protected WebView view;

    protected HBox addButtonPanel() {
        Button goNeg = new Button("Go(-1)");
        goNeg.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                web.getHistory().go(-1);
            }
        });

        Button goPos = new Button("Go(1)");
        goPos.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                web.getHistory().go(1);
            }
        });

        Button goNegTooMuch = new Button("Go(-5)");
        goNegTooMuch.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                web.getHistory().go(-5);
            }
        });

        Button goPosTooMuch = new Button("Go(5)");
        goPosTooMuch.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                web.getHistory().go(5);
            }
        });

        Button go0 = new Button("Go(0)");
        go0.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                web.getHistory().go(0);
            }
        });

        Button setHistorySize1 = new Button("Set history size to 1");
        setHistorySize1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                web.getHistory().setMaxSize(1);
                currIndex.setText("Current index: " + web.getHistory().getCurrentIndex());
                currHistorySize.setText("Current number of entries: " + web.getHistory().getEntries().size());
            }
        });

        Button setHistorySize0 = new Button("Set history size to 0");
        setHistorySize0.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                web.getHistory().setMaxSize(0);
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(goNeg, goPos, go0, goNegTooMuch, goPosTooMuch, setHistorySize1, setHistorySize0);
        return buttons;
    }

    protected VBox createContainer() {
        VBox container = new VBox();
        container.getChildren().addAll(addButtonPanel(), currIndex, currHistorySize, view);
        return container;
    }

    @Override
    public void start(Stage stage) {
        view = new WebView();
        web = view.getEngine();
        web.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue<? extends State> ov, State t, State t1) {
                if (t1.equals(Worker.State.SUCCEEDED)) {
                    currIndex.setText("Current index: " + web.getHistory().getCurrentIndex());
                    currHistorySize.setText("Current number of entries: " + web.getHistory().getEntries().size());
                }
            }
        });
        String url = getParameters().getRaw().get(0);
        if (url != null)
            web.load(url);
        stage.setTitle("Launcher");
        stage.setScene(new Scene(createContainer(), 800, 600));
        stage.sizeToScene();
        stage.show();
    }

    public static void run(final String url) {
        final String[] args = new String[] {url};
        new Thread(new Runnable() {
            public void run() {
                Application.launch(HistoryLauncher.class, args);
            }
        }, "FXSQE app launch thread").start();
    }

}
