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
package javafx.scene.control.test.util;

import client.test.Smoke;
import java.awt.Toolkit;
import java.util.HashSet;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import static javafx.scene.control.test.nchart.Utils.applyStyle;
import javafx.scene.control.test.util.MultipleSelectionHelper.Range;
import static javafx.scene.control.test.util.UtilTestFunctions.CTRL_DOWN_MASK_OS;
import static javafx.scene.control.test.util.UtilTestFunctions.setPropertyBySlider;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import static org.junit.Assert.assertTrue;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.timing.State;
import org.junit.BeforeClass;
import org.junit.Test;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 *
 * Tests, common for VirtualFlow-based controls : ListView, TreeView, TableView,
 * TreeTableView.
 */
public abstract class TableListCommonTests extends UtilTestFunctions {

    public static boolean DEBUG_STEP = false;

    protected static int DATA_ITEMS_NUM = 0;
    protected static int DATA_FIELDS_NUM = 0;
    protected static Wrap<? extends Control> testedControl; //ListView or TableView or TreeView
    protected static MultipleSelectionHelper selectionHelper;
    final private boolean doIntermediateState = true;

    protected static void setContentSize(int x, int y) {
        DATA_FIELDS_NUM = x;
        DATA_ITEMS_NUM = y;
        selectionHelper = new MultipleSelectionHelper(DATA_FIELDS_NUM, DATA_ITEMS_NUM);
    }

    protected boolean isListControl() {
        return testedControl.getControl() instanceof ListView;
    }

    protected abstract void scrollTo(int inXCoord, int inYCoord);

    protected abstract void switchOnMultiple();

    protected abstract void adjustControl();

    protected abstract void clickOnFirstCell();

    protected abstract void setOrientation(Orientation orientation);

    protected abstract Wrap getCellWrap(final int column, final int row);

    protected abstract Range getVisibleRange();

    protected abstract void checkSelection();

    protected abstract HashSet<Point> getSelected();

    protected abstract Point getSelectedItem();

    @BeforeClass
    public static void switchNumLockOff() {
        if (!Utils.isLinux() && !Utils.isMacOS()) //Temporary. Need to be explored, why it generates exception on linux.
        {
            Toolkit.getDefaultToolkit().setLockingKeyState(java.awt.event.KeyEvent.VK_NUM_LOCK, false);
        }
    }

    @Smoke
    @Test(timeout = 300000)//Must be Ok on list.
    public void keyboardSequentialSingleSelectionTest() throws Throwable {
        adjustControl();
        scrollTo(0, 0);

        getCellWrap(0, 0).mouse().click();
        intermediateStateCheck();

        for (int i = 0; i < (Utils.isSmokeTesting() ? 2 : DATA_ITEMS_NUM); i++) {
            testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
            selectionHelper.push(KeyboardButtons.DOWN);
            checkSelection();
        }
        intermediateStateCheck();
        assertTrue(testedControl.getScreenBounds().contains(getCellWrap(0, DATA_ITEMS_NUM - 1).getScreenBounds()));
    }

    @Smoke
    @Test(timeout = 3000000)
    public void singleRowSelectionTest() throws Throwable {
        adjustControl();
        selectionCycle(null);
        selectionCycle(KeyboardButtons.SHIFT);
        selectionCycle(Utils.isMacOS() ? KeyboardButtons.META : KeyboardButtons.CONTROL);
    }

    @Smoke
    @Test(timeout = 3000000)
    public void multipleRowSelectionTest() throws Throwable {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        selectionCycle(KeyboardButtons.SHIFT);
        selectionCycle(Utils.isMacOS() ? KeyboardButtons.META : KeyboardButtons.CONTROL);
        selectionCycle(null);
    }

    @Test
    @Smoke
    public void onDiscontinuousSelectionTest() {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        scrollTo(0, 0);
        clickOnFirstCell();
        selectionHelper.click(0, 0);

        for (int i = 0; i < DATA_ITEMS_NUM / 5; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / 5; i++) {
            keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        }
        intermediateStateCheck();
        //Set an anchor. Starting from this anchor discontinuous selection should start.
        if (Utils.isMacOS()) {
            keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS, KeyboardModifiers.CTRL_DOWN_MASK);
        } else {
            keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / 5; i++) {
            keyboardCheck(KeyboardButtons.DOWN, getMods(true));
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / 3; i++) {
            keyboardCheck(KeyboardButtons.UP, getMods(true));
        }
        intermediateStateCheck();
    }

    @Smoke
    @Test(timeout = 300000)//Ok for ListView
    public void keyboardShiftSequentialMultipleSelectionTest() throws Throwable {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        intermediateStateCheck();
        scrollTo(0, 0);
        clickOnFirstCell();
        for (int i = 0; i < (Utils.isSmokeTesting() ? 2 : DATA_ITEMS_NUM); i++) {
            testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
            selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
            checkSelection();
        }
        intermediateStateCheck();
        assertTrue(testedControl.getScreenBounds().contains(getCellWrap(0, DATA_ITEMS_NUM - 1).getScreenBounds()));
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardRangeMultipleSelectionTest() throws Throwable {
        keyboardRangeMultipleSelectionCycle(false);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardRangeMultipleSelectionDiscontinuousTest() throws Throwable {
        keyboardRangeMultipleSelectionCycle(true);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardShortcutsSpaceTest() throws Throwable {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        intermediateStateCheck();
        scrollTo(0, 0);
        clickOnFirstCell();
        selectionHelper.click(0, 0);
        int counter = DATA_ITEMS_NUM / 4;
        for (int i = 0; i < counter; i++) {
            keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
            keyboardCheck(KeyboardButtons.SPACE);
        }
        intermediateStateCheck();
        for (int i = 0; i < counter; i++) {
            keyboardCheck(KeyboardButtons.SPACE);
            keyboardCheck(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
        }
        intermediateStateCheck();
        for (int i = 0; i < counter; i++) {
            if (Utils.isMacOS()) {
                keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS, KeyboardModifiers.CTRL_DOWN_MASK);
            } else {
                keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);
            }
            keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        }
        intermediateStateCheck();
        for (int i = 0; i < counter; i++) {
            keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS, KeyboardModifiers.SHIFT_DOWN_MASK);
            keyboardCheck(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
        }
        intermediateStateCheck();
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCtrlATest() throws Throwable {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        intermediateStateCheck();

        scrollTo(0, 0);

        clickOnFirstCell();
        selectionHelper.click(0, 0);

        if (Utils.isMacOS()) {
            keyboardCheck(KeyboardButtons.A, KeyboardModifiers.META_DOWN_MASK);
        } else {
            keyboardCheck(KeyboardButtons.A, KeyboardModifiers.CTRL_DOWN_MASK);
        }

        //Check, where is the anchor at that moment...
        for (int i = 0; i < DATA_ITEMS_NUM / 4; i++) {
            keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / 4; i++) {
            keyboardCheck(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
        }
        intermediateStateCheck();
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardHomeEndTest() throws Throwable {
        keyboardHomeEndCycle();
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCtrlHomeEndTest() throws Throwable {
        keyboardHomeEndCycle(CTRL_DOWN_MASK_OS);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardShiftSpaceTest() throws Throwable {
        adjustControl();

        for (Boolean multipleMode : new Boolean[]{Boolean.FALSE, Boolean.TRUE}) {
            if (multipleMode) {
                switchOnMultiple();
            }
            selectionHelper.setMultiple(multipleMode);

            intermediateStateCheck();

            scrollTo(0, 0);

            clickOnFirstCell();
            selectionHelper.click(0, 0);
            intermediateStateCheck();

            for (int i = 0; i < DATA_ITEMS_NUM / 4; i++) {
                keyboardCheck(KeyboardButtons.DOWN);
            }
            intermediateStateCheck();

            keyboardCheck(KeyboardButtons.SPACE, KeyboardModifiers.SHIFT_DOWN_MASK);
            intermediateStateCheck();

            for (int i = 0; i < DATA_ITEMS_NUM / 4; i++) {
                keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
            }
            intermediateStateCheck();

            keyboardCheck(KeyboardButtons.SPACE, KeyboardModifiers.SHIFT_DOWN_MASK);
            intermediateStateCheck();

            for (int i = 0; i < DATA_ITEMS_NUM / 2; i++) {
                keyboardCheck(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
            }
            intermediateStateCheck();

            keyboardCheck(KeyboardButtons.SPACE, KeyboardModifiers.SHIFT_DOWN_MASK);
            intermediateStateCheck();
        }
    }

    @Smoke
    @Test(timeout = 300000)//RT-18413
    public void keyboardShiftHomeEndTest() throws Throwable {
        keyboardHomeEndCycle(KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCtrlShiftHomeEndTest() throws Throwable {
        keyboardHomeEndCycle(KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardPageUpPageDownTest() throws Throwable {
        keyboardPageUpPageDownCycle();
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCtrlPageUpPageDownTest() throws Throwable {
        keyboardPageUpPageDownCycle(CTRL_DOWN_MASK_OS);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardShiftPageUpPageDownTest() throws Throwable {
        keyboardPageUpPageDownCycle(KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCtrlShiftPageUpPageDownTest() throws Throwable {
        keyboardPageUpPageDownCycle(KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardUpDownTest() throws Throwable {
        keyboardUpDownCycle();
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCtrlUpDownTest() throws Throwable {
        keyboardUpDownCycle(CTRL_DOWN_MASK_OS);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardShiftUpDownTest() throws Throwable {
        keyboardUpDownCycle(KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCtrlShiftUpDownTest() throws Throwable {
        keyboardUpDownCycle(KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS);
    }

    /**
     * Tests selection by applying shift + left mouse click
     * combination on the control cells.
     */
    @Smoke
    @Test(timeout = 300000)
    public void shiftLeftMouseClick() throws Throwable {
        MultipleSelectionHelper tmp = null;
        if (isListControl()) {
            tmp = selectionHelper;
            selectionHelper = new MultipleSelectionHelper.ListViewMultipleSelectionHelper(DATA_FIELDS_NUM, DATA_ITEMS_NUM);
            selectionHelper.setPageWidth(1);
            selectionHelper.setPageHeight(10);
        }
        adjustControl();

        for (Boolean multipleMode : new Boolean[]{Boolean.FALSE, Boolean.TRUE}) {
            if (multipleMode) {
                switchOnMultiple();
            }
            selectionHelper.setMultiple(multipleMode);

            intermediateStateCheck();

            scrollTo(0, 0);

            clickOnFirstCell();
            selectionHelper.click(0, 0);
            intermediateStateCheck();

            selectionCycle(0, 1, 1, DATA_ITEMS_NUM / 2, KeyboardButtons.SHIFT);
            reverseSelectionCycle(0, 1, DATA_ITEMS_NUM / 2, 0, KeyboardButtons.SHIFT);

            selectionCycle(0, 1, DATA_ITEMS_NUM / 2, DATA_ITEMS_NUM / 4 * 3, Utils.isMacOS() ? KeyboardButtons.META : KeyboardButtons.CONTROL);
            reverseSelectionCycle(0, 1, DATA_ITEMS_NUM / 2, DATA_ITEMS_NUM / 4 * 3, KeyboardButtons.SHIFT);

            selectionCycle(0, 1, DATA_ITEMS_NUM / 4 * 3, DATA_ITEMS_NUM, KeyboardButtons.SHIFT);
            reverseSelectionCycle(0, 1, DATA_ITEMS_NUM, DATA_ITEMS_NUM / 2, Utils.isMacOS() ? KeyboardButtons.META : KeyboardButtons.CONTROL);

            selectionCycle(0, 1, 0, DATA_ITEMS_NUM, KeyboardButtons.SHIFT);
            reverseSelectionCycle(0, 1, DATA_ITEMS_NUM, 1, KeyboardButtons.SHIFT);
        }

        if (isListControl()) {
            selectionHelper = tmp;
        }
    }

    public void keyboardRangeMultipleSelectionCycle(boolean discontinuous) throws Throwable {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        scrollTo(0, 0);
        clickOnFirstCell();
        intermediateStateCheck();
        KeyboardModifiers[] mods = getMods(discontinuous);
        for (int i = 0; i < DATA_ITEMS_NUM / 4; i++) {
            testedControl.keyboard().pushKey(KeyboardButtons.DOWN, mods);
            selectionHelper.push(KeyboardButtons.DOWN, mods);
            checkSelection();
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / 4; i++) {
            testedControl.keyboard().pushKey(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
            selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.CTRL_DOWN_MASK);
            checkSelection();
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / 4; i++) {
            testedControl.keyboard().pushKey(KeyboardButtons.DOWN, mods);
            selectionHelper.push(KeyboardButtons.DOWN, mods);
            checkSelection();
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / 2; i++) {
            testedControl.keyboard().pushKey(KeyboardButtons.UP, mods);
            selectionHelper.push(KeyboardButtons.UP, mods);
            checkSelection();
        }
        intermediateStateCheck();
    }

    public static KeyboardModifiers[] getMods(boolean discontinuous) {
        if (discontinuous) {
            return new KeyboardModifiers[]{KeyboardModifiers.SHIFT_DOWN_MASK, CTRL_DOWN_MASK_OS};
        } else {
            return new KeyboardModifiers[]{KeyboardModifiers.SHIFT_DOWN_MASK};
        }
    }

    protected void keyboardHomeEndCycle(KeyboardModifiers... mods) throws Throwable {
        adjustControl();
        Wrap center_cell = getCellWrap(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);
        intermediateStateCheck();

        switchOnMultiple();
        selectionHelper.setMultiple(true);

        scrollTo(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);

        center_cell.mouse().click();
        selectionHelper.click(DATA_FIELDS_NUM / 2, DATA_ITEMS_NUM / 2);
        intermediateStateCheck();

        keyboardCheck(KeyboardButtons.HOME, mods);
        keyboardCheck(KeyboardButtons.END, mods);
        keyboardCheck(KeyboardButtons.HOME, mods);
        keyboardCheck(KeyboardButtons.END, mods);
        intermediateStateCheck();
    }

    public void keyboardPageUpPageDownCycle(KeyboardModifiers... mods) throws Throwable {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);
        intermediateStateCheck();

        clickOnFirstCell();
        selectionHelper.click(0, 0);

        keyboardCheck(KeyboardButtons.PAGE_DOWN, mods);
        keyboardCheck(KeyboardButtons.PAGE_DOWN, mods);
        keyboardCheck(KeyboardButtons.PAGE_UP, mods);
        keyboardCheck(KeyboardButtons.PAGE_UP, mods);
        keyboardCheck(KeyboardButtons.PAGE_UP, mods);
        intermediateStateCheck();

        clickOnFirstCell();
        selectionHelper.click(0, 0);

        keyboardCheck(KeyboardButtons.DOWN, mods);
        keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        if (Utils.isMacOS()) {
            keyboardCheck(KeyboardButtons.SPACE, KeyboardModifiers.CTRL_DOWN_MASK, CTRL_DOWN_MASK_OS);
        } else {
            keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);
        }
        keyboardCheck(KeyboardButtons.DOWN, mods);

        intermediateStateCheck();

        for (int i = 0; i < DATA_ITEMS_NUM / selectionHelper.pageHeight + 1; i++) {
            //Reach the end.
            keyboardCheck(KeyboardButtons.PAGE_DOWN, mods);
        }
        intermediateStateCheck();
        for (int i = 0; i < DATA_ITEMS_NUM / selectionHelper.pageHeight + 1; i++) {
            //Move back.
            keyboardCheck(KeyboardButtons.PAGE_UP, mods);
        }
        intermediateStateCheck();
    }

    public void keyboardUpDownCycle(KeyboardModifiers... mods) throws Throwable {
        adjustControl();
        switchOnMultiple();
        selectionHelper.setMultiple(true);

        intermediateStateCheck();

        clickOnFirstCell();
        selectionHelper.click(0, 0);

        KeyboardButtons moreButton = KeyboardButtons.DOWN;
        KeyboardButtons lessButton = KeyboardButtons.UP;

        if (isListControl()) {
            setOrientation(Orientation.HORIZONTAL);
            moreButton = KeyboardButtons.RIGHT;
            lessButton = KeyboardButtons.LEFT;
        }

        intermediateStateCheck();

        keyboardCheck(moreButton, mods);
        keyboardCheck(lessButton, mods);
        keyboardCheck(lessButton, mods);

        intermediateStateCheck();

        if (isListControl()) {
            setOrientation(Orientation.VERTICAL);
            moreButton = KeyboardButtons.DOWN;
            lessButton = KeyboardButtons.UP;
        }

        keyboardCheck(moreButton, mods);
        keyboardCheck(lessButton, mods);
        keyboardCheck(lessButton, mods);

        intermediateStateCheck();

        clickOnFirstCell();
        selectionHelper.click(0, 0);

        keyboardCheck(KeyboardButtons.DOWN, mods);
        keyboardCheck(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
        if (Utils.isMacOS()) {
            keyboardCheck(KeyboardButtons.SPACE, KeyboardModifiers.CTRL_DOWN_MASK, CTRL_DOWN_MASK_OS);
        } else {
            keyboardCheck(KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);
        }
        keyboardCheck(KeyboardButtons.DOWN, mods);

        //Go to the end.
        for (int i = 0; i < DATA_ITEMS_NUM + 1; i++) {
            keyboardCheck(moreButton, mods);
        }

        intermediateStateCheck();

        //Move back from the end.
        for (int i = 0; i < DATA_ITEMS_NUM + 1; i++) {
            keyboardCheck(lessButton, mods);
        }

        intermediateStateCheck();
    }

    protected void keyboardCheck(KeyboardButtons btn, KeyboardModifiers... modifier) {
        if (btn == KeyboardButtons.PAGE_DOWN || btn == KeyboardButtons.PAGE_UP) {
            selectionHelper.setVisibleRange(getVisibleRange());
        }

        if (!DEBUG_STEP) {
            testedControl.keyboard().pushKey(btn, modifier);
        }

        if (isListControl() && btn == KeyboardButtons.RIGHT) {
            selectionHelper.push(KeyboardButtons.DOWN, modifier);
        } else if (isListControl() && btn == KeyboardButtons.LEFT) {
            selectionHelper.push(KeyboardButtons.UP, modifier);
        } else {
            selectionHelper.push(btn, modifier);
        }

        checkSelection();
    }

    protected void selectionCycle(KeyboardButtons modifier) throws Throwable {
        if (Utils.isSmokeTesting()) {
            selectionCycle(0, 2, 0, 2, modifier);
        } else {
            selectionCycle(0, DATA_FIELDS_NUM, 0, DATA_ITEMS_NUM, modifier);
        }
        //selectionCycle(0, 3, 0, 3, modifier);
    }

    protected void selectionCycle(int first_column, int last_column, int first_row, int last_row, KeyboardButtons modifier) throws Throwable {
        if (modifier != null) {
            testedControl.keyboard().pressKey(modifier);
        }
        try {
            for (int j = first_column; j < last_column; j++) {
                final int column = j;
                for (int i = first_row; i < last_row; i++) {
                    final int row = i;
                    if (isListControl()) {
                        scrollTo(j, i);
                    }
                    getCellWrap(column, row).mouse().click();
                    selectionHelper.click(column, row, modifier);
                    checkSelection();
                    intermediateStateCheck();
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

    void reverseSelectionCycle(int first_column, int last_column, int first_row, int last_row, KeyboardButtons modifier) throws Throwable {
        if (modifier != null) {
            testedControl.keyboard().pressKey(modifier);
        }
        try {
            for (int j = first_column; j < last_column; j++) {
                final int column = j;
                for (int i = first_row - 1; i >= last_row; i--) {
                    final int row = i;
                    if (isListControl()) {
                        scrollTo(j, i);
                    }
                    getCellWrap(column, row).mouse().click();
                    selectionHelper.click(column, row, modifier);
                    checkSelection();
                    intermediateStateCheck();
                }
            }
        } finally {
            if (modifier != null) {
                testedControl.keyboard().releaseKey(modifier);
            }
        }
    }

    public static void fixedCellSizePropertyTestCommon(Wrap<? extends Control> testedControl, Enum property, double defaultCellSize) throws InterruptedException {
        setPropertyBySlider(AbstractPropertyController.SettingType.BIDIRECTIONAL, property, 10);
        checkTextFieldValue(property, 10);
        checkCellsHeight(testedControl, 10);

        setPropertyBySlider(AbstractPropertyController.SettingType.SETTER, property, 20);
        checkTextFieldValue(property, 20);
        checkCellsHeight(testedControl, 20);

        setPropertyBySlider(AbstractPropertyController.SettingType.UNIDIRECTIONAL, property, 40);
        checkTextFieldValue(property, 40);
        checkCellsHeight(testedControl, 40);

        setPropertyBySlider(AbstractPropertyController.SettingType.SETTER, property, -1);
        checkTextFieldValue(property, -1);
        checkCellsHeight(testedControl, defaultCellSize);
    }

    public static void fixedCellSizePropertyCSSTestCommon(Wrap<? extends Control> testedControl, Enum property, double defaultCellSize) {
        final String fxfixedcellsize = "-fx-fixed-cell-size";
        applyStyle(testedControl, fxfixedcellsize, 30);
        checkTextFieldValue(property, 30);
        checkCellsHeight(testedControl, 30);

        applyStyle(testedControl, fxfixedcellsize, -1);
        checkTextFieldValue(property, -1);
        checkCellsHeight(testedControl, defaultCellSize);
    }

    public static void checkCellsHeight(Wrap<? extends Control> testedControl, final double expectedHeight) {
        final Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(IndexedCell.class);
        assertTrue(lookup.size() > 4);//Sanity check
        for (int i = 0; i < lookup.size(); i++) {
            if (testedControl.getScreenBounds().contains(lookup.wrap(i).getScreenBounds())
                    && ((IndexedCell) lookup.get(i)).isVisible()
                    && ((IndexedCell) lookup.get(i)).getParent().isVisible()) {
                final int indx = i;
                testedControl.waitState(new State() {
                    private IndexedCell cell;
                    private double height;

                    public Object reached() {
                        cell = (IndexedCell) lookup.wrap(indx).getControl();
                        height = cell.getHeight();
                        System.out.println(height + " " + cell);
                        return (Math.abs(expectedHeight - height) <= 0.5) ? Boolean.TRUE : null;
                    }

                    @Override
                    public String toString() {
                        return "For cell <" + cell + "> height <" + height + "> is not equals expected height <" + expectedHeight + ">.";
                    }
                });
            }
        }
    }

    /**
     * This function can be overriden to add intermediate check of control
     * state. Those are checks, that check correct control representation during
     * different operations.
     */
    final protected void intermediateStateCheck() {
        if (doIntermediateState) {
            doIntermediateStateCheck();
        }
    }

    protected void doIntermediateStateCheck() {
    }
}