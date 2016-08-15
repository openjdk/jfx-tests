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
package javafx.scene.control.test.colorpicker;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import com.oracle.jdk.sqe.cc.markup.Covers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.test.mix.PopupMenuTest;
import static javafx.scene.control.test.colorpicker.ColorPickerApp.predefinedColors;
import static javafx.scene.control.test.colorpicker.TestBase.testedControl;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.JemmyUtils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class ColorPickerTest extends TestBase {

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void initialControlAppearanceTest() throws Throwable {
        checkScreenshot("ColorPicker-InitialAppearance", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void initialPopupAppearanceTest() throws Throwable {
        setPropertyBySlider(SettingType.SETTER, Properties.translatex, 10);
        clickControl();
        Lookup allColorsInPopup = getPopupPaleteLookup();
        int size = allColorsInPopup.size();
        allColorsInPopup.wrap(size / 4).mouse().click();
        clickControl();
        allColorsInPopup.wrap(size / 2).mouse().move();
        Thread.sleep(3000);//wait for tooltip appearance
        checkScreenshot("ColorPicker-InitialPopupAppearanceWithTooltipWithSelectionWithFocus", getPopupWrap());
        clickControl();
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void initialCustomColorPopupWindowAppearanceTest() throws Throwable {
        setJemmyComparatorByDistance(0.005f);
        clickControl();
        clickCustomColorPopupButton();
        checkScreenshot("ColorPicker-InitialCustomColorPopupAppearance", getCustomColorPopupSceneWrap());
        closeCustomColorPopupModalWindow();
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    /*
     * This test checks visual appearance of RGB tab in custom color popup (with
     * screenshot).
     *
     * Test appeared after issue with TextInput in this tab - it was too big,
     * which appeared in 8.0b01.
     */
    public void initialCustomColorPopupWindowRGBTabAppearanceTest() throws Throwable {
        setJemmyComparatorByDistance(0.005f);
        clickControl();
        clickCustomColorPopupButton();
        clickColorToggle(ColorOption.RGB);
        checkScreenshot("ColorPicker-InitialCustomColorPopupRGBTabAppearance", getCustomColorPopupSceneWrap());
        closeCustomColorPopupModalWindow();
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    /*
     * This test appears like previous one, to check visual appearance of tab,
     * where you can set custom color with WEB-form of setting.
     */
    public void initialCustomColorPopupWindowWebTabAppearanceTest() throws Throwable {
        setJemmyComparatorByDistance(0.005f);
        clickControl();
        clickCustomColorPopupButton();
        clickColorToggle(ColorOption.WEB);
        checkScreenshot("ColorPicker-InitialCustomColorPopupWEBTabAppearance", getCustomColorPopupSceneWrap());
        closeCustomColorPopupModalWindow();
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void appearanceWhenResizedTest() throws InterruptedException, Throwable {
        setJemmyComparatorByDistance(0.005f);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 0);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 0);
        checkScreenshot("ColorPicker-ResizeControlAppearance-size_0_0", testedControl);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 150);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 150);
        checkScreenshot("ColorPicker-ResizeControlAppearance-size_150_150", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void choseColorFromPopupTest() throws InterruptedException {
        checkAllColorsOnChosability(initialColorsAmountInPopup);
    }

    @Smoke
    @Test(timeout = 300000)
    public void checkColorAmountTest() throws InterruptedException {
        checkColorAmountInPopup(initialColorsAmountInPopup);
    }

    @Smoke
    @Test(timeout = 300000)
    public void popupAppearancePoliticsTest() throws InterruptedException {
        assertFalse(isPopupVisible());

        clickControl();

        assertTrue(isPopupVisible());

        clickCustomColorPopupButton();

        assertTrue(isPopupVisible());

        clickSave();

        assertFalse(isPopupVisible());//Ask Jindra and look at spec.

        clickControl();
        clickCustomColorPopupButton();
        clickCancel();

        assertTrue(isPopupVisible());

        clickCustomColorPopupButton();
        clickUse();

        assertFalse(isPopupVisible());

        clickControl();
        clickCustomColorPopupButton();
        selectColorFromRainbowPallete(0.5);
        selectColorFromRectanglePallete(0.5, 0.5);

        clickColorToggle(ColorOption.WEB);
        clickColorToggle(ColorOption.HSB);
        clickColorToggle(ColorOption.RGB);

        assertTrue(isPopupVisible());
        closeCustomColorPopupModalWindow();

        clickControl();
        moveStage(100, 100);
        assertFalse(isPopupVisible());
    }

    @Smoke
    @Test(timeout = 300000)
    public void customColorAddingTest() throws InterruptedException {
        int colorNumberToAdd = 26;
        addCustomColors(colorNumberToAdd, 0.21);
        checkAllColorsOnChosability(initialColorsAmountInPopup + colorNumberToAdd);
        checkColorAmountInPopup(120 + colorNumberToAdd + (widthInColorGrid - colorNumberToAdd % widthInColorGrid) % widthInColorGrid);
        checkCustomColorsGetterCorrectness();
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void popupAppearanceAfterCustomColorsAddingTest() throws InterruptedException, Throwable {
        setJemmyComparatorByDistance(0.001f);
        int colorNumberToAdd = 26;
        addCustomColors(colorNumberToAdd, 0.15);
        checkAllColorsOnChosability(initialColorsAmountInPopup + colorNumberToAdd);
        clickControl();
        checkScreenshot("ColorPicker-customColorsAdded", getPopupWrap());
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void currentColorInCustomColorPopupTest() throws InterruptedException {
        clickControl();
        clickCustomColorPopupButton();
        Wrap<? extends Region> rect = getShapeWrap(ShapeType.CURRENT_COLOR);
        assertTrue(getFill(rect).equals(getCurrentColorValue()));
        selectCustomColor(0.5, 0.5, 0.5, true);
        clickControl();
        clickCustomColorPopupButton();
        rect = getShapeWrap(ShapeType.CURRENT_COLOR);
        assertTrue(getFill(rect).equals(getCurrentColorValue()));
        selectCustomColor(0.5, 0.5, 0.5, false);
        clickControl();
        clickCustomColorPopupButton();
        rect = getShapeWrap(ShapeType.CURRENT_COLOR);
        assertTrue(getFill(rect).equals(getCurrentColorValue()));
    }

    @Smoke
    @Test(timeout = 300000)
    public void newColorInCustomColorPopupTest() throws InterruptedException {
        selectColorFromRainbowPallete(0.5);
        javafx.scene.paint.Color selectedColor = selectColorFromRectanglePallete(0.5, 0.5);
        assertTrue(colorDistance(getNewColor(), selectedColor) <= 3.5);

        selectColorFromRectanglePallete(1.0, 0);
        selectedColor = selectColorFromRainbowPallete(0.7);

        assertTrue(colorDistance(getNewColor(), selectedColor) < 10);

        setHSB(50, 50, 50, 50);
        assertTrue(colorDistance(getNewColor(), javafx.scene.paint.Color.hsb(50, 0.5, 0.5, 0.5)) < 10);

        setRGB(50, 50, 50, 60);
        assertTrue(colorDistance(getNewColor(), javafx.scene.paint.Color.rgb(50, 50, 50, 0.6)) < 10);

        setWebColor("#abbccd", 50);
        assertTrue(colorDistance(getNewColor(), javafx.scene.paint.Color.web("#abbccd", 0.5)) < 10);
        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void initialStateOfCurrentAndNewColor() throws InterruptedException {
        selectColorFromRainbowPallete(0.5);
        selectColorFromRectanglePallete(0.5, 0.5);
        clickUse();
        clickControl();
        clickCustomColorPopupButton();
        assertTrue(colorDistance(getNewColor(), getCurrentColor()) == 0);
        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void correctCirclePositionOnSettingCustomColor() throws InterruptedException {
        clickControl();
        clickCustomColorPopupButton();

        setHSB(50, 50, 50, 100);
        assertTrue(colorDistance(getColorUnderCircle(), javafx.scene.paint.Color.hsb(50, 0.5, 0.5, 1.0)) < 10);

        setRGB(100, 50, 100, 100);
        assertTrue(colorDistance(getColorUnderCircle(), javafx.scene.paint.Color.rgb(100, 50, 100, 1.0)) < 10);

        setWebColor("#EEAAEE", 100);
        assertTrue(colorDistance(getColorUnderCircle(), javafx.scene.paint.Color.web("#eeaaee", 1.0)) < 10);
    }

    @Smoke
    @Test(timeout = 300000)
    public void gradientsCorrectnessTest() throws InterruptedException {
        clickControl();
        clickCustomColorPopupButton();
        for (double parameter = 0.0; parameter < 1.0; parameter += 0.13) {
            selectColorFromRainbowPallete(parameter);
            getShapeWrap(ShapeType.SQUARE_GRADIENT).mouse().click();
            checkCorrectnessOfSquareRectangleGradient();
        }
        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void correctColorPropagatingFromInitialTest() throws InterruptedException {
        clickControl();
        clickCustomColorPopupButton();
        selectColorFromRainbowPallete(0.01);
        selectColorFromRectanglePallete(0.01, 0.01);

        Color actualColor = getCurrentColorValue();//But actually, we don't need this.

        checkCorrectnessOfColorsInRectangles();
        assertTrue(colorDistance(getRGB(), actualColor) < 5);
        assertTrue(colorDistance(getNewColor(), actualColor) < 5);
        assertTrue(colorDistance(getHSB(), actualColor) < 5);
        assertTrue(colorDistance(getWebColor(), actualColor) < 5);

        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void correctColorPropagatingFromRainbowTest() throws InterruptedException {
        Color initialColor = getCurrentColorValue();
        clickControl();
        selectColorFromRectanglePallete(0.4, 0.3);
        selectColorFromRainbowPallete(0.6);

        checkCorrectnessOfSquareRectangleGradient();

        Color colorUnderCircle = getColorUnderCircle();

        assertTrue(colorDistance(getRGB(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getHSB(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getWebColor(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getCurrentColor(), initialColor) < 5);
        assertTrue(colorDistance(getCurrentColorValue(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getNewColor(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);

        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void correctColorPropagatingFromSquareTest() throws InterruptedException {
        Color initialColor = getCurrentColorValue();
        clickControl();
        clickCustomColorPopupButton();
        selectColorFromRainbowPallete(0.1);
        selectColorFromRectanglePallete(0.4, 0.3);

        checkCorrectnessOfSquareRectangleGradient();

        Color colorUnderCircle = getColorUnderCircle();

        assertTrue(colorDistance(getRGB(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getHSB(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getWebColor(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getCurrentColor(), initialColor) < 5);
        assertTrue(colorDistance(getCurrentColorValue(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getNewColor(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);

        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void correctColorPropagatingFromRGBTest() throws InterruptedException {
        Color initialColor = getCurrentColorValue();
        clickControl();
        clickCustomColorPopupButton();
        setRGB(15, 40, 9);

        Color rgb = getRGB();

        checkCorrectnessOfSquareRectangleGradient();

        Color colorUnderCircle = getColorUnderCircle();

        assertTrue(colorDistance(getRGB(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getHSB(), rgb) < 5);
        assertTrue(colorDistance(getWebColor(), rgb) < 5);
        assertTrue(colorDistance(getCurrentColor(), initialColor) < 5);
        assertTrue(colorDistance(getCurrentColorValue(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getNewColor(), rgb) < 5);

        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void correctColorPropagatingFromHSBTest() throws InterruptedException {
        Color initialColor = getCurrentColorValue();
        clickControl();
        clickCustomColorPopupButton();
        setHSB(50, 50, 50, 100);

        Color hsb = getHSB();

        checkCorrectnessOfSquareRectangleGradient();

        Color colorUnderCircle = getColorUnderCircle();

        assertTrue(colorDistance(hsb, colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getRGB(), hsb) < 5);
        assertTrue(colorDistance(getWebColor(), hsb) < 5);
        assertTrue(colorDistance(getCurrentColor(), initialColor) < 5);
        assertTrue(colorDistance(getCurrentColorValue(), colorUnderCircle) < INDISTINGUISHABLE_COLOR_DISTANCE);
        assertTrue(colorDistance(getNewColor(), hsb) < 5);

        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void correctColorPropagatingFromWebTest() throws InterruptedException {
        Color initialColor = getCurrentColorValue();
        clickControl();
        clickCustomColorPopupButton();
        setWebColor("#B1AAEE", 100);

        Color web = getWebColor();

        Color colorUnderCircle = getColorUnderCircle();

        assertTrue(colorDistance(web, colorUnderCircle) < 10);
        assertTrue(colorDistance(getHSB(), web) < 5);
        assertTrue(colorDistance(getRGB(), web) < 5);
        assertTrue(colorDistance(getCurrentColor(), initialColor) < 5);
        assertTrue(colorDistance(getNewColor(), web) < 5);
        assertTrue(colorDistance(getCurrentColorValue(), colorUnderCircle) < 5);

        closeCustomColorPopupModalWindow();
    }

    @Smoke
    @Test(timeout = 300000)
    public void colorSavingTest() throws InterruptedException {
        Color oldColor = getCurrentColorValue();
        selectColorFromRectanglePallete(0.5, 0.5);
        Color color = getRGB();

        assertTrue(isPopupVisible());
        assertTrue(isCustomColorPopupVisible());

        clickSave();

        assertFalse(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());

        checkColorAmountInPopup(initialColorsAmountInPopup + widthInColorGrid);
        checkCustomColorAmountProgrammly(1);

        Color newColor = getCurrentColorValue();

        assertTrue(colorDistance(newColor, color) < 1);//Equals
        assertTrue(colorDistance(newColor, oldColor) > 1);//Not equals
    }

    @Smoke
    @Test(timeout = 300000)
    public void colorUsingTest() throws InterruptedException {
        Color oldColor = getCurrentColorValue();
        selectColorFromRectanglePallete(0.5, 0.5);

        Color color = getRGB();

        assertTrue(isPopupVisible());
        assertTrue(isCustomColorPopupVisible());

        clickUse();

        assertFalse(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());

        Color newColor = getCurrentColorValue();

        checkColorAmountInPopup(initialColorsAmountInPopup);

        assertTrue(colorDistance(newColor, color) < 1);
        assertTrue(colorDistance(newColor, oldColor) > 1);
    }

    @Smoke
    @Test(timeout = 300000)
    public void cancelCustomColorChosingTest() throws InterruptedException {
        standartCustomColorChosingPopupDismissTest();
    }

    @Smoke
    @Test(timeout = 900000)
    public void keyboardArrowHorizontalNavigationInPopupTest() throws InterruptedException {
        addCustomColors(10, 0.21);
        clickControl();
        clickRectangle(0, 0);
        Color controlColor = getCurrentColorValue();
        clickControl();
        moveOnRectangle(0, 0);
        for (KeyboardButtons key : EnumSet.of(KeyboardButtons.LEFT, KeyboardButtons.RIGHT)) {
            int direction = key.equals(KeyboardButtons.RIGHT) ? +1 : -1;
            int counter = 0;
            for (int i = 0; i < initialColorsAmountInPopup; i++) {
                getPopupWrap().keyboard().pushKey(key);
                counter += direction;
                if (counter < 0) {
                    counter += initialColorsAmountInPopup;
                }
                int estimatedX = counter % widthInColorGrid;
                int estimatedY = counter / widthInColorGrid % (initialColorsAmountInPopup / widthInColorGrid);
                checkSelectionAndFocusPosition(0, 0, estimatedX, estimatedY);
                checkCurrentColorValue(controlColor);
            }
        }
    }

    @Smoke
    @Test(timeout = 900000)
    public void keyboardArrowVerticalNavigationInPopupTest() throws InterruptedException {
        addCustomColors(10, 0.21);
        clickControl();
        clickRectangle(0, 0);
        Color controlColor = getCurrentColorValue();
        clickControl();
        moveOnRectangle(0, 0);
        Set intSet = new HashSet(widthInColorGrid);
        intSet.add((Integer) 0);
        intSet.add((Integer) widthInColorGrid - 1);
        for (Object temp : intSet.toArray()) {
            Integer xCoord = (Integer) temp;
            int shift = 0;
            if (xCoord == 0) {
                shift = -20;
            } else if (xCoord == (widthInColorGrid - 1)) {
                shift = 20;
            }

            //scroll to selected rect and it will grab focus
            moveOnRectangle(xCoord, heightInColorGrid / 2);
            //scroll aside from selected cell
            //focus remains, but mouse cursor doesn't impact test execution
            moveOnRectangle(xCoord, heightInColorGrid / 2, shift);

            for (KeyboardButtons key : EnumSet.of(KeyboardButtons.UP, KeyboardButtons.DOWN)) {
                int direction = key.equals(KeyboardButtons.DOWN) ? +1 : -1;
                int counter = heightInColorGrid / 2;
                for (int i = 0; i < heightInColorGrid; i++) {
                    getPopupWrap().keyboard().pushKey(key);
                    counter += direction;
                    if (counter < 0) {
                        counter += heightInColorGrid;
                    }
                    int estimatedX = xCoord;
                    int estimatedY = counter % heightInColorGrid;
                    checkSelectionAndFocusPosition(0, 0, estimatedX, estimatedY);
                    checkCurrentColorValue(controlColor);
                }
            }
        }
    }

    @Test(timeout = 300000)
    public void keyboardTabShiftTabInPopupTest() {
        //RT-21549
        assertTrue(false);//doesn't work and need comments
    }

    @Test(timeout = 300000)
    @Smoke
    /*
     * When custom color is added into pallete popup, it can be removed, by
     * clicking on it with right mouse button, and chosing remove action from
     * context menu.
     */
    public void deleteActionInPalletePopupTest() throws InterruptedException {
        //Step 1. It works only for custom colors.
        selectCustomColor(0.5, 0.5, 0.5, true);
        assertEquals(getCustomColorsCountFromControl(), 1, 0);

        showPopup();
        Lookup allColorsInPopup = getPopupPaleteLookup();
        assertEquals(allColorsInPopup.size(), initialColorsAmountInPopup + widthInColorGrid, 0);

        allColorsInPopup.wrap(initialColorsAmountInPopup / 2).mouse().click(1, allColorsInPopup.wrap(initialColorsAmountInPopup / 2).getClickPoint(), MouseButtons.BUTTON3);
        assertFalse(isPopupVisible());

        //Step 2. Remove from popup and check, that it was removed.
        showPopup();
        assertTrue(isPopupVisible());
        assertEquals(getCustomColorsCountFromControl(), 1, 0);

        allColorsInPopup.wrap(initialColorsAmountInPopup).mouse().click(1, allColorsInPopup.wrap(initialColorsAmountInPopup).getClickPoint(), MouseButtons.BUTTON3);
        assertTrue(isPopupVisible());
        PopupMenuTest.getPopupSceneWrap().as(Parent.class, Node.class).lookup(Node.class, new LookupCriteria<Node>() {
            public boolean check(Node node) {
                if (node.getProperties().containsKey(MenuItem.class)) {
                    String text = ((MenuItem) node.getProperties().get(MenuItem.class)).getText();
                    //String there, is the name of menuItem in context menu, on
                    //right mouse button click, over the custom color.
                    if (text != null && text.contentEquals("Remove Color")) {
                        return true;
                    }
                }
                return false;
            }
        }).wrap().mouse().click();

        setCustomColorsEnabled(false);
        assertEquals(getPopupPaleteLookup().size(), initialColorsAmountInPopup, 0);
        assertEquals(getCustomColorsCountFromControl(), 0, 0);
    }

    @Test(timeout = 300000)
    public void keyboardDeleteInPopupTest() {
        //RT-24838
        assertTrue(false);//not implemented
    }

    @Test(timeout = 300000)
    @Smoke
    /*
     * Space in pallete popup can be used, for clicking on link to custom color
     * dialog. But this link should be focused. But in cannot be focused (now,
     * when test is being written, because tab and shoft+tab don't work). So,
     * trick is used: click on it, see custom color dialog, close it, press
     * space again (suppose, that focus is still on link).
     */
    public void keyboardSpaceInPopupTest() throws InterruptedException {
        showPopup();
        clickCustomColorPopupButton();
        clickCancel();
        assertTrue(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());
        getPopupWrap().keyboard().pushKey(KeyboardButtons.SPACE);
        assertTrue(isPopupVisible());
        assertTrue(isCustomColorPopupVisible());

    }

    @Test(timeout = 600000)
    @Smoke
    /*
     * You can select color from palete popup, using enter key press, over the
     * focused color. So, add some amount of custom color, and select each of
     * all colors, presented in a popup.
     */
    public void keyboardEnterInPopupTest() throws InterruptedException {

        for (int i = 0; i < widthInColorGrid; i++) {
            selectCustomColor(((double) i) / (widthInColorGrid + 1), ((double) i) / (widthInColorGrid + 1), ((double) i) / (widthInColorGrid + 1), true);
        }

        showPopup();
        Lookup allColorsInPopup = getPopupPaleteLookup();
        int size = allColorsInPopup.size();
        assertEquals(size, initialColorsAmountInPopup + widthInColorGrid, 0);

        for (int i = size - 1; i >= 0; i--) {
            showPopup();
            assertTrue(isPopupVisible());
            allColorsInPopup.wrap(i).mouse().move();
            Color shosenColor = (Color) ((Rectangle) allColorsInPopup.wrap(i).getControl()).getFill();
            getPopupWrap().keyboard().pushKey(KeyboardButtons.ENTER);
            checkCurrentColorValue(shosenColor);
            assertFalse(isPopupVisible());
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardEscInPopupTest() throws InterruptedException {
        showPopup();
        waitColorPickerPopupShowingState(true);
        testedControl.keyboard().pushKey(KeyboardButtons.ESCAPE);
        waitColorPickerPopupShowingState(false);
    }

    @Test(timeout = 300000)
    public void keyboardArrowsInCustomColorDialogTest() {
        assertTrue(false);//not implemented
    }

    @Test(timeout = 300000)
    @Smoke
    /*
     * [A button is focused] Fire the action associated with the button. So,
     * open three times, custom color dialog, select some color, and request
     * focus on some button (there are three buttons). Press space, and review,
     * that action, according to button's aim was done.
     */
    public void keyboardSpaceInCustomColorDialogTest() throws InterruptedException {
        showPopup();
        showCustomColorDialog();

        Color currentColor = getCurrentColorValue();

        assertTrue(isPopupVisible());
        assertTrue(isCustomColorPopupVisible());

        requestFocusOnControl(getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT));
        getCustomColorPopupSceneWrap().keyboard().pushKey(KeyboardButtons.SPACE);

        assertTrue(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());
        checkCurrentColorValue(currentColor);
        assertEquals(getCustomColorsCountFromControl(), 0, 0);

        clickCustomColorPopupButton();

        setRGB(100, 50, 70);
        requestFocusOnControl(getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT));
        getCustomColorPopupSceneWrap().keyboard().pushKey(KeyboardButtons.SPACE);

        assertEquals(getCustomColorsCountFromControl(), 0, 0);
        assertFalse(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());
        assertEquals(colorDistance(Color.rgb(100, 50, 70), getCurrentColorValue()), 0, INDISTINGUISHABLE_COLOR_DISTANCE);

        showPopup();
        showCustomColorDialog();
        setRGB(17, 18, 19);
        requestFocusOnControl(getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT));
        getCustomColorPopupSceneWrap().keyboard().pushKey(KeyboardButtons.SPACE);

        assertEquals(getCustomColorsCountFromControl(), 1, 0);
        assertFalse(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());
        assertEquals(colorDistance(Color.rgb(17, 18, 19), getCurrentColorValue()), 0, INDISTINGUISHABLE_COLOR_DISTANCE);
    }

    @Test(timeout = 300000)
    public void keyboardTabNavigationInCustomColorDialogTest() throws InterruptedException {
        showCustomColorDialog();
        clickColorToggle(ColorOption.WEB);
        getColorPillWrap(ColorOption.WEB).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getWebControlWrap());

        getWebControlWrap().keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getSlider(ColorComponent.OPACITY));

        getSlider(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.OPACITY));

        getCustomColorTextInput(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getColorPillWrap(ColorOption.HSB));

        getColorPillWrap(ColorOption.HSB).keyboard().pushKey(KeyboardButtons.SPACE);
        getColorPillWrap(ColorOption.HSB).keyboard().pushKey(KeyboardButtons.TAB);
        getColorPillWrap(ColorOption.RGB).keyboard().pushKey(KeyboardButtons.TAB);
        getColorPillWrap(ColorOption.WEB).keyboard().pushKey(KeyboardButtons.TAB);

        checkFocused(getSlider(ColorComponent.HUE));

        getSlider(ColorComponent.HUE).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.HUE));

        getCustomColorTextInput(ColorComponent.HUE).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getSlider(ColorComponent.SATURATION));

        getSlider(ColorComponent.SATURATION).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.SATURATION));

        getCustomColorTextInput(ColorComponent.SATURATION).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getSlider(ColorComponent.BRIGHTNESS));

        getSlider(ColorComponent.BRIGHTNESS).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.BRIGHTNESS));

        getCustomColorTextInput(ColorComponent.BRIGHTNESS).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getSlider(ColorComponent.OPACITY));

        getSlider(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.OPACITY));

        getCustomColorTextInput(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getColorPillWrap(ColorOption.HSB));

        getColorPillWrap(ColorOption.HSB).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getColorPillWrap(ColorOption.RGB));
        getColorPillWrap(ColorOption.RGB).keyboard().pushKey(KeyboardButtons.SPACE);
        getColorPillWrap(ColorOption.RGB).keyboard().pushKey(KeyboardButtons.TAB);
        getColorPillWrap(ColorOption.WEB).keyboard().pushKey(KeyboardButtons.TAB);

        checkFocused(getSlider(ColorComponent.RED));

        getSlider(ColorComponent.RED).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.RED));

        getCustomColorTextInput(ColorComponent.RED).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getSlider(ColorComponent.GREEN));

        getSlider(ColorComponent.GREEN).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.GREEN));

        getCustomColorTextInput(ColorComponent.GREEN).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getSlider(ColorComponent.BLUE));

        getSlider(ColorComponent.BLUE).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.BLUE));

        getCustomColorTextInput(ColorComponent.BLUE).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getSlider(ColorComponent.OPACITY));

        getSlider(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getCustomColorTextInput(ColorComponent.OPACITY));

        getCustomColorTextInput(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB);
        checkFocused(getColorPillWrap(ColorOption.HSB));
    }

    @Test(timeout = 300000)
    public void keyboardShiftTabNavigationInCustomColorDialogTest() throws InterruptedException {
        showCustomColorDialog();
        clickColorToggle(ColorOption.WEB);
        getColorPillWrap(ColorOption.WEB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        getColorPillWrap(ColorOption.RGB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        getColorPillWrap(ColorOption.HSB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.OPACITY));

        getCustomColorTextInput(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.OPACITY));

        getSlider(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getWebControlWrap());

        getWebControlWrap().keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);

        checkFocused(getColorPillWrap(ColorOption.WEB));

        getColorPillWrap(ColorOption.WEB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        getColorPillWrap(ColorOption.RGB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        getColorPillWrap(ColorOption.HSB).keyboard().pushKey(KeyboardButtons.SPACE);
        getColorPillWrap(ColorOption.HSB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.OPACITY));

        getCustomColorTextInput(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.OPACITY));

        getSlider(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.BRIGHTNESS));

        getCustomColorTextInput(ColorComponent.BRIGHTNESS).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.BRIGHTNESS));

        getSlider(ColorComponent.BRIGHTNESS).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.SATURATION));

        getCustomColorTextInput(ColorComponent.SATURATION).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.SATURATION));

        getSlider(ColorComponent.SATURATION).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.HUE));

        getCustomColorTextInput(ColorComponent.HUE).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.HUE));

        getSlider(ColorComponent.HUE).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getColorPillWrap(ColorOption.WEB));

        getColorPillWrap(ColorOption.WEB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        getColorPillWrap(ColorOption.RGB).keyboard().pushKey(KeyboardButtons.SPACE);
        getColorPillWrap(ColorOption.RGB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        getColorPillWrap(ColorOption.HSB).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(CANCEL_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(USE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT));

        getButtonWrapInCustomColorDialog(SAVE_BUTTON_TEXT).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.OPACITY));

        getCustomColorTextInput(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.OPACITY));

        getSlider(ColorComponent.OPACITY).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.BLUE));

        getCustomColorTextInput(ColorComponent.BLUE).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.BLUE));

        getSlider(ColorComponent.BLUE).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.GREEN));

        getCustomColorTextInput(ColorComponent.GREEN).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.GREEN));

        getSlider(ColorComponent.GREEN).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getCustomColorTextInput(ColorComponent.RED));

        getCustomColorTextInput(ColorComponent.RED).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getSlider(ColorComponent.RED));

        getSlider(ColorComponent.RED).keyboard().pushKey(KeyboardButtons.TAB, Keyboard.KeyboardModifiers.SHIFT_DOWN_MASK);
        checkFocused(getColorPillWrap(ColorOption.WEB));
    }

    @Test(timeout = 300000)//RT-24842
    @Smoke
    /*
     * Dismiss the dialog. Save the new color to the Custom Colors area in the
     * Color Palette. Close the Color Palette. Update the Color Chooser. Apply
     * the color.
     */
    public void keyboardEnterInCustomColorDialogTest() throws InterruptedException {
        showPopup();
        clickCustomColorPopupButton();

        assertTrue(isPopupVisible());
        assertTrue(isCustomColorPopupVisible());

        setRGB(50, 50, 50);
        getCustomColorPopupSceneWrap().keyboard().pushKey(KeyboardButtons.ENTER);

        assertFalse(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());
        checkCurrentColorValue(Color.rgb(50, 50, 50));
    }

    @Smoke
    @Test(timeout = 300000)
    public void keyboardEscInCustomColorDialogTest() throws InterruptedException {
        showPopup();
        waitColorPickerPopupShowingState(true);
        clickCustomColorPopupButton();
        assertTrue(isCustomColorPopupVisible());
        getCustomColorPopupSceneWrap().keyboard().pushKey(KeyboardButtons.ESCAPE);
        waitColorPickerPopupShowingState(true);
        assertFalse(isCustomColorPopupVisible());

        standartCustomColorChosingPopupDismissTest();
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ColorPicker.value.BEHAVIOR", "javafx.scene.control.ColorPicker.value.GET",
        "javafx.scene.control.ColorPicker.value.BIND", "javafx.scene.control.ColorPicker.value.DEFAULT",
        "javafx.scene.control.ColorPicker.value.SET"}, level = Covers.Level.FULL)
    public void colorPropertyTest() {
        assertTrue(new ColorPicker().getValue().equals(Color.WHITE));
        setColor("aaeeaa");
        checkCurrentColorValue(Color.web("#aaeeaa"));

        for (SettingType settingType : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            for (Color color : predefinedColors) {
                setPropertyByChoiceBox(settingType, color, Properties.value);
                checkTextFieldText(Properties.value, color.toString());
            }
        }
    }

    @Smoke
    @Test(timeout = 300000)
    public void listOfCustomColorsPropertyTest() throws InterruptedException {
        addCustomColors(50, 0.936);
        checkCustomColorsGetterCorrectness();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void opaciteColorTest() throws InterruptedException, Throwable {
        setJemmyComparatorByDistance(0.01f);
        clickControl();
        clickCustomColorPopupButton();
        setRGB(130, 130, 230, 50);
        checkScreenshot("ColorPicker-opaciteColorChosed", getCustomColorPopupSceneWrap());
        clickUse();
        checkCurrentColorValue(Color.rgb(130, 130, 230, 1.0), true);//1.0 and not 0.5, because description ignores opacity.
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 60000)
    public void renderingAfterSortingTest() throws InterruptedException {
        //colors in the hue ascending order
        final Color[] SAMPLE_COLORS = {
            Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK
        };

        //select colors in reverse order
        for (int i = SAMPLE_COLORS.length - 1; i >= 0; --i) {
            clickCustomColorPopupButton();
            Color c = SAMPLE_COLORS[i];
            setRGB(Math.round(c.getRed() * 255.0), Math.round(c.getGreen() * 255.0), Math.round(c.getBlue() * 255.0));
            clickSave();
        }

        //sort
        new GetAction<Object>() {
            @Override
            public void run(Object... os) throws Exception {
                FXCollections.sort(testedControl.getControl().getCustomColors(), new Comparator<Color>() {
                    public int compare(Color o1, Color o2) {
                        return Double.valueOf(o1.getHue()).compareTo(Double.valueOf(o2.getHue()));
                    }
                });
            }
        }.dispatch(testedControl.getEnvironment());

        showPopup();

        Lookup lookup = getCustomColorsLookup();

        final int COLOR_SQUARES_MAX_COUNT = 12;
        assertEquals("[Lookup failed]", COLOR_SQUARES_MAX_COUNT, lookup.size());

        ArrayList<Wrap> ls = new ArrayList<Wrap>(COLOR_SQUARES_MAX_COUNT);
        for (int i = 0; i < COLOR_SQUARES_MAX_COUNT; i++) {
            ls.add(lookup.wrap(i));
        }

        Collections.sort(ls, new Comparator<Wrap>() {
            public int compare(Wrap o1, Wrap o2) {
                return o1.getScreenBounds().x - o2.getScreenBounds().x;
            }
        });

        //check results
        for (int i = 0; i < SAMPLE_COLORS.length; i++) {
            System.out.format("i = %d%n", i);
            double[] colors = JemmyUtils.getColors(ls.get(i).getScreenImage());
            if (JemmyUtils.usingGlassRobot()) {
                for (int x = 0; x < colors.length; x++) {
                    colors[x] = colors[x] * 255;
                }
            }
            final String msg = "[Color components not equal]";
            assertEquals(msg, Math.round(SAMPLE_COLORS[i].getRed() * 255.0), colors[0], 2.5);
            assertEquals(msg, Math.round(SAMPLE_COLORS[i].getGreen() * 255.0), colors[1], 2.5);
            assertEquals(msg, Math.round(SAMPLE_COLORS[i].getBlue() * 255.0), colors[2], 2.5);
        }

        final Color WHITE = Color.WHITE;

        for (int i = SAMPLE_COLORS.length; i < COLOR_SQUARES_MAX_COUNT; i++) {
            System.out.format("i = %d%n", i);
            double[] colors = JemmyUtils.getColors(ls.get(i).getScreenImage());
            if (JemmyUtils.usingGlassRobot()) {
                for (int x = 0; x < colors.length; x++) {
                    colors[x] = colors[x] * 255;
                }
            }
            final String msg = "[Empty color square must be white]";
            assertEquals(msg, Math.round(WHITE.getRed() * 255.0), colors[0], 0.001);
            assertEquals(msg, Math.round(WHITE.getGreen() * 255.0), colors[1], 0.001);
            assertEquals(msg, Math.round(WHITE.getBlue() * 255.0), colors[2], 0.001);
        }
    }

    protected void standartCustomColorChosingPopupDismissTest() throws InterruptedException {
        Color oldColor = getCurrentColorValue();
        selectColorFromRectanglePallete(0.5, 0.5);

        assertTrue(isPopupVisible());
        assertTrue(isCustomColorPopupVisible());

        clickCancel();

        Color newColor = getCurrentColorValue();

        assertTrue(isPopupVisible());
        assertFalse(isCustomColorPopupVisible());

        assertTrue(colorDistance(newColor, oldColor) < 1);
    }
}