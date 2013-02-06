/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.debug;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import test.javaclient.shared.AppLauncher;

/**
 * For debugging purposes only
 *
 * @author Sergey Grinev
 */
public class PluginValidatorApp extends Application {

    private static final String className = "test.scenegraph.functional.Shape2Test";
    private static final String testName = "Circle_LINEAR_GRAD";

    @Override
    public void start(Stage stage) throws Exception {
        AppLauncher.getInstance().setRemoteStage(stage);

        new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("Running junit");
                    Result r = new JUnitCore().run(Request.method(Class.forName(className), testName));
                    System.out.println("got result = " + r.wasSuccessful());
                }
                catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } finally {
                    //System.exit(0);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}
