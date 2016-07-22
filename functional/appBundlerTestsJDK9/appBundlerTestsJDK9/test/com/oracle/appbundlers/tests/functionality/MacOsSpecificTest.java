/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DMG;
import static com.oracle.appbundlers.utils.BundlerUtils.MAC_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.PKG;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */
public class MacOsSpecificTest extends TestBase {
    private static final String category = "mustBeOverriden";
    private static final String macCategory = "public.app-category.developer-tools";
    private static final String bundleName = "FooBar";

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParam = new HashMap<>();
            additionalParam.put(CATEGORY, category);
            additionalParam.put(MAC_CATEGORY, macCategory);
            additionalParam.put(MAC_CF_BUNDLE_NAME, bundleName);
            return additionalParam;
        };
    }

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>(
                    getAdditionalParams().getAdditionalParams());
            verifiedOptions.remove(CATEGORY);
            return verifiedOptions;
        };
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { MAC_APP, DMG, PKG };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
