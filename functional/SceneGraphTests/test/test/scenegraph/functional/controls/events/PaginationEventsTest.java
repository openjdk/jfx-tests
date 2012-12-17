/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import java.util.Arrays;
import javafx.scene.control.Labeled;
import org.jemmy.fx.control.ControlDock;
import org.jemmy.fx.control.LabeledDock;
import org.jemmy.lookup.LookupCriteria;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.ControlEventsApp;
import test.scenegraph.app.ControlEventsApp.Controls;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class PaginationEventsTest extends EventTestCommon<ControlDock>
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
        setControl(Controls.PAGINATION);
    }
    
    @Override
    protected ControlDock findPrimeDock()
    {
        return new ControlDock(getActiveTabDock().asParent(), 
                ControlEventsApp.CONTROL_ID);
    }
    
    /**
     * Finds right arrow button inside pagination and click it to produce an
     * action event.
     */
    @Override
    @Test(timeout = 30000)
    public void onAction()
    {
        test(ControlEventsApp.EventTypes.ACTION, new Command() {

            public void invoke() {
                LabeledDock rightButtonDock = new LabeledDock(
                        getPrimeNodeDock().asParent(), new LookupCriteria<Labeled>() {

                    public boolean check(Labeled cntrl) {
                        if(cntrl.getStyleClass().containsAll(Arrays.asList(
                            "button", "right-arrow-button")))
                            return true;
                        return false;
                    }
                });
                rightButtonDock.mouse().click();
            }
        });
    }
    
}
