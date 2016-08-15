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
package test.scenegraph.fx3d.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import junit.framework.Assert;
import test.javaclient.shared.DragSupport;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class ShapesTestCase implements ShapesTestingFace{

    private DragSupport dsx;
    private DragSupport dsy;
    private ExtendedDragSupport edsx;
    private ExtendedDragSupport edsy;
    private Shape3D shape;
    private PhongMaterial material;
    final Image diffuseMap = new Image("test/textures/cup_diffuseMap_1024.png");
    final Image bumpMap = new Image("test/textures/cup_normalMap_1024.png");
    final Image specularMap = new Image("test/textures/earth.png");
    final Image illuminationMap = new Image("test/textures/selfillumination.png");
    private CheckBox diffuseMapCheck;
    private CheckBox bumpMapCheck;
    private CheckBox selfIlluminationCheck;
    private CheckBox specularCheck;
    private CheckBox diffuseMapUpdateCheck;
    private CheckBox bumpMapUpdateCheck;
    private CheckBox selfIlluminationUpdateCheck;
    private CheckBox specularUpdateCheck;
    private Button drawModeLine;
    private Button drawModeFill;
    private GroupMover rb;
    private PointLight pointLight;
    private Slider specularPowerSlider;
    private ColorPicker specularColorPicker;
    private ColorPicker diffuseColorPicker;
    private Button cullFaceNone;
    private Button cullFaceBack;
    private Button cullFaceFront;
    public static final double spacing = 5;
    public static final double SCALE = 100;
    private static int len = 50;

    protected abstract Shape3D getShape();

    protected abstract Group buildGroup(Group grp);

    public abstract HBox getControlPane();

    private void initMaterial() {
        material = new PhongMaterial();
        material.setDiffuseColor(Color.LIGHTGRAY);
        material.setSpecularColor(Color.rgb(30, 30, 30));
        shape.setMaterial(material);

    }

    @Override
    public void setDiffuseMap(boolean bln) {
        if (bln) {
            material.setDiffuseMap(diffuseMap);
//            material.setDiffuseColor(Color.WHITE);

        } else {
            material.setDiffuseMap(null);
//            material.setDiffuseColor(Color.LIGHTGREY);
        }
    }
    @Override
    public void setVisible(boolean bln){
        shape.setVisible(bln);
    }

    private static void updateImage(WritableImage map) {
        PixelWriter pw = map.getPixelWriter();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                pw.setColor(i, j, Color.rgb(73, 212, 206));
                if (j > 0 && j % len == 0) {
                    j += len + 1;
                }
            }
            if (i > 0 && i % len == 0) {
                i += len + 1;
            }
        }
    }

    @Override
    public void setAndUpdateDiffuseMap(boolean bln) {
        if (bln) {
            WritableImage map = new WritableImage(diffuseMap.getPixelReader(),
                    (int) diffuseMap.getWidth(), (int) diffuseMap.getHeight());
            material.setDiffuseMap(map);
            material.setDiffuseColor(Color.WHITE);
            updateImage(map);
        } else {
            material.setDiffuseMap(null);
            material.setDiffuseColor(Color.LIGHTGREY);
        }
    }

    @Override
    public void setAndUpdateBumpMap(boolean bln) {
        if (bln) {
            WritableImage map = new WritableImage(bumpMap.getPixelReader(),
                    (int) bumpMap.getWidth(), (int) bumpMap.getHeight());
            material.setBumpMap(map);
            updateImage(map);
        } else {
            material.setBumpMap(null);
        }
    }

    @Override
    public void setAndUpdateSpecularMap(boolean bln) {
        if (bln) {
            WritableImage map = new WritableImage(specularMap.getPixelReader(),
                    (int) specularMap.getWidth(), (int) specularMap.getHeight());
            material.setSpecularMap(map);
            updateImage(map);
            Assert.assertTrue(material.specularMapProperty().get().equals(map));

        } else {
            material.setSpecularMap(null);
            Assert.assertNull(material.specularMapProperty().get());
        }
    }

    @Override
    public void setAndUpdateSelfIlluminationMap(boolean bln) {
        if (bln) {
            WritableImage map = new WritableImage(illuminationMap.getPixelReader(),
                    (int) illuminationMap.getWidth(), (int) illuminationMap.getHeight());
            material.setSelfIlluminationMap(map);
            updateImage(map);
        } else {
            material.setSelfIlluminationMap(null);
        }
    }

    @Override
    public void setBumpMap(boolean bln) {
        if (bln) {
            material.setBumpMap(bumpMap);

        } else {
            material.setBumpMap(null);
        }
    }

    @Override
    public void setSpecularMap(boolean bln) {
        if (bln) {
            material.setSpecularMap(specularMap);
            Assert.assertTrue(material.specularMapProperty().get().equals(specularMap));
        } else {
            material.setSpecularMap(null);
            Assert.assertNull(material.specularMapProperty().get());
        }
    }

    @Override
    public String materialToString() {
        return material.toString();
    }

    @Override
    public void materialConstruct1() {
        material = new PhongMaterial(Color.RED);
        material.setSpecularColor(Color.rgb(30, 30, 30));
        shape.setMaterial(material);
    }

    @Override
    public void materialConstruct2() {
        material = new PhongMaterial(Color.WHITE, diffuseMap, specularMap,
                bumpMap, illuminationMap);
        shape.setMaterial(material);
    }

    @Override
    public void setSpecularPower(double d) {
        material.setSpecularPower(d);
        Assert.assertTrue(material.specularPowerProperty().get() == d);
    }

    @Override
    public void setSpecularColor(Color color) {
        material.setSpecularColor(color);
        Assert.assertTrue(material.specularColorProperty().get().equals(color));
    }

    @Override
    public void setDiffuseColor(Color color) {
        material.setDiffuseColor(color);
        Assert.assertTrue(material.diffuseColorProperty().get().equals(color));
    }

    @Override
    public void setSelfIllumination(boolean bln) {
        if (bln) {
            material.setSelfIlluminationMap(illuminationMap);
        } else {
            material.setSelfIlluminationMap(null);
        }
    }

    private void initMaterialControls() {
        diffuseMapCheck = new CheckBox("Diffuse");
        diffuseMapCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setDiffuseMap(t1);
            }
        });
        bumpMapCheck = new CheckBox("Bump");
        bumpMapCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setBumpMap(t1);
            }
        });
        diffuseMapUpdateCheck = new CheckBox("Updated Diffuse");
        diffuseMapUpdateCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setAndUpdateDiffuseMap(t1);
            }
        });
        bumpMapUpdateCheck = new CheckBox("Updated Bump");
        bumpMapUpdateCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setAndUpdateBumpMap(t1);
            }
        });
        specularPowerSlider = new Slider(0, 100, material.getSpecularPower());
        specularPowerSlider.setShowTickLabels(true);
        specularPowerSlider.setShowTickMarks(true);
        specularPowerSlider.setMajorTickUnit(10);
        specularPowerSlider.setMinorTickCount(20);
        specularPowerSlider.valueProperty().bindBidirectional(material.specularPowerProperty());
        specularColorPicker = new ColorPicker(material.getSpecularColor());
        specularColorPicker.valueProperty().bindBidirectional(material.specularColorProperty());
        diffuseColorPicker = new ColorPicker(material.getDiffuseColor());
        diffuseColorPicker.valueProperty().bindBidirectional(material.diffuseColorProperty());
        selfIlluminationCheck = new CheckBox("Self Illumination");
        selfIlluminationCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setSelfIllumination(t1);
            }
        });
        selfIlluminationUpdateCheck = new CheckBox("Update Self Illumination");
        selfIlluminationUpdateCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setAndUpdateSelfIlluminationMap(t1);
            }
        });
        specularCheck = new CheckBox("Specular map");
        specularCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setSpecularMap(t1);
            }
        });
        specularUpdateCheck = new CheckBox("Update Specular map");
        specularUpdateCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setAndUpdateSpecularMap(t1);
            }
        });
    }
    @Override
    public void setNullMaterial(boolean bln){
        shape.setMaterial(bln?null:material);
    }

    @Override
    public void setDrawMode(DrawMode mode) {
        shape.setDrawMode(mode);
    }

    @Override
    public void setCullFace(CullFace face) {
        shape.setCullFace(face);
    }

    private void initShapeControls() {
        drawModeFill = new Button("Set Fill");
        drawModeFill.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                setDrawMode(DrawMode.FILL);
            }
        });
        drawModeLine = new Button("Set Line");
        drawModeLine.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                setDrawMode(DrawMode.LINE);
            }
        });
        cullFaceBack = new Button("Set Back");
        cullFaceBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                setCullFace(CullFace.BACK);
            }
        });
        cullFaceFront = new Button("Set Front");
        cullFaceFront.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                setCullFace(CullFace.FRONT);
            }
        });
        cullFaceNone = new Button("Set None");
        cullFaceNone.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                setCullFace(CullFace.NONE);
            }
        });
    }

    private Group getGroup() {
        shape = getShape();
        shape.setScaleX(SCALE);
        shape.setScaleY(SCALE);
        shape.setScaleZ(SCALE);
        shape.setCullFace(CullFace.NONE);
        rb = new GroupMover(shape);
        return rb.getGroup();
    }

    public HBox getShapePane() {
        initMaterialControls();
        initShapeControls();

        HBox pane = new HBox();
        VBox colorImageBox = new VBox();
        colorImageBox.setSpacing(spacing);
        VBox illuminationBox = new VBox();
        illuminationBox.setSpacing(spacing);
        VBox drawModeBox = new VBox();
        drawModeBox.setSpacing(spacing);
        VBox rotateBox = new VBox();
        rotateBox.setSpacing(spacing);
        VBox cullFaceBox = new VBox();
        cullFaceBox.setSpacing(spacing);
        VBox specularColorBox = new VBox();
        specularColorBox.setSpacing(spacing);
        colorImageBox.getChildren().addAll(new Label("diffuse map"), diffuseMapCheck, diffuseMapUpdateCheck,
                bumpMapCheck, bumpMapUpdateCheck, diffuseColorPicker);
        drawModeBox.getChildren().addAll(new Label("draw mode"), drawModeLine, drawModeFill);
        specularColorBox.getChildren().addAll(new Label("specular Color"), specularPowerSlider, specularColorPicker, specularCheck, specularUpdateCheck);
        cullFaceBox.getChildren().addAll(new Label("Cull Face"), cullFaceBack, cullFaceFront, cullFaceNone);
        illuminationBox.getChildren().addAll(new Label("Illumination"), selfIlluminationCheck, selfIlluminationUpdateCheck);
        pane.getChildren().addAll(drawModeBox, colorImageBox, rotateBox, specularColorBox, illuminationBox, cullFaceBox);
        return pane;
    }

    public Camera addCamera(Scene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(false);
        scene.setCamera(perspectiveCamera);
        return perspectiveCamera;
    }

    public Camera addCamera(SubScene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(false);
        scene.setCamera(perspectiveCamera);
        return perspectiveCamera;
    }

    private Group getLight(Group grp) {
        Group root;

        pointLight = new PointLight(Color.ANTIQUEWHITE);
        pointLight.setTranslateX(15);
        pointLight.setTranslateY(-10 * SCALE);
        pointLight.setTranslateZ(-100 * SCALE);
        root = new Group(grp, pointLight);

        root.setFocusTraversable(true);
        return root;
    }

    public Group buildAll() {
        Group group = getLight(buildGroup(getGroup()));
        initMaterial();
        return group;
    }

    public GroupMover getGroupMover() {
        return rb;
    }
    public void initRotation(Scene scene){
        dsx = new DragSupport(scene, null, Orientation.HORIZONTAL, rb.rotateYProperty(), 3);
        dsy = new DragSupport(scene, null, Orientation.VERTICAL, rb.rotateXProperty(), 3);
    }
    public void initRotation(Node node){
        edsx = new ExtendedDragSupport(node, null, Orientation.HORIZONTAL, rb.rotateYProperty(), 3);
        edsy = new ExtendedDragSupport(node, null, Orientation.VERTICAL, rb.rotateXProperty(), 3);
    }

    @Override
    public void setRotateX(double d) {
        getGroupMover().setRotateX(d);
    }

    @Override
    public void setRotateY(double d) {
        getGroupMover().setRotateY(d);
    }

    @Override
    public void setRotateZ(double d) {
        getGroupMover().setRotateZ(d);
    }

}
