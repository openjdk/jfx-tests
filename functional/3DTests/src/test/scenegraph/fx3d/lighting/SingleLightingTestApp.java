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
package test.scenegraph.fx3d.lighting;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.DragSupport;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.GroupMover;
import test.scenegraph.fx3d.utils.SemiSphere;
import test.scenegraph.fx3d.utils.VisibleLight;

/**
 *
 * @author Andrew Glushchenko
 */
public class SingleLightingTestApp extends FX3DAbstractApp {

    private static int WIDTH = 500;
    private static int HEIGHT = 500;
    private static int SCALE = 100;
    private Group root;
    private SemiSphere ss;
    private Group lightGroup;
    private GroupMover lightmv;
    private GroupMover rootmv;
    private Scene scene;
    private PhongMaterial material;
    private Camera cm;
    private DragSupport dsx;
    private DragSupport dsy;
    VisibleLight vl = null;

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    protected void initScene() {
        Group grp = new Group(buildGroup());
        scene = createScene(grp, WIDTH, HEIGHT);
        cm = addCamera(scene);
        grp.getChildren().add(new Group(cm));
        rootmv.translateXProperty().set(WIDTH / 2);
        rootmv.translateYProperty().set(HEIGHT / 2);
        rootmv.translateZProperty().set(150);
        setLightType(VisibleLight.LightType.Point);
        if (!isTest()) {
            scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    switch (t.getCharacter()) {
                        case "a":
                            setLightType(VisibleLight.LightType.Ambient);
                            break;
                        case "p":
                            setLightType(VisibleLight.LightType.Point);
                            break;
                    }
                }
            });
            dsx = new DragSupport(scene, null, Orientation.HORIZONTAL, lightmv.rotateYProperty(), -3);
            dsy = new DragSupport(scene, null, Orientation.VERTICAL, lightmv.rotateXProperty(), 3);

        }
    }

    public void setLightPosition(double angleX, double angleY) {
        lightmv.rotateYProperty().set(angleY);
        lightmv.rotateXProperty().set(angleX);
        System.out.println(angleX + " " + angleY);
    }

    public ObservableList<Node> getScope() {
        if (vl != null) {
            return vl.getBase().getScope();
        }
        return null;
    }

    public void setLightOn(boolean bln) {
        if (vl != null) {
            vl.getBase().setLightOn(bln);
        }
    }

    public boolean checkLightOnProperty() {
        if (vl == null) {
            return false;
        }
        return (vl.getBase().lightOnProperty().get() == vl.getBase().isLightOn());
    }

    public void setColor(Color color) {
        if (vl != null) {
            vl.setColor(color);
        }
    }

    public void setLightType(VisibleLight.LightType lt) {
        vl = new VisibleLight(Color.ANTIQUEWHITE, lt);
        vl.setTranslateZ(-140);
        Sphere balance = new Sphere();
        balance.setTranslateZ(140);
        lightGroup.getChildren().clear();
        lightGroup.getChildren().addAll(vl, balance);
    }

    protected Group buildGroup() {
        material = new PhongMaterial(Color.RED);
        ss = new SemiSphere();
        Shape3D sh = ss.getMesh();
        sh.setMaterial(material);
        sh.setScaleX(SCALE);
        sh.setScaleY(SCALE);
        sh.setScaleZ(SCALE);
        lightGroup = new Group();
        lightmv = new GroupMover(lightGroup);
        root = new Group(lightmv.getGroup(), ss.getGroup());
        rootmv = new GroupMover(root);
        return rootmv.getGroup();
    }

    private Camera addCamera(Scene scene) {
        ParallelCamera pc = new ParallelCamera();
        scene.setCamera(pc);
        return pc;
    }

    public static void main(String[] args) {
        Utils.launch(SingleLightingTestApp.class, args);
    }
}
