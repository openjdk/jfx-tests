/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.RPM;

import java.io.IOException;
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
 * Tests that app is installed as the OS service/daemon and is started after
 * installation. Also tests service startup parameters. Tested options:
 * <ul>
 * <li>{@code description}</li>
 * <li>{@code serviceHint}</li>
 * <li>{@code startOnInstall}</li>
 * <li>{@code runAtStartup}</li>
 * </ul>
 */
public class ServiceHintTest extends TestBase {

    public AdditionalParams getAdditionalParams() {
        return () -> {
            final String description = "Caramba service!";
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(DESCRIPTION, description);
            additionalParams.put(SERVICE_HINT, true);
            additionalParams.put(START_ON_INSTALL, true);
            additionalParams.put(RUN_AT_STARTUP, true);
            return additionalParams;
        };
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { EXE, MSI, DEB, RPM };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}
