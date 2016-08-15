/*
 * Copyright (c) 2012-2013, Oracle and/or its affiliates. All rights reserved.
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
package org.jemmy.samples.treetableview;

import java.util.Date;
import java.util.List;
import org.jemmy.fx.SceneDock;
import org.jemmy.Point;
import org.jemmy.fx.control.TextFieldCellEditor;
import org.jemmy.fx.control.TreeTableCellDock;
import org.jemmy.fx.control.TreeTableViewDock;
import org.jemmy.fx.control.TreeTableItemDock;
import org.jemmy.interfaces.EditableCellOwner.EditableCell;
import org.jemmy.lookup.LookupCriteria;
import static org.jemmy.resources.StringComparePolicy.*;
import org.jemmy.samples.SampleBase;
import org.jemmy.samples.treetableview.TreeTableViewApp.Person;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TreeTableViewSample extends SampleBase {

    private static SceneDock scene;
    private static TreeTableViewDock treeTableView;

    @BeforeClass
    public static void launch() throws InterruptedException {
        startApp(TreeTableViewApp.class);
        scene = new SceneDock();

        /**
         * Looking up for TreeTableView control. The best option is to do that
         * by id.
         */
        treeTableView = new TreeTableViewDock(scene.asParent(),
                TreeTableViewApp.TREE_TABLE_VIEW_ID);
    }

    /**
     * TreeTableView lookup is no different than other components - check the
     * lookup samples, table item lookup is another matter.
     *
     * You can lookup cells by content, treeItems or data items (objects/data
     * inside the tree item), directly, or by lookup criteria. You will need to
     * use appropriate implementation of Parent interface over data or its
     * representation in cells or treeItems.
     *
     * Explore different possible lookup possibilities via API. There are more
     * than 15 possible ways to specify cell for lookup, and more than 20 ways
     * to specify treeItem for lookup.
     */
    @Test
    public void itemLookup() {
        //Cell lookups are similar to lookups for TableView.
        //You could find a cell by it's content.
        new TreeTableCellDock(treeTableView.asTable(), "0-1-lastname").mouse().click();
        //By coordinates.
        new TreeTableCellDock(treeTableView.asTable(), 1, 1).mouse().click();
        //By toString() of the object.
        new TreeTableCellDock(treeTableView.asTable(),
                "0-2-first", SUBSTRING).mouse().click();
        //Or any other way by creating a custom lookup criteria.
        new TreeTableCellDock(treeTableView.asTable(), new LookupCriteria<Object>() {
            @Override
            public boolean check(Object cntrl) {
                return cntrl instanceof Date;
            }
        }).mouse().click();
        //TreeItem lookups are similar to lookups for TreeView.
        //Lookup treeItem, for instance, by the first column's content (only in visible part of TreeTableView).
        new TreeTableItemDock(treeTableView.asTreeItemParent(), "0-0", EXACT).mouse().click();
        //Lookup by data item, which is stored in treeItem.
        new TreeTableCellDock(treeTableView.asTableItemParent(),
                treeTableView.wrap().getControl().getRoot().getValue()).mouse().click();
        //Lookup by text of the first column. Ir works only if according treeItem is visible.
        new TreeTableItemDock(treeTableView.asTreeItemParent(), treeTableView.wrap(), "0-0-1").mouse().click();
    }

    /**
     * How to expand and collapse a treeItem.
     */
    @Test
    public void expandingCollapsing() throws InterruptedException {
        //collapse
        new TreeTableItemDock(treeTableView.asTreeItemParent(), "0-2-0", EXACT).collapse();
        new TreeTableItemDock(treeTableView.asTreeItemParent(), "0-2", EXACT).collapse();
        new TreeTableItemDock(treeTableView.asTreeItemParent(), "0-3-3", EXACT).collapse();
        //expand happens automatically
        new TreeTableItemDock(treeTableView.asTreeItemParent(), "0-2-0-2", EXACT).mouse().click();
        //but there's, of course, an expand:
        new TreeTableItemDock(treeTableView.asTreeItemParent(), "0-3-3", EXACT).expand();
    }

    /**
     * Selecting a cell by column and row indexes.
     */
    @Test
    public void selectCellByColumnAndRow() {
        new TreeTableCellDock(treeTableView.asTable(), 2, 2).asEditableCell().select();
        //or
        treeTableView.asTable().select(new Point(3, 3));
    }

    /**
     * Scrolling happens automatically when you are trying to select a cell.
     * Should you need the scrolling anyway just call show()
     */
    @Test
    public void scrollToCell() {
        new TreeTableCellDock(treeTableView.asTable(), "0-3-3-3-lastname").shower().show();
        new TreeTableCellDock(treeTableView.asTable(), "0-0-0").shower().show();
        new TreeTableItemDock(treeTableView.asTreeItemParent(), EXACT, "0", "0-3", "0-3-2").shower().show();
    }

    /**
     * Getting information on the table size
     */
    @Test
    public void getTableSize() {
        System.out.println("tableView has " + treeTableView.getColumns().size()
                + " columns and " + treeTableView.getItems().size() + " rows.");
    }

    /**
     * Selecting multiple cells.
     */
    @Test
    public void selectMultipleCells() {
        treeTableView.asTable().select(new Point(1, 1), new Point(2, 2), new Point(3, 3));
    }

    /**
     * Editing cell value. Before the editing, you need to specify the editor.
     */
    @Test
    public void editCell() {
        treeTableView.asTreeItemParent(Person.class).setEditor(new TextFieldCellEditor<>());
        treeTableView.asTreeItemParent(Person.class).lookup(new LookupCriteria<Person>() {
            @Override
            public boolean check(Person cntrl) {
                return cntrl.firstNameProperty().getValue().equals("0-0-0-2-firstname");
            }
        }).wrap().as(EditableCell.class).edit("Kirov A.");

        treeTableView.asTable().setEditor(new TextFieldCellEditor<>());
        new TreeTableCellDock(treeTableView.asTable(), 2, 2).asEditableCell().edit("Kirov");
    }

    /**
     * Sorting by field.
     */
    @Test
    public void sort() {
        // No direct API for that http://javafx-jira.kenai.com/browse/JMY-170
    }

    /**
     * Getting selected cells
     */
    @Test
    public void getSelectedCells() {
        new TreeTableCellDock(treeTableView.asTable(), "0-2-first",
                SUBSTRING).asEditableCell().select();
        List<Point> selection = treeTableView.getSelectedCells();
        System.out.println("Selected indices are = " + selection);
        System.out.println("While actual are = "
                + treeTableView.control().getSelectionModel().getSelectedIndices());
    }

    /**
     * How to scroll using Scrollable2D interface.
     */
    @Test
    public void scrollingViaScrollable2D() {
        treeTableView.asScrollable2D().asVerticalScroll().to(0.9);
    }
}