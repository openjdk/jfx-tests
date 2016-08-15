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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.test.textinput.TextAreaApp.Pages;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.interfaces.Keyboard;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class TextAreaTest extends TextInputBase {

    private Wrap<? extends Button> bEmpty = null;
    private Wrap<? extends Button> bClear = null;
    private Wrap<? extends Button> bResize = null;
    private Wrap<? extends Button> bUpdatePrompt = null;
    private Wrap<? extends TextArea> taTesting = null;
    private Wrap<? extends TextArea> taPrompt = null;
    private static String longString = null;
    //@RunUI

    @BeforeClass
    public static void runUI() {
        TextAreaApp.main(null);
    }
//    /**
//     * Test for TextField setPrefColumnCount API
//     */
//    @ScreenshotCheck
//    @Test(timeout=300000)
//    public void prefColumnCountTest() throws InterruptedException {
//        testCommon(Pages.PrefColumnCount.name());
//    }
//

    /**
     * Testing drawing prompt text.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Smoke
    @Test
    public void simplePromptTest() throws Throwable {
        testCommon(Pages.SimplePromptTest.name());
    }

    /**
     * Testing drawing prompt text after get focus by click.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Test
    public void simpleClickPromptTest() throws Throwable {
        openPage(Pages.SimpleClickPromptTest.name());
        initPomptPageControls(Pages.SimpleClickPromptTest.name());
        click(taTesting);
        click(bClear);
        testCommon(Pages.SimpleClickPromptTest.name());
    }

    /**
     * Testing prompt text getting focus by tab.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Test
    public void keyboard1PromptTest() throws Throwable {
        openPage(Pages.Keyboard1PromptTest.name());
        initPomptPageControls(Pages.Keyboard1PromptTest.name());
        click(bClear);
        tab(1);
        testCommon(Pages.Keyboard1PromptTest.name());
    }

    /**
     * Testing prompt text getting focus by shift+tab.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Test
    public void keyboard2PromptTest() throws Throwable {
        openPage(Pages.Keyboard2PromptTest.name());
        initPomptPageControls(Pages.Keyboard2PromptTest.name());
        click(bEmpty);
        shiftTab(2);
        testCommon(Pages.Keyboard2PromptTest.name());
    }

    /**
     * Testing prompt text width simple text in text area.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Test
    public void keyboard3PromptTest() throws Throwable {
        openPage(Pages.Keyboard3PromptTest.name());
        initPomptPageControls(Pages.Keyboard3PromptTest.name());
        click(bClear);
        tab(1);
        setText("SomeText");
        shiftTab(1);
        testCommon(Pages.Keyboard3PromptTest.name());
    }

    /**
     * Testing drawing long string in prompt text.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Test
    public void longStringPromptTest() throws Throwable {
        openPage(Pages.LongStringPromptTest.name());
        initPomptPageControls(Pages.LongStringPromptTest.name());
        setPromptText(getLongString());
        click(bUpdatePrompt);
        testCommon(Pages.LongStringPromptTest.name());
    }

    /**
     * Testing drawing simple text width prompt text.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Test
    public void textInsidePromptTest() throws Throwable {
        openPage(Pages.TextInsidePromptTest.name());
        initPomptPageControls(Pages.TextInsidePromptTest.name());
        String innerText = "this text should be in Text Area";
        String pText = "this text is prompt text";
        setPromptText(pText);
        click(bUpdatePrompt);
        setText(innerText);
        testCommon(Pages.TextInsidePromptTest.name());
    }

    /**
     * Testing drawing long string in prompt text after resizing area.
     *
     * @throws Throwable
     */
    @ScreenshotCheck
    @Test
    public void resizePromptTest() throws Throwable {
        openPage(Pages.resizePromptTest.name());
        initPomptPageControls(Pages.resizePromptTest.name());
        setPromptText(getLongString());
        click(bUpdatePrompt);
        click(bResize);
        testCommon(Pages.resizePromptTest.name());
        click(bResize);
    }

    private void initPomptPageControls(String name) throws Exception {
        org.jemmy.interfaces.Parent p = getScene().as(org.jemmy.interfaces.Parent.class, Node.class);
        bUpdatePrompt = p.lookup(new ByID<Button>(TextAreaApp.PROMPT_BUTTON_ID + name)).wrap();
        bClear = p.lookup(new ByID<Button>(TextAreaApp.CLEAR_BUTTON_ID + name)).wrap();
        taTesting = p.lookup(new ByID<TextArea>(TextAreaApp.AREA_ID + name)).wrap();
        bEmpty = p.lookup(new ByID<Button>(TextAreaApp.EMPTY_BUTTON_ID + name)).wrap();
        bResize = p.lookup(new ByID<Button>(TextAreaApp.RESIZE_BUTTON_ID + name)).wrap();
        taPrompt = p.lookup(new ByID<TextArea>(TextAreaApp.PROMPT_AREA_ID + name)).wrap();
    }

    private void tab(int n) {
        for (int i = 0; i < n; i++) {
            getScene().keyboard().pushKey(Keyboard.KeyboardButtons.TAB);
        }
    }

    private void shiftTab() {
        shiftTab(1);
    }

    private void shiftTab(int i) {
        for (int j = 0; j < i; j++) {
            getScene().keyboard().pushKey(Keyboard.KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        }
    }

    private void setPromptText(String string) {
        click(taPrompt);
        new GetAction<Void>() {
            @Override
            public void run(Object... os) throws Exception {
                taPrompt.getControl().setText(os[0].toString());
            }
        }.dispatch(taPrompt.getEnvironment(), string);
    }

    private void setText(String innerText) {
        click(taTesting);
        for (char c : innerText.toCharArray()) {
            taTesting.keyboard().typeChar(c);
        }
    }

    private String getLongString() {
        if (longString == null) {
            StringBuilder sb = new StringBuilder("This is very long string,");
            for (int i = 0; i < 30; i++) {
                sb.append(" very-very long string,");
            }
            sb.append("end of string");
            longString = sb.toString();
        }
        return longString;
    }
}
