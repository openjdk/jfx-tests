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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 * Test for javafx.scene.web.WebEngine.addListener method.
 * @author Dmitrij Pochepko
 */
public class addChangeListenerTest extends GenericTestClass {
    private boolean propertyChanged = false;

    @BeforeClass
    public static void init(){
        test.javaclient.shared.Utils.launch(ToolkitInitializer.class, new String[0]);
    }

    /**
     * Test for javafx.scene.web.WebEngine.removeListener method. Checks that change listener
     * starts monitoring WebEngine events when attached.
     */
    @Test(timeout=10000)
    public void test1() {
        Platform.runLater(new Runnable() {
            public void run() {
                initWebEngine();
                engine.locationProperty().addListener(new InvalidationListener() {
                    public void invalidated(Observable ov) {
                        synchronized(locker){
                            propertyChanged = true;
                            locker.notify();
                        }
                    }
                });
                engine.load(url1);
                while(!propertyChanged){
                    synchronized(locker){
                        try {
                            locker.wait();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        doWait(new Tester() {
            public boolean isPassed() {
                return propertyChanged == true;
            }
        });
    }
}
