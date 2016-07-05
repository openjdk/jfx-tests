/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Ramesh BG Example 7 in chris list Example 7: Named Module App + all
 *         modules -appClass HelloWorld -BmainJar=hello.world.jar -addmods
 *         ALL_MODULEPATH
 */
public class NamedModuleBundledWithAllModules extends ModuleTestBase {

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(APPCLASS, COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME);
            hashMap.put(OUTPUT_CONTAINS, HELLO_WORLD_OUTPUT);
            /*
             * TODO verificator needs to check ALL_MODULES in output currently
             * ALL_MODULEPATH is not working.
             */
            hashMap.put(ADD_MODS, ALL_MODULEPATH);
            return hashMap;
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(ADD_MODS, ALL_MODULEPATH);
            hashMap.put(MODULEPATH, getApp().getModulePath());
            return hashMap;
        };
    }

    public AppWrapper getApp() throws IOException {
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
            SourceFactory.get_com_greetings_module());
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        if(ExtensionType.NormalJar != intermediate) {
            currentParameter.setAdditionalParams(getAdditionalParams());
            currentParameter.setVerifiedOptions(getVerifiedOptions());
            currentParameter.setApp(getApp());
        }
    }
}