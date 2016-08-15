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

import client.test.Smoke;
import java.util.EnumSet;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TextAreaPropertiesTest extends TextControlCommonTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
        TextAreaPropertiesApp.main(null);
    }

    @Before
    public void setup() throws InterruptedException {
        setSize(200, 70);
    }

    @Smoke
    @Test(timeout = 300000)//RT-18852
    public void scrollLeftPropertyTest() throws InterruptedException, Throwable {
        Assert.assertEquals((new TextArea()).scrollLeftProperty().getValue(), 0, 0);
        commonScrollLeftTopTest(Orientation.HORIZONTAL, TextControlTestBase.Properties.scrollleft, "ContentScrolledLeft");
    }

    @Smoke
    @Test(timeout = 300000)//RT-18852
    public void scrollTopPropertyTest() throws InterruptedException, Throwable {
        Assert.assertEquals((new TextArea()).scrollTopProperty().getValue(), 0, 0);
        commonScrollLeftTopTest(Orientation.VERTICAL, TextControlTestBase.Properties.scrolltop, "ContentScrolledDown");
    }

    private void commonScrollLeftTopTest(Orientation orientation, TextControlTestBase.Properties property, String screenShotName) throws InterruptedException, Throwable {
        setSize(200, 200);
        addText();
        //There are both scrollBars must exist

        assertEquals(getScrollBarValue(findScrollBar(testedControl.as(Parent.class, Node.class), orientation, true)), 0, 0);

        double[] values = {10, 0, 50, 0, 100};

        setPropertyBySlider(SettingType.BIDIRECTIONAL, property, 10);
        double item = getScrollBarValue(findScrollBar(testedControl.as(Parent.class, Node.class), orientation, true)) / 10;

        for (SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            for (double value : values) {
                setPropertyBySlider(type, property, value);
                checkTextFieldValue(property, value);
                assertEquals(getScrollBarValue(findScrollBar(testedControl.as(Parent.class, Node.class), orientation, true)), value * item, 0.1 * item);
            }
        }

        checkScreenshot(screenShotName + "On" + values[values.length - 1] + "Pixels", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)//RT-18856
    public void prefColumnCountPropertyTest() throws InterruptedException {
        assertEquals((new TextArea()).prefColumnCountProperty().getValue(), 12, 0);
        prefColumnCountCommonTest();
    }

    @Smoke
    @Test(timeout = 300000)//RT-18856
    public void prefRowCountPropertyTest() throws InterruptedException {
        int[] values = {5, 1, 0, 7, 5};
        double currentHeightPerRow = (getHeightValue(testedControl) - 10) / 10; // +/-10 is according to the minimal width.

        for (SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            for (int value : values) {
                setPropertyBySlider(type, Properties.prefrowcount, value);//bind
                checkTextFieldValue(Properties.prefrowcount, value);//getter
                assertEquals(getHeightValue(testedControl), value * currentHeightPerRow + 10, 0.1 * currentHeightPerRow);//behavior
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void wrapTextPropertyTest() throws InterruptedException {
        setSize(70, 70);

        String text = "";
        for (int i = 0; i < 15; i++) {
            text += "text ";
        }

        setPropertyByTextField(SettingType.BIDIRECTIONAL, Properties.text, text);

        for (SettingType type : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            setPropertyByToggleClick(type, Properties.wraptext);//true//bind
            checkTextFieldText(Properties.wraptext, "true");//getter
            Assert.assertFalse(findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, false) == null);//behavior
            Assert.assertFalse(findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true) == null);//behavior

            setPropertyByToggleClick(type, Properties.wraptext);//false//bind
            checkTextFieldText(Properties.wraptext, "false");//getter
            Assert.assertFalse(findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true) == null);//behavior
            Assert.assertFalse(findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, false) == null);//behavior
        }
    }

    /**
     * Test checks, that if control is focused and text is selected, it is blue,
     * but when focused is lost, selected text becomes grey. Checking done with
     * screenshot.
     *
     * @throws Throwable
     */
    @Test(timeout = 300000)
    @Smoke
    public void focusedPropertyTest() throws Throwable {
        final String text = "some text";
        setPropertyByTextField(SettingType.BIDIRECTIONAL, Properties.text, text);

        checkTextFieldText(Properties.focused, "false");

        testedControl.mouse().click();
        testedControl.keyboard().pushKey(KeyboardButtons.A, CTRL_DOWN_MASK_OS);

        checkTextFieldText(Properties.focused, "true");

        checkScreenshot("TextArea-focused-[blue]selection", testedControl);

        testedControl.keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
        checkTextFieldText(Properties.focused, "false");

        checkScreenshot("TextArea-focused-[grey]selection", testedControl);

        throwScreenshotError();
    }

    @Override
    protected TextInputControl getNewControl() {
        return new TextArea();
    }
}
