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
package javafx.scene.control.test.toggleradiobutton;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.ToggleButtonNewApp;
import static javafx.scene.control.test.ToggleButtonNewApp.*;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import static org.jemmy.fx.control.TextControlWrap.*;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class ToggleButtonTestBase extends ControlsTestBase {

    static Wrap<? extends Scene> scene = null;
    static Parent<Node> parent = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ToggleButtonNewApp.main(null);

        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
    }

    @After
    public void tearDown() {
        clickButton(RESET_BUTTON_ID);
    }

    /**
     * State which is considered as true if toggle/radio button is toggled or
     * checked and false when button untoggled or unchecked.
     *
     * @param state1 That is the state of checkBox which bidirectionally sets
     * state of toggle button 1.
     * @param state2 That is the state of checkBox which unidirectionally sets
     * state of toggle button 2.
     * @param state3 That is the state of ToggleButton1 and state of CheckBox
     * which listen to toggled state of toggle button 1.
     * @param state4 That is the state of ToggleButton2 and state of CheckBox
     * which listen to toggled state of toggle button 2.
     */
    protected void checkAllStates(boolean state1, boolean state2, boolean state3, boolean state4) {
        checkCheckBox(CHECK_BOX_SETTING_1_BIDIR_ID, state1);
        checkCheckBox(CHECK_BOX_SETTING_2_UNIDIR_ID, state2);
        checkTwoStates(state3, state4);
    }

    /**
     * State which is considered as true if toggle/radio button is toggled or
     * checked and false when button untoggled or unchecked.
     *
     * @param state1 That is the state of ToggleButton1 and state of CheckBox
     * which listen to toggled state of toggle button 1.
     * @param state2 That is the state of ToggleButton2 and state of CheckBox
     * which listen to toggled state of toggle button 2.
     */
    protected void checkTwoStates(boolean state1, boolean state2) {
        checkState(TOGGLE_BUTTON_1_ID, CHECK_BOX_LISTENING_1_ID, state1);
        checkState(TOGGLE_BUTTON_2_ID, CHECK_BOX_LISTENING_2_ID, state2);
    }

    /**
     * Clicks on checkbox with according id.
     *
     * @param checkBoxId
     */
    protected void clickCheckBox(String checkBoxId) {
        parent.lookup(CheckBox.class, new ByID<CheckBox>(checkBoxId)).wrap().mouse().click();
    }

    /**
     * Checkes whether the state of CheckBox is as set.
     *
     * @param checkBoxId
     * @param expectedValue
     */
    protected void checkCheckBox(String checkBoxId, boolean expectedValue) {
        parent.lookup(CheckBox.class, new ByID<CheckBox>(checkBoxId)).wrap().waitProperty(SELECTED_PROP_NAME, expectedValue ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
    }

    /**
     * Clicks on toggle button with according id.
     *
     * @param toggleButtonId
     */
    protected void clickToggleButton(String toggleButtonId) {
        parent.lookup(ToggleButton.class, new ByID<ToggleButton>(toggleButtonId)).wrap().mouse().click();
    }

    /**
     * Checks, whether the toggle button has a toggled state as asked.
     *
     * @param toggleButtonId
     * @param selectedExpected
     */
    protected void checkToggleButtonSelection(String toggleButtonId, boolean selectedExpected) {
        parent.lookup(ToggleButton.class, new ByID<ToggleButton>(toggleButtonId)).wrap().waitProperty(SELECTED_PROP_NAME, selectedExpected);
    }

    /**
     * Click button with set id.
     *
     * @param buttonId
     */
    protected void clickButton(String buttonId) {
        parent.lookup(Button.class, new ByID<Button>(buttonId)).wrap().mouse().click();
    }

    /**
     * Checks toggled state of toggle button and checked state of check box.
     *
     * @param toggleButtonId
     * @param checkBoxId
     * @param state
     */
    protected void checkState(String toggleButtonId, String checkBoxId, boolean state) {
        checkToggleButtonSelection(toggleButtonId, state);
        checkCheckBox(checkBoxId, state);
    }

    protected void checkFocuses(boolean toggle1Focused, boolean toogle2Focused) {
        parent.lookup(ToggleButton.class, new ByID<ToggleButton>(TOGGLE_BUTTON_1_ID)).wrap().waitProperty("isFocused", toggle1Focused);
        parent.lookup(ToggleButton.class, new ByID<ToggleButton>(TOGGLE_BUTTON_2_ID)).wrap().waitProperty("isFocused", toogle2Focused);
    }

    /**
     * Complex checking checks that state is set to some value and isn't changed
     * through time or on some other reason later.
     *
     * @param checkBoxState
     * @param counter
     * @throws Exception
     */
    protected void checkTwoStatesComplex(boolean checkBoxState, int counter) throws Exception {
        checkCheckBox(CHECK_BOX_LISTENING_1_ID, checkBoxState);
        checkNonEquivCheckBoxState(CHECK_BOX_LISTENING_1_ID, !checkBoxState);
        checkListeningLabel(counter);
    }

    /**
     * Complex checking checks that state is set to some value and isn't changed
     * through time or on some other reason later.
     *
     * @param checkBoxState
     * @param counter
     * @throws Exception
     */
    protected void checkThreeStatesComplex(boolean checkBox1State, boolean checkBox2State, int counter) throws Exception {
        checkTwoStatesComplex(checkBox1State, counter);
        checkCheckBox(CHECK_BOX_LISTENING_2_ID, checkBox2State);
        checkNonEquivCheckBoxState(CHECK_BOX_LISTENING_2_ID, !checkBox2State);
    }

    /**
     * Simple checking don't wait for some time to check that state could be
     * changed without command or on some other reason later.
     *
     * @param checkBoxState
     * @param counter
     * @throws Exception
     */
    protected void checkTwoStatesSimple(boolean checkBoxState, int counter) throws Exception {
        checkCheckBox(CHECK_BOX_LISTENING_1_ID, checkBoxState);
        checkListeningLabel(counter);
    }

    /**
     * Simple checking don't wait for some time to check, that state could be
     * changed without command or on some other reason later.
     *
     * @param checkBoxState
     * @param counter
     * @throws Exception
     */
    protected void checkThreeStatesSimple(boolean checkBox1State, boolean checkBox2State, int counter) throws Exception {
        checkTwoStatesSimple(checkBox1State, counter);
        checkCheckBox(CHECK_BOX_LISTENING_2_ID, checkBox2State);
    }

    private void checkNonEquivCheckBoxState(String checkBoxId, boolean expectedValue) throws Exception {
        try {
            parent.lookup(CheckBox.class, new ByID<CheckBox>(checkBoxId)).wrap().waitProperty(SELECTED_PROP_NAME, expectedValue ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        } catch (Exception ex) {
            return;
        }
        throw new Exception("Values are equivalent.");
    }

    private void checkListeningLabel(int waitedCount) {
        parent.lookup(Label.class, new ByID<Label>(MOUSE_EVENTS_LOGGER_LABEL_ID)).wrap().waitProperty(Wrap.TEXT_PROP_NAME, String.valueOf(waitedCount));
    }
    protected static int ITERATIONS = 3;
    protected static int SLEEP = 300;
}
