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
package test.scenegraph.fx3d.lighting.scoping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.Cone;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.GroupMover;
import test.scenegraph.fx3d.utils.VisibleLight;

/**
 *
 * @author Andrew Glushchenko
 */
public class LightScopeTestApp extends FX3DAbstractApp {

    public enum TestCaseType {

        SINGLE_SPHERE_CASE, SINGLE_BOX_CASE, SINGLE_CYLINDER_CASE, SINGLE_MESH_CASE,
        MULTIPLE_SHAPE3D_CASE, MULTIPLE_SHAPE_CASE,
    }
    private Scene scene;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private Map<VisibleLight, GroupMover> movers;
    private List<VisibleLight> lights;
    private List<Node> nodes;
    private boolean isThereTestCase;
    private Group root;
    private Node currentDragTarget = null;

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    protected void initScene() {
        root = new Group();
        isThereTestCase = false;
        root.setTranslateX(WIDTH / 2);
        root.setTranslateY(HEIGHT / 2);
        movers = new HashMap<>(2);
        lights = new LinkedList<>();
        nodes = new LinkedList<>();
        scene = createScene(root, WIDTH, HEIGHT);
        scene.setCamera(new PerspectiveCamera());
        if (!isTest()) {
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (t.getButton() == MouseButton.PRIMARY) {
                        PickResult pr = t.getPickResult();
                        if (pr != null) {
                            Node node = pr.getIntersectedNode();
                            if (node != null && lights.contains(node.getParent())) {
                                currentDragTarget = node.getParent();
                                System.out.println(currentDragTarget);
                                return;
                            }
                            if (currentDragTarget != null && node != null && nodes.contains(node)) {
                                ObservableList<Node> scope = ((VisibleLight) currentDragTarget).getBase().getScope();
                                if (scope.contains(node)) {
                                    scope.remove(node);
                                } else {
                                    scope.add(node);
                                }
                                return;
                            }
                        }
                        currentDragTarget = null;
                    }
                }
            });
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (t.getButton() == MouseButton.PRIMARY) {
                        if (currentDragTarget != null) {
                            GroupMover mv = movers.get(currentDragTarget);
                            if (mv != null) {
                                mv.translateXProperty().set(t.getSceneX() - WIDTH / 2);
                                mv.translateYProperty().set(t.getSceneY() - HEIGHT / 2);
                            }
                        }
                    }
                }
            });
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    switch (t.getText()) {
                        case "1":
                            initTestCase(TestCaseType.SINGLE_SPHERE_CASE);
                            break;
                        case "2":
                            initTestCase(TestCaseType.SINGLE_BOX_CASE);
                            break;
                        case "3":
                            initTestCase(TestCaseType.SINGLE_CYLINDER_CASE);
                            break;
                        case "4":
                            initTestCase(TestCaseType.SINGLE_MESH_CASE);
                            break;
                        case "5":
                            initTestCase(TestCaseType.MULTIPLE_SHAPE3D_CASE);
                            break;
                        case "6":
                            initTestCase(TestCaseType.MULTIPLE_SHAPE_CASE);
                            break;
                        case "+":
                            getLightMover(buildNewLight(VisibleLight.LightType.Point, Color.RED)).translateXProperty().set(240);
                            break;
                        case "=":
                            getLightMover(buildNewLight(VisibleLight.LightType.Ambient, Color.RED)).translateXProperty().set(240);
                            break;
                    }
                }
            });
        }
    }

    public GroupMover getLightMover(VisibleLight light) {
        return movers.get(light);
    }

    public VisibleLight[] getVisibleLights() {
        return lights.toArray(new VisibleLight[0]);
    }

    public Node[] getNodes() {
        return nodes.toArray(new Node[0]);
    }

    public VisibleLight buildNewLight(VisibleLight.LightType lt, Color color) {
        VisibleLight newLight = new VisibleLight(color, lt);
        GroupMover lightMv = new GroupMover(newLight);
        root.getChildren().add(lightMv.getGroup());
        lights.add(newLight);
        movers.put(newLight, lightMv);
        return newLight;
    }

    public void deleteLight(VisibleLight light) {
        root.getChildren().remove(movers.get(light).getGroup());
        movers.remove(light);
        lights.remove(light);
    }

    public void initTestCase(TestCaseType type) {
        if (isThereTestCase) {
            root.getChildren().clear();
            nodes.clear();
            lights.clear();
            movers.clear();
        }
        switch (type) {
            case SINGLE_SPHERE_CASE:
                root.getChildren().add(buildSingle(new Sphere(50)));
                break;
            case SINGLE_BOX_CASE:
                root.getChildren().add(buildSingle(new Box(100, 100, 100)));
                break;
            case SINGLE_CYLINDER_CASE:
                root.getChildren().add(buildSingle(new Cylinder(50, 100)));
                break;
            case SINGLE_MESH_CASE:
                Cone cone = new Cone(50, 100);
                root.getChildren().add(buildSingle(cone.getMesh()));
                break;
            case MULTIPLE_SHAPE3D_CASE:
                root.getChildren().add(buildMultipleShapes3D());
                break;
            case MULTIPLE_SHAPE_CASE:
                root.getChildren().add(buildMultipleShapes());
                break;
        }
        isThereTestCase = true;
    }

    private Group buildSingle(Shape3D shape) {
        Group grp = new Group(shape);
        nodes.add(shape);
        return grp;
    }

    private Group buildMultipleShapes3D() {
        Box box = new Box(100, 100, 100);
        Sphere sphere = new Sphere(50);
        Cylinder cyl = new Cylinder(50, 100);
        Cone cone = new Cone(50, 100);
        box.setTranslateX(-100);
        box.setTranslateY(-150);

        sphere.setTranslateX(100);
        sphere.setTranslateY(-50);

        cyl.setTranslateX(-100);
        cyl.setTranslateY(50);

        cone.getMesh().setTranslateX(100);
        cone.getMesh().setTranslateY(150);

        nodes.add(box);
        nodes.add(sphere);
        nodes.add(cyl);
        nodes.add(cone.getMesh());
        return new Group(box, sphere, cyl, cone.getMesh());
    }

    private Group buildMultipleShapes() {
        Box box = new Box(100, 100, 100);
        Sphere sphere = new Sphere(50);
        Cylinder cyl = new Cylinder(50, 100);
        Cone cone = new Cone(50, 100);
        Rectangle rect = new Rectangle(50, 50);
        rect.setFill(Color.WHITESMOKE);
        box.setTranslateX(-100);
        box.setTranslateY(-150);

        sphere.setTranslateX(100);
        sphere.setTranslateY(-50);

        cyl.setTranslateX(-100);
        cyl.setTranslateY(50);

        cone.getMesh().setTranslateX(100);
        cone.getMesh().setTranslateY(150);

        rect.setTranslateX(-25);
        rect.setTranslateY(-25);
        rect.setRotationAxis(Rotate.Y_AXIS);
        rect.setRotate(45);

        nodes.add(box);
        nodes.add(sphere);
        nodes.add(cyl);
        nodes.add(cone.getMesh());
        nodes.add(rect);

        return new Group(box, sphere, cyl, cone.getMesh(), rect);
    }

    public static void main(String[] args) {
        Utils.launch(LightScopeTestApp.class, args);
    }
}
