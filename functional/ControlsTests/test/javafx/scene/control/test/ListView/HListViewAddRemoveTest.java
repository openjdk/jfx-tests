/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * questions.
 */

package javafx.scene.control.test.ListView;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 *
 * @author shura
 */
@RunWith(FilteredTestRunner.class)
public class HListViewAddRemoveTest extends ListViewAddRemoveTest {

    public HListViewAddRemoveTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ListViewTestBase.startApp(true);
    }

    protected void extraTestingForLongItem() {
        list.as(Selectable.class).selector().select("string");
        //check that right side of the long item is within the listview
        final Wrap<? extends ListCell> longCell = list.as(Parent.class, Node.class).
                lookup(ListCell.class, new LookupCriteria<ListCell>() {

            public boolean check(ListCell cntrl) {
                return cntrl.getItem() != null &&
                        cntrl.getItem().toString().contains(ListViewApp.createLongItem(0));
            }
        }).wrap();
        longCell.waitState(new State() {

            public Object reached() {
                Rectangle bounds = longCell.getScreenBounds();
                Rectangle listBounds = list.getScreenBounds();
                return ((bounds.x + bounds.width) >= listBounds.x) &&
                        ((bounds.x + bounds.width) <= (listBounds.x+ listBounds.width)) ?
                            true : null;
            }
        });
    }
}