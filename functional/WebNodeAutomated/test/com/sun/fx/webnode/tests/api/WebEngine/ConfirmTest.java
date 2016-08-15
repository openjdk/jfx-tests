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

package com.sun.fx.webnode.tests.api.WebEngine;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import java.io.File;
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javafx.util.Callback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for javafx.scene.web.UI.confirm()
 * UI.confirm() should be called when JavaScript window.confirm() happens.
 * @author Irina Latysheva
 */
public class ConfirmTest extends GenericTestClass implements Callback<String, Boolean>{

    private boolean confirmCalled = false;
    private String messageReceived = null;
    private boolean correctHandlerReturned = false;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }


    @Before
    public void cleanup(){
        confirmCalled = false;
        messageReceived = null;
        correctHandlerReturned = false;
    }

    /**
     * Test for javafx.scene.web.WebEngine confirm handlers. Uses loading document with window.confirm JavaScript
     * executed on document load with associated WebEngine.
     */
    @Test(timeout=10000)
    public void testConfirmFromHTMLJavascript() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.load(getPath(this.getClass(), "resource/confirmTest.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        Assert.assertTrue(confirmCalled);
        Assert.assertTrue(correctHandlerReturned);
    }

    /**
     * Test for javafx.scene.web.WebEngine confirm handlers.  Uses passing window.confirm JavaScript
     * to associated WebEngine.
     */
    @Test(timeout=10000)
    public void testConfirmCallFromJavaScriptFromEngine() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.confirm('Test confirm?');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (confirmCalled && (messageReceived!=null) && messageReceived.equals("Test confirm?"));
            }
        });
    }

    protected final Callback thisHandler = this;    // Test hack.
    /**
     * Test for javafx.scene.web.WebEngine.confirmHandlerProperty. Checks that the property
     * holds the same handler as javafx.scene.web.WebEngine.getConfirmHandler().
     * Also checks that the handler is the same object as the test's *this*.
     */
    @Test(timeout=10000)
    public void testConfirmHandlerProperty() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (engine != null)
                        && (engine.confirmHandlerProperty().get() == engine.getConfirmHandler())
                        && (engine.confirmHandlerProperty().get() == thisHandler);
            }
        });
    }


    private void initEngine(){
        engine = new WebEngine();
        engine.setConfirmHandler(this);
        correctHandlerReturned = (this == engine.getConfirmHandler());
    }

    public Boolean call(String p) {
        messageReceived = p;
        confirmCalled = true;
        return true;
    }


}
