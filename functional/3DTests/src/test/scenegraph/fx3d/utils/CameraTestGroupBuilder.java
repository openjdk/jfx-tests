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
package test.scenegraph.fx3d.utils;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

/**
 *
 * @author Andrew Glushchenko
 */
public class CameraTestGroupBuilder {

    private Shape3D meshView[];
    private PhongMaterial topMtrl;
    private PhongMaterial bottomMtrl;
    private PhongMaterial leftMtrl;
    private PhongMaterial rightMtrl;
    private PhongMaterial frontMtrl;
    private PhongMaterial backMtrl;
    private PhongMaterial borderMtrl;
    private Group grp;
    private Group root;
    private static int side = 10;
    private static double radius = 1;
    private static double SCALE = 100;

    public Group getGroup() {
        return buildGroup();
    }

    private Material getMtrl(int x, int y, int z) {
        if (((x == 0 || x == side - 1) && (y == 0 || y == side - 1))
                || ((x == 0 || x == side - 1) && (z == 0 || z == side - 1))
                || ((z == 0 || z == side - 1) && (y == 0 || y == side - 1))) {
            return borderMtrl;
        }
        if (x == 0) {
            return leftMtrl;
        } else if (x == side - 1) {
            return rightMtrl;
        } else if (y == 0) {
            return topMtrl;
        } else if (y == side - 1) {
            return bottomMtrl;
        } else if (z == 0) {
            return frontMtrl;
        } else if (z == side - 1) {
            return backMtrl;
        }
        return null;
    }

    private void initMaterials() {
        topMtrl = new PhongMaterial();
        topMtrl.setDiffuseColor(Color.LIGHTGRAY);
        topMtrl.setSpecularColor(Color.rgb(30, 30, 30));
        bottomMtrl = new PhongMaterial();
        bottomMtrl.setDiffuseColor(Color.RED);
        bottomMtrl.setSpecularColor(Color.rgb(30, 30, 30));
        leftMtrl = new PhongMaterial();
        leftMtrl.setDiffuseColor(Color.YELLOW);
        leftMtrl.setSpecularColor(Color.rgb(30, 30, 30));
        rightMtrl = new PhongMaterial();
        rightMtrl.setDiffuseColor(Color.BLUE);
        rightMtrl.setSpecularColor(Color.rgb(30, 30, 30));
        frontMtrl = new PhongMaterial();
        frontMtrl.setDiffuseColor(Color.GREEN);
        frontMtrl.setSpecularColor(Color.rgb(30, 30, 30));
        backMtrl = new PhongMaterial();
        backMtrl.setDiffuseColor(Color.AQUA);
        backMtrl.setSpecularColor(Color.rgb(30, 30, 30));
        borderMtrl = new PhongMaterial();
        borderMtrl.setDiffuseColor(Color.BLACK);
        borderMtrl.setSpecularColor(Color.rgb(30, 30, 30));
    }

    public Group buildGroup() {
        initMaterials();
        meshView = new Shape3D[(int) Math.pow(side, 3) - (int) Math.pow(side - 2, 3)];
        int i = 0;
        for (int x = 0; x < side; x++) {
            for (int y = 0; y < side; y++) {
                for (int z = 0; z < side; z++) {
                    if (x == 0 || y == 0 || z == 0 || z == side - 1 || y == side - 1 || x == side - 1) {
                        meshView[i] = new Sphere(radius);
                        meshView[i].setMaterial(getMtrl(x, y, z));
                        meshView[i].setScaleX(SCALE);
                        meshView[i].setScaleY(SCALE);
                        meshView[i].setScaleZ(SCALE);
                        meshView[i].setTranslateX((x - (side) / 2) * SCALE * 2 * radius + SCALE * radius);
                        meshView[i].setTranslateY((y - (side) / 2) * SCALE * 2 * radius + SCALE * radius);
                        meshView[i].setTranslateZ((z - (side) / 2) * SCALE * 2 * radius + SCALE * radius);
                        i++;
                    }
                }
            }
        }

        root = new Group(meshView);
        root.setFocusTraversable(true);
        grp = new Group(root);
        return grp;
    }
}
