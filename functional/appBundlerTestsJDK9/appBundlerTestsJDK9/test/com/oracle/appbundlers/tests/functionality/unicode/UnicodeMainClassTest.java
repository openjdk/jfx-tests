/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality.unicode;

import static com.oracle.appbundlers.utils.Config.OPTION_PREFIX;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.functionality.MainClassTest;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.Bundler;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

/**
 * MainClassTest applied for Unicode
 */

public class UnicodeMainClassTest extends MainClassTest {
    @Override
    protected String mainClassName() {
        return "Ð�Ð¿Ð¿2";
    }

    protected AppWrapper getApp() throws IOException {
        return new AppWrapper(
                Utils.getTempSubDir(WORK_DIRECTORY),
                APP1_FULLNAME,
                new Source(APP1_FULLNAME, FXAPP_JAVA_TEMPLATE,
                        "testFxAppWithUtil",
                        new HashMap<String, String>() {
                            {
                                put(PRINTLN_STATEMENT, SYSTEM_OUT_PRINTLN);
                                put(APP_NAME_REPLACEMENT_STATEMENT, APP1_NAME);
                                put(PASS_STRING_REPLACEMENT_STATEMENT, PASS_1);
                                put("%PREFIX%", OPTION_PREFIX);
                            }
                        }
                ),
                new Source(fullName(), FXAPP_JAVA_TEMPLATE,
                        "testFxAppWithUtil",
                        new HashMap<String, String>() {
                            {
                                put(PRINTLN_STATEMENT, SYSTEM_OUT_PRINTLN);
                                put(APP_NAME_REPLACEMENT_STATEMENT, mainClassName());
                                put(PASS_STRING_REPLACEMENT_STATEMENT, PASS_2);
                                put("%PREFIX%", OPTION_PREFIX);
                            }
                        }
                )
        );
    }

    @Override
    protected boolean isConfigExceptionExpected(Bundler bundler) {
        final String id = bundler.getID().toUpperCase();
        return id.equals("DEB") || id.equals("RPM");
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
