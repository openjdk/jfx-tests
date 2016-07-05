/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.win;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.WIN_SYSTEM_WIDE_FILE_ASSOCIATIONS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.WIN_USER_FILE_ASSOCIATIONS;

import java.util.Map;

import com.oracle.appbundlers.tests.functionality.FileAssociationTest;
import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */
public class UserFileAssociations extends FileAssociationTest  {
    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] {
                BundlerUtils.EXE,
                BundlerUtils.MSI
        };
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] {
                BundlingManagers.JAVA_API,
                BundlingManagers.ANT
        };
    }

    protected AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = super.getAdditionalParams().getAdditionalParams();
            additionalParams.put(SYSTEM_WIDE, false);
            return additionalParams;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = super.getVerifiedOptions().getVerifiedOptions();
            verifiedOptions.remove(WIN_SYSTEM_WIDE_FILE_ASSOCIATIONS);
            verifiedOptions.put(WIN_USER_FILE_ASSOCIATIONS, getAdditionalParams().getAdditionalParams().get(FILE_ASSOCIATIONS));
            return verifiedOptions;
        };

    }
}
