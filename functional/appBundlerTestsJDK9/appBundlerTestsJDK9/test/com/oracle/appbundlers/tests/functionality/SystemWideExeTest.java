/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.EXE;

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
 * Tests an option of installing the app for all users in the system.
 */
public class SystemWideExeTest extends TestBase {

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { EXE };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(EXE_SYSTEM_WIDE, true);
            return additionalParams;
        };
    }

    public void overrideParameters(ExtensionType intermediate) throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}