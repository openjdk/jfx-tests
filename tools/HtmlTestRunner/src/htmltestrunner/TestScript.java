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

package htmltestrunner;

import com.sun.javatest.Script;
import com.sun.javatest.TestDescription;
import com.sun.javatest.TestEnvironment;
import com.sun.javatest.Status;
import com.sun.javatest.WorkDirectory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shura
 */
public class TestScript extends Script {

    /**
     *
     */
    protected String workdirPath = null;
    private Status status = Status.passed("");
    private TestDialog dialog;
    private boolean interrupted = false;

    /**
     *
     */
    public TestScript() {
    }

    private String getJtrDir(TestDescription td) {
        return workdirPath + File.separator + td.getParameter("testName");
    }

    /**
     *
     * @param td
     * @param env
     */
    protected void showTestDialog(TestDescription td, TestEnvironment env) {
        synchronized (this) {
            try {
                dialog = TestDialog.createDialog(td.getParameter(ExtensionTestFinder.INSTR_FILE_PATH_PROP), td.getParameter("testName"), getJtrDir(td));
            } catch (InterruptedException ex) {
                //never gonna happen
            } catch (InvocationTargetException ex) {
                setStatus(Status.error(ex.getMessage()));
                return;
            }
        }
        dialog.setVisible(true);
        setStatus(dialog.getStatus());
    }

    /**
     *
     * @param args
     * @param td
     * @param env
     * @return
     */
    public Status run(String[] args, TestDescription td, TestEnvironment env) {
        String jtrDir = getJtrDir(td);
        new File(jtrDir).mkdirs();
        try {
            before(td, jtrDir);
        } catch (Throwable e) {
            interrupt(Status.error(e.toString()));
        }
        if (getStatus().isPassed()) {
            showTestDialog(td, env);
            if (getStatus().isPassed()) {
                try {
                    after(td, jtrDir);
                } catch (Throwable e) {
                    interrupt(Status.error(e.toString()));
                }
            }
        }
        return getStatus();
    }

    /**
     *
     * @param workDir
     */
    @Override
    public void initWorkDir(WorkDirectory workDir) {
        super.initWorkDir(workDir);
        workdirPath = workDir.getPath();
    }

    /**
     *
     * @param description
     * @param resultDir
     * @throws Throwable
     */
    protected void before(TestDescription description, String resultDir) throws Throwable {
    }

    /**
     *
     * @param description
     * @param resultDir
     * @throws Throwable
     */
    protected void after(TestDescription description, String resultDir) throws Throwable {
    }

    /**
     *
     * @return
     */
    protected synchronized Status getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public synchronized void setStatus(Status status) {
        if (this.status.isPassed()) {
            this.status = status;
        }
    }

    /**
     *
     * @return
     */
    public synchronized boolean isInterrupted() {
        return interrupted;
    }

    /**
     *
     * @param status
     */
    protected synchronized void interrupt(Status status) {
        interrupted = true;
        if (this.status.isPassed()) {
            this.status = status;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }
}
