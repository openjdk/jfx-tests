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
import com.sun.javatest.*;
import com.sun.javatest.util.BackupPolicy;
import java.io.File;
import java.util.Map;

/**
 *
 * @author sg
 */
public class BasicJcovFXTestSuite extends TestSuite {

    public BasicJcovFXTestSuite(File root) {
        super(root);
    }

    public BasicJcovFXTestSuite(File root, Map tsInfo, ClassLoader cl) throws Fault {
        super(root, tsInfo, cl);
    }

    @Override
    public Script createScript(TestDescription td, String[] exclTestCases, TestEnvironment scriptEnv,
            WorkDirectory workDir, BackupPolicy backupPolicy) throws Fault {
        Script s;
        if (td.getKeywordTable().contains("media")) {
             s = new JcovTestScript();
            //s = new MediaTestScript();
            //throw new IllegalArgumentException("implementation excluded: media");
        } else if (YesNoQuestion.YES.equals(envLookup(scriptEnv, BasicFXInterview.REMOTE_RUN))) {
            s = new DistributedTestScript();
        } else {
            //s = new TestScript();
            s = new JcovTestScript();
        }
        s.initTestDescription(td);
        s.initExcludedTestCases(exclTestCases);
        s.initTestEnvironment(scriptEnv);
        s.initWorkDir(workDir);
        s.initBackupPolicy(backupPolicy);
        s.initClassLoader(getClassLoader());
        return s;
    }

    private String envLookup(TestEnvironment env, String property) {
        String[] ss;
        try {
            ss = env.lookup(property);
        } catch (TestEnvironment.Fault e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "Problem looking up " + property + " in the environment.");
        }
        if (ss != null && ss.length != 0) {
            return ss[0];
        }
        return property;
    }
}
