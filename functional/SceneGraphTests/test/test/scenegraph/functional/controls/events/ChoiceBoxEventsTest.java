/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import org.jemmy.fx.control.ChoiceBoxDock;
import org.jemmy.interfaces.Selectable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.ControlEventsApp;
import test.scenegraph.app.ControlEventsApp.Controls;
import test.scenegraph.app.ControlEventsApp.EventTypes;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class ChoiceBoxEventsTest extends EventTestHidingPopup<ChoiceBoxDock> 
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
        setControl(Controls.CHOICE_BOX);
    }
    
    @Override
    protected ChoiceBoxDock findPrimeDock()
    {
        return new ChoiceBoxDock(getActiveTabDock().asParent(), 
                ControlEventsApp.CONTROL_ID);
    }
    
    @Override
    @Test(timeout = 30000)
    public void onAction()
    {
        test(EventTypes.ACTION, new Command() {

            public void invoke() {
                Selectable<String> s = getPrimeNodeDock().asSelectable(String.class);
                for(String state: s.getStates())
                {
                    if(!state.equals(s.getState()))
                    {
                        getPrimeNodeDock().asSelectable(String.class).
                                selector().select(state);
                        break;
                    }
                }
            }
        });
    }
    
}
