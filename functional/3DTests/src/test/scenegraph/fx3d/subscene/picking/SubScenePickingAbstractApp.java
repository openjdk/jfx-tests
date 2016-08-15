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
package test.scenegraph.fx3d.subscene.picking;

import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import test.scenegraph.fx3d.picking.PickingSharedAbstractApp;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class SubScenePickingAbstractApp extends PickingSharedAbstractApp {

    public final int BORDER_WEIGHT = 20;
    private SubScene subscene;
    private StackPane subroot;

    @Override
    public void initScene() {
        ptc = buildTestCase();
        subscene = createSubScene(ptc.getRoot(), ptc.HEIGHT, ptc.WIDTH);
        subroot = new StackPane(subscene);
        scene = createScene(subroot, ptc.HEIGHT + 2 * BORDER_WEIGHT, ptc.WIDTH + 2 * BORDER_WEIGHT);
        subscene.setCamera(ptc.getCamera());
        if (!isTest()) {
            scene.addEventHandler(KeyEvent.ANY, keyHandler);
            initKeys(scene);
        }
        subscene.addEventHandler(MouseEvent.MOUSE_CLICKED, pickHandler);
    }

    @Override
    public void setFill(Paint paint) {
        subscene.setFill(paint);
    }

}
