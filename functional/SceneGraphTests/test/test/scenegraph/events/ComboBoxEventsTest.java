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

import org.jemmy.fx.control.ComboBoxDock;
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
public class ComboBoxEventsTest extends EventTestHidingPopup<ComboBoxDock>
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
        setControl(Controls.COMBO_BOX);
    }

    @Override
    protected ComboBoxDock findPrimeDock()
    {
        return new ComboBoxDock(getActiveTabDock().asParent(),
                ControlEventsApp.CONTROL_ID);
    }

    @Override
    @Test(timeout = 60000)
    public void onAction()
    {
        test(EventTypes.ACTION, new Command() {

            public void invoke() {
                Selectable<String> s = getPrimeNodeDock().asSelectable(String.class);
                for(String state: s.getStates())
                {
                    if(!state.equals(s.getState()))
                    {
                        s.selector().select(state);
                        break;
                    }
                }
            }
        });
    }

}
