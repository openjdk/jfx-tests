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

import com.sun.interview.*;
import com.sun.javatest.Parameters.EnvParameters;
import java.util.Map;

public class BasicJcovFXInterview extends BasicFXInterview
        implements EnvParameters {

    public final static String COVERAGE_TAG = "jcov";
    public final static String COVERAGE_LIST_TAG = "jcov_list";
    public final static String[] COVERAGE_LIST = {"com.sun.openpisces.*","com.sun.pisces.*","com.javafx.*", "com.sun.javafx.*", "javafx.*", "com.sun.scenario.*", "com.sun.mat.*", "com.sun.prism.*"};

    public BasicJcovFXInterview() throws Fault {
        super();
    }

    @Override
    protected Question getEnvFirstQuestion() {
        return qCoverage;
    }


    private YesNoQuestion qCoverage = new YesNoQuestion(this, COVERAGE_TAG, YesNoQuestion.NO) {
        @Override
        public Question getNext() {
            if (getValue().toString().equals(YES)) {
                return qCoverageIncludes;
            }
            return qName;
        }

        @Override
        public String getText() {
            return getSummary();
        }

        @Override
        public String getSummary() {
            return "Calculate code coverage";
        }

        @Override
        public void export(Map data) {
            data.put(COVERAGE_TAG, getValue());
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(COVERAGE_TAG)) {
                setValue((String) data.get(COVERAGE_TAG));
            }
        }
    };

    private StringListQuestion qCoverageIncludes = new StringListQuestion(this, COVERAGE_LIST_TAG) {
        {
            this.setDefaultValue(COVERAGE_LIST);
        }

        @Override
        public Question getNext() {
            return qName;
        }

        @Override
        public String getText() {
            return getSummary();
        }

        @Override
        public String getSummary() {
            return "Code coverage: classes";
        }

        @Override
        public String[] getValue() {
            return super.getValue();
        }

        @Override
        public void export(Map data) {
            save(data);
        }

        @Override
        public void load(Map data) {
            super.load(data);
        }
    };

}
