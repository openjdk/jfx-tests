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
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import com.oracle.jdk.sqe.cc.markup.Covers;

/**
 * Tests for javafx.scene.web.WebEngine.documentProperty property.
 * @author Irina Latysheva
 */
public class DocumentPropertyTest extends GenericTestClass {

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for javafx.scene.web.WebEngine.documentProperty property getter.
     * Checks that a non-null object is returned.
     */
    @Test(timeout=10000)
    public void test1() {
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                engine.load(getPath(this.getClass(), "resource/example1.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        ReadOnlyObjectProperty<Document> document = engine.documentProperty();
        Assert.assertNotNull(document);
    }

    /**
     * Test for javafx.scene.web.WebEngine.documentProperty property getter.
     * Checks that the documentProperty of WebEngine holds the same document as
     * javafx.scene.web.WebEngine.getDocument() method returns.
     */
    @Test(timeout=10000)
    @Covers(value="javafx.scene.web.WebEngine.document.GET", level=Covers.Level.FULL)
    public void test2() {
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                engine.load(getPath(this.getClass(), "resource/example1.html"));
                prepareWaitPageLoading();
            }
        });
        doWaitPageLoading();
        ReadOnlyObjectProperty<Document> document = engine.documentProperty();
        Assert.assertEquals(engine.getDocument(), document.get());
    }
}
