/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
