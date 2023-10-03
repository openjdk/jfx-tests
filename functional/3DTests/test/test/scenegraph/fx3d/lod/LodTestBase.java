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
package test.scenegraph.fx3d.lod;

import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.FX3DTestBase;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class LodTestBase extends FX3DTestBase {

    protected static LodTestAbstractApp application;
    protected static double STANDARD_DELTA = 100D;
    private final double LOD_TOLERANCE = 1.0f;

    @Override
    protected FX3DAbstractApp getApplication() {
        return application;
    }

    protected void moveGroup(final double delta) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.moveGroup(delta);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void moveObject(final double delta) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.moveObject(delta);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void moveCameraGroup(final double delta) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.moveCameraGroup(-delta);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void moveCamera(final double delta) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.moveCamera(-delta);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected double computeAreaInScreen() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(application.getNode().computeAreaInScreen());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void setLight(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.setLight(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void selectType(final LodTestAbstractApp.ShapeType type) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                application.selectType(type);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected abstract double getInitialLod(LodTestAbstractApp.ShapeType type);

    protected abstract double getChangedLod(LodTestAbstractApp.ShapeType type);

    protected void checkLod(final double lod) {
        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureValue(Boolean.TRUE, new State<Boolean>() {
            @Override
            public Boolean reached() {
                if (Math.abs(computeAreaInScreen() - lod) <= LOD_TOLERANCE) {
                    return Boolean.TRUE;
                }
                return null;
            }

            @Override
            public String toString() {
                return "Expected " + lod + ", but was " + computeAreaInScreen();
            }
        });
    }
}
