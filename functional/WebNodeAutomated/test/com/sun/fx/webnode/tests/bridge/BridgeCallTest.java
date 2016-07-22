/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
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
 * Tests for JSObject.call method.
 * @author Irina Latysheva
 */
public class BridgeCallTest extends BridgeTestClass  {
    
    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for simple JSObject.call call.
     * Also ensures proper result conversion for an array to JSObject with index.
     */
    @Test(timeout = 10000)
    public void testCallArrayResult() {
        Platform.runLater(() -> {
            initWebEngine();
            JSObject stringO = getString(engine);
            Object[] args = {SPLIT_SMB};
            JSObject splitResult = (JSObject) stringO.call("split", args);
            resultObject = new Object[]{splitResult.getSlot(0), splitResult.getSlot(1)};
            System.out.println(resultObject);
        });
        doWait(() -> (resultObject != null));
        Object[] result = (Object[]) resultObject;
        Assert.assertEquals(TEST_STR_SPLIT[0], result[0]);
        Assert.assertEquals(TEST_STR_SPLIT[1], result[1]);
    }

    /**
     * Test for simple JSObject.call call.
     * Associated CR: http://javafx-jira.kenai.com/browse/RT-14174
     */
    @Test(timeout=10000)
    public void testCallSimpleResult(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject stringO = getString(engine);
                Object []args = {index};
                resultObject = stringO.call("charAt", args);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(CHARAT_RES, resultObject);
    }

    /**
     * Test for JSObject.call call with empty arguments.
     */
    @Test(timeout=10000)
    public void testCallEmptyArgs(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject stringO = getString(engine);
                Object []args = {};
                resultObject = stringO.call("toUpperCase", args);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST_STRING.toUpperCase(), resultObject);
    }

    /**
     * Test for JSObject.call call with null instead of variable arguments array.
     * Associated CR: http://javafx-jira.kenai.com/browse/RT-14175
     */
    @Test(timeout=10000)
    public void testCallNullArgs(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject stringO = getString(engine);
                resultObject = stringO.call("toUpperCase", (Object)null);
                System.out.println(resultObject);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(TEST_STRING.toUpperCase(), resultObject);
    }

    /**
     * Test for JSObject.call call for an absent method.
     */
    @Test(timeout=10000)
    public void testCallNoSuchMethod(){
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                JSObject stringO = getString(engine);
                Object []args = {};
                resultObject = stringO.call("wrongName", args);
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
    
}
