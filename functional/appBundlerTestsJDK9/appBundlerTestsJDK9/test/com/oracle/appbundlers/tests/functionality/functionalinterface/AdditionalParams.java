/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.functionalinterface;

import java.util.Map;

/**
 * AdditionalParams is a functional interface for providing additional parameters to test case apart from basic parameters.
 * Default Parameters to each test case is provided by
 * com.oracle.appbundlers.tests.functionality.parameters.Parameters Hierarchy,
 * if default parameters doesn't suite then this functional interface can be
 * used to assign new AdditionalParams to test case.
 * @author Ramesh BG
 */
public interface AdditionalParams {
    public Map<String, Object> getAdditionalParams() throws Exception;
}

