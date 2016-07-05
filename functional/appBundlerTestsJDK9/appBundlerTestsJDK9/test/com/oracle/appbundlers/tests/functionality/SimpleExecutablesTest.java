/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * This test can be used as an example on how to create tests it should
 * demonstrate all the installers working on this platform
 * <p>
 * It should work an all the platforms, all the bundlers, all the bundling
 * managers (JAVA_API, ANT, CLI)
 */
public class SimpleExecutablesTest extends TestBase {

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifications = new HashMap<>();
            verifications.put(OUTPUT_CONTAINS, PASS_1);
            return verifications;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
          Map<String, Object> additionalParams = new HashMap<>();
          additionalParams.put(STRIP_NATIVE_COMMANDS, false);
          return additionalParams;
        };
    }
}
