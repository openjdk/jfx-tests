/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
