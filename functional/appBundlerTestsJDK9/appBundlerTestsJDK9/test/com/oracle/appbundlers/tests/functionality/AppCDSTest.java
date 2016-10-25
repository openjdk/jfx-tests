/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.APPCDS_CACHE_FILE_EXISTS_RUN;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Utils;

/**
 *
 * @author Dmitriy.Ermashov@oracle.com
 */
public class AppCDSTest extends TestBase {

    public AdditionalParams getAdditionalParams(ExtensionType extensionType) {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(UNLOCK_COMMERCIAL_FEATURES, true);
            additionalParams.put(ENABLE_APP_CDS, true);
            additionalParams.put(IDENTIFIER,
                    this.currentParameter.getApp().getIdentifier(extensionType));
            return additionalParams;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(APPCDS_CACHE_FILE_EXISTS_RUN,
                    bundlingManager.getAppCDSCacheFile(
                            this.currentParameter.getApp(),
                            getResultingAppName()));
            return verifiedOptions;
        };
    }

    @AfterMethod
    public void removeCacheFile() throws IOException, ExecutionException {
        Utils.runCommand(
                bundlingManager.getBundlerUtils()
                        .getRmCommand(bundlingManager.getAppCDSCacheFile(
                                this.currentParameter.getApp(),
                                getResultingAppName())),
                true, CONFIG_INSTANCE.getRunTimeout());
    }

    @Override
    public void overrideParameters(ExtensionType extensionType) {
        this.currentParameter.setAdditionalParams(getAdditionalParams(extensionType));
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    /*
     * SKIPPING APPCDS TEST CASES UNTIL https://bugs.openjdk.java.net/browse/JDK-8167657 is fixed.
     */
    @Override
    @Test(dataProvider = "getBundlers", enabled=false)
    public void runTest(BundlingManager bundlingManager) throws Exception {
        super.runTest(bundlingManager);
    }
}
