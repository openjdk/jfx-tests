/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality.manual;

import static com.oracle.appbundlers.utils.BundlerUtils.DMG;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.PKG;
import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Config.LICENSE_FILE_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;

import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.tools.packager.RelativeFileSet;

/**
 * Manual test for verification of license agreement text
 *
 * @author Dmitriy.Ermashov@oracle.com
 */
public class LicenseTextTest extends ManualTestBase {
    private Path licenseFileSrc;
    private Path licenseFile;

    @BeforeMethod
    public void initializeVars() throws IOException {
        licenseFileSrc = CONFIG_INSTANCE.getResourceFilePath(LICENSE_FILE_NAME);
        licenseFile = this.currentParameter.getApp().getJarDir().resolve(LICENSE_FILE_NAME);
    }

    protected Map<String, Object> getBasicParams() {
//        Map<String, Object> basicParams =  super.getBasicParams(app);
        Map<String, Object> basicParams = new HashMap<>();
        RelativeFileSet appResources = (RelativeFileSet) basicParams.get(APP_RESOURCES);
        appResources.getIncludedFiles().add(LICENSE_FILE_NAME);

        return basicParams;
    }


    @Override
    public void prepareTestEnvironment() throws IOException {
        result = null;
        Files.deleteIfExists(licenseFile);
        Files.copy(licenseFileSrc, licenseFile);
        createDialogWithInstructions(
                "Wait until app installation window is opened\n"
                        + "(it may take few minutes)\n" +
                        "and follow these instructions:\n" +
                        "1. Start the installation process\n" +
                        "2. Check, that during the process a screen with Lorem ipsum\n" +
                        "    license agreement appeared.\n" +
                        "3. Complete the installation.\n" +
                        "4. Click \"Pass\" button if (2) was shown as expected\n" +
                        "    and \"Fail\" otherwise.");
    }

    protected Map<String, Object> getAdditionalParams() {
        Map<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(LICENSE_FILE, LICENSE_FILE_NAME);
        return additionalParams;
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[]{
            MSI, EXE,
            PKG, DMG
        };
    }

    @Override
    protected void doManualVerifications() throws Exception {
        new Thread(() -> {
            try {
                bundlingManager.install(this.currentParameter.getApp(), null, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }).start();
        synchronized(LOCK) {
            while (result == null) {
                try {
                    LOCK.wait();
                } catch (Exception ie) {
                    ie.printStackTrace();
                    return;
                }
            }
        }
    }
}
