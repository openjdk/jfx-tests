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

package com.sun.fx.webnode.tests.bridge.javascript2java;

import static com.sun.fx.webnode.tests.commonUtils.GenericTestClass.getPath;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import netscape.javascript.JSObject;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import netscape.javascript.JSException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests that check that objects bound and unbound by
 * WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding methods
 * are properly accessible/inaccessible after WebEngine.reload.
 * @author Irina Grineva
 */
public class ReloadTest extends JavaScript2JavaBridgeTestClass {
    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for WebEngine.addJavaScriptBinding call.
     * Checks that a bound object is not accessible from JavaScript
     * after WebEngine reload.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingReload() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                engine.load(getPath(ReloadTest.class, "stub.html"));
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(testObject, resultObject);
        resultObject = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                engine.reload();
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                        if (t1.equals(Worker.State.SUCCEEDED)) {
                            try {
                                resultObject = engine.executeScript("testObject;");
                            } catch (netscape.javascript.JSException e) {
                                if (e.getMessage().contains("Can't find variable: testObject")) {
                                    resultObject = null;
                                }
                            }
                        }
                    }
                });
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject == null);
            }
        });
    }

    /**
     * Test for WebEngine.removeJavaScriptBinding call.
     * Checks that an unbound object is not accessible from JavaScript
     * after WebEngine reload.
     */
    @Test(timeout=10000)
    public void testRemoveJavaScriptBindingReload() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                engine.load(getPath(ReloadTest.class, "stub.html"));
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(testObject, resultObject);

        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                window.removeMember("testObject");
                try {
                    engine.executeScript("testObject;");
                } catch (JSException e) {
                    resultObject = e;
                    System.out.println(e.getMessage());
                }
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertTrue(resultObject instanceof JSException);
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(NO_VARIABLE_MSG));

        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                engine.reload();
            }
        });
        doWaitPageLoading();
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    engine.executeScript("testObject;");
                } catch (JSException e) {
                    resultObject = e;
                    System.out.println(e.getMessage());
                }
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertTrue(resultObject instanceof JSException);
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(NO_VARIABLE_MSG));
    }
}
