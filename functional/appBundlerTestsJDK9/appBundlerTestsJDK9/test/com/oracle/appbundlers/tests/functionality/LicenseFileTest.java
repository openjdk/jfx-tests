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
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.RelativeFileSet;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Tests {@code licenseFile} option
 */
public class LicenseFileTest extends TestBase {
    private String licenseFileContent;
    private Path licenseFileSrc;

    public void initializeVars() throws IOException {
        licenseFileSrc = CONFIG_INSTANCE.getResourceFilePath(LICENSE_FILE_NAME);
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { DEB, WIN_APP, EXE, MSI };
    }

    protected AdditionalParams getAdditionalParams(ExtensionType extension)
            throws IOException {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();

            RelativeFileSet existingRelativeFileSet = (RelativeFileSet) this.currentParameter
                    .getBasicParams().get(APP_RESOURCES);
            if (existingRelativeFileSet == null) {
                existingRelativeFileSet = this.currentParameter
                        .getApp().getNewRelativeFileSetBasedOnExtension(extension);
                existingRelativeFileSet.getIncludedFiles().add(LICENSE_FILE_NAME);
                additionalParams.put(APP_RESOURCES, existingRelativeFileSet);
            } else {
                existingRelativeFileSet.getIncludedFiles()
                        .add(LICENSE_FILE_NAME);
            }
            additionalParams.put(BundleParams.PARAM_APP_RESOURCES, existingRelativeFileSet);
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
                    .getJavaExtensionDirPathBasedonExtension(extension)
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

    @Override
    protected void executeJavaPackager(BundlingManager bundlingManager,
            Map<String, Object> allParams) throws IOException {
        bundlingManager.execute(allParams, this.currentParameter.getApp(), true);
    }
}