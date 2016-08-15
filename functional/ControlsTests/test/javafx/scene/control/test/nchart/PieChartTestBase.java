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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.test.chart.apps.NewPieChartApp;
import static javafx.scene.control.test.chart.apps.NewPieChartApp.*;
import static javafx.scene.control.test.nchart.Geometry.*;
import javafx.scene.control.test.nchart.PieChartDescriptionProvider.PieChartContentDescription.PieLineLabelMatch;
import javafx.scene.control.test.nchart.PieChartDescriptionProvider.PieInfo;
import static javafx.scene.control.test.nchart.Utils.*;
import javafx.scene.control.test.nchart.Utils.RunnableWithThrowable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
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
public abstract class PieChartTestBase extends AllChartsBase {

    protected PieChartDescriptionProvider pieChartDescriptionProvider;

    @Before
    public void before3() {
        pieChartDescriptionProvider = new PieChartDescriptionProvider((Wrap<? extends PieChart>) testedControl);
        pieChartDescriptionProvider.clearState();
    }

    @BeforeClass
    public static void setUpClass() {
        NewPieChartApp.main(null);
    }

    protected void addDataItem(String name, Double value, int position) {
        setText(findTextField(ADD_ITEM_TEXT_FIELD_ID), name);
        setText(findTextField(ADD_ITEM_POSITION_TEXT_FIELD_ID), String.valueOf(position));
        setText(findTextField(ADD_ITEM_VALUE_TEXT_FIELD_ID), String.valueOf(value));
        clickButtonForTestPurpose(ADD_ITEM_BUTTON_ID);
    }

    protected void removeDataItem(int index) {
        setText(findTextField(REMOVE_ITEM_POS_TEXT_FIELD_ID), String.valueOf(index));
        clickButtonForTestPurpose(REMOVE_BUTTON_ID);
    }

    @Override
    protected void checkChartAppearanceInvariant() throws Throwable {
        pieChartDescriptionProvider.clearState();
        super.checkChartAppearanceInvariant();
        checkPieChartAppearanceInvariant();
        checkAllPiesAtTheSameCenter();
        checkPiesOrientationCorrectness();
        checkDataConsistentRepresentation();
        checkPiesHaveTheSameRadius();
        checkColorCorrectMatching();
        //checkStyleClassesCorrectAssignating();
    }

    @Override
    protected Chart getNewChartInstance() {
        return new GetAction<Chart>() {
            @Override
            public void run(Object... os) throws Exception {
                //All JavaFX objects must be instantiated on JFX thread.
                setResult(new PieChart());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    protected enum PieChartProperties {

        startAngle, labelLineLength, labelsVisible, clockWise
    };

    protected void checkColorCorrectMatching() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                if (pieChartDescriptionProvider.isLegendVisible()
                        && pieChartDescriptionProvider.isLabelsVisible()
                        && new GetAction<Boolean>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(pieChartDescriptionProvider.getLegend().getControl().isVisible());
                    }
                }.dispatch(Root.ROOT.getEnvironment())) {
                    Map<PieInfo, Color> piesColors = pieChartDescriptionProvider.getPieColors();
                    Map<String, Color> labelsColors = pieChartDescriptionProvider.getColorsInLegend();

                    int counter = 0;

                    for (final PieLineLabelMatch match : pieChartDescriptionProvider.getPieChartContentDescription().info) {
                        if (match.label != null) {
                            //If there is a label in the chart, then we expect to
                            //find a label in the legend and then we will check its colors;
                            Color color1 = piesColors.get(match.pie);
                            Color color2 = labelsColors.get(new GetAction<String>() {
                                @Override
                                public void run(Object... os) throws Exception {
                                    setResult(match.label.getControl().getText());
                                }
                            }.dispatch(Root.ROOT.getEnvironment()));

                            assertNotNull(color1);
                            assertNotNull(color2);
                            assertEquals(color1.getHue(), color2.getHue(), 3);
                            counter++;
                        }
                    }

                    //Check, that at least 1 color was checked.
                    assertTrue(counter > 0);
                }
            }
        });
    }

    protected void checkPieChartAppearanceInvariant() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                ArrayList<Wrap> nonIntersectingComponents = getStandartNonIntersectingComponents();

                if (pieChartDescriptionProvider.isLabelsVisible()) {
                    final Line[] pathLines = convertToGlobalCoordinates(pieChartDescriptionProvider.getChartContent(), pieChartDescriptionProvider.getPathLines());
                    final ArrayList<Wrap<? extends Text>> labels = pieChartDescriptionProvider.getPieLabels();
                    for (Wrap wrap : labels) {
                        nonIntersectingComponents.add(wrap);
                    }
                    Assert.assertEquals(pathLines.length, labels.size());

                    //Lines should be visible.
                    for (Wrap wrap : nonIntersectingComponents) {
                        for (Line line : pathLines) {
                            final Point startPoint = new Point(line.getStartX(), line.getStartY());
                            final Rectangle wrapBounds = wrap.getScreenBounds();
                            final Point endPoint = new Point(line.getEndX(), line.getEndY());
                            try {
                                wrap.getControl();
                                Assert.assertFalse("Path lines shouldn't intersect with anything else.", wrapBounds.contains(startPoint));
                                Assert.assertFalse("Path lines shouldn't intersect with anything else.", wrapBounds.contains(endPoint));
                            } catch (Throwable ex) {
                                System.err.println("Start point : " + startPoint);
                                System.err.println("End point" + endPoint);
                                System.err.println("Wrap bounds : " + wrapBounds);
                                System.err.println("Wrap : " + wrap);
                                System.err.println("Control : " + wrap.getControl());
                                throw ex;
                            }
                        }
                    }
                }

                checkNonIntersectance(pieChartDescriptionProvider.getChartContent(), nonIntersectingComponents);

                checkEverythingIsInsideControl(nonIntersectingComponents);

                checkLegendContentSizeCorrectness();
            }
        });
    }

    /*
     * Check, that all Pies start from the same point.
     */
    protected void checkAllPiesAtTheSameCenter() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                List<PieInfo> info = pieChartDescriptionProvider.getSlicesInfo();
                if (info.isEmpty()) {
                    throw new IllegalStateException("Pies not found.");
                }
                if (info.size() == 1) {
                    return;
                }
                double x = info.get(0).regionCenterX;
                double y = info.get(0).regionCenterY;

                for (int i = 0; i < info.size(); i++) {
                    info.get(2);
                    Assert.assertEquals(0.0, info.get(i).centerX, 0.01);
                    Assert.assertEquals(0.0, info.get(i).centerY, 0.01);
                    Assert.assertEquals(x, info.get(i).regionCenterX, 0.01);
                    Assert.assertEquals(y, info.get(i).regionCenterY, 0.01);
                }
            }
        });
    }

    /*
     * Will check, that for each line angle there is a Pie, with the same angle
     * of median line.
     */
    protected void checkPiesOrientationCorrectness() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                List<PieInfo> piesInfo = pieChartDescriptionProvider.getSlicesInfo();
                Collection<Double> angles = pieChartDescriptionProvider.getAngles(Arrays.asList(pieChartDescriptionProvider.getPathLines()));

                for (Double angle : angles) {
                    boolean found = false;
                    for (PieInfo info : piesInfo) {
                        if (areAnglesEqual(angle, info.startAngle + info.angle / 2)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        Assert.fail("Cound find a pie for a line with angle " + angle + ".");
                    }
                }
            }
        });
    }

    protected void checkDataConsistentRepresentation() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                List<PieInfo> piesInfo = pieChartDescriptionProvider.getSlicesInfo();
                Collection<Double> datas = new GetAction<Collection<Double>>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        Collection<Double> pieDatas = new ArrayList<Double>();
                        final ObservableList<Data> dataCollection = ((PieChart) testedControl.getControl()).getData();
                        for (Data data : dataCollection) {
                            pieDatas.add(data.getPieValue());
                        }
                        setResult(pieDatas);
                    }
                }.dispatch(Root.ROOT.getEnvironment());

                double datasSum = 0.0;
                for (Double dataSize : datas) {
                    datasSum += Math.abs(dataSize);
                }

                for (Double dataSize : datas) {
                    double proportionalSize = dataSize / datasSum;
                    boolean found = false;
                    for (PieInfo info : piesInfo) {
                        double proportionalAngle = Math.abs(info.angle / 360.0);
                        found |= Math.abs(proportionalAngle - proportionalSize) < 0.01;
                    }
                    if (!found) {
                        throw new IllegalStateException("Sizes are not proportional to data.");
                    }
                }

                ObservableList<Data> dataArray = pieChartDescriptionProvider.getData();

                if (pieChartDescriptionProvider.isLabelsVisible()) {
                    //Otherwise, we cannot recognise, which Pies correspond to which data.
                    //Will rely on this check, only when labels are shown.
                    for (final Data data : dataArray) {
                        for (final PieLineLabelMatch match : pieChartDescriptionProvider.getPieChartContentDescription().info) {
                            PieInfo info = match.pie;
                            String text = new GetAction<String>() {
                                @Override
                                public void run(Object... os) throws Exception {
                                    setResult(match.label.getControl().getText());
                                }
                            }.dispatch(Root.ROOT.getEnvironment());

                            String name = new GetAction<String>() {
                                @Override
                                public void run(Object... os) throws Exception {
                                    setResult(data.getName());
                                }
                            }.dispatch(Root.ROOT.getEnvironment());

                            Double value = new GetAction<Double>() {
                                @Override
                                public void run(Object... os) throws Exception {
                                    setResult(data.getPieValue());
                                }
                            }.dispatch(Root.ROOT.getEnvironment());

                            if (text.equals(name)) {
                                assertEquals(Math.abs(value / datasSum), Math.abs(info.angle) / 360, 0.01);
                            }
                        }
                    }
                }
            }
        });
    }

    protected void checkStyleClassesCorrectAssignating() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                if (pieChartDescriptionProvider.isLabelsVisible()) {//Otherwise, it is hard to extract info about data index.
                    ObservableList<Data> dataArray = pieChartDescriptionProvider.getData();

                    for (final Data data : dataArray) {
                        for (final PieLineLabelMatch match : pieChartDescriptionProvider.getPieChartContentDescription().info) {
                            String text = new GetAction<String>() {
                                @Override
                                public void run(Object... os) throws Exception {
                                    setResult(match.label.getControl().getText());
                                }
                            }.dispatch(Root.ROOT.getEnvironment());

                            String name = new GetAction<String>() {
                                @Override
                                public void run(Object... os) throws Exception {
                                    setResult(data.getName());
                                }
                            }.dispatch(Root.ROOT.getEnvironment());

                            if (text.equals(name)) {
                                String styleClass = findStyleClassByPattern(match.pie.region.getStyleClass(), "data");
                                assertNotNull(styleClass);

                                int index = Integer.parseInt(styleClass.substring(4));
                                //Key check. That index in style class is equal to number of data.
                                assertTrue("Index <" + index
                                        + ">, name <" + name
                                        + ">, dataArray.get(index).getName() <" + dataArray.get(index).getName()
                                        + ">, text <" + text + ">.",
                                        dataArray.get(index).getName().equals(text));
                            }
                        }
                    }
                }
            }
        });
    }

    protected void checkPiesHaveTheSameRadius() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(pieChartDescriptionProvider) {
            public void run() throws Throwable {
                List<PieInfo> piesInfo = pieChartDescriptionProvider.getSlicesInfo();
                if (piesInfo.size() >= 2) {
                    double radius = piesInfo.get(0).radius;
                    for (int i = 1; i < piesInfo.size(); i++) {
                        if (Math.abs(radius - piesInfo.get(i).radius) > 0.1) {
                            throw new IllegalStateException("Different radiuses found : <" + radius + "> and <" + piesInfo.get(i).radius + ">.");
                        }
                    }
                }
            }
        });
    }
}