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
 * Tests for JSObject.removeMember method.
 * @author Irina Latysheva
 */
public class BridgeRemoveMemberTest extends BridgeTestClass {

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for JSObject.removeMember call on existing object prototype member.
     * JSObject.removeMember should do nothing in this case.
     */
    @Test(timeout=10000)
    public void testRemoveMember(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject strO = getString(engine);
                strO.removeMember("length");
                resultObject = strO.getMember("length");
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST_STRING.length(), resultObject);
    }

    /**
     * Test for simple JSObject.removeMember call on a previously added member.
     */
    @Test(timeout=10000)
    public void testRemoveAddedMember(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject strO = getString(engine);
                strO.setMember("iAmNew", "!!!");
                strO.removeMember("iAmNew");
                resultObject = strO.getMember("iAmNew");
                System.out.println(resultObject);
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
     * Test for JSObject.removeMember call on an absent member.
     */
    @Test(timeout=10000)
    public void testRemoveAbsentMember(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject strO = getString(engine);
                strO.removeMember("iAmWrongMember");
                // We just check that nothing bad happens here.
                resultObject = new Object();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
    }
}
