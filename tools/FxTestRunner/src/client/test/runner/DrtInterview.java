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
import com.sun.interview.Question;
import com.sun.interview.ChoiceQuestion;
import com.sun.javatest.Parameters.EnvParameters;
import com.sun.javatest.TestEnvironment;
import com.sun.javatest.interview.BasicInterviewParameters;
import com.sun.interview.StringQuestion;
import com.sun.interview.FileQuestion;
import com.sun.interview.DirectoryFileFilter;
import com.sun.interview.AllFilesFileFilter;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import client.test.runner.interview.PipelineGroupQuestion;
import client.test.runner.interview.PipelineQuestion;

/**
 *
 * @author vshubov
 */
public class DrtInterview extends BasicInterviewParameters
        implements EnvParameters {

    /**
     *
     */
    public static String FX_SDK_HOME_PARAM_NAME = "javafx";

    /**
     *
     */
    public static String BROWSER_PARAM_NAME = "browserPath";

    /**
     *
     */
    public static String PROXY_PARAM_NAME = "proxy";

    private String javaFXHome;
    private String browserExec = "firefox";

    /**
     *
     * @throws Fault
     */
    public DrtInterview() throws Fault {
        super(null);
    }

    /**
     *
     * @return
     */
    @Override
    protected Question getEnvFirstQuestion() {
        return qName;
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
        try {
            String name = qName.getValue();
            if (name == null || name.length() == 0) {
                name = "unknown";
            }
            return new TestEnvironment(name, envProps, "configuration interview");
        } catch (TestEnvironment.Fault e) {
            throw new Error("should not happen" + e);
        }
    }
    private StringQuestion qName = new StringQuestion(this, "name") {

        @Override
        public String getText() {
            return getSummary();
        }

        @Override
        public String getSummary() {
            return "Configuration name";
        }

        @Override
        public Question getNext() {
            if ("".equals(getValue()) || getValue() == null) {
                return null;
            }
            return qJavaFX;
        }

        @Override
        public void export(Map data) {
            data.put("name", value);
        }

        @Override
        public void load(Map data) {
            setValue((String) data.get("name"));
        }
    };
    private FileQuestion qJavaFX = new FileQuestion(this, FX_SDK_HOME_PARAM_NAME) {

        {
            this.setFilter(new DirectoryFileFilter("JavaFX SDK containing directory"));
        }

        @Override
        public String getText() {
            return getSummary();
        }

        @Override
        public String getSummary() {
            return "JavaFX SDK Home";
        }

        @Override
        public void clear() {
            if (javaFXHome != null) {
                setValue(javaFXHome);
            } else {
                setValue("");
            }
        }

        @Override
        public Question getNext() {
            if (!value.isDirectory()) {
                return null;
            }
            return qBrowser;
        }

        @Override
        public void export(Map data) {
            javaFXHome = value.getPath();
            data.put(FX_SDK_HOME_PARAM_NAME, javaFXHome);
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(FX_SDK_HOME_PARAM_NAME)) {
                javaFXHome = (String) data.get(FX_SDK_HOME_PARAM_NAME);
                if (javaFXHome != null) {
                    setValue(new File(javaFXHome));
                }
            }
        }
    };

    private FileQuestion qBrowser = new FileQuestion(this, BROWSER_PARAM_NAME) {

        {
             this.setFilter(new AllFilesFileFilter("Browser executable"));
        }

        @Override
        public String getText() {
            return getSummary();
        }

        @Override
        public String getSummary() {
            return "Browser executable";
        }

        @Override
        public void clear() {
            setValue(browserExec);
        }

        @Override
        public Question getNext() {
            if (!value.isFile()) {
                return null;
            }
            return qPipelineGroup;
        }

        @Override
        public void export(Map data) {
            browserExec = value.getPath();
            data.put(BROWSER_PARAM_NAME, browserExec);
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(BROWSER_PARAM_NAME)) {
                browserExec = (String) data.get(BROWSER_PARAM_NAME);
                if (browserExec != null) {
                    setValue(new File(browserExec));
                }
            }
        }
    };

    private StringQuestion qProxy = new StringQuestion(this, "proxy") {
        @Override
        public void clear() {
            setValue("");
        }

        @Override
        public String getText() {
            return "Proxy settings in format: host:port\nE.g. someproxy.example.com:80";
        }

        @Override
        public String getSummary() {
            return "Proxy settings";
        }

        @Override
        public Question getNext() {
            String val = getValue();
            if (val == null || val.trim().length() == 0 || CtrUtils.getProxyUrl(val) != null) {
                return getEnvSuccessorQuestion();
            } else {
                return null;
            }
        }

        @Override
        public void export(Map data) {
            data.put(PROXY_PARAM_NAME, value);
        }

        @Override
        public void load(Map data) {
            setValue((String) data.get(PROXY_PARAM_NAME));
        }
    };

    private ChoiceQuestion qPipelineGroup = new PipelineGroupQuestion (this, qProxy);


}

