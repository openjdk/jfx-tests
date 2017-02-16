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
package javafx.scene.control.test.tableview;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.test.tableview.TableViewApp.Data;
import static javafx.scene.control.test.tableview.TestBase.DATA_FIELDS_NUM;
import javafx.scene.control.test.treetable.TreeTableAsOldTableApp;
import javafx.util.StringConverter;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.*;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class TableViewTest extends TestBase {

    /*
     * removed
     * @Smoke
     @Test(timeout=300000) public void keyboardCtrlShiftPageUpPageDownTest() throws
     * Throwable { keyboardPageUpPageDownCycle(CTRL_DOWN_MASK_OS,
     * KeyboardModifiers.SHIFT_DOWN_MASK); }
     */
    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void insertSingleItemTest() throws Throwable {
        insertAt(0);
        insertAt(TableViewApp.DATA_ITEMS_NUM / 2);
        ScreenshotUtils.checkScreenshot(getControlName() + "Test-insert-item", contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void removeSingleItemTest() throws Throwable {
        removeAt(0);
        removeAt(TableViewApp.DATA_ITEMS_NUM / 2);
        ScreenshotUtils.checkScreenshot(getControlName() + "Test-remove-item", contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void insertSingleColumnTest() throws Throwable {
        insertColumnAt(0);
        insertColumnAt(TableViewApp.DATA_FIELDS_NUM / 2);
        ScreenshotUtils.checkScreenshot(getControlName() + "Test-insert-column", contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void removeSingleColumnTest() throws Throwable {
        removeColumnAt(0);
        removeColumnAt(TableViewApp.DATA_FIELDS_NUM / 2);
        ScreenshotUtils.checkScreenshot(getControlName() + "Test-remove-column", contentPane);
    }

    @Smoke
    @Test(timeout = 300000)
    public void scrollBarsTest() throws Throwable {
        final Lookup vScrollBar = getScrollLookup(Orientation.VERTICAL);
        final Lookup hScrollBar = getScrollLookup(Orientation.HORIZONTAL);
        final int ROWS_TO_REMOVE = DATA_ITEMS_NUM - pageHeightInCells;
        final int COLS_TO_REMOVE = DATA_FIELDS_NUM - pageWidthInCells;

        final State<Integer> vScrollBarCount = new State<Integer>() {
            public Integer reached() { return Integer.valueOf(vScrollBar.size()); }
        };

        final State<Integer> hScrollBarCount = new State<Integer>() {
            public Integer reached() { return Integer.valueOf(hScrollBar.size()); }
        };

        testedControl.waitState(hScrollBarCount, Integer.valueOf(1));
        testedControl.waitState(vScrollBarCount, Integer.valueOf(1));
        for (int i = 0; i < ROWS_TO_REMOVE; i++) {
            btnRemoveItem.mouse().click();
        }
        testedControl.waitState(vScrollBarCount, Integer.valueOf(0));

        testedControl.waitState(hScrollBarCount, Integer.valueOf(1));
        for (int i = 0; i < COLS_TO_REMOVE; i++) {
            btnRemoveColumn.mouse().click();
        }
        testedControl.waitState(hScrollBarCount, Integer.valueOf(0));

        btnInsertItem.mouse().click();
        testedControl.waitState(vScrollBarCount, Integer.valueOf(1));

        btnInsertColumn.mouse().click();
        testedControl.waitState(hScrollBarCount, Integer.valueOf(1));
    }

    /**
     * Checks that sort works for the column. According to the spec there are
     * three states of sorting and all of them are checked
     *
     */
    @Smoke
    @Test(timeout = 300000)
    public void columnSortTest() throws Throwable {
        Wrap<? extends Node> columHeader = headerWrap.as(Parent.class, Node.class).lookup(Label.class, new ByText("field 0")).wrap();

        //Ascending sort
        columHeader.mouse().click();
        Wrap<? extends IndexedCell> cellWrap = getCellWrap(0, isTableTests ? 0 : 1);// first line for treeTable - is root line.
        assertTrue(cellWrap.getControl().getItem().equals("item 00 field 0"));

        //Descending sort
        columHeader.mouse().click();
        cellWrap = getCellWrap(0, isTableTests ? 0 : 1);
        int expectedIndex = isTableTests
                             ? TableViewApp.DATA_ITEMS_NUM - 1
                             : TreeTableAsOldTableApp.DATA_ITEMS_NUM - 2;
        assertEquals(String.format("item %02d field 0", expectedIndex), cellWrap.getControl().getItem());

        //Disable sort
        columHeader.mouse().click();

         //Ascending sort
        columHeader.mouse().click();
        cellWrap = getCellWrap(0, isTableTests ? 0 : 1);
        assertTrue(cellWrap.getControl().getItem().equals("item 00 field 0"));

        scrollTo(0, TableViewApp.DATA_ITEMS_NUM - 1);
        cellWrap = getCellWrap(0, TableViewApp.DATA_ITEMS_NUM - 1);
        Rectangle old_bounds = cellWrap.getScreenBounds();
        columHeader.mouse().click();
        cellWrap = getCellWrap(0, TableViewApp.DATA_ITEMS_NUM - 1);
        Rectangle newBounds = cellWrap.getScreenBounds();
        assertEquals(old_bounds.x, newBounds.x, 1);
        assertEquals(old_bounds.y, newBounds.y, 1);
        assertEquals(old_bounds.width, newBounds.width, 1);
        assertEquals(old_bounds.height, newBounds.height, 1);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void columnDragTest() throws Throwable {
        Wrap<? extends Node> column_header_0 = headerWrap.as(Parent.class, Node.class).lookup(Label.class, new ByText("field 0")).wrap();

        Rectangle header_bounds = column_header_0.getScreenBounds();
        Rectangle content_bounds = contentPane.getScreenBounds();
        Double x = header_bounds.getX() - content_bounds.getX() + header_bounds.getWidth() / 2;
        Double y = header_bounds.getY() - content_bounds.getY() + header_bounds.getHeight() / 2;
        contentPane.mouse().move(new Point(x, y));
        contentPane.mouse().press();
        contentPane.mouse().move(new Point(0, 0));
        checkScreenshot(getControlName() + "Test-columnDrag-outside-0", contentPane);
        contentPane.mouse().move(new Point(content_bounds.getWidth(), 0));
        checkScreenshot(getControlName() + "Test-columnDrag-outside-1", contentPane);
        contentPane.mouse().move(new Point(content_bounds.getWidth(), content_bounds.getHeight()));
        checkScreenshot(getControlName() + "Test-columnDrag-outside-2", contentPane);
        contentPane.mouse().move(new Point(0, content_bounds.getHeight()));
        checkScreenshot(getControlName() + "Test-columnDrag-outside-3", contentPane);
        contentPane.mouse().move(new Point(0, 0));
        checkScreenshot(getControlName() + "Test-columnDrag-outside-0", contentPane);
        contentPane.mouse().release();

        Rectangle table_bounds = tableViewWrap.getScreenBounds();
        x = header_bounds.getX() - table_bounds.getX() + header_bounds.getWidth() / 2;
        y = header_bounds.getY() - table_bounds.getY() + header_bounds.getHeight() / 2;
        tableViewWrap.mouse().move(new Point(x, y));
        tableViewWrap.mouse().press();
        tableViewWrap.mouse().move(new Point(0, 0));
        checkScreenshot(getControlName() + "Test-columnDrag-inside-0", contentPane);
        tableViewWrap.mouse().move(new Point(table_bounds.getWidth() / 2, header_bounds.getHeight() / 2));
        checkScreenshot(getControlName() + "Test-columnDrag-inside-1", contentPane);
        tableViewWrap.mouse().move(new Point(table_bounds.getWidth(), header_bounds.getHeight() / 2));
        checkScreenshot(getControlName() + "Test-columnDrag-inside-2", contentPane);
        tableViewWrap.mouse().move(new Point(x, y));
        tableViewWrap.mouse().release();

        Wrap<? extends Node> column_header_3 = headerWrap.as(Parent.class, Node.class).lookup(Label.class, new ByText("field 3")).wrap();
        column_header_0.drag().dnd(column_header_3, column_header_3.getClickPoint().translate(5, 0));
        Wrap<? extends IndexedCell> cell_wrap = getCellWrap(0, 0);
        assertTrue(cell_wrap.getControl().getItem().equals("item 00 field 1"));
        cell_wrap = getCellWrap(3, 0);
        assertTrue(cell_wrap.getControl().getItem().equals("item 00 field 0"));

        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void columnAutoSize() throws Throwable {
        final double AUTO_WIDTH = isTableTests ? 85 : 215;

        final Wrap<? extends Node> columnHeader0 = headerWrap.as(Parent.class, Node.class).lookup(Label.class, new ByText("field 0")).wrap();

        Rectangle headerBounds = columnHeader0.getScreenBounds();

        final double shrinkedWidth = headerBounds.getWidth() / 2;
        columnHeader0.mouse().move(new Point(headerBounds.getWidth(), headerBounds.getHeight() / 2));
        columnHeader0.mouse().press();
        columnHeader0.mouse().move(new Point(shrinkedWidth, headerBounds.getHeight() / 2));
        columnHeader0.mouse().release();

        columnHeader0.waitState(new State() {
            public Object reached() {
                System.out.println("Checking, that width reducing was applied correctly (by 1 times).");
                Rectangle header_bounds = columnHeader0.getScreenBounds();
                if (Math.abs(header_bounds.getWidth() - shrinkedWidth) <= 5) {
                    System.out.println("Correct.");
                    return Boolean.TRUE;
                }
                System.out.println("Incorrect.");
                return null;
            }
        });

        headerBounds = columnHeader0.getScreenBounds();
        columnHeader0.mouse().click(2, new Point(headerBounds.getWidth(), headerBounds.getHeight() / 2));

        columnHeader0.waitState(new State() {
            public Object reached() {
                System.out.println("Checking, that autosizing was applied correctly.");
                Rectangle header_bounds = columnHeader0.getScreenBounds();
                if (Math.abs(header_bounds.getWidth() - AUTO_WIDTH) < 5) { // TODO:
                    System.out.println("Correct.");
                    return Boolean.TRUE;
                }
                System.out.println("Incorrect.");
                return null;
            }
        });
    }

    @Smoke
    @Test(timeout = 300000)
    public void userDataColumnTest() {
        ObservableList<TableColumnBase> columns;
        if (isTableTests) {
            columns = ((TableView) tableViewWrap.getControl()).getColumns();
        } else {
            columns = ((TreeTableView) tableViewWrap.getControl()).getColumns();
        }
        Object userData[] = new Object[columns.size()];
        for (int i = 0; i < userData.length; i++) {
            userData[i] = new Object();
            columns.get(i).setUserData(userData[i]);
        }
        for (int i = 0; i < userData.length; i++) {
            System.out.println("userData[i] = " + userData[i]);
            System.out.println("columns.get(i).getUserData() = " + columns.get(i).getUserData());
            System.out.println("columns.get(i).getProperties().entrySet() = " + columns.get(i).getProperties().entrySet());
//            Assert.assertTrue(columns.get(i).getProperties().containsKey(userData[i]));
            Assert.assertTrue(columns.get(i).getProperties().containsValue(userData[i]));

            Set entrySet = columns.get(i).getProperties().entrySet();
            for (Iterator<Entry> it = entrySet.iterator(); it.hasNext();) {
                Entry entry = it.next();
                System.out.println("key = " + entry.getKey() + "; val = " + entry.getValue());
            }

            Assert.assertEquals(userData[i], columns.get(i).getUserData());
            //Assert.assertTrue(columns.get(i).getProperties().entrySet().contains(userData[i]));
        }
    }

    /**
     * Checks that when the sorting is applied
     * to the underlying data collection
     * the cells are rendered in the right order.
     */
    @Smoke
    @Test(timeout=300000)
    public void renderingAfterSortingTest() throws Throwable {

        final int ITEMS_COUNT = 7;
        final int COL_IDX = 0;

        StringConverter<Data> conv = new StringConverter<Data>() {
            @Override
            public String toString(Data item) {
                return String.format("item %s field %d", item.toString(), COL_IDX);
            }

            @Override
            public Data fromString(String s) { return new Data(s, DATA_FIELDS_NUM, 0); }
        };

        SortValidator<Data, TableCell> validator = new SortValidator<Data, TableCell>(ITEMS_COUNT, conv) {

            @Override
            protected void setControlData(final ObservableList<Data> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        if (isTableTests) {
                            ((TableView) testedControl.getControl()).setItems(ls);
                        } else {
                            for (Data data : ls) {
                                ((TreeTableView) testedControl.getControl()).getRoot().getChildren().addAll(new TreeItem(data));
                            }
                            ((TreeTableView) testedControl.getControl()).setShowRoot(false);
                        }
                    }
                }.dispatch(testedControl.getEnvironment());
            }

            @Override
            protected Lookup<? extends TableCell> getCellsLookup() {
                return testedControl.as(Parent.class, Node.class).lookup(TableCell.class, new LookupCriteria<TableCell>() {
                            public boolean check(TableCell cell) {
                                TableColumn col = cell.getTableColumn();
                                return COL_IDX == cell.getTableView().getColumns().indexOf(col) && cell.isVisible();
                            }
                        });
            }

            @Override
            protected String getTextFromCell(TableCell cell) {
               return cell.getText();
            }
        };

        boolean result = validator.check();
        String msg = validator.getFailureReason();
        assertTrue(msg, result);
    }

    @Smoke
    @Test(timeout=300000)
    public void scrollToColumnIndex() {
        DATA_FIELDS_NUM = 64;
        refill(DATA_FIELDS_NUM, DATA_ITEMS_NUM);

        scrollToColumnByIndex(DATA_FIELDS_NUM - 1);
        assertTrue(isColumnHeaderVisible(DATA_FIELDS_NUM - 1));
        assertTrue(!isColumnHeaderVisible(0));
        assertEquals(getScrollBarMaxValue(Orientation.HORIZONTAL), getScrollBarValue(Orientation.HORIZONTAL), 1e-7);

        scrollToColumnByIndex(0);
        assertTrue(isColumnHeaderVisible(0));
        assertTrue(!isColumnHeaderVisible(DATA_FIELDS_NUM - 1));
        assertEquals(getScrollBarMinValue(Orientation.HORIZONTAL), getScrollBarValue(Orientation.HORIZONTAL), 1e-7);

        scrollToColumn(DATA_FIELDS_NUM - 1);
        assertTrue(isColumnHeaderVisible(DATA_FIELDS_NUM - 1));
        assertTrue(!isColumnHeaderVisible(0));
        assertEquals(getScrollBarMaxValue(Orientation.HORIZONTAL), getScrollBarValue(Orientation.HORIZONTAL), 1e-7);

        scrollToColumn(0);
        assertTrue(isColumnHeaderVisible(0));
        assertTrue(!isColumnHeaderVisible(DATA_FIELDS_NUM - 1));
        assertEquals(getScrollBarMinValue(Orientation.HORIZONTAL), getScrollBarValue(Orientation.HORIZONTAL), 1e-7);

        scrollToColumnByIndex(DATA_FIELDS_NUM / 3);
        assertTrue(isColumnHeaderVisible(DATA_FIELDS_NUM / 3));
        assertTrue(!isColumnHeaderVisible(0));
        assertTrue(!isColumnHeaderVisible(DATA_FIELDS_NUM - 1));
        double delta = testedControl.getScreenBounds().getWidth() / 2;
        assertEquals(getScrollBarMaxValue(Orientation.HORIZONTAL) / 3, getScrollBarValue(Orientation.HORIZONTAL), delta);
    }

    /**
     * Checks that column resizing is correct
     * according to desired values
     */
    @Smoke
    @Test
    public void columnResizeTest() {
        final int columnIndex = 0;

        double delta = 0.0;
        double expectedWidth = getColumnWidth(columnIndex);
        resizeColumn(columnIndex, delta);
        assertEquals(expectedWidth, getColumnWidth(columnIndex), 1e-7);

        delta = 100.0;
        expectedWidth += delta;
        resizeColumn(columnIndex, delta);
        assertEquals(expectedWidth, getColumnWidth(columnIndex), 1e-7);

        delta = 0.0;
        resizeColumn(columnIndex, delta);
        assertEquals(expectedWidth, getColumnWidth(columnIndex), 1e-7);

        delta = 100.0;
        expectedWidth += delta;
        resizeColumn(columnIndex, delta);
        assertEquals(expectedWidth, getColumnWidth(columnIndex), 1e-7);
    }

    @Override
    protected void insertAt(int pos) {
        txtItemInsertIndex.clear();
        txtItemInsertIndex.type(String.valueOf(pos));
        btnInsertItem.mouse().click();
    }

    @Override
    protected void insertColumnAt(int pos) {
        txtColumnInserIndex.clear();
        txtColumnInserIndex.type(String.valueOf(pos));
        btnInsertColumn.mouse().click();
    }

    @Override
    protected void removeAt(int pos) {
        txtItemRemoveIndex.clear();
        txtItemRemoveIndex.type(String.valueOf(pos));
        btnRemoveItem.mouse().click();
    }

    @Override
    protected void removeColumnAt(int pos) {
        txtColumnRemoveIndex.clear();
        txtColumnRemoveIndex.type(String.valueOf(pos));
        btnRemoveColumn.mouse().click();
    }
}
