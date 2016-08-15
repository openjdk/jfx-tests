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
 * These tests check that situations when an absent member is accessed is handled
 * properly.
 * @author Irina Grineva
 */
public class AbsentMembersTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * This test checks that the situation when JS is trying to access an absent
     * field of an exposed object is handled properly.
     */
    @Test(timeout=10000)
    public void testAccessAbsentField() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.absent;");
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
     * This test checks that the situation when JS is trying to access a null
     * field of an exposed object is handled properly.
     */
    @Test(timeout=10000)
    public void testAccessNullField() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                resultObject = engine.executeScript("testObject.null;");
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
     * This test checks that the situation when JS is trying to access an absent
     * method of an exposed object is handled properly.
     */
    @Test(timeout=10000)
    public void testAccessAbsentMethod() {
        final String UNDEFINED_FUNCTION_ABSENT_MSG = "TypeError: 'undefined' is not a function (evaluating 'testObject.absent()')";
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                try {
                    resultObject = engine.executeScript("testObject.absent();");
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(UNDEFINED_FUNCTION_ABSENT_MSG));
    }

    /**
     * This test checks that the situation when JS is trying to access a null
     * method of an exposed object is handled properly.
     */
    @Test(timeout=10000)
    public void testAccessNullMethod() {
        final String UNDEFINED_FUNCTION_NULL_MSG = "TypeError: 'undefined' is not a function (evaluating 'testObject.null()')";
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testObject", testObject);
                try {
                    resultObject = engine.executeScript("testObject.null();");
                } catch (JSException e) {
                    resultObject = e;
                    // TODO: Should be some way to check this message!
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
        Assert.assertTrue(((JSException) resultObject).getMessage().equals(UNDEFINED_FUNCTION_NULL_MSG));
    }
}
