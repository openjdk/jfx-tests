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
 * Tests for accessing fields of a bound object with different access modifiers.
 * @author Irina Grineva
 */
public class AccessModifiersTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for accessing a public field.
     * Checks that public field of a bound object is accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testPublicField() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.publicField;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(((HelperObject)testObject).publicField, resultObject);
    }

    /**
     * Test for accessing a protected field.
     * Checks that protected field of a bound object is not accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testProtectedField() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.protectedField;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(UNDEFINED, resultObject);
    }

    /**
     * Test for accessing a private field.
     * Checks that private field of a bound object is not accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testPrivateField() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.privateField;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(UNDEFINED, resultObject);
    }

    /**
     * Test for accessing a public method.
     * Checks that public method of a bound object is accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testPublicMethod() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.doSomethingPublic();");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(((HelperObject)testObject).publicField, resultObject);
    }

    /**
     * Test for accessing a protected method.
     * Checks that protected method of a bound object is not accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testProtectedMethod() {
        final String UNDEFINED_FUNCTION_PROTECTED_MSG = "TypeError: 'undefined' is not a function (evaluating 'testObject.protectedMethod()')";
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                try {
                    resultObject = engine.executeScript("testObject.protectedMethod();");
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(UNDEFINED_FUNCTION_PROTECTED_MSG));
    }

    /**
     * Test for accessing a private method.
     * Checks that private method of a bound object is not accessible from JavaScript.
     */
    @Test(timeout=10000)
    public void testPrivateMethod() {
        final String UNDEFINED_FUNCTION_PRIVATE_MSG = "TypeError: 'undefined' is not a function (evaluating 'testObject.privateMethod()')";
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                try {
                    resultObject = engine.executeScript("testObject.privateMethod();");
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(UNDEFINED_FUNCTION_PRIVATE_MSG));
    }
}
