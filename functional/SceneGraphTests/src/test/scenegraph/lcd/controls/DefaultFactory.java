/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.controls;

import javafx.scene.Parent;
import org.jemmy.fx.NodeDock;

/**
 *
 * @author Alexander Petrov
 */
public abstract class DefaultFactory implements Factory{

    public Parent createControl(boolean lcd) {
        Parent value = createControl();
        
        value.getStylesheets().add(DefaultFactory.class.getResource(lcd ? "lcd.css":"gray.css").toExternalForm());
        
        return value;
    }
    
    public abstract Parent createControl(); 
}
