/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * Aim: To execute other class available in module other than Main-Class of Manifest
 * @author Ramesh BG
 */
public class OtherClassInMainModuleTest extends ModuleTestBase {

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> params = new HashMap<>();
            params.put(MAIN_MODULE,
                    this.currentParameter.getApp().getMainModuleName() + "/"
                            + COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME);
            return params;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setApp(getApp());
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    private VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifications = new HashMap<>();
            verifications.put(OUTPUT_CONTAINS, PASS_2);
            return verifications;
        };
    }

    private AppWrapper getApp() throws IOException {
        Map<String, Map<String, String>> classNameToReplacements = new LinkedHashMap<>();
        classNameToReplacements.put(APP1_NAME, Collections.emptyMap());
        classNameToReplacements.put(APP2_NAME, Collections.emptyMap());

        Map<String, String> classNameToTemplateMap = new HashMap<>();
        classNameToTemplateMap.put(COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                FXAPP_JAVA_TEMPLATE);
        classNameToTemplateMap.put(COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME,
                FXAPP_JAVA_TEMPLATE);
        Source get_com_greetings_module = SourceFactory.get_com_greetings_module(
                classNameToTemplateMap,
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                classNameToReplacements);
        get_com_greetings_module.setMainModule(true);
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                get_com_greetings_module);
    }
}

