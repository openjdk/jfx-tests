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
package javafx.scene.control.test.tabpane;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.ByText;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TabWrap;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Selectable;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.screenshots.ScreenshotUtils;
import static javafx.scene.control.test.tabpane.TabPaneApp2.*;
import org.jemmy.env.Timeout;

@RunWith(FilteredTestRunner.class)
public class TabPane2Test extends UtilTestFunctions {

    public TabPane2Test() {
    }
    protected static final int ANIMATION_DELAY = 1500;
    static Wrap<? extends Scene> scene = null;
//    static Parent<Node> parent = null;
    static Wrap<? extends TabPane> tabPane = null;
    static Selectable<Object> tabPaneAsSelectable;
    static Parent<Tab> tabPaneAsParent;
    static Wrap contentPane = null;
    static Wrap clearBtn = null;
    static Wrap resetBtn = null;
    static Wrap<? extends ChoiceBox> sideCB = null;
    static Wrap<? extends Label> lastPressedLbl = null;
    static Wrap<? extends Label> errorLbl = null;
    static Selectable<Object> eventsList = null;
    Throwable error = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        TabPaneApp2.main(null);
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);
        tabPane = parent.lookup(TabPane.class).wrap();
        tabPaneAsSelectable = tabPane.as(Selectable.class, Tab.class);
        tabPaneAsParent = tabPane.as(Parent.class, Tab.class);
        contentPane = parent.lookup(new ByID<Node>(TEST_PANE_ID)).wrap();
        clearBtn = parent.lookup(new ByText(CLEAR_BTN_ID)).wrap();
        resetBtn = parent.lookup(new ByText(RESET_BTN_ID)).wrap();
        sideCB = parent.lookup(ChoiceBox.class).wrap();
        lastPressedLbl = (Wrap<? extends Label>) parent.lookup(new ByID(LAST_SELECTED_ID)).wrap();
        errorLbl = (Wrap<? extends Label>) parent.lookup(new ByID(ERROR_ID)).wrap();
        eventsList = (Selectable<Object>) parent.lookup(new ByID(EVENT_LIST_ID)).wrap().as(Selectable.class, String.class);
    }

    @Before
    public void setUI() throws InterruptedException {
        Environment.getEnvironment().setTimeout("wait.state", 3000);
        Environment.getEnvironment().setTimeout("wait.control", 2000);
        clickButtonForTestPurpose(RESET_BTN_ID);
        Thread.sleep(ANIMATION_DELAY);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void clearTest() {
        tabPaneAsSelectable.selector().select(tabPaneAsSelectable.getStates().get(0));
        clearBtn.mouse().click();
        ScreenshotUtils.checkScreenshot("TabPaneTest-clearTest", contentPane);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void addItemsTest() {
        ScreenshotUtils.checkScreenshot("TabPaneTest-addTest", contentPane);
        assertEquals(eventsList.getStates().size(), 1);
        assertEquals(eventsList.getStates().get(0), "Tab 0");
    }

    @ScreenshotCheck
    @Test(timeout = 900000)
    public void selectionTest() throws Throwable {
        Selectable selectable = sideCB.as(Selectable.class);
        for (Object side : selectable.getStates()) {
            sideCB.as(Selectable.class).selector().select(side);
            mouseSelectionCycle(side.toString());
        }
        for (Object side : selectable.getStates()) {
            sideCB.as(Selectable.class).selector().select(side);
            keyboardSelectionCycle(side.toString());
        }
        throwError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void closeTest() throws Throwable {
        tabPaneAsParent.lookup(Tab.class).size();
        for (int i = 0; i < TABS_NUM; i++) {
            TabWrap wrap = (TabWrap) tabPaneAsParent.lookup(Tab.class, new LookupCriteria<Tab>() {

                public boolean check(Tab tab) {
                    return tab == tabPaneAsSelectable.getStates().get(0);
                }
            }).wrap();
            wrap.close();
            tabPane.mouse().move();
            Thread.sleep(ANIMATION_DELAY);
            checkScreenshot("TabPaneTest-close-" + i);
        }
        throwError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void disableAllTest() throws Throwable {
        /**
         * Kinsley: The Tab is not selectable by the user, but it is selectable
         * programmatically. If you are on the selected Tab when it becomes
         * disabled, you should remain on this Tab. A disable tab is no longer
         * interactive or traversable, but they still exist in the user
         * interface, and the programmer can still force them to fire their
         * actions. RT-17668
         */
        tabPaneAsParent.lookup(Tab.class).size();
        for (int i = 0; i < TABS_NUM; i++) {
            disableAt(i);
            assertTrue(getCurrentSelection() == 0);
        }
        Thread.sleep(ANIMATION_DELAY);
        checkScreenshot("TabPaneTest-disabled-allTabs");
        throwError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void disableAndClosing1Test() throws Throwable {
        tabPaneAsParent.lookup(Tab.class).size();
        TabWrap wrap1 = getTabWrapByPos(0);
        TabWrap wrap2 = getTabWrapByPos(1);

        disableAt(1);
        assertTrue(tryToCloseTab(wrap1));
        assertTrue(tryToCloseTab(wrap2));
    }

    @Smoke
    @Test(timeout = 300000)
    public void disableAndSelectThroughtPopupTest() throws Throwable {
        disableAt(TABS_NUM - 1);
        getPlaceholderWrap((Tab) tabPaneAsSelectable.getStates().get(TABS_NUM - 1)).mouse().click();
        assertFalse(getCurrentSelection() == TABS_NUM - 1);
    }

    @Smoke
    @Test(timeout = 300000)
    public void disableAndProgrammaticalSelectTest() throws Throwable {
        disableAt(1);
        selectProgrammaticaly(1);
        assertTrue(getCurrentSelection() == 1);
        assertTrue(tryToCloseTab(getTabWrapByPos(1)));
    }

    @Smoke
    @Test(timeout = 300000)
    public void disableAndTraverseTest() {
        disableAt(1);
        assertTrue(getCurrentSelection() == 0);
        tabPane.keyboard().pushKey(Keyboard.KeyboardButtons.RIGHT);
        assertTrue(getCurrentSelection() == 2);
        tabPane.keyboard().pushKey(Keyboard.KeyboardButtons.LEFT);
        assertTrue(getCurrentSelection() == 0);
    }

    @Smoke
    @Test(timeout = 30000)
    public void disableAtEndsAndTraverseTest() {
        disableAt(0);
        assertTrue(getCurrentSelection() == 0);
        tabPane.keyboard().pushKey(Keyboard.KeyboardButtons.RIGHT);
        assertTrue(getCurrentSelection() == 1);
        tabPane.keyboard().pushKey(Keyboard.KeyboardButtons.LEFT);
        assertTrue(getCurrentSelection() == 1);

        getPlaceholderWrap((Tab) tabPaneAsSelectable.getStates().get(TABS_NUM - 1)).mouse().click();
        disableAt(TABS_NUM - 1);
        assertTrue(getCurrentSelection() == TABS_NUM - 1);
        tabPane.keyboard().pushKey(Keyboard.KeyboardButtons.LEFT);
        assertTrue(getCurrentSelection() == TABS_NUM - 2);
        tabPane.keyboard().pushKey(Keyboard.KeyboardButtons.RIGHT);
        assertTrue(getCurrentSelection() == TABS_NUM - 2);
    }

    protected TabWrap getTabWrapByPos(final int i) {
        return (TabWrap) tabPaneAsParent.lookup(Tab.class, new LookupCriteria<Tab>() {

            @Override
            public boolean check(Tab tab) {
                return tab == tabPaneAsSelectable.getStates().get(i);
            }
        }).wrap();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void tooltipTest() throws Throwable {
        final String imageWaitName = "wait.state";

        final int defaultSmoothness = getMouseSmoothness();
        final Timeout defaultTimeout = scene.getEnvironment().getTimeout(imageWaitName);

        setMouseSmoothness(4);
        scene.getEnvironment().setTimeout(imageWaitName, 3000);//Tooltip disappears quickly.

        Lookup<Node> tabLookup = getTabLookup(true);
        for (int i = 0; i < tabLookup.size(); i++) {
            tabLookup.wrap(i).mouse().move();
            Root.ROOT.lookup().wait(2);
            checkScreenshot("TabPaneTest-tooltip-" + i);
        }

        setMouseSmoothness(defaultSmoothness);
        scene.getEnvironment().setTimeout(imageWaitName, defaultTimeout.getValue());

        throwError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void contextMenuTest() throws Throwable {
        setMouseSmoothness(4);
        Lookup tab_lookup = getTabLookup(true);
        for (int i = 0; i < tab_lookup.size(); i++) {
            Wrap wrap = tab_lookup.wrap(i);
            Point pt = wrap.getClickPoint();
            pt.translate(0, -1 * i);
            wrap.mouse().click(1, pt, MouseButtons.BUTTON3);
            scene.mouse().move(new Point(1, 1));
            checkScreenshot("TabPaneTest-context-menu-" + i);
            scene.mouse().click(1, new Point(1, 1));
        }
        throwError();
    }

    protected void selectProgrammaticaly(final int i) {
        new GetAction() {

            @Override
            public void run(Object... os) throws Exception {
                tabPane.getControl().getSelectionModel().select(i);
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected int getCurrentSelection() {
        return (int) new GetAction<Integer>() {

            @Override
            public void run(Object... os) throws Exception {
                setResult(tabPane.getControl().getSelectionModel().getSelectedIndex());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    public void keyboardSelectionCycle(String side) throws Throwable {
        for (int i = 0; i < tabPaneAsSelectable.getStates().size(); i++) {
            tabPaneAsSelectable.selector().select(tabPaneAsSelectable.getStates().get(i));
            int size = eventsList.getStates().size();
            assertEquals(eventsList.getStates().get(size - 1), "Tab " + i);
            tabPane.mouse().move();
            assertTrue(lastPressedLbl.getProperty(String.class, Wrap.TEXT_PROP_NAME).contentEquals(String.valueOf(i)));
            checkScreenshot("TabPaneTest-" + side + "-select-" + i);
        }
    }

    public void mouseSelectionCycle(String side) throws Throwable {
        int evens_list_size = eventsList.getStates().size() + (side == "TOP" ? 0 : 2);
        int prev_selected = tabPaneAsSelectable.getStates().indexOf(tabPaneAsSelectable.getState());
        for (int i = 0; i < tabPaneAsSelectable.getStates().size(); i++) {
            getPlaceholderWrap((Tab) tabPaneAsSelectable.getStates().get(i)).mouse().click();
            int size = eventsList.getStates().size();
            if (size != evens_list_size + i * 2) {
                Thread.sleep(10000);
            }
            assertEquals(size, evens_list_size + i * 2);
            assertEquals(eventsList.getStates().get(size - 1), "Tab " + i);
            if (size > 1) {
                assertEquals(eventsList.getStates().get(size - 2), "Tab " + prev_selected);
            }
            prev_selected = i;
            tabPane.mouse().move();
            assertTrue(lastPressedLbl.getProperty(String.class, Wrap.TEXT_PROP_NAME).contentEquals(String.valueOf(i)));
            checkScreenshot("TabPaneTest-" + side + "-select-" + i);
        }
    }

    protected void disableAt(int pos) {
        setText(findTextField(DISABLE_POS_EDIT_ID), String.valueOf(pos));
        clickButtonForTestPurpose(DISABLE_SINGLE_AT_POS_BTN_ID);
    }

    protected boolean tryToCloseTab(TabWrap wrap) throws InterruptedException {
        Thread.sleep(ANIMATION_DELAY);

        if (wrap.isDisabled()) {
            tabPane.as(Parent.class, Node.class).lookup(Node.class, new LookupCriteria<Node>() {

                public boolean check(Node node) {
                    return node.getStyleClass().contains("tab-close-button") && node.isVisible();
                }
            }).wrap().mouse().click();
        } else {
            try {
                wrap.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    protected void checkScreenshot(String name) {
        try {
            ScreenshotUtils.checkScreenshot(name, contentPane);
        } catch (Throwable throwable) {
            if (error == null) {
                error = throwable;
            }
        }
    }

    protected void throwError() throws Throwable {
        if (error != null) {
            throw error;
        }
    }

    Lookup getTabLookup(final boolean visible) {
        Parent pane_as_parent = tabPane.as(Parent.class, Node.class);
        final Wrap<Node> tab_pane_header = pane_as_parent.lookup(Node.class, new ByStyleClass("tab-header-area")).wrap();
        Parent<Node> header_as_parent = tab_pane_header.as(Parent.class, Node.class);
        final Wrap<Node> control_btn = header_as_parent.lookup(Node.class, new ByStyleClass("control-buttons-tab")).wrap();
        Lookup<Node> tab_lookup = header_as_parent.lookup(Node.class, new LookupCriteria<Node>() {

            public boolean check(Node control) {
                Bounds ctrl_bounds = control.getBoundsInParent();
                Rectangle ctrl_rect = new Rectangle(ctrl_bounds.getMinX(), ctrl_bounds.getMinY(), ctrl_bounds.getWidth(), ctrl_bounds.getHeight());
                Rectangle header_rect = new Rectangle(0, 0, tab_pane_header.getScreenBounds().getWidth(), tab_pane_header.getScreenBounds().getHeight());
                Bounds ctrl_btn_bounds = control_btn.getControl().getBoundsInParent();
                Rectangle ctrl_btn_rect = new Rectangle(ctrl_btn_bounds.getMinX(), ctrl_btn_bounds.getMinY(), ctrl_btn_bounds.getWidth(), ctrl_btn_bounds.getHeight());
                return control.getStyleClass().contains("tab")
                        && (!visible || (header_rect.contains(ctrl_rect)
                        && !ctrl_btn_rect.intersects(ctrl_rect)));
            }
        });
        return tab_lookup;
    }

    private Wrap<? extends Node> getPlaceholderWrap(Tab tab) {
        Wrap placeholder_wrap = null;

        Parent pane_as_parent = tabPane.as(Parent.class, Node.class);
        Wrap<Node> tab_pane_header = pane_as_parent.lookup(Node.class, new ByStyleClass("tab-header-area")).wrap();
        Parent<Node> header_as_parent = tab_pane_header.as(Parent.class, Node.class);

        ArrayList<Wrap> labels = new ArrayList<Wrap>();
        Lookup lookup = getTabLookup(false);
        for (int i = 0; i < lookup.size(); i++) {
            labels.add(lookup.wrap(i));
        }
        Collections.sort(labels, new Comparator<Wrap>() {

            public int compare(Wrap w1, Wrap w2) {
                switch (tabPane.getControl().getSide()) {
                    case LEFT:
                    case RIGHT: {
                        return Double.compare(w1.getScreenBounds().getY(), w2.getScreenBounds().getY());
                    }
                    default: {
                        return Double.compare(w1.getScreenBounds().getX(), w2.getScreenBounds().getX());
                    }
                }
            }
        });
        placeholder_wrap = labels.get(tabPane.getControl().getTabs().indexOf(tab));

        Wrap down_button = header_as_parent.lookup(Node.class, new LookupCriteria<Node>() {

            public boolean check(Node node) {
                return node.getStyleClass().contains("tab-down-button");
            }
        }).wrap();

        if (!tabPane.getScreenBounds().contains(placeholder_wrap.getScreenBounds()) || down_button.getScreenBounds().intersects(placeholder_wrap.getScreenBounds())) {

            Lookup scene_lookup = Root.ROOT.lookup(new LookupCriteria<Scene>() {

                public boolean check(Scene scene) {
                    if (scene.getWindow() instanceof ContextMenu) {
                        Object property = scene.getRoot().getProperties().get(TabPane.class);
                        return property != null && property == tabPane.getControl();
                    }
                    return false;
                }
            });

            Wrap<? extends Scene> popup_scene_wrap;

            if (scene_lookup.size() == 0) {
                down_button.mouse().click();

                //Wait for animation
                try {
                    Thread.sleep(SLEEP);
                } catch (Exception ignore) {
                }

                popup_scene_wrap = Root.ROOT.lookup(new LookupCriteria<Scene>() {

                    public boolean check(Scene scene) {
                        if (scene.getWindow() instanceof ContextMenu) {
                            scene.getRoot().getProperties().put(TabPane.class, tabPane.getControl());
                            return true;
                        }
                        return false;
                    }
                }).wrap();
            } else {
                popup_scene_wrap = scene_lookup.wrap();
            }

            Lookup popup_lookup = popup_scene_wrap.as(Parent.class, Node.class).lookup(Node.class, new ByStyleClass("menu-item"));
            placeholder_wrap = popup_lookup.wrap(tabPane.getControl().getTabs().indexOf(tab) - tabPane.getControl().getTabs().size() + popup_lookup.size());
        }

        return placeholder_wrap;
    }
}
