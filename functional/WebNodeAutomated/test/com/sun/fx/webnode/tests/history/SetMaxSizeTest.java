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

package com.sun.fx.webnode.tests.history;

import com.sun.fx.webnode.tests.history.resources.HistoryResources;
import javafx.application.Platform;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for setting history maximum size.
 * @author Irina Grineva
 */
public class SetMaxSizeTest extends GetMaxSizeTest {
    protected int NEW_SIZE = 101;
    protected int NEW_SIZE2 = 98;

    private void setHistorySize(final int sizeParam) {
        initWebEngineOnFXThread();
        doWait(engineReady);
        size = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                history.setMaxSize(sizeParam);
                size = history.getMaxSize();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return size != null;
            }
        });
    }

    private void setHistorySizeProperty(final int sizeParam) {
        initWebEngineOnFXThread();
        doWait(engineReady);
        size = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                sizeProperty = history.maxSizeProperty();
                sizeProperty.set(sizeParam);
                size = history.getMaxSize();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return size != null;
            }
        });
    }

    private void loadAndSet(final int sizeParam) {
        initWELoadHelloFXThread();
        doWaitPageLoading();
        setHistorySize(sizeParam);
    }

    private void loadAndSetProperty(final int sizeParam) {
        initWELoadHelloFXThread();
        doWaitPageLoading();
        setHistorySizeProperty(sizeParam);
    }

    private IllegalArgumentException error;
    private Tester exceptionCaught = new Tester() {
        public boolean isPassed() {
            return error != null;
        }
    };

    /**
     * Test for setting history maximum size of a newly created WebEngine to a bigger value using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSizeNewWE() {
        setHistorySize(NEW_SIZE);
        Assert.assertEquals(NEW_SIZE, size.intValue());
    }

    /**
     * Test for setting history maximum size of a newly created WebEngine to a smaller value using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSizeNewWE2() {
        setHistorySize(NEW_SIZE2);
        Assert.assertEquals(NEW_SIZE2, size.intValue());
    }

    /**
     * Test for simple setting history maximum size of a newly created WebEngine to a bigger value using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizePropertyNewWE() {
        setHistorySizeProperty(NEW_SIZE);
        Assert.assertEquals(NEW_SIZE, size.intValue());
    }

    /**
     * Test for simple setting history maximum size of a newly created WebEngine to a smaller value using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizePropertyNewWE2() {
        setHistorySizeProperty(NEW_SIZE2);
        Assert.assertEquals(NEW_SIZE2, size.intValue());
    }

    /**
     * Test for setting history maximum size of a newly created WebEngine to 0 using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSizeZeroNewWE() {
        setHistorySize(0);
        Assert.assertEquals(0, size.intValue());
    }

    /**
     * Test for setting history maximum size of a newly created WebEngine to 0 using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizePropertyZeroNewWE() {
        setHistorySizeProperty(0);
        Assert.assertEquals(0, size.intValue());
    }

    /**
     * Test for setting history maximum size of a newly created WebEngine to a negative value using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSizeSubzeroNewWE() {
        error = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                try {
                    history.setMaxSize(-1);
                } catch (IllegalArgumentException e) {
                    error = e;
                }
            }
        });
        doWait(exceptionCaught);
    }

    /**
     * Test for setting history maximum size of a newly created WebEngine to a negative value using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizePropertySubzeroNewWE() {
        error = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                try {
                    history.maxSizeProperty().set(-1);
                } catch (IllegalArgumentException e) {
                    error = e;
                }
            }
        });
        doWait(exceptionCaught);
    }

    /**
     * Test for simple setting history maximum size to a bigger value using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSize() {
        loadAndSet(NEW_SIZE);
        Assert.assertEquals(NEW_SIZE, size.intValue());
    }

    /**
     * Test for simple setting history maximum size to a smaller value using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSize2() {
        loadAndSet(NEW_SIZE2);
        Assert.assertEquals(NEW_SIZE2, size.intValue());
    }

    /**
     * Test for setting history maximum size to a bigger value using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizeProperty() {
        setHistorySizeProperty(NEW_SIZE);
        Assert.assertEquals(NEW_SIZE, size.intValue());
    }

    /**
     * Test for setting history maximum size to a smaller value using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizeProperty2() {
        setHistorySizeProperty(NEW_SIZE2);
        Assert.assertEquals(NEW_SIZE2, size.intValue());
    }

    /**
     * Test for setting history maximum size to 0 using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSizeZero() {
        loadAndSet(0);
        Assert.assertEquals(0, size.intValue());
    }

    /**
     * Test for setting history maximum size to 0 using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizePropertyZero() {
        loadAndSetProperty(0);
        Assert.assertEquals(0, size.intValue());
    }

    /**
     * Test for setting history maximum size to a negative value using history.setMaxSize() method.
     */
    @Test(timeout=10000)
    public void setMaxSizeSubzero() {
        error = null;
        initWebEngineOnFXThread();
        createHistory();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                try {
                    history.setMaxSize(-1);
                } catch (IllegalArgumentException e) {
                    error = e;
                }
            }
        });
        doWait(exceptionCaught);
    }

    /**
     * Test for setting history maximum size to a negative value using history.maxSize property.
     */
    @Test(timeout=10000)
    public void setMaxSizePropertySubzero() {
        error = null;
        initWebEngineOnFXThread();
        createHistory();
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                try {
                    history.maxSizeProperty().set(-1);
                } catch (IllegalArgumentException e) {
                    error = e;
                }
            }
        });
        doWait(exceptionCaught);
    }

    /**
     * Test for navigating with a zero-sized history.
     */
    @Test(timeout=10000)
    public void setMaxSizeZeroNavigation() {
        loadAndSet(0);
        Platform.runLater(new Runnable() {
            public void run() {
                engine.load(HistoryResources.getHelloHTML2());
            }
        });
        doWaitPageLoading();   // Let's just check that nothing dies here, ok?
    }
}
