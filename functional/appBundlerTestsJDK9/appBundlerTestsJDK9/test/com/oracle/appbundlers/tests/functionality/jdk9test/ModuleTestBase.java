package com.oracle.appbundlers.tests.functionality.jdk9test;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.utils.ExtensionType;

/**
 * This is the baseclass for module based test case.
 * @author Ramesh BG
 */
public class ModuleTestBase extends TestBase {

    @Override
    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType extension) {
        return ExtensionType.NormalJar != extension;
    }
}

