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

package com.sun.fx.webnode.tests.customizable;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for enabling/disabling JavaScript functionality.
 * @author Irina Grineva
 */
public class JSEnabledTest extends GenericTestClass {

    private BooleanProperty jsEnabledProperty;
    private Boolean jsEnabledPropertyValue;
    private Boolean isJSEnabled;

    private Tester isJSEnabledReady = new Tester() {
        public boolean isPassed() {
            return isJSEnabled != null;
        }
    };
    private Tester valuesReady = new Tester() {
        public boolean isPassed() {
            return isJSEnabled != null && jsEnabledPropertyValue != null;
        }
    };

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for checking default JavaScript state.
     */
    @Test(timeout=10000)
    public void testGetDefaultJSEnabled() {
        initWebEngineOnFXThread();
        isJSEnabled = null;
        Platform.runLater(new Runnable() {
            public void run() {
                isJSEnabled = engine.isJavaScriptEnabled();
            }
        });
        doWait(isJSEnabledReady);
        Assert.assertTrue("JS is not enabled by default!", isJSEnabled);
    }

    /**
     * Test for disabling JavaScript.
     */
    @Test(timeout=10000)
    public void testSetJSEnabledFalse() {
        initWebEngineOnFXThread();
        isJSEnabled = null;
        Platform.runLater(new Runnable() {
            public void run() {
                engine.setJavaScriptEnabled(false);
                isJSEnabled = engine.isJavaScriptEnabled();
            }
        });
        doWait(isJSEnabledReady);
        Assert.assertFalse("JS is not disabled!", isJSEnabled);
    }

    /**
     * Test for disabling and then enabling JavaScript.
     */
    @Test(timeout=10000)
    public void testSetJSEnabledFalseThenTrue() {
        initWebEngineOnFXThread();
        isJSEnabled = null;
        Platform.runLater(new Runnable() {
            public void run() {
                engine.setJavaScriptEnabled(false);
                engine.setJavaScriptEnabled(true);
                isJSEnabled = engine.isJavaScriptEnabled();
            }
        });
        doWait(isJSEnabledReady);
        Assert.assertTrue("JS is disabled!", isJSEnabled);
    }

    /**
     * Test for checking default JavaScript state using a property.
     */
    @Test(timeout=10000)
    public void testGetDefaultJSEnabledProperty() {
        initWebEngineOnFXThread();
        jsEnabledProperty = null;
        jsEnabledPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                jsEnabledProperty = engine.javaScriptEnabledProperty();
                jsEnabledPropertyValue = jsEnabledProperty.get();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return jsEnabledPropertyValue != null && jsEnabledProperty != null;
            }
        });
        Assert.assertTrue("JS is not enabled by default!", jsEnabledPropertyValue);
    }

    /**
     * Test for disabling JavaScript using a property.
     */
    @Test(timeout=10000)
    public void testSetJSEnabledPropertyFalse() {
        initWebEngineOnFXThread();
        isJSEnabled = null;
        jsEnabledPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                jsEnabledProperty = engine.javaScriptEnabledProperty();
                jsEnabledProperty.set(false);
                jsEnabledPropertyValue = jsEnabledProperty.get();
                isJSEnabled = engine.isJavaScriptEnabled();
            }
        });
        doWait(valuesReady);
        Assert.assertFalse("JS is not disabled!", jsEnabledPropertyValue);
        Assert.assertFalse("JS is not disabled!", isJSEnabled);
    }

    /**
     * Test for disabling and then enabling JavaScript using a property.
     */
    @Test(timeout=10000)
    public void testSetJSEnabledPropertyFalseThenTrue() {
        initWebEngineOnFXThread();
        isJSEnabled = null;
        jsEnabledPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                jsEnabledProperty = engine.javaScriptEnabledProperty();
                jsEnabledProperty.set(false);
                jsEnabledPropertyValue = jsEnabledProperty.get();
                isJSEnabled = engine.isJavaScriptEnabled();
            }
        });
        doWait(valuesReady);
        Assert.assertFalse("JS is not disabled!", jsEnabledPropertyValue);
        Assert.assertFalse("JS is not disabled!", isJSEnabled);
        isJSEnabled = null;
        jsEnabledPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                jsEnabledProperty = engine.javaScriptEnabledProperty();
                jsEnabledProperty.set(true);
                jsEnabledPropertyValue = jsEnabledProperty.get();
                isJSEnabled = engine.isJavaScriptEnabled();
            }
        });
        doWait(valuesReady);
        Assert.assertTrue("JS is disabled!", jsEnabledPropertyValue);
        Assert.assertTrue("JS is disabled!", isJSEnabled);
    }
}
