/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ramesh BG
 *
 */
public class SourceFactory implements Constants {

    private SourceFactory() {

    }

    /*
     * com.greetings module
     */

    public static Source get_com_greetings_module() throws IOException {
        return get_com_greetings_module(Collections.emptyMap(),
                Collections.emptyMap(), null);
    }

    /*
     * com.greetings module
     */

    public static Source get_com_greetings_module(
            Map<String, String> classNameToTemplateMap,
            String mainClassfullyQualifiedName,
            Map<String, Map<String, String>> classNameToReplacementsInSrcMap)
                    throws IOException {
        String mainClassfullyQualifiedNameInternal = COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME;

        if (mainClassfullyQualifiedName != null) {
            mainClassfullyQualifiedNameInternal = mainClassfullyQualifiedName;
        }

        Map<String, Map<String, String>> classNameToReplacementsInSrcMapInternal = new HashMap<String, Map<String, String>>();
        int count = 1;
        for (String className : classNameToReplacementsInSrcMap.keySet()) {
            Map<String, String> commonReplacementsForEachClass = new HashMap<String, String>();
            commonReplacementsForEachClass.put(PRINTLN_STATEMENT,
                    SYSTEM_OUT_PRINTLN);
            commonReplacementsForEachClass.put(PASS_STRING_REPLACEMENT_STATEMENT,
                    "PASS_"+count++);
            commonReplacementsForEachClass.put(PACKAGE_NAME_STATEMENT,
                    COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
            commonReplacementsForEachClass.put(PREFIX, OPTION_PREFIX);
            commonReplacementsForEachClass.put(APP_NAME_REPLACEMENT_STATEMENT, className);
            classNameToReplacementsInSrcMapInternal.put(className, commonReplacementsForEachClass);
        }

        Map<String, String> moduleInfoReplacements = new HashMap<>();
        moduleInfoReplacements.put(DEPENDENT_MODULE, "");
        moduleInfoReplacements.put(PACKAGE_NAME_STATEMENT, COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
        classNameToReplacementsInSrcMapInternal.put(MODULE_INFO_DOT_JAVA, moduleInfoReplacements);

        return new Source(COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                COM_GREETINGS_MODULE_INFO_TEMPLATE,
                classNameToTemplateMap,
                mainClassfullyQualifiedNameInternal,
                classNameToReplacementsInSrcMapInternal, COM_GREETINGS_JAR_NAME);
    }

    /*
     * com.greetings module
     */

    public static Source get_com_greetings_module(
            Map<String, String> classnameToTemplateMap,
            Map<String, String> replacementsInSourceCode, String mainClassName)
                    throws IOException {

        String mainClassInternal = COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME;

        if (mainClassName != null) {
            mainClassInternal = mainClassName;
        }

        Map<String, String> classNameToTemplateMapInternal = new HashMap<String, String>();
        classNameToTemplateMapInternal.put(mainClassInternal,FXAPP_JAVA_TEMPLATE);
        classNameToTemplateMapInternal.putAll(classnameToTemplateMap);

        Map<String, String> replacementsInSourceCodeInternal = new HashMap<String, String>();
        replacementsInSourceCodeInternal.put(PRINTLN_STATEMENT,
                SYSTEM_OUT_PRINTLN);
        replacementsInSourceCodeInternal.put(APP_NAME_REPLACEMENT_STATEMENT,
                APP1_NAME);
        replacementsInSourceCodeInternal.put(PASS_STRING_REPLACEMENT_STATEMENT,
                PASS_1);
        replacementsInSourceCodeInternal.put(PACKAGE_NAME_STATEMENT,
                COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
        replacementsInSourceCodeInternal.put(DEPENDENT_MODULE, "");
        replacementsInSourceCodeInternal.put(PREFIX, OPTION_PREFIX);

        replacementsInSourceCodeInternal.putAll(replacementsInSourceCode);

        return new Source(COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                COM_GREETINGS_MODULE_INFO_TEMPLATE,
                classNameToTemplateMapInternal, mainClassInternal,
                COM_GREETINGS_JAR_NAME, replacementsInSourceCodeInternal, true);
    }

    /*
     * com.greetings module
     */

    public static Source get_com_greetings_module(
            Map<String, String> classNameToTemplateMap,
            Map<String, String> replacementsInSourceCode) throws IOException {
        return get_com_greetings_module(classNameToTemplateMap,
                replacementsInSourceCode, null);
    }

    /*
     * @return TempSource com.greetings module depends on custom.util
     * com.greetings ---> custom.util
     */
    public static Source get_com_greetings_module_depends_on_custom_util_module()
            throws IOException {
        Map<String, String> replacementsInSourceCode = new HashMap<>();
        replacementsInSourceCode.put(DEPENDENT_MODULE, "requires custom.util;");
        replacementsInSourceCode.put(PRINTLN_STATEMENT,
                CUSTOM_UTIL_PRINTLN_STATEMENT);

        return SourceFactory.get_com_greetings_module(Collections.emptyMap(),
                replacementsInSourceCode);
    }

    /*
     * custom.util module
     */
    public static Source get_custom_util_module() throws IOException {
        Map<String, String> classNameToTemplateMap = new HashMap<String, String>();
        classNameToTemplateMap.put(CUSTOM_UTIL_CLASS_NAME,CUSTOM_UTIL_JAVA_TEMPLATE);

        Map<String, String> replacementsInSourceCode = new HashMap<String, String>();
        replacementsInSourceCode.put(PRINTLN_STATEMENT,
                CUSTOM_UTIL_PRINTLN_STATEMENT);
        replacementsInSourceCode.put(APP_NAME_REPLACEMENT_STATEMENT, APP1_NAME);
        replacementsInSourceCode.put(PASS_STRING_REPLACEMENT_STATEMENT, PASS_1);
        replacementsInSourceCode.put(PACKAGE_NAME_STATEMENT,
                CUSTOM_UTIL_PACKAGE_STATEMENT);
        replacementsInSourceCode.put(CLASS_NAME_STATEMENT,
                CUSTOM_UTIL_CLASS_SIMPLE_NAME);

        return new Source(CUSTOM_UTIL_MODULE_NAME,
                CUSTOM_UTIL_MODULE_TEMPLATE_FILE_NAME, classNameToTemplateMap,
                CUSTOM_UTIL_CLASS_FULLY_QUALIFIED_NAME, CUSTOM_UTIL_MODULE_NAME,
                replacementsInSourceCode);
    }

    /*
     * com.shape.serviceinterface module
     */
    public static Source get_com_shape_serviceinterface_module(
            Map<String, String> templateToClassNameMap,
            Map<String, String> replacementsInSourceCode,
            String mainClassFullName) throws IOException {

        String mainClassFullNameInternal = COM_SHAPE_SERVICEINTERFACE_SHAPE_CLASS_NAME;
        if (mainClassFullName != null) {
            mainClassFullNameInternal = mainClassFullName;
        }
        Map<String, String> classNameToTemplateMapInternal = new HashMap<String, String>();
        classNameToTemplateMapInternal.put(COM_SHAPE_SERVICEINTERFACE_SHAPE_CLASS_NAME,
                COM_SHAPE_SERVICEINTERFACE_SHAPE_TEMPLATE
                );
        classNameToTemplateMapInternal.putAll(templateToClassNameMap);

        return new Source(COM_SHAPE_SERVICEINTERFACE_MODULE_NAME,
                COM_SHAPE_SERVICEINTERFACE_MODULE_INFO_TEMPLATE,
                classNameToTemplateMapInternal, mainClassFullNameInternal,
                COM_SHAPE_SERVICEINTERFACE_MODULE_NAME, Collections.emptyMap(),
                true);
    }

    /*
     * com.shape.serviceinterface module Main Module
     */
    public static Source get_com_shape_serviceinterface_module()
            throws IOException {
        String mainClassFullNameInternal = COM_SHAPE_SERVICEINTERFACE_SHAPE_CLASS_NAME;
        Map<String, String> classNameToTemplateMapInternal = new HashMap<String, String>();
        classNameToTemplateMapInternal.put(COM_SHAPE_SERVICEINTERFACE_SHAPE_CLASS_NAME,
                COM_SHAPE_SERVICEINTERFACE_SHAPE_TEMPLATE
                );

        return new Source(COM_SHAPE_SERVICEINTERFACE_MODULE_NAME,
                COM_SHAPE_SERVICEINTERFACE_MODULE_INFO_TEMPLATE,
                classNameToTemplateMapInternal, mainClassFullNameInternal,
                COM_SHAPE_SERVICEINTERFACE_MODULE_NAME, Collections.emptyMap(),
                true);
    }

    /*
     * com.shape.serviceinterface module
     */
    public static Source get_com_shape_serviceinterface_module(boolean isMainModule)
            throws IOException {
        String mainClassFullNameInternal = COM_SHAPE_SERVICEINTERFACE_SHAPE_CLASS_NAME;
        Map<String, String> classNameToTemplateMapInternal = new HashMap<String, String>();
        classNameToTemplateMapInternal.put(COM_SHAPE_SERVICEINTERFACE_SHAPE_CLASS_NAME,
                COM_SHAPE_SERVICEINTERFACE_SHAPE_TEMPLATE
                );

        return new Source(COM_SHAPE_SERVICEINTERFACE_MODULE_NAME,
                COM_SHAPE_SERVICEINTERFACE_MODULE_INFO_TEMPLATE,
                classNameToTemplateMapInternal, mainClassFullNameInternal,
                COM_SHAPE_SERVICEINTERFACE_MODULE_NAME, Collections.emptyMap(),
                isMainModule);
    }



    /*
     * com.shape.serviceprovider.circle module
     */

    public static Source get_com_shape_serviceprovider_circle_module()
            throws IOException {
        Map<String, String> classNameToTemplateMapInternal = new HashMap<String, String>();
        classNameToTemplateMapInternal.put(COM_SHAPE_SERVICEPROVIDER_CIRCLE_CLASSNAME,
                COM_SHAPE_SERVICEPROVIDER_CIRCLE_TEMPLATE
                );

        return new Source(COM_SHAPE_SERVICEPROVIDER_CIRCLE_MODULENAME,
                COM_SHAPE_SERVICEPROVIDER_CIRCLE_MODULE_INFO_TEMPLATE,
                classNameToTemplateMapInternal,
                COM_SHAPE_SERVICEPROVIDER_CIRCLE_CLASSNAME,
                COM_SHAPE_SERVICEPROVIDER_CIRCLE_MODULENAME,
                Collections.emptyMap());
    }

    /*
     * com.shape.serviceprovider.rectangle module
     */

    public static Source get_com_shape_serviceprovider_rectangle_module()
            throws IOException {
        Map<String, String> classNameToTemplateMapInternal = new HashMap<String, String>();
        classNameToTemplateMapInternal.put(COM_SHAPE_SERVICEPROVIDER_RECTANGLE_CLASS_NAME,
                COM_SHAPE_SERVICEPROVIDER_RECTANGLE_TEMPLATE
                );

        return new Source(COM_SHAPE_SERVICEPROVIDER_RECTANGLE_MODULE_NAME,
                COM_SHAPE_SERVICEPROVIDER_RECTANGLE_MODULE_INFO_TEMPLATE,
                classNameToTemplateMapInternal,
                COM_SHAPE_SERVICEPROVIDER_RECTANGLE_CLASS_NAME,
                COM_SHAPE_SERVICEPROVIDER_RECTANGLE_MODULE_NAME,
                Collections.emptyMap());
    }

    /*
     * com.shape.test module
     */

    public static Source get_com_shape_test_module(boolean isMainModule) throws IOException {
        Map<String, String> classNameToTemplateMapInternal = new HashMap<String, String>();
        classNameToTemplateMapInternal.put(COM_SHAPE_TEST_LIMITMODSMAINCLASS,
                COM_SHAPE_TEST_LIMITMODSMAINCLASS_TEMPLATE
                );

        return new Source(COM_SHAPE_TEST_MODULE_NAME,
                COM_SHAPE_TEST_MODULE_INFO_TEMPLATE,
                classNameToTemplateMapInternal,
                COM_SHAPE_TEST_LIMITMODSMAINCLASS, COM_SHAPE_TEST_MODULE_NAME,
                Collections.emptyMap(), isMainModule);
    }

    /*
     * unnamed module com.greetings
     */
    public static Source get_com_greetings_app_unnamed_module()
            throws IOException {
        return get_com_greetings_app_unnamed_module(Collections.emptyMap());
    }

    /*
     * unnamed module com.greetings
     */
    public static Source get_com_greetings_app_unnamed_module(
            Map<String, String> replacementsInSourceCode) throws IOException {

        return get_com_greetings_app_unnamed_module(
                COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                replacementsInSourceCode);
    }

    /*
     * unnamed module com.greetings
     */
    public static Source get_com_greetings_app_unnamed_module(String fullName,
            Map<String, String> replacementsInSourceCode) throws IOException {

        Map<String, String> replacementsInSourceCodeInternal = new HashMap<String, String>();
        replacementsInSourceCodeInternal.put(PRINTLN_STATEMENT,
                SYSTEM_OUT_PRINTLN);
        replacementsInSourceCodeInternal.put(APP_NAME_REPLACEMENT_STATEMENT,
                APP1_NAME);
        replacementsInSourceCodeInternal.put(PASS_STRING_REPLACEMENT_STATEMENT,
                PASS_1);
        replacementsInSourceCodeInternal.put(PACKAGE_NAME_STATEMENT,
                COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
        replacementsInSourceCodeInternal.put(PREFIX, OPTION_PREFIX);
        replacementsInSourceCodeInternal.putAll(replacementsInSourceCode);

        return new Source(fullName, FXAPP_JAVA_TEMPLATE,
                COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                replacementsInSourceCodeInternal);

    }

    /*
     * testapp.util.Util unnamed module
     */
    public static Source get_test_app_util_unnamed_module() throws IOException {
        Map<String, String> replacementsInSourceCode = new HashMap<String, String>();
        replacementsInSourceCode.put(PRINTLN_STATEMENT,
                CUSTOM_UTIL_PRINTLN_STATEMENT);
        replacementsInSourceCode.put(PACKAGE_NAME_STATEMENT,
                CUSTOM_UTIL_UNNAMED_MODULE_PACKAGE_STATEMENT);
        replacementsInSourceCode.put(CLASS_NAME_STATEMENT,
                CUSTOM_UTIL_CLASS_SIMPLE_NAME);

        return new Source(CUSTOM_UTIL_UNNAMED_MODULE_FULLY_QUALIFIED_CLASS_NAME,
                CUSTOM_UTIL_JAVA_TEMPLATE, CUSTOM_UTIL_CLASS_SIMPLE_NAME,
                replacementsInSourceCode);
    }

    /*
     * com.greetings unnamed module depends on testapp.util unnamed module
     */
    public static Source get_com_greetings_unnamed_module_depends_on_test_app_util_unnamed_module()
            throws IOException {
        Map<String, String> replacementsInSourceCode = new HashMap<String, String>();
        replacementsInSourceCode.put(PRINTLN_STATEMENT,
                CUSTOM_UTIL_PRINTLN_STATEMENT);

        return SourceFactory
                .get_com_greetings_app_unnamed_module(replacementsInSourceCode);
    }

    /*
     * com.greetings unnamed module depends on testapp.util unnamed module
     */
    public static Source get_com_greetings_unnamed_module_depends_on_testapp_util_unnamed_module(
            Map<String, String> replacementsInSourceCode) throws IOException {
        Map<String, String> replacementsInSourceCodeInternal = new HashMap<String, String>();
        replacementsInSourceCodeInternal.put(PRINTLN_STATEMENT,
                CUSTOM_UTIL_PRINTLN_STATEMENT);
        replacementsInSourceCodeInternal.putAll(replacementsInSourceCode);

        return SourceFactory.get_com_greetings_app_unnamed_module(
                replacementsInSourceCodeInternal);
    }
}
