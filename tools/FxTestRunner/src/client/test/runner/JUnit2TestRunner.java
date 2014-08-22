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

import java.util.logging.Level;
import java.util.logging.Logger;
import test.javaclient.shared.OtherThreadRunner;

/**
 *
 * @author Sergey Grinev, Victor Shubov
 */
class JUnit2TestRunner implements Abstract2TestRunner {

    private CommonTestRunnerWorker cw;

    public JUnit2TestRunner() {
        cw = new CommonTestRunnerWorker(this);
    }

    public JUnit2TestRunner(String... args) {
        cw = new CommonTestRunnerWorker(this);
        cw.setArgs(args);
    }

    @Override
    public void exit(Status exitStatus) {
        cw.exit(exitStatus);
    }

    @Override
    public Abstract2TestRunner.Status getCurrentStatus() {
        // automated test is not passed until it reports this - TODO: what about nodesc?
        final Abstract2TestRunner.Status currentStatus = Abstract2TestRunner.Status.FAIL;
        return currentStatus;
    }

    public static void main(final String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new JUnit2TestRunner(args).prepareAndRunTest();
            }
        }).start();

        //Killer thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JUnit2TestRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
                OtherThreadRunner.putPoison();
            }
        }).start();

        //Main thread will execute significant tasks, before atking poison.
        OtherThreadRunner.executeTasks();
    }

    final void prepareAndRunTest() {
        cw.prepareAndRunTest();
    }

    @Override
    public void checkUI() {
        // do nothing
    }

    @Override
    public void stopUI() throws Throwable {
        // do nothing
    }

    @Override
    public void runUI() throws Throwable {
        cw.runUI();
    }
}
