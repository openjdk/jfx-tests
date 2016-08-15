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
import javafx.application.Platform;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for shared workers.
 * @author Irina Grineva
 */
public class SharedWorkerTest extends WorkerTestClass {
    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for connecting multiple pages to one shared worker.
     */
    @Test(timeout=5000)
    public void sharedWorker1() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/sharedWorkerPage1.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("Connections: 2");
    }

    /**
     * Test to ensure that pages connected to a shared worker have separate contexts.
     */
    @Test(timeout=5000)
    public void sharedWorker2() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(getAbsolutePathToResource("resources/sharedWorkerPage2.html"));
            }
        });
        doWaitPageLoading();
        waitForResult("true");
    }
}