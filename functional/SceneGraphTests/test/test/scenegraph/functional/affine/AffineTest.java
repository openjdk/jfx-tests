/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.affine;

import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.CheckBoxDock;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.timing.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.TestBase;
import test.scenegraph.app.AffineApp;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class AffineTest extends TestBase
{
    
    @BeforeClass
    public static void runUI()
    {
        AffineApp.main(null);
    }
    
    @Before
    @Override
    public void before()
    {
        super.before();
        
        sceneDock = new SceneDock(scene);
        fxArea = new NodeDock(sceneDock.asParent(), AffineApp.FX_AFFINE_AREA);
        manualArea = new NodeDock(sceneDock.asParent(), AffineApp.MANUAL_AFFINE_AREA);
    }
    
    @Test
    public void append6d()
    {
        check(AffineApp.AffineAPI.APPEND_6D.name());
    }
    
    @Test
    public void append12d()
    {
        check(AffineApp.AffineAPI.APPEND_12D.name());
    }
    
    @Test
    public void appendTransform()
    {
        check(AffineApp.AffineAPI.APPEND_TRANSFORM.name());
    }
    
    @Test
    public void appendMatrix()
    {
        check(AffineApp.AffineAPI.APPEND_D_ARR_MATRIX_TYPE_INT.name());
    }
    
    @Test
    public void appendRotateD()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_D.name());
    }
    
    @Test
    public void appendRotate3D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_3D.name());
    }
    
    @Test
    public void appendRotateDPoint2D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_D_POINT2D.name());
    }
    
    @Test
    public void appendRotate7D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_7D.name());
    }
    
    @Test
    public void appendRotate4DPoint3D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_4D_POINT3D.name());
    }
    
    @Test
    public void appendRotateD2Point3D()
    {
        check(AffineApp.AffineAPI.APPEND_ROTATE_D_2POINT3D.name());
    }
    
    private void check(String checkBoxId)
    {
        CheckBoxDock checkBoxDock = new CheckBoxDock(sceneDock.asParent(), checkBoxId);
        checkBoxDock.selector().select(CheckBoxWrap.State.CHECKED);
        try
        {
            scene.waitState(new State<Boolean>() {

                public Boolean reached() {
                    return fxArea.wrap().getScreenImage().compareTo(manualArea.wrap().getScreenImage()) == null;
                }
            }, true);
        }
        finally
        {
            checkBoxDock.selector().select(CheckBoxWrap.State.UNCHECKED);
        }
    }
    
    private SceneDock sceneDock;
    private NodeDock fxArea, manualArea;
    
}
