/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;
import com.oracle.tools.packager.ConfigException;
import com.oracle.tools.packager.RelativeFileSet;
import com.oracle.tools.packager.UnsupportedPlatformException;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

import javafx.util.Pair;

/**
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public class ConsoleBundlingManager extends BundlingManager {

    /**
     * This key is used for providing options directly to command-line without
     * checking and formatting. This key is provided to support negative testing
     * where we need to provide intetionally incorrect values. E.g.
     * <em>{@literal -BuserJvmOptions=-Xmx=1g}</em> is correct and could be
     * provided via bundling manager API but
     * <em>{@literal -BuserJvmOptions=-Xmx1g}</em> is incorrect and API won't
     * put in on a command line thus the solution is to use the
     * {@code RAW_OPTIONS} key:
     * <p>
     * {@code parameters.put(RAW_OPTIONS, Arrays.asList("-BuserJvmOptions=-Xmx1g")}
     */
    public static final String RAW_OPTIONS = "RAW";

    @SuppressWarnings("serial")
    private final static Map<String, String> toConsoleFlag = new HashMap<String, String>() {
        {
            put(APPLICATION_CLASS, "-appclass");
            put(APP_NAME, "-" + APP_NAME);
            put("vendor", "-vendor");
            put("title", "-title");
            put(DESCRIPTION, "-" + DESCRIPTION);
            put("Description", "-description");
            put(INSTALLDIR_CHOOSER, "-" + INSTALLDIR_CHOOSER);
            put("App Name", "-name");
            put("Title", "-title");
            put(VENDOR, "-vendor");
            put(SERVICE_HINT, "-" + SERVICE_HINT);

            /*
             * JDK 9 CLI GNU style parameters
             */
            put(ADD_MODS, DOUBLE_HYPHEN + ADD_MODS);
            put(LIMIT_MODS, DOUBLE_HYPHEN + LIMIT_MODS);
            put(MODULEPATH, DOUBLE_HYPHEN + MODULEPATH);
            put(MAIN_MODULE, DOUBLE_HYPHEN + MAIN_MODULE);
            put(STRIP_NATIVE_COMMANDS, DOUBLE_HYPHEN + STRIP_NATIVE_COMMANDS);


            List<String> list = Arrays.asList(LICENSE_FILE, IDENTIFIER, VERSION,
                    ICON, EMAIL, COPYRIGHT, LICENSE_TYPE, CATEGORY,
                    SHORTCUT_HINT, MENU_HINT, SYSTEM_WIDE, JVM_OPTIONS,
                    JVM_PROPERTIES, USER_JVM_OPTIONS,
                    // SERVICE_HINT,
                    PREFERENCES_ID, MAIN_JAR, CLASSPATH,
                    BundleParams.PARAM_RUNTIME, MAC_APP_STORE_APP_SIGNING_KEY,
                    MAC_APP_STORE_ENTITLEMENTS, MAC_APP_STORE_PKG_SIGNING_KEY,
                    MAC_CATEGORY, MAC_CF_BUNDLE_NAME, SIGNING_KEY_USER,
                    LinuxDebBundler_BUNDLE_NAME, MAINTAINER,
                    LinuxRpmBundler_BUNDLE_NAME, MENU_GROUP, EXE_SYSTEM_WIDE,
                    MSI_SYSTEM_WIDE, UPGRADE_UUID, RUN_AT_STARTUP,
                    START_ON_INSTALL, CATEGORY, SIMPLE_DMG, ARGUMENTS,
                    UNLOCK_COMMERCIAL_FEATURES, ENABLE_APP_CDS,
                    APP_CDS_CACHE_MODE, APP_CDS_CLASS_ROOTS, SIGNING_KEYCHAIN);
            list.forEach(el -> put(el, "-B" + el));
        }
    };

    public ConsoleBundlingManager(AbstractBundlerUtils bundler) {
        super(bundler);
    }

    @Override
    public boolean validate(Map<String, Object> params)
            throws UnsupportedPlatformException, ConfigException {
        return true;
    }

    @Override
    public File execute(Map<String, Object> params, File file)
            throws IOException {
       return execute(params, file, false);
    }

    @Override
    public File execute(Map<String, Object> params, File file,
            boolean isSrcDirRequired) throws IOException {
        try {
            List<String> command = command(file, toConsole(params));
            System.out.println("execution command is " + command);
            ProcessOutput process = Utils.runCommand(command,
                    CONFIG_INSTANCE.getInstallTimeout());
            if (process.exitCode() != 0) {
                throw new IOException(
                        "Process finished with not zero exit code");
            }
            return file;
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }

    private List<String> command(File file,
            List<Pair<String, Collection<String>>> toConsole) {
        List<String> command = new ArrayList<>();
        String bundlerType = getBundler().getBundleType();
        if (!file.getName().equals("bundles")) {
            throw new IllegalArgumentException(
                    "Invalid bundle directory : " + file);
        }
        command.addAll(Arrays.asList(
                CONFIG_INSTANCE
                        .javafxpackager()  ,
                "-deploy", "-verbose", "-outdir", file.toString(),
                // mandatory option
                "-outfile", "test", "-native",
                "image".equalsIgnoreCase(bundlerType) ? "image"
                        : getBundler().getID()));
        for (Pair<String, Collection<String>> entry : toConsole) {
            String key = entry.getKey();
            Collection<String> value = entry.getValue();
            if (key.startsWith("-B")) {
                command.add(key + "=" + value.stream().collect(joining(" ")));
            } else if (RAW_OPTIONS.equals(key)) {
                value.stream().forEach(option -> command.add(option));
            } else if ((DOUBLE_HYPHEN + STRIP_NATIVE_COMMANDS).equals(key)) {
                command.add(key);
                command.add(value.iterator().next());
            } else {
                command.add(key);
                command.add(
                        value.stream().collect(joining(File.pathSeparator)));
            }
        }
        return command;
    }

    @SuppressWarnings("unchecked")
    private List<Pair<String, Collection<String>>> toConsole(
            Map<String, Object> params) {
        List<Pair<String, Collection<String>>> key2Value = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            RelativeFileSet fileSet;
            switch (key) {
            case "appResources":
                if(ExtensionType.NormalJar == extensionType) {
                    fileSet = (RelativeFileSet) value;
                    String path = fileSet.getBaseDirectory().getPath();
                    key2Value.add(new Pair<>("-srcdir", Arrays.asList(path)));
                    key2Value.add(
                            new Pair<>("-srcfiles", fileSet.getIncludedFiles()));
                }
                break;
            case "jvmOptions":
                Collection<String> col = (Collection<String>) value;
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        Arrays.asList(col.stream().collect(joining(" ")))));
                break;
            case "userJvmOptions":
                key2Value.addAll(separateOptions(getMappedKeyAndCheck(key),
                        (Map<String, String>) value));
                break;
            case "jvmProperties":
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        collectOptions((Map<String, String>) value)));
                break;
            case "runtime":
                fileSet = (RelativeFileSet) value;
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key), Arrays
                        .asList(fileSet.getBaseDirectory().getAbsolutePath())));
                break;
            case "licenseFile":
                String file = (String) value;
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        Arrays.asList(file)));
                break;
            case "installdirChooser":
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        new ArrayList<>(0)));
                break;
            case "mainJar":
                // Use relative references
//                fileSet = (RelativeFileSet) value;
                String jar = (String) value;
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        Arrays.asList(jar)));
                break;
            case MODULEPATH:
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        Arrays.asList((String) value)));
                break;
            case RAW_OPTIONS:
                key2Value.add(new Pair<>(RAW_OPTIONS, (List<String>) value));
                break;
            case "fxPackaging": // do nothing
                break;
            case SERVICE_HINT:
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        new ArrayList<>(0)));
                break;
            case "appResourcesList":
                break;
            default:
                key2Value.add(new Pair<>(getMappedKeyAndCheck(key),
                        Arrays.asList(value.toString())));
            }
        }
        return key2Value;
    }

    private List<Pair<String, Collection<String>>> separateOptions(String key,
            Map<String, String> value) {
        List<Pair<String, Collection<String>>> result = new ArrayList<>();
        value.entrySet().stream().map((entry) -> {
            if (entry.getValue().isEmpty()) {
                return format("%s", entry.getKey());
            }
            return format("%s=%s", entry.getKey(), entry.getValue());
        }).forEach(str -> result.add(new Pair<>(key, Arrays.asList(str))));
        return result;
    }

    private List<String> glueOptions(Map<String, String> options) {
        return Arrays.asList(
                options.entrySet().stream().map(e -> e.getKey() + e.getValue())
                        .collect(joining("\n", "\"", "\"")));
    }

    private List<String> collectOptions(Map<String, String> options) {
        return Arrays.asList(options.entrySet().stream()
                .map(e -> format("%s=%s", e.getKey(), e.getValue()))
                .collect(joining("\n", "\"", "\"")));
    }

    private String getMappedKeyAndCheck(String key) {
        String result = toConsoleFlag.get(key);
        if (result == null) {
            throw new IllegalArgumentException("Can not map : " + key);
        }
        return result;
    }

    @Override
    public String getShortName() {
        return "CLI";
    }
}
