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
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.RelativeFileSet;
import com.oracle.tools.packager.StandardBundlerParam;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

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

    public BasicParams getBasicParams() {
        return (AppWrapper app) -> {
            Map<String, Object> basicParams = new HashMap<String, Object>();
            basicParams
                    .put(BundleParams.PARAM_APP_RESOURCES,
                            new RelativeFileSet(app.getJarDir().toFile(),
                                    app.getJarFilesList().stream()
                                            .map(Path::toFile)
                                            .collect(toSet())));
            String mainClass = StandardBundlerParam.MAIN_CLASS
                    .fetchFrom(basicParams);
            basicParams.put(APPLICATION_CLASS, mainClass);
            RelativeFileSet appResources = (RelativeFileSet) basicParams
                    .get(APP_RESOURCES);
            appResources.getIncludedFiles().add(LICENSE_FILE_NAME);
            return basicParams;
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
        this.currentParameter.setBasicParams(getBasicParams());
    }
}