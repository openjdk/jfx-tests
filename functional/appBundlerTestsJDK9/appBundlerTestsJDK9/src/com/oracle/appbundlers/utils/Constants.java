/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import java.io.File;

/**
 * @author Ramesh BG
 *
 */
public interface Constants {
    String MENU_HINT = "menuHint";
    String SERVICE_HINT = "daemon";
    String START_ON_INSTALL = "startOnInstall";
    String RUN_AT_STARTUP = "runAtStartup";
    String USER_JVM_OPTIONS = "userJvmOptions";
    String SHORTCUT_HINT = "shortcutHint";
    String VENDOR = "Vendor";
    String VERSION = "appVersion";
    String SYSTEM_WIDE = "systemWide";
    String EXE_SYSTEM_WIDE = "win.exe.systemWide";
    String TITLE = "Title";
    String MENU_GROUP = "win.menuGroup";
    String DESCRIPTION = "description";
    String MAIN_JAR = "mainJar";
    String CLASSPATH = "classpath";
    String ICON = "icon";
    String IDENTIFIER = "identifier";
    String JVM_OPTIONS = "jvmOptions";
    String JVM_PROPERTIES = "jvmProperties";
    String EMAIL = "email";
    String CATEGORY = "applicationCategory";
    String APP_CDS_CACHE_MODE = "commercial.AppCDS.cache";
    String COPYRIGHT = "copyright";
    String LICENSE_FILE = "licenseFile";
    String FA_EXTENSIONS = "fileAssociation.extension";
    String FA_ICON = "fileAssociation.icon";
    String UNLOCK_COMMERCIAL_FEATURES = "commercialFeatures";
    String ENABLE_APP_CDS = "commercial.AppCDS";
    String LICENSE_TYPE = "licenseType";
    String PREFERENCES_ID = "preferencesID";
    String MAC_APP_STORE_APP_SIGNING_KEY = "mac.signing-key-app";
    String MAC_APP_STORE_ENTITLEMENTS = "mac.app-store-entitlements";
    String MAC_APP_STORE_PKG_SIGNING_KEY = "mac.signing-key-pkg";
    String MAC_CATEGORY = "mac.category";
    String MAC_CF_BUNDLE_NAME = "mac.CFBundleName";
    String SIGNING_KEY_USER = "mac.signing-key-user-name";
    String LinuxDebBundler_BUNDLE_NAME = "linux.bundleName";
    String MAINTAINER = "linux.deb.maintainer";
    String LinuxRpmBundler_BUNDLE_NAME = "linux.bundleName";
    String MSI_SYSTEM_WIDE = "systemWide";
    String APP_CDS_CLASS_ROOTS = "commercial.AppCDS.classRoots";
    String SIGNING_KEYCHAIN = "mac.signing-keychain";
    String SIMPLE_DMG = "mac.dmg.simple";
    String ARGUMENTS = "arguments";
    String UPGRADE_UUID = "win.msi.upgradeUUID";
    String FA_DESCRIPTION = "fileAssociation.description";
    String FA_CONTENT_TYPE = "fileAssociation.contentType";
    String FILE_ASSOCIATIONS = "fileAssociations";
    String APP_NAME = "name";
    String SECONDARY_LAUNCHERS = "secondaryLaunchers";
    String ADD_MODS = "add-modules";
    String LIMIT_MODS = "limit-modules";
    String MODULEPATH = "module-path";
    String STRIP_NATIVE_COMMANDS = "strip-native-commands";
    String SPACE = " ";
    char QUOTE = '\"';
    String DETECT_JRE_MODS = "detectjremods";
    String JDKMODULEPATH = "jdkmodulepath";
    String APPCLASS = "appclass";
    String APPLICATION_CLASS = "applicationClass";
    String WORK_DIRECTORY = "testBundlerWorkDir";
    /*
     * class names
     */
    String COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS = "com.shape.serviceinterface.ShapeMainClass";
    String COM_SHAPE_SERVICEINTERFACE_SHAPE_TEMPLATE = "com.shape.serviceinterface.Shape.template";
    String COM_SHAPE_SERVICEINTERFACE_SHAPE_CLASS_NAME = "com.shape.serviceinterface.Shape";
    String COM_SHAPE_SERVICEINTERFACE_MODULE_NAME = "com.shape.serviceinterface";
    String COM_SHAPE_SERVICEINTERFACE_MODULE_INFO_TEMPLATE = "com.shape.serviceinterface.module.info.template";
    String COM_SHAPE_SERVICEPROVIDER_CIRCLE_MODULENAME = "com.shape.serviceprovider.circle";
    String COM_SHAPE_SERVICEPROVIDER_CIRCLE_MODULE_INFO_TEMPLATE = "com.shape.serviceprovider.circle.module.info.template";

    String HELLO_WORLD_OUTPUT = "Hello World initialized!";
    String COM_SHAPE_TEST_LIMITMODSMAINCLASS = "com.shape.test.LimitModsMainClass";
    String COM_SHAPE_TEST_LIMITMODSMAINCLASS_TEMPLATE = "com.shape.test.LimitModsMainClass.template";
    String COM_SHAPE_SERVICEPROVIDER_CIRCLE_TEMPLATE = "com.shape.serviceprovider.Circle.template";
    String COM_SHAPE_SERVICEPROVIDER_CIRCLE_CLASSNAME = "com.shape.serviceprovider.circle.Circle";
    String COM_SHAPE_TEST_MODULE_NAME = "com.shape.test";
    String COM_SHAPE_TEST_MODULE_INFO_TEMPLATE = "com.shape.test.module.info.template";
    String CIRCLE_OUTPUT = "This is Circle";

    String COM_SHAPE_SERVICEPROVIDER_RECTANGLE_MODULE_NAME = "com.shape.serviceprovider.rectangle";
    String COM_SHAPE_SERVICEPROVIDER_RECTANGLE_MODULE_INFO_TEMPLATE = "com.shape.serviceprovider.rectangle.module.info.template";
    String COM_SHAPE_SERVICEPROVIDER_RECTANGLE_TEMPLATE = "com.shape.serviceprovider.Rectangle.template";
    String COM_SHAPE_SERVICEPROVIDER_RECTANGLE_CLASS_NAME = "com.shape.serviceprovider.rectangle.Rectangle";
    String COM_SHAPE_SERVICEINTERFACE_SHAPEMAINCLASS_TEMPLATE = "com.shape.serviceinterface.ShapeMainClass.template";
    String EXAMPLE_BUNDLER_TEMPLATE ="ExampleBundler.java.template";
    String EXAMPLE_BUNDLERS_TEMPLATE = "ExampleBundlers.java.template";
    String TESTAPP_EXAMPLE_BUNDLER="testapp.ExampleBundler";
    String TESTAPP_EXAMPLE_BUNDLERS="testapp.ExampleBundlers";
    String RECTANGLE_OUTPUT = "This is Rectangle";
    String ALL_MODULE_PATH = "ALL-MODULE-PATH";
    String FXAPP_JAVA_TEMPLATE = "FXApp.java.template";
    String COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME = "com.greetings.App1";
    String COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME = "com.greetings.App2";
    String COM_GREETINGS_APP3_QUALIFIED_CLASS_NAME = "com.greetings.App3";
    String COM_GREETINGS_JAR_NAME = "com.greetings";
    String JMODS_PATH_IN_JDK = String.join(File.separator, System.getProperty("java.home"), "jmods");
    String COM_GREETINGS_MODULE_INFO_TEMPLATE = "com.greetings.module.info.template";
    String SYSTEM_OUT_PRINTLN = "System.out.println";
    String COM_GREETINGS_MODULE_CUM_PACKAGE_NAME = "com.greetings";
    String PRINTLN_STATEMENT = "%PRINTLN%";
    String APP_NAME_REPLACEMENT_STATEMENT = "%APP_NAME%";
    String PACKAGE_NAME_STATEMENT = "%PACKAGE_NAME%";
    String CLASS_NAME_STATEMENT = "%CLASS_NAME%";
    String PASS_STRING_REPLACEMENT_STATEMENT = "%PASS_STRING%";
    String DEPENDENT_MODULE = "%REQUIRED_MODULE%";
    String OPTION_PREFIX = "sqe";
    String PREFIX = "%PREFIX%";
    String PASS_1 = "PASS_1";
    String CUSTOM_UTIL_MODULE_NAME = "custom.util";
    String CUSTOM_UTIL_MODULE_TEMPLATE_FILE_NAME = "util.module.template";
    String CUSTOM_UTIL_JAVA_TEMPLATE = "Util.java.template";
    String CUSTOM_UTIL_CLASS_NAME = "testapp.util.Util";
    String CUSTOM_UTIL_CLASS_FULLY_QUALIFIED_NAME = "custom.util.testapp.util.Util";
    String CUSTOM_UTIL_CLASS_SIMPLE_NAME = "Util";
    String CUSTOM_UTIL_PRINTLN_STATEMENT = "testapp.util.Util.println";
    String CUSTOM_UTIL_PACKAGE_STATEMENT = "testapp.util";
    String CUSTOM_UTIL_UNNAMED_MODULE_FULLY_QUALIFIED_CLASS_NAME = "testapp.util.Util";
    String CUSTOM_UTIL_UNNAMED_MODULE_PACKAGE_STATEMENT = "testapp.util";
    String INSTALLDIR_CHOOSER = "installdirChooser";
    String CUSTOM_UTIL_APPEND_CLASS_NAME_PRINT_METHOD = "testapp.util.Util.appendClassNameToPrint";
    String MAIN_MODULE = "module";
    String APP1_NAME = "App1";
    String APP_RESOURCES = "appResources";
    String COPYRIGHT_VALUE = "Copyright (c) 2011, 2016 Oracle and/or its affiliates. All rights reserved.";
    String LICENSE_TYPE_VALUE = "SQE GPL v.100.500";
    String PASS_2 = "PASS_2";
    String PASS_3 = "PASS_3";
    String APP2_NAME = "App2";
    String APP3_NAME = "App3";
    String packageName = "testapp";
    /*
     * MODULE RELATED PARAMETERS in AppWrapper
     */
    String BUNDLES = "bundles";
    String SOURCE = "src";
    String BIN = "bin";
    String JARS = "jars";
    String EXPLODED_MODS_DIR = "explodedmods";
    String JMODS_DIR = "jmods";
    String MODULAR_JARS_DIR = "modjars";
    String APP1_FULLNAME = packageName + "." + APP1_NAME;
    String APP2_FULLNAME = packageName + "." + APP2_NAME;
    String DOUBLE_HYPHEN ="--";
    String RUNTIME = "runtime";
    String MODULE_INFO_DOT_JAVA = "module-info.java";

}
