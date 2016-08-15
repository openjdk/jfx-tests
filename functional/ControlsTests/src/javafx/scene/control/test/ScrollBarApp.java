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
import javafx.scene.control.ScrollBar;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class ScrollBarApp extends BasicButtonChooserApp {

    protected TestNode rootTestNode = new TestNode();
    int SLOT_WIDTH = 140;
    int SLOT_HEIGHT = 140;

    public enum Pages {

        Constructor, AdjustValueHorizontal, AdjustValueVertical, Decrement, Increment, PositionsHorizontal, PositionsVertical, VisibleAmount
    }

    public ScrollBarApp() {
        super(800, 500, "ScrollBar", false); // "true" stands for "additionalActionButton = "
    }

    protected class DefaultConstructorNode extends TestNode {

        public DefaultConstructorNode() {
        }

        @Override
        public Node drawNode() {
            ScrollBar scroll = new ScrollBar();
            if (Double.compare(scroll.getValue(), 0) != 0) {
                reportGetterFailure("ScrollBar.getValue()");
            }
            if (Double.compare(scroll.getMin(), 0) != 0) {
                reportGetterFailure("ScrollBar.getMin()");
            }
            if (Double.compare(scroll.getMax(), 100) != 0) {
                reportGetterFailure("ScrollBar.getMax()");
            }
            if (Double.compare(scroll.getBlockIncrement(), 10) != 0) {
                reportGetterFailure("ScrollBar.getBlockIncrement()");
            }
            if (Double.compare(scroll.getUnitIncrement(), 1) != 0) {
                reportGetterFailure("ScrollBar.isVertical()");
            }
            if (scroll.getOrientation() != Orientation.HORIZONTAL) {
                reportGetterFailure("ScrollBar.isVertical()");
            }
            if (Double.compare(scroll.getVisibleAmount(), 15) != 0) {
                reportGetterFailure("ScrollBar.isVertical()");
            }
            return scroll;
        }
    }

    protected class AdjustValueNode extends TestNode {

        protected double position;
        boolean vertical;

        public AdjustValueNode(boolean vertical, double position) {
            this.position = position;
            this.vertical = vertical;
        }

        @Override
        public Node drawNode() {
            ScrollBar scroll = createScroll(0, 100, vertical);
            scroll.setValue(50);
            scroll.setBlockIncrement(20);
            double new_value = scroll.getValue();
            double desired = scroll.getMin() + position * (scroll.getMax() - scroll.getMin());
            int direction = Double.compare(new_value, desired);
            scroll.adjustValue(position);
            if (direction != 0 && !Double.isNaN(position)) {
                double increment = scroll.getBlockIncrement();
                if (Math.abs(desired - new_value) < increment) {
                    new_value = desired;
                } else {
                    if (direction == 1) {
                        new_value -= increment;
                    } else {
                        new_value += increment;
                    }
                }
                if (new_value < scroll.getMin()) {
                    new_value = scroll.getMin();
                }
                if (new_value > scroll.getMax()) {
                    new_value = scroll.getMax();
                }
                if (Double.compare(scroll.getValue(), new_value) != 0) {
                    reportGetterFailure("ScrollBar.adjustValue()");
                }
            }

            return scroll;
        }
    }

    protected class doStepNode extends TestNode {

        protected int count;
        protected double step;
        boolean vertical;
        boolean decrement;

        public doStepNode(boolean vertical, double step, int count, boolean decrement) {
            this.count = count;
            this.vertical = vertical;
            this.step = step;
            this.decrement = decrement;
        }

        @Override
        public Node drawNode() {
            ScrollBar scroll = createScroll(0, 100, vertical);
            scroll.setValue(50);
            scroll.setUnitIncrement(step);
            if (Double.compare(scroll.getUnitIncrement(), step) != 0) {
                reportGetterFailure("ScrollBar.getBlockIncrement()");
            }
            for (int i = 0; i < count; i++) {
                double val = scroll.getValue();
                if (decrement) {
                    scroll.decrement();
                    val -= step;
                } else {
                    scroll.increment();
                    val += step;
                }
                if (val < scroll.getMin()) {
                    val = scroll.getMin();
                }
                if (val > scroll.getMax()) {
                    val = scroll.getMax();
                }
                if (Double.compare(scroll.getValue(), val) != 0) {
                    reportGetterFailure("ScrollBar.decrement()");
                }
            }
            return scroll;
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
            ScrollBar scroll = createScroll(min, max, vertical);
            scroll.setValue(value);
            if (Double.compare(scroll.getMin(), min) != 0) {
                reportGetterFailure("Slider.getMin()");
            }
            if (Double.compare(scroll.getMax(), max) != 0) {
                reportGetterFailure("Slider.getMax()");
            }
            if (Double.compare(scroll.getValue(), value) != 0) {
                reportGetterFailure("Slider.getValue()");
            }
            return scroll;
        }
    }

    protected class VisibleAmountNode extends TestNode {

        boolean vertical;
        double amount;

        public VisibleAmountNode(boolean vertical, double amount) {
            this.vertical = vertical;
            this.amount = amount;
        }

        @Override
        public Node drawNode() {
            ScrollBar scroll = createScroll(0, 100, vertical);
            scroll.setVisibleAmount(amount);
            if (Double.compare(scroll.getVisibleAmount(), amount) != 0) {
                reportGetterFailure("Slider.getVisibleAmount()");
            }
            return scroll;
        }
    }

    @Override
    protected TestNode setup() {
        boolean vertical[] = {false, true};

        TestNode default_constructor_node = new DefaultConstructorNode();
        pageSetup(Pages.Constructor.name(), default_constructor_node);

        setupAdjustValuePage(Pages.AdjustValueHorizontal.name(), false);
        setupAdjustValuePage(Pages.AdjustValueVertical.name(), true);

        setupStepPage(Pages.Decrement.name(), true);
        setupStepPage(Pages.Increment.name(), false);

        setupPositionsPage(Pages.PositionsHorizontal.name(), false);
        setupPositionsPage(Pages.PositionsVertical.name(), true);

        final PageWithSlots visible_amount_page = new PageWithSlots(Pages.VisibleAmount.name(), height, width);
        visible_amount_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        double amount[] = {-10, 0, 50, 100, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        for (int k = 0; k < vertical.length; k++) {
            for (int i = 0; i < amount.length; i++) {
                TestNode visible_amount_node = new VisibleAmountNode(vertical[k], amount[i]);
                visible_amount_page.add(visible_amount_node, (vertical[k] ? "ver," : "hor,") + amount[i]);
            }
        }
        rootTestNode.add(visible_amount_page);

        return rootTestNode;
    }

    void setupAdjustValuePage(String name, boolean vertical) {
        final PageWithSlots adjust_value_page = new PageWithSlots(name, height, width);
        adjust_value_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        double positions[] = {-0.1, 0.0, 0.5, 0.6, 1.0, 1.1, Double.NaN};
        for (int j = 0; j < positions.length; j++) {
            TestNode adjust_value_node = new AdjustValueNode(vertical, positions[j]);
            adjust_value_page.add(adjust_value_node, (vertical ? "ver," : "hor,") + positions[j]);
        }
        rootTestNode.add(adjust_value_page);
    }

    void setupPositionsPage(String name, boolean vertical) {
        final PageWithSlots positions_page = new PageWithSlots(name, height, width);
        positions_page.setSlotSize(SLOT_HEIGHT - 30, SLOT_WIDTH - 30);
        double min[] = {-100, 0, 100};
        double max[] = {-100, 0, 100};
        double values[] = {-200, 0, 200};
        for (int i = 0; i < min.length; i++) {
            for (int j = 0; j < max.length; j++) {
                for (int k = 0; k < values.length; k++) {
                    TestNode value_node = new PositionsNode(vertical, min[i], max[j], values[k]);
                    positions_page.add(value_node, String.valueOf(min[i]) + "," + max[j] + "," + values[k]);
                }
            }
        }
        rootTestNode.add(positions_page);
    }

    void setupStepPage(String name, boolean decrement) {
        final PageWithSlots positions_page = new PageWithSlots(name, height, width);
        positions_page.setSlotSize(SLOT_HEIGHT - 30, SLOT_WIDTH - 30);
        boolean vertical[] = {false, true};
        double step[] = {-Double.NEGATIVE_INFINITY, -200, -10, 10, 200, Double.POSITIVE_INFINITY, Double.NaN};
        int count[] = {1, 100};
        for (int k = 0; k < vertical.length; k++) {
            for (int i = 0; i < step.length; i++) {
                for (int j = 0; j < count.length; j++) {
                    TestNode value_node = new doStepNode(vertical[k], step[i], count[j], decrement);
                    positions_page.add(value_node, (vertical[k] ? "vert," : "hor, ") + step[i] + "," + count[j]);
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

    protected ScrollBar createScroll(double min, double max, boolean vertical) {
        ScrollBar scroll = new ScrollBar();
        scroll.setOrientation(vertical ? Orientation.VERTICAL : Orientation.HORIZONTAL);
        scroll.setMin(min);
        scroll.setMax(max);
        return scroll;
    }

    public static void main(String[] args) {
        Utils.launch(ScrollBarApp.class, args);
    }
}