/*
 * Copyright (c) 2014, 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;
import com.oracle.tools.packager.Bundler;
import com.oracle.tools.packager.ConfigException;
import com.oracle.tools.packager.UnsupportedPlatformException;

/**
 *
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public abstract class BundlingManager implements Constants {

    protected static final Logger LOG = Logger
            .getLogger(BundlingManager.class.getName());

    private boolean installed = false;

    private final AbstractBundlerUtils bundlerUtils;
    protected AppWrapper app;
    protected ExtensionType extensionType;

    /*
     * modulePath is used only by JmodExplodedModuleAndModularJarDependencyTest,
     * couldn't find any other alternative.
     */
    protected Path modulePath;

    public BundlingManager(AbstractBundlerUtils bundlerUtils) {
        this.bundlerUtils = bundlerUtils;
    }

    public abstract boolean validate(Map<String, Object> params)
            throws UnsupportedPlatformException, ConfigException;

    public abstract File execute(Map<String, Object> params, File file)
            throws IOException;

    public abstract File execute(Map<String, Object> params, File file,
            boolean isSrcDirRequired) throws IOException;

    public Path execute(Map<String, Object> params, AppWrapper app)
            throws IOException {
        this.app = app;
        Path bundlesDir = app.getBundlesDir();
        return execute(params, bundlesDir.toFile()).toPath();
    }

    public Path execute(Map<String, Object> params, AppWrapper app,
            boolean isSrcDirRequired) throws IOException {
        this.app = app;
        Path bundlesDir = app.getBundlesDir();
        return execute(params, bundlesDir.toFile(), isSrcDirRequired).toPath();
    }

    public Bundler getBundler() {
        return bundlerUtils.getBundler();
    }

    public void verifyOption(String name, Object value, AppWrapper app2,
            String applicationTitle) {
        bundlerUtils.verifyOption(name, value, app2, applicationTitle);
    }

    public Path getInstalledAppRootLocation(AppWrapper app,
            String applicationTitle) {
        return bundlerUtils.getInstalledAppRootLocation(app, applicationTitle);
    }

    public ProcessOutput runInstalledExecutable(AppWrapper app,
            String applicationTitle) throws IOException, ExecutionException {
        return bundlerUtils.runInstalledExecutable(app, applicationTitle);
    }

    public Path getInstalledExecutableLocation(AppWrapper app,
            String applicationTitle) {
        return bundlerUtils.getInstalledExecutableLocation(app,
                applicationTitle);
    }

    /**
     * install application
     *
     * @param app
     *            application to install
     * @param applicationTitle
     *            application title
     * @return executable path
     * @throws java.io.IOException
     */
    public String install(AppWrapper app, String applicationTitle,
            boolean manual) throws IOException {
        try {
            if (!manual) {
                return bundlerUtils.install(app, applicationTitle);
            } else {
                bundlerUtils.manualInstall(app);
                return null;
            }
        } finally {
            installed = true;
        }
    }

    /**
     * uninstall application
     *
     * @param app
     *            application to uninstall
     * @param applicationTitle
     *            application title
     * @throws java.io.IOException
     */
    public void uninstall(AppWrapper app, String applicationTitle)
            throws IOException {
        if (installed) {
            bundlerUtils.uninstall(app, applicationTitle);
            installed = false;
        }
    }

    public String getAppName(Map<String, Object> params) {
        return bundlerUtils.getAppName(params);
    }

    public abstract String getShortName();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (Objects.nonNull(getShortName())) {
            stringBuilder.append(getShortName());
        }

        if (Objects.nonNull(getExtensionType())) {
            stringBuilder.append("-");
            stringBuilder.append(getExtensionType());
        } else if (Objects.nonNull(modulePath)) {
            stringBuilder.append("-");
            stringBuilder.append(modulePath.getFileName());
        }

        if (Objects.nonNull(getBundler().getID())) {
            stringBuilder.append("-");
            stringBuilder.append(getBundler().getID().replace('.', '_'));
        }
        return stringBuilder.toString();
    }

    public Path getAppCDSCacheFile(AppWrapper app, String appName) {
        return bundlerUtils.getAppCDSCacheFile(app, appName, extensionType);
    }

    public AbstractBundlerUtils getBundlerUtils() {
        return bundlerUtils;
    }

    public ExtensionType getExtensionType() {
        return this.extensionType;
    }

    public void setExtensionType(ExtensionType extensionType) {
        this.extensionType = extensionType;
    }

    public Path getModulePath() {
        return this.modulePath;
    }

    public void setModulePath(Path modulePath) {
        this.modulePath = modulePath;
    }
}
