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

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.Chronology;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.test.datepicker.DatePickerApp.DummyConverter;
import javafx.scene.control.test.datepicker.DatePickerApp.LocalDateConverter;
import javafx.scene.control.test.datepicker.DatePickerApp.WorkingDays;
import static javafx.scene.control.test.datepicker.TestBase.testedControl;
import javafx.scene.control.test.util.PropertyTest;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Keyboard.KeyboardButton;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Modifier;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov, Dmitry Zinkevich
 *
 * @see <a href="http://xdesign.us.oracle.com/projects/javaFX/fxcontrols-ue/specifications/DatePicker/datepicker-UEspec.html">specification</a>
 */
@RunWith(FilteredTestRunner.class)
public class DatePickerTest extends TestBase {
    Double width;

    void checkFinalState() {
        checkTextFieldValue(Properties.width, width.doubleValue());
        defaultController.check();
    }

    void rememberInitialState(Enum... excludePropertiesList) {
        width = new GetAction<Double>() {
            @Override
            public void run(Object... parameters) throws Exception {
                setResult(Double.valueOf(testedControl.getControl().getWidth()));
            }
        }.dispatch(testedControl.getEnvironment());

        initChangingController(parent);
        defaultController.include().allTables().allProperties().allCounters().apply();
        defaultController.exclude().allTables().properties(excludePropertiesList).apply();
        defaultController.fixCurrentState();
    }

    /**
     * Checks that week numbers have been rendered.
     */
    @Test(timeout = 10000)
    public void weekNumbersScreenshotTest() throws Throwable {
        setDate(LocalDate.of(1990, Month.OCTOBER, 11));
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        setPropertyByToggleClick(SettingType.SETTER, Properties.showWeekNumbers, Boolean.TRUE);
        showPopup();

        checkFinalState();

        checkScreenshot("DatePicker-weekNumbers", getRootWrap(getPopupWrap()));
        throwScreenshotError();
    }

    /**
     * Checks that the current date is shown by default in the DateChooser.
     */
    @Test(timeout = 10000)
    public void currentDateShownTest() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        clickDropDownButton();
        waitPopupShowingState(true);

        PopupSceneDescription description = new PopupSceneDescription();
        final PopupInfoDescription infoDescription = description.getInfoDescription();
        LocalDate now = LocalDate.now();

        assertEquals(Integer.parseInt(infoDescription.year), now.getYear());
        assertEquals(infoDescription.monthName.toLowerCase(), now.getMonth().toString().toLowerCase());
        assertEquals(infoDescription.today.mainDate, now.getDayOfMonth());

        boolean found = false;
        for (DateCellDescription descriptionVar : infoDescription.currentMonthDays) {
            if (descriptionVar.mainDate == now.getDayOfMonth()) {
                found = true;
            }
        }
        assertTrue(found);

        checkFinalState();
    }

    /**
     * Checks that previous month button works as expected.
     */
    @Test(timeout = 10000)
    public void correctPreviousMonthTest() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        clickDropDownButton();
        waitPopupShowingState(true);

        final PopupSceneDescription description = new PopupSceneDescription();
        final PopupInfoDescription infoDescription = description.getInfoDescription();

        final int initialDaysNum = description.currentMonthDays.size();

        description.previousMonthWrap.mouse().click();

        testedControl.waitState(new State() {
            public Object reached() {
                PopupInfoDescription infoDescription2 = null;
                try {
                    infoDescription2 = description.getInfoDescription();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatePickerTest.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (Month.valueOf(infoDescription.monthName.toUpperCase()).minus(1).equals(Month.valueOf(infoDescription2.monthName.toUpperCase()))
                    && (initialDaysNum != description.currentMonthDays.size()
                        || infoDescription.monthName.equalsIgnoreCase("AUGUST")
                        || infoDescription.monthName.equalsIgnoreCase("JANUARY"))) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        checkFinalState();
    }

    /**
     * Checks that next month button works as expected.
     */
    @Test(timeout = 10000)
    public void correctNextMonthTest() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        clickDropDownButton();
        waitPopupShowingState(true);

        final PopupSceneDescription description = new PopupSceneDescription();
        final PopupInfoDescription infoDescription = description.getInfoDescription();

        final int initialDaysNum = description.currentMonthDays.size();

        description.nextMonthWrap.mouse().click();

        testedControl.waitState(new State() {
            public Object reached() {
                PopupInfoDescription infoDescription2 = null;
                try {
                    infoDescription2 = description.getInfoDescription();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatePickerTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (Month.valueOf(infoDescription.monthName.toUpperCase()).plus(1).equals(Month.valueOf(infoDescription2.monthName.toUpperCase()))
                    && (initialDaysNum != description.currentMonthDays.size()
                        || infoDescription.monthName.equalsIgnoreCase("JULY")
                        || infoDescription.monthName.equalsIgnoreCase("DECEMBER"))) {
                    return true;
                } else {
                    return null;
                }
            }
        });

        checkFinalState();
    }

    /**
     * Checks that arrows which increase/decrease year work correctly
     */
    @Test(timeout = 10000)
    public void yearNavigationTest() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        setDate(LocalDate.of(2012, 5, 9));
        clickDropDownButton();

        PopupSceneDescription description = new PopupSceneDescription();
        description.extractData();
        description.previousYearWrap.mouse().click();

        HashMap<String, String> expectedState = new HashMap<String, String>(2);
        expectedState.put("monthName", "May");
        expectedState.put("year", "2011");
        testedControl.waitState(new DateState(expectedState, description));

        description.extractData(); description.currentMonthDays.get(30).mouse().click();
        waitShownText("5/31/2011");

        setDate(LocalDate.of(2012, 2, 29));
        clickDropDownButton();
        description = new PopupSceneDescription();
        description.extractData(); description.nextYearWrap.mouse().click();
        expectedState.put("monthName", "February");
        expectedState.put("year", "2013");
        testedControl.waitState(new DateState(expectedState, description));

        description.extractData(); description.currentMonthDays.get(27).mouse().click();
        waitShownText("2/28/2013");

        checkFinalState();
    }

    /**
     * Checks that when mouse clicks the first or the last cell
     * in the DateChooser then the current month is set to the previous or
     * to the next from current respectively.
     */
    @Test(timeout = 10000)
    public void extremeDateCellsChangeCurrentMonth() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        setDate(LocalDate.of(2000, 1, 27));
        clickDropDownButton();

        PopupSceneDescription description = new PopupSceneDescription();
        description.extractData(); description.previousMonthDays.get(0).mouse().click();

        HashMap<String, String> expectedState = new HashMap<String, String>(2);
        expectedState.put("monthName", "December");
        expectedState.put("year", "1999");
        testedControl.waitState(new DateState(expectedState, description));

        clickDropDownButton();
        description.extractData(); description.currentMonthDays.get(30).mouse().click();
        waitShownText("12/31/1999");

        setDate(LocalDate.of(2019, 12, 20));
        clickDropDownButton();

        description = new PopupSceneDescription();
        description.extractData(); description.nextMonthDays.get(description.nextMonthDays.size() - 1).mouse().click();
        expectedState.put("monthName", "January");
        expectedState.put("year", "2020");
        testedControl.waitState(new DateState(expectedState, description));

        clickDropDownButton();
        description.extractData(); description.currentMonthDays.get(26).mouse().click();
        waitShownText("1/27/2020");
        checkFinalState();
    }

    /**
     * Checks that when navigating in horizontal or vertical direction
     * the switching between days and months occurs correctly.
     * @see <a href=http://xdesign.us.oracle.com/projects/javaFX/fxcontrols-ue/specifications/DatePicker/datepicker-UEspec.html#navigation>navigation</a>
     *
     * Also, tests for month/year navigation, respectively Control+PgUp/Control+PgDn and PgUp/PgDn
     * @see <a href="https://javafx-jira.kenai.com/browse/RT-32493">RT-32493</a> and <a href="https://javafx-jira.kenai.com/browse/RT-32492">RT-32492</a>
     */
    @Test(timeout = 20000)
    public void keyboardNavigation() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        setDate(LocalDate.of(2013, 01, 06));
        clickDropDownButton();
        waitPopupShowingState(true);

        scene.keyboard().pushKey(KeyboardButtons.LEFT);
        PopupSceneDescription description = new PopupSceneDescription();
        description.extractData();

        //Focus must be on 1/5/2013
        description.currentMonthDays.get(4).waitProperty("isFocused", Boolean.TRUE);

        for (int i = 0; i < 5; i++) scene.keyboard().pushKey(KeyboardButtons.LEFT);

        //Expect switching to the previous month and year
        HashMap<String, String> expectedState = new HashMap<String, String>(3);
        expectedState.put("monthName", "december");
        expectedState.put("year", "2012");
        expectedState.put("focusedDay", "31");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        //Expect to switch to the next month and year
        scene.keyboard().pushKey(KeyboardButtons.DOWN);
        expectedState.put("monthName", "january");
        expectedState.put("year", "2013");
        expectedState.put("focusedDay", "7");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        for (int i = 0; i < 6; i++) scene.keyboard().pushKey(KeyboardButtons.RIGHT);

        //Expect to move to the next week
        expectedState.put("monthName", "january");
        expectedState.put("year", "2013");
        expectedState.put("focusedDay", "13");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        for (int i = 0; i < 2; i++) scene.keyboard().pushKey(KeyboardButtons.DOWN);
        for (int i = 0; i < 5; i++) scene.keyboard().pushKey(KeyboardButtons.RIGHT);

        //Expect to move to the next month
        expectedState.put("monthName", "february");
        expectedState.put("year", "2013");
        expectedState.put("focusedDay", "1");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        scene.keyboard().pushKey(KeyboardButtons.RIGHT);
        scene.keyboard().pushKey(KeyboardButtons.UP);

        //Expect to move to the prev month
        expectedState.put("monthName", "january");
        expectedState.put("year", "2013");
        expectedState.put("focusedDay", "26");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        for (int i = 0; i < 3; ++i)
            scene.keyboard().pushKey(KeyboardButtons.PAGE_DOWN, KeyboardModifiers.CTRL_DOWN_MASK);

        //Expect to move forward in three years
        expectedState.put("monthName", "january");
        expectedState.put("year", "2016");
        expectedState.put("focusedDay", "26");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        scene.keyboard().pushKey(KeyboardButtons.PAGE_UP, KeyboardModifiers.CTRL_DOWN_MASK);

        //Expect to move backward for one years
        expectedState.put("monthName", "january");
        expectedState.put("year", "2015");
        expectedState.put("focusedDay", "26");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        for (int i = 0; i < 4; ++i)
            scene.keyboard().pushKey(KeyboardButtons.PAGE_UP);

        //Expect to move backward for four months
        expectedState.put("monthName", "september");
        expectedState.put("year", "2014");
        expectedState.put("focusedDay", "26");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));

        for (int i = 0; i < 6; ++i)
            scene.keyboard().pushKey(KeyboardButtons.PAGE_DOWN);

        //Expect to move forward for six months
        expectedState.put("monthName", "march");
        expectedState.put("year", "2015");
        expectedState.put("focusedDay", "26");
        description.extractData();
        testedControl.waitState(new DateState(expectedState, description));
        checkFinalState();
    }

    /**
     * Checks all possible ways to select a date with the keyboard.
     */
    @Test(timeout = 10000)
    public void keyboardDateSelection() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);

        clickDropDownButton();
        waitPopupShowingState(true);

        PopupSceneDescription description = new PopupSceneDescription();
        description.extractData();
        description.currentMonthDays.get(14).mouse().click();
        waitPopupShowingState(false);
        checkExpectedDate(LocalDate.now().withDayOfMonth(15));

        clickDropDownButton();
        scene.keyboard().pushKey(KeyboardButtons.DOWN);
        scene.keyboard().pushKey(KeyboardButtons.SPACE);
        checkExpectedDate(LocalDate.now().withDayOfMonth(22));

        clickDropDownButton();
        for (int i = 0; i < 2; i++) scene.keyboard().pushKey(KeyboardButtons.UP);
        scene.keyboard().pushKey(KeyboardButtons.ENTER);
        checkExpectedDate(LocalDate.now().withDayOfMonth(8));

        clickDropDownButton();
        scene.keyboard().pushKey(KeyboardButtons.DOWN);
        scene.keyboard().pushKey(KeyboardButtons.ESCAPE);
        checkExpectedDate(LocalDate.now().withDayOfMonth(8));

        checkFinalState();
    }

    /**
     * Check that after typing a correct date
     * the DateChooser will also show it.
     * First time the default converter is used which
     * reads date in en_US format according to test default locale.
     * The second time a custom converter is used in the format yyyy-MM-dd.
     */
    @Test(timeout = 20000)
    public void customConverter() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);

        final StringConverter converter = new DummyConverter();
        setConverter(converter);
        assertSame(converter, getConverter());
        selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.converter, null);

        String dates[][] = {{"7/4/1776", "July", "4", "1776"}, {"1779-07-14", "July", "14", "1779"}};

        TextInputControlDock text = new TextInputControlDock(parent, new ByStyleClass<TextInputControl>("date-picker-display-node"));
        HashMap<String, String> expectedState = new HashMap<String, String>(3);

        for (int i = 0; i < 2; i++) {
            String[] dateParts = dates[i];
            text.clear();
            text.asSelectionText().type(dateParts[0]);
            text.keyboard().pushKey(KeyboardButtons.ENTER);
            waitShownText(dateParts[0]);

            clickDropDownButton();
            PopupSceneDescription description = new PopupSceneDescription();
            description.extractData();

            expectedState.put("monthName", dateParts[1]);
            expectedState.put("selectedDay", dateParts[2]);
            expectedState.put("year", dateParts[3]);

            testedControl.waitState(new DateState(expectedState, description));

            testedControl.keyboard().pushKey(KeyboardButtons.ESCAPE);
            waitPopupShowingState(false);

            //The second time test custom converter
            selectObjectFromChoiceBox(SettingType.UNIDIRECTIONAL, Properties.converter, LocalDateConverter.class);
        }
        checkFinalState();
    }

    /**
     * Checks that disabled DateCell can't be selected and
     * that state doesn't change after.
     */
    @Test(timeout = 10000)
    public void daysRestriction() throws InterruptedException {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(DatePicker param) { return new DateCell(); }
        };
        setDayCellFactory(dayCellFactory);
        assertSame(dayCellFactory, getDayCellFactory());

        selectObjectFromChoiceBox(SettingType.BIDIRECTIONAL, Properties.dayCellFactory, WorkingDays.class);
        setDate(LocalDate.of(2020, 10, 31));
        clickDropDownButton();
        waitPopupShowingState(true);

        PopupSceneDescription description = new PopupSceneDescription();
        description.extractData();
        Wrap<? extends DateCell> cellWrap = description.currentMonthDays.get(24);
        DateCellDescription cell = new DateCellDescription(cellWrap);
        assertEquals("[Selected wrong day]", 25, cell.mainDate);

        cellWrap.mouse().click();
        waitPopupShowingState(true);

        HashMap<String, String> expectedState = new HashMap<String, String>(2);
        expectedState.put("selectedDay", "31");
        expectedState.put("monthName", "October");
        expectedState.put("year", "2020");
        testedControl.waitState(new DateState(expectedState, description));
        waitShownText("10/31/2020");

        testedControl.keyboard().pushKey(KeyboardButtons.ESCAPE);
        setDate(LocalDate.of(2020, Month.OCTOBER, 25));
        checkFinalState();
    }

    /**
     * Changes value of the property 'showWeekNumbers'
     * and checks that DateChooser is rendered correctly.
     * The value is set via api and via context menu.
     */
    @Test(timeout = 20000)
    public void showWeekNumbersProperty() throws InterruptedException {
        assertFalse(new GetAction<Boolean>() {
            @Override public void run(Object... parameters) throws Exception {
                setResult(new DatePicker().isShowWeekNumbers());
            }
        }.dispatch(testedControl.getEnvironment()).booleanValue());

        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);

        setDate(LocalDate.of(2013, Month.DECEMBER, 31));
        for(SettingType settingType : SettingType.values()) {
            System.out.format("Testing binding:%s\n", settingType.toString());
            setPropertyByToggleClick(settingType, Properties.showWeekNumbers, Boolean.TRUE);

            checkTextFieldText(Properties.showWeekNumbers, "true");

            clickDropDownButton(); waitPopupShowingState(true);

            PopupSceneDescription description = new PopupSceneDescription();
            description.extractData();
            assertEquals("[Incorrect count of week numbers]", 6, description.weeksNumbers.size());

            description.weeksNumbers.sort(new Comparator<Wrap<? extends DateCell>>() {
                public int compare(Wrap<? extends DateCell> o1, Wrap<? extends DateCell> o2) {
                    return o1.getScreenBounds().y - o2.getScreenBounds().y;
                }
            });

            Integer[] expectedNumbers = {49, 50, 51, 52, 1, 2};
            assertEquals("[Weeks are not in correct order]", Arrays.asList(expectedNumbers), description.getInfoDescription().weekNumbers);

            setPropertyByToggleClick(settingType, Properties.showWeekNumbers, Boolean.FALSE);

            description.extractData();
            assertEquals("[No week numbers expected]", 0, description.weeksNumbers.size());

            scene.mouse().click(1, new Point(2, 2));
            switchOffBinding(settingType, Properties.showWeekNumbers);
        }
        checkFinalState();
    }

    /**
     * Checks bindings of the 'chronology' property.
     */
    @Test(timeout = 10000)
    public void chronologyProperty() {
        rememberInitialState();

        final String PROPERTY_NAME = "ThaiBuddhist";
        final Chronology CHRONOLOGY = Chronology.of(PROPERTY_NAME);
        for(SettingType settingType : SettingType.values()) {
            System.out.format("Testing binding:%s\n", settingType.toString());
            selectObjectFromChoiceBox(settingType, Properties.chronology, CHRONOLOGY);

            checkTextFieldText(Properties.chronology, PROPERTY_NAME);

            switchOffBinding(settingType, Properties.chronology);
            selectObjectFromChoiceBox(SettingType.SETTER, Properties.chronology, Chronology.of("ISO"));
        }
        checkFinalState();
    }

    /**
     * Check value of the 'showing' property.
     */
    @Test(timeout = 20000)
    public void showingProperty() throws InterruptedException {
        assertFalse(new GetAction<Boolean>() {
            @Override public void run(Object... parameters) throws Exception {
                setResult(new DatePicker().showingProperty().getValue());
            }
        }.dispatch(testedControl.getEnvironment()).booleanValue());

        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);

        clickDropDownButton();
        waitPopupShowingState(true);

        clickDropDownButton();
        waitPopupShowingState(false);

        testedControl.mouse().click();
        waitPopupShowingState(false);

        Object[][] keyCombinations = {
            {KeyboardButtons.F4},
            {KeyboardButtons.DOWN, KeyboardModifiers.ALT_DOWN_MASK},
            {KeyboardButtons.UP, KeyboardModifiers.ALT_DOWN_MASK}
        };

        for(Object[] keyCombo : keyCombinations) {
            System.out.println(Arrays.toString(keyCombo));
            if (keyCombo.length == 1) {
                testedControl.keyboard().pushKey((KeyboardButton) keyCombo[0]);
            } else {
                testedControl.keyboard().pushKey((KeyboardButton) keyCombo[0], (Modifier) keyCombo[1]);
            }
            waitPopupShowingState(true);
            testedControl.keyboard().pushKey(KeyboardButtons.ESCAPE);
            waitPopupShowingState(false);
        }

        checkFinalState();
    }

    /**
     * Checks bindings of the 'editable' property.
     */
    @Test(timeout = 20000)
    public void editableProperty() {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);
        final LocalDate INITIAL_DATE = LocalDate.of(2041, Month.SEPTEMBER, 22);
        final String INITIAL_DATE_TEXT = "9/22/2041";
        final LocalDate TESTED_DATE = LocalDate.of(2000, Month.JANUARY, 1);
        final String DATE_TEXT = "1/1/2000";

        setDate(INITIAL_DATE);
        waitShownText(INITIAL_DATE_TEXT);
        setPropertyByToggleClick(SettingType.SETTER, Properties.editable, Boolean.FALSE);

        //Test non editable state
        TextInputControlDock input = new TextInputControlDock(testedControl.as(Parent.class, Node.class));
        selectAllText(input);
        assertEquals("[Text was not selected]", INITIAL_DATE_TEXT, input.asSelectionText().selection());
        input.keyboard().pushKey(KeyboardButtons.A);
        input.keyboard().pushKey(KeyboardButtons.ENTER);

        waitShownText(INITIAL_DATE_TEXT);

        for(SettingType settingType : SettingType.values()) {
            System.out.format("Testing binding:%s\n", settingType.toString());
            setPropertyByToggleClick(settingType, Properties.editable, Boolean.TRUE);

            checkTextFieldText(Properties.editable, "true");

            input = new TextInputControlDock(testedControl.as(Parent.class, Node.class));
            selectAllText(input);
            assertEquals("[Text was not selected]", INITIAL_DATE_TEXT, input.asSelectionText().selection());

            input.type(DATE_TEXT);
            input.keyboard().pushKey(KeyboardButtons.ENTER);
            assertEquals("[Date was not typed]", TESTED_DATE, getDate());
            waitShownText(DATE_TEXT);

            switchOffBinding(settingType, Properties.editable);
            setPropertyByToggleClick(SettingType.SETTER, Properties.editable, Boolean.FALSE);

            //Restore initial state
            setDate(INITIAL_DATE);
            waitShownText(INITIAL_DATE_TEXT);
        }
        setPropertyByToggleClick(SettingType.SETTER, Properties.editable, Boolean.TRUE);
        checkFinalState();
    }

    /**
     * Check bindings of the 'prompt text' property
     */
    @Test(timeout = 10000)
    public void promptTextProperty() {
        rememberInitialState();
        assertTrue("[Propmpt text is empty by default]", new GetAction<Boolean>() {
            @Override public void run(Object... parameters) throws Exception {
                setResult(new DatePicker().promptTextProperty().getValue().isEmpty());
            }
        }.dispatch(testedControl.getEnvironment()).booleanValue());

        final String TESTED_TEXT = "MM_dd_YY";
        final String DEFAULT_TEXT = "default";

        for(SettingType settingType : SettingType.values()) {
            System.out.format("Testing binding:%s\n", settingType.toString());

            setPropertyByTextField(settingType, Properties.prompttext, TESTED_TEXT);
            checkTextFieldText(Properties.prompttext, TESTED_TEXT);

            switchOffBinding(settingType, Properties.prompttext);
            setPropertyByTextField(SettingType.SETTER, Properties.prompttext, DEFAULT_TEXT);
        }
        checkFinalState();
    }

    /**
     * Test that TextField returned by getEditor() method remains the same after
     * changing editable property
     */
    @Test(timeout = 10000)
    public void editorProperty() throws Throwable {
        rememberInitialState(Properties.showing, Properties.hover, Properties.pressed, Properties.armed);

        new PropertyTest.EditorPropertyTest(testedControl).test();

        checkFinalState();
    }
};