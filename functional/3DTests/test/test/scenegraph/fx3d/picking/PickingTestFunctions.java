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
package test.scenegraph.fx3d.picking;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Paint;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import test.scenegraph.fx3d.interfaces.PickingTestAppFace;
import test.scenegraph.fx3d.utils.FX3DTestBase;
import test.scenegraph.fx3d.utils.PickingTestCase;
import test.scenegraph.fx3d.utils.PickingTestFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class PickingTestFunctions extends FX3DTestBase implements PickingTestFace {

    protected static PickingTestAppFace app;

    @Override
    public PickResult click(Point pt) {
        final int count = getResultsCount();
        mouse().click(1, pt);
        new Waiter(Wrap.WAIT_STATE_TIMEOUT).ensureValue(Boolean.TRUE, new State<Boolean>() {
            @Override
            public Boolean reached() {
                if (count < getResultsCount()) {
                    return Boolean.TRUE;
                } else {
                    return null;
                }
            }
        });
        return new GetAction<PickResult>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(app.getLastResult().getPickResult());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setFill(final Paint paint) {
         new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                app.setFill(paint);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }



    protected double getDistanceExpectedValue(double funVal, double x, double y) {
        System.out.println(funVal + " " + x + " " + y + " " + getZTranslation() + " " + getCameraZeroPositionError());
        return Math.sqrt(Math.pow(getCameraZeroPositionError() + getCameraRealPositionError() +  getZTranslation() - funVal, 2)
                + Math.pow(x, 2) + Math.pow(y, 2));
    }
    protected double getCameraRealPositionError(){
        return 0;
    }
    protected double getCameraZeroPositionError(){
        return 0;
    }

    @Override
    public int getResultsCount() {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(app.getResultsCount());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setTranslationMode(final PickingTestCase.TranslationMode tm) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                app.setTranslationMode(tm);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public MouseEvent getLastResult() {
        return new GetAction<MouseEvent>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(app.getLastResult());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public double getZTranslation() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(app.getZTranslation());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public double getXTranslation() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(app.getXTranslation());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public double getYTranslation() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(app.getYTranslation());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
