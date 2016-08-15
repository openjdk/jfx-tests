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

package javafx.scene.control.test.jfxpanel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import test.javaclient.shared.Utils;

public class BrowserApp extends Application {
    static {
        System.setProperty("prism.lcdtext", "false");
    }

    WebView browser = new WebView();
    VBox pane = new VBox();

    public class MainScene extends Scene {
        public MainScene() {
            super(pane, JFXPanelBrowserApp.SCENE_WIDTH, JFXPanelBrowserApp.SCENE_HEIGHT);

            Utils.setCustomFont(this);

            pane.setMinSize(JFXPanelBrowserApp.SCENE_WIDTH, JFXPanelBrowserApp.SCENE_HEIGHT);
            pane.setMaxSize(JFXPanelBrowserApp.SCENE_WIDTH, JFXPanelBrowserApp.SCENE_HEIGHT);
            pane.setPrefSize(JFXPanelBrowserApp.SCENE_WIDTH, JFXPanelBrowserApp.SCENE_HEIGHT);

            Button button = new Button("Refresh");
            button.setId(JFXPanelBrowserApp.BUTTON_ID);
            button.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    browser.getEngine().load("http://education.oracle.com/");
                }
            });
            browser.setId(JFXPanelBrowserApp.CONTENT_ID);

            pane.setVgrow(browser, Priority.ALWAYS);
            pane.getChildren().add(button);
            pane.getChildren().add(browser);
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(this.getClass().getSimpleName());
        stage.setScene(new MainScene());
        stage.show();
    }

    public static void main(String[] args) {
        test.javaclient.shared.Utils.launch(BrowserApp.class, args);
    }
}