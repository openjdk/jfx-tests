/*
 * Copyright (c) 2014, 2016 Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test.dialog;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Robot;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.dialog.DialogApp;
import static javafx.scene.control.test.dialog.DialogApp.DialogScene.*;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.image.Image;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.resources.StringComparePolicy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.screenshots.GoldenImageManager;
/**
 *
 * @author Alexander Vorobyev
 */
@RunWith(FilteredTestRunner.class)
public class DialogTest extends ControlsTestBase {

    private static final int DEFAULT_DELAY = 1000;

    public DialogTest() {
    }

    public static ButtonType[] types = {ButtonType.APPLY, ButtonType.CANCEL, ButtonType.CLOSE, ButtonType.FINISH, ButtonType.NEXT, ButtonType.NO, ButtonType.OK, ButtonType.PREVIOUS, ButtonType.YES};
    public static String[] content = {TOGGLE_BUTTON_TEXT, TOGGLE_LABEL_TEXT, TOGGLE_INPUT_TEXT};

    public static enum InputType {

        MOUSE, SPACE, ENTER
    }
    Wrap<? extends Scene> scene = null;
    Parent<Node> parent = null;
    Wrap clear = null;
    static boolean isRemote;

    @BeforeClass
    public static void setUpClass() throws Exception {
        DialogApp.main(null);
        isRemote = (test.javaclient.shared.AppLauncher.getInstance().getMode() == test.javaclient.shared.AppLauncher.Mode.REMOTE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        scene = TestUtil.getScene();
        parent = scene.as(Parent.class, Node.class);
        scene.mouse().click();
    }

    @After
    public void tearDown() {
    }

    private void deselectButtons() {
        for (ButtonType b : types) {
            Wrap<? extends Node> addButton = parent.lookup(ToggleButton.class,
                    new ByText<ToggleButton>(b.getText(), StringComparePolicy.EXACT)).wrap();
            click(addButton, InputType.MOUSE);
        }
    }

    private void addButton(String button) {
        Wrap<? extends Node> addButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(button, StringComparePolicy.EXACT)).wrap();
        click(addButton, InputType.MOUSE);
    }

    @Test(timeout = 300000)
    public void informationAlertBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_INFO_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("informationAlertBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void informationAlertWithMastheadTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_INFO_TEXT)).wrap();
        checkShowMastheadBox();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("informationAlertWithMastheadTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void confirmationAlertBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CONFIRM_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("confirmationAlertBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void confirmationAlertWithMastheadTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CONFIRM_TEXT)).wrap();
        checkShowMastheadBox();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("confirmationAlertWithMastheadTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void warningAlertBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_WARNING_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("warningAlertBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void warningAlertWithMastheadTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_WARNING_TEXT)).wrap();
        checkShowMastheadBox();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("warningAlertWithMastheadTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void errorAlertBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_ERROR_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("errorAlertBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void errorAlertWithMastheadTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_ERROR_TEXT)).wrap();
        checkShowMastheadBox();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("errorAlertWithMastheadTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void informationAlertBasicWithButtonsTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_INFO_TEXT)).wrap();
                click(button, InputType.MOUSE);
                Thread.sleep(DEFAULT_DELAY);
                checkScreenshot("informationAlertBasicWithButtonsTest" + s + "_" + b + "Test", scene);
                closeDialogWindowByClickingButton();
                Thread.sleep(DEFAULT_DELAY);
                throwScreenshotError();
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void confirmationAlertBasicWithButtonsTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CONFIRM_TEXT)).wrap();
                click(button, InputType.MOUSE);
                Thread.sleep(DEFAULT_DELAY);
                checkScreenshot("confirmationAlertBasicWithButtonsTest" + s + "_" + b + "Test", scene);
                closeDialogWindowByClickingButton();
                Thread.sleep(DEFAULT_DELAY);
                throwScreenshotError();
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void warningAlertBasicWithButtonsTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_WARNING_TEXT)).wrap();
                click(button, InputType.MOUSE);
                Thread.sleep(DEFAULT_DELAY);
                checkScreenshot("warningAlertBasicWithButtonsTest" + s + "_" + b + "Test", scene);
                closeDialogWindowByClickingButton();
                Thread.sleep(DEFAULT_DELAY);
                throwScreenshotError();
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void errorAlertBasicWithButtonsTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_ERROR_TEXT, StringComparePolicy.EXACT)).wrap();
                click(button, InputType.MOUSE);
                Thread.sleep(DEFAULT_DELAY);
                checkScreenshot("errorAlertBasicWithButtonsTest" + s + "_" + b + "Test", scene);
                closeDialogWindowByClickingButton();
                Thread.sleep(DEFAULT_DELAY);
                throwScreenshotError();
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void textInputDialogBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_TEXT_FIELD_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("textInputDialogBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void textInputWithInitialValueDialogBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_INITIAL_VALUE_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("textInputWithInitialValueDialogBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void setChoiceDialogLtTenBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CHOICE_LT_TEN_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("setChoiceDialogLtTenBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void setChoiceDialogGtTenBasicTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CHOICE_GT_TEN_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            checkScreenshot("setChoiceDialogGtTenBasicTest" + s.toString() + "Test", scene);
            closeDialogWindowByClickingButton();
            throwScreenshotError();
        }
    }

    @Test(timeout = 300000)
    public void informationAlertResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_INFO_TEXT)).wrap();
                click(button, InputType.MOUSE);
                closeDialogWindowByClickingButton(b.getText());
                Wrap<? extends Node> resultField = parent.lookup(TextField.class, new ByID<TextField>(RESULT_FIELD_ID)).wrap();
                resultField.waitProperty(Wrap.TEXT_PROP_NAME, b.getText());
                Thread.sleep(DEFAULT_DELAY);
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void confirmationAlertResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CONFIRM_TEXT)).wrap();
                click(button, InputType.MOUSE);
                closeDialogWindowByClickingButton(b.getText());
                Thread.sleep(DEFAULT_DELAY);
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void warningAlertResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_WARNING_TEXT)).wrap();
                click(button, InputType.MOUSE);
                closeDialogWindowByClickingButton(b.getText());
                Wrap<? extends Node> resultField = parent.lookup(TextField.class, new ByID<TextField>(RESULT_FIELD_ID)).wrap();
                resultField.waitProperty(Wrap.TEXT_PROP_NAME, b.getText());
                Thread.sleep(DEFAULT_DELAY);
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void errorAlertResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_ERROR_TEXT)).wrap();
                click(button, InputType.MOUSE);
                closeDialogWindowByClickingButton(b.getText());
                Wrap<? extends Node> resultField = parent.lookup(TextField.class, new ByID<TextField>(RESULT_FIELD_ID)).wrap();
                resultField.waitProperty(Wrap.TEXT_PROP_NAME, b.getText());
                Thread.sleep(DEFAULT_DELAY);
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void textInputDialogResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_TEXT_FIELD_TEXT)).wrap();
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            closeDialogWindowByClickingButton("OK");
        }
    }

    @Test(timeout = 300000)
    public void textInputWithInitialValueDialogResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_INITIAL_VALUE_TEXT)).wrap();
            click(button, InputType.MOUSE);
            Thread.sleep(DEFAULT_DELAY);
            closeDialogWindowByClickingButton("OK");
        }
    }

    @Test(timeout = 300000)
    public void setChoiceDialogLtTenResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CHOICE_LT_TEN_TEXT)).wrap();
                click(button, InputType.MOUSE);
                closeDialogWindowByClickingButton(b.getText());
                Thread.sleep(1000);
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void setChoiceDialogGtTenResponceTest() throws Throwable {
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (ButtonType b : types) {
                addButton(b.getText());
                Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CHOICE_GT_TEN_TEXT)).wrap();
                click(button, InputType.MOUSE);
                closeDialogWindowByClickingButton(b.getText());
                Thread.sleep(DEFAULT_DELAY);
            }
            deselectButtons();
        }
    }

    @Test(timeout = 300000)
    public void alertWithExpandableContentTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_EXP_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (String c : content) {
                Wrap<? extends Node> contentToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(c)).wrap();
                click(contentToggleButton, InputType.MOUSE);
                click(button, InputType.MOUSE);
                Thread.sleep(DEFAULT_DELAY);
                SceneDock sd = new SceneDock();
                click(sd.asParent().lookup(Hyperlink.class, new ByText<Hyperlink>("Show Details")).wrap(), InputType.MOUSE);
                // checkScreenshot("alertWithExpandableContentTest" + s + "_" + c + "Test", scene);
                Thread.sleep(DEFAULT_DELAY);
                closeDialogWindowByClickingButton("OK");
                throwScreenshotError();
            }
        }
    }

    @Test(timeout = 300000)
    public void alertWithCustomContentTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_CUSTOM_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            for (String c : content) {
                Wrap<? extends Node> contentToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(c)).wrap();
                click(contentToggleButton, InputType.MOUSE);
                click(button, InputType.MOUSE);
                Thread.sleep(DEFAULT_DELAY);
                SceneDock sd = new SceneDock();
                checkScreenshot("alertWithCustomContentTest" + s + "_" + c + "Test", scene);
                Thread.sleep(DEFAULT_DELAY);
                closeDialogWindowByClickingButton("OK");
                throwScreenshotError();
            }
        }
    }

    @Test(timeout = 300000)
    public void alertWithNullCustomContentTest() throws Throwable {
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(BUTTON_SHOW_EXP_TEXT)).wrap();
        Wrap<? extends Node> contentToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(TOGGLE_NOTHING_TEXT)).wrap();
        for (StageStyle s : StageStyle.values()) {
            Wrap<? extends Node> styleToggleButton = parent.lookup(ToggleButton.class, new ByText<ToggleButton>(s.toString())).wrap();
            click(styleToggleButton, InputType.MOUSE);
            click(contentToggleButton, InputType.MOUSE);
            click(button, InputType.MOUSE);
            closeDialogWindowByClickingButton("OK");
        }
    }

    protected void click(final Wrap<? extends Node> wrap, InputType ctrl) {
        switch (ctrl) {
            case MOUSE:
                wrap.mouse().click();
                break;
            case SPACE:
                if (requestFocusByKeyboard(wrap)) {
                    wrap.keyboard().pushKey(KeyboardButtons.SPACE);
                }
                break;
            case ENTER:
                if (requestFocusByKeyboard(wrap)) {
                    wrap.keyboard().pushKey(KeyboardButtons.ENTER);
                }
                break;
        }
    }

    protected void closeDialogWindowByClickingButton() {
        SceneDock sd = new SceneDock();
        click(sd.asParent().lookup(Button.class).wrap(0), InputType.MOUSE);
    }

    protected void closeDialogWindowByClickingButton(String text) {
        SceneDock sd = new SceneDock();
        click(sd.asParent().lookup(Button.class, new ByText<Button>(text)).wrap(), InputType.MOUSE);
    }

    protected void closeDialogWindowByClosingWindow() {
        Application.invokeAndWait(() -> {
            final Robot robot = Application.GetApplication().createRobot();
            Wrap<? extends com.sun.glass.ui.Window> dialogWindow = Root.ROOT.lookup(new ByWindowType(Window.class)).lookup(Scene.class).wrap(0);
            robot.mouseMove(dialogWindow.getScreenBounds().x + dialogWindow.getScreenBounds().width - 2, dialogWindow.getScreenBounds().y - 20);
            robot.mousePress(1);
            robot.mouseRelease(1);
        });
    }

    protected void checkShowMastheadBox() {
        Wrap<? extends Node> showMastheadCheckbox = parent.lookup(CheckBox.class, new ByID<CheckBox>(CHK_SHOW_MASTHEAD_ID)).wrap();
        click(showMastheadCheckbox, InputType.MOUSE);
    }

    protected void saveSceneImage(String fileName) {
        Image sceneImage;
        sceneImage = scene.getScreenImage();
        sceneImage.save(GoldenImageManager.getScreenshotPath(fileName));
    }

    protected boolean requestFocusByKeyboard(final Wrap<? extends Node> wrap) {
        int numberOfControls = parent.lookup(Node.class).size() + 1;
        for (int i = 0; i < numberOfControls; ++i) {
            if (new GetAction<Boolean>() {

                @Override
                public void run(Object... os) throws Exception {
                    setResult(wrap.getControl().isFocused());
                }
            }.dispatch(Root.ROOT.getEnvironment())) {
                return true;
            }
            scene.keyboard().pushKey(KeyboardButtons.TAB);
        }
        return false;
    }
}
