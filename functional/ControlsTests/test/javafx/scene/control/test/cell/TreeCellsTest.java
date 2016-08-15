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

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.test.cellapps.CellsApp;
import javafx.scene.control.test.cellapps.CellsApp.DataItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.CriteriaList;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Tree;
import org.jemmy.lookup.LookupCriteria;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class TreeCellsTest extends CellsTestBase {

    @BeforeClass
    public static void setUpClass() throws Exception {
        CellsApp.main(null);
    }
    protected static Tree<DataItem> treeAsTreeSelector;

    @Before
    public void treeSetUpClass() throws Exception {
        updateWraps();
        testedControl = parent.lookup(TreeView.class, new ByID<TreeView>(CellsApp.TREEVIEW_ID)).wrap();
        treeAsTreeSelector = testedControl.as(Tree.class, String.class);
        cellID = CellsApp.TREE_EDIT_ID;
        choiceID = CellsApp.TREE_FACTORY_CHOICE_ID;

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((TreeView) testedControl.getControl()).getRoot().setExpanded(true);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected Wrap select(final String data) {
        Wrap<? extends DataItem> wrap = treeAsTreeSelector.selector().select(new LookupCriteria<DataItem>() {
            public boolean check(DataItem dataItem) {
                return dataItem.toString().contains(data);
            }
        });
        return wrap;
    }

    protected class StringCriteriaList extends CriteriaList<DataItem> {

        public StringCriteriaList(String... strings) {
            list = new ArrayList<DataItem>();
            for (String str : strings) {
                list.add(new DataItem(str));
            }
        }
    }

    @Override
    protected ObservableList<DataItem> getCurrentProgramValues() {
        ObservableList<DataItem> items = FXCollections.observableArrayList();
        List<TreeItem> temp = testedControl.as(Selectable.class).getStates();
        for (TreeItem item : temp) {
            items.add((DataItem) item.getValue());
        }
        return items;
    }
}