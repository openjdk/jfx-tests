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

import com.sun.fx.webnode.tests.bridge.javascript2java.helpers.HelperObject;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import netscape.javascript.JSException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for WebEngine.addJavaScriptBinding method.
 * @author Irina Grineva
 */
public class AddJavaScriptBindingTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that a bound object is accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBinding() {
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
    }

    /**
     * Test for WebEngine.addJavaScriptBinding call.
     * Checks that a bound object is accessible from JavaScript, and
     * changes of the object can be recognized from JavaScript.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingObjectChanged() {
        testObject = new HelperObject("a");
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
        Assert.assertEquals("a", ((HelperObject)resultObject).stringField);

        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                ((HelperObject)testObject).stringField = "b";
                resultObject = engine.executeScript("testObject;");
            }
        });

        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(testObject, resultObject);
        Assert.assertEquals("b", ((HelperObject)resultObject).stringField);
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that an object with null value is properly bound and accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingForNull() {
        testObject = null;
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testObject, resultObject);
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that a null value is properly bound and accessible from JavaScript. And nothing bad happens ;)
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingWithNull() {
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", null);
                resultObject = engine.executeScript("testObject;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertNull(resultObject);
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that nothing nasty happens when a binding with null name
     * is attempted to be created.
     * http://javafx-jira.kenai.com/browse/RT-19060
     * Currently crashes.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingWithNullName() {
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                try {
                    window.setMember(null, null);
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
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that a situation with empty binding name is handled nicely
     * and using this malformed name in JS conforms to JS syntax.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingEmptyName() {
        testObject = new Object();
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("", testObject);
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
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that a situation with malformed binding name is handled nicely
     * and using this malformed name in JS conforms to JS syntax.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingDotName() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test.Object", testObject);
                try {
                    resultObject = engine.executeScript("test.Object;");
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(NO_VARIABLE_MSG2));
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that a situation with malformed binding name is handled nicely
     * and using this malformed name in JS conforms to JS syntax.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingHashName() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test#Object", testObject);
                try {
                    resultObject = engine.executeScript("test#Object;");
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(INVALID_CHARACTER_MSG));
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that a situation with malformed binding name is handled nicely.
     * and using this malformed name in JS conforms to JS syntax.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingDashName() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test-Object", testObject);
                try {
                    resultObject = engine.executeScript("test-Object;");
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(NO_VARIABLE_MSG2));
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that a situation with malformed binding name is handled nicely.
     * and using this malformed name in JS conforms to JS syntax.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingQuoteName() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test\"Object", testObject);
                try {
                    resultObject = engine.executeScript("test\"Object;");
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(UNEXPECTED_EOF_MSG));
    }

    /**
     * Simple test for WebEngine.addJavaScriptBinding call.
     * Checks that nothing nasty happens when binding an object to the name already used.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingNameExists() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                containerObject = engine.executeScript("window;");
                window.setMember("window", testObject);
                resultObject = engine.executeScript("window;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(containerObject, resultObject);
    }

    /**
     * Test for WebEngine.addJavaScriptBinding call.
     * Checks that an object can be bound under multiple names,
     * and tracked under each name properly.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingMultiple() {
        testObject = new Object();
        resultObject = null;
        containerObject = null;
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
    }

    /**
     * Test for WebEngine.addJavaScriptBinding call.
     * Checks that an object can be bound under multiple names,
     * and tracked under each name properly.
     */
    @Test(timeout=10000)
    public void testAddJavaScriptBindingMultipleObjectChanged() {
        testObject = new HelperObject("a");
        resultObject = null;
        containerObject = null;
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
        Assert.assertEquals("a", ((HelperObject)resultObject).stringField);
        Assert.assertEquals("a", ((HelperObject)containerObject).stringField);

        resultObject = null;
        containerObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                ((HelperObject)testObject).stringField = "b";
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
        Assert.assertEquals("b", ((HelperObject)testObject).stringField);
    }
}
