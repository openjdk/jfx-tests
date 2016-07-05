/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality.manual;

import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.utils.BundlerUtils;

/**
 * Manual test for verification installation directory chooser
 *
 * @author Dmitriy.Ermashov@oracle.com
 */
public class InstallDirChooserTest extends ManualTestBase {

    @Override
    public void prepareTestEnvironment() throws IOException {
        result = null;
        createDialogWithInstructions(
                "Wait until app installation window is opened\n"
                        + "(it may take few minutes)\n" +
                        "and follow these instructions:\n" +
                        "1. Continue the installation process until the screen\n" +
                        "    with installation directory chooser appeared.\n" +
                        "2. Choose custom install directory.\n" +
                        "3. Complete the installation.\n" +
                        "4. Check the directory for installed application\n" +
                        "5. Click \"Pass\" button if directory chooser screen appeared\n" +
                        "    and the application was installed in this directory");
    }

    public Map<String, Object> getAdditionalParams() {
        Map<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(INSTALLDIR_CHOOSER, Boolean.TRUE.toString());
        return additionalParams;
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[]{
            MSI, EXE
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
