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
 * These tests check that values of primitive types and strings can be exposed
 * properly and concealed without generating any errors.
 * @author Irina Grineva
 */
public class AddRemoveCommonTypesTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding calls.
     * Checks that an integer value can be bound and unbound.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingInt() {
        final int value = 140;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", value);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
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
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding calls.
     * Checks that a boolean value can be bound and unbound.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingBoolean() {
        final boolean value = true;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", value);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
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
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding calls.
     * Checks that a double value can be bound and unbound.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingDouble() {
        final double value = 251.7;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", value);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, (Double)resultObject, precision);
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
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding calls.
     * Checks that a float value can be bound and unbound.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingFloat() {
        final float value = (float) 25.9;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", value);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, (Double)resultObject, precision);
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
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding calls.
     * Checks that a char value can be bound and unbound.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingChar() {
        final char value = 'a';
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", value);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
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
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding calls.
     * Checks that a string value can be bound and unbound.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingString() {
        final String value = "test me!";
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", value);
                resultObject = engine.executeScript("testObject;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
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
    }

}
