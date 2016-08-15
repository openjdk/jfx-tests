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
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import static javafx.scene.control.test.nchart.Utils.*;
import javafx.scene.control.test.nchart.Utils.RunnableWithThrowable;
import javafx.scene.control.test.util.UtilTestFunctions;
import javafx.scene.control.test.utils.ptables.AbstractPropertyController.SettingType;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public class ChartTestsBase extends UtilTestFunctions {

    static Wrap<? extends Scene> scene;
    static Wrap<? extends Chart> testedControl;
    static Parent<Node> controlAsParent;
    protected static ChartDescriptionProvider chartDescriptionProvider;

    @Before
    public void before2() {
        chartDescriptionProvider = new ChartDescriptionProvider(testedControl);
        chartDescriptionProvider.clearState();
    }

    protected void checkTitle(String expectedText) {
        checkTextFieldText(ChartProperties.title, expectedText);
        final Wrap<? extends Label> title = chartDescriptionProvider.getTitleWrap();

        String realTitle = new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(title.getControl().getText());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        assertEquals(realTitle, expectedText);

        assertTrue(testedControl.getScreenBounds().contains(title.getScreenBounds()));

        assertTrue(new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(title.getControl().isVisible() & title.getControl().getOpacity() > 0.01);
            }
        }.dispatch(Root.ROOT.getEnvironment()));

        checkRectanglesRelation(chartDescriptionProvider.getChartContent().getScreenBounds(), RectanglesRelations.CENTERED_IN_VERTICAL, title.getScreenBounds());
    }

    protected void checkTitlePosition(final Side side) throws Throwable {
        final Wrap<? extends Label> title = chartDescriptionProvider.getTitleWrap();
        final RectanglesRelations relation = convertSideToRelation(side);

        doCheckingWithExceptionCatching(new RunnableWithThrowable(chartDescriptionProvider) {
            public void run() throws Throwable {
                checkRectanglesRelation(chartDescriptionProvider.getChartContent().getScreenBounds(), relation, title.getScreenBounds());
                checkRectanglesRelation(chartDescriptionProvider.getChartContent().getScreenBounds(),
                        (side.equals(Side.BOTTOM) || side.equals(Side.TOP))
                        ? RectanglesRelations.CENTERED_IN_VERTICAL
                        : RectanglesRelations.CENTERED_IN_HORIZONTAL,
                        title.getScreenBounds());
            }
        });
    }

    protected void checkLegendPosition(final Side side, boolean couldLegendBeInvisible) throws Throwable {
        final Wrap<? extends Legend> legend = chartDescriptionProvider.getLegend();

        boolean visibility = new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(legend.getControl().isVisible());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        if (!couldLegendBeInvisible) {
            assertTrue(visibility);
        }

        if (visibility) {
            final RectanglesRelations relation = convertSideToRelation(side);

            doCheckingWithExceptionCatching(new RunnableWithThrowable(chartDescriptionProvider) {
                public void run() throws Throwable {
                    checkRectanglesRelation(chartDescriptionProvider.getChartContent().getScreenBounds(), relation, legend.getScreenBounds());
                    checkRectanglesRelation(chartDescriptionProvider.getChartContent().getScreenBounds(),
                            (side.equals(Side.BOTTOM) || side.equals(Side.TOP))
                            ? RectanglesRelations.CENTERED_IN_VERTICAL
                            : RectanglesRelations.CENTERED_IN_HORIZONTAL, legend.getScreenBounds());
                }
            });
        }
    }

    protected void setSize(double width, double height) throws InterruptedException {
        setPropertyBySlider(SettingType.SETTER, ChartProperties.prefWidth, width);
        setPropertyBySlider(SettingType.SETTER, ChartProperties.prefHeight, height);
    }

    protected void checkChartAppearanceInvariant() throws Throwable {
        doCheckingWithExceptionCatching(new RunnableWithThrowable(chartDescriptionProvider) {
            public void run() throws Throwable {
                ArrayList<Wrap> nonIntersectingComponents = getStandartNonIntersectingComponents();
                checkNonIntersectance(chartDescriptionProvider.getChartContent(), nonIntersectingComponents);
                checkEverythingIsInsideControl(nonIntersectingComponents);
                checkLegendContentSizeCorrectness();
            }
        });
    }

    protected void checkEverythingIsInsideControl(List<Wrap> wraps) {
        Rectangle controlBounds = testedControl.getScreenBounds();
        for (Wrap wrap : wraps) {
            Assert.assertTrue(controlBounds.contains(wrap.getScreenBounds()));
        }
    }

    protected void checkLegendContentSizeCorrectness() {
        int itemsSize = new GetAction<Integer>() {
            @Override
            public void run(Object... os) throws Exception {
                final Chart chart = testedControl.getControl();
                if (chart instanceof PieChart) {
                    setResult(((PieChart) chart).getData().size());
                } else {
                    setResult(((XYChart) chart).getData().size());
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());

        final List<Wrap<? extends Label>> legendLabels = chartDescriptionProvider.getLegendLabels();
        Assert.assertEquals(legendLabels.size(), itemsSize);
    }

    protected void checkChartPrefSizeAffectance(double prefWidth, double prefHeight) {
        double width = new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getWidth());
            }
        }.dispatch(Root.ROOT.getEnvironment());
        double height = new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(testedControl.getControl().getHeight());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        assertEquals(width, prefWidth, 0.05 * Math.max(width, prefWidth));
        assertEquals(height, prefHeight, 0.05 * Math.max(height, prefHeight));
    }

    protected ArrayList<Wrap> getStandartNonIntersectingComponents() {
        final Wrap<? extends Legend> legend = chartDescriptionProvider.getLegend();
        final List<Wrap<? extends Label>> legendLabels = chartDescriptionProvider.getLegendLabels();
        final Wrap<? extends Label> title = chartDescriptionProvider.getTitleWrap();

        for (Wrap wrap : legendLabels) {
            Assert.assertTrue("Legend labels must be inside legend.", legend.getScreenBounds().contains(wrap.getScreenBounds()));
        }

        ArrayList<Wrap> nonIntersectingComponents = new ArrayList<Wrap>();
        if (chartDescriptionProvider.isLegendVisible() && new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                Legend control = legend.getControl();
                setResult(control.isVisible() && control.getWidth() > 0 && control.getHeight() > 0);
            }
        }.dispatch(Root.ROOT.getEnvironment())) {
            nonIntersectingComponents.add(legend);
        }
        nonIntersectingComponents.add(title);
        return nonIntersectingComponents;
    }

    protected enum ChartProperties {

        legendVisible, legendSide, titleSide, title, animated, prefWidth, prefHeight
    };
}