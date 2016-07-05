/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package com.oracle.appbundlers.tests.functionality.unicode;

import java.io.IOException;
import java.nio.file.Files;

import com.oracle.appbundlers.tests.functionality.AppInfoParametersTest;
import com.oracle.appbundlers.utils.AppWrapper;
import com.oracle.appbundlers.utils.BundlerUtils;
import com.oracle.appbundlers.utils.BundlingManagers;

/**
 * @author Dmitry Ginzburg &lt;dmitry.x.ginzburg@oracle.com&gt;
 */

/**
 * AppInfoParametersTest applied for Unicode
 */
public class UnicodeAppInfoParametersTest extends AppInfoParametersTest {
    protected AppWrapper getApp() throws IOException {
        return new AppWrapper(this.currentParameter.getApp(), Files.createTempDirectory("ÑŽÐ½Ð¸ÐºÐ¾Ð´Ð½Ð°Ñ� Ð´Ð¸Ñ€ÐµÐºÑ‚Ð¾Ñ€Ð¸Ñ� Ñ� Ð¿Ñ€Ð¾Ð±ÐµÐ»Ð°Ð¼Ð¸"));
    }

    @Override
    protected String title() {
        return "Ð¥ÐµÐ»Ð»Ð¾Ð’Ð¾Ñ€Ð»Ð´ Ð°Ð¿Ð¿Ð»Ð¸ÐºÐµÐ¹ÑˆÐ½";
    }

    @Override
    protected String vendor() {
        return "ÐžÑ€Ð°ÐºÐ» Ð´ÐµÐ²ÐµÐ»Ð¾Ð¿Ð¼ÐµÐ½Ñ‚";
    }

    @Override
    protected String appName() {
        return "Ñ…ÐµÐ»Ð»Ð¾Ð²Ð¾Ñ€Ð»Ð´";
    }

    @Override
    protected String description() {
        return "ÐºÑ€Ð°Ð¹Ð½Ðµ Ð±Ð¾Ð»ÑŒÑˆÐ¾Ðµ Ð¾Ð¿Ð¸Ñ�Ð°Ð½Ð¸Ðµ Ñ�Ð¾ Ñ�Ñ‚Ñ€Ð°Ð½Ð½Ñ‹Ð¼Ð¸ Ñ�Ð¸Ð¼Ð²Ð¾Ð»Ð°Ð¼Ð¸";
    }

    @Override
    protected String email() {
        return "Ð²Ð°Ñ�Ñ�@Ð¿ÑƒÐ¿ÐºÐ¸Ð½.ÐºÐ¾Ð¼";
    }

    @Override
    protected BundlerUtils[] getBundlerUtils() {
        return new BundlerUtils[] {BundlerUtils.EXE};
    }

    @Override
    protected BundlingManagers[] getBundlingManagers() {
        return new BundlingManagers[] {BundlingManagers.CLI};
    }
}
