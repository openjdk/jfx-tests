
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;

import javafx.util.Pair;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt
 */
public class CoupleSecondaryLaunchersTest extends TestBase {

    private static final String appName = "SQE-TEST-APP";
    private static final String secondAppName = "app2";
    private static final String thirdAppName = "app3";

    @Override
    public String getResultingAppName() {
        return appName;
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.ANT,
                BundlingManagers.JAVA_API };
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] {
                /* Linux */
                BundlerUtils.DEB, BundlerUtils.RPM, BundlerUtils.LINUX_APP,
                /* Windows */
                BundlerUtils.EXE, BundlerUtils.MSI, BundlerUtils.WIN_APP
                /*
                 * MacOS is not supported. See
                 * https://bugs.openjdk.java.net/browse/JDK-8096558
                 */
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(APP_NAME_REPLACEMENT_STATEMENT, appName);

            List<Map<String, Object>> launchers = new ArrayList<>();

            Map<String, Object> launcherParams = new HashMap<>();
            launcherParams.put(APP_NAME_REPLACEMENT_STATEMENT, secondAppName);
            launcherParams.put(APPLICATION_CLASS, APP1_FULLNAME);
            launcherParams.put(ARGUMENTS, asList(secondAppName));

            launchers.add(launcherParams);

            launcherParams = new HashMap<>();
            launcherParams.put(APP_NAME_REPLACEMENT_STATEMENT, thirdAppName);
            launcherParams.put(APPLICATION_CLASS, APP2_FULLNAME);
            launcherParams.put(ARGUMENTS, asList(thirdAppName));
            launchers.add(launcherParams);

            additionalParams.put(SECONDARY_LAUNCHERS, launchers);

            return additionalParams;
        };

    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = getAdditionalParams()
                    .getAdditionalParams();
            verifiedOptions.put(SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS,
                    new Pair<>(secondAppName, asList(PASS_1, secondAppName)));
            verifiedOptions.put(SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS,
                    new Pair<>(thirdAppName, asList(PASS_2, thirdAppName)));
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_1);
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
