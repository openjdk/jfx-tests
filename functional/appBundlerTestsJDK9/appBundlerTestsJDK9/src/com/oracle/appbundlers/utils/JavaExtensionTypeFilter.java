package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;

public class JavaExtensionTypeFilter {

    private JavaExtensionTypeFilter() {

    }

    public static boolean accept(ExtensionType javaExtensionType) {
        return CONFIG_INSTANCE.getAcceptedJavaExtensionType() == null
                || CONFIG_INSTANCE
                        .getAcceptedJavaExtensionType() == javaExtensionType;
    }
}
