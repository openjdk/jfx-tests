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
package test.scenegraph.fx3d.utils;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.stage.Stage;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class FX3DAbstractApp extends Application {

    private static FX3DAbstractApp inst;
    private static boolean isTest = false;
    protected Stage stage;

    public void reinitScene() {
        initScene();
        stage.setScene(getScene());
    }
    /*Will not work after RT-31878*/
//    private static boolean antiAliasingEnabled = false;
//
//    public static boolean isAntiAliasingEnabled() {
//        return antiAliasingEnabled;
//    }
//
//    public static void setAntiAliasingEnabled(boolean b) {
//        if (!antiAliasingEnabled.equals(b)) {
//            antiAliasingEnabled = b;
//        }
//    }
//
//    protected Scene createScene(Parent p, double width, double height) {
//        return new Scene(p, width, height, true,getAntiAliasingEnabled());
//    }
//    protected SubScene createSubScene(Parent p, double width, double height) {
//        return new SubScene(p, width, height, true,getAntiAliasingEnabled());
//    }
    /*******************************************/
    /*Will work after RT-31878*/
    private static SceneAntialiasing mode = SceneAntialiasing.DISABLED;

    public static SceneAntialiasing getAntiAliasingMode(){
        return mode;
    }
    public static void setAntiAliasingMode(SceneAntialiasing md){
        mode = md;
    }
    protected Scene createScene(Parent p, double width, double height) {
        return new Scene(p, width, height, true,getAntiAliasingMode());
    }
    protected SubScene createSubScene(Parent p, double width, double height) {
        return new SubScene(p, width, height, true,getAntiAliasingMode());
    }
    /*********************************************/
    @Override
    public void start(Stage stage) throws Exception {
        inst = this;
        this.stage = stage;
        reinitScene();
        stage.show();
    }

    public static boolean isTest() {
        return isTest;
    }

    public static void setTest(boolean bln) {
        isTest = bln;
    }

    public static FX3DAbstractApp getInstance() {
        return inst;
    }


    public abstract Scene getScene();

    protected abstract void initScene();
}
