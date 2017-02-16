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
package javafx.scene.control.test.choicebox;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import com.sun.javafx.scene.control.LabeledText;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.layout.Pane;
import javafx.stage.PopupWindow;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.resources.StringComparePolicy;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.screenshots.ScreenshotUtils;

/**
 *
 * @author shura
 */
@RunWith(FilteredTestRunner.class)
public class ChoiceTest extends ControlsTestBase {

    static Wrap<? extends ChoiceBox> choice;
    static Parent<Node> scene;
    static Wrap<? extends Scene> sceneWrap;
    static Wrap<? extends Label> selection;
    static Wrap<? extends ListView> list;
    static Wrap add;
    static Text textField;
    static Wrap getField;
    static Wrap show;
    static Wrap hide;
    static Wrap get;
    static Wrap converter;
    static Wrap removeConverter;
    static Wrap clear;
    static Wrap reset;
    static Wrap isShowing;
    static Wrap propVal;
    static Wrap propArg1;
    static Wrap propArg2;
    static Wrap testPane;

    public ChoiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ChoiceBoxApp.main(new String[0]);

        sceneWrap = Root.ROOT.lookup(new ByWindowType(Window.class)).lookup(Scene.class).wrap();

        scene = sceneWrap.as(Parent.class, Node.class);
        choice = scene.lookup(ChoiceBox.class).wrap();
        list = scene.lookup(ListView.class).wrap();
        selection = scene.lookup(Label.class, new ByID<Label>(ChoiceBoxApp.SELECTION_LABEL)).wrap();

        get = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.GET_BUTTON_ID)).wrap();
        getField = scene.lookup(TextField.class, new ByID<TextField>(ChoiceBoxApp.GET_FIELD_ID)).wrap();

        converter = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.CONVERTER_BUTTON_ID)).wrap();
        removeConverter = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.REMOVE_CONVERTER_BUTTON_ID)).wrap();

        add = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.ADD_BUTTON_ID)).wrap();
        textField = scene.lookup(TextField.class, new ByID<TextField>(ChoiceBoxApp.ADD_FIELD)).wrap().as(Text.class);

        show = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.SHOW_BUTTON_ID)).wrap();
        hide = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.HIDE_BUTTON_ID)).wrap();

        clear = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.CLEAR_BUTTON_ID)).wrap();
        reset = scene.lookup(Button.class, new ByID<Button>(ChoiceBoxApp.RESET_BUTTON_ID)).wrap();

        isShowing = scene.lookup(CheckBox.class, new ByID<CheckBox>(ChoiceBoxApp.IS_SHOWING_CHECK_ID)).wrap();
        propVal = scene.lookup(CheckBox.class, new ByID<CheckBox>(ChoiceBoxApp.SHOWING_PROPERTY_VAL_CHECK_ID)).wrap();
        propArg1 = scene.lookup(CheckBox.class, new ByID<CheckBox>(ChoiceBoxApp.SHOWING_PROPERTY_ARG1_CHECK_ID)).wrap();
        propArg2 = scene.lookup(CheckBox.class, new ByID<CheckBox>(ChoiceBoxApp.SHOWING_PROPERTY_ARG2_CHECK_ID)).wrap();

        testPane = scene.lookup(Pane.class, new ByID<Pane>(ChoiceBoxApp.TEST_PANE_ID)).wrap();
    }

    @Before
    public void setUp() {
        reset.mouse().click();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void initialState() throws InterruptedException {
        ScreenshotUtils.checkScreenshot("ChoiceBox-initialState", testPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selection() throws Throwable {
        checkSelection(choice.as(Selectable.class).selector(), false);
        checkSelection(list.as(Selectable.class).selector(), false);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void showAPI() throws InterruptedException {
        for (int i = 0; i < 5; i++) { // cycle is reassonable due to previously observed bugs
            hide.mouse().click();
            show.mouse().click();
            checkState(true);
            ScreenshotUtils.checkScreenshot("ChoiceBox-showAPI", testPane);
        }
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void show() throws InterruptedException {
        for (int i = 0; i < 5; i++) { // cycle is reassonable due to previously observed bugs
            testPane.mouse().click();
            choice.mouse().click();
            checkState(true);
            ScreenshotUtils.checkScreenshot("ChoiceBox-show", testPane);
        }
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void hideAPI() throws InterruptedException {
        show.mouse().click();
        hide.mouse().click();
        checkState(false);
        ScreenshotUtils.checkScreenshot("ChoiceBox-hideAPI", testPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void hide() throws InterruptedException {
        choice.mouse().click();
        testPane.mouse().click();
        checkState(false);
        ScreenshotUtils.checkScreenshot("ChoiceBox-hide-outside", testPane);
        hide.mouse().click();
        hide.mouse().click();
        checkState(false);
        ScreenshotUtils.checkScreenshot("ChoiceBox-hide", testPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void add() throws InterruptedException {
        clear.mouse().click();
        reset.mouse().click();
        textField.clear();
        textField.type("New item");
        add.mouse().click();
        choice.mouse().click();
        ScreenshotUtils.checkScreenshot("ChoiceBox-add", testPane);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardCapture() throws InterruptedException { //RT-12597
        choice.mouse().click();
        Wrap<? extends Scene> popupScene = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).wrap(0);
        Node focused = getFocused();
        popupScene.keyboard().pushKey(KeyboardButtons.UP);
        assertEquals(focused, getFocused());
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void clear() throws InterruptedException {
        choice.as(Selectable.class).selector().select("1");
        selection.waitProperty(Wrap.TEXT_PROP_NAME, "1");
        clear.mouse().click();
        choice.mouse().click();
        ScreenshotUtils.checkScreenshot("ChoiceBox-clear", testPane);
    }

    @Smoke
    @Test(timeout = 300000)
    public void converter() throws InterruptedException {
        choice.as(Selectable.class).selector().select("1");
        converter.mouse().click();
        final LookupCriteria marker = new ByText("Converted", StringComparePolicy.SUBSTRING);
        final Parent choiceAsParent = choice.as(Parent.class, Node.class);
        choiceAsParent.lookup(marker).wait(1);
        choice.mouse().click();
        final Lookup<? extends Scene> popupLookup = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class);
        popupLookup.wrap().as(Parent.class, Node.class).lookup(marker).wait(3);
        removeConverter.mouse().click();
        choice.waitState(new State<Integer>() {
            public Integer reached() {
                return choiceAsParent.lookup(marker).size();
            }
        }, 0);
        choice.mouse().click();
        choice.waitState(new State<Integer>() {
            public Integer reached() {
                return popupLookup.wrap().as(Parent.class, Node.class).lookup(marker).size();
            }
        }, 0);
    }

    /**
     * Checks that when the sorting is applied
     * to the underlying data collection
     * the cells are rendered in the right order.
     */
    @Smoke
    @Test(timeout=30000)
    public void renderingAfterSortingTest() {
        final int ITEMS_COUNT = 10;

        StringConverter<String> conv = new StringConverter<String>() {
            @Override public String toString(String s) { return s; }

            @Override public String fromString(String s) { return s; }
        };

        SortValidator<String, LabeledText> validator
                = new SortValidator<String, LabeledText>(ITEMS_COUNT, conv) {

            @Override
            protected void setControlData(final ObservableList<String> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        choice.getControl().setItems(ls);
                    }
                }.dispatch(choice.getEnvironment());
            }

            @Override
            protected Lookup<? extends LabeledText> getCellsLookup() {
                choice.mouse().click();

                final Lookup<? extends Scene> popup =
                        Root.ROOT.lookup(new ByWindowType(PopupWindow.class))
                        .lookup(Scene.class);

                choice.waitState(new State() {
                    public Boolean reached() {
                        if (popup.size() == 1) {
                            return Boolean.valueOf(true);
                        } else {
                            return null;
                        }
                    }
                });

                Lookup result = popup.as(Parent.class, Node.class)
                                       .lookup(LabeledText.class);

                choice.keyboard().pushKey(KeyboardButtons.ESCAPE);

                return result;
            }

            @Override
            protected String getTextFromCell(LabeledText cell) {
                return cell.getText();
            }
        };

        boolean result = validator.check();
        String msg = validator.getFailureReason();
        assertTrue(msg, result);
    }

    protected void checkState(boolean check) {
        isShowing.waitProperty(TextControlWrap.SELECTED_PROP_NAME, check ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        propVal.waitProperty(TextControlWrap.SELECTED_PROP_NAME, check ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        propArg1.waitProperty(TextControlWrap.SELECTED_PROP_NAME, check ? CheckBoxWrap.State.UNCHECKED : CheckBoxWrap.State.CHECKED);
        propArg2.waitProperty(TextControlWrap.SELECTED_PROP_NAME, check ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
    }

    protected Node getFocused() {
        return new GetAction<Node>() {
            @Override
            public void run(Object... parameters) {
                setResult(sceneWrap.getControl().getFocusOwner());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void checkSelection(Selector selector, boolean requestFocus) {
        Object[] objects = new Object[]{"1", 3, false};
        for (int i = 0; i < objects.length; i++) {
            selector.select(objects[i]);
            get.mouse().click();
            getField.waitProperty(Wrap.TEXT_PROP_NAME, objects[i].toString());
            selection.waitProperty(Wrap.TEXT_PROP_NAME, objects[i].toString());
            list.waitState(new State() {
                public Object reached() {
                    return list.as(Selectable.class).getState();
                }
            }, objects[i]);
            if (requestFocus) {
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        choice.getControl().requestFocus();
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
            checkScreenshot("ChoiceBox-selection" + (i + 1), testPane);
        }
    }
}
