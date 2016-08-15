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
package javafx.scene.control.test.textinput;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.util.EnumSet;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import static javafx.scene.control.test.textinput.TextControlTestBase.testedControl;
import static javafx.scene.control.test.textinput.TextFieldPropertiesApp.ON_ACTION_COUNTER;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.text.Font;
import org.jemmy.Point;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Text;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public abstract class TextControlCommonTests extends TextControlTestBase {

    @Before
    public void setUp() {
        initWrappers();
        scene.mouse().move(new Point(0, 0));
    }

    protected abstract TextInputControl getNewControl();

    @Test(timeout = 300000)
    @Smoke
    public void textPropertyTest() throws InterruptedException {
        assertEquals(getNewControl().textProperty().getValue(), "");
        String[] values = {"text1", "text2", "text3"};

        for (SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            for (String value : values) {

                setPropertyByTextField(type, Properties.text, value);//bind
                selectAll();
                checkSimpleListenerValue(Properties.selectedtext, value);//behavior
                checkTextFieldText(Properties.text, value);//getter
            }
        }
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void promptStringPropertyTest() throws Throwable {
        assertEquals(getNewControl().promptTextProperty().getValue(), "");

        setJemmyComparatorByDistance(0.012f);
        Control control = testedControl.getControl();

        for (SettingType st : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            setPropertyByTextField(st, Properties.text, "");//clear
            setPropertyByTextField(st, Properties.prompttext, st.name());//bind

            testedControl.mouse().click();
            checkScreenshot(getNewControl().getClass().getSimpleName() + "PromptTextTest-[focused]", testedControl);
            checkTextFieldText(Properties.prompttext, st.name());//getter

            if (control instanceof TextArea) {
                testedControl.keyboard().pushKey(KeyboardButtons.TAB, KeyboardModifiers.CTRL_DOWN_MASK);
            } else {
                testedControl.keyboard().pushKey(KeyboardButtons.TAB);
            }

            checkScreenshot(getNewControl().getClass().getSimpleName() + "PromptTextTest-[" + st.name() + ",unfocused]", testedControl);
            checkTextFieldText(Properties.prompttext, st.name());//getter

            //Type some text.
            testedControl.mouse().click();
            setPropertyByTextField(st, Properties.text, "Text");
            //Leave control. It will be unfocused.
            if (control instanceof TextArea) {
                testedControl.keyboard().pushKey(KeyboardButtons.TAB, KeyboardModifiers.CTRL_DOWN_MASK);
            } else {
                testedControl.keyboard().pushKey(KeyboardButtons.TAB);
            }
            //Check, that prompt didn't appear.
            checkScreenshot(getNewControl().getClass().getSimpleName() + "PromptTextTest-[with text, with prompt, unfocused]", testedControl);
        }

        throwScreenshotError();
    }

    protected void prefColumnCountCommonTest() throws InterruptedException {
        final int paddingWidth = 14;//7 pt from both sides of the text field
        final int initialColumnsCount = 12;
        double innerTextWidth = testedControl.getScreenBounds().getWidth() - paddingWidth;

        final double COL_WIDTH = innerTextWidth / initialColumnsCount;

        int[] values = {5, 1, 0, 10, 20, 5};
        for (SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            for (final int value : values) {
                setPropertyBySlider(type, Properties.prefcolumncount, value);//bind
                checkTextFieldValue(Properties.prefcolumncount, value);//getter
                new Waiter(new Timeout("", 2000)).ensureState(new State() {
                    public Object reached() {
                        //double totalColWidth = (0 == value) ? 7.0 : value * COL_WIDTH;//nonlinear behavior near zero
                        double totalColWidth = value * COL_WIDTH;
                        double innerTextWidth = testedControl.getScreenBounds().getWidth() - paddingWidth;
//                        System.out.println("innerTextWidth = " + innerTextWidth);
//                        System.out.println("totalColWidth = " + totalColWidth);
//                        System.out.println("Math.abs(innerTextWidth - totalColWidth) " + Math.abs(innerTextWidth - totalColWidth));
//                        System.out.println("0.1 * COL_WIDTH = " + 0.1 * COL_WIDTH);
//                        System.out.println("(Math.abs(innerTextWidth - totalColWidth) < 0.1 * COL_WIDTH) = " + (Math.abs(innerTextWidth - totalColWidth) < 0.1 * COL_WIDTH));
                        return (Math.abs(innerTextWidth - totalColWidth) < 0.1 * COL_WIDTH) ? Boolean.TRUE : null;
                    }

                    @Override public String toString() { return "[Text field has expected size]"; }
                });
            }
        }
    }

    public void onActionPropertyCommonTest() {
        addListener();
        addText();
        for (int i = 0; i < 10; i++) {
            checkCounterValue(ON_ACTION_COUNTER, i);
            testedControl.keyboard().pushKey(KeyboardButtons.ENTER);
            checkCounterValue(ON_ACTION_COUNTER, i + 1);
        }
    }

    @Test(timeout = 300000)
    @Smoke
    public void editablePropertyTest() {
        final String text = "some text";
        for (SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            setPropertyByTextField(SettingType.BIDIRECTIONAL, Properties.text, text);
            setPropertyByToggleClick(type, Properties.editable);//true//bind
            selectAll();
            testedControl.keyboard().pushKey(KeyboardButtons.DELETE);
            selectAll();
            checkSimpleListenerValue(Properties.selectedtext, "");//behavior
            checkTextFieldText(Properties.text, "");
            checkTextFieldText(Properties.editable, "true");//getter

            setPropertyByTextField(SettingType.BIDIRECTIONAL, Properties.text, text);
            setPropertyByToggleClick(type, Properties.editable);//false//bind
            selectAll();
            testedControl.keyboard().pushKey(KeyboardButtons.DELETE);
            selectAll();
            checkSimpleListenerValue(Properties.selectedtext, text);//behavior
            checkTextFieldText(Properties.text, text);
            checkTextFieldText(Properties.editable, "false");//getter
        }
    }

    @Test(timeout = 300000)
    @Smoke
    /**
     * Checks font property : all bindings, different values. Checks font of
     * text node inside.
     */
    public void fontPropertyTest() {
        removeStylesheet();
        assertEquals(getNewControl().fontProperty().getValue(), Font.getDefault());
        setPropertyByTextField(SettingType.SETTER, Properties.text, "Text");

        for (SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            selectObjectFromChoiceBox(type, Properties.font, new Font(20));
            assertEquals(getActualFontSize(), 20.0, 1.0);
            checkFontSize(Properties.font, 20.0);

            testedControl.as(Text.class).clear();
            testedControl.as(Text.class).type("Text");

            assertEquals(getActualFontSize(), 20.0, 1.0);
            checkFontSize(Properties.font, 20.0);

            selectObjectFromChoiceBox(type, Properties.font, new Font(10));
            assertEquals(getActualFontSize(), 10.0, 1.0);
            checkFontSize(Properties.font, 10.0);

            testedControl.as(Text.class).clear();

            selectObjectFromChoiceBox(type, Properties.font, new Font(15));
            assertEquals(getActualFontSize(), 15.0, 1.0);
            checkFontSize(Properties.font, 15.0);

            testedControl.as(Text.class).type("Text");
            assertEquals(getActualFontSize(), 15.0, 1.0);
            checkFontSize(Properties.font, 15.0);
        }
        restoreStylesheet();
    }
}