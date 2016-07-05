/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

/* @test

@summary Testcases that show different possibilities of resource bundling.

@library
/appBundlerTests/lib/ant-javafx.jar
/appBundlerTests/src
/appBundlerTests/test

@build
BundlerTest
com.oracle.appbundlers.utils.AppWrapper
com.oracle.appbundlers.utils.Utils
com.oracle.appbundlers.utils.Config
com.oracle.appbundlers.utils.VerificationUtil
com.oracle.appbundlers.utils.installers.Installer

@run testng com.oracle.appbundlers.tests.functionality.TestBase
*/
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.Utils.isLinux;
import static com.oracle.appbundlers.utils.Utils.isWindows;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.BundlerProvider;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.tests.functionality.parameters.ExplodedModuleParameters;
import com.oracle.appbundlers.tests.functionality.parameters.JmodParameters;
import com.oracle.appbundlers.tests.functionality.parameters.ModularJarParameters;
import com.oracle.appbundlers.tests.functionality.parameters.NormalJarParameters;
import com.oracle.appbundlers.tests.functionality.parameters.Parameters;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.Constants;
import com.oracle.appbundlers.utils.PackageTypeFilter;
import com.oracle.appbundlers.utils.PackagerApiFilter;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;
import com.oracle.tools.packager.Bundler;
import com.oracle.tools.packager.ConfigException;
import com.oracle.tools.packager.Log;

import javafx.util.Pair;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */
public abstract class TestBase implements Constants {

    protected BundlingManager bundlingManager;
    protected Parameters currentParameter;
    protected Map<ExtensionType, Parameters> intermediateToParametersMap = new HashMap<ExtensionType, Parameters>() {
        /**
         * serial version UID
         */
        private static final long serialVersionUID = -9110787670838081437L;

        {
            put(ExtensionType.NormalJar, new NormalJarParameters());
        /*    put(ExtensionType.ModularJar, new ModularJarParameters());
            put(ExtensionType.ExplodedModules, new ExplodedModuleParameters());
            put(ExtensionType.Jmods, new JmodParameters());*/
        }
    };

    /*
     * Functional Interface References
     */
    protected AdditionalParams additionalParams;
    protected BasicParams basicParams;
    protected VerifiedOptions verifiedOptions;

    private static final Logger LOG = Logger
            .getLogger(TestBase.class.getName());

    protected Method testMethod = null;

    // method block: should be overridden in some tests
    // all these implementations are just "default-values"
    protected BundlerUtils[] getBundlerUtils() {
//        return BundlerUtils.values();
        return new BundlerUtils[] { BundlerUtils.EXE};
    }

    protected BundlingManagers[] getBundlingManagers() {
//        return BundlingManagers.values();
        return new BundlingManagers[] { BundlingManagers.CLI};
    }

    /**
     * return App Name
     */
    public String getResultingAppName() {
        return this.getClass().getSimpleName();
    }

    protected boolean isConfigExceptionExpected(Bundler bundler) {
        return false;
    }

    protected void prepareApp(final AppWrapper app)
            throws IOException, ExecutionException {
        app.preinstallApp();
        app.writeSourcesToAppDirectory();
        app.compileApp();
        app.jarApp();
        app.createJmod();
    }

    @BeforeClass
    public void setupApplication() throws Exception {
        Log.setLogger(new Log.Logger(true));
        prepareTestEnvironment();
        customBeforeClassHook();
    }

    public void customBeforeClassHook() throws Exception {
    }

    protected void prepareTestEnvironment() throws Exception {
        for (ExtensionType intermediate : ExtensionType.values()) {
            this.currentParameter = this.intermediateToParametersMap
                    .get(intermediate);
            overrideParameters(intermediate);
            initializeAndPrepareApp();
        }
    }

    private void initializeAndPrepareApp()
            throws Exception {
        if (this.currentParameter.getApp() == null) {
            this.currentParameter.initializeDefaultApp();
        }
        prepareApp(this.currentParameter.getApp());
    }

    @BeforeMethod
    public void saveTestMethod(Method method) throws IOException {
        testMethod = method;
    }

    @Test(dataProvider = "getBundlers")
    public void runTest(BundlingManager bundlingManager) throws Exception {
        for (ExtensionType intermediate : ExtensionType.values()) {
            this.currentParameter = intermediateToParametersMap
                    .get(intermediate);

            if (!isTestCaseApplicableForExtensionType(intermediate)) {
                continue;
            }

            Map<String, Object> allParams = getAllParams(intermediate);
            String testName = this.getClass().getName() + "::"
                    + testMethod.getName() + "$" + bundlingManager.toString();
            this.bundlingManager = bundlingManager;
            LOG.log(Level.INFO, "Starting test \"{0}\".", testName);
            try {
                validate();
                if (isConfigExceptionExpected(bundlingManager.getBundler())) {
                    Assert.fail(
                            "ConfigException is expected, but isn't thrown");
                }
            } catch (ConfigException ex) {
                if (isConfigExceptionExpected(bundlingManager.getBundler())) {
                    return;
                } else {
                    LOG.log(Level.SEVERE, "Configuration error: {0}.",
                            new Object[] { ex });
                    throw ex;
                }
            }

            try {
                bundlingManager.execute(allParams,
                        this.currentParameter.getApp());
                String path = bundlingManager.install(
                        this.currentParameter.getApp(), getResultingAppName(),
                        false);
                LOG.log(Level.INFO, "Installed at: {0}", path);

                Pair<TimeUnit, Integer> tuple = getDelayAfterInstall();
                tuple.getKey().sleep(tuple.getValue());
                AppWrapper app2 = this.currentParameter.getApp();
                this.currentParameter.getVerifiedOptions().forEach(
                        (name, value) -> bundlingManager.verifyOption(name,
                                value, app2, getResultingAppName()));
            } finally {
                uninstallApp(intermediate);
                LOG.log(Level.INFO, "Finished test: {0}", testName);
            }
        }
    }

    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType extension) {
        return true;
    }

    private void uninstallApp(ExtensionType intermediate) throws Exception {
        if (bundlingManager != null) {
            String appName = bundlingManager
                    .getAppName(getAllParams(intermediate));
            bundlingManager.uninstall(this.currentParameter.getApp(), appName);
        }
    }

    @AfterClass
    protected void cleanUp() throws IOException {
        try {
            LOG.log(Level.INFO, "Removing temporary files: ");
        } finally {
            for (Parameters parameters : intermediateToParametersMap.values()) {
                Utils.tryRemoveRecursive(parameters.getApp().getWorkDir());
            }
        }
    }

    public void overrideParameters(ExtensionType intermediate)
            throws Exception {

    }

    @DataProvider(name = "getBundlers")
    public Iterator<Object[]> getBundlers() {

        List<BundlingManagers> packagerInterfaces = Stream
                .of(getBundlingManagers()).filter(PackagerApiFilter::accept)
                .collect(Collectors.toList());

        final List<AbstractBundlerUtils> installationPackageTypes = Stream
                .of(getBundlerUtils()).filter(BundlerUtils::isSupported)
                .filter(PackageTypeFilter::accept)
                .map(BundlerUtils::getBundlerUtils).collect(toList());

        return BundlerProvider.createBundlingManagers(installationPackageTypes,
                packagerInterfaces);
    }

    public boolean mustBeSupported(String bundlerId) {
        if (isLinux()) {
            return bundlerId.equals("linux.app") || bundlerId.equals("deb")
                    || bundlerId.equals("rpm");
        } else if (isWindows()) {
            return bundlerId.equals("windows.app") || bundlerId.equals("exe")
                    || bundlerId.equals("msi");
        } else {
            return bundlerId.equals("mac.app")
                    || bundlerId.equals("mac.appStore")
                    || bundlerId.equals("dmg");
        }
    }

    protected Pair<TimeUnit, Integer> getDelayAfterInstall() {
        return new Pair<TimeUnit, Integer>(TimeUnit.MILLISECONDS, 100);
    }

    protected Map<String, Object> getAllParams(ExtensionType intermediate)
            throws Exception {
        Map<String, Object> basicParams = this.currentParameter
                .getBasicParams();
        Map<String, Object> allParams = new HashMap<String, Object>();
        allParams.put(APP_NAME, getResultingAppName());
        allParams.putAll(basicParams);
        allParams.putAll(this.currentParameter.getAdditionalParams());
        return allParams;
    }

    public void validate() throws Exception {
        bundlingManager.validate(this.currentParameter.getBasicParams());
    }

    public Parameters getParameters() {
        return this.currentParameter;
    }
}
