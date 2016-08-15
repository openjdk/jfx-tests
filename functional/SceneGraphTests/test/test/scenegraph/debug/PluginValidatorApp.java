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
