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
import org.junit.BeforeClass;
import org.jemmy.image.ImageComparator;
import org.jemmy.image.glass.GlassPixelImageComparator;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;
import test.scenegraph.fx3d.shapes.BoxTests;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneBoxTest extends BoxTests {

    private Pair<Integer, Integer>[] positions = null;
    private static SubSceneBoxTestApp application;

    @BeforeClass
    public static void setUp() {
        Root.ROOT.getEnvironment().setProperty(ImageComparator.class,
            new GlassPixelImageComparator(new
                PixelEqualityRasterComparator(FX3DAbstractApp.COLOR_TOLERANCE)));
        SubSceneBoxTestApp.main(null);
        application = (SubSceneBoxTestApp) SubSceneBoxTestApp.getInstance();
    }

    @Override
    protected Pair<Integer, Integer>[] getPositions() {
        if (positions == null) {
            positions = new Pair[1];
            positions[0] = new Pair<Integer, Integer>(45, 45);
        }
        return positions;
    }

    @Override
    public void setHeight(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((SubSceneBoxTestApp) application).setHeight(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected void setWidth(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((SubSceneBoxTestApp) application).setWidth(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected void setDepth(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((SubSceneBoxTestApp) application).setDepth(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void shapePrepare() {
        setDepth(2);
        setWidth(2);
        setHeight(1);
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
