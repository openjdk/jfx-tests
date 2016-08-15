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
package test.scenegraph.fx3d.subscene.lighting.scoping;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.FX3DTestBase;
import test.scenegraph.fx3d.utils.ShellVisibleLight;
import test.scenegraph.fx3d.utils.VisibleLight.LightType;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneLightScopingTest extends FX3DTestBase {

    private static SubSceneLightScopingTestApp application;

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }

    @BeforeClass
    public static void setUp() {
        SubSceneLightScopingTestApp.main(null);
        application = (SubSceneLightScopingTestApp) SubSceneLightScopingTestApp.getInstance();
    }

    private ShellVisibleLight getLight(final int num) {
        return new GetAction<ShellVisibleLight>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(new ShellVisibleLight(application.getLight(num)));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private Node getNode(final int num) {
        return new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.getNode(num));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private void setLight(final int num, final Color color, final LightType lt) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight(num, color, lt);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Test
    public void isolatePointLightFullTest() {
        setLight(0, Color.RED, LightType.Point);
        setLight(1, Color.GREEN, LightType.Point);
        setLight(2, Color.BLUE, LightType.Point);
        getLight(0).addToScope(getNode(0));
        getLight(1).addToScope(getNode(1));
        getLight(2).addToScope(getNode(2));
        checkScreenshot("isolatePointLightFullTest");
    }

    @Test
    public void isolateAmbientLightFullTest() {
        setLight(0, Color.RED, LightType.Ambient);
        setLight(1, Color.GREEN, LightType.Ambient);
        setLight(2, Color.BLUE, LightType.Ambient);
        getLight(0).addToScope(getNode(0));
        getLight(1).addToScope(getNode(1));
        getLight(2).addToScope(getNode(2));
        checkScreenshot("isolateAmbientLightFullTest");
    }

    @Test
    public void isolatePointLightEmptyTest() {
        setLight(0, Color.RED, LightType.Point);
        setLight(1, Color.GREEN, LightType.Point);
        setLight(2, Color.BLUE, LightType.Point);
        checkScreenshot("isolatePointLightEmptyTest");
    }

    @Test
    public void isolateAmbientLightEmptyTest() {
        setLight(0, Color.RED, LightType.Ambient);
        setLight(1, Color.GREEN, LightType.Ambient);
        setLight(2, Color.BLUE, LightType.Ambient);
        checkScreenshot("isolateAmbientLightEmptyTest");
    }

    @Test
    public void pointLightTest() {
        setLight(0, Color.RED, LightType.Point);
        setLight(1, Color.GREEN, LightType.Point);
        setLight(2, Color.BLUE, LightType.Point);
        getLight(0).addToScope(getNode(1));
        getLight(1).addToScope(getNode(2));
        getLight(2).addToScope(getNode(0));
        checkScreenshot("pointLightTest");
    }

    @Test
    public void ambientLightTest() {
        setLight(0, Color.RED, LightType.Ambient);
        setLight(1, Color.GREEN, LightType.Ambient);
        setLight(2, Color.BLUE, LightType.Ambient);
        getLight(0).addToScope(getNode(1));
        getLight(1).addToScope(getNode(2));
        getLight(2).addToScope(getNode(0));
        checkScreenshot("ambientLightTest");
    }
}
