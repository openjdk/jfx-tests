/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DMG;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.PKG;
import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.testng.annotations.AfterClass;

import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.Utils;

/**
 * Test to verify the fix of JDK-8093714
 *
 * @author Dmitriy.Ermashov@oracle.com
 */
public class PostInstallScriptTest extends TestBase {

    File fakeDir;

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[]{
            PKG, DMG,
            MSI,
            EXE
        };
    }

    @Override
    public void customBeforeClassHook() throws IOException {
        // creating fake dir and copy post script in it
        String fakeScriptname;
        if (Utils.isWindows()) {
            fakeScriptname = currentParameter.getApp().getAppName()+"-post-image.wsf";
            fakeDir = new File(System.getProperty("user.dir")+"/package/windows");
        } else {
            fakeScriptname = currentParameter.getApp().getAppName()+"-post-image.sh";
            fakeDir = new File(System.getProperty("user.dir")+"/package/macosx");
        }
        fakeDir.mkdirs();

        Files.copy(
                CONFIG_INSTANCE.getResourceFilePath(fakeScriptname).toAbsolutePath(),
                fakeDir.toPath().resolve(fakeScriptname));
    }

    @AfterClass
    public void removeFakeDir() throws IOException {
        Utils.removeRecursive(fakeDir.toPath(), true);
    }
}
