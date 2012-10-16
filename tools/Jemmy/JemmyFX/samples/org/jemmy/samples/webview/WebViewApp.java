/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package org.jemmy.samples.webview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewApp extends Application {
    static {
        System.setProperty("http.proxyHost", "www-proxy.uk.oracle.com");
        System.setProperty("http.proxyPort", "80");
    }

    WebView browser;
    VBox pane;

    public class MainScene extends Scene {
        public MainScene() {
            super(pane, 500, 500);

            pane.setVgrow(browser, Priority.ALWAYS);
            pane.getChildren().add(browser);
        }
    }

    @Override
    public void start(Stage stage) {
        browser = new WebView();
        browser.getEngine().load("file:///" + System.getProperty("user.dir") +  "\\samples\\org\\jemmy\\samples\\webview\\button.html");

        pane = new VBox();
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(new MainScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(WebViewApp.class, args);
    }
}