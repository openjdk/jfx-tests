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
package test.scenegraph.fx3d.utils;

import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import test.scenegraph.fx3d.interfaces.Mover;

/**
 *
 * @author Andrew Glushchenko
 */
public class ShellGroupMover {

    private Mover mv;

    public ShellGroupMover(Mover mv) {
        this.mv = mv;
    }

    public void setTranslateX(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                mv.translateXProperty().set(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void setTranslateY(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                mv.translateYProperty().set(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void setTranslateZ(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                mv.translateZProperty().set(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void setRotateX(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                mv.rotateXProperty().set(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void setRotateY(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                mv.rotateYProperty().set(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void setRotateZ(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                mv.rotateZProperty().set(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public double getTranslateX() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(mv.translateXProperty().get());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public double getTranslateY() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(mv.translateYProperty().get());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public double getTranslateZ() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(mv.translateZProperty().get());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public double getRotateX() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(mv.rotateXProperty().get());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public double getRotateY() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(mv.rotateYProperty().get());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public double getRotateZ() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(mv.rotateZProperty().get());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
