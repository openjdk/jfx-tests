/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;
import com.oracle.tools.packager.ConfigException;
import com.oracle.tools.packager.RelativeFileSet;
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
    public File execute(Map<String, Object> params, File file) throws IOException {
        return execute(params, file, false);
    }

    @Override
    public String getShortName() {
        return "JAVA-API";
    }

    @Override
    public File execute(Map<String, Object> params, File file,
            boolean isSrcDirRequired) throws IOException {

        if(ExtensionType.NormalJar == extensionType || isSrcDirRequired) {
            Object object = params.get("appResources");
            RelativeFileSet fileSet = (RelativeFileSet) object;
            String srcDirPath = fileSet.getBaseDirectory().getPath();
            params.put("srcdir", srcDirPath);
            LOG.log(Level.INFO, "Bundling with params after adding srcdir to params: {0}.", params);
        }
        return getBundler().execute(params, file);
    }
}
