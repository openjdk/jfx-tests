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
import org.junit.Test;

/**
 * Test for javafx.scene.web.WebView.resize() method.
 * @author Dmitrij Pochepko
 */
public class resizeTest extends GenericTestClass {
    @org.junit.BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    double precision = 0.001;
    double newHeight = 100;
    double newWidth = 100;
    double prefSize = 50;
    double initialHeight;
    double initialWidth;
    volatile boolean resizeDone;
    /**
     * Test for javafx.scene.web.WebView.resize() method.
     */
    @Test(timeout=10000)
    public void test1() {
        resizeDone = false;
        Platform.runLater(new Runnable() {
            public void run() {
                initView();
        view.setPrefSize(prefSize, prefSize);
        }
    });
        doWait(new Tester() {
            public boolean isPassed() {
            return (view != null)
                && ((view.getWidth() - prefSize) <= precision)
                && ((view.getHeight() - prefSize) <= precision);
        }
    });
        Platform.runLater(new Runnable() {
            public void run() {
                System.out.println("Is resizable: " + view.isResizable());
                initialHeight = view.getHeight();
                initialWidth = view.getWidth();
                System.out.println("View width was: " + view.getWidth());
                System.out.println("View height was: " + view.getHeight());
                view.resize(newHeight, newWidth);
                System.out.println("View width now: " + view.getWidth());
                System.out.println("View height now: " + view.getHeight());
                resizeDone = true;
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return (resizeDone)
            && (view.isResizable() == true)
            ?
                (Math.abs(view.getHeight() - newHeight) <= precision)
                && (Math.abs(view.getWidth() - newWidth) <= precision)
            :
                (Math.abs(view.getHeight() - initialHeight) <= precision)
                && (Math.abs(view.getWidth() - initialWidth) <= precision)
            ;
            }
        });
    }
}
