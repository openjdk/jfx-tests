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
package javafx.scene.control.test.combobox;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import static javafx.scene.control.test.combobox.ComboBoxApp.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.stage.PopupWindow;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TestBase extends UtilTestFunctions {

    static Wrap<? extends ComboBox<String>> testedControl;
    static Wrap<? extends Scene> scene;
    static Wrap<? extends Scene> popupScene;
    protected boolean resetHardByDefault = true;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        ComboBoxApp.main(null);
        currentSettingOption = SettingOption.PROGRAM;
    }

    @After
    public void tearDown() {
        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = (Wrap<? extends ComboBox<String>>) parent.lookup(ComboBox.class, new ByID<ComboBox>(TESTED_COMBOBOX_ID)).wrap();
    }

    //                   ACTIONS
    protected void resetSceneSoft() {
        clickButtonForTestPurpose(SOFT_RESET_BUTTON_ID);
    }

    protected void resetSceneHard() {
        clickButtonForTestPurpose(HARD_RESET_BUTTON_ID);
    }

    protected void doNextResetHard() {
        doNextResetHard = true;
    }

    protected void doNextResetSoft() {
        doNextResetHard = false;
    }

    protected void applyCustomCellAPI() throws InterruptedException {
        clickButtonForTestPurpose(APPLY_CUSTOM_CELL_FACTORY_BUTTON_ID);
        Thread.sleep(SLEEP);
    }

    protected void applyCustomSelectionModel() {
        clickButtonForTestPurpose(APPLY_CUSTOM_SELECTION_MODEL_BUTTON_ID);
    }

    protected void applyCustomStringConverter() {
        clickButtonForTestPurpose(APPLY_CUSTOM_STRING_CONVERTER_BUTTON_ID);
    }

    protected void removeFromPos(int position) {
        setText(findTextField(REMOVE_ITEM_POS_TEXT_FIELD_ID), position);
        clickButtonForTestPurpose(REMOVE_BUTTON_ID);
    }

    protected void addElement(String element, int position) {
        setText(findTextField(ADD_ITEM_POSITION_TEXT_FIELD_ID), position);
        setText(findTextField(ADD_ITEM_TEXT_FIELD_ID), element);
        clickButtonForTestPurpose(ADD_ITEM_BUTTON_ID);
    }

    protected void addElements(Object... elements) {
        for (int i = 0; i < elements.length; i++) {
            addElement(String.valueOf(elements[i]), i);
        }
    }

    protected void clickDropDownButton() {
        testedControl.mouse().click(1, new Point(testedControl.getScreenBounds().width - 10, testedControl.getScreenBounds().height - 10));
    }

    protected Wrap<? extends Scene> getPopupWrap() {
        Wrap<? extends Scene> temp;
        try {
            temp = Root.ROOT.lookup(new ByWindowType(PopupWindow.class)).lookup(Scene.class).wrap(0);
        } catch (Exception e) {
            return null;
        }
        return temp;
    }

    protected boolean isPopupVisible() {
        return isPopupVisible(true);
    }

    protected boolean isPopupVisible(boolean checkCoordinates) {
        if ((popupScene = getPopupWrap()) != null) {
            Boolean res = new GetAction<Boolean>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(popupScene.getControl().getWindow().showingProperty().getValue());
                }
            }.dispatch(Root.ROOT.getEnvironment());
            if (res && checkCoordinates) {
                checkPopupCoordinates();
            }
            return res;
        } else {
            return false;
        }
    }

    protected Wrap<? extends TextField> getEditTextFieldWrap() {
        return testedControl.as(Parent.class, Node.class).lookup(TextField.class).wrap(0);
    }

    protected String getCurrentValue() {
        return new GetAction<String>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getValue().toString());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected String getTextFieldPrompt() {
        return new GetAction<String>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getEditTextFieldWrap().getControl().getPromptText());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected String getTextFieldText() {
        return new GetAction<String>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(getEditTextFieldWrap().getControl().getText());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected String getValue() {
        return new GetAction<String>() {

            @Override
            public void run(Object... os) throws Exception {
                Object res = testedControl.getControl().getValue();
                if (res != null) {
                    setResult(testedControl.getControl().getValue().toString());
                } else {
                    setResult("null");
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void checkValue(String str) {
        assertEquals(str, getValue());
        checkTextFieldText(Properties.value, str);
    }

    //                  CHECKERS
    protected void checkEditable(boolean editable) {
        checkTextFieldText(Properties.editable, String.valueOf(editable));
        Assert.assertEquals(checkTextFieldVisibility(), editable);
    }

    protected void checkPopupShowing(boolean showing) throws InterruptedException {
        checkSimpleListenerValue(Properties.showing, String.valueOf(showing));
        Assert.assertEquals(isPopupVisible(), showing);
    }

    protected void checkScrollBarVisibility(boolean visible) throws InterruptedException {
        Wrap<? extends Scene> popup = getPopupWrap();
        if (popup != null) {
            Assert.assertTrue(findScrollBar(((Parent<Node>) ((Wrap<? extends ListView>) ((Parent<Node>) popup.as(Parent.class, Node.class))
                    .lookup(ListView.class).wrap()).as(Parent.class, Node.class)), Orientation.VERTICAL, visible) != null);
        }
    }

    protected boolean checkTextFieldVisibility() {
        Parent<Node> parentWrap = testedControl.as(Parent.class, Node.class);
        LookupCriteria<Node> lc = new LookupCriteria<Node>() {

            public boolean check(Node cntrl) {
                return cntrl instanceof TextField && cntrl.isVisible();
            }
        };
        return parentWrap.lookup(lc).size() != 0;
    }

    public void checkPopupCoordinates() {
        Wrap listViewWrap = getPopupWrap().as(Parent.class, Node.class).lookup(ListView.class).wrap();
        assertEquals(listViewWrap.getScreenBounds().x, testedControl.getScreenBounds().x, 2);
        assertEquals(listViewWrap.getScreenBounds().y, testedControl.getScreenBounds().y + testedControl.getScreenBounds().height, 2);
    }

    protected void checkSelectionState(int currentIndex, int currentItem) {
        checkSimpleListenerValue(Properties.selectedIndex, currentIndex);
        checkSimpleListenerValue(Properties.selectedItem, currentItem);
    }
    protected final static int ITER = 30;

    static protected enum Properties {

        prefWidth, prefHeight, visibleRowCount, promptText, visible, disable, showing, armed, editable, selectedIndex, selectedItem, value, text, parent, placeholder
    };
}
