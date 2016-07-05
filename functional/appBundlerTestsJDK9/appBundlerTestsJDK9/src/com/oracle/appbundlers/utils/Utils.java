/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.exists;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */
public class Utils {

    public static long timeEps = 100;
    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    public static String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isWindows() {
        return getOS().contains("win");
    }

    public static boolean isMacOS() {
        return getOS().contains("mac");
    }

    public static boolean isLinux() {
        return getOS().contains("linux");
    }

    public static boolean isDpkgPresent() {
        return presentInPath("dpkg") && presentInPath("apt-get");
    }

    public static Path getTempDir() {
        return Paths.get(System.getProperty("java.io.tmpdir"));
    }

    public static Path getTempSubDir(String subDir) throws IOException {
        return Files.createTempDirectory(getTempDir(), subDir);
    }

    public static ProcessOutput runCommand(List<String> cmd, boolean verbose,
            long timeout, Map<String, String> env)
                    throws IOException, ExecutionException {
        LOG.log(Level.INFO, "Running command: {0}", cmd);
        // TODO: Process logging
        final AtomicBoolean done = new AtomicBoolean(false);
        final ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.environment().putAll(env);
        final Process process = pb.start();
        final ProcessOutput po = new ProcessOutput(process, verbose);
        ScheduledExecutorService executor = Executors
                .newSingleThreadScheduledExecutor();
        long tick = 100;
        executor.scheduleWithFixedDelay(() -> {
            if (done.get()) {
                executor.shutdown();
            }
        } , 0, tick, TimeUnit.MILLISECONDS);
        executor.schedule(() -> {
            if (!done.get()) {
                executor.shutdown();
                process.destroy();
            }
        } , timeout, TimeUnit.MILLISECONDS);
        try {
            po.waitFor(timeout, TimeUnit.MILLISECONDS);
            LOG.log(Level.INFO,
                    "Command \"{0}\"... finished with exit code \"{1}\"",
                    new Object[] { cmd.get(0), po.exitCode() });
            return po;
        } catch (InterruptedException e) {
            throw new IOException(e);
        } finally {
            done.set(true);
            po.shutdown();
        }
    }

    public static ProcessOutput runCommand(List<String> cmd, boolean verbose,
            long timeout) throws IOException, ExecutionException {
        return runCommand(cmd, verbose, timeout, new HashMap<>());
    }

    public static ProcessOutput runCommand(List<String> cmd, long timeout)
            throws IOException, ExecutionException {
        return runCommand(cmd, true, timeout);
    }

    public static ProcessOutput runCommand(String[] cmd, boolean verbose,
            long timeout) throws IOException, ExecutionException {
        return runCommand(Arrays.asList(cmd), verbose, timeout);
    }

    public static ProcessOutput runCommand(String[] cmd, long timeout)
            throws IOException, ExecutionException {
        return runCommand(cmd, true, timeout);
    }

    public static boolean x64() {
        String arch = System.getenv("PROCESSOR_ARCHITECTURE");
        String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

        String realArch = arch.endsWith("64")
                || wow64Arch != null && wow64Arch.endsWith("64") ? "64" : "32";
        return "64".equals(realArch);
    }

    public static void tryRemoveRecursive(Path path) {
        try {
            removeRecursive(path);
        } catch (IOException ioex) {
            ioex.printStackTrace();
            LOG.log(Level.WARNING,
                    "Failed to cleanup {0}, got an excepion '{1}'.",
                    new Object[] { path, ioex });
        }
    }

    public static void removeRecursive(Path path) throws IOException {
        // removeRecursive(path, false);
        deleteDir(path.toFile());
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    deleteDir(new File(dir, children[i]));
                }
            }
        }
        return dir.delete();
    }

    public static void removeRecursive(Path path, boolean deleteParentDir)
            throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attr) throws IOException {
                System.out.println("FileName: " + file.getFileName());
                if (attr.isRegularFile()) {
                    System.out.println(
                            "Deleting Regular File: " + file.getFileName());
                    boolean deleteIfExists = Files.deleteIfExists(file);
                    if (deleteIfExists) {
                        System.out.println("FileName: " + file.getFileName()
                                + " is deleted successfully" + "time is "
                                + System.currentTimeMillis());
                    }
                    System.out.println("Deleting File, Thread Name is "
                            + Thread.currentThread().getName());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException ex)
                    throws IOException {

                if (!dir.equals(path) || ex == null) {
                    boolean deleteIfExists = Files.deleteIfExists(dir);
                    if (deleteIfExists) {
                        System.out.println("Directory name: "
                                + dir.getFileName() + " deleted successfully"
                                + " time is " + System.currentTimeMillis());
                    } else {
                        System.out.println("Directory name: "
                                + dir.getFileName() + "deletion failed"
                                + " time is " + System.currentTimeMillis());
                    }
                    System.out.println("Deleting Directory, Thread Name is "
                            + Thread.currentThread().getName());
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static String getProgramFilesDirWindows() {
        boolean javaX64 = System.getProperty("os.arch").endsWith("64");
        boolean windowsX64 = x64();
        if (windowsX64 && !javaX64) {
            return System.getenv("ProgramFiles(x86)");
        } else {
            return System.getenv("PROGRAMFILES");
        }
    }

    public static boolean isPlatformX64() {
        String arch = System.getenv("PROCESSOR_ARCHITECTURE");
        String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

        return arch != null && arch.endsWith("64")
                || wow64Arch != null && wow64Arch.endsWith("64");
    }

    public static boolean checkFilesEquality(Path file1, Path file2)
            throws IOException {
        return Files.size(file1) == Files.size(file2) && Arrays
                .equals(Files.readAllBytes(file1), Files.readAllBytes(file2));
    }

    public static boolean checkFileContains(Path file, String regex)
            throws IOException {
        String fileContent = Files.readAllLines(file).stream()
                .collect(Collectors.joining(System.lineSeparator()));
        Matcher regexMatcher = Pattern.compile(regex).matcher(fileContent);
        return regexMatcher.find();
    }

    public static boolean presentInPath(String exec) {
        return Stream
                .of(System.getenv("PATH")
                        .split(Pattern.quote(File.pathSeparator)))
                .map(Paths::get)
                .anyMatch(path -> Files.exists(path.resolve(exec)));
    }

    public static void waitUntil(Supplier<Boolean> condition, long timeout)
            throws TimeoutException {
        waitUntil(condition, timeout, timeEps);
    }

    public static void waitUntil(Supplier<Boolean> condition, long timeout,
            long tick) throws TimeoutException {
        long startTime = System.currentTimeMillis();
        while (!condition.get()
                && System.currentTimeMillis() - startTime <= timeout) {
            try {
                Thread.sleep(tick);
            } catch (InterruptedException e) {
            }
        }
        if (!condition.get()) {
            throw new TimeoutException();
        }
    }

    public static Path createDir(Path dir) throws IOException {
        if (!exists(dir)) {
            createDirectories(dir);
        }
        return dir;
    }
}
