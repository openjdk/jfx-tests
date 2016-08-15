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
package test.scenegraph.fx3d.camera;

import org.jemmy.action.GetAction;
import org.jemmy.fx.Root;
import org.junit.Test;
import test.scenegraph.fx3d.interfaces.camera.CameraIsolateTestingFace;
import test.scenegraph.fx3d.interfaces.camera.PerspectiveCameraFixedEyeIsolateTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class PerspectiveCameraFixedEyeIsolateTests extends CameraIsolateTests implements PerspectiveCameraFixedEyeIsolateTestingFace {

    protected abstract PerspectiveCameraFixedEyeIsolateTestingFace getPerspectiveCameraFixedEyeIsolateTestingFace();

    @Override
    protected CameraIsolateTestingFace getCameraIsolateTestingFace() {
        return getPerspectiveCameraFixedEyeIsolateTestingFace();
    }

    @Override
    public void setFieldOfView(final double d) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getPerspectiveCameraFixedEyeIsolateTestingFace().setFieldOfView(d);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public void setVerticalFieldOfView(final boolean bln) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                getPerspectiveCameraFixedEyeIsolateTestingFace().setVerticalFieldOfView(bln);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    public boolean isVerticalFieldOfView() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getPerspectiveCameraFixedEyeIsolateTestingFace().isVerticalFieldOfView());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /**
     * Test for fieldOfViewProperty.
     */
    @Test(timeout = 10000)
    public void fieldOfViewTest() {
        setFieldOfView(161.24343);
        checkScreenshot("fieldOfViewTest");
    }

    /**
     * Test for verticalFieldOfViewProperty.
     */
    @Test(timeout = 10000)
    public void verticalFieldOfViewTest() {
        setVerticalFieldOfView(true);
        checkScreenshot("verticalFieldOfViewTest");
    }
}
