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
import test.scenegraph.fx3d.interfaces.Mover;
import test.scenegraph.fx3d.interfaces.camera.CameraAsChildTestingFace;
import test.scenegraph.fx3d.interfaces.camera.CameraTestingFace;
import test.scenegraph.fx3d.utils.ShellGroupMover;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class CameraAsChildTests extends CameraTestsOverall implements CameraAsChildTestingFace {

    protected abstract CameraAsChildTestingFace getCameraAsChildTestingFace();

    @Override
    protected CameraTestingFace getCameraTestingFace() {
        return getCameraAsChildTestingFace();
    }

    @Override
    public Mover getCameraMover() {
        return new GetAction<Mover>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(getCameraAsChildTestingFace().getCameraMover());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /**
     * Test for camera rotation.
     */
    @Test(timeout = 10000)
    public void rotationTest() {
        ShellGroupMover sgm = new ShellGroupMover(getCameraMover());
        sgm.setRotateX(45);
        sgm.setRotateY(45);
        sgm.setRotateZ(45);
        checkScreenshot("rotationTest");

    }

    /**
     * Test for camera translation.
     */
    @Test(timeout = 10000)
    public void translationTest() {
        ShellGroupMover sgm = new ShellGroupMover(getCameraMover());
        sgm.setTranslateZ(-3500);
        sgm.setTranslateX(100);
        sgm.setTranslateY(100);
        checkScreenshot("translationTest");
    }
}
