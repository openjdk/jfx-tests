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
 *
 * @author shubov
 */

public class GoldenImagePath {
   
    private static final String PRISM_ORDER_J2D_FOLDER_NAME = "j2d";
    private static final String DEFAULT_PRISM_ORDER_FOLDER_NAME = "prism";
    private static final String EMBEDDED_DFB_PLATFORM_NAME = "dfb";
    private static final String EMBEDDED_EGLFB_PLATFORM_NAME = "eglfb";
    private static final String DEFAULT_OS_FOLDER_NAME = "seven";
    static final String WIN_8_FOLDER = "eight";
    static final String WIN_8_OS_VERSION = "6.2";
    static final String WIN_81_OS_VERSION = "6.3";
    
    
    
    
    private static String getPrismOrderFolderName() {
        if (Utils.isJ2D()) {
            return PRISM_ORDER_J2D_FOLDER_NAME;
        } else {
            return DEFAULT_PRISM_ORDER_FOLDER_NAME;
        }
    }
    
    private static String getProjectName(final String _baseDir) {
        String projectName = null;
        try {
            projectName = new File(new File(_baseDir).getCanonicalPath()).getName();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return projectName;
    }
    
    public static String getGoldenImagePath(final String _baseDir,
                                          final String _ROOT_IMAGES_DIRECTORY) {
        final boolean inUpLevel = true;
        return getGoldenImagePathCommon(_baseDir, _ROOT_IMAGES_DIRECTORY , inUpLevel);
    }
    public static String getGoldenImagePath2(final String _baseDir,
                                          final String _ROOT_IMAGES_DIRECTORY) {
        final boolean inUpLevel = false;
        return getGoldenImagePathCommon(_baseDir, _ROOT_IMAGES_DIRECTORY , inUpLevel);
    }
            
    private static String getGoldenImagePathCommon(final String _baseDir,
                                          final String _ROOT_IMAGES_DIRECTORY,
                                          final boolean inUpLevel
            ) {

        final String oldLookAndFeelName = "caspian";
        final String lfProp = System.getProperty("javafx.userAgentStylesheetUrl");

        String result = _baseDir;
        if (inUpLevel) {
            result = result + File.separator + "..";
        }
        result = result + File.separator + _ROOT_IMAGES_DIRECTORY + File.separator;
        
        if (Utils.isCaspian()) {
            result += oldLookAndFeelName + File.separator;
        }

        result +=
                  getProjectName(_baseDir) + File.separator
                + getPrismOrderFolderName() + File.separator
                + osFolder();

        return result;
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
            } else if (version.startsWith(WIN_8_OS_VERSION)
                    || version.startsWith(WIN_81_OS_VERSION)) {
                return WIN_8_FOLDER;
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
