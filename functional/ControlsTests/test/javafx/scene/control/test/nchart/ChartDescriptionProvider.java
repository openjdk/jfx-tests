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
import javafx.scene.chart.Chart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import static org.junit.Assert.*;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public class ChartDescriptionProvider {

    public final static String charTtitleStyleClass = "chart-title";
    public final static String chartContentStyleClass = "chart-content";
    protected Parent<Node> controlAsParent;
    protected static boolean USE_CACHING = true;
    private Wrap<? extends Chart> chart;

    //Cached, common for all instances:
    //They are static to be common, when chart with multiple types of data will
    //be implemented.
    private static Wrap<? extends Label> titleWrap;
    private static List<Wrap<? extends Label>> legendLabels;
    private static Wrap<? extends Legend> legend;
    private static Wrap<? extends Pane> chartContent;

    public ChartDescriptionProvider(Wrap<? extends Chart> chart) {
        assertNotNull(chart);
        this.chart = chart;
        this.controlAsParent = chart.as(Parent.class, Node.class);
    }

    public void clearState() {
        titleWrap = null;
        legendLabels = null;
        legend = null;
        chartContent = null;
    }

    public Wrap<? extends Label> getTitleWrap() {
        if (USE_CACHING && titleWrap != null) {
            return titleWrap;
        }

        titleWrap = controlAsParent.lookup(Label.class, new ByStyleClass<Label>(charTtitleStyleClass)).wrap();
        return titleWrap;
    }

    public List<Wrap<? extends Label>> getLegendLabels() {
        if (USE_CACHING && legendLabels != null) {
            return legendLabels;
        }

        legend = getLegend();
        legendLabels = new ArrayList<Wrap<? extends Label>>();

        Lookup lookup = legend.as(Parent.class, Node.class).lookup(Label.class);
        for (int i = 0; i < lookup.size(); i++) {
            legendLabels.add(lookup.wrap(i));
        }

        return legendLabels;
    }

    public Wrap<? extends Legend> getLegend() {
        if (USE_CACHING && legend != null) {
            return legend;
        }

        legend = controlAsParent.lookup(Legend.class).wrap();
        return legend;
    }

    public Wrap<? extends Pane> getChartContent() {
        if (USE_CACHING && chartContent != null) {
            return chartContent;
        }

        chartContent = controlAsParent.lookup(Pane.class, new ByStyleClass(chartContentStyleClass)).wrap();
        return chartContent;
    }

    //Properties will not cached.
    final public Side getTitleSide() {
        return new GetAction<Side>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(chart.getControl().getTitleSide());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Side getLegendSide() {
        return new GetAction<Side>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(chart.getControl().getLegendSide());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public String getTitle() {
        return new GetAction<String>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(chart.getControl().getTitle());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Boolean isAnimated() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(chart.getControl().getAnimated());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Boolean isLegendVisible() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(chart.getControl().isLegendVisible());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }
}