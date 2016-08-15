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
import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javafx.scene.web.PopupFeatures;
import javafx.util.Callback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for javafx.scene.web.UI.setStatusbarVisible()
 * UI.setStatusbarVisible() should be called when window.open is called.
 * @author Irina Latysheva
 */
public class PopupTest extends GenericTestClass implements Callback<PopupFeatures, WebEngine>{

    private boolean popupCalled = false;
    private boolean hasMenu = false;
    private boolean hasStatus = false;
    private boolean hasToolbar = false;
    private boolean isResizable = false;
    private boolean correctHandlerReturned = false;
    private WebEngine returnedEngine = null;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }


    @Before
    public void cleanup(){
        popupCalled = false;
        hasMenu = false;
        hasStatus = false;
        hasToolbar = false;
        isResizable = false;
        correctHandlerReturned = false;
        returnedEngine = null;
    }

    /**
     * Test for javafx.scene.web.WebEngine popup handler. Uses loading document with window.open JavaScript
     * executed on document load with associated WebEngine.
     */


    @Test(timeout=10000)
    public void testPopupFromHTML() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.load(getPath(this.getClass(), "resource/createNewWindowTest.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        Assert.assertTrue(popupCalled);
        Assert.assertTrue(correctHandlerReturned);
    }



    /**
    *  Test for javafx.scene.web.WebEngine popup handler. Uses passing window.open JavaScript
     * to associated WebEngine.
    */

    @Test(timeout=10000)
    public void testPopupWithoutStatus() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.open('about:blank', 'Test', 'status=no');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (returnedEngine != null)
                        && popupCalled
                        && (hasStatus == false)
                        && "about:blank".equals(returnedEngine.getLocation());
            }
        });
    }


    /**
    *  Test for javafx.scene.web.WebEngine popup handler. Uses passing window.open JavaScript
     * to associated WebEngine.
    */

    @Test(timeout=20000)
    public void testPopupWithoutStatusAndMenu() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.open('about:blank', 'Test', 'status=no,width=200,menubar=no');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return popupCalled
                        && (hasStatus == false)
                        && (hasMenu == false)
                        && "about:blank".equals(returnedEngine.getLocation());
            }
        });
    }


    /**
    *  Test for javafx.scene.web.WebEngine popup handler. Uses passing window.open JavaScript
     * to associated WebEngine.
    */

    @Test(timeout=10000)
    public void testPopupWithoutStatusAndMenuAndToobar() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.open('about:blank', 'Test', 'status=no,width=200,menubar=no,toolbar=no');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return popupCalled
                        && (hasStatus == false)
                        && (hasMenu == false)
                        && (hasToolbar == false)
                        && "about:blank".equals(returnedEngine.getLocation());
            }
        });
    }


    /**
    *  Test for javafx.scene.web.WebEngine popup handler. Uses passing window.open JavaScript
     * to associated WebEngine.
    */

    @Test(timeout=10000)
    public void testPopupURLSet() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.open('http://javafx-jira.kenai.com/browse/', 'Test', 'status=no,width=200,menubar=no,toolbar=no');");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (returnedEngine != null)
                        && popupCalled
                        && (hasStatus == false)
                        && (hasMenu == false)
                        && (hasToolbar == false)
                        && "http://javafx-jira.kenai.com/browse/".equals(returnedEngine.getLocation());
            }
        });
    }

    protected final Callback thisHandler = this;    // Test hack.
    /**
     * Test for javafx.scene.web.WebEngine.createPopupHandlerProperty. Checks that the property
     * holds the same handler as javafx.scene.web.WebEngine.getCreatePopupHandler().
     * Also checks that the handler is the same object as the test's *this*.
     */
    @Test(timeout=10000)
    public void testCreatePopupHandlerProperty() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (engine != null)
                        && (engine.createPopupHandlerProperty().get() == engine.getCreatePopupHandler())
                        && (engine.createPopupHandlerProperty().get() == thisHandler);
            }
        });
    }

    private void initEngine(){
        engine = new WebEngine();
        engine.setCreatePopupHandler(this);
        correctHandlerReturned = (this == engine.getCreatePopupHandler());
    }

    public WebEngine call(PopupFeatures p) {
        hasMenu = p.hasMenu();
        hasStatus = p.hasStatus();
        hasToolbar = p.hasToolbar();
        isResizable = p.isResizable();
        popupCalled = true;
        returnedEngine = new WebEngine();
        return returnedEngine;
    }


}
