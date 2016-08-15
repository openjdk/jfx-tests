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
package javafx.scene.control.test.treetable;

import client.test.Smoke;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.tableview.ApplicationInteractionFunctions;
import javafx.scene.control.test.treeview.TreeViewTest;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static javafx.scene.control.test.treetable.TreeTableNewApp.*;

/**
 * @author Alexander Kirov
 */
public class TreeTableAsTreeTest extends TreeViewTest {

    static {
        isTreeTests = false;
    }

    @Before
    public void treeTablePreparation() {
        ApplicationInteractionFunctions.addColumn(TREE_DATA_COLUMN_NAME, 0, true);
        selectionHelper.setPageHeight(7);
    }

    /**
     * Checks that when the sorting is applied to the underlying data collection
     * the cells are rendered in the right order.
     */
    @Smoke
    @Test(timeout = 30000)
    @Override
    public void renderingAfterSortingTest() throws Throwable {
        adjustControl();

        final int ITEMS_COUNT = 7;

        StringConverter<TreeItem<DataItem>> conv = new StringConverter<TreeItem<DataItem>>() {
            @Override
            public String toString(TreeItem<DataItem> t) {
                return t.getValue().getTreeValue().get();
            }

            @Override
            public TreeItem<DataItem> fromString(String s) {
                return new TreeItem<DataItem>(new DataItem(s));
            }
        };

        SortValidator<TreeItem<DataItem>, TreeTableCell> validator = new SortValidator<TreeItem<DataItem>, TreeTableCell>(ITEMS_COUNT, conv, DEFAULT_TREE_ITEM_COMPARATOR) {
            @Override
            protected void setControlData(final ObservableList<TreeItem<DataItem>> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        TreeTableView tv = (TreeTableView) testedControl.getControl();
                        tv.getRoot().getChildren().setAll(ls);
                    }
                }.dispatch(testedControl.getEnvironment());
            }

            @Override
            protected Lookup<? extends TreeTableCell> getCellsLookup() {
                return testedControl.as(Parent.class, Node.class).lookup(TreeTableCell.class, new LookupCriteria<TreeTableCell>() {
                    public boolean check(TreeTableCell cell) {
                        return !"ROOT".equals(cell.getText()) && cell.isVisible();
                    }
                });
            }

            @Override
            protected String getTextFromCell(TreeTableCell cell) {
                return cell.getText();
            }

            @Override
            protected void sort() {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        TreeTableView tv = (TreeTableView) testedControl.getControl();
                        FXCollections.sort(tv.getRoot().getChildren(), DEFAULT_TREE_ITEM_COMPARATOR);
                    }
                }.dispatch(testedControl.getEnvironment());
            }
        };

        boolean result = validator.check();
        String msg = validator.getFailureReason();
        assertTrue(msg, result);
    }
}