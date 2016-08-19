/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.utils;

/**
 * @author Ramesh BG
 *
 */
public enum ExtensionType implements Constants {
    NormalJar, ModularJar, ExplodedModules, Jmods;

    public static ExtensionType[] getModuleTypes() {
        return new ExtensionType[] { ModularJar, ExplodedModules, Jmods };
    }
}
