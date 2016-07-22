/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 */
package test.javaclient.shared.screenshots;

import com.sun.prism.GraphicsPipeline;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author andrey.rusakov@oracle.com
 */
public class GoldenImageManager {

    private static final String IMG_VERSION = "-\\d+";
    private static final String IMG_FOLDER = "images-svn";

    private static File getImagesRoot() throws IOException {
        return new File("../", IMG_FOLDER).getCanonicalFile();
    }

    private static String getSuiteName() throws IOException {
        return new File(".").getCanonicalFile().getName();
    }

    //Requires -XaddExports:javafx.graphics/com.sun.prism=ALL-UNNAMED to work in JDK9
    private static String getRenderingMode() {
        GraphicsPipeline pipeline = com.sun.prism.GraphicsPipeline.getPipeline();
        return pipeline.getClass().getName().endsWith("J2DPipeline")
                || pipeline.getClass().getName().endsWith("SWPipeline") ? "j2d" : "prism";
    }

    private static String getPlatformAlias() {
        String os = System.getProperty("os.name").toLowerCase();
        String version = System.getProperty("os.version").toLowerCase();
        if (os.contains("win")) {
            if (version.startsWith("5.")) {
                return "xp";
            } else if (version.startsWith("6.2")
                    || version.startsWith("6.3")) {
                return "eight";
            } else if (version.startsWith("10.")) {
                return "ten";
            }

        } else if (os.contains("mac")) {
            return "mac";
        } else if (os.contains("linux") || os.contains("ubuntu")) {
            return "linux";
        }
        return "";
    }
    
    public static String getDiffPath(String goldenPath) {
        String relativeGoldenPath = goldenPath.substring(goldenPath.indexOf(IMG_FOLDER) +
                IMG_FOLDER.length());
        String goldenName = relativeGoldenPath.replace(File.pathSeparator, "_");
        String diffName = goldenName.substring(0, goldenName.lastIndexOf(".") - 1) + "-diff";
        return getScreenshotPath(diffName);
    }
    
    public static String getScreenshotPath(String testName) {
        return getGoldenPath(testName, "png");
    }

    public static String getGoldenPath(String testName, String extension) {
        String imagesOutputPath = System.getProperty("imageutils.outputpath");
        if (imagesOutputPath == null) {
            imagesOutputPath = Paths.get(".", "build").toString();
        }
        try {
            return new File(imagesOutputPath, testName + "." + extension).getCanonicalFile().toString();
        } catch (IOException ex) {
            System.err.println("Failed to get screenshot path: " + ex.getMessage());
        }
        return null;
    }

    public static List<String> getTestImages(String testName) {
        return getTestImages(testName, ".png");
    }

    /**
     * Retrieves images from reference image folder according to suffix (usually test name) and 
     * postfix (usually file extension).
     * Files should have name that matches "suffix[-number-]postfix", e.g: "SomeTestName.png",
     * "SomeTestName-1.png"... "SomeTestName-NUMBER.png".  Method searches for all matching files
     * in "TestsuiteName/RenderingMode/Platform", "TestsuiteName/RenderingMode/" and
     * "TestsuiteName/".
     * 
     * @param suffix filename suffix, usually test name.
     * @param postfix filename prefix, usually file extension.
     * @return Full paths to all matching files or empty list if no files matched.
     */
    public static List<String> getTestImages(String suffix, String postfix) {
        File imageRoot;
        String suiteName;
        try {
            imageRoot = getImagesRoot();
                    System.out.println(imageRoot.getCanonicalPath());
        } catch (IOException ex) {
            System.err.printf("Failed to get golden images folder path: %s\n", ex.getMessage());
            return Collections.emptyList();
        }
        try {
            suiteName = getSuiteName();
        } catch (IOException ex) {
            System.err.printf("Failed to get testsuite name: %s\n", ex.getMessage());
            return Collections.emptyList();
        }
        File currentDirectory = new File(imageRoot,
                Paths.get(suiteName, getRenderingMode(), getPlatformAlias()).toString());
        List<String> goldenVariants = new ArrayList<>();        
        while (!currentDirectory.equals(imageRoot)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory.toPath())) {
                stream.forEach(path -> {
                    String name = path.getFileName().toString();
                    if (name.startsWith(suffix) && name.endsWith(postfix)) {
                        String imageVersion = name.substring(suffix.length(), name.lastIndexOf(postfix));
                        if (imageVersion.isEmpty() || imageVersion.matches(IMG_VERSION)) {
                            goldenVariants.add(path.toAbsolutePath().toString());
                        }
                    }
                });
            } catch (IOException ex) {
                System.err.printf("Failed to enumerate golden images at %s: %s\n", 
                        currentDirectory, ex);
            }
            currentDirectory = currentDirectory.getParentFile();
        }
        System.out.printf("Found %d acceptable golden image(s)\n", goldenVariants.size());
        goldenVariants.forEach(System.out::println);
        return goldenVariants;
    }
}
