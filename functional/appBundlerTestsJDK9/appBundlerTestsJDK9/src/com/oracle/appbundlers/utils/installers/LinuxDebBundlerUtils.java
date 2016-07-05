/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.runCommand;
import static com.oracle.tools.packager.linux.LinuxDebBundler.BUNDLE_NAME;
import static java.lang.String.format;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.testng.Assert;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ProcessOutput;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.linux.LinuxDebBundler;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class LinuxDebBundlerUtils extends LinuxAbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(LinuxDebBundlerUtils.class.getName());

    public static final String getCopyrightCmdTemplate = "ar p %DEB_FILE% control.tar.gz | tar xzvO ./copyright";

    {
        VerificationMethod copyrightVerificator = (value, app,
                applicationTitle) -> {
            try {
                Path debPath = findByExtension(app.getBundlesDir(), "deb",
                        ROOT_DIRECTORY_DEPTH);
                ProcessOutput copyrightContent = Utils.runCommand(
                        new String[] { "/bin/sh", "-c",
                                getCopyrightCmdTemplate.replace("%DEB_FILE%",
                                        debPath.toString()) },
                        CONFIG_INSTANCE.getRunTimeout());

                String output = copyrightContent.getOutputStream().stream()
                        .collect(Collectors.joining(System.lineSeparator()));
                assertTrue(output.contains(value.toString()),
                        format("Expected:%n%s%nActual:%n%s", value, output));
            } catch (IOException | ExecutionException e) {
                Assert.fail(e.getMessage(), e);
            }
        };
        verificators.put(COPYRIGHT, copyrightVerificator);
        verificators.put(LICENSE_TYPE, copyrightVerificator);
        verificators.put(LICENSE_FILE, copyrightVerificator);
        verificators.put(ICON, getIconVerificator());
        verificators.put(TITLE, (value, app, applicationTitle) -> {
            getTitleVerificator().verify(value, app, applicationTitle);
            getDebInfoContainsVerificator(
                    "Description\\s*:\\s*" + Pattern.quote(value.toString()))
                            .verify(value, app, applicationTitle);
        });
        verificators.put(VENDOR,
                (value, app, applicationTitle) -> getDebInfoContainsVerificator(
                        "Maintainer\\s*:\\s*" + Pattern.quote(value.toString()))
                                .verify(value, app, applicationTitle));
        verificators.put(MAINTAINER, verificators.get(VENDOR));
        verificators.put(EMAIL,
                (value, app, applicationTitle) -> getDebInfoContainsVerificator(
                        "Maintainer\\s*:[^\n]*\\<"
                                + Pattern.quote(value.toString()) + "\\>")
                                        .verify(value, app, applicationTitle));
        verificators.put(VERSION,
                (value, app, applicationTitle) -> getDebInfoContainsVerificator(
                        "Version\\s*:\\s*" + Pattern.quote(value.toString()))
                                .verify(value, app, applicationTitle));
        verificators.put(CATEGORY, getCategoryVerificator());
        verificators.put(FILE_ASSOCIATIONS, getFileAssociationVerificator());
        verificators.put(BUNDLE_NAME.getID(),
                (value, app, applicationTitle) -> {
                    try {
                        ProcessOutput aptCacheShowOutput = runCommand(
                                Arrays.asList("apt-cache", "show",
                                        value.toString().toLowerCase()),
                                CONFIG_INSTANCE.getRunTimeout());
                        assertTrue(aptCacheShowOutput.exitCode() == 0);
                    } catch (IOException | ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        verificators.put(SERVICE_HINT, getServiceHintVerificator());
    }

    public LinuxDebBundlerUtils() {
        super(BundleType.INSTALLER, BundlerUtils.DEB);
    }

    private VerificationMethod getDebInfoContainsVerificator(String regex) {
        return (value, app, applicationTitle) -> {
            try {
                String output = Utils
                        .runCommand(
                                new String[] { "apt-cache", "show",
                                        applicationTitle.toLowerCase() },
                                CONFIG_INSTANCE.getRunTimeout())
                        .getOutputStream().stream()
                        .collect(Collectors.joining(System.lineSeparator()));
                assertTrue(Pattern.compile(regex).matcher(output).find());
            } catch (IOException | ExecutionException e) {
                Assert.fail(e.getMessage(), e);
            }
        };
    }

    @Override
    public String install(AppWrapper app, String applicationTitle)
            throws IOException {
        String debPath = this.findByExtension(app.getBundlesDir(), "deb",
                ROOT_DIRECTORY_DEPTH).toString();
        try {
            LOG.log(Level.INFO, "Installing {0}.", new Object[] { debPath });
            String[] cmd = new String[] { "sudo", "dpkg", "--install",
                    debPath };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Installation finished.");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
        return getInstalledExecutableLocation(app, applicationTitle).toString();
    }

    @Override
    public void uninstall(AppWrapper app, String applicationTitle)
            throws IOException {
        try {
            LOG.log(Level.INFO, "Uninstalling {0}.",
                    new Object[] { applicationTitle });
            String[] cmd = new String[] { "sudo", "apt-get", "-y", "remove",
                    applicationTitle.toLowerCase() };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Uninstalling done.");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }

    @Override
    public Path getInstalledAppRootLocation(AppWrapper app,
            String applicationTitle) {
        return Paths.get("/opt", applicationTitle);
    }

    @Override
    public String getAppName(Map<String, Object> params) {
        return LinuxDebBundler.BUNDLE_NAME.fetchFrom(params);
    }

    @Override
    public void manualInstall(AppWrapper app) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // To
                                                                       // change
                                                                       // body
                                                                       // of
                                                                       // generated
                                                                       // methods,
                                                                       // choose
                                                                       // Tools
                                                                       // |
                                                                       // Templates.
    }
}
