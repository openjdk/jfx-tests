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

import com.sun.javafx.charts.Legend;
import java.lang.reflect.InvocationTargetException;
import javafx.geometry.Side;
import javafx.scene.chart.Chart;
import javafx.scene.control.Label;
import static javafx.scene.control.test.nchart.Geometry.*;
import static javafx.scene.control.test.nchart.Utils.*;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.timing.State;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public abstract class ChartTestCommon extends ChartTestsBase {

    protected abstract Chart getNewChartInstance();
    abstract void populateChart();

    @Test(timeout = 300000)
    public void titlePropertyTest() throws InstantiationException, IllegalArgumentException, InvocationTargetException, IllegalAccessException, Throwable {
        assertNull(getNewChartInstance().getTitle());

        setPropertyByTextField(SettingType.BIDIRECTIONAL, ChartProperties.title, "New Title");
        checkTitle("New Title");

        setPropertyByTextField(SettingType.SETTER, ChartProperties.title, "Some Title");
        checkTitle("Some Title");

        setPropertyByTextField(SettingType.UNIDIRECTIONAL, ChartProperties.title, "Another Title");
        checkTitle("Another Title");

        checkChartAppearanceInvariant();
    }

    @Test(timeout = 300000)
    public void wideTitleCorrectAllocationTest() throws Throwable {
        String newTitle = "";
        for (int i = 0; i < 10; i++) {
            newTitle += "this is a title";
        }

        setPropertyByTextField(SettingType.BIDIRECTIONAL, ChartProperties.title, newTitle);
        checkTitle(newTitle);
        checkChartAppearanceInvariant();

        newTitle = "";
        for (int i = 0; i < 5; i++) {
            newTitle += "this is a title\n";
        }

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                testedControl.getControl().setTitle((String) os[0]);
            }
        }.dispatch(Root.ROOT.getEnvironment(), newTitle);

        final Wrap<? extends Label> title = chartDescriptionProvider.getTitleWrap();
        String realTitle = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(title.getControl().getText());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        assertEquals(realTitle, newTitle);
        checkChartAppearanceInvariant();
    }

    @Test(timeout = 300000)
    public void titleSidePropertyTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Throwable {
        assertEquals(getNewChartInstance().getTitleSide(), Side.TOP);

        //Switch off legend visibility to check, that title is centered to chart content.
        setPropertyByToggleClick(SettingType.SETTER, ChartProperties.legendVisible, Boolean.FALSE);

        setPropertyByTextField(SettingType.BIDIRECTIONAL, ChartProperties.title, "New Title");
        titleSideCommonTest(Side.TOP);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Side.BOTTOM, ChartProperties.titleSide);
        titleSideCommonTest(Side.BOTTOM);

        setPropertyByChoiceBox(SettingType.SETTER, Side.LEFT, ChartProperties.titleSide);
        titleSideCommonTest(Side.LEFT);

        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Side.RIGHT, ChartProperties.titleSide);
        titleSideCommonTest(Side.RIGHT);
    }

    @Test(timeout = 300000)
    public void titleSideCSSTest() throws Exception, Throwable {
        //Switch off legend visibility to check, that title is centered to chart content.
        setPropertyByToggleClick(SettingType.SETTER, ChartProperties.legendVisible, Boolean.FALSE);

        for (Side side : Side.values()) {
            applyStyle(testedControl, "-fx-title-side", side.toString());
            titleSideCommonTest(side);
        }
    }

    private void titleSideCommonTest(Side side) throws Exception, Throwable {
        checkTitlePosition(side);
        checkTextFieldText(ChartProperties.titleSide, side.toString());
        checkChartAppearanceInvariant();
    }

    @Test(timeout = 300000)
    public void legendSidePropertyTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Throwable {
        assertEquals(getNewChartInstance().getLegendSide(), Side.BOTTOM);

        setSize(450, 450);

        setPropertyByTextField(SettingType.BIDIRECTIONAL, ChartProperties.title, "New Title");
        legendSideTestCommon(Side.BOTTOM);

        setPropertyByChoiceBox(SettingType.BIDIRECTIONAL, Side.TOP, ChartProperties.legendSide);
        legendSideTestCommon(Side.TOP);

        setPropertyByChoiceBox(SettingType.SETTER, Side.LEFT, ChartProperties.legendSide);
        legendSideTestCommon(Side.LEFT);

        setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, Side.RIGHT, ChartProperties.legendSide);
        legendSideTestCommon(Side.RIGHT);
    }

    @Test(timeout = 300000)
    public void legendSideCSSTest() throws Exception, Throwable {
        for (Side side : Side.values()) {
            applyStyle(testedControl, "-fx-legend-side", side.toString());
            legendSideTestCommon(side);
        }
    }

    private void legendSideTestCommon(Side side) throws Exception, Throwable {
        checkLegendPosition(side, false);
        checkTextFieldText(ChartProperties.legendSide, side.toString());
        checkChartAppearanceInvariant();
    }

    @Test(timeout = 300000)
    public void legendVisiblePropertyTest() throws Throwable {
        assertEquals(getNewChartInstance().isLegendVisible(), true);

        populateChart();

        for (Side side : Side.values()) {
            setPropertyByChoiceBox(SettingType.SETTER, side, ChartProperties.legendSide);
            legendVisibleTestCommon(new BooleanOptionSetter() {
                public void setProperty(Boolean value) {
                    setPropertyByToggleClick(SettingType.SETTER, ChartProperties.legendVisible, value);
                }
            }, side);
        }
    }

    @Test(timeout = 300000)
    public void legendVisibleCSSTest() throws Throwable {

        populateChart();

        for (Side side : Side.values()) {
            setPropertyByChoiceBox(SettingType.SETTER, side, ChartProperties.legendSide);
            legendVisibleTestCommon(new BooleanOptionSetter() {
                public void setProperty(Boolean value) {
                    applyStyle(testedControl, "-fx-legend-visible", value.toString());
                }
            }, side);
        }
    }

    private void legendVisibleTestCommon(BooleanOptionSetter visiblePropertySetter, final Side legendSide) throws Throwable {
        checkChartAppearanceInvariant();

        final Wrap<? extends Legend> legendWrap = chartDescriptionProvider.getLegend();

        assertTrue(testedControl.getScreenBounds().contains(legendWrap.getScreenBounds()));
        checkTextFieldText(ChartProperties.legendVisible, "true");
        waitLegendRelations(true, legendSide);

        final Rectangle oldBoundaries = chartDescriptionProvider.getChartContent().getScreenBounds();

        visiblePropertySetter.setProperty(false);
        checkTextFieldText(ChartProperties.legendVisible, "false");

        waitLegendRelations(false, legendSide);

        testedControl.waitState(new State<Boolean>() {
            public Boolean reached() {
                final Rectangle extandedBounds = chartDescriptionProvider.getChartContent().getScreenBounds();
                return (extandedBounds.contains(oldBoundaries)
                        && (extandedBounds.equals(oldBoundaries)));
            }
        });

        visiblePropertySetter.setProperty(true);
        checkTextFieldText(ChartProperties.legendVisible, "true");

        testedControl.waitState(new State() {
            public Object reached() {
                return areRectanglesEqual(chartDescriptionProvider.getChartContent().getScreenBounds(), oldBoundaries);
            }
        }, Boolean.TRUE);

        waitLegendRelations(true, legendSide);
    }

    private void waitLegendRelations(Boolean visibilityExpected, final Side legendSide) throws Throwable {
        final Wrap<? extends Legend> legendWrap = chartDescriptionProvider.getLegend();

        testedControl.waitState(new State() {
            public Object reached() {
                return new GetAction<Boolean>() {
                    @Override
                    public void run(Object... os) throws Exception {
                        setResult(legendWrap.getControl().isVisible());
                    }
                }.dispatch(Root.ROOT.getEnvironment());
            }
        }, visibilityExpected);

        if (visibilityExpected) {
            //Check relations only if it is visible.
            testedControl.waitState(new State<Object>() {
                public Object reached() {
                    try {
                        checkRectanglesRelation(chartDescriptionProvider.getChartContent().getScreenBounds(), convertSideToRelation(legendSide), legendWrap.getScreenBounds());
                        checkRectanglesRelation(chartDescriptionProvider.getChartContent().getScreenBounds(), convertSideToCentering(legendSide), legendWrap.getScreenBounds());
                    } catch (Throwable ex) {
                        chartDescriptionProvider.clearState();
                        return null;
                    }
                    return true;
                }
            });
        }

        checkChartAppearanceInvariant();
    }

    @Test(timeout = 600000)//RT-28241
    public void notAnimatedChartComponentsRenderingCorrectnessTest() throws Throwable {
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, false);
        commonComponentsRenderingCorrectnessTest();
    }

    @Test(timeout = 600000)//RT-28241
    public void animatedChartComponentsRenderingCorrectnessTest() throws Throwable {
        setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.animated, true);
        commonComponentsRenderingCorrectnessTest();
    }

    private void commonComponentsRenderingCorrectnessTest() throws Throwable {
        Side tSide = null;
        Side lSide = null;
        Boolean lVisible = null;
        Double prefWH = null;

        for (Side titleSide : Side.values()) {
            for (Side legendSide : Side.values()) {
                for (Boolean legendVisible : new Boolean[]{Boolean.TRUE, Boolean.FALSE}) {
                    for (Double prefWidthHeight : new Double[]{400 - 50.0, 400 + 50.0}) {
                        //Batch of checkers for speedup.
                        if (!titleSide.equals(tSide)) {
                            setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, titleSide, ChartProperties.titleSide);
                            tSide = titleSide;
                        }

                        if (!legendSide.equals(lSide)) {
                            setPropertyByChoiceBox(SettingType.UNIDIRECTIONAL, legendSide, ChartProperties.legendSide);
                            lSide = legendSide;
                        }

                        if (!legendVisible.equals(lVisible)) {
                            setPropertyByToggleClick(SettingType.UNIDIRECTIONAL, ChartProperties.legendVisible, legendVisible);
                            lVisible = legendVisible;
                        }

                        if (!prefWidthHeight.equals(prefWH)) {
                            setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefWidth, prefWidthHeight);
                            setPropertyBySlider(SettingType.UNIDIRECTIONAL, ChartProperties.prefHeight, prefWidthHeight);
                            prefWH = prefWidthHeight;
                        }

                        try {
                            checkChartAppearanceInvariant();
                            checkChartPrefSizeAffectance(prefWidthHeight, prefWidthHeight);
                        } catch (Throwable ex) {
                            System.err.println("TitleSide : <" + titleSide + "> legendSide : <" + legendSide + "> legendVisible : <" + legendVisible + "> prefWidth and prefHeight : <" + prefWidthHeight + ">.");
                            throw ex;
                        }
                    }
                }
            }
        }
    }

    private static interface BooleanOptionSetter {

        public void setProperty(Boolean value);
    }
}