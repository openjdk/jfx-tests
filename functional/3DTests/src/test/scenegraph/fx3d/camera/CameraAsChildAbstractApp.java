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
package test.scenegraph.fx3d.camera;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import test.scenegraph.fx3d.interfaces.Mover;
import test.scenegraph.fx3d.interfaces.camera.CameraAsChildTestingFace;
import test.scenegraph.fx3d.utils.GroupMover;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class CameraAsChildAbstractApp extends CameraAbstractApp implements CameraAsChildTestingFace{

    protected Mover cm;

    @Override
    protected void initDrag() {
    }

    @Override
    public Mover getCameraMover() {
        return cm;
    }

    @Override
    protected void initCamera() {
        if (cm != null) {
            grp.getChildren().remove(cm.getGroup());
        }
        camera = addCamera(scene);
        cm = new GroupMover(camera);
        grp.getChildren().add(cm.getGroup());
    }

    @Override
    protected void initCameraMovement() {
        scene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                double d;
                switch (e.getCharacter()) {
                    case "i":
                        d = cm.translateZProperty().get();
                        cm.translateZProperty().set(d + 10);
                        System.out.println(cm.translateZProperty().get());
                        break;
                    case "k":
                        d = cm.translateZProperty().get();
                        cm.translateZProperty().set(d - 10);
                        System.out.println(cm.translateZProperty().get());
                        break;
                    case "j":
                        d = cm.translateXProperty().get();
                        cm.translateXProperty().set(d - 10);
                        break;
                    case "l":
                        d = cm.translateXProperty().get();
                        cm.translateXProperty().set(d + 10);
                        break;
                    case "a":
                        d = cm.rotateYProperty().get();
                        cm.rotateYProperty().set(d + 10);
                        System.out.println(cm.rotateYProperty().get());
                        break;
                    case "d":
                        d = cm.rotateYProperty().get();
                        cm.rotateYProperty().set(d - 10);
                        System.out.println(cm.rotateYProperty().get());
                        break;
                    case "s":
                        d = cm.rotateXProperty().get();
                        cm.rotateXProperty().set(d - 10);
                        break;
                    case "w":
                        d = cm.rotateXProperty().get();
                        cm.rotateXProperty().set(d + 10);
                        break;
                }
            }
        });

    }
}