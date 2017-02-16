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

import client.test.Smoke;
import javafx.scene.control.skin.NestedTableColumnHeader;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.control.skin.TableHeaderRow;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.collections.FXCollections.*;
import javafx.collections.ObservableList;
import static javafx.commons.Consts.*;
import javafx.commons.Consts.CellEditorType;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import static javafx.scene.control.test.tableview.ApplicationInteractionFunctions.addColumn;
import static javafx.scene.control.test.tableview.ApplicationInteractionFunctions.isTableTests;
import static javafx.scene.control.test.tableview.ApplicationInteractionFunctions.testedControl;
import static javafx.scene.control.test.util.TableListCommonTests.fixedCellSizePropertyTestCommon;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory.CustomReversedComparator;
import javafx.scene.text.Text;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByText;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import static org.junit.Assert.*;
import org.junit.Test;
import static javafx.scene.control.test.tableview.TableUtils.*;
import static javafx.scene.control.test.treetable.TreeTableNewApp.DataItem;
import javafx.stage.PopupWindow;
import javafx.util.Callback;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;

/**
 * @author Alexander Kirov
 */
public class TableViewNewTest extends ApplicationInteractionFunctions {

    @Test(timeout = 300000)
    /**
     * This test will sort content of some column and check, that other columns
     * were sorted in the way, so that data in the same row is preserved (that
     * there is no data confusing).
     */
    public void correctSortConsistencyTest() throws InterruptedException {
        setSize(200, 200);
        final int columns = 2;
        final int rows = 5;
        for (int i = 0; i < columns; i++) {
            addColumn("items" + String.valueOf(i), i, true);
        }
        setNewDataSize(rows);
        createNestedColumn("nested", 0, 0, 1);
        requestFocusOnControl(testedControl);

        if (!isTableTests) {
            new GetAction<Object>() {
                @Override
                public void run(Object... os) throws Exception {
                    TreeTableView ttv = (TreeTableView) testedControl.getControl();
                    ttv.showRootProperty().set(false);
                }
            }.dispatch(testedControl.getEnvironment());
        }

        switchToPropertiesTab("items0");
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefWidth, 80);
        switchToPropertiesTab("items1");
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefWidth, 80);

        Wrap<? extends TableColumnHeader> header1 = getTableColumnHeaderWrap("items0");
        Wrap<? extends TableColumnHeader> header2 = getTableColumnHeaderWrap("items1");

        checkKeyCellsContent("items0-0", "items1-0", "items0-4", "items1-4", columns, rows);
        //Direct sort.
        header1.mouse().click();
        checkKeyCellsContent("items0-0", "items1-0", "items0-4", "items1-4", columns, rows);
        //Reversed sort.
        header1.mouse().click();
        checkKeyCellsContent("items0-4", "items1-4", "items0-0", "items1-0", columns, rows);
        //Direct sort.
        header2.mouse().click();
        checkKeyCellsContent("items0-0", "items1-0", "items0-4", "items1-4", columns, rows);
        //Reversed sort.
        header2.mouse().click();
        checkKeyCellsContent("items0-4", "items1-4", "items0-0", "items1-0", columns, rows);

        switchToPropertiesTab("items0");
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.sortable, Boolean.FALSE);

        header1.mouse().click();
        checkKeyCellsContent("items0-4", "items1-4", "items0-0", "items1-0", columns, rows);

        switchToPropertiesTab("items1");
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.sortable, Boolean.FALSE);
        header2.mouse().click();
        checkKeyCellsContent("items0-4", "items1-4", "items0-0", "items1-0", columns, rows);
    }

    protected void checkKeyCellsContent(String upperLeftText, String upperRightText, String downLeftText, String downRightText, int columns, int rows) {
        checkTextContainment(getCellWrap(0, 0), upperLeftText);
        checkTextContainment(getCellWrap(columns - 1, 0), upperRightText);
        checkTextContainment(getCellWrap(0, rows - 1), downLeftText);
        checkTextContainment(getCellWrap(columns - 1, rows - 1), downRightText);
    }

    protected void checkTextContainment(final Wrap<? extends TableCell> cellWrap, final String textExpectToFind) {
        new Waiter(new Timeout("", 1000)).ensureState(new State() {
            public Object reached() {
                LookupCriteria<Node> lookupCriteria = new LookupCriteria<Node>() {
                    public boolean check(Node cntrl) {
                        return Text.class.isAssignableFrom(cntrl.getClass())
                                || ((Text) cntrl).getText().equals(textExpectToFind);
                    }
                };

                if (cellWrap.as(Parent.class, Node.class).lookup(Text.class, new ByText(textExpectToFind)).size() == 1) {
                    return true;
                } else if (cellWrap.as(Parent.class, Node.class).lookup(lookupCriteria).size() == 1) {
                    return true;
                } else {
                    return null;
                }

            }
        });
    }

    @Test(timeout = 300000)
    /**
     * When you scroll (in horizontal dimension) to some column, and sort it,
     * then scrolling will be dropped. This test checks, that scrolling will be
     * preserved after sorting.
     */
    public void tableViewNotScrollingOnSortTest() throws InterruptedException {
        setSize(200, 200);
        int count = 10;
        for (int i = 0; i < count; i++) {
            addColumn("items" + String.valueOf(i), i);
        }
        setNewDataSize(25);
        switchToPropertiesTab("TreeTableView");
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefWidth, 170);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefHeight, 170);

        for (int i = 0; i < count; i++) {
            checkSortNotScrollingFor("items" + String.valueOf(i), i);
        }
    }

    private void checkSortNotScrollingFor(String name, int index) {
        scrollTo(index, 0);
        final double initialScrollPos = getScrollBarValue(findScrollBar((Parent<Node>)testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true));
        getTableColumnHeaderWrap(name).mouse().click();
        getTableColumnHeaderWrap(name).mouse().click();
        getTableColumnHeaderWrap(name).mouse().click();
        getTableColumnHeaderWrap(name).mouse().click();
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (Math.abs(initialScrollPos - getScrollBarValue(findScrollBar((Parent<Node>)testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true))) < 0.001) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    @Test(timeout = 300000)
    /**
     * This test applies new sortNode and checks, that it was applied.
     */
    public void actualChangeSortNodeTest() throws InterruptedException {
        setSize(200, 200);
        String columnName = "column1";
        addColumn(columnName, 0, true);
        setNewDataSize(10);
        requestFocusOnControl(testedControl);

        final Wrap<? extends TableColumnHeader> header = getTableColumnHeaderWrap(columnName);
        final Rectangle columnNameCoords = header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds();

        switchToPropertiesTab(columnName);
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.sortNode, javafx.scene.shape.Rectangle.class);

        //Direct sort.
        header.mouse().click();
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (!(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds().equals(columnNameCoords))) {
                    return true;
                } else {
                    return null;
                }
            }
        });
        checkRectanglesRelation(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds(), RectanglesRelations.LEFTER,
                header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).wrap().getScreenBounds());
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 1) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        //Reversed sort.
        header.mouse().click();
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (!(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds().equals(columnNameCoords))) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        checkRectanglesRelation(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds(), RectanglesRelations.LEFTER,
                header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).wrap().getScreenBounds());

        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 1) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        //Unsort.
        header.mouse().click();
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds().equals(columnNameCoords)) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 0) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    @Test(timeout = 300000)
    /**
     * This test checks, that using different types of setting or binding,
     * sortNode can be set. Standart property test.
     */
    public void sortNodePropertyTest() throws InterruptedException {
        assertNull(new TableColumn().getSortNode());
        setSize(200, 200);
        String columnName = "column1";
        addColumn(columnName, 0, true);
        setNewDataSize(10);
        requestFocusOnControl(testedControl);
        switchToPropertiesTab(columnName);

        selectObjectFromChoiceBox(SettingType.SETTER, Properties.sortNode, javafx.scene.shape.Rectangle.class);
        checkTextFieldTextContaining(Properties.sortNode, "Rectangle");

        selectObjectFromChoiceBox(SettingType.BIDIRECTIONAL, Properties.sortNode, Text.class);
        checkTextFieldTextContaining(Properties.sortNode, "Text");

        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.sortNode, Group.class);
        checkTextFieldTextContaining(Properties.sortNode, "Group");
    }

    @Test(timeout = 300000)
    /**
     * This test checks, that sort node is applied right at the same moment, as
     * it was set.
     */
    public void sortNodeImmidiateApplying() throws InterruptedException {
        setSize(200, 200);
        String columnName = "column1";
        addColumn(columnName, 0, true);
        setNewDataSize(10);
        requestFocusOnControl(testedControl);
        switchToPropertiesTab(columnName);

        //Direct sort.
        Wrap<? extends TableColumnHeader> header = getTableColumnHeaderWrap(columnName);
        header.mouse().click();

        assertTrue(header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 0);

        selectObjectFromChoiceBox(SettingType.SETTER, Properties.sortNode, javafx.scene.shape.Rectangle.class);
        assertTrue(header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 1);
        header.mouse().click();
        assertTrue(header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 1);
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.sortNode, null);
        assertTrue(header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 0);
        selectObjectFromChoiceBox(SettingType.BIDIRECTIONAL, Properties.sortNode, javafx.scene.shape.Rectangle.class);
        assertTrue(header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 1);
    }

    @Test(timeout = 300000)
    /**
     * This test checks, that if comparator is set, then it is applied
     * immediately.
     */
    public void comparatorPropertyImmidiateAndCorrectApplyingTest() throws InterruptedException {
        setSize(200, 200);
        final int columns = 2;
        final int rows = 5;
        for (int i = 0; i < columns; i++) {
            addColumn("items" + String.valueOf(i), i, true);
        }
        setNewDataSize(rows);

        createNestedColumn("nested", 0, 0, 1);
        requestFocusOnControl(testedControl);

        if (!isTableTests) {
            switchToPropertiesTab("ROOT");
            setPropertyByToggleClick(SettingType.SETTER, Properties.expanded, true);
        }

        Wrap<? extends TableColumnHeader> header1 = getTableColumnHeaderWrap("items0");

        checkKeyCellsContent("items0-0", "items1-0", "items0-4", "items1-4", columns, rows);
        //Direct sort.
        header1.mouse().click();
        checkKeyCellsContent("items0-0", "items1-0", "items0-4", "items1-4", columns, rows);
        switchToPropertiesTab("items0");
        //Change comparator to reversed one.
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.comparator, CustomReversedComparator.class);
        checkTextFieldText(Properties.comparator, CustomReversedComparator.comparatorName);
        //Expect, that reversed sorting will be applied.
        checkKeyCellsContent("items0-4", "items1-4", "items0-0", "items1-0", columns, rows);
        header1.mouse().click();
        //Expect, that ascending sorting will be applied.
        checkKeyCellsContent("items0-0", "items1-0", "items0-4", "items1-4", columns, rows);
    }

    @Test(timeout = 300000)
    /**
     * Checks, that comparator property can be set by setter or any of bindings.
     * That is a standard test.
     */
    public void comparatorPropertyTest() throws InterruptedException {
        assertTrue(new TableColumn().getComparator().getClass().equals(TableColumn.DEFAULT_COMPARATOR.getClass()));
        setSize(200, 200);
        final int rows = 5;
        addColumn("items0", 0, true);
        setNewDataSize(rows);

        switchToPropertiesTab("items0");
        Wrap<? extends TableColumnHeader> header1 = getTableColumnHeaderWrap("items0");
        header1.mouse().click();
        checkKeyCellsContent("items0-0", "items0-4", rows);
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.comparator, CustomReversedComparator.class);
        checkTextFieldText(Properties.comparator, CustomReversedComparator.comparatorName);
        checkKeyCellsContent("items0-4", "items0-0", rows);
        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.comparator, CustomReversedComparator.class);
        checkTextFieldText(Properties.comparator, CustomReversedComparator.comparatorName);
        checkKeyCellsContent("items0-0", "items0-4", rows);
    }

    protected void checkKeyCellsContent(String upperText, String downText, int rows) {
        checkTextContainment(getCellWrap(0, 0), upperText);
        checkTextContainment(getCellWrap(0, rows - 1), downText);
    }

    @Test(timeout = 300000)
    /**
     * Standard test on graphic property: setting with setter, bidirectional of
     * unidirectional binding, checking with getter.
     */
    public void graphicPropertyTest() throws InterruptedException {
        assertNull(new TableColumn().getGraphic());
        setSize(200, 200);
        addColumn("items0", 0, true);
        setNewDataSize(5);

        switchToPropertiesTab("items0");
        final Wrap<? extends TableColumnHeader> header = getTableColumnHeaderWrap("items0");
        final Rectangle initialColumnNameCoords = header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds();

        selectObjectFromChoiceBox(SettingType.SETTER, Properties.graphic, javafx.scene.shape.Rectangle.class);
        checkTextFieldTextContaining(Properties.graphic, "Rectangle");
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (!(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds().equals(initialColumnNameCoords))
                        && header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).size() == 1) {
                    return true;
                } else {
                    return null;
                }
            }
        });
        checkRectanglesRelation(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds(), RectanglesRelations.RIGHTER,
                header.as(Parent.class, Node.class).lookup(javafx.scene.shape.Rectangle.class).wrap().getScreenBounds());

        selectObjectFromChoiceBox(SettingType.BIDIRECTIONAL, Properties.graphic, Group.class);
        checkTextFieldTextContaining(Properties.graphic, "Group");
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {
                if (!(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds().equals(initialColumnNameCoords))
                        && header.as(Parent.class, Node.class).lookup(Group.class).size() == 1) {
                    return true;
                } else {
                    return null;
                }
            }
        });
        checkRectanglesRelation(header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds(), RectanglesRelations.RIGHTER,
                header.as(Parent.class, Node.class).lookup(Group.class).wrap().getScreenBounds());

        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.graphic, null);
        checkTextFieldText(Properties.graphic, "null");
        new Waiter(new Timeout("", 500)).ensureState(new State() {
            public Object reached() {

                if ((header.as(Parent.class, Node.class).lookup(Text.class).wrap().getScreenBounds().equals(initialColumnNameCoords))
                        && header.as(Parent.class, Node.class).lookup(Group.class).size() == 0) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    @Test(timeout = 300000)
    public void reorderablePropertyTest() throws InterruptedException {
        final String ITEMS_0 = "items0";
        final String ITEMS_1 = "items1";

        setSize(200, 200);
        addColumn(ITEMS_0, 0, true);
        addColumn(ITEMS_1, 1, true);
        setNewDataSize(5);

        Wrap columnHeader1 = testedControl.as(Parent.class, Node.class).lookup(Text.class, new ByText(ITEMS_0)).wrap();
        Wrap columnHeader2 = testedControl.as(Parent.class, Node.class).lookup(Text.class, new ByText(ITEMS_1)).wrap();

        switchToPropertiesTab(ITEMS_0);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.reorderable, false);
        checkTextFieldText(Properties.reorderable, "false");
        columnHeader1.drag().dnd(columnHeader2, columnHeader2.getClickPoint());
        assertTrue(columnHeader1.getScreenBounds().x < columnHeader2.getScreenBounds().x);

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.reorderable, true);
        checkTextFieldText(Properties.reorderable, "true");
        columnHeader1.drag().dnd(columnHeader2, columnHeader2.getClickPoint().translate(5, 0));

        columnHeader1 = testedControl.as(Parent.class, Node.class).lookup(Text.class, new ByText(ITEMS_0)).wrap();
        columnHeader2 = testedControl.as(Parent.class, Node.class).lookup(Text.class, new ByText(ITEMS_1)).wrap();
        assertTrue(columnHeader1.getScreenBounds().x > columnHeader2.getScreenBounds().x);
    }

    @Test(timeout = 300000)
    /**
     * Standard test on resizable property test. Checked with getter, setter,
     * initial value, bindings, behavior is checked too. Checked for resizing of
     * nested columns and for autosizing cases.
     */
    public void resizablePropertyTest() throws InterruptedException {
        if (isTableTests) {
            assertTrue(new TableColumn().isResizable());
        } else {
            assertTrue(new TreeTableColumn().isResizable());
        }
        setSize(200, 200);
        addColumn("items0", 0, true);
        addColumn("items1", 1, true);
        createNestedColumn("nested", 0, 0, 1);
        setNewDataSize(5);

        final Wrap<? extends TableColumnHeader> items0 = getTableColumnHeaderWrap("items0");
        final Wrap<? extends TableColumnHeader> nested = getTableColumnHeaderWrap("nested");

        switchToPropertiesTab("items0");
        checkTextFieldValue(Properties.width, 80, 1);
        switchToPropertiesTab("nested");
        checkTextFieldValue(Properties.width, 160, 1);

        switchToPropertiesTab("items0");
        setPropertyByToggleClick(SettingType.SETTER, Properties.resizable, false);
        checkTextFieldText(Properties.resizable, "false");
        DnDInHorizontalDimnsion(getColumnResizablePoint(items0), 10);
        checkTextFieldValue(Properties.width, 80, 1);
        DnDInHorizontalDimnsion(getColumnResizablePoint(nested), 10);
        checkTextFieldValue(Properties.width, 80, 1);
        switchToPropertiesTab("nested");
        checkTextFieldValue(Properties.width, 170, 1);

        switchToPropertiesTab("items0");
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.resizable, true);
        checkTextFieldText(Properties.resizable, "true");
        DnDInHorizontalDimnsion(getColumnResizablePoint(items0), -10);
        checkTextFieldValue(Properties.width, 70, 1);
        DnDInHorizontalDimnsion(getColumnResizablePoint(nested), -10);
        checkTextFieldValue(Properties.width, 65, 3);
        switchToPropertiesTab("nested");
        checkTextFieldValue(Properties.width, 160, 1);

        switchToPropertiesTab("items0");
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.resizable, false);
        checkTextFieldText(Properties.resizable, "false");
        DnDInHorizontalDimnsion(getColumnResizablePoint(items0), 10);
        checkTextFieldValue(Properties.width, 65, 1);
        DnDInHorizontalDimnsion(getColumnResizablePoint(nested), 10);
        checkTextFieldValue(Properties.width, 65, 3);
        switchToPropertiesTab("nested");
        checkTextFieldValue(Properties.width, 170, 1);

        switchToPropertiesTab("items0");
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.resizable, true);
        checkTextFieldText(Properties.resizable, "true");
        DnDInHorizontalDimnsion(getColumnResizablePoint(items0), 50);

        Point endPoint = getColumnResizablePoint(items0);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.resizable, false);
        checkTextFieldText(Properties.resizable, "false");
        items0.mouse().click(1, getColumnResizablePoint(items0));
        items0.mouse().click(1, getColumnResizablePoint(items0));
        //Expect, that width doesn't change even on request of autosizing. Issue RT-25###.
        assertTrue(endPoint.equals(getColumnResizablePoint(items0)));
    }

    protected Point getColumnResizablePoint(Wrap<? extends TableColumnHeader> wrap) {
        return new Point(wrap.getScreenBounds().getX() + wrap.getScreenBounds().width, wrap.getScreenBounds().getY() + wrap.getScreenBounds().height / 2);
    }

    protected void DnDInHorizontalDimnsion(Point initialPoint, int movement) throws InterruptedException, InterruptedException {
        try {
            Point point = new Point(initialPoint.x + movement, initialPoint.y);
            Robot robot = new Robot();
            robot.mouseMove(initialPoint.x, initialPoint.y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            Thread.sleep(100);
            final int STEPS = 20;
            int differenceX = point.x - initialPoint.x;
            int differenceY = point.y - initialPoint.y;
            for (int i = 0; i <= STEPS; i++) {
                robot.mouseMove(initialPoint.x + differenceX * i / STEPS, initialPoint.y + differenceY * i / STEPS);
                Thread.sleep(20);
            }
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(SLEEP);
        } catch (Exception ex) {
            Logger.getLogger(TableViewNewTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test(timeout = 300000)
    /**
     * Test on width property - readonly one. Check correct behavior on resizing
     * for simple and nested columns
     */
    public void widthPropertyTest() throws InterruptedException {
        setSize(200, 200);
        addColumn("items0", 0, true);
        addColumn("items1", 1, true);
        createNestedColumn("nested", 0, 0, 1);
        setNewDataSize(5);

//        final Wrap<? extends TableColumnHeader> items0 = getTableColumnHeaderWrap("items0");
//        final Wrap<? extends TableColumnHeader> items1 = getTableColumnHeaderWrap("items1");
//        final Wrap<? extends TableColumnHeader> nested = getTableColumnHeaderWrap("nested");
        switchToPropertiesTab("items0");
        checkTextFieldValue(Properties.width, 80, 1);
        switchToPropertiesTab("items1");
        checkTextFieldValue(Properties.width, 80, 1);
        switchToPropertiesTab("nested");
        checkTextFieldValue(Properties.width, 160, 1);

        DnDInHorizontalDimnsion(getResizingPointOfColumn("nested"), +20);
        switchToPropertiesTab("items0");
        checkTextFieldValue(Properties.width, 90, 1);
        switchToPropertiesTab("items1");
        checkTextFieldValue(Properties.width, 90, 1);
        switchToPropertiesTab("nested");
        checkTextFieldValue(Properties.width, 180, 1);

        DnDInHorizontalDimnsion(getResizingPointOfColumn("items0"), -20);
        switchToPropertiesTab("items0");
        checkTextFieldValue(Properties.width, 70, 1);
        switchToPropertiesTab("items1");
        checkTextFieldValue(Properties.width, 90, 1);
        switchToPropertiesTab("nested");
        checkTextFieldValue(Properties.width, 160, 1);
    }

    @Test(timeout = 300000)
    /**
     * This test checks, that TableCell are resized correctly according to the
     * resizing of the tableColumnHeaders. That is, if you resize headers, do
     * autosizing, at least left and right borders have the same x-coordinates
     * as columnHeaders have.
     */
    public void correctAllocationOnResizingTest() throws InterruptedException {
        setSize(210, 210);
        addColumn("items0", 0, true);
        addColumn("items1", 1, true);
        createNestedColumn("nested", 0, 0, 1);
        final int rows = 5;
        setNewDataSize(rows);

        final Wrap<? extends TableColumnHeader> items0 = getTableColumnHeaderWrap("items0");
        final Wrap<? extends TableColumnHeader> items1 = getTableColumnHeaderWrap("items1");
        checkCorrectCoordinatesAllocation(items0, items1, rows + (isTableTests ? 0 : 1));

        DnDInHorizontalDimnsion(getResizingPointOfColumn("nested"), +20);
        checkCorrectCoordinatesAllocation(items0, items1, rows);

        DnDInHorizontalDimnsion(getResizingPointOfColumn("items1"), +20);
        checkCorrectCoordinatesAllocation(items0, items1, rows);

        DnDInHorizontalDimnsion(getResizingPointOfColumn("items0"), -20);
        checkCorrectCoordinatesAllocation(items0, items1, rows);

        Point colResizablePoint = items1.toLocal(getColumnResizablePoint(items1));

        items1.mouse().click(1, colResizablePoint);
        items1.mouse().click(1, colResizablePoint);

        checkCorrectCoordinatesAllocation(items0, items1, rows);
    }

    /**
     * Checks the state of control after removing all its observable data.
     */
    @Test(timeout = 300000)
    public void testAllDataRemoving() throws Throwable {
        final int COLS = 4;
        final int ROWS = 10;
        final String noContent = isTableTests ? "TableView_no_content" : "TreeTableView_no_content";
        final String noColumns = isTableTests ? "TableView_no_columns" : "TreeTableView_no_columns";

        setSize(200, 200);

        if (!isTableTests) {
            setPropertyByToggleClick(SettingType.SETTER, Properties.showRoot, false);
        }

        checkScreenshot(noColumns, testedControl);

        for (boolean bulkClear : new boolean[]{true, false}) {

            for (int i = 0; i < COLS; i++) {
                addColumn(String.format("column%d", i), i, true);
            }

            checkScreenshot(noContent, testedControl);

            if (bulkClear) {
                new GetAction<Object>() {
                    public void run(Object... parameters) throws Exception {
                        if (isTableTests) {
                            ((TableView) testedControl.getControl()).getColumns().clear();
                        } else {
                            ((TreeTableView) testedControl.getControl()).getColumns().clear();
                        }
                    }
                }.dispatch(testedControl.getEnvironment());
            } else {
                for (int i = COLS - 1; i >= 0; --i) {
                    removeColumns(i);
                }
            }

            checkScreenshot(noColumns, testedControl);
        }

        for (int i = 0; i < COLS; i++) {
            addColumn(String.format("column%d", i), i, true);
        }

        setNewDataSize(ROWS);

        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                Control control = testedControl.getControl();

                if (isTableTests) {
                    TableView tv = (TableView) control;
                    tv.getItems().clear();
                } else {
                    TreeTableView ttv = (TreeTableView) control;
                    ttv.getRoot().getChildren().clear();
                }
            }
        }.dispatch(testedControl.getEnvironment());

        //Wait for animation to finish
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }

        checkScreenshot(noContent, testedControl);

        for (int i = COLS - 1; i >= 0; --i) {
            removeColumns(i);
        }

        checkScreenshot(noColumns, testedControl);

        throwScreenshotError();
    }

    @Test(timeout = 300000)
    public void columnHeaderAndRowCustomisation() throws InterruptedException {
        setSize(200, 200);
        addColumn("items0", 0, true);
        addColumn("items1", 1, true);
        createNestedColumn("nested", 0, 0, 1);
        setNewDataSize(5);

        checkHeaderCustomizedState(false);

        clickButtonForTestPurpose(javafx.commons.Consts.REPLACE_SKIN_IMPLEMENTATION_BUTTON_ID);

        checkHeaderCustomizedState(true);
    }

    private void checkHeaderCustomizedState(final boolean isCustomized) {
        Lookup<TableColumnHeader> headers = testedControl.as(Parent.class, Node.class).lookup(TableColumnHeader.class);
        Wrap<TableHeaderRow> headerRow = testedControl.as(Parent.class, Node.class).lookup(TableHeaderRow.class).wrap();

        for (int i = 0; i < headers.size(); i++) {
            final String columnHeaderId = getId(headers.wrap(i));

            testedControl.waitState(new State() {
                public Object reached() {
                    final String expectedId = isCustomized ? javafx.commons.Consts.CUSTOM_IMPLEMENTATION_MARKER : null;
                    if (expectedId == null) {
                        if (columnHeaderId == null) {
                            return Boolean.TRUE;
                        } else {
                            return null;
                        }
                    } else {
                        return expectedId.equals(columnHeaderId);
                    }
                }
            });
        }

        final String headerRowId = getId(headerRow);
        testedControl.waitState(new State() {
            public Object reached() {
                final String expectedId = isCustomized ? javafx.commons.Consts.CUSTOM_IMPLEMENTATION_MARKER : null;
                if (expectedId == null) {
                    if (headerRowId == null) {
                        return Boolean.TRUE;
                    } else {
                        return null;
                    }
                } else {
                    return expectedId.equals(headerRowId);
                }
            }
        });
    }

    private String getId(final Wrap<? extends Node> node) {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(node.getControl().getId());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /**
     * Checks control behavior when sortOrder list is changed.
     */
    @Test(timeout = 300000)
    public void testSortOrderAPI() throws InterruptedException {
        testSortTypeCombinations(false);
    }

    @Test(timeout = 300000)
    public void testOnEditStartCancel() throws InterruptedException {
        testDifferentCellEditors(false);
    }

    @Test(timeout = 300000)
    public void testOnEditStartCommit() throws InterruptedException {
        testDifferentCellEditors(true);
    }

    /**
     * Tests that sorting in column may be disabled via the API.
     */
    @Test(timeout = 380000)
    public void columnSortablePropertyTest() throws InterruptedException {
        testSortTypeCombinations(true);
    }

    /**
     * Tests, that cells/rows correctly answer on a question about row, column,
     * and control.
     */
    @Test(timeout = 300000)
    public void columnPropertyOfCellTest() throws InterruptedException {
        setSize(210, 210);
        addColumn("items0", 0, false);
        addColumn("items1", 1, false);
        final int rows = 5;
        setNewDataSize(rows);

        Class<?> rowClass = isTableTests ? TableRow.class : TreeTableRow.class;
        Class<?> cellClass = isTableTests ? TableCell.class : TreeTableCell.class;
        List<Cell> allCells = new ArrayList<Cell>();
        final Control control = testedControl.getControl();
        List<TableColumnBase> columns = new GetAction<List<TableColumnBase>>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTableTests) {
                    setResult(((TableView) control).getColumns());
                } else {
                    setResult(((TreeTableView) control).getColumns());
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());

        final Lookup<?> rowsLookup = testedControl.as(Parent.class, Node.class).lookup(rowClass);
        for (int i = 0; i < rowsLookup.size(); i++) {
            Wrap<?> rowWrap = rowsLookup.wrap(i);
            final Object row = rowWrap.getControl();
            Control controlResRow = new GetAction<Control>() {
                @Override
                public void run(Object... os) throws Exception {
                    if (isTableTests) {
                        setResult(((TableRow) row).getTableView());
                    } else {
                        setResult(((TreeTableRow) row).getTreeTableView());
                    }
                }
            }.dispatch(Root.ROOT.getEnvironment());
            assertEquals(controlResRow, control);

            final Lookup<Cell> cellsInRowLookup = rowWrap.as(Parent.class, Node.class).lookup(Cell.class);
            for (int j = 0; j < cellsInRowLookup.size(); j++) {
                Object rowRes = new GetAction<Object>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        if (isTableTests) {
                            setResult(((TableCell) os[0]).getTableRow());
                        } else {
                            setResult(((TreeTableCell) os[0]).getTreeTableRow());
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment(), cellsInRowLookup.get(j));
                assertEquals(rowRes, row);

                Control controlRes = new GetAction<Control>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        if (isTableTests) {
                            setResult(((TableCell) os[0]).getTableView());
                        } else {
                            setResult(((TreeTableCell) os[0]).getTreeTableView());
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment(), cellsInRowLookup.get(j));
                assertEquals(controlRes, control);

                TableColumnBase column = new GetAction<TableColumnBase>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        if (isTableTests) {
                            setResult(((TableCell) os[0]).getTableColumn());
                        } else {
                            setResult(((TreeTableCell) os[0]).getTableColumn());
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment(), cellsInRowLookup.get(j));
                assertEquals(column, columns.get(j));

                allCells.add(cellsInRowLookup.get(j));
            }
        }

        assertEquals(testedControl.as(Parent.class, Node.class).lookup(cellClass).size(), allCells.size());
        assertTrue(allCells.size() >= 2 * rows);
    }

    /**
     * Checks that when columns collection is ordered it will be rendered
     * properly.
     */
    @Test(timeout = 300000)
    public void columnsOrderTest() throws InterruptedException {
        setSize(210, 210);
        final int COLS = 3;
        final int ROWS = 5;

        setNewDataSize(ROWS);

        final String[] names = new String[]{"A", "B", "C"};

        for (String n : names) {
            addColumn(n, 0);
        }

        if (isTableTests) {
            checkKeyCellsContent("C-0", "A-0", "C-4", "A-4", COLS, ROWS);
        } else {
            setShowRoot(false);
            checkKeyCellsContent("C-1", "A-1", "C-5", "A-5", COLS, ROWS);
        }

        sortColumnsByName(true);

        if (isTableTests) {
            checkKeyCellsContent("A-0", "C-0", "A-4", "C-4", COLS, ROWS);
        } else {
            checkKeyCellsContent("A-1", "C-1", "A-5", "C-5", COLS, ROWS);
        }

        //check column headers
        final Lookup<TableColumnHeader> lookup = testedControl.waitState(new State<Lookup<TableColumnHeader>>() {
            public Lookup reached() {
                final Lookup lookup = parent.lookup(TableColumnHeader.class, new LookupCriteria<TableColumnHeader>() {
                    public boolean check(TableColumnHeader cntrl) {
                        return (cntrl.getBoundsInLocal().getMaxY() > 15) && (!(cntrl instanceof NestedTableColumnHeader));
                    }
                });

                return (lookup.size() == 3) ? lookup : null;
            }

            @Override
            public String toString() {
                return "[Correct number of column headers]";
            }
        });

        for (int i = 0; i < lookup.size(); i++) {
            final int x = i;

            assertEquals(names[i], new GetAction<String>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(((Label) lookup.wrap(x).getControl().getChildrenUnmodifiable().get(0)).getText());
                }
            }.dispatch(testedControl.getEnvironment()));
        }
    }

    /**
     * Checks the public 'sort' method and 'onSort' event. Features are
     * described here:
     * <a href="http://javafx-jira.kenai.com/browse/RT-26505">RT-19482</a>
     * and here <a
     * href="http://javafx-jira.kenai.com/browse/RT-26505">RT-17550</a>
     */
    @Test(timeout = 300000)
    public void onSortTest() throws InterruptedException {
        setSize(250, 250);
        final int COLS = 3;

        createRowsForSortPurposes(COLS);
        String[][] testData = NewTableViewApp.getDataForSorting(COLS);

        checkCounterValue(COUNTER_ON_SORT, 0);

        new GetAction<Void>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTableTests) {
                    ((TableView) testedControl.getControl()).setSortPolicy(getAlwaysReverseTableSortPolicy());
                } else {
                    Callback<TreeTableView<DataItem>, Boolean> treeTableSortPolicy = getAlwaysReverseTreeTableSortPolicy();
                    ((TreeTableView) testedControl.getControl()).setSortPolicy(treeTableSortPolicy);
                }
            }
        }.dispatch(testedControl.getEnvironment());

        checkCounterValue(COUNTER_ON_SORT, 1);
        reverseTestData(testData);
        assertTrue("Sort order is wrong", checkSortResult(testData));

        createRowsForSortPurposes(COLS);
        testData = NewTableViewApp.getDataForSorting(COLS);
        assertTrue("Sort order is wrong", checkSortResult(testData));

        new GetAction<Void>() {
            @Override
            public void run(Object... os) throws Exception {
                if (isTableTests) {
                    ((TableView) testedControl.getControl()).sort();
                } else {
                    ((TreeTableView) testedControl.getControl()).sort();
                }
            }
        }.dispatch(testedControl.getEnvironment());

        reverseTestData(testData);
        checkCounterValue(COUNTER_ON_SORT, 2);
        assertTrue("Sort order is wrong", checkSortResult(testData));
    }

    /**
     * Checks the returning to the unsorted state.
     * Feature is described here: <a href="https://javafx-jira.kenai.com/browse/RT-19487">RT-19487</a>
     */
    @Test(timeout = 60000)
    public void SortedListAsModel() throws InterruptedException {
        if (!isTableTests) {
            return;//pass all the time as this feature is not applicable to the tree table.
        }
        setSize(250, 250);

        final int COLS = 3;

        createRowsForSortPurposes(COLS);

        clickButtonForTestPurpose(BTN_SET_SORTED_LIST_FOR_MODEL_ID);

        String[][] testData = NewTableViewApp.getDataForSorting(COLS);

        List<ColumnState> columnStates = new ArrayList<ColumnState>(COLS);

        //Ascending
        getTableColumnHeaderWrap("column 2").mouse().click();
        columnStates.add(new ColumnState(2, ColumnState.SortType.ASCENDING));
        sortTestData(testData, columnStates);
        assertTrue("Sort order is wrong", checkSortResult(testData));

        //Descending
        getTableColumnHeaderWrap("column 2").mouse().click();
        columnStates.clear();
        columnStates.add(new ColumnState(2, ColumnState.SortType.DESCENDING));
        sortTestData(testData, columnStates);
        assertTrue("Sort order is wrong", checkSortResult(testData));

        //Initial state
        getTableColumnHeaderWrap("column 2").mouse().click();
        testData = NewTableViewApp.getDataForSorting(COLS);
        assertTrue("Sort order is wrong", checkSortResult(testData));
    }

    /*
     * Helper methods
     */
    private void testSortTypeCombinations(boolean testSortableProp) throws InterruptedException {
        setSize(250, 250);

        final int COLS = 3;
        createRowsForSortPurposes(COLS);

        String[][] testData = NewTableViewApp.getDataForSorting(COLS);

        //Check initial state
        assertTrue("Initial sort order is wrong", checkSortResult(testData));

        List<ColumnState> columnStates = new ArrayList<ColumnState>(COLS);
        ColumnState.SortType[] sortTypes = ColumnState.SortType.values();

        int sortTypesCount = sortTypes.length + 1;

        boolean[] sortableCols = {true, true, true};

        for (int col = 0; col < COLS; col++) {

            if (!testSortableProp) {
                //When all columns are sortable it is enough to
                //run outer loop once to test different combinations
                //of the sort orders
                if (col > 0) {
                    break;
                }
            } else {
                //Disable sortable property of each col sequentialy and
                //test all sort type combinations.
                sortableCols[col] = false;
                System.out.println(String.format("Column %d.sortable = false", col));
            }

            for (int x = 0; x < sortTypesCount; x++) {
                for (int y = 0; y < sortTypesCount; y++) {
                    for (int z = 0; z < sortTypesCount; z++) {

                        columnStates.clear();

                        if (x != 0) {
                            columnStates.add(new ColumnState(0, sortTypes[x - 1], sortableCols[0]));
                        }
                        if (y != 0) {
                            columnStates.add(new ColumnState(1, sortTypes[y - 1], sortableCols[1]));
                        }
                        if (z != 0) {
                            columnStates.add(new ColumnState(2, sortTypes[z - 1], sortableCols[2]));
                        }

                        for (int i = 0; i < columnStates.size(); i++) {
                            ColumnState pr = columnStates.get(i);
                            System.out.format("Column %d; Sort type %s; ", pr.columnIndex, pr.sortType);
                        }
                        setSortOrder(columnStates);
                        checkSortNodes(columnStates);
                        sortTestData(testData, columnStates);
                        assertTrue("Sort order is wrong", checkSortResult(testData));
                    }
                }
            }
        }
    }

    private void checkSortNodes(final List<ColumnState> columnStates) {

        int sortableColsCount = 0;

        for (ColumnState state : columnStates) {
            if (state.sortable) {
                ++sortableColsCount;
            }
        }

        int curPosInSortOrder = 0;

        for (final ColumnState state : columnStates) {
            Wrap<? extends TableColumnHeader> header = getTableColumnHeaderWrap(String.format("column %d", state.columnIndex));
            final Parent prnt = header.as(Parent.class, Node.class);

            header.waitState(new State<Boolean>() {
                public Boolean reached() {
                    return ((state.sortable ? 1 : 0) == prnt.lookup(new ByStyleClass("arrow")).size()) ? Boolean.TRUE : null;
                }

                public String tiString() {
                    return state.sortable ? "[Sort node not found in sortable column]" : "[Sort node found in unsortable column]";
                }
            });

            if (state.sortable) {
                ++curPosInSortOrder;

                if (sortableColsCount > 1) {
                    assertEquals("[Sort order dots container not found]", 1, prnt.lookup(new ByStyleClass("sort-order-dots-container")).size());
                    assertEquals("[Sort order dots not found]", curPosInSortOrder, prnt.lookup(new LookupCriteria<Node>() {
                        public boolean check(Node node) {
                            return node.getStyleClass().contains("sort-order-dot")
                                    && node.getStyleClass().contains(state.sortType.toString().toLowerCase());
                        }
                    }).size());
                }
            }
        }
    }

    void setSortOrder(final List<ColumnState> newColumnStates) {
        new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {
                ObservableList<TableColumnBase> columns = observableArrayList(getTopLevelColumns());

                ObservableList<TableColumnBase> columnSortOrder
                        = isTableTests
                        ? ((TableView) testedControl.getControl()).getSortOrder()
                        : ((TreeTableView) testedControl.getControl()).getSortOrder();

                columnSortOrder.clear();

                for (ColumnState columnState : newColumnStates) {
                    TableColumnBase col = columns.get(columnState.columnIndex);
                    setSortType(col, columnState.sortType);
                    checkSortType(col, columnState.sortType);
                    col.setSortable(columnState.sortable);
                    columnSortOrder.add(col);
                }
            }
        }.dispatch(testedControl.getEnvironment());

        //Animation delay
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
    }

    static void checkSortType(TableColumnBase col, ColumnState.SortTypeProvider type) {
        if (isTableTests) {
            assertTrue(((TableColumn) col).getSortType().equals(type.forTableColumn()));
        } else {
            assertTrue(((TreeTableColumn) col).getSortType().equals(type.forTreeTableColumn()));
        }
    }

    static void setSortType(TableColumnBase col, ColumnState.SortTypeProvider type) {
        if (isTableTests) {
            ((TableColumn) col).setSortType(type.forTableColumn());
        } else {
            ((TreeTableColumn) col).setSortType(type.forTreeTableColumn());
        }
    }

    boolean checkSortResult(String[][] testData) {
        Boolean state = Boolean.TRUE;

        for (int j = 0; j < testData[0].length; j++) {
            final int fj = j;
            for (int i = 0; i < testData.length; i++) {
                final int fi = i;
                final Wrap<? extends IndexedCell> cellWrap = getCellWrap(j, i);
                final String expected = testData[i][j];

                state = cellWrap.waitState(new State<Boolean>() {
                    public Boolean reached() {
                        String actual = cellWrap.getControl().getText();
                        if (!expected.equals(actual)) {
                            System.out.println("i = " + fi + " j = " + fj);
                            System.out.println(String.format("exp|act:%s|%s", expected, actual));
                        }
                        return expected.equals(actual) ? Boolean.TRUE : Boolean.FALSE;
                    }
                });
            }

            if (Boolean.FALSE == state) {
                System.out.println(String.format("Column %d ended.", j));
            }
        }
        return state.booleanValue();
    }

    public void checkCorrectCoordinatesAllocation(Wrap<? extends TableColumnHeader> header1, Wrap<? extends TableColumnHeader> header2, int rows) {
        for (int i = 0; i < rows; i++) {
            Wrap wrap1 = getCellWrap(0, i);
            Wrap wrap2 = getCellWrap(1, i);

            if (false) {
                System.out.println("wrap1.getScreenBounds().x = " + wrap1.getScreenBounds().x);
                System.out.println("header1.getScreenBounds().x = " + header1.getScreenBounds().x);
                System.out.println("wrap2.getScreenBounds().x = " + wrap2.getScreenBounds().x);
                System.out.println("header2.getScreenBounds().x = " + header2.getScreenBounds().x);
                System.out.println("wrap1.getScreenBounds().x + wrap1.getScreenBounds().width = " + (wrap1.getScreenBounds().x + wrap1.getScreenBounds().width));
                System.out.println("header1.getScreenBounds().x + header1.getScreenBounds().width = " + (header1.getScreenBounds().x + header1.getScreenBounds().width));
                System.out.println("wrap2.getScreenBounds().x + wrap2.getScreenBounds().width = " + (wrap2.getScreenBounds().x + wrap2.getScreenBounds().width));
                System.out.println("header2.getScreenBounds().x + header2.getScreenBounds().width = " + (header2.getScreenBounds().x + header2.getScreenBounds().width));
            }

            if (isTableTests) {
                assertEquals(wrap1.getScreenBounds().x, header1.getScreenBounds().x, 0);
            } else {
                //For treeTable - there are nodes for showing tree structure
                //(arrows), so cells are less, and left border is not aligned
                //according to the left border of the column header.
                assertTrue(wrap1.getScreenBounds().x > header1.getScreenBounds().x);
                assertTrue(wrap1.getScreenBounds().x < wrap2.getScreenBounds().x);
                assertTrue(wrap1.getScreenBounds().x < wrap1.getScreenBounds().x + wrap1.getScreenBounds().width);
            }
            assertEquals(wrap2.getScreenBounds().x, header2.getScreenBounds().x, 0);
            assertEquals(wrap1.getScreenBounds().x + wrap1.getScreenBounds().width, header1.getScreenBounds().x + header1.getScreenBounds().width, 0);
            assertEquals(wrap2.getScreenBounds().x + wrap2.getScreenBounds().width, header2.getScreenBounds().x + header2.getScreenBounds().width, 0);
        }
    }

    protected void testDifferentCellEditors(boolean commit) throws InterruptedException {
        setSize(212.5, 209.3);
        setPropertyByToggleClick(SettingType.SETTER, Properties.editable, true);

        addColumn("colA", 0, true);
        addColumn("colB", 1, true);
        addColumn("colC", 2, true);

        final int ROWS = 10;
        setNewDataSize(ROWS);

        clickButtonForTestPurpose(BTN_SET_ON_EDIT_EVENT_HANDLERS);

        int editStartCounter = 0;
        int editCancelCounter = 0;
        int editCommitCounter = 0;

        final class GetCellControlClassAction extends GetAction<Class> {

            int rowIndex = -1;
            int columnIndex = -1;

            public GetCellControlClassAction(int rowIndex, int columnIndex) {
                this.columnIndex = columnIndex;
                this.rowIndex = rowIndex;
            }

            @Override
            public void run(Object... os) throws Exception {
                TableCell cell = getCellWrap(columnIndex, rowIndex).getControl();
                setResult((null != cell.getGraphic()) ? cell.getGraphic().getClass() : Object.class);
            }

        }

        Wrap<? extends TableCell> cellWrap;
        for (CellEditorType type : CellEditorType.values()) {

            for (boolean isCustom : new boolean[]{true, false}) {

                switch (type) {
                    case TEXT_FIELD:
                        setCellEditor(CellEditorType.TEXT_FIELD, isCustom);
                        cellWrap = getCellWrap(1, 4);
                        String initialText = getCellText(cellWrap);

                        cellWrap.mouse().click();
                        cellWrap.keyboard().pushKey(KeyboardButtons.F2);
                        cellWrap.keyboard().pushKey(KeyboardButtons.A, KeyboardModifiers.CTRL_DOWN_MASK);
                        cellWrap.keyboard().pushKey(KeyboardButtons.A);
                        cellWrap.keyboard().pushKey(KeyboardButtons.B);
                        cellWrap.keyboard().pushKey(KeyboardButtons.C);

                        final TextField textField = (TextField) cellWrap.as(Parent.class, Node.class).lookup(TextField.class).get();
                        cellWrap.waitState(new State() {

                            @Override
                            public Object reached() {
                                if (textField == null) {
                                    System.out.println("TextField null");
                                    return null;
                                }
                                if (textField.getText().equals("abc")) {
                                    return true;
                                } else {
                                    return null;
                                }
                            }
                        });

                        cellWrap.keyboard().pushKey(commit ? KeyboardButtons.ENTER : KeyboardButtons.ESCAPE);

                        if (commit) {
                            assertEquals("Text didn't changed", "abc", getCellText(cellWrap));
                        } else {
                            assertEquals("[Text changed unexpectedly]", initialText, getCellText(cellWrap));
                        }
                        break;
                    case COMBOBOX:
                        setCellEditor(CellEditorType.COMBOBOX, isCustom);
                        cellWrap = getCellWrap(1, 4);

                        GetAction<Class> getCellClassAction = new GetCellControlClassAction(4, 1);
                        Class cellControlType = getCellClassAction.dispatch(cellWrap.getEnvironment());
                        while ( !ComboBox.class.equals(cellControlType) )
                        {
                            cellWrap.mouse().click();
                            cellControlType = getCellClassAction.dispatch(cellWrap.getEnvironment());
                        }

                        Wrap<ComboBox> combobox = cellWrap.as(Parent.class, Node.class).lookup(ComboBox.class).wrap();

                        //Select nothing
                        if (!commit) {
                            combobox.mouse().click();
                            assertTrue("Popup hasn't appeared",
                                        new GetAction<Boolean>() {
                                            @Override
                                            public void run(Object... os) throws Exception {
                                                Wrap<? extends Scene> popupScene = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).wrap(0);
                                                setResult(popupScene.getControl().getWindow().showingProperty().getValue());
                                            }
                                        }.dispatch(Root.ROOT.getEnvironment()));

                            //First escape to close popup
                            scene.keyboard().pushKey(KeyboardButtons.ESCAPE);
                            //Second escape to cancel editing
                            scene.keyboard().pushKey(KeyboardButtons.ESCAPE);
                        } else {
                            combobox.as(Selectable.class).selector().select("colB-1");
                        }
                        break;
                    case CHOICEBOX:
                        setCellEditor(CellEditorType.CHOICEBOX, isCustom);
                        cellWrap = getCellWrap(1, 4);

                        getCellClassAction = new GetCellControlClassAction(4, 1);
                        cellControlType = getCellClassAction.dispatch(cellWrap.getEnvironment());
                        while ( !ChoiceBox.class.equals(cellControlType) )
                        {
                            cellWrap.mouse().click();
                            cellControlType = getCellClassAction.dispatch(cellWrap.getEnvironment());
                        }

                        Wrap<ChoiceBox> choicebox = cellWrap.as(Parent.class, Node.class).lookup(ChoiceBox.class).wrap();

                        //Select nothing
                        if (!commit) {
                            choicebox.mouse().click();

                            choicebox.waitState(new State<Boolean>() {
                                @Override
                                public Boolean reached() {
                                    return new GetAction<Boolean>() {
                                               @Override
                                               public void run(Object... os) throws Exception {
                                                   Wrap<? extends Scene> popupScene = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).wrap(0);
                                                   setResult(popupScene.getControl().getWindow().showingProperty().getValue());
                                               }
                                           }.dispatch(Root.ROOT.getEnvironment())
                                           ? Boolean.TRUE : null;
                                }

                                @Override
                                public String toString() {
                                    return "[Popup expected to appear]";
                                }
                            });
                            //First escape to close popup
                            scene.keyboard().pushKey(KeyboardButtons.ESCAPE);
                            //Second escape to cancel editing
                            scene.keyboard().pushKey(KeyboardButtons.ESCAPE);
                        } else {
                            choicebox.as(Selectable.class).selector().select("colB-1");
                        }
                        break;
                }

                checkCounterValue(COUNTER_EDIT_START, ++editStartCounter);

                if (commit) {
                    ++editCommitCounter;
                } else {
                    ++editCancelCounter;
                }

                checkCounterValue(COUNTER_EDIT_CANCEL, editCancelCounter);
                checkCounterValue(COUNTER_EDIT_COMMIT, editCommitCounter);
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void fixedCellSizePropertyTest() throws InterruptedException {
        setSize(200, 200);
        addColumn("items0", 0, true);
        addColumn("items1", 1, true);
        createNestedColumn("nested", 0, 0, 1);
        setNewDataSize(5);
        fixedCellSizePropertyTestCommon(testedControl, Properties.fixedCellSize, 24);
    }

    @Smoke
    @Test(timeout = 300000)
    public void fixedCellSizePropertyCSSTest() throws InterruptedException {
        setSize(200, 200);
        addColumn("items0", 0, true);
        addColumn("items1", 1, true);
        createNestedColumn("nested", 0, 0, 1);
        setNewDataSize(5);
        fixedCellSizePropertyTestCommon(testedControl, Properties.fixedCellSize, 24);
    }

    @Test(timeout = 300000)
    public void styleOnCellsApplyingTest() throws InterruptedException, Throwable {
        setSize(200, 200);
        addColumn("items0", 0, true);
        addColumn("items1", 1, true);
        createNestedColumn("nested", 0, 0, 1);
        setNewDataSize(5);

        final Wrap<? extends TableColumnHeader> nested = getTableColumnHeaderWrap("nested");
        final Wrap<? extends TableColumnHeader> items0 = getTableColumnHeaderWrap("items1");

        applyStyleOnColumn(nested.getControl().getTableColumn(), "-fx-background-color: blue;");
        applyStyleOnColumn(items0.getControl().getTableColumn(), "-fx-background-color: red;");

        checkScreenshot((isTableTests ? "TableView-" : "TreeTableView-") + "styleOnColumnApplication", testedControl);
        throwScreenshotError();
    }

    protected void applyStyleOnColumn(final TableColumnBase column, final String style) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                column.setStyle(style);
            }
        }.dispatch(testedControl.getEnvironment());
    }
}
