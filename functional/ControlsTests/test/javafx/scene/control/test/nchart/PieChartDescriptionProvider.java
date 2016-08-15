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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.test.colorpicker.TestBase;
import static javafx.scene.control.test.nchart.Geometry.*;
import javafx.scene.control.test.nchart.PieChartDescriptionProvider.PieChartContentDescription.PieLineLabelMatch;
import static javafx.scene.control.test.nchart.Utils.*;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Text;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.Wrap;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.timing.State;
import org.junit.Assert;
import static org.junit.Assert.*;

/**
 * @author Alexander Kirov
 *
 * This class is a part of experimental tests on Charts, which are aimed to
 * replace tests with huge amount of images, and which (probably) could fail at
 * any moment and for unknown reason. They could be fixed or disabled.
 */
public class PieChartDescriptionProvider extends ChartDescriptionProvider {

    public static final String ChartLabelPathStyleClass = "chart-pie-label-line";
    public static final String ChartPieLabelStyleClass = "chart-pie-label";
    private Wrap<? extends Chart> piechart;
    //Cached results:
    private Wrap<? extends Path> labelPath;
    private Line[] pathLines;
    private List<Double> linesAngles;
    private List<PieInfo> slicesInfo;
    private ArrayList<Wrap<? extends Text>> pieLabels;
    private Map<PieInfo, Color> pieInfoOnColorMatching = new HashMap<PieInfo, Color>();
    private Map<String, Color> colorsInLegend;
    private PieChartContentDescription pieChartContentDescription;
    private Map<Wrap<? extends Label>, Color> labelToColorMatch = new HashMap<Wrap<? extends Label>, Color>();
    private Map<PieInfo, Color> pieColors;

    public PieChartDescriptionProvider(Wrap<? extends PieChart> piechart) {
        super(piechart);
        this.piechart = piechart;
    }

    @Override
    public void clearState() {
        super.clearState();
        labelPath = null;
        pathLines = null;
        linesAngles = null;
        slicesInfo = null;
        pieLabels = null;
        pieInfoOnColorMatching.clear();
        colorsInLegend = null;
        pieChartContentDescription = null;
        labelToColorMatch.clear();
        pieColors = null;
    }

    public Wrap<? extends Path> getLabelPath() {
        if (USE_CACHING && labelPath != null) {
            return labelPath;
        }

        labelPath = controlAsParent.lookup(Path.class, new ByStyleClass<Path>(ChartLabelPathStyleClass)).wrap();
        return labelPath;
    }

    /**
     * Note, that this method returns line coordinates relative to scene. Use
     * convertToGlobalCoordinates(Line[]) method to convert them to global
     * coordinates.
     *
     * It will not return line, if its length is equal to 0.
     */
    public Line[] getPathLines() {
        if (USE_CACHING && pathLines != null) {
            return pathLines;
        }

        ArrayList<Line> lines = new ArrayList<Line>();

        final Wrap<? extends Path> path = getLabelPath();

        if (path == null) {
            return new Line[0];
        }

        final ObservableList<PathElement> elementsList = new GetAction<ObservableList<PathElement>>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(path.getControl().getElements());
            }
        }.dispatch(Root.ROOT.getEnvironment());

        for (PathElement element : elementsList) {
            if (element instanceof LineTo) {
                PathElement previous = elementsList.get(elementsList.indexOf(element) - 1);
                Line lineToAdd = null;
                if (previous instanceof MoveTo) {
                    lineToAdd = new Line(((MoveTo) previous).getX(), ((MoveTo) previous).getY(), ((LineTo) element).getX(), ((LineTo) element).getY());
                }
                if (previous instanceof LineTo) {
                    lineToAdd = new Line(((LineTo) previous).getX(), ((LineTo) previous).getY(), ((LineTo) element).getX(), ((LineTo) element).getY());
                }
                if (previous instanceof ArcTo) {
                    lineToAdd = new Line(((ArcTo) previous).getX(), ((ArcTo) previous).getY(), ((LineTo) element).getX(), ((LineTo) element).getY());
                }

                if (lineLenght(lineToAdd) > 0.0) {
                    lines.add(lineToAdd);
                }
            }
        }

        pathLines = lines.toArray(new Line[lines.size()]);
        return pathLines;
    }

    public List<Double> getLinesAngles() {
        if (USE_CACHING && linesAngles != null) {
            return linesAngles;
        }

        List<Map.Entry<Line, Wrap<? extends Text>>> matchings = matchLinesToLabels(getPathLines(), getPieLabels());
        Map<String, Line> lines = getDataNameToLineMatching(matchings);
        linesAngles = getAngles(lines.values());
        return linesAngles;
    }

    public List<Double> getAngles(Collection<Line> lines) {
        List<Double> angles = new ArrayList<Double>();
        for (Line line : lines) {
            angles.add(reductionInto0to360(getLineAngle(line)));
        }

        return angles;
    }

    public List<PieInfo> getSlicesInfo() {
        if (USE_CACHING && slicesInfo != null) {
            return slicesInfo;
        }

        //Usual style classes: chart-pie data0 default-color0
        Lookup lookup = controlAsParent.lookup(Region.class, new ByStyleClass("chart-pie"));
        slicesInfo = new ArrayList<PieInfo>();
        int size = lookup.size();
        for (int i = 0; i < size; i++) {
            final Wrap wrap = lookup.wrap(i);
            if (((Region) wrap.getControl()).getShape() != null) {
                slicesInfo.add(new PieInfo(wrap));
            }
        }

        return slicesInfo;
    }

    /**
     * On input receives array of items, and return array of lines in the same
     * order, as labels came on input.
     *
     * @param pathLines
     * @param getLabels
     * @return
     */
    public List<Map.Entry<Line, Wrap<? extends Text>>> matchLinesToLabels(Line[] pathLines, ArrayList<Wrap<? extends Text>> labels) {
        Assert.assertEquals(pathLines.length, labels.size());
        List<Map.Entry<Line, Wrap<? extends Text>>> result = new ArrayList<Map.Entry<Line, Wrap<? extends Text>>>();
        for (Line line : pathLines) {
            Map.Entry<Line, Wrap<? extends Text>> entry = new AbstractMap.SimpleEntry<Line, Wrap<? extends Text>>(line, null);
            double min = Double.MAX_VALUE;
            for (Wrap wrap : labels) {
                double newMin = minDistance(getChartContent(), line, wrap);
                if (newMin < min) {
                    min = newMin;
                    entry.setValue(wrap);
                }
            }
            result.add(entry);
        }

        return result;
    }

    public Map<String, Line> getDataNameToLineMatching(List<Map.Entry<Line, Wrap<? extends Text>>> matching) {
        Map<String, Line> map = new HashMap<String, Line>();

        for (Map.Entry<Line, Wrap<? extends Text>> entry : matching) {
            final String text = entry.getValue().getControl().getText();
            if (map.containsKey(text)) {
                throw new IllegalStateException("Not unique matching for text <" + text + ">.");
            }
            map.put(text, entry.getKey());
        }

        return map;
    }

    public ArrayList<Wrap<? extends Text>> getPieLabels() {
        if (USE_CACHING && pieLabels != null) {
            return pieLabels;
        }

        pieLabels = findLabels();

        piechart.waitState(new State<Boolean>() {
            public Boolean reached() {
                return isLabelsSetCorrect(pieLabels);
            }
        }, Boolean.TRUE);

        return pieLabels;
    }

    /**
     * @param info - description of the pie
     * @return - color of the center of the pie
     * @throws AWTException
     */
    public Color getPieColor(PieInfo info) {
        if (USE_CACHING && pieInfoOnColorMatching.containsKey(info) && pieInfoOnColorMatching.get(info) != null) {
            return pieInfoOnColorMatching.get(info);
        }

        TestBase.newRobot();
        Rectangle relativeWrapBounds = getChartContent().getScreenBounds();
        final double x = relativeWrapBounds.x + info.regionCenterX + info.radius * Math.cos(info.bisectAngle / 180 * Math.PI) / 2;
        final double y = relativeWrapBounds.y + info.regionCenterY - info.radius * Math.sin(info.bisectAngle / 180 * Math.PI) / 2;
        final Color color = TestBase.getPixelColor(x, y);
        pieInfoOnColorMatching.put(info, color);
        return color;
    }

    /**
     * @param label - wrap of label
     * @return - color of the circle near the label inside the legend
     * @throws AWTException
     */
    public Color getLabelColor(final Wrap<? extends Label> label) {
        if (USE_CACHING && labelToColorMatch.containsKey(label) && labelToColorMatch.get(label) != null) {
            return labelToColorMatch.get(label);
        }

        TestBase.newRobot();
        Rectangle circleInLegend = label.as(Parent.class, Node.class).lookup(Region.class, new ByStyleClass("chart-legend-item-symbol")).wrap().getScreenBounds();

        final Color color = TestBase.getPixelColor(circleInLegend.x + circleInLegend.width / 2, circleInLegend.y + circleInLegend.height / 2);

        labelToColorMatch.put(label, color);
        return color;
    }

    public Map<PieInfo, Color> getPieColors() {
        if (USE_CACHING && pieColors != null) {
            return pieColors;
        }

        pieColors = new HashMap<PieInfo, Color>();
        for (PieLineLabelMatch match : getPieChartContentDescription().info) {
            pieColors.put(match.pie, getPieColor(match.pie));
        }

        return pieColors;
    }

    public Map<String, Color> getColorsInLegend() {
        if (USE_CACHING && colorsInLegend != null) {
            return colorsInLegend;
        }

        colorsInLegend = new HashMap<String, Color>();
        for (final Wrap<? extends Label> label : getLegendLabels()) {
            colorsInLegend.put(new GetAction<String>() {
                @Override
                public void run(Object... os) throws Exception {
                    setResult(label.getControl().getText());
                }
            }.dispatch(Root.ROOT.getEnvironment()), getLabelColor(label));
        }

        return colorsInLegend;
    }

    public PieChartContentDescription getPieChartContentDescription() {
        if (USE_CACHING && pieChartContentDescription != null) {
            return pieChartContentDescription;
        }

        pieChartContentDescription = new PieChartContentDescription();
        return pieChartContentDescription;
    }

    public class PieInfo {

        public final String colorSlyleClass;
        public final String dataStyleClass;
        public final double radius;
        public final double startAngle;
        public final double angle;
        public final double centerX;
        public final double centerY;
        public final double regionCenterX;
        public final double regionCenterY;
        public final double bisectAngle;
        public final Region region;

        public PieInfo(Wrap<? extends Region> wrap) {
            region = wrap.getControl();

            regionCenterX = region.getLayoutX();
            regionCenterY = region.getLayoutY();

            Assert.assertTrue(region.getShape() instanceof Arc);
            final Arc arc = (Arc) region.getShape();

            centerX = arc.getCenterX();
            centerY = arc.getCenterY();

            Assert.assertEquals(arc.getRadiusX(), arc.getRadiusY(), 0.00001);
            radius = arc.getRadiusX();
            startAngle = reductionInto0to360(arc.getStartAngle());
            angle = arc.getLength();//yes, angle in grads.
            bisectAngle = reductionInto0to360(startAngle + angle / 2.0);

            dataStyleClass = findStyleClassByPattern(region.getStyleClass(), "data");
            colorSlyleClass = findStyleClassByPattern(region.getStyleClass(), "default-color");
        }

        @Override
        public String toString() {
            return "PieInfo : colorSlyleClass <" + colorSlyleClass
                    + ">, dataStyleClass <" + dataStyleClass
                    + ">, radius <" + radius
                    + ">, startAngle <" + startAngle
                    + ">, angle < " + angle
                    + ">, centerX <" + centerX
                    + ">, centerY <" + centerY + ">.";
        }
    }

    /**
     * It is supposed, that when parsing is done, it should be done correctly,
     * or it will not be done at all.
     *
     * It is expected, that amount of lines is equal to the amount of labels.
     * Also, it is expected, that amount if Pies is equal to the data size, and
     * more than amount of lines (== amount of labels).
     *
     */
    public class PieChartContentDescription {

        final protected ObservableList<PieLineLabelMatch> info;
        final protected int labelsArtifactsSize;

        protected PieChartContentDescription() {
            final List<PieInfo> piesInfo = getSlicesInfo();
            final Line[] lines = getPathLines();
            final ArrayList<Wrap<? extends Text>> labels = getPieLabels();
            final ObservableList<PieChart.Data> data = getData();

            assertEquals(lines.length, labels.size());
            assertEquals(data.size(), piesInfo.size());
            assertTrue(piesInfo.size() >= lines.length);

            info = FXCollections.observableArrayList();

            int matchingCounter = 0;

            for (PieInfo pieInfo : piesInfo) {
                Line line = null;
                Wrap<? extends Text> text = null;

                if (lines.length > 0) {
                    line = findLineWithTheNearestAngle(pieInfo, lines);

                    if (Math.abs(reductionInto0to360(getLineAngle(line)) - pieInfo.bisectAngle) > 0.5) {
                        line = null;//Strict matching is expected.
                    }

                    if (line != null) {
                        int minIndex = 0;
                        double minDistance = Double.MAX_VALUE;
                        for (Wrap<? extends Text> label : labels) {
                            double newMin = minDistance(getChartContent(), line, label);
                            if (newMin < minDistance) {
                                minIndex = labels.indexOf(label);
                                minDistance = newMin;
                            }
                        }

                        text = labels.get(minIndex);
                        matchingCounter++;
                    }
                }

                info.add(new PieLineLabelMatch(pieInfo, line, text));
            }

            assertEquals(matchingCounter, lines.length);
            labelsArtifactsSize = matchingCounter;
        }

        private Line findLineWithTheNearestAngle(PieInfo info, Line[] lines) {
            assertTrue(lines.length > 0);
            assertNotNull(info);

            int minAngleIndex = 0;
            double minAngleDiff = Double.MAX_VALUE;

            final List<Double> angles = getAngles(Arrays.asList(lines));

            for (int i = 0; i < angles.size(); i++) {
                if (Math.abs(reductionInto0to360(angles.get(i)) - reductionInto0to360(info.bisectAngle)) < minAngleDiff) {
                    minAngleIndex = i;
                    minAngleDiff = Math.abs(reductionInto0to360(angles.get(i)) - reductionInto0to360(info.bisectAngle));
                }
            }
            return lines[minAngleIndex];
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("Mathing info (" + info.size() + " items) : \n");
            for (PieLineLabelMatch match : info) {
                builder.append(match.toString()).append("\n");
            }
            return builder.toString();
        }

        /**
         * Actually, it is a triple of Pie, and Line and Label, associted with
         * it, if the last two are.
         */
        public class PieLineLabelMatch {

            protected final Line line;
            protected final Wrap<? extends Text> label;
            protected final PieInfo pie;

            protected PieLineLabelMatch(PieInfo pie, Line line, Wrap<? extends Text> label) {
                assertNotNull(pie);
                this.label = label;
                this.line = line;
                this.pie = pie;
            }

            @Override
            public String toString() {
                return "Matching : pie <" + pie + ">, line <" + ((line != null) ? getLineDescription(line) : "null") + ">, label <" + ((label != null) ? label.getControl().getText() : "null") + ">.";
            }
        }
    }

    /**
     * Be careful, it returns the copy of the data array, and it is not
     * synchronized with he original data array.
     */
    final public ObservableList<PieChart.Data> getData() {
        final ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        new GetAction() {
            @Override
            public void run(Object... os) throws Exception {
                PieChart chart = (PieChart) piechart.getControl();
                for (PieChart.Data dataItem : chart.getData()) {
                    data.add(dataItem);
                }
            }
        }.dispatch(Root.ROOT.getEnvironment());

        return data;
    }

    final public boolean isClockwise() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((PieChart) piechart.getControl()).isClockwise());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public boolean isLabelsVisible() {
        return new GetAction<Boolean>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((PieChart) piechart.getControl()).getLabelsVisible());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Double getStartAngle() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((PieChart) piechart.getControl()).getStartAngle());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    final public Double getLabelLineLength() {
        return new GetAction<Double>() {
            @Override
            public void run(Object... os) throws Exception {
                setResult(((PieChart) piechart.getControl()).getLabelLineLength());
            }
        }.dispatch(Root.ROOT.getEnvironment());
    }

    private boolean isLabelsSetCorrect(ArrayList<Wrap<? extends Text>> labelWraps) {
        List<String> labels = new ArrayList<String>();

        for (Wrap<? extends Text> wrap : labelWraps) {
            labels.add(wrap.getControl().getText());
        }
        //Check, that, there are no equal strings.
        Set set = new HashSet(labels);
        return set.size() == labels.size();
    }

    private ArrayList<Wrap<? extends Text>> findLabels() {
        ArrayList<Wrap<? extends Text>> labels = new ArrayList<Wrap<? extends Text>>();
        final Lookup lookup = controlAsParent.lookup(Text.class, new ByStyleClass(ChartPieLabelStyleClass));

        for (int i = 0; i < lookup.size(); i++) {
            final Wrap<? extends Text> textWrap = lookup.wrap(i);

            boolean isCorrect = new GetAction<Boolean>() {
                @Override
                public void run(Object... os) throws Exception {
                    Text text = textWrap.getControl();
                    setResult(text.getOpacity() > 0 && text.isVisible() && (text.getText().length() > 0));
                }
            }.dispatch(Root.ROOT.getEnvironment());

            if (isCorrect) {
                labels.add(textWrap);
            }
        }
        return labels;
    }
}