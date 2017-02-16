/*
 * Copyright (c) 2009, 2016, Oracle and/or its affiliates. All rights reserved.
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

import static client.test.runner.InterviewUtils.*;
import client.test.runner.interview.LookAndFeelQuestion;
import client.test.runner.interview.PipelineGroupQuestion;
import client.util.CtrUtils;
import com.sun.interview.*;
import com.sun.javatest.Parameters.EnvParameters;
import com.sun.javatest.TestEnvironment;
import com.sun.javatest.interview.BasicInterviewParameters;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vshubov
 */
public class BasicFXInterview extends BasicInterviewParameters
        implements EnvParameters {

    /**
     *
     */
    public final static String FX_SDK_HOME_PARAM_NAME = "javafx";

    /**
     *
     */
    public final static String EXTERNALOUTPUT = "externaloutput";

    /**
     *
     */
    public final static String PROXY_PARAM_NAME = "proxy";

    /**
     *
     */
    public final static String DRY_RUN_TAG = "dryrun";

    /**
     *
     */
    public final static String DRY_RUN_DURATION_TAG = "dryrun_duration";

    /**
     *
     */
    public static final String REMOTE_RUN = "remote";

    /**
     *
     */
    public static final String JAVATEST_HOSTNAME = "hostname";

    /**
     *
     */
    public static final String RUN_MODE_PARAM = "runmode";

    /**
     *
     */
    public static final String RUN_MODE_DESKTOP = "desktop";

    /**
     *
     */
    public static final String RUN_MODE_DESKTOP_SWING_INTEROPERABILITY = "swing_interoperability";

    /**
     *
     */
    public static final String RUN_MODE_DESKTOP_SWT_INTEROPERABILITY = "swt_interoperability";

    /**
     *
     */
    public static final String RUN_MODE_PLUGIN = "plugin";

    /**
     *
     */
    public static final String RUN_MODE_JNLP = "jnlp";

    /**
     *
     */
    public final static String BROWSER_PARAM_NAME = "browserPath";

    /**
     *
     */
    public final static String JAVAWS_PARAM_NAME = "javawsPath";

    /**
     *
     */
    public final static String JAVA_PARAM_NAME = "javaPath";

    /**
     *
     */
    public final static String ADD_EXPORTS_PARAM_NAME = "addExports";

    /**
     *
     */
    public final static String XPATCH_PARAM_NAME = "XPatch";

    /**
     *
     */
    public final static String ENABLE_ADD_EXPORTS_PARAM_NAME = "addExportsEnabled";

    /**
     *
     */
    public final static String VM_OPTIONS_PARAM_NAME = "vmOptions";

    /**
     *
     */
    public final static String TESTSUITE_ID = "testsuiteID";

    private String javaFXHome;
    private String extOut;

    /**
     *
     * @throws Fault
     */
    public BasicFXInterview() throws Fault {
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
    @Override
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

    /**
     *
     * @param data
     */
    @Override
    public void export(Map data) {
        String id = getTestSuite().getTestSuiteInfo("id");
        data.put(TESTSUITE_ID, id);
        super.export(data);
    }

    /**
     *
     * @return
     */
    public boolean isRemoteRun() {
        return YesNoQuestion.YES.equals(qRemoteRun.getValue());
    }

    /**
     *
     * @return
     */
    public File getJavaPath(){
        return qJavaPath.getValue();
    }

    public String getXPatch() {
        return qXPatch.getValue();
    }

    /**
     *
     * @return
     */
    protected Question getLastQuestion(){
        return getEnvSuccessorQuestion();
    }

    /**
     *
     */
    protected StringQuestion qName = new StringQuestion(this, "name") {
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
            return qRemoteRun;
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
    private YesNoQuestion qRemoteRun = new YesNoQuestion(this, REMOTE_RUN, YesNoQuestion.NO) {
        @Override
        public String getText() {
            return "Remote test execution: please specify whether you want to run the tests on a remote host."
                    + "The agent should be run on that host first.";
        }

        @Override
        public String getSummary() {
            return "Remote test execution";
        }
        @Override
        public Question getNext() {
            if (YesNoQuestion.YES.equals(getValue())) {
                return qHostName;
            }
            return qRunMode;
        }

        @Override
        public void clear() {
            setValue("No");
        }

        @Override
        protected void export(Map data) {
            data.put(REMOTE_RUN, getValue());
        }
    };
    private StringQuestion qHostName = new StringQuestion(this, JAVATEST_HOSTNAME) {

        @Override
        public String getText() {
            return "Enter the name of the host you are currently running Javatest harness";
        }

        @Override
        public String getSummary() {
            return "Javatest Host Name";
        }

        @Override
        public void clear() {
            String host = "localhost";
            try {
                host = java.net.InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ex) {
                //just ignore
            }
            setValue(host);
        }

        @Override
        public Question getNext() {
            return qRunMode;
        }

        @Override
        public void export(Map data) {
            data.put(JAVATEST_HOSTNAME, value);
        }
    };

    private ChoiceQuestion qRunMode = new ChoiceQuestion(this, RUN_MODE_PARAM) {
        {
            setChoices(new String[]{RUN_MODE_DESKTOP, RUN_MODE_DESKTOP_SWING_INTEROPERABILITY, RUN_MODE_DESKTOP_SWT_INTEROPERABILITY, RUN_MODE_PLUGIN, RUN_MODE_JNLP}, false);
            clear();
        }

        @Override
        public Question getNext() {
            if (RUN_MODE_PLUGIN.equals(getValue())) {
                return qBrowser;
            } else if (RUN_MODE_JNLP.equals(getValue())) {
                return qJavawsPath;
            } else {
                return qJavaPath;
            }
        }

        @Override
        public String getDefaultValue() {
            return RUN_MODE_DESKTOP;
        }

        @Override
        public String getText() {
            return "Test Run Mode: desktop or web based.\n N.B.: For web-based modes RUNTIME installed to system "
                    + "would be used instead SDK (javafx.home). Make sure they are of the same version.\n"
                    + "Run 'javaws -uninstall' before running web-mode tests.";
        }

        @Override
        public String getSummary() {
            return "Test Run Mode";
        }

        @Override
        public void clear() {
            setValue(RUN_MODE_DESKTOP);
        }

        @Override
        public void export(Map map) {
            map.put(RUN_MODE_PARAM, value);
        }

        @Override
        public void load(Map data) {
            setValue((String) data.get(RUN_MODE_PARAM));
        }
    };

    private FileQuestion qBrowser = new FileQuestion(this, BROWSER_PARAM_NAME) {
        {
            this.setFilter(new ExecutablesFileFilter());
        }

        @Override
        public String getText() {
            return "Choose browser executable. Please, close all instances of that browser prior to running tests.";
        }

        @Override
        public String getSummary() {
            return "Browser executable";
        }

        @Override
        public void export(Map data) {
            data.put(BROWSER_PARAM_NAME, value.getPath());
        }

        //TODO: browsers autodetection
        private static final String FF = "firefox";

        @Override
        protected void save(Map data) {
            // this is not the best place to perform this check, but javatest doesn't provide better option
            String path = value.getPath();
            if (path != null && path.contains("iexplore")) {
                BasicFXInterview.ieFix.run();
            }
            super.save(data);
        }

        private final String mostCommonPaths[] = new String[]{
            "\\Applications\\Firefox.app\\Contents\\MacOS\\firefox",
            "C:/Program Files (x86)/Mozilla Firefox/firefox.exe"
        };

        @Override
        public void clear() {
            String def = FF;
            for (String path : mostCommonPaths) {
                File f = new File(path);
                if (f.isFile()) {
                    setValue(f.getAbsolutePath());
                    return;
                }
            }
            setValue(def);
        }

        @Override
        public Question getNext() {
            if (!(value.isFile() || FF.equals(value.getName()))) {
                return null;
            }
            return getEnvSuccessorQuestion();
        }

        @Override
        public void load(Map data) {

            String tmp = null;
            if (data.containsKey(BROWSER_PARAM_NAME)
                && (null!=(tmp= (String)data.get(BROWSER_PARAM_NAME)))
                &&  (new File(tmp)).isFile()  ) {
                         setValue(new File(tmp));
                }
            else {
                clear();
            }
        }

    };

    private FileQuestion qJavawsPath = new FileQuestion(this, JAVAWS_PARAM_NAME) {
        {
            this.setFilter(new ExecutablesFileFilter());
        }

        @Override
        public String getText() {
            return "Choose javaws executable location.";
        }

        @Override
        public String getSummary() {
            return "Java Web Start Executable";
        }
        private final String mostCommonPaths[] = new String[]{
            "C:/Program Files (x86)/Oracle/JavaFX Runtime 2.0/bin/javaws.exe",
            "C:/Program Files/Oracle/JavaFX Runtime 2.0/bin/javaws.exe",
            "C:/Program Files/Java/jdk1.8.0/jre/bin/javaws.exe",
            "C:/Program Files (x86)/Java/jdk1.8.0/jre/bin/javaws.exe"
        };
        private static final String JAVAWS = "javaws";

        @Override
        public void clear() { // you should not call setValue() twice here,
                              //  and should not ask why
            String def = JAVAWS;
            if (isWindows()) {
                for (String path : mostCommonPaths) {
                    // debug System.out.println("try:" + path);
                    File f = new File(path);
                    if (f.isFile()) {
                        // debug System.out.println("Yes!" + f.getAbsolutePath());
                        setValue(f.getAbsolutePath());
                        // debug System.out.println("Yes=" + getValue());
                        return;
                    }
                }
            }
            setValue(def);
        }

        @Override
        public Question getNext() {
            return qPipelineGroup;
        }

        @Override
        public void export(Map data) {
            data.put(JAVAWS_PARAM_NAME, value.getPath());
        }

        @Override
        public void load(Map data) {

            String tmp = null;
            if (data.containsKey(JAVAWS_PARAM_NAME)
                && (null!=(tmp= (String)data.get(JAVAWS_PARAM_NAME)))
                &&  (new File(tmp)).isFile()  ) {
                         setValue(new File(tmp));
                }
            else {
                clear();
            }
        }
    };

    private FileQuestion qJavaPath = new FileQuestion(this, JAVA_PARAM_NAME) {
        {
            this.setFilter(new ExecutablesFileFilter());
        }

        @Override
        public boolean isValueValid() {
            if(YesNoQuestion.YES.equals(qRemoteRun.getValue())) {
                return true;
            }
            return super.isValueValid();
        }

        @Override
        public String getText() {
            return "Choose java executable from JDK installation.";
        }

        @Override
        public String getSummary() {
            return "Java Executable";
        }
        private final String JAVA_DEF = System.getProperty("java.home") + File.separator + "bin" + File.separator + (isWindows() ? "java.exe":"java");

        @Override
        public void clear() {
                setValue(JAVA_DEF);
            }

        @Override
        public Question getNext() {
            return qJavaFX;
        }

        @Override
        public void export(Map data) {
            data.put(JAVA_PARAM_NAME, value.getPath());
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(JAVA_PARAM_NAME)) {
                String exec = (String) data.get(JAVA_PARAM_NAME);
                if (exec != null) {
                    setValue(new File(exec));
                }
            }
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
        public boolean isValueValid() {
            if(YesNoQuestion.YES.equals(qRemoteRun.getValue())) {
                return true;
            }
            return super.isValueValid();
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
            return qDryRun;
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
    private YesNoQuestion qDryRun = new YesNoQuestion(this, DRY_RUN_TAG, YesNoQuestion.NO) {
        @Override
        public Question getNext() {
            if (getValue().toString().equals(YES)) {
                return qDryRunDuration;
            }
            return qExternalOutput;
        }

        @Override
        public String getText() {
            return "During dry run no tests' instructions are shown, only tests' UI. All tests are marked as ERROR. "
                    + "This is artificial mode used for debugging and other purposes. Doesn't affect automated tests.";
        }

        @Override
        public String getSummary() {
            return "Perform a Dry Run";
        }

        @Override
        public void export(Map data) {
            data.put(DRY_RUN_TAG, getValue());
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(DRY_RUN_TAG)) {
                setValue((String) data.get(DRY_RUN_TAG));
            }
        }
    };
    static final int DRY_RUN_DEFAULT_DELAY = 5;
    private IntQuestion qDryRunDuration = new IntQuestion(this, DRY_RUN_DURATION_TAG, 0, 600) {
        {
            this.setDefaultValue(DRY_RUN_DEFAULT_DELAY);
        }

        @Override
        protected Question getNext() {
            return qExternalOutput;
        }

        @Override
        public String getText() {
            return "Setup duration of delay for closing test UI (in seconds)";
        }

        @Override
        public String getSummary() {
            return "Dry Run delay";
        }

        @Override
        public void export(Map data) {
            data.put(DRY_RUN_DURATION_TAG, Integer.toString(getValue()));
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(DRY_RUN_DURATION_TAG)) {
                try {
                    setValue(Integer.parseInt((String) data.get(DRY_RUN_DURATION_TAG)));
                } catch (Exception e) {
                    // keep default
                }

            }
        }
    };
    private FileQuestion qExternalOutput = new FileQuestion(this, EXTERNALOUTPUT) {
        @Override
        public boolean isValueValid() {
            if(YesNoQuestion.YES.equals(qRemoteRun.getValue())) {
                return true;
            }
            return super.isValueValid();
        }

        @Override
        public Question getNext() {
            return qLookAndFeelGroup;
        }

        @Override
        public String getText() {
            return getSummary();
        }

        @Override
        public String getSummary() {
            return "output dir";
        }

        @Override
        public void export(Map data) {
            extOut = value.getPath();
            data.put(EXTERNALOUTPUT, extOut);
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(EXTERNALOUTPUT)) {
                extOut = (String) data.get(EXTERNALOUTPUT);
                if (extOut != null) {
                    setValue(new File(extOut));
                }
            }
        }
    };

    private StringQuestion qProxy = new StringQuestion(this, "proxy") {

        {
            clear();
        }
        @Override
        public void clear() {
            setValue("");
        }

        @Override
        public String getText() {
            return "Proxy settings in format: host:port\nE.g. proxy.example.com:80";
        }

        @Override
        public String getSummary() {
            return "Proxy settings";
        }

        @Override
        public Question getNext() {
            String val = getValue();
            if (val == null || val.trim().length() == 0 || CtrUtils.getProxyUrl(val) != null) {
                return qXPatch;
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
            String v = (String) data.get(PROXY_PARAM_NAME);
            setValue(v != null ? v : "");
        }
    };

    private LookAndFeelQuestion qLookAndFeelGroup = new LookAndFeelQuestion (this, qProxy);
    private ChoiceQuestion qPipelineGroup = new PipelineGroupQuestion (this, getLastQuestion());

    private StringQuestion qXPatch = new StringQuestion(this, XPATCH_PARAM_NAME) {

        @Override
        public String getText() {
            return "Set -Xpatch directory for the test run. "
                    + "\nE.g. build/shim-modules";
    }

        @Override
        public String getSummary() {
            return "--patch-module";
        }

        @Override
        public void clear() {
            setValue("");
        }

        @Override
        public Question getNext() {
            return qEnableAddExports;
        }

        @Override
        public void export(Map data) {
            data.put(XPATCH_PARAM_NAME, value);
        }

        @Override
        public void load(Map data) {
            String newValue = (String) data.get(XPATCH_PARAM_NAME);
            setValue(newValue != null ? newValue : "");
        }
    };

    private YesNoQuestion qEnableAddExports = new YesNoQuestion(this, ENABLE_ADD_EXPORTS_PARAM_NAME, YesNoQuestion.YES) {
        @Override
        public Question getNext() {
            if (getValue().equals(YES)) {
                return qAddExports;
            }
            return qVmOptions;
        }

        @Override
        public String getText() {
            return "Do you wish to add -XaddExports JVM argument when running tests? "
                    + "\nAnswer Yes when running with JDK 9 Jigsaw."
                    + "\nAnswer No when running with JDK 8 and prior.";
        }

        @Override
        public String getSummary() {
            return "Add -XaddExports?";
        }

        @Override
        public void export(Map data) {
            data.put(ENABLE_ADD_EXPORTS_PARAM_NAME, getValue());
        }

        @Override
        public void load(Map data) {
            if (data.containsKey(ENABLE_ADD_EXPORTS_PARAM_NAME)) {
                setValue((String) data.get(ENABLE_ADD_EXPORTS_PARAM_NAME));
            }
        }
    };

    private StringQuestion qAddExports = new StringQuestion(this, ADD_EXPORTS_PARAM_NAME) {

        @Override
        public String getText() {
            return "Set -XaddExports values for the test run. "
                    + "Individual tests can use @addExports annotation "
                    + "to add more specific entries to -XaddExports JVM argument."
                    + "\nE.g. javafx.graphics/com.sun.prism=ALL-UNNAMED,javafx.base/com.sun.javafx.runtime=ALL-UNNAMED";
        }

        @Override
        public String getSummary() {
            return "--add-exports";
        }

        @Override
        public void clear() {
            setValue("");
        }

        @Override
        public Question getNext() {
            return qVmOptions;
        }

        @Override
        public void export(Map data) {
            data.put(ADD_EXPORTS_PARAM_NAME, value);
        }

        @Override
        public void load(Map data) {
            String newValue = (String) data.get(ADD_EXPORTS_PARAM_NAME);
            setValue(newValue != null ? newValue : "");
        }
    };

    private StringQuestion qVmOptions = new StringQuestion(this, VM_OPTIONS_PARAM_NAME) {

        @Override
        public String getText() {
            return "Set VM Options for test run (desktop mode only).\nE.g. -Dprism.verbose=true";
        }

        @Override
        public String getSummary() {
            return "VM Options";
        }

        @Override
        public void clear() {
            setValue("");
        }

        @Override
        public Question getNext() {
            return qPipelineGroup;//getLastQuestion();
        }

        @Override
        public void export(Map data) {
            data.put(VM_OPTIONS_PARAM_NAME, value);
        }

        @Override
        public void load(Map data) {
            String newValue = (String) data.get(VM_OPTIONS_PARAM_NAME);
            setValue(newValue != null ? newValue : "");
        }
    };

    private static Runnable ieFix = new Runnable() {
        @Override
        public void run() {
            if (!alreadyRun) {
                alreadyRun = true; // we prefer this to be run only once but it's not big enough deal to introduce syncronization
                System.out.println("NB: For internet explorer test run registry would be modified to run IE as one process and allow ActiveX from localhost.");
                try {
                    System.out.println("'" + IE_TABS_LOCK_PATH + "\\" + IE_TABS_LOCK_KEY + "' key is being set to 0.\n");
                    //TODO: in the perfect world we would read current value and restore it after test run
                    Runtime.getRuntime().exec("reg add \"" + IE_TABS_LOCK_PATH + "\" /v " + IE_TABS_LOCK_KEY + " /t REG_DWORD /d 0 /f");

                    System.out.println("'" + IE_ACTIVEX_PATH + "\\" + IE_ACTIVEX_KEY + "' key is being set to 0.\n");
                    Runtime.getRuntime().exec("reg add \"" + IE_ACTIVEX_PATH + "\" /v " + IE_ACTIVEX_KEY + " /t REG_DWORD /d 0 /f");

                    System.out.println("'" + IE_ACTIVEX_LOCAL_PATH + "\\" + IE_ACTIVEX_LOCAL_KEY + "' key is being set to 0.\n");
                    Runtime.getRuntime().exec("reg add \"" + IE_ACTIVEX_LOCAL_PATH + "\" /v " + IE_ACTIVEX_LOCAL_KEY + " /t REG_DWORD /d 0 /f");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        private boolean alreadyRun = false;
        private static final String IE_TABS_LOCK_PATH = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\Main";
        private static final String IE_TABS_LOCK_KEY = "TabProcGrowth";
        private static final String IE_ACTIVEX_PATH = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\Safety\\ActiveXFiltering";
        private static final String IE_ACTIVEX_KEY = "IsEnabled";
        private static final String IE_ACTIVEX_LOCAL_PATH = "HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_LOCALMACHINE_LOCKDOWN";
        private static final String IE_ACTIVEX_LOCAL_KEY = "iexplore.exe";
    };

}
