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
public class Style {
    /**
     * Test for checking default font smoothing (LCD) and setting it to gray.
     */
    @RunUI(value="fontsmoothinggray_1.html")
    public static void fontSmoothing(){
        StyleLauncher.run("-fx-font-smoothing-type: gray;");
    }

    /**
     * Test for setting font scale.
     */
    @RunUI(value="fontscale_1.html")
    public static void fontScale(){
        StyleLauncher.run("-fx-font-scale: 2;");
    }

    /**
     * Test for setting maximum width of WebView.
     */
    @RunUI(value="maxwidth_1.html")
    public static void maxWidth(){
        StyleLauncher.run("-fx-max-width: 100;");
    }

    /**
     * Test for setting maximum height of WebView.
     */
    @RunUI(value="maxheight_1.html")
    public static void maxHeight(){
        StyleLauncher.run("-fx-max-height: 100;");
    }

    /**
     * Test for setting minimum width of WebView.
     */
    @RunUI(value="minwidth_1.html")
    public static void minWidth(){
        StyleLauncher.run("-fx-min-width: 400;");
    }

    /**
     * Test for setting minimum height of WebView.
     */
    @RunUI(value="minheight_1.html")
    public static void minHeight(){
        StyleLauncher.run("-fx-min-height: 400;");
    }

    /**
     * Test for setting preferred width of WebView.
     */
    @RunUI(value="prefwidth_1.html")
    public static void prefWidth(){
        StyleLauncher.run("-fx-pref-width: 100;");
    }

    /**
     * Test for setting preferred height of WebView.
     */
    @RunUI(value="prefheight_1.html")
    public static void prefHeight(){
        StyleLauncher.run("-fx-pref-height: 100;");
    }

    /**
     * Test Helvetica font rendering on Mac
     */
    @RunUI(value="fontHelveticaOnMac.html")
    public static void fontHelveticaOnMac()
    {
        FontLauncher.run("");
    }
}
