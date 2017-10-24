/*
 * Copyright (c) 2014, 2017 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.fx.webnode.tests.menu;

import client.test.RunUI;
import com.sun.fx.webnode.tests.generic.WebNodeLauncher;

/**
 * Tests for WebView context menu.
 * @author Irina Grineva
 */
public class Menu {
    /**
     * Test for "Go Back" context menu item.
     */
    @RunUI(value="back.html")
    public static void back(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Go Forward" context menu item.
     */
    @RunUI(value="forward.html")
    public static void forward(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Open Link" context menu item.
     */
    @RunUI(value="openLink.html")
    public static void openLink(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Open Link in New Window" context menu item.
     */
    @RunUI(value="linkNewWindow.html")
    public static void openLinkNewWindow(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Open Image in New Window" context menu item.
     */
    @RunUI(value="imageNewWindow.html")
    public static void openImageNewWindow(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Copy Link to Clipboard" context menu item.
     */
    @RunUI(value="copyLink.html")
    public static void copyLink(){
        RichTextLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Copy Image to Clipboard" context menu item.
     */
    @RunUI(value="copyImage.html")
    public static void copyImage(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Copy" context menu item.
     */
    @RunUI(value="copy.html")
    public static void copy(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Stop Page Loading" context menu item.
     */
    @RunUI(value="stop.html")
    public static void stop(){
        WebNodeLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Reload" context menu item.
     */
    @RunUI(value="reload.html")
    public static void reload(){
        ReloadLauncher.run(Menu.class.getResource("resources/reload.html").getPath(), Menu.class.getResource("resources/reload.html").toExternalForm());
    }

    /**
     * Test for "Copy" context menu item.
     */
    @RunUI(value="textCopy.html")
    public static void textCopy(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Paste" context menu item.
     */
    @RunUI(value="textPaste.html")
    public static void textPaste(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test for "Cut" context menu item.
     */
    @RunUI(value="textCut.html")
    public static void textCut(){
        MenuLauncher.run(Menu.class.getResource("resources/page1.html").toExternalForm());
    }

    /**
     * Test Clipboard Contents after selecting "Copy Image to Clipboard" context
     * menu item
     */
    @RunUI(value="imageCopyToClipboard.html")
    public static void verifyClipboardOnCopyImage(){
        ClipboardContentLauncher.run(Menu.class.getResource("resources/imageCopyToClipboard.html").toExternalForm());
    }
}
