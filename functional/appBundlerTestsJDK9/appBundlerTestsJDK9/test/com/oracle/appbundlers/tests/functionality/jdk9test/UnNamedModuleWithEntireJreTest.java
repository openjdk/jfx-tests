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
 * @author Ramesh BG Example 1 in chris list TestCase working and no pending in
 *         testcase Example 1: Unnamed Module + Entire JRE (default, backwards
 *         compatible case) -srcfiles hello.world.jar -appClass HelloWorld
 *         -BmainJar=hello.world.jar
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
