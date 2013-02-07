/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.fxapp;

import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.stage.Stage;

/**
 *
 * @author Sergey
 */
public class LifecycleApp extends Application {

    static final String PARAM1 = "param1";
    static final String PARAM2 = "    param2";
    static final String PARAM3 = "    param3    ";
    static final String PARAMZ = "param4 param5";
    static final String NAME1 = "named1";
    static final String VALUE1 = "value1";
    static final String NAME2 = "named2";
    static final String FAKENAMED = "fakenamed3=value3";

    static final String[] DEFAULT_PARAMS = new String[] {PARAM1, PARAM2, "--" + NAME1 + "=" + VALUE1, "--" + NAME2 + "=", FAKENAMED, PARAMZ, PARAM3};

    public static void main(String[] args) {
        reset();
        launch(DEFAULT_PARAMS);
    }
    
    @Override
    public void start(final Stage stage) throws Exception {

        System.out.println("START");
        status.start = now();

        status.named = getParameters().getNamed();
        System.out.println("Named Parameters:");
        for (String pkey : status.named.keySet()) {
            System.out.println(pkey + "=" + status.named.get(pkey));
        }
        System.out.println("Unnamed Parameters:");
        for (String string : status.unnamed = getParameters().getUnnamed()) {
            System.out.println(string);
        }
        System.out.println("Raw Parameters:");
        for (String string : status.raw = getParameters().getRaw()) {
            System.out.println(string);
        }
        stage.setScene(new Scene(new Group(ButtonBuilder.create().text("close").onAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                stage.close();
            }

        }).build())));
        stage.show();
        
    }
    
    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("INIT");
        status.init = now();
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("STOP");
        status.stop = now();
    }


    // reporting utility, we use an interpid assume that there is one test in the VM at one time
    static class Status {
        long zero = System.nanoTime();

        long init = -1;
        long start = -1;
        long stop = -1;

        Map<String, String> named = null;
        List<String> unnamed = null;
        List<String> raw = null;
    }

    private static long now() {
        return System.nanoTime() - status.zero;
    }

    static Status status = null;

    static void reset() {
        status = new Status();
    }
}
