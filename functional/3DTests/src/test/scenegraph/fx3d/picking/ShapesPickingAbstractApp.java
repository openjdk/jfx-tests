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

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.PickingTestCase;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class ShapesPickingAbstractApp extends PickingAbstractApp {

    public enum Shape {

        Sphere, Cylinder, Box
    }

    public static void main(String[] args) {
        Utils.launch(ShapesPickingAbstractApp.class, args);
    }

    private ShapesPickingAbstractTestCase spatc;

    @Override
    public PickingTestCase buildTestCase() {
        spatc = buildShapesTestCase();
        return spatc;
    }

    protected abstract ShapesPickingAbstractTestCase buildShapesTestCase();


    public abstract class ShapesPickingAbstractTestCase extends PickingTestCase {

        private Group grp;
        private Shape3D meshView = null;
        private PhongMaterial material;

        @Override
        protected Group buildGroup() {
            material = new PhongMaterial();
            material.setDiffuseColor(Color.LIGHTGRAY);
            material.setSpecularColor(Color.rgb(30, 30, 30));
            grp = new Group();
            setShape(Shape.Sphere);
            return grp;
        }

        public void setShape(Shape shape) {
            if (meshView != null) {
                grp.getChildren().remove(meshView);
            }
            switch (shape) {
                case Box:
                    meshView = new Box();
                    break;
                case Cylinder:
                    meshView = new Cylinder();
                    break;
                case Sphere:
                    meshView = new Sphere();
                    break;
            }
            meshView.setMaterial(material);
            meshView.setScaleX(SCALE);
            meshView.setScaleY(SCALE);
            meshView.setScaleZ(SCALE);
            grp.getChildren().add(meshView);
        }


    }

    public void setShape(Shape shape) {
        spatc.setShape(shape);
    }

    @Override
    protected void initKeys(Scene scene) {
        scene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                switch (t.getText()) {
                    case "1":
                        setShape(Shape.Sphere);
                        break;
                    case "2":
                        setShape(Shape.Box);
                        break;
                    case "3":
                        setShape(Shape.Cylinder);
                        break;
                }
            }
        });
    }

}
