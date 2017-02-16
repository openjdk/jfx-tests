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
package javafx.scene.control.test.util;

import com.sun.javafx.geom.PickRay;
import com.sun.javafx.geom.Vec3d;
import com.sun.javafx.scene.input.PickResultChooser;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ComponentsFactory.MultipleIndexFormComponent;
import javafx.scene.control.test.utils.ptables.AbstractApplicationPropertiesRegystry;
import javafx.scene.control.test.utils.ptables.AbstractEventsCounter;
import javafx.scene.control.test.utils.ptables.AbstractPropertiesTable;
import static javafx.scene.control.test.utils.ptables.AbstractPropertiesTable.BIDIR_PREFIX;
import static javafx.scene.control.test.utils.ptables.AbstractPropertiesTable.UNIDIR_PREFIX;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.control.test.utils.ptables.AbstractPropertyValueListener;
import static javafx.scene.control.test.utils.ptables.AbstractPropertyValueSetter.CONTROLLER_SUFFIX;
import static javafx.scene.control.test.utils.ptables.AbstractPropertyValueSetter.SET_PREFIX;
import javafx.scene.control.test.utils.ptables.AbstractStateCheckable.StateChangedException;
import static javafx.scene.control.test.utils.ptables.NodesChoserFactory.*;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import static javafx.scene.control.test.utils.ptables.PropertyValueListener.LISTENER_SUFFIX;
import static javafx.scene.control.test.utils.ptables.TabPaneWithControl.TAB_CONTENT_ID;
import static javafx.scene.control.test.utils.ptables.TabPaneWithControl.TAB_PANE_WITH_CONTROL_ID;
import static javafx.scene.control.test.utils.ptables.TextFieldEventsCounter.COUNTER_SUFFIX;
import static javafx.scene.control.test.utils.ptables.ToggleBindingSwitcher.BIND_BUTTON_SUFFIX;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ScrollEvent.HorizontalTextScrollUnits;
import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;
import javafx.scene.text.Font;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.Action;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TabPaneDock;
import static org.jemmy.fx.control.TextControlWrap.SELECTED_PROP_NAME;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.jemmy.timing.Waiter;
import static org.junit.Assert.*;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.Utils;

import com.sun.javafx.scene.NodeHelper;

/**
 * @author Alexander Kirov
 *
 * Used for many my tests. Don't touch it until you are sure, what you do.
 */
public class UtilTestFunctions extends ControlsTestBase {

    protected static Parent<Node> parent;
    protected static KeyboardModifiers CTRL_DOWN_MASK_OS;
    protected static ChangingController defaultController;
    protected static final int ITERATIONS = 3;
    protected static final int SLEEP = 300;
    protected static double ASSERT_CMP_PRECISION = 3.5;
    protected static SettingOption currentSettingOption = SettingOption.PROGRAM;
    private static Parent<Node> propertiesTableScrollPane = null;
    private static final boolean useCaching = true;
    private static String currentTabName = AbstractApplicationPropertiesRegystry.DEFAULT_DOMAIN_NAME;
    private static final String CUSTOM_STYLE_CONST = "imagescomparator/custom_font.css";
    private String rememberedStylesheet;

    static {
        if (Utils.isMacOS()) {
            CTRL_DOWN_MASK_OS = KeyboardModifiers.META_DOWN_MASK;
        } else {
            CTRL_DOWN_MASK_OS = KeyboardModifiers.CTRL_DOWN_MASK;
        }
    }

    protected void initChangingController(Parent<Node> parent) {
        defaultController = new ChangingController(parent);
    }

    /**
     * Clicks toggle button with according id.
     *
     * @param toggleButtonId
     */
    protected static void clickToggleButton(String toggleButtonId) {
        findToggleButton(toggleButtonId).mouse().click();
    }

    /**
     * Checks, whether the toggle button has a toggled state as asked.
     *
     * @param toggleButtonId
     * @param selectedExpected
     */
    protected static void checkToggleButtonSelection(String toggleButtonId, boolean selectedExpected) {
        findToggleButton(toggleButtonId).waitProperty(SELECTED_PROP_NAME, selectedExpected);
    }

    /**
     * Change toggle button's state to value, with set id.
     *
     * @param buttonId
     */
    protected static void setToggleButtonSelectedStateForTestPurpose(final String buttonId, final Boolean newValue) {
        final Wrap<? extends ToggleButton> btnWrap = findToggleButton(buttonId);
        Boolean curValue = new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(btnWrap.getControl().isSelected());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        switch (currentSettingOption) {
            case MANUAL:
                if (curValue != newValue) {
                    clickToggleButtonByID(buttonId);
                }
                break;
            case PROGRAM:
                if (curValue != newValue) {
                    new GetAction() {
                        @Override
                        public void run(Object... os) throws Exception {
                            findToggleButton(buttonId).getControl().setSelected(newValue);
                        }
                    }.dispatch(Root.ROOT.getEnvironment());
                }
                break;
        }
    }

    /**
     * Clicks button with set id.
     *
     * @param buttonId
     */
    protected static void clickButtonForTestPurpose(final String buttonId) {
        switch (currentSettingOption) {
            case MANUAL:
                clickButtonByID(buttonId);
                break;
            case PROGRAM:
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        Button btn = findButton(buttonId).getControl();
                        EventHandler<ActionEvent> onAction = btn.getOnAction();
                        if (null != onAction) {
                            onAction.handle(null);
                        } else {
                            System.err.format("onAction event handler is not set for [%s]%n", buttonId);
                            btn.fire();
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                break;
            default:
                throw new IllegalStateException("Unknown option.");
        }
    }

    protected static void clickToggleButtonByID(String buttonId) {
        findToggleButton(buttonId).mouse().click();
    }

    protected static void clickButtonByID(String buttonId) {
        findButton(buttonId).mouse().click();
    }

    /**
     * Checks focused state of control.
     *
     * @param nodeId
     * @param isFocused
     */
    protected static void checkFocus(String nodeId, boolean isFocused) {
        findControl(nodeId).waitProperty("isFocused", isFocused);
    }

    // Two functions clicking arrows of the scrollBar.
    // This function make click over scrollBar (left or up arrow)
    protected static void clickLess(Wrap<? extends ScrollBar> wrap) {
        wrap.mouse().click(1, new Point(5, 5));
    }

    // This function make click over scrollBar (right or down arrow)
    protected static void clickMore(Wrap<? extends ScrollBar> wrap) {
        wrap.mouse().click(1, new Point(wrap.getScreenBounds().width - 5, wrap.getScreenBounds().height - 5));
    }

    /**
     * *******************************************************************
     *
     * SECTION OF MANUAL CONTROLING
     *
     ********************************************************************
     */
    //               DIRECT PROPERTIES CONTROL
    protected void requestFocusOnControl(final Wrap<? extends Node> control) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                control.getControl().requestFocus();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected static void setSliderPosition(String id, final double toPosition, SettingOption option) throws InterruptedException {
        Thread.sleep(SLEEP);
        setSliderPosition(findSlider(id), toPosition, option);
    }

    protected static void setSliderPosition(final Wrap<? extends Slider> propertySliderWrap, final double toPosition, SettingOption option) throws InterruptedException {
        switch (option) {
            case MANUAL:
                AbstractScroll abstrScroll = propertySliderWrap.as(AbstractScroll.class);
                abstrScroll.allowError(ASSERT_CMP_PRECISION / 4);
                abstrScroll.caret().to(toPosition);
                break;
            case PROGRAM:
                new GetAction<Point2D>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        propertySliderWrap.getControl().setValue(toPosition);
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                Thread.sleep(SLEEP);
        }
    }

    protected static void setChoiceBoxValue(String controlID, final Object value) {
        switch (currentSettingOption) {
            case MANUAL:
                findChoiceBox(controlID).as(Selectable.class).selector().select(value);
                //new org.jemmy.fx.control.ChoiceBoxDock(parent, controlID).asSelectable().selector().select(value);
                //parent.lookup(ChoiceBox.class, new ByID<ChoiceBox>(getPrefix(type) + propType.toString().toUpperCase() + CHOICE_BOX_SUFFIX)).wrap().as(Selectable.class).selector().select(propValue.name());
                break;
            case PROGRAM:
                final Object actualValue = getChoiceBoxState(controlID);
                if (!value.equals(actualValue)) {
                    final ChoiceBox cb = findChoiceBox(controlID).getControl();
                    new GetAction() {
                        @Override
                        public void run(Object... os) throws Exception {
                            cb.setValue(value);
                        }
                    }.dispatch(Root.ROOT.getEnvironment());
                }
                break;
        }
    }

    protected static void changePropertyControlBindingToggleButtonState(SettingType type, Enum propType) {
        changeToggleButtonState(getPrefix(type) + propType.toString().toUpperCase() + BIND_BUTTON_SUFFIX);
    }

    protected static void changeToggleButtonState(String controlID) {
        switch (currentSettingOption) {
            case MANUAL:
                findToggleButton(controlID).mouse().click();
                break;
            case PROGRAM:
                final boolean selected = getToggleButtonState(controlID);
                final ToggleButton tb = findToggleButton(controlID).getControl();
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        tb.setSelected(!selected);
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                break;
        }
    }

    protected static void changeTextFieldText(String controlID, final String newText) {
        switch (currentSettingOption) {
            case MANUAL:
                Text text = findTextField(controlID).as(Text.class);
                text.clear();
                text.type(newText);
                break;
            case PROGRAM:
                final TextField tf = findTextField(controlID).getControl();
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        tf.setText(newText);
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                break;
        }
    }

    protected static void switchToPropertiesTab(final String tabName) {
        if (useCaching) {
            Cache.clear();
        }
        final Wrap<? extends TabPane> tabPane = parent.lookup(TabPane.class, new ByID<TabPane>(TAB_PANE_WITH_CONTROL_ID)).wrap();
        switch (currentSettingOption) {
            case MANUAL:
                Tab tab = new GetAction<Tab>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        for (Tab tab : tabPane.getControl().getTabs()) {
                            if (tab.getText().equals(tabName)) {
                                setResult(tab);
                            }
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                TabPaneDock dock = new TabPaneDock(tabPane);
                dock.asSelectable().selector().select(tab);
                break;
            case PROGRAM:
                new GetAction() {
                    @Override
                    public void run(Object... os) throws Exception {
                        Tab tabToSelect = null;
                        for (Tab tab : tabPane.getControl().getTabs()) {
                            if (tab.getText().equals(tabName)) {
                                tabToSelect = tab;
                            }
                        }
                        if (tabToSelect == null) {
                            throw new Exception("Tab with name <" + tabName + "> could not be found");
                        } else {
                            tabPane.getControl().getSelectionModel().select(tabToSelect);
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                break;
        }

        //Wait, until content of according Tab (root item of tab is scrollPane,
        //with preset Id), will be findable on the scene.
        new Waiter(new Timeout("", TestUtil.isEmbedded() ? 20000 : 2000)).ensureState(new State() {
            public Object reached() {
                if (parent.lookup(ScrollPane.class, new ByID<ScrollPane>(tabName + TAB_CONTENT_ID)).size() == 1) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        propertiesTableScrollPane = parent.lookup(ScrollPane.class, new ByID<ScrollPane>(tabName + javafx.scene.control.test.utils.ptables.TabPaneWithControl.TAB_CONTENT_ID)).wrap().as(Parent.class, Node.class);
        currentTabName = tabName;
    }

    //              WORKING WITH LISTENING TEXT FIELDS
    protected static void checkText(String textFieldId, String expectedText) {
        findTextField(textFieldId).waitProperty(Wrap.TEXT_PROP_NAME, expectedText);
    }

    protected static void checkTextFieldText(Enum propType, String expectedText) {
        findTextField(propType.toString().toUpperCase() + LISTENER_SUFFIX).waitProperty(Wrap.TEXT_PROP_NAME, expectedText);
        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void checkTextFieldTextContaining(final Enum propType, final String expectedContainedText) {
        final TextField tf = findTextField(propType.toString().toUpperCase() + LISTENER_SUFFIX).getControl();
        new Waiter(new Timeout("", TestUtil.isEmbedded() ? 20000 : 2000)).ensureState(new State() {
            public Object reached() {
                if (tf.getText().contains(expectedContainedText)) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void checkTextFieldValue(Enum propType, final double expectedValue) {
        final Wrap<? extends TextField> tfwrap = findTextField(propType.toString().toUpperCase() + LISTENER_SUFFIX);
        new Waiter(new Timeout("", TestUtil.isEmbedded() ? 20000 : 2000)).ensureState(new State() {
            public Object reached() {
                if (Math.abs(Double.parseDouble(tfwrap.getControl().getText()) - expectedValue) < ASSERT_CMP_PRECISION) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void checkTextFieldValue(Enum propType, final int expectedValue, final double COMPARISON_DELTA) {
        final Wrap<? extends TextField> tfwrap = findTextField(propType.toString().toUpperCase() + LISTENER_SUFFIX);
        new Waiter(new Timeout("", TestUtil.isEmbedded() ? 20000 : 2000)).ensureState(new State() {
            double actual;

            public Object reached() {
                actual = Double.parseDouble(tfwrap.getControl().getText());
                if (Math.abs(actual - expectedValue) <= COMPARISON_DELTA) {
                    return true;
                } else {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("[Expected:%d, got:%.6f, delta:%.6f]",
                        expectedValue, actual, COMPARISON_DELTA);
            }
        });

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void checkTextFieldValue(Enum propType, int expectedValue) {//more strict for int values.
        checkTextFieldValue(propType, expectedValue, ASSERT_CMP_PRECISION);
    }

    protected static void checkSimpleListenerValue(Enum propType, final int expectedValue) {
        final Wrap<? extends TextField> tfwrap = findTextField(propType.toString().toUpperCase() + LISTENER_SUFFIX);
        new Waiter(new Timeout("", TestUtil.isEmbedded() ? 20000 : 2000)).ensureState(new State() {
            public Object reached() {
                if (Math.abs(Double.parseDouble(tfwrap.getControl().getText()) - expectedValue) == 0) {
                    return true;
                } else {
                    System.err.println("Diff : tfwrap.getControl().getText() = " + tfwrap.getControl().getText() + "; expectedValue = " + expectedValue);
                    return null;
                }
            }
        });

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void checkFontSize(Enum property, Double expectedSize) {
        final Wrap<? extends TextField> tfwrap = findTextField(property.toString().toUpperCase() + LISTENER_SUFFIX);
        String fontDescription = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(tfwrap.getControl().getText());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        String fontSize = fontDescription.substring(fontDescription.indexOf("size=") + "size=".length(), fontDescription.indexOf(']', fontDescription.indexOf("size=")));
        assertEquals(Double.parseDouble(fontSize), expectedSize, 0.5);
    }

    protected static void checkSimpleListenerValue(Enum propType, String expectedText) {
        findTextField(propType.toString().toUpperCase() + LISTENER_SUFFIX).waitProperty(Wrap.TEXT_PROP_NAME, expectedText);
    }

    protected static void checkCounterValue(Enum propType, int expectedValue) {
        checkCounterValue(propType.toString().toUpperCase(), expectedValue);
    }

    protected static void checkCounterValue(String counterName, final int expectedValue) {
        final Wrap<? extends TextField> tfwrap = findTextField(counterName.toUpperCase() + COUNTER_SUFFIX);
        new Waiter(new Timeout("", TestUtil.isEmbedded() ? 20000 : 5000)).ensureState(new State() {
            public Object reached() {
                if (Math.abs(Double.parseDouble(tfwrap.getControl().getText()) - expectedValue) == 0) {
                    return true;
                } else {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("Expected value: %d, actual: %f.2", expectedValue, Double.parseDouble(tfwrap.getControl().getText()));
            }
        });

        if (defaultController != null) {
            defaultController.removeCounter(currentTabName, counterName);
        }
    }

    //                            SEARCHERS
    protected static Wrap<? extends ScrollBar> findScrollBar(final Parent<Node> parentWrap, final Orientation orientation, final Boolean visible) {
        final LookupCriteria<ScrollBar> lc = new LookupCriteria<ScrollBar>() {
            public boolean check(ScrollBar cntrl) {
                return ((cntrl.isVisible() == visible) && (cntrl.getOrientation() == orientation));
            }
        };

        try {
            new Waiter(new Timeout("", TestUtil.isEmbedded() ? 20000 : 2000)).ensureState(new State() {
                public Object reached() {
                    if (parentWrap.lookup(ScrollBar.class, lc).size() > 0) {
                        return true;
                    } else {
                        return null;
                    }
                }
            });
        } catch (Exception ex) {
            return null;
        }

        return parentWrap.lookup(ScrollBar.class, lc).wrap();
    }

    protected static Wrap<? extends ScrollBar> findScrollBar(Parent<Node> parentWrap, Orientation orientation) {
        return findScrollBar(parentWrap, orientation, true);
    }

    protected static Wrap<? extends ScrollBar> findScrollBar(Parent<Node> parentWrap) {
        Wrap<? extends ScrollBar> sb1, sb2;
        sb1 = findScrollBar(parentWrap, Orientation.VERTICAL);
        sb2 = findScrollBar(parentWrap, Orientation.HORIZONTAL);
        if (sb1 != null) {
            return sb1;
        } else {
            return sb2;
        }
    }

    //                                CONTROL
    protected static void setPropertyBySlider(SettingType type, Enum propType, double toPosition) throws InterruptedException {
        if (type == SettingType.SETTER) {
            switchOffBinding(SettingType.UNIDIRECTIONAL, propType);
            setSliderPosition(getPrefix(SettingType.UNIDIRECTIONAL) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX, toPosition, currentSettingOption);
            clickButtonForTestPurpose(SET_PREFIX + propType.toString().toUpperCase());
        } else {
            switchOnBinding(type, propType);
            setSliderPosition(getPrefix(type) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX, toPosition, currentSettingOption);
        }

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void setPropertyByToggleClick(SettingType type, Enum propType) {
        if (type == SettingType.SETTER) {
            switchOffBinding(SettingType.UNIDIRECTIONAL, propType);
            changeToggleButtonState(getPrefix(SettingType.UNIDIRECTIONAL) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX);
            clickButtonForTestPurpose(SET_PREFIX + propType.toString().toUpperCase());
        } else {
            switchOnBinding(type, propType);
            changeToggleButtonState(getPrefix(type) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX);
        }

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void setPropertyByToggleClick(SettingType type, Enum propType, Boolean newVal) {

        if (type == SettingType.SETTER) {
            String btnID = getPrefix(SettingType.UNIDIRECTIONAL) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX;
            final Wrap<? extends ToggleButton> btnWrap = findToggleButton(btnID);

            Boolean curVal = new GetAction<Boolean>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(btnWrap.getControl().isSelected());
                }
            }.dispatch(Root.ROOT.getEnvironment());

            switchOffBinding(SettingType.UNIDIRECTIONAL, propType);
            if (curVal != newVal) {
                setToggleButtonSelectedStateForTestPurpose(btnID, newVal);
            }
            clickButtonForTestPurpose(SET_PREFIX + propType.toString().toUpperCase());
        } else {
            String btnID = getPrefix(type) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX;
            setToggleButtonSelectedStateForTestPurpose(btnID, newVal);
            switchOnBinding(type, propType);
        }

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void setPropertyByChoiceBox(SettingType type, Object propValue, Enum propType) {
        if (type == SettingType.SETTER) {
            switchOffBinding(SettingType.UNIDIRECTIONAL, propType);
            setChoiceBoxValue(getPrefix(SettingType.UNIDIRECTIONAL) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX, propValue);
            clickButtonForTestPurpose(SET_PREFIX + propType.toString().toUpperCase());
        } else {
            switchOnBinding(type, propType);
            setChoiceBoxValue(getPrefix(type) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX, propValue);
        }

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected static void setPropertyByTextField(SettingType type, Enum propType, String value) {
        if (type == SettingType.SETTER) {
            switchOffBinding(SettingType.UNIDIRECTIONAL, propType);
            changeTextFieldText(getPrefix(SettingType.UNIDIRECTIONAL) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX, value);
            clickButtonForTestPurpose(SET_PREFIX + propType.toString().toUpperCase());
        } else {
            switchOnBinding(type, propType);
            changeTextFieldText(getPrefix(type) + propType.toString().toUpperCase() + CONTROLLER_SUFFIX, value);
        }

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, propType);
        }
    }

    protected void selectControlFromFactory(final int index) {
        final Wrap<? extends ChoiceBox> choice = (Wrap<? extends ChoiceBox>) findControl(NODE_CHOSER_CHOICE_BOX_ID);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                choice.getControl().getSelectionModel().select(index);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void selectControlFromFactory(final Class controlClass) {
        final Wrap<? extends ChoiceBox> choice = (Wrap<? extends ChoiceBox>) findControl(NODE_CHOSER_CHOICE_BOX_ID);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                for (Object item : choice.getControl().getItems()) {
                    if (item.getClass() == controlClass) {
                        choice.getControl().getSelectionModel().select(item);
                    }
                }

            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected int controlsFactorySize() {
        final Wrap<? extends ChoiceBox> choice = (Wrap<? extends ChoiceBox>) findControl(NODE_CHOSER_CHOICE_BOX_ID);
        return new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(choice.getControl().getItems().size());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /*
     * This is complex method. It should be able to select different fonts, and
     * different objects at all. So it will be executed always in a way, like
     * "program" (not manual) execution done.
     */
    protected static void selectObjectFromChoiceBox(SettingType settingType, Enum property, Object whichToChose) {
        Wrap<? extends ChoiceBox> choice;
        if (settingType == SettingType.SETTER) {
            switchOffBinding(SettingType.UNIDIRECTIONAL, property);
            choice = findChoiceBox(getPrefix(SettingType.UNIDIRECTIONAL) + property.toString().toUpperCase() + CONTROLLER_SUFFIX);
        } else {
            choice = findChoiceBox(getPrefix(settingType) + property.toString().toUpperCase() + CONTROLLER_SUFFIX);
        }

        selectObjectFromChoiceBox(choice, whichToChose);

        if (settingType.equals(SettingType.SETTER)) {
            clickButtonForTestPurpose(SET_PREFIX + property.toString().toUpperCase());
        } else {
            switchOnBinding(settingType, property);
        }

        if (defaultController != null) {
            defaultController.removeProperty(currentTabName, property);
        }
    }

    protected static void selectObjectFromChoiceBox(Wrap<? extends ChoiceBox> choiceBox, final Object whatToChose) {
        switch (currentSettingOption) {
            case MANUAL:
                choiceBox.as(Selectable.class).selector().select(whatToChose);
                break;
            case PROGRAM:
                new GetAction<Object>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        ChoiceBox choiceBox = (ChoiceBox) os[0];
                        Collection allObjects = choiceBox.getItems();
                        Object actualToChose = null;

                        if (whatToChose instanceof Font) {

                            for (Object font : allObjects) {
                                //Supposed - font are different in their color.
                                if ((font instanceof Font) && (((Font) font).getSize() == ((Font) whatToChose).getSize())) {
                                    actualToChose = font;
                                }
                            }
                        } else {
                            for (Object obj : allObjects) {
                                if ((obj != null) && obj.getClass().equals(whatToChose)) {
                                    actualToChose = obj;
                                }
                                if ((obj != null) && obj.equals(whatToChose)) {
                                    actualToChose = obj;
                                }
                            }
                            if (actualToChose == null) {
                                System.out.println("Select NULL (Possibly, nothing found to select).");
                            }
                        }

                        ChoiceBox choice = (ChoiceBox) os[0];
                        if (actualToChose == null) {
                            choice.setValue(null);
                        } else {
                            choice.getSelectionModel().select(actualToChose);
                        }
                    }
                }.dispatch(Root.ROOT.getEnvironment(), choiceBox.getControl());
                break;
        }
    }

    /**
     * Send ScrollEvent in the center of the control
     *
     * @param wrap Wrap, which will receive event
     * @param scrollX Number of pixels to scroll by x coordinate
     * @param scrollY Number of pixels to scroll by y coordinate
     */
    protected static void sendScrollEvent(final Wrap<? extends Scene> scene, Wrap<? extends Node> wrap, double scrollX, double scrollY) {
        double x = wrap.getScreenBounds().width / 4;
        double y = wrap.getScreenBounds().height / 4;
        sendScrollEvent(scene, wrap, scrollX, scrollY, HorizontalTextScrollUnits.NONE, scrollX, VerticalTextScrollUnits.NONE, scrollY, x, y, wrap.getScreenBounds().x + x, wrap.getScreenBounds().y + y);
    }

    protected static void sendScrollEvent(final Wrap<? extends Scene> scene, final Wrap<? extends Node> wrap,
            double _scrollX, double _scrollY,
            HorizontalTextScrollUnits _scrollTextXUnits, double _scrollTextX,
            VerticalTextScrollUnits _scrollTextYUnits, double _scrollTextY,
            double _x, double _y,
            double _screenX, double _screenY) {
        //For 2.1.0 :
        //final ScrollEvent scrollEvent = ScrollEvent.impl_scrollEvent(_scrollX, _scrollY, _scrollTextXUnits, _scrollTextX, _scrollTextYUnits, _scrollTextY, _x, _y, _screenX, _screenY, false, false, false, false);
        //For 2.2.0 :
        //Interpretation: EventType<ScrollEvent> eventType, double _scrollX, double _scrollY, double _totalScrollX, double _totalScrollY, HorizontalTextScrollUnits _scrollTextXUnits, double _scrollTextX, VerticalTextScrollUnits _scrollTextYUnits, double _scrollTextY, int _touchPoints, double _x, double _y, double _screenX, double _screenY, boolean _shiftDown, boolean _controlDown, boolean _altDown, boolean _metaDown, boolean _direct, boolean _inertia)
        //For 8.0 before b64 and RT-9383
        //final ScrollEvent scrollEvent = new ScrollEvent.impl_scrollEvent(ScrollEvent.SCROLL, _scrollX, _scrollY, _scrollX, _scrollY, _scrollTextXUnits, _scrollTextX, _scrollTextYUnits, _scrollTextY, 0, _x, _y, _screenX, _screenY, false, false, false, false, false, false);

        //new ScrollEvent(EventType<ScrollEvent> eventType,
        //double x, double y, double screenX, double screenY,
        //boolean shiftDown, boolean controlDown, boolean altDown, boolean metaDown,
        //boolean direct, boolean inertia, double deltaX, double deltaY, double gestureDeltaX, double gestureDeltaY,
        //ScrollEvent.HorizontalTextScrollUnits textDeltaXUnits, double textDeltaX,
        //ScrollEvent.VerticalTextScrollUnits textDeltaYUnits, double textDeltaY, int touchCount)
        final ScrollEvent scrollEvent = new ScrollEvent(ScrollEvent.SCROLL,
                _x, _y, _screenX, _screenY,
                false, false, false, false,
                false, false, _scrollX, _scrollY, 0, 0,
                _scrollTextXUnits, _scrollTextX,
                _scrollTextYUnits, _scrollTextY, 0, null /* PickResult?*/);

        wrap.getEnvironment().getExecutor().execute(wrap.getEnvironment(), true, new Action() {
            @Override
            public void run(Object... os) throws Exception {
                Wrap<? extends Node> wrap = ((Wrap<? extends Node>) os[2]);
                Point2D pointOnScene = new GetAction<Point2D>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(((Node) os[0]).localToScene((Double) os[1], (Double) os[2]));
                    }
                }.dispatch(Root.ROOT.getEnvironment(), wrap.getControl(), Double.valueOf(wrap.getScreenBounds().width / 4), Double.valueOf(wrap.getScreenBounds().height / 4));

                // The following line requires --add-exports javafx.graphics/com.sun.javafx.scene.input=ALL-UNNAMED
                final PickResultChooser result = new PickResultChooser();

                // The following line requires --add-exports javafx.graphics/com.sun.javafx.geom=ALL-UNNAMED
                //public PickRay(Vec3d origin, Vec3d direction, double nearClip, double farClip) {
                NodeHelper.pickNode(
                    (((Wrap<? extends Scene>) os[0]).getControl()).getRoot(),
                    new PickRay(new Vec3d(pointOnScene.getX(), pointOnScene.getY(), -10), new Vec3d(0, 0, 1), 1.0, 100),
                    result);
                Node node = result.getIntersectedNode();
                node.fireEvent(scrollEvent);
            }
        }, scene, scrollEvent, wrap);
    }

    protected static void switchOnBinding(SettingType type, Enum propType) {
        if (!getToggleButtonState(getPrefix(type) + propType.toString().toUpperCase() + BIND_BUTTON_SUFFIX)) {
            changePropertyControlBindingToggleButtonState(type, propType);
        }
    }

    protected static void switchOffBinding(SettingType type, Enum propType) {
        if (getToggleButtonState(getPrefix(type) + propType.toString().toUpperCase() + BIND_BUTTON_SUFFIX)) {
            changePropertyControlBindingToggleButtonState(type, propType);
        }
    }

    private static boolean getToggleButtonState(String controlID) {
        final ToggleButton tb = findToggleButton(controlID).getControl();
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(tb.selectedProperty().getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private static Object getChoiceBoxState(String controlID) {
        final ChoiceBox cb = findChoiceBox(controlID).getControl();
        return new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(cb.valueProperty().getValue());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private static String getPrefix(SettingType type) {
        if (type == SettingType.BIDIRECTIONAL) {
            return BIDIR_PREFIX;
        } else {
            return UNIDIR_PREFIX;
        }
    }

    protected static void setText(final Wrap<? extends TextField> tf, final int value) {
        setText(tf, String.valueOf(value));
    }

    protected static void setText(final Wrap<? extends TextField> tf, final String value) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                tf.getControl().setText(value);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected static void setCheckBoxState(String controlId, final boolean checked) {
        final Wrap<? extends CheckBox> wrap = (Wrap<? extends CheckBox>) findControl(controlId);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                wrap.getControl().setSelected(checked);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    /*
     * FINDERS. Needed for ControlTabs. The problem is with TabPane. Its content
     * (tabs' content) is not removed from SceneGraph, but just made invisible.
     * So there are many controls with the same id, because they correspond to
     * the same properties (by name) of different controls of the same class.
     * So, sometimes, we need to find them in scrollPane, which is inside some
     * Tab and its ID corresponds to name of tab.
     */
    protected static Wrap<? extends Button> findButton(String id) {
        return (Wrap<? extends Button>) findControl(id);
    }

    protected static Wrap<? extends TextField> findTextField(String id) {
        return (Wrap<? extends TextField>) findControl(id);
    }

    protected static Wrap<? extends Slider> findSlider(String id) {
        return (Wrap<? extends Slider>) findControl(id);
    }

    protected static Wrap<? extends ToggleButton> findToggleButton(String id) {
        return (Wrap<? extends ToggleButton>) findControl(id);
    }

    protected static Wrap<? extends ChoiceBox> findChoiceBox(String id) {
        return (Wrap<? extends ChoiceBox>) findControl(id);
    }

    protected static Wrap<? extends CheckBox> findCheckBox(String id) {
        return (Wrap<? extends CheckBox>) findControl(id);
    }

    protected static Wrap<? extends Control> findControl(String id) {
        if (useCaching) {
            Wrap cached = Cache.search(id);
            if (cached != null) {
                return cached;
            }
        }
        if ((propertiesTableScrollPane != null) && (propertiesTableScrollPane.lookup(Control.class, new ByID(id)).size() > 0)) {
            return propertiesTableScrollPane.lookup(Control.class, new ByID<Control>(id)).wrap();
        } else {
            if (parent.lookup(Control.class, new ByID(id)).size() == 1) {
                Wrap found = parent.lookup(Control.class, new ByID<Control>(id)).wrap();
                if (useCaching) {
                    if (Cache.search(id) != null) {
                        throw new IllegalStateException("We tried to lookup cached wrap. We shouldn't be here.");
                    } else {
                        Cache.add(id, found);
                    }
                }
                return found;
            } else {
                if (parent.lookup(Control.class, new ByID(id)).size() > 1) {
                    /**
                     * If you are here, possibly, you didn't call
                     * switchToPropertiesTab() before specifying a property
                     * value, which appears on several tabs.
                     */
                    throw new RuntimeException("Not unique ID!!! (" + id + ").");
                } else {
                    throw new RuntimeException("Not found!!! (" + id + ").");
                }
            }
        }
    }

    protected double adjustValue(double min, double max, double value) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less then max: min=" + min + "; max=" + max);
        }

        if (value < min) {
            return min;
        }

        if (value > max) {
            return max;
        }

        return value;
    }

    protected void clearCache() {
        Cache.clear();
    }

    public static String convertMultiIndicesToString(int... indices) {
        StringBuilder builder = new StringBuilder();
        for (int i : indices) {
            builder.append(i);
            builder.append(MultipleIndexFormComponent.INDICES_DELIMITER);
        }
        builder.delete(builder.length() - MultipleIndexFormComponent.INDICES_DELIMITER.length(), builder.length());
        return builder.toString();
    }

    protected void provideSpaceForControl(int width, int height, boolean nonTestedControlsVisibility) {
        Wrap<? extends Scene> scene = Root.ROOT.lookup().wrap();
        ((CommonPropertiesScene) scene.getControl()).setNonTestedContentVisibility(nonTestedControlsVisibility);
        ((CommonPropertiesScene) scene.getControl()).setTestedControlContainerSize(width, height);
    }

    protected Wrap<? extends Node> getParentWrap(Wrap<? extends Node> node) {
        final Node control = node.getControl();
        return new NodeWrap(node.getEnvironment(), new GetAction<Node>(){

            @Override
            public void run(Object... os) throws Exception {
                setResult(control.getParent());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
    }

    /**
     * Checks, that rec1 rel rec2.
     */
    public static void checkRectanglesRelation(Rectangle rec1, RectanglesRelations rel, Rectangle rec2) {
        switch (rel) {
            case ABOVE:
                assertTrue(rec1.y + rec1.height <= rec2.y);
                break;
            case BELOW:
                assertTrue(rec1.y >= rec2.y + rec2.height);
                break;
            case CONTAINS:
                assertTrue(rec1.contains(rec2));
                break;
            case ISCONTAINED:
                assertTrue(rec2.contains(rec1));
                break;
            case RIGHTER:
                assertTrue(rec1.x >= rec2.x + rec2.width);
                break;
            case LEFTER:
                assertTrue(rec1.x + rec1.width <= rec2.x);
                break;
            case CENTERED_IN_HORIZONTAL:
                //Centered, relative to horizontal dimension.
                assertEquals(rec1.y + rec1.height / 2.0, rec2.y + rec2.height / 2.0, 1.0);
                break;
            case CENTERED_IN_VERTICAL:
                //Centered, relative to vertical dimension.
                assertEquals(rec1.x + rec1.width / 2.0, rec2.x + rec2.width / 2.0, 1.0);
                break;
            default:
                throw new IllegalStateException("No case provided!");
        }
    }

    protected void removeStylesheet() {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                Scene scene = parent.lookup().wrap().getControl().getScene();
                for (String str : scene.getStylesheets()) {
                    if (str.contains(CUSTOM_STYLE_CONST)) {
                        rememberedStylesheet = str;
                        scene.getStylesheets().remove(str);
                        return;
                    }
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void restoreStylesheet() {
        assertNotNull(rememberedStylesheet);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                Scene scene = parent.lookup().wrap().getControl().getScene();
                scene.getStylesheets().add(rememberedStylesheet);
            }
        }.dispatch(Root.ROOT.getEnvironment());
        rememberedStylesheet = null;
    }

    private static class Cache {

        private static Map<String, Wrap> cache = new HashMap<String, Wrap>();
        private static Parent<Node> parentUsedForCaching = null;

        private static void add(String id, Wrap cachedValue) {
            if (parent.equals(parentUsedForCaching)) {
                cache.put(id, cachedValue);
            } else {
                cache.clear();
                parentUsedForCaching = parent;
                cache.put(id, cachedValue);
            }
        }

        private static Wrap search(String id) {
            if (parent.equals(parentUsedForCaching)) {
                return cache.get(id);
            } else {
                cache.clear();
                parentUsedForCaching = parent;
                return null;
            }
        }

        private static void clear() {
            cache.clear();
        }
    }

    /**
     * Use case:
     *
     * @Test public void testTest() throws InterruptedException {
     * initChangingController(parent);
     * defaultController.include().allTables().allProperties().allCounters().apply();
     * defaultController.fixCurrentState();
     * defaultController.provideInfoForCodeCompletion(TestBase.Properties.values(),
     * null);
     *
     * assertEquals((new Slider()).maxProperty().getValue(), 100,
     * ASSERT_CMP_PRECISION);//initial value
     *
     * setPropertyBySlider(AbstractPropertyController.SettingType.BIDIRECTIONAL,
     * TestBase.Properties.max, -10);
     * checkTextFieldValue(TestBase.Properties.value, -10);
     * checkTextFieldValue(TestBase.Properties.min, -10);
     * checkTextFieldValue(TestBase.Properties.max, -10);
     *
     * setPropertyBySlider(AbstractPropertyController.SettingType.BIDIRECTIONAL,
     * TestBase.Properties.max, 150); setSliderPosition(TESTED_SLIDER_ID, 130,
     * SettingOption.MANUAL); checkTextFieldValue(TestBase.Properties.value,
     * 130);
     *
     * testedControl.keyboard().pushKey(Keyboard.KeyboardButtons.END);
     * checkTextFieldValue(TestBase.Properties.value, 150);
     *
     * setPropertyBySlider(AbstractPropertyController.SettingType.UNIDIRECTIONAL,
     * TestBase.Properties.max, 30);
     * checkTextFieldValue(TestBase.Properties.min, -10);
     * checkTextFieldValue(TestBase.Properties.max, 30);
     * checkTextFieldValue(TestBase.Properties.value, 30);
     *
     * defaultController.check(); } Can be added into
     * javafx.scene.control.test.slider.SliderTest class.
     */
    protected static class ChangingController {

        //This variable can deactivate this class on failing tests, if occasionally it cannot be easily fixed.
        final private boolean FAIL_ON_ERROR_IN_NON_CHANGING_CHECKING = true;
        private Object[] propertiesEnum = null;
        private Object[] countersEnum = null;
        private Parent<Node> stageWithPropertiestables = null;
        private boolean codeCompletionInfoProvided = false;
        private boolean stateWasFixed = false;
        private boolean atLeastOneConfigDone = false;
        private String changingControllerVariableName;
        private List<Entry<AbstractPropertiesTable, String>> trackedProperties = new ArrayList<Entry<AbstractPropertiesTable, String>>();
        private List<Entry<AbstractPropertiesTable, String>> trackedCounters = new ArrayList<Entry<AbstractPropertiesTable, String>>();

        private ChangingController(Parent<Node> parent) {
            if (parent == null) {
                throw new IllegalArgumentException("Stage for looking up the properties table cannot be null.");
            }
            this.stageWithPropertiestables = parent;
        }

        public void provideInfoForCodeCompletion(Object[] propertiesEnum, Object[] countersEnum) {
            this.changingControllerVariableName = "defaultController";
//            if (changingControllerVariableName == null) {
//                throw new IllegalArgumentException("Variable name cannot be null.");
//            }

            if ((propertiesEnum == null) && (countersEnum == null)) {
                throw new IllegalArgumentException("Both enums cannot be null.");
            }
            codeCompletionInfoProvided = true;
            this.propertiesEnum = propertiesEnum;
            this.countersEnum = countersEnum;
//            this.changingControllerVariableName = changingControllerVariableName;
        }

        public void fixCurrentState() {
            Exception ex = new GetAction<Exception>() {
                @Override
                public void run(Object... os) throws Exception {
                    try {
                        for (Entry<AbstractPropertiesTable, String> entry : trackedProperties) {
                            for (AbstractPropertyValueListener listener : entry.getKey().getListeners()) {
                                listener.rememberCurrentState();
                            }
                        }

                        for (Entry<AbstractPropertiesTable, String> entry : trackedCounters) {
                            for (AbstractEventsCounter counter : entry.getKey().getCounters()) {
                                counter.rememberCurrentState();
                            }
                        }
                    } catch (Exception ex) {
                        setResult(ex);
                    }
                }
            }.dispatch(Root.ROOT.getEnvironment());

            if (ex != null) {
                System.err.println("Exception during state fixing happened : " + ex.getMessage());
                ex.printStackTrace(System.err);
            }

            stateWasFixed = true;
        }

        public void check() {
            if (!atLeastOneConfigDone) {
                throw new IllegalStateException("No configs were done.");
            }
            if (!stateWasFixed) {
                throw new IllegalStateException("You need to call fixState() method, to fix the counters or properties.");
            }

            final List<FailedItem> failedItems = new ArrayList<FailedItem>();

            Throwable ex = new GetAction<Throwable>() {
                @Override
                public void run(Object... os) throws Exception {
                    try {


                        for (Entry<AbstractPropertiesTable, String> entry : trackedProperties) {
                            try {
                                for (AbstractPropertyValueListener listener : entry.getKey().getListeners()) {
                                    if (listener.getPropertyName().toUpperCase().equals(entry.getValue().toUpperCase())) {
                                        listener.checkCurrentStateEquality();
                                    }
                                }
                            } catch (StateChangedException exception) {
                                failedItems.add(new FailedItem(FailedItem.FailedItemType.PROPERTY, entry.getKey().getDomainName(), entry.getValue(), exception));
                            }
                        }

                        for (Entry<AbstractPropertiesTable, String> entry : trackedCounters) {
                            try {
                                for (AbstractEventsCounter counter : entry.getKey().getCounters()) {
                                    if (counter.getName().toUpperCase().equals(entry.getValue().toUpperCase())) {
                                        counter.checkCurrentStateEquality();
                                    }
                                }
                            } catch (StateChangedException exception) {
                                failedItems.add(new FailedItem(FailedItem.FailedItemType.COUNTER, entry.getKey().getDomainName(), entry.getValue(), exception));
                            }
                        }
                    } catch (Throwable ex) {
                        setResult(ex);
                    }
                }
            }.dispatch(Root.ROOT.getEnvironment());

            if (ex != null) {
                System.err.println("During checking an error occured : " + ex.getMessage());
                ex.printStackTrace(System.err);
            }

            if (codeCompletionInfoProvided) {
                generateCompletingCode(failedItems);
            }

            fail(failedItems);
        }

        public Config include() {
            if (stateWasFixed) {
                throw new IllegalStateException("Inclusion cannot be done, because fixing has been done.");
            }
            return new Config(this, true);
        }

        public Config exclude() {
            return new Config(this, false);
        }

        protected void removeProperty(String domainName, Enum propertyName) {
            new Config(this, false).table(domainName).property(propertyName).apply();
        }

        protected void removeCounter(String domainName, String counterName) {
            new Config(this, false).table(domainName).counter(counterName).apply();
        }

        final public void setPropertiesEnum(Object[] propertiesEnum) {
            this.propertiesEnum = propertiesEnum;
        }

        final public void setCountersEnum(Object[] countersEnum) {
            this.countersEnum = countersEnum;
        }

        private void generateCompletingCode(List<FailedItem> failedItems) {
            if (!failedItems.isEmpty()) {
                System.out.println("You can add this code to provide code completion : ");
            }

            try {
                for (FailedItem failedItem : failedItems) {
                    if (failedItem.getFailedItemType().equals(FailedItem.FailedItemType.COUNTER)) {
                        if (countersEnum != null) {
                            int index = findItemIndex(countersEnum, failedItem.getName());
                            if (index != -1) {
                                System.out.println(changingControllerVariableName + ".exclude().table(" + failedItem.getDomainName() + ").counter(Counters." + countersEnum[index].toString() + ").apply();");
                            }
                        }
                    }

                    if (failedItem.getFailedItemType().equals(FailedItem.FailedItemType.PROPERTY)) {
                        if (propertiesEnum != null) {
                            int index = findItemIndex(propertiesEnum, failedItem.getName());
                            if (index != -1) {
                                System.out.println(changingControllerVariableName + ".exclude().table(" + failedItem.getDomainName() + ").property(Properties." + propertiesEnum[index].toString() + ").apply();");
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                System.err.println("During code generating an error has occured : " + ex.getMessage());
                ex.printStackTrace(System.err);
            }
        }

        private void fail(List<FailedItem> failedItems) {
            for (FailedItem failedItem : failedItems) {
                System.err.println("Failed on checking : " + failedItem);
            }

            if (FAIL_ON_ERROR_IN_NON_CHANGING_CHECKING) {
                if (!failedItems.isEmpty()) {
                    throw new IllegalStateException("Failures detected.");
                }
            }
        }

        private int findItemIndex(Object[] enumm, String name) {
            for (int i = 0; i < enumm.length; i++) {
                if (enumm[i].toString().toUpperCase().equals(name.toUpperCase())) {
                    return i;
                }
            }

            return -1;
        }

        protected void applyConfig(final Config config) {
            Throwable ex = new GetAction<Throwable>() {
                @Override
                public void run(Object... os) throws Exception {
                    try {
                        Map<String, AbstractPropertiesTable> currentlyExistingTables = searchForTables();

                        List<String> listOfTables = new ArrayList<String>();
                        if (config.allTables) {
                            for (AbstractPropertiesTable table : currentlyExistingTables.values()) {
                                listOfTables.add(table.getDomainName());
                            }
                        } else {
                            listOfTables = config.tableNames;
                        }

                        if (config.toInclude) {
                            for (String domainName : listOfTables) {
                                if (config.allCounters) {
                                    for (AbstractEventsCounter counter : currentlyExistingTables.get(domainName).getCounters()) {
                                        trackedCounters.add(new AbstractMap.SimpleEntry<AbstractPropertiesTable, String>(currentlyExistingTables.get(domainName), counter.getName()));
                                    }
                                } else {
                                    for (String counterName : config.counterNames) {
                                        trackedCounters.add(new AbstractMap.SimpleEntry<AbstractPropertiesTable, String>(currentlyExistingTables.get(domainName), counterName));
                                    }
                                }

                                if (config.allProperties) {
                                    for (AbstractPropertyValueListener listener : currentlyExistingTables.get(domainName).getListeners()) {
                                        trackedProperties.add(new AbstractMap.SimpleEntry<AbstractPropertiesTable, String>(currentlyExistingTables.get(domainName), listener.getPropertyName()));
                                    }
                                } else {
                                    for (Enum property : config.propertiesNames) {
                                        trackedProperties.add(new AbstractMap.SimpleEntry<AbstractPropertiesTable, String>(currentlyExistingTables.get(domainName), property.name()));
                                    }
                                }
                            }
                        } else { //ToExclude
                            for (String domainName : listOfTables) {
                                if (config.allCounters) {
                                    List<Entry<AbstractPropertiesTable, String>> removeList = new ArrayList<Entry<AbstractPropertiesTable, String>>();
                                    for (Entry<AbstractPropertiesTable, String> entry : trackedCounters) {
                                        if (entry.getKey().getDomainName().equals(domainName)) {
                                            removeList.add(entry);
                                        }
                                    }
                                    for (Entry<AbstractPropertiesTable, String> entry : removeList) {
                                        trackedCounters.remove(entry);
                                    }
                                } else {
                                    List<Entry<AbstractPropertiesTable, String>> removeList = new ArrayList<Entry<AbstractPropertiesTable, String>>();
                                    for (Entry<AbstractPropertiesTable, String> entry : trackedCounters) {
                                        for (String counterName : config.counterNames) {
                                            if (entry.getKey().getDomainName().equals(domainName) && entry.getValue().equalsIgnoreCase(counterName)) {
                                                removeList.add(entry);
                                            }
                                        }
                                    }
                                    for (Entry<AbstractPropertiesTable, String> entry : removeList) {
                                        trackedCounters.remove(entry);
                                    }
                                }

                                if (config.allProperties) {
                                    List<Entry<AbstractPropertiesTable, String>> removeList = new ArrayList<Entry<AbstractPropertiesTable, String>>();
                                    for (Entry<AbstractPropertiesTable, String> entry : trackedProperties) {
                                        if (entry.getKey().getDomainName().equals(domainName)) {
                                            removeList.add(entry);
                                        }
                                    }
                                    for (Entry<AbstractPropertiesTable, String> entry : removeList) {
                                        trackedProperties.remove(entry);
                                    }
                                } else {
                                    List<Entry<AbstractPropertiesTable, String>> removeList = new ArrayList<Entry<AbstractPropertiesTable, String>>();
                                    for (Entry<AbstractPropertiesTable, String> entry : trackedProperties) {
                                        for (Enum propertyName : config.propertiesNames) {
                                            if (entry.getKey().getDomainName().equals(domainName) && entry.getValue().equalsIgnoreCase(propertyName.name())) {
                                                removeList.add(entry);
                                            }
                                        }
                                    }
                                    for (Entry<AbstractPropertiesTable, String> entry : removeList) {
                                        trackedProperties.remove(entry);
                                    }
                                }
                            }
                        }
                    } catch (Throwable th) {
                        setResult(th);
                    }
                }
            }.dispatch(Root.ROOT.getEnvironment());

            if (ex != null) {
                ex.printStackTrace(System.err);
            }
        }

        private Map<String, AbstractPropertiesTable> searchForTables() {
            List<AbstractPropertiesTable> foundTables = new ArrayList<AbstractPropertiesTable>();

            int found = stageWithPropertiestables.lookup(PropertiesTable.class).size();
            for (int i = 0; i < found; i++) {
                foundTables.add(stageWithPropertiestables.lookup(PropertiesTable.class).wrap(i).getControl());
            }

            Map<String, AbstractPropertiesTable> tableToIdMathing = new HashMap<String, AbstractPropertiesTable>();

            for (final AbstractPropertiesTable table : foundTables) {
                String domainName = new GetAction<String>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(table.getDomainName());
                    }
                }.dispatch(Root.ROOT.getEnvironment());
                tableToIdMathing.put(domainName, table);
            }

            return tableToIdMathing;
        }

        protected enum CHECKING_SET {

            COUNTERS, PROPERTIES, STANDART_SET, ALL
        };

        private static class FailedItem {

            final private FailedItemType failedItemType;
            final private String name;
            final private String domainName;
            final private Exception exception;

            public FailedItem(FailedItemType failedItemType, String domainName, String name, Exception exception) {
                if (failedItemType == null) {
                    throw new IllegalArgumentException("Failed item type cannot be null.");
                }
                if (name == null) {
                    throw new IllegalArgumentException("Name cannot be null.");
                }
                if (domainName == null) {
                    throw new IllegalArgumentException("Domain name cannot be null.");
                }
                if (exception == null) {
                    throw new IllegalArgumentException("Create an exception.");
                }

                this.failedItemType = failedItemType;
                this.name = name;
                this.domainName = domainName;
                this.exception = exception;
            }

            public FailedItemType getFailedItemType() {
                return failedItemType;
            }

            public String getName() {
                return name;
            }

            public String getDomainName() {
                return domainName;
            }

            public Exception getException() {
                return exception;
            }

            public enum FailedItemType {

                PROPERTY, COUNTER
            };

            @Override
            public String toString() {
                return "Failed <" + failedItemType.name() + "> with name <" + name + "> on table <" + domainName + "> with error : \n" + exception.getMessage();
            }
        }

        /**
         * The only applicable way of doing initialisation is : Create an
         * instance -> set tables -> set properties and counters in any order ->
         * apply.
         */
        public static class Config {

            private ConfigStage currentStage = ConfigStage.NOT_STARTED;
            private ChangingController controller = null;
            private List<String> tableNames = new ArrayList<String>();
            private List<Enum> propertiesNames = new ArrayList<Enum>();
            private List<String> counterNames = new ArrayList<String>();
            private boolean allTables = false;
            private boolean allCounters = false;
            private boolean allProperties = false;
            private Boolean toInclude = null;

            /**
             * @param controller that controller, which will receive this
             * config.
             * @param toInclude - true - to include, false - to exclude.
             */
            private Config(ChangingController controller, boolean toInclude) {
                if (controller == null) {
                    throw new IllegalArgumentException("Controller instance cannot be null.");
                }
                this.controller = controller;
                this.toInclude = toInclude;
            }

            public Config table(String name) {
                if (name == null) {
                    throw new IllegalStateException("Name cannot be null.");
                }
                checkNonInitialisedOrTablesSetStage();
                tableNames.add(name);
                currentStage = ConfigStage.DOMAIN_SET;
                return this;
            }

            public Config tables(String... names) {
                if (names == null) {
                    throw new IllegalStateException("Names cannot be null.");
                }
                checkNonInitialisedOrTablesSetStage();
                for (String name : names) {
                    if (name == null) {
                        throw new IllegalStateException("No one name can be null.");
                    }
                    tableNames.add(name);
                }
                currentStage = ConfigStage.DOMAIN_SET;
                return this;
            }

            /**
             * You can write: tables("treeItem-<i>", 1, 4) to decribe such list
             * of items: "treeItem-1", "treeItem-2", "treeItem-3", "treeItem-4"
             *
             * @param pattern should contain <i>
             * @param start initial index.
             * @param end final index
             * @return self
             */
            public Config tables(String pattern, int start, int end) {
                if (pattern == null) {
                    throw new IllegalStateException("Names cannot be null.");
                }

                if (pattern.indexOf("<i>") < 0) {
                    throw new IllegalStateException("Patter should contain \"<i>\" substring.");
                }

                checkNonInitialisedOrTablesSetStage();
                for (int i = start; i <= end; i++) {
                    this.table(pattern.replace("<i>", String.valueOf(i)));
                }
                currentStage = ConfigStage.DOMAIN_SET;
                return this;
            }

            public Config allTables() {
                checkNonInitialisedOrTablesSetStage();
                allTables = true;
                currentStage = ConfigStage.DOMAIN_SET;
                return this;
            }

            public Config counter(String name) {
                if (name == null) {
                    throw new IllegalStateException("Name cannot be null.");
                }
                checkCanSetPropertyOrCounterDetermined();
                counterNames.add(name);
                currentStage = ConfigStage.PROPERTIES_OR_COUNTERS_SET;
                return this;
            }

            public Config property(Enum name) {
                if (name == null) {
                    throw new IllegalStateException("Name cannot be null.");
                }
                checkCanSetPropertyOrCounterDetermined();
                propertiesNames.add(name);
                currentStage = ConfigStage.PROPERTIES_OR_COUNTERS_SET;
                return this;
            }

            public Config counters(String... names) {
                if (names == null) {
                    throw new IllegalStateException("Names cannot be null.");
                }
                checkCanSetPropertyOrCounterDetermined();
                for (String name : names) {
                    if (name == null) {
                        throw new IllegalStateException("No one name can be null.");
                    }
                    counterNames.add(name);
                }
                currentStage = ConfigStage.PROPERTIES_OR_COUNTERS_SET;
                return this;
            }

            public Config properties(Enum... names) {
                if (names == null) {
                    throw new IllegalStateException("Names cannot be null.");
                }
                checkCanSetPropertyOrCounterDetermined();
                for (Enum name : names) {
                    if (name == null) {
                        throw new IllegalStateException("No one name can be null.");
                    }
                    propertiesNames.add(name);
                }
                currentStage = ConfigStage.PROPERTIES_OR_COUNTERS_SET;
                return this;
            }

            public Config allCounters() {
                checkCanSetPropertyOrCounterDetermined();
                allCounters = true;
                currentStage = ConfigStage.PROPERTIES_OR_COUNTERS_SET;
                return this;
            }

            public Config allProperties() {
                checkCanSetPropertyOrCounterDetermined();
                allProperties = true;
                currentStage = ConfigStage.PROPERTIES_OR_COUNTERS_SET;
                return this;
            }

            public void apply() {
                if (currentStage != ConfigStage.PROPERTIES_OR_COUNTERS_SET) {
                    throw new IllegalStateException("Cannot be applied, when properties or counters are not determined.");
                }
                controller.atLeastOneConfigDone = true;
                controller.applyConfig(this);
                currentStage = ConfigStage.APPLIED;
            }

            private void checkNonInitialisedOrTablesSetStage() {
                if (currentStage != ConfigStage.NOT_STARTED && currentStage != ConfigStage.DOMAIN_SET) {
                    throw new IllegalStateException("Should be not initialised, or only tables initialised.");
                }
            }

            private void checkCanSetPropertyOrCounterDetermined() {
                if (!((currentStage == ConfigStage.DOMAIN_SET) || (currentStage == ConfigStage.PROPERTIES_OR_COUNTERS_SET))) {
                    throw new IllegalStateException();
                }
            }

            private static enum ConfigStage {

                NOT_STARTED, DOMAIN_SET, PROPERTIES_OR_COUNTERS_SET, APPLIED
            };
        }
    }

    static protected enum SettingOption {

        MANUAL, PROGRAM
    };

    static public enum RectanglesRelations {

        BELOW, LEFTER, RIGHTER, ABOVE, ISCONTAINED, CONTAINS, CENTERED_IN_HORIZONTAL, CENTERED_IN_VERTICAL
    }
}