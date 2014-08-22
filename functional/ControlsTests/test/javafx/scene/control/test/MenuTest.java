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
package javafx.scene.control.test;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.MenuBarWrap;
import org.jemmy.fx.control.MenuWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class MenuTest extends MenuTestBase {

    static Parent menuBarAsParent;
    static Parent<? extends MenuItem> menuAsParent;
    static MenuBarWrap<? extends MenuBar> menuBar;
    static MenuWrap<? extends Menu> menu = null;
    static Wrap clearBtn = null;
    static Wrap resetBtn = null;
    static Wrap addPosBtn = null;
    static Wrap removePosBtn = null;
    static Wrap<? extends CheckBox> hidden = null;
    static Wrap<? extends CheckBox> hiding = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        MenuApp.main(null);
        scene = Root.ROOT.lookup().wrap();
        sceneAsParent = scene.as(Parent.class, Node.class);
        menuBar = (MenuBarWrap) sceneAsParent.lookup(MenuBar.class).wrap();
        menu = (MenuWrap<? extends Menu>) menuBar.as(Parent.class, Menu.class).lookup().wrap();
        menuAsParent = menu.as(Parent.class, MenuItem.class);
        container = menuBar;
        object = menu;
        contentPane = sceneAsParent.lookup(new ByID<Node>(MenuApp.TEST_PANE_ID)).wrap();
        clearBtn = sceneAsParent.lookup(new ByText(MenuApp.CLEAR_BTN_ID)).wrap();
        resetBtn = sceneAsParent.lookup(new ByText(MenuApp.RESET_BTN_ID)).wrap();
        addPosBtn = sceneAsParent.lookup(new ByText(MenuApp.ADD_SINGLE_AT_POS_BTN_ID)).wrap();
        removePosBtn = sceneAsParent.lookup(new ByText(MenuApp.REMOVE_SINGLE_AT_POS_BTN_ID)).wrap();
        check = (Wrap<? extends Label>) sceneAsParent.lookup(new ByID(MenuApp.LAST_SELECTED_ID)).wrap();
        hidden = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(MenuApp.SHOWN_CHECK_ID)).wrap();
        hiding = sceneAsParent.lookup(CheckBox.class, new ByID<CheckBox>(MenuApp.SHOWING_CHECK_ID)).wrap();
    }

    @Before
    public void setUp() {
        scene.mouse().click();
        resetBtn.mouse().click();
        new Timeout("Item list delay", 200).sleep();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void initialStateTest() throws InterruptedException {
        ScreenshotUtils.checkScreenshot("MenuTest-initialState", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void emptyTest() throws InterruptedException {
        clearBtn.mouse().click();
        menuBar.mouse().click();
        ScreenshotUtils.checkScreenshot("MenuTest-empty", contentPane);
        menuBar.mouse().click();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.MenuItem.onHidden.GET", "javafx.scene.control.MenuItem.onHidden.SET", "javafx.scene.control.MenuItem.onHidden.BIND", "javafx.scene.control.MenuItem.onHiding.GET", "javafx.scene.control.MenuItem.onHiding.SET", "javafx.scene.control.MenuItem.onHiding.BIND"}, level = Level.FULL)
    public void apiTest() throws InterruptedException {
        Thread.sleep(1300);
        show();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        assertTrue(isShown());
        Thread.sleep(300);
        ScreenshotUtils.checkScreenshot("MenuTest-api-show", contentPane);
        hide();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        assertFalse(isShown());
        ScreenshotUtils.checkScreenshot("MenuTest-api-hide", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void addTest() throws InterruptedException {
        Thread.sleep(300);
        expand();
        ScreenshotUtils.checkScreenshot("MenuTest-add", contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void insertTest() throws InterruptedException {
        addPosBtn.mouse().click();
        expand();
        ScreenshotUtils.checkScreenshot("MenuTest-insert", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void removeTest() throws InterruptedException {
        removePosBtn.mouse().click();
        expand();
        ScreenshotUtils.checkScreenshot("MenuTest-remove", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.MenuItem.onHidden.GET", "javafx.scene.control.MenuItem.onHidden.SET", "javafx.scene.control.MenuItem.onHidden.BIND", "javafx.scene.control.MenuItem.onHiding.GET", "javafx.scene.control.MenuItem.onHiding.SET", "javafx.scene.control.MenuItem.onHiding.BIND"}, level = Level.FULL)
    public void dropTest() throws InterruptedException {
        expand();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        assertTrue(isShown());
        menu.mouse().click();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        assertFalse(isShown());
        ScreenshotUtils.checkScreenshot("MenuTest-drop-itself", contentPane);
        menu.mouse().click();
        scene.mouse().click();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        assertFalse(isShown());
        ScreenshotUtils.checkScreenshot("MenuTest-drop-outside", contentPane);
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseSelectionTest() throws InterruptedException {
        selectionCheck();
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardSelectionTest() throws InterruptedException {
        focus();
        keyboardSelectionCheck(KeyboardButtons.DOWN);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void mouseHoverTest() throws Throwable {
        expand();
        hoverCycle();
        scene.mouse().move();
        hoverCycle();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void keyboardHoverTest() throws Throwable {
        focus();
        keyboardHoverCycle(KeyboardButtons.DOWN, false);
        keyboardHoverCycle(KeyboardButtons.DOWN, true);
    }

    @Smoke
    @Test(timeout = 300000)
    public void handlersSettersTest() throws Throwable {
        Wrap error = sceneAsParent.lookup(Label.class, new ByID<Label>(MenuApp.ERROR_ID)).wrap();
        assertEquals("", error.getProperty(Wrap.TEXT_PROP_NAME));
    }

    protected void selectionCheck() throws InterruptedException {
        for (int i = 0; i < menuAsParent.lookup().size(); i++) {
            expand();
            final Wrap<? extends MenuItem> menu_item = menuAsParent.lookup().wrap(i);
            menu_item.mouse().click();
            check.waitState(new State() {
                public Object reached() {
                    return (check.getProperty(String.class, Wrap.TEXT_PROP_NAME).contentEquals(menu_item.getControl().getText()) ? Boolean.TRUE : null);
                }
            });
        }
    }

    protected void expand() {
        if (!isShown()) {
            menu.mouse().click();
        }
        checkShown(true);
    }

    protected void checkShown(final boolean shown) {
        menu.waitState(new State() {
            public Object reached() {
                return ((new GetAction<Boolean>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(menu.getControl().isShowing());
                    }
                }.dispatch(Root.ROOT.getEnvironment()) == shown) ? Boolean.TRUE : null);
            }
        });
    }

    protected void show() {
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                menu.getControl().show();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void hide() {
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                menu.getControl().hide();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected boolean isShown() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(menu.getControl().isShowing());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}
