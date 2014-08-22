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

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.Cone;
import test.scenegraph.fx3d.utils.DefaultMeshBuilder;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.GroupMover;
import test.scenegraph.fx3d.utils.SemiSphere;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class LodTestAbstractApp extends FX3DAbstractApp {

    private Scene scene;
    protected GroupMover rootMV;
    protected GroupMover cameraMV;
    protected Group cameraGroup;
    protected Group root;
    private Camera camera;
    private Shape3D shape = null;
    private Group shapeGroup;
    private Material mtrl;
    private static int SCALE = 100;
    protected static int WIDTH = 500;
    protected static int HEIGHT = 500;
    PointLight pl;
    private DefaultMeshBuilder planeBuilder;
    private Cone cone;
    private SemiSphere ss;

    public enum ShapeType {

        Cone, SemiSphere, Plane, Sphere, Box, Cylinder
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    protected void initScene() {
        pl = new PointLight(Color.GREEN);
        pl.setTranslateX(-300);

        cone = new Cone(1, 1);
        ss = new SemiSphere(3, 120, 120);
        planeBuilder = new PlaneBuilder();
        rootMV = new GroupMover(buildGroup());
        root = new Group(rootMV.getGroup());
        scene = createScene(root, WIDTH, HEIGHT);
        camera = addCamera(scene);
        cameraGroup = new Group(camera);
        cameraMV = new GroupMover(cameraGroup);
        root.getChildren().add(cameraMV.getGroup());
        initTranslations();
        if (!isTest()) {
            System.err.println("Debug mode: init picking.");
            Utils.addBrowser(scene);
            scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    PickResult pr = t.getPickResult();
                    if (pr != null) {
                        System.out.println("Distance: " + pr.getIntersectedDistance());
                        Node n = pr.getIntersectedNode();
                        if (n != null) {
                            System.out.println("LOD: " + n.computeAreaInScreen());
                        } else {
                            System.out.println("Intersected node is null! Check picking tests");
                        }
                    }
                }
            });
        }
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                System.out.println(t.getText());
                switch (t.getText()) {
                    case "1":
                        selectType(ShapeType.Box);
                        break;
                    case "2":
                        selectType(ShapeType.Cone);
                        break;
                    case "3":
                        selectType(ShapeType.Plane);
                        break;
                    case "4":
                        selectType(ShapeType.SemiSphere);
                        break;
                    case "5":
                        selectType(ShapeType.Sphere);
                        break;
                    case "6":
                        selectType(ShapeType.Cylinder);
                        break;
                    case "l":
                        setLight(true);
                        break;
                    case "k":
                        setLight(false);
                        break;
                    case "z":
                        moveObject(-100);
                        break;
                    case "x":
                        moveObject(100);
                        break;
                    case "c":
                        moveGroup(-100);
                        break;
                    case "v":
                        moveGroup(100);
                        break;
                    case "a":
                        moveCamera(-100);
                        break;
                    case "s":
                        moveCamera(100);
                        break;
                    case "d":
                        moveCameraGroup(-100);
                        break;
                    case "f":
                        moveCameraGroup(100);
                        break;
                }
            }
        });
    }

    public Node getNode() {
        return shape;
    }

    public void moveObject(double delta) {
        shape.translateZProperty().set(shape.translateZProperty().get() + delta);
    }

    public void moveGroup(double delta) {
        rootMV.translateZProperty().set(rootMV.translateZProperty().doubleValue() + delta);
    }

    public void moveCameraGroup(double delta) {
        cameraMV.translateZProperty().set(cameraMV.translateZProperty().get() + delta);
    }

    public void moveCamera(double delta) {
        camera.translateZProperty().set(camera.translateZProperty().get() + delta);
    }

    protected abstract void initTranslations();

    protected abstract Camera addCamera(Scene scene);

    private Group buildGroup() {
        mtrl = new PhongMaterial();
        shapeGroup = new Group();
        selectShapeType(ShapeType.Cone);

        return shapeGroup;
    }

    public void setLight(boolean bln) {
        if (bln) {
            if (!shapeGroup.getChildren().contains(pl)) {
                shapeGroup.getChildren().add(pl);
            }
        } else {
            if (shapeGroup.getChildren().contains(pl)) {
                shapeGroup.getChildren().remove(pl);
            }
        }
    }

    public void selectType(ShapeType st) {
        selectShapeType(st);
        initTranslations();
    }

    private void selectShapeType(ShapeType st) {
        if (shape != null) {
            shapeGroup.getChildren().remove(shape);
        }
        switch (st) {
            case Cone:
                shape = cone.getMesh();
                break;
            case Plane:
                shape = new MeshView(planeBuilder.getTriangleMesh());
                break;
            case SemiSphere:
                shape = ss.getMesh();
                break;
            case Sphere:
                shape = new Sphere();
                break;
            case Box:
                shape = new Box();
                break;
            case Cylinder:
                shape = new Cylinder();
                break;
        }
        shape.setMaterial(mtrl);
        shape.setScaleX(SCALE);
        shape.setScaleY(SCALE);
        shape.setScaleZ(SCALE);
        shape.setTranslateZ(0);
        shape.setTranslateX(0);
        shape.setTranslateY(0);
        shapeGroup.getChildren().add(shape);
    }

    private class PlaneBuilder extends DefaultMeshBuilder {

        public PlaneBuilder() {
            scale = 0.3F;
        }

        @Override
        protected double function(double fx, double fy) {
            return 0;
        }
    }
}
