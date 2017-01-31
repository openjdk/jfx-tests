package com.oracle.appbundlers.tests.functionality.jdk9test;

import org.testng.annotations.Test;

import com.oracle.appbundlers.tests.functionality.TestBase;
import com.oracle.appbundlers.utils.BundlingManager;

/**
 * @author Ramesh BG
 * Skipping this test case until bug
 * https://bugs.openjdk.java.net/browse/JDK-8171959 is fixed
 */
public class SecondaryLauncherNormalAndModularMixTest extends TestBase {

    @Override
    @Test(dataProvider = "getBundlers", enabled=false)
    public void runTest(BundlingManager bundlingManager) throws Exception {
        super.runTest(bundlingManager);
    }
}
