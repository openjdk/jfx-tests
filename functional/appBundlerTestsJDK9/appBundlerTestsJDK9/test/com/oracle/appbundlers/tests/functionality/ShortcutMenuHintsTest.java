/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.utils.BundlerUtils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Test checks that all shortcuts are created on the desktop and in the
 * start menu.
 * Tested options:
 * <ul>
 *     <li>{@code vendor}</li>
 *     <li>{@code shortcutHint}</li>
 *     <li>{@code menuHint}</li>
 * </ul>
 */
public class ShortcutMenuHintsTest extends TestBase {
    private static final String VENDOR_NAME = "SQE_TEST";

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[]{
                EXE
              , MSI
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(VENDOR, VENDOR_NAME);
            additionalParams.put(SHORTCUT_HINT, true);
            additionalParams.put(MENU_HINT, true);
            return additionalParams;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(MENU_HINT, VENDOR_NAME);
            verifiedOptions.put(SHORTCUT_HINT, null);
            return verifiedOptions;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}
