/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

/**
 *
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public enum BundlingManagers {
    JAVA_API(new JavaApiBundlingManagerFactory()), CLI(
            new ConsoleBundlingManagerFactory()), ANT(
                    new AntBundlingManagerFactory());

    private final BundlingManagerFactory factory;

    private BundlingManagers(BundlingManagerFactory factory) {
        this.factory = factory;
    }

    public BundlingManagerFactory getFactory() {
        return factory;
    }
}
