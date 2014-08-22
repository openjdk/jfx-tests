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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import test.javaclient.shared.Utils;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneBasicPropsTestApp extends SubSceneAbstractApp {

    private SubScene[] scenes = new SubScene[3];

    @Override
    protected SubScene getTopLeftScene() {
        SubScene ss = new SubScene(buildGroup(), SS_WIDTH, SS_HEIGHT, true, SceneAntialiasing.DISABLED);
        ss.setCamera(new PerspectiveCamera());
        scenes[0] = ss;
        return ss;
    }

    @Override
    protected SubScene getTopRightScene() {
        SubScene ss = new SubScene(buildGroup(), SS_WIDTH, SS_HEIGHT, true, SceneAntialiasing.DISABLED);
        ss.setCamera(new PerspectiveCamera());
        scenes[1] = ss;
        return ss;
    }

    @Override
    protected SubScene getDownLeftScene() {
        SubScene ss = new SubScene(buildGroup(), SS_WIDTH, SS_HEIGHT, true, SceneAntialiasing.DISABLED);
        ss.setCamera(new PerspectiveCamera());
        scenes[2] = ss;
        return ss;
    }

    private static Group buildGroup() {
        Sphere s = new Sphere();
        s.setScaleX(SCALE);
        s.setScaleY(SCALE);
        s.setScaleZ(SCALE);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTGRAY);
        material.setSpecularColor(Color.rgb(30, 30, 30));
        s.setMaterial(material);
//        PointLight pl = new PointLight(Color.AQUA);
//        pl.setTranslateZ(-1000);
        Group group = new Group(/*pl,*/ s);
        group.setTranslateX(SS_WIDTH / 2);
        group.setTranslateY(SS_HEIGHT / 2);
        return group;
    }

    @Override
    protected VBox getControlsForSubScene(final SubScene ss) {
        Button checkRoot = new Button("Check Root");
        Button reinitRoot = new Button("Reinit Root");
        Button checkCamera = new Button("Check Camera");
        Button reinitCamera = new Button("Reinit Camera");
        Button checkFill = new Button("Check Fill");
        Button isFillNull = new Button("is Fill == null");
        ColorPicker fillPicker = new ColorPicker((Color) ss.getFill());
        Button checkWidth = new Button("Check Width");
        Button checkHeight = new Button("Check Height");
        ScrollBar sbWidth = new ScrollBar();
        ScrollBar sbHeight = new ScrollBar();
        VBox controls = new VBox();
        controls.setSpacing(5);
        controls.getChildren().addAll(new HBox(checkRoot, reinitRoot),
                new HBox(checkCamera, reinitCamera),
                new HBox(checkFill,isFillNull, fillPicker),
                new HBox(checkWidth, sbWidth),
                new HBox(checkHeight, sbHeight));

        checkRoot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (checkRoot(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        reinitRoot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (reinitRoot(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        checkCamera.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (checkCamera(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        reinitCamera.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (reinitCamera(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        checkFill.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (checkFill(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        isFillNull.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (isFillNull(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        fillPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> ov, Color t, Color t1) {
                if (setFill(ss, t1)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        checkWidth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (checkWidth(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        checkHeight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (checkHeight(ss)) {
                    System.out.println("Pass");
                } else {
                    System.out.println("Fail");
                }
            }
        });
        sbWidth.setMin(1);
        sbWidth.setMax(SS_WIDTH);
        sbWidth.valueProperty().set(ss.getWidth());
        sbWidth.valueProperty().bindBidirectional(ss.widthProperty());
        sbHeight.setMin(1);
        sbHeight.setMax(SS_HEIGHT);
        sbHeight.valueProperty().set(ss.getHeight());
        sbHeight.valueProperty().bindBidirectional(ss.heightProperty());

        return controls;
    }

    private boolean checkCamera(SubScene ss) {
        return ss.getCamera() == ss.cameraProperty().get();
    }

    private boolean reinitCamera(SubScene ss) {
        Camera cam = ss.getCamera();
        ss.setCamera(new PerspectiveCamera());
        return cam != ss.getCamera();
    }

    private boolean checkRoot(SubScene ss) {
        return ss.getRoot() == ss.rootProperty().get();
    }

    private boolean reinitRoot(SubScene ss) {
        Parent p = ss.getRoot();
        ss.setRoot(buildGroup());
        return p != ss.getParent();
    }

    private boolean setFill(SubScene ss, Color color) {
        Paint fill = ss.getFill();
        if (fill == color) {
            return true;
        }
        ss.setFill(color);
        return color != fill;
    }

    private boolean checkFill(SubScene ss) {
        return ss.getFill() == ss.fillProperty().get();
    }

    private boolean checkWidth(SubScene ss) {
        return ss.getWidth() == ss.widthProperty().get();
    }

    private boolean checkHeight(SubScene ss) {
        return ss.getHeight() == ss.heightProperty().get();
    }

    private boolean setWidth(SubScene ss, double d) {
        double old = ss.getWidth();
        if (old == d) {
            return true;
        }
        ss.setWidth(d);
        return old != ss.getWidth();
    }

    private boolean setHeight(SubScene ss, double d) {
        double old = ss.getHeight();
        if (old == d) {
            return true;
        }
        ss.setHeight(d);
        return old != ss.getHeight();
    }
    private boolean isFillNull(SubScene ss){
        return ss.fillProperty().get() == null;
    }

    public boolean checkHeight() {
        return checkHeight(scenes[0]) && checkHeight(scenes[1]) && checkHeight(scenes[2]);
    }

    public boolean checkWidth() {
        return checkWidth(scenes[0]) && checkWidth(scenes[1]) && checkWidth(scenes[2]);
    }

    public boolean checkFill() {
        return checkFill(scenes[0]) && checkFill(scenes[1]) && checkFill(scenes[2]);
    }

    public boolean checkRoots() {
        return checkRoot(scenes[0]) && checkRoot(scenes[1]) && checkRoot(scenes[2]);
    }

    public boolean reinitRoots() {
        return reinitRoot(scenes[0]) && reinitRoot(scenes[1]) && reinitRoot(scenes[2]);
    }

    public boolean checkCameras() {
        return checkCamera(scenes[0]) && checkCamera(scenes[1]) && checkCamera(scenes[2]);
    }

    public boolean reinitCameras() {
        return reinitCamera(scenes[0]) && reinitCamera(scenes[1]) && reinitCamera(scenes[2]);
    }
    public boolean isFillNull(){
        return isFillNull(scenes[0]) && isFillNull(scenes[1]) && isFillNull(scenes[2]);
    }

    public boolean setFill(int i, Color color) {
        return setFill(scenes[i], color);
    }

    public boolean setHeight(int i, double d) {
        return setHeight(scenes[i], d);
    }

    public boolean setWidth(int i, double d) {
        return setWidth(scenes[i], d);
    }

    public static void main(String[] args) {
        Utils.launch(SubSceneBasicPropsTestApp.class, args);
    }
}
