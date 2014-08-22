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

import client.test.RunUI;

/**
 * Manual tests for WebHistory
 * @author Irina Grineva
 */
public class WebHistory {
    /**
     * Test for simple navigation.
     */
    @RunUI(value="simple.html")
    public static void simple(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
    /**
     * Test for WebHistory.go() with negative parameter.
     */
    @RunUI(value="goback.html")
    public static void goback(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
    /**
     * Test for WebHistory.go() with positive parameter.
     */
    @RunUI(value="goforward.html")
    public static void goforward(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
    /**
     * Test for WebHistory.go(0).
     */
    @RunUI(value="go0.html")
    public static void go0(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
    /**
     * Test for WebHistory.go() with negative parameter when it's impossible to load the entry.
     */
    @RunUI(value="gobacktoomuch.html")
    public static void gobacktoomuch(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
    /**
     * Test for WebHistory.go() with positive parameter when it's impossible to load the entry.
     */
    @RunUI(value="goforwardtoomuch.html")
    public static void goforwardtoomuch(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
    /**
     * Test for navigating with zero-sized history.
     */
    @RunUI(value="historysize0.html")
    public static void historysize0(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
    /**
     * Test for resizing the history to the size smaller then current entries list.
     */
    @RunUI(value="shrinkhistory.html")
    public static void shrinkhistory(){
        HistoryLauncher.run(WebHistory.class.getResource("resources/page1.html").toExternalForm());
    }
}
