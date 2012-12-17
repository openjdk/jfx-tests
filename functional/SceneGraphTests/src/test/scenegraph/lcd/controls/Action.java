/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.controls;

import javafx.scene.Parent;
import javafx.scene.control.Control;

/**
 *
 * @author Alexander Petrov
 */
public interface Action {
    Parent updateControl(Parent node);
    boolean isLCDWork();
}
