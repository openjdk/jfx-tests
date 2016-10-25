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

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.JavaApiBundlingManager;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.RelativeFileSet;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Tests {@code licenseFile} option
 *
 */
public class LicenseFileTest extends TestBase {
    private String licenseFileContent;
    private Path licenseFileSrcInSuite;
    private Path licenseFolder;

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { DEB, WIN_APP, EXE, MSI };
    }

    protected AdditionalParams getAdditionalParams(ExtensionType extension)
            throws IOException {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            RelativeFileSet appResources = (RelativeFileSet) this.currentParameter
                    .getBasicParamsMap().get(APP_RESOURCES);
            if (extension == ExtensionType.NormalJar) {
                appResources.getIncludedFiles().add(LICENSE_FILE_NAME);
            } else {
                List<RelativeFileSet> appResourcesList = new ArrayList<RelativeFileSet>();
                appResourcesList.add(appResources);
                List<Path> licenses = new ArrayList<>();
                try (DirectoryStream<Path> licenseFileStream = Files
                        .newDirectoryStream(Paths.get(licenseFolder.toString()),
                                "*")) {
                    licenseFileStream.forEach(licenses::add);
                }

                RelativeFileSet licenseAppResource = new RelativeFileSet(
                        new File(licenseFolder.toString()), licenses.stream()
                                .map(Path::toFile).collect(Collectors.toSet()));
                appResourcesList.add(licenseAppResource);
                additionalParams.put(APP_RESOURCES_LIST, appResourcesList);
                additionalParams.put(DUMMY_RELATIVE_FILESET,
                        licenseAppResource);
            }
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
        Path licenseFileInTestAppFolder = null;
        for (ExtensionType extension : getExtensionArray()) {
            if (!isTestCaseApplicableForExtensionType(extension)) {
                continue;
            }
            this.currentParameter = this.intermediateToParametersMap
                    .get(extension);
            overrideParameters(extension);
            initializeAndPrepareApp();
            licenseFileSrcInSuite = CONFIG_INSTANCE.getResourceFilePath(LICENSE_FILE_NAME);
            licenseFileContent = new String(Files.readAllBytes(licenseFileSrcInSuite),
                    "UTF-8");

            if(extension == ExtensionType.NormalJar) {
                licenseFolder = this.currentParameter.getApp().getJarDir();
            } else {
                licenseFolder = this.currentParameter.getApp().getWorkDir()
                        .resolve("license");
                Utils.createDir(licenseFolder);
            }

            licenseFileInTestAppFolder = licenseFolder.resolve(LICENSE_FILE_NAME);
            Files.copy(licenseFileSrcInSuite, licenseFileInTestAppFolder);
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
            Map<String, Object> allParams, ExtensionType extension) throws IOException {

        if (bundlingManager instanceof JavaApiBundlingManager) {
            if (allParams.containsKey(DUMMY_RELATIVE_FILESET)
                    && (extension != ExtensionType.NormalJar)) {
                RelativeFileSet dummyRelativeFileSet = (RelativeFileSet) allParams
                        .get(DUMMY_RELATIVE_FILESET);
                allParams.put("srcdir",
                        dummyRelativeFileSet.getBaseDirectory().getPath());
                allParams.put("srcfiles",
                        dummyRelativeFileSet.getIncludedFiles());
                allParams.remove(DUMMY_RELATIVE_FILESET);
            }
        }
        bundlingManager.execute(allParams, this.currentParameter.getApp(), true);
    }
}
