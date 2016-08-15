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

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import test.scenegraph.fx3d.interfaces.camera.CameraIsolateTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class SubSceneCameraIsolateAbstractApp extends SubSceneCameraAbstractApp implements CameraIsolateTestingFace{

    private Camera bindedCamera;

    @Override
    protected void preInitScene(){
        bindedCamera = new PerspectiveCamera();
    }
    @Override
    protected void initCamera(int num) {
        if(cameras[num]!=null){
            unbind(num);
        }
        cameras[num] = addCamera(subScenes[num]);
        bind(num);
    }


    @Override
    public Camera getCamera() {
        return bindedCamera;
    }
    protected void bind(int num){
        cameras[num].rotateProperty().set(bindedCamera.rotateProperty().get());
        cameras[num].rotationAxisProperty().set(bindedCamera.rotationAxisProperty().get());
        cameras[num].translateXProperty().set(bindedCamera.translateXProperty().get());
        cameras[num].translateYProperty().set(bindedCamera.translateYProperty().get());
        cameras[num].translateZProperty().set(bindedCamera.translateZProperty().get());
        cameras[num].rotateProperty().bindBidirectional(bindedCamera.rotateProperty());
        cameras[num].rotationAxisProperty().bindBidirectional(bindedCamera.rotationAxisProperty());
        cameras[num].translateXProperty().bindBidirectional(bindedCamera.translateXProperty());
        cameras[num].translateYProperty().bindBidirectional(bindedCamera.translateYProperty());
        cameras[num].translateZProperty().bindBidirectional(bindedCamera.translateZProperty());

    }
    protected void unbind(int num){
        cameras[num].rotateProperty().unbindBidirectional(bindedCamera.rotateProperty());
        cameras[num].rotationAxisProperty().unbindBidirectional(bindedCamera.rotationAxisProperty());
        cameras[num].translateXProperty().unbindBidirectional(bindedCamera.translateXProperty());
        cameras[num].translateYProperty().unbindBidirectional(bindedCamera.translateYProperty());
        cameras[num].translateZProperty().unbindBidirectional(bindedCamera.translateZProperty());
    }
}
