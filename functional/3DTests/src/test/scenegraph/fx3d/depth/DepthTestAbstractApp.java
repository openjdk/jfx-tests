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
package test.scenegraph.fx3d.depth;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import test.javaclient.shared.DragSupport;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class DepthTestAbstractApp extends FX3DAbstractApp {

    private static int SCALE = 100;
    private static int WIDTH = 500;
    private static int HEIGHT = 500;
    protected Group root;
    protected Scene scene = null;
    protected BaseCase iCase;
    private DragSupport dsx;
    private DragSupport dsy;
    final protected DoubleProperty rotationX = new SimpleDoubleProperty();
    final protected DoubleProperty rotationY = new SimpleDoubleProperty();

    @Override
    public Scene getScene(){
        return scene;
    }

    @Override
    protected void initScene(){
        root = new Group(getLight());
        root.setScaleX(SCALE);
        root.setScaleY(SCALE);
        root.setScaleZ(SCALE);
        root.setTranslateX(SCALE * 2);
        root.setTranslateY(SCALE * 2);
        scene = addCamera(createScene(root, WIDTH, HEIGHT));
        openPage(getDefaultPage());
        rotationX.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                iCase.getRotationBox().setRotateX(t1.doubleValue());
            }
        });
        rotationY.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                iCase.getRotationBox().setRotateY(t1.doubleValue());
            }
        });
        if (!isTest()) {
            initKeys();
            dsx = new DragSupport(scene, null, Orientation.HORIZONTAL, rotationY, 3);
            dsy = new DragSupport(scene, null, Orientation.VERTICAL, rotationX, 3);
        }
    }

    private Scene addCamera(Scene scene) {
        PerspectiveCamera pc = new PerspectiveCamera(false);
        scene.setCamera(pc);
        return scene;
    }

    private LightBase getLight() {
        PointLight pl = new PointLight(Color.WHITE);
        pl.setTranslateZ(-10);
        return pl;
    }
    public void setRotateX(double d){
        iCase.getRotationBox().setRotateX(d);
    }
    public void setRotateY(double d){
        iCase.getRotationBox().setRotateY(d);
    }
    public void setRotateZ(double d){
        iCase.getRotationBox().setRotateZ(d);
    }

    public abstract void openPage(String name);

    protected abstract void initKeys();

    protected abstract String getDefaultPage();
}
