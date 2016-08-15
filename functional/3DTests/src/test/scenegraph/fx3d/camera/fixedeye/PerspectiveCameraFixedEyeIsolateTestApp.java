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
package test.scenegraph.fx3d.camera.fixedeye;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import test.javaclient.shared.Utils;
import test.scenegraph.fx3d.camera.CameraIsolateAbstractApp;
import test.scenegraph.fx3d.interfaces.camera.PerspectiveCameraFixedEyeIsolateTestingFace;

/**
 *
 * @author Andrew Glushchenko
 */
public class PerspectiveCameraFixedEyeIsolateTestApp extends CameraIsolateAbstractApp implements PerspectiveCameraFixedEyeIsolateTestingFace {

    protected PerspectiveCamera cam = null;

    @Override
    protected Camera addCamera(Scene scene) {
        camera = cam = new PerspectiveCamera(true);
        scene.setCamera(camera);
        camera.setFarClip(10000);
        return camera;
    }

    public static void main(String[] args) {
        Utils.launch(PerspectiveCameraFixedEyeIsolateTestApp.class, args);
    }

    public double getFieldOfView() {
        return cam.getFieldOfView();
    }

    @Override
    public void setFieldOfView(double d) {
        cam.setFieldOfView(d);
    }

    @Override
    public boolean isVerticalFieldOfView() {
        return cam.isVerticalFieldOfView();
    }

    @Override
    public void setVerticalFieldOfView(boolean bln) {
        cam.setVerticalFieldOfView(bln);
    }

    @Override
    protected void initCustomKeys() {

        scene.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                switch (t.getText()) {
                    case "b":
                        setFieldOfView(getFieldOfView() - 10);
                        System.out.println(getFieldOfView());
                        break;
                    case "n":
                        setFieldOfView(getFieldOfView() + 10);
                        System.out.println(getFieldOfView());
                        break;
                    case "m":
                        setVerticalFieldOfView(!isVerticalFieldOfView());
                        break;

                }
            }
        });

    }
}
