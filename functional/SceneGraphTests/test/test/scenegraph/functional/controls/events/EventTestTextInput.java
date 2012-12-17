/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Keyboard;
import org.junit.Test;
import test.scenegraph.app.ControlEventsApp;

/**
 *
 * @author Aleksandr Sakharuk
 */
public abstract class EventTestTextInput extends EventTestCommon<TextInputControlDock> 
{
    
    @Override
    protected TextInputControlDock findPrimeDock()
    {
        return new TextInputControlDock(getActiveTabDock().asParent(), 
                ControlEventsApp.CONTROL_ID);
    }
    
    @Override
    @Test(timeout = 30000)
    public void onAction()
    {
        test(ControlEventsApp.EventTypes.ACTION, new Command() {

            public void invoke() {
                getPrimeNodeDock().mouse().click();
                getPrimeNodeDock().keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onInputMethodTextChanged()
    {
        test(ControlEventsApp.EventTypes.INPUT_METHOD_TEXT_CHANGED, new Command() {

            public void invoke() {
                getPrimeNodeDock().mouse().click();
                getPrimeNodeDock().keyboard().typeChar('i');
                new TextInputControlDock(getActiveTabDock().asParent(), 
                        ControlEventsApp.DRAG_FIELD_ID).mouse().click();
            }
        });
        
    }
    
}
