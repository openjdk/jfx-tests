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
 * Tests for enabling/disabling context menu functionality.
 * @author Irina Grineva
 */
public class ContextMenuEnabledTest extends GenericTestClass {

    private BooleanProperty contextMenuEnabledProperty;
    private Boolean contextMenuEnabledPropertyValue;
    private Boolean isContextMenuEnabled;

    private Tester isContextMenuEnabledReady = new Tester() {
        public boolean isPassed() {
            return isContextMenuEnabled != null;
        }
    };
    private Tester valuesReady = new Tester() {
        public boolean isPassed() {
            return isContextMenuEnabled != null && contextMenuEnabledPropertyValue != null;
        }
    };

    @BeforeClass
    public static void init() {
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for checking default context menu state.
     */
    @Test(timeout=10000)
    public void testGetDefaultContextMenuEnabled() {
        initWebEngineAndWebViewOnFXThread();
        isContextMenuEnabled = null;
        Platform.runLater(new Runnable() {
            public void run() {
                isContextMenuEnabled = view.isContextMenuEnabled();
            }
        });
        doWait(isContextMenuEnabledReady);
        Assert.assertTrue("Context menu is not enabled by default!", isContextMenuEnabled);
    }

    /**
     * Test for disabling context menu.
     */
    @Test(timeout=10000)
    public void testSetContextMenuEnabledFalse() {
        initWebEngineAndWebViewOnFXThread();
        isContextMenuEnabled = null;
        Platform.runLater(new Runnable() {
            public void run() {
                view.setContextMenuEnabled(false);
                isContextMenuEnabled = view.isContextMenuEnabled();
            }
        });
        doWait(isContextMenuEnabledReady);
        Assert.assertFalse("Context menu is not disabled!", isContextMenuEnabled);
    }

    /**
     * Test for disabling, and then enabling context menu.
     */
    @Test(timeout=10000)
    public void testSetContextMenuEnabledFalseThenTrue() {
        initWebEngineAndWebViewOnFXThread();
        isContextMenuEnabled = null;
        Platform.runLater(new Runnable() {
            public void run() {
                view.setContextMenuEnabled(false);
                view.setContextMenuEnabled(true);
                isContextMenuEnabled = view.isContextMenuEnabled();
            }
        });
        doWait(isContextMenuEnabledReady);
        Assert.assertTrue("Context menu is disabled!", isContextMenuEnabled);
    }

    /**
     * Test for checking default context menu state using a property.
     */
    @Test(timeout=10000)
    public void testGetDefaultContextMenuEnabledProperty() {
        initWebEngineAndWebViewOnFXThread();
        isContextMenuEnabled = null;
        contextMenuEnabledProperty = null;
        Platform.runLater(new Runnable() {
            public void run() {
                contextMenuEnabledProperty = view.contextMenuEnabledProperty();
                contextMenuEnabledPropertyValue = contextMenuEnabledProperty.get();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return contextMenuEnabledProperty != null && contextMenuEnabledPropertyValue != null;
            }
        });
        Assert.assertTrue("Context menu is not enabled by default!", contextMenuEnabledPropertyValue);
    }

    /**
     * Test for disabling context menu using a property.
     */
    @Test(timeout=10000)
    public void testSetContextMenuEnabledPropertyFalse() {
        initWebEngineAndWebViewOnFXThread();
        isContextMenuEnabled = null;
        contextMenuEnabledPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                contextMenuEnabledProperty = view.contextMenuEnabledProperty();
                contextMenuEnabledProperty.set(false);
                contextMenuEnabledPropertyValue = contextMenuEnabledProperty.get();
                isContextMenuEnabled = view.isContextMenuEnabled();
            }
        });
        doWait(valuesReady);
        Assert.assertFalse("Context menu is not disabled!", contextMenuEnabledPropertyValue);
        Assert.assertFalse("Context menu is not disabled!", isContextMenuEnabled);
    }

    /**
     * Test for disabling, and then enabling context menu using a property.
     */
    @Test(timeout=10000)
    public void testSetContextMenuEnabledPropertyFalseThenTrue() {
        initWebEngineAndWebViewOnFXThread();
        isContextMenuEnabled = null;
        contextMenuEnabledPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                contextMenuEnabledProperty = view.contextMenuEnabledProperty();
                contextMenuEnabledProperty.set(false);
                contextMenuEnabledPropertyValue = contextMenuEnabledProperty.get();
                isContextMenuEnabled = view.isContextMenuEnabled();
            }
        });
        doWait(valuesReady);
        Assert.assertFalse("Context menu is not disabled!", contextMenuEnabledPropertyValue);
        Assert.assertFalse("Context menu is not disabled!", isContextMenuEnabled);
        isContextMenuEnabled = null;
        contextMenuEnabledPropertyValue = null;
        Platform.runLater(new Runnable() {
            public void run() {
                contextMenuEnabledProperty = view.contextMenuEnabledProperty();
                contextMenuEnabledProperty.set(true);
                contextMenuEnabledPropertyValue = contextMenuEnabledProperty.get();
                isContextMenuEnabled = view.isContextMenuEnabled();
            }
        });
        doWait(valuesReady);
        Assert.assertTrue("Context menu is disabled!", contextMenuEnabledPropertyValue);
        Assert.assertTrue("Context menu is disabled!", isContextMenuEnabled);
    }
}
