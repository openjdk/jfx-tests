/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import org.junit.Before;
import org.junit.BeforeClass;
import test.scenegraph.app.ControlEventsApp;
import test.scenegraph.app.ControlEventsApp.Controls;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class PasswordFieldEventsTest extends EventTestTextInput
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
        setControl(Controls.PASSWORD_FIELD);
    }
    
}
