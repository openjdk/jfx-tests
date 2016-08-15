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

import com.sun.fx.webnode.tests.commonUtils.HistoryTestClass;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerProperty;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for getting history current index.
 * @author Irina Grineva
 */
public class CurrentIndexTest extends HistoryTestClass {

    private Integer currentIndex;
    private ReadOnlyIntegerProperty currentIndexProperty;

    private void getCurrentIndex() {
        doWait(engineReady);
        currentIndex = null;
        Platform.runLater(new Runnable() {
            public void run() {
                currentIndex = history.getCurrentIndex();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return currentIndex != null;
            }
        });
    }

    private void getCurrentIndexProperty() {
        doWait(engineReady);
        currentIndexProperty = null;
        Platform.runLater(new Runnable() {
            public void run() {
                currentIndexProperty = history.currentIndexProperty();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return currentIndexProperty != null;
            }
        });
    }

    /**
     * Test for getting current index in new WebEngine.
     */
    @Test(timeout=10000)
    public void getCurrentIndexNewWE() {
        initWebEngineOnFXThread();
        getCurrentIndex();
        Assert.assertEquals(0, currentIndex.intValue());
    }

    /**
     * Test for getting current index property in new WebEngine.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyNewWE() {
        initWebEngineOnFXThread();
        getCurrentIndexProperty();
        Assert.assertEquals(0, currentIndexProperty.intValue());
    }

    /**
     * Test for getting current index from zero-length history.
     */
    @Test(timeout=10000)
    public void getCurrentIndexZeroHistory() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                history.setMaxSize(0);
            }
        });
        getCurrentIndex();
        Assert.assertEquals(0, currentIndex.intValue());
    }

    /**
     * Test for getting current index property from zero-length history.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyZeroHistory() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                history.setMaxSize(0);
            }
        });
        getCurrentIndexProperty();
        Assert.assertEquals(0, currentIndexProperty.intValue());
    }

    /**
     * Test for getting current index from zero-length history.
     */
    @Test(timeout=10000)
    public void getCurrentIndexZeroHistory2() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                history.setMaxSize(0);
            }
        });
        createHistory();
        getCurrentIndex();
        Assert.assertEquals(0, currentIndex.intValue());
    }

     /**
     * Test for getting current index property from zero-length history.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyZeroHistory2() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                history.setMaxSize(0);
            }
        });
        createHistory();
        getCurrentIndexProperty();
        Assert.assertEquals(0, currentIndexProperty.intValue());
    }

    /**
     * Test for getting current index after navigation.
     */
    @Test(timeout=10000)
    public void getCurrentIndexNLoads() {
        initWebEngineOnFXThread();
        createHistory();
        getCurrentIndex();
        Assert.assertEquals(2, currentIndex.intValue());
    }

    /**
     * Test for getting current index property after navigation.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyNLoads() {
        initWebEngineOnFXThread();
        createHistory();
        getCurrentIndexProperty();
        Assert.assertEquals(2, currentIndexProperty.intValue());
    }

    /**
     * Test for getting current index after calling WebHistory.go() with negative argument.
     */
    @Test(timeout=10000)
    public void getCurrentIndexNLoadsGoNeg() {
        initWebEngineOnFXThread();
        createHistory();
        go(-1);
        getCurrentIndex();
        Assert.assertEquals(1, currentIndex.intValue());
    }

    /**
     * Test for getting current index property after calling WebHistory.go() with negative argument.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyNLoadsGoNeg() {
        initWebEngineOnFXThread();
        createHistory();
        go(-1);
        getCurrentIndexProperty();
        Assert.assertEquals(1, currentIndexProperty.intValue());
    }

    /**
     * Test for getting current index after calling WebHistory.go() with positive argument.
     */
    @Test(timeout=10000)
    public void getCurrentIndexNLoadsGoPos() {
        initWebEngineOnFXThread();
        createHistory();
        go(-1);
        go(-1);
        go(1);
        getCurrentIndex();
        Assert.assertEquals(1, currentIndex.intValue());
    }

    /**
     * Test for getting current index property after calling WebHistory.go() with positive argument.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyNLoadsGoPos() {
        initWebEngineOnFXThread();
        createHistory();
        go(-1);
        go(-1);
        go(1);
        getCurrentIndexProperty();
        Assert.assertEquals(1, currentIndexProperty.intValue());
    }

    /**
     * Test for getting current index after calling WebHistory.go() with 0.
     */
    @Test(timeout=10000)
    public void getCurrentIndexNLoadsGo0() {
        initWebEngineOnFXThread();
        createHistory();
        go(0);
        getCurrentIndex();
        Assert.assertEquals(2, currentIndex.intValue());
    }

    /**
     * Test for getting current index property after calling WebHistory.go() with 0.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyNLoadsGo0() {
        initWebEngineOnFXThread();
        createHistory();
        go(0);
        getCurrentIndexProperty();
        Assert.assertEquals(2, currentIndexProperty.intValue());
    }

    /**
     * Test for getting current index after reloading page.
     */
    @Test(timeout=10000)
    public void getCurrentIndexReload() {
        initWebEngineOnFXThread();
        createHistory();
        reload();
        getCurrentIndex();
        Assert.assertEquals(2, currentIndex.intValue());
    }

    /**
     * Test for getting current index property after reloading page.
     */
    @Test(timeout=10000)
    public void getCurrentIndexPropertyReload() {
        initWebEngineOnFXThread();
        createHistory();
        reload();
        getCurrentIndexProperty();
        Assert.assertEquals(2, currentIndexProperty.intValue());
    }
}
