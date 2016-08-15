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
package javafx.scene.control.test.nchart;

import client.test.ScreenshotCheck;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import static javafx.scene.control.test.nchart.Geometry.*;
import javafx.scene.control.test.nchart.PieChartDescriptionProvider.PieChartContentDescription;
import static javafx.scene.control.test.nchart.Utils.*;
import javafx.scene.control.test.nchart.Utils.RunnableWithThrowable;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import javafx.scene.shape.Line;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.javaclient.shared.FilteredTestRunner;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
@RunWith(FilteredTestRunner.class)
public class PieChartTest extends PieChartTestBase {

    @Test(timeout = 300000)
    public void startAnglePropertyTest() throws InterruptedException, Throwable {
        assertEquals(new PieChart().getStartAngle(), 0.0, 0.000001);

        List<Double> initialAngles = pieChartDescriptionProvider.getLinesAngles();

        setPropertyBySlider(SettingType.SETTER, PieChartProperties.startAngle, 30.0);
        checkStartAngle(initialAngles, 30);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, PieChartProperties.startAngle, 90.0);
        checkStartAngle(initialAngles, 90);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.startAngle, -60.0);
        checkStartAngle(initialAngles, -60);

        for (int i = -50; i < 300; i += 10) {
            setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.startAngle, i);
            checkStartAngle(initialAngles, i);
        }
    }

    @Test(timeout = 300000)
    public void startAngleCSSTest() throws InterruptedException, Throwable {
        assertEquals(new PieChart().getStartAngle(), 0.0, 0.000001);

        List<Double> angles = pieChartDescriptionProvider.getLinesAngles();

        applyStyle(testedControl, "-fx-start-angle", 49);
        checkStartAngle(angles, 49);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.startAngle, -60.0);
        checkStartAngle(angles, -60);
    }

    private void checkStartAngle(List<Double> angles, int angle) throws Throwable {
        checkShiftedAngles(angles, angle);
        checkTextFieldValue(PieChartProperties.startAngle, angle);
        checkChartAppearanceInvariant();
    }

    private void checkShiftedAngles(final List<Double> initialAngles, final double shift) throws Throwable {
        for (final Double angle : initialAngles) {
            testedControl.waitState(new State<Boolean>() {
                public Boolean reached() {
                    pieChartDescriptionProvider.clearState();
                    for (Double newAngle : pieChartDescriptionProvider.getLinesAngles()) {
                        if (areAnglesEqual(newAngle - shift, angle)) {
                            return true;
                        }
                    }
                    return Boolean.FALSE;
                }
            }, Boolean.TRUE);
        }
    }

    @Test(timeout = 300000)//RT-27751
    public void labelLineLengthTest() throws Throwable {
        assertEquals(new PieChart().getLabelLineLength(), 20, 0.0);

        checkLineLengths(20);

        setPropertyBySlider(SettingType.BIDIRECTIONAL, PieChartProperties.labelLineLength, 20);
        checkLineLengths(20);

        setPropertyBySlider(SettingType.SETTER, PieChartProperties.labelLineLength, 10);
        checkLineLengths(10);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.labelLineLength, 30);
        checkLineLengths(30);

        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.labelLineLength, -30);
        checkLineLengths(0);
    }

    @Test(timeout = 300000)
    public void labelLineLengthCSSTest() throws Throwable {
        applyStyle(testedControl, "-fx-label-line-length", 40);
        checkLineLengths(40);

        applyStyle(testedControl, "-fx-label-line-length", 20);
        checkLineLengths(20);
    }

    private void checkLineLengths(final double expectedLength) throws Throwable {
        checkTextFieldValue(PieChartProperties.labelLineLength, expectedLength);
        final ObjectProperty<Throwable> exception = new SimpleObjectProperty(null);
        try {
            testedControl.waitState(new State() {
                public Object reached() {
                    try {
                        Line[] lines = pieChartDescriptionProvider.getPathLines();
                        for (int i = 0; i < lines.length; i++) {
                            assertEquals("Checking line's <" + i + ">, which is <" + getLineDescription(lines[i]) + ">.", lineLenght(lines[i]), expectedLength, 0.1);
                        }
                    } catch (Throwable ex) {
                        exception.set(ex);
                        pieChartDescriptionProvider.clearState();
                        return null;
                    }
                    return true;
                }
            });
        } catch (Throwable ex) {
            throw new Exception("Could reach invariant state", exception.get());
        }

        checkChartAppearanceInvariant();
    }

    @Test(timeout = 300000)
    public void clockwisePropertyTest() throws Throwable {
        assertTrue(((PieChart) getNewChartInstance()).isClockwise());

        List<Double> angles = pieChartDescriptionProvider.getLinesAngles();

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, PieChartProperties.clockWise, false);
        checkTextFieldText(PieChartProperties.clockWise, "false");
        checkClockWiseAffecting(angles, false);

        setPropertyByToggleClick(SettingType.SETTER, PieChartProperties.clockWise, true);
        checkTextFieldText(PieChartProperties.clockWise, "true");
        checkClockWiseAffecting(angles, true);

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.clockWise, false);
        checkTextFieldText(PieChartProperties.clockWise, "false");
        checkClockWiseAffecting(angles, false);
    }

    @Test(timeout = 300000)
    public void clockwiseCSSTest() throws Throwable {
        List<Double> angles = pieChartDescriptionProvider.getLinesAngles();

        applyStyle(testedControl, "-fx-clockwise", "false");
        checkTextFieldText(PieChartProperties.clockWise, "false");
        checkClockWiseAffecting(angles, false);

        applyStyle(testedControl, "-fx-clockwise", "true");
        checkTextFieldText(PieChartProperties.clockWise, "true");
        checkClockWiseAffecting(angles, true);
    }

    private void checkClockWiseAffecting(List<Double> angles, final boolean clockwise) throws Throwable {
        for (final Double angle : angles) {
            testedControl.waitState(new State<Boolean>() {
                public Boolean reached() {
                    pieChartDescriptionProvider.clearState();
                    for (Double newAngle : pieChartDescriptionProvider.getLinesAngles()) {
                        if (areAnglesEqual((clockwise ? +1 : -1) * newAngle, angle)) {
                            return true;
                        }
                    }
                    return Boolean.FALSE;
                }
            }, Boolean.TRUE);
        }
        checkChartAppearanceInvariant();
    }

    @Test(timeout = 300000)
    public void labelsVisiblePropertyTest() throws Throwable {
        assertTrue(((PieChart) getNewChartInstance()).getLabelsVisible());

        checkLabelsVisible(4, true);

        setPropertyByToggleClick(SettingType.SETTER, PieChartProperties.labelsVisible, false);
        checkLabelsVisible(4, false);

        setPropertyByToggleClick(SettingType.BIDIRECTIONAL, PieChartProperties.labelsVisible, true);
        checkLabelsVisible(4, true);

        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.labelsVisible, false);
        checkLabelsVisible(4, false);
    }

    @Test(timeout = 300000)//RT-27768
    public void labelsVisibleCSSTest() throws Throwable {
        applyStyle(testedControl, "-fx-pie-label-visible", false);
        checkLabelsVisible(5, false);

        addDataItem("new data", 100.0, 0);

        applyStyle(testedControl, "-fx-pie-label-visible", true);
        checkLabelsVisible(5, true);
    }

    private void checkLabelsVisible(int labelsCountInCommonCase, Boolean visible) throws Throwable {
        checkTextFieldText(PieChartProperties.labelsVisible, visible.toString());
        waitLabelsCount(visible ? labelsCountInCommonCase : 0);
        checkChartAppearanceInvariant();
    }

    protected void waitLabelsCount(int i) {
        testedControl.waitState(new State<Integer>() {
            public Integer reached() {
                try {
                    pieChartDescriptionProvider.clearState();
                    return pieChartDescriptionProvider.getPieChartContentDescription().labelsArtifactsSize;
                } catch (Throwable ex) {
                    return -1;
                }
            }
        }, i);
        assertEquals(pieChartDescriptionProvider.getPathLines().length, i);
        assertEquals(pieChartDescriptionProvider.getPieLabels().size(), i);
    }

    public void dataAddingAndRemovingTest() throws Throwable {
        addDataItem("Added data 1", 3000.0, 2);
        checkChartContent(5);

        addDataItem("Added data 2", 300.0, 4);
        checkChartContent(6);

        removeDataItem(2);
        checkChartContent(5);

        removeDataItem(3);
        checkChartContent(4);

        removeDataItem(0);
        checkChartContent(3);

        removeDataItem(1);
        checkChartContent(2);

        addDataItem("Added data 3", 400.0, 1);
        checkChartContent(3);
    }

    private void checkChartContent(final int expectedCount) throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                PieChartContentDescription description = pieChartDescriptionProvider.getPieChartContentDescription();

                assertEquals(pieChartDescriptionProvider.getLegendLabels(), expectedCount);
                assertEquals(description.labelsArtifactsSize, expectedCount);
                assertEquals(description.info.size(), expectedCount);
            }
        });
        checkChartAppearanceInvariant();
    }

    @Test(timeout = 300000)
    @ScreenshotCheck
    public void screenshot1Test() throws Throwable {
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, true);
        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Side.BOTTOM, ChartProperties.titleSide);
        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Side.LEFT, ChartProperties.legendSide);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefWidth, 300);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefHeight, 500);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.startAngle, -100);
        addDataItem("Added dynamicly", 100.0, 1);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.clockWise, true);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.labelsVisible, true);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.labelLineLength, 10);
        setPropertyByTextField(SettingType.UNIDIRECTIONAL, ChartProperties.title, "Changed title");

        checkScreenshot("PieChart-multiple1", testedControl);
        throwScreenshotError();
    }

    @Test(timeout = 300000)
    @ScreenshotCheck
    public void screenshot2Test() throws Throwable {
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, false);
        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Side.RIGHT, ChartProperties.titleSide);
        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Side.TOP, ChartProperties.legendSide);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefWidth, 500);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefHeight, 300);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.startAngle, 100);
        removeDataItem(1);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.clockWise, false);
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.labelsVisible, false);
        setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.labelLineLength, -10);
        setPropertyByTextField(SettingType.UNIDIRECTIONAL, ChartProperties.title, "Changed title");

        checkScreenshot("PieChart-multiple2", testedControl);
        throwScreenshotError();
    }

    @Test(timeout = 300000)
    @ScreenshotCheck
    public void screenshot3Test() throws Throwable {
        applyStyle(testedControl, "-fx-clockwise", "false");
        applyStyle(testedControl, "-fx-label-line-length", 30);
        applyStyle(testedControl, "-fx-start-angle", 30);
        checkScreenshot("PieChart-multiple3-CSS1", testedControl);

        applyStyle(testedControl, "-fx-pie-label-visible", false);

        checkScreenshot("PieChart-multiple3-CSS2", testedControl);
        throwScreenshotError();
    }

    @Test(timeout = 3000000)//RT-27768
    public void chartPropertiesMultipleChangingAndCorrectness1Test() throws Throwable {
        setSize(500, 500);

        for (Boolean animated : new Boolean[]{Boolean.TRUE, Boolean.FALSE}) {
            setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, animated);
            for (Double yShift : new Double[]{-50.0, +50.0}) {
                setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefHeight, 400 + yShift);
                try {
                    pieChartPropertiesTest();
                } catch (Throwable ex) {
                    System.out.println("Exception occured in state : ");
                    System.out.println("animated : " + animated);
                    System.out.println("yShift : " + yShift);
                    throw ex;
                }
            }
        }
    }

    @Test(timeout = 3000000)//RT-27768
    public void chartPropertiesMultipleChangingAndCorrectness2Test() throws Throwable {
        setSize(500, 500);

        for (Boolean animated : new Boolean[]{Boolean.TRUE, Boolean.FALSE}) {
            setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, animated);
            for (Double xShift : new Double[]{-50.0, +50.0}) {
                setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefWidth, 400 + xShift);
                try {
                    pieChartPropertiesTest();
                } catch (Throwable ex) {
                    System.out.println("Exception occured in state : ");
                    System.out.println("animated : " + animated);
                    System.out.println("xShift : " + xShift);
                    throw ex;
                }
            }
        }
    }

    @Test(timeout = 3000000)//RT-27768
    public void chartPropertiesMultipleChangingAndCorrectness3Test() throws Throwable {
        setSize(500, 500);

        for (Boolean animated : new Boolean[]{Boolean.TRUE, Boolean.FALSE}) {
            setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, animated);
            for (Side titleSide : Side.values()) {
                setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, titleSide, ChartProperties.titleSide);
                try {
                    pieChartPropertiesTest();
                } catch (Throwable ex) {
                    System.out.println("Exception occured in state : ");
                    System.out.println("animated : " + animated);
                    System.out.println("titleSide : " + titleSide);
                    throw ex;
                }
            }
        }
    }

    @Test(timeout = 3000000)//RT-27768
    public void chartPropertiesMultipleChangingAndCorrectness4Test() throws Throwable {
        setSize(500, 500);

        for (Boolean animated : new Boolean[]{Boolean.TRUE, Boolean.FALSE}) {
            setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, animated);
            for (Side legendSide : Side.values()) {
                setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, legendSide, ChartProperties.legendSide);
                try {
                    pieChartPropertiesTest();
                } catch (Throwable ex) {
                    System.out.println("Exception occured in state : ");
                    System.out.println("animated : " + animated);
                    System.out.println("legendSide : " + legendSide);
                    throw ex;
                }

            }
        }
    }

    private void pieChartPropertiesTest() throws Throwable {
        for (double startAngle = -100; startAngle < 300; startAngle += 10 * Math.PI * Math.PI) {
            setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.startAngle, startAngle);
            for (boolean toAdd : new Boolean[]{true, false}) {
                if (toAdd) {
                    addDataItem("Added dynamicly", 100.0, 1);
                } else {
                    removeDataItem(1);
                }
                for (Boolean clockWise : new Boolean[]{Boolean.TRUE, Boolean.FALSE}) {
                    setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.clockWise, clockWise);
                    for (Boolean labelsVisible : new Boolean[]{Boolean.TRUE, Boolean.FALSE}) {
                        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, PieChartProperties.labelsVisible, labelsVisible);
                        for (Double lineLength : new Double[]{10.0, 30.0, -20.0}) {
                            setPropertyBySlider(SettingType.UNIDIRECTIONAL, PieChartProperties.labelLineLength, lineLength);
                            try {
                                checkChartAppearanceInvariant();
                            } catch (Throwable ex) {
                                System.out.println("Exception occured in state : ");
                                System.out.println("startAngle : " + startAngle);
                                System.out.println("toAdd : " + toAdd);
                                System.out.println("clockWise : " + clockWise);
                                System.out.println("labelsVisible : " + labelsVisible);
                                System.out.println("lineLength : " + lineLength);
                                throw ex;
                            }
                        }
                    }
                }
            }
        }
    }

    //    //Test
    //    //Dont' look at this.
    //    public void experimental1() {
    //        testedControl.waitState(new State<Integer>() {
    //            public Integer reached() {
    //                return getSlicesInfo().size();
    //            }
    //        }, 4);
    //        final List<PieInfo> extractSlicesInfo = getSlicesInfo();
    //        System.out.println(extractSlicesInfo.size());
    //        for (PieInfo info : extractSlicesInfo) {
    //            System.out.println(info);
    //        }
    //    }
    //
    //    //Test
    //    //Don't look at this.
    //    public void experimental2() {
    //        try {
    //            //Do some stuff
    //            Thread.sleep(30000);
    //        } catch (InterruptedException ex) {
    //            Logger.getLogger(PieChartTest.class.getName()).log(Level.SEVERE, null, ex);
    //        }
    //        PieChartContentDescription description = new PieChartContentDescription();
    //        System.out.println(description);
    //    }
    //
    //    //Test
    //    //Don't look at this.
    //    public void experimental3() throws Throwable {
    //        try {
    //            //Do some needed stuff
    //            Thread.sleep(30000);
    //        } catch (InterruptedException ex) {
    //            Logger.getLogger(PieChartTest.class.getName()).log(Level.SEVERE, null, ex);
    //        }
    //        PieChartContentDescription description = new PieChartContentDescription();
    //    }
    //    }
    @Override
    void populateChart() {
        addDataItem("Added data 1", 3000.0, 2);
        addDataItem("Added data 2", 300.0, 4);
        addDataItem("Added data 3", 400.0, 1);
    }
}