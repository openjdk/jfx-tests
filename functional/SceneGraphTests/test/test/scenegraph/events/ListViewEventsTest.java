/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 */
package test.scenegraph.events;

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

    @Test(timeout = 60000)
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

    @Test(timeout = 60000)
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

    @Test(timeout = 60000)
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
