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
package javafx.scene.control.test.cell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.test.cellapps.CellsApp;
import javafx.scene.control.test.cellapps.CellsApp.DataItem;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.LookupCriteria;
import org.junit.Before;
import org.junit.BeforeClass;

public class ListCellsTest extends CellsTestBase {

    @BeforeClass
    public static void setUpClass() throws Exception {
        CellsApp.main(null);
    }
    protected static Wrap<? extends Control> listView;

    @Before
    public void listSetUpClass() throws Exception {
        updateWraps();
        testedControl = parent.lookup(ListView.class, new ByID<ListView>(CellsApp.LISTVIEW_ID)).wrap();
        cellID = CellsApp.LIST_EDIT_ID;
        choiceID = CellsApp.LIST_FACTORY_CHOICE_ID;
    }

    @Override
    protected Wrap select(final String data) {
        Wrap wrap = testedControl.as(Parent.class, DataItem.class).lookup(
                new LookupCriteria<DataItem>() {
                    @Override
                    public boolean check(DataItem cell_item) {
                        return cell_item.toString().contains(data);
                    }
                }).wrap();
        wrap.mouse().click();
        return wrap;
    }

    @Override
    protected ObservableList<DataItem> getCurrentProgramValues() {
        return FXCollections.observableArrayList(testedControl.as(Selectable.class).getStates());
    }
}