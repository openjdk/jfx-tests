/*
 * Copyright (c) 2014, 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static java.lang.String.format;
import static java.nio.file.Files.newDirectoryStream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import javafx.util.Pair;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class AppWrapper implements Constants {

    private static final Logger LOG = Logger
            .getLogger(AppWrapper.class.getName());

    private Path workDir;
    private final List<Source> sources;
    private final String mainJar;
    private final String mainClass;

    private String paramsForJarsCompilation;

    public AppWrapper(Path workDir, String mainClass, Source... source) {
        this.workDir = workDir;
        this.sources = Arrays.asList(source);
        this.mainClass = mainClass;
        List<String> jars = sources.stream()
                .filter(src -> src.getFullName().equals(mainClass))
                .map(Source::getJarName).collect(toList());
        if (jars.size() != 1) {
            throw new IllegalArgumentException(
                    format("Can not determine main jar : " + jars));
        }
        mainJar = jars.get(0);
    }

    public AppWrapper(Path workDir, String mainClass,
            String paramsForCompilation, Source... source) {
        this(workDir, mainClass, source);
        this.paramsForJarsCompilation = paramsForCompilation;
    }

    /**
     * copy the old AppWrapper, but change the work dir
     */
    public AppWrapper(AppWrapper copied, Path workDir) {
        this.workDir = workDir;
        sources = new ArrayList<>(copied.sources);
        mainJar = copied.mainJar;
        mainClass = copied.mainClass;
    }

    public String getAppName() {
        return mainClass.substring(mainClass.lastIndexOf('.') + 1);
    }

    public void preinstallApp(ExtensionType extension) throws IOException {
        createSrcBundleAndBinDirs();

        if (!getJarTempSources().isEmpty()) {
            Utils.createDir(getJarDir());
        }

        if (!getModuleTempSources().isEmpty()) {
            Utils.createDir(
                    Paths.get(getModulePathBasedOnExtension(extension)));
        }
    }

    private void createSrcBundleAndBinDirs() throws IOException {
        Utils.createDir(getSrcDir());
        Utils.createDir(getBundlesDir());
        Utils.createDir(getBinDir());
    }

    public void preinstallApp(ExtensionType[] extensionArray)
            throws IOException {
        createSrcBundleAndBinDirs();

        for (ExtensionType extension : extensionArray) {
            Utils.createDir(
                    Paths.get(getModulePathBasedOnExtension(extension)));
        }
    }

    private List<Source> getJarTempSources() {
        return sources.stream().filter((source) -> !source.isModule())
                .collect(Collectors.toList());
    }

    public Path getWorkDir() {
        return workDir;
    }

    public void setWorkDir(Path workDir) {
        this.workDir = workDir;
    }

    public List<Source> getSources() {
        return sources;
    }

    public String getMainClass() {
        return this.mainClass;
    }

    public Path getBundlesDir() {
        return getWorkDir().resolve(BUNDLES);
    }

    private Path getSrcDir() {
        return getWorkDir().resolve(SOURCE);
    }

    public Path getJarDir() {
        return getWorkDir().resolve(JARS);
    }

    private Path getBinDir() {
        return getWorkDir().resolve(BIN);
    }

    public Path getMainJarFile() {
        return getJarDir().resolve(mainJar + ".jar");
    }

    public void writeSourcesToAppDirectory() throws IOException {
        Path appDir = getSrcDir();
        for (Source source : getModuleTempSources()) {
            source.generateSourceForModule(appDir);
        }

        for (Source source : getJarTempSources()) {
            source.generateSourceForJar(appDir);
        }
    }

    public int compileApp(ExtensionType extension, Path... classpath)
            throws IOException {
        return compileApp(new String[0], extension, classpath);
    }

    private int compileApp(String[] javacOptions, ExtensionType extension,
            Path... classpath) throws IOException {
        int resultForModule = compileAppForModules(javacOptions, extension,
                classpath);
        int resultForJar = compileAppForJars(javacOptions, extension,
                classpath);
        int result = 0;
        if (resultForJar < 0 || resultForModule < 0) {
            result = -1;
        }
        return result;
    }

    public int compileApp(Path... classpath) throws IOException {
        return compileApp(new String[0], classpath);
    }

    public int compileApp(String[] javacOptions, Path... classpath)
            throws IOException {
        return compileApp(javacOptions, null, classpath);
    }

    public int compileAndCreateJavaExtensionProduct(ExtensionType extension,
            Path... classpath) throws IOException, ExecutionException {
        int resultForModule = compileAppForModules(new String[0], extension,
                classpath);
        jarApp(extension);
        compileAppForJars(new String[0], extension, classpath);
        jarApp(ExtensionType.NormalJar);
        return resultForModule;
    }

    private int compileAppForJars(String[] javacOptions,
            ExtensionType extension, Path[] classpath) throws IOException {
        if (getJarTempSources().isEmpty()) {
            return 0;
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final List<String> argsList = new ArrayList<>();

        if (javacOptions != null && javacOptions.length > 0) {
            for (int i = 0; i < javacOptions.length; i++) {
                String javacOption = javacOptions[i];
                argsList.add(javacOption);
            }
        }

        argsList.add("-d");
        argsList.add(getBinDir().toString());

        int result = 0;
        for (Source tempSource : getJarTempSources()) {
            List<String> newArgs = new ArrayList<String>();
            newArgs.addAll(argsList);

            newArgs.add("-classpath");
            if (classpath.length != 0) {
                newArgs.add(
                        Stream.of(classpath)
                                .map(eachPath -> eachPath.toAbsolutePath()
                                        .toString())
                        .collect(joining(File.pathSeparator)));
            } else {
                newArgs.add(getBinDir().toString());
            }

            if (paramsForJarsCompilation != null) {
                newArgs.add(this.paramsForJarsCompilation);
            }

            if (extension != null) {
                newArgs.add("-mp");
                newArgs.add(String.join(File.pathSeparator,
                        getModulePathBasedOnExtension(extension),
                        JMODS_PATH_IN_JDK));
            }

            String string = getSrcDir() + File.separator
                    + tempSource.getPackageName().replace(".", File.separator);
            Path path = Paths.get(string);
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file,
                        BasicFileAttributes attr) {

                    if (file.toString().endsWith(".java")) {
                        newArgs.add(file.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            LOG.log(Level.INFO,
                    "====================COMPILATION STARTS FOR NORMAL JAR===========================");
            LOG.log(Level.INFO, "compilation command for jars is " + newArgs);
            result = compiler.run(System.in, outputStream, System.err,
                    newArgs.toArray(new String[newArgs.size()]));
            LOG.log(Level.INFO,
                    "====================COMPILATION ENDS FOR NORMAL JAR=============================");
        }
        return result;
    }

    private int compileAppForModules(String[] javacOptions,
            ExtensionType extension, Path... classpath) throws IOException {
        if (getModuleTempSources().isEmpty()) {
            return 0;
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = 0;
        for (Source source : getModuleTempSources()) {
            final List<String> argsList = new ArrayList<>();

            if (javacOptions != null && javacOptions.length > 0) {
                for (int i = 0; i < javacOptions.length; i++) {
                    String javacOption = javacOptions[i];
                    argsList.add(javacOption);
                }
            }

            argsList.add("-mp");
            argsList.add(String.join(File.pathSeparator, getBinDir().toString(),
                    JMODS_PATH_IN_JDK));
            argsList.add("-d");
            argsList.add(String.join(File.separator, getBinDir().toString(),
                    source.getModuleName()));
            Files.walkFileTree(
                    Paths.get(String.join(File.separator,
                            getSrcDir().toString(), source.getModuleName())),
                    new SimpleFileVisitor<Path>() {
                        public FileVisitResult visitFile(Path file,
                                BasicFileAttributes attr) {
                            if (file.toString().endsWith(".java")) {
                                argsList.add(file.toString());
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
            if (classpath.length != 0) {
                argsList.add("-classpath");
                argsList.add(Stream.of(classpath)
                        .map(path -> path.toAbsolutePath().toString())
                        .collect(joining(File.pathSeparator)));
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (extension != null) {
                LOG.log(Level.INFO,
                        "====================COMPILATION STARTS FOR "
                                + extension + " ===========================");
                LOG.log(Level.INFO, "compilation command for " + extension
                        + " is " + argsList);
            } else {
                LOG.log(Level.INFO,
                        "compilation command for modules is " + argsList);
            }

            int tempResult = compiler.run(System.in, outputStream, System.err,
                    argsList.toArray(new String[argsList.size()]));
            if (tempResult != 0) {
                result = tempResult;
            }
            String out = outputStream.toString();
            if (!out.trim().isEmpty()) {
                LOG.log(Level.INFO, out);
            }
            if (extension != null) {
                LOG.log(Level.INFO, "===================COMPILATION ENDS FOR "
                        + extension + " ==============================");
            } else {
                LOG.log(Level.INFO,
                        "===================COMPILATION ENDS===================");
            }
            LOG.log(Level.INFO, "\n");
        }
        return result;
    }

    public List<Path> getJarFilesList() throws IOException {
        try (DirectoryStream<Path> jarsStream = Files
                .newDirectoryStream(getJarDir(), "*.jar")) {
            List<Path> jars = new ArrayList<>();
            jarsStream.forEach(jars::add);
            return jars;
        }
    }

    private void createSimpleJar(List<Pair<String, String>> services,
            boolean crossClassPath) throws IOException {

        if (getJarTempSources().isEmpty()) {
            return;
        }
        Map<String, List<Source>> jars = getJarTempSources().stream()
                .collect(Collectors.groupingBy(Source::getJarName));

        for (Entry<String, List<Source>> entry : jars.entrySet()) {
            String jarFileName = entry.getKey();
            Path jarFile = getJarDir().resolve(jarFileName + ".jar");
            Manifest manifest = new Manifest();
            manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION,
                    "1.0");
            if (crossClassPath)
                manifest.getMainAttributes().put(Attributes.Name.CLASS_PATH,
                        jars.keySet().stream()
                                .filter(str -> !str.equals(jarFileName))
                                .map(str -> str + ".jar")
                                .collect(joining(File.pathSeparator)));
            if (mainJar.equals(jarFileName)) {
                manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS,
                        mainClass);
            }

            try (JarOutputStream jarOutputStream = new JarOutputStream(
                    Files.newOutputStream(jarFile), manifest)) {
                add(jarOutputStream, new HashSet<>(entry.getValue()));
                for (Pair<String, String> service : services) {
                    if (!entry.getValue().stream().anyMatch(source -> source
                            .getFullName().equals(service.getValue()))) {
                        continue;
                    }
                    jarOutputStream.putNextEntry(new JarEntry(
                            "META-INF/services/" + service.getKey()));
                    jarOutputStream.write(service.getValue().getBytes());
                    jarOutputStream.closeEntry();
                }
            }
        }
    }

    private void add(JarOutputStream jarStream, Set<Source> sources)
            throws IOException {
        Path classesDir = getBinDir();
        for (Source src : sources) {
            Path packageDir = classesDir
                    .resolve(src.getPackageName().replace('.', '/'));
            // we need to process inner classes like App1$1
            DirectoryStream<Path> classFiles = newDirectoryStream(packageDir,
                    src.getSimpleName() + "*.class");
            for (Path classFile : classFiles) {
                ZipEntry entry = new JarEntry(
                        src.getPackageName().replace('.', '/') + "/"
                                + classFile.getFileName().toString());
                jarStream.putNextEntry(entry);
                jarStream.write(Files.readAllBytes(classFile));
                jarStream.closeEntry();
            }
        }
    }

    public List<Source> getModuleTempSources() {
        return sources.stream().filter((source) -> source.isModule())
                .collect(Collectors.toList());
    }

    public Path getExplodedModsDir() {
        return getBinDir();
    }

    public Path getJmodsDir() {
        return getWorkDir().resolve(JMODS_DIR);
    }

    public Path getModularJarsDir() {
        return getWorkDir().resolve(MODULAR_JARS_DIR);
    }

    public List<Path> getModularJarFileList() throws IOException {
        try (DirectoryStream<Path> jarsStream = Files
                .newDirectoryStream(getModularJarsDir(), "*.jar")) {
            List<Path> jars = new ArrayList<>();
            jarsStream.forEach(jars::add);
            return jars;
        }
    }

    public List<Path> getJmodFileList() throws IOException {
        try (DirectoryStream<Path> jarsStream = Files
                .newDirectoryStream(getJmodsDir(), "*.jmod")) {
            List<Path> jmods = new ArrayList<>();
            jarsStream.forEach(jmods::add);
            return jmods;
        }
    }

    /*
     * list mod directory
     */

    public List<Path> getExplodedModFileList() throws IOException {
        try (DirectoryStream<Path> jarsStream = Files
                .newDirectoryStream(getExplodedModsDir(), "*")) {
            List<Path> modFiles = new ArrayList<>();
            jarsStream.forEach(modFiles::add);
            return modFiles;
        }
    }

    public void jarApp(ExtensionType extension)
            throws IOException, ExecutionException {
        switch (extension) {
        case NormalJar:
            createSimpleJar(Collections.emptyList(), false);
            break;
        case ModularJar:
            createModularJar();
            break;
        case ExplodedModules: /******************************************
                               * bin directory itself is exploded modules
                               * directory
                               ******************************************/
            break;
        case Jmods:
            createJmod();
            break;
        }
    }

    public void jarApp(ExtensionType extension,
            List<Pair<String, String>> services)
                    throws IOException, ExecutionException {
        switch (extension) {
        case NormalJar:
            createSimpleJar(services, false);
            break;
        case ModularJar:
            createModularJar();
            break;
        case ExplodedModules: /******************************************
                               * bin directory itself is exploded modules
                               * directory
                               ******************************************/
            break;
        case Jmods:
            createJmod();
            break;
        }
    }

    public void jarApp(List<Pair<String, String>> services,
            boolean crossClassPath) throws IOException, ExecutionException {
        createSimpleJar(services, crossClassPath);
        createModularJar();
    }

    private void createJmod() throws IOException, ExecutionException {
        for (Source source : getModuleTempSources()) {
            List<String> command = new ArrayList<String>();
            command.add("jmod");
            command.add("create");
            command.add("--class-path");
            command.add(getBinDir().toString() + File.separator
                    + source.getModuleName());
            command.add("--main-class");
            command.add(source.getFullName());
            command.add(getJmodsDir() + File.separator + source.getModuleName()
                    + ".jmod");
            System.out.println(
                    "=========================JMOD CREATION STARTS=========================");
            Utils.runCommand(command, CONFIG_INSTANCE.getInstallTimeout());
            System.out.println(
                    "=========================JMOD CREATION ENDS===========================");
            System.out.println();
        }
    }

    private void createModularJar() throws IOException, ExecutionException {

        if (getModuleTempSources().isEmpty()) {
            return;
        }
        /*
         * JDK 9 jar creation procedure. $ jar --create
         * --file=mlib/org.astro@1.0.jar --module-version=1.0 -C mods/org.astro
         * .
         */
        for (Source source : getModuleTempSources()) {
            List<String> command = new ArrayList<String>();
            command.add("jar");
            command.add("--create");
            command.add("--file=" + getModularJarsDir() + File.separator
                    + source.getJarName() + ".jar");
            command.add("--module-version=1.0");
            if (source.getFullName() != null && this.mainClass != null
                    && source.getFullName().equals(this.mainClass)) {
                command.add("--main-class=" + this.mainClass);
            }
            command.add("-C");
            String moduleAbsouleDirectoryPath = String.join(File.separator,
                    getBinDir().toString(), source.getModuleName());
            command.add(moduleAbsouleDirectoryPath);
            command.add(".");
            System.out.println(
                    "====================MODULAR JAR CREATION STARTS==================");
            Utils.runCommand(command, CONFIG_INSTANCE.getInstallTimeout());
            System.out.println(
                    "====================MODULAR JAR CREATION ENDS====================");
            System.out.println();
        }
    }

    public String getIdentifier() {
        try (JarFile mainJar = new JarFile(getMainJarFile().toFile());) {
            Manifest manifest = mainJar.getManifest();
            for (Map.Entry<Object, Object> entry : manifest.getMainAttributes()
                    .entrySet()) {
                if (entry.getKey().toString().equals("Main-Class")) {
                    return entry.getValue().toString();
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return "";
    }

    public String getAllModuleNamesSeparatedByPathSeparator() {
        return getModuleTempSources().stream().map(Source::getModuleName)
                .collect(Collectors.joining(File.pathSeparator));
    }

    public String getAllModuleNamesSeparatedByComma() {
        return getModuleTempSources().stream().map(Source::getModuleName)
                .collect(Collectors.joining(","));
    }

    public List<String> getAllModuleNamesAsList() {
        return getModuleTempSources().stream().map(Source::getModuleName)
                .collect(Collectors.toList());
    }

    public String getMainModuleName() {
        List<Source> collect = sources.stream()
                .filter((source) -> source.isMainModule())
                .collect(Collectors.toList());
        return !collect.isEmpty() ? collect.get(0).getModuleName() : null;
    }

    public boolean isAppContainsModules() {
        return !getModuleTempSources().isEmpty();
    }

    private String getModulePathBasedOnExtension(ExtensionType extension) {
        if (extension == null) {
            throw new NullPointerException("Extension cannot be null");
        }
        switch (extension) {
        case ModularJar:
            return getModularJarsDir().toString();
        case ExplodedModules:
            return getExplodedModsDir().toString();
        case Jmods:
            return getJmodsDir().toString();
        default:
            return getJarDir().toString();
        }
    }
}
