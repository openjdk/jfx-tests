/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.actions;

import javafx.scene.text.Text;
import test.scenegraph.lcd.TestAction;

/**
 *
 * @author Alexander Petrov
 */
public class RotateTestAction  implements TestAction{
    private double angle;

    public RotateTestAction(double angle) {
        this.angle = angle;
    }
    
    

    public void updateNode(Text text) {
        text.setRotate(angle);
    }

    public boolean isLCDWork() {
        return true;
    }
    
    public static TestAction[] generate(double[] angles) {
        if (angles == null) {
            return null;
        }

        TestAction[] value = new TestAction[angles.length];

        for (int i = 0; i < angles.length; i++) {
            value[i] = new RotateTestAction(angles[i]);
        }

        return value;
    }

    @Override
    public String toString() {
        return "angle=" + angle;
    }
    
}
