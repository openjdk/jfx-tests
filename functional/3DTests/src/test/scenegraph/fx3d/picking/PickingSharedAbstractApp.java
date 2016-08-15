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

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import test.scenegraph.fx3d.interfaces.PickingTestAppFace;
import test.scenegraph.fx3d.utils.FX3DAbstractApp;
import test.scenegraph.fx3d.utils.PickingTestCase;

/**
 *
 * @author Andrew Glushchenko
 */
public abstract class PickingSharedAbstractApp extends FX3DAbstractApp implements PickingTestAppFace {

    protected PickingTestCase ptc;
    protected Scene scene;
    protected final EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent t) {
            System.out.println(t.getText());
            switch (t.getText()) {
                case "g":
                    try {
                        setTranslationMode(PickingTestCase.TranslationMode.GroupTranslation);
                    } catch (Exception ex) {
                        Logger.getLogger(PickingAbstractApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "c":
                    try {
                        setTranslationMode(PickingTestCase.TranslationMode.CameraTranslation);
                    } catch (Exception ex) {
                        Logger.getLogger(PickingAbstractApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

            }
        }
    };
    protected final EventHandler<MouseEvent> pickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            pick(t);
        }
    };

    @Override
    public void setTranslationMode(PickingTestCase.TranslationMode mode) {
        ptc.setTranslationMode(mode);
    }

    public int getTestCaseWidth(){
        return ptc.WIDTH;
    }
    public int getTestCaseHeight(){
        return ptc.HEIGHT;
    }

    protected abstract void initKeys(Scene scene);

    protected void pick(MouseEvent t) {

        ptc.pick(t);

        if (!isTest()) {
            System.out.println(t.getSceneX());
            System.out.println(t.getSceneY());
            System.out.println(t.getZ());
            if (t.getPickResult() != null) {

                System.out.println(t.getPickResult().getIntersectedDistance());
                System.out.println(t.getPickResult().getIntersectedFace());
                System.out.println(t.getPickResult().getIntersectedNode());
                System.out.println(t.getPickResult().getIntersectedPoint());
                System.out.println(t.getPickResult().getIntersectedTexCoord());
            } else {
                System.out.println("null");
            }
        }

    }

    @Override
    public MouseEvent getLastResult() {
        return ptc.getLastResult();
    }

    @Override
    public int getResultsCount() {
        return ptc.getResultsCount();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public double getZTranslation() {
        return ptc.getZTranslation();
    }

    @Override
    public double getXTranslation() {
        return ptc.getXTranslation();
    }

    @Override
    public double getYTranslation() {
        return ptc.getYTranslation();
    }

    public abstract PickingTestCase buildTestCase();
}
