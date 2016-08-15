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
import javafx.scene.Group;
import javafx.scene.SubScene;
import org.junit.Assert;
import test.scenegraph.fx3d.interfaces.camera.CameraTestingFace;
import test.scenegraph.fx3d.subscene.SubSceneAbstractApp;
import test.scenegraph.fx3d.utils.CameraTestGroupBuilder;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class SubSceneCameraAbstractApp extends SubSceneAbstractApp implements CameraTestingFace {

    protected Camera[] cameras = new Camera[scenesCount];
    protected SubScene[] subScenes = new SubScene[scenesCount];
    protected Group[] grps = new Group[scenesCount];
    protected static double delta = 0.01;
    protected static final String FAR_CLIP_DIFFERS = "Far clip is differs!";
    protected static final String NEAR_CLIP_DIFFERS = "Near clip is differs!";
    protected static String FIELD_OF_VIEW_DIFFERS = "Field of view differs!";
    protected static String VERTICAL_FIELD_OF_VIEW_DIFFERS = "Vertical field of view differs!";

    protected abstract void initCamera(int num);

    protected abstract void preInitScene();

//    protected abstract void initDrag();
//
//    protected abstract void initCameraMovement();
//
//    protected abstract void initCustomKeys();
    protected abstract Camera addCamera(SubScene scene);

    @Override
    protected void initScene() {
        preInitScene();
        super.initScene();
    }

    @Override
    public double getFarClip() {
        checkFarClip();
        return cameras[0].getFarClip();
    }

    @Override
    public double getNearClip() {
        checkNearClip();
        return cameras[0].getNearClip();
    }

    @Override
    public void setFarClip(double d) {
        for (int i = 0; i < scenesCount; i++) {
            cameras[i].setFarClip(d);
        }
        checkFarClip();
    }

    @Override
    public void setNearClip(double d) {
        for (int i = 0; i < scenesCount; i++) {
            cameras[i].setNearClip(d);
        }
        checkNearClip();
    }

    @Override
    protected SubScene getTopLeftScene() {
        return buildSubScene(0);
    }

    @Override
    protected SubScene getTopRightScene() {
        return buildSubScene(1);
    }

    @Override
    protected SubScene getDownLeftScene() {
        return buildSubScene(2);
    }

    protected void checkFarClip() {
        Assert.assertEquals(FAR_CLIP_DIFFERS, cameras[0].getFarClip(), cameras[1].getFarClip(), delta);
        Assert.assertEquals(FAR_CLIP_DIFFERS, cameras[0].getFarClip(), cameras[2].getFarClip(), delta);
        Assert.assertEquals(FAR_CLIP_DIFFERS, cameras[2].getFarClip(), cameras[1].getFarClip(), delta);
    }

    protected void checkNearClip() {
        Assert.assertEquals(NEAR_CLIP_DIFFERS, cameras[0].getNearClip(), cameras[1].getNearClip(), delta);
        Assert.assertEquals(NEAR_CLIP_DIFFERS, cameras[0].getNearClip(), cameras[2].getNearClip(), delta);
        Assert.assertEquals(NEAR_CLIP_DIFFERS, cameras[2].getNearClip(), cameras[1].getNearClip(), delta);
    }

    private SubScene buildSubScene(int num) {
        grps[num] = new CameraTestGroupBuilder().getGroup();
        subScenes[num] = createSubScene(grps[num], SS_WIDTH, SS_HEIGHT);
        initCamera(num);
        return subScenes[num];
    }
}
