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
 * Tests for setting fields of a bound object.
 * @author Irina Grineva
 */
public class AssignTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for setting a public integer field.
     * Checks that public integer field of a bound object
     * can be set from JavaScript.
     */
    @Test(timeout=10000)
    public void testSetIntField() {
        ready = false;
        testObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.intField = 42;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(42, ((HelperObject)testObject).intField);
    }

    /**
     * Test for setting a public character (char) field.
     * Checks that public character field of a bound object
     * can be set from JavaScript.
     * http://javafx-jira.kenai.com/browse/RT-19193
     */
    @Test(timeout=10000)
    public void testSetCharField() {
        ready = false;
        testObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.charField = 'o';");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals('o', ((HelperObject)testObject).charField);
    }

    /**
     * Test for setting a public character (char) field.
     * Checks that public character field of a bound object
     * can be set from JavaScript.
     * http://javafx-jira.kenai.com/browse/RT-19193
     */
    @Test(timeout=10000)
    public void testSetCharAsNumericField() {
        ready = false;
        testObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.charField = 15;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(15, ((HelperObject)testObject).charField);
    }

    /**
     * Test for setting a public boolean field.
     * Checks that public boolean field of a bound object
     * can be set from JavaScript.
     */
    @Test(timeout=10000)
    public void testSetBooleanField() {
        ready = false;
        testObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.booleanField = true;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(true, ((HelperObject)testObject).booleanField);
    }

    /**
     * Test for setting a public double field.
     * Checks that public double field of a bound object
     * can be set from JavaScript.
     */
    @Test(timeout=10000)
    public void testSetDoubleField() {
        ready = false;
        testObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.doubleField = 42.24;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(42.24, ((HelperObject)testObject).doubleField, precision);
    }

    /**
     * Test for setting a public float field.
     * Checks that public float field of a bound object
     * can be set from JavaScript.
     */
    @Test(timeout=10000)
    public void testSetFloatField() {
        ready = false;
        testObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.floatField = 42.42;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(42.42, ((HelperObject)testObject).floatField, precision);
    }

    /**
     * Test for setting a public string field.
     * Checks that public string field of a bound object
     * can be set from JavaScript.
     */
    @Test(timeout=10000)
    public void testSetStringField() {
        ready = false;
        testObject = new HelperObject("");
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.stringField = 'test';");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals("test", ((HelperObject)testObject).stringField);
    }

    /**
     * Test for setting a public integer field.
     * Checks that public integer field of a bound object
     * can be set to null from JavaScript, and null is converted to "0".
     */
    @Test(timeout=10000)
    public void testSetIntFieldNull() {
        ready = false;
        testObject = new HelperObject();
        ((HelperObject)testObject).intField = 1;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.intField = null;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(0, ((HelperObject)testObject).intField);
    }

    /**
     * Test for setting a public character (char) field.
     * Checks that public character field of a bound object
     * can be set to null from JavaScript, and null is converted to "0".
     */
    @Test(timeout=10000)
    public void testSetCharFieldNull() {
        ready = false;
        testObject = new HelperObject();
        ((HelperObject)testObject).intField = 1;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.charField = null;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(0, ((HelperObject)testObject).charField);
    }

    /**
     * Test for setting a public double field.
     * Checks that public double field of a bound object
     * can be set to null from JavaScript, and null is converted to "0".
     */
    @Test(timeout=10000)
    public void testSetDoubleFieldNull() {
        ready = false;
        testObject = new HelperObject();
        ((HelperObject)testObject).doubleField = 1;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.doubleField = null;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(0, ((HelperObject)testObject).intField, precision);
    }

    /**
     * Test for setting a public boolean field.
     * Checks that public boolean field of a bound object
     * can be set to null from JavaScript, and null is converted to "false".
     */
    @Test(timeout=10000)
    public void testSetBooleanFieldNull() {
        ready = false;
        testObject = new HelperObject();
        ((HelperObject)testObject).booleanField = true;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.booleanField = null;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(false, ((HelperObject)testObject).booleanField);
    }

    /**
     * Test for setting a public Object (String) field.
     * Checks that public boolean field of a bound object
     * can be set to null from JavaScript.
     */
    @Test(timeout=10000)
    public void testSetStringFieldNull() {
        ready = false;
        testObject = new HelperObject();
        ((HelperObject)testObject).stringField = "test";
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.stringField = null;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(null, ((HelperObject)testObject).stringField);
    }

    /**
     * Test for setting a public object field.
     * Checks that public object field of a bound object can be set
     * to a string value from JavaScript. This proves a possibility to set
     * values to fields when type cast is possible.
     */
    @Test(timeout=10000)
    public void testSetObjectFieldCast() {
        ready = false;
        testObject = new HelperObject();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject.objectField = 'test';");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals("test", ((HelperObject)testObject).objectField);
    }

    /**
     * Test for setting a public double field.
     * Checks that public double field of a bound object can be set
     * to a float value from JavaScript. This proves a possibility to set
     * values to fields when type cast is possible.
     */
    @Test(timeout=10000)
    public void testSetObjectFieldCast2() {
        ready = false;
        testObject = new HelperObject();
        ((HelperObject)testObject).doubleField = -1.9;
        final Float floatO = new Float(42.2);
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("floatObject", floatO);
                engine.executeScript("testObject.doubleField = floatObject;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(42.2, ((HelperObject)testObject).doubleField, precision);
    }

    /**
     * Test for setting a public field with the value it can't accept.
     * TODO: finish after RT-19119 is evaluated.
     */
//    @Test(timeout=10000)
//    public void testSetObjectFieldCantCast() {
//        b[0] = false;
//        testObject = new HelperObject();
//        ((HelperObject)testObject).intField = 42;
//        Platform.runLater(new Runnable() {
//            public void run() {
//                initWebEngineAndWindow();
//                window.setMember("testObject", testObject);
//                engine.executeScript("testObject.intField = 'test';");
//                b[0] = true;
//            }
//        });
//        doWait(new Tester() {
//            public boolean isPassed() {
//                return (b[0] == true);
//            }
//        });
//        Assert.assertEquals(Integer.MIN_VALUE, ((HelperObject)testObject).intField);
//    }

    /**
     * Test for setting a new value to the exposed object.
     * This test checks that an object itself can't be changed from JS code
     * by assigning another exposed object to it.
     */
    @Test(timeout=10000)
    public void testSetObjectToObject() {
        ready = false;
        testObject = new Object();
        Object testObjectHardLink = testObject;
        final Object testObject2 = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                window.setMember("testObject2", testObject2);
                engine.executeScript("testObject = testObject2;");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testObjectHardLink, testObject);
    }

    /**
     * Test for setting a new value to the exposed object.
     * This test checks that an object itself can't be changed from JS code
     * by assigning an arbitrary JS object to it.
     */
    @Test(timeout=10000)
    public void testSetStringToObject() {
        ready = false;
        testObject = new Object();
        Object testObjectHardLink = testObject;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                engine.executeScript("testObject = new String('TEST!');");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
        Assert.assertEquals(testObjectHardLink, testObject);
    }
}
