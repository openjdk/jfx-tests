/*
 * Copyright (c) 2010-2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.javaclient.shared;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static test.javaclient.shared.TestUtil.isEmbedded;

public abstract class InteroperabilityApp extends Application
                                          implements Interoperability {

    static {
        System.setProperty("prism.lcdtext", "false");
    }

    Scene scene;
    protected Stage stage;

    protected abstract Scene getScene();

    @Override
    public void start(Stage stage) throws InterruptedException {
        this.stage = stage;
        stage.setTitle(this.getClass().getSimpleName());
        if(isEmbedded())
            stage.setFullScreen(true);
        scene = getScene();
        Utils.setCustomFont(scene);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        stage.requestFocus();
    }

    public void startSwing(Object frame, Object panel) {
        SwingAWTUtils swUtils = new SwingAWTUtils();
        swUtils.startSwing(frame, panel, scene, getScene());
        scene = swUtils.scene;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InteroperabilityApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startSWT() {
        final CountDownLatch sync = new CountDownLatch(1);
        SWTUtils swUtils = new SWTUtils();
        swUtils.startSWT(scene, getScene(), sync);
        scene = swUtils.scene;
        try {
            sync.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(InteroperabilityApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
