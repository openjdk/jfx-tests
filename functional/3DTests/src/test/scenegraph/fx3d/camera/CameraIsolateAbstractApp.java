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
import javafx.geometry.Orientation;
import javafx.scene.Camera;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import test.javaclient.shared.DragSupport;
import test.scenegraph.fx3d.interfaces.camera.CameraIsolateTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class CameraIsolateAbstractApp extends CameraAbstractApp implements CameraIsolateTestingFace{

    @Override
    protected void initCamera() {
        camera = addCamera(scene);
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    protected void initDrag() {
        dsx = new DragSupport(scene, null, Orientation.HORIZONTAL, camera.rotateProperty(), 0.1);
    }

    @Override
    protected void initCameraMovement() {
        scene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                double d;
                switch (e.getCharacter()) {
                    case "i":
                        d = camera.translateZProperty().get();
                        camera.translateZProperty().set(d + 10);
                        break;
                    case "k":
                        d = camera.translateZProperty().get();
                        camera.translateZProperty().set(d - 10);
                        break;
                    case "j":
                        d = camera.translateXProperty().get();
                        camera.translateXProperty().set(d - 10);
                        break;
                    case "l":
                        d = camera.translateXProperty().get();
                        camera.translateXProperty().set(d + 10);
                        break;
                    case "x":
                        camera.setRotationAxis(Rotate.X_AXIS);
                        break;
                    case "y":
                        camera.setRotationAxis(Rotate.Y_AXIS);
                        break;
                    case "z":
                        camera.setRotationAxis(Rotate.Z_AXIS);
                        break;
                }
            }
        });
    }
}
