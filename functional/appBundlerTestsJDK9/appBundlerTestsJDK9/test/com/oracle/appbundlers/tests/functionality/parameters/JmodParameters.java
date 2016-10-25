/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.parameters;

import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.RelativeFileSet;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

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
    public Map<String, Object> createNewBasicParams() throws Exception {
        basicParamsMap = new HashMap<String, Object>();
        basicParamsMap.putAll(super.createNewBasicParams());
        basicParamsMap.put(BundleParams.PARAM_APP_RESOURCES,
                new RelativeFileSet(this.app.getJmodsDir().toFile(),
                        app.getJmodFileList().stream().map(Path::toFile)
                                .collect(toSet())));
        basicParamsMap.put(MODULEPATH, String.join(File.pathSeparator,
                JMODS_PATH_IN_JDK, app.getJmodsDir().toString()));
        return requireNonNull(getBasicParamsFunctionalInterface(), basicParamsMap);
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

