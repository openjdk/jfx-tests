package com.oracle.appbundlers.tests.loading;

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

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.Config;
import com.oracle.appbundlers.utils.Constants;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.Bundler;
import com.oracle.tools.packager.Bundlers;

import javafx.util.Pair;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class BundlerLoadingTest implements Constants {

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

    private AppWrapper createAndPrepareNewAppForExampleBundler(
            ExtensionType extension, List<Pair<String, String>> services)
                    throws IOException, ExecutionException {
        app = getExampleBundlerApp(extension);
        app.preinstallApp(extension);
        app.writeSourcesToAppDirectory();
        app.compileApp(extension);
        app.jarApp(extension, services);
        return app;
    }

    private AppWrapper createAndPrepareNewAppForExampleBundlers(
            ExtensionType extension, List<Pair<String, String>> services)
                    throws IOException, ExecutionException {
        app = getExampleBundlersApp(extension);
        app.preinstallApp(extension);
        app.writeSourcesToAppDirectory();
        app.compileApp(extension);
        app.jarApp(extension, services);
        return app;
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
         * string's directly without use of -XaddExports
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
        arrayList.add(new Pair<>(BundlerLoadingTest.COM_ORACLE_TOOLS_PACKAGER_BUNDLER,
                exampleBundlerFullName));
        app = createAndPrepareNewAppForExampleBundler(extension, arrayList);
        List<Path> endProductListPath = getJavaExtensionProductListPath(app, extension);
        List<URL> urlList = new ArrayList<>();
        for (Path path : endProductListPath) {
            urlList.add(path.toUri().toURL());
        }
        ServiceLoader<Bundler> loader = ServiceLoader.load(Bundler.class,

        new URLClassLoader(urlList.toArray(new URL[urlList.size()])));

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

    @Test(dataProvider = "getExtension")
    public void testLoadBundlersFromServicesMethod(ExtensionType extension)
            throws IOException, ExecutionException {
        List<Pair<String, String>> arrayList = new ArrayList<Pair<String, String>>();
        arrayList.add(new Pair<>(BundlerLoadingTest.COM_ORACLE_TOOLS_PACKAGER_BUNDLER,
                exampleBundlerFullName));
        app = createAndPrepareNewAppForExampleBundler(extension, arrayList);
        Bundlers bundlers = Bundlers.createBundlersInstance();
        List<Path> endProductListPath = getJavaExtensionProductListPath(app, extension);
        List<URL> urlList = new ArrayList<>();
        for (Path path : endProductListPath) {
            urlList.add(path.toUri().toURL());
        }
        bundlers.loadBundlersFromServices(
                new URLClassLoader(urlList.toArray(new URL[urlList.size()])));
        assertTrue(
                bundlers.getBundlers().stream()
                        .anyMatch(bundler -> bundler.getClass().getName()
                                .equals(exampleBundlerFullName)),
                exampleBundlerFullName + " Bundler is not loaded from jar");
    }

    @Test(dataProvider = "getExtension")
    public void testLoadCustomBundlersFromJar(ExtensionType extension)
            throws IOException, ExecutionException {
        List<Pair<String, String>> arrayList = new ArrayList<Pair<String, String>>();
        arrayList.add(new Pair<>(BundlerLoadingTest.COM_ORACLE_TOOLS_PACKAGER_BUNDLERS,
                exampleBundlersFullName));
        app = createAndPrepareNewAppForExampleBundlers(extension, arrayList);
        List<Path> endProductListPath = getJavaExtensionProductListPath(app, extension);
        List<URL> urlList = new ArrayList<>();
        for (Path path : endProductListPath) {
            urlList.add(path.toUri().toURL());
        }

        ClassLoader customBundlersLoader = new URLClassLoader(
                urlList.toArray(new URL[urlList.size()]));
        Bundlers bundlers = Bundlers
                .createBundlersInstance(customBundlersLoader);

        assertEquals(bundlers.getClass().getName(), exampleBundlersFullName,
                " Bundlers are not loaded");

        assertTrue(!bundlers.getBundlers().isEmpty(),
                "Default bundlers are not loaded");
    }

    @AfterMethod
    @BeforeMethod
    protected void cleanUp() throws IOException {
        if (app != null) {
            Utils.tryRemoveRecursive(app.getWorkDir());
        }
    }

    public List<Path> getJavaExtensionProductListPath(AppWrapper app,
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
    public Iterator<Object[]> getExtension() {
        List<Object[]> list = new ArrayList<Object[]>();
        for (ExtensionType extension : getExtensionArray()) {
            Object[] eachArray = new Object[] { extension };
            list.add(eachArray);
        }
        return list.iterator();
    }

    private ExtensionType[] getExtensionArray() {
        return ExtensionType.values();
    }

    private AppWrapper getExampleBundlerApp(ExtensionType extension)
            throws IOException, ExecutionException {
        switch (extension) {
        default:
            String fullName = examplePackage + "." + exampleBundlerName;
            return new AppWrapper(Utils.getTempSubDir("testBundlerLoading"),
                    fullName, new Source(fullName, exampleBundlerName + ".java.template",
                            exampleBundlerName, Collections.emptyMap()));
        case ExplodedModules:
        case Jmods:
        case ModularJar:
            Map<String, String> classToTemplateMap = new HashMap<String, String>();
            classToTemplateMap.put(TESTAPP_EXAMPLE_BUNDLER,
                    EXAMPLE_BUNDLER_TEMPLATE);

            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    TESTAPP_EXAMPLE_BUNDLER,
                    new Source("testapp.examplebundler",
                            "testapp.examplebundler.module.info.template",
                            classToTemplateMap, TESTAPP_EXAMPLE_BUNDLER,
                            "ExampleBundler", Collections.emptyMap(), true));
        }
    }

    private AppWrapper getExampleBundlersApp(ExtensionType extension)
            throws IOException, ExecutionException {
        switch (extension) {
        default:
            String fullName = examplePackage + "." + exampleBundlersName;
            return new AppWrapper(Utils.getTempSubDir("testBundlerLoading"),
                    fullName, new Source(fullName, exampleBundlersName + ".java.template",
                            exampleBundlersName, Collections.emptyMap()));
        case ExplodedModules:
        case Jmods:
        case ModularJar:
            Map<String, String> classToTemplateMap = new HashMap<String, String>();
            classToTemplateMap.put(TESTAPP_EXAMPLE_BUNDLERS,
                    EXAMPLE_BUNDLERS_TEMPLATE);

            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    TESTAPP_EXAMPLE_BUNDLERS,
                    new Source("testapp.examplebundlers",
                            "testapp.examplebundlers.module.info.template",
                            classToTemplateMap, TESTAPP_EXAMPLE_BUNDLERS,
                            "ExampleBundlers", Collections.emptyMap(), true));
        }
    }
}
