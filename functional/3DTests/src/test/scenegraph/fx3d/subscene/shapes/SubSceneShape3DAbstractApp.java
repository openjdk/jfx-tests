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
package test.scenegraph.fx3d.subscene.shapes;

import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import org.junit.Assert;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;
import test.scenegraph.fx3d.subscene.SubSceneAbstractApp;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class SubSceneShape3DAbstractApp extends SubSceneAbstractApp implements ShapesTestingFace {

    protected abstract ShapesTestCase[] buildTestCases();
    private SubScene[] scenes = new SubScene[3];
    protected ShapesTestCase[] cases;

    @Override
    protected void initScene() {
        cases = buildTestCases();
        super.initScene();
    }

    private SubScene getSubScene(int num) {
        Group root = cases[num].buildAll();
        cases[num].getGroupMover().translateXProperty().set(WIDTH / 4);
        cases[num].getGroupMover().translateYProperty().set(HEIGHT / 4);
        SubScene ss = new SubScene(root, HEIGHT / 2, WIDTH / 2, true, SceneAntialiasing.DISABLED);
        cases[num].addCamera(ss);
        ss.setFill(Color.GREEN);
        if (!isTest()) {
            cases[num].initRotation(ss);
        }
        return ss;
    }

    @Override
    protected SubScene getTopLeftScene() {
        scenes[0] = getSubScene(0);
        return scenes[0];
    }

    @Override
    protected SubScene getTopRightScene() {
        scenes[1] = getSubScene(1);
        return scenes[1];
    }

    @Override
    protected SubScene getDownLeftScene() {
        scenes[2] = getSubScene(2);
        return scenes[2];
    }

    @Override
    public void setNullMaterial(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setNullMaterial(bln);
        }
    }

    @Override
    protected VBox getControlsForSubScene(SubScene ss) {
        ShapesTestCase stc = null;
        if (ss.equals(scenes[0])) {
            stc = cases[0];
        } else if (ss.equals(scenes[1])) {
            stc = cases[1];
        } else {
            stc = cases[2];
        }
        return new VBox(stc.getControlPane(), stc.getShapePane());
    }

    @Override
    public void setDiffuseMap(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setDiffuseMap(bln);
        }
    }

    @Override
    public void setVisible(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setVisible(bln);
        }
    }

    @Override
    public void setBumpMap(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setBumpMap(bln);
        }
    }

    @Override
    public void setSpecularMap(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setSpecularMap(bln);
        }
    }

    @Override
    public void setAndUpdateDiffuseMap(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setAndUpdateDiffuseMap(bln);
        }
    }

    @Override
    public void setAndUpdateBumpMap(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setAndUpdateBumpMap(bln);
        }
    }

    @Override
    public void setAndUpdateSelfIlluminationMap(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setAndUpdateSelfIlluminationMap(bln);
        }
    }

    @Override
    public void setAndUpdateSpecularMap(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setAndUpdateSpecularMap(bln);
        }
    }

    @Override
    public String materialToString() {
        Assert.assertEquals(cases[0].materialToString(), cases[1].materialToString());
        Assert.assertEquals(cases[1].materialToString(), cases[2].materialToString());
        return cases[1].materialToString();
    }

    @Override
    public void materialConstruct1() {
        for (ShapesTestCase c : cases) {
            c.materialConstruct1();
        }
    }

    @Override
    public void materialConstruct2() {
        for (ShapesTestCase c : cases) {
            c.materialConstruct2();
        }
    }

    @Override
    public void setSpecularPower(double d) {
        for (ShapesTestCase c : cases) {
            c.setSpecularPower(d);
        }
    }

    @Override
    public void setSpecularColor(Color color) {
        for (ShapesTestCase c : cases) {
            c.setSpecularColor(color);
        }
    }

    @Override
    public void setDiffuseColor(Color color) {
        for (ShapesTestCase c : cases) {
            c.setDiffuseColor(color);
        }
    }

    @Override
    public void setSelfIllumination(boolean bln) {
        for (ShapesTestCase c : cases) {
            c.setSelfIllumination(bln);
        }
    }

    @Override
    public void setDrawMode(DrawMode mode) {
        for (ShapesTestCase c : cases) {
            c.setDrawMode(mode);
        }
    }

    @Override
    public void setCullFace(CullFace face) {
        for (ShapesTestCase c : cases) {
            c.setCullFace(face);
        }
    }

    @Override
    public void setRotateX(double d) {
        cases[0].getGroupMover().setRotateX(d);
        cases[1].getGroupMover().setRotateX(d);
        cases[2].getGroupMover().setRotateX(d);
    }

    @Override
    public void setRotateY(double d) {
        cases[0].getGroupMover().setRotateY(d);
        cases[1].getGroupMover().setRotateY(d + 90);
        cases[2].getGroupMover().setRotateY(d);
    }

    @Override
    public void setRotateZ(double d) {
        cases[0].getGroupMover().setRotateZ(d);
        cases[1].getGroupMover().setRotateZ(d);
        cases[2].getGroupMover().setRotateZ(d + 90);
    }
}
