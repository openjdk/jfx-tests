/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import javafx.scene.text.Text;
import org.jemmy.fx.TextDock;
import org.jemmy.fx.control.ListViewDock;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.Keyboard;
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
public class ListViewEventsTest extends EventTestCommon<ListViewDock>
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
        setControl(Controls.LIST_VIEW);
    }
    
    @Override
    protected ListViewDock findPrimeDock()
    {
        return new ListViewDock(getActiveTabDock().asParent(), 
                ControlEventsApp.CONTROL_ID);
    }
    
    @Test(timeout = 30000)
    public void onEditStart()
    {
        test(ControlEventsApp.EventTypes.LIST_VIEW_EDIT_START_EVENT, new Command() {

            public void invoke() {
                ListViewDock lwd = getPrimeNodeDock();
                lwd.selector().select(lwd.getItems().get(0));
                lwd.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
                lwd.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onEditCommit()
    {
        test(ControlEventsApp.EventTypes.LIST_VIEW_EDIT_COMMIT_EVENT, new Command() {

            public void invoke() {
                ListViewDock lwd = getPrimeNodeDock();
                final Object item = lwd.getItems().get(1);
                lwd.selector().select(item);
                lwd.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
                TextDock cellEditorDock = new TextDock(lwd.asParent(), new LookupCriteria<Text>() {

                    public boolean check(Text cntrl) {
                        return cntrl.getText().equals(item.toString());
                    }
                });
                cellEditorDock.keyboard().typeChar('c');
                cellEditorDock.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
            }
        });
    }
    
    @Test(timeout = 30000)
    public void onEditCancel()
    {
        test(ControlEventsApp.EventTypes.LIST_VIEW_EDIT_CANCEL_EVENT, new Command() {

            public void invoke() {
                ListViewDock lwd = getPrimeNodeDock();
                final Object item = lwd.getItems().get(2);
                lwd.selector().select(item);
                lwd.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
                TextDock cellEditorDock = new TextDock(lwd.asParent(), new LookupCriteria<Text>() {

                    public boolean check(Text cntrl) {
                        return cntrl.getText().equals(item.toString());
                    }
                });
                cellEditorDock.keyboard().typeChar('c');
                cellEditorDock.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);
            }
        });
    }
    
}
