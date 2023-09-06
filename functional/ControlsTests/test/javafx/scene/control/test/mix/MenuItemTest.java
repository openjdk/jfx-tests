/*
 * Copyright (c) 2014, 2023, Oracle and/or its affiliates. All rights reserved.
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

import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.test.ControlsTestBase;
import javafx.scene.control.test.MenuItemApp;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.CheckBoxWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.MenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.interfaces.Selector;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.TestUtil;
import test.javaclient.shared.screenshots.ScreenshotUtils;

@RunWith(FilteredTestRunner.class)
public class MenuItemTest extends ControlsTestBase {

    static Wrap<? extends Scene> scene = null;
    static Parent<Node> sceneAsParent = null;
    static Wrap<? extends MenuBar> menuBar = null;
    static Parent<Menu> menuBarAsParent;
    static Lookup<Menu> menuBarLookup;
    static MenuOwner<Menu> menuAsMenuOwner;
    static Wrap contentPane = null;
    static Wrap clearLastSelectedBtn = null;
    static Wrap clearLastEventBtn = null;
    static Wrap<? extends CheckBox> disableBtn = null;
    static Wrap<? extends CheckBox> hideBtn = null;
    static Wrap<? extends CheckBox> eventBtn = null;
    static Wrap lastSelected = null;
    static Wrap lastEvent = null;
    static int defaultSmoothness = getMouseSmoothness();

    @BeforeClass
    public static void setUpClass() throws Exception {
        MenuItemApp.main(null);
        scene = Root.ROOT.lookup().wrap();
        sceneAsParent = scene.as(Parent.class, Node.class);
        menuBar = sceneAsParent.lookup(MenuBar.class).wrap();
        menuBarAsParent = menuBar.as(Parent.class, Menu.class);
        menuBarLookup = menuBarAsParent.lookup(Menu.class, new LookupCriteria<Menu>() {
            public boolean check(Menu control) {
                return control.getParentMenu() == null;
            }
        });
        menuAsMenuOwner = menuBar.as(MenuOwner.class, Menu.class);
        contentPane = sceneAsParent.lookup(new ByID<Node>(MenuItemApp.TEST_PANE_ID)).wrap();
        clearLastSelectedBtn = sceneAsParent.lookup(new ByText(MenuItemApp.LAST_PRESSED_CLEAR_BTN_ID)).wrap();
        clearLastEventBtn = sceneAsParent.lookup(new ByText(MenuItemApp.LAST_EVENT_CLEAR_BTN_ID)).wrap();
        disableBtn = sceneAsParent.lookup(CheckBox.class, new ByText(MenuItemApp.DISABLE_CB_ID)).wrap();
        eventBtn = sceneAsParent.lookup(CheckBox.class, new ByText(MenuItemApp.EVENT_CB_ID)).wrap();
        hideBtn = sceneAsParent.lookup(CheckBox.class, new ByText(MenuItemApp.HIDE_CB_ID)).wrap();
        lastSelected = sceneAsParent.lookup(new ByID(MenuItemApp.LAST_PRESSED_ID)).wrap();
        lastEvent = sceneAsParent.lookup(new ByID(MenuItemApp.LAST_EVENT_ID)).wrap();
    }

    @Before
    public void setUp() throws Exception {
        scene.mouse().click();
        clearLastSelectedBtn.mouse().click();
    }

    @After
    public void tearDown() {
        setMouseSmoothness(defaultSmoothness);
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void constructorsTest() throws InterruptedException {
        menuBarLookup.lookup(new MenuByText(MenuItemApp.CONSTRUCTORS_ID)).wrap().mouse().click();
        ScreenshotUtils.checkScreenshot("MenuItemTest-constructors", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void selectionTest() throws Throwable {
        selectionCycle(true, false);
        menuAsMenuOwner.menu().push(new MenuByText(MenuItemApp.MIXED_ID));
        ScreenshotUtils.checkScreenshot("MenuItemTest-selection", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.MenuItem.disable.GET", "javafx.scene.control.MenuItem.disable.SET", "javafx.scene.control.MenuItem.disable.BIND"}, level = Level.FULL)
    public void disableTest() throws Throwable {
        Selector disable_selector = disableBtn.as(Selectable.class).selector();
        disable_selector.select(CheckBoxWrap.State.CHECKED);
        try {
            selectionCycle(false, false);
            ScreenshotUtils.checkScreenshot("MenuItemTest-disable", contentPane);
        } finally {
            //In case the menu still open
            scene.keyboard().pushKey(KeyboardButtons.ESCAPE);
            disable_selector.select(CheckBoxWrap.State.UNCHECKED);
        }
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void eventTest() throws Throwable {
        Selector event_selector = eventBtn.as(Selectable.class).selector();
        event_selector.select(CheckBoxWrap.State.CHECKED);
        try {
            selectionCycle(true, true);
            event_selector.select(CheckBoxWrap.State.UNCHECKED);
            selectionCycle(true, false);
        } finally {
            event_selector.select(CheckBoxWrap.State.UNCHECKED);
        }
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.MenuItem.visible.GET", "javafx.scene.control.MenuItem.visible.SET", "javafx.scene.control.MenuItem.visible.BIND"}, level = Level.FULL)
    public void visibleTest() throws Throwable {
        invisibleCheckCycle(MenuItemApp.INVISIBLE_ID, "");
        invisibleCheckCycle(MenuItemApp.ALL_INVISIBLE_ID, "-all");
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void simpleTest() throws Throwable {
        checkCycle(MenuItemApp.SIMPLE_ID);
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void radioTest() throws Throwable {
        checkCycle(MenuItemApp.RADIO_ID);
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void checkTest() throws Throwable {
        checkCycle(MenuItemApp.CHECK_ID);
    }

    @ScreenshotCheck
    //TODO@Smoke
    @Test(timeout = 300000)
    public void nodeTest() throws Throwable {
        checkCycle(MenuItemApp.NODE_ID);
    }

    //TODO@Smoke
    @Test(timeout = 300000)
    public void shortcutsTest() throws InterruptedException {
        menuBarAsParent.lookup(new MenuByText(MenuItemApp.MIXED_ID)).wrap().mouse().click();
        menuBar.keyboard().pushKey(KeyboardButtons.A);
        lastSelected.waitProperty(Wrap.TEXT_PROP_NAME, MenuItemApp.MENU_ITEM_GRAPHICS_ID);

        menuBarAsParent.lookup(new MenuByText(MenuItemApp.MIXED_ID)).wrap().mouse().click();
        menuBar.keyboard().pushKey(KeyboardButtons.B);
        lastSelected.waitProperty(Wrap.TEXT_PROP_NAME, MenuItemApp.CHECK_MENU_ITEM_GRAPHICS_ID);

        menuBarAsParent.lookup(new MenuByText(MenuItemApp.MIXED_ID)).wrap().mouse().click();
        menuBar.keyboard().pushKey(KeyboardButtons.C);
        lastSelected.waitProperty(Wrap.TEXT_PROP_NAME, MenuItemApp.RADIO_MENU_ITEM_GRAPHICS_ID);

        menuBarAsParent.lookup(new MenuByText(MenuItemApp.MIXED_ID)).wrap().mouse().click();
        menuBar.keyboard().pushKey(KeyboardButtons.D);
        lastSelected.waitProperty(Wrap.TEXT_PROP_NAME, MenuItemApp.NODE_MENU_ITEM_BOOL_ID);
    }

    //TODO@Smoke
    @Test(timeout = 300000)
    public void radioGroupTest() throws InterruptedException {
        Wrap<? extends Menu> menu = menuBarAsParent.lookup(new MenuByText(MenuItemApp.RADIO_ID)).wrap();
        Parent<MenuItem> menu_as_parent = menu.as(Parent.class, MenuItem.class);
        for (int i = 0; i < menu_as_parent.lookup().size(); i++) {
            Wrap<? extends MenuItem> item_wrap = menu_as_parent.lookup().wrap(i);
            MenuItem item = item_wrap.getControl();
            expand(menu, true);
            item_wrap.mouse().click();
            for (int j = 0; j < menu_as_parent.lookup().size(); j++) {
                Wrap<? extends MenuItem> another_item_wrap = menu_as_parent.lookup().wrap(i);
                final MenuItem another_item = another_item_wrap.getControl();
                expand(menu, true);
                item_wrap.mouse().click();
                if (!another_item.equals(item)) {
                    assertFalse(new GetAction<Boolean>() {
                        @Override
                        public void run(Object... parameters) {
                            setResult(((RadioMenuItem) another_item).isSelected());
                        }
                    }.dispatch(Root.ROOT.getEnvironment()));
                }
            }
        }
    }

    //TODO@Smoke
    @Test(timeout = 300000)
    public void radioSizingTest() throws Throwable {
        Wrap<? extends Menu> menu = menuBarAsParent.lookup(new MenuByText(MenuItemApp.RADIO_ID)).wrap();
        Parent<MenuItem> menu_as_parent = menu.as(Parent.class, MenuItem.class);
        expand(menu, true);
        final Scene popup_scene = Root.ROOT.lookup(new LookupCriteria<Scene>() {
            public boolean check(Scene scene) {
                if (!(scene.getWindow() instanceof ContextMenu)) {
                    return false;
                }
                return true;
            }
        }).get();
        Double width = getWidth(popup_scene);
        Double height = getHeight(popup_scene);
        for (int i = 0; i < menu_as_parent.lookup().size(); i++) {
            Wrap<? extends MenuItem> item_wrap = menu_as_parent.lookup().wrap(i);
            item_wrap.mouse().move();
            Double new_width = getWidth(popup_scene);
            Double new_height = getHeight(popup_scene);
            assertEquals(width, new_width);
            assertEquals(height, new_height);
        }
    }

    public void invisibleCheckCycle(String id, String suffix) throws Throwable {
        Selector hide_selector = hideBtn.as(Selectable.class).selector();
        try {
            hide_selector.select(CheckBoxWrap.State.CHECKED);
            menuAsMenuOwner.menu().push(new MenuByText(id));
            checkScreenshot("MenuItemTest-invisible" + suffix, contentPane);
            hide_selector.select(CheckBoxWrap.State.UNCHECKED);
            menuAsMenuOwner.menu().push(new MenuByText(id));
            checkScreenshot("MenuItemTest-visible" + suffix, contentPane);
            hide_selector.select(CheckBoxWrap.State.CHECKED);
            menuAsMenuOwner.menu().push(new MenuByText(id));
            checkScreenshot("MenuItemTest-invisible" + suffix, contentPane);
        } finally {
            hide_selector.select(CheckBoxWrap.State.UNCHECKED);
        }
        throwScreenshotError();
    }

    public void checkCycle(String menu_id) throws Throwable {
        Wrap<? extends Menu> menu = menuBarAsParent.lookup(new MenuByText(menu_id)).wrap();
        Parent<MenuItem> menu_as_parent = menu.as(Parent.class, MenuItem.class);
        for (int i = 0; i < menu_as_parent.lookup().size(); i++) {
            Wrap<? extends MenuItem> item_wrap = menu_as_parent.lookup().wrap(i);
            MenuItem item = item_wrap.getControl();
            expand(menu, true);
            item_wrap.mouse().click();
            lastSelected.waitProperty(Wrap.TEXT_PROP_NAME, item.getId());
            expand(menu, true);
            setMouseSmoothness(4);
            item_wrap.mouse().move();
            setMouseSmoothness(defaultSmoothness);
            checkScreenshot("MenuItemTest-" + menu_id + "-" + i, contentPane);
        }
        throwScreenshotError();
    }

    protected Double getWidth(final Scene popup_scene) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... parameters) {
                setResult(popup_scene.getWindow().getWidth());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected Double getHeight(final Scene popup_scene) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... parameters) {
                setResult(popup_scene.getWindow().getHeight());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected void selectionCycle(boolean enabled, boolean event) {
        clearLastSelectedBtn.mouse().click();
        clearLastEventBtn.mouse().click();
        Wrap<? extends Menu> menu = menuBarLookup.lookup(new MenuByText(MenuItemApp.MIXED_ID)).wrap();
        Parent<MenuItem> menu_as_parent = menu.as(Parent.class, MenuItem.class);
        for (int i = 0; i < menu_as_parent.lookup().size(); i++) {
            menu_as_parent = menu.as(Parent.class, MenuItem.class);
            Wrap<? extends MenuItem> item_wrap = menu_as_parent.lookup().wrap(i);
            MenuItem item = item_wrap.getControl();
            if (SeparatorMenuItem.class.isInstance(item)) {
                continue;
            }
            expand(menu, true);
            item_wrap.mouse().click();
            lastSelected.waitProperty(Wrap.TEXT_PROP_NAME, enabled ? item.getId() : "");
            lastEvent.waitProperty(Wrap.TEXT_PROP_NAME, event ? item.getId() : "");
        }
    }

    protected static boolean isShown(final Wrap<? extends Menu> menu) {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... parameters) {
                setResult(menu.getControl().isShowing());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public static void expand(final Wrap<? extends Menu> menu, Boolean val) {
        if (isShown(menu) != val) {
            menu.mouse().click();
        }
        checkExpanded(menu, val);
    }

    public static void checkExpanded(final Wrap<? extends Menu> menu, final Boolean val) {
        menu.waitState(new State() {
            public Object reached() {
                return (new GetAction<Boolean>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(menu.getControl().isShowing() == val);
                    }
                }.dispatch(Root.ROOT.getEnvironment()) ? Boolean.TRUE : null);
            }
        });
    }

    class MenuByText<T extends MenuItem> implements LookupCriteria<T> {

        String str = null;

        public MenuByText(String str) {
            this.str = str;
        }

        public boolean check(T item) {
            String text = item.getText();
            return text != null && text.contentEquals(str);
        }
    }

    class MenuItemByObject implements LookupCriteria<MenuItem> {

        MenuItem item = null;

        public MenuItemByObject(MenuItem item) {
            this.item = item;
        }

        public boolean check(MenuItem item) {
            return this.item.equals(item);
        }
    }
}
