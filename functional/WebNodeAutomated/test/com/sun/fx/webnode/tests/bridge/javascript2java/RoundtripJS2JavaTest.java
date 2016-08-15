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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * These tests check that passing a value from JavaScript to Java and back
 * doesn't modify it.
 * @author Irina Grineva
 */
public class RoundtripJS2JavaTest extends JavaScript2JavaBridgeTestClass {
    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * This test checks that a string can be passed from JS to Java
     * without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSString() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript(
                        "var myVar = 'test';"
                        // Dumping values into Java...
                        + "testObject.stringField = myVar;"
                        // ...and collecting them back.
                        + "var myVar2 = testObject.stringField;"
                        + "myVar == myVar2;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(Boolean.TRUE, resultObject);
    }

    /**
     * This test checks that an integer can be passed from JS to Java
     * without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSInt() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript(
                        "var myVar = 125;"
                        // Dumping values into Java...
                        + "testObject.intField = myVar;"
                        // ...and collecting them back.
                        + "var myVar2 = testObject.intField;"
                        + "myVar == myVar2;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(Boolean.TRUE, resultObject);
    }

    /**
     * This test checks that a boolean can be passed from JS to Java
     * without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSBool() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript(
                        "var myVar = true;"
                        // Dumping values into Java...
                        + "testObject.booleanField = myVar;"
                        // ...and collecting them back.
                        + "var myVar2 = testObject.booleanField;"
                        + "myVar == myVar2;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(Boolean.TRUE, resultObject);
    }

    /**
     * This test checks that a double can be passed from JS to Java
     * without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19205
     */
    @Test(timeout=10000)
    public void testJava2JSDouble() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript(
                        "var myVar = 2.75;"
                        // Dumping values into Java...
                        + "testObject.doubleField = myVar;"
                        // ...and collecting them back.
                        + "var myVar2 = testObject.doubleField;"
                        // Why don't we need a hack here?
                        + "myVar == myVar2;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(Boolean.TRUE, resultObject);
    }

    /**
     * This test checks that a float can be passed from JS to Java
     * without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19205
     */
    @Test(timeout=10000)
    public void testJava2JSFloat() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript(
                        "var myVar = 2.755;"
                        // Dumping values into Java...
                        + "testObject.floatField = myVar;"
                        // ...and collecting them back.
                        + "var myVar2 = testObject.floatField;"
                        // Hack needed to compare float vs double (as JS default)
                        + "myVar.toFixed(3) == myVar2.toFixed(3);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(Boolean.TRUE, resultObject);
    }

    /**
     * This test checks that the char can be passed from JS to Java
     * without being damaged.
     * As we have no "char" in JS, this test is meaningless by now.
     */
//    @Test(timeout=10000)
//    public void testJava2JSChar() {
//    }

    /**
     * This test checks that an object can be passed from JS to Java
     * without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSObject() {
        testObject = new HelperObject();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript(
                        "var myVar = window;"
                        // Dumping values into Java...
                        + "testObject.objectField = myVar;"
                        // ...and collecting them back.
                        + "var myVar2 = testObject.objectField;"
                        + "myVar == myVar2;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(Boolean.TRUE, resultObject);
    }
}
