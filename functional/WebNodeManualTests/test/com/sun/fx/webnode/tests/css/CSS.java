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

package com.sun.fx.webnode.tests.css;

import client.test.RunUI;

/**
 * Tests for feature RT-14436 (Allow CSS styling for WebView).
 * @author Irina Grineva
 */
public class CSS {
    /**
     * Test for checking default font smoothing (LCD) and setting it to gray.
     */
    @RunUI(value="fontsmoothinggray.html")
    public static void fontSmoothing(){
        CSSLauncher.run(CSS.class.getResource("resources/fontsmoothinggray.css").toExternalForm());
    }

    /**
     * Test for setting font scale.
     */
    @RunUI(value="fontscale.html")
    public static void fontScale(){
        CSSLauncher.run(CSS.class.getResource("resources/fontscale.css").toExternalForm());
    }

    /**
     * Test for setting maximum width of WebView.
     */
    @RunUI(value="maxwidth.html")
    public static void maxWidth(){
        CSSLauncher.run(CSS.class.getResource("resources/maxwidth.css").toExternalForm());
    }

    /**
     * Test for setting maximum height of WebView.
     */
    @RunUI(value="maxheight.html")
    public static void maxHeight(){
        CSSLauncher.run(CSS.class.getResource("resources/maxheight.css").toExternalForm());
    }

    /**
     * Test for setting minimum width of WebView.
     */
    @RunUI(value="minwidth.html")
    public static void minWidth(){
        CSSLauncher.run(CSS.class.getResource("resources/minwidth.css").toExternalForm());
    }

    /**
     * Test for setting minimum height of WebView.
     */
    @RunUI(value="minheight.html")
    public static void minHeight(){
        CSSLauncher.run(CSS.class.getResource("resources/minheight.css").toExternalForm());
    }

    /**
     * Test for setting preferred width of WebView.
     */
    @RunUI(value="prefwidth.html")
    public static void prefWidth(){
        CSSLauncher.run(CSS.class.getResource("resources/prefwidth.css").toExternalForm());
    }

    /**
     * Test for setting preferred height of WebView.
     */
    @RunUI(value="prefheight.html")
    public static void prefHeight(){
        CSSLauncher.run(CSS.class.getResource("resources/prefheight.css").toExternalForm());
    }

    /**
     * Test for enabling/disabling context menu in WebView.
     */
    @RunUI(value="contextmenudisable.html")
    public static void contextmenudisable(){
        CSSLauncher.run(CSS.class.getResource("resources/contextmenudisable.css").toExternalForm());
    }
}
