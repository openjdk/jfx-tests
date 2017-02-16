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
import javafx.scene.control.skin.TextInputControlSkin;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.test.Change;
import javafx.scene.control.test.mix.PopupMenuTest;
import javafx.scene.control.test.textinput.TextInputBaseApp.Pages;
import javafx.scene.control.test.textinput.TextInputChanger.TextInputControlWrapInterface;
import javafx.scene.control.test.textinput.TextInputChanger.TextInputPages;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.Pane;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestBase;
import test.javaclient.shared.Utils;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 * @author Oleg Barbashov
 */
@RunWith(FilteredTestRunner.class)
public class TextInputBase extends TestBase {

    {
        ScreenshotUtils.setComparatorDistance(0.003f);
    }
    private Wrap<? extends TextInputControl> taTesting = null;

    static final KeyboardModifiers CTRL = Utils.isMacOS() ? KeyboardModifiers.ALT_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK;
//    /**
//     * Test for TextInput setMaximumLength API
//     */
//    @ScreenshotCheck
//    @Test(timeout=300000)
//    public void maximumLengthTest() throws InterruptedException, Throwable {
//        openPage(Pages.MaximumLength.name());
//
//        verifyFailures();
//
//        Parent<Node> parent = scene.as(Parent.class, Node.class);
//        Lookup lookup = parent.lookup(TextInput.class);
//        for (int i = 0; i < lookup.size(); i++) {
//            final Wrap<? extends TextInput> wrap = lookup.wrap(i);
//            final Integer maximumLength = new GetAction<Integer>() {
//                @Override
//                public void run(Object... parameters) {
//                    setResult(wrap.getControl().getMaximumLength());
//                }
//            }.dispatch(Root.ROOT.getEnvironment());
//            Text text = wrap.as(Text.class);
//            long timeout = Wrap.WAIT_STATE_TIMEOUT.getValue();
//            String str = createLongString(maximumLength + 2);
//            try {
//                text.type(str);
//            } catch (Exception ex) {
//                // expected as text length is limited but Jemmy does not expect limitation and wait for all sequence
//            }
//            Wrap.WAIT_STATE_TIMEOUT.setValue(timeout);
//            wrap.waitProperty(Wrap.TEXT_PROP_NAME, str.subSequence(0, maximumLength));
//        }
//        checkScreenshot(getClass().getSimpleName() + "-" + Pages.MaximumLength.name());
//        throwScreenshotError();
//    }
    /**
     * Test for backward API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void backwardInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.backward.name(), true);
    }

    /**
     * Test for copy API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void copyInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.copy.name(), true);
    }

    /**
     * Test for cut API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cutInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.cut.name(), true);
    }

    /**
     * Test for deleteNextChar API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void deleteNextCharInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.deleteNextChar.name(), true);
    }

    /**
     * Test for deletePreviousChar API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void deletePreviousCharInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.deletePreviousChar.name(), true);
    }

    /**
     * Test for end API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void endInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.end.name(), true);
    }

    /**
     * Test for forward API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void forwardInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.forward.name(), true);
    }

    /**
     * Test for home API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void homeInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.home.name(), true);
    }

    /**
     * Test for isEditableTextBoxWrap API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void isEditableTextBoxWrapInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.isEditable.name(), true);
    }

    /**
     * Test for nextWord API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void nextWordInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.nextWord.name(), true);
    }

    /**
     * Test for paste API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void pasteInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.paste.name(), true);
    }

    /**
     * Test for positionCaret API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void positionCaretInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.positionCaret.name(), true);
    }

    /**
     * Test for previousWord API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void previousWordInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.previousWord.name(), true);
    }

    /**
     * Test for selectAll API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectAllInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectAll.name(), true);
    }

    /**
     * Test for selectBackward API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectBackwardInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectBackward.name(), true);
    }

    /**
     * Test for selectEnd API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectEndInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectEnd.name(), true);
    }

    /**
     * Test for selectForward API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectForwardInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectForward.name(), true);
    }

    /**
     * Test for selectHome API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectHomeInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectHome.name(), true);
    }

    /**
     * Test for selectNext API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectNextWordInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectNextWord.name(), true);
    }

    /**
     * Test for selectPreviousWord API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectPreviousWordInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectPreviousWord.name(), true);
    }

    /**
     * Test for setText API
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setTextInternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.setText.name(), true);
    }

    /**
     * Test for backward API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void backwardExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.backward.name(), false);
    }

    /**
     * Test for copy API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void copyExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.copy.name(), false);
    }

    /**
     * Test for cut API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void cutExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.cut.name(), false);
    }

    /**
     * Test for deleteNextChar API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void deleteNextCharExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.deleteNextChar.name(), false);
    }

    /**
     * Test for deletePreviousChar API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void deletePreviousCharExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.deletePreviousChar.name(), false);
    }

    /**
     * Test for end API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void endExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.end.name(), false);
    }

    /**
     * Test for forward API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void forwardExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.forward.name(), false);
    }

    /**
     * Test for home API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void homeExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.home.name(), false);
    }

    /**
     * Test for isEditableTextBoxWrap API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void isEditableTextBoxWrapExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.isEditable.name(), false);
    }

    /**
     * Test for nextWord API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void nextWordExternalTest() throws InterruptedException {
        if (Utils.isMacOS()) {
            textInputControlInput(TextInputPages.macNextWordExternal.name(), false);
        } else {
            textInputControlInput(TextInputPages.nextWord.name(), false);
        }
    }

    /**
     * Test for paste API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void pasteExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.paste.name(), false);
    }

    /**
     * Test for positionCaret API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void positionCaretExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.positionCaret.name(), false);
    }

    /**
     * Test for previousWord API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void previousWordExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.previousWord.name(), false);
    }

    /**
     * Test for selectAll API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectAllExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectAll.name(), false);
    }

    /**
     * Test for selectBackward API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectBackwardExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectBackward.name(), false);
    }

    /**
     * Test for selectEnd API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectEndExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectEnd.name(), false);
    }

    /**
     * Test for selectForward API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectForwardExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectForward.name(), false);
    }

    /**
     * Test for selectHome API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectHomeExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectHome.name(), false);
    }

    /**
     * Test for selectNext API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectNextWordExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectNextWord.name(), false);
    }

    /**
     * Test for selectPreviousWord API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectPreviousWordExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.selectPreviousWord.name(), false);
    }

    /**
     * Test for setText API by simulating user input
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void setTextExternalTest() throws InterruptedException {
        textInputControlInput(TextInputPages.setText.name(), false);
    }

    /**
     * Test for Context Menu
     */
    @ScreenshotCheck
    @Test(timeout = 300000)
    public void contextMenuTest() throws InterruptedException {
        openPage(Pages.InputTest.name());

        final Wrap<? extends TextInputControl> wrap = getScene().as(Parent.class, Node.class).lookup(TextInputControl.class).wrap();
        final Text text = wrap.as(Text.class);

        initContext();
        boolean password = PasswordField.class.isAssignableFrom(wrap.getControl().getClass());
        if (!password) {
            getMenuItem("Select All").mouse().move();
            getMenuItem("Copy").mouse().click();
            checkClipboard(SAMPLE_STRING);

            getMenuItem("Cut").mouse().click();
            checkClipboard(SAMPLE_STRING);
            checkText(text, "");
        } else {
            checkState("Copy", true);
            checkState("Cut", true);
            getScene().keyboard().pushKey(KeyboardButtons.ESCAPE);
        }

        initContext();
        getMenuItem("Delete").mouse().click();
        checkText(text, "");

        initContext();
        getMenuItem("Select All").mouse().click();
        getScene().waitState(new State() {
            public Object reached() {
                return wrap.getControl().getSelection().getLength() == SAMPLE_STRING.length() ? true : null;
            }
        });

        initContext();
        final Map<DataFormat, Object> data_map = new HashMap<DataFormat, Object>();
        data_map.put(DataFormat.PLAIN_TEXT, SAMPLE_STRING);

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                Clipboard.getSystemClipboard().setContent(data_map);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        getMenuItem("Paste").mouse().click();
        checkText(text, SAMPLE_STRING);

        wrap.keyboard().pushKey(KeyboardButtons.HOME);
        checkState("Copy", true);
        checkState("Cut", true);
        checkState("Paste", false);

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                Clipboard.getSystemClipboard().clear();
            }
        }.dispatch(Root.ROOT.getEnvironment());

        checkState("Paste", true);
        checkState("Delete", true);
        checkState("Select All", false);
    }

    @ScreenshotCheck
    @Test
    public void ctrlBackspaceMultilineTest() {
        openPage(Pages.CtrlBackspaceTest.name());
        Wrap<? extends Button> btn = getScene().as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByID<Button>(TextAreaApp.MULTILINE_BTN_ID + Pages.CtrlBackspaceTest.name())).wrap();
        click(btn);
        ctrlBackspaceTest();
    }

    @ScreenshotCheck
    @Test
    public void ctrlBackspaceRandomSymbolsTest() {
        openPage(Pages.CtrlBackspaceTest.name());
        Wrap<? extends Button> btn = getScene().as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByID<Button>(TextAreaApp.RANDOM_TEXT_BTN_ID + Pages.CtrlBackspaceTest.name())).wrap();
        click(btn);
        ctrlBackspaceTest();
    }

    @ScreenshotCheck
    @Test
    public void ctrlDeleteMultilineTest() {
        openPage(Pages.CtrlDeleteTest.name());
        Wrap<? extends Button> btn = getScene().as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByID<Button>(TextAreaApp.MULTILINE_BTN_ID + Pages.CtrlDeleteTest.name())).wrap();
        click(btn);
        ctrlDeleteTest();
    }

    @ScreenshotCheck
    @Test
    public void ctrlDeleteRandomSymbolsTest() {
        openPage(Pages.CtrlDeleteTest.name());
        Wrap<? extends Button> btn = getScene().as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByID<Button>(TextAreaApp.RANDOM_TEXT_BTN_ID + Pages.CtrlDeleteTest.name())).wrap();
        click(btn);
        ctrlDeleteTest();
    }

    private void ctrlBackspaceTest() {
        taTesting = getScene().as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByID<TextInputControl>(TextAreaApp.INPUT_AREA_ID + Pages.CtrlBackspaceTest.name())).wrap();
        click(taTesting);
        end();

        String text = getTextFromControl();
        final String initialText = text;
        if (isPasswordField()) {
            taTesting.keyboard().pushKey(Keyboard.KeyboardButtons.BACK_SPACE, CTRL);
            if (Utils.isMacOS()) {
                Assert.assertEquals("", getTextFromControl());
            } else {
                Assert.assertEquals(initialText, getTextFromControl());
            }
        } else {
            while (!text.isEmpty()) {
            text = deleteLastWord(text);
            taTesting.keyboard().pushKey(Keyboard.KeyboardButtons.BACK_SPACE, CTRL);
                if (!text.equals(getTextFromControl())) {
                    out(initialText, text);
                }
                Assert.assertEquals(text, getTextFromControl());
            }
        }
    }

    private void ctrlDeleteTest() {
        taTesting = getScene().as(org.jemmy.interfaces.Parent.class, Node.class).lookup(new ByID<TextInputControl>(TextAreaApp.INPUT_AREA_ID + Pages.CtrlDeleteTest.name())).wrap();

        click(taTesting);
        home();
        String text = getTextFromControl();
        final String initialText = text;
        while (!"".equals(text)) {
            text = deleteFirstWord(text, Utils.isMacOS() ? false : true);
            taTesting.keyboard().pushKey(Keyboard.KeyboardButtons.DELETE, CTRL);
            if (isPasswordField()) {
                Assert.assertEquals(initialText, getTextFromControl());
            } else {
                if (!text.equals(getTextFromControl())) {
                    out(initialText, text);
                }
                Assert.assertEquals(text, getTextFromControl());
            }
        }
    }

    private void out(String initial, String text) {
        System.out.println("Initial text : ");
        System.out.println(initial);
        System.out.println("Control's text : ");
        System.out.println(getTextFromControl());
        System.out.println("Text : ");
        System.out.println(text);
        System.out.println("END");
    }

    private String deleteLastWord(String text) {
        String reverce = new StringBuffer(text).reverse().toString();
        reverce = deleteFirstWord(reverce, false);
        return new StringBuffer(reverce).reverse().toString();
    }

    /*
     * Deletes first word according to specific rules.
     * Behavior depend on whether deleting is made
     * using Ctrl + Delete in the beginning of the string or
     * using Ctrl + Backspace in the end of the string.
     * <code>deleteFromBeginning</code> flag separates these cases.
     *
     * @param text initial string
     * @param deleteFromBeginning flag which shows how deletion is performed.
     * @return string without first word.
     */
    private String deleteFirstWord(String text, boolean deleteFromBeginning) {
        final String[] punctuation = new String[]{" ", "\n", "\t", "/", ",", ";", "!", "@", "#", "$", "%", "^", "*", "(", ")", "&", "."};

        if (!deleteFromBeginning) {
            while (startsWithAnyOf(text, punctuation)) {
                text = text.substring(1);
            }
            text = removeLeadingChars(text, punctuation);
        } else {
            if (Utils.isLinux()) {
                if (' ' == text.charAt(0)) {
                    int pos = 0;
                    while(' ' == text.charAt(pos)) ++pos;
                    text = text.substring(pos);
                } else if (startsWithAnyOf(text, punctuation)) {
                    text = text.substring(1);
                }
            }

            if ('\n' == text.charAt(0)) {
                text = text.substring(1);
                return text;
            }

            text = removeLeadingChars(text, punctuation);

            if ('\n' == text.charAt(0)) {
                return text;
            }
            if (Utils.isLinux()) {
                return text;
            }
            while (startsWithAnyOf(text, punctuation)) {
                text = text.substring(1);
            }
        }
        return text;
    }

    /*
    For each charachter from <code>toRemove</code> finds which occurs first and deletes it.
    If none is found then returns empty string.
    */
    String removeLeadingChars(String str, String[] toRemove) {
        int idx = Integer.MAX_VALUE;
        for (String ch : toRemove) {
            if (str.indexOf(ch) >= 0) {
                idx = Math.min(str.indexOf(ch), idx);
            }
        }
        idx = idx == Integer.MAX_VALUE ? -1 : idx;
        return (idx < 0) ? "" : str.substring(idx);
    }

    protected boolean startsWithAnyOf(String str, String[] symbols) {
        for (String sym : symbols) {
            if (str.startsWith(sym)) {
                return true;
            }
        }
        return false;
    }

    protected void click(Wrap<? extends Control> control) {
        Point p = control.getClickPoint();
        control.mouse().move(p);
        control.mouse().click();
    }

    protected void initContext() {
        final Wrap<? extends TextInputControl> wrap = getScene().as(Parent.class, Node.class).lookup(TextInputControl.class).wrap();
        wrap.as(Text.class).clear();
        wrap.as(Text.class).type(SAMPLE_STRING);
        wrap.keyboard().pushKey(KeyboardButtons.A, Utils.isMacOS() ? KeyboardModifiers.META_DOWN_MASK : KeyboardModifiers.CTRL_DOWN_MASK);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                Clipboard.getSystemClipboard().clear();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void checkState(final String menu, boolean disabled) {
        Wrap<? extends Node> wrap = getMenuItem(menu);
        MenuItem menu_item = ((MenuItem) wrap.getControl().getProperties().get(MenuItem.class));
        assertEquals(menu_item.isDisable(), disabled);
    }

    protected void checkText(final Text text, final String str) {
        getScene().waitState(new State() {
            public Object reached() {
                return text.text().contentEquals(str) ? true : null;
            }
        });
    }

    protected void checkClipboard(final String str) {
        getScene().waitState(new State() {
            public Object reached() {
                return new GetAction<String>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(Clipboard.getSystemClipboard().getString());
                    }
                }.dispatch(Root.ROOT.getEnvironment()).contentEquals(str) ? true : null;
            }
        });
    }

    protected Wrap<? extends Node> getMenuItem(final String menu) {
        getScene().mouse().click();
        Parent<Node> parent = getScene().as(Parent.class, Node.class);
        final Wrap<? extends TextInputControl> wrap = parent.lookup(TextInputControl.class).wrap();
        wrap.mouse().click(1, wrap.getClickPoint(), MouseButtons.BUTTON3);
        Wrap<? extends Scene> scene_wrap = PopupMenuTest.getPopupSceneWrap();
        Wrap<? extends Node> menu_item = scene_wrap.as(Parent.class, Node.class).lookup(Node.class, new LookupCriteria<Node>() {
            public boolean check(Node node) {
                if (node.getProperties().containsKey(MenuItem.class)) {
                    String text = ((MenuItem) node.getProperties().get(MenuItem.class)).getText();
                    if (text != null && text.contentEquals(menu)) {
                        return true;
                    }
                }
                return false;
            }
        }).wrap();
        return menu_item;
    }
    final protected String SAMPLE_STRING = "Sample string";
    protected Boolean focused;

    /**
     * Test for textInputControl API by simulating user input
     */
    public void textInputControlInput(final String changeName, boolean internal) throws InterruptedException {
        openPage(Pages.InputTest.name());
        Thread.sleep(500);
        Parent<Node> parent = getScene().as(Parent.class, Node.class);

        //Take focus on scene.
        getScene().mouse().click(1, new Point(1, 1));

        final Wrap<? extends TextInputControl> wrap = parent.lookup(TextInputControl.class).wrap();

        //Request focus on control.
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                wrap.getControl().requestFocus();
            }
        }.dispatch(Root.ROOT.getEnvironment());


        final ReporterImpl reporter = new ReporterImpl();
        List<Change<TextInputControlWrapInterface>> text_wrap_input_changers = TextInputChanger.getTextInputWrapChangers(reporter);

        for (final Change<TextInputControlWrapInterface> change : text_wrap_input_changers) {
            if (change.getMarker().startsWith(changeName)) {
                if (internal) {
                    applyChange(new TextInputInternalWrap(wrap.getControl()), change, reporter, wrap, true);
                } else {
                    applyChange(new TextInputExternalWrap(wrap), change, reporter, wrap, false);
                }
            }
        }

        if (reporter.isFailed()) {
            Assert.fail(reporter.getReason());
        }
    }

    void applyChange(final TextInputControlWrapInterface control, final Change<TextInputControlWrapInterface> change,
            ReporterImpl reporter, final Wrap<? extends TextInputControl> wrap, final boolean internal) {
        final String text = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(control.getControl().getText());
            }
        }.dispatch(Root.ROOT.getEnvironment());
        control.getControl().setEditable(true);

        final TextInputControl controlInstance = (TextInputControl) wrap.getControl();
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                ((TextInputControlSkin) controlInstance.getSkin()).setCaretAnimating(false);
                try {
                    Field blink_field;
                    blink_field = TextInputControlSkin.class.getDeclaredField("blink");
                    blink_field.setAccessible(true);
                    BooleanProperty blink = (BooleanProperty) blink_field.get((TextInputControlSkin) controlInstance.getSkin());
                    blink.set(false);
                } catch (Exception ex) {
                    Logger.getLogger(TextInputBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());

        if (internal) {
            new GetAction() {
                @Override
                public void run(Object... parameters) {
                    change.apply(control, internal);
                }
            }.dispatch(Root.ROOT.getEnvironment());
        } else {
            change.apply(control, internal);
        }

        try {
            Wrap<? extends Pane> paneWrap = (Wrap<? extends Pane>) ScreenshotUtils.getPageContent();
            ScreenshotUtils.checkScreenshot(new StringBuilder(getName()).append("-").append(change.getMarker().replace(' ', '_')).append(internal ? "-internal" : "-external").toString(), paneWrap);
        } catch (Throwable e) {
            reporter.report("For change <" + change.getMarker() + "> message : " + e.getMessage());
        } finally {
            new GetAction() {
                @Override
                public void run(Object... parameters) {
                    ((TextInputControlSkin) controlInstance.getSkin()).setCaretAnimating(true);
                }
            }.dispatch(Root.ROOT.getEnvironment());
        }
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                control.setText(text);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        System.err.println(">>got text: " + getTextFromControl(wrap.getControl()));
        wrap.waitState(new State() {
            public Object reached() {
                String currentText = getTextFromControl(wrap.getControl());
                return text.equalsIgnoreCase(currentText) ? 42 : null;
            }
        });

        wrap.waitState(new State() {
            String s1;
            String s2;

            public Object reached() {
                s1 = text.toUpperCase();
                s2 = getTextFromControl(wrap.getControl()).toUpperCase();
                return s1.equals(s2) ? true : null;
            }

            public String toString() {
                return "s1 = <" + s1 + ">, s2 = <" + s2 + ">.";
            }
        });
    }

    String getTextFromControl(final TextInputControl cntrl) {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(cntrl.getText());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    String getTextFromControl() {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(taTesting.getControl().getText());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    void forward() {
        new GetAction<Void>() {
            @Override
            public void run(Object... os) throws Exception {
                taTesting.getControl().forward();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    void home() {
        new GetAction<Void>() {
            @Override
            public void run(Object... os) throws Exception {
                taTesting.getControl().home();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean isPasswordField() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(PasswordField.class.isAssignableFrom(taTesting.getControl().getClass()));
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    void end() {
        new GetAction<Void>() {
            @Override
            public void run(Object... os) throws Exception {
                taTesting.getControl().end();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public class ReporterImpl extends Reporter {

        protected boolean failed = false;
        protected HashSet<String> reasons = new HashSet<String>();

        public boolean isFailed() {
            return failed;
        }

        public String getReason() {
            String reason = "Fault reasons:\n";
            for (String str : reasons) {
                reason += "   <" + str + ">\n";
            }
            return reason;
        }

        @Override
        public void report(String string) {
            failed = true;
            reasons.add(string + "\n" + getStackTrace());
        }

        protected String getStackTrace() {
            String trace = "Stack trace:\n";
            for (StackTraceElement ste : new Throwable().getStackTrace()) {
                trace += "\t" + ste + "\n";
            }
            return trace;
        }
    }

    protected static String createLongString(int length) {
        StringBuilder builder = new StringBuilder("L");
        for (int j = 0; j < length - 3; j++) {
            builder.append("o");
        }
        builder.append("ng");
        return builder.substring(0, length).toString();
    }
}
