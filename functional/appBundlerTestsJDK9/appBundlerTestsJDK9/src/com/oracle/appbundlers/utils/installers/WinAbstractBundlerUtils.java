/*
 * Copyright (c) 2014, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils.installers;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.Utils.checkFilesEquality;
import static com.oracle.appbundlers.utils.Utils.getProgramFilesDirWindows;
import static com.oracle.appbundlers.utils.Utils.runCommand;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.exists;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.testng.Assert;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.Config;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.ProcessOutput;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.appbundlers.utils.windows.Registry;

/**
 *
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */
public abstract class WinAbstractBundlerUtils extends AbstractBundlerUtils {

    private static final Logger LOG = Logger
            .getLogger(WinAbstractBundlerUtils.class.getName());

    public WinAbstractBundlerUtils(BundleType type, BundlerUtils id) {
        super(type, id);
    }

    {
        verificators.put(ICON, (sourceIconPath, app, appName) -> {
            Path sourceIcon = ((File) sourceIconPath).toPath();
            Path destIcon = getInstalledAppRootLocation(app, appName)
                    .resolve(appName + ".ico");

            try {
                assertTrue(checkFilesEquality(sourceIcon, destIcon),
                        "[Icon wasn't correctly placed into installation directory]");
            } catch (IOException ex) {
                fail("[" + ex.getMessage() + "]");
            }
        });

        verificators.put(LICENSE_FILE, (value, app, appName) -> {
            try {
                Path license = getInstalledAppRootLocation(app, appName)
                        .resolve("app").resolve(Config.LICENSE_FILE_NAME);
                assertTrue(Files.exists(license),
                        "[" + license + " does not exists]");
                assertTrue(checkFilesEquality(license, Config.CONFIG_INSTANCE
                        .getResourcePath().resolve(Config.LICENSE_FILE_NAME)));
            } catch (IOException e) {
                Assert.fail(e.getMessage(), e);
            }
        });

        verificators.put(JVM_PROPERTIES, (value, app, appName) -> {
            @SuppressWarnings("unchecked")
            Map<String, String> jvmProperties = (Map<String, String>) value;
            for (Map.Entry<String, String> pair : jvmProperties.entrySet()) {
                verificators.get(OUTPUT_CONTAINS).verify(String
                        .format("-D%s=%s", pair.getKey(), pair.getValue()), app,
                        appName);
            }
        });

    }

    @Override
    public void openFileWithAssociatedApplication(Path path)
            throws IOException, ExecutionException {
        runCommand(Arrays.asList("cmd", "/c", path.toString()),
                CONFIG_INSTANCE.getRunTimeout());
    }

    @Override
    public Path getInstalledExecutableLocation(AppWrapper app,
            String applicationTitle, String execName) {
        return getInstalledAppRootLocation(app, applicationTitle)
                .resolve(execName + ".exe");
    }

    protected VerificationMethod getShortcutHintVerificator() {
        return (value, app, applicationTitle) -> {
            Path desktop = Paths.get(System.getenv("userprofile"))
                    .resolve("Desktop");
            final String shortcut = applicationTitle + ".lnk";

            boolean shortcutExists = desktop.resolve(shortcut).toFile()
                    .exists();
            shortcutExists |= Paths.get("C:\\Users\\Public\\Desktop")
                    .resolve(shortcut).toFile().exists();

            assertTrue(shortcutExists, "[Unable to locate " + shortcut + "]");
        };
    }

    protected VerificationMethod getServiceHintVerificator() {
        return (value, app, applicationTitle) -> {
            try {
                ProcessOutput out = Utils.runCommand(
                        new String[] { "sc", "query", applicationTitle }, true,
                        CONFIG_INSTANCE.getRunTimeout());
                assertTrue(
                        out.getOutputStream().parallelStream().map(String::trim)
                                .anyMatch(("SERVICE_NAME: "
                                        + applicationTitle)::equals),
                        "[Service " + applicationTitle + " not installed]");
            } catch (IOException | ExecutionException ex) {
                fail("[Failed to query service " + applicationTitle + "]");
            }
        };
    }

    protected VerificationMethod getStartOnInstallVerificator() {
        return (value, app, applicationTitle) -> {
            try {
                ProcessOutput out = Utils.runCommand(
                        new String[] { "sc", "query", applicationTitle }, true,
                        CONFIG_INSTANCE.getRunTimeout());
                assertTrue(
                        out.getOutputStream().parallelStream().map(String::trim)
                                .map(s -> s.replaceAll("\\s+", " "))
                                .anyMatch(("STATE : 4 RUNNING")::equals),
                        "[Service " + applicationTitle + " not running]");
            } catch (IOException | ExecutionException ex) {
                fail("[Failed to query service " + applicationTitle + "]");
            }
        };
    }

    protected VerificationMethod getRunAtStartupVerificator() {
        return (value, app, applicationTitle) -> {
            try {
                ProcessOutput out = Utils.runCommand(
                        new String[] { "sc", "qc",  applicationTitle, Integer.toString(2000) }, true,
                        CONFIG_INSTANCE.getRunTimeout());
                assertTrue(
                        out.getOutputStream().parallelStream().map(String::trim)
                                .map(s -> s.replaceAll("\\s+", " ")).anyMatch(
                                        ("START_TYPE : 2 AUTO_START")::equals),
                        "[Service " + applicationTitle
                                + " won't run on startup]");
            } catch (IOException | ExecutionException ex) {
                fail("[Failed to query service " + applicationTitle + "]");
            }
        };
    }

    protected VerificationMethod getSystemWideOptionVerificator() {
        return (isSystemWideObj, app, appName) -> {
            Boolean isSystemWide = (Boolean) isSystemWideObj; // Happy cast!

            if (isSystemWide) {

                Path progFiles = Paths.get(getProgramFilesDirWindows());
                assertTrue(
                        exists(progFiles.resolve(appName)
                                .resolve(appName + ".exe")),
                        "[" + appName
                                + " not installes in neither of Program Files folders]");
            } else {

                boolean appInstalled = exists(
                        Paths.get(System.getenv("LOCALAPPDATA"))
                                .resolve(appName).resolve(appName + ".exe"));
                assertTrue(appInstalled, "[" + appName
                        + " not installes in neither of Program Files folders]");
            }
        };
    }

    protected VerificationMethod getMenuGroupVerificator() {
        return (group, app, appName) -> {
            Path lnk = Paths.get("Microsoft/Windows/Start Menu/Programs/",
                    group.toString(), appName + ".lnk");
            Path appPath = Paths.get(System.getenv("APPDATA")).resolve(lnk);

            // If system wide installation was made
            if (!exists(appPath)) {
                appPath = Paths.get(System.getenv("ProgramData")).resolve(lnk);
            }

            assertTrue(exists(appPath), "[" + appPath + " does not exist]");
        };
    }

    protected VerificationMethod getServiceDescriptionVerificator() {
        return (descriptionObj, app, appName) -> {

            try {
                Path temp = Files.createTempDirectory("SQE");
                String content = new String(Files
                        .readAllBytes(Config.CONFIG_INSTANCE.getResourcePath()
                                .resolve("getServiceInfo.vbs")),
                        UTF_8);
                Path script = Files.createFile(temp.resolve("script.vbs"));
                Files.write(script,
                        content.replace("__PROPERTY__", "Description")
                                .replace("__SERVICE_NAME__", appName)
                                .getBytes(UTF_8));

                ProcessOutput output = Utils.runCommand(
                        new String[] { "cscript", script.toString() }, true,
                        CONFIG_INSTANCE.getRunTimeout());

                assertTrue(
                        output.getOutputStream().parallelStream()
                                .map(String::trim)
                                .anyMatch(s -> s
                                        .contains(descriptionObj.toString())),
                        "[Service '" + appName
                                + "' didn't have the description "
                                + descriptionObj + "]");

            } catch (IOException | ExecutionException ex) {
                fail("[Unable to query " + appName + " service due to "
                        + ex.getMessage() + "]");
            }
        };
    }

    protected VerificationMethod getFileAssociationVerificator() {
        return (value, app, appName) -> {
            try {
                File oneOfIcons = null;
                String extension = null;

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> associations = (List<Map<String, Object>>) value;
                for (Map<String, Object> association : associations) {
                    // get expected data
                    @SuppressWarnings("unchecked")
                    List<String> extensions = (List<String>) association
                            .get(FA_EXTENSIONS);
                    File icon = (File) association.get(FA_ICON);

                    // check association of extension with our application name
                    for (String ext : extensions) {
                        ProcessOutput process = Utils.runCommand(
                                Arrays.asList("cmd", "/c", "assoc", "." + ext),
                                CONFIG_INSTANCE.getRunTimeout());

                        // assoc output may have a "dot number" suffix
                        // e.g. ".foo=RmAppFile", but ".bar=RmAppFile.1"
                        // so we'll use regex to check for this case
                        Pattern pattern = Pattern.compile(format(
                                "\\.%s=%s[Ff]ile(\\.\\d+)?", ext, appName));

                        assertTrue(process.getOutputStream().stream()
                                .anyMatch(s -> pattern.matcher(s).matches()));
                    }

                    // check, that application is bounded with it's path
                    String execPath = getInstalledExecutableLocation(app,
                            appName).toString().replaceAll("\\.exe$", "");
                    ProcessOutput process = Utils.runCommand(
                            Arrays.asList("cmd", "/c", "ftype",
                                    appName + "File"),
                            CONFIG_INSTANCE.getRunTimeout());
                    assertTrue(process.getOutputStream().stream()
                            .anyMatch(str -> str.contains(execPath)));

                    if (icon != null) {
                        oneOfIcons = icon;
                        // Since the icon will be used for all extensions within
                        // same file association
                        // we may take any.
                        extension = extensions.get(0);
                    }
                    // TODO: description and contentTypes are not checked!!!
                    // see https://bugs.openjdk.java.net/browse/JDK-8093106
                }
                // check icon
                if (oneOfIcons != null) {

                    // Now we should find the location of the icon in the
                    // filesystem
                    // and check that it binary equals to the provided one.
                    // I. Get file association
                    // E.g. 'assoc .foo' => '.foo=RmAppFile.1'
                    // So take the 'RmAppFile.1' part
                    ProcessOutput process = Utils.runCommand(
                            Arrays.asList("cmd", "/c", "assoc",
                                    "." + extension),
                            CONFIG_INSTANCE.getRunTimeout());
                    String fileAssociation = process.getOutputStream().get(0)
                            .split("=")[1];

                    // II. Find registry entry
                    final String entry = "HKEY_CLASSES_ROOT\\" + fileAssociation
                            + "\\DefaultIcon";

                    // III. Query that entry to find key for icon
                    final String anyCharSequence = ".*";
                    final String space = "\\s+";
                    final String type = "REG_SZ";
                    final String iconPath = "\"?(.+\\\\[^\\\\]+\\.ico)";

                    Pattern pattern = Pattern.compile(anyCharSequence + type
                            + space + iconPath + anyCharSequence);

                    Optional<String> optIconEntry = Registry.queryKey(entry)
                            .map(content -> {
                        return content.parallelStream()
                                .filter(s -> pattern.matcher(s).matches())
                                .findFirst();
                    }).orElseGet(() -> Optional.empty());
                    assertTrue(optIconEntry.isPresent(),
                            "[Icon key not found in entry " + entry + "]");

                    // IV. Compare images
                    Matcher macho = pattern.matcher(optIconEntry.get());
                    macho.matches();
                    Path icon = Paths.get(macho.group(1)); // Since we use the
                                                           // same pattern we
                                                           // may be sure that a
                                                           // group
                    assertTrue(
                            Utils.checkFilesEquality(icon, oneOfIcons.toPath()),
                            String.format("Icons should be equal: %s & %s",
                                    icon, oneOfIcons));
                }
            } catch (IOException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    protected VerificationMethod getUserFileAssociationVerificator() {
        return (value, app, appName) -> {

            class Resolver {
                public String getApplicationEntryForExtension(String ext) {
                    final String CLASSES = "HKEY_CURRENT_USER\\Software\\Classes";
                    Optional<List<String>> output = Registry
                            .queryKey(CLASSES + "\\." + ext);

                    final String anyCharSequence = ".*";
                    final String space = "\\s+";
                    final String type = "REG_SZ";
                    final String data = "(\\S+)";
                    Pattern pattern = Pattern
                            .compile(anyCharSequence + type + space + data);

                    Matcher m = output.orElse(Collections.emptyList())
                            .parallelStream().map(s -> pattern.matcher(s))
                            .filter(Matcher::matches).findFirst()
                            .orElseThrow(() -> new RuntimeException(
                                    "[Cannot find application name inside "
                                            + ext + " entry inside " + CLASSES
                                            + "]"));
                    return CLASSES + "\\" + m.group(1);
                }
            }

            try {
                File oneOfIcons = null;
                String extension = null;

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> associations = (List<Map<String, Object>>) value;
                final String CLASSES = "HKEY_CURRENT_USER\\Software\\Classes";
                for (Map<String, Object> association : associations) {
                    // get expected data
                    @SuppressWarnings("unchecked")
                    List<String> extensions = (List<String>) association
                            .get(FA_EXTENSIONS);
                    File icon = (File) association.get(FA_ICON);

                    // check association of extension with our application name
                    for (String ext : extensions) {

                        Registry.QueryBuilder builder = Registry
                                .getQueryBuilder();
                        builder.key(CLASSES).useDataPattern(ext);
                        Registry.Query query = builder.build();
                        Optional<List<String>> output = query.execute();

                        final Predicate<? super String> predicate = str -> str
                                .trim().contains("." + ext);
                        boolean menuItemExists = output
                                .orElse(Collections.emptyList()).stream()
                                .anyMatch(predicate);

                        assertTrue(menuItemExists, "[Extension " + ext
                                + " not found in registry " + CLASSES + " ]");
                    }

                    // check, that application is bounded with it's path
                    String execPath = getInstalledExecutableLocation(app,
                            appName).toString().replaceAll("\\.exe$", "");

                    Registry.QueryBuilder builder = Registry.getQueryBuilder();
                    builder.key(CLASSES + "\\" + appName + "File")
                            .searchSubkeys();
                    Registry.Query query = builder.build();
                    Optional<List<String>> output = query.execute();

                    assertTrue(output.orElse(Collections.emptyList()).stream()
                            .anyMatch(str -> str.contains(execPath)));

                    if (icon != null) {
                        oneOfIcons = icon;
                        // Since the icon will be used for all extensions within
                        // same file association
                        // we may take any.
                        extension = extensions.get(0);
                    }
                    // TODO: description and contentTypes are not checked!!!
                    // see https://bugs.openjdk.java.net/browse/JDK-8093106
                }
                // check icon
                if (oneOfIcons != null) {
                    final String anyCharSequence = ".*";
                    final String space = "\\s+";
                    final String type = "REG_SZ";
                    final String iconPath = "\"?(.+\\\\[^\\\\]+\\.ico)";
                    Pattern pattern = Pattern.compile(anyCharSequence + type
                            + space + iconPath + anyCharSequence);

                    final String iconEntry = new Resolver()
                            .getApplicationEntryForExtension(extension)
                            + "\\DefaultIcon";

                    Optional<String> optIconEntry = Registry.queryKey(iconEntry)
                            .map(content -> {
                        return content.parallelStream()
                                .filter(s -> pattern.matcher(s).matches())
                                .findFirst();
                    }).orElseGet(() -> Optional.empty());
                    assertTrue(optIconEntry.isPresent(),
                            "[Icon key not found in entry " + iconEntry + "]");

                    Matcher macho = pattern.matcher(optIconEntry.get());
                    macho.matches();
                    Path icon = Paths.get(macho.group(1)); // Since we use the
                                                           // same pattern we
                                                           // may be sure that a
                                                           // group
                    assertTrue(
                            Utils.checkFilesEquality(icon, oneOfIcons.toPath()),
                            String.format("Icons should be equal: %s & %s",
                                    icon, oneOfIcons));
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @Override
    public Path getAppCDSCacheFile(AppWrapper app, String appName, ExtensionType extensionType) {
        return Paths.get(System.getenv("APPDATA"), app.getIdentifier(extensionType), "cache",
                appName + ".jpa");
    };

    @Override
    public String[] getRmCommand(Path file) {
        try {
            String whereRm = Utils
                    .runCommand(new String[] { "where", "rm" },
                            CONFIG_INSTANCE.getRunTimeout())
                    .getOutputStream().get(0);
            return new String[] { whereRm, file.toString() };
        } catch (IOException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return new String[] { "C:\\cygwin64\\bin\rm.exe", file.toString() };
    };

    public Path getJavaExecutableBinPathInInstalledApp(AppWrapper appWrapper, String appTitle) {
        return getInstalledAppRootLocation(appWrapper, appTitle).resolve("runtime/bin");
    }

    @Override
    public void checkIfExecutablesAvailableInBinDir(Path binDirPath) throws IOException {
        try (Stream<Path> find = Files.find(binDirPath, 1, (file, attr) -> file
                .toFile().getName().equals("java.exe"))) {
            Optional<Path> result = find.findFirst();
            if (!result.isPresent()) {
                throw new FileNotFoundException("java"
                        + " not found under " + binDirPath );
            }
            LOG.log(Level.INFO, "java.exe found in {0}", binDirPath);
        }
    }

    @Override
    public String getJavaExecutable() {
        return "java.exe";
    }
}
