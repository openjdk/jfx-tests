
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.LINUX_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.RPM;
import static com.oracle.appbundlers.utils.BundlerUtils.WIN_APP;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SECOND_LAUNCHER_OUTPUT_CONTAINS;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class SecondLauncherTest extends TestBase {

    private static final List<String> jvmOptions;
    private static final Map<String, String> jvmProperties;
    private static final Map<String, String> userJvmOptions;

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        // Mac OS is not supported
        return new BundlerUtils[] { LINUX_APP, DEB, RPM,

                WIN_APP, EXE, MSI

        };
    }

    static {
        jvmOptions = Arrays.asList("-Dsqe.foo.bar=baz",
                "-Dsqe.qux.corge=grault");

        jvmProperties = new HashMap<>();
        jvmProperties.put("sqe.aba.caba", "dabacaba");

        userJvmOptions = new HashMap<>();
        userJvmOptions.put("-Xmx", "1g");
        userJvmOptions.put("-Xms", "512m");
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.ANT,
                BundlingManagers.JAVA_API };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();

            additionalParams.put(APP_NAME,
                    SecondLauncherTest.this.getResultingAppName());
            String appClass = String.join("/",
                    this.currentParameter.getApp().getMainModuleName(),
                    this.currentParameter.getApp().getMainClass());
            additionalParams.put(MAIN_MODULE, appClass);
            Map<String, Object> launcherParams = new HashMap<>();
            launcherParams.put(APP_NAME,
                    SecondLauncherTest.this.getSecondaryLauncherName());
            if (this.currentParameter.getApp().isAppContainsModules()) {
                launcherParams.put(MAIN_MODULE, this.currentParameter.getApp()
                        .getModuleTempSources().get(0).getModuleName());
            }
            launcherParams.put(JVM_OPTIONS, jvmOptions);
            launcherParams.put(JVM_PROPERTIES, jvmProperties);
            launcherParams.put(USER_JVM_OPTIONS, userJvmOptions);
            List<Map<String, Object>> launchers = new ArrayList<>();
            launchers.add(launcherParams);

            additionalParams.put(SECONDARY_LAUNCHERS, launchers);

            return additionalParams;
        };

    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>(
                    getAdditionalParams().getAdditionalParams());
            verifiedOptions.put(SECOND_LAUNCHER_OUTPUT_CONTAINS,
                    new Pair<>(getSecondaryLauncherName(), PASS_2));
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_1);

            List<String> expectedJvmProps = jvmProperties.entrySet()
                    .stream().map(entry -> String.format("-D%s=%s",
                            entry.getKey(), entry.getValue()))
                    .collect(toList());

            verifiedOptions.put(SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS,
                    new Pair<>(getSecondaryLauncherName(), expectedJvmProps));
            verifiedOptions.put(SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS,
                    new Pair<>(getSecondaryLauncherName(), jvmOptions));
            final List<String> usrJvmOpts = userJvmOptions.entrySet().stream()
                    .map(entry -> entry.getKey() + entry.getValue())
                    .collect(toList());
            verifiedOptions.put(SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS,
                    new Pair<>(getSecondaryLauncherName(), usrJvmOpts));

            return verifiedOptions;
        };
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    private String getSecondaryLauncherName() {
        return String.join("", getResultingAppName(), "SecondaryLauncher");
    }
}
