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

package com.sun.fx.webnode.tests.history.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Irina Grineva
 */
public class HistoryResources {
    private static String getHello(String suffix) {
        return HistoryResources.class.getResource("hello" + suffix + ".html").toExternalForm();
    }
    public static String getHelloHTML() {
        return getHello("");
    }
    public static String getHelloHTML2() {
        return getHello("2");
    }
    public static String getHelloHTML3() {
        return getHello("3");
    }
    public static String getHelloHTMLRewrite() {
        return getHello("-rewrite");
    }
    public static final String TITLE_1 = "Title1";
    public static final String TITLE_2 = "Title2";
    private static void write(String title) {
        try {
            FileWriter fw = new FileWriter(HistoryResources.class.getResource("hello-rewrite.html").getFile());
            fw.write("<HTML><HEAD><TITLE>" + title + "</TITLE></HEAD><BODY></BODY></HTML>");
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            // Oh shi...
            Logger.getLogger(HistoryResources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void writeHello1() {
        write(TITLE_1);
    }
    public static void writeHello2() {
        write(TITLE_2);
    }
}
