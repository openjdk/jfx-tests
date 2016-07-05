/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import static com.oracle.appbundlers.utils.Config.CONFIG_INSTANCE;

public class PackagerApiFilter {
    private PackagerApiFilter() {
    }

    public static boolean accept(BundlingManagers obj) {
        return CONFIG_INSTANCE.getAcceptedPackagerApi() == null
                || CONFIG_INSTANCE.getAcceptedPackagerApi() == obj;
    }
}
