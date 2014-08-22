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
package test.scenegraph.fx3d.depth;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class IntersectionTestApp extends DepthTestAbstractApp {

    public enum Pages {

        IntersectionSpheresCase, IntersectionBoxesCase, IntersectionCylindersCase,
        IntersectionSphereAndBoxCase, IntersectionBoxAndCylinderCase, IntersectionSphereAndCylinderCase
    }

    @Override
    protected String getDefaultPage() {
        return Pages.IntersectionSpheresCase.name();
    }

    @Override
    public void openPage(String name) {
        Pages p = Pages.valueOf(name);
        rotationX.set(0);
        rotationY.set(0);
        root.getChildren().remove(iCase);
        switch (p) {
            case IntersectionSpheresCase:
                iCase = new IntersectionSpheresCase();
                break;
            case IntersectionBoxesCase:
                iCase = new IntersectionBoxesCase();
                break;
            case IntersectionCylindersCase:
                iCase = new IntersectionCylindersCase();
                break;
            case IntersectionBoxAndCylinderCase:
                iCase = new IntersectionBoxAndCylinderCase();
                break;
            case IntersectionSphereAndBoxCase:
                iCase = new IntersectionSphereAndBoxCase();
                break;
            case IntersectionSphereAndCylinderCase:
                iCase = new IntersectionSphereAndCylinderCase();
                break;
        }
        root.getChildren().add(iCase);
    }

    @Override
    protected void initKeys() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                switch (t.getText()) {
                    case "1":
                        openPage(Pages.IntersectionSpheresCase.name());
                        break;
                    case "2":
                        openPage(Pages.IntersectionBoxesCase.name());
                        break;
                    case "3":
                        openPage(Pages.IntersectionCylindersCase.name());
                        break;
                    case "4":
                        openPage(Pages.IntersectionSphereAndBoxCase.name());
                        break;
                    case "5":
                        openPage(Pages.IntersectionSphereAndCylinderCase.name());
                        break;
                    case "6":
                        openPage(Pages.IntersectionBoxAndCylinderCase.name());
                        break;
                }
            }
        });
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");

        Utils.launch(IntersectionTestApp.class, args);
    }

    private class IntersectionSpheresCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Sphere(1),
                new Sphere(1.5)
            };
            result[1].setTranslateX(1.5);
            result[1].setTranslateY(1.5);


            return result;
        }
    }

    private class IntersectionBoxesCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Box(4, 4, 1),
                new Box(2, 2, 2)
            };
            result[0].setRotationAxis(Rotate.Y_AXIS);
            result[0].setRotate(45);
            result[1].setRotationAxis(Rotate.X_AXIS);
            result[1].setRotate(45);

            return result;
        }
    }

    private class IntersectionBoxAndCylinderCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Box(2, 2, 2),
                new Cylinder(1, 4)
            };
            result[0].setTranslateX(1);
            result[0].setRotationAxis(Rotate.Y_AXIS);
            result[0].setRotate(45);
            result[1].setRotationAxis(Rotate.X_AXIS);
            result[1].setRotate(45);
            return result;
        }
    }

    private class IntersectionCylindersCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Cylinder(1, 4),
                new Cylinder(1, 4)
            };
            result[0].setTranslateX(1);
            result[0].setRotationAxis(Rotate.Y_AXIS);
            result[0].setRotate(45);
            result[1].setRotationAxis(Rotate.X_AXIS);
            result[1].setRotate(45);
            return result;
        }
    }

    private class IntersectionSphereAndBoxCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Box(2, 2, 2),
                new Sphere(2)
            };
            result[1].setTranslateX(1);
            result[1].setTranslateY(1);
            result[0].setRotationAxis(Rotate.Y_AXIS);
            result[0].setRotate(45);
            return result;
        }
    }

    private class IntersectionSphereAndCylinderCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Cylinder(1.5, 4),
                new Sphere(1.5)
            };
            result[1].setTranslateX(1);
            return result;
        }
    }
}
