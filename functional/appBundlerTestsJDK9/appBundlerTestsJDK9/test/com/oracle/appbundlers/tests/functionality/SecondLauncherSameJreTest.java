
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.LINUX_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.RPM;
import static com.oracle.appbundlers.utils.BundlerUtils.WIN_APP;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SAME_JAVA_EXECUTABLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.ExtensionType;

import javafx.util.Pair;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt
 */
public class SecondLauncherSameJreTest extends TestBase {

    private static final String appName = "SQE-TEST-APP";
    private static final String secondAppName = "SQE-TEST-APP-FOOBAR";

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        // Mac OS is not supported
        return new BundlerUtils[] { LINUX_APP, DEB, RPM,

                WIN_APP, EXE, MSI

        };
    }

    @Override
    public String getResultingAppName() {
        return appName;
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.ANT,
                BundlingManagers.JAVA_API };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(SAME_JAVA_EXECUTABLE,
                    new Pair<>(appName, secondAppName));
            return verifiedOptions;
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();

            additionalParams.put(APP_NAME_REPLACEMENT_STATEMENT, appName);

            Map<String, Object> launcherParams = new HashMap<>();
            launcherParams.put(APP_NAME_REPLACEMENT_STATEMENT, secondAppName);
            launcherParams.put(APPLICATION_CLASS, APP2_FULLNAME);
            List<Map<String, Object>> launchers = new ArrayList<>();
            launchers.add(launcherParams);

            additionalParams.put(SECONDARY_LAUNCHERS, launchers);

            return additionalParams;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
