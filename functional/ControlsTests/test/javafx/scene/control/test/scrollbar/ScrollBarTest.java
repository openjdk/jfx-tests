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
package javafx.scene.control.test.scrollbar;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import static javafx.scene.control.test.scrollbar.ScrollBarApp.TESTED_SCROLLBAR_ID;
import javafx.scene.control.test.scrollbar.TestBase.Properties;
import static javafx.scene.control.test.scrollbar.TestBase.scene;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByID;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class ScrollBarTest extends TestBase {

    @Smoke
    @Test(timeout = 300000)
    public void simpleValueSettingTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 70);
        checkTextFieldValue(Properties.value, 70);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.value, 30);
        checkTextFieldValue(Properties.value, 30);
    }

    @Smoke
    @Test(timeout = 300000)
    public void minAndMaxPropertyTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, -10);
        checkTextFieldValue(Properties.max, -10);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.max, 30);
        checkTextFieldValue(Properties.max, 30);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, 150);
        checkTextFieldValue(Properties.min, 150);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.min, 50);
        checkTextFieldValue(Properties.min, 50);
    }

    @Smoke
    @Test(timeout = 300000)
    public void visibleAmountPropertyTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 50);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.visibleAmount, 150);
        checkTextFieldValue(Properties.visibleAmount, 150);

        // check on any possible interferention.
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 100);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.visibleAmount, 50);
        checkTextFieldValue(Properties.visibleAmount, 50);

        checkTextFieldValue(Properties.unitIncrement, 100);
        checkTextFieldValue(Properties.blockIncrement, 100);
        checkTextFieldValue(Properties.value, 50);
    }

    @Smoke
    @Test(timeout = 300000) //RT-18223
    public void blockAndUnitIncrementAndMouseAndKeyBoardTest() throws InterruptedException {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);

        //Block - aboutclick over free space, Unit - about click on arrow.
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 100);
        checkTextFieldValue(Properties.blockIncrement, 100);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, 100);
        checkTextFieldValue(Properties.unitIncrement, 100);
        checkTextFieldValue(Properties.blockIncrement, 100);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.blockIncrement, 40);
        checkTextFieldValue(Properties.unitIncrement, 100);
        checkTextFieldValue(Properties.blockIncrement, 40);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.unitIncrement, 20);
        checkTextFieldValue(Properties.unitIncrement, 20);
        checkTextFieldValue(Properties.blockIncrement, 40);

        final Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();

        int iterations = ITERATIONS;
        double expectedValue = 0;
        double blockIncrement = 40;
        double unitIncrement = 20;
        double min = 0;
        double max = 100;

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, min);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, max);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, expectedValue);

        for (int i = 0; i < iterations; i++) {
            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);

            testedScrollBar.mouse().click();
            expectedValue = adjustValue(min, max, expectedValue + blockIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            clickMore(testedScrollBar);
            expectedValue = adjustValue(min, max, expectedValue + unitIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.RIGHT);
            expectedValue = adjustValue(min, max, expectedValue + blockIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.mouse().click();
            expectedValue = adjustValue(min, max, expectedValue - blockIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            clickLess(testedScrollBar);
            expectedValue = adjustValue(min, max, expectedValue - unitIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.RIGHT);
            expectedValue = adjustValue(min, max, expectedValue + blockIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.LEFT);
            expectedValue = adjustValue(min, max, expectedValue - blockIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.END);
            expectedValue = adjustValue(min, max, max);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.HOME);
            expectedValue = adjustValue(min, max, min);
            checkTextFieldValue(Properties.value, expectedValue);

            // Now scrollBar should be in state like it would be initially.

            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

            // ? getScreenBounds return old coordinates after orienatation changing.
            Thread.sleep(100);

            clickMore(testedScrollBar);
            expectedValue = adjustValue(min, max, expectedValue + unitIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.DOWN);
            expectedValue = adjustValue(min, max, expectedValue + blockIncrement);
            checkTextFieldValue(Properties.value, expectedValue); // RT-18223

            testedScrollBar.keyboard().pushKey(KeyboardButtons.UP);
            expectedValue = adjustValue(min, max, expectedValue - blockIncrement);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.END);
            expectedValue = adjustValue(min, max, max);
            checkTextFieldValue(Properties.value, expectedValue);

            testedScrollBar.keyboard().pushKey(KeyboardButtons.HOME);
            expectedValue = adjustValue(min, max, min);
            checkTextFieldValue(Properties.value, expectedValue);
        }
    }

    @Smoke
    @Test(timeout = 300000) //Test checks horizontal and vertical scrolling for horizontal and vertical scrollBar.
    public void scrollingTest() throws InterruptedException {
        //Unit increment value should determine scrolling value per 1 turn.
        Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 5);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, 10);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, 0);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 50);

        // Horizontal scrollBar should react on vertical and horizontal scrolling.
        testedScrollBar.mouse().turnWheel(1 * (Utils.isMacOS() ? -1 : 1));
        checkTextFieldValue(Properties.value, 60);

        testedScrollBar.mouse().turnWheel(-1 * (Utils.isMacOS() ? -1 : 1));
        checkTextFieldValue(Properties.value, 50);

        sendScrollEvent(scene, testedScrollBar, 20, 0);
        checkTextFieldValue(Properties.value, 40);

        sendScrollEvent(scene, testedScrollBar, -300, 0);
        checkTextFieldValue(Properties.value, 50);

        sendScrollEvent(scene, testedScrollBar, 0, 100);
        checkTextFieldValue(Properties.value, 40);

        // vertical scrollBar shouldn't respond on horizontal scrolling.
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        testedScrollBar.mouse().move();

        testedScrollBar.mouse().turnWheel(+1 * (Utils.isMacOS() ? -1 : 1));
        checkTextFieldValue(Properties.value, 50);

        testedScrollBar.mouse().turnWheel(-1 * (Utils.isMacOS() ? -1 : 1));
        checkTextFieldValue(Properties.value, 40);

        sendScrollEvent(scene, testedScrollBar, 50, 0);
        checkTextFieldValue(Properties.value, 40);

        sendScrollEvent(scene, testedScrollBar, -40, 0);
        checkTextFieldValue(Properties.value, 40);
    }

    @Smoke
    @Test(timeout = 300000)
    public void adjustValueTest() throws InterruptedException {
        Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();

        //Block - aboutclick over free space, Unit - about click on arrow.
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 50);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, 75);

        testedScrollBar.mouse().click();
        //knob in center
        clickLess(testedScrollBar);
        checkTextFieldValue(Properties.value, 0);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 150);
        testedScrollBar.mouse().click();
        checkTextFieldValue(Properties.value, 50);

        testedScrollBar.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 0);
        testedScrollBar.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, 100);
    }

    @Smoke
    @Test(timeout = 300000)
    public void scrolling2Test() {
        Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();
        AbstractScroll c = testedScrollBar.as(AbstractScroll.class);

        c.allowError(ASSERT_CMP_PRECISION / 2);

        c.caret().to(80);
        assertEquals(80, c.position(), ASSERT_CMP_PRECISION);

        c.caret().to(0);
        assertEquals(0, c.position(), ASSERT_CMP_PRECISION);

        c.caret().to(100);
        assertEquals(100, c.position(), ASSERT_CMP_PRECISION);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        initWrappers();

        AbstractScroll c1 = testedScrollBar.as(AbstractScroll.class);
        c1.caret().to(80);
        assertEquals(80, c1.position(), ASSERT_CMP_PRECISION);

        c1.caret().to(0);
        assertEquals(0, c1.position(), ASSERT_CMP_PRECISION);
    }

    @Smoke
    @Test(timeout = 300000)
    public void iterativeWorkingTest() throws InterruptedException {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();

        int iterations = 15;

        double expectedValue = 10;
        double min = -100;
        double max = 100;
        double unitIncrement = 5;
        double blockIncrement = 10;

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, blockIncrement);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, unitIncrement);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, min);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, max);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, expectedValue);

        for (Orientation orientationParameter : Orientation.values()) {
            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientationParameter, Properties.orientation);

            //reset, because they will be changed in circle.
            unitIncrement = 5;
            blockIncrement = 10;
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, blockIncrement);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, unitIncrement);

            for (int i = 0; i < iterations; i++) {
                checkTextFieldValue(Properties.value, expectedValue);

                for (int j = 0; j < iterations; j++) {
                    clickLess(testedScrollBar);
                    expectedValue = adjustValue(min, max, expectedValue - unitIncrement);
                    checkTextFieldValue(Properties.value, expectedValue);
                }

                for (int j = 0; j < iterations; j++) {
                    clickMore(testedScrollBar);
                    expectedValue = adjustValue(min, max, expectedValue + unitIncrement);
                    checkTextFieldValue(Properties.value, expectedValue);
                }

                expectedValue = min;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, min);

                testedScrollBar.mouse().click();
                expectedValue = adjustValue(min, max, expectedValue + blockIncrement);
                checkTextFieldValue(Properties.value, expectedValue);

                expectedValue = max;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, max);

                testedScrollBar.mouse().click();
                expectedValue = adjustValue(min, max, expectedValue - blockIncrement);
                checkTextFieldValue(Properties.value, expectedValue);

                unitIncrement += 1;
                blockIncrement += 2;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, blockIncrement);
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, unitIncrement);
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void focusTraversalTest() throws InterruptedException {
        Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();
        testedScrollBar.waitProperty("isFocused", false);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 50);
        testedScrollBar.mouse().click();
        testedScrollBar.waitProperty("isFocused", true);

        testedScrollBar.keyboard().pushKey(KeyboardButtons.DOWN);
        testedScrollBar.waitProperty("isFocused", false);

        testedScrollBar.mouse().click(); // knob must be in center
        testedScrollBar.waitProperty("isFocused", false);

        clickLess(testedScrollBar);
        clickMore(testedScrollBar);
        testedScrollBar.waitProperty("isFocused", false);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.unitIncrement, 70);
        clickLess(testedScrollBar);
        testedScrollBar.mouse().click();
        testedScrollBar.waitProperty("isFocused", true);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        if (currentSettingOption == SettingOption.PROGRAM) {
            testedScrollBar.keyboard().pushKey(KeyboardButtons.TAB);
        }

        clickMore(testedScrollBar);
        clickLess(testedScrollBar);
        testedScrollBar.waitProperty("isFocused", false);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 0);
        testedScrollBar.mouse().click();
        testedScrollBar.waitProperty("isFocused", true);

        testedScrollBar.keyboard().pushKey(KeyboardButtons.RIGHT);
        testedScrollBar.waitProperty("isFocused", false);

        clickLess(testedScrollBar);
        testedScrollBar.waitProperty("isFocused", false);

        testedScrollBar.mouse().click();
        testedScrollBar.waitProperty("isFocused", true);

        testedScrollBar.keyboard().pushKey(KeyboardButtons.DOWN);
        testedScrollBar.waitProperty("isFocused", true);

        testedScrollBar.keyboard().pushKey(KeyboardButtons.TAB);
        testedScrollBar.waitProperty("isFocused", false);

        clickLess(testedScrollBar);
        testedScrollBar.mouse().click();
        testedScrollBar.waitProperty("isFocused", true);

        testedScrollBar.keyboard().pushKey(KeyboardButtons.TAB, KeyboardModifiers.SHIFT_DOWN_MASK);
        testedScrollBar.waitProperty("isFocused", false);
    }

    @Smoke
    @Test(timeout = 300000)
    public void bigBidirectionalComposedTest() throws InterruptedException {
        for (SettingType type : SettingType.values()) {
            setUp();

            if (type == SettingType.UNIDIRECTIONAL) {
                setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
            }

            Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();

            setPropertyBySlider(type, Properties.prefWidth, 100);
            setPropertyBySlider(type, Properties.prefHeight, 100);
            setPropertyBySlider(type, Properties.blockIncrement, 7);
            setPropertyBySlider(type, Properties.unitIncrement, 20);
            setPropertyBySlider(type, Properties.min, -50);
            setPropertyBySlider(type, Properties.max, +150);
            setPropertyBySlider(type, Properties.value, -25);
            setPropertyBySlider(type, Properties.visibleAmount, 70);

            //Do some actions.
            testedScrollBar.mouse().click(); //java.lang.RuntimeException: A bound value cannot be set.
            clickLess(testedScrollBar);
            clickMore(testedScrollBar);

            checkTextFieldValue(Properties.max, 150);
            checkTextFieldValue(Properties.min, -50);
            checkTextFieldValue(Properties.value, -18);
            checkTextFieldValue(Properties.blockIncrement, 7);
            checkTextFieldValue(Properties.unitIncrement, 20);
            checkTextFieldValue(Properties.visibleAmount, 70);

            tearDown();
        }
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void screenShotCheckingInComplexConditionsTest() throws Throwable {
        for (SettingType type : SettingType.values()) {
            setUp();

            Wrap<? extends ScrollBar> testedScrollBar = parent.lookup(ScrollBar.class, new ByID<ScrollBar>(TESTED_SCROLLBAR_ID)).wrap();

            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, -25);
            setPropertyByChoiceBox(type, Orientation.HORIZONTAL, Properties.orientation);
            setPropertyBySlider(type, Properties.blockIncrement, 7);
            setPropertyBySlider(type, Properties.unitIncrement, 20);
            setPropertyBySlider(type, Properties.min, -50);
            setPropertyBySlider(type, Properties.max, +150);
            setPropertyBySlider(type, Properties.visibleAmount, 70);

            //Do some actions.
            testedScrollBar.mouse().click();
            clickLess(testedScrollBar);
            clickMore(testedScrollBar);

            checkScreenshot("ScrollBarTest", testedScrollBar);
            tearDown();
        }
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)// RT-18220 RT-18221
    public void exceptionOnNegativeValuesSettingTest() throws Exception {
        int exceptionsCounter = 0;

        try {
            ScrollBar sb1 = new ScrollBar();
            sb1.setBlockIncrement(-50);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                exceptionsCounter++;
            }
        }

        try {
            ScrollBar sb2 = new ScrollBar();
            DoubleProperty dp2 = new SimpleDoubleProperty(-50);
            sb2.blockIncrementProperty().bind(dp2);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                exceptionsCounter++;
            }
        }

        try {
            ScrollBar sb3 = new ScrollBar();
            DoubleProperty dp3 = new SimpleDoubleProperty(-50);
            sb3.blockIncrementProperty().bindBidirectional(dp3);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                exceptionsCounter++;
            }
        }

        try {
            ScrollBar sb4 = new ScrollBar();
            sb4.setUnitIncrement(-50);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                exceptionsCounter++;
            }
        }

        try {
            ScrollBar sb5 = new ScrollBar();
            DoubleProperty dp5 = new SimpleDoubleProperty(-50);
            sb5.unitIncrementProperty().bindBidirectional(dp5);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                exceptionsCounter++;
            }
        }

        try {
            ScrollBar sb6 = new ScrollBar();
            DoubleProperty dp6 = new SimpleDoubleProperty(-50);
            sb6.unitIncrementProperty().bind(dp6);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                exceptionsCounter++;
            }
        }

        if (exceptionsCounter != 0) {
            throw new Exception("Test on negative values failed, because there were " + exceptionsCounter + " exceptions, but must be 0.");
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void minGreatedThenMaxTest() {
        ScrollBar sb = new ScrollBar();
        sb.setMin(100);
        sb.setMax(0);

        sb.setValue(50);

        assertEquals(sb.getMin(), 100, ASSERT_CMP_PRECISION);
        assertEquals(sb.getMax(), 0, ASSERT_CMP_PRECISION);
    }
}
