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

import client.test.Smoke;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.commons.SortValidator;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabBuilder;
import static javafx.scene.control.test.tabpane.NewTabPaneApp.*;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.util.StringConverter;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.control.TabWrap;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Dmitry Zinkevich <dmitry.zinkevich@oracle.com>
 */
public class NewTabPaneTest extends NewTabPaneBase {

    /**
     * Disables the tab and then tries to switch to it by mouse clicking
     */
    @Smoke
    @Test(timeout = 300000)
    public void disablePropertyBindingTest() {
        final int NUM_TABS = SettingType.values().length + 1; //Number of tabs
        final List<String> tabs = populateTabPane(NUM_TABS); //Tabs names

        /*
         * Click on the fist tab and ensure it is selected.
         * It must remain selectet through all the test
         */
        getPlaceholderWrap((Tab) tabPaneAsSelectable.getStates().get(0)).mouse().click();

        final Tab firstTab = getSelectedTab();

        Assert.assertSame((Tab) tabPaneAsSelectable.getStates().get(0), firstTab);

        int testedIndex = NUM_TABS - 1;

        for (SettingType settingType : SettingType.values()) {
            System.out.println(String.format("Tested binding: %s", settingType.name()));

            initChangingController(parent);
            defaultController.include().allTables().allProperties().allCounters().apply();
            defaultController.exclude().allTables().properties(TabProperties.hover, TabProperties.pressed).apply();
            defaultController.fixCurrentState();

            /*
             * Disable tab
             */
            switchToPropertiesTab(tabs.get(testedIndex));
            setPropertyByToggleClick(settingType, TabProperties.disable, Boolean.TRUE);

            /*
             * Check disableProperty and disabledProperty
             */
            checkTextFieldText(TabProperties.disable, "true");
            checkTextFieldText(TabProperties.disabled, "true");

            Tab selectedTab = getSelectedTab();
            Assert.assertSame("Selected tab is still the same after changing property", firstTab, selectedTab);

            /*
             * Click on the disabled tab. Previously selected tab must be the same
             */
            Wrap<? extends Node> tab = getPlaceholderWrap((Tab) tabPaneAsSelectable.getStates().get(testedIndex));
            tab.mouse().click();

            defaultController.check();

            selectedTab = getSelectedTab();
            Assert.assertSame("Selected tab is still the same after click", firstTab, selectedTab);

            testedIndex--;
        }
    }

    /**
     * Sets tab prop closable to 'true' and then tries to close it
     */
    @Smoke
    @Test(timeout = 300000)
    public void closablePropertyBindingTest() {
        final int NUM_TABS = SettingType.values().length + 1;
        final List<String> tabs = populateTabPane(NUM_TABS);

        int testedIndex = NUM_TABS - 1;

        for (SettingType settingType : SettingType.values()) {
            System.out.println(String.format("Tested binding: %s", settingType.name()));

            initChangingController(parent);

            defaultController.include().allTables().allProperties().allCounters().apply();
            defaultController.exclude().allTables().properties(TabProperties.hover, TabProperties.selected, TabProperties.pressed).apply();
            defaultController.fixCurrentState();

            switchToPropertiesTab(tabs.get(testedIndex));
            setPropertyByToggleClick(settingType, TabProperties.closable, Boolean.FALSE);

            checkTextFieldText(TabProperties.closable, "false");

            /*
             * Click on the disabled tab.
             * Previously selected tab must be the same
             */
            Wrap<? extends Node> tab = getPlaceholderWrap((Tab) tabPaneAsSelectable.getStates().get(testedIndex));
            tab.mouse().click();

            TabWrap tabWrap = getTabWrapByIndex(testedIndex);
            try {
                tabWrap.close();
            } catch (Exception e) {
                System.out.println(String.format("After a closing attempt when closable = 'false':\n%s", e.getMessage()));
            }

            checkTextFieldText(TabProperties.selected, "true");

            defaultController.check();
            testedIndex--;
        }
    }

    /**
     * Checks API setUserData/getUserData
     */
    @Smoke
    @Test(timeout = 300000)
    public void userDataAPITest() {
        final java.util.List<String> userData = new java.util.ArrayList<String>();
        userData.add("Tested user data");

        populateTabPane(1);

        initChangingController(parent);

        defaultController.include().allTables().allProperties().allCounters().apply();
        defaultController.fixCurrentState();

        setUserData(0, userData);

        java.util.List<String> actualData = (java.util.List<String>) getUserData(0);

        Assert.assertEquals("User data represents the same object", userData, actualData);

        defaultController.check();
    }

    /**
     * Checks API getProperties/hasProperties
     */
    @Smoke
    @Test(timeout = 300000)
    public void propertiesAPITest() {
        Map<Object, Object> testedData = new HashMap<Object, Object>();
        for (int i = 0; i < 5; i++) {
            testedData.put(String.format("_Key #%d", i), new Tab(String.format("_Tab #%d", i)));
        }

        populateTabPane(1);

        initChangingController(parent);
        defaultController.include().allTables().allProperties().allCounters().apply();
        defaultController.fixCurrentState();

        Assert.assertFalse("hasProperties() must return false", tabHasProperties(0));
        Assert.assertTrue("Initial properties collection is empty", getPropertiesCount(0) == 0);

        setProperties(0, testedData);

        Map<Object, Object> actualProps = getProperties(0);

        Assert.assertTrue("hasProperties() must return true", tabHasProperties(0));
        Assert.assertTrue("Properties collection remains the same", testedData.equals(actualProps));

        defaultController.check();
    }

    /**
     * Checks API hasProperties
     */
    @Smoke
    @Test(timeout = 300000)
    public void hasPropertiesMethodTest() {
        populateTabPane(1);

        initChangingController(parent);
        defaultController.include().allTables().allProperties().allCounters().apply();
        defaultController.fixCurrentState();

        Assert.assertFalse("hasProperties() must return false", tabHasProperties(0));
        Assert.assertTrue("Initial properties collection is empty", getPropertiesCount(0) == 0);
        Assert.assertFalse("hasProperties() must return false", tabHasProperties(0));

        defaultController.check();
    }

    /**
     * This test dynamicaly changes focused state of control with the only tab,
     * and checks, that there is a focus.
     *
     * Bug on test creation RT-27869.
     */
    @Test(timeout = 300000)
    @Smoke
    public void visualFocusScreenshotTest() throws Throwable {
        final String NOT_FOCUSED_TAB_SCREENSHOT_NAME = "TabPane-not_focused_tab";
        final String FOCUSED_TAB_SCREENSHOT_NAME = "TabPane-focused_tab";

        addTab("FIRST", 0);

        //Time of adding animation.
        Thread.sleep(500);

        moveFocusFromTestedControl();

        checkScreenshot(NOT_FOCUSED_TAB_SCREENSHOT_NAME, testedControl);

        tabPaneAsSelectable.selector().select(tabPaneAsSelectable.getStates().get(0));
        moveMouseFromTestedControl();

        checkScreenshot(FOCUSED_TAB_SCREENSHOT_NAME, testedControl);

        testedControl.keyboard().pushKey(KeyboardButtons.TAB);

        checkScreenshot(NOT_FOCUSED_TAB_SCREENSHOT_NAME, testedControl);

        requestFocusOnControl(testedControl);
        moveMouseFromTestedControl();

        checkScreenshot(FOCUSED_TAB_SCREENSHOT_NAME, testedControl);

        moveFocusFromTestedControl();

        checkScreenshot(NOT_FOCUSED_TAB_SCREENSHOT_NAME, testedControl);

        throwScreenshotError();
    }

    /**
     * Test checks possibility of content to veto tab closing. A label with such
     * possibility is added as content of tab, and it will veto the tab closing.
     *
     * Bug on test creation RT-27823.
     */
    @Test(timeout = 300000)
    @Smoke
    public void contentVetoTest() {
        final String vetoTabName = "vetoTab";
        final String nonVetoTabName = "nonVetoTab";

        addTab(vetoTabName, 0, true, true);

        addTab(nonVetoTabName, 1, true, false);

        checkTabsCount(2);

        switchToPropertiesTab(vetoTabName);
        checkCounterValue(TAB_CLOSE_REQUEST_EVENT, 0);
        switchToPropertiesTab(nonVetoTabName);
        checkCounterValue(TAB_CLOSE_REQUEST_EVENT, 0);

        tryToCloseTabByIndex(0);

        checkTabsCount(2);

        switchToPropertiesTab(vetoTabName);
        checkCounterValue(TAB_CLOSE_REQUEST_EVENT, 1);
        switchToPropertiesTab(nonVetoTabName);
        checkCounterValue(TAB_CLOSE_REQUEST_EVENT, 0);

        tryToCloseTabByIndex(1);

        checkTabsCount(1);

        switchToPropertiesTab(vetoTabName);
        checkCounterValue(TAB_CLOSE_REQUEST_EVENT, 1);
        switchToPropertiesTab(nonVetoTabName);
        checkCounterValue(TAB_CLOSE_REQUEST_EVENT, 1);
    }

    /**
     * Checks that when the sorting is applied
     * to the underlying data collection
     * the tabs are rendered in the right order.
     */
    @Smoke
    @Test(timeout=30000)
    public void renderingAfterSortingTest() {
        final int ITEMS_COUNT = 3;

        StringConverter<Tab> conv = new StringConverter<Tab>() {
            @Override public String toString(Tab t) { return t.getText(); }

            @Override public Tab fromString(String s) {
                return TabBuilder.create().text(s).content(new Label("CONTENT!")).build();
            }
        };

        SortValidator<Tab, Label> validator = new SortValidator<Tab, Label>(ITEMS_COUNT, conv) {

            @Override
            protected void setControlData(final ObservableList<Tab> ls) {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... parameters) throws Exception {
                        testedControl.getControl().getTabs().setAll(ls);
                    }
                }.dispatch(testedControl.getEnvironment());
            }

            @Override
            protected Lookup<? extends Label> getCellsLookup() {
                return testedControl.as(Parent.class, Label.class)
                              .lookup(Label.class, new LookupCriteria<Label>() {

                    public boolean check(Label lbl) {
                        return lbl.getStyleClass().contains("tab-label")
                                && lbl.isVisible();
                    }
                });
            }

            @Override
            protected String getTextFromCell(Label cell) {
                return cell.getText();
            }

            @Override
            protected void sort() {
                new GetAction<Object>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        FXCollections.sort(testedControl.getControl().getTabs(), new Comparator<Tab>() {
                            @Override
                            public int compare(Tab o1, Tab o2) {
                                return o1.getText().compareTo(o2.getText());
                            }
                        });
                    }
                }.dispatch(testedControl.getEnvironment());
            }
        };
        validator.setOrientation(Orientation.HORIZONTAL);
        boolean result = validator.check();
        String msg = validator.getFailureReason();
        assertTrue(msg, result);
    }
}