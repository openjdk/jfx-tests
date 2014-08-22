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

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class SubSceneAbstractApp extends FX3DAbstractApp {

    protected static int WIDTH = 600;
    protected static int HEIGHT = 600;
    public final static int SS_WIDTH = 300;
    public final static int SS_HEIGHT = 300;
    protected static int SCALE = 100;
    protected static int scenesCount = 3;
    private Scene scene;
    private GridPane gridRoot;
    private SubScene[] scenes = new SubScene[scenesCount];
    private AnchorPane controlsBox;

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    protected void initScene() {
        impl_initScene();
    }

    private void impl_initScene() {
        gridRoot = new GridPane();
        scenes[0] = getTopLeftScene();
        scenes[1] = getTopRightScene();
        scenes[2] = getDownLeftScene();


        gridRoot.add(scenes[0], 0, 0);
        gridRoot.add(scenes[1], 1, 0);
        gridRoot.add(scenes[2], 0, 1);

        if (!isTest()) {
            gridRoot.add(buildControlsBox(), 1, 1);
        }
        scene = createScene(gridRoot, WIDTH, HEIGHT);
        Utils.addBrowser(scene);
        addGlobalListeners(scene);
    }

    protected void repaint() {
        impl_initScene();
        stage.setScene(scene);
    }

    protected abstract SubScene getTopLeftScene();

    protected abstract SubScene getTopRightScene();

    protected abstract SubScene getDownLeftScene();

    protected void addGlobalListeners(Scene scene) {
    }

    protected VBox getControlsForSubScene(SubScene ss) {
        return new VBox();
    }

    private Node buildControlsBox() {
        controlsBox = new AnchorPane();
        TabPane tp = new TabPane();
        AnchorPane.setTopAnchor(tp, 0D);
        AnchorPane.setRightAnchor(tp, 0D);
        AnchorPane.setLeftAnchor(tp, 0D);
        AnchorPane.setBottomAnchor(tp, 0D);
        controlsBox.getChildren().add(tp);
        for (int i = 0; i < 3; i++) {
            tp.getTabs().add(buildTab(i));
        }
        return controlsBox;
    }

    private Tab buildTab(final int i) {
        Tab tab = new Tab("SubScene" + i);

        VBox content = getControlsForSubScene(scenes[i]);

        tab.setContent(content);
        return tab;
    }

    public void setRotate(double d) {
        for (SubScene ss : scenes) {
            ss.setRotate(d);
        }
    }

    public void setRotationAxis(Point3D p3d) {
        for (SubScene ss : scenes) {
            ss.setRotationAxis(p3d);
        }
    }
}
