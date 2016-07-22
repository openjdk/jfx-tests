/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
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
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.RelativeFileSet;
import com.oracle.tools.packager.StandardBundlerParam;

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

    public BasicParams getBasicParams() {
        return (AppWrapper app) -> {

            Map<String, Object> basicParams = new HashMap<String, Object>();
            basicParams
                    .put(APP_RESOURCES,
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

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
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
        this.currentParameter.setBasicParams(getBasicParams());
    }
}
