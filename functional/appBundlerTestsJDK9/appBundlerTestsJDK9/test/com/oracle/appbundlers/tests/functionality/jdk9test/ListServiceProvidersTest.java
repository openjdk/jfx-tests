/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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
 * @TODO complete this testcase check whether all service providers are listed
 *       and override getVerifiedOptions and include output in this method.
 */
public class ListServiceProvidersTest extends ModuleTestBase {

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(MODULEPATH, getApp().getModularJarsDir());
            hashMap.put(APPLICATION_CLASS,
                    COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS);
            return hashMap;
        };
    }

    public AppWrapper getApp() throws IOException {

        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS_TEMPLATE,
                COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS);

        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS,
                SourceFactory.get_com_shape_serviceinterface_module(hashMap,
                        Collections.emptyMap(),
                        COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS),
                SourceFactory.get_com_shape_serviceprovider_circle_module(),
                SourceFactory
                        .get_com_shape_serviceprovider_rectangle_module());
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(AbstractBundlerUtils.MULTI_OUTPUT_CONTAINS,
                    Arrays.asList(CIRCLE_OUTPUT, RECTANGLE_OUTPUT));
            return hashMap;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        if (intermediate != ExtensionType.NormalJar) {
            currentParameter.setAdditionalParams(getAdditionalParams());
            currentParameter.setVerifiedOptions(getVerifiedOptions());
            currentParameter.setApp(getApp());
        }
    }
}