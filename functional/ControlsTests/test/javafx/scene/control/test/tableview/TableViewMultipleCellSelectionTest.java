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
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.test.util.TableListCommonTests;
import junit.framework.Assert;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Alexander Kirov
 */
public class TableViewMultipleCellSelectionTest extends TestBase {

    @Smoke
    //@Test(timeout = 30000)
    //Commented out due to https://javafx-jira.kenai.com/browse/RT-33174
    public void keyboardMultipleCellSelectionTest() throws Throwable {
        keyboardMultipleCellSelectionCycle(false);
    }

    @Smoke
    //@Test(timeout = 30000)
    //Commented out due to https://javafx-jira.kenai.com/browse/RT-33174
    public void keyboardMultipleCellSelectionDiscontinuousTest() throws Throwable {
        keyboardMultipleCellSelectionCycle(true);
    }

    @Smoke
    @Test(timeout = 300000)
    public void singleCellSelectionTest() throws Throwable {
        singleCellSelectionWrap.mouse().click();
        selectionHelper.setSingleCell(true);
        selectionCycle(null);
        selectionCycle(KeyboardButtons.SHIFT);
        selectionCycle(KeyboardButtons.CONTROL);
    }

    @Smoke
    //@Test(timeout = 30000)
    //Commented out due to https://javafx-jira.kenai.com/browse/RT-33174
    public void multipleCellSelectionTest() throws Throwable {
        enableMultipleCellSelection();
        selectionCycle(null);
        selectionCycle(KeyboardButtons.SHIFT);
        selectionCycle(KeyboardButtons.CONTROL);
        selectionCycle(KeyboardButtons.CONTROL);
    }

    @Smoke
    //@Test(timeout = 60000)
    //Commented out due to https://javafx-jira.kenai.com/browse/RT-33174
    public void keyboardMultipleCellScrollTest() throws Throwable {
        multipleSelectionWrap.mouse().click();
        singleCellSelectionWrap.mouse().click();
        scrollTo(0, 0);
        getCellWrap(0, 0).mouse().click();
        keyboardCycle(TableViewApp.DATA_ITEMS_NUM, KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        keyboardCycle(TableViewApp.DATA_ITEMS_NUM, KeyboardButtons.RIGHT, KeyboardModifiers.SHIFT_DOWN_MASK);
        assertTrue(tableViewWrap.getScreenBounds().contains(getCellWrap(TableViewApp.DATA_FIELDS_NUM - 1, TableViewApp.DATA_ITEMS_NUM - 1).getScreenBounds()));
        scrollTo(0, 0);
        getCellWrap(0, 0).mouse().click();
        keyboardCycle(TableViewApp.DATA_ITEMS_NUM, KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        keyboardCycle(TableViewApp.DATA_ITEMS_NUM, KeyboardButtons.RIGHT, CTRL_DOWN_MASK_OS);
        assertTrue(tableViewWrap.getScreenBounds().contains(getCellWrap(TableViewApp.DATA_FIELDS_NUM - 1, TableViewApp.DATA_ITEMS_NUM - 1).getScreenBounds()));
    }

    /**
     * After the first upper cell have been selected it is supposed to remain
     * selected when one applies deselection using SHIFT + UP. Fails due to:
     * RT-21373
     */
    @Smoke
    @Test(timeout = 300000)
    public void testDeselectionNearTopBoundary() throws Throwable {
        enableMultipleCellSelection();

        scrollTo(0, 0);
        getCellWrap(0, 0).mouse().click();

        int steps = 1;

        KeyboardButtons btn = KeyboardButtons.DOWN;
        KeyboardModifiers modifier = KeyboardModifiers.SHIFT_DOWN_MASK;

        for (int i = 0; i < steps; i++) {
            tableViewWrap.keyboard().pushKey(btn, modifier);
            selectionHelper.push(btn, modifier);
        }
        checkSelection();

        //Deselect
        //And add one more step to cross the boundary
        btn = KeyboardButtons.UP;
        for (int i = 0; i < steps + 1; i++) {
            tableViewWrap.keyboard().pushKey(btn, modifier);
            selectionHelper.push(btn, modifier);
        }
        //It is expected that one cell remains selected
        checkSelection();
    }

    /**
     * Selects cells with SHIFT + DOWN and pushes this key combination one more
     * time to check that selection remains.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testDeselectionNearBottomBoundary() throws Throwable {
        enableMultipleCellSelection();

        scrollTo(0, 0);
        getCellWrap(0, 0).mouse().click();

        KeyboardButtons btn = KeyboardButtons.DOWN;
        KeyboardModifiers modifier = KeyboardModifiers.SHIFT_DOWN_MASK;

        for (int i = 0; i < DATA_ITEMS_NUM; i++) {
            tableViewWrap.keyboard().pushKey(btn, modifier);
            selectionHelper.push(btn, modifier);
            checkSelection();
        }
    }

    /**
     * Deselects selected cell with SHIFT + LEFT and then pushes this
     * combination once more to check that selection remains.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testDeselectionNearLeftBoundary() {
        enableMultipleCellSelection();

        scrollTo(0, 0);
        KeyboardModifiers ctrl = CTRL_DOWN_MASK_OS;
        Wrap<? extends IndexedCell> cellWrap = getCellWrap(0, 0);

        cellWrap.mouse().click(1, cellWrap.getClickPoint(), Mouse.MouseButtons.BUTTON1, ctrl);
        selectionHelper.click(0, 0);
        checkSelection();

        KeyboardButtons btn = KeyboardButtons.LEFT;
        KeyboardModifiers shift = KeyboardModifiers.SHIFT_DOWN_MASK;

        for (int i = 0; i < 1; i++) {
            tableViewWrap.keyboard().pushKey(btn, shift);
            selectionHelper.push(btn, shift);
        }
        checkSelection();

        //Select the entire row
        btn = KeyboardButtons.RIGHT;
        for (int i = 0; i < DATA_FIELDS_NUM; i++) {
            tableViewWrap.keyboard().pushKey(btn, shift);
            selectionHelper.push(btn, shift);
        }
        checkSelection();

        //Apply combo once more to ensure that it doesn't affect selection
        tableViewWrap.keyboard().pushKey(btn, shift);
        selectionHelper.push(btn, shift);
        checkSelection();
    }

    /**
     * Tests selection both in horizontal and vertical direction. Select 4 items
     * in each direction clockwise. It is assumed that there will be 12 cells
     * selected which form a rectangle with 4 unselected cells in the middle.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testAreaSelection() {
        multipleSelectionWrap.mouse().click();
        singleCellSelectionWrap.mouse().click();

        scrollTo(0, 0);
        getCellWrap(0, 0).mouse().click();

        KeyboardButtons btn = KeyboardButtons.RIGHT;
        KeyboardModifiers mod = KeyboardModifiers.SHIFT_DOWN_MASK;

        final int SELECTION_COUNT = 3;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            tableViewWrap.keyboard().pushKey(btn, mod);
            checkSelectedCellVisible();
        }

        Set<Point> helperCells = new HashSet<Point>(12);
        for (int x = 0; x < 4; x++) {
            helperCells.add(new Point(x, 0));
        }

        Set<Point> selectedCells;
        selectedCells = TestBaseCommon.getSelected(testedControl);

        final String msg = "Selected cells and expected cells do not match";
        Assert.assertTrue(msg, helperCells.equals(selectedCells));

        btn = KeyboardButtons.DOWN;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            tableViewWrap.keyboard().pushKey(btn, mod);
            checkSelectedCellVisible();
        }

        for (int y = 0; y < 4; y++) {
            helperCells.add(new Point(3, y));
        }

        selectedCells = TestBaseCommon.getSelected(testedControl);
        Assert.assertTrue(msg, helperCells.equals(selectedCells));

        btn = KeyboardButtons.LEFT;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            tableViewWrap.keyboard().pushKey(btn, mod);
            checkSelectedCellVisible();
        }

        for (int x = 3; x >= 0; x--) {
            helperCells.add(new Point(x, 3));
        }

        selectedCells = TestBaseCommon.getSelected(testedControl);
        Assert.assertTrue(msg, helperCells.equals(selectedCells));

        btn = KeyboardButtons.UP;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            tableViewWrap.keyboard().pushKey(btn, mod);
            checkSelectedCellVisible();
        }

        for (int y = 3; y >= 0; y--) {
            helperCells.add(new Point(0, y));
        }

        selectedCells = TestBaseCommon.getSelected(testedControl);
        Assert.assertTrue(msg, helperCells.equals(selectedCells));
    }

    /**
     * Deselects selected cell with SHIFT + RIGHT and then pushes this
     * combination once more to check that selection remains.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testDeselectionNearRightBoundary() {
        enableMultipleCellSelection();

        final int COL_IDX = DATA_FIELDS_NUM - 1;
        final int ROW_IDX = DATA_ITEMS_NUM / 2;
        scrollTo(COL_IDX, ROW_IDX);
        getCellWrap(COL_IDX, ROW_IDX).mouse().click();

        testedControl.keyboard().pushKey(KeyboardButtons.SPACE);
        selectionHelper.click(COL_IDX, ROW_IDX);

        KeyboardButtons btn = KeyboardButtons.RIGHT;
        KeyboardModifiers shift = KeyboardModifiers.SHIFT_DOWN_MASK;

        //Try to deselect
        tableViewWrap.keyboard().pushKey(btn, shift);
        selectionHelper.push(btn, shift);
        checkSelection();

        //Select the entire row
        btn = KeyboardButtons.LEFT;
        for (int i = 0; i < DATA_FIELDS_NUM - 1; i++) {
            tableViewWrap.keyboard().pushKey(btn, shift);
            selectionHelper.push(btn, shift);
            checkSelectedCellVisible();
        }
        checkSelection();

        //Apply combo once more to ensure that it doesn't affect selection
        tableViewWrap.keyboard().pushKey(btn, shift);
        selectionHelper.push(btn, shift);
        checkSelection();
    }

    /**
     * Test Shift + Home/End combinations.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testShiftHomeEnd() throws Throwable {
        KeyboardModifiers mods[] = {
            KeyboardModifiers.SHIFT_DOWN_MASK
        };

        keyboardHomeEndCycle(mods);
    }

    /**
     * Test Ctrl + Shift + Home/End combinations.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testCtrlShiftHomeEnd() throws Throwable {
        KeyboardModifiers mods[] = {
            CTRL_DOWN_MASK_OS,
            KeyboardModifiers.SHIFT_DOWN_MASK
        };

        keyboardHomeEndCycle(mods);
    }

    /**
     * Test Shift + PgUp/PgDown combinations.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testShiftPgUpDown() throws Throwable {
        KeyboardModifiers mods[] = {
            KeyboardModifiers.SHIFT_DOWN_MASK
        };

        keyboardPageUpPageDownCycle(mods);
    }

    /**
     * Test Ctrl + Shift + PgUp/PgDown combinations.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testCtrlShiftPgUpDown() throws Throwable {
        KeyboardModifiers mods[] = {
            CTRL_DOWN_MASK_OS,
            KeyboardModifiers.SHIFT_DOWN_MASK
        };

        keyboardPageUpPageDownCycle(mods);
    }

    /**
     * Test different sequences of Ctrl/Shift + Space combinations.
     */
    @Smoke
    @Test(timeout = 300000)
    public void testSpaceWithModifiers() throws Throwable {
        enableMultipleCellSelection();

        for (int x = 0; x < DATA_FIELDS_NUM; x++) {
            KeyboardModifiers ctrl = CTRL_DOWN_MASK_OS;
            KeyboardModifiers shift = KeyboardModifiers.SHIFT_DOWN_MASK;

            KeyboardModifiers mods[] = {ctrl, shift};

            scrollTo(0, 0);
            getCellWrap(0, 0).mouse().click();

            int counter = DATA_ITEMS_NUM / 8;
            KeyboardButtons arrow = (0 == x % 2)
                    ? KeyboardButtons.DOWN
                    : KeyboardButtons.UP;

            for (int i = 0; i < counter - 1; i++) {
                keyboardCheck(arrow, shift);
            }

            for (int i = 0; i < counter; i++) {
                keyboardCheck(arrow, ctrl);
            }

            keyboardCheck(KeyboardButtons.SPACE);

            for (int i = 0; i < counter; i++) {
                keyboardCheck(arrow, ctrl);
            }

            for (int i = 0; i < counter; i++) {
                keyboardCheck(arrow, shift);
            }

            keyboardCheck(KeyboardButtons.SPACE, ctrl);
            keyboardCheck(KeyboardButtons.SPACE, ctrl);
            keyboardCheck(KeyboardButtons.SPACE, mods);

            for (int i = 0; i < counter; i++) {
                keyboardCheck(arrow, ctrl);
            }
            keyboardCheck(KeyboardButtons.SPACE, shift);

            keyboardCheck(KeyboardButtons.RIGHT, ctrl);
        }
    }

    /**
     * The goal of this test is to check that focused changes according to the
     * specification and to verify that focused cell is visible all the time.
     *
     */
    @Smoke
    @Test(timeout = 300000)
    public void testKeyboardFocusModification() throws Throwable {
        if (!isTableTests) {
            removeAt(DATA_ITEMS_NUM - 1);
        }
        enableMultipleCellSelection();

        KeyboardModifiers ctrl = CTRL_DOWN_MASK_OS;
        KeyboardModifiers shift = KeyboardModifiers.SHIFT_DOWN_MASK;

        Point center = new Point();
        center.x = DATA_FIELDS_NUM / 2;
        center.y = (DATA_ITEMS_NUM - 1) / 2;

        scrollTo(center.x, center.y);
        getCellWrap(center.x, center.y).mouse().click();
        selectionHelper.click(center.x, center.y);

        keyboardCheck(KeyboardButtons.HOME);
        keyboardCheck(KeyboardButtons.END);
        checkSelectedCellVisible();
        keyboardCheck(KeyboardButtons.PAGE_UP);
        keyboardCheck(KeyboardButtons.PAGE_DOWN);
        checkSelectedCellVisible();
        keyboardCheck(KeyboardButtons.HOME, ctrl);
        keyboardCheck(KeyboardButtons.END, ctrl);
        checkSelectedCellVisible();
        keyboardCheck(KeyboardButtons.PAGE_UP, ctrl);
        keyboardCheck(KeyboardButtons.PAGE_DOWN, ctrl);
        checkSelectedCellVisible();

        for (int i = 0; i < DATA_ITEMS_NUM + 1; i++) {
            keyboardCheck(KeyboardButtons.UP, ctrl);
            checkSelectedCellVisible();
        }

        for (int i = 0; i < DATA_ITEMS_NUM + 1; i++) {
            keyboardCheck(KeyboardButtons.DOWN, ctrl);
            checkSelectedCellVisible();
        }

        for (int i = 0; i < DATA_ITEMS_NUM + 1; i++) {
            keyboardCheck(KeyboardButtons.UP, ctrl, shift);
            checkSelectedCellVisible();
        }

        for (int i = 0; i < DATA_ITEMS_NUM + 1; i++) {
            keyboardCheck(KeyboardButtons.DOWN, ctrl, shift);
            checkSelectedCellVisible();
        }
    }

    public void keyboardMultipleCellSelectionCycle(boolean discontinuous) throws Throwable {
        enableMultipleCellSelection();

        scrollTo(0, 0);
        getCellWrap(0, 0).mouse().click();

        int counter = DATA_ITEMS_NUM / 4;

        KeyboardModifiers[] mods = TableListCommonTests.getMods(discontinuous);

        keyboardSelectionCheckCycle(counter, KeyboardButtons.DOWN, mods);
        keyboardSelectionCheckCycle(counter, KeyboardButtons.RIGHT, mods);
        keyboardSelectionCheckCycle(counter, KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        keyboardSelectionCheckCycle(counter, KeyboardButtons.RIGHT, CTRL_DOWN_MASK_OS);
        keyboardSelectionCheckCycle(counter, KeyboardButtons.DOWN, mods);
        keyboardSelectionCheckCycle(counter, KeyboardButtons.RIGHT, mods);
        keyboardSelectionCheckCycle(counter, KeyboardButtons.UP, mods);
        keyboardSelectionCheckCycle(counter, KeyboardButtons.LEFT, mods);
    }

    protected void keyboardHomeEndCycle(KeyboardModifiers... mods) throws Throwable {
        Wrap center_cell = getCellWrap(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);

        multipleSelectionWrap.mouse().click();
        selectionHelper.setMultiple(true);

        scrollTo(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);

        center_cell.mouse().click();
        selectionHelper.click(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);

        keyboardCheck(KeyboardButtons.HOME, mods);
        keyboardCheck(KeyboardButtons.END, mods);

        singleCellSelectionWrap.mouse().click();
        selectionHelper.setSingleCell(true);

        center_cell = getCellWrap(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);
        center_cell.mouse().click();
        selectionHelper.click(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);

        keyboardCheck(KeyboardButtons.HOME, mods);
        keyboardCheck(KeyboardButtons.END, mods);
    }

    public void keyboardPageUpPageDownCycle(KeyboardModifiers... mods) throws Throwable {
        multipleSelectionWrap.mouse().click();
        selectionHelper.setMultiple(true);

        getCellWrap(0, 0).mouse().click();
        selectionHelper.click(0, 0);

        keyboardCheck(KeyboardButtons.PAGE_DOWN, mods);
        keyboardCheck(KeyboardButtons.PAGE_DOWN, mods);
        keyboardCheck(KeyboardButtons.PAGE_UP, mods);
        keyboardCheck(KeyboardButtons.PAGE_UP, mods);

        singleCellSelectionWrap.mouse().click();
        selectionHelper.setSingleCell(true);

        getCellWrap(DATA_FIELDS_NUM / 2, 0).mouse().click();
        selectionHelper.click(DATA_FIELDS_NUM / 2, 0);

        keyboardCheck(KeyboardButtons.PAGE_DOWN, mods);
        keyboardCheck(KeyboardButtons.PAGE_DOWN, mods);
        keyboardCheck(KeyboardButtons.PAGE_UP, mods);
        keyboardCheck(KeyboardButtons.PAGE_UP, mods);
    }

    protected void checkSelectedCellVisible() {
        boolean isVisible = TestBaseCommon.isSelectedCellVisible(testedControl);
        Assert.assertTrue("Selected cell is invisible.", isVisible);
    }

    protected void enableMultipleCellSelection() {
        multipleSelectionWrap.mouse().click();
        singleCellSelectionWrap.mouse().click();
        selectionHelper.setMultiple(true);
        selectionHelper.setSingleCell(true);
    }
}