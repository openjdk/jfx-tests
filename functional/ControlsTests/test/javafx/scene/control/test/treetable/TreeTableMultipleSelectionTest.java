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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import static javafx.commons.Consts.*;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.tableview.TableViewMultipleCellSelectionTest;
import javafx.scene.control.test.tableview.TestBaseCommon;
import static javafx.scene.control.test.treetable.TreeTableAsOldTableApp.*;
import javafx.scene.control.test.treetable.TreeTableAsOldTableApp.Data;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.control.TreeTableCellDock;
import org.jemmy.fx.control.TreeTableItemDock;
import org.jemmy.fx.control.TreeTableViewDock;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Dmitry Zinkevich
 */
public class TreeTableMultipleSelectionTest extends TableViewMultipleCellSelectionTest {

    final Timeout timeout = new Timeout("", 1000L);

    static {
        isTableTests = false;
    }

    /**
     * In the hierarchy of tree rows several cells are selected and then their
     * parent is collapsed. Test checks that only the parent is selected after
     * collapsing. see RT-27180 for details.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testCollapse() {
        multipleSelectionWrap.mouse().click();
        singleCellSelectionWrap.mouse().click();
        createTree(DATA_ITEMS_NUM, DATA_FIELDS_NUM);

        TreeTableViewDock treeTable = new TreeTableViewDock(new SceneDock().asParent(), TESTED_CONTROL_ID);

        final String itemText = "item 00-00-05 field 0";
        TreeTableCellDock cell = new TreeTableCellDock(treeTable.asTable(), itemText);

        treeTable.asTable().select(cell.getItem());
        for (int i = 0; i < 2; i++) {
            treeTable.keyboard().pushKey(
                    KeyboardButtons.DOWN,
                    KeyboardModifiers.SHIFT_DOWN_MASK);
            checkSelectedCellVisible();
        }

        final String parentText = "00-00";
        TreeTableItemDock parentItem = getTreeItemDock(treeTable, parentText);
        parentItem.collapse();

        //No items are expected to be selected, as selected items were inside the collapsed branch.
        Collection<Point> selectedCells = Utility.getSelectedCellsAsPoints(testedControl);
        System.out.println("selectedCells = " + selectedCells);
        Assert.assertEquals("No cell must be selected.", 0, selectedCells.size());

//        Point selectedCell = (Point) selectedCells.toArray()[0];
//
//        final String targetText = "item 00-00 field 0";
//        TreeTableCellDock targetCell = new TreeTableCellDock(
//                treeTable.asTable(), targetText);
//
//        Point expected = targetCell.getItem();
//        Assert.assertEquals("Wrong cell is selected.", expected, selectedCell);
    }

    /**
     * Tests behavior of selected cells when the tree item they belong to is
     * expanded. N.B. Point class hold cell position in a following manner: x
     * stands for column index and y stands for row index respectively.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testSingleLineExpantion() {
        multipleSelectionWrap.mouse().click();
        singleCellSelectionWrap.mouse().click();
        createTree(DATA_ITEMS_NUM, DATA_FIELDS_NUM);

        TreeTableViewDock treeTable = new TreeTableViewDock(new SceneDock().asParent(), TESTED_CONTROL_ID);

        final TreeTableItemDock treeItem = getTreeItemDock(treeTable, "00-00");
        treeItem.collapse();

        Wrap cell = getCell(treeTable, treeItem);
        cell.mouse().click();

        for (int i = 0; i < 3; i++) {
            treeItem.keyboard().pushKey(
                    KeyboardButtons.RIGHT,
                    KeyboardModifiers.SHIFT_DOWN_MASK);
            checkSelectedCellVisible();
        }

        Set<Point> selectedCellsBefore;
        Set<Point> selectedCellsAfter;

        selectedCellsBefore = Utility.getSelectedCellsAsPoints(testedControl);
        Assert.assertTrue(
                "Selected cells set cant be empty.",
                0 != selectedCellsBefore.size());

        Set<Point> expectedSet = new HashSet<Point>(4);
        for (int x = 0; x < 4; x++) {
            expectedSet.add(new Point(x, 2));
        }

        Assert.assertEquals(
                "Selected set doesnt match expected set",
                expectedSet,
                selectedCellsBefore);

        treeItem.expand();

        selectedCellsAfter = Utility.getSelectedCellsAsPoints(testedControl);
        Assert.assertEquals(
                "Selecion was not supposed to change by expansion.",
                selectedCellsBefore,
                selectedCellsAfter);
    }

    /**
     * Tests behavior of selected cells when they belong to several collapsed
     * tree items and each is expanded.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testMultipleExpantion() {
        multipleSelectionWrap.mouse().click();
        singleCellSelectionWrap.mouse().click();
        createTree(DATA_ITEMS_NUM, DATA_FIELDS_NUM);

        TreeTableViewDock treeTable = new TreeTableViewDock(new SceneDock().asParent(), TESTED_CONTROL_ID);

        final TreeTableItemDock first = getTreeItemDock(treeTable, "00-00");
        first.collapse();

        final TreeTableItemDock second = getTreeItemDock(treeTable, "00-01");
        second.collapse();

        Wrap cell = getCell(treeTable, first);
        cell.mouse().click();

        for (int i = 0; i < 3; i++) {
            first.keyboard().pushKey(
                    KeyboardButtons.RIGHT,
                    KeyboardModifiers.SHIFT_DOWN_MASK);
            checkSelectedCellVisible();
        }

        first.keyboard().pushKey(
                KeyboardButtons.DOWN,
                KeyboardModifiers.SHIFT_DOWN_MASK);

        for (int i = 0; i < 3; i++) {
            first.keyboard().pushKey(
                    KeyboardButtons.LEFT,
                    KeyboardModifiers.SHIFT_DOWN_MASK);
            checkSelectedCellVisible();
        }

        Set<Point> selectedCells = Utility.getSelectedCellsAsPoints(testedControl);
        Set<Point> expectedCells = new HashSet<Point>(8);

        for (int x = 0; x < 4; x++) {
            for (int y = 2; y < 4; y++) {
                expectedCells.add(new Point(x, y));
            }
        }

        Assert.assertEquals(
                "Selected set doesnt match expected set",
                expectedCells,
                selectedCells);

        Set<String> selectedContentBefore;
        Set<String> selectedContentAfter;

        selectedContentBefore = Utility.getSelectedContent(testedControl);
        Assert.assertTrue(
                "Selected cells set cant be empty.",
                0 != selectedContentBefore.size());

        first.expand();

        selectedContentAfter = Utility.getSelectedContent(testedControl);
        Assert.assertEquals(
                "Selecion was not supposed to change by expansion.",
                selectedContentBefore,
                selectedContentAfter);
    }

    /**
     * Tests selection behavior when the row which contains selected cells is
     * removed.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testSingleRowRemoving() {
        enableMultipleCellSelection();

        TreeTableViewDock treeTable;
        treeTable = new TreeTableViewDock(
                new SceneDock().asParent(),
                TESTED_CONTROL_ID);

        final String itemText = "item 05 field 0";
        TreeTableCellDock cell = new TreeTableCellDock(
                treeTable.asTable(), itemText);

        treeTable.asTable().select(cell.getItem());
        selectionHelper.click(0, 6);

        checkSelection();

        KeyboardButtons btn = KeyboardButtons.RIGHT;
        KeyboardModifiers shift = KeyboardModifiers.SHIFT_DOWN_MASK;
        for (int i = 0; i < 2; i++) {
            treeTable.keyboard().pushKey(btn, shift);
            selectionHelper.push(btn, shift);
            checkSelectedCellVisible();
        }

        checkSelection();

        Set<Point> initialSelection = Utility.getSelectedCellsAsPoints(testedControl);

        removeAt(5);
        Set<Point> finalSelection = Utility.getSelectedCellsAsPoints(testedControl);
        Assert.assertEquals("Selection size has changed.",
                initialSelection,
                finalSelection);

        removeAt(4);
        finalSelection = Utility.getSelectedCellsAsPoints(testedControl);
        Assert.assertEquals("Selection size has changed.",
                initialSelection,
                finalSelection);
    }

    /**
     * Tests selection behavior when the column which contains selected cells is
     * removed.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testSingleColumnRemoving() {
        enableMultipleCellSelection();

        TreeTableViewDock treeTable;
        treeTable = new TreeTableViewDock(
                new SceneDock().asParent(),
                TESTED_CONTROL_ID);
        scrollTo(1, 4);
        final String itemText = "item 03 field 1";
        TreeTableCellDock cell = new TreeTableCellDock(
                treeTable.asTable(), itemText);
        treeTable.asTable().select(cell.getItem());
        selectionHelper.click(1, 4);

        checkSelection();

        KeyboardButtons btn = KeyboardButtons.DOWN;
        KeyboardModifiers shift = KeyboardModifiers.SHIFT_DOWN_MASK;
        for (int i = 0; i < 2; i++) {
            treeTable.keyboard().pushKey(btn, shift);
            selectionHelper.push(btn, shift);
        }

        checkSelection();

        final Set<Point> initialSelection = Utility.getSelectedCellsAsPoints(testedControl);

        removeColumnAt(1);

        new Waiter(timeout).ensureValue(Boolean.TRUE, new State<Boolean>() {
            public Boolean reached() {
                final Set<Point> selection = Utility.getSelectedCellsAsPoints(testedControl);
                if (initialSelection.equals(selection)) {
                    return true;
                } else {
                    System.out.println("initialSelection : " + initialSelection);
                    System.out.println("selection : " + selection);
                    return false;
                }
            }
        });

        removeColumnAt(0);

        new Waiter(timeout).ensureValue(Boolean.TRUE, new State<Boolean>() {
            public Boolean reached() {
                final Set<Point> selection = Utility.getSelectedCellsAsPoints(testedControl);
                if (initialSelection.equals(selection)) {
                    return true;
                } else {
                    System.out.println("initialSelection : " + initialSelection);
                    System.out.println("selection : " + selection);
                    return false;
                }
            }
        });
    }

    private void createTree(int rows, int cols) {
        Wrap tfColumnsCount = parent
                .lookup(TextField.class, new ByID(Table.COLUMNS_NUMBER_ID))
                .wrap();
        setText(tfColumnsCount, cols);

        Wrap tfRowsCount = parent
                .lookup(TextField.class, new ByID(Table.ROWS_NUMBER_ID))
                .wrap();
        setText(tfRowsCount, rows);

        clickButtonForTestPurpose(FILL_TREE_ID);
    }

    static class Utility extends TestBaseCommon {

        static Set<Point> getSelectedCellsAsPoints(
                final Wrap<? extends Control> testedControl) {
            return getSelected(testedControl);
        }

        static int getCellRowIndex(TreeTableCell cell) {
            return getRowIndex(cell);
        }

        static int getCellColumnIndex(TreeTableCell cell) {
            return getColumnIndex(cell);
        }

        static Set<String> getSelectedContent(
                final Wrap<? extends Control> wrap) {

            final Set<Point> cells = getSelectedCellsAsPoints(wrap);

            return new GetAction<Set<String>>() {
                @Override
                public void run(Object... parameters) throws Exception {

                    if (!(wrap.getControl() instanceof TreeTableView)) {
                        setResult(Collections.<String>emptySet());
                    }

                    TreeTableView treeTable = (TreeTableView) wrap.getControl();

                    Set<String> content = new HashSet<String>(cells.size());

                    for (Iterator<Point> it = cells.iterator(); it.hasNext();) {
                        final Point pt = it.next();

                        Object obj = treeTable.getColumns().get(pt.x);
                        TreeTableColumn col = (TreeTableColumn) obj;

                        ObservableValue<String> val = col.getCellObservableValue(pt.y);
                        content.add(val.getValue());
                    }

                    setResult(content);
                }
            }.dispatch(wrap.getEnvironment());
        }
    }

    static TreeTableItemDock getTreeItemDock(
            TreeTableViewDock treeTable,
            final String name) {

        TreeTableItemDock treeItem = new TreeTableItemDock(
                treeTable.asTreeItemParent(Data.class).lookup(
                new LookupCriteria<Data>() {
            public boolean check(Data cntrl) {
                return name.equals(cntrl.getName());
            }
        }).wrap());
        return treeItem;
    }

    static Wrap getCell(TreeTableViewDock treeTable, final TreeTableItemDock treeItem) {
        return treeTable.wrap().as(Parent.class, TreeTableCell.class)
                .lookup(TreeTableCell.class,
                new LookupCriteria<TreeTableCell>() {
            public boolean check(TreeTableCell control) {
                return control.getTreeTableRow().getTreeItem()
                        == treeItem.getItem();
            }
        }).wrap();
    }
}