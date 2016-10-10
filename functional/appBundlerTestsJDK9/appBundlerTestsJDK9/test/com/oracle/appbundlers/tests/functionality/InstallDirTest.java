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
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
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
    private Path licenseFileSrc;
    private String licenseFileContent;

    public void initializeVars() throws IOException {
        licenseFileSrc = CONFIG_INSTANCE.getResourceFilePath(LICENSE_FILE_NAME);
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { EXE, MSI };
    }

    public AdditionalParams getAdditionalParams(ExtensionType extension) {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            RelativeFileSet existingRelativeFileSet = (RelativeFileSet) this.currentParameter
                    .getBasicParams().get(APP_RESOURCES);
            if (existingRelativeFileSet == null) {
                RelativeFileSet newRelativeFileSet = this.currentParameter
                        .getApp().getRelativeFileSetBasedOnExtension(extension);
                newRelativeFileSet.getIncludedFiles().add(LICENSE_FILE_NAME);
                additionalParams.put(APP_RESOURCES, newRelativeFileSet);
            } else {
                existingRelativeFileSet.getIncludedFiles()
                        .add(LICENSE_FILE_NAME);
            }
            additionalParams.put(INSTALLDIR_CHOOSER, "true");
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
        Path licenseFile = null;
        for (ExtensionType extension : getExtensionArray()) {
            if (!isTestCaseApplicableForExtensionType(extension)) {
                continue;
            }
            this.currentParameter = this.intermediateToParametersMap
                    .get(extension);
            overrideParameters(extension);
            initializeAndPrepareApp();
            licenseFile = this.currentParameter.getApp()
                    .getJavaExtensionPathBasedonExtension(extension)
                    .resolve(LICENSE_FILE_NAME);
            initializeVars();
            Files.copy(licenseFileSrc, licenseFile);
            licenseFileContent = new String(Files.readAllBytes(licenseFile),
                    "UTF-8");
        }
    }

    @Override
    public void overrideParameters(ExtensionType extension) throws IOException {
        this.currentParameter
                .setAdditionalParams(getAdditionalParams(extension));
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
