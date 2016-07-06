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

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.parameters.GenericModuleParameters;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Ramesh BG Example 3 in chris list Example 3: Unnamed Module + Entire
 *         JRE + 3rd party modules -srcfiles hello.world.jar -appClass
 *         HelloWorld -BmainJar=hello.world.jar -addmods 3rd.party -modulepath
 *         <path to 3rd party JARs>
 */
public class UnnamedModuleDependsOn3rdPartyModulesBundledWithEntireJreTest
        extends TestBase {

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
            hashMap.put(ADD_MODS, getApp().addAllModules());
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
}