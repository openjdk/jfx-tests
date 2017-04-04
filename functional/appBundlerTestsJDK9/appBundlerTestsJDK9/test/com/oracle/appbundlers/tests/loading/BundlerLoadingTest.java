package com.oracle.appbundlers.tests.loading;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.Config;
import com.oracle.appbundlers.utils.Constants;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.Bundler;
import com.oracle.tools.packager.Bundlers;
import java.lang.module.*;
import java.lang.reflect.*;

import javafx.util.Pair;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class BundlerLoadingTest implements Constants {

    private static final String TESTAPP_EXAMPLEBUNDLERS_MODULE_NAME = "testapp.examplebundlers";
    private static final String TESTAPP_EXAMPLEBUNDLER_MODULE_NAME = "testapp.examplebundler";
    private static final String COM_ORACLE_TOOLS_PACKAGER_BUNDLERS = "com.oracle.tools.packager.Bundlers";
    private static final String COM_ORACLE_TOOLS_PACKAGER_BUNDLER = "com.oracle.tools.packager.Bundler";
    public static final String examplePackage = "testapp";
    public static final String exampleBundlerName = "ExampleBundler";
    public static final String exampleBundlerFullName = examplePackage + "."
            + exampleBundlerName;
    public static final String exampleBundlersName = "ExampleBundlers";
    public static final String exampleBundlersFullName = examplePackage + "."
            + exampleBundlersName;
    private AppWrapper app;

    protected static final Logger LOG = Logger
            .getLogger(BundlerLoadingTest.class.getName());


    private void prepareApp(ExtensionType extension,
            List<Pair<String, String>> services)
                    throws IOException, ExecutionException {
        app.preinstallApp(extension);
        app.writeSourcesToAppDirectory();
        app.compileApp(extension);
        app.jarApp(extension, services);
    }

    private AppWrapper createAndPrepareNewAppForExampleBundlerWithService(
            ExtensionType extension, List<Pair<String, String>> services)
                    throws IOException, ExecutionException {
        app = getExampleBundlerApp(extension);
        prepareApp(extension, services);
        return app;
    }

    private AppWrapper createAndPrepareNewAppForExampleBundlersWithService(
            ExtensionType extension, List<Pair<String, String>> services)
                    throws IOException, ExecutionException {
        app = getExampleBundlersApp(extension);
        prepareApp(extension, services);
        return app;
    }

    private AppWrapper getExampleBundlerApp(ExtensionType extension)
            throws IOException, ExecutionException {
        switch (extension) {
        default:
            return new AppWrapper(Utils.getTempSubDir("testBundlerLoading"),
                    exampleBundlerFullName,
                    new Source(exampleBundlerFullName, exampleBundlerName + ".java.template",
                            exampleBundlerFullName,
                            Collections.emptyMap()));
        case ExplodedModules:
        case Jmods:
        case ModularJar:
            Map<String, String> classToTemplateMap = new HashMap<String, String>();
            classToTemplateMap.put(TESTAPP_EXAMPLE_BUNDLER,
                    EXAMPLE_BUNDLER_TEMPLATE);

            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    TESTAPP_EXAMPLE_BUNDLER,
                    new Source(TESTAPP_EXAMPLEBUNDLER_MODULE_NAME,
                            "testapp.examplebundler.module.info.template",
                            classToTemplateMap, TESTAPP_EXAMPLE_BUNDLER,
                            exampleBundlerName, Collections.emptyMap(), true));
        }
    }

    private AppWrapper getExampleBundlersApp(ExtensionType extension)
            throws IOException, ExecutionException {
        switch (extension) {
        default:
            return new AppWrapper(Utils.getTempSubDir("testBundlerLoading"),
                    exampleBundlersFullName,
                    new Source(exampleBundlersFullName, exampleBundlersName + ".java.template",
                            exampleBundlersName, Collections.emptyMap()));
        case ExplodedModules:
        case Jmods:
        case ModularJar:
            Map<String, String> classToTemplateMap = new HashMap<String, String>();
            classToTemplateMap.put(TESTAPP_EXAMPLE_BUNDLERS,
                    EXAMPLE_BUNDLERS_TEMPLATE);

            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    TESTAPP_EXAMPLE_BUNDLERS,
                    new Source(TESTAPP_EXAMPLEBUNDLERS_MODULE_NAME,
                            "testapp.examplebundlers.module.info.template",
                            classToTemplateMap, TESTAPP_EXAMPLE_BUNDLERS,
                            exampleBundlersName, Collections.emptyMap(), true));
        }
    }

    @Test
    public void testLoadDefaultBundlers() {
        if (Config.CONFIG_INSTANCE.manualOnly()) {
            throw new SkipException("Skipping automated test");
        }

        Bundlers bundlers = Bundlers.createBundlersInstance();
        Set<String> loaded = bundlers.getBundlers().stream()
                .map(Bundler::getClass).map(Class::getName).collect(toSet());
        /*
         * since below classes are not exported from jdk.packager module, using
         * string's directly without use of --add-exports
         */
        String[] mustBeLoadedStrings = new String[] {
                "com.oracle.tools.packager.linux.LinuxRpmBundler",
                "com.oracle.tools.packager.linux.LinuxDebBundler",
                "com.oracle.tools.packager.linux.LinuxAppBundler",
                "com.oracle.tools.packager.windows.WinAppBundler",
                "com.oracle.tools.packager.windows.WinExeBundler",
                "com.oracle.tools.packager.windows.WinMsiBundler",
                "com.oracle.tools.packager.mac.MacAppBundler",
                "com.oracle.tools.packager.mac.MacDmgBundler",
                "com.oracle.tools.packager.mac.MacPkgBundler",
                "com.oracle.tools.packager.mac.MacAppStoreBundler",
                "com.oracle.tools.packager.jnlp.JNLPBundler" };
        List<String> mustBeLoadedList = Arrays.asList(mustBeLoadedStrings);
        Set<String> mustBeLoadedSet = new HashSet<>(mustBeLoadedList);
        assertEquals(mustBeLoadedSet, loaded,
                "Not all the default bundlers are loaded");
    }

    @Test(dataProvider = "getExtension")
    public void testLoadBundlerMethod(ExtensionType extension)
            throws IOException, ExecutionException {
        List<Pair<String, String>> arrayList = new ArrayList<Pair<String, String>>();
        if (extension == ExtensionType.NormalJar) {
            arrayList.add(
                    new Pair<>(BundlerLoadingTest.COM_ORACLE_TOOLS_PACKAGER_BUNDLER,
                            exampleBundlerFullName));
            app = createAndPrepareNewAppForExampleBundlerWithService(extension, arrayList);
            List<Path> endProductListPath = getJavaExtensionProductListPath(app,
                    extension);
            List<URL> urlList = new ArrayList<>();
            for (Path path : endProductListPath) {
                urlList.add(path.toUri().toURL());
            }
            Bundlers bundlers = Bundlers.createBundlersInstance();
            ServiceLoader<Bundler> loader = ServiceLoader.load(Bundler.class,
                    new URLClassLoader(
                            urlList.toArray(new URL[urlList.size()])));
            Iterator<Bundler> iterator = loader.iterator();
            while (iterator.hasNext()) {
                Bundler nextBundler = iterator.next();
                bundlers.loadBundler(nextBundler);
            }
            assertTrue(
                    bundlers.getBundlers().stream()
                            .anyMatch(bundler -> bundler.getClass().getName()
                                    .equals(exampleBundlerFullName)),
                    exampleBundlerFullName + " Bundler is not loaded");
        } else {
            app = createAndPrepareNewAppForExampleBundlerWithService(extension, arrayList);
            Layer layer = createCustomLayer(extension,
                    BundlerLoadingTest.TESTAPP_EXAMPLEBUNDLER_MODULE_NAME);
            ServiceLoader<Bundler> loader = ServiceLoader.load(layer,
                    Bundler.class);
            Bundlers bundlers = Bundlers.createBundlersInstance();
            Iterator<Bundler> iterator = loader.iterator();
            while (iterator.hasNext()) {
                Bundler nextBundler = iterator.next();
                bundlers.loadBundler(nextBundler);
            }
            assertTrue(
                    bundlers.getBundlers().stream()
                            .anyMatch(bundler -> bundler.getClass().getName()
                                    .equals(exampleBundlerFullName)),
                    exampleBundlerFullName + " Bundler is not loaded");
        }
    }

    @Test(dataProvider = "getExtension")
    public void testLoadBundlersFromServicesMethod(ExtensionType extension)
            throws IOException, ExecutionException {
        List<Pair<String, String>> arrayList = new ArrayList<Pair<String, String>>();
        if (extension == ExtensionType.NormalJar) {
            arrayList.add(
                    new Pair<>(BundlerLoadingTest.COM_ORACLE_TOOLS_PACKAGER_BUNDLER,
                            exampleBundlerFullName));
            app = createAndPrepareNewAppForExampleBundlerWithService(extension, arrayList);
            Bundlers bundlers = Bundlers.createBundlersInstance();
            List<Path> endProductListPath = getJavaExtensionProductListPath(app,
                    extension);
            List<URL> urlList = new ArrayList<>();
            for (Path path : endProductListPath) {
                urlList.add(path.toUri().toURL());
            }
            bundlers.loadBundlersFromServices(new URLClassLoader(
                    urlList.toArray(new URL[urlList.size()])));
            assertTrue(
                    bundlers.getBundlers().stream()
                            .anyMatch(bundler -> bundler.getClass().getName()
                                    .equals(exampleBundlerFullName)),
                    exampleBundlerFullName + " Bundler is not loaded from jar");
        } else {
            app = createAndPrepareNewAppForExampleBundlerWithService(extension, arrayList);
            Layer layer = createCustomLayer(extension,
                    BundlerLoadingTest.TESTAPP_EXAMPLEBUNDLER_MODULE_NAME);
            ClassLoader moduleClassLoader = layer
                    .findLoader(BundlerLoadingTest.TESTAPP_EXAMPLEBUNDLER_MODULE_NAME);
            ServiceLoader<Bundler> loader = ServiceLoader.load(Bundler.class);
            Bundlers bundlers = Bundlers.createBundlersInstance();
            bundlers.loadBundlersFromServices(moduleClassLoader);
            assertTrue(
                    bundlers.getBundlers().stream()
                            .anyMatch(bundler -> bundler.getClass().getName()
                                    .equals(exampleBundlerFullName)),
                    exampleBundlerFullName + " Bundler is not loaded from jar");

        }
    }

    @Test(dataProvider = "getExtension")
    public void testLoadCustomBundlersFromJar(ExtensionType extension)
            throws IOException, ExecutionException {
        List<Pair<String, String>> arrayList = new ArrayList<Pair<String, String>>();
        if (extension == ExtensionType.NormalJar) {
            arrayList.add(new Pair<>(
                    BundlerLoadingTest.COM_ORACLE_TOOLS_PACKAGER_BUNDLERS,
                    exampleBundlersFullName));
            app = createAndPrepareNewAppForExampleBundlersWithService(extension, arrayList);
            List<Path> endProductListPath = getJavaExtensionProductListPath(app,
                    extension);
            List<URL> urlList = new ArrayList<>();
            for (Path path : endProductListPath) {
                urlList.add(path.toUri().toURL());
            }

            ClassLoader customBundlersLoader = new URLClassLoader(
                    urlList.toArray(new URL[urlList.size()]));
            Bundlers bundlers = Bundlers
                    .createBundlersInstance(customBundlersLoader);
            ServiceLoader<Bundlers> bundlersLoader = ServiceLoader
                    .load(Bundlers.class, customBundlersLoader);
            Iterator<Bundlers> iter = bundlersLoader.iterator();
            while (iter.hasNext()) {
                bundlers = iter.next();
                if (exampleBundlersFullName
                        .equals(bundlers.getClass().getName())) {
                    break;
                }
            }
            assertEquals(bundlers.getClass().getName(), exampleBundlersFullName,
                    "Bundlers're not loaded");
        } else {
            app = createAndPrepareNewAppForExampleBundlersWithService(extension, arrayList);
            Layer layer = createCustomLayer(extension,
                    BundlerLoadingTest.TESTAPP_EXAMPLEBUNDLERS_MODULE_NAME);
            ClassLoader moduleClassLoader = layer
                    .findLoader(BundlerLoadingTest.TESTAPP_EXAMPLEBUNDLERS_MODULE_NAME);
            ServiceLoader<Bundler> loader = ServiceLoader.load(Bundler.class);
            Bundlers bundlers = Bundlers
                    .createBundlersInstance(moduleClassLoader);
            assertEquals(bundlers.getClass().getName(), exampleBundlersFullName,
                    "Bundlers're not loaded");
        }
    }

    private Layer createCustomLayer(ExtensionType extension,
            String moduleName) {
        ModuleFinder moduleFinder = ModuleFinder
                .of(app.getJavaExtensionDirPathBasedonExtension(extension));
        Layer parentLayer = Layer.boot();
        Configuration configuration = parentLayer.configuration()
                .resolve(moduleFinder, ModuleFinder.of(),
                        Set.of(moduleName));
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Layer layer = parentLayer.defineModulesWithOneLoader(configuration,
                classLoader);
        return layer;
    }

    @AfterMethod
    private void cleanUp() throws IOException {
        if (!Config.CONFIG_INSTANCE.isNoCleanSet()) {
            if (app != null) {
                LOG.log(Level.INFO, "Removing Directory "+app.getWorkDir());
                Utils.tryRemoveRecursive(app.getWorkDir());
            }
        } else {
            LOG.log(Level.INFO, "Skipped Removing Directory "+app.getWorkDir());
        }
    }

    private List<Path> getJavaExtensionProductListPath(AppWrapper app,
            ExtensionType extension) throws IOException {
        switch (extension) {
        case NormalJar:
            return app.getJarFilesList();
        case ExplodedModules:
            return app.getExplodedModFileList();
        case Jmods:
            return app.getJmodFileList();
        case ModularJar:
            return app.getModularJarFileList();
        }
        return null;
    }

    @DataProvider(name = "getExtension")
    private Iterator<Object[]> getExtension() {
        List<Object[]> list = new ArrayList<Object[]>();
        for (ExtensionType extension : getExtensionArray()) {
            Object[] eachArray = new Object[] { extension };
            list.add(eachArray);
        }
        return list.iterator();
    }

    /*
     * Jmods are excluded from this list because if jmods are included then we
     * need to pass those *.jmod file to jlink and create image for same as *.jmod
     * are not directly accepted by java command. so it is like finding all
     * providers from runtime image instead of providing parameters
     * programatically.
     */
    private ExtensionType[] getExtensionArray() {
        return new ExtensionType[] { ExtensionType.ExplodedModules,
                ExtensionType.ModularJar, ExtensionType.NormalJar };
    }
}
