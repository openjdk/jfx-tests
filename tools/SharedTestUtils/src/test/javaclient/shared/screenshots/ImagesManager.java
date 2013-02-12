/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.io.IOException;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.Utils;

/**
 * This singleton manager handles all work with golden screenshots and tests'
 * resulting screenshots
 *
 * @author Sergey
 */
public class ImagesManager {

    /**
     * property for disabling search up root directory of images.
     */
    private static final boolean IS_NOT_LOOK_UP_IMAGES_MODE = Boolean.getBoolean("test.javaclient.notlookupmode");
    private static final String PREDEFINED_RESULT_PATH = "build" + File.separator;
    private static final String IMAGES_OUTPUTPATH_PROP = "imageutils.outputpath";
    private static final String IMAGE_POSTFIX = ".png";
    private static final String HTML_POSTFIX = ".html";
    private static final String DESCRIPTION_POSTFIX = ".dsc";
    private static final String PRISM_ORDER_PROPERTY = "prism.order";
    private static final String DEFAULT_PRISM_ORDER_FOLDER_NAME = "prism";
    private static final String DEFAULT_OS_FOLDER_NAME = "seven";
    private static final String PRISM_ORDER_J2D_FOLDER_NAME = "j2d";
    private static final String EMBEDDED_DFB_PLATFORM_NAME = "dfb";
    private static final String EMBEDDED_EGLFB_PLATFORM_NAME = "eglfb";
    private static final String ROOT_IMAGES_DIRECTORY = "images-svn";
    private final static ImagesManager INSTANCE = new ImagesManager();
    private File goldenImagesPath;
    private File imagesOutputPath;
    private String baseDir = ".";
    private String absoluteDir = null;
    private String projectName;

    public ImagesManager() {
        update();
    }

    public static ImagesManager getInstance() {
        return INSTANCE;
    }

    /**
     * Set's the base directory to for screenshots work. Shouldn't be called for
     * desktop run.
     *
     * @param baseDir base directory, root of the test suite folder
     */
    public void setBaseDir(String baseDir) {
        System.err.println("Setting baseDir to " + baseDir);
        this.baseDir = baseDir;
        update();
    }

    /**
     * Set's the absolute directory to for screenshots work. Shouldn't be called
     * for desktop run.
     *
     * @param absoluteDir absolute directory, where whill be result screenshots
     */
    public void setAbsoluteDir(String absoluteDir) {
        System.err.println("Setting absoluteDir to " + absoluteDir);
        this.absoluteDir = absoluteDir;
        update();
    }

    /**
     * Finds out golden screenshot location based on name and current settings.
     * Automatically takes in account current prism.order, operating system and
     * suite name.
     *
     * @param name golden screenshot name without extension
     * @return absolute path to searched screenshot or null if nothing found
     */
    public String lookupGoldenScreenshot(String name) {
        return handleFileNames(goldenImagesPath, name, IMAGE_POSTFIX, true);
    }

    public String lookupGoldenDescription(String name) {
        return handleFileNames(goldenImagesPath, name, DESCRIPTION_POSTFIX, true);
    }

    /**
     * Return path to image where screenshot should be stored
     *
     * @param name screenshot name without extension
     * @return absolute path to target file
     */
    public String getScreenshotPath(String name) {
        return handleFileNames(imagesOutputPath, name, IMAGE_POSTFIX, false);
    }

    public String getHTMLPath(String name) {
        return handleFileNames(imagesOutputPath, name, HTML_POSTFIX, false);
    }

    public String getDescriptionPath(String name) {
        return handleFileNames(imagesOutputPath, name, DESCRIPTION_POSTFIX, false);
    }

    private void update() {
        try {
            projectName = new File(new File(baseDir).getCanonicalPath()).getName();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        goldenImagesPath = new File(computeGoldenImagePath());
        try {
            goldenImagesPath = goldenImagesPath.getCanonicalFile();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            reportWrongPath("golden images", goldenImagesPath);
        }

        if (isValidPath(goldenImagesPath)) {
            System.out.println("Golden images will be loaded from " + goldenImagesPath.getAbsolutePath());
        } else {
            reportWrongPath("golden images", goldenImagesPath);
        }

        if (absoluteDir == null) {

            String outImagesPath = System.getProperty(IMAGES_OUTPUTPATH_PROP); //getenv

            imagesOutputPath = outImagesPath == null
                    ? new File(baseDir + File.separator + PREDEFINED_RESULT_PATH + File.separator)
                    : new File(outImagesPath + File.separator);
        } else {
            imagesOutputPath = new File(absoluteDir);
        }
        try {
            imagesOutputPath = imagesOutputPath.getCanonicalFile();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
            reportWrongPath("output images", imagesOutputPath);
        }

        if (isValidPath(imagesOutputPath)) {
            System.out.println("Result images will be saved to " + imagesOutputPath.getAbsolutePath());
        } else {
            reportWrongPath("output images", imagesOutputPath);
        }
    }

    private String handleFileNames(File filePath, String suffix, String image_postfix, boolean doSearch) {
        if (null == filePath) {
            reportWrongPath("screenshot", filePath);
            return null;
        }
        if (IS_NOT_LOOK_UP_IMAGES_MODE || !doSearch) {
            return ((filePath == null) ? null : new File(filePath, suffix + image_postfix).getAbsolutePath());
        }
        return searchImage(suffix, image_postfix, filePath);
    }

    private static String searchImage(String suffix, String image_postfix, File filePath) {
        final String imageName = suffix + image_postfix;
        File imageFile;
        File currentDirectory = filePath;
        do {
            System.out.println("Go to " + currentDirectory.getAbsolutePath());
            imageFile = new File(currentDirectory, imageName);
            currentDirectory = currentDirectory.getParentFile();
        } while (!imageFile.exists() && currentDirectory != null && !currentDirectory.getName().equals(ROOT_IMAGES_DIRECTORY));
        return imageFile.getAbsolutePath();
    }

    private static boolean isValidPath(File filePath) {
        return filePath != null && filePath.exists() && filePath.isDirectory();
    }

    private static void reportWrongPath(String msg, File filePath) {
        System.err.println("Error: Invalid " + msg + " path [" + (filePath == null ? "null" : filePath.getAbsolutePath())
                + "] Please, refer to README_dev.html for information about images location.");
    }

    private String computeGoldenImagePath() {
        File imageSetDir = new File(baseDir + File.separator + ROOT_IMAGES_DIRECTORY);
        String imageFile = File.separator
                + getProjectName() + File.separator
                + getPrismOrderFolderName() + File.separator
                + osFolder();
        if (imageSetDir.exists() && imageSetDir.isDirectory()) {
            return imageSetDir.getAbsolutePath() + imageFile;
        } else {
            return baseDir + File.separator + ".." + File.separator + ROOT_IMAGES_DIRECTORY + imageFile;
        }
    }

    private static String getPrismOrderFolderName() {
        if (Utils.isJ2D()) {
            return PRISM_ORDER_J2D_FOLDER_NAME;
        } else {
            return DEFAULT_PRISM_ORDER_FOLDER_NAME;
        }
    }

    private String getProjectName() {
        return projectName;
    }

    private static String osFolder() {
        //For embedded mode, to use special folder
        if (TestUtil.isEmbedded()) {
            //dfb or eglfb
            String embPlatform = TestUtil.getEmbeddedFxPlatform();
            //returns dfb or eglfb
            return (embPlatform != null && embPlatform.equals(EMBEDDED_DFB_PLATFORM_NAME) ? EMBEDDED_DFB_PLATFORM_NAME : EMBEDDED_EGLFB_PLATFORM_NAME);
        }

        String os = System.getProperty("os.name").toLowerCase();
        String version = System.getProperty("os.version").toLowerCase();
        if (os.indexOf("win") >= 0) {
            if (version.startsWith("5.")) {
                return "xp";
            } else if (version.startsWith("6.")) {
                return "seven";
            }
        } else if (os.indexOf("mac") >= 0) {
            return "mac";
        } else if ((os.indexOf("linux") >= 0) || (os.indexOf("ubuntu") >= 0)) {
            return "linux";
        }
        return DEFAULT_OS_FOLDER_NAME;
    }
}
