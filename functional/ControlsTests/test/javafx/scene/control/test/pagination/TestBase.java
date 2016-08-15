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

import com.sun.javafx.scene.control.skin.LabeledText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import static javafx.scene.control.test.pagination.PaginationApp.*;
import javafx.scene.control.test.util.UtilTestFunctions;
import static javafx.scene.control.test.utils.ComponentsFactory.*;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.layout.StackPane;
import org.jemmy.Point;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;
import org.jemmy.fx.ByID;
import org.jemmy.fx.Root;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Text;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;
import org.jemmy.timing.State;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import static test.javaclient.shared.TestUtil.isEmbedded;

/**
 * @author Alexander Kirov
 */
public class TestBase extends UtilTestFunctions {

    static Wrap<? extends Pagination> testedControl;
    static Wrap<? extends Scene> scene;
    static Wrap<? extends Scene> popupScene;
    protected boolean resetHardByDefault = true;//switcher of hard and soft reset mode.
    protected boolean doNextResetHard = resetHardByDefault;
    private final static String leftArrowButtonStyleClass = "left-arrow";
    private final static String rightArrowButtonStyleClass = "right-arrow";

    @Before
    public void setUp() {
        initWrappers();
        Environment.getEnvironment().setTimeout("wait.state", isEmbedded() ? 60000 : 2000);
        Environment.getEnvironment().setTimeout("wait.control", isEmbedded() ? 60000 : 1000);
        scene.mouse().move(new Point(0, 0));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        PaginationApp.main(null);
        currentSettingOption = SettingOption.PROGRAM;
    }

    @After
    public void tearDown() {
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

        testedControl = (Wrap<? extends Pagination>) parent.lookup(Pagination.class, new ByID<Pagination>(TESTED_PAGINATION_ID)).wrap();
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

    protected void setNewFactory() {
        clickButtonForTestPurpose(SET_NEW_PAGE_FACTORY_BUTTON_ID);
    }

    protected void setOldFactory() {
        clickButtonForTestPurpose(SET_OLD_PAGE_FACTORY_BUTTON_ID);
    }

    protected void setBulletStyleOfPageIndicators() {
        clickButtonForTestPurpose(SET_BULLET_PAGE_INDICATOR_BUTTON_ID);
    }

    protected void setIndeterminatePageCount() {
        clickButtonForTestPurpose(SET_PAGE_COUNT_TO_INDETERMINATE_BUTTON_ID);
    }

    protected void checkFormComponentButton() {
        clickButtonByID(FORM_BUTTON_ID);
        testedControl.waitState(new State<Integer>() {
            public Integer reached() {
                return Integer.parseInt(findTextField(FORM_CLICK_TEXT_FIELD_ID).getControl().getText());
            }
        }, 1);
    }

    protected void checkScrollingOfFormComponentScrollBar() {
        Wrap<? extends ScrollBar> scrollBar = findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true);
        scrollBar.mouse().turnWheel(-1);
        testedControl.waitState(new State<Integer>() {
            public Integer reached() {
                return Integer.parseInt(findTextField(FORM_SCROLL_TEXT_FIELD_ID).getControl().getText());
            }
        }, 1);

        AbstractScroll c = scrollBar.as(AbstractScroll.class);
        c.allowError(0.01);
        double meanValue = getScrollBarCenter(scrollBar);
        c.caret().to(meanValue);
        assertTrue(Math.abs(meanValue - getScrollBarValue(scrollBar)) <= 1);
    }

    protected double getScrollBarValue(final Wrap<? extends ScrollBar> scrollBar) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(scrollBar.getControl().getValue());
            }
        }.dispatch(scrollBar.getEnvironment());
    }

    protected double getScrollBarCenter(final Wrap<? extends ScrollBar> scrollBar) {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult((scrollBar.getControl().getMax() - scrollBar.getControl().getMin()) / 2);
            }
        }.dispatch(scrollBar.getEnvironment());
    }

    protected void checkPrintingInInnerTextField() {
        final Wrap<? extends TextArea> editWrap = parent.lookup(TextArea.class, new ByID<TextArea>(FORM_TEXT_AREA_ID)).wrap();
        editWrap.as(Text.class).clear();
        editWrap.as(Text.class).type("New string");
        editWrap.waitState(new State<String>() {
            public String reached() {
                return editWrap.getControl().getText();
            }
        }, "New string");
    }

    protected void navigateToPage(int pageIndex, NavigationWay nav) {
        int direction;
        while ((direction = checkDirectionForSearch(pageIndex)) != 0) {
            if (direction == 1) {
                navigateToRight(nav);
            }
            if (direction == -1) {
                navigateToLeft(nav);
            }
        }
        Wrap<? extends LabeledText>[] array = getSortedPageIndeces();
        for (int i = 0; i < array.length; i++) {
            if (Integer.parseInt(array[i].getControl().getText()) == pageIndex) {
                array[i].mouse().click();
                return;
            }
        }
    }

    private void navigateToRight(NavigationWay nav) {
        switch (nav) {
            case KEYBOARD:
                testedControl.mouse().click(1, new Point(10, 10));
                testedControl.keyboard().pushKey(KeyboardButtons.RIGHT);
                break;
            case MOUSE:
                clickArrowByStyleClass(rightArrowButtonStyleClass);
                break;
        }
    }

    private void navigateToLeft(NavigationWay nav) {
        switch (nav) {
            case KEYBOARD:
                testedControl.mouse().click(1, new Point(10, 10));
                testedControl.keyboard().pushKey(KeyboardButtons.LEFT);
                break;
            case MOUSE:
                clickArrowByStyleClass(leftArrowButtonStyleClass);
                break;
        }
    }

    /**
     * Checks, that shown content is respect to according page.
     *
     * @param isNewFactory : true - new; false - old.
     * @param pageIndex
     */
    protected void checkCorrectPageContentShowing(final boolean isNewFactory, final int pageIndex) {
        assertEquals(testedControl.as(Parent.class, Node.class).lookup(Label.class, new LookupCriteria<Label>() {
            public boolean check(Label cntrl) {
                return (cntrl.getText().contains(PAGE_INDEX_PREFIX + pageIndex))
                        && (cntrl.getText().contains(isNewFactory ? NEW_FACTORY_MARKER : OLD_FACTORY_MARKER));
            }
        }).size(), LABELS_PER_PAGE, 0);
    }

    protected void setSize(double width, double height) throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minWidth, width);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minHeight, height);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, width);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, height);
    }

    /**
     * 0 - begin, 1 - end.
     *
     * @param pos
     */
    protected void checkScrollBarPosition(final int pos) {
        ScrollBar sb = getScrollbarWrap(true).getControl();
        if (pos == 1) {
            assertTrue(sb.getMax() == sb.getValue());
        }
        if (pos == 0) {
            assertTrue(sb.getMin() == sb.getValue());
        }
    }

    protected Wrap<? extends ScrollBar> getScrollbarWrap(boolean visible) {
        return findScrollBar(testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, visible);
    }

    protected void checkNumberOfVisiblePageIndeces(int expectedIndeces) {
        checkRightPageIndecesOrderAndContinuousOrder();
        Wrap<? extends LabeledText>[] array = getSortedPageIndeces();
        int len = array.length;
        assertEquals(len, expectedIndeces, 0);
    }

    protected void checkRangeOfPageIndecesVisibility(int expectedStart, int expectedEnd) {
        checkNumberOfVisiblePageIndeces(expectedEnd - expectedStart + 1);
        Wrap<? extends LabeledText>[] array = getSortedPageIndeces();
        int len = array.length;

        assertEquals(Integer.parseInt(array[0].getControl().getText()), expectedStart, 0);
        assertEquals(Integer.parseInt(array[len - 1].getControl().getText()), expectedEnd, 0);
    }

    protected void checkRightPageIndecesOrderAndContinuousOrder() {
        testedControl.waitState(new State() {
            public Object reached() {
                Wrap<? extends LabeledText>[] array = getSortedPageIndeces();
                return array.length > 0 ? true : null;
            }
        });

        Wrap<? extends LabeledText>[] array = getSortedPageIndeces();

        double initialY = array[0].getScreenBounds().y;
        for (int i = 1; i < array.length; i++) {
            assertTrue(Math.abs(initialY - array[i].getScreenBounds().y) <= yDeltaInPageIndeces);
            assertTrue(array[i - 1].getScreenBounds().x < array[i].getScreenBounds().x);
            assertTrue(Integer.parseInt(array[i - 1].getControl().getText()) + 1 == Integer.parseInt(array[i].getControl().getText()));
        }
    }

    private void clickArrowByStyleClass(final String name) {
        Lookup res = findArrow(name);

        if (res.size() == 0) {
            throw new IllegalStateException("Cannot find arrow : " + name);
        }

        if (((Node) ((Wrap) res.wrap()).getControl()).isVisible()) {
            ((Wrap) res.wrap()).mouse().click();
        } else {
            throw new IllegalStateException("Want to press an invisible control.");
        }
    }

    private Lookup findArrow(final String name) {
        return testedControl.as(Parent.class, Node.class).lookup(StackPane.class, new LookupCriteria<StackPane>() {
            public boolean check(StackPane cntrl) {
                if ((cntrl.getStyleClass().size() == 1) && (cntrl.getStyleClass().get(0).equals(name))) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    protected void checkTwoArrowsVisibility(boolean leftVisiblity, boolean rightVisibility) {
        checkArrowNonDisabledState(true, leftVisiblity);
        checkArrowNonDisabledState(false, rightVisibility);
    }

    protected int adjustNumber(int min, int max, int value) {
        assertTrue(min < max);
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    /**
     *
     * @param left - true - left, false - right.
     */
    protected void checkArrowNonDisabledState(boolean left, boolean nonDisabled) {
        final String name = left ? leftArrowButtonStyleClass : rightArrowButtonStyleClass;
        Lookup res = findArrow(name);

        if (res.size() == 0) {
            throw new IllegalStateException("Cannot find arrow : " + name);
        }

        assertEquals(((Node) ((Wrap) res.wrap()).getControl()).isDisabled(), !nonDisabled);
    }

    /**
     * @param itemToFind - page index.
     * @return +1 to press right; -1 to press left; 0, if we can click item and
     * it is visible. can throw exception.
     */
    private int checkDirectionForSearch(int itemToFind) {
        checkRightPageIndecesOrderAndContinuousOrder();
        Wrap<? extends LabeledText>[] array = getSortedPageIndeces();
        int len = array.length;

        for (int i = 0; i < len; i++) {
            if (Integer.parseInt(array[i].getControl().getText()) == itemToFind) {
                return 0;
            }
        }

        if (Integer.parseInt(array[0].getControl().getText()) > itemToFind) {
            return -1;
        }
        if (Integer.parseInt(array[len - 1].getControl().getText()) < itemToFind) {
            return +1;
        }

        throw new IllegalStateException("Can't find direction for search because of unknown issue. " + array.toString());
    }

    private Lookup<LabeledText> findVisiblePageIndeces() {
        final Parent<Node> testedControlAsParent = testedControl.as(Parent.class, Node.class);
        Parent<Node> foundStackPaneInControlAsParent = testedControlAsParent.lookup(StackPane.class, new LookupCriteria<StackPane>() {
            public boolean check(StackPane cntrl) {
                if (cntrl.getStyleClass().contains("pagination-control")) {
                    return true;
                } else {
                    return false;
                }
            }
        }).wrap().as(Parent.class, Node.class);
        return foundStackPaneInControlAsParent.lookup(LabeledText.class, new LookupCriteria<LabeledText>() {
            public boolean check(LabeledText cntrl) {
                try {
                    Integer.parseInt(cntrl.getText());
                } catch (Throwable ex) {
                    return false;
                }
                //return !cntrl.getText().contains("/");
                return true;
            }
        });
    }

    private Wrap<? extends LabeledText>[] getSortedPageIndeces() {
        Lookup<LabeledText> visiblePageIndeces = findVisiblePageIndeces();

        ArrayList<Wrap<? extends LabeledText>> list = new ArrayList<Wrap<? extends LabeledText>>();
        for (int i = 0; i < visiblePageIndeces.size(); i++) {
            list.add(visiblePageIndeces.wrap(i));
        }
        Wrap<? extends LabeledText>[] array = list.toArray(new Wrap[0]);

        Arrays.sort(array, new Comparator<Wrap<? extends LabeledText>>() {
            public int compare(Wrap<? extends LabeledText> t, Wrap<? extends LabeledText> t1) {
                String s1 = t.getControl().getText();
                String s2 = t1.getControl().getText();
                int int1 = Integer.parseInt("".equals(s1) ? "-1" : s1);
                int int2 = Integer.parseInt("".equals(s2) ? "-1" : s2);

                return int1 - int2;
            }
        });

        return array;
    }
    private double yDeltaInPageIndeces = 0;

    static protected enum NavigationWay {

        MOUSE, KEYBOARD
    };

    static protected enum Properties {

        minWidth, minHeight, prefWidth, prefHeight, maxHeight, maxWidth, currentPageIndex, pageCount, maxPageIndicatorCount
    };
}
