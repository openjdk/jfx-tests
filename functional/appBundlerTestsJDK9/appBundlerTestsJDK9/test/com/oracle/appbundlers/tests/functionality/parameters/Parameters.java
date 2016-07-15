/*
 * Copyright (c) 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package com.oracle.appbundlers.tests.functionality.parameters;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.oracle.appbundlers.tests.functionality.functionalinterface.AdditionalParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.BasicParams;
import com.oracle.appbundlers.tests.functionality.functionalinterface.VerifiedOptions;
import com.oracle.appbundlers.tests.functionality.jdk9test.ExtensionType;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.Constants;

/**
 * @author Ramesh BG
 *
 */
public abstract class Parameters implements Constants {

    protected AppWrapper app;

    /*
     * Functional Interface References
     */
    private AdditionalParams additionalParams;

    private BasicParams basicParams;

    private VerifiedOptions verifiedOptions;

    public Parameters() {

    }

    public Parameters(BasicParams basicParams,
            AdditionalParams additionalParams,
            VerifiedOptions verifiedOptions) {
        this.basicParams = basicParams;
        this.additionalParams = additionalParams;
        this.verifiedOptions = verifiedOptions;
    }

    public abstract void initializeDefaultApp() throws Exception;

    public AppWrapper getApp() throws IOException {
        return this.app;
    }

    public void setApp(AppWrapper app) {
        this.app = app;
    }

    public BasicParams getBasicParamsFunctionalInterface() {
        return this.basicParams;
    }

    public void setBasicParams(BasicParams basicParams) {
        this.basicParams = basicParams;
    }

    public void setAdditionalParams(AdditionalParams additionalParams) {
        this.additionalParams = additionalParams;
    }

    public void setVerifiedOptions(VerifiedOptions verifiedOptions) {
        this.verifiedOptions = verifiedOptions;
    }

    public Map<String, Object> getBasicParams() throws Exception {
        return requireNonNull(this.basicParams, Collections.emptyMap());
    }

    public Map<String, Object> getAdditionalParams() throws Exception {
        return requireNonNull(this.additionalParams, Collections.emptyMap());
    }

    public Map<String, Object> getVerifiedOptions() throws Exception {
        return requireNonNull(this.verifiedOptions, getAdditionalParams());
    }

    protected <T> Map<String, Object> requireNonNull(T obj, Map<String, Object> defaultValue) throws Exception {
        if(obj instanceof VerifiedOptions) {
            return ((VerifiedOptions) obj).getVerifiedOptions();
        } else if(obj instanceof AdditionalParams) {
            return ((AdditionalParams) obj).getAdditionalParams();
        } else if(obj instanceof BasicParams) {
            return ((BasicParams) obj).getBasicParams(this.app);
        }
        return defaultValue;
    }

    public abstract ExtensionType getExtension();
}
