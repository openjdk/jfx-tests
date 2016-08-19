/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.DMG;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MAC_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.PKG;
import static com.oracle.appbundlers.utils.BundlerUtils.RPM;
import static com.oracle.appbundlers.utils.BundlerUtils.WIN_APP;
import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.isLinux;
import static com.oracle.appbundlers.utils.Utils.isWindows;

import java.io.IOException;
import java.nio.file.Path;
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
 * Tests {@code testIcon} and {@code imagesRoot} bundler parameters.
 */
public class IconTest extends TestBase {
    public static String iconExtension() {
        return isWindows() ? "ico" : isLinux() ? "png" : "icns";
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Path sourceIconPath = CONFIG_INSTANCE.getResourcePath()
                    .resolve("icon." + iconExtension());

            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(ICON, sourceIconPath.toFile());
            return additionalParams;
        };
    }

    @Override
    public BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { MAC_APP, DMG, PKG, WIN_APP, EXE, MSI, DEB,
                RPM };
    }


    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }

    @Override
    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType extension) {
        return ExtensionType.NormalJar == extension;
    }
}
