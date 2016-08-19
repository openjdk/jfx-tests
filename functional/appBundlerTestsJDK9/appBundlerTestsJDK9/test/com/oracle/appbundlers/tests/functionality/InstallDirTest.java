/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Config.LICENSE_FILE_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.RelativeFileSet;

/**
 * @author Dmitriy Ermashov &lt;dmitriy.ermashov@oracle.com&gt;
 */

/**
 * Tests {@code installdirChooser} option.
 */
public class InstallDirTest extends TestBase {
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
        return new BundlerUtils[] { EXE, MSI };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            RelativeFileSet appResources = (RelativeFileSet) additionalParams
                    .get(APP_RESOURCES);
            appResources.getIncludedFiles().add(LICENSE_FILE_NAME);
            additionalParams.put(INSTALLDIR_CHOOSER, "true");
            additionalParams.put(LICENSE_FILE, LICENSE_FILE_NAME);
            return additionalParams;
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
    }
}
