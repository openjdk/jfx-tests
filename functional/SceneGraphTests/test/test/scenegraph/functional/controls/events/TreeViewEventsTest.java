/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.functional.controls.events;

import javafx.scene.control.TreeItem;
import javafx.scene.text.Text;
import org.jemmy.fx.TextDock;
import org.jemmy.fx.control.ListViewDock;
import org.jemmy.fx.control.TreeViewDock;
import org.jemmy.interfaces.Caret;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.lookup.LookupCriteria;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import test.scenegraph.app.ControlEventsApp;
import test.scenegraph.app.ControlEventsApp.Controls;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class TreeViewEventsTest extends EventTestCommon<TreeViewDock>
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
        setControl(Controls.TREE_VIEW);
    }
    
    @After
    public void collapseRootNode()
    {
        getPrimeNodeDock().getRootItem().setExpanded(true);
    }
    
    @Override
    protected TreeViewDock findPrimeDock()
    {
        return new TreeViewDock(getActiveTabDock().asParent(), 
                ControlEventsApp.CONTROL_ID);
    }
    
    @Test
    public void onEditStart()
    {
        test(ControlEventsApp.EventTypes.TREE_VIEW_EDIT_START_EVENT, new Command() {

            public void invoke() {
                TreeViewDock tvd = getPrimeNodeDock();
                tvd.selector().select((TreeItem) tvd.getRootItem().getChildren().get(0));
                tvd.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
                tvd.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);
            }
        });
    }
    
    @Test
    public void onEditCommit()
    {
        test(ControlEventsApp.EventTypes.TREE_VIEW_EDIT_COMMIT_EVENT, new Command() {

            public void invoke() {
                TreeViewDock twd = getPrimeNodeDock();
                final TreeItem item = (TreeItem) twd.getRootItem().getChildren().get(1);
                twd.selector().select(item);
                twd.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
                TextDock cellEditorDock = new TextDock(twd.asParent(), new LookupCriteria<Text>() {

                    public boolean check(Text cntrl) {
                        return cntrl.getText().equals(item.getValue().toString());
                    }
                });
                cellEditorDock.keyboard().typeChar('c');
                cellEditorDock.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
            }
        });
    }
    
    @Test
    public void onEditCancel()
    {
        test(ControlEventsApp.EventTypes.TREE_VIEW_EDIT_CANCEL_EVENT, new Command() {

            public void invoke() {
                TreeViewDock twd = getPrimeNodeDock();
                final TreeItem item = (TreeItem) twd.getRootItem().getChildren().get(2);
                twd.selector().select(item);
                twd.keyboard().pushKey(Keyboard.KeyboardButtons.ENTER);
                TextDock cellEditorDock = new TextDock(twd.asParent(), new LookupCriteria<Text>() {

                    public boolean check(Text cntrl) {
                        return cntrl.getText().equals(item.getValue().toString());
                    }
                });
                cellEditorDock.keyboard().typeChar('c');
                cellEditorDock.keyboard().pushKey(Keyboard.KeyboardButtons.ESCAPE);
            }
        });
    }
    
}
