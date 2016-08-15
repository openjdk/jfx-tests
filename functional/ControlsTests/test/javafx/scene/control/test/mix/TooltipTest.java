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

package javafx.scene.control.test.mix;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.TooltipApp;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.input.SelectionText;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

@RunWith(FilteredTestRunner.class)
public class TooltipTest extends ControlsTestBase {

    final static int AUTO_HIDE_DELAY = 2800;
    final static int HIDE_DELAY = 500;
    final static int CHECK_CYCLES_NUM = 5;
    static Parent<Node> scene;
    static Wrap<? extends Scene> sceneWrap;
    static Wrap<? extends Label> selection;
    static Wrap reset;
    static Wrap tooltipped;
    static Wrap disable;
    static Wrap show;
    static Wrap hide;
    static Wrap isVisible;
    static Wrap isActivated;
    static Wrap activatedPropVal;
    static Wrap testPane;
    static Wrap<? extends ChoiceBox> displayContent;
    static Wrap<? extends ChoiceBox> textAlignment;
    static Wrap<? extends ChoiceBox> textOverrun;
    static SelectionText fontSize;
    static Wrap setFontSize;
    static SelectionText gapSize;
    static Wrap setGapSize;
    static int defaultSmoothness = getMouseSmoothness();

    public TooltipTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        TooltipApp.main(new String[0]);

        sceneWrap = Root.ROOT.lookup(new ByWindowType(Window.class)).lookup(Scene.class).wrap();

        scene = sceneWrap.as(Parent.class, Node.class);

        tooltipped = scene.lookup(Button.class, new ByID<Button>(TooltipApp.TOOLTIPPED_BUTTON_ID)).wrap();
        disable = scene.lookup(ToggleButton.class, new ByID<ToggleButton>(TooltipApp.DISABLE_BUTTON_ID)).wrap();

        show = scene.lookup(Label.class, new ByID<Label>(TooltipApp.SHOW_BUTTON_ID)).wrap();
        hide = scene.lookup(Label.class, new ByID<Label>(TooltipApp.HIDE_BUTTON_ID)).wrap();

        isVisible = scene.lookup(CheckBox.class, new ByID<CheckBox>(TooltipApp.IS_VISIBLE_CHECK_ID)).wrap();

        isActivated = scene.lookup(CheckBox.class, new ByID<CheckBox>(TooltipApp.IS_ACTIVATED_CHECK_ID)).wrap();
        activatedPropVal = scene.lookup(CheckBox.class, new ByID<CheckBox>(TooltipApp.ACTIVATED_PROPERTY_VAL_CHECK_ID)).wrap();

        reset = scene.lookup(Button.class, new ByID<Button>(TooltipApp.RESET_BUTTON_ID)).wrap();

        displayContent = scene.lookup(ChoiceBox.class, new ByID<ChoiceBox>(TooltipApp.CONTENT_DISPLAY_ID)).wrap();

        textAlignment = scene.lookup(ChoiceBox.class, new ByID<ChoiceBox>(TooltipApp.TEXT_ALIGNMENT_ID)).wrap();

        textOverrun = scene.lookup(ChoiceBox.class,  new ByID<ChoiceBox>(TooltipApp.TEXT_OVERRUN_ID)).wrap();

        testPane = scene.lookup(Pane.class, new ByID<Pane>(TooltipApp.TEST_PANE_ID)).wrap();

        fontSize = scene.lookup(TextField.class, new ByID<TextField>(TooltipApp.FONT_SIZE_TEXT_ID)).wrap().as(SelectionText.class);
        setFontSize = scene.lookup(Button.class, new ByID<Button>(TooltipApp.SET_FONT_SIZE_BUTTON_ID)).wrap();

        gapSize = scene.lookup(TextField.class, new ByID<TextField>(TooltipApp.GAP_SIZE_TEXT_ID)).wrap().as(SelectionText.class);
        setGapSize = scene.lookup(Button.class, new ByID<Button>(TooltipApp.SET_GAP_SIZE_BUTTON_ID)).wrap();
    }

    @Before
    public void setUp() throws Exception {
        reset.mouse().click();
        sceneWrap.mouse().move();
    }

    @After
    public void tearDown() {
        setMouseSmoothness(defaultSmoothness);
        Wrap error = scene.lookup(Label.class, new ByID<Label>(TooltipApp.ERROR_ID)).wrap();
        assertEquals("", error.getProperty(Wrap.TEXT_PROP_NAME));
    }

    @Smoke
    @Test(timeout=300000)
    @Covers(value={"javafx.scene.control.Tooltip.activated.SET", "javafx.scene.control.Tooltip.activated.GET"}, level=Level.FULL)
    public void enabled() throws InterruptedException {
        check(true);
    }

    @Smoke
    @Test(timeout=300000)
    public void disabled() throws InterruptedException {
        check(false);
    }

    @Smoke
    @Test(timeout=300000)
    public void showAPI() throws InterruptedException {
        for (int i = 0; i < CHECK_CYCLES_NUM; i++) {
            show.mouse().move();
            checkVisibleState(true);
            hide.mouse().move();
            checkVisibleState(false);
            checkActivatedState(false);
        }
    }

    @Smoke
    @Test(timeout=300000)
    public void autohide() throws InterruptedException {
        tooltipped.mouse().move();
        isVisible.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        Thread.sleep(AUTO_HIDE_DELAY);
        isVisible.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        try {
            isActivated.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        } catch (Throwable th) {
            return;
        }
        assertTrue(false);
    }

    @ScreenshotCheck
    @Test(timeout=300000)
    public void displayContent() throws Throwable {
        for (ContentDisplay display : ContentDisplay.values()) {
            displayContent.as(Selectable.class).selector().select(display);
            show.mouse().move();
            checkScreenshot("Tooltip-display-content-" + display, testPane);
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout=300000)
    public void textAlignment() throws Throwable {
        for (TextAlignment display : TextAlignment.values()) {
            textAlignment.as(Selectable.class).selector().select(display);
            show.mouse().move();
            checkScreenshot("Tooltip-text-alignment-" + display, testPane);
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout=300000)
    public void textOverrun() throws Throwable {
        for (OverrunStyle display : OverrunStyle.values()) {
            textOverrun.as(Selectable.class).selector().select(display);
            show.mouse().move();
            checkScreenshot("Tooltip-text-overrun-" + display, testPane);
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout=300000)
    public void fontSize() throws Throwable {
        fontSize.clear();
        fontSize.type("20");
        setFontSize.mouse().click();
        show.mouse().move();
        checkScreenshot("Tooltip-font-size-20", testPane);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout=300000)
    public void gapSize() throws Throwable {
        gapSize.clear();
        gapSize.type("20");
        setGapSize.mouse().click();
        show.mouse().move();
        checkScreenshot("Tooltip-gap-size-20", testPane);
        throwScreenshotError();
    }

    public void check(boolean enabled) throws InterruptedException {
        if (disable.getProperty(TextControlWrap.SELECTED_PROP_NAME).equals(enabled)) {
            disable.mouse().click();
        }
        setMouseSmoothness(4);
        for (int i = 0; i < CHECK_CYCLES_NUM; i++) {
            tooltipped.mouse().move();
            checkActivatedState(enabled);
            checkVisibleState(enabled);
            testPane.mouse().click();
            checkVisibleState(false);
            checkActivatedState(false);
        }
        setMouseSmoothness(defaultSmoothness);
    }

    protected void checkActivatedState(boolean check) {
        isActivated.waitProperty(TextControlWrap.SELECTED_PROP_NAME, check ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        activatedPropVal.waitProperty(TextControlWrap.SELECTED_PROP_NAME, check ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        if (check) {
            isVisible.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        }
    }

    protected void checkVisibleState(boolean check) {
        isVisible.waitProperty(TextControlWrap.SELECTED_PROP_NAME, check ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        if (check) {
            isActivated.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
            activatedPropVal.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        }
        assertEquals(check ? 1 : 0, Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).size());
    }

    protected Node getFocused() {
        return new GetAction<Node>() {
                @Override
                public void run(Object... parameters) {
                    setResult(sceneWrap.getControl().getFocusOwner());
                }
            }.dispatch(Root.ROOT.getEnvironment());
    }
}