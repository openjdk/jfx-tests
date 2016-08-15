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

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.test.chart.BaseApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class AccordionApp extends BaseApp {

    public static int PANES_NUM = 5;
    public static int OVERSIZE_PANES_NUM = 15;

    public enum Pages {

        ExpandedPane, Oversize, InputTest
    }

    public AccordionApp() {
        super(800, 500, "Accordion", false);
        SLOT_WIDTH = 250;
        SLOT_HEIGHT = 230;
    }

    protected class SelectionNode extends TestNode {

        protected int index;

        public SelectionNode(int index) {
            this.index = index;
        }

        @Override
        public Node drawNode() {
            Accordion node = (Accordion) createObject();
            TitledPane pane = node.getPanes().get(index);
            node.setExpandedPane(pane);
            if (node.getExpandedPane() != pane) {
                reportGetterFailure("getExpandedPane()");
            }
            return node;
        }
    }

    protected class TitledSizingNode extends TestNode {

        double width;
        double height;
        int panes_num;
        Double content_width;
        Double content_height;

        public TitledSizingNode(double width, double height, int panes_num, Double content_width, Double content_height) {
            this.width = width;
            this.height = height;
            this.panes_num = panes_num;
            this.content_width = content_width;
            this.content_height = content_height;
        }

        @Override
        public Node drawNode() {
            Accordion node = (Accordion) createObject(width, height, panes_num, content_width, content_height);
            node.setExpandedPane(node.getPanes().get(0));
            return node;
        }
    }

    @Override
    protected TestNode setup() {

        final PageWithSlots selection_page = new PageWithSlots(Pages.ExpandedPane.name(), height, width);
        selection_page.setSlotSize(SLOT_HEIGHT, SLOT_WIDTH);
        for (int i = 0; i < PANES_NUM; i++) {
            selection_page.add(new SelectionNode(i), Pages.ExpandedPane.name() + " " + i);
        }
        rootTestNode.add(selection_page);

        pageSetup(Pages.Oversize.name(),
                new TitledSizingNode(SLOT_WIDTH, SLOT_HEIGHT, OVERSIZE_PANES_NUM, null, null),
                new TitledSizingNode(SLOT_WIDTH, SLOT_HEIGHT, PANES_NUM, SLOT_HEIGHT * 1.2, SLOT_WIDTH * 1.2),
                "Panes Oversize",
                "Panes Oversize");

        final PageWithSlots interactive_page = new PageWithSlots(Pages.InputTest.name(), height, width);
        interactive_page.add(createEmptyNode(), Pages.InputTest.name());
        rootTestNode.add(interactive_page);

        return rootTestNode;
    }

    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT, PANES_NUM, null, null);
    }

    protected Object createObject(double width, double height, int panes_num, Double content_width, Double content_height) {
        Accordion accordion = new Accordion();
        for (int i = 0; i < panes_num; i++) {
            Label label = new Label("Pane " + i + " Content");
            label.setAlignment(Pos.TOP_LEFT);
            if (content_width != null && content_height != null) {
                label.setPrefSize(content_width, content_height);
                label.setMinSize(content_width, content_height);
                label.setMaxSize(content_width, content_height);
            }
            TitledPane titled_pane = new TitledPane("Pane " + i, label);
            accordion.getPanes().add(titled_pane);
        }
        accordion.setMaxSize(width, height);
        accordion.setPrefSize(width, height);
        accordion.setStyle("-fx-border-color: darkgray;");
        return accordion;
    }

    public static void main(String[] args) {
        Utils.launch(AccordionApp.class, args);
    }
}