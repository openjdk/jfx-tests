/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.tests.functionality.IconTest.iconExtension;
import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.ASSOCIATED_EXTENSIONS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.WIN_SYSTEM_WIDE_FILE_ASSOCIATIONS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.WIN_USER_FILE_ASSOCIATIONS;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.Utils;

import javafx.util.Pair;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

public class FileAssociationTest extends TestBase {
    public static final String RM_FULLNAME = "testapp.RmApp";
    public static final String RM_NAME = "RmApp";
    public static final String ext1 = "foo", ext2 = "bar", ext3 = "baz",
            ext4 = "qux";

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { BundlerUtils.MAC_APP, BundlerUtils.PKG,
                BundlerUtils.DMG,

                BundlerUtils.EXE, BundlerUtils.MSI, BundlerUtils.DEB,
                BundlerUtils.RPM };
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.JAVA_API,
                BundlingManagers.ANT };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            Map<String, Object> association1 = new HashMap<>();
            association1.put(FA_EXTENSIONS, Arrays.asList(ext1, ext2));
            association1.put(FA_CONTENT_TYPE,
                    Arrays.asList("application/example"));
            association1.put(FA_DESCRIPTION, "The sample description");
            Map<String, Object> association2 = new HashMap<>();
            association2.put(FA_EXTENSIONS, Arrays.asList(ext3, ext4));
            association2.put(FA_CONTENT_TYPE, Arrays.asList("hello/world"));
            association2.put(FA_ICON, CONFIG_INSTANCE
                    .getResourceFilePath("icon2." + iconExtension()).toFile());
            additionalParams.put(FILE_ASSOCIATIONS,
                    Arrays.asList(association1, association2));
            additionalParams.put(SYSTEM_WIDE, true);
            return additionalParams;
        };
    }

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>(
                    getAdditionalParams().getAdditionalParams());
            verifiedOptions.put(ASSOCIATED_EXTENSIONS,
                    Arrays.asList(ext1, ext2, ext3, ext4));
            verifiedOptions.put(WIN_SYSTEM_WIDE_FILE_ASSOCIATIONS,
                    getAdditionalParams().getAdditionalParams()
                            .get(FILE_ASSOCIATIONS));
            verifiedOptions.remove(WIN_USER_FILE_ASSOCIATIONS);
            return verifiedOptions;
        };
    }

    public AppWrapper getApp() throws IOException {
        String templateName = Utils.isMacOS() ? "MacRmApp.java.template"
                : "RmApp.java.template";
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY), RM_FULLNAME,
                new Source(RM_FULLNAME, templateName, "rmApp",
                        new HashMap<String, String>() {
                            /**
                             * serial version UID
                             */
                            private static final long serialVersionUID = -559615341303319079L;

                            {
                                put(APP_NAME_REPLACEMENT_STATEMENT, RM_NAME);
                            }
                        }));
    }

    @Override
    public String getResultingAppName() {
        return RM_NAME;
    }

    @Override
    protected Pair<TimeUnit, Integer> getDelayAfterInstall() {
        return new Pair<>(TimeUnit.MILLISECONDS,
                CONFIG_INSTANCE.getAfterInstallationPause());
    }

    @Override
    protected void prepareApp(AppWrapper app, ExtensionType extension) throws IOException, ExecutionException {
        final String makeJavacReadClassesFromRtJar = "-XDignore.symbol.file=true";
        app.preinstallApp(extension);
        app.writeSourcesToAppDirectory();
        app.compileApp(new String[] { makeJavacReadClassesFromRtJar });
        app.jarApp(extension);
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        this.currentParameter.setApp(getApp());
    }
}
