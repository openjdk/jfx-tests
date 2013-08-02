/*
 * Copyright (c) 2010-2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
   // private static final String PRISM_ORDER_PROPERTY = "prism.order";
    private static final String ROOT_IMAGES_DIRECTORY = "images-svn";
    private final static ImagesManager INSTANCE = new ImagesManager();
    private File goldenImagesPath;
    private File imagesOutputPath;
    private String baseDir = ".";
    private String absoluteDir = null;
    private String projectName;
    private boolean needUpdate = true;

    public ImagesManager() {
      needUpdate = true;
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
        needUpdate = true;
    }

    /**
     * Set's the absolute directory to for screenshots work. Shouldn't be called for
     * desktop run.
     *
     * @param absoluteDir absolute directory, where whill be result screenshots
     */
    public void setAbsoluteDir(String absoluteDir) {
        System.err.println("Setting absoluteDir to " + absoluteDir);
        this.absoluteDir = absoluteDir;
        needUpdate = true;
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
        update();
        return handleFileNames(goldenImagesPath, name, IMAGE_POSTFIX, true);
    }

    public String lookupGoldenDescription(String name) {
        update();
        return handleFileNames(goldenImagesPath, name, DESCRIPTION_POSTFIX, true);
    }

    /**
     * Return path to image where screenshot should be stored
     *
     * @param name screenshot name without extension
     * @return absolute path to target file
     */
    public String getScreenshotPath(String name) {
        update();
        return handleFileNames(imagesOutputPath, name, IMAGE_POSTFIX, false);
    }

    public String getHTMLPath(String name) {
        update();
        return handleFileNames(imagesOutputPath, name, HTML_POSTFIX, false);
    }

    public String getDescriptionPath(String name) {
        update();
        return handleFileNames(imagesOutputPath, name, DESCRIPTION_POSTFIX, false);
    }

    private void update() {
        if (needUpdate) {
        goldenImagesPath = new File(GoldenImagePath.getGoldenImagePath(baseDir, ROOT_IMAGES_DIRECTORY));
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
        needUpdate = false;
        }
        
    }

    private String handleFileNames(File filePath, String suffix, String image_postfix, boolean doSearch) {
        if (null == filePath) {
            reportWrongPath("screenshot", filePath);
            return null;
        }
        if (IS_NOT_LOOK_UP_IMAGES_MODE || !doSearch) {
            return ( new File(filePath, suffix + image_postfix).getAbsolutePath());
        }
        return searchImage(suffix, image_postfix, filePath);
    }

    private static String searchImage(String suffix, String image_postfix, File filePath) {
        final String imageName = suffix + image_postfix;
        File imageFile;
        File currentDirectory = filePath;
        do {
            System.out.println("Go_to " + currentDirectory.getAbsolutePath() + "/ currDir=" + currentDirectory);
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

}
