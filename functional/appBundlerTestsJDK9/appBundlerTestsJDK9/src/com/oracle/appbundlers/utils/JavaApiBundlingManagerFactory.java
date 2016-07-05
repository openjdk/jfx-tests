/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;

/**
 *
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public class JavaApiBundlingManagerFactory implements BundlingManagerFactory {

    @Override
    public BundlingManager createInstance(AbstractBundlerUtils bundlerUtils) {
        return new JavaApiBundlingManager(bundlerUtils);
    }
}
