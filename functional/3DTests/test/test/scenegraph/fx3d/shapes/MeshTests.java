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

import junit.framework.Assert;
import org.junit.Test;
import test.scenegraph.fx3d.interfaces.MeshTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class MeshTests extends Shape3DTestFunctions implements MeshTestingFace {

    /**
     * Test for Mesh Constructor.
     */
    @Test(timeout = 6000)
    public void constructorTest() {
        Assert.assertTrue(checkConstruct());
        lookTestFunc("constructorTest");
    }

    /**
     * Test for setPoints method.
     */
    @Test(timeout = 10000)
    public void setPointsTest() {
        resetPoints();
        lookTestFunc("setPointsTest");
    }

    /**
     * Test for setFaces method.
     */
    @Test(timeout = 10000)
    public void setFacesTest() {
        setSpecialFaces();
        lookTestFunc("setFacesTest");
    }

    /**
     * Test for setTexCoords method.
     */
    @Test(timeout = 10000)
    public void setTexCoordsTest() {
        setDiffuseMap(true);
        resetTexCoords();
        lookTestFunc("setTexCoordsTest");
    }

    /**
     * Test for Not Valid SmoothingGroups.
     */
    @Test(timeout = 10000)
    public void setNotValidSmoothingGroupsTest() {
        Assert.assertFalse(setNotValidSmoothingGroups());
        lookTestFunc("setNotValidSmoothingGroupsTest");
    }

    /**
     * Test for setSmoothingGroups method.
     */
    @Test(timeout = 10000)
    public void setSmoothingGroupsTest() {
        setSmoothingGroups();
        lookTestFunc("setSmoothingGroupsTest");
    }

    /**
     * Test for default Smoothing Groups in Mesh.
     */
    @Test(timeout = 10000)
    public void defaultSmoothingGroupsTest() {
        setDefaultSmoothingGroups(true);
        lookTestFunc("defaultSmoothingGroupsTest");
    }

    /**
     * Test for getPoints method.
     */
    @Test(timeout = 10000)
    public void pointsGetterTest() {
        Assert.assertTrue(checkPointsGetter());
    }

    /**
     * Test for getTexCoords method.
     */
    @Test(timeout = 10000)
    public void texCoordsGetterTest() {
        resetTexCoords();
        Assert.assertTrue(checkTexCoordsGetter());
    }

    /**
     * Test for getSmoothingGroups method.
     */
    @Test(timeout = 10000)
    public void smoothingGroupsGetterTest() {
        Assert.assertTrue(checkSmoothingGroupsGetter());
    }

    /**
     * Test for getFaces method.
     */
    @Test(timeout = 10000)
    public void facesGetterTest() {
        Assert.assertTrue(checkFacesGetter());
    }

}
