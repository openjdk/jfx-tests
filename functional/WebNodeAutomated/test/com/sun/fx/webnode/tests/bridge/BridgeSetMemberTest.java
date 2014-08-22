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
 * Tests for JSObject.setMember method.
 * @author Irina Latysheva
 */
public class BridgeSetMemberTest extends BridgeTestClass {

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for simple JSObject.setMember call.
     */
    @Test(timeout=10000)
    public void testSetMember(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject winO = getWindow(engine);
                winO.setMember("status", TEST_STRING);
                resultObject = winO.getMember("status");
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
     * Test for JSObject.setMember call with an absent member name as a parameter.
     */
    @Test(timeout=10000)
    public void testSetAbsentMember(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject strO = getString(engine);
                strO.setMember("iAmWrongMember", TEST_STRING);
                resultObject = strO.getMember("iAmWrongMember");
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
     * Test for JSObject.setMember call with null as value.
     * CR associated with this test failure:
     * http://javafx-jira.kenai.com/browse/RT-14178
     */
    @Test(timeout=10000)
    public void testSetMemberNull(){
        resultObject = new Object();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject strO = getString(engine);
                strO.setMember("iAmWrongMember", null);
                resultObject = strO.getMember("iAmWrongMember");
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
