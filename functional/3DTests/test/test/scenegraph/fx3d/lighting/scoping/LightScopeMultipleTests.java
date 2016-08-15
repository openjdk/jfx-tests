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
import org.junit.Test;
import test.scenegraph.fx3d.utils.ShellGroupMover;
import test.scenegraph.fx3d.utils.ShellVisibleLight;
import test.scenegraph.fx3d.utils.VisibleLight;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class LightScopeMultipleTests extends LightScopeTests {

    final static Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.AQUA, Color.CORAL};

    @Test(timeout = 10000)
    public void everyShapeWithPointLightTest() {
        everyShapeTest(VisibleLight.LightType.Point);
        checkScreenshot("everyShapeWithPointLightTest");
    }

    @Test(timeout = 10000)
    public void everyShapeWithAmbientLightTest() {
        everyShapeTest(VisibleLight.LightType.Ambient);
        checkScreenshot("everyShapeWithAmbientLightTest");
    }

    private void everyShapeTest(VisibleLight.LightType lt) {
        Node nodes[] = getNodes();
        for (int i = 0; i < nodes.length; i++) {
            ShellVisibleLight svl = buildNewLight(lt, colors[i]);
            svl.addToScope(nodes[i]);
            ShellGroupMover sgm = getLightMover(svl);
            sgm.setTranslateX(getNodeTranslateX(nodes[i]) - 75);
            sgm.setTranslateY(getNodeTranslateY(nodes[i]));
        }
    }

    private double getNodeTranslateX(final Node node) {
        return new GetAction<Double>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(node.getTranslateX());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private double getNodeTranslateY(final Node node) {
        return new GetAction<Double>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(node.getTranslateY());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
