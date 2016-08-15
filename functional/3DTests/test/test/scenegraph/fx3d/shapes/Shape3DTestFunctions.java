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

import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import test.scenegraph.fx3d.interfaces.ShapesTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class Shape3DTestFunctions extends Shape3DTestOverall{

    protected abstract ShapesTestingFace getShapesApp();

    @Override
    public void setDiffuseColor(final Color color) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setDiffuseColor(color);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setNullMaterial(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setNullMaterial(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setSpecularMap(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setSpecularMap(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setVisible(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setVisible(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setDiffuseMap(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setDiffuseMap(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setBumpMap(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setBumpMap(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setAndUpdateSpecularMap(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setAndUpdateSpecularMap(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setAndUpdateDiffuseMap(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setAndUpdateDiffuseMap(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setAndUpdateBumpMap(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setAndUpdateBumpMap(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setAndUpdateSelfIlluminationMap(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setAndUpdateSelfIlluminationMap(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setSpecularPower(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setSpecularPower(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setSpecularColor(final Color color) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setSpecularColor(color);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public String materialToString() {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getShapesApp().materialToString());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void materialConstruct1() {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().materialConstruct1();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void materialConstruct2() {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().materialConstruct2();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setSelfIllumination(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setSelfIllumination(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setDrawMode(final DrawMode mode) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setDrawMode(mode);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setRotateX(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setRotateX(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setRotateY(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setRotateY(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setRotateZ(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setRotateZ(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setCullFace(final CullFace face) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getShapesApp().setCullFace(face);
            }
        }.dispatch(Root.ROOT.getEnvironment());

    }
}
