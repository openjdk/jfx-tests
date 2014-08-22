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

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
public class MultipleLightingTestApp extends FX3DAbstractApp {

    private static int WIDTH = 500;
    private static int HEIGHT = 500;
    private static int SCALE = 100;
    private Group root;
    private SemiSphere ss;
    private GroupMover light1mv;
    private GroupMover light2mv;
    private GroupMover rootmv;
    private Scene scene;
    private PhongMaterial material;
    private Camera cm;
    private DragSupport dsx1;
    private DragSupport dsy1;
    private DragSupport dsx2;
    private DragSupport dsy2;
    VisibleLight vl1 = null;
    VisibleLight vl2 = null;
    private Group light1Group;
    private Group light2Group;

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

        if (!isTest()) {
            scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    switch (t.getCharacter()) {
                        case "a":
                            setLight1Type(VisibleLight.LightType.Ambient);
                            break;
                        case "p":
                            setLight1Type(VisibleLight.LightType.Point);
                            break;
                        case "A":
                            setLight2Type(VisibleLight.LightType.Ambient);
                            break;
                        case "P":
                            setLight2Type(VisibleLight.LightType.Point);
                            break;
                    }
                }
            });
            dsx1 = new DragSupport(scene, KeyCode.SHIFT, Orientation.HORIZONTAL, light1mv.rotateYProperty(), -3);
            dsy1 = new DragSupport(scene, KeyCode.SHIFT, Orientation.VERTICAL, light1mv.rotateXProperty(), 3);
            dsx2 = new DragSupport(scene, KeyCode.CONTROL, Orientation.HORIZONTAL, light2mv.rotateYProperty(), -3);
            dsy2 = new DragSupport(scene, KeyCode.CONTROL, Orientation.VERTICAL, light2mv.rotateXProperty(), 3);
        }
    }

    public void setLight1Position(double angleX, double angleY) {
        light1mv.rotateYProperty().set(angleY);
        light1mv.rotateXProperty().set(angleX);
        System.out.println(angleX + " " + angleY);
    }

    public void setLight2Position(double angleX, double angleY) {
        light2mv.rotateYProperty().set(angleY);
        light2mv.rotateXProperty().set(angleX);
        System.out.println(angleX + " " + angleY);
    }

    public void setLight1Type(VisibleLight.LightType lt) {
        vl1 = new VisibleLight(Color.ANTIQUEWHITE, lt);
        light1Group.getChildren().clear();
        light1Group.getChildren().add(buildLightGroup(vl1));
    }

    public void setLight2Type(VisibleLight.LightType lt) {
        vl2 = new VisibleLight(Color.RED, lt);
        light2Group.getChildren().clear();
        light2Group.getChildren().add(buildLightGroup(vl2));
    }

    public void setLight1Color(Color clr) {
        if (vl1 != null) {
            vl1.setColor(clr);
        }
    }

    public void setLight2Color(Color clr) {
        if (vl2 != null) {
            vl2.setColor(clr);
        }
    }

    protected Group buildGroup() {
        material = new PhongMaterial();
        material.setDiffuseColor(Color.ANTIQUEWHITE);
        material.setSpecularColor(Color.rgb(255, 255, 255));
        material.setSpecularPower(4);
        ss = new SemiSphere(2, 120, 120);
        Shape3D sh = ss.getMesh();
//        Shape3D sh = new Sphere();
        sh.setMaterial(material);
        sh.setScaleX(SCALE);
        sh.setScaleY(SCALE);
        sh.setScaleZ(SCALE);
        light1Group = new Group();
        light2Group = new Group();
        light1mv = new GroupMover(light1Group);
        light2mv = new GroupMover(light2Group);
        root = new Group(light1mv.getGroup(), light2mv.getGroup(), ss.getGroup());
        rootmv = new GroupMover(root);
        return rootmv.getGroup();
    }

    private static Group buildLightGroup(VisibleLight vl) {
        vl.setTranslateZ(-140);
        Sphere balance = new Sphere();
//        balance.setScaleX(SCALE);
//        balance.setScaleY(SCALE);
//        balance.setScaleZ(SCALE);
        balance.setTranslateZ(140);
        return new Group(vl, balance);
    }

    private Camera addCamera(Scene scene) {
        ParallelCamera pc = new ParallelCamera();
        scene.setCamera(pc);
        return pc;
    }

    public static void main(String[] args) {
        Utils.launch(MultipleLightingTestApp.class, args);
    }
}
