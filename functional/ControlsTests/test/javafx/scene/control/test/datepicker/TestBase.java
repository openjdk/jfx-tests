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

import com.sun.javafx.scene.control.skin.LabeledText;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.scene.control.test.datepicker.DatePickerApp.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.fx.control.TextInputControlDock;
import org.jemmy.interfaces.Keyboard;
import org.jemmy.interfaces.Mouse.MouseButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.JemmyUtils;
import static test.javaclient.shared.TestUtil.isEmbedded;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class TestBase extends UtilTestFunctions {

    static Wrap<? extends DatePicker> testedControl;
    static Wrap<? extends Scene> scene;
    static Wrap<? extends Scene> popupScene;
    protected boolean resetHardByDefault = true;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;
    static final LocalDate DEFAULT_DATE = LocalDate.of(1990, Month.OCTOBER, 11);

    //WIN7 : W : 249.0 H : 228.0 ; 8.0b109 ; (H-U:W:270.0;H:297.0)
    //LIN : TODO:
    //MAC : TODO:
    static final int POPUP_MIN_EXPECTED_WIDTH = 240;
    static final int POPUP_MAX_EXPECTED_WIDTH = 260;
    static final int POPUP_MIN_EXPECTED_HEIGHT = 220;
    static final int POPUP_MAX_EXPECTED_HEIGHT = 240;

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
        Locale.setDefault(Locale.ENGLISH);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        JemmyUtils.setJemmyComparatorByDistance(0.01f);
        DatePickerApp.main(null);
        currentSettingOption = SettingOption.PROGRAM;
    }

    @After
    public void tearDown() throws InterruptedException {
        if (isPopupVisible()) {
            scene.mouse().click(1, new Point(0, 0));
        }

        if (doNextResetHard) {
            resetSceneHard();
        } else {
            resetSceneSoft();
        }

        doNextResetHard = resetHardByDefault;
    }

    protected void initWrappers() {
        scene = Root.ROOT.lookup().wrap();
        parent = scene.as(Parent.class, Node.class);

        testedControl = (Wrap<? extends DatePicker>) parent.lookup(DatePicker.class, new ByID<DatePicker>(TESTED_DATEPICKER_ID)).wrap();
    }

    //                   ACTIONS
    protected void resetSceneSoft() {
        clickButtonForTestPurpose(SOFT_RESET_BUTTON_ID);
    }

    protected void resetSceneHard() {
        clickButtonForTestPurpose(HARD_RESET_BUTTON_ID);
    }

    protected void doNextResetHard() {
        doNextResetHard = true;
    }

    protected void doNextResetSoft() {
        doNextResetHard = false;
    }

    protected void clickControl() {
        testedControl.mouse().click();
    }

    protected void clickDropDownButton() {
        testedControl.as(Parent.class, Node.class).lookup(Region.class, new ByStyleClass("arrow-button")).wrap().mouse().click();
    }

    protected Wrap<? extends Scene> getPopupWrap() throws InterruptedException {
        try {
            return Root.ROOT.lookup(new LookupCriteria<Scene>() {
                int shift;
                int widthAddition;
                int heightAddition;

                {
                    Boolean weekNumbersShowing = new GetAction<Boolean>() {
                        @Override
                        public void run(Object... parameters) throws Exception {
                            setResult(Boolean.valueOf(testedControl.getControl().isShowWeekNumbers()));
                        }
                    }.dispatch(testedControl.getEnvironment());

                    Boolean isHijrahUmalqura = new GetAction<Boolean>() {
                        @Override
                        public void run(Object... parameters) throws Exception {
                            setResult(Boolean.valueOf(testedControl.getControl().getChronology().equals(Chronology.of("Hijrah-umalqura"))));
                        }
                    }.dispatch(testedControl.getEnvironment());

                    if (weekNumbersShowing == Boolean.TRUE) {
                        shift = 27;
                    }

                    if (isHijrahUmalqura) {
                        widthAddition = 20;
                        heightAddition = 77;
                    }
                }

                public boolean check(Scene cntrl) {
                    return cntrl.lookup(".date-picker-popup") != null;
                }
            }).wrap();
        } catch (Exception e) {
            return null;
        }
    }

    protected boolean isPopupVisible() throws InterruptedException {
        if ((popupScene = getPopupWrap()) != null) {
            System.out.println("Popup was found");
            Boolean res = new GetAction<Boolean>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(popupScene.getControl().getWindow().showingProperty().getValue());
                }
            }.dispatch(Root.ROOT.getEnvironment());
            if (res) {
                System.out.println("Checking coordinates");
                checkPopupCoordinates(popupScene);
            }
            return res;
        } else {
            System.out.println("Popup was not found.");
            return false;
        }
    }

    public void checkPopupVisibility(boolean expectedVisibility) throws InterruptedException {
        assertEquals(expectedVisibility, isPopupVisible());
    }

    protected void showPopup() throws InterruptedException {
        if (!isPopupVisible()) {
            clickDropDownButton();
            waitPopupShowingState(true);
            final Wrap<? extends Scene> popupWrap = getPopupWrap();
            assertNotNull(popupWrap);
            checkPopupCoordinates(popupWrap);
        }
    }

    protected void waitPopupShowingState(final boolean showing) throws InterruptedException {
        testedControl.waitState(new State() {
            public Object reached() {
                if (testedControl.getControl().isShowing() == showing) {
                    try {
                        if (showing == isPopupVisible()) {
                            return true;
                        }
                    } catch (InterruptedException ex) {
                        return null;
                    }
                    return true;
                } else {
                    return null;
                }
            }

            @Override public String toString() { return String.format("[Popup menu is showing = %b]", showing); }
        });
    }

    /*
     * Checks alignment of the popup with the date field
     */
    protected void checkPopupCoordinates(final Wrap<? extends Scene> popupWrap) throws InterruptedException {
        System.out.println("popupWrap = " + popupWrap);
        System.out.println("popupWrap.as(Parent.class, Pane.class) = " + popupWrap.as(Parent.class, Pane.class));
        Lookup lookup = popupWrap.as(Parent.class, Pane.class).lookup(new ByStyleClass("date-picker-popup"));
        assertEquals("[Popup content not found]", 1, lookup.size());

        Wrap content = lookup.wrap();

        assertEquals(testedControl.getScreenBounds().x, content.getScreenBounds().x, 1);
        assertEquals(testedControl.getScreenBounds().y + testedControl.getScreenBounds().height, content.getScreenBounds().y, 1);
    }

    Wrap getRootWrap(final Wrap<? extends Scene> popupWrap) {
        return popupWrap.as(Parent.class, Node.class).lookup(new ByStyleClass("date-picker-popup")).wrap();
    }

    void selectAllText(TextInputControlDock input) {
        input.keyboard().pushKey(Keyboard.KeyboardButtons.A, Utils.isMacOS() ? Keyboard.KeyboardModifiers.META_DOWN_MASK : Keyboard.KeyboardModifiers.CTRL_DOWN_MASK);
    }

    public class DateCellDescription {

        public final int mainDate;
        public final int secondaryDate;

        public DateCellDescription(Wrap<? extends DateCell> dateCellWrap) {
            Lookup lookup = dateCellWrap.as(Parent.class, Node.class).lookup();
            if (lookup.lookup(LabeledText.class).size() > 0) {
                final Wrap<? extends LabeledText> mainText = lookup.lookup(LabeledText.class).wrap();
                mainDate = Integer.parseInt(getText(mainText));
            } else {
                mainDate = -1;
            }

            if (lookup.lookup(Text.class, new ByStyleClass("secondary-text")).size() > 0) {
                final Wrap<? extends Text> secondaryText = lookup.lookup(Text.class, new ByStyleClass("secondary-text")).wrap();
                secondaryDate = Integer.parseInt(getText(secondaryText));
            } else {
                secondaryDate = -1;
            }
        }

        @Override
        public String toString() {
            return String.format("%d", mainDate);
        }
    }

    public class PopupInfoDescription {

        public final String monthName;
        public final String year;
        public final String secondaryLabel;
        public final List<Integer> weekNumbers;
        public final List<String> dayNames;
        public final List<DateCellDescription> previousMonthDays;
        public final List<DateCellDescription> currentMonthDays;
        public final List<DateCellDescription> nextMonthDays;
        public final DateCellDescription today;
        public final DateCellDescription selectedDay;
        public final DateCellDescription focusedDay;

        public PopupInfoDescription(PopupSceneDescription sceneDescription) {
            if (sceneDescription.today != null) {
                today = new DateCellDescription(sceneDescription.today);
            } else {
                today = null;
            }

            monthName = getLabelText(sceneDescription.currentMonthWrap);
            year = getLabelText(sceneDescription.currentYearWrap);

            if (sceneDescription.weeksNumbers.size() > 0) {
                assertEquals(sceneDescription.weeksNumbers.size(), 6);
            }

            weekNumbers = new ArrayList();
            for (Wrap<? extends DateCell> dateCell : sceneDescription.weeksNumbers) {
                weekNumbers.add(new DateCellDescription(dateCell).mainDate);
            }

            currentMonthDays = new ArrayList();
            for (Wrap<? extends DateCell> cell : sceneDescription.currentMonthDays) {
                currentMonthDays.add(new DateCellDescription(cell));
            }

            previousMonthDays = new ArrayList();
            for (Wrap<? extends DateCell> cell : sceneDescription.previousMonthDays) {
                previousMonthDays.add(new DateCellDescription(cell));
            }

            nextMonthDays = new ArrayList();
            for (Wrap<? extends DateCell> cell : sceneDescription.nextMonthDays) {
                nextMonthDays.add(new DateCellDescription(cell));
            }

            if (sceneDescription.secondaryLabel != null) {
                secondaryLabel = getLabelText(sceneDescription.secondaryLabel);
            } else {
                secondaryLabel = null;
            }

            dayNames = new ArrayList();
            assertEquals(sceneDescription.daysNames.size(), 7);
            for (Wrap<? extends DateCell> cell : sceneDescription.daysNames) {
                dayNames.add(getText(cell.as(Parent.class, Node.class).lookup(Text.class).wrap()));
            }

            assertEquals(sceneDescription.nextMonthDays.size()
                    + sceneDescription.previousMonthDays.size()
                    + sceneDescription.currentMonthDays.size(),
                    6 * 7);

            selectedDay = new GetAction<DateCellDescription>() {
                @Override
                public void run(Object... parameters) throws Exception {
                    for (Wrap<? extends DateCell> cell : ((PopupSceneDescription) parameters[0]).currentMonthDays) {
                        if (cell.getControl().getStyleClass().contains("selected")) {
                            setResult(new DateCellDescription(cell));
                        }
                    }
                }
            }.dispatch(testedControl.getEnvironment(), sceneDescription);

            focusedDay = new GetAction<DateCellDescription>() {
                @Override
                public void run(Object... parameters) throws Exception {
                    for (Wrap<? extends DateCell> cell : ((PopupSceneDescription) parameters[0]).previousMonthDays) {
                        if (cell.getControl().isFocused()) {
                            setResult(new DateCellDescription(cell));
                        }
                    }
                    for (Wrap<? extends DateCell> cell : ((PopupSceneDescription) parameters[0]).currentMonthDays) {
                        if (cell.getControl().isFocused()) {
                            setResult(new DateCellDescription(cell));
                        }
                    }
                    for (Wrap<? extends DateCell> cell : ((PopupSceneDescription) parameters[0]).nextMonthDays) {
                        if (cell.getControl().isFocused()) {
                            setResult(new DateCellDescription(cell));
                        }
                    }
                }
            }.dispatch(testedControl.getEnvironment(), sceneDescription);
        }
    }

    public class PopupSceneDescription {

        public List<Wrap<? extends DateCell>> previousMonthDays;
        public List<Wrap<? extends DateCell>> currentMonthDays;
        public List<Wrap<? extends DateCell>> nextMonthDays;
        public List<Wrap<? extends DateCell>> weeksNumbers;
        public List<Wrap<? extends DateCell>> daysNames;
        public Wrap<? extends Button> nextMonthWrap;
        public Wrap<? extends Label> currentMonthWrap;
        public Wrap<? extends Button> previousMonthWrap;
        public Wrap<? extends Button> nextYearWrap;
        public Wrap<? extends Label> currentYearWrap;
        public Wrap<? extends Button> previousYearWrap;
        public Wrap<? extends DateCell> today;
        public Wrap<? extends Label> secondaryLabel;

        /**
         * Performs the exploration of the popup and wraps it graphical
         * components
         */
        public void extractData() throws InterruptedException {
            checkPopupVisibility(true);
            popupScene = getPopupWrap();
            Parent<Node> sceneParent = popupScene.as(Parent.class, Node.class);

            previousMonthWrap = sceneParent.lookup(Button.class, new ByStyleClass("left-button")).wrap(0);
            previousYearWrap = sceneParent.lookup(Button.class, new ByStyleClass("left-button")).wrap(1);

            nextMonthWrap = sceneParent.lookup(Button.class, new ByStyleClass("right-button")).wrap(0);
            nextYearWrap = sceneParent.lookup(Button.class, new ByStyleClass("right-button")).wrap(1);

            currentMonthWrap = sceneParent.lookup(Label.class, new ByStyleClass("spinner-label")).wrap(0);
            currentYearWrap = sceneParent.lookup(Label.class, new ByStyleClass("spinner-label")).wrap(1);

            weeksNumbers = new ArrayList<Wrap<? extends DateCell>>();
            Lookup weekNumbersLookup = sceneParent.lookup(DateCell.class, new ByStyleClass("week-number-cell"));
            for (int i = 0; i < weekNumbersLookup.size(); i++) {
                weeksNumbers.add(weekNumbersLookup.wrap(i));
            }

            previousMonthDays = new ArrayList<Wrap<? extends DateCell>>();
            currentMonthDays = new ArrayList<Wrap<? extends DateCell>>();
            nextMonthDays = new ArrayList<Wrap<? extends DateCell>>();
            Lookup monthDaysLookup = sceneParent.lookup(DateCell.class, new ByStyleClass("day-cell"));
            for (int i = 0; i < monthDaysLookup.size(); i++) {
                if (((Wrap<? extends DateCell>) monthDaysLookup.wrap(i)).getControl().getStyleClass().contains("previous-month")) {
                    previousMonthDays.add(monthDaysLookup.wrap(i));
                } else if (((Wrap<? extends DateCell>) monthDaysLookup.wrap(i)).getControl().getStyleClass().contains("next-month")) {
                    nextMonthDays.add(monthDaysLookup.wrap(i));
                } else {
                    currentMonthDays.add(monthDaysLookup.wrap(i));
                }
            }

            daysNames = new ArrayList<Wrap<? extends DateCell>>();
            Lookup daysNamesLookup = sceneParent.lookup(DateCell.class, new ByStyleClass("day-name-cell"));
            for (int i = 0; i < daysNamesLookup.size(); i++) {
                daysNames.add(daysNamesLookup.wrap(i));
            }

            final Lookup todayLookup = sceneParent.lookup(DateCell.class, new ByStyleClass("today"));
            if (todayLookup.size() > 0) {
                today = todayLookup.wrap();
            } else {
                today = null;
            }

            final Lookup secondaryLabelLookup = sceneParent.lookup(Label.class, new ByStyleClass("secondary-text"));
            if (secondaryLabelLookup.size() > 0) {
                secondaryLabel = todayLookup.wrap();
            } else {
                secondaryLabel = null;
            }
        }

        public PopupInfoDescription getInfoDescription() throws InterruptedException {
            extractData();
            return new PopupInfoDescription(this);
        }
    }

    protected String getControlText() {
        final Wrap<? extends TextField> tf = testedControl.as(Parent.class, Node.class).lookup(TextField.class).wrap();
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(tf.getControl().getText());
            }
        }.dispatch(testedControl.getEnvironment());
    }

    protected void setDefaultDate() {
        setDate(DEFAULT_DATE);
    }

    protected LocalDate getDate() {
        return new GetAction<LocalDate>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getValue());
            }
        }.dispatch(testedControl.getEnvironment());
    }

    protected void setDate(final LocalDate date) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                testedControl.getControl().setValue(date);
            }
        }.dispatch(testedControl.getEnvironment());
    }

    protected void setChronology(final Chronology ch) {
        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                testedControl.getControl().setChronology(ch);
            }
        }.dispatch(testedControl.getEnvironment());
    }

    protected void waitShownText(final String expectedText) {
        testedControl.waitState(new State<String>() {
            public String reached() {
                return getControlText();
            }

            @Override
            public String toString() {
                return String.format("Waiting [%s] to be shown in the editor."
                        + "Got [%s]", expectedText, getControlText());
            }
        }, expectedText);
    }

    void checkExpectedDate(LocalDate expected) {
        LocalDate actual = new GetAction<LocalDate>() {
            @Override public void run(Object... parameters) throws Exception {
                setResult(testedControl.getControl().getValue());
            }
        }.dispatch(testedControl.getEnvironment());
        assertEquals(expected, actual);
    }

    private int determineDaysInMonth(YearMonth month) {
        return month.atDay(1).plusMonths(1).minusDays(1).getDayOfMonth();
    }

    protected void getDays() {
    }

    private String getText(final Wrap<? extends Text> textNodeWrap) {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(textNodeWrap.getControl().getText());
            }
        }.dispatch(textNodeWrap.getEnvironment());
    }

    private String getLabelText(final Wrap<? extends Label> labelNodeWrap) {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(labelNodeWrap.getControl().getText());
            }
        }.dispatch(labelNodeWrap.getEnvironment());
    }

    protected enum Properties {

        prefWidth, prefHeight, chronology, dayCellFactory,
        converter, showWeekNumbers, hover, showing, editable,
        prompttext,   focused, pressed, armed, width}

    /**
     * By providing correct map of checked fields it is possible to examine
     * PopupInfoDescription instance.
     */
    public static class DateState implements State<Boolean> {

        Map<String, String> expectedState;
        Map<String, String> actualState;
        PopupSceneDescription description;
        PopupInfoDescription info;

        public DateState(Map<String, String> expectedState, PopupSceneDescription description) {
            this.expectedState = expectedState;
            this.description = description;
            actualState = new HashMap<String, String>(expectedState.size());
        }

        public Boolean reached() {
            boolean isReached = true;
            try {
                info = description.getInfoDescription();
                for (String checkedField : expectedState.keySet()) {
                    actualState.put(checkedField, String.valueOf(info.getClass().getField(checkedField).get(info)).toLowerCase());
                    isReached &= expectedState.get(checkedField).toLowerCase().equals(actualState.get(checkedField));
                }
            } catch(InterruptedException iex) {
                Logger.getLogger(TestBase.class.getName()).log(Level.SEVERE, null, iex);
            } finally {
               return isReached ? Boolean.TRUE : null;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (String key : expectedState.keySet()) {
                sb.append(String.format("Expected: [%s], Actual: [%s]", expectedState.get(key), actualState.get(key)));
                sb.append("\n");
            }

            sb.append("]");
            return sb.toString();
        }
    }

    /*
     * Context menu methods.
     */

    final Lookup<Scene> contextMenuLookup = Root.ROOT.lookup(
            new LookupCriteria<Scene>() {
                public boolean check(Scene cntrl) {
                    return cntrl.getWidth() <= 175.0 + (Utils.isLinux() ? 7 : 0)
                            && cntrl.getHeight() <= 82.0 + (Utils.isLinux() ? 5 : 0);
                }
    });

    /*
     * Makes right mouse click on the popup.
     */
    void openContextMenu() throws InterruptedException {
        waitPopupShowingState(true);
        final Wrap<? extends Scene> popupWrap = getPopupWrap();
        popupWrap.mouse().click(1, popupWrap.getClickPoint(), MouseButtons.BUTTON3);
        waitPopupShowingState(true);
    }

    void waitContextMenuShowing(final boolean showing) {
        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                int expected = showing ? 1 : 0;
                return (contextMenuLookup.size() == expected) ? Boolean.TRUE : null;
            }

            @Override
            public String toString() { return String.format("[Context menu is showing = %b]", showing); }
        });
    }

    Wrap getMenuItemLabelWrap(final String itemText) {
        if (null == itemText) {
            throw new IllegalArgumentException("[Cannot lookup 'null' menu item]");
        }

        Wrap<? extends Scene> wrap = contextMenuLookup.wrap();
        Lookup lookup = wrap.as(Parent.class, Label.class).lookup(Label.class, new LookupCriteria<Label>() {
            public boolean check(Label control) {
                return control.getParent().getStyleClass().contains("menu-item")
                       && itemText.equals(control.getText());
            }
        });

        assertEquals("[Context menu item 'Show Today' not fonud]", 1, lookup.size());

        return lookup.wrap();
    }

    Wrap getMenuItemWrap(final String itemText) {
        Wrap<? extends Scene> wrap = contextMenuLookup.wrap();
        Lookup lookup = wrap.as(Parent.class, Region.class).lookup(Region.class, new LookupCriteria<Region>() {
            public boolean check(Region control) {

                if (!control.getStyleClass().contains("menu-item")) return false;

                for (Object object : control.getChildrenUnmodifiable()) {
                    if (Label.class.isAssignableFrom(object.getClass())
                        && itemText.equals(((Label) object).getText())) {

                            return true;
                    }
                }

                return false;
            }
        });

        assertEquals(String.format("[Context menu item '%s' not fonud]", itemText), 1, lookup.size());

        return lookup.wrap();
    }

    void contextMenuShowToday() throws InterruptedException {
        openContextMenu();
        getMenuItemWrap("Show Today").mouse().click();
    }

    void contextMenuShowWeekNumbers() throws InterruptedException {
        openContextMenu();
        getMenuItemWrap("Show Week Numbers").mouse().click();
    }

    Wrap<? extends Scene> getContextMenuWrap() {
        return contextMenuLookup.wrap();
    }

    Wrap getContextMenuContentWrap() {
        return getContextMenuWrap().as(Parent.class, Region.class)
               .lookup(Region.class, new ByStyleClass("context-menu")).wrap();
    }

    /*
     * Getter and setter wrappers
     */
    void setDayCellFactory(Callback<DatePicker, DateCell> dayCellFactory) {
        new GetAction<Void>() {
            @Override public void run(Object... parameters) throws Exception {
                testedControl.getControl().setDayCellFactory((Callback<DatePicker, DateCell>) parameters[0]);
            }
        }.dispatch(testedControl.getEnvironment(), dayCellFactory);
    }

    Callback<DatePicker, DateCell> getDayCellFactory() {
        return new GetAction<Callback<DatePicker, DateCell>>() {
            @Override public void run(Object... parameters) throws Exception {
                setResult(testedControl.getControl().getDayCellFactory());
            }
        }.dispatch(testedControl.getEnvironment());
    }

    void setConverter(StringConverter<LocalDate> converter) {
        new GetAction<Void>() {
            @Override public void run(Object... parameters) throws Exception {
                testedControl.getControl().setConverter((StringConverter<LocalDate>) parameters[0]);
            }
        }.dispatch(testedControl.getEnvironment(), converter);
    }

    StringConverter<LocalDate> getConverter() {
        return new GetAction<StringConverter>() {
            @Override public void run(Object... parameters) throws Exception {
                setResult(testedControl.getControl().getConverter());
            }
        }.dispatch(testedControl.getEnvironment());
    }
}
