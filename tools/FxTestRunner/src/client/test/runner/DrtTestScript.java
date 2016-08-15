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
import com.sun.javatest.Status;
import com.sun.javatest.TestDescription;
import com.sun.javatest.TestEnvironment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import client.test.runner.interview.PipelineGroupQuestion;
import client.test.runner.interview.PipelineQuestion;
import client.util.CtrUtils.OutputReader;
import java.io.FileOutputStream;

/**
 *
 * @author vshubov
 */
public class DrtTestScript extends htmltestrunner.TestScript {

    private Process process = null, process2 = null;
    private TestEnvironment savedEnv;
    private final Object locker = new Object();

    @Override
    protected void before(TestDescription description, String resultDir) throws Throwable {
        synchronized(locker){
            runTd(description, resultDir);
        }
    }

    @Override
    protected void after(TestDescription description, String resultDir) throws Throwable {
        synchronized(locker){
            process.destroy();
            process2.destroy();
            process = null;
            process2 = null;
        }
    }

    @Override
    public Status run(String[] args, TestDescription td, TestEnvironment env) {
        savedEnv = env;
        return super.run(args, td, env);
    }

    private void runTd(TestDescription td, String resultDir) throws IOException, InterruptedException {
        String testAbsPath = td.getParameter("testAbsPath");
        String fxSdkHome = "";
        try {
            fxSdkHome = savedEnv.lookup(DrtInterview.FX_SDK_HOME_PARAM_NAME)[0];
        } catch (Exception e) {
            fxSdkHome = "";
        }
        String libPath = "-Djava.library.path="+fxSdkHome+"/rt";

        List<String> cmdList = new ArrayList<String>();
        //java stuff
        Collections.addAll(cmdList, "java", "-cp", System.getProperty("java.class.path"), libPath);

        //proxy
        String proxy = "";
        try {
            proxy = savedEnv.lookup(BasicFXInterview.PROXY_PARAM_NAME)[0].trim(); //what on earth BasicFXInterview is doing here?
        } catch (Exception ex) {
            proxy = "";
        }
        if (proxy.length() > 0) {
             URL proxyUrl = CtrUtils.getProxyUrl(proxy);
             if (proxyUrl != null) {
                 Collections.addAll(cmdList, "-DproxyHost=" + proxyUrl.getHost(), "-DproxyPort=" + proxyUrl.getPort());
            }
        }

        //prism

        String pipelineOptions;
        try {
            pipelineOptions = savedEnv.lookup(PipelineQuestion.PIPELINE_PARAM_NAME)[0].trim();
        } catch (Exception ex) {
            pipelineOptions = "";
        }
        String jvmArgPrismOrder = "";
        if (!pipelineOptions.trim().equals("")) {
            jvmArgPrismOrder = "-Dprism.order=" + pipelineOptions;
            Collections.addAll (cmdList, jvmArgPrismOrder);
        }
        Collections.addAll(cmdList, "-Dprism.verbose=true");

        //adding params
        Collections.addAll(cmdList, DrtWebNodeRunner.class.getName(), "file:///" + testAbsPath);

        final PrintWriter writer = new PrintWriter(
                new FileWriter(resultDir + File.separator + "process_execution.log"));
        writer.println("starting process...");
        writer.println("Command: ");
        for (String s : cmdList) {
            writer.print(s);
            writer.print(" ");
        }
        writer.flush();
        if(process!=null){
            process.destroy();
            process.waitFor();
            process = null;
        }
        process = Runtime.getRuntime().exec(cmdList.toArray(new String[cmdList.size()]));
        new Thread(new OutputReader(new FileOutputStream(resultDir + File.separator + "process.out"), process.getInputStream())).start();
        new Thread(new OutputReader(new FileOutputStream(resultDir + File.separator + "process.err"), process.getErrorStream())).start();
        String browserStr = "";//a default value
        String browserStrArray[] = null;
        try {
            browserStrArray = savedEnv.lookup(DrtInterview.BROWSER_PARAM_NAME);
        } catch (Exception e) {
            browserStrArray = new String[]{"firefox"};
        }
        for(String elem:browserStrArray){
            browserStr +=" "+elem;
        }
        String[] command2 = {browserStr.trim(), "file:///"+testAbsPath};
        if(process2!=null){
            process2.destroy();
            process2.waitFor();
            process2 = null;
        }
        process2 = Runtime.getRuntime().exec(command2);
    }
}
