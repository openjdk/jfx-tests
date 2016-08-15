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
package test.scenegraph.fx3d.subscene.camera;

import test.scenegraph.fx3d.interfaces.Mover;
import test.scenegraph.fx3d.interfaces.camera.CameraAsChildTestingFace;
import test.scenegraph.fx3d.utils.GroupMover;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class SubSceneCameraAsChildAbstractApp extends SubSceneCameraAbstractApp implements CameraAsChildTestingFace {

    private Mover generalCameraMover;
    private Mover[] camMovers = new GroupMover[scenesCount];

    @Override
    protected void preInitScene() {
        generalCameraMover = new GroupMover();
    }


    @Override
    protected void initCamera(int num) {
        if (camMovers[num] != null) {
            grps[num].getChildren().remove(camMovers[num].getGroup());
            unbind(num);
        }
        cameras[num] = addCamera(subScenes[num]);
        camMovers[num] = new GroupMover(cameras[num]);
        grps[num].getChildren().add(camMovers[num].getGroup());
        bind(num);
    }

    private void bind(int num) {
        camMovers[num].rotateXProperty().set(generalCameraMover.rotateXProperty().get());
        camMovers[num].rotateXProperty().set(generalCameraMover.rotateYProperty().get());
        camMovers[num].rotateXProperty().set(generalCameraMover.rotateZProperty().get());
        camMovers[num].translateXProperty().set(generalCameraMover.translateXProperty().get());
        camMovers[num].translateYProperty().set(generalCameraMover.translateYProperty().get());
        camMovers[num].translateZProperty().set(generalCameraMover.translateZProperty().get());
        generalCameraMover.rotateXProperty().bindBidirectional(camMovers[num].rotateXProperty());
        generalCameraMover.rotateYProperty().bindBidirectional(camMovers[num].rotateYProperty());
        generalCameraMover.rotateZProperty().bindBidirectional(camMovers[num].rotateZProperty());
        generalCameraMover.translateXProperty().bindBidirectional(camMovers[num].translateXProperty());
        generalCameraMover.translateYProperty().bindBidirectional(camMovers[num].translateYProperty());
        generalCameraMover.translateZProperty().bindBidirectional(camMovers[num].translateZProperty());
    }

    private void unbind(int num) {
        generalCameraMover.rotateXProperty().unbindBidirectional(camMovers[num].rotateXProperty());
        generalCameraMover.rotateYProperty().unbindBidirectional(camMovers[num].rotateYProperty());
        generalCameraMover.rotateZProperty().unbindBidirectional(camMovers[num].rotateZProperty());
        generalCameraMover.translateXProperty().unbindBidirectional(camMovers[num].translateXProperty());
        generalCameraMover.translateYProperty().unbindBidirectional(camMovers[num].translateYProperty());
        generalCameraMover.translateZProperty().unbindBidirectional(camMovers[num].translateZProperty());
    }

    @Override
    public Mover getCameraMover() {
        return generalCameraMover;
    }
}
