/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.CHECK_ARGUMENTS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

/**
 * This test is designed to check whether arguments are correctly passed to the
 * executable
 */
public class CommandLineArgumentsTest extends TestBase {

    public List<String> args() {
        return Arrays.asList("aba", "caba", "aba caba");
    }

    @Override
    public void overrideParameters(ExtensionType intermediate) {
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    private VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(CHECK_ARGUMENTS, args());
            return verifiedOptions;
        };
    }
}
