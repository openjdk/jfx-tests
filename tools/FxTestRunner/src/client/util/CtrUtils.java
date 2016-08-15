/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */

package client.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sergey Grinev
 */
public class CtrUtils {

    private static final String HTTP = "http://";

    /**
     *
     * @param url
     * @return
     */
    public static URL getProxyUrl(String url) {
        if (url == null) {
            return null;
        }
        url = url.trim();
        try {
            String urlWithProtocol = url.indexOf(HTTP) > -1 ? url : HTTP + url;
            return new URL(urlWithProtocol);
        } catch (MalformedURLException ex) {
            System.out.println("Invalid proxy data:" + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param e
     * @return
     */
    public static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     */
    public static class OutputReader implements Runnable {

        PrintStream writer;
        BufferedReader stream;

        /**
         *
         * @param writer
         * @param stream
         */
        public OutputReader(OutputStream writer, InputStream stream) {
            this.writer = new PrintStream(writer);
            this.stream = new BufferedReader(new InputStreamReader(stream));
        }

        @Override
        public void run() {
            try {
                String s;
                while ((s = stream.readLine()) != null) {
                    writer.println(s);
                }
            } catch (IOException ex) {
            } finally {
                try {
                    stream.close();
                } catch (IOException ex) {
                    ex.printStackTrace(); // gieff jdk7 and try-with-resources
                }

                writer.close();
            }
        }
    }

    /**
     *
     * @param param
     * @return
     */
    public static String[] deleteEmptyElements(String[] param) {
        List<String> result = new ArrayList<String>();
        for (String string : param) {
            if (string.trim().length() > 0) {
                result.add(string);
            }
        }

        return result.toArray(new String[result.size()]);
    }

    private static final int JEMMY_PORT = 53669;

    /**
     *
     * @param logDir
     * @throws IOException
     */
    public static void ensureJemmyServerRun(String logDir) throws IOException {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(JEMMY_PORT);
            socket.setReuseAddress(true);
        } catch (IOException ex) {
            System.out.println("Found process on port " + JEMMY_PORT + ". We groundlessly believe it's remove jemmy server.");
            return;
        } finally {
            if (socket != null) {
                socket.close();
            }
        }

        // jemmy socket is free -- run jemmy

        //TODO: optimize classpaths for jemmy server and test in remote jemmy mode
        String[] command = new String[]{"java", "-classpath", System.getProperty("java.class.path"), "org.jemmy.input.RobotExecutor"};
        final Process jemmyProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
        String logFile = logDir + File.separator + "jemmy.out";
        new Thread(new OutputReader(new FileOutputStream(logFile), jemmyProcess.getInputStream())).start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                jemmyProcess.destroy();
            }
        }));
        System.out.println("Started remote jemmy. Log: " + logFile);
    }
}
