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
 * Test for setting @{code preferencesId} option: it should be passed to the application as JVM option: {@code -Dapp.preferences.id=PREFERENCES_ID}
 */
public class PreferencesIdTest extends TestBase {
    private final static String preferencesId = "foo/bar";

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(PREFERENCES_ID, preferencesId);
            return additionalParams;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(OUTPUT_CONTAINS, "-Dapp.preferences.id=" + preferencesId);
            return verifiedOptions;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
