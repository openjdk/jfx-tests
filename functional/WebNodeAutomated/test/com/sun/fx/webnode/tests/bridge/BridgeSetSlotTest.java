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

import com.sun.fx.webnode.tests.commonUtils.BridgeTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import netscape.javascript.JSObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for JSObject.setSlot method.
 * @author Irina Latysheva
 */
public class BridgeSetSlotTest extends BridgeTestClass {

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for simple JSObject.setSlot call.
     * Associated CR: http://javafx-jira.kenai.com/browse/RT-14241
     */
    @Test(timeout=10000)
    public void testSetSlotByIdArray(){
        final int TEST = 123;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject arrO = getArray(engine);
                Object prevO = arrO.getSlot(0);
                arrO.setSlot(0, TEST); // We have an array of Integer back there.
                resultObject = arrO.getSlot(0);
                Assert.assertTrue(!prevO.equals(resultObject));
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST, resultObject);
    }

    private Object previous;
    /**
     * Test for simple JSObject.setSlot call: to ensure that we behave the same way as Chrome and Firefox
     * when setting a slot of a String object.
     * Associated CR: http://javafx-jira.kenai.com/browse/RT-14241
     */
    @Test(timeout=10000)
    public void testSetSlotByIdString(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject arrO = getString(engine);
                previous = arrO.getSlot(0);
                arrO.setSlot(0, 'o'); // We have a String object back there.
                resultObject = arrO.getSlot(0);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(previous, resultObject);
    }

    /**
     * Test for JSObject.setSlot call on a new slot.
     * Associated CR: http://javafx-jira.kenai.com/browse/RT-14241
     */
    @Test(timeout=10000)
    public void testSetSlotNewSlot(){
        final int TEST = 123;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject arrO = getArray(engine);
                arrO.setSlot(3, TEST); // We have an array of Integer back there.
                resultObject = arrO.getSlot(3);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST, resultObject);
    }

    /**
     * Test for JSObject.setSlot call with a type different from current.
     */
    @Test(timeout=10000)
    public void testSetSlotMisType(){
        final String TEST = "oOo";
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject arrO = getArray(engine);
                Object prevO = arrO.getSlot(0);
                arrO.setSlot(0, TEST); // We have an array of Integer back there.
                resultObject = arrO.getSlot(0);
                Assert.assertTrue(!prevO.equals(resultObject));
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST, resultObject);
    }

    /**
     * Test for JSObject.setSlot call on an object with no slots available.
     */
    @Test(timeout=10000)
    public void testSetSlotNoSlots(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject winO = getWindow(engine);
                winO.setSlot(0, TEST_STRING);
                resultObject = winO.getSlot(0);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST_STRING, resultObject);
    }

    /**
     * Test for JSObject.setSlot call with -1 slot number.
     * Associated CR: http://javafx-jira.kenai.com/browse/RT-14241
     */
    @Test(timeout=10000)
    public void testSetSlotBadSlot(){
        final int TEST = 123;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject arrO = getArray(engine);
                arrO.setSlot(-1, TEST);
                resultObject = arrO.getSlot(-1);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST, resultObject);
    }

    /**
     * Test for JSObject.setSlot call setting a null value.
     * Associated CR: http://javafx-jira.kenai.com/browse/RT-14178
     */
    @Test(timeout=10000)
    public void testSetSlotNull(){
        resultObject = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject arrO = getArray(engine);
                arrO.setSlot(5, null); // We have an array of Integer back there.
                resultObject = arrO.getSlot(5);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject == null);
            }
        });
        Assert.assertNull(resultObject);
    }
}
