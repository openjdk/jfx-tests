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
package test.scenegraph.fx3d.lod;

import org.junit.Test;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class LodTests extends LodTestBase {

    /**
     * Test for LOD of Sphere without any z-transltations and without light.
     */
    @Test(timeout = 10000)
    public void sphereInitialLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(false);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation of parent Group and without
     * light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(false);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation and without light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateShapeLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(false);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation of Camera and without light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateCameraLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(false);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation of Camera parent Group and
     * without light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateCameraGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(false);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere without any z-transltations and with light.
     */
    @Test(timeout = 10000)
    public void sphereInitialLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(true);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation of parent Group and with
     * light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(true);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation and with light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateShapeLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(true);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation of Camera and with light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateCameraLightOnTest() {
        setLight(true);
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Sphere with z-transltation of Camera parent Group and
     * with light.
     */
    @Test(timeout = 10000)
    public void sphereTranslateCameraGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Sphere);
        setLight(true);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Sphere));
    }

    /**
     * Test for LOD of Box without any z-transltations and without light.
     */
    @Test(timeout = 10000)
    public void boxInitialLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(false);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation of parent Group and without
     * light.
     */
    @Test(timeout = 10000)
    public void boxTranslateGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(false);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation and without light.
     */
    @Test(timeout = 10000)
    public void boxTranslateShapeLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(false);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation of Camera and without light.
     */
    @Test(timeout = 10000)
    public void boxTranslateCameraLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(false);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation of Camera parent Group and
     * without light.
     */
    @Test(timeout = 10000)
    public void boxTranslateCameraGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(false);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box without any z-transltations and with light.
     */
    @Test(timeout = 10000)
    public void boxInitialLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(true);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation of parent Group and with light.
     */
    @Test(timeout = 10000)
    public void boxTranslateGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(true);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation and with light.
     */
    @Test(timeout = 10000)
    public void boxTranslateShapeLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(true);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation of Camera and with light.
     */
    @Test(timeout = 10000)
    public void boxTranslateCameraLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(true);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Box with z-transltation of Camera parent Group and with
     * light.
     */
    @Test(timeout = 10000)
    public void boxTranslateCameraGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Box);
        setLight(true);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Box));
    }

    /**
     * Test for LOD of Cone(custom) without any z-transltations and without
     * light.
     */
    @Test(timeout = 10000)
    public void coneInitialLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(false);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Cone));

    }

    /**
     * Test for LOD of Cone(custom) with z-transltation of parent Group and
     * without light.
     */
    @Test(timeout = 10000)
    public void coneTranslateGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(false);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Cone(custom) with z-transltation and without light.
     */
    @Test(timeout = 10000)
    public void coneTranslateShapeLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(false);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Cone(custom) with z-transltation of Camera and without
     * light.
     */
    @Test(timeout = 10000)
    public void coneTranslateCameraLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(false);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Cone(custom) with z-transltation of Camera parent Group
     * and without light.
     */
    @Test(timeout = 10000)
    public void coneTranslateCameraGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(false);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Cone(custom) without any z-transltations and with light.
     */
    @Test(timeout = 10000)
    public void coneInitialLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(true);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Cone));

    }

    /**
     * Test for LOD of Cone(custom) with z-transltation of parent Group and with
     * light.
     */
    @Test(timeout = 10000)
    public void coneTranslateGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(true);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Cone(custom) with z-transltation and with light.
     */
    @Test(timeout = 10000)
    public void coneTranslateShapeLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(true);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Cone(custom) with z-transltation of Camera and with
     * light.
     */
    @Test(timeout = 10000)
    public void coneTranslateCameraLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(true);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Cone(custom) with z-transltation of Camera parent Group
     * and with light.
     */
    @Test(timeout = 10000)
    public void coneTranslateCameraGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cone);
        setLight(true);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cone));
    }

    /**
     * Test for LOD of Plane(custom) without any z-transltations and without
     * light.
     */
    @Test(timeout = 10000)
    public void planeInitialLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(false);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Plane));

    }

    /**
     * Test for LOD of Plane(custom) with z-transltation of parent Group and
     * without light.
     */
    @Test(timeout = 10000)
    public void planeTranslateGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(false);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of Plane(custom) with z-transltation and without light.
     */
    @Test(timeout = 10000)
    public void planeTranslateShapeLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(false);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of Plane(custom) with z-transltation of Camera and without
     * light.
     */
    @Test(timeout = 10000)
    public void planeTranslateCameraLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(false);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of Plane(custom) with z-transltation of Camera parent Group
     * and without light.
     */
    @Test(timeout = 10000)
    public void planeTranslateCameraGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(false);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of Plane(custom) without any z-transltations and with light.
     */
    @Test(timeout = 10000)
    public void planeInitialLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(true);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Plane));

    }

    /**
     * Test for LOD of Plane(custom) with z-transltation of parent Group and
     * with light.
     */
    @Test(timeout = 10000)
    public void planeTranslateGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(true);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of Plane(custom) with z-transltation and with light.
     */
    @Test(timeout = 10000)
    public void planeTranslateShapeLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(true);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of Plane(custom) with z-transltation of Camera and with
     * light.
     */
    @Test(timeout = 10000)
    public void planeTranslateCameraLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(true);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of Plane(custom) with z-transltation of Camera parent Group
     * and with light.
     */
    @Test(timeout = 10000)
    public void planeTranslateCameraGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Plane);
        setLight(true);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Plane));
    }

    /**
     * Test for LOD of SemiSphere(custom) without any z-transltations and
     * without light.
     */
    @Test(timeout = 10000)
    public void semiSphereInitialLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(false);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.SemiSphere));

    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation of parent Group
     * and without light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(false);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation and without light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateShapeLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(false);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation of Camera and
     * without light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateCameraLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(false);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation of Camera parent
     * Group and without light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateCameraGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(false);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of SemiSphere(custom) without any z-transltations and with
     * light.
     */
    @Test(timeout = 10000)
    public void semiSphereInitialLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(true);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.SemiSphere));

    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation of parent Group
     * and with light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(true);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation and with light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateShapeLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(true);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation of Camera and with
     * light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateCameraLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(true);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of SemiSphere(custom) with z-transltation of Camera parent
     * Group and with light.
     */
    @Test(timeout = 10000)
    public void semiSphereTranslateCameraGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.SemiSphere);
        setLight(true);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.SemiSphere));
    }

    /**
     * Test for LOD of Cylinder without any z-transltations and without light.
     */
    @Test(timeout = 10000)
    public void cylinderInitialLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(false);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Cylinder));

    }

    /**
     * Test for LOD of Cylinder with z-transltation of parent Group and without
     * light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(false);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }

    /**
     * Test for LOD of Cylinder with z-transltation and without light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateShapeLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(false);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }

    /**
     * Test for LOD of Cylinder with z-transltation of Camera and without light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateCameraLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(false);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }

    /**
     * Test for LOD of Cylinder with z-transltation of Camera parent Group and
     * without light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateCameraGroupLightOffTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(false);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }

    /**
     * Test for LOD of Cylinder without any z-transltations and with light.
     */
    @Test(timeout = 10000)
    public void cylinderInitialLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(true);
        checkLod(getInitialLod(LodTestAbstractApp.ShapeType.Cylinder));

    }

    /**
     * Test for LOD of Cylinder with z-transltation of parent Group and with
     * light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(true);
        moveGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }

    /**
     * Test for LOD of Cylinder with z-transltation and with light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateShapeLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(true);
        moveObject(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }

    /**
     * Test for LOD of Cylinder with z-transltation of Camera and with light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateCameraLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(true);
        moveCamera(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }

    /**
     * Test for LOD of Cylinder with z-transltation of Camera parent Group and
     * with light.
     */
    @Test(timeout = 10000)
    public void cylinderTranslateCameraGroupLightOnTest() {
        selectType(LodTestAbstractApp.ShapeType.Cylinder);
        setLight(true);
        moveCameraGroup(STANDARD_DELTA);
        checkLod(getChangedLod(LodTestAbstractApp.ShapeType.Cylinder));
    }
}
