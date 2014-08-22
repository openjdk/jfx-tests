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
import javafx.event.EventHandler;
import javafx.scene.web.WebEngine;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Before;
import javafx.scene.web.WebEvent;
/**
 * Test for javafx.scene.web.UI.alert()
 * UI.alert() should be called when JavaScript window.alert() happens.
 * @author Irina Latysheva
 */
public class AlertTest extends GenericTestClass implements EventHandler<WebEvent<String>>{

    private boolean alertCalled = false;
    private String messageReceived = null;
    private boolean correctHandlerReturned = false;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }


    @Before
    public void cleanup(){
        alertCalled = false;
        messageReceived = null;
        correctHandlerReturned = false;
    }

    /**
     * Test for javafx.scene.web.WebEngine onAlert handlers are called. Uses loading document with window.alert JavaScript
     * executed on document load with associated WebEngine.
     */
    @Test(timeout=10000)
    public void testAlertInHtml() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.load(getPath(this.getClass(), "resource/alertTest.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        Assert.assertTrue(alertCalled);
        Assert.assertTrue(correctHandlerReturned);
    }

    /**
     * Test for javafx.scene.web.WebEngine alert handlers. Uses passing window.alert JavaScript
     * to associated WebEngine.
     */
    @Test(timeout=10000)
    public void testAlertDirectCallFromEngine() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.alert('Test alert.');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (alertCalled && (messageReceived!=null) && messageReceived.equals("Test alert."));
            }
        });
    }

    protected final EventHandler thisHandler = this;    // Test hack.
    /**
     * Test for javafx.scene.web.WebEngine.onAlertProperty. Checks that the property
     * holds the same handler as javafx.scene.web.WebEngine.getOnAlert().
     * Also checks that the handler is the same object as the test's *this*.
     */
    @Test(timeout=10000)
    public void testOnAlertProperty() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (engine != null)
                        && (engine.onAlertProperty().get() == engine.getOnAlert())
                        && (engine.onAlertProperty().get() == thisHandler);
            }
        });
    }

    public void handle(WebEvent<String> t) {
        messageReceived = t.getData();
        alertCalled = true;
    }


    private void initEngine(){
        engine = new WebEngine();
        engine.setOnAlert(this);
        correctHandlerReturned = (this == engine.getOnAlert());
    }
}
