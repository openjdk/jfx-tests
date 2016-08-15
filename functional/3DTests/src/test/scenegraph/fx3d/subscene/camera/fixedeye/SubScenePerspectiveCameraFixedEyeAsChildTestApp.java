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
package test.scenegraph.fx3d.subscene.camera.fixedeye;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import org.junit.Assert;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.interfaces.camera.PerspectiveCameraFixedEyeAsChildTestingFace;
import test.scenegraph.fx3d.subscene.camera.SubSceneCameraAsChildAbstractApp;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubScenePerspectiveCameraFixedEyeAsChildTestApp extends SubSceneCameraAsChildAbstractApp implements PerspectiveCameraFixedEyeAsChildTestingFace {



    @Override
    protected Camera addCamera(SubScene scene) {
        PerspectiveCamera pc = new PerspectiveCamera(true);
        scene.setCamera(pc);
        pc.setFarClip(10000);
        return pc;
    }

    @Override
    public void setFieldOfView(double d) {
        for (int i = 0; i < scenesCount; i++) {
            ((PerspectiveCamera) cameras[i]).setFieldOfView(d);
        }
        checkFieldOfView();
    }

    @Override
    public void setVerticalFieldOfView(boolean bln) {
        for (int i = 0; i < scenesCount; i++) {
            ((PerspectiveCamera) cameras[i]).setVerticalFieldOfView(bln);
        }
        checkVerticalFieldOfView();
    }

    @Override
    public boolean isVerticalFieldOfView() {
        checkVerticalFieldOfView();
        return ((PerspectiveCamera) cameras[0]).isVerticalFieldOfView();
    }

    private void checkFieldOfView() {
        Assert.assertEquals(FIELD_OF_VIEW_DIFFERS,
                ((PerspectiveCamera) cameras[0]).getFieldOfView(),
                ((PerspectiveCamera) cameras[1]).getFieldOfView(), delta);
        Assert.assertEquals(FIELD_OF_VIEW_DIFFERS,
                ((PerspectiveCamera) cameras[2]).getFieldOfView(),
                ((PerspectiveCamera) cameras[1]).getFieldOfView(), delta);
        Assert.assertEquals(FIELD_OF_VIEW_DIFFERS,
                ((PerspectiveCamera) cameras[0]).getFieldOfView(),
                ((PerspectiveCamera) cameras[2]).getFieldOfView(), delta);
    }

    private void checkVerticalFieldOfView() {
        Assert.assertEquals(VERTICAL_FIELD_OF_VIEW_DIFFERS,
                ((PerspectiveCamera) cameras[0]).isVerticalFieldOfView(),
                ((PerspectiveCamera) cameras[1]).isVerticalFieldOfView());
        Assert.assertEquals(VERTICAL_FIELD_OF_VIEW_DIFFERS,
                ((PerspectiveCamera) cameras[2]).isVerticalFieldOfView(),
                ((PerspectiveCamera) cameras[1]).isVerticalFieldOfView());
        Assert.assertEquals(VERTICAL_FIELD_OF_VIEW_DIFFERS,
                ((PerspectiveCamera) cameras[0]).isVerticalFieldOfView(),
                ((PerspectiveCamera) cameras[2]).isVerticalFieldOfView());
    }
    public static void main(String[] args) {
        Utils.launch(SubScenePerspectiveCameraFixedEyeAsChildTestApp.class, args);
    }
}
