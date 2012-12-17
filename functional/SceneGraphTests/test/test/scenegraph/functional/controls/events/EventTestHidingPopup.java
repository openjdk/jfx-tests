/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import javafx.geometry.Bounds;
import org.jemmy.Point;
import org.jemmy.fx.NodeDock;
import org.junit.After;

/**
 *
 * @author Aleksand Sakharuk
 */
public abstract class EventTestHidingPopup<T extends NodeDock> extends EventTestCommon<T> 
{
    
    @After
    public void hidePopup()
    {
        if(getPrimeNodeDock() != null)
        {
            Bounds bounds = getPrimeNodeDock().getBoundsInLocal();
            double x = bounds.getWidth() * 3 / 2;
            double y = bounds.getHeight() / 2;
            getPrimeNodeDock().mouse().click(1, new Point(x, y));
        }
    }
    
}
