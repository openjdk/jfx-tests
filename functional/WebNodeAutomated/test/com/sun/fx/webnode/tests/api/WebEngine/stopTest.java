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
package com.sun.fx.webnode.tests.api.WebEngine;

import com.sun.fx.webnode.tests.commonUtils.ExceptionWaitingTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for javafx.scene.web.WebEngine.stop() method.
 * @author Dmitrij Pochepko
 */
public class stopTest extends ExceptionWaitingTestClass {

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }
    /**
     * Test for javafx.scene.web.WebEngine.stop() method. Checks that WebEngine.stop() doesn't crash.
     * @author Dmitrij Pochepko
     */
    @Test(timeout = 10000)
    public void test1() {
        gotException = null;
        Platform.runLater(new Runnable() {

            public void run() {
                try {
                    initWebEngine(url1);
                    engine.getLoadWorker().cancel();
                } catch (Exception e) {
                    gotException = Boolean.TRUE;
                }
                if (gotException == null) {
                    gotException = Boolean.FALSE;
                }
            }
        });
        doWait(exceptionOcurred);
    }
}
