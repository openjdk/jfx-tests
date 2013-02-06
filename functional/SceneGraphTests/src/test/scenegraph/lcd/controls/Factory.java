/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.controls;

import javafx.scene.Parent;

/**
 *
 * @author Alexander Petrov
 */
public interface Factory {
    Parent createControl(boolean lcd);
}
