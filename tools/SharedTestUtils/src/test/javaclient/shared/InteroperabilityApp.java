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
package test.javaclient.shared;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static test.javaclient.shared.TestUtil.isEmbedded;

public abstract class InteroperabilityApp extends Application {

    static {
        System.setProperty("prism.lcdtext", "false");
    }
    Scene scene;
    protected Stage stage;

    protected abstract Scene getScene();

    protected boolean needToLoadCustomFont() {
        return true;
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        this.stage = stage;
        stage.setTitle(this.getClass().getSimpleName());
        if (isEmbedded()) {
            stage.setFullScreen(true);
        }
        scene = getScene();
        if (needToLoadCustomFont()) {
            Utils.setCustomFont(scene);
        }
        stage.setScene(scene);
        stage.show();
        stage.toFront();
        stage.requestFocus();
    }
}
