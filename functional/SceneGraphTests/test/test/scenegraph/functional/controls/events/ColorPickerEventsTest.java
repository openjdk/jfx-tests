/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import javafx.geometry.Bounds;
import org.jemmy.Point;
import org.jemmy.fx.control.ControlDock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.ControlEventsApp;
import test.scenegraph.app.ControlEventsApp.Controls;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ColorPickerEventsTest extends EventTestHidingPopup<ControlDock>
{
    
    @BeforeClass
    public static void rinUI()
    {
        ControlEventsApp.main(null);
    }
    
    @Override
    @Before
    public void before()
    {
        super.before();
        setControl(Controls.COLOR_PICKER);
    }
    
    @Override
    protected ControlDock findPrimeDock()
    {
        return new ControlDock(getActiveTabDock().asParent(), 
                ControlEventsApp.CONTROL_ID);
    }
    
    @Override
    @Test(timeout = 30000)
    public void onAction()
    {
        test(ControlEventsApp.EventTypes.ACTION, new Command() {

            public void invoke() {
                getPrimeNodeDock().mouse().click();
                Bounds bounds = getPrimeNodeDock().getBoundsInLocal();
                double x = bounds.getWidth() / 2;
                double y = bounds.getHeight() * 2;
                getPrimeNodeDock().mouse().click(1, new Point(x, y));
            }
        });
    }
    
}
