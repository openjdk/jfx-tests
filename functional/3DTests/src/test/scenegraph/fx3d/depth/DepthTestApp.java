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
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrey Glushchenko
 */
public class DepthTestApp extends DepthTestAbstractApp {

    public enum Pages {

        DepthSpheresCase, DepthBoxesCase, DepthCylindersCase
    }

    @Override
    public void openPage(String name) {
        Pages p = Pages.valueOf(name);
        rotationX.set(0);
        rotationY.set(0);
        root.getChildren().remove(iCase);
        switch (p) {
            case DepthSpheresCase:
                iCase = new DepthSpheresCase();
                break;
            case DepthCylindersCase:
                iCase = new DepthCylindersCase();
                break;
            case DepthBoxesCase:
                iCase = new DepthBoxesCase();
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
                        openPage(Pages.DepthSpheresCase.name());
                        break;
                    case "2":
                        openPage(Pages.DepthCylindersCase.name());
                        break;
                    case "3":
                        openPage(Pages.DepthBoxesCase.name());
                        break;
                }
            }
        });
    }

    @Override
    protected String getDefaultPage() {
        return Pages.DepthSpheresCase.name();
    }

    public static void main(String[] args) {
        System.setProperty("prism.dirtyopts", "false");

        Utils.launch(DepthTestApp.class, args);
    }

    private class DepthSpheresCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Sphere(1),
                new Sphere(1),
            };
            result[1].setTranslateX(1);
            result[0].setTranslateX(-1);
            return result;

        }
    }

    private class DepthBoxesCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Box(2, 2, 2),
                new Box(2, 2, 2)
            };
            result[1].setTranslateX(1.5);
            result[0].setTranslateX(-1.5);
            return result;
        }
    }

    private class DepthCylindersCase extends BaseCase {

        @Override
        protected Shape3D[] getShapes() {
            Shape3D[] result = {
                new Cylinder(1.3, 2),
                new Cylinder(1.3, 2)
            };
            result[1].setTranslateX(2);
            result[0].setTranslateX(-1);
            return result;
        }
    }
}
