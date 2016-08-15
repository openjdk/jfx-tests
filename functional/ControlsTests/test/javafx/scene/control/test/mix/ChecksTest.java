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

import client.test.Smoke;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import static javafx.scene.control.test.Buttons.ButtonsScene.*;
import javafx.scene.control.test.Checks;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.Utils;

/**
 *
 * @author cthulhu
 */
@RunWith(FilteredTestRunner.class)
public class ChecksTest extends ControlsTestBase {

    public ChecksTest() {
    }

    public static enum ControlType {

        MOUSE, SPACE, ENTER
    }
    Wrap<? extends Scene> scene = null;
    Parent<Node> parent = null;
    Wrap clear = null;
    static boolean isRemote;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Checks.main(null);
        isRemote = (test.javaclient.shared.AppLauncher.getInstance().getMode() == test.javaclient.shared.AppLauncher.Mode.REMOTE);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        scene = TestUtil.getScene();
        parent = scene.as(Parent.class, Node.class);
        clear = parent.lookup(Button.class, new ByText<Button>(CLEAR_BTN)).wrap();

        scene.mouse().click();
    }

    @After
    public void tearDown() {
        clear.mouse().click();
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouse() throws InterruptedException {
        final boolean changeExpected = true;
        checks(ControlType.MOUSE, changeExpected);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboard() throws InterruptedException {
        if (!isRemote) {
            final boolean changeExpected = true;
            checks(ControlType.SPACE, changeExpected);
        } else {
            System.out.println("Applet mode detected. Test skipped.");
        }
    }

    /*
     * Checks that when Enter is pressed the control will not change it's state.
     */
    @Smoke
    @Test(timeout = 300000)
    public void keyboardNotReacting() throws InterruptedException {
        if (!isRemote) {
            final boolean changeExpected = false;
            checks(ControlType.ENTER, changeExpected);
        } else {
            System.out.println("Applet mode detected. Test skipped.");
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void initialState() throws InterruptedException {
        final Wrap<? extends CheckBox> twsb = parent.lookup(CheckBox.class, new ByText<CheckBox>(TWO_STATE_CB)).wrap();
        assertFalse(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((CheckBox) twsb.getControl()).isSelected());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
        final Wrap<? extends CheckBox> trsb = parent.lookup(CheckBox.class, new ByText<CheckBox>(TRI_STATE_CB)).wrap();
        assertFalse(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((CheckBox) trsb.getControl()).isSelected());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
        final Wrap<? extends CheckBox> db = parent.lookup(CheckBox.class, new ByText<CheckBox>(DISABLED_CONTROL)).wrap();
        assertFalse(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((CheckBox) db.getControl()).isSelected());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
    }

    @Covers(value = {"javafx.scene.control.CheckBox.selected.GET", "javafx.scene.control.CheckBox.selected.SET", "javafx.scene.control.CheckBox.selected.DEFAULT", "javafx.scene.control.CheckBox.indeterminate.GET", "javafx.scene.control.CheckBox.indeterminate.SET", "javafx.scene.control.CheckBox.indeterminate.DEFAULT"}, level = Level.FULL)
    public void checks(ControlType ctrl, boolean changeExpected) throws InterruptedException {
        Wrap<? extends Text> label = parent.lookup(Text.class, new ByID<Text>(CHECKBOX_LABEL_ID)).wrap();
        Wrap<? extends CheckBox> cb = parent.lookup(CheckBox.class, new ByText<CheckBox>(TWO_STATE_CB)).wrap();
        click(cb, ctrl);
        label.waitProperty(Wrap.TEXT_PROP_NAME, changeExpected ? SELECTED_LABEL_TEXT : Checks.NO_SELECTION);
        cb.waitProperty(TextControlWrap.SELECTED_PROP_NAME, changeExpected ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        click(cb, ctrl);
        label.waitProperty(Wrap.TEXT_PROP_NAME, changeExpected ? UNSELECTED_LABEL_TEXT : Checks.NO_SELECTION);
        cb.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);

        cb = parent.lookup(CheckBox.class, new ByText<CheckBox>(TRI_STATE_CB)).wrap();
        click(cb, ctrl);
        label.waitProperty(Wrap.TEXT_PROP_NAME, changeExpected ? UNDEFINED_LABEL_TEXT : Checks.NO_SELECTION);
        cb.waitProperty(TextControlWrap.SELECTED_PROP_NAME, changeExpected ? CheckBoxWrap.State.UNDEFINED : CheckBoxWrap.State.UNCHECKED);
        click(cb, ctrl);
        label.waitProperty(Wrap.TEXT_PROP_NAME, changeExpected ? SELECTED_LABEL_TEXT : Checks.NO_SELECTION);
        cb.waitProperty(TextControlWrap.SELECTED_PROP_NAME, changeExpected ? CheckBoxWrap.State.CHECKED : CheckBoxWrap.State.UNCHECKED);
        click(cb, ctrl);
        label.waitProperty(Wrap.TEXT_PROP_NAME, changeExpected ? UNSELECTED_LABEL_TEXT : Checks.NO_SELECTION);
        cb.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);

        if (ControlType.MOUSE == ctrl) {
            parent.lookup(CheckBox.class, new ByText<CheckBox>(DISABLED_CONTROL)).wrap().mouse().click();
            Thread.sleep(2000);
            assertEquals(UNSELECTED_LABEL_TEXT, label.getProperty(Wrap.TEXT_PROP_NAME));
            assertEquals(CheckBoxWrap.State.UNCHECKED, cb.getProperty(TextControlWrap.SELECTED_PROP_NAME));
        }
    }

    protected void click(final Wrap<? extends Node> wrap, ControlType ctrl) {
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
