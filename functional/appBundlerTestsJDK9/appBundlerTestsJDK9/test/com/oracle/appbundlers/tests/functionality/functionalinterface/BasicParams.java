/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.functionalinterface;

import java.util.Map;

import com.oracle.appbundlers.utils.AppWrapper;

/**
 * BasicParams is a functional interface for providing basic parameters to
 * test case. Default Parameters to each test case is provided by
 * com.oracle.appbundlers.tests.functionality.parameters.Parameters Hierarchy,
 * if default parameters doesn't suite then this functional interface can be
 * used to assign new BasicParams.
 * @author Ramesh BG
 */
public interface BasicParams {
    public Map<String, Object> getBasicParams(AppWrapper app) throws Exception;
}

