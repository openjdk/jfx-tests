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
import com.sun.fx.webnode.tests.history.resources.HistoryResources;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.web.WebHistory.Entry;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Complex tests for WebHistory and navigation.
 * @author Irina Grineva
 */
public class NavigatingTest extends HistoryTestClass {

    /**
     * Test for simple navigation.
     */
    @Test(timeout=10000)
    public void testNavigateSimple() {
        initWebEngineOnFXThread();
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertNotNull(entries);
        Assert.assertTrue("Entries list is not empty before navigating", entries.isEmpty());
        Assert.assertEquals(0, currentIndex);
        Assert.assertNull(title);
        loadSite(HistoryResources.getHelloHTML());
        currentIndex = history.getCurrentIndex();
        title = engine.getTitle();
        Assert.assertEquals(1, entries.size());
        Assert.assertEquals(0, currentIndex);
        Assert.assertEquals("0", title);
        loadSite(HistoryResources.getHelloHTML2());
        currentIndex = history.getCurrentIndex();
        title = engine.getTitle();
        Assert.assertEquals(2, entries.size());
        Assert.assertEquals(1, currentIndex);
        Assert.assertEquals("1", title);
    }

    /**
     * Test for loading the same page multiple times.
     */
    @Test(timeout=10000)
    public void testLoadSamePage() {
        initWELoadHelloFXThread();
        doWaitPageLoading();
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertEquals(1, entries.size());
        Assert.assertEquals(0, currentIndex);
        Assert.assertEquals("0", title);
        loadSite(HistoryResources.getHelloHTML());
        currentIndex = history.getCurrentIndex();
        title = engine.getTitle();
        Assert.assertEquals(1, entries.size());
        Assert.assertEquals(0, currentIndex);
        Assert.assertEquals("0", title);
    }

    /**
     * Test for WebHistory.go() method called with a negative argument.
     */
    @Test(timeout=10000)
    public void testGoBack() {
        initWebEngineOnFXThread();
        createHistory();
        go(-1);
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(1, currentIndex);
        Assert.assertEquals("1", title);
    }

    /**
     * Test for WebHistory.go() method called with a positive argument.
     */
    @Test(timeout=10000)
    public void testGoForward() {
        initWebEngineOnFXThread();
        createHistory();
        go(-1);
        go(1);
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(2, currentIndex);
        Assert.assertEquals("2", title);
    }

    IndexOutOfBoundsException e;
    Tester exceptionHere = new Tester() {
        public boolean isPassed() {
            return e != null;
        }
    };

    /**
     * Test for WebHistory.go() method called with a negative argument when it is impossible to load an entry.
     */
    @Test(timeout=10000)
    public void testGoBackImpossible() {
        initWebEngineOnFXThread();
        createHistory();
        e = null;
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                   history.go(-5);
                } catch (IndexOutOfBoundsException ex) {
                    e = ex;
                }
            }
        });
        doWait(exceptionHere);
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(2, currentIndex);
        Assert.assertEquals("2", title);
    }

    /**
     * Test for WebHistory.go() method called with a positive argument when it is impossible to load an entry.
     */
    @Test(timeout=10000)
    public void testGoForwardImpossible() {
        initWebEngineOnFXThread();
        createHistory();
        e = null;
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                   history.go(5);
                } catch (IndexOutOfBoundsException ex) {
                    e = ex;
                }
            }
        });
        doWait(exceptionHere);
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(2, currentIndex);
        Assert.assertEquals("2", title);
    }

    /**
     * Test for WebHistory.go() method called with 0.
     */
    @Test(timeout=10000)
    public void testGo0() {
        initWebEngineOnFXThread();
        createHistory();
        go(0);
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(2, currentIndex);
        Assert.assertEquals("2", title);
    }

    /**
     * Test for loading a resource which cannot be loaded.
     */
    @Test(timeout=10000)
    public void test404() {
        initWebEngineOnFXThread();
        loadSite("file://nothing-here");
        List<Entry> entries = history.getEntries();
        int currentIndex = history.getCurrentIndex();
        String title = engine.getTitle();
        Assert.assertEquals(0, entries.size());
        Assert.assertEquals(0, currentIndex);
        Assert.assertNull(title);
    }
}
