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
package javafx.scene.control.test.combobox;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import static javafx.scene.control.test.combobox.ComboBoxApp.*;
import static javafx.scene.control.test.combobox.TestBase.ITER;
import javafx.scene.control.test.combobox.TestBase.Properties;
import static javafx.scene.control.test.combobox.TestBase.testedControl;
import javafx.scene.control.test.util.PropertyTest;
import javafx.scene.control.test.utils.CustomMultipleSelectionModel;
import javafx.scene.control.test.utils.CustomStringConverter;
import static javafx.scene.control.test.utils.CustomStringConverter.FROM_STRING_PREFIX;
import static javafx.scene.control.test.utils.CustomStringConverter.TO_STRING_PREFIX;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.FormatStringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByText;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.ChoiceBoxWrap;
import org.jemmy.fx.control.ListItemWrap.ListItemByObjectLookup;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 *
 * Specification:
 * http://xdesign.us.oracle.com/projects/javaFX/fxcontrols-ue/specifications/combobox/combobox.html
 */
@RunWith(FilteredTestRunner.class)
public class ComboBoxTest extends TestBase {

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void editablePropertyTest() throws Throwable {
        assertEquals((new ComboBox()).editableProperty().getValue(), false);

        testedControl.getEnvironment().setTimeout(Wrap.WAIT_STATE_TIMEOUT, 2000);

        for (SettingType btype : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            for (int i = 0; i < 4; i++) {
                setPropertyByToggleClick(btype, Properties.editable);
                checkEditable(i % 2 == 0 ? true : false);
                checkScreenshot("ComboBox_editable_[" + (i % 2 == 0 ? true : false) + "]", testedControl);
            }
        }

        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void showingPropertyTest() throws InterruptedException {
        assertEquals((new ComboBox()).showingProperty().getValue(), false);

        clickDropDownButton();
        checkPopupShowing(true);
        clickDropDownButton();
        checkPopupShowing(false);
        testedControl.mouse().click();
        checkPopupShowing(true);
        checkButtonForPopupShowing(KeyboardButtons.F4);
        checkButtonForPopupShowing(KeyboardButtons.DOWN, KeyboardModifiers.ALT_DOWN_MASK);
        checkButtonForPopupShowing(KeyboardButtons.UP, KeyboardModifiers.ALT_DOWN_MASK);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        //We switched property and need to wait, until it will be applied, to click on buttons.
        Thread.sleep(1000);

        testedControl.mouse().click();
        checkPopupShowing(false);
        clickDropDownButton();
        checkPopupShowing(true);
        clickDropDownButton();
        checkPopupShowing(false);
    }

    private void checkButtonForPopupShowing(KeyboardButtons button, KeyboardModifiers... mdfs) throws InterruptedException {
        testedControl.keyboard().pushKey(button, mdfs);
        checkPopupShowing(false);
        testedControl.keyboard().pushKey(button, mdfs);
        checkPopupShowing(true);
    }

    @Smoke
    @Test(timeout = 300000)
    public void armedTest() {
        assertEquals((new ComboBox()).armedProperty().getValue(), false);

        testedControl.mouse().move();
        testedControl.mouse().press();

        checkTextFieldText(Properties.armed, "true");

        testedControl.mouse().release();

        checkTextFieldText(Properties.armed, "false");
        checkTextFieldText(Properties.showing, "true");

        testedControl.mouse().press();

        checkTextFieldText(Properties.armed, "false");
        checkTextFieldText(Properties.showing, "false");

        testedControl.mouse().release();

        checkTextFieldText(Properties.armed, "false");
        checkTextFieldText(Properties.showing, "false");
    }

    @Smoke
    @Test(timeout = 300000)//RT-18945
    public void valuePropertyTest() throws InterruptedException {
        assertEquals((new ComboBox()).getValue(), null);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);

        setPropertyByTextField(SettingType.BIDIRECTIONAL, Properties.value, NEW_VALUE_1);
        checkTextFieldText(Properties.value, NEW_VALUE_1);
        assertEquals(getTextFieldText(), NEW_VALUE_1);

        setPropertyByTextField(SettingType.UNIDIRECTIONAL, Properties.value, NEW_VALUE_2);
        checkTextFieldText(Properties.value, NEW_VALUE_2);
        assertEquals(getTextFieldText(), NEW_VALUE_2);

        setPropertyByTextField(SettingType.SETTER, Properties.value, NEW_VALUE_1);
        checkTextFieldText(Properties.value, NEW_VALUE_1);
        assertEquals(getTextFieldText(), NEW_VALUE_1);

        switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.value);

        for (int i = 0; i < ITER; i++) {
            addElement(String.valueOf(i), i);
        }

        for (int i = 0; i < ITER; i++) {
            testedControl.as(Selectable.class).selector().select(String.valueOf(i));
            assertEquals(getValue(), String.valueOf(i));
        }

        testedControl.as(Selectable.class).selector().select(String.valueOf(ITER - 1));

        //Do some actions to change state of control and verify value
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        setPropertyByTextField(SettingType.BIDIRECTIONAL, Properties.promptText, "some text");
        clickDropDownButton();
        applyCustomSelectionModel();
        applyCustomStringConverter();

        assertEquals(getValue().toString(), "null");
        assertEquals(getTextFieldText(), "");
    }

    @ScreenshotCheck
    @Test(timeout = 300000)//RT-19225
    public void promptPropertyTest() throws Throwable {
        assertEquals((new ComboBox()).promptTextProperty().getValue(), "");

        //move focus from control. otherwise, we will not see prompt.
        requestFocusOnControl(testedControl);
        testedControl.keyboard().pushKey(KeyboardButtons.TAB);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);

        for (SettingType btype : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            setPropertyByTextField(btype, Properties.promptText, btype.name());
            checkTextFieldText(Properties.promptText, btype.name());
            assertEquals(btype.name(), getTextFieldPrompt());
            checkScreenshot("ComboBox_PromptText_[" + btype.name() + "]", testedControl);
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void visibleRowCountPropertyTest() throws InterruptedException, Throwable {
        assertEquals((long) (new ComboBox()).visibleRowCountProperty().getValue(), 10);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleRowCount, 6);
        checkTextFieldValue(Properties.visibleRowCount, 6);

        addElements(1, 2, 3, 4, 5, 6, 7);

        clickDropDownButton();
        checkScrollBarVisibility(true);

        Wrap<? extends ListView> list = getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap();
        Wrap<? extends ListCell> cell = list.as(Parent.class, Node.class).lookup(ListCell.class, new ListItemByObjectLookup<String>("1")).wrap();
        assertEquals(cell.getScreenBounds().getHeight() * 6, list.getScreenBounds().getHeight(), 4);

        removeFromPos(2);

        clickDropDownButton();
        checkScrollBarVisibility(false);

        setPropertyBySlider(SettingType.SETTER, Properties.visibleRowCount, 5);
        checkTextFieldValue(Properties.visibleRowCount, 5);

        clickDropDownButton();
        checkScrollBarVisibility(true);
        removeFromPos(5);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.visibleRowCount, 4);
        checkTextFieldValue(Properties.visibleRowCount, 4);

        testedControl.as(Selectable.class).selector().select("2");

        clickDropDownButton();
        checkScrollBarVisibility(true);

        checkScreenshot("ComboBox_visibleRowCount_4_visible_and_5_elements_and_3_removed", getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap());
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void emptyDropDownScreenshotTest() throws InterruptedException, Throwable {
        clickDropDownButton();
        checkPopupShowing(true);
        checkScreenshot("ComboBox_emptyDropDown", getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap());
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void converterPropertyTest() throws InterruptedException {
        assertNotSame((new ComboBox()).getConverter(), null);

        assertTrue(new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((ComboBox) os[0]).getConverter());
            }
        }.dispatch(Root.ROOT.getEnvironment(),
                testedControl.getControl()) instanceof StringConverter);

        String element = "Random element 1";
        addElement(element, 0);

        testedControl.as(Selectable.class).selector().select(element);
        checkSimpleListenerValue(Properties.selectedItem, element);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);

        assertTrue(new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((ComboBox) os[0]).getConverter());
            }
        }.dispatch(Root.ROOT.getEnvironment(),
                testedControl.getControl()) instanceof StringConverter);

        String element2 = "Random element 2";
        addElement(element2, 1);
        testedControl.as(Selectable.class).selector().select(element2);
        checkSimpleListenerValue(Properties.selectedItem, element2);
        assertEquals(getTextFieldText(), element2);

        applyCustomStringConverter();

        assertTrue(new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((ComboBox) os[0]).converterProperty().getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment(),
                testedControl.getControl()) instanceof CustomStringConverter);

        String element3 = "Random element 3";
        addElement(element3, 2);
        testedControl.as(Selectable.class).selector().select(element3);
        checkSimpleListenerValue(Properties.selectedItem, element3);
        assertEquals(getTextFieldText(), TO_STRING_PREFIX + "1");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        testedControl.as(Selectable.class).selector().select(element2);
        checkSimpleListenerValue(Properties.selectedItem, element2);
    }

    @Smoke
    @Test(timeout = 300000)
    public void itemsPropertyTest() throws InterruptedException {
        assertNotSame((new ComboBox()).getItems(), null);
        assertTrue((new ComboBox()).getItems() instanceof ObservableList);

        checkListsEquality();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                addElement(String.valueOf(j), j);
                checkListsEquality();
            }

            for (int j = 0; j < 5; j++) {
                removeFromPos(0);
                checkListsEquality();
            }
        }

    }

    private void checkListsEquality() throws InterruptedException {
        //Equality: both are lists, size are equal, elements each by each are equal
        if (!testedControl.getProperty(Boolean.class, ChoiceBoxWrap.IS_SHOWING_PROP_NAME)) {
            clickDropDownButton();
        }
        assertEquals(((ListView) getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap().getControl()).getItems(), ((ComboBox) testedControl.getControl()).getItems());
    }

    //Test//Jonathan said, that there is no support for multiple
    // selection model now. So I'll wait until he will write it and check my
    // customMultipleSelection model class. So to write test now is impossible.
    public void customMultipleSelectionModelTest() throws InterruptedException {
        assertNotSame((new ComboBox()).getItems(), null);

        clickDropDownButton();

        applyCustomSelectionModel();
        clickDropDownButton();

        assertTrue(new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((ComboBox) os[0]).getSelectionModel());
            }
        }.dispatch(Root.ROOT.getEnvironment(),
                testedControl.getControl()) instanceof CustomMultipleSelectionModel);

        addElements(1, 2, 3);

        for (int i = 1; i <= 3; i++) {
            testedControl.as(Selectable.class).selector().select(String.valueOf(i));
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void onActionPropertyTest() {
        assertTrue((new ComboBox()).getOnAction() == null);
        assertTrue(testedControl.getControl().getOnAction() instanceof EventHandler);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 0);

        addElements(1, 2, 3);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 0);
        testedControl.mouse().click();
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 0);
        testedControl.as(Selectable.class).selector().select("2");
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 1);
        testedControl.keyboard().pushKey(KeyboardButtons.UP);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 2);
        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 3);
        testedControl.keyboard().pushKey(KeyboardButtons.UP, KeyboardModifiers.ALT_DOWN_MASK);
        testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.ALT_DOWN_MASK);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 3);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);

        testedControl.mouse().click();
        testedControl.mouse().click();

        testedControl.keyboard().pushKey(KeyboardButtons.A);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 4);
        testedControl.keyboard().pushKey(KeyboardButtons.ENTER);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 5);
        setPropertyByTextField(SettingType.BIDIRECTIONAL, Properties.value, NEW_VALUE_1);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 7);
        setPropertyByTextField(SettingType.UNIDIRECTIONAL, Properties.value, NEW_VALUE_2);
        checkCounterValue(ON_ACTION_EVENT_COUNTER_ID, 9);
    }

    @Smoke
    @Test(timeout = 300000)//RT-19227
    public void selectionTest() throws InterruptedException, Exception {
        final int number = 10;
        for (int i = 0; i < number; i++) {
            addElement(String.valueOf(i), i);
        }
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleRowCount, 4);

        for (int i = 0; i < number * 2; i++) {
            testedControl.as(Selectable.class).selector().select(String.valueOf(i % number));
            try {
                checkSelectionState(i % number, i % number);
            } catch (Exception e) {
                System.out.println("Exception was thrown on step " + i);
                throw e;
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void selection2Test() throws InterruptedException, Exception {
        clickDropDownButton();
        ListView lw = ((ListView) getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap().getControl());
        final int number = 10;
        for (int i = 0; i < number; i++) {
            addElement(String.valueOf(i), i);
        }
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleRowCount, 4);

        for (int i = 0; i < number * 3; i++) {
            testedControl.as(Selectable.class).selector().select(String.valueOf(i % number));
            clickDropDownButton();
            clickDropDownButton();
            try {
                assertEquals(i % number, lw.getSelectionModel().getSelectedIndex());
                assertEquals(String.valueOf(i % number), lw.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                System.out.println("Exception was thrown on step " + i);
                throw e;
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardF4Tests() throws Exception {
        popupShowingOnKeyPressCommonTest(KeyboardButtons.F4);
    }

    @Smoke
    @Test(timeout = 300000)//RT-18176
    public void keyboardAltUpTest() throws Exception {
        popupShowingOnKeyPressCommonTest(KeyboardButtons.UP, KeyboardModifiers.ALT_DOWN_MASK);
    }

    @Smoke
    @Test(timeout = 300000)//RT-18176
    public void keyboardAltDownTest() throws Exception {
        popupShowingOnKeyPressCommonTest(KeyboardButtons.DOWN, KeyboardModifiers.ALT_DOWN_MASK);
    }

    private void popupShowingOnKeyPressCommonTest(KeyboardButtons button, KeyboardModifiers... mdfs) throws Exception {
        requestFocusOnControl(testedControl);
        for (int j = 0; j <= 1; j++) { //to switch editable property
            setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
            for (int i = 0; i < ITER; i++) {
                testedControl.keyboard().pushKey(button, mdfs);
                if (j == 0) {//editable state.
                    assertEquals(getTextFieldText(), "");
                }
                checkTextFieldText(Properties.value, "null");

                try {
                    checkPopupShowing(i % 2 == 0 ? true : false);
                } catch (Exception e) {
                    System.out.println("Exception was thrown on step " + i);
                    throw e;
                }
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardUpDownTest() throws InterruptedException {
        final int number = 10;
        for (int i = 0; i < number; i++) {
            addElement(String.valueOf(i), i);
        }

        int currentItem = -1;

        testedControl.mouse().click();
        testedControl.mouse().click();
        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);

        for (int k = 0; k < 3; k++) {
            for (KeyboardButton key : EnumSet.of(KeyboardButtons.DOWN, KeyboardButtons.UP)) {
                for (int i = 0; i < ITER; i++) {
                    if (key == KeyboardButtons.UP) {
                        currentItem -= 1;
                    } else {
                        currentItem += 1;
                    }
                    currentItem = (int) Math.round(adjustValue(0, number - 1, currentItem));

                    testedControl.keyboard().pushKey(key);

                    checkSelectionState(currentItem, currentItem);
                }
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardPopupCloseTest() throws InterruptedException {
        //According to spec: [Active list is open] close the active list.
        for (int i = 0; i < 2; i++) {
            for (KeyboardButton button : EnumSet.of(KeyboardButtons.ENTER, KeyboardButtons.ESCAPE, KeyboardButtons.SPACE)) {
                clickDropDownButton();
                checkPopupShowing(true);

                requestFocusOnControl(testedControl);
                testedControl.keyboard().pushKey(button);
                checkPopupShowing(false);
            }
        }

        //ANTITEST:
        requestFocusOnControl(testedControl);
        for (int i = 0; i < 2; i++) {
            for (KeyboardButton button : EnumSet.of(KeyboardButtons.ENTER, KeyboardButtons.ESCAPE, KeyboardButtons.SPACE)) {
                checkPopupShowing(false);
                testedControl.keyboard().pushKey(button);
                checkPopupShowing(false);
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void onEscapeNotSelectingTest() throws InterruptedException {
        selectOnKeyboardCommonTest(KeyboardButtons.ESCAPE);
    }

    @Smoke
    @Test(timeout = 300000)
    public void onEnterSelectTest() throws InterruptedException {
        selectOnKeyboardCommonTest(KeyboardButtons.ENTER);
    }

    @Smoke
    @Test(timeout = 300000)
    public void onSpaceSelectTest() throws InterruptedException {
        selectOnKeyboardCommonTest(KeyboardButtons.SPACE);
    }

    private void selectOnKeyboardCommonTest(KeyboardButtons button) throws InterruptedException {
        int number = 10;
        for (int i = 0; i < number; i++) {
            addElement(String.valueOf(i), i);
        }

        for (int i = 1; i < number; i++) {
            testedControl.keyboard().pushKey(KeyboardButtons.F4);
            testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
            testedControl.keyboard().pushKey(button);
            checkPopupShowing(false);
            checkTextFieldText(Properties.value, String.valueOf((i - 1) % number));
        }
    }

    //RT-18064 that would be a feature, so that is not a test//Ok.
    public void keyboardAnyKeyTest() {
        addElements("aaa", "bbb", "ccc");

        requestFocusOnControl(testedControl);
        testedControl.keyboard().pushKey(KeyboardButtons.B);
        checkSimpleListenerValue(Properties.selectedItem, "bbb");
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseTest() throws InterruptedException {

        //common click
        testedControl.mouse().click();
        checkTextFieldText(Properties.showing, "true");
        testedControl.mouse().click();
        checkTextFieldText(Properties.showing, "false");

        //drop down button test
        clickDropDownButton();
        checkTextFieldText(Properties.showing, "true");
        clickDropDownButton();
        checkTextFieldText(Properties.showing, "false");

        //select test
        addElement("0", 0);
        testedControl.as(Selectable.class).selector().select("0");
        assertEquals("0", getCurrentValue());

        //scrollbar interaction test
        addElements(1, 2, 3, 4, 5, 6);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleRowCount, 5);
        clickDropDownButton();
        Wrap<? extends ScrollBar> sb = findScrollBar(((Wrap<? extends ListView>) getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap()).as(Parent.class, Node.class), Orientation.VERTICAL, true);
        sb.as(AbstractScroll.class).caret().to(1);
        checkTextFieldText(Properties.showing, "true");
        testedControl.as(Selectable.class).selector().select("6");
        checkTextFieldText(Properties.showing, "false");

        //selection in editable state
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        requestFocusOnControl(testedControl);
        testedControl.as(Text.class).type("a");
        assertEquals(getTextFieldText(), "a");

        //drop down in editable state
        clickDropDownButton();
        testedControl.as(Selectable.class).selector().select("3");
        checkTextFieldText(Properties.value, "3");
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)//RT-19227
    public void commonNonEditableTest() throws InterruptedException, Throwable {
        addElements(1, 2, 3, 4, 5, 6);
        checkScreenshot("ComboBox_initial", testedControl);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleRowCount, 3);

        testedControl.mouse().click();
        testedControl.mouse().click();

        testedControl.keyboard().pushKey(KeyboardButtons.UP, KeyboardModifiers.ALT_DOWN_MASK);
        checkScrollBarVisibility(true);
        checkTextFieldText(Properties.showing, "true");

        testedControl.as(Selectable.class).selector().select("6");
        checkSelectionState(5, 6);
        checkTextFieldText(Properties.value, "6");

        clickDropDownButton();
        checkScreenshot("ComboBox_combobox_noneditable[6_is_selected]", testedControl);
        Wrap wrap = getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap();

        checkScreenshot("ComboBox_popup_noneditable[6_is_selected]", wrap);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void commonEditableTest() throws InterruptedException, Throwable {
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        addElements(1, 2, 3, 4, 5, 6);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleRowCount, 4);

        testedControl.as(Selectable.class).selector().select("6");
        checkSelectionState(5, 6);
        assertEquals(getTextFieldText(), "6");
        checkTextFieldText(Properties.value, "6");

        testedControl.mouse().click();
        testedControl.as(Text.class).type("abc");
        testedControl.keyboard().pushKey(KeyboardButtons.ENTER);

        checkSimpleListenerValue(Properties.selectedIndex, 5);
        checkSimpleListenerValue(Properties.selectedItem, "6abc");
        checkTextFieldText(Properties.value, "6abc");

        clickDropDownButton();

        checkScreenshot("ComboBox_combobox_editable", testedControl);
        checkScreenshot("ComboBox_popup_editable", getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap());
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void initialAndFinalSizeTest() throws Throwable {
        String commonScreenShotName = "ComboBox_initial";
        //checkScreenshot(commonScreenShotName, testedControl);//Removed, as size of empty is different.

        int size = 10;
        for (int i = 0; i < size; i++) {
            addElement(String.valueOf(i), i);
            checkScreenshot(commonScreenShotName, testedControl);
        }
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleRowCount, 5);

        for (int i = 0; i < size; i++) {
            removeFromPos(0);
            checkScreenshot(commonScreenShotName, testedControl);
        }

        checkScreenshot(commonScreenShotName, testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void consistentAndEmptySelectionTest() {
        //seems, the only way to do empty selection is using keyboard.UP.
        int number = 5;
        for (int i = 0; i < number; i++) {
            addElement(String.valueOf(i), i);
        }

        requestFocusOnControl(testedControl);
        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
        for (int j = 1; j < number; j++) {
            for (int k = 0; k < j; k++) {
                testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
                checkTextFieldText(Properties.value, String.valueOf(k + 1));
            }

            for (int k = 0; k < number; k++) {
                testedControl.keyboard().pushKey(KeyboardButtons.UP);
                if (k + 1 < j) {
                    checkTextFieldText(Properties.value, String.valueOf(j - k - 1));
                } else {
                    checkTextFieldText(Properties.value, String.valueOf(0));
                }
            }

            checkTextFieldText(Properties.value, "0");
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void addElementsInDropDownTest() {
        addElements(0, 1, 2, 3, 4, 5, 6);

        testedControl.as(Selectable.class).selector().select("3");
        checkSelectionState(3, 3);
        addElement("2,5", 2);
        checkSelectionState(4, 3);
        addElement("3,5", 4);
        checkSelectionState(5, 3);
        addElement("4,5", 6);
        checkSelectionState(5, 3);
    }

    @Smoke
    @Test(timeout = 300000)//RT-18944, RT-19227
    public void removeElementsFromDropDownTest() {
        addElements(0, 1, 2, 3, 4, 5, 6);

        testedControl.as(Selectable.class).selector().select("3");
        checkSelectionState(3, 3);
        removeFromPos(5);
        checkSelectionState(3, 3);
        removeFromPos(4);
        checkSelectionState(3, 3);
        removeFromPos(2);
        checkSelectionState(2, 3);
        removeFromPos(2);
        checkSimpleListenerValue(Properties.selectedIndex, -1);
        checkTextFieldText(Properties.value, "null");
    }

    @Smoke
    @Test(timeout = 300000)//RT-18972
    public void editingChangeModeText() {
        currentSettingOption = SettingOption.MANUAL;//That is needed because otherwise test fails
        addElements(1, 2, 3);
        //initial state is uneditable
        testedControl.as(Selectable.class).selector().select("3");
        checkValue("3");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        //editable
        checkValue("null");
        assertEquals(getTextFieldText(), "");
        testedControl.as(Text.class).type(NEW_VALUE_1);
        checkValue("null");
        testedControl.keyboard().pushKey(KeyboardButtons.ENTER);
        checkValue(NEW_VALUE_1);
        testedControl.as(Selectable.class).selector().select("1");
        assertEquals(getTextFieldText(), "1");
        checkValue("1");
        testedControl.as(Text.class).type(NEW_VALUE_2);
        testedControl.keyboard().pushKey(KeyboardButtons.ENTER);
        checkValue(NEW_VALUE_2 + "1");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        //non-editable
        checkValue("null");
        testedControl.as(Selectable.class).selector().select("2");
        checkValue("2");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);
        //editable
        checkValue("null");
        testedControl.as(Selectable.class).selector().select("1");
        checkValue("1");
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void customCellAPIApplying() throws Throwable {
        assertTrue((new ComboBox()).getCellFactory() == null);
        assertTrue(testedControl.getControl().getCellFactory() == null);
        applyCustomCellAPI();
        assertTrue(testedControl.getControl().getCellFactory() instanceof Callback);
        addElements(1, 2, 3);

        testedControl.as(Selectable.class).selector().select("3");
        checkValue("3");

        testedControl.mouse().click();

        checkScreenshot("ComboBox_Custom_CellAPI_[3]", testedControl);
        Wrap wrap = getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap();

        checkScreenshot("ComboBox_Custom_CellAPI_[Custom3]", wrap);
        throwScreenshotError();
    }
    //add cell factory testing
    static private final String NEW_VALUE_1 = "new value 1";
    static private final String NEW_VALUE_2 = "new value 2";

    @Smoke
    @Test(timeout = 300000)
    public void testOnShowingEvent() throws Throwable {
        testEvents(new CheckFunctor.IdCounter("SET_ON_SHOWING_COUNTER"),
                new CheckFunctor.NullCounter());
    }

    @Smoke
    @Test(timeout = 300000)
    public void testOnShownEvent() throws Throwable {
        testEvents(new CheckFunctor.IdCounter("SET_ON_SHOWN_COUNTER"),
                new CheckFunctor.NullCounter());
    }

    @Smoke
    @Test(timeout = 300000)
    public void testOnHiding() throws Throwable {
        testEvents(new CheckFunctor.NullCounter(),
                new CheckFunctor.IdCounter("SET_ON_HIDING_COUNTER"));
    }

    @Smoke
    @Test(timeout = 300000)
    public void testOnHidden() throws Throwable {
        testEvents(new CheckFunctor.NullCounter(),
                new CheckFunctor.IdCounter("SET_ON_HIDEN_COUNTER"));
    }

    @Smoke
    @Test(timeout = 300000)
    public void testEventsSequence() throws Throwable {

        /**
         * Save default event handlers
         */
        final EventHandler<Event> onShowing = new GetAction<EventHandler<Event>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getOnShowing());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        final EventHandler<Event> onShown = new GetAction<EventHandler<Event>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getOnShown());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        final EventHandler<Event> onHiding = new GetAction<EventHandler<Event>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getOnHiding());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        final EventHandler<Event> onHidden = new GetAction<EventHandler<Event>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getOnHidden());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        final Map<EventHandler<Event>, Long> timestampsOfEvents =
                new HashMap<EventHandler<Event>, Long>();

        /**
         * Assing new event handlers to get timestamps
         */
        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                testedControl.getControl().setOnShowing(new EventHandler() {
                    public void handle(Event t) {
                        timestampsOfEvents.put(onShowing, System.nanoTime());
                    }
                });

                testedControl.getControl().setOnShown(new EventHandler() {
                    public void handle(Event t) {
                        timestampsOfEvents.put(onShown, System.nanoTime());
                    }
                });

                testedControl.getControl().setOnHiding(new EventHandler() {
                    public void handle(Event t) {
                        timestampsOfEvents.put(onHiding, System.nanoTime());
                    }
                });

                testedControl.getControl().setOnHidden(new EventHandler() {
                    public void handle(Event t) {
                        timestampsOfEvents.put(onHidden, System.nanoTime());
                    }
                });
            }
        }.dispatch(Root.ROOT.getEnvironment());

        testEvents(new CheckFunctor.NullCounter(),
                new CheckFunctor() {
            @Override
            public void call(int conterVal) throws Throwable {
                /**
                 * Compare timestamps of events
                 */
                Long onShowingTime = timestampsOfEvents.get(onShowing);
                Long onShownTime = timestampsOfEvents.get(onShown);
                Long onHidingTime = timestampsOfEvents.get(onHiding);
                Long onHiddenTime = timestampsOfEvents.get(onHidden);

                assertEquals(true, onShowingTime < onShownTime);
                assertEquals(true, onShownTime < onHidingTime);
                assertEquals(true, onHidingTime < onHiddenTime);
            }
        });
        /**
         * Restore default event handlers
         */
        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                testedControl.getControl().setOnShowing(onShowing);
                testedControl.getControl().setOnShown(onShown);
                testedControl.getControl().setOnHiding(onHiding);
                testedControl.getControl().setOnHidden(onHidden);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /**
     * Method sets four counters to zero
     */
    private void clearCounters() {
        final String counterInitVal = "0";
        changeTextFieldText("SET_ON_SHOWING_COUNTER_COUNTER_TEXT_FIELD_ID", counterInitVal);
        changeTextFieldText("SET_ON_SHOWN_COUNTER_COUNTER_TEXT_FIELD_ID", counterInitVal);
        changeTextFieldText("SET_ON_HIDING_COUNTER_COUNTER_TEXT_FIELD_ID", counterInitVal);
        changeTextFieldText("SET_ON_HIDEN_COUNTER_COUNTER_TEXT_FIELD_ID", counterInitVal);
    }

    /**
     * Method opens and closes ComboBox by all possible means and calls passed
     * functors after each opening or closing.
     *
     * @param checkCountersAfterOpening
     * @param checkCountersAfterClosing
     * @throws Throwable
     */
    private void testEvents(CheckFunctor checkCountersAfterOpening,
            CheckFunctor checkCountersAfterClosing) throws Throwable {
        /**
         * make sure that comboBox is closed
         */
        assertFalse(new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().showingProperty().getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment()));

        /**
         * Fill ComboBox with elements
         */
        addElements("aaa", "bbb", "ccc", "ddd", "eee");

        clearCounters();

        int counterVal = 1;

        /**
         * Open ComboBox by clicking on it's text field
         */
        testedControl.mouse().click();
        checkPopupShowing(true);
        checkCountersAfterOpening.call(counterVal);
        /**
         * Close it the same way
         */
        testedControl.mouse().click();
        checkPopupShowing(false);
        checkCountersAfterClosing.call(counterVal);

        /**
         * Counter should have increased
         */
        counterVal++;

        /**
         * Click on drop down arrow
         */
        clickDropDownButton();
        checkPopupShowing(true);
        checkCountersAfterOpening.call(counterVal);

        clickDropDownButton();
        checkPopupShowing(false);
        checkCountersAfterClosing.call(counterVal);

        counterVal++;

        /**
         * Press f4
         */
        testedControl.keyboard().pushKey(KeyboardButtons.F4);
        checkPopupShowing(true);
        checkCountersAfterOpening.call(counterVal);

        testedControl.keyboard().pushKey(KeyboardButtons.F4);
        checkPopupShowing(false);
        checkCountersAfterClosing.call(counterVal);

        counterVal++;

        /**
         * Alt+down arrow
         */
        testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.ALT_DOWN_MASK);
        checkPopupShowing(true);
        checkCountersAfterOpening.call(counterVal);

        testedControl.keyboard().pushKey(KeyboardButtons.DOWN, KeyboardModifiers.ALT_DOWN_MASK);
        checkPopupShowing(false);
        checkCountersAfterClosing.call(counterVal);

        counterVal++;

        /**
         * Alt+up arrow
         */
        testedControl.keyboard().pushKey(KeyboardButtons.UP, KeyboardModifiers.ALT_DOWN_MASK);
        checkPopupShowing(true);
        checkCountersAfterOpening.call(counterVal);

        testedControl.keyboard().pushKey(KeyboardButtons.UP, KeyboardModifiers.ALT_DOWN_MASK);
        checkPopupShowing(false);
        checkCountersAfterClosing.call(counterVal);
    }

    private static interface CheckFunctor {

        public void call(int counterVal) throws Throwable;

        static class NullCounter implements CheckFunctor {

            public void call(int counterVal) throws Throwable {
            }
        };

        static class IdCounter implements CheckFunctor {

            private final String id;

            public IdCounter(String id) {
                this.id = id;
            }

            public void call(int counterVal) throws Throwable {
                ComboBoxTest.checkCounterValue(id, counterVal);
            }
        }
    };

    /**
     * Test adds an element to ComboBox when onShowing event is fired and checks
     * that new element was rendered properly
     *
     * @throws Throwable
     */
    @Smoke
    @Test(timeout = 300000)
    public void testOnShowingByModifyingContent() throws Throwable {

        final int initialSize = 4;
        addElements("1", "2", "3", "4");

        clickButtonForTestPurpose(SET_ADDING_ELEMENTS_ON_SHOWING);
        testedControl.mouse().click();
        checkPopupShowing(true);

        Wrap<? extends ListView> list = getPopupWrap()
                .as(Parent.class, Node.class)
                .lookup(ListView.class)
                .wrap();

        Wrap<? extends ListCell> testCell = list
                .as(Parent.class, Node.class)
                .lookup(ListCell.class, new ListItemByObjectLookup<String>(INITIAL_VALUE))
                .wrap();

        Wrap<? extends ListCell> previousCell = list
                .as(Parent.class, Node.class)
                .lookup(ListCell.class, new ListItemByObjectLookup<String>("4"))
                .wrap();

        assertTrue(testCell.getScreenBounds().getY()
                >= previousCell.getScreenBounds().getY()
                + previousCell.getScreenBounds().getHeight());

        /* Check that size of ComboBox list has increased */
        Boolean result = new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getItems().size() == (initialSize + 1));
            }
        }.dispatch(Root.ROOT.getEnvironment());
        assertTrue(result);

        clickButtonForTestPurpose(RESTORE_ON_SHOWING_EVENT_HANDLER);
    }

    /**
     * Test that not editable ComboBox's getEditor() method must return null
     */
    @Smoke
    @Test(timeout = 300000)
    public void testNotEditableGetEditorMethod() {
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable, false);
        checkTextFieldText(Properties.text, "");
    }

    /**
     * Test that text entered in TextField doesn't change ComboBox's value
     */
    @Smoke
    @Test(timeout = 300000)
    public void testInputByEditorProperty() throws Throwable {
        addElements("1", "2", "3", "4");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.editable);

        final String selectedVal = "2";
        testedControl.as(Selectable.class).selector().select(selectedVal);

        /**
         * Change text in TextField
         */
        final String testedText = "aaa";
        setPropertyByTextField(SettingType.SETTER, Properties.text, testedText);

        /**
         * Value shouldn't have changed
         */
        checkTextFieldText(Properties.value, selectedVal);

        checkTextFieldText(Properties.text, testedText);
    }

    /**
     * Test that TextField returned by getEditor() method remains the same after
     * changing editable property
     */
    @Smoke
    @Test(timeout = 300000)
    public void testEditorProperty() throws Throwable {
        new PropertyTest.EditorPropertyTest(testedControl).test();
    }

    /**
     * Test that TextField's parent remains the same after changing editable
     * property
     *
     */
    @Smoke
    @Test(timeout = 300000)
    public void testEditorPropertyParent() throws Throwable {
        new PropertyTest.EditorParentPropertyTest(testedControl).test();
    }

    /**
     * Test that text entered via TextField becomes ComboBox's value when
     * ComboBox looses focus
     */
    @Smoke
    @Test(timeout = 300000)
    public void textEditorPropertyCommitOnFocusLost() throws Throwable {
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);

        final String testedText = "test test test";
        getEditTextFieldWrap().as(Text.class).type(testedText);

        Wrap<? extends TextField> textFieldWrap = findTextField("ARMED_LISTENER_ID");
        textFieldWrap.mouse().click();

        checkTextFieldText(Properties.value, testedText);

        final String anotherText = "another string";
        setPropertyByTextField(SettingType.SETTER, Properties.text, anotherText);

        testedControl.keyboard().pushKey(KeyboardButtons.TAB);
        checkTextFieldText(Properties.value, anotherText);
    }

    /**
     * Test different cell rendering of ComboBox button and list (pop up) Button
     * height must remain the same. Height of cells of ComboBox must increase.
     *
     * http://javafx-jira.kenai.com/browse/RT-24528
     */
    @Smoke
    @Test(timeout = 300000)
    public void testCellRenderingHeight() throws Throwable {

        clickButtonForTestPurpose(POPULATE_COMBOBOX_WITH_FONT_SIZES);

        for (boolean isEditable : new boolean[]{false, true}) {

            if (!isEditable) {
                setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable, false);
            } else {
                setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable);
            }

            Wrap<? extends javafx.scene.text.Text> textFieldWrap = testedControl.as(Parent.class, Node.class)
                    .lookup(javafx.scene.text.Text.class).wrap();
            final double initialButtonHeight = testedControl.getScreenBounds().getHeight();
            double prevCellTextHeight = 0f;

            for (int fontSize = INITIAL_FONT_SIZE; fontSize <= MAX_FONT_SIZE; fontSize += 2) {
                String testVal = String.valueOf(fontSize);

                testedControl.as(Selectable.class).selector().select(String.valueOf(fontSize));

                /* check heighgt of the button */
                assertEquals(initialButtonHeight, testedControl.getScreenBounds().getHeight(), 0.001);

                /* text node should be alwas rendered inside ComboBox button */
                assertTrue(textFieldWrap.getScreenBounds().getY() >= testedControl.getScreenBounds().getY());
                assertTrue(textFieldWrap.getScreenBounds().getY() + textFieldWrap.getScreenBounds().getHeight()
                        <= testedControl.getScreenBounds().getY() + testedControl.getScreenBounds().getHeight());

                /* get wraps of cell and cell text node to compare coordinates */
                clickDropDownButton();

                Wrap<? extends ListView> listWrap = getPopupWrap().as(Parent.class, Node.class)
                        .lookup(ListView.class).wrap();

                Wrap<? extends ListCell> cellWrap = listWrap.as(Parent.class, Node.class)
                        .lookup(ListCell.class, new ByText(testVal, StringComparePolicy.EXACT)).wrap();

                Wrap<? extends javafx.scene.text.Text> cellTextWrap = cellWrap.as(Parent.class, Node.class)
                        .lookup(javafx.scene.text.Text.class).wrap();

                /* cell text should be always inside cell */
                assertTrue(cellTextWrap.getScreenBounds().getY() >= cellWrap.getScreenBounds().getY());
                assertTrue(cellWrap.getScreenBounds().getY() + cellWrap.getScreenBounds().getHeight()
                        >= cellTextWrap.getScreenBounds().getY() + cellTextWrap.getScreenBounds().getHeight());

                if (prevCellTextHeight == 0) {
                    prevCellTextHeight = cellTextWrap.getScreenBounds().getHeight();
                } else {
                    assertTrue(prevCellTextHeight < cellTextWrap.getScreenBounds().getHeight());
                    prevCellTextHeight = cellTextWrap.getScreenBounds().getHeight();
                }

                if (fontSize == INITIAL_FONT_SIZE) {
                    continue;
                }

                Wrap<? extends ListCell> prevCellWrap = listWrap.as(Parent.class, Node.class)
                        .lookup(ListCell.class, new ByText(String.valueOf(fontSize - 2), StringComparePolicy.EXACT)).wrap();

                /* compare cell coordinates with previous cell coordinates */
                assertTrue(cellWrap.getScreenBounds().getY()
                        >= prevCellWrap.getScreenBounds().getY() + prevCellWrap.getScreenBounds().getHeight());
            }
        }
    }

    /**
     * Test different cell rendering of ComboBox button and list (pop up).
     * Button width must remain the same. Width of cells of ComboBox must also
     * remain the same.
     *
     * http://javafx-jira.kenai.com/browse/RT-24528
     */
    @Test(timeout = 300000)
    public void testCellRenderingWidth() throws Throwable {

        clickButtonForTestPurpose(POPULATE_COMBOBOX_WITH_FONT_SIZES);

        for (boolean isEditable : new boolean[]{false, true}) {

            if (!isEditable) {
                setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable, false);
            } else {
                setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.editable, true);
            }

            //Wait editable state application.
            Thread.sleep(1000);

            final double initialButtonWidth = testedControl.getScreenBounds().getWidth();

            if (!isPopupVisible()) {
                clickDropDownButton();
            }
            final double initialPopupWidth = getPopupWrap().getScreenBounds().getWidth();
            clickDropDownButton();

            for (int fontSize = INITIAL_FONT_SIZE; fontSize <= MAX_FONT_SIZE; fontSize += 2) {
                String testVal = String.valueOf(fontSize);

                testedControl.as(Selectable.class).selector().select(String.valueOf(fontSize));

                /* check width of the button */
                assertEquals(initialButtonWidth, testedControl.getScreenBounds().getWidth(), 2.001);

                if (!isPopupVisible()) {
                    clickDropDownButton();
                }

                /* check width of the popup */
                assertEquals(initialPopupWidth, getPopupWrap().getScreenBounds().getWidth(), 1.001);

                /* get wraps of cell and cell text node to compare coordinates */
                Wrap<? extends ListView> listWrap = getPopupWrap().as(Parent.class, Node.class)
                        .lookup(ListView.class).wrap();

                Wrap<? extends ListCell> cellWrap = listWrap.as(Parent.class, Node.class)
                        .lookup(ListCell.class, new ByText(testVal, StringComparePolicy.EXACT)).wrap();

                Wrap<? extends javafx.scene.text.Text> cellTextWrap = cellWrap.as(Parent.class, Node.class)
                        .lookup(javafx.scene.text.Text.class).wrap();

                /* cell text should be always inside cell */
                assertTrue(cellTextWrap.getScreenBounds().getX() >= cellWrap.getScreenBounds().getX());
                assertTrue(cellWrap.getScreenBounds().getY() + cellWrap.getScreenBounds().getHeight()
                        >= cellTextWrap.getScreenBounds().getY() + cellTextWrap.getScreenBounds().getHeight());

                if (fontSize == INITIAL_FONT_SIZE) {
                    continue;
                }

                Wrap<? extends ListCell> prevCellWrap = listWrap.as(Parent.class, Node.class)
                        .lookup(ListCell.class, new ByText(String.valueOf(fontSize - 2), StringComparePolicy.EXACT)).wrap();

                /* compare cell coordinates with previous cell coordinates */
                assertEquals(prevCellWrap.getScreenBounds().getX(), cellWrap.getScreenBounds().getX(), 0.001);
                assertEquals(prevCellWrap.getScreenBounds().getWidth(), cellWrap.getScreenBounds().getWidth(), 0.001);
            }
        }
    }

    /**
     * This test checks that a custom string converter fromString() method is
     * called only once when some value is entered in the editable ComboBox
     */
    @Smoke
    @Test(timeout = 300000)
    public void testStringConvertersFromStringMethod() throws InterruptedException {
        setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 150);

        applyCustomStringConverter();

        setPropertyByToggleClick(SettingType.SETTER, Properties.editable, true);

        final String testString = "test";
        setPropertyByTextField(SettingType.SETTER, Properties.text, testString);

        testedControl.keyboard().pushKey(KeyboardButtons.ENTER);

        checkTextFieldText(Properties.value, FROM_STRING_PREFIX + "1");
    }

    /**
     * Tests format string converter and checks that transformation is performed
     * properly
     */
    @Smoke
    @Test(timeout = 300000)
    public void testFormatStringConverter() throws InterruptedException {
        setPropertyBySlider(SettingType.SETTER, Properties.prefWidth, 150);

        int itemsCount = 3;
        String prefix = "value #";

        final List<String> testedValues = new ArrayList<String>(itemsCount);
        for (int i = 0; i < itemsCount; i++) {
            testedValues.add(prefix + i);
        }

        final String pattern = "Before text. {0} After text.";
        MessageFormat m = new MessageFormat(pattern);
        final Format fmt = new ComboBoxApp.MyMessageFormat(m);

        //Set format string converter
        new GetAction<Object>() {
            @Override
            public void run(Object... parameters) throws Exception {

                testedControl.getControl().setConverter(new FormatStringConverter<String>(fmt));

                //populate control
                testedControl.getControl().getItems().addAll(testedValues);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        setPropertyByToggleClick(SettingType.SETTER, Properties.editable, true);

        //#toString
        for (int i = 0; i < testedValues.size(); i++) {
            testedControl.as(Selectable.class).selector().select(testedValues.get(i));
            assertEquals(getTextFieldText(), fmt.format(testedValues.get(i)));
        }

        //#fromString
        for (int i = 0; i < testedValues.size(); i++) {
            testedControl.as(Text.class).clear();
            testedControl.as(Text.class).type(fmt.format(testedValues.get(i)));
            testedControl.keyboard().pushKey(KeyboardButtons.ENTER);

            checkTextFieldText(Properties.value, testedValues.get(i));
        }

        doNextResetHard();
    }

    /**
     * Checks that when the sorting is applied to the underlying data collection
     * the cells are rendered in the right order.
     */
    @Smoke
    @Test(timeout = 30000)
    public void renderingAfterSortingTest() {
        addElements("1", "2", "3", "4", "1", "2", "3", "4", "1", "42");

        final int ITEMS_COUNT = 9;

        StringConverter<String> conv = new StringConverter<String>() {
            @Override
            public String toString(String s) {
                return s;
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
                    public void run(Object... parameters) throws Exception {
                        testedControl.getControl().setItems(ls);
                    }
                }.dispatch(testedControl.getEnvironment());
            }

            @Override
            protected Lookup<? extends ListCell> getCellsLookup() {
                testedControl.mouse().click();

                Wrap<? extends ListView> listWrap = getPopupWrap().as(Parent.class, Node.class)
                        .lookup(ListView.class).wrap();

                Lookup lookup = listWrap.as(Parent.class, Node.class)
                        .lookup(ListCell.class, new LookupCriteria<ListCell>() {
                    public boolean check(ListCell cell) {
                        return cell.isVisible();
                    }
                });

                testedControl.keyboard().pushKey(KeyboardButtons.ESCAPE);

                return lookup;
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

    /**
     * When there are no items, or items list is null, placeholder about items
     * emptyness is shown in the popup. This test changes placeholder and
     * checks, that it is shown when popup is shown.
     */
    @Smoke
    @Test(timeout = 30000)
    public void emptyListDropDownPlaceholderTest() throws Throwable {
        placeholderTestLoop(true);
        placeholderTestLoop(false);

        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.placeholder, Label.class);
        if (!isPopupVisible(false)) {
            clickDropDownButton();
        }

        //wait for animation
        try {
            Thread.sleep(SLEEP);
        } catch (Exception ignore) {
        }

        checkScreenshot("combobox_placeholder_dimmed",
                new NodeWrap(testedControl.getEnvironment(), new GetAction<Node>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((javafx.scene.Parent) getPopupWrap().getControl().getRoot().getChildrenUnmodifiable().get(0)).getChildrenUnmodifiable().get(0));
            }
        }.dispatch(Root.ROOT.getEnvironment())));
        throwScreenshotError();
    }

    private void placeholderTestLoop(boolean withPopupClosedState) {
        assertEquals("Check initial emptyness of items list.", new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getItems().size());
            }
        }.dispatch(testedControl.getEnvironment()), 0, 0);

        selectObjectFromChoiceBox(SettingType.SETTER, Properties.placeholder, Rectangle.class);
        checkTextFieldTextContaining(Properties.placeholder, "Rectangle");
        testEmptyPlaceholder(Rectangle.class, true, withPopupClosedState);

        addElement("0", 0);
        testEmptyPlaceholder(Rectangle.class, false, withPopupClosedState);

        removeFromPos(0);
        testEmptyPlaceholder(Rectangle.class, true, withPopupClosedState);

        selectObjectFromChoiceBox(SettingType.BIDIRECTIONAL, Properties.placeholder, Polygon.class);
        checkTextFieldTextContaining(Properties.placeholder, "Polygon");
        testEmptyPlaceholder(Polygon.class, true, withPopupClosedState);

        clickButtonForTestPurpose(SET_ITEMS_NULL_BUTTON_ID);
        testEmptyPlaceholder(Polygon.class, true, withPopupClosedState);

        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.placeholder, Rectangle.class);
        checkTextFieldTextContaining(Properties.placeholder, "Rectangle");
        testEmptyPlaceholder(Rectangle.class, true, withPopupClosedState);

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                testedControl.getControl().setItems(FXCollections.<String>observableArrayList("1"));
            }
        }.dispatch(testedControl.getEnvironment());
        testEmptyPlaceholder(Rectangle.class, false, withPopupClosedState);

        removeFromPos(0);
    }

    private void testEmptyPlaceholder(final Class classOfPlaceholder, boolean expectedToBeShown, boolean closePopupAfter) {
        if (!isPopupVisible()) {
            clickDropDownButton();
        }

        testedControl.waitState(new State<Integer>() {
            public Integer reached() {
                final Lookup<Node> lookup = getPopupWrap().as(Parent.class, Node.class).lookup(classOfPlaceholder);
                final int size = lookup.size();
                if (size == 0) {
                    return 0;
                } else {
                    final Wrap placeholderPane = getPopupWrap().as(Parent.class, Node.class).lookup(new ByStyleClass("placeholder")).wrap(0);
                    final Parent<Node> parent = (Parent<Node>) placeholderPane.as(Parent.class, Node.class);
                    assertTrue("Double check : there still must be a placeholder.", parent.lookup(classOfPlaceholder).size() > 0);
                    if (!new GetAction<Boolean>() {
                        @Override
                        public void run(Object... os) throws Exception {
                            setResult(((Wrap<Node>) os[0]).getControl().isVisible());
                        }
                    }.dispatch(testedControl.getEnvironment(), placeholderPane)) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }
        }, expectedToBeShown ? 1 : 0);

        if (closePopupAfter) {
            clickDropDownButton();
        }
    }
}