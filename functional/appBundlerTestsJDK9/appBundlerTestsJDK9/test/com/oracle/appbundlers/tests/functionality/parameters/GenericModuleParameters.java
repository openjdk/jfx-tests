/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.parameters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * In order to provide default(common) parameters to
 * com.oracle.tools.packager.Bundler.execute(Map params,File outputFile) such as
 * -mainmodule required by most number of test cases
 * then this class provides those parameters
 * com.oracle.tools.packager.Bundler.execute(Map params,File outputFile)
 * @author Ramesh BG
 */
public abstract class GenericModuleParameters extends Parameters {

    public GenericModuleParameters(BasicParams basicParams,
            AdditionalParams additionalParams,
            VerifiedOptions verifiedOptions) {
        super(basicParams, additionalParams, verifiedOptions);
    }

    public GenericModuleParameters() {
    }

    public void initializeDefaultApp() throws IOException {
        setApp(new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                SourceFactory.get_custom_util_module(), SourceFactory
                        .get_com_greetings_module_depends_on_custom_util_module()));
    }

    @Override
    public Map<String, Object> createNewBasicParams() throws Exception {
        basicParamsMap = new HashMap<String, Object>();
        basicParamsMap.put(MAIN_MODULE, String.join("/", app.getMainModuleName(), app.getMainClass()));
        return basicParamsMap;
    }

    public abstract String getModulePath();
}

