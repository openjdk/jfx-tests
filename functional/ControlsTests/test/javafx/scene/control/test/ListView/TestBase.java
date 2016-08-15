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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import static javafx.scene.control.test.ListView.NewListViewApp.*;
import javafx.scene.control.test.util.MultipleSelectionHelper;
import javafx.scene.control.test.util.MultipleSelectionHelper.Range;
import javafx.scene.control.test.util.TableListCommonTests;
import static javafx.scene.control.test.utils.ComponentsFactory.*;
import javafx.scene.control.test.utils.SelectionFormatter;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import static javafx.scene.control.test.utils.ptables.NodesChoserFactory.*;
import javafx.scene.text.Text;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ListItemWrap.ListItemByObjectLookup;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Showable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import test.javaclient.shared.Utils;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * @author Alexander Kirov
 */
public class TestBase extends TableListCommonTests {

    static Wrap<? extends Scene> scene;
    protected boolean resetHardByDefault = false;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;

    @BeforeClass
    public static void setUpClass() throws Exception {
        NewListViewApp.main(null);
    }

    @Before
    public void setUp() {
        initWrappers();
        setContentSize(1, 9);
        selectionHelper.setPageWidth(1);
        selectionHelper.setPageHeight(10);
        scene.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        scene.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @After
    public void tearDown() {
        //It is needed in cases, when in process of testing, fail happened when
        //shift button was pressed and was not released because of test fail
        //between press and release.
        scene.keyboard().pushKey(KeyboardButtons.SHIFT);

        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
        currentSettingOption = SettingOption.PROGRAM;
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = parent.lookup(ListView.class, new ByID<ListView>(TESTED_LIST_VIEW_ID)).wrap();
    }

    // Wraps for intellisence.
    protected void checkCounter(Counters counter, int value) {
        checkCounterValue(counter, value);
    }

    protected void checkListener(Listeners listener, int value) {
        checkSimpleListenerValue(listener, value);
    }

    protected void checkListener(Listeners listener, String text) {
        checkSimpleListenerValue(listener, text);
    }

    //                       SINGLE BUTTONS
    protected void resetSceneHard() {
        clickButtonForTestPurpose(HARD_RESET_BUTTON_ID);
        initWrappers();
    }

    protected void resetSceneSoft() {
        clickButtonForTestPurpose(SOFT_RESET_BUTTON_ID);
        //initWrappers();
    }

    protected void resetSceneByDefault() {
        if (resetHardByDefault) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }
    }

    protected void doNextResetHard() {
        doNextResetHard = true;
    }

    protected void doNextResetSoft() {
        doNextResetHard = false;
    }

    protected void addRectangleAtPos(int position) {
        setText(findTextField(ADD_RECTANGLE_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(ADD_RECTANGLE_BUTTON_ID);
    }

    protected void addTextFieldAtPos(int position) {
        setText(findTextField(ADD_TEXT_FIELD_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(ADD_TEXT_FIELD_BUTTON_ID);
    }

    protected void startMotion(int position) {
        setText(findTextField(START_MOTION_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(START_MOTION_BUTTON_ID);
    }

    protected void increaseScale(int position) {
        setText(findTextField(INCREASE_SCALE_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(INCREASE_SCALE_BUTTON_ID);
    }

    protected void decreaseScale(int position) {
        setText(findTextField(DECREASE_SCALE_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(DECREASE_SCALE_BUTTON_ID);
    }

    protected void addElement(String element, int position) {
        setText(findTextField(ADD_ITEM_POSITION_TEXT_FIELD_ID), position);
        setText(findTextField(ADD_ITEM_TEXT_FIELD_ID), element);
        clickButtonForTestPurpose(ADD_ITEM_BUTTON_ID);
    }

    protected void addControlToPosition(int controlIndex, int position) {
        setText(findTextField(LIST_VIEW_CONTROL_ADD_INDEX_TEXT_FIELD_ID), String.valueOf(position));
        selectControlFromFactory(controlIndex);
        clickButtonForTestPurpose(NODE_CHOOSER_ACTION_BUTTON_ID);
    }

    protected void addElements(int... elements) {
        for (int i = 0; i < elements.length; i++) {
            addElement(String.valueOf(elements[i]), i);
        }
    }

    protected void addFormAtPos(int position) {
        setText(findTextField(ADD_FORM_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(ADD_FORM_BUTTON_ID);
    }

    protected void clickFormButton() {
        clickButtonForTestPurpose(FORM_BUTTON_ID);
    }

    protected void checkFormClickCounter(int expectedValue) {
        parent.lookup(TextField.class, new ByID<TextField>(FORM_CLICK_TEXT_FIELD_ID)).wrap().waitProperty(Wrap.TEXT_PROP_NAME, String.valueOf(expectedValue));
    }

    protected void scrollFormScrollBar(int move) {
        parent.lookup(ScrollBar.class, new ByID<ScrollBar>(FORM_SCROLLBAR_ID)).wrap().mouse().move();
        parent.lookup(ScrollBar.class, new ByID<ScrollBar>(FORM_SCROLLBAR_ID)).wrap().mouse().turnWheel(move * (Utils.isMacOS() ? -1 : 1));
    }

    protected void checkFormScrollCounter(int expectedValue) {
        parent.lookup(TextField.class, new ByID<TextField>(FORM_SCROLL_TEXT_FIELD_ID)).wrap().waitProperty(Wrap.TEXT_PROP_NAME, String.valueOf(expectedValue));
    }

    protected void removeFromPos(int position) {
        setText(findTextField(REMOVE_ITEM_POS_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(REMOVE_BUTTON_ID);
    }

    protected void scrollTo(int position) {
        setText(findTextField(SCROLL_TO_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(SCROLL_TO_BUTTON_ID);
    }

    protected void changeSelectionModel(int position) {
        clickButtonForTestPurpose(CHANGE_SELECTION_MODEL_BUTTON_ID);
    }

    /**
     * Checks visibility states of horizontal and vertical scrollBars.
     */
    protected void checkScrollbarsStates(boolean horizontalVisibility, boolean verticalVisibility) {
        Assert.assertFalse(findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, horizontalVisibility) == null);
        Assert.assertFalse(findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, verticalVisibility) == null);
    }

    /**
     * Set size if tested control using bidirectional bindings.
     */
    protected void setSize(double width, double height) throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, height);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, width);
    }

    /**
     * Make chessboard focus.
     *
     * @param elemCount - length of list of added elements.
     */
    protected void addAndSelectElements(final int elemCount) {
        for (int i = 0; i < elemCount; i++) {
            addElement(String.valueOf(i * i), i);
        }

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ListView lv = (ListView) os[0];
                for (int i = 0; i < elemCount; i++) {
                    if (Integer.valueOf((String) lv.getItems().get(i)) % 2 == 0) {
                        lv.getSelectionModel().select(i);
                    }
                }
            }
        }.dispatch(Root.ROOT.getEnvironment(), (ListView) testedControl.getControl());
    }

    protected void checkScreenshotsWithStep(String name, final int elementsCount, final int step) throws Throwable {
        Wrap<Text> cellWrap = getCellWrap((Integer) (0)); //mouse will be over the second item.
        cellWrap.as(Showable.class).shower().show();
        cellWrap.mouse().click(1, cellWrap.getClickPoint(), Mouse.MouseButtons.BUTTON1, CTRL_DOWN_MASK_OS);
        for (int i = 0; i < elementsCount / step; i++) {
            for (int j = 0; j < step; j++) {
                testedControl.keyboard().pushKey(KeyboardButtons.RIGHT, CTRL_DOWN_MASK_OS);
            }
            checkScreenshot("ListView_" + name + "_" + i, testedControl);
        }

        throwScreenshotError();
    }

    @Override
    protected void checkSelection() {
        testedControl.waitState(new State() {
            public Object reached() {
                Collection<Point> helper_selected = selectionHelper.getSelected();
                Collection<Point> selected = getSelected();
                Point helper_selection = selectionHelper.focus;
                Point selection = getSelectedItem();

                System.out.println(SelectionFormatter.format("Helper selection: ", helper_selected, "Selection: ", selected));
                System.out.println("Helper focus : " + helper_selection);
                System.out.println("Focus        : " + selection);
                System.out.println("Anchor : " + selectionHelper.anchor + "\n\n");

                if (((helper_selected.size() == selected.size())
                        && helper_selected.containsAll(selected)
                        && selected.containsAll(helper_selected)
                        && selection.equals(helper_selection))
                        || (selectionHelper.ctrlA && (selected.size() == currentListContentSize))) {
                    return true;
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * @return hashSet of selected items in listView
     */
    @Override
    protected HashSet<Point> getSelected() {
        return new GetAction<HashSet<Point>>() {
            @Override
            public void run(Object... parameters) throws Exception {
                HashSet<Point> selected = new HashSet<Point>();
                MultipleSelectionModel model = ((Wrap<? extends ListView>) testedControl).getControl().getSelectionModel();
                for (Object obj : model.getSelectedIndices()) {
                    Integer pos = (Integer) obj;
                    selected.add(new Point(-1, pos));
                }
                setResult(selected);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    @Override
    protected Point getSelectedItem() {
        return new GetAction<Point>() {
            @Override
            public void run(Object... parameters) throws Exception {
                Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                    public boolean check(Node row) {
                        return ListCell.class.isAssignableFrom(row.getClass())
                                && ((ListCell) row).isFocused()
                                && ((ListCell) row).isVisible();
                    }
                });
                if (lookup.size() > 0) {
                    setResult(new Point(-1, ((ListCell) lookup.get()).getIndex()));
                    return;
                }
                setResult(new Point(-1, -1));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void localReset() {
        clearList();
        addExponentialContent();
        testedControl.keyboard().pushKey(KeyboardButtons.HOME);
    }

    protected void clearList() {
        for (int i = 1; i < listItems; i++) {
            removeFromPos(0);
        }
    }

    protected void addExponentialContent() {
        for (int i = 1; i < listItems; i++) {
            addElement(String.valueOf(Integer.valueOf((int) Math.round(Math.pow(i, i)))), i - 1);
        }
    }

    protected void applyKeysPushing(int times, KeyboardButtons button, KeyboardModifiers... modifiers) {
        for (int i = 0; i < times; i++) {
            if (button == KeyboardButtons.PAGE_DOWN || button == KeyboardButtons.PAGE_UP) {
                selectionHelper.setVisibleRange(getVisibleRange());
            }
            testedControl.keyboard().pushKey(button, modifiers);

            if (button == KeyboardButtons.RIGHT) {
                selectionHelper.push(KeyboardButtons.DOWN, modifiers);
            } else if (button == KeyboardButtons.LEFT) {
                selectionHelper.push(KeyboardButtons.UP, modifiers);
            } else {
                selectionHelper.push(button, modifiers);
            }
            checkSelection();
        }
    }

    protected void selectionCycle(int firstLine, int lastLine, KeyboardButtons modifier, SelectionMode mode) throws Throwable {
        if (isListControl()) {
            selectionHelper = new MultipleSelectionHelper.ListViewMultipleSelectionHelper(1, 8);
        } else {
            selectionHelper = new MultipleSelectionHelper(1, 8);
        }
        selectionHelper.setSingleCell(false);
        selectionHelper.setMultiple((mode == SelectionMode.MULTIPLE ? true : false));
        selectionHelper.push(KeyboardButtons.HOME);

        if (modifier != null) {
            testedControl.keyboard().pressKey(modifier);
        }
        try {
            for (int j = firstLine; j < lastLine; j++) {
                Wrap<Text> cellWrap = getCellWrap(Integer.valueOf((int) Math.round(Math.pow(j + 1, j + 1))));
                mouseCellClick(cellWrap, modifier);
                selectionHelper.click(-1, j, modifier);
                checkSelection();
            }

            int j = firstLine + (lastLine - firstLine) / 2;
            Wrap<Text> cellWrap = getCellWrap(Integer.valueOf((int) Math.round(Math.pow(j + 1, j + 1))));
            mouseCellClick(cellWrap, modifier);
            selectionHelper.click(-1, j, modifier);
            checkSelection();

            j = firstLine;
            cellWrap = getCellWrap(Integer.valueOf((int) Math.round(Math.pow(j + 1, j + 1))));
            mouseCellClick(cellWrap, modifier);
            selectionHelper.click(-1, j, modifier);
            checkSelection();

            j = lastLine - 1;
            cellWrap = getCellWrap(Integer.valueOf((int) Math.round(Math.pow(j + 1, j + 1))));
            mouseCellClick(cellWrap, modifier);
            selectionHelper.click(-1, j, modifier);
            checkSelection();

        } catch (Throwable error) {
            throw error;
        } finally {
            if (modifier != null) {
                testedControl.keyboard().releaseKey(modifier);
            }
        }
    }

    /**
     * @return wrapper over required data item which can be used for mouse
     * actions
     */
    protected Wrap<Text> getCellWrap(final Integer item) {
        return testedControl.as(Parent.class, String.class).lookup(
                new LookupCriteria<String>() {
                    @Override
                    public boolean check(String cell_item) {
                        return cell_item.equals(String.valueOf(item));
                    }
                }).wrap();
    }

    protected void mouseCellClick(Wrap<Text> cell, KeyboardButtons mod) {
        cell.as(Showable.class).shower().show();
        Point cp = cell.getClickPoint();
        if (mod == null) {
            cell.mouse().click(1, cp, Mouse.MouseButtons.BUTTON1);
        } else {
            switch (mod) {
                case SHIFT:
                    cell.mouse().click(1, cp, Mouse.MouseButtons.BUTTON1, KeyboardModifiers.SHIFT_DOWN_MASK);
                    break;
                case CONTROL:
                    cell.mouse().click(1, cp, Mouse.MouseButtons.BUTTON1, CTRL_DOWN_MASK_OS);
                    break;
            }
        }
    }

    protected void scrollTo(final int inXCoord, int inYCoord) {
        if (inXCoord > 1) {
            throw new IllegalArgumentException("Incorrect X coordinate!");
        }

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((ListView) testedControl.getControl()).scrollTo(inXCoord);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void switchOnMultiple() {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, SelectionMode.MULTIPLE, Properties.selectionMode);
    }

    protected void showItem(int item) {
        getCellWrap((Integer) (item)).as(Showable.class).shower().show();
    }

    protected void moveSelectUp(int times, Orientation orientation) {
        KeyboardButtons lessKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.LEFT : KeyboardButtons.UP);
        applyKeysPushing(times, lessKey, KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    protected void moveSelectDown(int times, Orientation orientation) {
        KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);
        applyKeysPushing(times, moreKey, KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    protected void moveSelectPageUp(int times) {
        applyKeysPushing(times, KeyboardButtons.PAGE_UP, KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    protected void moveSelectPageDown(int times) {
        applyKeysPushing(times, KeyboardButtons.PAGE_DOWN, KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    protected void moveFocusUp(int times, Orientation orientation) {
        KeyboardButtons lessKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.LEFT : KeyboardButtons.UP);
        applyKeysPushing(times, lessKey, CTRL_DOWN_MASK_OS);
    }

    protected void moveFocusDown(int times, Orientation orientation) {
        KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN);
        applyKeysPushing(times, moreKey, CTRL_DOWN_MASK_OS);
    }

    protected void moveFocusPageUp(int times) {
        applyKeysPushing(times, KeyboardButtons.PAGE_UP, CTRL_DOWN_MASK_OS);
    }

    protected void moveFocusPageDown(int times) {
        applyKeysPushing(times, KeyboardButtons.PAGE_DOWN, CTRL_DOWN_MASK_OS);
    }

    protected void moveToEnd() {
        applyKeysPushing(1, KeyboardButtons.END, CTRL_DOWN_MASK_OS);
    }

    protected void selectToEnd() {
        applyKeysPushing(1, KeyboardButtons.END, KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    protected void moveToHome() {
        applyKeysPushing(1, KeyboardButtons.HOME, CTRL_DOWN_MASK_OS);
    }

    protected void selectToHome() {
        applyKeysPushing(1, KeyboardButtons.HOME, KeyboardModifiers.SHIFT_DOWN_MASK);
    }

    @Override
    protected void adjustControl() {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        try {
            setSize(100, 218);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        for (int i = 0; i < DATA_ITEMS_NUM; i++) {
            addElement(String.valueOf(i), i);
        }
    }

    @Override
    protected void clickOnFirstCell() {
        String item = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult((String) ((ListView) testedControl.getControl()).getItems().get((Integer) os[1]));
            }
        }.dispatch(Root.ROOT.getEnvironment(), testedControl, 0);
        Wrap<Text> cellWrap = getCellWrap(Integer.valueOf(item));
        cellWrap.as(Showable.class).shower().show();
        cellWrap.mouse().click(1, cellWrap.getClickPoint(), Mouse.MouseButtons.BUTTON1);
    }

    @Override
    protected Wrap getCellWrap(int column, final int row) {
        String item = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult((String) ((ListView) testedControl.getControl()).getItems().get(row));
            }
        }.dispatch(Root.ROOT.getEnvironment());
        return getCellWrap(Integer.valueOf(item));
    }
    //must be int in [3, 9]
    static protected final int listItems = 9;
    static protected int currentListContentSize = 10;

    @Override
    protected Range getVisibleRange() {
        int top = -1;
        int bottom = -1;
        final List<Object> states = testedControl.as(Selectable.class).getStates();
        for (int i = 0; i < states.size(); i++) {
            final Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(ListCell.class, new ListItemByObjectLookup<Object>(states.get(i)));
            boolean visible = lookup.size() > 0 && testedControl.getScreenBounds().contains(lookup.wrap().getScreenBounds());
            if (visible && top < 0) {
                top = i;
            }
            if (visible) {
                bottom = i;
            }
            if (!visible && top >= 0) {
                bottom = i - 1;
                break;
            }
        }
        return new Range(top, bottom);
    }

    @Override
    protected void setOrientation(Orientation orientation) {
        setPropertyByChoiceBox(SettingType.SETTER, orientation, Properties.orientation);
    }

    // All controlled properties.
    static protected enum Properties {

        editable, orientation, prefWidth, prefHeight, selectionMode, fixedCellSize
    };

    static protected enum Listeners {

        selectedIndex, selectedItem, focusedIndex, focusedItem, editingIndex
    };

    static protected enum Counters {

        set_on_edit_cancel, set_on_edit_commit, set_on_edit_start, get_on_edit_cancel, get_on_edit_commit, get_on_edit_start
    };
}