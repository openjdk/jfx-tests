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
package javafx.scene.control.test.ListView;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.test.util.MultipleSelectionHelper;
import javafx.scene.control.test.util.ScrollingChecker;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class NewListViewTest extends TestBase {

    static StringBuilder sb = new StringBuilder();

    @Before
    @Override
    public void setUp() {
       super.setUp();

       new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                ListView list = (ListView) testedControl.getControl();
                list.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<javafx.scene.input.KeyEvent>() {
                    public void handle(javafx.scene.input.KeyEvent event) {
                        if (event.getCode() == KeyCode.CONTROL) return;
                        if (event.getCode() == KeyCode.ALT) return;
                        if (event.getCode() == KeyCode.SHIFT) return;

                        if (event.isAltDown()) sb.append("ALT + ");
                        if (event.isControlDown()) sb.append("CTRL + ");
                        if (event.isShiftDown()) sb.append("SHIFT + ");

                        sb.append(event.getCode()).append("\n");
                    }
                });
            }
        }.dispatch(testedControl.getEnvironment());

       new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                ListView list = (ListView) testedControl.getControl();
                list.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        sb.append("mouse ").append(event.getButton()).append("\n");
                    }
                });
            }
        }.dispatch(testedControl.getEnvironment());
    }

    @After
    public void _after() {
        String newLine = "\n";
        int currIndex = sb.indexOf(newLine);
        int prevIndex = 0;

        int counter = 1;
        String prevLine = null;

        StringBuilder rolledUpResult = new StringBuilder();
        while(currIndex > 0) {
            String currLine = sb.substring(prevIndex, currIndex);
            if (prevLine == null) {
                prevLine = currLine;
            } else if (prevLine.equals(currLine)) {
                ++counter;
            } else {
                rolledUpResult.append(prevLine);
                if (counter > 1) rolledUpResult.append(" x").append(counter);
                rolledUpResult.append(newLine);
                counter = 1;
                prevLine = currLine;
            }
            prevIndex = currIndex + 1;
            currIndex = sb.indexOf(newLine, prevIndex);
            if (currIndex == sb.length() - 1) {
                rolledUpResult.append(sb.substring(prevIndex));
                break;
            }
        }
        System.out.println("Key sequence:");
        System.out.println(rolledUpResult.toString());
        sb = new StringBuilder();
    }

    //SECTION OF TESTS ON PROPERTIES
    @Smoke
    @Test(timeout = 300000)
    public void editablePropertyTest() {
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        checkTextFieldText(Properties.editable, "true");

        clickToggleButton("BIDIR_EDITABLE_CONTROLLER_ID");
        checkTextFieldText(Properties.editable, "false");

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);
        checkTextFieldText(Properties.editable, "true");

        clickToggleButton("UNIDIR_EDITABLE_CONTROLLER_ID");
        checkTextFieldText(Properties.editable, "false");
    }

    @Smoke
    @Test(timeout = 300000)
    public void orientationPropertyTest() {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        checkTextFieldText(Properties.orientation, "VERTICAL");

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        checkTextFieldText(Properties.orientation, "HORIZONTAL");

        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        checkTextFieldText(Properties.orientation, "VERTICAL");

        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        checkTextFieldText(Properties.orientation, "HORIZONTAL");
    }

    @Smoke
    @Test(timeout = 300000)
    public void selectionModePropertyTest() {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        checkTextFieldText(Properties.selectionMode, "MULTIPLE");

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.SINGLE, Properties.selectionMode);
        checkTextFieldText(Properties.selectionMode, "SINGLE");

        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        checkTextFieldText(Properties.selectionMode, "MULTIPLE");

        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, SelectionMode.SINGLE, Properties.selectionMode);
        checkTextFieldText(Properties.selectionMode, "SINGLE");
    }

    @Smoke
    @Test(timeout = 300000)
    public void prefHeightPropertyTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 200);
        checkTextFieldValue(Properties.prefHeight, 200);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        checkTextFieldValue(Properties.prefHeight, 100);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefHeight, 150);
        checkTextFieldValue(Properties.prefHeight, 150);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefHeight, 50);
        checkTextFieldValue(Properties.prefHeight, 50);
    }

    @Smoke
    @Test(timeout = 300000)
    public void prefWidthPropertyTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        checkTextFieldValue(Properties.prefWidth, 200);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);
        checkTextFieldValue(Properties.prefWidth, 100);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefWidth, 150);
        checkTextFieldValue(Properties.prefWidth, 150);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefWidth, 50);
        checkTextFieldValue(Properties.prefWidth, 50);
    }

    //                          SITUATIONAL TESTS
    @Smoke
    @Test(timeout = 300000)
    public void keyboardRangeMultipleSelection2Test() throws Throwable {
        addElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        KeyboardButtons CTRL_OS = Utils.isMacOS() ? KeyboardButtons.META : KeyboardButtons.CONTROL;

        selectionHelper = new MultipleSelectionHelper(1, DATA_ITEMS_NUM);
        selectionHelper.setMultiple(true);

        requestFocusOnControl(testedControl);

        testedControl.keyboard().pressKey(KeyboardButtons.SHIFT);
        try {
            for (int i = 1; i < 5; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
                selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }
        } finally {
            testedControl.keyboard().releaseKey(KeyboardButtons.SHIFT);
        }

        testedControl.keyboard().pressKey(CTRL_OS);
        try {
            for (int i = 1; i < 5; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
                selectionHelper.push(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                checkSelection();
            }
        } finally {
            testedControl.keyboard().releaseKey(CTRL_OS);
        }

        testedControl.keyboard().pressKey(KeyboardButtons.SHIFT);
        try {
            for (int i = 1; i < 5; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.UP);
                selectionHelper.push(KeyboardButtons.UP, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }
        } finally {
            testedControl.keyboard().releaseKey(KeyboardButtons.SHIFT);
        }

        testedControl.keyboard().pressKey(CTRL_OS);
        try {
            for (int i = 1; i < 5; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.UP);
                selectionHelper.push(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
                checkSelection();
            }
        } finally {
            testedControl.keyboard().releaseKey(CTRL_OS);
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void scrollToTest() throws InterruptedException {
        int size = 10;

        for (int i = 0; i <= size; i++) {
            addElement(String.valueOf(i), i);
        }

        setSize(50, 50);
        for (Orientation orientation : Orientation.values()) {
            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientation, Properties.orientation);

            scrollTo(size);
            checkScrollingState(1, false, true, size, orientation);

            scrollTo(0);
            checkScrollingState(0, true, false, size, orientation);

            scrollTo(size * 2);
            checkScrollingState(1, false, true, size, orientation);

            scrollTo(-size);
            checkScrollingState(0, true, false, size, orientation);
        }
    }

    private void checkScrollingState(final double scrollValue, boolean beginVisible, boolean endVisible, int size, final Orientation orientation) {
        //assertEquals(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), orientation, true).getControl().getValue(), scrollValue, 0.01);
        testedControl.waitState(new State() {
            public Object reached() {
                Wrap<? extends ScrollBar> sb = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), orientation, true);
                if (Math.abs(sb.getControl().getValue() - scrollValue) < 0.01) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        if (beginVisible) {
            assertTrue(isCellShown(0, orientation));
        }
        if (endVisible) {
            assertTrue(isCellShown(size, orientation));
        }
    }

    private boolean isCellShown(final int item, Orientation orientation) {
        Wrap<Text> cellWrap = getCellWrap(item);
        Rectangle cellRect = cellWrap.getScreenBounds();
        Rectangle control = testedControl.getScreenBounds();

//        int epsilon = 0;
//
//        if (orientation == Orientation.HORIZONTAL) {
//            control.x = control.x - epsilon;
//            control.width = control.width + 2 * epsilon;
//        } else {
//            control.y = control.y - epsilon;
//            control.height = control.height + 2 * epsilon;
//        }

        return control.contains(cellRect);
    }

    @Smoke
    @Test(timeout = 30000)
    public void fixedCellSizePropertyTest() throws InterruptedException {
        adjustControl();
        fixedCellSizePropertyTestCommon(testedControl, Properties.fixedCellSize, Utils.isCaspian() ? 19.4 : 21);
    }

    @Smoke
    @Test(timeout = 30000)
    public void fixedCellSizePropertyCSSTest() {
        adjustControl();
        fixedCellSizePropertyCSSTestCommon(testedControl, Properties.fixedCellSize, Utils.isCaspian() ? 19.4 : 21);
    }

    @Smoke
    @Test(timeout = 300000)
    public void verticalScrollBarBehaviorOnAddingDeletingTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 50);
        assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, false) == null);
        addElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true) == null);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 500);
        assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, false) == null);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 50);
        for (int i = 0; i < 10; i++) {
            removeFromPos(0);
        }
        assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, false) == null);
    }

    //Test//RT-17538//Test removed, as non actual (bug - is not a bug).
    public void steadyScrollingTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 150);

        addRectangleAtPos(0);
        addTextFieldAtPos(1);
        addRectangleAtPos(2);

        Wrap<? extends ScrollBar> sb = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
        ScrollingChecker sc = new ScrollingChecker(sb.getControl().valueProperty());
        testedControl.mouse().move();
        for (int i = 0; i < 8; i++) {
            findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(+1);
            sc.checkChanging(+1);
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void scrollAppearingOnResizingTest() throws InterruptedException {
        setSize(50, 50);
        checkScrollbarsStates(false, false);

        addRectangleAtPos(0);

        checkScrollbarsStates(true, true);

        setSize(230, 50);
        checkScrollbarsStates(false, true);

        setSize(50, 225);
        checkScrollbarsStates(true, false);

        setSize(230, 230);
        checkScrollbarsStates(false, false);

        setSize(50, 50);
        checkScrollbarsStates(true, true);

        removeFromPos(0);

        checkScrollbarsStates(false, false);
    }

    @Smoke
    @Test(timeout = 300000)
    public void scrollBarAppearingSensitivityTest() throws InterruptedException {
        addRectangleAtPos(0);

        setSize(208, 208);
        checkScrollbarsStates(true, true);

        setSize(225, 225);
        checkScrollbarsStates(false, false);
    }

    @Smoke
    @Test(timeout = 300000)//RT-18293
    public void eventsCommingTest() throws InterruptedException {
        addFormAtPos(0);

        setSize(200, 200);

        clickFormButton();
        checkFormClickCounter(1);
        scrollFormScrollBar(-1);
        checkFormScrollCounter(1);
    }

    //Test//RT-17701// Test removed, because developers don't want to count size of content, to evaluate scrollBar size propertly.
    public void scrollBarSize1Test() throws InterruptedException, Throwable {
        setSize(150, 150);
        addRectangleAtPos(0);
        addRectangleAtPos(0);
        addTextFieldAtPos(1);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);

        checkScreenshot("ListView_scrollBarSize1Test_HorizontalScroll_1", findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true));
        checkScreenshot("ListView_scrollBarSize1Test_VerticalScroll_1", findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true));
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000) //RT-17460
    public void scrollBarSize2Test() throws InterruptedException, Throwable {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        setSize(200, 210);

        addRectangleAtPos(0);
        addRectangleAtPos(0);
        checkScreenshot("ListView_HorizontalScroll_2", findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true));
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000) //RT-17462
    public void scrollBarDisappearTest() throws InterruptedException {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        setSize(210, 210);

        addRectangleAtPos(0);
        addRectangleAtPos(0);

        for (int i = 0; i < 100; i++) {
            findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(+1);
        }

        assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true) == null);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)//RT-17465
    public void selectionSavingTest() throws InterruptedException, Throwable {
        setSize(100, 100);
        addRectangleAtPos(0);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        testedControl.mouse().click();

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        checkScreenshot("ListView_SelectionSavingChecking", testedControl);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        checkScreenshot("ListView_SelectionSavingChecking", testedControl);

        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)//RT-17705
    public void scrollingConfused1Test() throws InterruptedException, Throwable {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        setSize(150, 150);
        addRectangleAtPos(0);
        int scroll_value = Utils.isMacOS() ? -20 : +20;
        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(scroll_value);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(scroll_value);

        checkScreenshot("ListView_ScrollingIssue_1", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)//RT-17730
    public void scrollingConfused2Test() throws InterruptedException, Throwable {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        setSize(150, 150);
        addRectangleAtPos(0);
        Thread.sleep(500);

        for (int i = 0; i < 20; i++) {
            findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(+1);
        }

        for (int i = 0; i < 20; i++) {
            findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(+1);
        }

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(+20);
        checkScreenshot("ListView_ScrollingIssue_2", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)//RT-17733
    public void scrollingConfused3Test() throws Throwable {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        setSize(150, 150);
        addRectangleAtPos(0);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(+20);
        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(+20);
        checkScreenshot("ListView_ScrollingIssue_3", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)//RT-17522
    public void addingElementOnFocusPositionTest() {
        addElements(1, 2, 3, 4, 5, 6, 7);
        testedControl.mouse().click();

        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);

        checkListener(Listeners.focusedIndex, 2);
        addElement("0", 0);
        checkListener(Listeners.focusedIndex, 3);
        addElement("0", 2);
        checkListener(Listeners.focusedIndex, 4);
        addElement("0", 4);
        checkListener(Listeners.focusedIndex, 5);
    }

    //Test//RT-17539//Test removed because developers don't count size of content.
    public void knobSizeChangingTest() throws InterruptedException, Throwable {
        setSize(150, 150);

        addTextFieldAtPos(0);
        addRectangleAtPos(1);
        addElement("0", 2);

        checkScreenshot("ListView_ScrollBarKnobSizeIssue_1", testedControl);

        for (int i = 0; i < 20; i++) {
            findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(+1);
        }

        checkScreenshot("ListView_ScrollBarKnobSizeIssue_2", testedControl);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(+10);

        checkScreenshot("ListView_ScrollBarKnobSizeIssue_3", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)//RT-18234 - possible
    public void singleSelectionOnKeyBoardTest() throws InterruptedException, Throwable {
        addElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.SINGLE, Properties.selectionMode);
        setSize(130, 130);

        selectionHelper = new MultipleSelectionHelper(1, DATA_ITEMS_NUM);
        selectionHelper.setSingleCell(false);
        selectionHelper.setMultiple(false);

        requestFocusOnControl(testedControl);

        //Testing section
        try {
            for (int i = 0; i < 5; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }

            for (int i = 0; i < 3; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
                selectionHelper.push(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
                checkSelection();
            }

            testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
            testedControl.keyboard().releaseKey(KeyboardButtons.SHIFT);
            selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
            checkSelection();
            // End of testing section
        } catch (Throwable error) {
            throw error;
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void multipleSelectionCommonLogicOnKeyBoardTest() throws InterruptedException, Throwable {
        addElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.SINGLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        setSize(130, 130);

        selectionHelper = new MultipleSelectionHelper(1, 10);
        selectionHelper.setSingleCell(false);
        selectionHelper.setMultiple(true);

        requestFocusOnControl(testedControl);

        //Testing section
        try {
            for (int i = 0; i < 2; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
                selectionHelper.push(KeyboardButtons.DOWN);
            }

            for (int i = 0; i < 5; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }
            //Elements from 2 to 7 are selected

            for (int i = 0; i < 2; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                selectionHelper.push(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                checkSelection();
            }

            for (int i = 0; i < 5; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.UP, KeyboardModifiers.SHIFT_DOWN_MASK);
                selectionHelper.push(KeyboardButtons.UP, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }

            for (int i = 0; i < 4; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
                selectionHelper.push(KeyboardButtons.UP, CTRL_DOWN_MASK_OS);
                checkSelection();
            }

            for (int i = 0; i < 6; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection(); //RT-18234 - possible
            }

            for (int i = 0; i < 2; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                selectionHelper.push(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                checkSelection();
            }

            for (int i = 0; i < 15; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }
            // End of testing section
        } catch (Throwable error) {
            throw error;
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void ctrlAClickSingleTest() throws InterruptedException, Throwable {
        addElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.SINGLE, Properties.selectionMode);
        setSize(130, 130);

        selectionHelper = new MultipleSelectionHelper(1, DATA_ITEMS_NUM);
        selectionHelper.setSingleCell(false);
        selectionHelper.setMultiple(false);

        requestFocusOnControl(testedControl);

        //Testing section
        try {
            for (int i = 0; i < 2; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
                selectionHelper.push(KeyboardButtons.DOWN);
            }

            for (int i = 0; i < 4; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }
            //Elements from 2 to 6 are selected

            for (int i = 0; i < 2; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                selectionHelper.push(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                checkSelection();
            }

            testedControl.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
            selectionHelper.ctrlA = true;
            currentListContentSize = 10;
            checkSelection();
            checkListener(Listeners.focusedIndex, 8);
            checkListener(Listeners.selectedIndex, 6);

            // End of testing section
        } catch (Throwable error) {
            throw error;
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void ctrlAClickMultipleTest() throws InterruptedException, Throwable {
        addElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.SINGLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        setSize(130, 130);

        selectionHelper = new MultipleSelectionHelper(1, 10);
        selectionHelper.setSingleCell(false);
        selectionHelper.setMultiple(true);

        requestFocusOnControl(testedControl);

        //Testing section
        try {
            for (int i = 0; i < 2; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
                selectionHelper.push(KeyboardButtons.DOWN);
            }

            for (int i = 0; i < 4; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                selectionHelper.push(KeyboardButtons.DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
                checkSelection();
            }
            //Elements from 2 to 6 are selected

            for (int i = 0; i < 2; i++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                selectionHelper.push(KeyboardButtons.DOWN, CTRL_DOWN_MASK_OS);
                checkSelection();
            }

            testedControl.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);
            selectionHelper.ctrlA = true;
            currentListContentSize = 10;
            checkSelection();
            checkListener(Listeners.focusedIndex, 8);
            checkListener(Listeners.selectedIndex, 8);

            // End of testing section
        } catch (Throwable error) {
            throw error;
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseClickingVerticalMultipleTest() throws InterruptedException, Throwable {
        commonClickingTest(Orientation.VERTICAL, SelectionMode.MULTIPLE);
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseClickingHorizontalMultipleTest() throws InterruptedException, Throwable {
        commonClickingTest(Orientation.HORIZONTAL, SelectionMode.MULTIPLE);
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseClickingVerticalSingleTest() throws InterruptedException, Throwable {
        commonClickingTest(Orientation.VERTICAL, SelectionMode.SINGLE);
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseClickingHorizontalSingleTest() throws InterruptedException, Throwable {
        commonClickingTest(Orientation.HORIZONTAL, SelectionMode.SINGLE);
    }

    protected void commonClickingTest(Orientation orientation, SelectionMode selectionMode) throws InterruptedException, Throwable {
        setSize(130, 130);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.SINGLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, selectionMode, Properties.selectionMode);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientation, Properties.orientation);
        for (int i = 1; i < listItems; i++) {
            addElement(String.valueOf(Integer.valueOf((int) Math.round(Math.pow(i, i)))), i - 1);
        }

        selectionCycle(0, listItems - 1, null, selectionMode);

        localReset();

        selectionCycle(0, listItems - 1, KeyboardButtons.SHIFT, selectionMode);

        localReset();

        selectionCycle(0, listItems - 1, KeyboardButtons.CONTROL, selectionMode);
    }

    @Test(timeout = 300000)
    /**
     * Add big amount of elements in a list and select some of the according to
     * some rule. After that checks, whether focus is correct.
     */
    public void simpleSelectionScreenShotTest() throws InterruptedException, Throwable {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);

        final int elementsCount = 15;
        addAndSelectElements(elementsCount);

        final int step = 6;
        checkScreenshotsWithStep("SimpleTest", elementsCount, step);
    }

    @Test(timeout = 300000)//RT-18234 - possible
    /**
     * Add big amount of elements in a list and select some of the according to
     * some rules. It tries to apply different combinations of keys pushing with
     * modifiers. A few time in process it tries to check screenshots.
     */
    public void complexSelectionScreenShotTest() throws InterruptedException, Throwable {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        selectionHelper.setMultiple(true);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);

        final int elementsCount = 50;
        addAndSelectElements(elementsCount);
        final int step = 5;

        for (Orientation orientation : Orientation.values()) {
            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientation, Properties.orientation);
            Wrap<Text> cellWrap = getCellWrap((Integer) (0)); //mouse will be over the second item.
            cellWrap.as(Showable.class).shower().show();
            cellWrap.mouse().click(1, cellWrap.getClickPoint(), Mouse.MouseButtons.BUTTON1, CTRL_DOWN_MASK_OS);
            selectionHelper.click(0, 0, KeyboardButtons.CONTROL);
            checkSelection();

            KeyboardButtons lessKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.LEFT : KeyboardButtons.UP);
            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(2, KeyboardButtons.PAGE_DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
            applyKeysPushing(1, KeyboardButtons.PAGE_DOWN, CTRL_DOWN_MASK_OS);
            applyKeysPushing(2, KeyboardButtons.PAGE_UP, KeyboardModifiers.SHIFT_DOWN_MASK);
            applyKeysPushing(1, KeyboardButtons.PAGE_UP, CTRL_DOWN_MASK_OS);

            applyKeysPushing(15, moreKey, CTRL_DOWN_MASK_OS);
            for (int i = 0; i < 10; i++) {
                if (Utils.isMacOS()) {
                    applyKeysPushing(i, KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS, KeyboardModifiers.CTRL_DOWN_MASK);
                } else {
                    applyKeysPushing(i, KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);
                }
                applyKeysPushing(2, lessKey, CTRL_DOWN_MASK_OS);
            }
            applyKeysPushing(5, lessKey, KeyboardModifiers.SHIFT_DOWN_MASK);
            applyKeysPushing(1, KeyboardButtons.SPACE, KeyboardModifiers.SHIFT_DOWN_MASK);
            applyKeysPushing(2, KeyboardButtons.PAGE_DOWN, CTRL_DOWN_MASK_OS);
            checkScreenshotsWithStep("ComlexTests_" + orientation + "_1", elementsCount, step);

            applyKeysPushing(1, KeyboardButtons.HOME, CTRL_DOWN_MASK_OS);
            applyKeysPushing(1, KeyboardButtons.END, KeyboardModifiers.SHIFT_DOWN_MASK);
            applyKeysPushing(1, KeyboardButtons.HOME, CTRL_DOWN_MASK_OS);
            applyKeysPushing(1, KeyboardButtons.END, CTRL_DOWN_MASK_OS);
            applyKeysPushing(1, KeyboardButtons.HOME, KeyboardModifiers.SHIFT_DOWN_MASK);

            applyKeysPushing(20, lessKey, CTRL_DOWN_MASK_OS);
            applyKeysPushing(5, moreKey, KeyboardModifiers.SHIFT_DOWN_MASK);
            applyKeysPushing(3, moreKey, CTRL_DOWN_MASK_OS);
            applyKeysPushing(2, KeyboardButtons.PAGE_UP, CTRL_DOWN_MASK_OS);
            checkScreenshotsWithStep("ComlexTests_" + orientation + "_2", elementsCount, step);
        }
    }

    @Test(timeout = 300000)
    public void simpleSelectionUpTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(10, moreKey, CTRL_DOWN_MASK_OS);

            moveSelectUp(5, orientation);

            checkMainListeners(5, 5);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleSelectionDownTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(10, moreKey, CTRL_DOWN_MASK_OS);

            moveSelectDown(5, orientation);

            checkMainListeners(15, 15);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleMoveUpTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(10, moreKey, CTRL_DOWN_MASK_OS);

            moveFocusUp(5, orientation);

            checkMainListeners(5, 18);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleMoveDownTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(10, moreKey, CTRL_DOWN_MASK_OS);

            moveFocusDown(5, orientation);

            checkMainListeners(15, 18);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleMovePageUpTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);
            selectionHelper.setPageHeight(8);
            selectionHelper.setPageWidth(1);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(15, moreKey, CTRL_DOWN_MASK_OS);

            moveFocusPageUp(1);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleMovePageDownTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);
            selectionHelper.setPageHeight(8);
            selectionHelper.setPageWidth(1);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(5, moreKey, CTRL_DOWN_MASK_OS);

            moveFocusPageDown(1);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleSelectPageUpTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);
            selectionHelper.setPageHeight(8);
            selectionHelper.setPageWidth(1);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(15, moreKey, CTRL_DOWN_MASK_OS);

            moveSelectPageUp(1);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleSelectPageDownTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);
            selectionHelper.setPageHeight(8);
            selectionHelper.setPageWidth(1);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(5, moreKey, CTRL_DOWN_MASK_OS);

            moveSelectPageDown(1);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void simpleMoveHomeTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(15, moreKey, CTRL_DOWN_MASK_OS);

            moveToHome();

            checkMainListeners(0, 18);

            resetSceneByDefault();
        }

    }

    @Test(timeout = 300000)
    public void simpleMoveEndTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(5, moreKey, CTRL_DOWN_MASK_OS);

            moveToEnd();

            checkMainListeners(19, 18);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)//RT-18413
    public void simpleSelectHomeTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(15, moreKey, CTRL_DOWN_MASK_OS);

            selectToHome();

            checkMainListeners(0, 18);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)//RT-18412
    public void simpleSelectEndTest() throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 20);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(5, moreKey, CTRL_DOWN_MASK_OS);

            selectToEnd();

            checkMainListeners(19, 19);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 60000)
    public void compositeSelectionTest() throws InterruptedException, Throwable {
        for (Orientation orientation : Orientation.values()) {
            final int elementsCount = 20;
            prepareSelectionHelper(orientation, elementsCount);
            selectionHelper.setPageHeight(8);
            selectionHelper.setPageWidth(1);

            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

            applyKeysPushing(10, moreKey, CTRL_DOWN_MASK_OS);//we are in center

            final int stepForScreenShotMaking = 8;

            moveSelectUp(5, orientation);
            moveFocusPageDown(1);
            moveSelectUp(3, orientation);
            moveSelectPageUp(1);
            moveFocusDown(4, orientation);
            moveSelectUp(6, orientation);
            selectToHome();
            moveSelectDown(4, orientation);
            checkScreenshotsWithStep("ComplexSelectionTest_1_" + orientation, elementsCount, stepForScreenShotMaking);
            selectToEnd();
            moveFocusUp(3, orientation);
            moveSelectUp(5, orientation);
            moveToHome();
            moveSelectPageDown(1);
            moveSelectDown(4, orientation);
            moveToEnd();
            moveSelectDown(4, orientation);
            moveSelectUp(5, orientation);
            moveFocusPageUp(2);
            moveSelectDown(4, orientation);
            checkScreenshotsWithStep("ComplexSelectionTest_2_" + orientation, elementsCount, stepForScreenShotMaking);

            resetSceneByDefault();
        }
    }

    @Test(timeout = 300000)
    public void selectionCancelTest() throws InterruptedException {
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.HOME);
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.END);
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.UP);
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.DOWN);
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.LEFT);
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.RIGHT);
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.PAGE_UP);
        checkKeyboardButtonOnCancellingSelection(KeyboardButtons.PAGE_DOWN);
    }

    @Test(timeout = 300000)
    public void focusTraversalSelectionSavingTest() throws InterruptedException {
        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(Orientation.HORIZONTAL, KeyboardButtons.UP);
        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(Orientation.HORIZONTAL, KeyboardButtons.DOWN);

        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(Orientation.VERTICAL, KeyboardButtons.RIGHT);
        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(Orientation.VERTICAL, KeyboardButtons.LEFT);

        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(KeyboardButtons.TAB);
        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(KeyboardButtons.TAB, KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    /**
     * Checks that when the sorting is applied to the underlying data collection
     * the cells are rendered in the right order.
     */
    @Smoke
    @Test(timeout = 30000)
    public void renderingAfterSortingTest() {
        try {
            setSize(100, 218);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

        addElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 100500);

        final int ITEMS_COUNT = 9;

        StringConverter<String> conv = new StringConverter<String>() {
            @Override
            public String toString(String t) {
                return t;
            }

            @Override
            public String fromString(String s) {
                return s;
            }
        };

        SortValidator<String, ListCell> validator = new SortValidator<String, ListCell>(ITEMS_COUNT, conv) {
            @Override
            protected void setControlData(final ObservableList<String> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        ListView listView = (ListView) testedControl.getControl();
                        listView.setItems(ls);
                    }
                }.dispatch(testedControl.getEnvironment());
            }

            @Override
            protected Lookup<? extends ListCell> getCellsLookup() {
                return testedControl.as(Parent.class, Node.class)
                        .lookup(ListCell.class, new LookupCriteria<ListCell>() {
                    public boolean check(ListCell cell) {
                        return cell.isVisible();
                    }
                });
            }

            @Override
            protected String getTextFromCell(ListCell cell) {
                return cell.getText();
            }
        };

        boolean result = validator.check();
        String msg = validator.getFailureReason();
        assertTrue(msg, result);
    }

    protected void checkKeyboardButtonOnCancellingSelection(KeyboardButtons button) throws InterruptedException {
        for (Orientation orientation : Orientation.values()) {
            prepareSelectionHelper(orientation, 10);
            selectionHelper.setPageHeight(8);
            selectionHelper.setPageWidth(1);
            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);
            applyKeysPushing(5, moreKey, CTRL_DOWN_MASK_OS);
            if (!(orientation == Orientation.HORIZONTAL && (button == KeyboardButtons.UP || button == KeyboardButtons.DOWN))
                    && !(orientation == Orientation.VERTICAL && (button == KeyboardButtons.LEFT || button == KeyboardButtons.RIGHT))) //because it is focus traversal.
            {
                applyKeysPushing(1, button);
            }
            resetSceneByDefault();
        }
    }

    protected void checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(KeyboardButtons button, KeyboardModifiers... modifiers) throws InterruptedException {
        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(Orientation.HORIZONTAL, button, modifiers);
        checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(Orientation.VERTICAL, button, modifiers);
    }

    protected void checkKeyboardButtonOnNonCancellingSelectionBecauseTraversal(Orientation orientation, KeyboardButtons button, KeyboardModifiers... modifiers) throws InterruptedException {
        resetSceneByDefault();
        requestFocusOnControl(testedControl);
        prepareSelectionHelper(orientation, 10);
        KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);
        applyKeysPushing(5, moreKey, CTRL_DOWN_MASK_OS);

        testedControl.keyboard().pushKey(button, modifiers);

        checkSelection();
    }

    protected void checkMainListeners(int focusIndex, int selectedIndex) {
        checkListener(Listeners.focusedIndex, focusIndex);
        checkListener(Listeners.selectedIndex, selectedIndex);
    }

    protected void checkMainListeners(int focusIndex, int editingIndex, int selectedIndex) {
        checkMainListeners(focusIndex, selectedIndex);
        checkListener(Listeners.editingIndex, editingIndex);
    }

    protected void prepareSelectionHelper(Orientation orientation, int numberOfElements) throws InterruptedException {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientation, Properties.orientation);
        setSize(195, 195);

        KeyboardButtons lessKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.LEFT : KeyboardButtons.UP);
        KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);

        selectionHelper = new MultipleSelectionHelper(1, numberOfElements);
        selectionHelper.setMultiple(true);

        for (int i = 0; i < numberOfElements; i++) {
            addElement(String.valueOf(i * i), i);
        }

        requestFocusOnControl(testedControl);

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((ListView) testedControl.getControl()).getSelectionModel().clearSelection();
                ((ListView) testedControl.getControl()).getFocusModel().focus(0);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        //testedControl.keyboard().pushKey(moreKey, CTRL_DOWN_MASK_OS);
        for (int i = 1; i < numberOfElements; i++) {
            applyKeysPushing(1, moreKey, CTRL_DOWN_MASK_OS);
            if (i % 2 == 0) {
                if (Utils.isMacOS()) {
                    applyKeysPushing(1, KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS, KeyboardModifiers.CTRL_DOWN_MASK);
                } else {
                    applyKeysPushing(1, KeyboardButtons.SPACE, CTRL_DOWN_MASK_OS);
                }
            }
        }
        applyKeysPushing(numberOfElements, lessKey, CTRL_DOWN_MASK_OS);
    }
}