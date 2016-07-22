/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * <p>
 * Tests the behavior when the {@code mainClass} is specified
 * </p>
 * <p>
 * This class should not be equal to the default one (the main class of the main
 * jar)
 * </p>
 */
public class MainClassTest extends TestBase {
    protected String mainClassName() {
        return APP2_NAME;
    }

    protected String fullName() {
        return COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME;
    }

    @Override
    public String getResultingAppName() {
        return mainClassName();
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(APPLICATION_CLASS, fullName());
            return additionalParams;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_2);
            return verifiedOptions;
        };
    }

    private AppWrapper getApp(ExtensionType intermediate) throws IOException {
        return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME,
                SourceFactory.get_com_greetings_app_unnamed_module(),
                SourceFactory.get_com_greetings_app_unnamed_module(
                        COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME,
                        new HashMap<String, String>() {
                            private static final long serialVersionUID = 1765566031472224391L;

                            {
                                put(APP_NAME_REPLACEMENT_STATEMENT,
                                        mainClassName());
                                put(PASS_STRING_REPLACEMENT_STATEMENT,
                                        "PASS_2");
                                put(APP_NAME_REPLACEMENT_STATEMENT, "App2");
                            }
                        }));

    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        if (ExtensionType.NormalJar == intermediate) {
            this.currentParameter.setApp(getApp(intermediate));
            this.currentParameter.setAdditionalParams(getAdditionalParams());
            this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        }
    }

    @Override
    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType intermediateType) {
        return ExtensionType.NormalJar == intermediateType;
    }
}
