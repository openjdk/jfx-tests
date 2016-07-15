/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.CHECK_MODULE_IN_JAVA_EXECUTABLE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.parameters.GenericModuleParameters;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.ConfigException;

import javafx.util.Pair;

/**
 * @author Ramesh BG Example 3 in chris list Example 3: Unnamed Module + Entire
 *         JRE + 3rd party modules -srcfiles hello.world.jar -appClass
 *         HelloWorld -BmainJar=hello.world.jar -addmods 3rd.party -modulepath
 *         <path to 3rd party JARs>
 */
public class UnnamedModuleDependsOn3rdPartyModulesBundledWithEntireJreTest
        extends TestBase {

    private static final Logger LOG = Logger.getLogger(UnnamedModuleDependsOn3rdPartyModulesBundledWithEntireJreTest.class.getName());

    protected AppWrapper getApp() throws IOException {
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                "-XaddExports:custom.util/testapp.util=ALL-UNNAMED",
                SourceFactory.get_com_greetings_app_unnamed_module(
                        new HashMap<String, String>() {
                            private static final long serialVersionUID = 2076100253408663958L;

                            {
                                put(PRINTLN_STATEMENT,
                                        CUSTOM_UTIL_PRINTLN_STATEMENT);
                            }
                        }),
                SourceFactory.get_custom_util_module());
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(OUTPUT_CONTAINS, HELLO_WORLD_OUTPUT);
            hashMap.put(CHECK_MODULE_IN_JAVA_EXECUTABLE,
                    CUSTOM_UTIL_MODULE_NAME);
            return hashMap;
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(MODULEPATH, ((GenericModuleParameters) this.currentParameter)
                    .getModulePath());
            hashMap.put(APPLICATION_CLASS,
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME);
            hashMap.put(ADD_MODS, this.currentParameter.getApp().addAllModules());
            return hashMap;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        if (ExtensionType.NormalJar == intermediate) {
            this.currentParameter.setAdditionalParams(getAdditionalParams());
            this.currentParameter.setVerifiedOptions(getVerifiedOptions());
            this.currentParameter.setApp(getApp());
        }
    }

    @Override
    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType extensionType) {
        return ExtensionType.NormalJar != extensionType;
    }

    @Override
    protected void prepareApp(AppWrapper app, ExtensionType extension)
            throws IOException, ExecutionException {
        app.preinstallApp(extension);
        app.writeSourcesToAppDirectory();
        app.compileApp();
        app.jarApp(extension);
    }

    @Test(dataProvider = "getBundlers")
    public void runTest(BundlingManager bundlingManager) throws Exception {
        /*
         * change the implementation
         * @TODO
         * Need to implement the following in this testcase.
         * normal jar file depending on modular jar
         * normal jar file depending on jmod
         * normal jar file depending on exploded mods
         */
        for (ExtensionType extension : ExtensionType.values()) {
            this.currentParameter = intermediateToParametersMap
                    .get(extension);

            if (!isTestCaseApplicableForExtensionType(extension)) {
                continue;
            }

            Map<String, Object> allParams = getAllParams(extension);
            String testName = this.getClass().getName() + "::"
                    + testMethod.getName() + "$" + bundlingManager.toString();
            this.bundlingManager = bundlingManager;
            LOG.log(Level.INFO, "Starting test \"{0}\".", testName);
            try {
                validate();
                if (isConfigExceptionExpected(bundlingManager.getBundler())) {
                    Assert.fail(
                            "ConfigException is expected, but isn't thrown");
                }
            } catch (ConfigException ex) {
                if (isConfigExceptionExpected(bundlingManager.getBundler())) {
                    return;
                } else {
                    LOG.log(Level.SEVERE, "Configuration error: {0}.",
                            new Object[] { ex });
                    throw ex;
                }
            }

            try {
                bundlingManager.execute(allParams,
                        this.currentParameter.getApp());
                String path = bundlingManager.install(
                        this.currentParameter.getApp(), getResultingAppName(),
                        false);
                LOG.log(Level.INFO, "Installed at: {0}", path);

                Pair<TimeUnit, Integer> tuple = getDelayAfterInstall();
                tuple.getKey().sleep(tuple.getValue());
                AppWrapper app2 = this.currentParameter.getApp();
                this.currentParameter.getVerifiedOptions().forEach(
                        (name, value) -> bundlingManager.verifyOption(name,
                                value, app2, getResultingAppName()));
            } finally {
                uninstallApp(extension);
                LOG.log(Level.INFO, "Finished test: {0}", testName);
            }
        }
    }
}