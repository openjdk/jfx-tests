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
package javafx.scene.control.test;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class SliderApp extends BasicButtonChooserApp {

    protected TestNode rootTestNode = new TestNode();
    int SLOT_WIDTH = 150;
    int SLOT_HEIGHT = 150;

    public enum Pages {

        DefaultConstructor, Constructor, AdjustValueVertical, AdjustValueHorizontal, LabelFormatter, MajorTickUnit, MinorTickCount, PositionsVertical, PositionsHorizontal, NoSizes
    }

    public SliderApp() {
        super(900, 600, "Slider", false); // "true" stands for "additionalActionButton = "
    }

    protected class DefaultConstructorNode extends TestNode {

        public DefaultConstructorNode() {
        }

        @Override
        public Node drawNode() {
            Slider slider = new Slider();
            checkDefaults(slider, 0, 100, 0);
            return slider;
        }
    }

    protected class ConstructorNode extends TestNode {

        public ConstructorNode() {
        }

        @Override
        public Node drawNode() {
            Slider slider = new Slider(-50000, 50000, 0);

            checkDefaults(slider, -50000, 50000, 0);
            return slider;
        }
    }

    protected void checkDefaults(Slider slider, double min, double max, double value) {
        if (Double.compare(slider.getValue(), value) != 0) {
            reportGetterFailure("Slider.getValue()");
        }
        if (Double.compare(slider.getMin(), min) != 0) {
            reportGetterFailure("Slider.getMin()");
        }
        if (Double.compare(slider.getMax(), max) != 0) {
            reportGetterFailure("Slider.getMax()");
        }
        if (Double.compare(slider.getBlockIncrement(), 10) != 0) {
            reportGetterFailure("Slider.getBlockIncrement()");
        }
        if (Double.compare(slider.getMajorTickUnit(), 25) != 0) {
            reportGetterFailure("Slider.getMajorTickUnit()");
        }
        if (Double.compare(slider.getMinorTickCount(), 3) != 0) {
            reportGetterFailure("Slider.getMinorTickCount()");
        }
        if (slider.isShowTickLabels() != false) {
            reportGetterFailure("Slider.isShowTickLabels()");
        }
        if (slider.isShowTickMarks() != false) {
            reportGetterFailure("Slider.isShowTickMarks()");
        }
        if (slider.isSnapToTicks() != false) {
            reportGetterFailure("Slider.isSnapToTicks()");
        }
        if (slider.getOrientation() != Orientation.HORIZONTAL) {
            reportGetterFailure("Slider.getOrientation()");
        }
    }

    protected class AdjustValueNode extends TestNode {

        protected double position;
        boolean vertical;

        public AdjustValueNode(boolean vertical, double position, boolean click_to_pos) {
            this.position = position;
            this.vertical = vertical;
        }

        @Override
        public Node drawNode() {
            Slider slider = createSlider(0, 100, vertical);
            slider.setValue(50);
            slider.setBlockIncrement(20);
            double new_value = slider.getValue();
            slider.adjustValue(position);
            new_value = position;
            if (Double.compare(slider.getValue(), new_value) != 0) {
                reportGetterFailure("ScrollBar.adjustValue()");
            }
            return slider;
        }
    }

    protected class LabelFormatterNode extends TestNode {

        StringConverter<Double> formatter;
        boolean vertical;

        public LabelFormatterNode(boolean vertical, StringConverter<Double> formatter) {
            this.formatter = formatter;
            this.vertical = vertical;
        }

        @Override
        public Node drawNode() {
            Slider slider = createSlider(0, 100, vertical);
            slider.setLabelFormatter(formatter);
            if (slider.getLabelFormatter() != formatter) {
                reportGetterFailure("Slider.getLabelFormatter()");
            }
            return slider;
        }
    }

    protected class MajorTickUnitNode extends TestNode {

        double distance;
        boolean vertical;

        public MajorTickUnitNode(boolean vertical, double distance) {
            this.distance = distance;
            this.vertical = vertical;
        }

        @Override
        public Node drawNode() {
            Slider slider = createSlider(0, 100, vertical);
            slider.setMajorTickUnit(distance);
            if (Double.compare(slider.getMajorTickUnit(), distance) != 0) {
                reportGetterFailure("Slider.getMajorTickUnit()");
            }
            return slider;
        }
    }

    protected class MinorTickCountNode extends TestNode {

        int count;
        boolean vertical;

        public MinorTickCountNode(boolean vertical, int distance) {
            this.count = distance;
            this.vertical = vertical;
        }

        @Override
        public Node drawNode() {
            Slider slider = createSlider(0, 100, vertical);
            slider.setMinorTickCount(count);
            if (slider.getMinorTickCount() != count) {
                reportGetterFailure("Slider.getMinorTickCount()");
            }
            return slider;
        }
    }

    protected class PositionsNode extends TestNode {

        boolean vertical;
        double min;
        double max;
        double value;

        public PositionsNode(boolean vertical, double min, double max, double value) {
            this.vertical = vertical;
            this.min = min;
            this.max = max;
            this.value = value;
        }

        @Override
        public Node drawNode() {
            Slider slider = createSlider(min, max, vertical);
            slider.setValue(value);
            if (value > slider.getMax()) {
                value = slider.getMax();
            }
            if (value < slider.getMin()) {
                value = slider.getMin();
            }
            if (Double.compare(slider.getMin(), min) != 0) {
                reportGetterFailure("Slider.getMin()");
            }
            if (Double.compare(slider.getMax(), max) != 0) {
                reportGetterFailure("Slider.getMax()");
            }
            if (Double.compare(slider.getValue(), value) != 0) {
                reportGetterFailure("Slider.getValue()");
            }
            return slider;
        }
    }

    protected class TicksNode extends TestNode {

        boolean marks;
        boolean labels;
        boolean vertical;

        public TicksNode(boolean vertical, boolean marks, boolean labels) {
            this.marks = marks;
            this.labels = labels;
            this.vertical = vertical;
        }

        @Override
        public Node drawNode() {
            Slider slider = createSlider(0, 100, vertical);
            slider.setShowTickLabels(labels);
            slider.setShowTickMarks(marks);
            if (slider.isShowTickLabels() != labels) {
                reportGetterFailure("Slider.isShowTickLabels()");
            }
            if (slider.isShowTickMarks() != marks) {
                reportGetterFailure("Slider.isShowTickMarks()");
            }
            return slider;
        }
    }

    protected class NoSizeNode extends TestNode {

        boolean vertical;

        public NoSizeNode(boolean vertical) {
            this.vertical = vertical;
        }

        @Override
        public Node drawNode() {
            Slider slider = createSliderNoSizes(0, 100, vertical);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(10);
            return slider;
        }
    }

    @Override
    protected TestNode setup() {
        boolean vertical[] = {false, true};

        TestNode default_constructor_node = new DefaultConstructorNode();
        pageSetup(Pages.DefaultConstructor.name(), default_constructor_node);

        TestNode constructor_node = new ConstructorNode();
        pageSetup(Pages.Constructor.name(), constructor_node);

        setupAdjustValuePage(Pages.AdjustValueHorizontal.name(), false);
        setupAdjustValuePage(Pages.AdjustValueVertical.name(), true);

        final PageWithSlots label_formatter_page = new PageWithSlots(Pages.LabelFormatter.name(), height, width);
        label_formatter_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int k = 0; k < vertical.length; k++) {
            TestNode label_formatter_node = new LabelFormatterNode(vertical[k], new StringConverter<Double>() {
                @Override
                public String toString(Double t) {
                    return String.valueOf(t.longValue());
                }

                @Override
                public Double fromString(String string) {
                    return Double.parseDouble(string);
                }
            });
            label_formatter_page.add(label_formatter_node, vertical[k] ? "vert" : "hor");
        }
        rootTestNode.add(label_formatter_page);

        final PageWithSlots major_tick_unit_page = new PageWithSlots(Pages.MajorTickUnit.name(), height, width);
        major_tick_unit_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        double distances[] = {200, 100, 50, 10, 1, Double.NaN};
        for (int k = 0; k < vertical.length; k++) {
            for (int j = 0; j < distances.length; j++) {
                TestNode major_tick_unit_node = new MajorTickUnitNode(vertical[k], distances[j]);
                major_tick_unit_page.add(major_tick_unit_node, (vertical[k] ? "vert," : "hor,") + distances[j]);
            }
        }
        rootTestNode.add(major_tick_unit_page);

        final PageWithSlots minor_tick_count_page = new PageWithSlots(Pages.MinorTickCount.name(), height, width);
        minor_tick_count_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        int counts[] = {0, 1, 5, 10, 100, Integer.MAX_VALUE};
        for (int k = 0; k < vertical.length; k++) {
            for (int j = 0; j < counts.length; j++) {
                TestNode minor_tick_count_node = new MinorTickCountNode(vertical[k], counts[j]);
                minor_tick_count_page.add(minor_tick_count_node, (vertical[k] ? "vert," : "hor,") + counts[j]);
            }
        }
        rootTestNode.add(minor_tick_count_page);

        setupPositionsPage(Pages.PositionsHorizontal.name(), false);
        setupPositionsPage(Pages.PositionsVertical.name(), true);

        final PageWithSlots no_sizes_page = new PageWithSlots(Pages.NoSizes.name(), height, width);
        no_sizes_page.setSlotSize((height - 50) / 2, (width - 50) / 2);
        for (int k = 0; k < vertical.length; k++) {
            NoSizeNode no_size_node = new NoSizeNode(vertical[k]);
            no_sizes_page.add(no_size_node, (vertical[k] ? "vert" : "hor"));
        }
        rootTestNode.add(no_sizes_page);

        return rootTestNode;
    }

    void setupAdjustValuePage(String name, boolean vertical) {
        final PageWithSlots adjust_value_page = new PageWithSlots(name, height, width);
        adjust_value_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        boolean click_to_pos[] = {false, true};
        double positions[] = {-10, 0, 50, 100, 110, Double.NaN};
        for (int i = 0; i < click_to_pos.length; i++) {
            for (int j = 0; j < positions.length; j++) {
                TestNode adjust_value_node = new AdjustValueNode(vertical, positions[j], click_to_pos[i]);
                adjust_value_page.add(adjust_value_node, (vertical ? "ver," : "hor,") + positions[j] + (click_to_pos[i] ? ",clickToPos" : ""));
            }
        }
        rootTestNode.add(adjust_value_page);
    }

    void setupPositionsPage(String name, boolean vertical) {
        final PageWithSlots positions_page = new PageWithSlots(name, height, width);
        positions_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        double min[] = {-100, 0, 100/*, Double.NaN*/};
        double max[] = {-100, 0, 100/*, Double.NaN*/};
        double values[] = {-200, 0, 200/*, Double.NaN*/};
        for (int i = 0; i < min.length; i++) {
            for (int j = 0; j < max.length; j++) {
                if (min[i] > max[j] || Double.compare(min[i], max[j]) == 0) {
                    continue; //TODO: RT-11576
                }
                for (int k = 0; k < values.length; k++) {
                    TestNode value_node = new PositionsNode(vertical, min[i], max[j], values[k]);
                    positions_page.add(value_node, String.valueOf(min[i]) + "," + max[j] + "," + values[k]);
                }
            }
        }
        rootTestNode.add(positions_page);
    }

    protected void pageSetup(String pageName, TestNode node) {
        pageSetup(pageName, pageName, node);
    }

    protected void pageSetup(String pageName, String node_name, TestNode node) {
        final PageWithSlots page = new PageWithSlots(pageName, height, width);
        page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        page.add(node, node_name);
        rootTestNode.add(page);
    }

    protected Slider createSlider(double min, double max, boolean vertical) {
        Slider slider = createSliderNoSizes(min, max, vertical);
        slider.setPrefSize(SLOT_WIDTH, SLOT_HEIGHT);
        slider.setMaxSize(SLOT_WIDTH, SLOT_HEIGHT);
        slider.setMinSize(SLOT_WIDTH, SLOT_HEIGHT);
        return slider;
    }

    protected Slider createSliderNoSizes(double min, double max, boolean vertical) {
        Slider slider = new Slider();
        slider.setOrientation(vertical ? Orientation.VERTICAL : Orientation.HORIZONTAL);
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setStyle("-fx-border-color: darkgray");
        return slider;
    }

    public static void main(String[] args) {
        Utils.launch(SliderApp.class, args);
    }
}