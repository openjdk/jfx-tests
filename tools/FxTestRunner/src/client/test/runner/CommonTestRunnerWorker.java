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

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.Semaphore;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import test.javaclient.shared.screenshots.ImagesManager;

/**
 *
 * @author shura, mrkam, Sergey Grinev, Victor Shubov
 */
public class CommonTestRunnerWorker {

    static final int DEFAULT_PORT = 56179;

    private final JUnitCore core = new JUnitCore();
    private Abstract2TestRunner testRunner;
    private final Semaphore uilock = new Semaphore(0);
    private Socket socket;
    private String testClassName;
    private String testName;
    private long testStart;
    private String host = "127.0.0.1";
    private boolean debugMode = false;
    private volatile boolean isExiting = false;

    private CommonTestRunnerWorker() {
    }

    /**
     *
     * @param _testRunner
     */
    public CommonTestRunnerWorker(Abstract2TestRunner _testRunner) {
        testRunner = _testRunner;
    }

    private final Thread exitThread = new Thread(new Runnable() {
        @Override
        public void run() {
            waitUI();
            exit(testRunner.getCurrentStatus());
        }
    }, "exit waiter");

    static int getTestPort() {
        return Integer.valueOf(System.getProperty("masterPort", String.valueOf(DEFAULT_PORT)));
    }
    /**
     *
     * @throws Throwable
     */
    public void runUI() throws Throwable {
        System.out.println("CommonTestRunnerWorker.runUI(): starting test...");
        core.addListener(new RunListener() {

            @Override
            public void testAssumptionFailure(Failure failure) {
                System.out.println("junit failure: " + failure.getMessage());
                failure.getException().printStackTrace();
                exit(Abstract2TestRunner.Status.FAIL);
            }

            @Override
            public void testFailure(Failure failure) throws Exception {
                System.out.println("junit failure: " + failure.getMessage());
                failure.getException().printStackTrace();
                exit(Abstract2TestRunner.Status.FAIL);
            }

        });
        Result r = core.run(Request.method(Class.forName(testClassName), testName));
        System.out.println("got result = " + r.wasSuccessful());
        exit(r.wasSuccessful() ? Abstract2TestRunner.Status.PASS : Abstract2TestRunner.Status.FAIL);
    }

    private void waitUI() {
        if (!uilock.tryAcquire()) {
            report("Waiting for UI to start or nodesc/auto test to finish");
            try {
                uilock.acquire();
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
        }
        report("UI started successfully");
    }

    /**
     *
     * @param text
     */
    protected void report(String text) {
        long time = System.nanoTime() - testStart;
        System.out.printf("%dms: %s\n", time / 1000000, text);
    }

    private void handleEx(Throwable ex) {
        ex.printStackTrace();
        exit(Abstract2TestRunner.Status.ERROR);
    }

    /**
     *
     * @param args
     */
    protected final void setArgs(final String... args) {
        if (args != null && args.length > 0) {
            if (args.length == 1) {
                testClassName = args[0];
            } else if (args.length >= 3 && args[0].equals("--debug")) {
                System.err.println("debug mode");
                debugMode = true;
                testClassName = args[1];
                testName = args[2];
            } else {
                System.err.println("remote mode");
                testClassName = args[0];
                host = args[1];
            }
        }
        if (null == testClassName) {
            System.err.println("null == testClassName, argslen=" + args.length);
        }
    }
    
    /**
     *
     * @return
     */
    public String getTestClassName() {
        return testClassName;
    }

    /**
     *
     * @return
     */
    public String getTestName() {
        return testName;
    }

    /**
     *
     */
    protected final void prepareAndRunTest() {

        if (debugMode) {
            runTest();
            System.err.println("Test done.");
            return;
        }

        testStart = System.nanoTime();
        try {
            int port = getTestPort();
            report("Looking for server at host " + host + ", port " + port + "...");
            socket = new Socket(host, port);
            ObjectInputStream readerStream = new ObjectInputStream(socket.getInputStream());
            report("Connected");

            loop:
            do {
                Command trc = (Command) readerStream.readObject();
                report("Got command: " + trc.type + ":" + trc.param);
                switch (trc.type) {
                    case SET_TEST_CLASS:
                        testClassName = trc.param;
                        break;
                    case SET_BASEDIR:
                        //TODO: this design is a bit flawed as we introducing backward
                        //dependency on SharedTestUtils here.
                        ImagesManager.getInstance().setBaseDir(trc.param);
                        break;
                    case SET_ABSOLUTE_DIR:
                        ImagesManager.getInstance().setAbsoluteDir(trc.param);
                        break;
                    case SET_TEST_NAME:
                        testName = trc.param;
                        break;
                    case SET_ERR:
                        System.setErr(new PrintStream(new File(trc.param)));
                        break;
                    case SET_OUT:
                        System.setOut(new PrintStream(new File(trc.param)));
                        break;
                    case RUN_TEST:
                        if (testClassName == null) {
                        handleEx(new IllegalStateException("Running test without classname!"));
                        break loop;
                    } else {
                        runTest();
                    }
                        break;
                    case CHECK_UI_AND_EXIT:
                        try {
                        waitUI();
                        testRunner.checkUI();
                    } catch (Throwable ex) {
                        handleEx(ex);
                    }
                        exit(testRunner.getCurrentStatus());
                        break;
                    case EXIT:
                        // we want be able to wait for abort here
                        if (!exitThread.isAlive()) {
                        exitThread.start();
                    }
                        break;
                    case ABORT:
                        exit(Abstract2TestRunner.Status.ABORTED);
                        break;
                    default:
                        exit(Abstract2TestRunner.Status.UKNOWN_COMMAND);
                        break;
                }

            } while (true);
            readerStream.close();
        } catch (Throwable ex) {
            if (!isExiting) {
                handleEx(ex);
            }
        }
    }

    private void runTest() {
        testStart = System.nanoTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                report("CommonTestRunnerWorker.runTest(): starting UI ...");
                try {
                    testRunner.runUI();
                } catch (Throwable ex) {
                    handleEx(ex);
                } finally {
                    report("UI started");
                    uilock.release(1000);
                }
            }
        }).start();
    }

    /**
     *
     * @param exitStatus
     */
    protected synchronized void exit(Abstract2TestRunner.Status exitStatus) {
        Abstract2TestRunner.Status exitValue = exitStatus;
        System.err.println("called exit " + exitValue);

        try {
            System.err.println("call stopui");
            testRunner.stopUI();
        } catch (Throwable ex) {
            ex.printStackTrace();
            exitValue = Abstract2TestRunner.Status.ERROR;
        }

        report("Exiting with status: " + exitValue);
        isExiting = true;
        if (socket != null && socket.isConnected()) {
            try {
                new ObjectOutputStream(socket.getOutputStream()).writeObject(exitValue);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.exit(exitValue.getN());
    }

    static class Command implements Serializable {
    static enum CommandType {
        CHECK_UI_AND_EXIT,
        ABORT,
        STOP_UI,
        EXIT,
        SET_TEST_CLASS,
        SET_TEST_NAME,
        SET_ERR,
        SET_OUT,
        //SET_SUITE_NAME,
        SET_BASEDIR,
        SET_ABSOLUTE_DIR,
        RUN_TEST;
    }
        final String param;
        final CommandType type;

        @Override
        public String toString() {
            return "TestRunnerCommand{type='" + type + "',param='" + param + "'}";
        }

        public Command(CommandType cmd, String param) {
            this.param = param;
            this.type = cmd;
        }
    }
    
}