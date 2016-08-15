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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.web.WebHistory.Entry;
import junit.framework.Assert;
import org.junit.Test;

//for OnlyRunModeMethod (avoid running testCheckOneEntryReload in non-Desktop mode)
import client.test.OnlyRunModeMethod;
import client.test.RunModes;

/**
 * Tests for WebHistory.Entry objects.
 * @author Irina Grineva
 */
public class EntryTest extends HistoryTestClass {

    private Entry entry;

    private void getEntry(final int n) {
        doWait(engineReady);
        entry = null;
        Platform.runLater(new Runnable() {
            public void run() {
                List <Entry>l = history.getEntries();
                entry = l.get(n);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return entry != null;
            }
        });
    }

    private void getLastEntry() {
        getEntry(history.getCurrentIndex());
    }

    /**
     * Simple test for a single entry.
     */
    @Test(timeout=10000)
    public void testCheckOneEntry() {
        Date date1 = new Date();
        initWELoadHelloFXThread();
        doWaitPageLoading();
        Date date2 = new Date();
        getLastEntry();
        Assert.assertEquals("0", entry.getTitle());
        Date lastVisited = entry.getLastVisitedDate();
        Assert.assertTrue("Date " + date1.getTime() + " doesn't precede " + lastVisited.getTime(), date1.equals(lastVisited) || date1.before(lastVisited));
        Assert.assertTrue("Date " + date2.getTime() + " is not after " + lastVisited.getTime(), date2.equals(lastVisited) || date2.after(lastVisited));
        try {
            URL url = new URL(HistoryResources.getHelloHTML());
            Assert.assertTrue(entry.getUrl().equals(url.toExternalForm()));
        } catch (MalformedURLException ex) {
            // This will probably never happen...
            Logger.getLogger(EntryTest.class.getName()).log(Level.SEVERE, null, ex);
            // ...but if it does, this still might work.
            Assert.assertTrue(entry.getUrl().toString().equals(HistoryResources.getHelloHTML()));
        }
    }

    /**
     * This test checks that last entry in entries list is updated properly when going back and forward the history.
     */
    @Test(timeout=10000)
    public void testCheckOneEntryBackForth() {
        initWebEngineOnFXThread();
        createHistory();
        go(-1);
        Date date1 = new Date();
        go(1);
        Date date2 = new Date();
        getLastEntry();
        Assert.assertEquals("2", entry.getTitle());
        Date lastVisited = entry.getLastVisitedDate();
        Assert.assertTrue("Date " + date1.getTime() + " doesn't precede " + lastVisited.getTime(), date1.equals(lastVisited) || date1.before(lastVisited));
        Assert.assertTrue("Date " + date2.getTime() + " is not after " + lastVisited.getTime(), date2.equals(lastVisited) || date2.after(lastVisited));
        try {
            URL url = new URL(HistoryResources.getHelloHTML3());
            Assert.assertTrue(entry.getUrl().equals(url.toExternalForm()));
        } catch (MalformedURLException ex) {
            // This will probably never happen...
            Logger.getLogger(EntryTest.class.getName()).log(Level.SEVERE, null, ex);
            // ...but if it does, this still might work.
            Assert.assertTrue(entry.getUrl().toString().equals(HistoryResources.getHelloHTML3()));
        }
    }

    /**
     * This test checks that last entry in entries list is updated properly when reloading page.
     * This test should not be run in plugin/jnlp mode.
     * http://javafx-jira.kenai.com/browse/RT-21790
     */
    @OnlyRunModeMethod(RunModes.DESKTOP)
    @Test(timeout=10000)
    public void testCheckOneEntryReload() {
        initWebEngineOnFXThread();
        HistoryResources.writeHello1();
        loadSite(HistoryResources.getHelloHTMLRewrite());
        getLastEntry();
        String title_1 = entry.getTitle();
        Date date_1 = entry.getLastVisitedDate();
        String url_1 = entry.getUrl();
        Assert.assertEquals(HistoryResources.TITLE_1, title_1);
        HistoryResources.writeHello2();
        // Just to make sure that the dates will be different.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(EntryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        reload();
        String title_2 = entry.getTitle();
        Date date_2 = entry.getLastVisitedDate();
        String url_2 = entry.getUrl();
        Assert.assertEquals(HistoryResources.TITLE_2, title_2);
        Assert.assertTrue("Title has not changed during reload", !title_1.equals(title_2));
        Assert.assertTrue("Url has changed while reloading", url_1.equals(url_2));
        Assert.assertTrue("Date has not changed during reload", !date_1.equals(date_2));
    }
}
