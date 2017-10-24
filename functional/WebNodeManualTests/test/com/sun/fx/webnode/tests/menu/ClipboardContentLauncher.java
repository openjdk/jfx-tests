/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.html.HTMLImageElement;
import org.w3c.dom.html.HTMLParagraphElement;

/**
 *
 * @author rkamath
 */
public class ClipboardContentLauncher extends Application {
    private final int SIZE = 200;
    private WebView webView = null;
    private HTMLImageElement htmlImageElement = null;
    private Button verifyButton = null;
    private static int clickCount = 0;
    private final List<String> supportedImages = Arrays.asList("../../dnd/resources/square.png",
                                                       "../../dnd/resources/tinker.jpg",
                                                       "../../acid/sec5526c.gif");
    private final List<String> supportedImagesDescr = Arrays.asList("Copy PNG Image Type",
                                                        "Copy JPG Image Type",
                                                        "Copy GIF Image Type");

    private void initializeResources(int index) {
        final Document document = webView.getEngine().getDocument();
        htmlImageElement = (HTMLImageElement)document.getElementById("myimage");
        htmlImageElement.setSrc(supportedImages.get(index));
        HTMLParagraphElement htmlParagraphElement = (HTMLParagraphElement)document.getElementById("description");
        htmlParagraphElement.setTextContent(supportedImagesDescr.get(index));
        verifyButton.setText("Verify Clipboard");
        verifyButton.setTextFill(Color.BLACK);
    }

    private Scene createScene(String url) {
        webView = new WebView();
        webView.getEngine().load(url);
        webView.getEngine().getLoadWorker().stateProperty().addListener(
            (observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                initializeResources(clickCount);
            }
        });

        verifyButton = new Button("Verify Clipboard");
        verifyButton.setPrefSize(SIZE, (SIZE/4));
        verifyButton.setOnAction(actionEvent -> {
            boolean success = false;
            String imgURL = htmlImageElement.getSrc();
            String extension = imgURL.substring(imgURL.lastIndexOf(".") + 1);
            List<File> clipboardFiles = Clipboard.getSystemClipboard().getFiles();
            for (File file : clipboardFiles) {
                if (file.getName().endsWith(extension)) {
                    success = true;
                }
            }
            if (success) {
                verifyButton.setTextFill(Color.GREEN);
                verifyButton.setText("Success");
            } else {
                verifyButton.setTextFill(Color.RED);
                verifyButton.setText("Failure");
            }
        });

        final Button nextButton = new Button("Next Image");
        nextButton.setPrefSize(SIZE, (SIZE/4));
        nextButton.setOnAction(actionEvent -> {
            ++clickCount;
            initializeResources(clickCount);
            if (clickCount == (supportedImages.size()-1)) {
                clickCount = -1;
                nextButton.setText("Test Concluded.\nClick to start over again!");
            } else {
                nextButton.setText("Next Image");
            }
        });

        final VBox box = new VBox();
        box.getChildren().addAll(webView, verifyButton, nextButton);

        final Scene scene = new Scene(box);
        scene.setFill(Color.GRAY);
        return scene;
    }

    @Override
    public void start(Stage stage) {
        final Scene scene = createScene(getParameters().getRaw().get(0));
        stage.setTitle("ClipboardContentLauncher");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void run(final String url) {
        new Thread(() -> Application.launch(ClipboardContentLauncher.class,
            new String[] {url}), "FXSQE app launch thread").start();
    }
}
