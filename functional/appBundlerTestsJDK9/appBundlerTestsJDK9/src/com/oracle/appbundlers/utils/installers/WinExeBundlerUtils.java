/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.getProgramFilesDirWindows;
import static java.nio.file.Files.exists;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.Config;
import com.oracle.appbundlers.utils.ProcessOutput;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.appbundlers.utils.windows.Registry;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */
public class WinExeBundlerUtils extends WinAbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(WinExeBundlerUtils.class.getName());

    {
        verificators.put(SHORTCUT_HINT, getShortcutHintVerificator());

        verificators.put(MENU_HINT, getMenuGroupVerificator());
        verificators.put(SERVICE_HINT, getServiceHintVerificator());
        verificators.put(START_ON_INSTALL, getStartOnInstallVerificator());
        verificators.put(RUN_AT_STARTUP, getRunAtStartupVerificator());
        verificators.put(VENDOR, getMenuGroupVerificator());
        verificators.put(VERSION, (version, app, appName) -> {
            Path config = getInstalledAppRootLocation(app, appName)
                    .resolve("app/" + appName + ".cfg");
            try {
                assertTrue(
                        Utils.checkFileContains(config,
                                "app.version=" + version),
                        "[Version info not found]");
            } catch (IOException ex) {
                fail("[Unable to read package.cfg]");
            }
        });

        verificators.put(SYSTEM_WIDE, getSystemWideOptionVerificator());
        verificators.put(EXE_SYSTEM_WIDE, getSystemWideOptionVerificator());
        verificators.put(MENU_GROUP, getMenuGroupVerificator());
        verificators.put(WIN_USER_FILE_ASSOCIATIONS,
                getUserFileAssociationVerificator());
        verificators.put(WIN_SYSTEM_WIDE_FILE_ASSOCIATIONS,
                getFileAssociationVerificator());
        verificators.put(TITLE, (val, app, appName) -> {
            Optional<String> optKey = Registry.findAppRegistryKey(appName);
            assertTrue(optKey.isPresent(),
                    "[Registry info not found for " + appName + "]");

            Optional<List<String>> optContent = Registry.queryKey(optKey.get());
            optContent.ifPresent(System.out::println);
            final String expectedText = val.toString();

            Optional<String> comment = optContent.map(content -> {
                return content.parallelStream().map(String::trim)
                        .filter(s -> s.startsWith("Comments")
                                && s.endsWith(expectedText))
                        .findFirst();
            }).orElseGet(() -> {
                return Optional.empty();
            });

            assertTrue(comment.isPresent(),
                    "[Comments are not set in registry for " + appName + "]");
        });

        verificators.put(COPYRIGHT, (copyright, app, appName) -> {
            Path installer = app.getBundlesDir().resolve(appName + "-1.0.exe");
            assertTrue(exists(installer),
                    "[" + installer + " does not exists]");

            try {
                Path temp = Files.createTempDirectory("SQE");
                Path tmpInstaller = Files.copy(installer,
                        temp.resolve("installer.exe"),
                        StandardCopyOption.COPY_ATTRIBUTES);

                String content = new String(
                        Files.readAllBytes(
                                Config.CONFIG_INSTANCE.getResourcePath()
                                        .resolve("getExeCopyright.vbs")),
                        StandardCharsets.UTF_8);

                Path script = Files.createFile(temp.resolve("script.vbs"));
                Files.write(script,
                        content.replace("__FILE_NAME__",
                                tmpInstaller.getFileName().toString())
                        .getBytes());

                ProcessOutput output = Utils.runCommand(
                        new String[] { "cscript", script.toString() }, true,
                        CONFIG_INSTANCE.getRunTimeout());

                assertTrue(
                        output.getOutputStream().parallelStream()
                                .map(String::trim).anyMatch(
                                        s -> s.contains(copyright.toString())),
                        "[Copyright wasn't stored in " + installer + "]");

            } catch (IOException | ExecutionException ex) {
                fail("[Unable to query " + installer + " due to "
                        + ex.getMessage() + "]");
            }
        });

        verificators.put(DESCRIPTION, getServiceDescriptionVerificator());
    }

    public WinExeBundlerUtils() {
        super(BundleType.INSTALLER, BundlerUtils.EXE);
    }

    @Override
    public String install(AppWrapper app, String applicationTitle)
            throws IOException {
        String exePath = findByExtension(app.getBundlesDir(), "exe",
                ROOT_DIRECTORY_DEPTH, applicationTitle).toString();
        try {
            LOG.log(Level.INFO, "Installing {0}.", exePath);
            String[] cmd = new String[] { exePath, "/VERYSILENT" };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Installation done.");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
        return getInstalledExecutableLocation(app, applicationTitle).toString();
    }

    @Override
    public void uninstall(AppWrapper app, String appName) throws IOException {
        try {
            final Path exePath = Paths.get(appName, "unins000.exe");

            Path uninstaller = Paths.get(System.getenv("LOCALAPPDATA"))
                    .resolve(exePath);
            if (!exists(uninstaller)) {
                uninstaller = Paths.get(getProgramFilesDirWindows())
                        .resolve(exePath);
            }
            if (!exists(uninstaller)) {
                LOG.warning("Can't find uninstaller.");
                return;
            }
            LOG.log(Level.INFO, "Using uninstaller: {0}", uninstaller);
            LOG.log(Level.INFO, "Uninstalling {0}", appName);
            String[] cmd = new String[] { uninstaller.toString(),
                    "/VERYSILENT" };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Uninstallation done.");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Path getInstalledAppRootLocation(AppWrapper app, String appName) {
        Path installPath = Paths.get(System.getenv("LOCALAPPDATA"), appName);
        if (!Files.exists(installPath)) {
            installPath = Paths.get(getProgramFilesDirWindows(), appName);
        }

        assertTrue(Files.exists(installPath),
                "[" + installPath + " not found]");
        return installPath;
    }

    @Override
    public void manualInstall(AppWrapper app) throws IOException {
        String exePath = findByExtension(app.getBundlesDir(), "exe",
                ROOT_DIRECTORY_DEPTH).toString();
        try {
            LOG.log(Level.INFO, "Running installer: {0}", exePath);
            Utils.runCommand(new String[] { exePath }, true,
                    CONFIG_INSTANCE.getInstallTimeout());
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }
}
