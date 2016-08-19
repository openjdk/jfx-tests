/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests;


import static java.util.stream.Collectors.toList;

import java.util.Iterator;
import java.util.List;

import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public class BundlerProvider {
    public static Iterator<Object[]> createBundlingManagers(List<AbstractBundlerUtils> params,
                                                             List<BundlingManagers> interfaces) {
        return params.stream()
                .flatMap(b -> bundlingInterfaces(b, interfaces).stream())
                .collect(toList()).iterator();
    }

    private static List<Object[]> bundlingInterfaces(AbstractBundlerUtils b,
                                                     List<BundlingManagers> interfaces) {
        return interfaces.stream()
                .map(i -> new Object[]{i.getFactory().createInstance(b)})
                .collect(toList());
    }
}
