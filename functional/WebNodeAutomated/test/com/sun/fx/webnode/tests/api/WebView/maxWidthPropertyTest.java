/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */

package com.sun.fx.webnode.tests.api.WebView;

import com.oracle.jdk.sqe.cc.markup.Covers;
import com.sun.fx.webnode.helperapps.MaxWidthTestApp;
import com.sun.fx.webnode.tests.commonUtils.SizeProperyTestClass;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import org.jemmy.fx.Lookups;
import org.jemmy.fx.Root;
import org.jemmy.control.Wrap;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * Test for javafx.scene.web.WebView.maxWidth property management methods.
 * @author Irina Latysheva
 */
public class maxWidthPropertyTest extends SizeProperyTestClass {
    private final double MAX_WIDTH = 100.0;

    @BeforeClass
    public static void init(){
        useGlassRobot();
        test.javaclient.shared.Utils.launch(MaxWidthTestApp.class, new String[0]);
    }

    /**
     * Test case for javafx.scene.web.WebView.setMaxWidth and javafx.scene.web.WebView.getMaxWidth methods.
     */
    @Test(timeout=10000)
    @Covers(value="javafx.scene.web.WebView.maxWidth.GET", level=Covers.Level.FULL)
    public void test1() {
        view = null;
        Platform.runLater(new Runnable() {
            public void run() {
                view = new WebView();
                view.setMaxWidth(MAX_WIDTH);
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (view != null) && (view.getMaxWidth() == MAX_WIDTH);
            }
        });
    }

    /**
     * Test case for javafx.scene.web.WebView.maxWidthProperty getter method.
     */
    @Test(timeout=10000)
    public void test2() {
        view = null;
        p = null;
        Platform.runLater(new Runnable() {
            public void run() {
                view = new WebView();
                p = view.maxWidthProperty();
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (p != null);
            }
        });
    }

    /**
     * Test case for javafx.scene.web.WebView.setMaxWidth method.
     */
    @Test(timeout=10000)
    @Covers(value="javafx.scene.web.WebView.maxWidth.GET", level=Covers.Level.FULL)
    public void test3() {
        view = null;
        Wrap<? extends Scene> scene = Root.ROOT.lookup(Scene.class).wrap(0);
        view = Lookups.byID(scene, MaxWidthTestApp.VIEW_ID, WebView.class).getControl();
        double result = view.getMaxWidth();
        Assert.assertTrue(result == MaxWidthTestApp.MAX_WIDTH);
    }
}
