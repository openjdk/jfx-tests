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
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * In order to provide default(common) parameters to
 * com.oracle.tools.packager.Bundler.execute(Map params,File outputFile) such as
 * -mainmodule, -modulepath required by most number of test cases
 * then this class provides those parameters
 * com.oracle.tools.packager.Bundler.execute(Map params,File outputFile)
 * @author Ramesh BG
 */
public class JmodParameters extends GenericModuleParameters {

    public JmodParameters(BasicParams basicParams,
            AdditionalParams additionalParams,
            VerifiedOptions verifiedOptions) {
        super(basicParams, additionalParams, verifiedOptions);
    }

    public JmodParameters() {
    }

    @Override
    public Map<String, Object> getBasicParams() throws Exception {
        Map<String, Object> basicParams = new HashMap<String, Object>();
        basicParams.putAll(super.getBasicParams());
        basicParams.put(MODULEPATH, String.join(File.pathSeparator,
                JMODS_PATH_IN_JDK, app.getJmodsDir().toString()));
        return requireNonNull(getBasicParamsFunctionalInterface(), basicParams);
    }

    @Override
    public String getModulePath() {
        return app.getJmodsDir().toString();
    }

    @Override
    public ExtensionType getExtension() {
        return ExtensionType.Jmods;
    }
}

