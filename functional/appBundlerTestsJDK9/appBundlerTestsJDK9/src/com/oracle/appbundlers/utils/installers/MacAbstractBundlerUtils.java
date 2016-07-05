/*
 * Copyright (c) 2014, 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.runCommand;
import static java.nio.file.Files.readAllBytes;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ProcessOutput;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public abstract class MacAbstractBundlerUtils extends AbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(MacAbstractBundlerUtils.class.getName());

    public static final String lsregisterPath = "/System/Library/Frameworks/CoreServices.framework/Versions/A/Frameworks/LaunchServices.framework/Versions/A/Support/lsregister";

    {
        verificators.put(COPYRIGHT,
                keyValueInfoPlistVerificator("NSHumanReadableCopyright"));
        verificators.put(IDENTIFIER,
                keyValueInfoPlistVerificator("CFBundleIdentifier"));
        verificators.put(VERSION,
                keyValueInfoPlistVerificator("CFBundleShortVersionString"));
        verificators.put(MAC_CATEGORY,
                keyValueInfoPlistVerificator("LSApplicationCategoryType"));
        verificators.put(CATEGORY, verificators.get(MAC_CATEGORY));
        verificators.put(MAC_CF_BUNDLE_NAME,
                keyValueInfoPlistVerificator("CFBundleName"));
        verificators.put(ICON, (value, app, appName) -> {
            try {
                assertTrue(Utils.checkFilesEquality(
                        getInstalledAppRootLocation(app, appName)
                                .resolve("Contents/Resources")
                                .resolve(appName + ".icns"),
                        ((File) value).toPath()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        verificators.put(FILE_ASSOCIATIONS, getFileAssociationVerificator());

        verificators.put(SIGNING_KEYCHAIN, (value, app, appName) -> {
            try {
                ProcessOutput output = Utils
                        .runCommand(
                                Arrays.asList("pkgutil", "--check-signature",
                                        app.getBundlesDir().toFile()
                                                .listFiles()[0].toString()),
                                CONFIG_INSTANCE.getRunTimeout());
                checkOutputContains(output, Arrays
                        .asList("Status: signed by untrusted certificate"));
            } catch (IOException | ExecutionException e) {
                Assert.fail(e.getMessage(), e);
            }
        });
    }

    public MacAbstractBundlerUtils(BundleType type, BundlerUtils id) {
        super(type, id);
    }

    @Override
    public void openFileWithAssociatedApplication(Path path)
            throws IOException, ExecutionException {
        runCommand(Arrays.asList("open", "-W", path.toString()),
                CONFIG_INSTANCE.getRunTimeout());
    }

    @Override
    public Path getInstalledExecutableLocation(AppWrapper app,
            String applicationTitle, String execName) {
        return getInstalledAppRootLocation(app, applicationTitle)
                .resolve("Contents/MacOS").resolve(execName);
    }

    protected Path getInfoPlist(AppWrapper app, String applicationTitle) {
        return getInstalledAppRootLocation(app, applicationTitle)
                .resolve("Contents/Info.plist");
    }

    protected String getInfoPlistContent(AppWrapper app,
            String applicationTitle) throws IOException {
        return new String(readAllBytes(getInfoPlist(app, applicationTitle)),
                "UTF-8");
    }

    @Override
    public void registerFileAssociations(AppWrapper app,
            String applicationTitle) throws IOException, ExecutionException {
        runCommand(Arrays.asList("open",
                getInstalledAppRootLocation(app, applicationTitle).toString()),
                CONFIG_INSTANCE.getRunTimeout());
    }

    protected VerificationMethod keyValueInfoPlistVerificator(String key) {
        return (value, app, appName) -> {
            try {
                assertTrue(
                        Pattern.compile(
                                Pattern.quote("<key>" + key + "</key>") + "\\s*"
                                        + Pattern.quote("<string>"
                                                + value.toString().trim())
                        + "\\s*" + Pattern.quote("</string>"))
                        .matcher(getInfoPlistContent(app, appName)).find(),
                        "Info.plist doesn't contain {" + key + ": " + value
                                + "}");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    protected VerificationMethod getFileAssociationVerificator() {
        return (value, app, appName) -> {
            try {
                registerFileAssociations(app, appName);

                File oneOfIcons = null;

                final List<String> appDetails = getApplicationRegistrationDetailsFromLsregister(
                        getInstalledAppRootLocation(app, appName).toString());

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> associations = (List<Map<String, Object>>) value;
                for (Map<String, Object> association : associations) {

                    // get expected data
                    @SuppressWarnings("unchecked")
                    final List<String> extensions = (List<String>) association
                            .get(FA_EXTENSIONS);
                    @SuppressWarnings("unchecked")
                    final List<String> contentTypes = (List<String>) association
                            .get(FA_CONTENT_TYPE);
                    final String description = (String) association
                            .get(FA_DESCRIPTION);
                    File icon = (File) association.get(FA_ICON);

                    LOG.log(Level.INFO, "Checking extensions: {0}.",
                            extensions);
                    final List<String> extRegexes = extensions.stream()
                            .map(ext -> ".*tags:.*\\." + ext + ".*")
                            .collect(Collectors.toList());

                    assertTrue(
                            eachRegexMatchesAtLeastOnce(extRegexes, appDetails),
                            "[Extensions were not found in lsregister output]");

                    if (contentTypes != null) {

                        LOG.log(Level.INFO, "Checking content types: {0}.",
                                contentTypes);
                        List<String> typeRegexes = contentTypes.stream()
                                .map(type -> ".*tags:.*" + type + ".*")
                                .collect(Collectors.toList());

                        assertTrue(
                                eachRegexMatchesAtLeastOnce(typeRegexes,
                                        appDetails),
                                "[Content types were not found in lsregister output]");
                    }

                    if (description != null) {
                        LOG.log(Level.INFO, "Checking description: '{0}'.",
                                description);

                        List<String> descriptionRegex = Stream.of(description)
                                .map(d -> ".*description:\\s*" + d + ".*")
                                .collect(Collectors.toList());

                        assertTrue(
                                eachRegexMatchesAtLeastOnce(descriptionRegex,
                                        appDetails),
                                "[File association description was not found in lsregister output]");
                    }

                    if (icon != null) {
                        oneOfIcons = icon;
                    }

                }
                // check icon
                if (oneOfIcons != null) {
                    // TODO: need to check that correct icon is associated with
                    // extension
                }
            } catch (IOException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private static String getLsregisterDump()
            throws IOException, ExecutionException {
        ProcessOutput lsregisterProcess = runCommand(
                Arrays.asList(lsregisterPath, "-dump"), /* verbose */ false,
                CONFIG_INSTANCE.getRunTimeout());

        return lsregisterProcess.getOutputStream().stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static List<String> getApplicationRegistrationDetailsFromLsregister(
            String appInstallationDir) throws IOException, ExecutionException {

        String lsregisterDump = getLsregisterDump();

        final String everythingReluctantly = ".*?";
        final String wholeLineOfDashes = "^-*$";
        final String startGroup = "(";
        final String endGroup = ")";

        Matcher bundleRecordsMatcher = Pattern.compile(
                Pattern.quote(appInstallationDir) + startGroup
                        + everythingReluctantly + endGroup + wholeLineOfDashes,
                Pattern.MULTILINE | Pattern.DOTALL).matcher(lsregisterDump);

        LOG.log(Level.INFO, "Looking for: '{0}'.",
                new Object[] { bundleRecordsMatcher.pattern().pattern() });

        List<String> bundleRecords = new ArrayList<>();
        while (bundleRecordsMatcher.find()) {
            bundleRecords.add(bundleRecordsMatcher.group(1));
        }

        LOG.log(Level.INFO, "Found {0} groups.", bundleRecords.size());

        final String oneOrMoreSpace = "\\s+";
        final String oneOrMoreDash = "-+";

        return bundleRecords.stream()
                .flatMap(s -> Stream.of(s.split(
                        oneOrMoreSpace + oneOrMoreDash + oneOrMoreSpace)))
                // Make one line string from each multiline entry
                .map(s -> s.replaceAll("\\n", " "))
                .collect(Collectors.toList());
    }

    private static boolean eachRegexMatchesAtLeastOnce(List<String> regexes,
            List<String> items) {
        boolean success = true;
        for (String regex : regexes) {
            success |= items.stream().anyMatch(record -> {
                return record.matches(regex);
            });
        }
        return success;
    }

    @Override
    public Path getAppCDSCacheFile(AppWrapper app, String appName) {
        return Paths.get(System.getenv("HOME"), "Library/Application Support",
                app.getIdentifier(), "cache", appName + ".jpa");
    };
}
