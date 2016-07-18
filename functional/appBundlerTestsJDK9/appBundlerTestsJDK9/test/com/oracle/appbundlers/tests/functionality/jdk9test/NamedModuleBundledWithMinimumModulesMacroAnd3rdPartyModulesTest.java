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

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;

/**
 * @author Ramesh BG Example 10 in chris list Example 10: Named Module, Minimum
 *         modules + 3rd party modules -appClass HelloWorld
 *         -BmainJar=hello.world.jar -addmods MINIMUM_MODULES -modulepath <path
 *         to 3rd party JARs>
 *
 */
public class NamedModuleBundledWithMinimumModulesMacroAnd3rdPartyModulesTest
        extends ModuleTestBase {

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(OUTPUT_CONTAINS, HELLO_WORLD_OUTPUT);
            hashMap.put(CHECK_MODULE_IN_JAVA_EXECUTABLE,
                    COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
            return hashMap;
        };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(ADD_MODS, MINIMUM_MODULES);
            return hashMap;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        if (ExtensionType.NormalJar != intermediate) {
            this.currentParameter.setAdditionalParams(getAdditionalParams());
            this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        }
    }
}