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
public class MultipleLightingTest extends FX3DTestBase {

    private static MultipleLightingTestApp application;

    @BeforeClass
    public static void setUp() {
        MultipleLightingTestApp.setTest(true);
        MultipleLightingTestApp.main(null);
        application = (MultipleLightingTestApp) MultipleLightingTestApp.getInstance();
    }
    /**
     * Test for multiple AmbientLight objects on Scene.
     */
    @Test(timeout = 10000)
    public void ambientChangeTest() {
        setLight1Type(VisibleLight.LightType.Ambient);
        setLight2Type(VisibleLight.LightType.Ambient);
        checkScreenshot("ambeintChangeTest");
    }
    /**
     * Test for visual representation of multiple PointLight's objects.
     */
    @Test(timeout = 10000)
    public void lightBackfaceTest() {
        setLight1Type(VisibleLight.LightType.Point);
        setLight2Type(VisibleLight.LightType.Point);
        setLight1Position(90, 45);
        setLight1Position(-90, -45);
        checkScreenshot("lightBackfaceTest");
    }
    /**
     * Test for mixed lighting: ambient and point.
     */
    @Test(timeout = 10000)
    public void mixedLightTest() {
        setLight1Type(VisibleLight.LightType.Ambient);
        setLight2Type(VisibleLight.LightType.Point);
        setLight2Position(-90, -30);
        checkScreenshot("mixedLightTest");
    }
    /**
     * Test for changing color of AmbientLight.
     */
    @Test(timeout = 10000)
    public void ambientChangeColor1Test() {
        setLight1Type(VisibleLight.LightType.Ambient);
        setLight2Type(VisibleLight.LightType.Ambient);
        setLight1Color(Color.YELLOW);
        checkScreenshot("ambientChangeColor1Test");
    }
/**
     * Test for changing color of AmbientLight.
     */
    @Test(timeout = 10000)
    public void ambientChangeColor2Test() {
        setLight1Type(VisibleLight.LightType.Ambient);
        setLight2Type(VisibleLight.LightType.Ambient);
        setLight2Color(Color.YELLOW);
        checkScreenshot("ambientChangeColor2Test");
    }
    /**
     * Test for changing color of PointLight.
     */
    @Test(timeout=10000)
    public void pointChangeColorTest(){
        setLight1Type(VisibleLight.LightType.Point);
        setLight2Type(VisibleLight.LightType.Point);
        setLight1Color(Color.GREEN);
        checkScreenshot("pointChangeColorTest");
    }
    /**
     * Test for changing color of AmbientLight with mixed lighting.
     */
    @Test(timeout=10000)
    public void mixedChangeAmbientColorTest(){
        setLight1Type(VisibleLight.LightType.Ambient);
        setLight2Type(VisibleLight.LightType.Point);
        setLight1Color(Color.GREEN);
        checkScreenshot("mixedChangeAmbientColorTest");
    }
    /**
     * Test for changing color of PointLight with mixed lighting.
     */
    @Test(timeout=10000)
    public void mixedChangePointColorTest(){
        setLight1Type(VisibleLight.LightType.Ambient);
        setLight2Type(VisibleLight.LightType.Point);
        setLight2Color(Color.GREEN);
        checkScreenshot("mixedChangePointColorTest");
    }


    private void setLight1Position(final double angleX, final double angleY) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight1Position(angleX, angleY);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLight2Position(final double angleX, final double angleY) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight2Position(angleX, angleY);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLight1Type(final VisibleLight.LightType lt) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight1Type(lt);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLight2Type(final VisibleLight.LightType lt) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight2Type(lt);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLight1Color(final Color clr) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight1Color(clr);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLight2Color(final Color clr) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight2Color(clr);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }
}
