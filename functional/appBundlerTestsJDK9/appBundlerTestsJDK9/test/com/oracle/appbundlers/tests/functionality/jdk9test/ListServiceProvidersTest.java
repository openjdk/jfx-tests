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
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;

/**
 * Aim: module com.shape.serviceinterface is the service interface
 *      module com.shape.serviceprovider.circle and module com.shape.serviceprovider.rectangle are two service provider modules.
 *      Aim of this test case is to display all modules which implements com.shape.serviceinterface module.
 * @author Ramesh BG
 */
public class ListServiceProvidersTest extends ModuleTestBase {

    public AppWrapper getApp() throws IOException {

        Map<String, String> classToTemplateMap = new HashMap<String, String>();
        classToTemplateMap.put(COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS,
                COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS_TEMPLATE);

        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS,
                SourceFactory.get_com_shape_serviceinterface_module(classToTemplateMap,
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
        this.currentParameter.setApp(getApp());
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    private AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<String, Object>();
            additionalParams.put(ADD_MODS, this.currentParameter.getApp()
                    .getAllModuleNamesSeperatedByCommaExceptMainmodule());
            return additionalParams;
        };
    }
}


