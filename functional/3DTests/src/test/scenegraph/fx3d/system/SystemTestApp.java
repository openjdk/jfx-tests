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
package test.scenegraph.fx3d.system;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.DefaultMeshBuilder;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrew Glushchenko
 */
public class SystemTestApp extends FX3DAbstractApp {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private Scene scene;

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    protected void initScene() {
        DefaultMeshBuilder dmb = new DefaultMeshBuilder() {

            @Override
            protected double function(double fx, double fy) {
                return 1;
            }
        };
        MeshView meshView = new MeshView(dmb.getTriangleMesh());
        meshView.setScaleX(100);
        meshView.setScaleY(100);
        meshView.setScaleZ(100);
        Sphere s = new Sphere(50);
        s.setTranslateX(WIDTH / 2);
        s.setTranslateY(HEIGHT / 2);
        Group root = new Group(s, meshView);
        scene = createScene(root, WIDTH, HEIGHT);
        scene.setCamera(new PerspectiveCamera());
    }

    public static void main(String[] args) {
        Utils.launch(SystemTestApp.class, args);
    }
}
