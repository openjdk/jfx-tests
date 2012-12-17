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
public interface TestAction {
    void updateNode(Text text);
    boolean isLCDWork();
}
