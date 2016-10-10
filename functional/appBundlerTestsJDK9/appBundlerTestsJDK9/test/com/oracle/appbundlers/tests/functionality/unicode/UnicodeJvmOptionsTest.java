/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.unicode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

import com.oracle.appbundlers.tests.functionality.JvmOptionsTest;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.BundlingManagers;

/**
 * JvmOptionsTest applied for Unicode
 */
public class UnicodeJvmOptionsTest extends JvmOptionsTest {
    @Override
    public List<String> jvmOptions() {
        return Arrays.asList("-Dsqe.йцу=кен");
    }

    @Override
    public Map<String, String> jvmProperties() {
        return new HashMap<String, String>() {{
            put("sqe.йцу.кен", "фывапролдж");
        }};
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] { BundlerUtils.EXE};
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.CLI};
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
