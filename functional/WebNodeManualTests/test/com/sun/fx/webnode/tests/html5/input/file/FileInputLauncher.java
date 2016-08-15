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

package com.sun.fx.webnode.tests.html5.input.file;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Launcher for INPUT of type FILE tests.
 * @author Irina Grineva
 */
public class FileInputLauncher extends Application {
    private WebEngine web = null;
    private static final String TARGET_DIR = System.getProperty("user.home")
            + System.getProperty("file.separator");
    private static final String []names = {"test1.txt"
            , "test2.txt"
            , "testveryveryverylongname.txt"
            , "test.png"
            , "test.jpg"};

    private Scene createScene(String url) {
        WebView view = new WebView();
        web = view.getEngine();
        final VBox box = new VBox();
        web.load(url);

        Label generate = new Label("Files generated in " + TARGET_DIR);
        box.getChildren().addAll(generate, view);

        final Scene scene = new Scene(box);
        scene.widthProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                box.setPrefWidth(scene.getWidth());
            }
        });

        scene.heightProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                box.setPrefHeight(scene.getHeight());
            }
        });

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
        stage.show();
    }

    public static void run(final String url) {
        final String[] args = new String[] {url};
        new Thread(new Runnable() {
            public void run() {
                Application.launch(FileInputLauncher.class, args);
            }
        }, "FXSQE app launch thread").start();
    }

    public static void generate() {
        for (String name : names) {
            try {
                File f = new File(TARGET_DIR + name);
                // So that the files can be updated in future:
                if (f.exists()) {
                    f.delete();
                }
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                fw.write("test " + name);   // Yes, we do not need a valid png/jpeg here.
                fw.flush();
                fw.close();
            } catch (Exception e)  {
                Logger.getLogger(FileInputLauncher.class.getName()).log(Level.SEVERE
                        , "Exception ocurred while creating file " + TARGET_DIR + name
                        , e);
            }
        }
    }
}