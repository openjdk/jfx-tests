/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
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
