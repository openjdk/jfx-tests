/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.getProgramFilesDirWindows;
import static com.oracle.appbundlers.utils.Utils.runCommand;
import static java.nio.file.Files.exists;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.windows.Registry;
import com.oracle.appbundlers.utils.windows.Registry.Query;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */
public class WinMsiBundlerUtils extends WinAbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(WinMsiBundlerUtils.class.getName());

    {
        verificators.put(SHORTCUT_HINT, getShortcutHintVerificator());

        final VerificationMethod menuItemVerificator;
        menuItemVerificator = (vendor, app, appName) -> {

            Optional<List<String>> optOutput = Registry.getQueryBuilder()
                    .key("HKEY_LOCAL_MACHINE\\SOFTWARE\\" + vendor)
                    .useDataPattern(appName).build().execute();

            boolean menuItemExists = optOutput.orElse(Collections.emptyList())
                    .stream().anyMatch(str -> str.trim().endsWith(appName));

            assertTrue(menuItemExists, "[Menu item for " + appName
                    + " key not found in the registry]");
        };

        verificators.put(MENU_HINT, menuItemVerificator);
        verificators.put(SERVICE_HINT, getServiceHintVerificator());
        verificators.put(START_ON_INSTALL, getStartOnInstallVerificator());
        verificators.put(RUN_AT_STARTUP, getRunAtStartupVerificator());
        verificators.put(VENDOR, menuItemVerificator);
        verificators.put(WIN_SYSTEM_WIDE_FILE_ASSOCIATIONS,
                getFileAssociationVerificator());
        verificators.put(WIN_USER_FILE_ASSOCIATIONS,
                getUserFileAssociationVerificator());
        verificators.put(VERSION, (version, app, appName) -> {

            Query query = Registry.getQueryBuilder()
                    .key("HKEY_CURRENT_USER\\SOFTWARE\\")
                    .useDataPattern(appName).searchSubkeys()
                    .serarchInKeyNamesOnly().build();

            Optional<List<String>> optOutput = query.execute();
            assertTrue(optOutput.isPresent(),
                    "[" + appName + " not found in the registry]");

            Optional<String> optSubKey = optOutput.get().stream()
                    .map(String::trim).filter(s -> s.endsWith(appName))
                    .findFirst();

            optOutput = Registry.getQueryBuilder().key(optSubKey.get())
                    .useDataPattern(version.toString()).build().execute();

            assertTrue(optOutput.isPresent(),
                    "[" + version + " version not found in the registry]");

        });

        verificators.put(SYSTEM_WIDE, getSystemWideOptionVerificator());
        verificators.put(MENU_GROUP, getMenuGroupVerificator());
        verificators.put(DESCRIPTION, getServiceDescriptionVerificator());
    }

    public WinMsiBundlerUtils() {
        super(BundleType.INSTALLER, BundlerUtils.MSI);
    }

    @Override
    public String install(AppWrapper app, String applicationTitle)
            throws IOException {
        String msiPath = findByExtension(app.getBundlesDir(), "msi",
                ROOT_DIRECTORY_DEPTH).toString();
        try {
            LOG.log(Level.INFO, "Installing {0}.", msiPath);
            String[] cmd = new String[] { "msiexec.exe", "/i", msiPath,
                    "/quiet" };
            runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Installing done.");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
        return getInstalledExecutableLocation(app, applicationTitle).toString();
    }

    @Override
    public void uninstall(AppWrapper app, String applicationTitle)
            throws IOException {
        Path msiPath = findByExtension(app.getBundlesDir(), "msi",
                ROOT_DIRECTORY_DEPTH);
        String msiPath1 = msiPath.toString();
        try {
            LOG.log(Level.INFO, "Uninstalling {0}.", msiPath);
            String[] cmd = new String[] { "msiexec.exe", "/x", msiPath1,
                    "/quiet" };
            runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Uninstallation done from "+msiPath1);
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Path getInstalledAppRootLocation(AppWrapper app, String appName) {
        Path installPath = Paths.get(getProgramFilesDirWindows(), appName);
        if (!exists(installPath)) {
            installPath = Paths.get(System.getenv("LOCALAPPDATA"), appName);
        }

        assertTrue(exists(installPath), "[" + installPath + " not found]");
        return installPath;
    }

    @Override
    public void manualInstall(AppWrapper app) throws IOException {
        String msiPath = findByExtension(app.getBundlesDir(), "msi",
                ROOT_DIRECTORY_DEPTH).toString();
        try {
            LOG.log(Level.INFO, "Running installer {0}.", msiPath);
            String[] cmd = new String[] { "msiexec.exe", "/i", msiPath };
            runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }
}
