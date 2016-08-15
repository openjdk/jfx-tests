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

import client.test.CheckUI;
import client.test.RunUI;
import client.test.StopUI;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author shura, mrkam, Victor Shubov
 */
public class TestRunner implements Abstract2TestRunner {
    private CommonTestRunnerWorker cw;

    /**
     *
     * @param args
     */
    public TestRunner(String... args) {
        cw = new CommonTestRunnerWorker(this);
        cw.setArgs(args);
    }

    /**
     *
     */
    public TestRunner() {
        cw = new CommonTestRunnerWorker(this);
        final String cmdline = System.getProperty("sun.java.command");
        final String[] splittedCmdLine = cmdline.split(" ");
        final String[] args = new String[splittedCmdLine.length-1];

        for (int i = 0; i < splittedCmdLine.length-1; ++i) {
            args[i] = splittedCmdLine[i+1];
        }
        cw.setArgs(args);

        System.out.println(" cmd: " + cmdline);
    }

    /**
     *
     * @param exitStatus
     */
    @Override
    public void exit(Status exitStatus) {
        cw.exit(exitStatus);
    }

    /**
     *
     * @throws Throwable
     */
    @Override
    public void runUI() throws Throwable {
        callAnnotated(cw.getTestClassName(), cw.getTestName() == null ? null : new AnnotationValidator() {
            @Override
            public boolean isValid(Annotation ann) {
                if (ann instanceof RunUI) {
                    String value = ((RunUI) ann).value();
                    // we assume that RunUI without value is always valid
                    return value == null || value.length() == 0 || value.equals(cw.getTestName());
                }
                return false;
            }
        }, RunUI.class, true);
    }

    // unfortunately we can't have ERROR here as we would want for nodesc tests
    // we must proclaim PASS for manual tests due to JavaTest architecture
    // (one more reason to get rid of nodesc tests)
    private Status currentStatus = Status.PASS;

    /**
     *
     * @return
     */
    @Override
    public Status getCurrentStatus() {
        return currentStatus;
    }

    /**
     *
     * @throws Throwable
     */
    @Override
    public void checkUI() throws Throwable {
        callAnnotated(cw.getTestClassName(), CheckUI.class, false);
    }

    /**
     *
     * @throws Throwable
     */
    @Override
    public void stopUI() throws Throwable {
        callAnnotated(cw.getTestClassName(), StopUI.class, false);
    }

    private interface AnnotationValidator {
        boolean isValid(Annotation ann);
    }

    private void callAnnotated(String testClassName, Class annotationClass, boolean required) throws Throwable {
        callAnnotated(testClassName, null, annotationClass, required);
    }

    private void callAnnotated(String testClassName, AnnotationValidator av, Class annotationClass, boolean required) throws Throwable {
        if (testClassName != null) {
            Class testClass = Class.forName(testClassName);
            Method m = null;
            for (Method current : testClass.getMethods()) {
                if (current.getAnnotation(annotationClass) != null) {
                    if (av == null || av.isValid(current.getAnnotation(annotationClass))) {
                        m = current;
                        break;
                    }
                }
            }
            if (m == null && !required) {
                return;
            }
            if (m != null) {
                cw.report("Calling " + testClass.getName() + "." + m.getName());
                try {
                    m.invoke(null);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                } catch (IllegalAccessError e) {
                    throw new RuntimeException("Insufficient permissions for method "
                            + testClass.getName() + "." + m.getName());
                }
            }
        }
    }

    // backdoor for no_description tests

    /**
     *
     */

    public static void fail() {
        getInstance().cw.exit(Status.FAIL);
    }

    /**
     *
     */
    public static void pass() {
        getInstance().cw.exit(Status.PASS);
    }

    /**
     *
     */
    public static void exception() {
        getInstance().cw.exit(Status.ERROR);
    }

    private static TestRunner instance;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        instance = new TestRunner(args);
        instance.cw.prepareAndRunTest();
    }

    private static TestRunner getInstance() {
        if (instance == null) { //for standalone debugging runs, no correst stopUI would be called
            System.err.println("WARNING: no test class provided, this is debug run or invalid test setup");
            instance = new TestRunner(new String[] {null});
        }
        return instance;
    }
}
