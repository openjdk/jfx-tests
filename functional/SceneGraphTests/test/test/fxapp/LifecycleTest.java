/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package test.fxapp;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;

/**
 *
 * @author Sergey Grinev
 */
public class LifecycleTest extends TestBase {

    @BeforeClass
    public static void runUI() {
        LifecycleApp.reset();
        Utils.launch(LifecycleApp.class, LifecycleApp.DEFAULT_PARAMS);
    }

    @Test
    public void init() {
        Assert.assertFalse(LifecycleApp.status.init == -1);
    }

    @Test
    public void start() {
        Assert.assertFalse(LifecycleApp.status.start == -1);
        Assert.assertTrue(LifecycleApp.status.start > LifecycleApp.status.init);
    }

    @Test
    public void unnamedParams() {
        Assert.assertTrue(LifecycleApp.status.unnamed.contains(LifecycleApp.PARAM1));
        Assert.assertTrue(LifecycleApp.status.unnamed.contains(LifecycleApp.PARAM2));
        Assert.assertTrue(LifecycleApp.status.unnamed.contains(LifecycleApp.PARAM3));
        Assert.assertTrue(LifecycleApp.status.unnamed.contains(LifecycleApp.PARAMZ));
        Assert.assertTrue(LifecycleApp.status.unnamed.contains(LifecycleApp.FAKENAMED));
        Assert.assertEquals(5, LifecycleApp.status.unnamed.size());
    }

    @Test
    public void namedParams() {
        Assert.assertEquals(LifecycleApp.status.named.get(LifecycleApp.NAME1), LifecycleApp.VALUE1);
        Assert.assertEquals(LifecycleApp.status.named.get(LifecycleApp.NAME2), "");
        Assert.assertEquals(LifecycleApp.status.named.size(), 2);
    }

    public void allParams() {
        Assert.assertEquals(LifecycleApp.status.raw.size(), LifecycleApp.DEFAULT_PARAMS.length);
        for (String st : LifecycleApp.DEFAULT_PARAMS) {
            LifecycleApp.status.raw.contains(st);
        }
    }
}
