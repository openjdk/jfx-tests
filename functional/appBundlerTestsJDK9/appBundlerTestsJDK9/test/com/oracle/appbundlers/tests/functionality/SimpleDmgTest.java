/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DMG;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.utils.BundlerUtils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

/**
 * Test for "mac.dmg.simple" option, which purpose is to create the simplest possible app
 */
public class SimpleDmgTest extends TestBase {

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_1);
            return verifiedOptions;
        };
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[]{
                DMG
        };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(SIMPLE_DMG, true);
            return additionalParams;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}
