
package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OVERRIDE_DEFAULT_ARGS;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.ExtensionType;

import javafx.util.Pair;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt
 */
public class DefaultArgumentsOverrideTest extends TestBase {

    private static final String appName = "SQE-DEFAULT-PARAMS-OVERRIDE-TEST-APP";
    private static final List<String> arguments = Arrays
            .asList("this.is.a.test=tru", "one.more.arg=affirmative");

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(APP_NAME, appName);
            additionalParams.put(ARGUMENTS, arguments);
            return additionalParams;
        };
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>(
                    getAdditionalParams().getAdditionalParams());
            verifiedOptions.put(OVERRIDE_DEFAULT_ARGS,
                    new Pair<>(asList("aba", "caba", "aba caba"), arguments));
            return verifiedOptions;
        };
    }

    @Override
    public String getResultingAppName() {
        return appName;
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
        this.currentParameter.setAdditionalParams(getAdditionalParams());
    }
}
