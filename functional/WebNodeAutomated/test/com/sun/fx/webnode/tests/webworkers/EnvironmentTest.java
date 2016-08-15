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
 * Tests for accessing worker's environment.
 * @author Irina Grineva
 */
public class EnvironmentTest extends WorkerTestClass {
    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for accessing navigator opject.
     */
    @Test(timeout=5000)
    public void navigator() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/navigatorPage.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("true");
    }

    /**
     * Test for accessing location object.
     */
    @Test(timeout=5000)
    public void location1() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/locationPage1.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("true");
    }

    /**
     * Test for modifying location object (should be unmodifiable).
     */
    @Test(timeout=5000)
    public void location2() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/locationPage2.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("true", true);
    }

    /**
     * Test for accessing XMLHttpRequest prototype from a worker.
     */
    @Test(timeout=5000)
    public void XMLHttpRequest() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/XMLHttpRequestPage.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("true");
    }

    /**
     * Test for using setTimeout/clearTimeout methods from a worker.
     */
    @Test(timeout=10000)
    public void setClearTimeout() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/setTimeoutPage.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("alpha");
    }

    /**
     * Test for using setInterval/clearInterval methods from a worker.
     */
    @Test(timeout=10000)
    public void setClearInterval() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/setIntervalPage.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("alpha");
    }

    /**
     * Test for using importScripts() method from a worker.
     */
    @Test(timeout=5000)
    public void importScripts1() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/importScriptsPage1.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("Success");
    }

    /**
     * Test for using importScripts() method from a worker.
     */
    @Test(timeout=5000)
    public void importScripts2() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/importScriptsPage2.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("Success");
    }

    /**
     * Test for using importScripts() method from a worker while imported
     * script's origin differs from worker's one.
     * http://javafx-jira.kenai.com/browse/RT-27063
     */
    @Test(timeout=5000)
    public void importScripts3() {
        initWebEngineOnFXThread();
        final int port = 13912;
        JettyServer jetty = JettyServer.getInstance(port);
        jetty.setBaseDir(getPath (this.getClass(), "resources"));
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/importScriptsPage3.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("Success");
    }

    /**
     * Test for using AppCache from a worker.
     * http://javafx-jira.kenai.com/browse/RT-9775
     */
    @Test(timeout=5000)
    public void appCache() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/appCachePage.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("true");
    }
}