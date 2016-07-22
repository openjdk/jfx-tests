/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.MSI;

import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Tests an option of installing the app for all users in the system.
 */
public class SystemWideMsiTest extends TestBase {
    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { MSI };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(MSI_SYSTEM_WIDE, true);
            return additionalParams;
        };
    }

    public void overrideParameters(ExtensionType intermediate) {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}
