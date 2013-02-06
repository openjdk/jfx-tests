/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.actions;

import javafx.scene.effect.Effect;
import javafx.scene.text.Text;
import test.scenegraph.lcd.TestAction;

/**
 *
 * @author Alexander Petrov
 */
public class EffectTestAction implements TestAction{   
    private Effect effect;
    private boolean lcdWork;

    public EffectTestAction() {
        this.effect = null;
        this.lcdWork = true;
    }

    public EffectTestAction(Effect effect, boolean lcdWork) {
        this.effect = effect;
        this.lcdWork = lcdWork;
    }

    public void updateNode(Text text) {
        text.setEffect(effect);
    }

    public boolean isLCDWork() {
        return lcdWork;
    }
    
    public static TestAction[] generate(Effect effect, boolean isLCDWork){
        return new TestAction[] {
            new EffectTestAction(),
            new EffectTestAction(effect, isLCDWork),
            new EffectTestAction()
        };
    }

    @Override
    public String toString() {
        return "effect=" + (effect != null ? effect.getClass().getSimpleName() : "no") ;
    }
    
    
    
}
