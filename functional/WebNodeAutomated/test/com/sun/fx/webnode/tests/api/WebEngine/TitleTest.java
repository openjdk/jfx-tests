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

package com.sun.fx.webnode.tests.api.WebEngine;

import com.sun.fx.webnode.tests.commonUtils.GenericTestClass;
import com.sun.fx.webnode.tests.commonUtils.ToolkitInitializer;
import java.io.File;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringProperty;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * Test for javafx.scene.web.WebView.getTitle method.
 * @author Dmitrij Pochepko
 */
public class TitleTest extends GenericTestClass{

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for javafx.scene.web.WebView.getTitle method. Checks that a valid title is returned
     * by WebEngine.getTitle() method after a document is loaded.
     */
    @Test(timeout=10000)
    public void getTitleTest() {
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                engine.load(getPath(this.getClass(), "resource/example1.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        String title = engine.getTitle();
        Assert.assertEquals("Testing page 1", title);
    }

    @Test(timeout=10000)
    public void titlePropertyTest() {
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                engine.load(getPath(this.getClass(), "resource/example1.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        final ReadOnlyStringProperty titleProperty = engine.titleProperty();
        Assert.assertEquals(titleProperty.get(), "Testing page 1");
        Platform.runLater(new Runnable() {
            public void run() {
                engine.executeScript("window.document.title = \"Test Title 2\";");
            }
        });
        doWait (new Tester() {
            public boolean isPassed () {
                return titleProperty.get().equals("Test Title 2");
            }
        });
    }
}
