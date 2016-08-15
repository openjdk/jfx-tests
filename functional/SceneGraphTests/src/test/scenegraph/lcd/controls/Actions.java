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
package test.scenegraph.lcd.controls;


import javafx.scene.Parent;
import javafx.scene.effect.*;

/**
 *
 * @author Alexander Petrov
 */
public enum Actions implements Action {

    None(new Action() {

        public Parent updateControl(Parent control) {
            control.setRotate(0);
            control.setEffect(null);
            return control;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Rotate90(new Action() {

        public Parent updateControl(Parent control) {
            control.setRotate(90);
            control.setEffect(null);
            return control;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Rotate180(new Action() {

        public Parent updateControl(Parent control) {
            control.setRotate(180);
            control.setEffect(null);
            return control;
        }

        public boolean isLCDWork() {
            return true;
        }
    }),
    Rotate360(new Action() {

        public Parent updateControl(Parent control) {
            control.setRotate(360);
            control.setEffect(null);
            return control;
        }

        public boolean isLCDWork() {
            return true;
        }

    }),
    ColorAdjust(new EffectAction(new ColorAdjust(), true)),
    DisplacementMap(new EffectAction(new DisplacementMap(), true)),
    DropShadow(new EffectAction(new DropShadow(), true)),
    Glow(new EffectAction(new Glow(), true)),
    InnerShadow(new EffectAction(new InnerShadow(), true)),
    Lighting(new EffectAction(new Lighting(), true)),
    Reflection(new EffectAction(new Reflection(), true));

    private Action action;

    private Actions(Action action) {
        this.action = action;
    }

    public Parent updateControl(Parent control) {
        return action.updateControl(control);
    }

    public boolean isLCDWork() {
        return action.isLCDWork();
    }
}
