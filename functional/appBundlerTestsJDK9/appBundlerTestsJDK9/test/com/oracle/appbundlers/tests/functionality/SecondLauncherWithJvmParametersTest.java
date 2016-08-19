
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
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.Constants;
import com.oracle.appbundlers.utils.ExtensionType;

import javafx.util.Pair;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt
 */
public class SecondLauncherWithJvmParametersTest extends TestBase {

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
            Map<String, Object> firstLauncherParams = new HashMap<>();

            firstLauncherParams.put(APP_NAME,
                    SecondLauncherWithJvmParametersTest.this.getResultingAppName());
            String mainModuleWithAppClass = String.join("/",
                    this.currentParameter.getApp().getMainModuleName(),
                    this.currentParameter.getApp().getMainClass());
            if(this.currentParameter.getApp().isAppContainsModules()) {
                firstLauncherParams.put(MAIN_MODULE, mainModuleWithAppClass);
            }
           
            Map<String, Object> secondLauncher = new HashMap<>();
            secondLauncher.put(APP_NAME,
                    SecondLauncherWithJvmParametersTest.this.getSecondaryLauncherName());
            if (this.currentParameter.getApp().isAppContainsModules()) {
                secondLauncher.put(MAIN_MODULE,
                        Constants.COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
            }
            secondLauncher.put(APPLICATION_CLASS,
                    Constants.COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME);
            secondLauncher.put(JVM_OPTIONS, jvmOptions);
            secondLauncher.put(JVM_PROPERTIES, jvmProperties);
            secondLauncher.put(USER_JVM_OPTIONS, userJvmOptions);
            
            List<Map<String, Object>> secondarylaunchersList = new ArrayList<>();
            secondarylaunchersList.add(secondLauncher);

            firstLauncherParams.put(SECONDARY_LAUNCHERS, secondarylaunchersList);

            return firstLauncherParams;
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
