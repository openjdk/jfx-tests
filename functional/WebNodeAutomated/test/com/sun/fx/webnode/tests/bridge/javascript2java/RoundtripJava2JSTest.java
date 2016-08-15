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
 * These tests check that passing a value to JavaScript and back doesn't
 * modify it.
 * @author Irina Grineva
 */
public class RoundtripJava2JSTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    {
        testObject = new HelperObject("test", 42, false, 42.2, (float)42.3, 'o', new Object(), "\u16cf");
    }
    private HelperObject containerObject;

    /**
     * This test checks that the value of a string field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSString() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var strVar = testObject.stringField;"
                        // ...and collecting them back.
                        + "containerObject.stringField = strVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).stringField, containerObject.stringField);
    }

    /**
     * This test checks that the value of a string field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSString2() {
        containerObject = new HelperObject();
        final String testValue = "test me";
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var strVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.stringField = strVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.stringField);
    }

    /**
     * This test checks that the value of a unicode string field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSUnicode() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var strVar = testObject.unicodeField;"
                        // ...and collecting them back.
                        + "containerObject.unicodeField = strVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).unicodeField, containerObject.unicodeField);
    }

    /**
     * This test checks that the value of a unicode string field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSUnicode2() {
        containerObject = new HelperObject();
        final String testValue = "\u16cf";
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var strVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.stringField = strVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.stringField);
    }

    /**
     * This test checks that the value of an integer field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSInt() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var intVar = testObject.intField;"
                        // ...and collecting them back.
                        + "containerObject.intField = intVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).intField, containerObject.intField);
    }

    /**
     * This test checks that the value of an integer field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSInt2() {
        containerObject = new HelperObject();
        final int testValue = 42;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var intVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.intField = intVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.intField);
    }

    /**
     * This test checks that the value of a boolean field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSBoolean() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var boolVar = testObject.booleanField;"
                        // ...and collecting them back.
                        + "containerObject.booleanField = boolVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).booleanField, containerObject.booleanField);
    }

    /**
     * This test checks that the value of a boolean field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSBoolean2() {
        containerObject = new HelperObject();
        final boolean testValue = true;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var boolVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.booleanField = boolVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.booleanField);
    }

    /**
     * This test checks that the value of a double field
     * can be passed to JS and back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19205
     */
    @Test(timeout=10000)
    public void testJava2JSDouble() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var doubleVar = testObject.doubleField;"
                        // ...and collecting them back.
                        + "containerObject.doubleField = doubleVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).doubleField, containerObject.doubleField, precision);
    }

    /**
     * This test checks that the value of a double field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSDouble2() {
        containerObject = new HelperObject();
        final double testValue = 42.2;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var doubleVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.doubleField = doubleVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.doubleField, precision);
    }

    /**
     * This test checks that the values of a float field
     * can be passed to JS and back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19205
     */
    @Test(timeout=10000)
    public void testJava2JSFloat() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var floatVar = testObject.floatField;"
                        // ...and collecting them back.
                        + "containerObject.floatField = floatVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).floatField, containerObject.floatField, precision);
    }

    /**
     * This test checks that the values of a float field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSFloat2() {
        containerObject = new HelperObject();
        final float testValue = (float) 42.3;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var floatVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.floatField = floatVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.floatField, precision);
    }

    /**
     * This test checks that the value of a character field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSChar() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var charVar = testObject.charField;"
                        // ...and collecting them back.
                        + "containerObject.charField = charVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).charField, containerObject.charField);
    }

    /**
     * This test checks that the value of a character field
     * can be passed to JS and back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19204
     */
    @Test(timeout=10000)
    public void testJava2JSChar2() {
        containerObject = new HelperObject();
        final char testValue = 'o';
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var charVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.charField = charVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.charField);
    }

    /**
     * This test checks that the values of an object field
     * can be passed to JS and back without being damaged.
     */
    @Test(timeout=10000)
    public void testJava2JSObject() {
        containerObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var objVar = testObject.objectField;"
                        // ...and collecting them back.
                        + "containerObject.objectField = objVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(((HelperObject)testObject).objectField, containerObject.objectField);
    }

    /**
     * This test checks that the values of an object field
     * can be passed to JS and back without being damaged.
     * http://javafx-jira.kenai.com/browse/RT-19209
     */
    @Test(timeout=10000)
    public void testJava2JSObject2() {
        containerObject = new HelperObject();
        final Object testValue = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testValue);
                window.setMember("containerObject", containerObject);
                engine.executeScript(
                        // Dumping values into JS...
                        "var objVar = testObject;"
                        // ...and collecting them back.
                        + "containerObject.objectField = objVar;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testValue, containerObject.objectField);
    }

}
