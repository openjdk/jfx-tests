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
import javafx.scene.web.PromptData;
import javafx.util.Callback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for javafx.scene.web.WebEngine prompt handler
 * UI.prompt() should be called when JavaScript window.prompt() happens.
 * @author Irina Latysheva
 */
public class PromptTest extends GenericTestClass implements Callback<PromptData, String>{

    private boolean promptCalled = false;
    private String messageReceived = null;
    private String defaultText = null;
    private boolean correctHandlerReturned = false;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }


    @Before
    public void cleanup(){
        promptCalled = false;
        messageReceived = null;
        defaultText = null;
        correctHandlerReturned = false;
    }

    /**
     * Test for javafx.scene.web.WebEngine prompt handler. Uses loading document with window.prompt JavaScript
     * executed on document load with associated WebEngine.
     */
    @Test(timeout=10000)
    public void testPromptCalledFromHTML() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.load(getPath(this.getClass(), "resource/promptTest.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        Assert.assertTrue(promptCalled);
        Assert.assertTrue(correctHandlerReturned);
    }

    /**
     * Test for javafx.scene.web.WebEngine prompt handler. Uses passing window.prompt JavaScript
     * to associated WebEngine.
     */
    @Test(timeout=10000)
    public void testPromptCalledFromJSFromEngineNoDefaultText() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.prompt('Test prompt?');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (promptCalled && (messageReceived!=null) && messageReceived.equals("Test prompt?") && defaultText==null);
            }
        });
    }



    /**
     * Test for javafx.scene.web.WebEngine prompt handler. Uses passing window.prompt JavaScript
     * to associated WebEngine with default text for prompt.
     */
    @Test(timeout=10000)
    public void testPromptCalledFromJSFromEngineDefaultText() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.prompt('Test prompt?','Default text');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (promptCalled && (messageReceived!=null) && messageReceived.equals("Test prompt?")
                        && defaultText!=null && defaultText.equals("Default text"));
            }
        });
    }

    protected final Callback thisHandler = this;    // Test hack.
    /**
     * Test for javafx.scene.web.WebEngine.promptHandlerProperty. Checks that the property
     * holds the same handler as javafx.scene.web.WebEngine.getPromptHandler().
     * Also checks that the handler is the same object as the test's *this*.
     */
    @Test(timeout=10000)
    public void testPromptHandlerProperty() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (engine != null)
                        && (engine.promptHandlerProperty().get() == engine.getPromptHandler())
                        && (engine.promptHandlerProperty().get() == thisHandler);
            }
        });
    }

    public String call(PromptData p) {
        defaultText = p.getDefaultValue();
        messageReceived = p.getMessage();
        promptCalled = true;
        return "response";
    }


    private void initEngine(){
        engine = new WebEngine();
        engine.setPromptHandler(this);
        correctHandlerReturned = (this == engine.getPromptHandler());
    }






}
