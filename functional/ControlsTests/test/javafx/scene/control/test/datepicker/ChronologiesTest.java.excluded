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
package javafx.scene.control.test.datepicker;

import java.time.chrono.Chronology;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.Point;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 *
 * @author Dmitry Zinkevich
 */
@RunWith(FilteredTestRunner.class)
public class ChronologiesTest extends TestBase {
    /**
     * Checks initial appearance of the DateChooser
     * with predefined date using ISO chronology.
     */
    @Test(timeout = 10000)
    public void initialPopupAppearanceISO() throws Throwable {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("ISO"));
        setDefaultDate();
        showPopup();

        checkScreenshot("DatePicker-initialPopupISO", getRootWrap(getPopupWrap()));
        throwScreenshotError();
    }

    /**
     * Checks initial appearance of the DateChooser
     * with predefined date using Minguo chronology.
     */
    @Test(timeout = 10000)
    public void initialPopupAppearanceMinguo() throws Throwable {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("Minguo"));
        setDefaultDate();
        showPopup();

        checkScreenshot("DatePicker-initialPopupMinguo", getRootWrap(getPopupWrap()));
        throwScreenshotError();
    }

    /**
     * Checks initial appearance of the DateChooser
     * with predefined date using ThaiBuddhist chronology.
     */
    @Test(timeout = 10000)
    public void initialPopupAppearanceThaiBuddhist() throws Throwable {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("ThaiBuddhist"));
        setDefaultDate();
        showPopup();

        checkScreenshot("DatePicker-initialPopupThaiBuddhist", getRootWrap(getPopupWrap()));
        throwScreenshotError();
    }

    /**
     * Checks initial appearance of the DateChooser
     * with predefined date using Japanese chronology.
     */
    @Test(timeout = 10000)
    public void initialPopupAppearanceJapanese() throws Throwable {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("Japanese"));
        setDefaultDate();
        showPopup();

        checkScreenshot("DatePicker-initialPopupJapanese", getRootWrap(getPopupWrap()));
        throwScreenshotError();
    }

    /**
     * Checks initial appearance of the DateChooser
     * with predefined date using Hijrah-umalqura chronology.
     */
    @Test(timeout = 10000)
    public void initialPopupAppearanceHijrahUmalqura() throws Throwable {
        final Chronology chronology = Chronology.of("Hijrah-umalqura");
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, chronology);
        setDefaultDate();
        showPopup();
        checkScreenshot("DatePicker-initialPopupHijrahUmalqura", getRootWrap(getPopupWrap()));
        throwScreenshotError();
    }

    /**
     * Checks that DateField shows correct date representation
     * according using ISO chronology.
     */
    @Test(timeout = 10000)
    public void dateRepresentationISO() {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("ISO"));
        setDefaultDate();
        waitShownText("10/11/1990");
    }

    /**
     * Checks that DateField shows correct date representation
     * according using Minguo chronology.
     */
    @Test(timeout = 10000)
    public void dateRepresentationMinguo() {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("Minguo"));
        setDefaultDate();
        waitShownText("10/11/0079 1");
    }

    /**
     * Checks that DateField shows correct date representation
     * according using Thai Buddhist chronology.
     */
    @Test(timeout = 10000)
    public void dateRepresentationThaiBuddhist() {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("ThaiBuddhist"));
        setDefaultDate();
        waitShownText("10/11/2533 B.E.");
    }

    /**
     * Checks that DateField shows correct date representation
     * according using Japanese chronology.
     */
    @Test(timeout = 10000)
    public void dateRepresentationJapanese() {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("Japanese"));
        setDefaultDate();
        waitShownText("10/11/0002 H");
    }

    /**
     * Checks that DateField shows correct date representation
     * according using Hijrah-umalqura chronology.
     */
    @Test(timeout = 10000)
    public void dateRepresentationHijrahUmalqura() {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("Hijrah-umalqura"));
        setDefaultDate();
        waitShownText("3/21/1411 AH");
    }

    /**
     * Test checks that after typing
     * a correct date in the DateField the DateChooser will render
     * it correctly.
     * Using ISO chronology.
     */
    @Test(timeout = 10000)
    public void dateTypingISO() throws InterruptedException {
        HashMap<String, String> expectedState = new HashMap<String, String>(3);
        expectedState.put("selectedDay", "29");
        expectedState.put("monthName", "february");
        expectedState.put("year", "2012");
        checkTypedDate(Chronology.of("ISO"), expectedState, "2/29/2012");
    }

    /**
     * Test checks that after typing
     * a correct date in the DateField the DateChooser will render
     * it correctly.
     * Using Minguo chronology.
     */
    @Test(timeout = 10000)
    public void dateTypingMinguo() throws InterruptedException {
        HashMap<String, String> expectedState = new HashMap<String, String>(3);
        expectedState.put("selectedDay", "29");
        expectedState.put("monthName", "february");
        expectedState.put("year", "101");
        checkTypedDate(Chronology.of("Minguo"), expectedState, "2/29/0101 1");
    }

    /**
     * Test checks that after typing
     * a correct date in the DateField the DateChooser will render
     * it correctly.
     * Using ThaiBuddhist chronology.
     */
    @Test(timeout = 10000)
    public void dateTypingThaiBuddhist() throws InterruptedException {
        HashMap<String, String> expectedState = new HashMap<String, String>(3);
        expectedState.put("selectedDay", "29");
        expectedState.put("monthName", "february");
        expectedState.put("year", "2555");
        checkTypedDate(Chronology.of("ThaiBuddhist"), expectedState, "2/29/2555 B.E.");
    }

    /**
     * Test checks that after typing
     * a correct date in the DateField the DateChooser will render
     * it correctly.
     * Using Japanese chronology.
     */
    @Test(timeout = 10000)
    public void dateTypingJapanese() throws InterruptedException {
        HashMap<String, String> expectedState = new HashMap<String, String>(3);
        expectedState.put("selectedDay", "29");
        expectedState.put("monthName", "february");
        expectedState.put("year", "Heisei24");
        checkTypedDate(Chronology.of("Japanese"), expectedState, "2/29/0024 H");
    }

    /**
     * Test checks that after typing
     * a correct date in the DateField the DateChooser will render
     * it correctly.
     * Using Hijrah-umalqura chronology.
     */
    @Test(timeout = 10000)
    public void dateTypingHijrahUmalqura() throws InterruptedException {
        HashMap<String, String> expectedState = new HashMap<String, String>(3);
        expectedState.put("selectedDay", "29");
        expectedState.put("monthName", "february");
        expectedState.put("year", "2012");
        checkTypedDate(Chronology.of("Hijrah-umalqura"), expectedState, "4/7/1433 AH");
    }

    public void checkTypedDate(Chronology chronology, HashMap<String, String> expectedState, String date) throws InterruptedException {
        selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, chronology);
        TextInputControlDock  in = new TextInputControlDock(testedControl.as(Parent.class, Node.class));
        in.type(date);
        in.keyboard().pushKey(KeyboardButtons.ENTER);
        waitShownText(date);

        clickDropDownButton();
        waitPopupShowingState(true);

        PopupSceneDescription description = new PopupSceneDescription();
        description.extractData();

        testedControl.waitState(new DateState(expectedState, description));
        //Close pop up
        scene.mouse().click(1, new Point(2, 2));
    }
}
