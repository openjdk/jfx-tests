/*
 * Copyright (c) 2014, 2023 Oracle and/or its affiliates. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.test.MenuButtonApp;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.MenuButtonWrap;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class MenuButtonTest extends MenuTestBase {

    static StringMenuOwner<? extends MenuItem> menuButtonAsStringMenuOwner;
    static Parent<? extends MenuItem> menuButtonAsParent;
    static Wrap clearBtn = null;
    static Wrap resetBtn = null;
    static Wrap addPosBtn = null;
    static Wrap removePosBtn = null;
    static Wrap<? extends ChoiceBox> sideCB = null;
    static KeyboardButtons activationBtn = KeyboardButtons.SPACE;
    static KeyboardButtons secondaryActivationBtn = KeyboardButtons.ENTER;

    @BeforeClass
    public static void setUpClass() throws Exception {
        MenuButtonApp.main(null);
        setUpVars();
    }

    public static void setUpVars() throws Exception {
        scene = Root.ROOT.lookup().wrap();
        sceneAsParent = scene.as(Parent.class, Node.class);
        object = container = (MenuButtonWrap) sceneAsParent.lookup(new LookupCriteria<Node>() {
            public boolean check(Node cntrl) {
                return MenuButton.class.isAssignableFrom(cntrl.getClass());
            }
        }).wrap();
        contentPane = sceneAsParent.lookup(new ByID<Node>(MenuButtonApp.TEST_PANE_ID)).wrap();
        clearBtn = sceneAsParent.lookup(new ByText(MenuButtonApp.CLEAR_BTN_ID)).wrap();
        resetBtn = sceneAsParent.lookup(new ByText(MenuButtonApp.RESET_BTN_ID)).wrap();
        addPosBtn = sceneAsParent.lookup(new ByText(MenuButtonApp.ADD_SINGLE_AT_POS_BTN_ID)).wrap();
        removePosBtn = sceneAsParent.lookup(new ByText(MenuButtonApp.REMOVE_SINGLE_AT_POS_BTN_ID)).wrap();
        check = (Wrap<? extends Label>) sceneAsParent.lookup(new ByID(MenuButtonApp.LAST_SELECTED_ID)).wrap();
        sideCB = sceneAsParent.lookup(ChoiceBox.class).wrap();
        menuButtonAsStringMenuOwner = container.as(StringMenuOwner.class, Menu.class);
        menuButtonAsParent = container.as(Parent.class, MenuItem.class);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws InterruptedException {
        scene.mouse().click(1, new Point(1, 1));
        resetBtn.mouse().click();
        new Timeout("Item list delay", 200).sleep();
    }

    @After
    public void tearDown() {
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void initialStateTest() throws InterruptedException {
        ScreenshotUtils.checkScreenshot(getClass().getSimpleName() + "-initialState", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void emptyTest() throws InterruptedException {
        clearBtn.mouse().click();
        expand();
        ScreenshotUtils.checkScreenshot(getClass().getSimpleName() + "-empty", contentPane);
        container.mouse().click();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void apiTest() throws Throwable {
        hide(); // RT-12437
        show();
        checkShown(true);
        Thread.sleep(300);
        checkScreenshot(getClass().getSimpleName() + "-api-show", contentPane);
        hide();
        checkShown(false);
        checkScreenshot(getClass().getSimpleName() + "-api-hide", contentPane);
        throwScreenshotError();
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void addTest() throws InterruptedException {
        Thread.sleep(300);
        expand();
        ScreenshotUtils.checkScreenshot(getClass().getSimpleName() + "-add", contentPane);
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void insertTest() throws InterruptedException {
        addPosBtn.mouse().click();
        expand();
        ScreenshotUtils.checkScreenshot(getClass().getSimpleName() + "-insert", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void removeTest() throws InterruptedException {
        removePosBtn.mouse().click();
        expand();
        ScreenshotUtils.checkScreenshot(getClass().getSimpleName() + "-remove", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void dropTest() throws Throwable {
        expand();
        container.mouse().click();
        checkShown(false);
        checkScreenshot(getClass().getSimpleName() + "-drop-itself", contentPane);
        container.mouse().click();
        scene.mouse().click();
        checkShown(false);
        checkScreenshot(getClass().getSimpleName() + "-drop-outside", contentPane);
        throwScreenshotError();
    }

    @Test(timeout = 300000)
    public void mouseSelectionTest() throws InterruptedException {
        selectionCheck();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void mouseHoverTest() throws Throwable {
        hoverCycle();
        contentPane.mouse().move();
        hoverCycle();
    }

    //TODO@Smoke
    @Test(timeout = 300000)
    public void keyboardSelectionTest() throws InterruptedException {
        focus();
        keyboardSelectionCheck(KeyboardButtons.DOWN);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void keyboardHoverTest() throws Throwable {
        focus();
        keyboardHoverCycle(activationBtn, false);
        keyboardHoverCycle(activationBtn, true);
    }

    //TODO@Smoke
    @Test(timeout = 300000)
    public void keyboardDropTest() throws Throwable {
        focus();
        expandByKeyboard(activationBtn);
        if (secondaryActivationBtn != null) {
            expandByKeyboard(secondaryActivationBtn);
        }
        Map<Side, KeyboardButtons> sideMap = new HashMap<Side, KeyboardButtons>() {
            {
                put(Side.BOTTOM, KeyboardButtons.DOWN);
                put(Side.LEFT, KeyboardButtons.LEFT);
                put(Side.RIGHT, KeyboardButtons.RIGHT);
                put(Side.TOP, KeyboardButtons.UP);
            }
        };
        for (Entry<Side, KeyboardButtons> entry : sideMap.entrySet()) {
            sideCB.as(Selectable.class).selector().select(entry.getKey());
            expandByKeyboard(entry.getValue());
        }
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void sideTest() throws Throwable {
        for (Side side : Side.values()) {
            sideCB.as(Selectable.class).selector().select(side);
            expand();
            checkScreenshot(getClass().getSimpleName() + "-side-" + side.name(), contentPane);
        }
        throwScreenshotError();
    }

    protected void selectionCheck() throws InterruptedException {
        for (int i = 0; i < MenuButtonApp.MENU_ITEMS_NUM; i++) {
            final int index = i;
            menuButtonAsParent.lookup().wrap(i).mouse().click();
            container.waitState(new State() {
                public Object reached() {
                    return check.getProperty(String.class, Wrap.TEXT_PROP_NAME).contentEquals(MenuButtonApp.MENU_ITEM + index) ? Boolean.TRUE : null;
                }
            });
        }
    }

    protected void expand() {
        if (!isShown()) {
            Parent parentAsParent = container.as(Parent.class, Node.class);
            parentAsParent.lookup(Node.class, new ByStyleClass<Node>("arrow-button")).wrap().mouse().click();
        }
        checkShown(true);
    }

    protected void show() {
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                ((MenuButton) container.getControl()).show();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void hide() {
        new GetAction() {
            @Override
            public void run(Object... parameters) {
                ((MenuButton) container.getControl()).hide();
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected boolean isShown() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(((MenuButton) container.getControl()).isShowing());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void checkShown(final boolean shown) {
        container.waitState(new State() {
            public Object reached() {
                return ((((MenuButton) container.getControl()).isShowing() == shown) ? Boolean.TRUE : null);
            }
        });
    }
}
