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
 */
package test.scenegraph.app;

import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.*;
import test.javaclient.shared.Utils;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class Transforms3DApp extends TransformsApp
{

    {
        Affine aff = Transform.affine(1, -0.15, 0, 50, 0, 0.95, 0, 0, -1, 1.05, 0.95, 0);
        TransformsApp.TransformToggle[] tt = {
            new TransformsApp.TransformToggle("scale", new Scale(.33f, .66f, .45f, 100, 100, -100)),
            new TransformsApp.TransformToggle("rotate", new Rotate(45f, new Point3D(100, 100, 0))),
            new TransformsApp.TransformToggle("shear", new Shear(-.33f, .1f, 80, 70)),
            new TransformsApp.TransformToggle("translate", new Translate(50, 50, 100)),
            new TransformsApp.TransformToggle("affine", aff)
        };
        setTransformToggle(tt);
    }

    @Override
    protected Scene getScene()
    {
        Scene zScene = super.getScene();
        zScene.getRoot().setDepthTest(DepthTest.ENABLE);
        zScene.setCamera(new PerspectiveCamera());
        //System.out.println("Camera angle " + new PerspectiveCamera().getFieldOfView());
        return zScene;
    }

    public static void main(String[] args) {
        Utils.launch(Transforms3DApp.class, args);
    }

}
