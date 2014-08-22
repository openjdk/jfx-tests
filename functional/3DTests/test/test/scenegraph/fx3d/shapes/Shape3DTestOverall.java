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

import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;
import test.scenegraph.fx3d.utils.FX3DTestBase;

/**
 *
 * @author Andrey Glushchenko
 */
public abstract class Shape3DTestOverall extends FX3DTestBase implements ShapesTestingFace {

    @Before
    @Override
    public void prepare() {
        super.prepare();
        shapePrepare();
    }

    protected abstract Pair<Integer, Integer>[] getPositions();

    public abstract void shapePrepare();

    /**
     * Test check default configuration and Constructor (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void defaultTest() {
        lookTestFunc("defaultTest");
    }

    /**
     * Test for selfIlluminationMapProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void selfIlluminationTest() {
        setSelfIllumination(true);
        lookTestFunc("selfIlluminationTest");
    }

    /**
     * Test for default material (Shape).
     */
    @Test(timeout = 10000)
    public void defaultMaterialTest() {
        setNullMaterial(true);
        lookTestFunc("defaultMaterialTest");
    }

    /**
     * Test for bumpMapProperty and diffuseMapProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void bumpAndDiffuseTest() {
        setBumpMap(true);
        setDiffuseMap(true);
        lookTestFunc("bumpAndDiffuseTest");
    }

    /**
     * Test for bumpMapProperty(PhongMaterial).
     */
    @Test(timeout = 10000)
    public void bumpTest() {
        setBumpMap(true);
        lookTestFunc("bumpTest");
    }

    /**
     * Test for setVisible function
     */
    @Test(timeout = 10000)
    public void setVisibleTest() {
        setVisible(false);
        checkScreenshot("SetVisible[FALSE]Test");
        setVisible(true);
        checkScreenshot("SetVisible[TRUE]Test");
    }

    /**
     * Test for diffuseMapProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void diffuseTest() {
        setDiffuseMap(true);
        lookTestFunc("diffuseTest");
    }

    /**
     * Test for specularMapProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void specularMapTest() {
        setSpecularMap(true);
        lookTestFunc("specularMapTest");
    }

    /**
     * Test for specularColorProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void specularColorAfterMapTest() {
        setSpecularColor(Color.RED);
        setSpecularPower(16); //half of the default value for the Phong material
        setSpecularMap(true);
        setSpecularMap(false);
        lookTestFunc("specularColorAfterMapTest");
    }

    /**
     * Test for diffuseColorProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void diffuseColorAfterMapTest() {
        setDiffuseColor(Color.RED);
        setDiffuseMap(true);
        setDiffuseMap(false);
        lookTestFunc("diffuseColorAfterMapTest");
    }

    /**
     * Test for diffuseColorProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void diffuseColorTest() {
        setDiffuseColor(Color.BLUE);
        lookTestFunc("diffuseColorTest");
    }

    /**
     * Test for specularColorProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void specularColorMinimumTest() {
        setSpecularColor(Color.RED);
        setSpecularPower(0);
        lookTestFunc("specularColorMinimumTest");
    }

    /**
     * Test for specularColorProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void specularColorMaximumTest() {
        setSpecularColor(Color.RED);
        setSpecularPower(1);
        lookTestFunc("specularColorMaximumTest");
    }

    /**
     * Test for drawModeProperty (PhongMaterial).
     */
    @Test(timeout = 10000)
    public void drawModeLineTest() {
        setDrawMode(DrawMode.LINE);
        lookTestFunc("drawModeLineTest");
    }

    /**
     * Test for cullFaceProperty (Shape3D).
     */
    @Test(timeout = 10000)
    public void cullFaceBackTest() {
        setCullFace(CullFace.BACK);
        lookTestFunc("cullFaceBackTest");
    }

    /**
     * Test for cullFaceProperty (Shape3D).
     */
    @Test(timeout = 10000)
    public void cullFaceFrontTest() {
        setCullFace(CullFace.FRONT);
        lookTestFunc("cullFaceFrontTest");
    }

    /**
     * Test for PhongMaterial.toString() method.
     */
    @Test(timeout = 10000)
    public void materialToStringTest() {
        String expected = "PhongMaterial[diffuseColor=0xd3d3d3ff, "
                + "specularColor=0x1e1e1eff, "
                + "specularPower=32.0, "
                + "diffuseMap=null, "
                + "specularMap=null, "
                + "bumpMap=null, "
                + "selfIlluminationMap=null]";
        Assert.assertEquals(expected, materialToString());
    }

    /**
     * Test for PhongMaterial Constructor.
     */
    @Test(timeout = 10000)
    public void materialConstructor1Test() {
        materialConstruct1();
        lookTestFunc("materialConstructor1Test");
    }

    /**
     * Test for PhongMaterial Constructor.
     */
    @Test(timeout = 10000)
    public void materialConstructor2Test() {
        materialConstruct2();
        lookTestFunc("materialConstructor2Test");
    }

    /**
     * Test for bumpMapProperty(PhongMaterial) with WritableImage.
     */
    @Test(timeout = 10000)
    public void bumpUpdateTest() {
        setAndUpdateBumpMap(true);
        lookTestFunc("bumpUpdateTest");
    }

    /**
     * Test for diffuseMapProperty (PhongMaterial) with WritableImage.
     */
    @Test(timeout = 10000)
    public void diffuseUpdateTest() {
        setAndUpdateDiffuseMap(true);
        lookTestFunc("diffuseUpdateTest");
    }

    /**
     * Test for selfIlluminationMapProperty (PhongMaterial) with WritableImage.
     */
    @Test(timeout = 10000)
    public void selfIlluminationUpdateTest() {
        setAndUpdateSelfIlluminationMap(true);
        lookTestFunc("selfIlluminationUpdateTest");
    }

    /**
     * Test for specularMapProperty (PhongMaterial) with WritableImage.
     */
    @Test(timeout = 10000)
    public void specularUpdateMapTest() {
        setAndUpdateSpecularMap(true);
        lookTestFunc("specularUpdateMapTest");
    }

    protected void lookTestFunc(String testName) {
        for (Pair<Integer, Integer> pair : getPositions()) {
            setRotateX(pair.getKey());
            setRotateY(pair.getValue());
            checkScreenshot(testName + "[X=" + pair.getKey() + ";Y=" + pair.getValue() + "]");
        }
    }
}
