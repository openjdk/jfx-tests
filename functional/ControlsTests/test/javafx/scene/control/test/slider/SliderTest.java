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
package javafx.scene.control.test.slider;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import static javafx.scene.control.test.slider.SliderNewApp.TESTED_SLIDER_ID;
import static javafx.scene.control.test.slider.SliderNewApp.VALUE_CHANGING_COUNTER;
import javafx.scene.control.test.slider.TestBase.Properties;
import static javafx.scene.control.test.slider.TestBase.testedControl;
import javafx.scene.control.test.util.ClickableTrack;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.SceneDock;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Shiftable;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class SliderTest extends TestBase {

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", 2000);
        Environment.getEnvironment().setTimeout("wait.control", 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @Smoke
    @Test(timeout = 300000)
    public void simpleValueSettingTest() throws InterruptedException {
        assertEquals((new Slider()).valueProperty().getValue(), 0, ASSERT_CMP_PRECISION);//initial value

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 70);
        checkTextFieldValue(Properties.value, 70);

        testedControl.mouse().click(1, new Point(testedControl.getClickPoint()).translate(-1, 0));
        checkTextFieldValue(Properties.value, 50);

        testedControl.keyboard().pushKey(KeyboardButtons.END);
        checkTextFieldValue(Properties.value, 100);

        testedControl.keyboard().pushKey(KeyboardButtons.HOME);
        checkTextFieldValue(Properties.value, 0);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.value, 30);
        checkTextFieldValue(Properties.value, 30);
    }

    @Smoke
    @Test(timeout = 300000)
    public void maxPropertyTest() throws InterruptedException {
        assertEquals((new Slider()).maxProperty().getValue(), 100, ASSERT_CMP_PRECISION);//initial value

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, -10);
        checkTextFieldValue(Properties.value, -10);
        checkTextFieldValue(Properties.min, -10);
        checkTextFieldValue(Properties.max, -10);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, 150);
        setSliderPosition(TESTED_SLIDER_ID, 130, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 130);

        testedControl.keyboard().pushKey(KeyboardButtons.END);
        checkTextFieldValue(Properties.value, 150);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.max, 30);
        checkTextFieldValue(Properties.min, -10);
        checkTextFieldValue(Properties.max, 30);
        checkTextFieldValue(Properties.value, 30);
    }

    @Smoke
    @Test(timeout = 300000)
    public void minPropertyTest() throws InterruptedException {
        assertEquals((new Slider()).minProperty().getValue(), 0, ASSERT_CMP_PRECISION);//initial value

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, 150);
        checkTextFieldValue(Properties.value, 150);
        checkTextFieldValue(Properties.min, 150);
        checkTextFieldValue(Properties.max, 150);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, 100);
        setSliderPosition(TESTED_SLIDER_ID, 100, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 100);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, -100);
        requestFocusOnControl(testedControl);
        testedControl.keyboard().pushKey(KeyboardButtons.HOME);
        checkTextFieldValue(Properties.value, -100);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.min, -50);
        checkTextFieldValue(Properties.min, -50);
        checkTextFieldValue(Properties.value, 100);//100, because initial value of slider in unidirectional binding is 100.
        checkTextFieldValue(Properties.max, 150);
    }

    @Smoke
    @Test(timeout = 300000)//should fail.
    public void snapToTicksTest() throws InterruptedException {
        assertEquals((new Slider()).snapToTicksProperty().getValue(), false);//initial value

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.majorTickUnit, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minorTickCount, 1);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, -100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 10);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 20);
        checkTextFieldValue(Properties.value, 20);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 190);
        checkTextFieldValue(Properties.value, 190);

        setSliderPosition(TESTED_SLIDER_ID, -60, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, -60);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.snapToTicks);//switch on

        setSliderPosition(TESTED_SLIDER_ID, 120, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 100);

        setSliderPosition(TESTED_SLIDER_ID, -80, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, -100);

        testedControl.keyboard().pushKey(KeyboardButtons.HOME);
        checkTextFieldValue(Properties.value, -100);

        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, -50);

        testedControl.keyboard().pushKey(KeyboardButtons.END);
        checkTextFieldValue(Properties.value, 200);

        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 150);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 35);
        checkTextFieldValue(Properties.value, 50);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.value, 55);
        checkTextFieldValue(Properties.value, 50);

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.snapToTicks);//switch on
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.snapToTicks);//switch off

        setSliderPosition(TESTED_SLIDER_ID, -80, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, -80);

        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, -70);

        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, -80);
    }

    @Smoke
    @Test(timeout = 300000)
    public void snapToTicksMouseTest() throws InterruptedException {
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.snapToTicks);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.majorTickUnit, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minorTickCount, 3);

        setSliderPosition(TESTED_SLIDER_ID, 60, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 50);

        setSliderPosition(TESTED_SLIDER_ID, 10, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 0);

        setSliderPosition(TESTED_SLIDER_ID, 85, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 75);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.majorTickUnit, 50);

        setSliderPosition(TESTED_SLIDER_ID, 5, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 0);

        setSliderPosition(TESTED_SLIDER_ID, 55, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 50);

        setSliderPosition(TESTED_SLIDER_ID, 75, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 75);
    }

    @Smoke
    @Test(timeout = 300000)//Should fail.
    public void valueChangingOnBidirectionalTest() throws InterruptedException {
        assertEquals((new Slider()).valueChangingProperty().getValue(), false);

        checkCounterValue(VALUE_CHANGING_COUNTER, 0);
        setSliderPosition(TESTED_SLIDER_ID, 30, SettingOption.MANUAL);
        checkCounterValue(VALUE_CHANGING_COUNTER, 1);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 70);
        checkCounterValue(VALUE_CHANGING_COUNTER, 2);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.value, 40);
        checkCounterValue(VALUE_CHANGING_COUNTER, 3);
    }

    @Smoke
    @Test(timeout = 300000)//Should fail.
    public void majorTickUnitTest() throws InterruptedException {
        assertEquals((new Slider()).majorTickUnitProperty().getValue(), 25, ASSERT_CMP_PRECISION);//initial value test

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.snapToTicks);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minorTickCount, 0);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.majorTickUnit, 50);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 45);
        checkTextFieldValue(Properties.value, 50);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.majorTickUnit, 80);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 75);
        checkTextFieldValue(Properties.value, 80);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.majorTickUnit, 30);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 3);
        checkTextFieldValue(Properties.value, 0);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.majorTickUnit, 30);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 23);
        checkTextFieldValue(Properties.value, 30);

        testedControl.mouse().click();//to 50
        checkTextFieldValue(Properties.value, 60);

        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 90);

        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, 90);

        testedControl.keyboard().pushKey(KeyboardButtons.HOME);
        checkTextFieldValue(Properties.value, 0);

        testedControl.keyboard().pushKey(KeyboardButtons.END);
        checkTextFieldValue(Properties.value, 90);
    }

    @Smoke
    @Test(timeout = 300000)//Should fail.
    public void minorTickCountTest() throws InterruptedException {
        assertEquals((new Slider()).minorTickCountProperty().getValue(), 3, ASSERT_CMP_PRECISION);//initial value test

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.snapToTicks);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.majorTickUnit, 100);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minorTickCount, 1);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 65);
        checkTextFieldValue(Properties.value, 50);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minorTickCount, 3);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 15);
        checkTextFieldValue(Properties.value, 25);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.minorTickCount, 19);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 35);
        checkTextFieldValue(Properties.value, 35);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.minorTickCount, -5);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 19);
        checkTextFieldValue(Properties.value, 19);
    }

    @Smoke
    @Test(timeout = 300000)
    public void blockIncrementTest() throws InterruptedException {
        assertEquals((new Slider()).blockIncrementProperty().getValue(), 10, ASSERT_CMP_PRECISION);//initial value

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 30);

        testedControl.mouse().click();
        checkTextFieldValue(Properties.value, 50);

        setSliderPosition(TESTED_SLIDER_ID, 70, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 70);

        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, 100);
        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 40);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, -20);

        testedControl.mouse().click();
        checkTextFieldValue(Properties.value, 50);

        setSliderPosition(TESTED_SLIDER_ID, 20, SettingOption.MANUAL);
        checkTextFieldValue(Properties.value, 20);

        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, 0);
        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 40);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)//RT-18448
    public void labelFormatterTest() throws InterruptedException {
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.showTickLabels);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.showTickMarks);

        applyCustomLabelFormatter();

        //Check applicating.
        checkScreenshot("Slider_customLabelFomatter_1", testedControl);

        //Change control all states.
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 8);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.majorTickUnit, 50);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minorTickCount, 6);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, -50);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, +150);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 50);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.snapToTicks);

        //Check after changing all
        checkScreenshot("Slider_customLabelFomatter_2", testedControl);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyEventsArrowsTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 30);

        Wrap<? extends Slider> testedSlider = parent.lookup(Slider.class, new ByID<Slider>(TESTED_SLIDER_ID)).wrap();

        testedSlider.mouse().click();
        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);

        testedSlider.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, 80);

        testedSlider.keyboard().pushKey(KeyboardButtons.DOWN);
        checkTextFieldValue(Properties.value, 80);
        requestFocusOnControl(testedControl);
        testedSlider.keyboard().pushKey(KeyboardButtons.UP);
        checkTextFieldValue(Properties.value, 80);

        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);
        testedSlider.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 20);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);
        testedSlider.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 50);
        testedSlider.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, 50);

        testedSlider.mouse().click();
        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);
        testedSlider.keyboard().pushKey(KeyboardButtons.DOWN);
        checkTextFieldValue(Properties.value, 20);

        testedSlider.keyboard().pushKey(KeyboardButtons.UP);
        checkTextFieldValue(Properties.value, 50);
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyEventsDifferentButtonsTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.blockIncrement, 30);

        Wrap<? extends Slider> testedSlider = parent.lookup(Slider.class, new ByID<Slider>(TESTED_SLIDER_ID)).wrap();

        testedSlider.mouse().click();
        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);

        testedSlider.keyboard().pushKey(KeyboardButtons.END);
        checkTextFieldValue(Properties.value, 100);

        testedSlider.keyboard().pushKey(KeyboardButtons.HOME);
        checkTextFieldValue(Properties.value, 0);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        testedSlider.mouse().click();
        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);

        testedSlider.keyboard().pushKey(KeyboardButtons.END);
        checkTextFieldValue(Properties.value, 100);

        testedSlider.keyboard().pushKey(KeyboardButtons.HOME);
        checkTextFieldValue(Properties.value, 0);

        testedSlider.keyboard().pushKey(KeyboardButtons.SPACE);
        checkTextFieldValue(Properties.value, 0);
    }

    @Smoke
    @Test(timeout = 300000)//Should fail.
    public void bigBidirectionalComposedTest() throws InterruptedException {
        for (SettingType type : SettingType.values()) {
            setUp();

            if (type == SettingType.UNIDIRECTIONAL) {
                setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
            }

            Wrap<? extends Slider> testedSlider = parent.lookup(Slider.class, new ByID<Slider>(TESTED_SLIDER_ID)).wrap();

            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
            setPropertyBySlider(type, Properties.blockIncrement, 7);
            setPropertyBySlider(type, Properties.majorTickUnit, 50);
            setPropertyBySlider(type, Properties.minorTickCount, 4);
            setPropertyBySlider(type, Properties.min, -50);
            setPropertyBySlider(type, Properties.max, +150);
            setPropertyBySlider(type, Properties.value, 50);
            setPropertyByToggleClick(type, Properties.snapToTicks);

            testedSlider.mouse().click();
            KeyboardButtons moreKey = type == SettingType.BIDIRECTIONAL ? KeyboardButtons.RIGHT : KeyboardButtons.DOWN;
            testedSlider.keyboard().pushKey(moreKey);
            checkTextFieldValue(Properties.value, 60);

            checkTextFieldText(Properties.snapToTicks, "true");
            checkTextFieldValue(Properties.max, 150);
            checkTextFieldValue(Properties.min, -50);
            checkTextFieldValue(Properties.majorTickUnit, 50);
            checkTextFieldValue(Properties.minorTickCount, 4);
            checkTextFieldValue(Properties.blockIncrement, 8);

            tearDown();
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseClickingTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 220);
        ClickableTrack track = new ClickableTrack(testedControl.as(Shiftable.class), 1);
        testClick(100, track);
        testClick(50, track);
        testClick(1, track);
        testClick(25, track);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, -50);

        testClick(199, track);
        testClick(-48, track);
        testClick(0, track);
        testClick(150, track);
        testClick(-25, track);
    }

    @Smoke
    @Test(timeout = 300000)
    public void mouseDragingTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        Rectangle rect = testedControl.getScreenBounds();

        Point center = new Point(rect.width / 2, rect.height / 2);
        Point right = new Point(rect.width / 2 + 150, rect.height / 2);
        Point up = new Point(rect.width / 2, rect.height / 2 + 150);
        Point left = new Point(rect.width / 2 - 150, rect.height / 2);
        Point down = new Point(rect.width / 2, rect.height / 2 - 150);

        NodeDock slider = new NodeDock(new SceneDock().asParent(), new ByID(TESTED_SLIDER_ID));

        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);
        slider.drag().dnd(center, slider.wrap(), right);
        checkTextFieldValue(Properties.value, 100);

        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);
        slider.drag().dnd(center, slider.wrap(), up);
        checkTextFieldValue(Properties.value, 50);

        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);
        slider.drag().dnd(center, slider.wrap(), down);
        checkTextFieldValue(Properties.value, 50);

        setSliderPosition(TESTED_SLIDER_ID, 50, SettingOption.MANUAL);
        slider.drag().dnd(center, slider.wrap(), left);
        checkTextFieldValue(Properties.value, 0);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)// should fail
    public void screenShotCheckingInComplexConditionsTest() throws Throwable {
        for (SettingType type : SettingType.values()) {
            setUp();

            if (type == SettingType.UNIDIRECTIONAL) {//for intersection.
                setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
            }

            setPropertyByToggleClick(type, Properties.showTickLabels);
            setPropertyByToggleClick(type, Properties.showTickMarks);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
            setPropertyBySlider(type, Properties.blockIncrement, 8);
            setPropertyBySlider(type, Properties.majorTickUnit, 50);
            setPropertyBySlider(type, Properties.minorTickCount, 6);
            setPropertyBySlider(type, Properties.min, -50);
            setPropertyBySlider(type, Properties.max, +150);
            setPropertyBySlider(type, Properties.value, 50);
            setPropertyByToggleClick(type, Properties.snapToTicks);

            checkScreenshot("Slider_compositScreenshot_" + type, testedControl);

            tearDown();
        }
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void adjustValueTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 50);
        ClickableTrack track = new ClickableTrack(testedControl.as(Shiftable.class), 1);

        track.click(50);
        checkTextFieldValue(Properties.value, 50);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, 150);
        track.click(50);
        checkTextFieldValue(Properties.value, 50);

        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        checkTextFieldValue(Properties.value, 0);

        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        checkTextFieldValue(Properties.value, 100);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        track.click(50);

        testedControl.keyboard().pushKey(KeyboardButtons.UP);
        checkTextFieldValue(Properties.value, 100);

        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
        checkTextFieldValue(Properties.value, 0);
    }

    @Smoke
    @Test(timeout = 300000)
    public void ScrollingTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, -50);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, 150);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        double[] values = {100, 30, 50, -50, 150, 50};

        for (Orientation orientation : Orientation.values()) {
            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientation, Properties.orientation);
            AbstractScroll c = testedControl.as(AbstractScroll.class);
            c.allowError(ASSERT_CMP_PRECISION / 2);
            for (double value : values) {
                c.caret().to(value);
                assertEquals(value, c.position(), ASSERT_CMP_PRECISION);
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void iterativeWorkingTest() throws InterruptedException {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 200);

        int iterations = 5;

        double expectedValue = 10;
        final double min = -50;
        final double max = 50;

        for (Orientation orientation : Orientation.values()) {
            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientation, Properties.orientation);

            double blockIncrement = 5;

            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.min, min);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, max);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, expectedValue);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, blockIncrement);

            KeyboardButtons lessKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.LEFT : KeyboardButtons.DOWN);
            KeyboardButtons moreKey = (orientation == Orientation.HORIZONTAL ? KeyboardButtons.RIGHT : KeyboardButtons.UP);

            for (int i = 0; i < iterations; i++) {
                checkTextFieldValue(Properties.value, expectedValue);

                requestFocusOnControl(testedControl);

                for (int j = 0; j < iterations; j++) {
                    testedControl.keyboard().pushKey(lessKey);
                    expectedValue = adjustValue(min, max, expectedValue - blockIncrement);
                    checkTextFieldValue(Properties.value, expectedValue);
                }

                for (int j = 0; j < iterations; j++) {
                    testedControl.keyboard().pushKey(moreKey);
                    expectedValue = adjustValue(min, max, expectedValue + blockIncrement);
                    checkTextFieldValue(Properties.value, expectedValue);
                }

                expectedValue = max;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, max);

                testedControl.as(AbstractScroll.class).to(min);

                expectedValue = min;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, min);

                testedControl.mouse().click();
                expectedValue = adjustValue(min, max, min + (max - min) / 2);
                checkTextFieldValue(Properties.value, expectedValue);

                expectedValue = min;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, min);

                testedControl.as(AbstractScroll.class).to(max);

                expectedValue = max;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, max);

                testedControl.mouse().click();
                expectedValue = adjustValue(min, max, min + (max - min) / 2);
                checkTextFieldValue(Properties.value, expectedValue);

                blockIncrement += 3;
                setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.blockIncrement, blockIncrement);
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void focusTraversalTest() throws InterruptedException {
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.VERTICAL, Properties.orientation);

        testedControl.waitProperty("isFocused", false);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 0);
        testedControl.mouse().click();
        testedControl.waitProperty("isFocused", true);

        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        testedControl.waitProperty("isFocused", false);

        testedControl.waitProperty("isFocused", false);

        testedControl.mouse().click();
        testedControl.waitProperty("isFocused", true);

        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
        testedControl.waitProperty("isFocused", true);

        testedControl.keyboard().pushKey(KeyboardButtons.UP);
        testedControl.waitProperty("isFocused", true);

        testedControl.keyboard().pushKey(KeyboardButtons.TAB);
        testedControl.waitProperty("isFocused", false);

        testedControl.mouse().click();
        testedControl.waitProperty("isFocused", true);

        testedControl.keyboard().pushKey(KeyboardButtons.TAB, KeyboardModifiers.SHIFT_DOWN_MASK);
        testedControl.waitProperty("isFocused", false);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.focusTraversable);
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.focusTraversable);//should be off.
        testedControl.mouse().click();

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Orientation.HORIZONTAL, Properties.orientation);
        testedControl.waitProperty("isFocused", currentSettingOption == SettingOption.PROGRAM ? true : false);

        testedControl.mouse().click();
        testedControl.waitProperty("isFocused", true);

        testedControl.keyboard().pushKey(KeyboardButtons.DOWN);
        testedControl.waitProperty("isFocused", false);

        testedControl.mouse().click(); // knob must be in center
        testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
        testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
        testedControl.waitProperty("isFocused", true);
    }

    /**
     * Tests, that value can be changed with dragging.
     */
    @Test(timeout = 30000)//RT-29414
    @Smoke
    public void knobDragTest() throws InterruptedException {
        final int width = 200, height = 200;
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, width);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, height);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.max, 100);

        for (Orientation orientation : Orientation.values()) {
            setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, orientation, Properties.orientation);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.value, 0);
            checkTextFieldValue(Properties.value, 0);
            waitKnobRelativePosition(0);

            testedControl.mouse().move(new Point(width / 2, height / 2));
            testedControl.mouse().press();
            checkTextFieldValue(Properties.value, 50);
            waitKnobRelativePosition(0.5);

            testedControl.mouse().move(new Point(width, height));
            checkTextFieldValue(Properties.value, 100);
            waitKnobRelativePosition(1.0);

            testedControl.mouse().move(new Point(0, 0));
            checkTextFieldValue(Properties.value, 0);
            waitKnobRelativePosition(0);

            testedControl.mouse().release();
            checkTextFieldValue(Properties.value, 0);
            waitKnobRelativePosition(0);
        }
    }
}