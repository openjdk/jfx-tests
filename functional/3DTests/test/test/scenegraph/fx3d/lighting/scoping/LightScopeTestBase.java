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
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.junit.Before;
import org.junit.BeforeClass;
import test.javaclient.shared.JemmyUtils;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.FX3DTestBase;
import test.scenegraph.fx3d.utils.GroupMover;
import test.scenegraph.fx3d.utils.ShellGroupMover;
import test.scenegraph.fx3d.utils.ShellVisibleLight;
import test.scenegraph.fx3d.utils.VisibleLight;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class LightScopeTestBase extends FX3DTestBase {

    private static LightScopeTestApp application;

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }

    @BeforeClass
    public static void setUp() {
        LightScopeTestApp.setTest(true);
        LightScopeTestApp.main(null);
        application = (LightScopeTestApp) LightScopeTestApp.getInstance();
        JemmyUtils.setJemmyComparatorByDistance(0.001f);
    }

    @Before
    public abstract void chooseCase();

    protected ShellVisibleLight[] getVisibleLights() {
        return new GetAction<ShellVisibleLight[]>() {

            @Override
            public void run(Object... os) throws Exception {
                VisibleLight[] vl = application.getVisibleLights();
                ShellVisibleLight[] result = new ShellVisibleLight[vl.length];
                for (int i = 0; i < vl.length; i++) {
                    result[i] = new ShellVisibleLight(vl[i]);
                }
                setResult(result);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void initTestCase(final LightScopeTestApp.TestCaseType type) {
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                application.initTestCase(type);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Node[] getNodes() {
        return new GetAction<Node[]>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(application.getNodes());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected ShellGroupMover getLightMover(final ShellVisibleLight svl) {
        return new GetAction<ShellGroupMover>() {

            @Override
            public void run(Object... os) throws Exception {
                VisibleLight vl = svl.getVisibleLight();
                GroupMover gm = application.getLightMover(vl);
                setResult(new ShellGroupMover(gm));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected ShellVisibleLight buildNewLight(final VisibleLight.LightType type, final Color color) {
        return new GetAction<ShellVisibleLight>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(new ShellVisibleLight(application.buildNewLight(type, color)));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void deleteLight(final ShellVisibleLight light) {
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                application.deleteLight(light.getVisibleLight());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

}
