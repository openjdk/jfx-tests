/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality.unicode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.functionality.CommandLineArgumentsTest;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

/**
 * CommandLineArgumentsTest applied for Unicode
 */

public class UnicodeCommandLineArgumentsTest extends CommandLineArgumentsTest {
    @Override
    public List<String> args() {
        return Arrays.asList("Ñ‡Ñ‚Ð¾-Ñ‚Ð¾", "ÐµÑ‰Ñ‘ Ñ‡Ñ‚Ð¾-Ñ‚Ð¾",
                "×©Ö¸×�×œ×•Ö¹×�", "\u0048\u0065\u006C\u006C\u006F");
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { BundlerUtils.EXE };
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.CLI };
    }

    protected AppWrapper getApp() throws IOException {
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    new Source(COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                            COM_GREETINGS_MODULE_INFO_TEMPLATE,
                            new HashMap<String, String>() {
                                {
                                    put(FXAPP_JAVA_TEMPLATE,
                                            COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME);
                                }
                            }, COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                            COM_GREETINGS_JAR_NAME,
                            new HashMap<String, String>() {
                                {
                                    put(PRINTLN_STATEMENT, SYSTEM_OUT_PRINTLN);
                                    put(APP_NAME_REPLACEMENT_STATEMENT,
                                            APP1_NAME);
                                    put(PASS_STRING_REPLACEMENT_STATEMENT,
                                            PASS_1);
                                    put(PACKAGE_NAME_STATEMENT,
                                            COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
                                    put(DEPENDENT_MODULE, "");
                                }
                            }));
    }

    /*
     * SKIPPING UNICODE TEST CASES UNTIL https://bugs.openjdk.java.net/browse/JDK-8089899 is fixed.
     */
    @Override
    @Test(dataProvider = "getBundlers", enabled=false)
    public void runTest(BundlingManager bundlingManager) throws Exception {
        super.runTest(bundlingManager);
    }
}
