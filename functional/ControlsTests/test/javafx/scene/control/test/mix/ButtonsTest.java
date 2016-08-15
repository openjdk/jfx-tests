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
import javafx.scene.control.test.Buttons;
import static javafx.scene.control.test.Buttons.ButtonsScene.*;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.text.Text;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
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
 * @author shura
 * @author cthulhu
 */
@RunWith(FilteredTestRunner.class)
public class ButtonsTest extends ControlsTestBase {

    public ButtonsTest() {
    }

    public static enum InputType {

        MOUSE, SPACE, ENTER
    }
    Wrap<? extends Scene> scene = null;
    Parent<Node> parent = null;
    Wrap clear = null;
    static boolean isRemote;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Buttons.main(null);
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
    @Covers(value = {"javafx.scene.control.Button.cancelButton.GET", "javafx.scene.control.Button.cancelButton.SET", "javafx.scene.control.Button.cancelButton.BIND", "javafx.scene.control.Button.cancelButton.DEFAULT"}, level = Level.FULL)
    public void cancelButtonTest() throws InterruptedException {
        Wrap<? extends Text> label = parent.lookup(Text.class, new ByID<Text>(BUTTON_LABEL_ID)).wrap();
        final Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(ENABLED_BTN)).wrap();

        assertFalse(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((Button) button.getControl()).isCancelButton());
            }
        }.dispatch(Root.ROOT.getEnvironment()));


        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                ((Button) button.getControl()).setCancelButton(true);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        scene.keyboard().pushKey(KeyboardButtons.ESCAPE);

        assertTrue(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((Button) button.getControl()).isCancelButton());
            }
        }.dispatch(Root.ROOT.getEnvironment()));

        label.waitProperty(Wrap.TEXT_PROP_NAME, BUTTON_PUSHED);

        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                ((Button) button.getControl()).setCancelButton(false);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        assertFalse(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((Button) button.getControl()).isCancelButton());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.Button.defaultButton.GET", "javafx.scene.control.Button.defaultButton.SET", "javafx.scene.control.Button.defaultButton.BIND", "javafx.scene.control.Button.defaultButton.DEFAULT"}, level = Level.FULL)
    public void defaultButtonTest() throws InterruptedException {
        Wrap<? extends Text> label = parent.lookup(Text.class, new ByID<Text>(BUTTON_LABEL_ID)).wrap();
        final Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(ENABLED_BTN)).wrap();

        assertFalse(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((Button) button.getControl()).isDefaultButton());
            }
        }.dispatch(Root.ROOT.getEnvironment()));

        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                ((Button) button.getControl()).setDefaultButton(true);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        scene.keyboard().pushKey(KeyboardButtons.ENTER);


        assertTrue(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((Button) button.getControl()).isDefaultButton());
            }
        }.dispatch(Root.ROOT.getEnvironment()));


        label.waitProperty(Wrap.TEXT_PROP_NAME, BUTTON_PUSHED);

        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                ((Button) button.getControl()).setDefaultButton(false);
            }
        }.dispatch(Root.ROOT.getEnvironment());

        assertFalse(new GetAction<Boolean>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(((Button) button.getControl()).isDefaultButton());
            }
        }.dispatch(Root.ROOT.getEnvironment()));
    }

    @Smoke
    @Test(timeout = 300000)
    public void bindingTestMouse() throws InterruptedException {
        binding(InputType.MOUSE);
    }

    @Smoke
    @Test(timeout = 300000)
    public void bindingTestKeyboard() throws InterruptedException {
        if (!isRemote) {
            binding(InputType.SPACE);
            if (!Utils.isMacOS()) {
                tearDown();
                setUp();
                binding(InputType.ENTER);
            }
        } else {
            System.out.println("Applet mode detected. Test skipped.");
        }
    }

    @Smoke
    @Covers(value = {"javafx.scene.control.Button.text.GET", "javafx.scene.control.Button.text.SET", "javafx.scene.control.Button.text.BIND", "javafx.scene.control.Button.text.DEFAULT"}, level = Level.FULL)
    public void binding(InputType ctrl) throws InterruptedException {
        String template = "pressed: ";
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(template)).wrap();
        for (int count = 1; count < 10; ++count) {
            click(button, ctrl);
            button.waitProperty(Wrap.TEXT_PROP_NAME, template + count);
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void buttonsTestMouse() throws InterruptedException {
        buttons(InputType.MOUSE);
    }

    @Smoke
    @Test(timeout = 300000)
    public void buttonsTestKeyboard() throws InterruptedException {
        if (!isRemote) {
            buttons(InputType.SPACE);
            if (!Utils.isMacOS()) {
                tearDown();
                setUp();
                buttons(InputType.ENTER);
            }
        } else {
            System.out.println("Applet mode detected. Test skipped.");
        }
    }

    public void buttons(InputType ctrl) throws InterruptedException {
        Wrap<? extends Text> label = parent.lookup(Text.class, new ByID<Text>(BUTTON_LABEL_ID)).wrap();
        Wrap<? extends Node> button = parent.lookup(Button.class, new ByText<Button>(ENABLED_BTN)).wrap();
        click(button, ctrl);
        label.waitProperty(Wrap.TEXT_PROP_NAME, (InputType.ENTER == ctrl) ? "button" : BUTTON_PUSHED);

        if (InputType.MOUSE == ctrl) {
            parent.lookup(Button.class, new ByText<Button>(DISABLED_CONTROL)).wrap().mouse().click();
            Thread.sleep(2000);
            assertEquals(0, parent.lookup(Text.class, new ByText<Text>(DISABLED_BUTTON_PUSHED)).size());
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
