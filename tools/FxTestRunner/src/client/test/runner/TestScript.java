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

import client.test.ExcludeRunModeMethod;
import client.test.ExcludeRunModeType;
import client.test.OnlyRunModeMethod;
import client.test.OnlyRunModeType;
import client.test.RunModes;
import client.test.RunModes.RunModeException;
import client.test.runner.CommonTestRunnerWorker.Command;
import client.test.runner.CommonTestRunnerWorker.Command.CommandType;
import client.test.runner.interview.LookAndFeelQuestion;
import client.test.runner.interview.PipelineQuestion;
import client.util.CtrUtils;
import static client.util.CtrUtils.*;
import client.util.CtrUtils.OutputReader;
import client.util.JettyServer;
import com.sun.interview.YesNoQuestion;
import com.sun.javatest.Status;
import com.sun.javatest.TestDescription;
import com.sun.javatest.TestEnvironment;
import com.sun.javatest.TestEnvironment.Fault;
import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runner.RunWith;
import test.javaclient.shared.CanvasRunner;
import test.javaclient.shared.Utils;

/**
 *
 * @author shura, mrkam, Sergey Grinev, Victor Shubov
 */
public class TestScript extends htmltestrunner.TestScript {

    private static final int FORCED_TERMINATION_TIMEOUT = 5000;
    private static final boolean verbose = true; //TODO: use real logger
    private volatile Process process = null;
    private volatile ServerSocket cmdServer = null;
    private volatile Socket cmdSocket;
    private volatile ObjectOutputStream commandStream;
    private volatile TestRunner.Status status = null;
    private volatile TestEnvironment savedEnv;
    private Thread resultThread;

    @Override
    protected synchronized void interrupt(Status status) {
        System.out.println("interrupting with status " + status);
        try {
            if (process.isAlive()) {
                process.destroyForcibly().waitFor(FORCED_TERMINATION_TIMEOUT, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException ex) {
            System.err.println("Exception while interrupting test process: " + ex.getMessage());
        }
        if (resultThread.isAlive()) {
            resultThread.interrupt();
        }
        super.interrupt(status);
    }

    @Override
    protected synchronized Status getStatus() {
        return super.getStatus();
    }

    @Override
    protected void before(TestDescription description, String resultDir) throws Throwable {
        String testClassName = td.getParameter(RunUITestFinder.UNIT_TEST_CLASS_NAME);
        String testName = td.getParameter(RunUITestFinder.TEST_NAME);
        final String runMode = savedEnv.lookup(BasicFXInterview.RUN_MODE_PARAM)[0];

        System.out.println("\nTEST: " + testClassName + "/" + testName);
        System.out.println("Mode:" + runMode);
        System.out.println("Result Dir:" + resultDir);
        if (needToRun(testClassName, testName, runMode)) {

            try {
                int masterPort = startServer();

                if (runMode.equals(BasicFXInterview.RUN_MODE_DESKTOP)
                        || runMode.equals(BasicFXInterview.RUN_MODE_DESKTOP_SWING_INTEROPERABILITY)
                        || runMode.equals(BasicFXInterview.RUN_MODE_DESKTOP_SWT_INTEROPERABILITY)) {
                    runTd(description, resultDir, masterPort);
                } else {
                    runPlugin(td, resultDir, BasicFXInterview.RUN_MODE_JNLP.equals(runMode));
                }
            } catch (Throwable e) {
                e.printStackTrace(System.err);
                if (cmdServer != null) {
                    try {
                        cmdServer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace(System.err);
                    }
                }
                if (cmdSocket != null) {
                    try {
                        cmdSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace(System.err);
                    }
                }
                if (commandStream != null) {
                    try {
                        commandStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace(System.err);
                    }
                }
                if (process != null) {
                    process.destroy();
                }
                commandStream.close();
                interrupt(Status.error(e.toString()));
            }

            resultThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.err.println("Waiting for exit");
                        ObjectInputStream ois = new ObjectInputStream(cmdSocket.getInputStream());
                        status = (TestRunner.Status) ois.readObject();

                        Status jtStatus;
                        System.err.println("Process returned status " + status);
                        if (status == null) {
                            jtStatus = Status.error("Unexpected status: " + status);
                        } else if (status.isPassed()) {
                            jtStatus = Status.passed(status.getText());
                        } else if (status.isFailed()) {
                            jtStatus = Status.failed(status.getText());
                        } else {
                            jtStatus = Status.error(status.getText());
                        }
                        interrupt(jtStatus);

                    } catch (Throwable ex) {
                        ex.printStackTrace(System.err);
                        interrupt(Status.error(CtrUtils.stackTraceToString(ex)));
                    } finally {

                        //finalize
                        try {
                            commandStream.close();
                            cmdSocket.close();
                            cmdServer.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        if (runMode.equals(BasicFXInterview.RUN_MODE_PLUGIN)) {
                            System.err.println("closing browser");
                            if (process != null) {
                                process.destroy();
                            }
                        }
                        try {
                            if (process != null) {
                                process.waitFor();
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace(System.err);
                        }
//                    if (jemmyProcess != null) {
//                        System.out.println("killed jemmy process");
//                        try {
//                            jemmyProcess.getInputStream().close();
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//
//                        jemmyProcess.destroy();
//                    }
                        System.out.println("DONE");
                    }
                }
            }, "I'm waiting for test's result");
            resultThread.start();
        }
    }

    @Override
    protected void showTestDialog(TestDescription td, TestEnvironment env) {
        String nodescription = td.getParameter(RunUITestFinder.NO_DESCRIPTION);
        boolean dryRun = YesNoQuestion.YES.equals(lookup(BasicFXInterview.DRY_RUN_TAG, YesNoQuestion.NO));
        if (!Boolean.parseBoolean(nodescription)) {
            if (dryRun) {
                System.err.println("DRY RUN MODE");
                int delay = Integer.parseInt(lookup(BasicFXInterview.DRY_RUN_DURATION_TAG, BasicFXInterview.DRY_RUN_DURATION_TAG.toString()));
                try {
                    Thread.sleep(delay * 1000);
                } catch (InterruptedException ex) {
                }
                setStatus(Status.error("Dry Run"));
            } else {
                super.showTestDialog(td, env);
            }
        }
    }

    /**
     * Never put any cleanup here as this method would be called only in case of
     * success
     * @throws java.lang.Throwable
     */
    @Override
    protected void after(TestDescription description, String resultDir) throws Throwable {
    }

    @Override
    public Status run(String[] args, TestDescription td, TestEnvironment env) {
        savedEnv = env;
        try {
            String testClassName = td.getParameter(RunUITestFinder.UNIT_TEST_CLASS_NAME);
            String testName = td.getParameter(RunUITestFinder.TEST_NAME);
            final String runMode = savedEnv.lookup(BasicFXInterview.RUN_MODE_PARAM)[0];
            if (!needToRun(testClassName, testName, runMode)) {
                return Status.passed("Not run in " + runMode + " mode");
            }
        } catch (RunModes.RunModeException ex) {
            return Status.error(ex.toString());
        } catch (NoSuchMethodException ex) {
            return Status.error(ex.toString());
        } catch (ClassNotFoundException ex) {
            return Status.error(ex.toString());
        } catch (Fault ex) {
            return Status.error(ex.toString());
        }
        super.run(args, td, env);
        String hasCheckUI = td.getParameter(RunUITestFinder.HAS_CHECK_UI);
        try {
            if (Boolean.parseBoolean(hasCheckUI)) {
                sendCommand(CommandType.CHECK_UI_AND_EXIT);
            } else {
                try {
                    sendCommand(CommandType.EXIT);
                } catch (IOException ex) {
                    //this is valid for UI to exit before we asked for
                }
            }
            resultThread.join(600000);
            if (status == null) {
                sendCommand(CommandType.ABORT);
                setStatus(Status.error("process didn't return valid status"));
            }
        } catch (Throwable ex) {
            return Status.error(ex.toString());
        }

        System.out.println("We are done with " + td.getName() + ". Status: " + getStatus());
        return getStatus();
    }

    private String getJvmArgPrismOrder() {
        final String pipelineOptions = lookup(PipelineQuestion.PIPELINE_PARAM_NAME, "");
        String jvmArgPrismOrder = "";
        if (!pipelineOptions.trim().equals("")) {
            jvmArgPrismOrder = "-Dprism.order=" + pipelineOptions;
        }
        return jvmArgPrismOrder;
    }

    /**
     *
     * @param td
     * @param resultDir
     * @return
     * @throws IOException
     * @throws Fault
     */
    protected String[] tdCmdArgs(TestDescription td, String resultDir, int port) throws IOException, Fault {
        String testClassName = td.getParameter(RunUITestFinder.UNIT_TEST_CLASS_NAME);

        boolean isJunit = Boolean.parseBoolean(td.getParameter(RunUITestFinder.TYPE_JUNIT));

        //String fxSdkHome = td.getParameter(BasicFXInterview.FX_SDK_HOME_PARAM_NAME);
        String fxSdkHome = savedEnv.lookup(BasicFXInterview.FX_SDK_HOME_PARAM_NAME)[0];

        String[] pathArr = savedEnv.lookup(BasicFXInterview.JAVA_PARAM_NAME);
        StringBuilder pathStr = new StringBuilder();
        for (String p : pathArr) {
            if (pathStr.length() > 0) {
                pathStr.append(" ");
            }
            pathStr.append(p);
        }
        String javaExec = pathStr.toString();


        String proxy = lookup(BasicFXInterview.PROXY_PARAM_NAME, "");
        String jvmProxyHost = "";
        String jvmProxyPort = "";
        if (proxy != null && proxy.trim().length() > 0) {
            URL proxyUrl = getProxyUrl(proxy);
            if (proxyUrl != null) {
                jvmProxyHost = "-DproxyHost=" + proxyUrl.getHost();
                jvmProxyPort = "-DproxyPort=" + proxyUrl.getPort();
            }
        }

        String additionalOptions = getAdditionalOptions();

        String[] jvmVmOptions = savedEnv.lookup(BasicFXInterview.VM_OPTIONS_PARAM_NAME);

        String[] xPatch = savedEnv.lookup(BasicFXInterview.XPATCH_PARAM_NAME);
        String xPatchArg = "";
        if (xPatch != null && xPatch.length > 0) {
            File xPatchFile = new File(xPatch[0]);
            xPatchArg = xPatchFile.getAbsolutePath();
        }

        boolean enableAddExports = YesNoQuestion.YES.equals(lookup(
                BasicFXInterview.ENABLE_ADD_EXPORTS_PARAM_NAME, YesNoQuestion.NO));

        String noAddExportsArg = enableAddExports ? null : "-DnoAddExports=true";

        String addExportsArg[] = enableAddExports
                ? combineAddExports(
                    savedEnv.lookup(BasicFXInterview.ADD_EXPORTS_PARAM_NAME),
                    td.getParameter(RunUITestFinder.ADD_EXPORTS))
                : null;

        String jvmArgPrismOrder = getJvmArgPrismOrder();


//        String externaloutput = lookup(BasicFXInterview.EXTERNALOUTPUT, "");
//        if (externaloutput != null) {
//            jvmArgImageUtils = "-Dimageutils.outputpath=" + externaloutput + File.separator;
//        }
        String jvmArgImageUtils = "-Dimageutils.outputpath=" + resultDir + File.separator;

        String jvmArgNoDesc = "";
        if (Boolean.parseBoolean(td.getParameter(RunUITestFinder.NO_DESCRIPTION))) {
            jvmArgNoDesc = "-Djavatest.mode.nodesc=true";
        }

        String runMode = lookup(BasicFXInterview.RUN_MODE_PARAM, BasicFXInterview.RUN_MODE_DESKTOP);

        String jvmInterop = "";
        if (runMode.equals(BasicFXInterview.RUN_MODE_DESKTOP_SWING_INTEROPERABILITY)) {
            jvmInterop = "-Djavafx.swinginteroperability=true";
        } else if (runMode.equals(BasicFXInterview.RUN_MODE_DESKTOP_SWT_INTEROPERABILITY)) {
            jvmInterop = "-Djavafx.swtinteroperability=true";
        }

        String lookAndFeelOptions = "";
        String lfOptions = lookup(LookAndFeelQuestion.LOOKANDFEEL_PARAM_NAME, "");
        if (!lfOptions.trim().equals("")) {
            lookAndFeelOptions = "-Djavafx.userAgentStylesheetUrl=" + lfOptions;
        }

        //Needed to get images via proxy in Evergreen via VPN see RT-21325 || RT-19661
        final boolean isFXCompatibility = System.getProperty("java.class.path").contains("JavaFXCompatibility");
        String ipV4 = isFXCompatibility ? "-Djava.net.preferIPv4Stack=true" : "";

        String swtTestOpt = "";
        try {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                RunWith wunWithAnnotation = Class.forName(testClassName).getAnnotation(RunWith.class);
                if ((wunWithAnnotation != null) && (wunWithAnnotation.value().equals(CanvasRunner.class))) {
                    swtTestOpt = "-XstartOnFirstThread";
                    System.out.println("Use -XstartOnFirstThread option, as we on MacOS, and SWT test is run.");
                }
            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error : " + ex);
        }

        String jvmArgClientTestRoot = "-DtestRoot=" + RunUITestFinder.testRoot;

        String[] command = new String[]{};
        command = addToArray(command, javaExec.trim());
        command = addToArray(command, jvmVmOptions);
        if (addExportsArg != null) {
            command = addToArray(command, addExportsArg);
        } else if (noAddExportsArg != null) {
            command = addToArray(command, noAddExportsArg);
        }
        if (xPatchArg != null && !xPatchArg.isEmpty()) {
            command = addToArray(command, "--patch-module " + xPatchArg);
        }
        command = addToArray(command, lookAndFeelOptions);
        command = addToArray(command, ipV4);
        command = addToArray(command, jvmArgPrismOrder, jvmArgImageUtils);
        command = addToArray(command, additionalOptions);
        command = addToArray(command, jvmArgNoDesc, jvmProxyHost, jvmProxyPort, jvmInterop, swtTestOpt);
        command = addToArray(command, jvmArgClientTestRoot);
        command = addToArray(command, "-DmasterPort=" + port);
        command = addToArray(command, "-classpath", System.getProperty("java.class.path"));
//        command = addToArray(command, "-Xdebug", "-Xnoagent", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5858");
        command = addToArray(command, isJunit ? JUnit2TestRunner.class.getName() : TestRunner.class.getName(), testClassName);
        return command;
    }

    static String[] combineAddExports(String[] addExportsArray, String addExportsValue) {
        if (addExportsValue == null) {
            return combineAddExports(addExportsArray);
        } else {
            String[] newArray = new String[addExportsArray.length + 1];
            System.arraycopy(addExportsArray, 0, newArray, 1, addExportsArray.length);
            newArray[0] = addExportsValue;
            return combineAddExports(newArray);
        }
    }

    static String[] combineAddExports(String[] addExportsArray) {
        List<String> addExportsList = new ArrayList<>();
        for (String addExports : addExportsArray) {
            if (addExports != null) {
                for (String value : addExports.split("\\s*,\\s*")) {
                    if (!value.isEmpty() && !addExportsList.contains(value.trim())) {
                        addExportsList.add("--add-exports");
                        addExportsList.add(value.trim());
                        addExportsList.add("--add-opens");
                        addExportsList.add(value.trim());
                    }
                }
            }
        }
        return addExportsList.toArray(new String[addExportsList.size()]);
    }


    /**
     *
     * @param td
     * @param resultDir
     * @return
     */
    protected String[] pluginCmdArgs(TestDescription td, String resultDir) {
        return null;
    }

    /**
     *
     * @param command
     * @throws IOException
     */
    protected void doRunTd(String[] command) throws IOException {
        process = Runtime.getRuntime().exec(deleteEmptyElements(command));
    }

    private int startServer() throws IOException {
        System.out.println("Starting server." );
        int iRetryCount = 3;
        boolean bindDone = false;
        while ( (iRetryCount > 0) && (false==bindDone) ) {
            iRetryCount = iRetryCount - 1;

            try {
                cmdServer = new ServerSocket(0);
                System.out.println("Started server at port " + cmdServer.getLocalPort());
                bindDone = true;
            } catch (java.net.BindException be) {
                bindDone = false;
                System.out.println("  === bind exception ===");
                Socket socket = new Socket("127.0.0.1", cmdServer.getLocalPort());
                commandStream = new ObjectOutputStream(socket.getOutputStream());
                sendCommand(CommandType.ABORT);
                try {Thread.sleep(100);} catch(Exception e){}
                }

        }// retry loop end
        cmdServer.setSoTimeout(60000); // we need to be generous for plugin mode, it gets to "download" runnable
        return cmdServer.getLocalPort();
    }

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for connection...");
        cmdSocket = cmdServer.accept();
        commandStream = new ObjectOutputStream(cmdSocket.getOutputStream());
        System.out.println("Connected");
    }

    private void sendCommand(CommandType command) throws UnknownHostException, IOException {
        sendCommand(command, "-");
    }

    private synchronized void sendCommand(CommandType command, String param) throws IOException {
        Command trc = new Command(command, param);
        if (verbose) {
            System.out.println("command " + trc);
        }
        commandStream.writeObject(trc);
    }

    /**
     *
     * @param id
     * @param def
     * @return
     */
    protected String lookup(String id, String def) {
        String[] result = null;
        try {
            result = savedEnv.lookup(id);
        } catch (Fault ex) {
            ex.printStackTrace(System.err);
        }
        return result == null || result.length == 0 ? def : result[0];
    }

    /**
     *
     * @param id
     * @return
     */
    protected String[] lookup(String id) {
        String[] result = null;
        try {
            result = savedEnv.lookup(id);
        } catch (Fault ex) {
            ex.printStackTrace(System.err);
        }
        return result;
    }

    private void runTd(TestDescription td, String resultDir, int port) throws IOException, InterruptedException, Fault {
        String[] command = tdCmdArgs(td, resultDir, port);
        dumpProcessExecution(resultDir, command, null);
        doRunTd(command);
        System.out.println("Logs: " + resultDir + File.separator);

        if (process != null) {
            //TODO: who close that streams?
            new Thread(new OutputReader(new FileOutputStream(resultDir + File.separator + "process.out"), process.getInputStream())).start();
            new Thread(new OutputReader(new FileOutputStream(resultDir + File.separator + "process.err"), process.getErrorStream())).start();
        } else {
            System.out.println("ERROR: Failed to create process"); // TODO: fail test?
        }
        waitForConnection();
        String testName = td.getParameter(RunUITestFinder.TEST_NAME);
        String testClassName = td.getParameter(RunUITestFinder.UNIT_TEST_CLASS_NAME);
        sendCommand(CommandType.SET_TEST_CLASS, testClassName);
        if (testName != null) {
            sendCommand(CommandType.SET_TEST_NAME, testName);
        }
        sendCommand(CommandType.RUN_TEST);
    }

    private static void dumpProcessExecution(String resultDir, String[] cmd, String workdir) throws IOException {
        final PrintWriter writer = new PrintWriter(
                new FileWriter(resultDir + File.separator + "process_execution.log"));
        writer.println("starting process...");
        writer.println("Command: ");
        for (String s : cmd) {
            writer.print(s);
            writer.print("\n");
        }
        writer.println("");
        if (workdir != null) {
            writer.println("Workdir: " + workdir);
        }
        writer.flush();
    }
    private static final String pluginPath = "./dist-plugin/";
    private static final String pluginFile = "JavaClientPluginTest";

    private void runPlugin(TestDescription td, String resultDir, boolean isJnlp)
            throws IOException, Fault {
        String testClassName = td.getParameter(RunUITestFinder.UNIT_TEST_CLASS_NAME);
        String testName = td.getParameter(RunUITestFinder.TEST_NAME);

        //TODO: add non-junit tests support
        boolean isJunit = Boolean.parseBoolean(td.getParameter(RunUITestFinder.TYPE_JUNIT));
        if (!isJunit) {
            throw new UnsupportedOperationException("runui is not supported yet");
        }
        String[] command;
        String param;
        File workdir = new File(System.getProperty("user.dir"));
        final int port = 8485;
        if (isJnlp) {
            String[] pathArr = savedEnv.lookup(BasicFXInterview.JAVAWS_PARAM_NAME);
            StringBuilder pathStr = new StringBuilder();
            for (String p : pathArr) {
                if (pathStr.length() > 0) {
                    pathStr.append(" ");
                }
                pathStr.append(p);
            }
            File path = new File(pathStr.toString());
            param = new File(pluginPath + pluginFile + ".jnlp").getAbsolutePath();
            if (!path.exists()) {
                throw new IllegalArgumentException("javaws path is invalid: " + pathStr);
            }
            workdir = path.getParentFile();

            String jvmArgPrismOrder = getJvmArgPrismOrder();
            if (jvmArgPrismOrder.length() > 0) {
                command = new String[]{pathStr.toString(), "-J" + jvmArgPrismOrder};  // "-Dprism.order=sw"
            } else {
                command = new String[]{pathStr.toString()};
            }

        } else { // plugin
            command = addBrowserTricks(savedEnv.lookup(BasicFXInterview.BROWSER_PARAM_NAME));
            JettyServer.getInstance(port).setBaseDir(new File(pluginPath).getAbsolutePath());
            param = "http://localhost:" + port + "/" + pluginFile + ".html";
        }
        if (verbose) {
            for (String string : command) {
                System.err.println("cmd: " + string);
            }
        }
        String[] merge = addToArray(command, param);
        dumpProcessExecution(resultDir, merge, workdir.getAbsolutePath());
        if (Utils.isMacOS()) {
            ensureJemmyServerRun(workdir.getAbsolutePath());
        }
        process = new ProcessBuilder(merge).directory(workdir).start();
        if (process == null) {
            throw new RuntimeException("Failed to create process for test ");
        }
        waitForConnection();
        sendCommand(CommandType.SET_OUT, resultDir + File.separator + "process.out");
        sendCommand(CommandType.SET_ERR, resultDir + File.separator + "process.err");
        sendCommand(CommandType.SET_TEST_CLASS, testClassName);
        sendCommand(CommandType.SET_BASEDIR, System.getProperty("user.dir"));
        sendCommand(CommandType.SET_ABSOLUTE_DIR, resultDir + File.separator);
        if (testName != null) {
            sendCommand(CommandType.SET_TEST_NAME, testName);
        }
        sendCommand(CommandType.RUN_TEST);
    }

    /**
     *
     * @param arr
     * @param st
     * @return
     */
    public static String[] addToArray(String[] arr, String... st) {
        String[] newArr = new String[arr.length + st.length];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        System.arraycopy(st, 0, newArr, arr.length, st.length);
        return newArr;
    }

    private static enum BROWSER {

        FIREFOX("firefox"), CHROME("chrome"), IE("iexplore"), SAFARI("safari"), UNKNOWN("\n");
        private final String key;

        private BROWSER(String key) {
            this.key = key;
        }

        public static BROWSER detect(String[] command) {
            for (int i = command.length - 1; i >= 0; i--) {
                for (BROWSER browser : values()) {
                    if (command[i] != null && command[i].toLowerCase().contains(browser.key)) {
                        System.out.println("Detected Browser: " + browser.name());
                        return browser;
                    }
                }
            }
            return UNKNOWN;
        }
    };

    private static String[] addBrowserTricks(String[] command) {
        switch (BROWSER.detect(command)) {
            case CHROME:
                return addToArray(command, "--kiosk", "--incognito", "--disable-hang-monitor", "--always-authorize-plugins", "--allow-outdated-plugins");
            case FIREFOX:
                return addToArray(command, "-new-window");
            case IE:
                return addToArray(command, "-k");
            case SAFARI:
                return new String[]{"/usr/bin/open", "-a", "Safari"};
        }
        return command;
    }

    private boolean needToRun(String testClassName, String testName, String runMode) throws NoSuchMethodException, ClassNotFoundException, RunModeException {
        Class testClass = Class.forName(testClassName);
        RunModes mode = RunModes.parseString(runMode);
        if (mode == null) {
            throw new RunModes.RunModeException(runMode);
        }
        ExcludeRunModeType ermt = (ExcludeRunModeType) testClass.getAnnotation(ExcludeRunModeType.class);
        if (ermt != null) {
            if (ermt.value() == mode) {
                return false;
            }
        }
        OnlyRunModeType ormt = (OnlyRunModeType) testClass.getAnnotation(OnlyRunModeType.class);
        if (ormt != null) {
            if (ormt.value() != mode) {
                return false;
            }
        }
        if (testName == null) {
            // fox JavaFXCompatibility suite weird run mode
            return true;
        }
        //not testClass.getMethod(testName) because we can't get static method.
        Method[] methods = testClass.getMethods();
        for (Method testMethod : methods) {
            if (testName.equals(testMethod.getName())) {
                ExcludeRunModeMethod ermm = (ExcludeRunModeMethod) testMethod.getAnnotation(ExcludeRunModeMethod.class);
                if (ermm != null) {
                    if (ermm.value() == mode) {
                        return false;
                    }
                }
                OnlyRunModeMethod ormm = (OnlyRunModeMethod) testMethod.getAnnotation(OnlyRunModeMethod.class);
                if (ormm != null) {
                    if (ormm.value() != mode) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    public Thread getProcessKiller() {
        return new ProcessKiller(5000L, process, cmdServer, cmdSocket);
    }

    private class ProcessKiller extends Thread {

        long sleepTime;
        Process targetProc;
        ServerSocket targetSerSocket;
        Socket targetSocket;

        public ProcessKiller(long time, Process process, ServerSocket targetSerSock, Socket targetSock) {
            this.sleepTime = time;
            this.targetProc = process;
            this.targetSerSocket = targetSerSock;
            this.targetSocket = targetSock;
        }

        @Override
        public void run() {
            try {
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestScript.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (targetProc.isAlive()) {
                System.out.println("Process has been interrupted forcibly!");
                targetProc.destroyForcibly();
                if (targetSerSocket != null) {
                    if (!targetSerSocket.isClosed()) {
                        try {
                            targetSerSocket.close();
                        } catch (IOException ex) {
                            System.out.println("CMDSERVER: exception while cloing");
                            ex.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("CMDSERVER: NULL");
                }
                if (targetSocket != null) {
                    if (!targetSocket.isClosed()) {
                        try {
                            targetSocket.close();
                        } catch (IOException ex) {
                            System.out.println("CMDSOCKET: exception while cloing");
                            ex.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("CMDSOCKET: NULL");
                }
            }
        }
    }

    /**
     *
     * @return
     */
    protected TestEnvironment getSavedEnv() {
        return savedEnv;
    }

    /**
     *
     * @return
     */
    protected String getAdditionalOptions(){
        return "";
    }

}
