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

import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import netscape.javascript.JSObject;
import javafx.application.Platform;
import netscape.javascript.JSException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for WebEngine.removeJavaScriptBinding method.
 * @author Irina Grineva
 */
public class RemoveJavaScriptBindingTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for WebEngine.removeJavaScriptBinding call.
     * Checks that a bound object can be unbound from JavaScript
     * and is not accessible anymore.
     */
    @Test(timeout=10000)
    public void testRemoveJavaScriptBinding() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
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
                initWebEngineAndWindow();
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
    }

    /**
     * Test for WebEngine.removeJavaScriptBinding call.
     * Checks that nothing nasty happens when unbinding a name which was not bound.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingWrongName() {
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.removeMember("abracadabra");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Simple test for WebEngine.removeJavaScriptBinding call.
     * Checks nothing nasty happens when calling it with null.
     * http://javafx-jira.kenai.com/browse/RT-19060
     * Currently crashes.
     */
    @Test(timeout=10000)
    public void testRemoveJavaScriptBindingNull() {
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                try {
                    window.removeMember(null);
                } catch (NullPointerException e) {
                    ready = true;
                }
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Simple test for WebEngine.removeJavaScriptBinding call.
     * Checks that nothing nasty happens when calling it with
     * empty string parameter.
     */
    @Test(timeout=10000)
    public void testRemoveJavaScriptBindingEmptyName() {
        testObject = new Object();
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.removeMember("");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Simple test for WebEngine.removeJavaScriptBinding call.
     * Checks that nothing nasty happens when calling it with
     * malformed name string.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingDotName() {
        testObject = new Object();
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test.Object", testObject);
                window.removeMember("test.Object");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Simple test for WebEngine.removeJavaScriptBinding call.
     * Checks that nothing nasty happens when calling it with
     * malformed name string.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingHashName() {
        testObject = new Object();
        ready = true;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test#Object", testObject);
                window.removeMember("test#Object");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Simple test for WebEngine.removeJavaScriptBinding call.
     * Checks that nothing nasty happens when calling it with
     * malformed name string.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingDashName() {
        testObject = new Object();
        ready = true;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test-Object", testObject);
                window.removeMember("test-Object");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Simple test for WebEngine.removeJavaScriptBinding call.
     * Checks that nothing nasty happens when calling it with
     * malformed name string.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingQuoteName() {
        testObject = new Object();
        ready = true;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test\"Object", testObject);
                window.removeMember("test\"Object");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Simple test for WebEngine.removeJavaScriptBinding call.
     * Checks that nothing bad happens when trying to remove binding which was
     * already removed.
     */
    @Test(timeout=10000)
    public void testRemoveJavaScriptBindingTwice() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
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

        for (int i = 0; i < 2; i++) {
            resultObject = null;
            Platform.runLater(new Runnable() {
                public void run() {
                    initWebEngineAndWindow();
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
        }
    }

    /**
     * Test for WebEngine.removeJavaScriptBinding call.
     * Checks that when an object is bound to two JavaScript aliases,
     * one will still be valid after another one is unbound.
     */
    @Test(timeout=10000)
    public void testRemoveJavaScriptBindingMultiple() {
        resultObject = null;
        containerObject = null;
        testObject = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testObject1", testObject);
                resultObject = engine.executeScript("testObject;");
                containerObject = engine.executeScript("testObject1;");
            }
        });

        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null) && (containerObject != null);
            }
        });
        Assert.assertEquals(testObject, resultObject);
        Assert.assertEquals(testObject, containerObject);

        resultObject = null;
        containerObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                window.removeMember("testObject");
                try {
                    engine.executeScript("testObject;");
                } catch (JSException e) {
                    resultObject = e;
                    System.out.println(e.getMessage());
                }
                containerObject = engine.executeScript("testObject1;");
            }
        });

        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null) && (containerObject != null);
            }
        });
        Assert.assertTrue(resultObject instanceof JSException);
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(NO_VARIABLE_MSG));
        Assert.assertEquals(testObject, containerObject);
    }
}
