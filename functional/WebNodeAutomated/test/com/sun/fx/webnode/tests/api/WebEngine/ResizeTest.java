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
import javafx.geometry.Rectangle2D;
import javafx.scene.web.WebEngine;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import javafx.scene.web.WebEvent;
import org.junit.Before;

/**
 * Test for javafx.scene.web.UI.setViewBounds()
 * UI.setViewBounds() should be called when JavaScript window.resizeBy() or window.resizeTo() happens.
 * @author Irina Latysheva
 */
public class ResizeTest extends GenericTestClass implements EventHandler<WebEvent<Rectangle2D>>{

    private boolean resizeCalled = false;
    private double newHeight = Double.NEGATIVE_INFINITY;
    private double newWidth = Double.NEGATIVE_INFINITY;
    private boolean correctHandlerReturned = false;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }


    @Before
    public void cleanup(){
        resizeCalled = false;
        newHeight = Double.NEGATIVE_INFINITY;
        newWidth = Double.NEGATIVE_INFINITY;
        correctHandlerReturned = false;
    }

    /**
     * Test for javafx.scene.web.WebEngine resize handler.  Uses loading document with window.resizeBy JavaScript
     * executed on document load with associated WebEngine.
     */
    @Test(timeout=10000)
    public void testResizeFromHTML(){
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.load(getPath(this.getClass(), "resource/setViewBoundsTest1.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        Assert.assertTrue(resizeCalled);
        Assert.assertTrue(correctHandlerReturned);
    }

    /**
     * Test for javafx.scene.web.WebEngine resize handler. Uses loading document with window.resizeTo JavaScript
     * executed on document load with associated WebEngine.
     */
    @Test(timeout=10000)
    public void testResizeFromHTML2(){
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.load(getPath(this.getClass(), "resource/setViewBoundsTest2.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        Assert.assertTrue(resizeCalled);
        Assert.assertTrue(correctHandlerReturned);

    }

    /**
     * Test for javafx.scene.web.WebEngine resize handler. Uses passing window.resizeBy JavaScript
     * to associated WebEngine.
     */
    @Test(timeout=10000)
    public void testResizeByValueFromJSFromEngine() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.resizeBy(100, 100);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resizeCalled && (newHeight!=Double.NEGATIVE_INFINITY)
                        && (newWidth!=Double.NEGATIVE_INFINITY));
            }
        });
    }

    /**
     * Test for javafx.scene.web.WebEngine resize handler. Uses passing window.resizeTo JavaScript
     * to associated WebEngine.
     */
    @Test(timeout=10000)
    public void testResizeToValueFromJSFromEngine() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
                engine.executeScript("window.resizeTo(100, 100);");
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resizeCalled && (Double.compare(newWidth, 100)==0)
                        && (Double.compare(newHeight, 100)==0));
            }
        });
    }

    protected final EventHandler thisHandler = this;    // Test hack.
    /**
     * Test for javafx.scene.web.WebEngine.onResizedProperty. Checks that the property
     * holds the same handler as javafx.scene.web.WebEngine.getOnResized().
     * Also checks that the handler is the same object as the test's *this*.
     */
    @Test(timeout=10000)
    public void testOnResizeProperty() {
        Platform.runLater(new Runnable() {
            public void run() {
                initEngine();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (engine != null)
                        && (engine.onResizedProperty().get() == engine.getOnResized())
                        && (engine.onResizedProperty().get() == thisHandler);
            }
        });
    }

    public void handle(WebEvent<Rectangle2D> t) {
        newHeight = t.getData().getHeight();
        newWidth = t.getData().getWidth();
        resizeCalled = true;
    }


    private void initEngine(){
        engine = new WebEngine();
        engine.setOnResized(this);
        correctHandlerReturned = (this == engine.getOnResized());
    }

}
