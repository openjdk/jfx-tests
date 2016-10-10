/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.APPCDS_CACHE_FILE_EXISTS_RUN;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.MULTI_OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import org.testng.annotations.AfterMethod;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Utils;

/**
 * Test verifies customizing class list to be caches with AppCDS. The
 * JAVAFX_ANT_DEBUG variable must be set to "true" value before running this
 * test
 *
 * @author Dmitriy.Ermashov@oracle.com
 */

public class AppCDSCustomClassesTest extends TestBase {

    protected AdditionalParams getAdditionalParams(ExtensionType extension) {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(UNLOCK_COMMERCIAL_FEATURES, true);
            additionalParams.put(ENABLE_APP_CDS, true);
            additionalParams.put(IDENTIFIER,
                    currentParameter.getApp().getIdentifier(extension));
            additionalParams.put(APP_CDS_CLASS_ROOTS, "testapp.util.Util");
            return additionalParams;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new TreeMap<>();

            List<String> shouldContain = new ArrayList<>(3);
            shouldContain.add("com.greetings.App1 source:");
            shouldContain.add("com.greetings.App1$1 source:");
            shouldContain.add(
                    "[class,load] testapp.util.Util source: shared objects file");
            verifiedOptions.put(APPCDS_CACHE_FILE_EXISTS_RUN,
                    bundlingManager.getAppCDSCacheFile(
                            currentParameter.getApp(), getResultingAppName()));
            verifiedOptions.put(MULTI_OUTPUT_CONTAINS, shouldContain);
            return verifiedOptions;
        };
    }

    @AfterMethod
    public void removeCacheFile() throws IOException, ExecutionException {
        Utils.runCommand(
                bundlingManager.getBundlerUtils()
                        .getRmCommand(bundlingManager.getAppCDSCacheFile(
                                currentParameter.getApp(),
                                getResultingAppName())),
                true, CONFIG_INSTANCE.getRunTimeout());
    }

    @Override
    public void overrideParameters(ExtensionType extension)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams(extension));
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
