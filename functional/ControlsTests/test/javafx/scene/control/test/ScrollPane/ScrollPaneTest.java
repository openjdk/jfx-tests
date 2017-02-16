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
package javafx.scene.control.test.ScrollPane;

import client.test.ScreenshotCheck;
import client.test.Smoke;
import com.oracle.jdk.sqe.cc.markup.Covers;
import com.oracle.jdk.sqe.cc.markup.Covers.Level;
import java.util.EnumSet;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import static javafx.scene.control.test.ScrollPane.NewScrollPaneApp.*;
import javafx.scene.control.test.util.ScrollingChecker;
import javafx.scene.control.test.utils.ContentMotion;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.env.Timeout;
import org.jemmy.fx.ByID;
import org.jemmy.fx.NodeDock;
import org.jemmy.fx.Root;
import org.jemmy.fx.SceneDock;
import org.jemmy.input.AbstractScroll;
import org.jemmy.interfaces.Keyboard.KeyboardButtons;
import org.jemmy.interfaces.Keyboard.KeyboardModifiers;
import org.jemmy.interfaces.Parent;
import org.jemmy.timing.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
@RunWith(FilteredTestRunner.class)
public class ScrollPaneTest extends TestBase {

    @Before
    public void setUp() {
        initWrappers();
        scene.mouse().move(new Point(0, 0));
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.vmax.DEFAULT", "javafx.scene.control.ScrollPane.vmax.GET"}, level = Level.FULL)
    public void checkVMaxOptionTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        Assert.assertEquals((new ScrollPane()).vmaxProperty().getValue(), 1, commonComparePrecision);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.vmax, 200);
        checkTextFieldValue(Properties.vmax, 200);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.vvalue, 200);
        checkTextFieldValue(Properties.vvalue, 200);

        Wrap<? extends ScrollBar> sb1 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
        sb1.as(AbstractScroll.class).to(0);
        checkTextFieldValue(Properties.vvalue, 0);

        Wrap<? extends ScrollBar> sb2 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
        sb2.as(AbstractScroll.class).to(200);
        checkTextFieldValue(Properties.vvalue, 200);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(-1 * (Utils.isMacOS() ? -1 : 1));

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(+1 * (Utils.isMacOS() ? -1 : 1));
        checkTextFieldValue(Properties.vvalue, 200);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.vmax, 150);
        checkTextFieldValue(Properties.vmax, 150);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.vmax, -150);
        checkTextFieldValue(Properties.vmax, -100);
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.vmin.DEFAULT", "javafx.scene.control.ScrollPane.vmin.GET"}, level = Level.FULL)
    public void checkVMinOptionTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        Assert.assertEquals((new ScrollPane()).vminProperty().getValue(), 0, commonComparePrecision);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.vmin, -100);
        checkTextFieldValue(Properties.vmin, -100);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.vvalue, -100);
        checkTextFieldValue(Properties.vvalue, -100);

        Wrap<? extends ScrollBar> sb1 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
        sb1.as(AbstractScroll.class).to(1);
        checkTextFieldValue(Properties.vvalue, 1);

        Wrap<? extends ScrollBar> sb2 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
        sb2.as(AbstractScroll.class).to(-100);
        checkTextFieldValue(Properties.vvalue, -100);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(+1 * (Utils.isMacOS() ? -1 : 1));

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).mouse().turnWheel(-1 * (Utils.isMacOS() ? -1 : 1));
        checkTextFieldValue(Properties.vvalue, -100);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.vmin, -50);
        checkTextFieldValue(Properties.vmin, -50);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.vmin, +150);
        checkTextFieldValue(Properties.vmin, +150);
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.hmax.DEFAULT", "javafx.scene.control.ScrollPane.hmax.GET"}, level = Level.FULL)
    public void checkHMaxOptionTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        Assert.assertEquals((new ScrollPane()).hmaxProperty().getValue(), 1, commonComparePrecision);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hmax, 200);
        checkTextFieldValue(Properties.hmax, 200);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hvalue, 200);
        checkTextFieldValue(Properties.hvalue, 200);

        Wrap<? extends ScrollBar> sb1 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true);
        sb1.as(AbstractScroll.class).to(0);
        checkTextFieldValue(Properties.hvalue, 0);

        Wrap<? extends ScrollBar> sb2 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true);
        sb2.as(AbstractScroll.class).to(200);
        checkTextFieldValue(Properties.hvalue, 200);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(-1);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(+1);
        checkTextFieldValue(Properties.hvalue, 200);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.hmax, 150);
        checkTextFieldValue(Properties.hmax, 150);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.hmax, -150);
        checkTextFieldValue(Properties.hmax, -100);
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.hmin.DEFAULT", "javafx.scene.control.ScrollPane.hmin.GET"}, level = Level.FULL)
    public void checkHMinOptionTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        Assert.assertEquals((new ScrollPane()).vminProperty().getValue(), 0, commonComparePrecision);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hmin, -100);
        checkTextFieldValue(Properties.hmin, -100);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hvalue, -100);
        checkTextFieldValue(Properties.hvalue, -100);

        Wrap<? extends ScrollBar> sb1 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true);
        sb1.as(AbstractScroll.class).to(1);
        checkTextFieldValue(Properties.hvalue, 1);

        Wrap<? extends ScrollBar> sb2 = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true);
        sb2.as(AbstractScroll.class).to(-100);
        checkTextFieldValue(Properties.hvalue, -100);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(+1);

        findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).mouse().turnWheel(-1);
        checkTextFieldValue(Properties.hvalue, -100);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.hmin, -50);
        checkTextFieldValue(Properties.hmin, -50);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.hmin, +150);
        checkTextFieldValue(Properties.hmin, +150);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void checkH_V_ValueOptionTest() throws InterruptedException, Throwable {
        Assert.assertEquals((new ScrollPane()).hvalueProperty().getValue(), 0, commonComparePrecision);
        Assert.assertEquals((new ScrollPane()).vvalueProperty().getValue(), 0, commonComparePrecision);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hvalue, 200);
        checkTextFieldValue(Properties.hvalue, 1);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.vvalue, 200);
        checkTextFieldValue(Properties.vvalue, 1);

        checkScreenshot("ScrollPane_H_V_Values_test", testedControl);
        throwScreenshotError();
    }

    /**
     * Assume, that scrollPane contain rectangle 200X200 pixels
     */
    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.prefViewportHeight.GET", "javafx.scene.control.ScrollPane.prefViewportHeight.DEFAULT"}, level = Level.FULL)
    public void checkPrefViewPortHeightOptionTest() throws InterruptedException {
        Assert.assertEquals((new ScrollPane()).prefViewportHeightProperty().getValue(), 0, commonComparePrecision);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 150);
        checkTextFieldValue(Properties.prefViewportHeight, 150);
        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true) == null);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefViewportHeight, 230);
        checkTextFieldValue(Properties.prefViewportHeight, 230);
        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, false) == null);
    }

    /**
     * Assume, that scrollPane contain rectangle 200X200 pixels
     */
    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.prefViewportWidth.GET", "javafx.scene.control.ScrollPane.prefViewportWidth.DEFAULT"}, level = Level.FULL)
    public void checkPrefViewPortWidthOptionTest() throws InterruptedException {
        Assert.assertEquals((new ScrollPane()).prefViewportWidthProperty().getValue(), 0, commonComparePrecision);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 150);
        checkTextFieldValue(Properties.prefViewportWidth, 150);
        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true) == null);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.prefViewportWidth, 230);
        checkTextFieldValue(Properties.prefViewportWidth, 230);
        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, false) == null);
    }

    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.prefViewportWidth.GET", "javafx.scene.control.ScrollPane.prefViewportWidth.DEFAULT"}, level = Level.FULL)
    public void checkMinViewPortWidthSetTest() throws InterruptedException {
        Assert.assertEquals((new ScrollPane()).prefViewportWidthProperty().getValue(), 0, commonComparePrecision);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.minViewportWidth, 600);
        checkTextFieldValue(Properties.width, 600, 20);
    }

    @Smoke
    @Test(timeout = 300000)//Property affecting is verified in other test.
    @Covers(value = {"javafx.scene.control.ScrollPane.pannable.GET", "javafx.scene.control.ScrollPane.pannable.DEFAULT"}, level = Level.FULL)
    public void checkPannableOptionTest() throws InterruptedException {
        Assert.assertFalse((new ScrollPane()).pannableProperty().getValue());

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, TestBase.Properties.pannable);
        checkTextFieldText(Properties.pannable, "true");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, TestBase.Properties.pannable);
        checkTextFieldText(Properties.pannable, "false");

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, TestBase.Properties.pannable);
        checkTextFieldText(Properties.pannable, "true");

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, TestBase.Properties.pannable);
        checkTextFieldText(Properties.pannable, "false");
    }

    @Smoke
    @Test(timeout = 300000)
    @Covers(value = {"javafx.scene.control.ScrollPane.fitToHeight.GET", "javafx.scene.control.ScrollPane.fitToHeight.DEFAULT",
        "javafx.scene.control.ScrollPane.fitToWidth.GET", "javafx.scene.control.ScrollPane.fitToWidth.DEFAULT"}, level = Level.FULL)
    public void checkFitToWidthAndHeightOptionTest() throws InterruptedException, Throwable {
        Assert.assertEquals((new ScrollPane()).fitToWidthProperty().getValue(), false);
        Assert.assertEquals((new ScrollPane()).fitToHeightProperty().getValue(), false);

        changeContentToResizable();

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.fitToWidth);
        checkTextFieldText(Properties.fitToWidth, "true");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.fitToWidth);
        checkTextFieldText(Properties.fitToWidth, "false");

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.fitToWidth);
        checkTextFieldText(Properties.fitToWidth, "true");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.fitToHeight);
        checkTextFieldText(Properties.fitToHeight, "true");

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.fitToHeight);
        checkTextFieldText(Properties.fitToHeight, "false");

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, Properties.fitToHeight);
        checkTextFieldText(Properties.fitToHeight, "true");

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 200);

        checkScreenshot("ScrollPane_fitToSize", testedControl);
        throwScreenshotError();
    }

    private enum Influence {

        INSIDEOUT, OUTSIDEIN
    };

    private void checkScrollbarsVisibility(boolean horizontalVisible, boolean verticalVisible) {
        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, horizontalVisible) == null);
        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, verticalVisible) == null);
    }

    @Smoke
    @Test(timeout = 300000)//RT-17395
    @Covers(value = {"javafx.scene.control.ScrollPane.vbarPolicy.GET", "javafx.scene.control.ScrollPane.vbarPolicy.DEFAULT",
        "javafx.scene.control.ScrollPane.hbarPolicy.GET", "javafx.scene.control.ScrollPane.hbarPolicy.DEFAULT"}, level = Level.FULL)
    public void checkHVBarPolicyOptionTest() throws InterruptedException {
        Assert.assertTrue((new ScrollPane()).vbarPolicyProperty().getValue() == ScrollBarPolicy.AS_NEEDED);
        Assert.assertTrue((new ScrollPane()).hbarPolicyProperty().getValue() == ScrollBarPolicy.AS_NEEDED);

        for (SettingType btype : EnumSet.of(SettingType.BIDIRECTIONAL, SettingType.SETTER, SettingType.UNIDIRECTIONAL)) {
            for (Influence influence : EnumSet.of(Influence.INSIDEOUT, Influence.OUTSIDEIN)) {
                for (ScrollPane.ScrollBarPolicy policy : EnumSet.of(ScrollPane.ScrollBarPolicy.ALWAYS, ScrollPane.ScrollBarPolicy.AS_NEEDED, ScrollPane.ScrollBarPolicy.NEVER)) {
                    //Content size == 100X100
                    setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 200);
                    setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 200);

                    setPropertyByChoiceBox(btype, policy, Properties.hbarPolicy);
                    setPropertyByChoiceBox(btype, policy, Properties.vbarPolicy);
                    checkTextFieldText(Properties.hbarPolicy, policy.name());
                    checkTextFieldText(Properties.vbarPolicy, policy.name());

                    switch (policy) {
                        case ALWAYS:
                            checkScrollbarsVisibility(true, true);
                            break;
                        case AS_NEEDED:
                            checkScrollbarsVisibility(false, false);
                            break;
                        case NEVER:
                            checkScrollbarsVisibility(false, false);
                            break;
                    }

                    if (influence == Influence.INSIDEOUT) {
                        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 230);
                        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 230);

                    } else { // Influence.OUTSIDEIN
                        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 50);
                        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 50);

                    }

                    switch (policy) {
                        case ALWAYS:
                            checkScrollbarsVisibility(true, true);
                            break;
                        case AS_NEEDED:
                            checkScrollbarsVisibility(false, false);
                            break;
                        case NEVER:
                            checkScrollbarsVisibility(false, false);
                            break;
                    }
                }
            }
        }
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void contentRotationTest() throws InterruptedException, Throwable {
        rotateContent();
        checkScreenshot("ScrollPaneRotatedContent", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void contentScalingTest() throws InterruptedException, Throwable {
        increaseContentScale();
        checkScreenshot("ScrollPaneScaledContent", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void dynamicContentChangingTest() throws Throwable {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        changeContent();
        //If content has really changed, button will be searchable.
        //If content didn't change, button won't be on scene.
        clickContentButton();

        checkScreenshot("ScrollPane_DynamicContentChanging", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000)//RT-17335
    public void resizingTest() throws InterruptedException, Throwable {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 200);

        currentSettingOption = SettingOption.MANUAL;
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 20);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 20);

        currentSettingOption = SettingOption.PROGRAM;
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 100);

        checkScreenshot("ScrollPaneAfterResizing", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)//RT-17368
    public void viewPortPrefSizeTest() throws InterruptedException {
        changeContentToResizable();
        double[] sizes = {100, 50, 40, 55};

        for (Double size : sizes) {

            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, size);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, size);

            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, size * 2);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, size * 2);

            Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true) == null);
            Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true) == null);

            Point controlSize = new GetAction<Point>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(new Point(((ScrollPane) os[0]).getWidth(), ((ScrollPane) os[0]).getHeight()));
                }
            }.dispatch(Root.ROOT.getEnvironment(), testedControl.getControl());

            Point viewPortSize = new GetAction<Point>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(new Point(((ScrollPane) os[0]).getPrefViewportWidth(), ((ScrollPane) os[0]).getPrefViewportHeight()));
                }
            }.dispatch(Root.ROOT.getEnvironment(), testedControl.getControl());

            double verticalScrollBarWidth = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).getScreenBounds().width;
            double horizontalScrollBarHeight = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).getScreenBounds().height;

            Assert.assertEquals(viewPortSize.x + verticalScrollBarWidth, controlSize.x, commonComparePrecision);
            Assert.assertEquals(viewPortSize.y + horizontalScrollBarHeight, controlSize.y, commonComparePrecision);
        }
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void whiteGapTest() throws InterruptedException, Throwable {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hmax, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hmin, 50);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hvalue, 200);

        checkScreenshot("ScrollPane_WhiteGapTest", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)//(expected=java.lang.RuntimeException.class) //RT-17334
    public void unidirectionalBindingForVHValueTest() throws InterruptedException {
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.vvalue, 200);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, Properties.hvalue, 200);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)//RT-17350
    public void immediateHVBarPoliticApplyingTest() throws Throwable {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, ScrollPane.ScrollBarPolicy.ALWAYS, Properties.hbarPolicy);
        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, ScrollPane.ScrollBarPolicy.NEVER, Properties.hbarPolicy);

        //There must not be horizontal bar.
        checkScreenshot("ScrollPane_PoliticApplyingTest", testedControl);
        throwScreenshotError();
    }

    @ScreenshotCheck
    @Test(timeout = 300000) //RT-17365
    public void contentMovingTest() throws InterruptedException, Throwable {
        contentMotionStart();

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 100);

        Thread.sleep(ContentMotion.motionDuration + 1000);

        checkScreenshot("ScrollPane_ContentMovingTest", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void contentReceiveMouseEventsTest() {
        changeContent();
        clickContentButton();

        checkContentTextFieldValue(1);
    }

    @Smoke
    private void checkFocusStates(boolean buttonFocus, boolean textFieldFocus, boolean textAreaFocus, boolean emptyButtonFocus) {
        checkFocus(CONTENT_BUTTON, buttonFocus);
        checkFocus(CONTENT_TEXT_FIELD_ID, textFieldFocus);
        checkFocus(CONTENT_TEXT_AREA_ID, textAreaFocus);
        checkFocus(WITHOUT_ACTION_BUTTON, emptyButtonFocus);
    }

    @ScreenshotCheck
    @Test(timeout = 300000)
    public void insideTraversalCheckTest() throws Throwable {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, 100);

        changeContent();
        clickContentButton();

        Wrap<? extends Button> buttonWrap = parent.lookup(Button.class, new ByID<Button>(CONTENT_BUTTON)).wrap();
        buttonWrap.mouse().click();
        checkFocusStates(true, false, false, false);
        checkScreenshot("ScrollPane_InnerTraversal_1_Test", testedControl);

        buttonWrap.keyboard().pushKey(KeyboardButtons.TAB);
        checkFocusStates(false, true, false, false);
        checkScreenshot("ScrollPane_InnerTraversal_2_Test", testedControl);

        parent.lookup(TextField.class, new ByID<TextField>(CONTENT_TEXT_FIELD_ID)).wrap().keyboard().pushKey(KeyboardButtons.TAB);
        checkFocusStates(false, false, true, false);
        checkScreenshot("ScrollPane_InnerTraversal_3_Test", testedControl);

        parent.lookup(TextArea.class, new ByID<TextArea>(CONTENT_TEXT_AREA_ID)).wrap().keyboard().pushKey(KeyboardButtons.TAB, KeyboardModifiers.CTRL_DOWN_MASK);
        checkFocusStates(false, false, false, true);
        checkScreenshot("ScrollPane_InnerTraversal_4_Test", testedControl);

        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)//RT-17378
    public void contentSizeDetectionAndScrolls() throws InterruptedException, Throwable {
        SettingOption temp = currentSettingOption;
        currentSettingOption = SettingOption.PROGRAM;
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 201);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 201);

        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, false) == null);
        Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, false) == null);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 200);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 200);

        Assert.assertTrue(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true) != null);
        Assert.assertTrue(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true) != null);

        currentSettingOption = temp;
    }

    @Smoke
    @Test(timeout = 300000)//RT-17380
    public void focusFromOutsideTest() {
        changeContent();
        parent.lookup(Button.class, new ByID<Button>(CHANGE_CONTENT_BUTTON_ID)).wrap().keyboard().pushKey(KeyboardButtons.UP);

        parent.lookup(Button.class, new ByID<Button>(WITHOUT_ACTION_BUTTON)).wrap().waitProperty("isFocused", true);
    }

    private void makeDND(int initialDeltaX, int initialDeltaY, int deltaX, int deltaY) throws InterruptedException {
        Wrap<? extends Group> group = parent.lookup(Group.class, new ByID<Group>(CUSTOM_CONTENT_ID)).wrap();
        Point fromPoint = group.getClickPoint();

        fromPoint.x += initialDeltaX;
        fromPoint.y += initialDeltaY;
        Point toPoint = new Point(fromPoint.x + deltaX, fromPoint.y + deltaY);

        NodeDock groupDock = new NodeDock(parent, new ByID<Node>(CUSTOM_CONTENT_ID));

        groupDock.drag().dnd(fromPoint, groupDock.wrap(), toPoint);
    }

    @ScreenshotCheck
    @Smoke
    @Test(timeout = 300000)
    public void panningTest() throws Throwable {
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.pannable);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 150);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 150);

        makeDND(-30, -30, -150, -150);
        checkTextFieldValue(Properties.hvalue, 1);
        checkTextFieldValue(Properties.vvalue, 1);

        makeDND(30, 30, -150, 150);
        checkTextFieldValue(Properties.hvalue, 1);
        checkTextFieldValue(Properties.vvalue, 0);

        makeDND(30, -30, 150, -150);
        checkTextFieldValue(Properties.hvalue, 0);
        checkTextFieldValue(Properties.vvalue, 1);

        makeDND(-30, 30, 150, 150);
        checkTextFieldValue(Properties.hvalue, 0);
        checkTextFieldValue(Properties.vvalue, 0);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.pannable);

        makeDND(-30, -30, -150, -150);
        checkTextFieldValue(Properties.hvalue, 0);
        checkTextFieldValue(Properties.vvalue, 0);
        checkScreenshot("ScrollPane_PanningTest", testedControl);
        throwScreenshotError();
    }

    @Smoke
    @Test(timeout = 300000)
    public void longRotativeDND() throws InterruptedException {
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.pannable);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 150);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 150);

        final int mouseSmoothness = getMouseSmoothness();
        int newMouseSmoothness = 3;
        setMouseSmoothness(newMouseSmoothness);

        final double R = 120;
        final double alpha = 0.1;
        double angle = 0;
        final Point initialPoint = testedControl.getClickPoint();
        final int numPoints = 500;

        Point next;

        testedControl.mouse().move(initialPoint);
        testedControl.mouse().press();
        testedControl.getEnvironment().getExecutor().waitQuiet(new Timeout("", 500));

        final double EPS = alpha / 2;
        for (int i = 0; i < numPoints; i++) {
            next = new Point(initialPoint.x + R * Math.sin(angle), initialPoint.y + R * Math.cos(angle));
            testedControl.mouse().move(next);
            final double DELTA = 0.05;

            if (Math.abs(angle - Math.PI / 4) < EPS) {
                checkTextFieldValue(Properties.hvalue, 0, DELTA);
                checkTextFieldValue(Properties.vvalue, 0, DELTA);
            } else if (Math.abs(angle - 3 * Math.PI / 4) < EPS) {
                checkTextFieldValue(Properties.hvalue, 0, DELTA);
                checkTextFieldValue(Properties.vvalue, 1, DELTA);
            } else if (Math.abs(angle - 5 * Math.PI / 4) < EPS) {
                checkTextFieldValue(Properties.hvalue, 1, DELTA);
                checkTextFieldValue(Properties.vvalue, 1, DELTA);
            } else if (Math.abs(angle - 7 * Math.PI / 4) < EPS) {
                checkTextFieldValue(Properties.hvalue, 1, DELTA);
                checkTextFieldValue(Properties.vvalue, 0, DELTA);
            }

            angle += alpha;
            if (angle > 2 * Math.PI) {
                angle -= 2 * Math.PI;
            }
        }

        scene.mouse().move(new Point(0, 0));
        testedControl.mouse().release();

        setMouseSmoothness(mouseSmoothness);

        checkSimpleListenerValue(Properties.hvalue, "1.0");
        checkSimpleListenerValue(Properties.vvalue, "1.0");
    }

    private void tryToMove(int initX, int initY, int deltaX, int deltaY) throws InterruptedException {
        Wrap<? extends Group> wrap = parent.lookup(Group.class, new ByID<Group>(CUSTOM_CONTENT_ID)).wrap();
        Point before = new Point(wrap.getScreenBounds().x, wrap.getScreenBounds().y);
        makeDND(initX, initY, deltaX, deltaY);
        Point after = new Point(wrap.getScreenBounds().x, wrap.getScreenBounds().y);
        //assert that the maximum difference in coordinates is less or equal 1 pixel
        final Point expected = new Point(deltaX, deltaY);
        final Point actual = new Point(after.x - before.x, after.y - before.y);

        int dx = expected.x - actual.x;
        int dy = expected.y - actual.y;
        int distance_squared = dx * dx + dy * dy;

        Assert.assertTrue("[Expected point is too far from actual]", distance_squared <= 2);
    }

    @Smoke
    @Test(timeout = 300000)
    public void panningAndMouseMovementCompare() throws InterruptedException {
        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, Properties.pannable);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 100);

        tryToMove(-75, -75, -50, -50);
        tryToMove(-25, -25, 50, -50);
        tryToMove(-75, 25, -50, 50);
        tryToMove(-25, -25, -50, -50);
    }

    //Test//Switched off, because developers don't fix such bugs.
    public void steadyScrollingTest() throws InterruptedException {
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, 100);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, 100);

        Wrap<? extends ScrollBar> vertical = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true);
        Wrap<? extends ScrollBar> horizontal = findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true);

        ScrollingChecker scV = new ScrollingChecker(vertical.getControl().valueProperty());
        ScrollingChecker scH = new ScrollingChecker(horizontal.getControl().valueProperty());
        for (int i = 0; i < 10; i++) {
            vertical.mouse().turnWheel(+1);
            scV.checkChanging(+1);

            horizontal.mouse().turnWheel(+1);
            scH.checkChanging(+1);
        }

        vertical.mouse().turnWheel(-17);
        horizontal.mouse().turnWheel(-17);

        changeContent();

        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.hvalue, 0);
        setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.vvalue, 0);

        ScrollingChecker sc = new ScrollingChecker(vertical.getControl().valueProperty());

        for (int i = 0; i < 17; i++) {
            vertical.mouse().turnWheel(+1);
            sc.checkChanging(+1);
        }
    }

    @Smoke
    @Test(timeout = 300000)//RT-17368
    @Covers(value = "javafx.scene.control.ScrollPane.viewportBounds.GET", level = Level.FULL)
    public void boundsTest() throws InterruptedException {
        changeContentToResizable();
        double[] sizes = {100, 50, 40, 55};

        for (Double size : sizes) {

            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportHeight, size);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefViewportWidth, size);

            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefHeight, size * 2);
            setPropertyBySlider(SettingType.BIDIRECTIONAL, Properties.prefWidth, size * 2);

            Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true) == null);
            Assert.assertFalse(findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true) == null);

            Bounds bounds = new GetAction<Bounds>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(((ScrollPane) os[0]).getViewportBounds());
                }
            }.dispatch(Root.ROOT.getEnvironment(), testedControl.getControl());

            Point viewPortSize = new GetAction<Point>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(new Point(((ScrollPane) os[0]).getPrefViewportWidth(), ((ScrollPane) os[0]).getPrefViewportHeight()));
                }
            }.dispatch(Root.ROOT.getEnvironment(), testedControl.getControl());

            Assert.assertEquals(viewPortSize.x, bounds.getWidth() + findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.VERTICAL, true).getScreenBounds().getWidth(), commonComparePrecision);
            Assert.assertEquals(viewPortSize.y, bounds.getHeight() + findScrollBar((Parent<Node>) testedControl.as(Parent.class, Node.class), Orientation.HORIZONTAL, true).getScreenBounds().getHeight(), commonComparePrecision);
        }
    }

    @Ignore //due to http://javafx-jira.kenai.com/browse/RT-27467
    @Smoke
    @Test(timeout = 30000)
    /**
     * Test checks method scrollTo of the control.
     */
    public void scrollToTest() {
        addGrid(10);
        scrollToCommonTest("B-5-5");
        scrollToCommonTest("B-9-0");
        scrollToCommonTest("B-0-9");
        scrollToCommonTest("B-9-9");
        scrollToCommonTest("B-0-0");
    }

    private void scrollToCommonTest(String name) {
        scrollTo(name);
        final Wrap<? extends Button> buttonInGrid = getButtonInGrid(name);

        testedControl.waitState(new State() {
            public Object reached() {
                final Point clickPoint = buttonInGrid.getClickPoint();
                final Rectangle buttonBounds = buttonInGrid.getScreenBounds();
                if (testedControl.getScreenBounds().contains(new Point(buttonBounds.getX() + clickPoint.x, buttonBounds.getY() + clickPoint.y))) {
                    return true;
                }
                return null;
            }
        });
    }
}
