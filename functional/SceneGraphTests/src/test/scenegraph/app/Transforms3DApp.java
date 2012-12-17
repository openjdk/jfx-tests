/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.app;

import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.*;
import test.javaclient.shared.Utils;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class Transforms3DApp extends TransformsApp
{
    
    {
        Affine aff = Transform.affine(1, -0.15, 0, 50, 0, 0.95, 0, 0, -1, 1.05, 0.95, 0);
        TransformsApp.TransformToggle[] tt = {
            new TransformsApp.TransformToggle("scale", new Scale(.33f, .66f, .45f, 100, 100, -100)),
            new TransformsApp.TransformToggle("rotate", new Rotate(45f, new Point3D(100, 100, 0))),
            new TransformsApp.TransformToggle("shear", new Shear(-.33f, .1f, 80, 70)),
            new TransformsApp.TransformToggle("translate", new Translate(50, 50, 100)),
            new TransformsApp.TransformToggle("affine", aff)
        };
        setTransformToggle(tt);
    }
    
    @Override
    protected Scene getScene() 
    {
        Scene zScene = super.getScene();
        zScene.getRoot().setDepthTest(DepthTest.ENABLE);
        zScene.setCamera(new PerspectiveCamera());
        //System.out.println("Camera angle " + new PerspectiveCamera().getFieldOfView());
        return zScene;
    }
    
    public static void main(String[] args) {
        Utils.launch(Transforms3DApp.class, args);
    }
    
}
