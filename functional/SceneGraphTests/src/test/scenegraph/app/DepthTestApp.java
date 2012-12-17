/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import javafx.scene.Scene;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;


/**
 *
 * @author Aleksandr Sakharuk
 */
public class DepthTestApp extends InteroperabilityApp
{
    
    public static void main(String... args) 
    {
        Utils.launch(DepthTestApp.class, args);
    }

    @Override
    protected Scene getScene() 
    {
        return new DepthTestScene();
    }
       
}
