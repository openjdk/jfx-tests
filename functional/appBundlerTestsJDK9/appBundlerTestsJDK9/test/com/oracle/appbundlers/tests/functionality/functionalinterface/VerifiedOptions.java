/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.functionalinterface;

import java.util.Map;

/**
 * VerifiedOptions Functional interface represents Expected Output of installer when
 * installed and executed on respective operating system.
 * @author Ramesh BG
 */
public interface VerifiedOptions {
    public Map<String, Object> getVerifiedOptions() throws Exception;
}

