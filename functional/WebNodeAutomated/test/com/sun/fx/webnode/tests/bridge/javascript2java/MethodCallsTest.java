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
import netscape.javascript.JSObject;
import javafx.application.Platform;
import netscape.javascript.JSException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * These tests cover various aspects of calling member methods of an exposed
 * object (ones not covered in other tests).
 * @author Irina Grineva
 */
public class MethodCallsTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Simple test that checks that JavaScript2Java Bridge can resolve overloaded
     * methods.
     * http://javafx-jira.kenai.com/browse/RT-19124
     */
    @Test(timeout=10000)
    public void testOverloadedMethodCall1() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doSomethingOverloaded1('test');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(((HelperObject)testObject).doSomethingOverloaded1("test"), resultObject);
    }

    /**
     * Test that checks that JavaScript2Java Bridge can resolve overloaded
     * methods.
     * http://javafx-jira.kenai.com/browse/RT-19124 (if methods in HelperObject
     * get swapped accidentally :)
     */
    @Test(timeout=10000)
    public void testOverloadedMethodCall2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doSomethingOverloaded1(1);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(((HelperObject)testObject).doSomethingOverloaded1(1), resultObject);
    }

    /**
     * Test that checks how Java2JavaScript Bridge behaves when an ambiguous
     * call is made from JS.
     * http://javafx-jira.kenai.com/browse/RT-19124 -- currently overloaded methods
     * resolution doesn't work at all
     * http://javafx-jira.kenai.com/browse/RT-19125 -- potentially ambiguous calls
     */
    @Test(timeout=10000)
    public void testOverloadedMethodCallAmbiguous() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doSomethingOverloaded2(1.0);");
                //System.out.println(o);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(((HelperObject)testObject).doSomethingOverloaded2(1.0), resultObject);
    }

    /**
     * This test checks what happens when a wrong parameter is passed to the
     * method call. JS ignores parameter type mismatch (and can cast strings to
     * integer in a very wicked way), so we just check that nothing bad happens.
     */
    @Test(timeout=10000)
    public void testMethodCallWrongParam() {
        testObject = new HelperObject();
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.doParamImportant('ololo');");
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
     * This test checks what happens when a call to a method is made
     * while parameter is lacking. JS ignores lacking parameter, so
     * we just check that nothing bad happens (and undefined result is returned).
     */
    @Test(timeout=10000)
    public void testMethodCallNoParam() {
        final String ILLEGAL_ARGUMENT_EXCEPTION_MSG = "java.lang.IllegalArgumentException: wrong number of arguments";
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                try {
                    engine.executeScript("testObject.doParamImportant();");
                } catch (JSException e) {
                    resultObject = e;
                    System.out.println("exceptionCaught.getMessage(): " + ((JSException)resultObject).getMessage());
                }
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(ILLEGAL_ARGUMENT_EXCEPTION_MSG, ((JSException)resultObject).getMessage());
    }

    /**
     * This test checks what happens when a method throws an exception.
     */
    @Test(timeout=10000)
    public void testMethodCallException() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                try {
                    resultObject = engine.executeScript("testObject.iThrowException();");
                } catch (JSException e) {
                    resultObject = e;
                }
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return resultObject != null;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).e, ((JSException)resultObject).getCause());
    }

    /**
     * This test checks what happens when a method throws an exception.
     */
    @Test(timeout=10000)
    public void testMethodCallException2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                try {
                    resultObject = engine.executeScript("testObject.iThrowException2();");
                } catch (JSException e) {
                    resultObject = e;
                }
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return resultObject != null;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).e2, ((JSException)resultObject).getCause());
    }

    /**
     * This test checks that a String value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testStringMethodCall() {
        testObject = new HelperObject();
        final String value = "test test";
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doString(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
    }

    /**
     * This test checks that a String value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testStringMethodCall2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doString('test me');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals("test me", resultObject);
    }

    /**
     * This test checks that an integer value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testIntMethodCall() {
        testObject = new HelperObject();
        final int value = 75;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doInt(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
    }

    /**
     * This test checks that an integer value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testIntMethodCall2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doInt(5);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(5, resultObject);
    }

    /**
     * This test checks that a boolean value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testBooleanMethodCall() {
        testObject = new HelperObject();
        final boolean value = true;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doBoolean(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
    }

    /**
     * This test checks that a boolean value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testBooleanMethodCall2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doBoolean(true);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(true, resultObject);
    }

    /**
     * This test checks that a double value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testDoubleMethodCall() {
        testObject = new HelperObject();
        final double value = 2.75;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doDouble(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, (Double)resultObject, precision);
    }

    /**
     * This test checks that a double value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testDoubleMethodCall2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doDouble(2.75);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(2.75, (Double)resultObject, precision);
    }

    /**
     * This test checks that a float value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testFloatMethodCall() {
        testObject = new HelperObject();
        final float value = (float)3.1;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doFloat(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, (Double)resultObject, precision);
    }

    /**
     * This test checks that a float value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testFloatMethodCall2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doFloat(3.1);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(3.1, (Double)resultObject, precision);
    }

    /**
     * This test checks that a char value can be passed to method call
     * and returned back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19204
     * http://javafx-jira.kenai.com/browse/RT-19193
     */
    @Test(timeout=10000)
    public void testCharMethodCallA() {
        testObject = new HelperObject();
        final char value = 'n';
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doChar(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
    }

    /**
     * This test checks that a char value can be passed to method call
     * and returned back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19193
     */
    @Test(timeout=10000)
    public void testCharMethodCallA2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doChar('n');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals('n', resultObject);
    }

    /**
     * This test checks that a char value can be passed to method call
     * and returned back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19204
     */
    @Test(timeout=10000)
    public void testCharMethodCallB() {
        testObject = new HelperObject();
        final char value = 11;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doChar(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals((int)value, resultObject);
    }

    /**
     * This test checks that a char value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testCharMethodCallB2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doChar(11);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(11, resultObject);
    }

    /**
     * This test checks that an Object value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testObjectMethodCall() {
        testObject = new HelperObject();
        final Object value = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testValue", value);
                resultObject = engine.executeScript("testObject.doObject(testValue);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(value, resultObject);
    }

    private Object helperO;
    /**
     * This test checks that an Object value can be passed to method call
     * and returned back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19209
     * Currently crashes.
     */
    @Test(timeout=10000)
    public void testObjectMethodCall2() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                helperO = engine.executeScript("var o = new Object(); o;");
                resultObject = engine.executeScript("testObject.doObject(o);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(helperO, resultObject);
    }

    /**
     * This test checks that a null value can be passed to method call
     * and returned back without being damaged.
     */
    @Test(timeout=10000)
    public void testObjectMethodCallNull() {
        testObject = new HelperObject();
        resultObject = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doObject(null);");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(null, resultObject);
    }

    /**
     * This test checks that a void return value is handled properly.
     */
    @Test(timeout=10000)
    public void testVoidMethodCall() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.justDoIt();");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(UNDEFINED, resultObject);
    }
}
