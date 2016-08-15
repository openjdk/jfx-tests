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
package test.scenegraph.fx3d.subscene.lighting.scoping;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.subscene.SubSceneAbstractApp;
import test.scenegraph.fx3d.utils.VisibleLight;
import test.scenegraph.fx3d.utils.VisibleLight.LightType;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneLightScopingTestApp extends SubSceneAbstractApp {

    private Node nodes[];
    private VisibleLight lights[];
    private Group roots[];

    @Override
    protected void initScene() {
        nodes = new Node[3];
        lights = new VisibleLight[3];
        roots = new Group[3];
        super.initScene();
    }

    @Override
    protected SubScene getTopLeftScene() {
        return buildGroup(0);
    }

    @Override
    protected SubScene getTopRightScene() {
        return buildGroup(1);
    }

    @Override
    protected SubScene getDownLeftScene() {
        return buildGroup(2);
    }

    private SubScene buildGroup(int num) {
        Sphere s = new Sphere(50);
        nodes[num] = s;
        Group grp = new Group(s);
        grp.setTranslateX(SS_WIDTH / 2);
        grp.setTranslateY(SS_HEIGHT / 2);
        roots[num] = grp;
        SubScene ss = createSubScene(grp, SS_WIDTH, SS_HEIGHT);
        ss.setCamera(new PerspectiveCamera());
        return ss;
    }

    public VisibleLight getLight(int num) {
        return lights[num];
    }

    public Node getNode(int num) {
        return nodes[num];
    }

    public void setLight(int num, Color color, LightType lt) {
        if (lights[num] != null) {
            roots[num].getChildren().remove(lights[num]);
        }
        lights[num] = new VisibleLight(color, lt);
        lights[num].setTranslateX(SS_WIDTH/4);
        lights[num].setTranslateY(SS_HEIGHT/4);
        roots[num].getChildren().add(lights[num]);
    }

    public static void main(String[] args) {
        Utils.launch(SubSceneLightScopingTestApp.class, args);
    }
}
