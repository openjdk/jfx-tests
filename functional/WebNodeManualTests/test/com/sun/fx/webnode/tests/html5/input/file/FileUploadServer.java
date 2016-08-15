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

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple HTTP server to test file uploads.
 * @author Irina Grineva
 */
public class FileUploadServer extends Thread {

    private Socket connectedClient = null;
    private BufferedReader inFromClient = null;
    private DataOutputStream outToClient = null;

    public FileUploadServer(Socket client) {
        connectedClient = client;
    }

    @Override
    public void run() {
        String currentLine;
        String boundary = null;
        String longBoundary = null;
        int contentLength = -1;
        int contentLengthAccepted = 0;
        boolean isMultipartFormData = false;
        boolean bodyStarted = false;

        try {
            inFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
            StringBuilder request = new StringBuilder();
            do {
                currentLine = inFromClient.readLine();
                request.append(currentLine);
                request.append("\n");

                if (bodyStarted) {
                    contentLengthAccepted += currentLine.getBytes("UTF-8").length + 2; // + \r\n
                }

                if ("".equals(currentLine)) {
                    bodyStarted = true;
                } else {
                    if (currentLine.startsWith("Content-Length")) {
                        contentLength = Integer.parseInt(currentLine.split(": ")[1]);
                    }
                    else {
                        if (currentLine.indexOf("Content-Type: multipart/form-data") != -1) {
                            boundary = currentLine.split("boundary=")[1];
                            isMultipartFormData = true;
                        } else {
                            if (boundary != null && currentLine.indexOf(boundary) != -1 && longBoundary == null) {
                                longBoundary = currentLine;
                            }
                        }
                    }
                }
            } while ((contentLength == -1) || (contentLengthAccepted < contentLength));

            String requestFull = request.toString();
            Logger.getLogger(FileUploadServer.class.getName()).log(Level.FINEST, "Got request:\n{0}", requestFull);
            if (requestFull.indexOf("POST") != -1) {
                if (isMultipartFormData) {
                    String[] requestParts = requestFull.split(longBoundary);
                    if (requestParts.length <= 1) {
                        sendOkResponse("No file data was sent!");
                    } else {
                        StringBuilder requestResponse = new StringBuilder();
                        for (int i = 1; i < requestParts.length; i++) {
                            if (!requestParts[i].startsWith("--")) {
                                requestResponse.append(i)
                                    .append(":")
                                    .append(requestParts[i])
                                    .append("<br>");
                            }
                        }
                        sendOkResponse("Files successfully uploaded:<br>" + requestResponse.toString());
                    }
                } else {
                    sendOkResponse("Not multipart/form-data request!");
                }
            } else {
                sendOkResponse("Not a POST request!");
            }
        } catch (Exception e) {
            Logger.getLogger(FileUploadServer.class.getName()).log(Level.SEVERE, "Error during file upload", e);
        }
    }

    public void sendOkResponse (String responseString) throws Exception {
        outToClient = new DataOutputStream(connectedClient.getOutputStream());
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.1 200 OK" + "\r\n");
        response.append("Content-Type: text/html" + "\r\n");
        response.append("Content-Length: ").append(responseString.length()).append("\r\n");
        response.append("Connection: close\r\n");
        response.append("\r\n");
        response.append(responseString);
        outToClient.writeBytes(response.toString());
        outToClient.flush();
        outToClient.close();
    }
}