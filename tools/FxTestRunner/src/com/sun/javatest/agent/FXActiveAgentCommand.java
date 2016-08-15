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

package com.sun.javatest.agent;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.javatest.Command;
import com.sun.javatest.Status;

/**
 *
 * @author vshubov
 */
public class FXActiveAgentCommand extends Command {

    /**
     *
     * @param args
     * @param err
     * @param out
     * @return
     */
    public Status run(String[] args, PrintWriter err, PrintWriter out) {
        boolean executeMain = true;
        String classPath = null;
        String tag = null;
        boolean localizeArgs = false;

        // analyse options
        int i = 0;
        for (; i < args.length && args[i].startsWith("-"); i++) {
            if ((args[i].equals("-cp") || args[i].equals("-classpath"))
                    && i + 1 < args.length) {
                classPath = args[++i];
            }
            else if (args[i].equals("-m") || args[i].equals("-mapArgs")) {
                localizeArgs = true;
            }
            else if ((args[i].equals("-t") || args[i].equals("-tag"))
                    && i + 1 < args.length) {
                tag = args[++i];
            }
            else if (args[i].equals("-c") || args[i].equals("-command")) {
                executeMain = false;
            }
            else
                return Status.error("Unrecognized option: " + args[i]);
        }

        if (i == args.length)
            return Status.error("No command specified");

        String cmdClass = args[i++];

        String[] cmdArgs = new String[args.length - i];
        System.arraycopy(args, i, cmdArgs, 0, cmdArgs.length);

        if (tag == null)
            tag = cmdClass;

        try {
            AgentManager mgr = AgentManager.access();
            AgentManager.Task t = mgr.connectToActiveAgent();

            if (classPath != null)
                t.setClassPath(classPath);

            if (executeMain) {
                out.println("Executing main " + cmdClass + "' via "
                        + t.getConnection().getName());
                return t.executeMain(tag, cmdClass, cmdArgs, localizeArgs,
                        err, out);

            }
            else {
                out.println("Executing command '" + cmdClass + "' via "
                        + t.getConnection().getName());

                return t.executeCommand(tag, cmdClass, cmdArgs, localizeArgs,
                        err, out);
            }
        }
        catch (InterruptedException e) {
            return Status.error("Interrupted while waiting for agent");
        }
        catch (IOException e) {
            return Status.error("Error accessing agent: " + e);
        }
        catch (ActiveAgentPool.NoAgentException e) {
            return Status.error("No agents available for use: "
                    + e.getMessage());
        }
    }
}
