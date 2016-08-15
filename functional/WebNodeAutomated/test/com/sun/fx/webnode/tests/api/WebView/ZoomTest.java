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

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * Test for WebView.zoom method
 * @author Dmitry Ginzburg
 */
public class ZoomTest extends GenericTestClass {

    final static int zoom = 2;
    final double precision = 1e-3;
    DoubleProperty zoomProperty;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    @Test
    public void zoomPropertyTest () {
        view = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initView();
            }
        });
        doWait (new Tester() {
            public boolean isPassed () {
                return view != null;
            }
        });
        Platform.runLater(new Runnable() {
            public void run() {
                zoomProperty = view.zoomProperty();
            }
        });
        doWait (new Tester() {
            public boolean isPassed () {
                return zoomProperty != null;
            }
        });
        Assert.assertEquals (zoomProperty.get(), 1., precision);
        Platform.runLater(new Runnable() {
            public void run() {
                zoomProperty.set(zoom);
            }
        });
        doWait (new Tester() {
           public boolean isPassed () {
               return Math.abs(zoomProperty.get() - zoom) <= precision;
           }
        });
        Assert.assertEquals(zoomProperty.get(), view.getZoom(), precision);
    }

    @Test
    public void getSetZoomTest () {
        view = null;
        Platform.runLater(new Runnable() {
            public void run() {
                initView();
            }
        });
        doWait (new Tester() {
            public boolean isPassed () {
                return view != null;
            }
        });
        Assert.assertEquals (view.getZoom(), 1., precision);
        Platform.runLater(new Runnable() {
            public void run() {
                view.setZoom(zoom);
            }
        });
        doWait (new Tester() {
           public boolean isPassed () {
               return Math.abs(view.getZoom() - zoom) <= precision;
           }
        });
    }
}
