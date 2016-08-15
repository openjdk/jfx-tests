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
import javafx.application.Platform;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * These tests check that JavaScript and Java
 * handle and exchange array objects properly.
 * http://javafx-jira.kenai.com/browse/RT-19092
 * TODO: add deleting element and adding extra element tests after RT-19092
 * is fixed.
 * @author Irina Grineva
 */
public class ArraysTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Simple test for accessing an element of bound array from JavaScript.
     */
    @Test(timeout=10000)
    public void testArrayGetElement() {
        arrayToJavaScript = new int[3];
        arrayToJavaScript[0] = 42;
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testArray", arrayToJavaScript);
                resultObject = engine.executeScript("testArray[0];");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(42, resultObject);
    }

    /**
     * Simple test for getting size of bound array from JavaScript.
     */
    @Test(timeout=10000)
    public void testArrayGetLength() {
        arrayToJavaScript = new int[3];
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("testArray", arrayToJavaScript);
                resultObject = engine.executeScript("testArray.length;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(3, resultObject);
    }

    int []arrayToJavaScript;
    int []arrayFromJavaScript;
}
