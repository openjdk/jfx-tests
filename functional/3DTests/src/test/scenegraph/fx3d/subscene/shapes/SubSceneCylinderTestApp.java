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
package test.scenegraph.fx3d.subscene.shapes;

import org.junit.Assert;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.shapes.CylinderTestApp;
import test.scenegraph.fx3d.utils.ShapesTestCase;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneCylinderTestApp extends SubSceneShape3DAbstractApp {

    @Override
    protected ShapesTestCase[] buildTestCases() {
        ShapesTestCase[] stc = new ShapesTestCase[3];
        for (int i = 0; i < stc.length; i++) {
            stc[i] = new CylinderTestApp().getTestCase();
        }
        return stc;
    }

    public void setDivisions(int divisions) {
        for (ShapesTestCase c : cases) {
            ((CylinderTestApp.CylinderTestCase) c).setDivisions(divisions);
            ((CylinderTestApp.CylinderTestCase) c).isDefaultDivisions = false;
        }
        repaint();
        for (ShapesTestCase c : cases) {
            ((CylinderTestApp.CylinderTestCase) c).isDefaultDivisions = true;
        }
    }

    private int getDivisions(int num) {
        return ((CylinderTestApp.CylinderTestCase) cases[num]).getDivisions();
    }

    public int getDivisions() {
        Assert.assertEquals(getDivisions(0), getDivisions(1));
        Assert.assertEquals(getDivisions(2), getDivisions(1));
        return getDivisions(2);
    }

    public void setRadius(double rad) {
        for (ShapesTestCase c : cases) {
            ((CylinderTestApp.CylinderTestCase) c).setRadius(rad);
        }
    }

    public void setHeight(double h) {
        for (ShapesTestCase c : cases) {
            ((CylinderTestApp.CylinderTestCase) c).setHeight(h);
        }
    }

    public static void main(String[] args) {
        Utils.launch(SubSceneCylinderTestApp.class, args);
    }
}
