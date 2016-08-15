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
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.FX3DTestBase;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneBasicPropsTest extends FX3DTestBase {

    private static SubSceneBasicPropsTestApp application;

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }

    @BeforeClass
    public static void setUp() {
        SubSceneBasicPropsTestApp.setTest(true);
        SubSceneBasicPropsTestApp.main(null);
        application = (SubSceneBasicPropsTestApp) SubSceneBasicPropsTestApp.getInstance();
    }

    /**
     * Tests for SubScene.getCamera() method.
     */
    @Test(timeout = 10000)
    public void cameraGetterTest() {
        Assert.assertTrue("", checkCameras());
    }

    /**
     * Tests for SubScene.getWidth() method.
     */
    @Test(timeout = 10000)
    public void widthGetterTest() {
        Assert.assertTrue("", checkWidth());
    }

    /**
     * Tests for SubScene.getHeight() method.
     */
    @Test(timeout = 10000)
    public void heightGetterTest() {
        Assert.assertTrue("", checkHeight());
    }

    /**
     * Tests for SubScene.getFill() method.
     */
    @Test(timeout = 10000)
    public void fillGetterTest() {
        Assert.assertTrue("", checkFill());
    }

    /**
     * Tests for SubScene.getRoot() method.
     */
    @Test(timeout = 10000)
    public void rootGetterTest() {
        Assert.assertTrue("", checkRoots());
    }

    /**
     * Tests for default SubScene fill.
     */
    @Test(timeout = 10000)
    public void checkDefaultFill() {
        Assert.assertTrue("", isFillNull());
    }

    /**
     * Tests for SubScene.setCamera() method.
     */
    @Test(timeout = 10000)
    public void cameraSetterTest() {//RT-29347
        Assert.assertTrue("", reinitCameras());
        checkScreenshot("cameraSetterTest");
    }

    /**
     * Tests for SubScene.setRoot() method.
     */
    @Test(timeout = 10000)
    public void rootSetterTest() {//RT-29347
        Assert.assertTrue("", reinitRoots());
        checkScreenshot("rootSetterTest");
    }

    /**
     * Tests for SubScene.setFill() method.
     */
    @Test(timeout = 10000)
    public void fillSetterTest() {//RT-29341,RT-29347
        Assert.assertTrue("", setFill(0, Color.RED));
        Assert.assertTrue("", setFill(1, Color.GREEN));
        Assert.assertTrue("", setFill(2, Color.BLUE));
        checkScreenshot("fillSetterTest");
    }
    /**
     * Tests for SubScene.setRotate() method.
     */
    @Test(timeout = 10000)
    public void rotateSetterXTest() {
        setFill(0, Color.RED);
        setFill(1, Color.GREEN);
        setFill(2, Color.BLUE);
        setRotationAxis(Rotate.X_AXIS);
        setRotate(45);
        checkScreenshot("rotateSetterXTest");
    }
    /**
     * Tests for SubScene.setRotate() method.
     */
    @Test(timeout = 10000)
    public void rotateSetterYTest() {
        setFill(0, Color.RED);
        setFill(1, Color.GREEN);
        setFill(2, Color.BLUE);
        setRotationAxis(Rotate.Y_AXIS);
        setRotate(45);
        checkScreenshot("rotateSetterYTest");
    }
    /**
     * Tests for SubScene.setRotate() method.
     */
    @Test(timeout = 10000)
    public void rotateSetterZTest() {
        setFill(0, Color.RED);
        setFill(1, Color.GREEN);
        setFill(2, Color.BLUE);
        setRotationAxis(Rotate.Z_AXIS);
        setRotate(45);
        checkScreenshot("rotateSetterZTest");
    }


    /**
     * Test for SubScene.setHeight() and SubScene.setWidth() methods.
     */
    @Test(timeout = 10000)
    public void dimentionsTest() {//RT-29347
        Assert.assertTrue("", setWidth(0, 100));
        Assert.assertTrue("", setHeight(1, 100));
        checkScreenshot("dimentionsTest");
    }

    /**
     * Test for fillProperty and dimentions.
     */
    @Test(timeout = 10000)
    public void fillAndDimentionsTest() {//RT-29341,RT-29347,RT-29350
        Assert.assertTrue("", setWidth(0, 100));
        Assert.assertTrue("", setHeight(0, 200));
        Assert.assertTrue("", setFill(0, Color.RED));
        Assert.assertTrue("", setFill(1, Color.GREEN));
        Assert.assertTrue("", setFill(2, Color.BLUE));
        checkScreenshot("fillAndDimentionsTest");
    }

    private boolean reinitCameras() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.reinitCameras());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean reinitRoots() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.reinitRoots());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean setFill(final int i, final Color color) {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.setFill(i, color));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean isFillNull() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.isFillNull());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean setHeight(final int i, final double d) {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.setHeight(i, d));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean setWidth(final int i, final double d) {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.setWidth(i, d));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean checkCameras() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkCameras());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean checkFill() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkFill());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean checkHeight() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkHeight());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean checkWidth() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkWidth());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean checkRoots() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkRoots());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
    private void setRotate(final double d){
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setRotate(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
    private void setRotationAxis(final Point3D p3d){
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setRotationAxis(p3d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
