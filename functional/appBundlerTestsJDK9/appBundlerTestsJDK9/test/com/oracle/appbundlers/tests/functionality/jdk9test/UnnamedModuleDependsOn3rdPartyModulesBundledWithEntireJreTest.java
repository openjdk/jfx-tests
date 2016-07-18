/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.CHECK_MODULE_IN_JAVA_EXECUTABLE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.parameters.GenericModuleParameters;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.RelativeFileSet;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

/**
 * @author Ramesh BG Example 3 in chris list Example 3: Unnamed Module + Entire
 *         JRE + 3rd party modules -srcfiles hello.world.jar -appClass
 *         HelloWorld -BmainJar=hello.world.jar -addmods 3rd.party -modulepath
 *         <path to 3rd party JARs>
 */
public class UnnamedModuleDependsOn3rdPartyModulesBundledWithEntireJreTest
        extends TestBase {

    protected AppWrapper getApp() throws IOException {
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                "-XaddExports:custom.util/testapp.util=ALL-UNNAMED",
                SourceFactory.get_custom_util_module(),
                SourceFactory.get_com_greetings_app_unnamed_module(
                        new HashMap<String, String>() {
                            private static final long serialVersionUID = 2076100253408663958L;

                            {
                                put(PRINTLN_STATEMENT,
                                        CUSTOM_UTIL_PRINTLN_STATEMENT);
                            }
                        }));
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
                    this.currentParameter.getApp().addAllModules());
            return hashMap;
        };
    }

    @Override
    protected void prepareTestEnvironment() throws Exception {
        for (ExtensionType intermediate : ExtensionType.getModuleTypes()) {
            this.currentParameter = this.intermediateToParametersMap
                    .get(intermediate);
            overrideParameters(intermediate);
            initializeAndPrepareApp();
        }
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
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
        app.compileAndCreateExtensionEndProduct(extension);
    }

    @Override
    protected void initializeAndPrepareApp() throws Exception {
        prepareApp(this.currentParameter.getApp(),
                this.currentParameter.getExtension());
    }

    public BasicParams getBasicParams() throws IOException {
        /*
         * there is no main module in this test since unnamed module depending
         * on named module via -XaddExports
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
            basicParams.put(MODULEPATH, String.join(File.pathSeparator,
                    JMODS_PATH_IN_JDK, ((GenericModuleParameters) this.currentParameter).getModulePath()));
            return basicParams;
        };
    }

    @Override
    public ExtensionType[] getExtensionArray() {
        return ExtensionType.getModuleTypes();
    }
}
