/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd;

import javafx.scene.text.Text;

/**
 *
 * @author Alexander Petrov
 */
public enum TestActions implements TestAction {

    ;
        
    private TestAction testAction;

    private TestActions(TestAction testAction) {
        this.testAction = testAction;
    }

    public void updateNode(Text text) {
        testAction.updateNode(text);
    }

    public boolean isLCDWork() {
        return testAction.isLCDWork();
    }
}
