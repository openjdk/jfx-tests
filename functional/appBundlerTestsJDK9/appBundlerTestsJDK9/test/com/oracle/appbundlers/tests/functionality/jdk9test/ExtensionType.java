/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.jdk9test;

import com.oracle.appbundlers.utils.Constants;

/**
 * @author Ramesh BG
 *
 */
public enum ExtensionType implements Constants {
    NormalJar, ModularJar, ExplodedModules, Jmods;
}
