/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.CHECK_MODULE_IN_JAVA_EXECUTABLE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.parameters.GenericModuleParameters;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.JavaExtensionTypeFilter;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.RelativeFileSet;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

/**
 *  Unnamed Module + JRE + 3rd party modules -srcfiles hello.world.jar -appClass
 *  HelloWorld -BmainJar=hello.world.jar -addmods 3rd.party -modulepath
 *  <path to 3rd party JARs>
 *  @author Ramesh BG
 */
public class UnNamedModuleDependsOn3rdPartyModulesTest
        extends TestBase {

    protected AppWrapper getApp() throws IOException {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(PRINTLN_STATEMENT, CUSTOM_UTIL_PRINTLN_STATEMENT);
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                SourceFactory.get_custom_util_module(),
                SourceFactory.get_com_greetings_app_unnamed_module(
                        hashMap));
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(OUTPUT_CONTAINS, HELLO_WORLD_OUTPUT);
            hashMap.put(CHECK_MODULE_IN_JAVA_EXECUTABLE,
                    CUSTOM_UTIL_MODULE_NAME);
            return hashMap;
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put(ADD_MODS,
                    this.currentParameter.getApp().getAllModuleNamesSeparatedByComma());
            hashMap.put(STRIP_NATIVE_COMMANDS, false);
            return hashMap;
        };
    }

    @Override
    protected void prepareTestEnvironment() throws Exception {
        for (ExtensionType javaExtensionFormat : getExtensionArray()) {
            this.currentParameter = this.intermediateToParametersMap
                    .get(javaExtensionFormat);
            overrideParameters(javaExtensionFormat);
            initializeAndPrepareApp();
        }
    }

    @Override
    public void overrideParameters(ExtensionType javaExtensionFormat)
            throws IOException {
        this.currentParameter.setApp(getApp());
        this.currentParameter.setBasicParams(getBasicParams());
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    @Override
    protected void prepareApp(AppWrapper app, ExtensionType extension)
            throws IOException, ExecutionException {
        app.preinstallApp(
                new ExtensionType[] { extension, ExtensionType.NormalJar });
        app.writeSourcesToAppDirectory();
        app.compileAndCreateJavaExtensionType(
                new String[] { DOUBLE_HYPHEN + ADD_EXPORTS,
                        "custom.util/testapp.util=ALL-UNNAMED", DOUBLE_HYPHEN + ADD_MODS, CUSTOM_UTIL_MODULE_NAME},
                extension);
    }

    @Override
    protected void initializeAndPrepareApp() throws Exception {
        prepareApp(this.currentParameter.getApp(),
                this.currentParameter.getExtension());
    }

    public BasicParams getBasicParams() throws IOException {
        /*
         * there is no main module in this test since unnamed module depending
         * on named module
         */
        return (AppWrapper app) -> {
            Map<String, Object> basicParams = new HashMap<String, Object>();
            basicParams.put(BundleParams.PARAM_APP_RESOURCES,
                    new RelativeFileSet(
                            this.currentParameter.getApp().getJarDir().toFile(),
                            app.getJarFilesList().stream().map(Path::toFile)
                                    .collect(toSet())));
            basicParams.put(APPLICATION_CLASS,
                    this.currentParameter.getApp().getMainClass());
            basicParams.put(CLASSPATH,
                    this.currentParameter.getApp().getJarFilesList().stream()
                            .map(Path::getFileName).map(Path::toString)
                            .collect(Collectors.joining(File.pathSeparator)));
            basicParams.put(MAIN_JAR,
                    this.currentParameter.getApp().getMainJarFile().toFile().getName());
            basicParams.put(MODULEPATH, String.join(File.pathSeparator,
                    JMODS_PATH_IN_JDK, ((GenericModuleParameters) this.currentParameter).getModulePath()));
            return basicParams;
        };
    }

    @Override
    public ExtensionType[] getExtensionArray() {
        return Stream.of(ExtensionType.getModuleTypes())
                .filter(JavaExtensionTypeFilter::accept).collect(toList())
                .toArray(new ExtensionType[0]);
    }

    @Override
    protected void executeJavaPackager(BundlingManager bundlingManager,
            Map<String, Object> allParams, ExtensionType extension) throws IOException {
        bundlingManager.execute(allParams, this.currentParameter.getApp() ,true);
    }
}

