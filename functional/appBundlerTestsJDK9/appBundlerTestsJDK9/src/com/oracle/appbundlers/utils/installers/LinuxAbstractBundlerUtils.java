/*
 * Copyright (c) 2014, 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.checkFileContains;
import static com.oracle.appbundlers.utils.Utils.checkFilesEquality;
import static com.oracle.appbundlers.utils.Utils.runCommand;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.ProcessOutput;
import com.oracle.appbundlers.utils.Utils;

import javafx.util.Pair;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public abstract class LinuxAbstractBundlerUtils extends AbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(LinuxAbstractBundlerUtils.class.getName());

    {
        verificators.put(USER_JVM_OPTIONS, (value, app, applicationTitle) -> {
            final String newValue = "NEW_VALUE";
            // preference id is the first arg
            @SuppressWarnings("unchecked")
            Map<String, String> valueMap = ((Pair<Map<String, String>, String>) value)
                    .getKey();
            @SuppressWarnings("unchecked")
            String preferencesId = ((Pair<Map<String, String>, String>) value)
                    .getValue();

            Path prefsXml = getPrefsXml(preferencesId);
            try {
                deleteIfExists(prefsXml);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            verificators.get(MULTI_OUTPUT_CONTAINS).verify(
                    getLinesFromOptions(valueMap), app, applicationTitle);
            try {
                createDirectories(prefsXml.getParent());
                String firstKey = valueMap.keySet().iterator().next();
                valueMap.put(firstKey, newValue);
                String prefsXmlContent = new String(readAllBytes(CONFIG_INSTANCE
                        .getResourceFilePath("prefs.xml.template")))
                                .replace("%KEY%", firstKey)
                                .replace("%VALUE%", newValue);
                write(prefsXml, prefsXmlContent.getBytes());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            verificators.get(MULTI_OUTPUT_CONTAINS).verify(
                    getLinesFromOptions(valueMap), app, applicationTitle);
        });
    }

    public LinuxAbstractBundlerUtils(BundleType type, BundlerUtils id) {
        super(type, id);
    }

    @Override
    public void openFileWithAssociatedApplication(Path path)
            throws IOException, ExecutionException {
        runCommand(Arrays.asList("xdg-open", path.toString()),
                CONFIG_INSTANCE.getRunTimeout());
    }

    // only for DEB and RPM
    protected VerificationMethod getIconVerificator() {
        return (value, app, applicationTitle) -> {
            Path sourceIcon = ((File) value).toPath();
            Path destIcon = getInstalledAppRootLocation(app, applicationTitle)
                    .resolve(applicationTitle + ".png");
            try {
                assertTrue(checkFilesEquality(sourceIcon, destIcon),
                        "Asserting icons are equal: " + sourceIcon + " vs. "
                                + destIcon);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    // only for DEB and RPM
    protected VerificationMethod getTitleVerificator() {
        return (value, app, applicationTitle) -> {
            Path desktopFile = getDesktopFile(app, applicationTitle);
            try {
                assertTrue(checkFileContains(desktopFile,
                        Pattern.quote("Comment=" + value)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    // only for DEB and RPM
    protected VerificationMethod getCategoryVerificator() {
        return (value, app, applicationTitle) -> {
            Path desktopFile = getDesktopFile(app, applicationTitle);
            try {
                assertTrue(checkFileContains(desktopFile,
                        Pattern.quote("Categories=" + value)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    // only for DEB and RPM
    protected VerificationMethod getServiceHintVerificator() {
        return (value, app, applicationTitle) -> {
            Path initScript = Paths.get("/etc/init.d",
                    applicationTitle.toLowerCase());
            assertTrue(exists(initScript), "init script doesn't exist");
        };
    }

    // only for DEB and RPM
    protected VerificationMethod getFileAssociationVerificator() {
        return (value, app, applicationTitle) -> {
            try {
                File oneOfIcons = null;

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> associations = (List<Map<String, Object>>) value;
                for (Map<String, Object> association : associations) {
                    @SuppressWarnings("unchecked")
                    // get expected data
                    List<String> extensions = (List<String>) association
                            .get(FA_EXTENSIONS);
                    @SuppressWarnings("unchecked")
                    List<String> contentTypes = (List<String>) association
                            .get(FA_CONTENT_TYPE);
                    String description = (String) association
                            .get(FA_DESCRIPTION);
                    File icon = (File) association.get(FA_ICON);

                    // only one file association is supported under Linux
                    String contentType = contentTypes.get(0);

                    // check association of extension with our application name
                    for (String ext : extensions) {
                        Path tmp = Files.createTempFile("test", "." + ext);
                        ProcessOutput process = Utils.runCommand(
                                Arrays.asList("xdg-mime", "query", "filetype",
                                        tmp.toString()),
                                CONFIG_INSTANCE.getRunTimeout());
                        String fileType = process.getOutputStream().get(0);
                        assertEquals(fileType, contentType);
                    }

                    // check, that mimetype is associated with correct
                    // application
                    ProcessOutput process = Utils.runCommand(
                            Arrays.asList("xdg-mime", "query", "default",
                                    contentType),
                            CONFIG_INSTANCE.getRunTimeout());
                    Path desktopFile = Paths.get("/usr/share/applications",
                            process.getOutputStream().get(0));
                    assertTrue(Utils.checkFilesEquality(desktopFile,
                            getDesktopFile(app, applicationTitle)));

                    if (icon != null) {
                        oneOfIcons = icon;
                    }
                }
            } catch (IOException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    // only for DEB and RPM
    protected Path getDesktopFile(AppWrapper app, String applicationTitle) {
        return getInstalledAppRootLocation(app, applicationTitle)
                .resolve(applicationTitle + ".desktop");
    }

    @Override
    public Path getInstalledExecutableLocation(AppWrapper app,
            String applicationTitle, String execName) {
        return getInstalledAppRootLocation(app, applicationTitle)
                .resolve(execName);
    }

    private Path getPrefsXml(String preferencesId) {
        return Paths.get(System.getProperty("user.home"), ".java", ".userPrefs")
                .resolve(preferencesId.replace('.', '/'))
                .resolve("JVMUserOptions/prefs.xml");

    }

    @Override
    public Path getAppCDSCacheFile(AppWrapper app, String appName,
            ExtensionType extension) {
        return Paths.get(System.getenv("HOME"), ".local",
                app.getIdentifier(extension), "cache", appName + ".jpa");
    };

    public Path getJavaExecutableBinPathInInstalledApp(AppWrapper appWrapper,
            String appTitle) {
        return getInstalledAppRootLocation(appWrapper, appTitle).resolve("runtime/bin");
    }

    @Override
    public void checkIfExecutablesAvailableInBinDir(Path binDirPath) throws IOException {
        Optional<Path> result = Files.find(binDirPath, 1, (file, attr) -> file
                .toFile().getName().equals("java")).findFirst();
        if (!result.isPresent()) {
            throw new FileNotFoundException("java"
                    + " not found under " + binDirPath );
        }
        LOG.log(Level.INFO, "java executable found in {0}", binDirPath);
    }

    @Override
    public String getJavaExecutable() {
        return "java";
    }
}
