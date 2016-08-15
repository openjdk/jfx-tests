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

package com.sun.fx.webnode.tests.bridge;

import java.util.concurrent.CountDownLatch;
import com.sun.fx.webnode.tests.commonUtils.BridgeTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import netscape.javascript.JSObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for simple JavaScript to Java type conversions.
 * @author Irina Latysheva
 */
public class BridgeSimpleTest extends BridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    private static void printResult(Object resultObject, CountDownLatch lock) {
        Platform.runLater(new Runnable() {
            public void run() {
            System.out.println("resultObject: " + resultObject);
            lock.countDown();
            }
        });
    }

    /**
     * Test for JavaScript integer to java.lang.Integer conversion.
     */
    @Test(timeout=10000)
    public void testInteger() throws InterruptedException {
        resultObject = null;

        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("2 + 2");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertTrue(resultObject instanceof java.lang.Integer);
        Assert.assertEquals(4, ((java.lang.Integer)resultObject).intValue());
    }

    /**
     * Test for JavaScript double to java.lang.Double conversion.
     */
    @Test(timeout=10000)
    public void testDouble() throws InterruptedException {
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("3 / 2");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertTrue(resultObject instanceof java.lang.Double);
        Assert.assertEquals(1.5, ((java.lang.Double)resultObject).doubleValue(), 0.0000001);
    }

    /**
     * Test for JavaScript string to java.lang.String conversion.
     */
    @Test(timeout=10000)
    public void testString() throws InterruptedException {
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("'test' + 'me'");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertTrue(resultObject instanceof java.lang.String);
        Assert.assertEquals((java.lang.String)resultObject, "testme");
    }

    /**
     * Test for JavaScript boolean to java.lang.Boolean conversion.
     */
    @Test(timeout=10000)
    public void testBoolean() throws InterruptedException {
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("5 == 5");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertTrue(resultObject instanceof java.lang.Boolean);
        Assert.assertTrue(((java.lang.Boolean)resultObject).booleanValue());
    }

    /**
     * Test for JavaScript null to Java null conversion.
     */
    @Test(timeout=10000)
    public void testNull() throws InterruptedException {
        resultObject = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("null");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject == null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertNull(resultObject);
    }

    /**
     * Test for JavaScript undefined to UNDEFINED conversion.
     */
    @Test(timeout=10000)
    public void testUndefined() throws InterruptedException {
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("alert('AAA!');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertEquals(UNDEFINED, resultObject);
    }

    /**
     * Test for JavaScript object to JSObject conversion.
     */
    @Test(timeout=10000)
    public void testJSObject() throws InterruptedException {
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("new Object()");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertTrue(resultObject instanceof JSObject);
    }

    /**
     * Test for JavaScript DOM object to JSNode conversion.
     */
    @Test(timeout=10000)
    public void testDOMObject() throws InterruptedException {
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                resultObject = engine.executeScript("document.createElement('span')");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });

        final CountDownLatch lock = new CountDownLatch(1);
        printResult(resultObject, lock);
        lock.await();
        Assert.assertTrue(resultObject instanceof org.w3c.dom.Node);
    }
}
