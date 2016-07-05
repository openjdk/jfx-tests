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
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.utils.BundlerUtils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Test for {@code identifier parameter}
 */
public class IdentifierTest extends TestBase {

    public AdditionalParams getAdditionalParams() {
        return () -> {
            final String IDENTIFIER_VALUE = "com.oracle.sqe.foo.bar.baz";
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(IDENTIFIER, IDENTIFIER_VALUE);
            return additionalParams;
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
    }
}
