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

package com.sun.fx.webnode.tests.html5.input.file;

import client.test.RunUI;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tests for INPUT control of FILE type.
 * @author Irina Grineva
 */
public class FileChooser {
    static {
        FileInputLauncher.generate();
    }

    /**
     * Test for AUTOFOCUS attribute of INPUT of type FILE.
     */
    @RunUI(value="autofocus.html")
    public static void autofocus() {
        FileInputLauncher.run(FileChooser.class.getResource("resources/autofocus1.html").toExternalForm());
    }

    /**
     * Test for simple operations with INPUT of type FILE.
     */
    @RunUI(value="simpleSingle.html")
    public static void simpleSingle() {
        FileInputLauncher.run(FileChooser.class.getResource("resources/simpleSingle.html").toExternalForm());
    }

    /**
     * Test for simple operations with INPUT of type FILE with MULTIPLE attribute on.
     */
    @RunUI(value="simpleMultiple.html")
    public static void simpleMultiple() {
        FileInputLauncher.run(FileChooser.class.getResource("resources/simpleMultiple.html").toExternalForm());
    }

    /**
     * Test for simple operations with INPUT of type FILE with REQUIRED attribute.
     */
    @RunUI(value="required.html")
    public static void required() {
        FileInputLauncher.run(FileChooser.class.getResource("resources/required.html").toExternalForm());
    }

    /**
     * Test for simple operations with INPUT of type FILE with ACCEPT attribute.
     */
    @RunUI(value="accept.html")
    public static void accept() {
        FileInputLauncher.run(FileChooser.class.getResource("resources/accept.html").toExternalForm());
    }

    /**
     * Test for file upload with INPUT of type FILE.
     */
    @RunUI(value="uploadSingle.html")
    public static void uploadSingle() {
        doUploadTask("resources/uploadSingle.html");
    }

    /**
     * Test for file upload with INPUT of type FILE with MULTIPLE attribute.
     */
    @RunUI(value="uploadMultiple.html")
    public static void uploadMultiple() {
        doUploadTask("resources/uploadMultiple.html");
    }

    /**
     * Test for file upload with INPUT of type FILE.
     */
    @RunUI(value="cancelThenUpload.html")
    public static void cancelThenUpload() {
        doUploadTask("resources/uploadSingle.html");
    }

    static ServerSocket server = null;
    private static void doUploadTask(final String task) {
        // Please do not change the port; it is used in non-generated HTML-files.
        final int port = 17829;
        if (server == null) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        server = new ServerSocket();
                        server.bind(new InetSocketAddress("127.0.0.1", port));
                    } catch (IOException e) {
                        server = null;
                        Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE
                            , "Could not create server"
                            , e);
                    }
                    while (server != null) {
                        try {
                            Socket connected = server.accept();
                            connected.setKeepAlive(true);
                            connected.setSoTimeout(5000);
                            (new FileUploadServer(connected)).start();
                        } catch (IOException e) {
                            Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE
                                , "Problem while establishing connection to server"
                                , e);
                        }
                    }
                }
            }).start();
        }
        FileInputLauncher.run(FileChooser.class.getResource(task).toExternalForm());
    }
}
