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

import javafx.scene.paint.Color;
import junit.framework.Assert;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.FX3DTestBase;
import test.scenegraph.fx3d.utils.VisibleLight;

/**
 *
 * @author Andrew Glushchenko
 */
public class SingleLightingTest extends FX3DTestBase {

    private static SingleLightingTestApp application;

    @BeforeClass
    public static void setUp() {
        SingleLightingTestApp.setTest(true);
        SingleLightingTestApp.main(null);
        application = (SingleLightingTestApp) SingleLightingTestApp.getInstance();
    }

    /**
     * Test for reinitialization of light(RT-28064).
     */
    @Test(timeout = 10000)
    public void reinitLightTest() {
        setLightType(VisibleLight.LightType.Point);
        setLightPosition(45, 45);
        setLightPosition(-45, -45);
        checkScreenshot("reinitLightTest");
    }

    /**
     * Test for ambient light.
     */
    @Test(timeout = 10000)
    public void ambientLightTest() {
        setLightType(VisibleLight.LightType.Ambient);
        checkScreenshot("ambientLightTest");
    }

    /**
     * Test for lightOnProperty.
     */
    @Test(timeout = 10000)
    public void lightOnPointTest() {
        setLightType(VisibleLight.LightType.Point);
        Assert.assertTrue(checkLightOnProperty());
        setLightOn(true);
        Assert.assertTrue(checkLightOnProperty());
        checkScreenshot("lightOnPointTest");
    }

    /**
     * Test for lightOnProperty.
     */
    @Test(timeout = 10000)
    public void lightOffPointTest() {
        setLightType(VisibleLight.LightType.Point);
        Assert.assertTrue(checkLightOnProperty());
        setLightOn(false);
        Assert.assertTrue(checkLightOnProperty());
        checkScreenshot("lightOffPointTest");
    }

    /**
     * Test for lightOnProperty.
     */
    @Test(timeout = 10000)
    public void lightOnAmbientTest() {
        setLightType(VisibleLight.LightType.Ambient);
        Assert.assertTrue(checkLightOnProperty());
        setLightOn(true);
        Assert.assertTrue(checkLightOnProperty());
        checkScreenshot("lightOnAmbientTest");
    }

    /**
     * Test for lightOnProperty.
     */
    @Test(timeout = 10000)
    public void lightOffAmbientTest() {
        setLightType(VisibleLight.LightType.Ambient);
        Assert.assertTrue(checkLightOnProperty());
        setLightOn(false);
        Assert.assertTrue(checkLightOnProperty());
        checkScreenshot("lightOffAmbientTest");
    }

    /**
     * Test for changing color of PointLight.
     */
    @Test(timeout = 10000)
    public void changeColorPointTest() {
        setLightType(VisibleLight.LightType.Point);
        setColor(Color.GREEN);
        checkScreenshot("changeColorPointTest");
    }

    /**
     * Test for changing color of AmbientLight.
     */
    @Test(timeout = 10000)
    public void changeColorAmbientTest() {
        setLightType(VisibleLight.LightType.Ambient);
        setColor(Color.GREEN);
        checkScreenshot("changeColorAmbientTest");
    }

    /**
     * Test for visual representation of light with simple shape.
     */
    @Test(timeout = 10000)
    public void centerTest() {
        standartTestFunction(0, 0, VisibleLight.LightType.Point, "centerTest");
    }

    /**
     * Test for visual representation of light with simple shape.
     */
    @Test(timeout = 10000)
    public void topLeftSideTest() {
        standartTestFunction(-9, 9, VisibleLight.LightType.Point, "topLeftSideTest");
    }

    /**
     * Test for visual representation of light with simple shape.
     */
    @Test(timeout = 10000)
    public void topRightSideTest() {
        standartTestFunction(-9, -9, VisibleLight.LightType.Point, "topRightSideTest");
    }

    /**
     * Test for visual representation of light with simple shape.
     */
    @Test(timeout = 10000)
    public void complanarTest() {
        standartTestFunction(-45, 90, VisibleLight.LightType.Point, "complanarTest");
    }

    private void standartTestFunction(double angleX, double angleY, VisibleLight.LightType lt, String name) {
        setLightType(lt);
        setLightPosition(angleX, angleY);
        checkScreenshot(name);
    }

    private void setColor(final Color clr) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setColor(clr);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLightType(final VisibleLight.LightType lt) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLightType(lt);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLightPosition(final double angleX, final double angleY) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLightPosition(angleX, angleY);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private int getLightScopeSize() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.getScope().size());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean checkLightOnProperty() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkLightOnProperty());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLightOn(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLightOn(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }
}
