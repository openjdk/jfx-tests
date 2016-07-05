/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.DMG;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MAC_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.PKG;
import static com.oracle.appbundlers.utils.BundlerUtils.RPM;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.utils.BundlerUtils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Tests {@code licenseType} and {@code copyright} bundler parameters.
 */
public class LicenseTest extends TestBase {
    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[]{
                DEB, RPM,
                MAC_APP, DMG, PKG,
                EXE
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(COPYRIGHT, COPYRIGHT_VALUE);
            additionalParams.put(LICENSE_TYPE, LICENSE_TYPE_VALUE);
            return additionalParams;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}
