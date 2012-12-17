/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.controls;

import javafx.scene.Parent;
import javafx.scene.effect.Effect;

/**
 *
 * @author Alexander Petrov
 */
public class EffectAction implements Action{
    private boolean lcdWork;
    private Effect effect;

    public EffectAction(Effect effect, boolean lcdWork) {
        this.lcdWork = lcdWork;
        this.effect = effect;
    }
    
    public Parent updateControl(Parent node) {
        node.setEffect(effect);
        node.setRotate(0);
        return node;
    }

    public boolean isLCDWork() {
        return lcdWork;
    }
    
}
