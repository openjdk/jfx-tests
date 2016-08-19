/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.WIN_APP;
import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Config.LICENSE_FILE_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.RelativeFileSet;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Tests {@code licenseFile} option
 */
public class LicenseFileTest extends TestBase {
    private String licenseFileContent = null;
    private Path licenseFileSrc;
    private Path licenseFile;

    public void initializeVars() throws IOException {
        licenseFileSrc = CONFIG_INSTANCE.getResourceFilePath(LICENSE_FILE_NAME);
        licenseFile = currentParameter.getApp().getJarDir()
                .resolve(LICENSE_FILE_NAME);
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { DEB,
                 WIN_APP,
                EXE, MSI };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            RelativeFileSet appResources = (RelativeFileSet) additionalParams
                    .get(APP_RESOURCES);
            appResources.getIncludedFiles().add(LICENSE_FILE_NAME);
            additionalParams.put(LICENSE_FILE, LICENSE_FILE_NAME);
            return additionalParams;
        };
    }

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(LICENSE_FILE, licenseFileContent);
            return verifiedOptions;
        };
    }

    @Override
    protected void prepareTestEnvironment() throws Exception {
        super.prepareTestEnvironment();
        initializeVars();
        Files.deleteIfExists(licenseFile);
        Files.copy(licenseFileSrc, licenseFile);
        licenseFileContent = new String(Files.readAllBytes(licenseFile),
                "UTF-8");
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}