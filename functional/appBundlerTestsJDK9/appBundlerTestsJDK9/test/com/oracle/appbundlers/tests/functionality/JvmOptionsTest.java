/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.MULTI_OUTPUT_CONTAINS;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 * @TODO: test needs to be modified.
 */

/**
 * Tests that provided JVM options are applied to the installed app.
 */
public class JvmOptionsTest extends TestBase {
    private static final List<String> jvmOptions = Arrays.asList(
            "-Dsqe.foo.bar=baz", "-Dsqe.qux.corge=grault", "-Xmx1g",
            "-Xms1024m");
    private static final Map<String, String> jvmProperties = new HashMap<String, String>() {
        {
            put("sqe.aba.caba", "dabacaba");
        }
    };
    private static final Map<String, String> userJvmOptions = new HashMap<String, String>() {
        {
            // put("-Xmx", "1g");
            // put("-Xms", "512m");
        }
    };

    public List<String> jvmOptions() {
        return jvmOptions;
    }

    public Map<String, String> jvmProperties() {
        return jvmProperties;
    }

    public Map<String, String> userJvmOptions() {
        return userJvmOptions;
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(JVM_OPTIONS, jvmOptions());
            additionalParams.put(JVM_PROPERTIES, jvmProperties());
            additionalParams.put(USER_JVM_OPTIONS, userJvmOptions());
            return additionalParams;
        };
    }

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(JVM_PROPERTIES, jvmProperties);
            verifiedOptions.put(MULTI_OUTPUT_CONTAINS, jvmOptions);
            verifiedOptions
                    .put(MULTI_OUTPUT_CONTAINS,
                            userJvmOptions.entrySet().stream()
                                    .map(entry -> entry.getKey()
                                            + entry.getValue())
                            .collect(toList()));
            return verifiedOptions;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
