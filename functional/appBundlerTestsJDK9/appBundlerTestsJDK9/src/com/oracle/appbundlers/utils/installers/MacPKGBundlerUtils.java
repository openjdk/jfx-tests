/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.runCommand;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.StandardBundlerParam;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class MacPKGBundlerUtils extends MacAbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(MacPKGBundlerUtils.class.getName());

    public MacPKGBundlerUtils() {
        super(BundleType.INSTALLER, BundlerUtils.PKG);
    }

    @Override
    public String install(AppWrapper app, String applicationTitle)
            throws IOException {
        Path pkg = findByExtension(app.getBundlesDir(), "pkg",
                ROOT_DIRECTORY_DEPTH, applicationTitle);
        try {
            LOG.log(Level.INFO, "Installing {0}.", pkg);
            String[] cmd = new String[] { "sudo", "installer", "-pkg",
                    pkg.toString(), "-target", "/", "-allowUntrusted","-dumplog","-verbose" };
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
            LOG.log(Level.INFO, "Package Application Identifier is {0}", applicationTitle);
            String subDirName = runCommand(
                    Arrays.asList("pkgutil", "--files", applicationTitle),
                    false, CONFIG_INSTANCE.getRunTimeout()).getOutputStream()
                            .get(0);
            Path packageDir = Paths.get("/Applications", subDirName);
            sudoRemove(packageDir);
            runCommand(
                    new String[] { "sudo", "pkgutil", "--forget",
                            applicationTitle },
                    true, CONFIG_INSTANCE.getRunTimeout());
        } catch (ExecutionException e) {
            LOG.log(Level.INFO, "IOException occured in MacPKGBundlerUtils.java::uninstall() {0} ",e);
            throw new IOException(e);
        } catch (IndexOutOfBoundsException e) {
            // if installation is failed, then package cannot be removed:
            // just ignore it
        } catch(Exception e) {
            LOG.log(Level.INFO, "Exception occured in MacPKGBundlerUtils.java::uninstall() {0} ",e);
        }
    }

    private void sudoRemove(Path path) {
        try {
            runCommand(Arrays.asList("sudo", "rm", "-rf", path.toString()),
                    CONFIG_INSTANCE.getRunTimeout());
        } catch (IOException | ExecutionException ex) {
            // just ignore it
            Logger.getLogger(MacPKGBundlerUtils.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Path getInstalledAppRootLocation(AppWrapper app,
            String applicationTitle) {
        return Paths.get("/Applications", applicationTitle + ".app");
    }

    @Override
    public String getAppName(Map<String, Object> params) {
        return StandardBundlerParam.IDENTIFIER.fetchFrom(params);
    }

    @Override
    public void manualInstall(AppWrapper app) throws IOException {
        Path pkg = findByExtension(app.getBundlesDir(), "pkg",
                ROOT_DIRECTORY_DEPTH);
        try {
            LOG.log(Level.INFO, "Running installer for {0}.", pkg);
            String[] cmd = new String[] { "open", pkg.toString() };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }
}
