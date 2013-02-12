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
package test.scenegraph.app;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.SwingAWTUtils;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class StageSceneApp extends InteroperabilityApp {

    static Stage stageLocalPtr = null;

    @Override
    protected Scene getScene() {
        return new StageScene();
    }

    public static void setNonFullScreen() {
        if (null != stageLocalPtr) {

            javafx.application.Platform.runLater(new Runnable() {
                public void run() {
                    stageLocalPtr.setFullScreen(false);
                }
            });
        }
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        stage.initStyle(StageStyle.UNDECORATED); // was: stage.setStyle(StageStyle.UNDECORATED);
        super.start(stage);
        StageSceneApp.stageLocalPtr = stage;
        stage.setX(50);
        stage.setY(70);
    }


    public class StageScene extends Scene {
        public StageScene() {
            super(new Group(), 250, 100);
            setFill(Color.GREEN);

            ((Group)getRoot()).getChildren().add(new Utils.TextButton("full screen", new Runnable() {
                public void run() {
                    if (stageLocalPtr != null) {
                        stageLocalPtr.setFullScreen(!stageLocalPtr.isFullScreen());
                    } else {
                        SwingAWTUtils swUtils = new SwingAWTUtils();
                        swUtils.setFullScreen();
                    }
                }
            }) {{
                setTranslateY(20);
            }});
        }
    }

    public static void main(String args[]) {
        Utils.launch(StageSceneApp.class, args);
    }
}
