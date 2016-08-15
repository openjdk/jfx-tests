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
import javafx.beans.property.IntegerProperty;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for getting history maximum size.
 * @author Irina Grineva
 */
public class GetMaxSizeTest extends HistoryTestClass {

    protected Integer size;
    protected IntegerProperty sizeProperty;

    private void getMaxSize() {
        doWait(engineReady);
        size = null;
        Platform.runLater(new Runnable() {
            public void run() {
                size = history.getMaxSize();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return size != null;
            }
        });
    }

    private void getMaxSizeProperty() {
        doWait(engineReady);
        sizeProperty = null;
        Platform.runLater(new Runnable() {
            public void run() {
                sizeProperty = history.maxSizeProperty();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return sizeProperty != null;
            }
        });
    }

    /**
     * Test for getting maximum size from newly created WebEngine.
     */
    @Test(timeout=10000)
    public void getMaxSizeNewWE() {
        initWebEngineOnFXThread();
        getMaxSize();
        Assert.assertEquals(DEFAULT_MAX_SIZE, size.intValue());
    }

    /**
     * Test for getting maximum size property from newly created WebEngine.
     */
    @Test(timeout=10000)
    public void getMaxSizePropertyNewWE() {
        initWebEngineOnFXThread();
        getMaxSizeProperty();
        Assert.assertEquals(DEFAULT_MAX_SIZE, sizeProperty.intValue());
    }

    /**
     * Test for getting maximum size after some navigation.
     */
    @Test(timeout=10000)
    public void getMaxSizeLoaded() {
        initWELoadHelloFXThread();
        doWaitPageLoading();
        getMaxSize();
        Assert.assertEquals(DEFAULT_MAX_SIZE, size.intValue());
    }

    /**
     * Test for getting maximum size property after some navigation.
     */
    @Test(timeout=10000)
    public void getMaxSizePropertyLoaded() {
        initWELoadHelloFXThread();
        doWaitPageLoading();
        getMaxSizeProperty();
        Assert.assertEquals(DEFAULT_MAX_SIZE, sizeProperty.intValue());
    }

}
