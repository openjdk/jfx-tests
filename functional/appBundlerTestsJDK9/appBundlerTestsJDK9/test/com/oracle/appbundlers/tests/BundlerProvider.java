/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oracle.appbundlers.utils.BundlingManager;
import com.oracle.appbundlers.utils.BundlingManagers;
import com.oracle.appbundlers.utils.ExtensionType;
import com.oracle.appbundlers.utils.installers.AbstractBundlerUtils;

/**
 *
 * @author Dmitry Zinkevich &lt;dmitry.zinkevich@oracle.com&gt;
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 * @author Andrei Eremeev &lt;andrei.eremeev@oracle.com&gt;
 */
public class BundlerProvider {
    public static Iterator<Object[]> createBundlingManagers(
            List<AbstractBundlerUtils> systemExtFormatList,
            List<BundlingManagers> bundlingMgrList,
            List<ExtensionType> javaExtFormatList) {
        List<Object[]> list = new ArrayList<Object[]>();
        for (BundlingManagers eachBundlingMgr : bundlingMgrList) {
            for (ExtensionType eachJavaExtension : javaExtFormatList) {
                for (AbstractBundlerUtils eachSystemExtension : systemExtFormatList) {
                    BundlingManager bundlingMgr = eachBundlingMgr.getFactory()
                            .createInstance(eachSystemExtension);
                    bundlingMgr.setExtensionType(eachJavaExtension);
                    Object[] object = new Object[] { bundlingMgr };
                    list.add(object);
                }
            }
        }
        return list.iterator();
    }

    public static Iterator<Object[]> createBundlingManagers(
            List<AbstractBundlerUtils> systemExtFormatList,
            List<BundlingManagers> bundlingMgrList, List<Path> modulePathList,
            boolean dummy) {
        List<Object[]> list = new ArrayList<Object[]>();
        for (BundlingManagers eachBundlingMgr : bundlingMgrList) {
            for (AbstractBundlerUtils eachSystemExtension : systemExtFormatList) {
                for (Path modulePath : modulePathList) {
                    BundlingManager bundlingMgr = eachBundlingMgr.getFactory()
                            .createInstance(eachSystemExtension);
                    bundlingMgr.setModulePath(modulePath);
                    Object[] object = new Object[] { bundlingMgr };
                    list.add(object);
                }
            }
        }
        return list.iterator();
    }
}
