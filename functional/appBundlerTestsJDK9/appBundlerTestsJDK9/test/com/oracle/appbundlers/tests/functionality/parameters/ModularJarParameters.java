/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.parameters;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;

/**
 * @author Ramesh BG
 *
 */
public class ModularJarParameters extends GenericModuleParameters {

    public ModularJarParameters(BasicParams basicParams,
            AdditionalParams additionalParams,
            VerifiedOptions verifiedOptions) {
        super(basicParams, additionalParams, verifiedOptions);
    }

    public ModularJarParameters() {
    }

    public Map<String, Object> getBasicParams() throws Exception {
        Map<String, Object> basicParams = new HashMap<String, Object>();
        basicParams.putAll(super.getBasicParams());
        basicParams.put(MODULEPATH, String.join(File.pathSeparator,
                JMODS_PATH_IN_JDK, app.getModularJarsDir().toString()));
        return requireNonNull(getBasicParamsFunctionalInterface(), basicParams);
    }

    @Override
    public String getModulePath() {
        return app.getModularJarsDir().toString();
    }

    @Override
    public ExtensionType getExtension() {
        return ExtensionType.ModularJar;
    }
}
