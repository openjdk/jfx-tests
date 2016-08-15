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
 * questions.
 */
package org.jemmy.image;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 *
 * @author shura
 */
public class TestApp extends Application {
    public static void main(String[] args) throws InterruptedException, IOException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                launch(TestApp.class);
            }
        }).start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox vb = new VBox();
        javafx.scene.shape.Rectangle red = new javafx.scene.shape.Rectangle(20, 20);
        red.setFill(new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.RED), new Stop(1, Color.WHITE)));
        vb.getChildren().add(red);
        javafx.scene.shape.Rectangle green = new javafx.scene.shape.Rectangle(20, 20);
        green.setFill(Color.GREEN);
        vb.getChildren().add(green);
        javafx.scene.shape.Rectangle blue = new javafx.scene.shape.Rectangle(20, 20);
        blue.setFill(Color.BLUE);
        vb.getChildren().add(blue);
        Scene scene = new Scene(vb);
        stage.setScene(scene);
        stage.setWidth(100);
        stage.setHeight(100);
        stage.setX(0);
        stage.setY(0);
        stage.show();
    }
}
