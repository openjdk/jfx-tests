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
package javafx.scene.control.test.cell;

import client.test.Smoke;
import com.sun.glass.ui.Application;
import com.sun.javafx.scene.control.LabeledText;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.cellapps.CellCustomStringConverter;
import javafx.scene.control.test.cellapps.CellsApp;
import static javafx.scene.control.test.cellapps.CellsApp.*;
import javafx.scene.control.test.cellapps.CellsApp.CellType;
import javafx.scene.control.test.cellapps.CellsApp.DataItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

@RunWith(FilteredTestRunner.class)
public abstract class CellsTestBase extends ControlsTestBase {

    protected static Wrap<? extends Scene> scene = null;
    protected static Parent<Node> parent = null;
    protected static String cellID = null;
    protected static String choiceID = null;
    protected static Wrap<? extends Control> testedControl = null;

    protected static void updateWraps() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
    }

    protected abstract Wrap select(String data);

    protected abstract ObservableList<DataItem> getCurrentProgramValues();

    @After
    public void tearDown() throws InterruptedException {
        Wrap error = parent.lookup(Label.class, new ByID<Label>(ERROR_LABEL_ID)).wrap();
        assertEquals("", error.getProperty(Wrap.TEXT_PROP_NAME));
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                ((Button) os[0]).getOnAction().handle(null);
            }
        }.dispatch(Root.ROOT.getEnvironment(), parent.lookup(Button.class, new ByID(RESET_SCENE_BTN_ID)).wrap().getControl());
    }

    @Smoke
    @Test(timeout = 300000)
    public void enterTextTest() throws InterruptedException {
        doFactoryChange(CellType.CustomCell);
        startEditingByMouse("Data item 0");

        String newData = "New data";
        Wrap<? extends TextField> edit_wrap = parent.lookup(TextField.class, new ByID<TextField>(cellID)).wrap();
        edit_wrap.keyboard().pushKey(KeyboardButtons.A, Utils.isMacOS() ? KeyboardModifiers.META_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK);
        edit_wrap.as(Text.class).type(newData);
        commitEditing(newData);
        select(newData);
    }

    @Smoke
    @Test(timeout = 300000)
    public void escapeEditingTest() throws Exception {
        doFactoryChange(CellType.CustomCell);
        startEditingByMouse("Data item 3");

        String newData = "New data";
        Wrap<? extends TextField> edit_wrap = parent.lookup(TextField.class, new ByID<TextField>(cellID)).wrap();
        edit_wrap.mouse().click();
        edit_wrap.keyboard().pushKey(KeyboardButtons.A, Utils.isMacOS() ? KeyboardModifiers.META_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK);
        edit_wrap.as(Text.class).type(newData);
        escapeEditing(newData);

        try {
            select(newData);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        throw new Exception("Test fails, because we've found item, which should be, because editing was escaped");
    }

    @Smoke
    @Test(timeout = 300000)
    public void editInnerSelectionTest() throws InterruptedException {
        doFactoryChange(CellType.CustomCell);
        Wrap<String> item_wrap = startEditingByMouse("Data item 3");
        item_wrap.mouse().click();
        testedControl.as(Parent.class, Node.class).lookup(TextField.class, new ByID<TextField>(cellID)).wait(1);
    }

    @Smoke
    @Test(timeout = 300000)
    public void editFocusTest() throws InterruptedException {
        doFactoryChange(CellType.CustomCell);
        startEditingByMouse("Data item 3");
        scene.keyboard().pushKey(KeyboardButtons.TAB);
        testedControl.as(Parent.class, Node.class).lookup(TextField.class, new ByID<TextField>(cellID)).wait(1);
    }

    @Smoke
    @Test(timeout = 300000)
    public void clickOtherItemTest() throws InterruptedException {
        doFactoryChange(CellType.CustomCell);
        startEditingByMouse("Data item 3");
        select("Data item 2");
        scene.waitState(new State<Integer>() {
            public Integer reached() {
                return parent.lookup(TextField.class, new ByID<TextField>(cellID)).size();
            }
        }, 0);
    }

    @Smoke
    @Test(timeout = 300000)
    public void editOtherItemTest() throws InterruptedException {
        doFactoryChange(CellType.CustomCell);
        select("Data item 5");
        startEditingByMouse("Data item 3");
        startEditingByMouse("Data item 2");
        parent.lookup(TextField.class, new ByID<TextField>(cellID)).wait(1);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonCheckBoxFactoryWithMouseTest() {
        doCommonCheckOfChanger(CellType.CheckBox, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, null, WayOfEditing.ByMouse);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonCheckBoxFactoryWithKeyboardTest() {
        doCommonCheckOfChanger(CellType.CheckBox, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, null, WayOfEditing.ByKeyboard);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonChoiceBoxFacoryWithMouseTest() {
        doCommonCheckOfChanger(CellType.ChoiceBox, data.get(5), someValues.get(1), someValues.get(1), new CellCustomStringConverter().toString(someValues.get(1).toString()), WayOfEditing.ByMouse);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonChoiceBoxFacoryWithKeyboardTest() {
        doCommonCheckOfChanger(CellType.ChoiceBox, data.get(3), someValues.get(2), someValues.get(2), new CellCustomStringConverter().toString(someValues.get(2).toString()), WayOfEditing.ByKeyboard);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonComboBoxFacoryWithMouseTest() {
        doCommonCheckOfChanger(CellType.ComboBox, data.get(8), someValues.get(2), someValues.get(2), new CellCustomStringConverter().toString(someValues.get(2).toString()), WayOfEditing.ByMouse);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonComboBoxFacoryWithKeyboardTest() {
        doCommonCheckOfChanger(CellType.ComboBox, data.get(4), someValues.get(0), someValues.get(0), new CellCustomStringConverter().toString(someValues.get(0).toString()), WayOfEditing.ByKeyboard);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonTextFieldFacoryWithMouseTest() {
        doCommonCheckOfChanger(CellType.TextField, data.get(7), someValues.get(0), new CellCustomStringConverter().fromString(someValues.get(0).toString()),
                new CellCustomStringConverter().toString(new CellCustomStringConverter().fromString(someValues.get(0).toString())), WayOfEditing.ByMouse);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonTextFieldFacoryWithKeyboardTest() {
        doCommonCheckOfChanger(CellType.TextField, data.get(2), someValues.get(1), new CellCustomStringConverter().fromString(someValues.get(1).toString()),
                new CellCustomStringConverter().toString(new CellCustomStringConverter().fromString(someValues.get(1).toString())), WayOfEditing.ByKeyboard);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonCustomTextFieldFacoryWithMouseTest() {
        doCommonCheckOfChanger(CellType.CustomCell, data.get(7), someValues.get(0), new CellCustomStringConverter().fromString(someValues.get(0).toString()),
                new CellCustomStringConverter().toString(new CellCustomStringConverter().fromString(someValues.get(0).toString())), WayOfEditing.ByMouse);
    }

    @Smoke
    @Test(timeout = 300000)
    public void commonCustomTextFieldFacoryWithKeyboardTest() {
        doCommonCheckOfChanger(CellType.CustomCell, data.get(2), someValues.get(1), new CellCustomStringConverter().fromString(someValues.get(1).toString()),
                new CellCustomStringConverter().toString(new CellCustomStringConverter().fromString(someValues.get(1).toString())), WayOfEditing.ByKeyboard);
    }

    protected Wrap startEditingByMouse(String data) {
        Wrap<? extends DataItem> wrap = select(data);
        wrap.mouse().click();
        return wrap;
    }

    protected Wrap startEditingByKeyboard(String data) {
        Wrap<? extends DataItem> wrap = select(data);
        wrap.keyboard().pushKey(KeyboardButtons.F2);
        return wrap;
    }

    protected void escapeEditing(String data) {
        parent.lookup(TextField.class, new ByText<TextField>(data)).wrap().keyboard().pushKey(KeyboardButtons.ESCAPE);
    }

    protected void commitEditing(String data) {
        parent.lookup(TextField.class, new ByText<TextField>(data)).wrap().keyboard().pushKey(KeyboardButtons.ENTER);
    }

    protected void doCommonCheckOfChanger(CellsApp.CellType cellType, Object editableValue, Object newValue, Object expectedValue, String expectedValueRepresentation, WayOfEditing way) {
        doFactoryChange(cellType);
        checkShowingChangeAfterTypeOfFactoryChanging(cellType);
        changeValue(cellType, way, editableValue, newValue);
        checkValueChangingProgrammly(expectedValue, cellType.equals(CellType.CheckBox) ? ValueType.Bool : ValueType.Str);
        checkValueChangingByShowing(expectedValueRepresentation, cellType);
    }

    protected void doFactoryChange(final CellsApp.CellType cellType) {
        final Wrap<? extends ComboBox<CellType>> comboBox = parent.lookup(ComboBox.class, new ByID(choiceID)).wrap();
        Application.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                try {
                    comboBox.getControl().getSelectionModel().select(cellType);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    protected void changeValue(CellsApp.CellType cellType, WayOfEditing way, Object editableValue, final Object newValue) {
        if (!cellType.equals(CellType.CheckBox)) {
            switch (way) {
                case ByKeyboard:
                    startEditingByKeyboard(editableValue.toString());
                    break;
                case ByMouse:
                    startEditingByMouse(editableValue.toString());
            }
        }

        switch (cellType) {
            case CheckBox:
                Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(CheckBox.class);
                int size = lookup.size();
                assertEquals(size, dataItemsSize + (testedControl.getControl() instanceof TreeView ? 1 : 0), 0);
                for (int i = 0; i < size; i++) {
                    if (way == WayOfEditing.ByMouse) {
                        lookup.wrap(i).mouse().click();
                    } else {
                        new GetAction() {
                            @Override
                            public void run(Object... os) throws Exception {
                                ((Control) os[0]).requestFocus();
                            }
                        }.dispatch(Root.ROOT.getEnvironment(), (Control) lookup.wrap(i).getControl());//Otherwise it use mouse click to get focus and make "selected" value changed.
                        lookup.wrap(i).keyboard().pushKey(KeyboardButtons.SPACE);
                    }
                }
                break;
            case ChoiceBox:
                Lookup lookupChoiceBox = testedControl.as(Parent.class, Node.class).lookup(ChoiceBox.class);
                final Wrap<? extends ChoiceBox> choiceBox = lookupChoiceBox.wrap();
                choiceBox.waitState(new State<Boolean>() {
                    public Boolean reached() {
                        return choiceBox.getControl().isVisible();
                    }
                }, true);
                assertEquals(lookupChoiceBox.size(), 1, 0);
                if (way == WayOfEditing.ByMouse) {
                    choiceBox.as(Selectable.class).selector().select(converter.toString(newValue));
                    choiceBox.waitState(new State<Boolean>() {
                        public Boolean reached() {
                            return choiceBox.getControl().getSelectionModel().getSelectedItem().equals(newValue);
                        }
                    }, true);
                } else { //by keyboard
                    //choiceBox.keyboard().pushKey(KeyboardButtons.SPACE);
                    for (int i = 0; i < getIndexOfValueInChoiceBox(choiceBox, newValue) + 1; i++) {
                        choiceBox.keyboard().pushKey(KeyboardButtons.DOWN);
                    }
                    choiceBox.keyboard().pushKey(KeyboardButtons.ENTER);
                }
                break;
            case ComboBox:
                Lookup lookupComboBox = testedControl.as(Parent.class, Node.class).lookup(ComboBox.class);
                assertEquals(lookupComboBox.size(), 1, 0);
                Wrap<? extends ComboBox> comboBox = lookupComboBox.wrap();
                if (way == WayOfEditing.ByMouse) {
                    comboBox.as(Selectable.class).selector().select(newValue);
                } else { //by keyboard
                    comboBox.keyboard().pushKey(KeyboardButtons.SPACE);
                    for (int i = 0; i < getIndexOfValueInComboBox(comboBox, newValue) + 1; i++) {
                        comboBox.keyboard().pushKey(KeyboardButtons.DOWN);
                    }
                    comboBox.keyboard().pushKey(KeyboardButtons.ENTER);
                }
                break;
            case TextField:
            case CustomCell:
                Lookup lookupTextField = testedControl.as(Parent.class, Node.class).lookup(TextField.class);
                assertEquals(lookupTextField.size(), 1, 0);
                final Wrap<? extends TextField> textField = lookupTextField.wrap();
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        textField.getControl().requestFocus();
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                textField.keyboard().pushKey(KeyboardButtons.HOME);
                textField.keyboard().pushKey(KeyboardButtons.END, KeyboardModifiers.SHIFT_DOWN_MASK);
                textField.keyboard().pushKey(KeyboardButtons.DELETE);
                textField.as(Text.class).type(newValue.toString());
                textField.keyboard().pushKey(KeyboardButtons.ENTER);
                break;
            default:
                throw new IllegalStateException("Unknown version");
        }
    }

    protected int getIndexOfValueInChoiceBox(final Wrap< ? extends ChoiceBox> choiceBox, final Object newValue) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(choiceBox.getControl().getItems().indexOf(newValue));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected int getIndexOfValueInComboBox(final Wrap< ? extends ComboBox> comboBox, final Object newValue) {
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(comboBox.getControl().getItems().indexOf(newValue));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void checkValueChangingByShowing(final String expectedValue, CellType cellType) {
        if (!cellType.equals(CellType.CheckBox)) {
            final int size = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                public boolean check(Node cntrl) {
                    return (cntrl instanceof LabeledText)
                            && ((LabeledText) cntrl).getText().equals(expectedValue)
                            && cntrl.isVisible();
                }
            }).lookup().size();
            assertEquals("Failed to lookup visible labeled with text [" + expectedValue + "]", size, 1, 0);
        }
    }

    protected void checkShowingChangeAfterTypeOfFactoryChanging(CellType cellType) {
        testedControl.waitState(() -> {
            List<String> shownValues = new ArrayList<>();
            Lookup lookup = testedControl.as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                @Override
                public boolean check(final Node cntrl) {
                    if (cntrl instanceof LabeledText) {
                        boolean isVisible = new GetAction<Boolean>() {

                            @Override
                            public void run(Object... os) throws Exception {
                                setResult(cntrl.isVisible());
                            }
                        }.dispatch(Root.ROOT.getEnvironment());
                        if (isVisible) {
                            return true;
                        }
                    }
                    return false;
                }
            }).lookup();
            int size = lookup.size();
            for (int i = 0; i < size; i++) {
                shownValues.add(new GetAction<String>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(((LabeledText) os[0]).getText());
                    }
                }.dispatch(Root.ROOT.getEnvironment(), (LabeledText) lookup.wrap(i).getControl()));
            }

            List<DataItem> failedAttemps = new ArrayList<>();
            for (DataItem item : data) {
                int counter = 0;
                for (String str : shownValues) {
                    if (str.contains(CellCustomStringConverter.TO_STRING_PREFIX) && str.contains(item.toString())) {
                        counter++;
                    }
                }
                if (counter != 1) {
                    System.out.println("For data item <" + item + "> cannot find proper representation.");
                    failedAttemps.add(item);
                }
            }
            if (failedAttemps.isEmpty()) {
                return true;
            } else {
                return null;
            }
        });
    }

    protected void checkValueChangingProgrammly(Object expectedValue, ValueType type) {
        switch (type) {
            case Bool:
                for (DataItem item : getCurrentProgramValues()) {
                    assertEquals((Boolean) expectedValue, item.choiceBoxChecker.getValue());
                }
                break;
            case Str:
                int counter = 0;
                for (DataItem item : getCurrentProgramValues()) {
                    if (item.toString().equals(expectedValue.toString())) {
                        counter++;
                    }
                }
                assertEquals(counter, 1, 0);
                break;
            default:
                throw new IllegalStateException("Unknown option");
        }
    }

    protected enum ValueType {

        Bool, Str
    };

    protected enum WayOfEditing {

        ByMouse, ByKeyboard
    };
}
