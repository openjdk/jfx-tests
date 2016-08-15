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
package test.scenegraph.fx3d.subscene;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.GroupMover;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneDepthTestApp extends SubSceneAbstractApp {


    private GroupMover rootTLMV;
    private GroupMover rootTRMV;
    private GroupMover rootDLMV;

    @Override
    protected SubScene getTopLeftScene() {
        rootTLMV = buildGroupMover();
        SubScene ss = new SubScene(rootTLMV.getGroup(), SS_WIDTH, SS_HEIGHT, true, SceneAntialiasing.DISABLED);
        ss.setCamera(new PerspectiveCamera());
        return ss;
    }

    @Override
    protected SubScene getTopRightScene() {
        rootTRMV = buildGroupMover();
        rootTRMV.setRotateY(60);

        SubScene ss = new SubScene(rootTRMV.getGroup(), SS_WIDTH, SS_HEIGHT, true, SceneAntialiasing.DISABLED);
        ss.setCamera(new PerspectiveCamera());
        return ss;
    }

    @Override
    protected SubScene getDownLeftScene() {
        rootDLMV = buildGroupMover();
        rootDLMV.setRotateY(-60);
        SubScene ss = new SubScene(rootDLMV.getGroup(), SS_WIDTH, SS_HEIGHT, true, SceneAntialiasing.DISABLED);
        ss.setCamera(new PerspectiveCamera());
        return ss;
    }

    @Override
    protected VBox getControlsForSubScene(SubScene ss) {
        GroupMover targetMover;
        if(ss.getRoot() == rootDLMV.getGroup()){
            targetMover = rootDLMV;
        }else if(ss.getRoot() == rootTLMV.getGroup()){
            targetMover = rootTLMV;
        }else if(ss.getRoot() == rootTRMV.getGroup()){
            targetMover = rootTRMV;
        }else{
            throw new IllegalArgumentException("SubScene does not applicable to this application");
        }
        ScrollBar rotYBar = new ScrollBar();
        VBox controls = new VBox(new HBox(new Label("Rotate"),rotYBar));

        rotYBar.setMin(-360);
        rotYBar.setMax(360);
        rotYBar.setValue(targetMover.rotateYProperty().get());
        rotYBar.valueProperty().bindBidirectional(targetMover.rotateYProperty());

        return controls;
    }


    private static GroupMover buildGroupMover(){
        GroupMover gm =  new GroupMover(buildGroup());
        gm.translateXProperty().set(SS_WIDTH/2);
        gm.translateYProperty().set(SS_HEIGHT/2);
        gm.translateZProperty().set(1000);
        return gm;
    }

    private static Group buildGroup() {
        Group grp = new Group();
        Sphere s = new Sphere();
        Box b = new Box();
        s.setScaleX(SCALE);
        s.setScaleY(SCALE);
        s.setScaleZ(SCALE);
        s.setTranslateX(-130);
        b.setScaleX(SCALE);
        b.setScaleY(SCALE);
        b.setScaleZ(SCALE);
        b.setTranslateX(130);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTGRAY);
        material.setSpecularColor(Color.rgb(30, 30, 30));
        s.setMaterial(material);
        b.setMaterial(material);
        PointLight pl = new PointLight(Color.AQUA);
        pl.setTranslateZ(-1000);
        Sphere lightBalance = new Sphere();
//        lightBalance.setScaleX(0.1);
//        lightBalance.setScaleX(0.1);
//        lightBalance.setScaleX(0.1);
        lightBalance.setTranslateZ(1000);
        grp.getChildren().addAll(s, b,pl,lightBalance);
        return grp;
    }

    public static void main(String[] args) {
        Utils.launch(SubSceneDepthTestApp.class, args);
    }


}
