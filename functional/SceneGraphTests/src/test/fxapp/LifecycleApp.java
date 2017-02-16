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
package test.fxapp;

import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        Button temp = new Button();
        temp.setText("close");
        temp.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                stage.close();
            }
        });
        stage.setScene(new Scene(new Group(temp)));
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
