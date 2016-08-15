/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.fx.webnode.tests.webworkers;

import client.util.JettyServer;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for spawning a new worker from inside a worker.
 * @author Irina Grineva
 */
public class SpawnTest extends WorkerTestClass {
    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for spanning a new worker based on a file which is in same directory
     * as current worker.
     * http://javafx-jira.kenai.com/browse/RT-27048
     */
    @Test(timeout=5000)
    public void spawn1() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/spawnPage1.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("Success");
    }

    /**
     * Test for spanning a new worker based on a file which is in a subdirectory
     * of current worker's directory.
     * http://javafx-jira.kenai.com/browse/RT-27048
     */
    @Test(timeout=5000)
    public void spawn2() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/spawnPage2.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("Success");
    }

    /**
     * Test for spanning a new worker based on a file which is has origin
     * different from current worker.
     * http://javafx-jira.kenai.com/browse/RT-27048
     */
    @Test(timeout=5000)
    public void spawn3() {
        initWebEngineOnFXThread();
        final int port = 13412;
        JettyServer jetty = JettyServer.getInstance(port);
        jetty.setBaseDir(getPath (this.getClass(), "resources"));
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/spawnPage3.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("Success");
    }
}