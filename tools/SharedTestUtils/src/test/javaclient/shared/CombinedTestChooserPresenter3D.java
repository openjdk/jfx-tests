/*
 * Copyright (c) 2010-2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.javaclient.shared;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 *
 * @author shubov
 */
public class CombinedTestChooserPresenter3D extends CombinedTestChooserPresenter {


    public CombinedTestChooserPresenter3D(int _width, int _height, String _title, Pane _buttonsPane) {
        super(_width, _height, _title, _buttonsPane, true);
        avoidSettingPageContentSize();
    }

    @Override
    protected Scene createSceneForChooserPresenter() {
      //  Scene3D scene = new Scene3D(new Group(), width + 50, height + TABS_SPACE + 30);
      Scene3D scene = new Scene3D(new Group(), width , height);

       // DEBUG:
       //     scene.setTranslateZ(0);
       //     scene.setRotateX(30);
       //     scene.setRotateY(45);

        //scene.addDragSupport();  //let it be here for debug
        return scene;
    }

    @Override
    public void show(Stage stage) {
        // DEBUG:
        //stage.setX(0);
        //stage.setY(0);

        stage.setTitle(title);
        fillScene();
        stage.setScene(getScene());
        stage.show();
        stage.toFront();
        setNodeForScreenshot(null);
        stage.setFocused(true);
    }

}

