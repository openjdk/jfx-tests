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
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneWrap;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.fx.control.TextControlWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class PopupMenuTest extends ControlsTestBase {

    public PopupMenuTest() {
    }
    static Wrap<? extends Scene> scene = null;
    static Parent<Node> parent = null;
    static Wrap<? extends Node> menuOwner = null;
    static Parent menuBarAsParent;
    static Wrap contentPane = null;
    static Wrap clearBtn = null;
    static Wrap resetBtn = null;
    static Wrap addPosBtn = null;
    static Wrap removePosBtn = null;
    static Wrap<? extends Label> lastPressedLbl = null;
    static Wrap<? extends Label> lastPressedLbl2 = null;
    static Wrap<? extends CheckBox> hidden = null;
    static Wrap<? extends CheckBox> hiding = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ContextMenuApp.main(null);
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        contentPane = parent.lookup(new ByID<Node>(ContextMenuApp.TEST_PANE_ID)).wrap();
        clearBtn = parent.lookup(new ByText(ContextMenuApp.CLEAR_BTN_ID)).wrap();
        resetBtn = parent.lookup(new ByText(ContextMenuApp.RESET_BTN_ID)).wrap();
        addPosBtn = parent.lookup(new ByText(ContextMenuApp.ADD_SINGLE_AT_POS_BTN_ID)).wrap();
        removePosBtn = parent.lookup(new ByText(ContextMenuApp.REMOVE_SINGLE_AT_POS_BTN_ID)).wrap();
        lastPressedLbl = (Wrap<? extends Label>) parent.lookup(new ByID(ContextMenuApp.LAST_SELECTED_ID)).wrap();
        lastPressedLbl2 = (Wrap<? extends Label>) parent.lookup(new ByID(ContextMenuApp.LAST_SELECTED_ACTION_ID)).wrap();
        hidden = parent.lookup(CheckBox.class, new ByID<CheckBox>(ContextMenuApp.SHOWN_CHECK_ID)).wrap();
        hiding = parent.lookup(CheckBox.class, new ByID<CheckBox>(ContextMenuApp.SHOWING_CHECK_ID)).wrap();
        menuOwner = parent.lookup(new ByID(ContextMenuApp.MENU_ID)).wrap();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        scene.mouse().click();
        resetBtn.mouse().click();
    }

    @After
    public void tearDown() {
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void emptyTest() throws InterruptedException {
        clearBtn.mouse().click();
        show();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        ScreenshotUtils.checkScreenshot("PopupMenuTest-empty", contentPane);
        hide();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
    }

    @Smoke
    @Test(timeout = 300000)
    public void apiTest() throws Throwable {
        show();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        assertTrue(isShown());
        Thread.sleep(300);
        checkScreenshot("PopupMenuTest-api-show", contentPane);
        hide();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        assertFalse(isShown());
        checkScreenshot("PopupMenuTest-api-hide", contentPane);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void addTest() throws InterruptedException {
        Thread.sleep(300);
        show();
        ScreenshotUtils.checkScreenshot("PopupMenuTest-add", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void insertTest() throws InterruptedException {
        addPosBtn.mouse().click();
        show();
        ScreenshotUtils.checkScreenshot("PopupMenuTest-insert", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void removeTest() throws InterruptedException {
        removePosBtn.mouse().click();
        show();
        ScreenshotUtils.checkScreenshot("PopupMenuTest-remove", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void dropTest() throws InterruptedException {
        show();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.CHECKED);
        assertTrue(isShown());
        scene.mouse().click();
        hidden.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        hiding.waitProperty(TextControlWrap.SELECTED_PROP_NAME, CheckBoxWrap.State.UNCHECKED);
        assertFalse(isShown());
        ScreenshotUtils.checkScreenshot("PopupMenuTest-drop", contentPane);
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseSelectionTest() throws InterruptedException {
        selectionCycle();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void mouseHoverTest() throws Throwable {
        selectionCycle(); // regression check
        show();
        Thread.sleep(300);
        hoverCycle(getPopupSceneWrap());
        contentPane.mouse().move();
        hoverCycle(getPopupSceneWrap());
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void keyboardHoverTest() throws InterruptedException {
        selectionCycle(); // regression check
        show();
        for (int i = 0; i < ContextMenuApp.BUTONS_NUM; i++) {
            menuOwner.keyboard().pushKey(KeyboardButtons.DOWN);
            Thread.sleep(500);
            checkScreenshot("PopupMenuTest-hoverTest-" + i, contentPane);
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void onActionTest() throws InterruptedException {
        Wrap error = parent.lookup(Label.class, new ByID<Label>(ContextMenuApp.ERROR_ID)).wrap();
        assertEquals("", error.getProperty(Wrap.TEXT_PROP_NAME));
    }

    protected void selectionCycle() throws InterruptedException {
        for (int i = 0; i < ContextMenuApp.BUTONS_NUM; i++) {
            show();
            lookupByMenuItem(getPopupSceneWrap(), ContextMenuApp.menu.getItems().get(i)).mouse().move(); // TODO: second iteration does not work without move(). why!!?
            lookupByMenuItem(getPopupSceneWrap(), ContextMenuApp.menu.getItems().get(i)).mouse().click();
            lastPressedLbl.waitState(new State<String>() {
                public String reached() {
                    return lastPressedLbl.getProperty(String.class, Wrap.TEXT_PROP_NAME);
                }
            }, ContextMenuApp.MENU_ITEM + i);
        }
    }

    protected void hoverCycle(Wrap<? extends Scene> popup_scene_wrap) throws Throwable {
        for (int i = 0; i < ContextMenuApp.BUTONS_NUM; i++) {
            lookupByMenuItem(popup_scene_wrap, ContextMenuApp.menu.getItems().get(i)).mouse().move();
            Thread.sleep(1000);
            checkScreenshot("PopupMenuTest-hoverTest-" + i, contentPane);
        }
        throwScreenshotError();
    }

    protected Wrap lookupByMenuItem(Wrap<? extends Scene> scene_wrap, final MenuItem menu) {
        final Wrap<Node> item = scene_wrap.as(Parent.class, Node.class).lookup(Node.class, new LookupCriteria<Node>() {
            public boolean check(Node node) {
                if (node.getProperties().get(MenuItem.class) == menu) {
                    return true;
                }
                return false;
            }
        }).wrap();
        return item;
    }

    public static Wrap<? extends Scene> getPopupSceneWrap() {

        Wrap<? extends Scene> popup_scene_wrap = Root.ROOT.lookup(new LookupCriteria<Scene>() {
            public boolean check(Scene scene) {
                if (!(scene.getWindow() instanceof ContextMenu)) {
                    return false;
                }
                Wrap<Scene> scene_wrap = new SceneWrap(Root.ROOT.getEnvironment(), scene);
                Parent<Node> parent = scene_wrap.as(Parent.class, Node.class);
                try {
                    parent.lookup(Node.class, new LookupCriteria<Node>() {
                        public boolean check(Node node) {
                            if (node.getProperties().containsKey(Menu.class)) { // TODO: stub, shuuld be node.getProperties().get(Menu.class) == menu
                                return true;
                            }
                            return false;
                        }
                    }).get();
                } catch (Throwable th) {
                    return false;
                }
                return true;
            }
        }).wrap();

        return popup_scene_wrap;
    }

    protected void show() {
        show(menuOwner.getControl(), Side.BOTTOM, 0, 0);
    }

    protected void show(final Node node, final Side side, final double x, final double y) {
        menuOwner.mouse().click();
    }

    protected void hide() {
        contentPane.mouse().click();
    }

    protected boolean isShown() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(ContextMenuApp.menu.isShowing());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}