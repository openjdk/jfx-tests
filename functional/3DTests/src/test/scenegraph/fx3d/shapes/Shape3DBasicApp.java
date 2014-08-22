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
package test.scenegraph.fx3d.shapes;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import test.javaclient.shared.DragSupport;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class Shape3DBasicApp extends FX3DAbstractApp implements ShapesTestingFace {

    private int width;
    private int height;
    private String title;
    private DragSupport dsx;
    private DragSupport dsy;
    protected Scene scene;

    public Shape3DBasicApp(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;

    }

    public abstract ShapesTestCase getTestCase();

    @Override
    public void setDiffuseMap(boolean bln) {
        getTestCase().setDiffuseMap(bln);
    }

    @Override
    public void setBumpMap(boolean bln) {
        getTestCase().setBumpMap(bln);
    }

    @Override
    public void setSpecularMap(boolean bln) {
        getTestCase().setSpecularMap(bln);
    }

    @Override
    public void setAndUpdateDiffuseMap(boolean bln) {
        getTestCase().setAndUpdateDiffuseMap(bln);
    }

    @Override
    public void setAndUpdateBumpMap(boolean bln) {
        getTestCase().setAndUpdateBumpMap(bln);
    }

    @Override
    public void setAndUpdateSelfIlluminationMap(boolean bln) {
        getTestCase().setAndUpdateSelfIlluminationMap(bln);
    }

    @Override
    public void setVisible(boolean bln) {
        getTestCase().setVisible(bln);
    }

    @Override
    public void setAndUpdateSpecularMap(boolean bln) {
        getTestCase().setAndUpdateSpecularMap(bln);
    }

    @Override
    public void setNullMaterial(boolean bln) {
        getTestCase().setNullMaterial(bln);
    }

    @Override
    public String materialToString() {
        return getTestCase().materialToString();
    }

    @Override
    public void materialConstruct1() {
        getTestCase().materialConstruct1();
    }

    @Override
    public void materialConstruct2() {
        getTestCase().materialConstruct2();
    }

    @Override
    public void setSpecularPower(double d) {
        getTestCase().setSpecularPower(d);
    }

    @Override
    public void setSpecularColor(Color color) {
        getTestCase().setSpecularColor(color);
    }

    @Override
    public void setDiffuseColor(Color color) {
        getTestCase().setDiffuseColor(color);
    }

    @Override
    public void setSelfIllumination(boolean bln) {
        getTestCase().setSelfIllumination(bln);
    }

    private void initRotation() {
        getTestCase().initRotation(scene);
    }

    @Override
    public void setDrawMode(DrawMode mode) {
        getTestCase().setDrawMode(mode);
    }

    @Override
    public void setCullFace(CullFace face) {
        getTestCase().setCullFace(face);
    }

    @Override
    public void initScene() {
        stage.setTitle(title);
        buildScene();
    }

    public Scene buildScene() {
        Group group = getTestCase().buildAll();

        scene = createScene(group, width, height);
        scene.setFill(Color.GREEN);
        if (!isTest()) {
            initRotation();
            VBox panesBox = new VBox();
            panesBox.setSpacing(ShapesTestCase.spacing);
            HBox cp = getTestCase().getControlPane();
            cp.setSpacing(ShapesTestCase.spacing);
            HBox sp = getTestCase().getShapePane();
            sp.setSpacing(ShapesTestCase.spacing);
            panesBox.getChildren().addAll(sp, cp);
            group.getChildren().add(panesBox);
            Utils.addBrowser(scene);
        }
        getTestCase().addCamera(scene);

        return scene;
    }

    @Override
    public Scene getScene() {
        return scene;

    }

//    public void reset() {
//        this.stage.setScene(buildScene());
//    }
    @Override
    public void setRotateX(double d) {
        getTestCase().setRotateX(d);
    }

    @Override
    public void setRotateY(double d) {
        getTestCase().setRotateY(d);
    }

    @Override
    public void setRotateZ(double d) {
        getTestCase().setRotateZ(d);
    }
}
