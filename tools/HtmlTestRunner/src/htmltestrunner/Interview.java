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

import com.sun.interview.Question;
import com.sun.interview.StringQuestion;
import com.sun.javatest.Parameters;
import com.sun.javatest.Parameters.EnvParameters;
import com.sun.javatest.TestEnvironment;
import com.sun.javatest.interview.BasicInterviewParameters;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shura
 */
public class Interview extends BasicInterviewParameters
        implements Parameters.EnvParameters {

    /**
     *
     * @param tag
     * @param ts
     * @throws Fault
     */
    public Interview(String tag, TestSuite ts) throws Fault {
        super(tag, ts);
    }

    /**
     *
     * @param args
     * @throws Fault
     */
    @Override
    public void init(String[] args) throws Fault {
        setFirstQuestion(fq);
    }

    /**
     *
     * @return
     */
    public Question getEnvFirstQuestion() {
        return fq;
    }

    /**
     *
     * @return
     */
    public EnvParameters getEnvParameters() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public TestEnvironment getEnv() {
        HashMap envProps = new HashMap();
        export(envProps);

        String name = "default value";
        if (name == null || name.length() == 0) {
            name = "main.error.noName";
        }

        String desc = "main.interview.name";

        try {
            return new TestEnvironment(name, envProps, desc);
        } catch (TestEnvironment.Fault e) {
            throw new Error("unexpected problem creating the TCK environment");
        }
    }
    private Question fq = new StringQuestion(this, "default value") {

        public Question getNext() {
            return getEnvSuccessorQuestion();
        }

        @Override
        public void export(Map data) {
            String cmd = "default value";
            data.put("default value", cmd);
        }
    };
}
