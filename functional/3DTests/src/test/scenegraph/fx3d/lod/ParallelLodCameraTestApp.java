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
package test.scenegraph.fx3d.lod;

import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrew Glushchenko
 */
public class ParallelLodCameraTestApp extends LodTestAbstractApp {

    @Override
    protected void initTranslations() {
        cameraMV.translateXProperty().set(-WIDTH / 2);
        cameraMV.translateYProperty().set(-HEIGHT / 2);
        cameraMV.translateZProperty().set(-100);
//                cameraGroup.translateXProperty().set(-WIDTH / 2);
//        cameraGroup.translateYProperty().set(-HEIGHT / 2);
//        cameraGroup.translateZProperty().set(-100);
    }

    @Override
    protected Camera addCamera(Scene scene) {
        ParallelCamera pc = new ParallelCamera();
        scene.setCamera(pc);
        return pc;
    }

    public static void main(String[] args) {
        Utils.launch(ParallelLodCameraTestApp.class, args);
    }
}
