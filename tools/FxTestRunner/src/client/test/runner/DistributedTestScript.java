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

package client.test.runner;

import client.util.CtrUtils;
import com.sun.javatest.Status;
import com.sun.javatest.TestDescription;
import com.sun.javatest.TestEnvironment.Fault;
import com.sun.javatest.TestResult;
import com.sun.javatest.util.StringArray;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author sg
 */
public class DistributedTestScript extends TestScript {

    /**
     *
     */
    protected static final String ACTIVE_AGENT_COMMAND = "com.sun.javatest.agent.FXActiveAgentCommand";

    /**
     *
     */
    protected static final String PROCESS_COMMAND = "com.sun.javatest.lib.FXProcessCommand";

    /**
     *
     */
    protected static final String TEST_RUNNER_NAME = "clientTestRunner";

    /**
     *
     * @param command
     * @throws IOException
     */
    @Override
    protected void doRunTd(String[] command) throws IOException {
        final String[] cmd = CtrUtils.deleteEmptyElements(command);
        new Thread(new Runnable() {
            @Override
            public void run() {
                invokeAgentCommand(cmd);
            }
        }).start();
    }

    /**
     *
     * @param td
     * @param resultDir
     * @return
     * @throws IOException
     * @throws Fault
     */
    @Override
    protected String[] tdCmdArgs(TestDescription td, String resultDir, int port) throws IOException, Fault {
        String id = env.lookup(BasicFXInterview.TESTSUITE_ID)[0];
        String host = env.lookup(BasicFXInterview.JAVATEST_HOSTNAME)[0];
        String verbose = Boolean.getBoolean("javatest.FXProcessCommand.verbose") ? "-v" : "";
//        String[] args = super.tdCmdArgs(td, id + File.separator + td.getRootRelativePath());
        String[] args = super.tdCmdArgs(td, workdirPath + File.separator + td.getParameter("testName"), port);

        String[] ret = {
            "-mapArgs",
            "-c",
            "-classpath",
            System.getProperty("java.class.path"),
            PROCESS_COMMAND,
            id,
//            "-execDir", id,
            verbose
        };
        return TestScript.addToArray(ret, TestScript.addToArray(args, host));
    }

    @Override
    protected synchronized void interrupt(Status status) {
        if(section != null && section.isMutable()){
            section.setStatus(status);
        }
        super.interrupt(status);
    }

    private TestResult.Section section;

    /**
     *
     * @param args
     * @return
     */
    protected Status invokeAgentCommand(String[] args) {
        Status s = null;
        com.sun.javatest.Command testCommand;

        try {
            section = getTestResult().createSection(TEST_RUNNER_NAME);

            section.getMessageWriter().println("command: " + ACTIVE_AGENT_COMMAND + " " + StringArray.join(args));

            PrintWriter out1 = null;
            PrintWriter out2 = null;
            try {
                out1 = section.createOutput(cmdOut1Name);
                out2 = section.createOutput(cmdOut2Name);

                Class c = (loader == null ? Class.forName(ACTIVE_AGENT_COMMAND) : loader.loadClass(ACTIVE_AGENT_COMMAND));
                testCommand = (com.sun.javatest.Command) (c.newInstance());
                testCommand.setClassLoader(loader);
                s = testCommand.run(args, out1, out2);

            } finally {
                if (out2 != null) {
                    out2.close();
                }
                if (out1 != null) {
                    out1.close();
                }
            }
            if (section.isMutable()) {
                section.setStatus(s);
            }
            return s;
        } catch (Throwable e) {
            e.printStackTrace();
            return Status.error(e.getMessage());
        }
    }
}
