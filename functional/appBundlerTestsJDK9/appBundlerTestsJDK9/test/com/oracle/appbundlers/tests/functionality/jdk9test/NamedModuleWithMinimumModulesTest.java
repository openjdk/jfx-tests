/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.CHECK_MODULE_IN_JAVA_EXECUTABLE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.parameters.GenericModuleParameters;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Ramesh BG Example 6 in chris list Example 6: Named Module, Minimum
 *         modules -appClass HelloWorld -BmainJar=hello.world.jar -addmods
 *         hello.world Testcase pending.
 */
public class NamedModuleWithMinimumModulesTest extends ModuleTestBase {

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(APPCLASS, COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME);
            hashMap.put(OUTPUT_CONTAINS, HELLO_WORLD_OUTPUT);
            hashMap.put(CHECK_MODULE_IN_JAVA_EXECUTABLE,
                    COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
            return hashMap;
        };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(ADD_MODS, COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
            hashMap.put(MODULEPATH, ((GenericModuleParameters) this.currentParameter)
                    .getModulePath());
            return hashMap;
        };
    }

    protected AppWrapper getApp() throws IOException {

        Map<String, String> replacementsInSourceCode = new HashMap<String, String>();
        replacementsInSourceCode.put(DEPENDENT_MODULE, "");
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                SourceFactory.get_com_greetings_module(
                        Collections.emptyMap(), replacementsInSourceCode));
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        if (ExtensionType.NormalJar != intermediate) {
            this.currentParameter.setApp(getApp());
            this.currentParameter.setAdditionalParams(getAdditionalParams());
            this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        }
    }
}