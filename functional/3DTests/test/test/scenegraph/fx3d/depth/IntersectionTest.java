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
import test.scenegraph.fx3d.depth.IntersectionTestApp.Pages;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrey Glushchenko
 */
public class IntersectionTest extends DepthTestBase {

    private static IntersectionTestApp application;

    @BeforeClass
    public static void setUp() {
        Root.ROOT.getEnvironment().setProperty(ImageComparator.class,
            new GlassPixelImageComparator(new PixelEqualityRasterComparator(.05)));
        IntersectionTestApp.setTest(true);
        IntersectionTestApp.main(null);
        application = (IntersectionTestApp) IntersectionTestApp.getInstance();
    }

    /**
     * Test for intersection of Spheres.
     */
    @Test(timeout = 10000)
    public void spheresTest() {
        openPage(application, Pages.IntersectionSpheresCase.name());
        checkScreenshot("spheresTest");
    }

    /**
     * Test for intersection of Boxes.
     */
    @Test(timeout = 10000)
    public void boxesTest() {
        openPage(application, Pages.IntersectionBoxesCase.name());
        checkScreenshot("boxesTest");
    }

    /**
     * Test for intersection of Cylinders.
     */
    @Test(timeout = 10000)
    public void cylindersTest() {
        openPage(application, Pages.IntersectionCylindersCase.name());
        checkScreenshot("cylindersTest");
    }

    /**
     * Test for intersection of Sphere and Box.
     */
    @Test(timeout = 10000)
    public void sphereAndBoxTest() {
        openPage(application, Pages.IntersectionSphereAndBoxCase.name());
        checkScreenshot("sphereAndBoxTest");
    }

    /**
     * Test for intersection of Sphere and Cylinder.
     */
    @Test(timeout = 10000)
    public void sphereAndCylinderTest() {
        openPage(application, Pages.IntersectionSphereAndCylinderCase.name());
        checkScreenshot("sphereAndCylinderTest");
    }

    /**
     * Test for intersection of Box and Cylinder.
     */
    @Test(timeout = 10000)
    public void boxAndCylinderTest() {
        openPage(application, Pages.IntersectionBoxAndCylinderCase.name());
        checkScreenshot("boxAndCylinderTest");
    }

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }
}
