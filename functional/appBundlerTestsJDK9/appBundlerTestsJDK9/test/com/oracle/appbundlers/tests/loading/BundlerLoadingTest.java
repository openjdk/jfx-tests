package com.oracle.appbundlers.tests.loading;

import static java.util.stream.Collectors.toSet;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.Config;
import com.oracle.tools.packager.Bundler;
import com.oracle.tools.packager.Bundlers;
import com.oracle.tools.packager.jnlp.JNLPBundler;
import com.oracle.tools.packager.linux.LinuxAppBundler;
import com.oracle.tools.packager.linux.LinuxDebBundler;
import com.oracle.tools.packager.linux.LinuxRpmBundler;
import com.oracle.tools.packager.mac.MacAppBundler;
import com.oracle.tools.packager.mac.MacAppStoreBundler;
import com.oracle.tools.packager.mac.MacDmgBundler;
import com.oracle.tools.packager.mac.MacPkgBundler;
import com.oracle.tools.packager.windows.WinAppBundler;
import com.oracle.tools.packager.windows.WinExeBundler;
import com.oracle.tools.packager.windows.WinMsiBundler;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class BundlerLoadingTest {

    public static final String examplePackage = "testapp";
    public static final String exampleBundlerName = "ExampleBundler";
    public static final String exampleBundlerFullName = examplePackage + "." + exampleBundlerName;
    public static final String exampleBundlersName = "ExampleBundlers";
    public static final String exampleBundlersFullName = examplePackage + "." + exampleBundlersName;

    @Test
    public void testLoadDefaultBundlers() {
        if (Config.CONFIG_INSTANCE.manualOnly()) {
            throw new SkipException("Skipping automated test");
        }

        Bundlers bundlers = Bundlers.createBundlersInstance();
        Set<Class<?>> mustBeLoaded = new HashSet<>(
                Arrays.asList(
                        LinuxAppBundler.class,
                        LinuxDebBundler.class,
                        LinuxRpmBundler.class,
                        WinAppBundler.class,
                        WinExeBundler.class,
                        WinMsiBundler.class,
                        MacAppBundler.class,
                        MacDmgBundler.class,
                        MacPkgBundler.class,
                        MacAppStoreBundler.class,
                        JNLPBundler.class)
        );
        Set<Class<?>> loaded = bundlers.getBundlers().stream()
                .map(Bundler::getClass)
                .collect(toSet());
        assertEquals(mustBeLoaded, loaded, "Not all the default bundlers are loaded");
    }

    private AppWrapper createAppWithService(String interfaceClass, String implClass) throws IOException {
        String fullName = examplePackage + "." + implClass;
        AppWrapper app = null;
//        AppWrapper app = new AppWrapper(
//                Utils.getTempSubDir("testBundlerLoading"),
//                fullName,
//                new Source(fullName, implClass + ".java.template", implClass)
//        );
//        app.cleanupApp();
//        app.preinstallApp();
//        app.writeSourcesToAppDirectory();
//        app.compileApp();
//        app.jarApp(Arrays.asList(new Pair<>(interfaceClass, fullName)));
        return app;
    }

    @Test
    public void testLoadCustomBundlerFromJar() throws IOException {
        AppWrapper app = createAppWithService("com.oracle.tools.packager.Bundler", "ExampleBundler");

        Bundlers bundlers = Bundlers.createBundlersInstance();
        bundlers.loadBundlersFromServices(new URLClassLoader(new URL[]{app.getJarFilesList().get(0).toUri().toURL()}));
        assertTrue(bundlers.getBundlers().stream().anyMatch(
                        bundler -> bundler.getClass().getName().equals(exampleBundlerFullName)),
                "Bundler's not loaded from jar"
        );
    }

    @Test
    public void testLoadCustomBundler() throws IOException {
        AppWrapper app = createAppWithService("com.oracle.tools.packager.Bundler", "ExampleBundler");

        ServiceLoader<Bundler> loader = ServiceLoader.load(
                Bundler.class, new URLClassLoader(
                        new URL[]{app.getJarFilesList().get(0).toUri().toURL()}
                )
        );

        Bundlers bundlers = Bundlers.createBundlersInstance();
        bundlers.loadBundler(loader.iterator().next());
        assertTrue(bundlers.getBundlers().stream().anyMatch(
                        bundler -> bundler.getClass().getName().equals(exampleBundlerFullName)),
                "Bundler's not loaded"
        );
    }

    @Test
    public void testLoadCustomBundlersFromJar() throws IOException {
        AppWrapper app = createAppWithService("com.oracle.tools.packager.Bundlers", "ExampleBundlers");

        ClassLoader bundlersLoader = new URLClassLoader(
                new URL[]{app.getJarFilesList().get(0).toUri().toURL()}
        );
        Bundlers bundlers = Bundlers.createBundlersInstance(bundlersLoader);

        assertEquals(bundlers.getClass().getName(), exampleBundlersFullName,
                "Bundlers're not loaded"
        );

        assertTrue(!bundlers.getBundlers().isEmpty(),
                "Default bundlers are not loaded"); // see the code of ExampleBundlers.java.template
    }
}
