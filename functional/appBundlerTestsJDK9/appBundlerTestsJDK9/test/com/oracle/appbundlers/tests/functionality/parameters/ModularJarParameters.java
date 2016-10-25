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
 * -modulepath, -mainmodule required by most number of test cases
 * then this class provides those parameters
 * com.oracle.tools.packager.Bundler.execute(Map params,File outputFile)
 * @author Ramesh BG
 */
public class ModularJarParameters extends GenericModuleParameters {

    public ModularJarParameters(BasicParams basicParams,
            AdditionalParams additionalParams,
            VerifiedOptions verifiedOptions) {
        super(basicParams, additionalParams, verifiedOptions);
    }

    public ModularJarParameters() {
    }

    public Map<String, Object> createNewBasicParams() throws Exception {
        basicParamsMap = new HashMap<String, Object>();
        basicParamsMap.putAll(super.createNewBasicParams());
        basicParamsMap.put(BundleParams.PARAM_APP_RESOURCES,
                new RelativeFileSet(this.app.getModularJarsDir().toFile(),
                        app.getModularJarFileList().stream().map(Path::toFile)
                                .collect(toSet())));
        basicParamsMap.put(MODULEPATH, String.join(File.pathSeparator,
                JMODS_PATH_IN_JDK, app.getModularJarsDir().toString()));
        return requireNonNull(getBasicParamsFunctionalInterface(), basicParamsMap);
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

