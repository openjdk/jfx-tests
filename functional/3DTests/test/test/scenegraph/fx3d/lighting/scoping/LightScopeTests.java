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
package test.scenegraph.fx3d.lighting.scoping;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import junit.framework.Assert;
import org.junit.Test;
import test.scenegraph.fx3d.utils.ShellVisibleLight;
import test.scenegraph.fx3d.utils.VisibleLight;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class LightScopeTests extends LightScopeTestBase {

    protected Node getShape() {
        return getNodes()[0];
    }

    @Test(timeout = 10000)
    public void singlePointEmptyScopeTest() {
        ShellVisibleLight singlePoint = buildNewLight(VisibleLight.LightType.Point, Color.RED);
        getLightMover(singlePoint).setTranslateX(240);
        Assert.assertEquals("default scope is not empty!", 0, singlePoint.getScope().length);
        checkScreenshot("singlePointLightEmptyScopeTest");
    }

    @Test(timeout = 10000)
    public void singleAmbientEmptyScopeTest() {
        ShellVisibleLight singlePoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        getLightMover(singlePoint).setTranslateX(240);
        Assert.assertEquals("default scope is not empty!", 0, singlePoint.getScope().length);
        checkScreenshot("singleAmbientLightEmptyScopeTest");
    }

    @Test(timeout = 10000)
    public void singlePointFullScopeTest() {
        ShellVisibleLight singlePoint = buildNewLight(VisibleLight.LightType.Point, Color.RED);
        getLightMover(singlePoint).setTranslateX(240);
        singlePoint.addToScope(getShape());
        Assert.assertEquals(1, singlePoint.getScope().length);
        checkScreenshot("singlePointLightFullScopeTest");
    }

    @Test(timeout = 10000)
    public void singleAmbientFullScopeTest() {
        ShellVisibleLight singlePoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        getLightMover(singlePoint).setTranslateX(240);
        singlePoint.addToScope(getShape());
        Assert.assertEquals(1, singlePoint.getScope().length);
        checkScreenshot("singleAmbientLightFullScopeTest");
    }

    @Test(timeout = 10000)
    public void twoPointEmptyScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Point, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Point, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        Assert.assertEquals("default scope of redPoint is not empty!", 0, redPoint.getScope().length);
        Assert.assertEquals("default scope of bluePoint is not empty!", 0, bluePoint.getScope().length);
        checkScreenshot("twoPointLightEmptyScopeTest");
    }

    @Test(timeout = 10000)
    public void twoPointDifferentScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Point, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Point, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        bluePoint.addToScope(getShape());
        Assert.assertEquals("default scope of redPoint is not empty!", 0, redPoint.getScope().length);
        Assert.assertEquals(1, bluePoint.getScope().length);
        checkScreenshot("twoPointLightDifferentScopeTest");
    }

    @Test(timeout = 10000)
    public void twoPointFullScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Point, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Point, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        redPoint.addToScope(getShape());
        bluePoint.addToScope(getShape());
        Assert.assertEquals(1, redPoint.getScope().length);
        Assert.assertEquals(1, bluePoint.getScope().length);
        checkScreenshot("twoPointLightFullScopeTest");
    }

    @Test(timeout = 10000)
    public void twoAmbientEmptyScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Ambient, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        Assert.assertEquals("default scope of redPoint is not empty!", 0, redPoint.getScope().length);
        Assert.assertEquals("default scope of bluePoint is not empty!", 0, bluePoint.getScope().length);
        checkScreenshot("twoAmbientLightEmptyScopeTest");
    }

    @Test(timeout = 10000)
    public void twoAmbientDifferentScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Ambient, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        bluePoint.addToScope(getShape());
        Assert.assertEquals("default scope of redPoint is not empty!", 0, redPoint.getScope().length);
        Assert.assertEquals(1, bluePoint.getScope().length);
        checkScreenshot("twoAmbientLightDifferentScopeTest");
    }

    @Test(timeout = 10000)
    public void twoAmbientFullScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Ambient, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        redPoint.addToScope(getShape());
        bluePoint.addToScope(getShape());
        Assert.assertEquals(1, redPoint.getScope().length);
        Assert.assertEquals(1, bluePoint.getScope().length);
        checkScreenshot("twoAmbientLightFullScopeTest");
    }

    @Test(timeout = 10000)
    public void mixedAmbientEmptyPointEmptyScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Point, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        Assert.assertEquals("default scope of redPoint is not empty!", 0, redPoint.getScope().length);
        Assert.assertEquals("default scope of bluePoint is not empty!", 0, bluePoint.getScope().length);
        checkScreenshot("mixedAmbientEmptyPointEmptyScopeTest");
    }

    @Test(timeout = 10000)
    public void mixedAmbientEmptyPointFullScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Point, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        bluePoint.addToScope(getShape());
        Assert.assertEquals("default scope of redPoint is not empty!", 0, redPoint.getScope().length);
        Assert.assertEquals(1, bluePoint.getScope().length);
        checkScreenshot("mixedAmbientEmptyPointFullScopeTest");
    }

    @Test(timeout = 10000)
    public void mixedAmbientFullPointEmptyScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Point, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        redPoint.addToScope(getShape());
        Assert.assertEquals("default scope of bluePoint is not empty!", 0, bluePoint.getScope().length);
        Assert.assertEquals(1, redPoint.getScope().length);
        checkScreenshot("mixedAmbientFullPointEmptyScopeTest");
    }

    @Test(timeout = 10000)
    public void mixedAmbientFullPointFullScopeTest() {
        ShellVisibleLight redPoint = buildNewLight(VisibleLight.LightType.Ambient, Color.RED);
        ShellVisibleLight bluePoint = buildNewLight(VisibleLight.LightType.Point, Color.BLUE);
        getLightMover(bluePoint).setTranslateX(240);
        getLightMover(redPoint).setTranslateX(-240);
        redPoint.addToScope(getShape());
        bluePoint.addToScope(getShape());
        Assert.assertEquals(1, redPoint.getScope().length);
        Assert.assertEquals(1, bluePoint.getScope().length);
        checkScreenshot("mixedAmbientFullPointFullScopeTest");
    }

    @Test(timeout = 10000)
    public void bigAmbientLightCountTest() {
        for (int i = 0; i < 20; i++) {
            getLightMover(buildNewLight(VisibleLight.LightType.Ambient, Color.AQUA)).setTranslateX(240);
        }
        checkScreenshot("bigAmbientLightCountTest");
    }

    @Test(timeout = 10000)
    public void bigPointLightCountTest() {
        for (int i = 0; i < 20; i++) {
            getLightMover(buildNewLight(VisibleLight.LightType.Point, Color.AQUA)).setTranslateX(240);
        }
        checkScreenshot("bigPointLightCountTest");
    }
}
