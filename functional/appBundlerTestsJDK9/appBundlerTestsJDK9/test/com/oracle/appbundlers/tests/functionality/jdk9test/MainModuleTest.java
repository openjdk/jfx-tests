/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.tools.packager.RelativeFileSet;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

/**
 * @author Ramesh BG
 *
 */
public class MainModuleTest extends ModuleTestBase {

    protected BasicParams getBasicParams() {
        return (AppWrapper app) -> {
            Map<String, Object> params = new HashMap<>();
            params.put(BundleParams.PARAM_APP_RESOURCES,
                    new RelativeFileSet(app.getJarDir().toFile(),
                            app.getJarFilesList().stream().map(Path::toFile)
                                    .collect(toSet())));
            params.put(MODULEPATH, currentParameter.getApp().getModulePath());
            params.put(APP_NAME, getResultingAppName());
            params.put(MAIN_MODULE, COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
            return params;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setBasicParams(getBasicParams());
    }
}
