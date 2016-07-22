/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.Config.OPTION_PREFIX;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.USER_FRIENDLY_API;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * Tests that provided JVM options are applied to the installed app.
 *
 * Test execution consists of the following steps:
 *
 * I. App runs and clears user JVM options.
 *
 * II. App runs and default JVM options are checked. App sets specific options.
 *
 * III. App runs and checks that specific options were applied. Apps clears user
 * JVM options.
 *
 * IV. App runs and default JVM options are checked.
 *
 */
public class UserFriendlyJvmOptionsTest extends TestBase {

    public UserFriendlyJvmOptionsTest() throws IOException {
    }

    // Default user JVM options
    private static final Map<String, String> defultUserJvmOptions;
    private static final Map<String, String> newUserJvmOptions;

    static {
        defultUserJvmOptions = new HashMap<>();
        defultUserJvmOptions.put("-Xmx", "1g");
        defultUserJvmOptions.put("-Xms", "512m");

        // The same values are used in Util.java.template
        newUserJvmOptions = new HashMap<>();
        newUserJvmOptions.put("-Xmx", "777m");
        newUserJvmOptions.put("-Xms", "256m");
    }

    protected AppWrapper getApp(ExtensionType extension) throws IOException {
        if (ExtensionType.NormalJar == extension) {
            Map<String, String> replacementsInSourceCode = new HashMap<String, String>();
            replacementsInSourceCode.put("%PREFIX%", OPTION_PREFIX);
            replacementsInSourceCode.put("/*USER_FRIENDLY_API_TEST*/",
                    "testapp.util.Util.changeUserOptions(userJvmAction);");
            replacementsInSourceCode.put(DEPENDENT_MODULE, "");
            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    SourceFactory.get_test_app_util_unnamed_module(),
                    SourceFactory.get_com_greetings_app_unnamed_module(
                            replacementsInSourceCode));

        } else {
            Map<String, String> replacementsInSourceCode = new HashMap<String, String>();
            replacementsInSourceCode.put("%PREFIX%", OPTION_PREFIX);
            replacementsInSourceCode.put("/*USER_FRIENDLY_API_TEST*/",
                    "testapp.util.Util.changeUserOptions(userJvmAction);");
            replacementsInSourceCode.put(DEPENDENT_MODULE,
                    "requires custom.util;");
            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    SourceFactory.get_custom_util_module(),

            SourceFactory.get_com_greetings_module(Collections.emptyMap(),
                    replacementsInSourceCode));
        }
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(USER_JVM_OPTIONS, defultUserJvmOptions);

            Long uuid = UUID.randomUUID().getMostSignificantBits();
            additionalParams.put(IDENTIFIER, uuid.toString());

            return additionalParams;
        };
    }

    protected VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions
                    .put(USER_FRIENDLY_API,
                            new List[] {
                                    defultUserJvmOptions.entrySet().stream()
                                            .map(entry -> entry.getKey()
                                                    + entry.getValue())
                                    .collect(toList()),
                            newUserJvmOptions.entrySet().stream()
                                    .map(entry -> entry.getKey()
                                            + entry.getValue())
                            .collect(toList()) });
            return verifiedOptions;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setApp(getApp(intermediate));
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
