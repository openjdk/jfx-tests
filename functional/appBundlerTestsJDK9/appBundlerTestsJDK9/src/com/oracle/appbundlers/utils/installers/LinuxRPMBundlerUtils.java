/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.runCommand;
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
import com.oracle.tools.packager.linux.LinuxRpmBundler;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class LinuxRPMBundlerUtils extends LinuxAbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(LinuxDebBundlerUtils.class.getName());

    {
        verificators.put(LICENSE_TYPE,
                (value, app, applicationTitle) -> getRpmInfoContainsVerificator(
                        "License\\s*:\\s*" + Pattern.quote(value.toString())
                                + "\\s*").verify(value, app, applicationTitle));
        verificators.put(ICON, getIconVerificator());
        verificators.put(TITLE, (value, app, applicationTitle) -> {
            getTitleVerificator().verify(value, app, applicationTitle);
            getRpmInfoContainsVerificator(
                    "Summary\\s*:\\s*" + Pattern.quote(value.toString()))
                            .verify(value, app, applicationTitle);
        });
        verificators.put(VENDOR,
                (value, app, applicationTitle) -> getRpmInfoContainsVerificator(
                        "Vendor\\s*:\\s*" + Pattern.quote(value.toString()))
                                .verify(value, app, applicationTitle));
        verificators.put(VERSION,
                (value, app, applicationTitle) -> getRpmInfoContainsVerificator(
                        "Version\\s*:\\s*" + Pattern.quote(value.toString()))
                                .verify(value, app, applicationTitle));
        verificators.put(DESCRIPTION, (value, app,
                applicationTitle) -> getRpmInfoContainsVerificator(
                        "Description\\s*:\\s*"
                                + Pattern.quote(value.toString())).verify(value,
                                        app, applicationTitle));
        verificators.put(CATEGORY, getCategoryVerificator());
        verificators.put(FILE_ASSOCIATIONS, getFileAssociationVerificator());
        verificators.put(LinuxRpmBundler_BUNDLE_NAME,
                (value, app, applicationTitle) -> {
                    try {
                        ProcessOutput rpmGetInfoOutput = runCommand(
                                Arrays.asList("rpm", "-ql", value.toString()),
                                CONFIG_INSTANCE.getRunTimeout());
                        assertTrue(rpmGetInfoOutput.exitCode() == 0);
                    } catch (IOException | ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        verificators.put(SERVICE_HINT, getServiceHintVerificator());
    }

    public LinuxRPMBundlerUtils() {
        super(BundleType.INSTALLER, BundlerUtils.RPM);
    }

    private VerificationMethod getRpmInfoContainsVerificator(String regex) {
        return (value, app, applicationTitle) -> {
            try {
                Path rpmFile = findByExtension(app.getBundlesDir(), "rpm",
                        ROOT_DIRECTORY_DEPTH);
                String output = Utils
                        .runCommand(
                                new String[] { "rpm", "-qip",
                                        rpmFile.toString() },
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
        String rpmPath = findByExtension(app.getBundlesDir(), "rpm",
                ROOT_DIRECTORY_DEPTH).toString();
        try {
            LOG.log(Level.INFO, "Installing {0}.", rpmPath);
            String[] cmd = new String[] { "sudo", "rpm", "--install",
                    "--nodeps", rpmPath };
            Utils.runCommand(cmd, true, CONFIG_INSTANCE.getInstallTimeout());
            LOG.info("Installing done.");
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
            String[] cmd = new String[] { "sudo", "rpm", "-e", "--nodeps",
                    applicationTitle };
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
        String fetchFrom = LinuxRpmBundler.BUNDLE_NAME.fetchFrom(params);
        System.out.println("Linux RPM Bundle Name is "+fetchFrom);
        return fetchFrom;
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
