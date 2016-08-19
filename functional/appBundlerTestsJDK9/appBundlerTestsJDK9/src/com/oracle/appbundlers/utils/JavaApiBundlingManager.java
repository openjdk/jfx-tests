/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.utils;

import java.io.File;
import java.util.Map;

import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;
import com.oracle.tools.packager.ConfigException;
import com.oracle.tools.packager.UnsupportedPlatformException;

/**
 *
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public class JavaApiBundlingManager extends BundlingManager {

    public JavaApiBundlingManager(AbstractBundlerUtils bundler) {
        super(bundler);
    }

    @Override
    public boolean validate(Map<String, Object> params)
            throws UnsupportedPlatformException, ConfigException {
        return getBundler().validate(params);
    }

    @Override
    public File execute(Map<String, Object> params, File file) {
        return getBundler().execute(params, file);
    }

    @Override
    public String getShortName() {
        return "JAVA-API";
    }
}
