/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * Unnamed Module + JRE -srcfiles hello.world.jar -appClass HelloWorld
 * -BmainJar=hello.world.jar
 * @author Ramesh BG
 */
public class UnNamedModuleWithEntireJreTest extends TestBase {

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(OUTPUT_CONTAINS, HELLO_WORLD_OUTPUT);
            return hashMap;
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(APPLICATION_CLASS,
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME);
            return hashMap;
        };
    }

    @Override
    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType extensionType) {
        return ExtensionType.NormalJar == extensionType;
    }
}


