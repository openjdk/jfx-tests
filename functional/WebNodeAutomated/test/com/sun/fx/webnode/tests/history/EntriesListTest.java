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
import javafx.collections.ObservableList;
import javafx.scene.web.WebHistory.Entry;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for list of WebHistory entries.
 * @author Irina Grineva
 */
public class EntriesListTest extends HistoryTestClass {

    private ObservableList<Entry> entries;

    private final Tester entriesReady = new Tester() {
        public boolean isPassed() {
            return entries != null;
        }
    };

    /**
     * Test for getting entries list for empty history.
     */
    @Test(timeout=10000)
    public void testGetEntriesEmptyHistory() {
        initWebEngineOnFXThread();
        entries = history.getEntries();
        Assert.assertNotNull(entries);
        Assert.assertTrue("Entries list is not empty before navigating", entries.isEmpty());
    }

    private Integer newSize;

    /**
     * Test for getting entries list for empty history after resizing it.
     */
    @Test(timeout=10000)
    public void testGetEntriesEmptyResizeHistory() {
        initWebEngineOnFXThread();
        createHistory();
        Platform.runLater(new Runnable() {
            public void run() {
                history.setMaxSize(1);
                newSize = history.getEntries().size();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return newSize != null;
            }
        });
        Assert.assertEquals(1, newSize.intValue());
    }

    /**
     * Test for getting entries list for empty history after resizing it to 0.
     */
    @Test(timeout=10000)
    public void testGetEntriesEmptyZeroHistory() {
        initWebEngineOnFXThread();
        Platform.runLater(new Runnable() {
            public void run() {
                history.setMaxSize(0);
                entries = history.getEntries();
            }
        });
        doWait(entriesReady);
        Assert.assertNotNull(entries);
        Assert.assertEquals(0, entries.size());
    }

    /**
     * Test for getting entries list for zero sized history.
     * http://javafx-jira.kenai.com/browse/RT-21791
     */
    @Test(timeout=10000)
    public void testGetEntriesZeroHistory() {
        initWebEngineOnFXThread();
        createHistory();
        Platform.runLater(new Runnable() {
            public void run() {
                history.setMaxSize(0);
                entries = history.getEntries();
            }
        });
        doWait(entriesReady);
        Assert.assertNotNull(entries);
        Assert.assertEquals(0, entries.size());
    }

    /**
     * Test for getting entries list.
     */
    @Test(timeout=10000)
    public void testGetEntriesHistory() {
        initWebEngineOnFXThread();
        createHistory();
        entries = history.getEntries();
        Assert.assertNotNull(entries);
        Assert.assertEquals(3, entries.size());
    }

    /**
     * Test for getting entries list after calling WebHistory.go() with negative argument.
     */
    @Test(timeout=10000)
    public void testGetEntriesHistoryGoBack() {
        initWebEngineOnFXThread();
        createHistory();
        entries = history.getEntries();
        int prevSize = entries.size();
        go(-1);
        entries = history.getEntries();
        Assert.assertNotNull(entries);
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(prevSize, entries.size());
    }

    /**
     * Test for getting entries list after calling WebHistory.go() with negative and positive argument.
     */
    @Test(timeout=10000)
    public void testGetEntriesHistoryGoForward() {
        initWebEngineOnFXThread();
        createHistory();
        entries = history.getEntries();
        int prevSize = entries.size();
        go(-1);
        go(1);
        entries = history.getEntries();
        Assert.assertNotNull(entries);
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(prevSize, entries.size());
    }

    /**
     * Test for getting entries list after reloading page.
     */
    @Test(timeout=10000)
    public void testGetEntriesHistoryReload() {
        initWebEngineOnFXThread();
        createHistory();
        entries = history.getEntries();
        int prevSize = entries.size();
        reload();
        entries = history.getEntries();
        Assert.assertNotNull(entries);
        Assert.assertEquals(3, entries.size());
        Assert.assertEquals(prevSize, entries.size());
    }

    private UnsupportedOperationException exception = null;
    /**
     * Test for trying to add entry to entries list.
     */
    @Test(timeout=10000)
    public void testAddEntry() {
        initWELoadHelloFXThread();
        doWaitPageLoading();
        entries = history.getEntries();
        Assert.assertNotNull(entries);
        try {
            entries.add(entries.get(0));
        } catch (UnsupportedOperationException ex) {
            exception = ex;
        }
        doWait(new Tester() {
            public boolean isPassed() {
                return exception != null;
            }
        });
    }

}
