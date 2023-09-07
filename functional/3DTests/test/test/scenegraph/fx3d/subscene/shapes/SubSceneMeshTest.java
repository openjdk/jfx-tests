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
import junit.framework.Assert;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.jemmy.image.ImageComparator;
import org.jemmy.image.glass.GlassPixelImageComparator;
import org.jemmy.image.pixel.PixelEqualityRasterComparator;
import org.junit.BeforeClass;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;
import test.scenegraph.fx3d.shapes.MeshTests;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;

/**
 *
 * @author Andrew Glushchenko
 */
public class SubSceneMeshTest extends MeshTests {

    private Pair<Integer, Integer>[] positions = null;
    private static SubSceneMeshTestApp application;

    @Override
    protected Pair<Integer, Integer>[] getPositions() {
        if (positions == null) {
            positions = new Pair[2];
            positions[0] = new Pair<Integer, Integer>(45, 0);
            positions[1] = new Pair<Integer, Integer>(135, 0);
        }
        return positions;
    }

    @BeforeClass
    public static void setUp() {
        Root.ROOT.getEnvironment().setProperty(ImageComparator.class,
            new GlassPixelImageComparator(new PixelEqualityRasterComparator(.05)));
        SubSceneMeshTestApp.main(null);
        application = (SubSceneMeshTestApp) SubSceneMeshTestApp.getInstance();
    }

    @Override
    public void resetPoints() {
        Throwable ex = new GetAction<Throwable>() {
            @Override
            public void run(Object... os) throws Exception {
                try {
                    application.resetPoints();
                    setResult(null);
                } catch (Throwable ex) {
                    setResult(ex);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Override
    public void resetTexCoords() {
        Throwable ex = new GetAction<Throwable>() {
            @Override
            public void run(Object... os) throws Exception {
                try {
                    application.resetTexCoords();
                    setResult(null);
                } catch (Throwable ex) {
                    setResult(ex);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Override
    public void setSmoothingGroups() {
        Throwable ex = new GetAction<Throwable>() {
            @Override
            public void run(Object... os) throws Exception {
                try {
                    application.setSmoothingGroups();
                    setResult(null);
                } catch (Throwable ex) {
                    setResult(ex);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Override
    public void setDefaultSmoothingGroups(final boolean bln) {
        Throwable ex = new GetAction<Throwable>() {
            @Override
            public void run(Object... os) throws Exception {
                try {
                    application.setDefaultSmoothingGroups(bln);
                    setResult(null);
                } catch (Throwable ex) {
                    setResult(ex);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Override
    public void resetFaces() {
        Throwable ex = new GetAction<Throwable>() {
            @Override
            public void run(Object... os) throws Exception {
                try {
                    application.resetFaces();
                    setResult(null);
                } catch (Throwable ex) {
                    setResult(ex);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
        if (ex != null) {
            Assert.fail(ex.getLocalizedMessage());
        }
    }

    @Override
    public boolean checkPointsGetter() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkPointsGetter());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public boolean checkFacesGetter() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkFacesGetter());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public boolean checkTexCoordsGetter() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkTexCoordsGetter());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public boolean checkSmoothingGroupsGetter() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkSmoothingGroupsGetter());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public boolean checkConstruct() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.checkConstruct());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void shapePrepare() {
    }

    @Override
    public boolean setNotValidSmoothingGroups() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.setNotValidSmoothingGroups());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected ShapesTestingFace getShapesApp() {
        return application;
    }

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }

    @Override
    public void setSpecialFaces() {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setSpecialFaces();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
