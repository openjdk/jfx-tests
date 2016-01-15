/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.fx.webnode.tests.api.WebEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import netscape.javascript.JSObject;

/**
 * Test for JDK-8138773
 *
 * @author andrey.rusakov@oracle.com
 */
public class ExternalResourceLoadingTest extends Application {

    private static final String EXTERNAL_PAGE = "/resources/external/html/externalTest.html";

    public static class ScriptReady {

        public void scriptReady(boolean passed) {
            System.exit(passed ? 0 : -1);
        }
    }

    @Test(timeout = 10000)
    public void testExternalResourceUnavailable() throws InterruptedException, IOException {
        List<String> cmd = Arrays.asList(
                Paths.get(System.getProperty("java.home"), "bin", "java").toString(),
                "-cp", System.getProperty("java.class.path") + File.pathSeparator
                + "resources/external_html.jar",
                getClass().getName()
        );
        ProcessBuilder procBuilder = new ProcessBuilder(cmd);
        procBuilder.redirectErrorStream(true);
        procBuilder.inheritIO();
        Process process = procBuilder.start();
        if (!process.waitFor(6000, TimeUnit.MILLISECONDS)) {
            process.destroy();
            Assert.fail("Failed with timeout.");
        } else {
            Assert.assertEquals("Zero if test passes.", 0, process.exitValue());
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        JSObject win = (JSObject) engine.executeScript("window");
        win.setMember("scriptReady", new ScriptReady());
        engine.load(getClass().getResource(EXTERNAL_PAGE).toExternalForm());
        stage.setScene(new Scene(new BorderPane(view), 640, 480));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
