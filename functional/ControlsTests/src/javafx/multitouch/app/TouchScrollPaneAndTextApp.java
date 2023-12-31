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

package javafx.multitouch.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;
//import test.javaclient.shared.Utils;

/**
 *
 * @author Taras Ledkov < taras.ledkov@oracle.com >
 */
//public class TouchScrollPaneAndTextApp extends Application {
public class TouchScrollPaneAndTextApp extends InteroperabilityApp {

    Scene scene;
    Group root = new Group();

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle(this.getClass().getSimpleName());
//        scene = getScene();
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    protected Scene getScene() {
        scene = new Scene(root, 320, 200, Color.WHITE);

        VBox vb = new VBox();

        vb.setPrefSize(250, 150);

        TextArea textArea = new TextArea();
        ScrollPane scrollPane = new ScrollPane();

        for (int i = 0; i < 100; ++i) {
            textArea.setText(textArea.getText() +
                    "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq\n");
        }

        root.getChildren().add(vb);
        vb.getChildren().add(textArea);
        vb.getChildren().add(scrollPane);
        scrollPane.setContent(new Rectangle(1000,1000,Color.RED));

        //Utils.addBrowser(scene);
        return scene;
    }

    public static void main(String[] args) {
        Utils.launch(TouchScrollPaneAndTextApp.class, args);
        //Application.launch(TouchScrollPaneAndTextApp.class, args);
    }
}
