/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;
import static com.oracle.appbundlers.utils.installers.AbstractBundlerUtils.OUTPUT_CONTAINS;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.ProcessOutput;
import com.oracle.appbundlers.utils.Utils;
import com.oracle.tools.packager.RelativeFileSet;
import com.sun.javafx.tools.packager.bundlers.BundleParams;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 */

/**
 * <p>
 * Tests {@code runtime} option: the other JRE should be correctly bundled with
 * the application
 * </p>
 * <p>
 * Build of "that" JRE should appear in the output of the application (as
 * "java.runtime.version")
 * </p>
 */
@Deprecated
/*
 * see bug JDK-8155956
 */
public class RuntimeTest extends TestBase {
    private RelativeFileSet fileSet = null;
    private String build = null;

    @Override
    protected void prepareTestEnvironment()
            throws IOException, ExecutionException {
        Path jreOrJdk = Paths.get(CONFIG_INSTANCE.getOtherJre());
        fileSet = new RelativeFileSet(jreOrJdk.toFile(),
                Files.walk(jreOrJdk)
                        .map(path -> path.toFile().getAbsoluteFile())
                        .filter(File::isFile).collect(Collectors.toSet()));

        ProcessOutput jreHome = javaVersion(CONFIG_INSTANCE.getOtherJre());
        String output = jreHome.getErrorStream().stream()
                .collect(joining(System.lineSeparator()));
        Pattern pattern = Pattern.compile("Environment\\s+\\(build\\s+(.*)\\)");
        Matcher matcher = pattern.matcher(output);
        if (!matcher.find()) {
            throw new IllegalArgumentException(
                    format("Can not find \"build...\" :%n%s", output));
        }
        build = matcher.group(1);
    }

    public VerifiedOptions getVerifiedOptions() {
        return () -> {
            Map<String, Object> verifiedOptions = new HashMap<>();
            verifiedOptions.put(OUTPUT_CONTAINS, build);
            return verifiedOptions;
        };
    }

    public AdditionalParams getAdditionalParams() {
        return () -> {
            Map<String, Object> additionalParams = new HashMap<>();
            additionalParams.put(BundleParams.PARAM_RUNTIME, fileSet);
            return additionalParams;
        };
    }

    private ProcessOutput javaVersion(String javaHome)
            throws IOException, ExecutionException {
        return Utils.runCommand(
                new String[] { javaHome + File.separator + "bin"
                        + File.separator + "java", "-version" },
                true, CONFIG_INSTANCE.getRunTimeout());
    }

    @Override
    public void overrideParameters(ExtensionType intermediate)
            throws IOException {
        this.currentParameter.setAdditionalParams(getAdditionalParams());
        this.currentParameter.setVerifiedOptions(getVerifiedOptions());
    }
}
