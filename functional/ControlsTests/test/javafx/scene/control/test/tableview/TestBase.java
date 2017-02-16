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

import javafx.scene.control.skin.TableColumnHeader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.test.treetable.TreeTableAsOldTableApp;
import javafx.scene.control.test.util.MultipleSelectionHelper;
import javafx.scene.control.test.util.UtilTestFunctions;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Drag;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.Before;
import org.junit.BeforeClass;
import test.javaclient.shared.Utils;
import static javafx.commons.Consts.*;
import javafx.scene.control.TableColumn;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.control.TreeTableColumn;
import org.jemmy.env.Timeout;
import org.jemmy.timing.Waiter;

/**
 * @author Alexander Kirov
 */
public class TestBase extends UtilTestFunctions {

    protected static int DATA_ITEMS_NUM = 0;
    protected static int DATA_FIELDS_NUM = 0;
    protected static Wrap<? extends Control> testedControl; //ListView or TableView or TreeView
    protected static MultipleSelectionHelper selectionHelper;
    protected static Wrap<? extends Label> labelWrap;
    protected static Wrap<? extends Scene> sceneWrap;
    protected static Wrap<? extends Control> tableViewWrap;
    protected static Wrap<? extends Button> resetWrap;
    protected static Wrap<? extends TextField> selectedWrap;
    protected static Wrap<? extends CheckBox> multipleSelectionWrap;
    protected static Wrap<? extends CheckBox> singleCellSelectionWrap;
    protected static Wrap contentPane;
    protected static Wrap btnInsertItem;
    protected static Text txtItemInsertIndex;
    protected static Wrap btnRemoveItem;
    protected static Text txtItemRemoveIndex;
    protected static Wrap btnInsertColumn;
    protected static Text txtColumnInserIndex;
    protected static Wrap btnRemoveColumn;
    protected static Text txtColumnRemoveIndex;
    protected int pageWidthInCells;
    protected int pageHeightInCells;
    Wrap<? extends Node> headerWrap;
    protected static boolean isTableTests = true;

    private static int getColumnCount() {
        return isTableTests ? TableViewApp.DATA_FIELDS_NUM : TreeTableAsOldTableApp.DATA_FIELDS_NUM;
    }

    private static int getRowCount() {
        return isTableTests ? TableViewApp.DATA_ITEMS_NUM : TreeTableAsOldTableApp.DATA_ITEMS_NUM;
    }

    private static String getContentPaneID() {
        return TableViewApp.CONTENT_PANE_ID;
    }

    private static void initWrappers() {
        sceneWrap = Root.ROOT.lookup().wrap();
        parent = sceneWrap.as(Parent.class, Node.class);
        contentPane = parent.lookup(new ByID<Node>(getContentPaneID())).wrap();
        if (isTableTests) {
            tableViewWrap = parent.lookup(TableView.class).wrap();
        } else {
            tableViewWrap = parent.lookup(TreeTableView.class).wrap();
        }
        tableViewWrap.getEnvironment().setTimeout(Drag.IN_DRAG_TIMEOUT, 1);

        resetWrap = parent.lookup(Button.class, new ByID<Button>(RESET_BTN_ID)).wrap();
        selectedWrap = parent.lookup(TextField.class, new ByID<TextField>(SELECTED_ITEMS_ID)).wrap();
        multipleSelectionWrap = parent.lookup(CheckBox.class, new ByID<CheckBox>(ENABLE_MULTIPLE_SELECTION_ID)).wrap();
        btnInsertItem = parent.lookup(Button.class, new ByID<Button>(INSERT_ITEM_BTN_ID)).wrap();
        txtItemInsertIndex = (Text) parent.lookup(TextField.class, new ByID<TextField>(INSERT_ITEM_INDEX_ID)).wrap().as(Text.class);
        btnInsertColumn = parent.lookup(Button.class, new ByID<Button>(INSERT_COLUMN_BTN_ID)).wrap();
        txtColumnInserIndex = (Text) parent.lookup(TextField.class, new ByID<TextField>(INSERT_COLUMN_INDEX_ID)).wrap().as(Text.class);
        btnRemoveItem = parent.lookup(Button.class, new ByID<Button>(REMOVE_ITEM_BTN_ID)).wrap();
        txtItemRemoveIndex = (Text) parent.lookup(TextField.class, new ByID<TextField>(ITEM_INDEX_TO_REMOVE_ID)).wrap().as(Text.class);
        btnRemoveColumn = parent.lookup(Button.class, new ByID<Button>(REMOVE_COLUMN_BTN_ID)).wrap();
        txtColumnRemoveIndex = (Text) parent.lookup(TextField.class, new ByID<TextField>(COLUMN_INDEX_TO_REMOVE_ID)).wrap().as(Text.class);

        testedControl = tableViewWrap;
        setContentSize(getColumnCount(), getRowCount());
        if (isTableTests) {
            singleCellSelectionWrap = parent.lookup(CheckBox.class, new ByID<CheckBox>(TableViewApp.SINGLE_CELL_SELECTION_ID)).wrap();
        } else {
            singleCellSelectionWrap = parent.lookup(CheckBox.class, new ByID<CheckBox>(TreeTableAsOldTableApp.SINGLE_CELL_SELECTION_ID)).wrap();
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        if (isTableTests) {
            System.out.println("Starting an old TableView application with simple controllers.");
            TableViewApp.main(null);
        } else {
            System.out.println("Starting a new TreeTable application created as an old TableView application.");
            TreeTableAsOldTableApp.main(null);
        }
        initWrappers();
    }

    @Before
    public void setUp() throws Exception {
        resetWrap.mouse().click();
        selectionHelper.setMultiple(false);
        selectionHelper.setSingleCell(false);
        pageWidthInCells = (int) (tableViewWrap.getScreenBounds().getWidth() / (TestBaseCommon.getCellWrap(testedControl, 1, 0).getScreenBounds().getX() - TestBaseCommon.getCellWrap(testedControl, 0, 0).getScreenBounds().getX()));
        pageHeightInCells = (int) ((tableViewWrap.getScreenBounds().getHeight() - TestBaseCommon.getHeaderWrap(tableViewWrap).getScreenBounds().getHeight()) / (TestBaseCommon.getCellWrap(testedControl, 0, 1).getScreenBounds().getY() - TestBaseCommon.getCellWrap(testedControl, 0, 0).getScreenBounds().getY()));
        headerWrap = TestBaseCommon.getHeaderWrap(tableViewWrap);
        selectionHelper.setPageHeight(pageHeightInCells);
        selectionHelper.setPageWidth(pageWidthInCells);
    }

    protected static void setContentSize(int x, int y) {
        DATA_FIELDS_NUM = x;
        DATA_ITEMS_NUM = y;
        selectionHelper = new MultipleSelectionHelper(DATA_FIELDS_NUM, DATA_ITEMS_NUM);
    }

    protected void selectionCycle(Keyboard.KeyboardButtons modifier) throws Throwable {
        if (Utils.isSmokeTesting()) {
            selectionCycle(0, 2, 0, 2, modifier);
        } else {
            selectionCycle(0, DATA_FIELDS_NUM, 0, DATA_ITEMS_NUM, modifier);
        }
        //selectionCycle(0, 3, 0, 3, modifier);
    }

    protected void selectionCycle(int first_column, int last_column, int first_row, int last_row, Keyboard.KeyboardButtons modifier) throws Throwable {
        if (modifier != null) {
            testedControl.keyboard().pressKey(modifier);
        }
        try {
            for (int j = first_column; j < last_column; j++) {
                final int column = j;
                for (int i = first_row; i < last_row; i++) {
                    final int row = i;
                    TestBaseCommon.scrollTo(testedControl, j, i);
                    TestBaseCommon.getCellWrap(testedControl, column, row).mouse().click();
                    selectionHelper.click(column, row, modifier);
                    checkSelection();
                }
            }
        } catch (Throwable error) {
            throw error;
        } finally {
            if (modifier != null) {
                testedControl.keyboard().releaseKey(modifier);
            }
        }
    }

    protected Lookup getScrollLookup(final Orientation orientation) {
        return parent.lookup(ScrollBar.class, new LookupCriteria<ScrollBar>() {
            public boolean check(ScrollBar cntrl) {
                return (cntrl.getOrientation() == orientation) && cntrl.isVisible();
            }
        });
    }

    protected void insertAt(int pos) {
        txtItemInsertIndex.clear();
        txtItemInsertIndex.type(String.valueOf(pos));
        btnInsertItem.mouse().click();
    }

    protected void insertColumnAt(int pos) {
        txtColumnInserIndex.clear();
        txtColumnInserIndex.type(String.valueOf(pos));
        btnInsertColumn.mouse().click();
    }

    protected void removeAt(int pos) {
        txtItemRemoveIndex.clear();
        txtItemRemoveIndex.type(String.valueOf(pos));
        btnRemoveItem.mouse().click();
    }

    protected void removeColumnAt(int pos) {
        txtColumnRemoveIndex.clear();
        txtColumnRemoveIndex.type(String.valueOf(pos));
        btnRemoveColumn.mouse().click();
    }

    protected void keyboardCycle(int counter, Keyboard.KeyboardButtons btn, Keyboard.KeyboardModifiers modifier) {
        for (int i = 0; i < counter; i++) {
            tableViewWrap.keyboard().pushKey(btn, modifier);
        }
    }

    protected void keyboardSelectionCheckCycle(int counter, Keyboard.KeyboardButtons btn, Keyboard.KeyboardModifiers... modifier) {
        for (int i = 0; i < counter; i++) {
            keyboardCheck(btn, modifier);
        }
    }

    protected Wrap<? extends IndexedCell> getCellWrap(final int column, final int row) {
        return TestBaseCommon.getCellWrap(testedControl, column, row);
    }

    public static class ByPosition implements LookupCriteria<Node> {

        private int column, row;

        public ByPosition(int column, int row) {
            this.column = column;
            this.row = row;
        }

        public boolean check(Node control) {
            if (TableCell.class.isAssignableFrom(control.getClass())) {
                return getColumnIndex((TableCell) control) == column
                        && getRowIndex((TableCell) control) == row;
            } else if (TreeTableCell.class.isAssignableFrom(control.getClass())) {
                return getColumnIndex((TreeTableCell) control) == column
                        && getRowIndex((TreeTableCell) control) == row;
            } else {
                return false;
            }
        }
    }

    protected static void switchOnMultiple() {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                multipleSelectionWrap.getControl().setSelected(true);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void scrollTo(final int column, final int row) {
        TestBaseCommon.scrollTo(testedControl, column, row);
    }

    private static int getRowIndex(TableCell tableCell) {
        return TestBaseCommon.getRowIndex(tableCell);
    }

    private static int getRowIndex(TreeTableCell tableCell) {
        return TestBaseCommon.getRowIndex(tableCell);
    }

    private static int getColumnIndex(TableCell tableCell) {
        return TestBaseCommon.getColumnIndex(tableCell);
    }

    private static int getColumnIndex(TreeTableCell tableCell) {
        return TestBaseCommon.getColumnIndex(tableCell);
    }

    protected void checkSelection() {
        testedControl.waitState(new State() {
            Collection<Point> helper_selected;
            Collection<Point> selected;
            Point helperFocus;
            Point focus;

            public Object reached() {
                helper_selected = selectionHelper.getSelected();
                selected = TestBaseCommon.getSelected(testedControl);
                helperFocus = selectionHelper.focus;
                focus = TestBaseCommon.getSelectedItem(testedControl);

                if (helper_selected.size() == selected.size()
                        && helper_selected.containsAll(selected)
                        && (focus.equals(helperFocus) || selectionHelper.ctrlA)) {
                    return true;
                } else {
                    return null;
                }
            }

            @Override
            public String toString() {
                helper_selected = new ArrayList<Point>(helper_selected);
                selected = new ArrayList<Point>(selected);

                final Comparator<Point> comparator = new Comparator<Point>() {
                    public int compare(Point p1, Point p2) {
                        return p1.y - p2.y;
                    }
                };
                Collections.sort((ArrayList) helper_selected, comparator);
                Collections.sort((ArrayList) selected, comparator);

                StringBuilder sb;
                sb = new StringBuilder("{")
                        .append("Helper selected : ").append(helper_selected).append("\n")
                        .append("Selected : ").append(selected).append("\n")
                        .append("Helper focus : ").append(helperFocus).append("\n")
                        .append("Contol focus : ").append(focus).append("}");
                return sb.toString();
            }
        });
    }

    protected void keyboardCheck(Keyboard.KeyboardButtons btn, Keyboard.KeyboardModifiers... modifier) {
        if (btn == Keyboard.KeyboardButtons.PAGE_DOWN || btn == Keyboard.KeyboardButtons.PAGE_UP) {
            selectionHelper.setVisibleRange(TestBaseCommon.getVisibleRange(testedControl));
        }
        testedControl.keyboard().pushKey(btn, modifier);
        selectionHelper.push(btn, modifier);
        checkSelection();
    }

    protected String getControlName() {
        if (isTableTests) {
            return "TableView";
        } else {
            return "TreeTableView";
        }
    }

    Wrap scrollToColumnByIndexBtn;
    Text columnIndexToScroll;

    void scrollToColumnByIndex(final int index) {
        if (null == scrollToColumnByIndexBtn) {
            scrollToColumnByIndexBtn = parent.lookup(Button.class, new ByID<Button>(Table.SCROLL_TO_COLUMN_BTN_ID)).wrap();
        }
        if (null == columnIndexToScroll) {
            columnIndexToScroll = (Text) parent.lookup(TextField.class, new ByID<TextField>(Table.SCROLL_TO_COLUMN_INDEX_ID)).wrap().as(Text.class);
        }

        columnIndexToScroll.clear();
        columnIndexToScroll.type(String.valueOf(index));
        scrollToColumnByIndexBtn.mouse().click();
    }

    class Helper extends ApplicationInteractionFunctions {

        {
            testedControl = TestBase.testedControl;
        }
    }

    boolean isColumnHeaderVisible(final int index) {

        Boolean state = new Waiter(new Timeout("", 1000)).waitState(new State<Boolean>() {
            Helper helper = new Helper();
            Wrap<? extends TableColumnHeader> headerWrap;

            public Boolean reached() {
                headerWrap = helper.getTableColumnHeaderWrap(getSimpleColumnHeaderText(index));
                TableColumnHeader header = headerWrap.getControl();
                if (header.isVisible()
                        && getSimpleColumnHeaderText(index).equals(header.getTableColumn().getText())
                        && testedControl.getScreenBounds().contains(headerWrap.getScreenBounds())) {
                    return Boolean.TRUE;
                } else {
                    return null;
                }
            }
        });

        return (null == state) ? false : true;
    }

    void scrollToColumn(final int index) {
        new GetAction<Void>() {

            @Override
            public void run(Object... parameters) throws Exception {
                if (isTableTests) {
                    TableView tableView = (TableView) testedControl.getControl();
                    TableColumn col = (TableColumn) tableView.getColumns().get(index);
                    tableView.scrollToColumn(col);
                } else {
//                    TreeTableView treeTableView = (TreeTableView) testedControl.getControl();
//                    TreeTableColumn col = (TreeTableColumn) treeTableView.getColumns().get(index);
//                    treeTableView.scrollToColumn(col);
                    throw new RuntimeException("TreeTableView#scrollToColumn doesn't support TreeTableColumn type "
                            + "\nrefer: https://javafx-jira.kenai.com/browse/RT-31856");
                }
            }
        }.dispatch(testedControl.getEnvironment());
    }

    void refill(final int cols, final int rows) {

        final String colsTextID = Table.COLUMNS_NUMBER_ID;
        final Text columnsText = (Text) parent.lookup(TextField.class, new ByID(colsTextID)).wrap().as(Text.class);
        columnsText.clear();
        columnsText.type(String.valueOf(cols));

        final String rowsTextID = Table.ROWS_NUMBER_ID;
        final Text rowsText = (Text) parent.lookup(TextField.class, new ByID(rowsTextID)).wrap().as(Text.class);
        rowsText.clear();
        rowsText.type(String.valueOf(rows));

        final String refillBtnID = Table.REFILL_BNT_ID;
        parent.lookup(Button.class, new ByID<Button>(refillBtnID)).wrap().mouse().click();
    }

    String getSimpleColumnHeaderText(final int index) {
        return "field " + index;
    }

    double getScrollBarValue(final Orientation orientation) {
        return new GetAction<Double>() {

            @Override
            public void run(Object... parameters) throws Exception {
                Lookup hScrollBar = getScrollLookup(orientation);
                setResult(((ScrollBar) hScrollBar.wrap().getControl()).getValue());
            }
        }.dispatch(testedControl.getEnvironment());
    }

    double getScrollBarMaxValue(final Orientation orientation) {
         return new GetAction<Double>() {

            @Override
            public void run(Object... parameters) throws Exception {
                Lookup hScrollBar = getScrollLookup(orientation);
                setResult(((ScrollBar) hScrollBar.wrap().getControl()).getMax());
            }
        }.dispatch(testedControl.getEnvironment());
    }

    double getScrollBarMinValue(final Orientation orientation) {
         return new GetAction<Double>() {

            @Override
            public void run(Object... parameters) throws Exception {
                Lookup hScrollBar = getScrollLookup(orientation);
                setResult(((ScrollBar) hScrollBar.wrap().getControl()).getMin());
            }
        }.dispatch(testedControl.getEnvironment());
    }

    void resizeColumn(final int index, final double delta) {

        final double initialWidth = getColumnWidth(index);

        new GetAction<Void>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTableTests) {
                    TableView table = (TableView) testedControl.getControl();
                    table.resizeColumn((TableColumn) table.getColumns().get(index), delta);
                } else {
                    TreeTableView treeTable = (TreeTableView) testedControl.getControl();
                    treeTable.resizeColumn((TreeTableColumn) treeTable.getColumns().get(index), delta);
                }
            }
        }.dispatch(testedControl.getEnvironment());

        new Waiter(new Timeout("", 1000)).waitState(new State<Boolean>() {
            public Boolean reached() {
                return (Math.abs(getColumnWidth(index) - initialWidth - delta) <= 1e-7) ? Boolean.TRUE : null;
            }
        });
    }

    protected void setColumnTextSync(final int index, final String text) {
    assert !Platform.isFxApplicationThread();
    CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
                if (isTableTests) {
                    TableView table = (TableView) testedControl.getControl();
                    ((TableColumn) table.getColumns().get(index)).setText(text);
                } else {
                    TreeTableView treeTable = (TreeTableView) testedControl.getControl();
                    ((TreeTableColumn) treeTable.getColumns().get(index)).setText(text);
                }
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException ex) {
            System.out.println("Exception while awaiting text to set: " + ex);
        }
    }

    protected String getColumnText(final int index) {
        if (isTableTests) {
            TableView table = (TableView) testedControl.getControl();
            return ((TableColumn) table.getColumns().get(index)).getText();
        } else {
            TreeTableView treeTable = (TreeTableView) testedControl.getControl();
            return ((TreeTableColumn) treeTable.getColumns().get(index)).getText();
        }
    }

    protected double getColumnWidth(final int index) {
        return new Helper().getCellWrap(index, 0).getScreenBounds().getWidth();
    }
}
