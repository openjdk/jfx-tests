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

import javafx.util.Pair;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.jemmy.image.ImageComparator;
import org.jemmy.image.glass.GlassPixelImageComparator;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import org.junit.BeforeClass;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;
import test.scenegraph.fx3d.shapes.SphereTests;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneSphereTest extends SphereTests{

    private Pair<Integer, Integer>[] positions = null;
private static SubSceneSphereTestApp application;
    @Override
    protected Pair<Integer, Integer>[] getPositions() {
        if (positions == null) {
            positions = new Pair[2];
            //135, 215
            positions[0] = new Pair<Integer, Integer>(135, 0);
            positions[1] = new Pair<Integer, Integer>(215, 0);
        }
        return positions;
    }

    @Override
    protected void setRadius(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((SubSceneSphereTestApp) application).setRadius(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected void setDivisions(final int d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((SubSceneSphereTestApp) application).setDivisions(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected int getDivisions() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((SubSceneSphereTestApp) application).getDivisions());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @BeforeClass
    public static void setUp() {
        Root.ROOT.getEnvironment().setProperty(ImageComparator.class,
            new GlassPixelImageComparator(new
                PixelEqualityRasterComparator(FX3DAbstractApp.COLOR_TOLERANCE)));
        SubSceneSphereTestApp.main(null);
        application = (SubSceneSphereTestApp) SubSceneSphereTestApp.getInstance();
    }

    @Override
    public void shapePrepare() {
        setRadius(2);
    }
    @Override
    protected ShapesTestingFace getShapesApp() {
        return application;
    }

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }
}
