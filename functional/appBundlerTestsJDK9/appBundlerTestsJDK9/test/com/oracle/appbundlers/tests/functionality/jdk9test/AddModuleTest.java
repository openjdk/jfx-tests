/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.CHECK_MODULE_IN_JAVA_EXECUTABLE;

import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;

/**
 * @author Ramesh BG Aim: add all modules available in application mods dir and
 *         check the same in java -listmods output. [STDOUT]: Bundler EXE
 *         Installer failed because of jdk.tools.jlink.plugin.PluginException:
 *         java.lang. module.ResolutionException: Module
 *         custom.util;com.greetings not found
 */
public class AddModuleTest extends ModuleTestBase {

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(ADD_MODS, AddModuleTest.this.getParameters().getApp()
                    .addAllModules());
            hashMap.put(MODULEPATH, AddModuleTest.this.getParameters().getApp()
                    .getModulePath());
            return hashMap;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(CHECK_MODULE_IN_JAVA_EXECUTABLE, AddModuleTest.this
                    .getParameters().getApp().getAllModuleNamesAsList());
            return hashMap;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate) {
        if (intermediate != ExtensionType.NormalJar) {
            this.currentParameter.setAdditionalParams(getAdditionalParams());
            this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        }
    }
}