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
 * Tests for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding methods called in sequence (more or less).
 * @author Irina Grineva
 */
public class AddRemoveJavaScriptBindingTest extends JavaScript2JavaBridgeTestClass {

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding call.
     * Checks that a situation with malformed binding name is handled nicely when adding and removing binding.
     * TODO: check other malformed binding names after Per answers!
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingMalformedNameDot() {
        testObject = new Object();
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test.Object", testObject);
                window.removeMember("test.Object");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding call.
     * Checks that a situation with malformed binding name is handled nicely when adding and removing binding.
     * TODO: check other malformed binding names after Per answers!
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingMalformedNameHash() {
        testObject = new Object();
        ready = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                window.setMember("test#Object", testObject);
                window.removeMember("test#Object");
                ready = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return ready;
            }
        });
    }

    /**
     * Test for WebEngine.addJavaScriptBinding and WebEngine.removeJavaScriptBinding call.
     * Checks that nothing nasty happens when binding an unbinding
     * an object using the name that is already used.
     */
    @Test(timeout=10000)
    public void testAddRemoveJavaScriptBindingNameExists() {
        testObject = new Object();
        resultObject = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngineAndWindow();
                containerObject = engine.executeScript("window;");
                window.setMember("window", testObject);
                window.removeMember("window");
                resultObject = engine.executeScript("window;");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resultObject != null);
            }
        });
        Assert.assertEquals(containerObject, resultObject);
    }

}
