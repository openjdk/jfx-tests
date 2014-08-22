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
package test.scenegraph.fx3d.picking;

/**
 *
 * @author Andrew Glushchenko
 */
public class PickingDepthTestApp/*/ extends PickingAbstractApp {

    @Override
    protected void initKeys(Scene scene) {
    }

    public void setDepthTest(DepthTest dt) {
    }

    @Override
    protected PickingTestCase buildTestCase() {
        return new PickingDepthTestCase();
    }

    private class PickingDepthTestCase extends PickingTestCase {

        private Shape3D shapes[];
        private Group grp;
        private PhongMaterial material;

        @Override
        protected Group buildGroup() {
            material = new PhongMaterial();
            material.setDiffuseColor(Color.LIGHTGRAY);
            material.setSpecularColor(Color.rgb(30, 30, 30));
            Cone c = new Cone(1, 2);
            shapes = new Shape3D[4];
            shapes[0] = new Sphere();
            shapes[0].setTranslateX(-2 * SCALE);
            shapes[0].setTranslateY(-2 * SCALE);
            shapes[1] = new Cylinder();
            shapes[1].setTranslateX(2 * SCALE);
            shapes[1].setTranslateY(-2 * SCALE);
            shapes[2] = new Box();
            shapes[2].setTranslateX(-2 * SCALE);
            shapes[2].setTranslateY(2 * SCALE);
            shapes[3] = c.getMesh();
            shapes[3].setTranslateX(2 * SCALE);
            shapes[3].setTranslateY(2 * SCALE);
            for (Shape3D s : shapes) {
                s.setMaterial(material);
                s.setScaleX(SCALE);
                s.setScaleY(SCALE);
                s.setScaleZ(SCALE);
            }
            grp = new Group(shapes);
            grp.setDepthTest(DepthTest.DISABLE);
            return grp;
        }

        @Override
        public double getXTranslation() {
            return 200;
        }

        @Override
        public double getYTranslation() {
            return 200;
        }

        @Override
        public double getZTranslation() {
            return 1000;
        }

    }

    public static void main(String[] args) {
        Utils.launch(PickingDepthTestApp.class, args);
    }
//*/{
}
