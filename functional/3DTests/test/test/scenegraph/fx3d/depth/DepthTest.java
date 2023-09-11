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
package test.scenegraph.fx3d.depth;

import org.junit.BeforeClass;
import org.junit.Test;
import org.jemmy.fx.Root;
import org.jemmy.image.ImageComparator;
import org.jemmy.image.glass.GlassPixelImageComparator;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import test.scenegraph.fx3d.depth.DepthTestApp.Pages;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrey Glushchenko
 */
public class DepthTest extends DepthTestBase {

    private static DepthTestApp application;

    @BeforeClass
    public static void setUp() {
        Root.ROOT.getEnvironment().setProperty(ImageComparator.class,
            new GlassPixelImageComparator(new
                PixelEqualityRasterComparator(FX3DAbstractApp.COLOR_TOLERANCE)));
        DepthTestApp.setTest(true);
        DepthTestApp.main(null);
        application = (DepthTestApp) DepthTestApp.getInstance();

    }

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }

    /**
     * Test for representation some number of Spheres.
     */
    @Test(timeout = 10000)
    public void spheresRightTest() {
        openPage(application, Pages.DepthSpheresCase.name());
        setRotateY(application, 45);
        checkScreenshot("spheresRightTest");
    }

    /**
     * Test for representation some number of Boxes.
     */
    @Test(timeout = 10000)
    public void boxesRightTest() {
        openPage(application, Pages.DepthBoxesCase.name());
        setRotateY(application, 45);
        checkScreenshot("boxesRightTest");
    }

    /**
     * Test for representation some number of Cylinders.
     */
    @Test(timeout = 10000)
    public void cylindersRightTest() {
        openPage(application, Pages.DepthCylindersCase.name());
        setRotateY(application, 45);
        checkScreenshot("cylindersRightTest");
    }

    /**
     * Test for representation some number of Spheres.
     */
    @Test(timeout = 10000)
    public void spheresLeftTest() {
        openPage(application, Pages.DepthSpheresCase.name());
        setRotateY(application, -45);
        checkScreenshot("spheresLeftTest");
    }

    /**
     * Test for representation some number of Boxes.
     */
    @Test(timeout = 10000)
    public void boxesLeftTest() {
        openPage(application, Pages.DepthBoxesCase.name());
        setRotateY(application, -45);
        checkScreenshot("boxesLeftTest");
    }

    /**
     * Test for representation some number of Cylinders.
     */
    @Test(timeout = 10000)
    public void cylindersLeftTest() {
        openPage(application, Pages.DepthCylindersCase.name());
        setRotateY(application, -45);
        checkScreenshot("cylindersLeftTest");
    }
}
