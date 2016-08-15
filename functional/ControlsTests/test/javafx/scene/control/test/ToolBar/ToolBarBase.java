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
package javafx.scene.control.test.ToolBar;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import org.jemmy.env.Timeout;
import org.jemmy.timing.State;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import javafx.scene.control.ToolBar;
import javafx.geometry.Orientation;
import javafx.scene.control.test.ControlsTestBase;
import org.jemmy.fx.control.ToolBarWrap;
import javafx.scene.control.test.ToolBarApp;
import javafx.scene.control.Label;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Selectable;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.jemmy.action.GetAction;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.screenshots.ScreenshotUtils;
import static test.javaclient.shared.TestUtil.isEmbedded;

@RunWith(FilteredTestRunner.class)
public class ToolBarBase extends ControlsTestBase {

    public ToolBarBase() {
    }
    static Wrap<? extends Scene> scene = null;
    static Parent<Node> parent = null;
    static ToolBarWrap<? extends ToolBar> toolBar = null;
    static Selectable<Object> menuAsSelectable;
    static Wrap contentPane = null;
    static Wrap clearBtn = null;
    static Wrap resetBtn = null;
    static Wrap shrinkBtn = null;
    static Wrap verticalBtn = null;
    static Wrap<? extends Label> lastPressedLbl = null;
    static Selectable<Node> toolBarAsSelectable;
    static long SLEEP = isEmbedded() ? 5000 : 500;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ToolBarApp.main(null);

        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        toolBar = (ToolBarWrap<? extends ToolBar>) parent.lookup(ToolBar.class).wrap();
        contentPane = parent.lookup(new ByID<Node>(ToolBarApp.TEST_PANE_ID)).wrap();
        toolBarAsSelectable = toolBar.as(Selectable.class, Node.class);
        clearBtn = parent.lookup(new ByText(ToolBarApp.CLEAR_BTN_ID)).wrap();
        resetBtn = parent.lookup(new ByText(ToolBarApp.RESET_BTN_ID)).wrap();
        shrinkBtn = parent.lookup(new ByText(ToolBarApp.SHRINK_BTN_ID)).wrap();
        verticalBtn = parent.lookup(new ByText(ToolBarApp.VERTICAL_BTN_ID)).wrap();
        lastPressedLbl = (Wrap<? extends Label>) parent.lookup(new ByID(ToolBarApp.LAST_PRESSED_LBL_ID)).wrap();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        resetBtn.mouse().click();
        new Timeout("Item list delay", 20000).sleep();
    }

    @After
    public void tearDown() {
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void initialStateTest() throws InterruptedException {
        ScreenshotUtils.checkScreenshot(getFullName("InitialState"), contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectionTest() throws InterruptedException {
        Thread.sleep(SLEEP);
        selectionCheck();
        ScreenshotUtils.checkScreenshot(getFullName("Selection"), contentPane);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardSelectionTest() throws InterruptedException {
        keyboardSelectionCycle(KeyboardButtons.SPACE);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void clearTest() throws InterruptedException {
        clearBtn.mouse().click();
        Thread.sleep(SLEEP);
        ScreenshotUtils.checkScreenshot(getFullName("Clear"), contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void addItemsTest() throws InterruptedException {
        ScreenshotUtils.checkScreenshot(getFullName("Add"), contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void shrinkTest() throws InterruptedException {
        shrinkBtn.mouse().click();
        Thread.sleep(SLEEP);
        selectionCheck();
        ScreenshotUtils.checkScreenshot(getFullName("Shrink"), contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void vmodeSwitchTest() throws InterruptedException {
        shrinkBtn.mouse().click();
        verticalBtn.mouse().click();
        Thread.sleep(SLEEP);
        ScreenshotUtils.checkScreenshot(getFullName("ModeSwitch"), contentPane);
    }

    public void keyboardSelectionCycle(KeyboardButton btn) throws InterruptedException {
        final Node control = toolBarAsSelectable.getStates().get(0);
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                control.requestFocus();
            }
        }.dispatch(Root.ROOT.getEnvironment());
        for (int i = 0; i < ToolBarApp.BUTONS_NUM; i++) {
            toolBar.keyboard().pushKey(btn);
            checkLastPressed(i);
            Orientation orientation = new GetAction<Orientation>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(toolBar.getControl().getOrientation());
                }
            }.dispatch(Root.ROOT.getEnvironment());
            if (orientation == Orientation.HORIZONTAL) {
                toolBar.keyboard().pushKey(KeyboardButtons.RIGHT);
            } else {
                toolBar.keyboard().pushKey(KeyboardButtons.DOWN);
            }
        }
    }

    protected void checkLastPressed(int i) {
        lastPressedLbl.waitState(new State<Integer>() {
            public Integer reached() {
                final String text = lastPressedLbl.getControl().getText();
                if (text.isEmpty()) {
                    return -1;
                }
                return Integer.valueOf(text).intValue();
            }
        }, i);
    }

    protected String getFullName(String name) {
        return "ToolBarTest-" + toolBar.getControl().getOrientation().name().toLowerCase() + name;
    }

    public void selectionCheck() throws InterruptedException {
        for (int i = 0; i < ToolBarApp.BUTONS_NUM; i++) {
            toolBarAsSelectable.selector().select(toolBarAsSelectable.getStates().get(i));
            checkLastPressed(i);
        }
    }
}