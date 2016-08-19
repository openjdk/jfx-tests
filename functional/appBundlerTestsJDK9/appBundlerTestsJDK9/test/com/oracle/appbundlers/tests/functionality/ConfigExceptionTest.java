package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.EXE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.Bundler;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * The test is designed to ensure that ConfigException is thrown when it's
 * needed
 */
public class ConfigExceptionTest extends TestBase {
    @Override
    protected boolean isConfigExceptionExpected(Bundler bundler) {
        return true;
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(COPYRIGHT,
                    "Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved."
                            + " DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.");
            return additionalParams;
        };
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.JAVA_API };
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { EXE };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }

    @Override
    public void validate() throws Exception {
        this.bundlingManager.validate(getAllParams());
    }
}