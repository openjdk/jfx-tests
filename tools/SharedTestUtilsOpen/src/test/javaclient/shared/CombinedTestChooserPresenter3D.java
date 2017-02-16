/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */

package test.javaclient.shared;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import javafx.stage.Stage;
import com.sun.javafx.stage.WindowHelper;

/**
 *
 * @author shubov
 */
public class CombinedTestChooserPresenter3D extends CombinedTestChooserPresenter {


    public CombinedTestChooserPresenter3D(int _width, int _height, String _title, Pane _buttonsPane) {
        super(_width, _height, _title, _buttonsPane, true);
        avoidSettingPageContentSize();
    }

    @Override
    protected Scene createSceneForChooserPresenter() {
      //  Scene3D scene = new Scene3D(new Group(), width + 50, height + TABS_SPACE + 30);
      Scene3D scene = new Scene3D(new Group(), width , height);

       // DEBUG:
       //     scene.setTranslateZ(0);
       //     scene.setRotateX(30);
       //     scene.setRotateY(45);

        //scene.addDragSupport();  //let it be here for debug
        return scene;
    }

    @Override
    public void show(Stage stage) {
        // DEBUG:
        //stage.setX(0);
        //stage.setY(0);

        stage.setTitle(title);
        fillScene();
        stage.setScene(getScene());
        stage.show();
        stage.toFront();
        setNodeForScreenshot(null);
        //stage.setFocused(true);
        WindowHelper.setFocused(stage, true);
    }

}

