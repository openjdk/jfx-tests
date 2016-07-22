/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.MULTI_OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Ramesh BG Example 2 in chris list Example 2: Unnamed Module + Entire
 *         JRE + 3rd party JARs
 *
 *         -srcfiles hello.world.jar,3rd.party.jar -appClass HelloWorld
 *         -BmainJar=hello.world.jar
 *
 *         Test case working completed
 */
public class UnnamedModuleDependsOnOtherJarsBundledWithEntireJre
        extends TestBase {

    protected AppWrapper getApp() throws IOException {
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                SourceFactory.get_test_app_util_unnamed_module(),
                SourceFactory.get_com_greetings_app_unnamed_module(
                        new HashMap<String, String>() {
                            /**
                             * serial version UID
                             */
                            private static final long serialVersionUID = 5243894457965235103L;

                            {
                                put(PRINTLN_STATEMENT,
                                        CUSTOM_UTIL_APPEND_CLASS_NAME_PRINT_METHOD);
                            }
                        })
                );
    }

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            List<String> arrayList = new ArrayList<String>();
            /*
             * following parameter is added to check dependency of
             * com.greeetings.App1 on test.app.Util i.e. com.greetings.jar on
             * Util.jar
             */
            arrayList
                    .add(CUSTOM_UTIL_UNNAMED_MODULE_FULLY_QUALIFIED_CLASS_NAME);
            arrayList.add(HELLO_WORLD_OUTPUT);
            hashMap.put(MULTI_OUTPUT_CONTAINS, arrayList);
            return hashMap;
        };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(APPLICATION_CLASS,
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME);
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
        return ExtensionType.NormalJar == extensionType;
    }
}
