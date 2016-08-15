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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author Sergey Grinev
 */
public class JettyServer {

    private final Server server;
    private ResourceHandler handler = new ResourceHandler();
    private static Map servers = new HashMap <Integer, JettyServer> ();

    /**
     *
     * @param path
     */
    public void setBaseDir(String path) {
        handler.setResourceBase(path);
    }

    // singleton staff
    private JettyServer(int port) {
        System.out.println("Starting server to provide plugin htmls on port " + port);
        server = new Server(port);
        handler.setDirectoriesListed(true);
        server.setHandler(handler);
        try {
            server.start();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to start server.", ex);
        }
    }

    /**
     *
     * @param port
     * @return
     */
    public static JettyServer getInstance (int port) {
        JettyServer server = (JettyServer) servers.get (new Integer (port));
        if (server == null) {
            server = new JettyServer (port);
            servers.put (new Integer (port), server);
        }
        return server;
    }
}
