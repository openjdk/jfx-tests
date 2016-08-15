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
package javafx.scene.control.test.pagination;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import com.oracle.jdk.sqe.cc.markup.Covers;
import java.util.EnumSet;
import javafx.scene.control.Pagination;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class PaginationTest extends TestBase {

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"com.sun.javafx.scene.control.Pagination.currentpageindex.BEHAVIOR", "com.sun.javafx.scene.control.Pagination.currentpageindex.GET", "com.sun.javafx.scene.control.Pagination.currentpageindex.BIND", "com.sun.javafx.scene.control.Pagination.currentpageindex.DEFAULT", "com.sun.javafx.scene.control.Pagination.currentpageindex.SET"}, level = Covers.Level.FULL)
    public void currentPageIndexPropertyTest() throws InterruptedException {
        assertEquals(new Pagination().getCurrentPageIndex(), 0, 0);

        int pagesCount = 100;

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 5);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, pagesCount);
        setSize(200, 200);

        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(false, 0);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.currentPageIndex, 13);
        checkTextFieldValue(Properties.currentPageIndex, 13);
        checkRangeOfPageIndecesVisibility(11, 15);
        checkCorrectPageContentShowing(false, 13);

        setPropertyBySlider(SettingType.SETTER, Properties.currentPageIndex, 96);
        checkTextFieldValue(Properties.currentPageIndex, 96);
        checkRangeOfPageIndecesVisibility(93, 97);
        checkCorrectPageContentShowing(false, 96);

        setNewFactory();
        checkTextFieldValue(Properties.currentPageIndex, 0);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(true, 0);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.currentPageIndex, 7);
        checkTextFieldValue(Properties.currentPageIndex, 7);
        checkRangeOfPageIndecesVisibility(4, 8);
        checkCorrectPageContentShowing(true, 7);

        switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.currentPageIndex);

        navigateToPage(pagesCount, NavigationWay.MOUSE); //to right to the end.
        checkTwoArrowsVisibility(true, false);
        checkTextFieldValue(Properties.currentPageIndex, pagesCount - 1);
        checkRangeOfPageIndecesVisibility(pagesCount - 1, pagesCount);
        checkCorrectPageContentShowing(true, pagesCount - 1);

        navigateToPage(1, NavigationWay.MOUSE);//to left at the beginning.
        checkTwoArrowsVisibility(false, true);
        checkTextFieldValue(Properties.currentPageIndex, 0);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(true, 0);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 10);
        for (SettingType settingType : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.currentPageIndex);
            setPropertyBySlider(settingType, Properties.currentPageIndex, 15);
            setPropertyBySlider(settingType, Properties.currentPageIndex, -5);
        }
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"com.sun.javafx.scene.control.Pagination.pagecount.BEHAVIOR", "com.sun.javafx.scene.control.Pagination.pagecount.GET", "com.sun.javafx.scene.control.Pagination.pagecount.BIND", "com.sun.javafx.scene.control.Pagination.pagecount.DEFAULT", "com.sun.javafx.scene.control.Pagination.pagecount.SET"}, level = Covers.Level.FULL)
    public void pageCountPropertyTest() throws InterruptedException {
        assertEquals(new Pagination().getPageCount(), Pagination.INDETERMINATE, 0);

        setSize(200, 200);
        setPropertyBySlider(SettingType.SETTER, Properties.maxPageIndicatorCount, 5);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 7);
        navigateToPage(7, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(6, 7);
        checkCorrectPageContentShowing(false, 6);

        setPropertyBySlider(SettingType.SETTER, Properties.pageCount, 12);
        checkTextFieldValue(Properties.currentPageIndex, 0);
        navigateToPage(12, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(11, 12);
        checkCorrectPageContentShowing(false, 11);

        setPropertyBySlider(SettingType.SETTER, Properties.maxPageIndicatorCount, 10);
        setPropertyBySlider(SettingType.SETTER, Properties.pageCount, 10);
        checkCorrectPageContentShowing(false, 0);
        navigateToPage(10, NavigationWay.MOUSE);
        checkCorrectPageContentShowing(false, 9);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.pageCount, 3);
        checkTextFieldValue(Properties.currentPageIndex, 0);
        navigateToPage(3, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(1, 3);

        setNewFactory();

        checkTextFieldValue(Properties.currentPageIndex, 3);
        checkRangeOfPageIndecesVisibility(1, 3);
        checkCorrectPageContentShowing(true, 0);

        switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.pageCount);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 5);
        for (SettingType settingType : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.pageCount);

            setPropertyBySlider(settingType, Properties.pageCount, 10);
            checkTextFieldValue(Properties.pageCount, 10);
            checkRangeOfPageIndecesVisibility(1, 5);
            checkCorrectPageContentShowing(true, 0);

            switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.pageCount);
            setPropertyBySlider(settingType, Properties.pageCount, -5);
            if (settingType.equals(SettingType.BIDIRECTIONAL)) {
                setPropertyBySlider(settingType, Properties.pageCount, 10);
            } else {
                setPropertyBySlider(settingType, Properties.pageCount, -5);
            }
            checkRangeOfPageIndecesVisibility(1, 5);
            checkCorrectPageContentShowing(true, 0);
            testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
            testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        }
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"com.sun.javafx.scene.control.Pagination.pageindicatorcount.BEHAVIOR", "com.sun.javafx.scene.control.Pagination.pageindicatorcount.GET", "com.sun.javafx.scene.control.Pagination.pageindicatorcount.BIND", "com.sun.javafx.scene.control.Pagination.pageindicatorcount.DEFAULT", "com.sun.javafx.scene.control.Pagination.pageindicatorcounts.SET"}, level = Covers.Level.FULL)
    public void pageIndicatorCountTest() throws InterruptedException {
        assertEquals(new Pagination().getMaxPageIndicatorCount(), 10, 0);
        setSize(200, 200);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 25);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 5);
        checkTextFieldValue(Properties.maxPageIndicatorCount, 5);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkArrowNonDisabledState(false, true);

        navigateToPage(15, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(11, 15);
        checkArrowNonDisabledState(true, true);

        setPropertyBySlider(SettingType.SETTER, Properties.maxPageIndicatorCount, 4);
        checkTextFieldValue(Properties.maxPageIndicatorCount, 4);
        checkRangeOfPageIndecesVisibility(1, 4);
        checkArrowNonDisabledState(false, true);

        navigateToPage(15, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(13, 16);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.maxPageIndicatorCount, 3);
        checkTextFieldValue(Properties.maxPageIndicatorCount, 3);
        checkRangeOfPageIndecesVisibility(1, 3);
        checkArrowNonDisabledState(false, true);

        navigateToPage(10, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(10, 12);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 2);
        checkRangeOfPageIndecesVisibility(1, 2);

        switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.maxPageIndicatorCount);

        setPropertyBySlider(SettingType.SETTER, Properties.maxPageIndicatorCount, 5);
        checkTextFieldValue(Properties.maxPageIndicatorCount, 5);
        checkRangeOfPageIndecesVisibility(1, 2);

        setPropertyBySlider(SettingType.SETTER, Properties.maxPageIndicatorCount, 25);

        for (SettingType settingType : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER)) {
            setPropertyBySlider(settingType, Properties.maxPageIndicatorCount, -5);
            checkTextFieldValue(Properties.maxPageIndicatorCount, 4);
            checkRangeOfPageIndecesVisibility(1, 2);
            checkCorrectPageContentShowing(false, 0);
        }

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.maxPageIndicatorCount, -5);
        checkTextFieldValue(Properties.maxPageIndicatorCount, -5);
        checkCorrectPageContentShowing(false, 0);

        switchOffBinding(SettingType.UNIDIRECTIONAL, Properties.maxPageIndicatorCount);

        for (SettingType settingType : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER)) {
            setPropertyBySlider(settingType, Properties.maxPageIndicatorCount, 10);//Assume, page count is 2.
            checkTextFieldValue(Properties.maxPageIndicatorCount, 3);
            checkRangeOfPageIndecesVisibility(1, 2);
            checkCorrectPageContentShowing(false, 0);
        }

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.maxPageIndicatorCount, 10);//Assume, page count is 2.
        checkTextFieldValue(Properties.maxPageIndicatorCount, 10);
        checkRangeOfPageIndecesVisibility(1, 2);
        checkCorrectPageContentShowing(false, 0);
    }

    @Smoke
    @Test(timeout = 300000)
    public void innerContentReceiveEventsTest() throws InterruptedException {
        setSize(200, 200);
        requestFocusOnControl(testedControl);
        testedControl.mouse().turnWheel(+6);
        checkFormComponentButton();
        checkScrollingOfFormComponentScrollBar();
        checkPrintingInInnerTextField();
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"com.sun.javafx.scene.control.Pagination.pagefactory.BEHAVIOR", "com.sun.javafx.scene.control.Pagination.pagefactory.GET", "com.sun.javafx.scene.control.Pagination.pagefactory.DEFAULT", "com.sun.javafx.scene.control.Pagination.pagefactory.SET"}, level = Covers.Level.FULL)
    public void factoryChangingTest() throws InterruptedException {
        assertTrue((new Pagination().getPageFactory()) == null);
        setSize(200, 200);

        setPropertyBySlider(SettingType.SETTER, Properties.pageCount, 15);
        setPropertyBySlider(SettingType.SETTER, Properties.maxPageIndicatorCount, 7);
        setPropertyBySlider(SettingType.SETTER, Properties.currentPageIndex, 5);

        checkTextFieldValue(Properties.currentPageIndex, 5);
        checkTextFieldValue(Properties.pageCount, 15);
        checkTextFieldValue(Properties.maxPageIndicatorCount, 7);

        checkCorrectPageContentShowing(false, 5);

        for (int i = 1; i < 30; i++) {
            setNewFactory();

            checkTextFieldValue(Properties.currentPageIndex, 0);
            checkCorrectPageContentShowing(true, 0);

            setPropertyBySlider(SettingType.SETTER, Properties.currentPageIndex, 8);
            checkTextFieldValue(Properties.currentPageIndex, 8);
            checkCorrectPageContentShowing(true, 8);

            setOldFactory();

            checkTextFieldValue(Properties.currentPageIndex, 0);
            checkCorrectPageContentShowing(false, 0);
        }

        //These values should not be changed.
        checkTextFieldValue(Properties.pageCount, 15);
        checkTextFieldValue(Properties.maxPageIndicatorCount, 7);
        checkRangeOfPageIndecesVisibility(1, 5);

        //And now, after playing with factory, let's play with control.
        setPropertyBySlider(SettingType.SETTER, Properties.pageCount, 20);
        checkTextFieldValue(Properties.pageCount, 20);
        checkTextFieldValue(Properties.currentPageIndex, 0);
        navigateToPage(20, NavigationWay.MOUSE);
        checkCorrectPageContentShowing(false, 19);
        checkTwoArrowsVisibility(true, false);
        navigateToPage(1, NavigationWay.MOUSE);
        checkCorrectPageContentShowing(false, 0);
        checkTwoArrowsVisibility(false, true);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void complexScreenshot1Test() throws InterruptedException, Throwable {
        setNewFactory();

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 20);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 6);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.currentPageIndex, 10);
        setSize(200, 200);
        navigateToPage(20, NavigationWay.MOUSE);

        checkCorrectPageContentShowing(true, 19);

        checkScreenshot("Paginator_complexScreenshot_inpage19_pageCount20_pageIndicator6", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void complexScreenshot2Test() throws InterruptedException, Throwable {
        setBulletStyleOfPageIndicators();
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 20);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 5);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.currentPageIndex, 10);
        setSize(200, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 25);
        checkScreenshot("Paginator_complexScreenshot_page0_pageCount20_pageIndicator25", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void leftRightKeyboardTest() throws InterruptedException {
        setSize(200, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 20);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 5);

        setNewFactory();
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(true, 0);
        checkTwoArrowsVisibility(false, true);

        testedControl.keyboard().pushKey(Keyboard.KeyboardButtons.RIGHT);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(true, 1);
        checkTwoArrowsVisibility(true, true);

        testedControl.keyboard().pushKey(Keyboard.KeyboardButtons.LEFT);
        testedControl.keyboard().pushKey(Keyboard.KeyboardButtons.LEFT);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(true, 0);
        checkTwoArrowsVisibility(false, true);

        navigateToPage(20, NavigationWay.KEYBOARD);
        checkRangeOfPageIndecesVisibility(16, 20);
        checkCorrectPageContentShowing(true, 19);
        checkTwoArrowsVisibility(true, false);

        testedControl.keyboard().pushKey(Keyboard.KeyboardButtons.LEFT);
        checkRangeOfPageIndecesVisibility(16, 20);
        checkCorrectPageContentShowing(true, 18);
        checkTwoArrowsVisibility(true, true);

        testedControl.keyboard().pushKey(Keyboard.KeyboardButtons.RIGHT);
        testedControl.keyboard().pushKey(Keyboard.KeyboardButtons.RIGHT);
        checkRangeOfPageIndecesVisibility(16, 20);
        checkCorrectPageContentShowing(true, 19);
        checkTwoArrowsVisibility(true, false);

        navigateToPage(1, NavigationWay.KEYBOARD);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(true, 0);
        checkTwoArrowsVisibility(false, true);
    }

    @Smoke
    @Test(timeout = 300000)
    public void complexInteractionTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 20);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 5);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.currentPageIndex, 10);
        setSize(200, 200);

        setNewFactory();
        navigateToPage(15, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(11, 15);
        checkCorrectPageContentShowing(true, 14);
        checkTwoArrowsVisibility(true, true);

        navigateToPage(20, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(16, 20);
        checkCorrectPageContentShowing(true, 19);
        checkTwoArrowsVisibility(true, false);

        navigateToPage(1, NavigationWay.MOUSE);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(true, 0);
        checkTwoArrowsVisibility(false, true);

        navigateToPage(2, NavigationWay.MOUSE);
        checkTwoArrowsVisibility(true, true);
        setOldFactory();
        checkTwoArrowsVisibility(false, true);
        checkRangeOfPageIndecesVisibility(1, 5);
        checkCorrectPageContentShowing(false, 0);
    }

    @Smoke
    @Test(timeout = 300000)
    public void indeterminateValueTest() throws InterruptedException {
        setSize(200, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.pageCount, 20);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.maxPageIndicatorCount, 5);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.currentPageIndex, 10);
        setIndeterminatePageCount();
        navigateToPage(30, NavigationWay.MOUSE);
        navigateToPage(25, NavigationWay.KEYBOARD);
        navigateToPage(40, NavigationWay.MOUSE);
    }
}
