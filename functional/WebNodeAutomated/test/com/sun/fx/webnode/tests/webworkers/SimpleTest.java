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

import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import netscape.javascript.JSObject;
import javafx.application.Platform;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Simple tests to ensure WebWorkers work.
 * @author Irina Grineva
 */
public class SimpleTest extends WorkerTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test to check if WebWorkers are supported (prototype is available).
     */
    @Test(timeout=5000)
    public void isSupported() {
        initWebEngineOnFXThread();
        result = null;
        Platform.runLater(new Runnable() {
            public void run() {
                result = (String) engine.executeScript("typeof(Worker);");
            }
        });
        doWait(resultReady);
        Assert.assertFalse("typeof(Worker) is undefined!", UNDEFINED.equals((String)result));
    }

    /**
     * Test to check that the simplest worker does its job.
     */
    @Test(timeout=5000)
    public void loadsAndWorks() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/simplePage.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("done");
    }

    /**
     * Test for the situation when worker file is not present.
     * http://javafx-jira.kenai.com/browse/RT-27149
     */
    @Test(timeout=5000)
    public void noWorkerFile() {
        initWebEngineOnFXThread();
        result = null;
        Platform.runLater(new Runnable() {
            public void run() {
                result = (JSObject) engine.executeScript("new Worker('ololo.js');");
                result = Boolean.TRUE;
            }
        });
        doWait(resultReady);
    }
}
