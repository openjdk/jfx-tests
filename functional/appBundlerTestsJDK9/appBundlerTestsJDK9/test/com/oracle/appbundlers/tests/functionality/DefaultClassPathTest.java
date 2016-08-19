/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.tools.packager.RelativeFileSet;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

/**
 * This test's purpose is to check whether:
 * <ul>
 * <li>the main jar is extracted correctly without manually specifying it</li>
 * <li>the default classpath is extracted correctly from the main jar without
 * manually specifying it</li>
 * </ul>
 * So, the only passed parameter is essential {@code appResources}
 */
public class DefaultClassPathTest extends TestBase {

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_1);
            return verifiedOptions;
        };
    }

    @Override
    protected void prepareApp(AppWrapper app, ExtensionType extension)
            throws IOException, ExecutionException {
        app.preinstallApp(extension);
        app.writeSourcesToAppDirectory();
        app.compileApp();
        app.jarApp(Collections.emptyList(), true);
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        this.currentParameter.setBasicParams(getBasicParams());
    }

    protected BasicParams getBasicParams() {
        return (AppWrapper app) -> {
            Map<String, Object> basicParams = new HashMap<>();
            basicParams
                    .put(APP_RESOURCES,
                            new RelativeFileSet(app.getJarDir().toFile(),
                                    app.getJarFilesList().stream()
                                            .map(Path::toFile)
                                            .collect(toSet())));
            basicParams.put(APPLICATION_CLASS, this.currentParameter.getApp().getMainClass());
            return basicParams;
        };
    }

    @Override
    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType extension) {
        return ExtensionType.NormalJar == extension;
    }
}
