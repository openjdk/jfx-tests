package com.oracle.appbundlers.tests.functionality.jdk9test;

import com.oracle.appbundlers.tests.functionality.TestBase;

/**
 * @author Ramesh BG
 *
 */
public class ModuleTestBase extends TestBase {

    @Override
    public boolean isTestCaseApplicableForExtensionType(
            ExtensionType extension) {
        return ExtensionType.NormalJar != extension;
    }
}
