/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;

/**
 * @author Ramesh BG
 *
 */
public class LimitModuleTest extends ModuleTestBase {

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            /*
             * Do not include Rectangle module name for Limit Mods
             */
            hashMap.put(LIMIT_MODS,
                    COM_SHAPE_SERVICEPROVIDER_CIRCLE_MODULENAME);
            return hashMap;
        };
    }

    public AppWrapper getApp() throws IOException {
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_SHAPE_TEST_LIMITMODSMAINCLASS,
                SourceFactory.get_com_shape_serviceinterface_module(),
                SourceFactory.get_com_shape_serviceprovider_circle_module(),
                SourceFactory.get_com_shape_serviceprovider_rectangle_module(),
                SourceFactory.get_com_shape_test_module());
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(AbstractBundlerUtils.MULTI_OUTPUT_CONTAINS,
                    Arrays.asList(CIRCLE_OUTPUT));
            return hashMap;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        if (ExtensionType.NormalJar != intermediate) {
            this.currentParameter.setAdditionalParams(getAdditionalParams());
            this.currentParameter.setVerifiedOptions(getVerifiedOptions());
            this.currentParameter.setApp(getApp());
        }
    }
}