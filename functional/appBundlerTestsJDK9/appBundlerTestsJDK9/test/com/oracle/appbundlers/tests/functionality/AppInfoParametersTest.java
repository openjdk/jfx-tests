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
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Tests that provided
 * <ul>
 * <li>{@code name}</li>
 * <li>{@code title}</li>
 * <li>{@code vendor}</li>
 * <li>{@code version}</li>
 * <li>{@code description}</li>
 * <li>{@code email}</li>
 * <li>{@code category}</li>
 * </ul>
 * are correctly applied to the bundled application.
 */
public class AppInfoParametersTest extends TestBase {

    private static final String title = "Sqe Application Title";
    private static final String vendor = "FXSQE";
    private static final String appName = "SQEDEMOAPP";
    private static final String version = "1.0.42";
    private static final String description = "Full application description!";
    private static final String email = "example@oracle.com";
    private static final String category = "SomeCategory";

    protected String title() {
        return title;
    }

    protected String vendor() {
        return vendor;
    }

    protected String appName() {
        return appName;
    }

    protected String version() {
        return version;
    }

    protected String description() {
        return description;
    }

    protected String email() {
        return email;
    }

    protected String category() {
        return category;
    }

    @Override
    public String getResultingAppName() {
        return appName();
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(APP_NAME, appName());

            // Important to enable this option
            // because it's the only way to check title propagation to registry
            // key
            additionalParams.put(SYSTEM_WIDE, true);

            additionalParams.put(TITLE, title());
            additionalParams.put(VENDOR, vendor());
            additionalParams.put(VERSION, version());
            additionalParams.put(DESCRIPTION, description());
            additionalParams.put(EMAIL, email());
            additionalParams.put(CATEGORY, category());
            return additionalParams;
        };

    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>(
                    getAdditionalParams().getAdditionalParams());
            if (Utils.isWindows()) {
                // Description is used only for services on Win
                verifiedOptions.remove(DESCRIPTION);
            }
            verifiedOptions.remove(APP_NAME);
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_1);
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
