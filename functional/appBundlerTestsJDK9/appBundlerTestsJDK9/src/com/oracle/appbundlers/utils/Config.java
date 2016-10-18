/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Utils.presentInPath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public enum Config {
    CONFIG_INSTANCE;

    private final Logger LOG = Logger.getLogger(Config.class.getName());

    public static final String LICENSE_FILE_NAME = "License.lic";
    public static final String OPTION_PREFIX = "sqe";

    private final Properties properties = new Properties();
    private boolean manualOnly = false;
    private Set<BundlerUtils> acceptedInstallationPackagerType = new HashSet<>();
    private Set<BundlingManagers> acceptedPackagerInterface = new HashSet<>();
    private Set<ExtensionType> javaExtensionType = new HashSet<>();

    private Config() {
        tryLoadTestSuiteProperties();
        defineTestMode();
    }

    private void tryLoadTestSuiteProperties() throws RuntimeException {
        String javahome = System.getenv("JAVA_HOME");
        if (javahome != null) {
            properties.setProperty("java.home",
                    Paths.get(javahome).toAbsolutePath().toString());
            properties.setProperty("other.jre",
                    Paths.get(javahome, "jre").toAbsolutePath().toString());
            properties.setProperty("ant.javafx",
                    Paths.get(javahome, "lib", "ant-javafx.jar")
                            .toAbsolutePath().toString());
        }
        if (System.getenv("ANT_HOME") != null) {
            properties.setProperty("ant.home", System.getenv("ANT_HOME"));
        }
        properties.setProperty("run.timeout.ms", "60000");
        properties.setProperty("install.timeout.ms", "180000");
        properties.setProperty("after.install.pause.ms", "60000");
        try (InputStream resource = Config.class.getResourceAsStream(
                "/com/oracle/appbundlers/utils/resources/testrun.properties")) {
            properties.load(resource);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Can't load config: {0}.", ex);
        }
        String props = "";
        for (Map.Entry<Object, Object> kv : properties.entrySet()) {
            props += String.format("%s = %s\n", kv.getKey(), kv.getValue());
        }
        props += "JAVAFX_ANT_DEBUG is = " + System.getenv("JAVAFX_ANT_DEBUG");
        LOG.info("Loaded properties: ");
        LOG.info(props);
        if (properties.getProperty("java.home") == null
                || properties.get("ant.home") == null) {
            throw new RuntimeException(
                    "JAVA_HOME or ANT_HOME are not defined.");
        }
    }

    private void defineTestMode() {
        if ("single-method".equals(System.getProperty("test-run-mode"))) {
            tryDefineInstallationPackageType();
            tryDefinePackagerInterface();
        }
        tryFilterManual();
        isJavaExtensionTypeDefined();
    }

    private void tryFilterManual() {
        try {
            String value = System.getProperty("manual-only");
            value = value.toUpperCase();
            if (value.equalsIgnoreCase("true")) {
                manualOnly = true;
                System.out.println("Running only manual tests");
            }
        } catch (Throwable t) {
            System.out.println("Running only automated tests");
        }
    }

    private void tryDefineInstallationPackageType() {
        try {
            String value = System.getProperty("installation-package-type");
            value = value.toUpperCase();
            StringTokenizer stringTokenizer = new StringTokenizer(value, ",");
            while (stringTokenizer.hasMoreElements()) {
                String eachInstallationType = ((String) stringTokenizer.nextElement()).trim();
                BundlerUtils bundlerUtil = getEnumInstance(
                        BundlerUtils.class, eachInstallationType);
                acceptedInstallationPackagerType.add(bundlerUtil);
            }
            System.out.println("[Installation package type filter: "
                    + acceptedInstallationPackagerType + "]");
        } catch (Throwable t) {
            System.out
                    .println("[Installation package types won't be filtered.]");
        }
    }

    private void tryDefinePackagerInterface() {
        try {
            String value = System.getProperty("packager-interface");
            value = value.toUpperCase();
            StringTokenizer stringTokenizer = new StringTokenizer(value, ",");
            while (stringTokenizer.hasMoreElements()) {
                String eachBundlingMgr = ((String) stringTokenizer
                        .nextElement()).trim();
                BundlingManagers bundlingMgr = getEnumInstance(
                        BundlingManagers.class, eachBundlingMgr);
                acceptedPackagerInterface.add(bundlingMgr);
                System.out.println("[Packager interface filter: "
                        + acceptedPackagerInterface + "]");
            }
        } catch (Throwable t) {
            System.out.println("[Packager interfaces won't be filtered]");
        }
    }

    private void isJavaExtensionTypeDefined() {
        try {
            String javaExtensionString = System
                    .getProperty("java-extension-type");
            if (!javaExtensionString.trim().equals("")) {
                StringTokenizer stringTokenizer = new StringTokenizer(
                        javaExtensionString, ",");
                while (stringTokenizer.hasMoreElements()) {
                    String eachJavaExtension = ((String) stringTokenizer
                            .nextElement()).trim();
                    ExtensionType extensionType = getEnumInstance(
                            ExtensionType.class, eachJavaExtension);
                    javaExtensionType.add(extensionType);
                    System.out.println("[Filtered java extension type: "
                            + javaExtensionString + "]");
                }
            } else {
                System.out.println("[Java Extension Type won't be Filtered]");
            }
        } catch (Exception e) {
            System.out.println("[Java Extension Type won't be Filtered]");
        }
    }

    private static <T extends Enum<T>> T getEnumInstance(Class<T> type,
            String value) {
        return T.valueOf(type, value);
    }

    public Path getResourcePath() {
        String dir = Config.class
                .getResource("/com/oracle/appbundlers/utils/resources")
                .getFile();
        return new File(Utils.isWindows() ? dir.substring(1) : dir).toPath();
    }

    public Path getResourceFilePath(String fileName) {
        return getResourcePath().resolve(fileName);
    }

    public String getResourceDir() {
        return getResourcePath().toFile().getAbsolutePath();
    }

    public String getResource(String fileName) {
        return getResourceDir() + File.separator + fileName;
    }

    public String getJavaHome() {
        return properties.getProperty("java.home");
    }

    public String getAntHome() {
        return properties.getProperty("ant.home");
    }

    public String antExec() {
        String exec = Utils.isWindows() ? "ant.bat" : "ant";
        if (presentInPath(exec)) {
            return exec;
        }
        return getAntHome() + File.separator + "bin" + File.separator + exec;
    }

    public String javafxpackager() {
        return getJavaHome() + File.separator + "bin" + File.separator
                + "javapackager";
    }

    public String getOtherJre() {
        return properties.getProperty("other.jre");
    }

    public String getAntJavaFx() {
        return properties.getProperty("ant.javafx");
    }

    /**
     * Returns the application installation timeout. All apps are expected to be
     * installed within this time.
     *
     * @return timeout in milliseconds
     */
    public int getInstallTimeout() {
        return Integer.valueOf(properties.getProperty("install.timeout.ms"));
    }

    /**
     * Returns the application running timeout. All apps are expected finish
     * execution within this time.
     *
     * @return timeout in milliseconds
     */
    public int getRunTimeout() {
        return Integer.valueOf(properties.getProperty("run.timeout.ms"));
    }

    public int getAfterInstallationPause() {
        return Integer
                .valueOf(properties.getProperty("after.install.pause.ms"));
    }

    public Set<BundlerUtils> getAcceptedInstallationPackageType() {
        return this.acceptedInstallationPackagerType;
    }

    public Set<BundlingManagers> getAcceptedPackagerApi() {
        return this.acceptedPackagerInterface;
    }

    public Set<ExtensionType> getAcceptedJavaExtensionType() {
        return this.javaExtensionType;
    }

    public boolean isNoCleanSet() {
        String nocleanProperty = System.getProperty("noclean");
        if(nocleanProperty == null) {
            return false;
        }
        return nocleanProperty.equalsIgnoreCase("true");
    }

    public boolean manualOnly() {
        return manualOnly;
    }
}
