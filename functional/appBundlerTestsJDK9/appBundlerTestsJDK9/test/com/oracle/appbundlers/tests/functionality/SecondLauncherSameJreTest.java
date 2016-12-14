
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.BundlerUtils.DEB;
import static com.oracle.appbundlers.utils.BundlerUtils.EXE;
import static com.oracle.appbundlers.utils.BundlerUtils.LINUX_APP;
import static com.oracle.appbundlers.utils.BundlerUtils.MSI;
import static com.oracle.appbundlers.utils.BundlerUtils.RPM;
import static com.oracle.appbundlers.utils.BundlerUtils.WIN_APP;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SAME_JAVA_EXECUTABLE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SAME_JAVA_EXECUTABLE_OUTPUT;

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
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.Source;
import com.oracle.appbundlers.utils.SourceFactory;
import com.oracle.appbundlers.utils.Utils;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt
 */
public class SecondLauncherSameJreTest extends TestBase {

    private static final String secondAppName = "SQE-TEST-APP-FOOBAR";

    private static final List<String> jvmOptions = Arrays.asList(
            "-verbose:class");

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        // Mac OS is not supported
        return new BundlerUtils[] { LINUX_APP, DEB, RPM,

                WIN_APP, EXE, MSI

        };
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] { BundlingManagers.ANT,
                BundlingManagers.JAVA_API };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, String> appNamesToOutput = new HashMap<>();
            appNamesToOutput.put(getResultingAppName(), PASS_1);
            appNamesToOutput.put(secondAppName, PASS_2);

            Map<String, Map<String, String>> entireAppOutput = new HashMap<String, Map<String, String>>();
            entireAppOutput.put(SAME_JAVA_EXECUTABLE_OUTPUT, appNamesToOutput);

            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(SAME_JAVA_EXECUTABLE, entireAppOutput);
            return verifiedOptions;
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(APP_NAME, getResultingAppName());
            additionalParams.put(JVM_OPTIONS, jvmOptions);
            String mainModuleWithAppClass = String.join("/",
                    this.currentParameter.getApp().getMainModuleName(),
                    this.currentParameter.getApp().getMainClass());
            if (this.currentParameter.getApp().isAppContainsModules()) {
                additionalParams.put(MAIN_MODULE, mainModuleWithAppClass);
            } else {
                additionalParams.put(APPLICATION_CLASS,
                        this.currentParameter.getApp().getMainClass());
            }

            Map<String, Object> secondLauncher = new HashMap<>();
            secondLauncher.put(APP_NAME, secondAppName);
            secondLauncher.put(JVM_OPTIONS, jvmOptions);

            if (this.currentParameter.getApp().isAppContainsModules()) {
                secondLauncher.put(MAIN_MODULE,
                        COM_GREETINGS_MODULE_CUM_PACKAGE_NAME);
                secondLauncher.put(MAIN_MODULE,
                        String.join("/", COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                                COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME));
            } else {
                secondLauncher.put(APPLICATION_CLASS,
                        COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME);
            }

            List<Map<String, Object>> launchers = new ArrayList<>();
            launchers.add(secondLauncher);

            additionalParams.put(SECONDARY_LAUNCHERS, launchers);
            return additionalParams;
        };
    }

    @Override
    public void overrideParameters(ExtensionType javaExtensionFormat)
            throws IOException {
        this.currentParameter.setApp(getApp(javaExtensionFormat));
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
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
