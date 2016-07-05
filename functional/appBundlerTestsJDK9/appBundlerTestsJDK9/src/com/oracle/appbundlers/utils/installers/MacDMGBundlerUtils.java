/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.Utils;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class MacDMGBundlerUtils extends MacAbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(MacDMGBundlerUtils.class.getName());

    public MacDMGBundlerUtils() {
        super(BundleType.INSTALLER, BundlerUtils.DMG);
    }

    @Override
    public String install(AppWrapper app, String applicationTitle)
            throws IOException {
        Path dmg = findByExtension(app.getBundlesDir(), "dmg",
                ROOT_DIRECTORY_DEPTH);
        try {
            LOG.log(Level.INFO, "Installing {0}.", dmg);
            String[] cmd = new String[] { "hdiutil", "attach", dmg.toString() };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Installation done.");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
        return getInstalledExecutableLocation(app, applicationTitle).toString();
    }

    @Override
    public void uninstall(AppWrapper app, String applicationTitle)
            throws IOException {
        try {
            LOG.log(Level.INFO, "Uninstalling {0}.", applicationTitle);
            String[] cmd = new String[] { "hdiutil", "detach",
                    "/Volumes/" + applicationTitle };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Uninstalling done.");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Path getInstalledAppRootLocation(AppWrapper app,
            String applicationTitle) {
        return Paths.get("/Volumes", applicationTitle,
                applicationTitle + ".app");
    }

    @Override
    public void manualInstall(AppWrapper app) throws IOException {
        Path dmg = findByExtension(app.getBundlesDir(), "dmg",
                ROOT_DIRECTORY_DEPTH);
        try {
            LOG.log(Level.INFO, "Running installer {0}.", dmg);
            String[] cmd = new String[] { "open", dmg.toString() };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }
}
