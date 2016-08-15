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
 */

package client.test.runner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author vshubov
 */
public class DrtWebNodeRunner extends Application {
    static String param = "";
    WebEngine web;

    WebView createWebView(String url) {
        System.out.println("URL to load: " + url);
        WebView view = new WebView();
        web = view.getEngine();
        web.getLoadWorker().stateProperty().addListener (
        new ChangeListener <State>() {
            @Override
            public void changed (ObservableValue ov, State oldState, State newState) {
                System.out.println ("WebEngine's state changed from " + oldState.toString() + " to " + newState.toString());
                System.out.println("Location: " + web.getLocation());
                if (web.getLoadWorker().getException() != null) {
                     System.out.println("Exception message: " + web.getLoadWorker().getException().getMessage());
                }
            }
        });
        web.load(url);
        return view;
    }

    Scene createScene(String url) {
        Scene scene = new Scene(createWebView(url));
        // Make a bottom gray bar to free the resize corner on Mac
        scene.setFill(Color.GRAY);
        return scene;
    }

    /**
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        if (param == null || "".equals(param)) {
            param = getParameters().getRaw().get(0);
        }
        final Scene scene = createScene(param);
        stage.setTitle("Launcher");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    // ...so this is not run either.

    /**
     *
     * @param url
     */
        public static void run(final String url) {
        String[] args = new String[0];
        param = url;
        Application.launch(DrtWebNodeRunner.class, args);
    }

    // Seems like main is not used by JavaFX at all anymore...

    /**
     *
     * @param args
     */
        public static void main(String args[]) {
        if (args.length == 1) {
            param = args[0];
            run(param);
        }
    }
}
