/*
 * Copyright (c) 2014, 2023, Oracle and/or its affiliates. All rights reserved.
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
package test.scenegraph.fx3d.camera;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import test.javaclient.shared.DragSupport;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.interfaces.camera.CameraTestingFace;
import test.scenegraph.fx3d.utils.CameraTestGroupBuilder;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class CameraAbstractApp extends FX3DAbstractApp implements CameraTestingFace {

    protected Camera camera;
    protected Scene scene;
    protected static int WIDTH = 500;
    protected static int HEIGHT = 600;
    protected DragSupport dsx;
    protected DragSupport dsy;
    protected Group grp;

    protected abstract void initCamera();

    protected abstract void initDrag();

    protected abstract void initCameraMovement();

    protected abstract void initCustomKeys();

    protected abstract Camera addCamera(Scene scene);

    @Override
    public double getFarClip() {
        return camera.getFarClip();
    }

    @Override
    public double getNearClip() {
        return camera.getNearClip();
    }

    @Override
    public void setFarClip(double d) {
        camera.setFarClip(d);
        System.out.println("FarClip=" + d);
    }

    @Override
    public void setNearClip(double d) {
        camera.setNearClip(d);
        System.out.println("NearClip=" + d);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    protected void initScene() {
        grp = new CameraTestGroupBuilder().getGroup();
        scene = createScene(grp, WIDTH, HEIGHT);
        initCamera();
        if (!isTest()) {
            Utils.addBrowser(scene);
            initDrag();
            initCustomKeys();
            initCameraMovement();
            scene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    double d;
                    switch (e.getCharacter()) {
                        case "z":
                            if (getNearClip() > 1) {
                                setNearClip(getNearClip() - 1);
                            }
                            break;
                        case "x":
                            if (getFarClip() > getNearClip() + 1) {
                                setNearClip(getNearClip() + 1);
                            }
                            break;
                        case "c":
                            if (getFarClip() > getNearClip() + 1) {
                                setFarClip(getFarClip() - 1);
                            }
                            break;
                        case "v":
                            setFarClip(getFarClip() + 1);
                            break;
                    }
                }
            });
        }

    }
}
