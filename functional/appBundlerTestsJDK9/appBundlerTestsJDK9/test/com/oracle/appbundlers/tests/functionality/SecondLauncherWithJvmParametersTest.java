
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.LINUX_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.RPM;
import static com.oracle.appbundlers.utils.BundlerUtils.WIN_APP;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.Constants;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

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
            Map<String, Object> additionalParams = new HashMap<>();

            additionalParams.put(APP_NAME,
                    SecondLauncherWithJvmParametersTest.this.getResultingAppName());
            String mainModuleWithAppClass = String.join("/",
                    this.currentParameter.getApp().getMainModuleName(),
                    this.currentParameter.getApp().getMainClass());
            if (this.currentParameter.getApp().isAppContainsModules()) {
                additionalParams.put(MAIN_MODULE, mainModuleWithAppClass);
            } else {
                additionalParams.put(APPLICATION_CLASS,
                        this.currentParameter.getApp().getMainClass());
            }

            Map<String, Object> secondLauncherParams = new HashMap<>();
            secondLauncherParams.put(APP_NAME,
                    SecondLauncherWithJvmParametersTest.this.getSecondaryLauncherName());
            if (this.currentParameter.getApp().isAppContainsModules()) {
                secondLauncherParams.put(MAIN_MODULE, String.join("/",
                        Constants.COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                        Constants.COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME));
            } else {
                secondLauncherParams.put(APPLICATION_CLASS,
                        Constants.COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME);
            }
            secondLauncherParams.put(JVM_OPTIONS, jvmOptions);
            secondLauncherParams.put(JVM_PROPERTIES, jvmProperties);
            secondLauncherParams.put(USER_JVM_OPTIONS, userJvmOptions);

            List<Map<String, Object>> secondarylaunchersList = new ArrayList<>();
            secondarylaunchersList.add(secondLauncherParams);

            additionalParams.put(SECONDARY_LAUNCHERS, secondarylaunchersList);

            return additionalParams;
        };

    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>(
                    getAdditionalParams().getAdditionalParams());
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_1);

            List<String> expectedJvmProps = jvmProperties.entrySet()
                    .stream().map(entry -> String.format("-D%s=%s",
                            entry.getKey(), entry.getValue()))
                    .collect(toList());

            final List<String> usrJvmOpts = userJvmOptions.entrySet().stream()
                    .map(entry -> entry.getKey() + entry.getValue())
                    .collect(toList());

            List<String> expectedSecondaryLauncherOutput = new ArrayList<String>();
            expectedSecondaryLauncherOutput.addAll(expectedJvmProps);
            expectedSecondaryLauncherOutput.addAll(jvmOptions);
            expectedSecondaryLauncherOutput.addAll(usrJvmOpts);
            expectedSecondaryLauncherOutput.add(PASS_2);


            List<Pair<String, List<String>>> multipleExpectedOutputPair = new ArrayList<>();
            multipleExpectedOutputPair.add(new Pair<>(getSecondaryLauncherName(),
                    expectedSecondaryLauncherOutput));
            verifiedOptions.put(SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS,
                    multipleExpectedOutputPair);

            return verifiedOptions;
        };
    }

    @Override
    public void overrideParameters(ExtensionType javaExtensionFormat)
            throws IOException {
        this.currentParameter.setApp(getApp(javaExtensionFormat));
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    private String getSecondaryLauncherName() {
        return String.join("_", getResultingAppName(), "SecondaryLauncher");
    }

    private AppWrapper getApp(ExtensionType extension) throws IOException {
        Map<String, String> replacementsInSourceCodeForApp2 = new HashMap<String, String>();
        replacementsInSourceCodeForApp2.put(PASS_STRING_REPLACEMENT_STATEMENT,
                PASS_2);
        replacementsInSourceCodeForApp2.put(APP_NAME_REPLACEMENT_STATEMENT,
                APP2_NAME);

        switch (extension) {
        default:
            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    SourceFactory.get_com_greetings_app_unnamed_module(),
                    SourceFactory.get_com_greetings_app_unnamed_module(
                            COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME,
                            replacementsInSourceCodeForApp2));
        case ExplodedModules:
        case Jmods:
        case ModularJar:
            Map<String, Map<String, String>> classNameToReplacements = new LinkedHashMap<>();
            classNameToReplacements.put(APP1_NAME, Collections.emptyMap());
            classNameToReplacements.put(APP2_NAME, Collections.emptyMap());

            Map<String, String> classNameToTemplateMap = new HashMap<>();
            classNameToTemplateMap.put(COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    FXAPP_JAVA_TEMPLATE);
            classNameToTemplateMap.put(COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME,
                    FXAPP_JAVA_TEMPLATE);
            Source get_com_greetings_module_source = SourceFactory
                    .get_com_greetings_module(classNameToTemplateMap,
                            COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                            classNameToReplacements);
            get_com_greetings_module_source.setMainModule(true);
            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    get_com_greetings_module_source);
        }
    }
}
