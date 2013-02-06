/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional;

import javafx.geometry.Point3D;
import javafx.scene.transform.Scale;
import org.jemmy.Point;
import org.junit.BeforeClass;
import test.scenegraph.app.Transforms3DApp;
import static java.lang.Math.*;
import javafx.scene.transform.*;
import org.junit.Before;
import test.scenegraph.app.TransformsApp;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class Transform3DTest {} /*extends TransformTest
{
    
    {
        setScale(new Scale(.33f, .66f, .45f, 100, 100, -100));
//        setRotate(new Rotate(45f, new Point3D(100, 100, 0)));
//        setShear(new Shear(-.33f, .1f, 80, 70));
//        setTranslate(new Translate(50, 50, 100));
//        setAffine(Transform.affine(1, -0.15, 0, 50, 0, 0.95, 0, 0, -1, 1.05, 0.95, 0));
    }
    
    //@RunUI
    @BeforeClass
    public static void RunUI() {
        Transforms3DApp.main(null);
    }
    
    @Override
    @Before
    public void before()
    {
        super.before();
        CAMERA_X = scene.getControl().getWidth() / 2;
        CAMERA_Y = scene.getControl().getHeight() / 2;
    }
    
    @Override
    protected Point scale(final Point p)
    {
        Point sup = super.scale(p);
        double z = getScale().getPivotZ() + (0 - getScale().getPivotZ())*getScale().getZ();
        sup.x = (int) ((sup.x - 2 * CAMERA_X) * 217.39 / z);
        return sup;
    }
    
    @Override
    protected Point rotate(final Point p) // Modify
    {
        Point sup = super.rotate(p);
        return sup;
    }
    
    @Override
    protected Point shear(final Point p)
    {
        Point sup = super.shear(p);
        return sup;
    }

    @Override
    protected Point translate(final Point p) // Modify
    {
        Point sup = super.translate(p);
        return sup;
    }

    @Override
    protected Point affine(final Point p) // Modify
    {
        Point sup = super.affine(p);
        return sup;
    }
    
    private double CAMERA_X;
    private double CAMERA_Y;
    private double CAMERA_Z = 0;
    
}
*/