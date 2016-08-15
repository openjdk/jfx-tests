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

package client.test.runner;

import com.sun.interview.YesNoQuestion;
import com.sun.javatest.Status;

/**
 *
 * @author shura, mrkam, Sergey Grinev, Victor Shubov
 */
public class JcovTestScript extends TestScript {

    private boolean doCoverage = false;
    private static final String JCOV_RESULT_FOLDER = "jcov-results/";
    private static final String JCOV_LIBRARY_PATH = "../../tools/lib/jCovb13/jcov.jar";

    @Override
    protected synchronized void interrupt(Status status) {
        System.out.println("interrupting: kill process");
        if (doCoverage) {
            /*
             * Workaround.
             * Sometimes,when we use jCov, we can't interrupt test,
             * so we need to do this manualy.
             */
//            ProcessKiller killer = new ProcessKiller(5000L, process, cmdServer, cmdSocket);
            Thread killer = getProcessKiller();
            killer.start();
        }
        super.interrupt(status);
    }

    @Override
    protected String getAdditionalOptions(){
        // JCOV - related:
        String jvmArgCoverage = "";
        final String testClassName = td.getParameter(RunUITestFinder.UNIT_TEST_CLASS_NAME);
        final String testName = td.getParameter(RunUITestFinder.TEST_NAME);
        final String doCoverageStrValue = lookup(BasicJcovFXInterview.COVERAGE_TAG, YesNoQuestion.NO);
        String[] includeClasses = BasicJcovFXInterview.COVERAGE_LIST;
        if (doCoverageStrValue.equals(YesNoQuestion.YES)) {
            this.doCoverage = true;

            // prepare class list for coverage
            String arCoverage[] = null;
            try {
                arCoverage = getSavedEnv().lookup(BasicJcovFXInterview.COVERAGE_LIST_TAG);
            } catch (Exception ex) {
                includeClasses = BasicJcovFXInterview.COVERAGE_LIST;
            }
            if (arCoverage != null && arCoverage.length > 1) {
                includeClasses = arCoverage;
            } else if (arCoverage != null) {
                includeClasses = arCoverage[0].split("\n");
            }

            // create jvmArgCoverage part of commandline
            for (String s : includeClasses) {
                jvmArgCoverage += "include=" + s + ",";
            }
//            deleteOldResultsAndTrash();
//            checkJCov();
            final String resultFile = testClassName + "." + testName + ".xml";
            jvmArgCoverage = "-javaagent:" + JCOV_LIBRARY_PATH + "=" + jvmArgCoverage + "type=all,file=" + JCOV_RESULT_FOLDER + resultFile;

        } else {
            this.doCoverage = false;
        }
        return jvmArgCoverage;
    }

}
