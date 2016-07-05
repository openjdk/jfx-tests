/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.functionalinterface;

import java.util.Map;

import com.oracle.appbundlers.utils.AppWrapper;

public interface BasicParams {
    public Map<String, Object> getBasicParams(AppWrapper app) throws Exception;
}
