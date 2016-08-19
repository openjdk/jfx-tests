/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.oracle.appbundlers.tests.functionality.parameters.ExplodedModuleParameters;
import com.oracle.appbundlers.tests.functionality.parameters.JmodParameters;
import com.oracle.appbundlers.tests.functionality.parameters.ModularJarParameters;
import com.oracle.appbundlers.tests.functionality.parameters.NormalJarParameters;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.Constants;
import com.oracle.tools.packager.Bundlers;
import com.oracle.tools.packager.ConfigException;
import com.oracle.tools.packager.UnsupportedPlatformException;
import com.sun.javafx.tools.packager.Log;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class AppBundlersSampleGUI extends Application implements Constants {

    private final Logger LOG = Logger
            .getLogger(AppBundlersSampleGUI.class.getName());

    private AppWrapper app;

    private Map<String, Object> params;

    @Override
    public void start(Stage primaryStage) {
        final CheckBox fxAppCheckBox = new CheckBox("Java App");
        final Button buildButton = new Button("Build application");
        final CheckBox verbose = new CheckBox("Verbose output/Debug");

        final VBox root = new VBox();
        root.getChildren().addAll(fxAppCheckBox, verbose, buildButton);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(5);

        final Scene scene = new Scene(root);

        buildButton.setOnAction((ev) -> {
            try {
                if (verbose.isSelected()) {
                    Log.setLogger(new Log.Logger(true));
                    Log.setDebug(true);
                }
                createParametersAndExecuteAppBundler(new NormalJarParameters());
                createParametersAndExecuteAppBundler(
                        new ExplodedModuleParameters());
                createParametersAndExecuteAppBundler(
                        new ModularJarParameters());
                createParametersAndExecuteAppBundler(new JmodParameters());
            } finally {
                Platform.exit();
            }
        });

        primaryStage.setTitle("AppBundlers Sample GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void executeAppBundler() {
        Bundlers.createBundlersInstance().getBundlers().stream()
                .filter((bundler) -> {
                    try {
                        bundler.validate(params);
                        return true;
                    } catch (ConfigException ex) {
                        LOG.log(Level.SEVERE,
                                "Failed to create bundler \"{0}\": {1}.",
                                new Object[] { bundler.getID(), ex });
                        return false;
                    } catch (UnsupportedPlatformException ex) {
                        LOG.log(Level.INFO,
                                "Current platform is not supported by \"{0}\".",
                                bundler.getID());
                        return false;
                    }
                }).forEach((bundler) -> {
                    String paramStr = params.entrySet().stream()
                            .map((e) -> String.format("%s = %s", e.getKey(),
                                    e.getValue()))
                            .collect(Collectors.joining("\n"));
                    LOG.log(Level.INFO, "Executing bundler: {0}, params:\n{1}",
                            new Object[] { bundler.getID(), paramStr });
                    File result = bundler.execute(params,
                            app.getBundlesDir().toFile());
                    LOG.log(Level.INFO, "Finished with result: {0}", result);
                });
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void createParametersAndExecuteAppBundler(
            com.oracle.appbundlers.tests.functionality.parameters.Parameters parameters) {
        try {
            parameters.initializeDefaultApp();
            this.app = parameters.getApp();
            System.out.println(
                    "Writing app to app directory: " + app.getWorkDir());
            this.app.preinstallApp(parameters.getExtension());
            this.app.writeSourcesToAppDirectory();
            this.app.compileApp();
            this.app.jarApp(parameters.getExtension());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> basicParams = null;
        Map<String, Object> additionalParams = null;
        try {
            basicParams = parameters.getBasicParams();
            additionalParams = parameters.getAdditionalParams();
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        this.params = new HashMap<>();
        this.params.putAll(basicParams);
        this.params.putAll(additionalParams);

        executeAppBundler();
    }
}
