/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import java.io.IOException;
import java.nio.file.Path;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class LinuxAppBundlerUtils extends LinuxAbstractBundlerUtils {

    public LinuxAppBundlerUtils() {
        super(BundleType.IMAGE, BundlerUtils.LINUX_APP);
    }

    @Override
    public String install(AppWrapper app, String applicationTitle) {
        // no need to install anything, just return executable path
        return getInstalledExecutableLocation(app, applicationTitle).toString();
    }

    @Override
    public void uninstall(AppWrapper app, String applicationTitle) {
        // nothing to do here
    }

    @Override
    public Path getInstalledAppRootLocation(AppWrapper app,
            String applicationTitle) {
        return app.getBundlesDir().resolve(applicationTitle);
    }

    @Override
    public void manualInstall(AppWrapper app) throws IOException {
    }
}
