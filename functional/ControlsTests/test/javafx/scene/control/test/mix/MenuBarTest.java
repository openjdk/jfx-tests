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
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.test.MenuBarApp;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.input.StringMenuOwner;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class MenuBarTest extends MenuTestBase {

    static Wrap<? extends MenuBar> menuBar = null;
    static StringMenuOwner<? extends Menu> menuAsStringMenuOwner;
    static Parent<? extends Menu> menuAsParent;
    static Wrap clearBtn = null;
    static Wrap resetBtn = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        MenuBarApp.main(null);
        scene = Root.ROOT.lookup().wrap();
        sceneAsParent = scene.as(Parent.class, Node.class);
        menuBar = sceneAsParent.lookup(MenuBar.class).wrap();
        container = menuBar;
        object = menuBar;
        menuAsStringMenuOwner = menuBar.as(StringMenuOwner.class, Menu.class);
        menuAsParent = menuBar.as(Parent.class, Menu.class);
        contentPane = sceneAsParent.lookup(new ByID<Node>(MenuBarApp.TEST_PANE_ID)).wrap();
        clearBtn = sceneAsParent.lookup(new ByText(MenuBarApp.CLEAR_BTN_ID)).wrap();
        resetBtn = sceneAsParent.lookup(new ByText(MenuBarApp.RESET_BTN_ID)).wrap();
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
        ScreenshotUtils.checkScreenshot("MenuBarTest-initialState", contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void clearTest() throws InterruptedException {
        menuAsStringMenuOwner.select(MenuBarApp.MENU_STR + "0");
        clearBtn.mouse().click();
        Thread.sleep(300);
        ScreenshotUtils.checkScreenshot("MenuBarTest-clearTest", contentPane);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void addItemsTest() throws InterruptedException {
        clearBtn.mouse().click();
        resetBtn.mouse().click();
        Thread.sleep(300);
        ScreenshotUtils.checkScreenshot("MenuBarTest-addTest", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectionTest() throws Throwable {
        Lookup<MenuItem> lookup = upperLevelLookup();
        for (int i = 0; i < lookup.size(); i++) {
            Wrap<? extends MenuItem> menu = lookup.wrap(i);
            menu.mouse().click();
            Thread.sleep(300);
            checkScreenshot("MenuBarTest-selection_select-" + i, contentPane);
            contentPane.mouse().click();
        }
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void deselectionTest() throws InterruptedException {
        menuAsStringMenuOwner.push(MenuBarApp.MENU_STR + "0");
        contentPane.mouse().move();
        contentPane.mouse().click();
        Thread.sleep(300);
        ScreenshotUtils.checkScreenshot("MenuBarTest-selection_deselect", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void mouseHoverTest() throws Throwable {
        setMouseSmoothness(1);
        hoverCycle();
        contentPane.mouse().move();
        hoverCycle();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void keyboardHoverTest() throws Throwable {
        menuAsStringMenuOwner.push(MenuBarApp.MENU_STR + "0");
        keyboardHoverCycle();
        keyboardHoverCycle();
    }

    @Smoke
    @Test(timeout = 300000)
    public void requestingFocusTest() throws Throwable {
        if (Utils.isMacOS()) {
            checkFocus(KeyboardButtons.F10, KeyboardModifiers.CTRL_DOWN_MASK);
        } else {
            checkFocus(KeyboardButtons.F10);
        }

        checkFocus(KeyboardButtons.ALT);
    }

    @Smoke
    @Test(timeout = 300000)
    public void rt18356Test() throws InterruptedException {
        menuAsStringMenuOwner.push(MenuBarApp.MENU_STR + "0");
        menuBar.keyboard().pushKey(KeyboardButtons.RIGHT);
        menuAsStringMenuOwner.push(MenuBarApp.MENU_STR + "0");
        menuBar.keyboard().pushKey(KeyboardButtons.RIGHT);
        menuBar.waitState(new State<Integer>() {
            public Integer reached() {
                return getOpenedMenu();
            }
        }, 1);
    }

    /**
     * Adds menu items to the menu bar in reverse order,
     * sorts them and checks that rendering works correctly.
     */
    @Smoke
    @Test(timeout = 30000)
    public void renderingAfterSortingTest() {

        StringConverter<Menu> conv = new StringConverter<Menu>() {
            @Override public String toString(Menu t) { return t.getText(); }

            @Override public Menu fromString(String s) { return new Menu(s); }
        };

        final Comparator<Menu> cmp = new Comparator<Menu>() {
            public int compare(Menu o1, Menu o2) {
                return o1.getText().compareTo(o2.getText());
            }
        };

        final int COUNT = 3;

        SortValidator<Menu, MenuButton> sv = new SortValidator<Menu, MenuButton>(COUNT, conv, cmp) {
            @Override
            protected void setControlData(final ObservableList<Menu> ls) {
                new GetAction<Object>() {

                    @Override
                    public void run(Object... parameters) throws Exception {
                         menuBar.getControl().getMenus().setAll(ls);
                    }
                }.dispatch(menuBar.getEnvironment());
            }

            @Override
            protected Lookup<? extends MenuButton> getCellsLookup() {
                return sceneAsParent.lookup(MenuButton.class);
            }

            @Override
            protected String getTextFromCell(MenuButton cell) {
                return cell.getText();
            }

            @Override
            protected void sort() {
                new GetAction<Object>() {

                    @Override
                    public void run(Object... parameters) throws Exception {
                        FXCollections.sort(menuBar.getControl().getMenus(), cmp);
                    }
                }.dispatch(menuBar.getEnvironment());
            }
        };
        sv.setOrientation(Orientation.HORIZONTAL);

        boolean res = sv.check();
        assertTrue(sv.getFailureReason(), res);

    }

    protected int getOpenedMenu() {
        return (new GetAction<Integer>() {
            @Override
            public void run(Object... parameters) throws Exception {
                ObservableList<Menu> menus = menuBar.getControl().getMenus();
                for (int i = 0; i < menus.size(); i++) {
                    if (menus.get(i).isShowing()) {
                        setResult(i);
                        return;
                    }
                }
                setResult(-1);
            }
        }).dispatch(Root.ROOT.getEnvironment());
    }

    protected void checkFocus(KeyboardButtons key, KeyboardModifiers... km) throws Throwable {
        resetBtn.mouse().click();
        contentPane.keyboard().pushKey(key, km);
        contentPane.waitState(new State() {
            public Object reached() {
                return isFocused() ? Boolean.TRUE : null;
            }
        });
    }

    public void keyboardHoverCycle() throws Throwable {
        Lookup<MenuItem> lookup = upperLevelLookup();
        for (int i = 0; i < lookup.size(); i++) {
            Thread.sleep(300);
            checkScreenshot("MenuBarTest-keyboard-hover-" + i, contentPane);
            menuBar.keyboard().pushKey(KeyboardButtons.RIGHT);
        }
        throwScreenshotError();
    }

    @Override
    protected boolean isShown() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void checkShown(boolean shown) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}