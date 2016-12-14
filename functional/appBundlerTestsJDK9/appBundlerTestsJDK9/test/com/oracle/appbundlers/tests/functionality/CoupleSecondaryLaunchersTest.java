
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
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

import javafx.util.Pair;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt
 */
public class CoupleSecondaryLaunchersTest extends TestBase {

    private static final String secondAppName = "app2";
    private static final String thirdAppName = "app3";

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
            additionalParams.put(APP_NAME, getResultingAppName());

            List<Map<String, Object>> launchers = new ArrayList<>();
            Map<String, Object> secondLauncher = getSecondLauncher();
            launchers.add(secondLauncher);

            Map<String, Object> thirdLauncher = getThirdLauncher();
            launchers.add(thirdLauncher);

            additionalParams.put(SECONDARY_LAUNCHERS, launchers);

            return additionalParams;
        };
    }

    private Map<String, Object> getSecondLauncher() throws IOException {
        Map<String, Object> secondLauncher = new HashMap<>();
        secondLauncher.put(APP_NAME, secondAppName);
        if (this.currentParameter.getApp().isAppContainsModules()) {
            secondLauncher.put(MAIN_MODULE,
                    String.join("/", COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                            COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME));
        } else {
            secondLauncher.put(APPLICATION_CLASS,
                    COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME);
        }
        secondLauncher.put(ARGUMENTS, asList(secondAppName));
        return secondLauncher;
    }

    private Map<String, Object> getThirdLauncher() throws IOException {
        Map<String, Object> thirdLauncher = new HashMap<>();
        thirdLauncher.put(APP_NAME, thirdAppName);
        if (this.currentParameter.getApp().isAppContainsModules()) {
            thirdLauncher.put(MAIN_MODULE,
                    String.join("/", COM_GREETINGS_MODULE_CUM_PACKAGE_NAME,
                            COM_GREETINGS_APP3_QUALIFIED_CLASS_NAME));
        } else {
            thirdLauncher.put(APPLICATION_CLASS,
                    COM_GREETINGS_APP3_QUALIFIED_CLASS_NAME);
        }
        thirdLauncher.put(ARGUMENTS, asList(thirdAppName));
        return thirdLauncher;
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = getAdditionalParams()
                    .getAdditionalParams();
            verifiedOptions.put(OUTPUT_CONTAINS, PASS_1);

            List<Pair<String, List<String>>> multipleExpectedOutputPair = new ArrayList<>();
            multipleExpectedOutputPair.add(new Pair<>(secondAppName, asList(PASS_2, secondAppName)));
            multipleExpectedOutputPair.add(new Pair<>(thirdAppName, asList(PASS_3, thirdAppName)));
            verifiedOptions.put(SECOND_LAUNCHER_MULTI_OUTPUT_CONTAINS,
                    multipleExpectedOutputPair);
            return verifiedOptions;
        };
    }

    @Override
    public void overrideParameters(ExtensionType extension) throws IOException {
        this.currentParameter.setApp(getApp(extension));
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }

    private AppWrapper getApp(ExtensionType extension) throws IOException {
        Map<String, String> replacementsInSourceCodeForApp2 = new HashMap<String, String>();
        replacementsInSourceCodeForApp2.put(PASS_STRING_REPLACEMENT_STATEMENT,
                PASS_2);
        replacementsInSourceCodeForApp2.put(APP_NAME_REPLACEMENT_STATEMENT,
                APP2_NAME);

        Map<String, String> replacementsInSourceCodeForApp3 = new HashMap<String, String>();
        replacementsInSourceCodeForApp3.put(PASS_STRING_REPLACEMENT_STATEMENT,
                PASS_3);
        replacementsInSourceCodeForApp3.put(APP_NAME_REPLACEMENT_STATEMENT,
                APP3_NAME);
        switch (extension) {
        default:
            return new AppWrapper(Utils.getTempSubDir(WORK_DIRECTORY),
                    COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    SourceFactory.get_com_greetings_app_unnamed_module(),
                    SourceFactory.get_com_greetings_app_unnamed_module(
                            COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME,
                            replacementsInSourceCodeForApp2),
                    SourceFactory.get_com_greetings_app_unnamed_module(
                            COM_GREETINGS_APP3_QUALIFIED_CLASS_NAME,
                            replacementsInSourceCodeForApp3));
        case ExplodedModules:
        case Jmods:
        case ModularJar:
            Map<String, Map<String, String>> classNameToReplacements = new LinkedHashMap<>();
            classNameToReplacements.put(APP1_NAME, Collections.emptyMap());
            classNameToReplacements.put(APP2_NAME, Collections.emptyMap());
            classNameToReplacements.put(APP3_NAME, Collections.emptyMap());

            Map<String, String> classNameToTemplateMap = new HashMap<>();
            classNameToTemplateMap.put(COM_GREETINGS_APP1_QUALIFIED_CLASS_NAME,
                    FXAPP_JAVA_TEMPLATE);
            classNameToTemplateMap.put(COM_GREETINGS_APP2_QUALIFIED_CLASS_NAME,
                    FXAPP_JAVA_TEMPLATE);
            classNameToTemplateMap.put(COM_GREETINGS_APP3_QUALIFIED_CLASS_NAME,
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
