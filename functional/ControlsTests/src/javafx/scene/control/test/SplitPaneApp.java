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

import java.util.Arrays;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.test.chart.BaseApp;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

public class SplitPaneApp extends BaseApp {

    public static int SPLITS_NUM = 5;

    public enum Pages {

        Orientation, DividersPositions, Oversize, ResizableWithParent, UserInput
    }
    public static String PARENT_SPLIT = "parent.split.pane";
    public static String NESTED_SPLIT = "nested.split.pane";
    public static String SPLIT_PANE_CONTENT = "split.pane.content";

    public SplitPaneApp() {
        super(800, 500, "SplitPane", false);
        SLOT_WIDTH = 200;
        SLOT_HEIGHT = 200;
    }

    protected class ContentOversizeNode extends TestNode {

        protected Orientation orientation;

        public ContentOversizeNode(Orientation orientation) {
            this.orientation = orientation;
        }

        @Override
        public Node drawNode() {
            SplitPane split_pane = new SplitPane();
            Rectangle blue_rect = new Rectangle(SLOT_WIDTH, SLOT_HEIGHT);
            blue_rect.setFill(new Color(0, 0, 1, 1));
            Rectangle red_rect = new Rectangle(SLOT_WIDTH, SLOT_HEIGHT);
            red_rect.setFill(new Color(1, 0, 0, 1));

            split_pane.getItems().add(blue_rect);
            split_pane.getItems().add(red_rect);

            split_pane.setMaxSize(SLOT_WIDTH, SLOT_HEIGHT);
            split_pane.setPrefSize(SLOT_WIDTH, SLOT_HEIGHT);
            split_pane.setMinSize(SLOT_WIDTH, SLOT_HEIGHT);

            split_pane.setStyle("-fx-border-color: darkgray;");

            split_pane.setOrientation(orientation);
            return split_pane;
        }
    }

    protected class PositionsNode extends TestNode {

        protected Orientation orientation;
        protected double[] position;

        public PositionsNode(double[] position, Orientation orientation) {
            this.position = position;
            this.orientation = orientation;
        }

        @Override
        public Node drawNode() {
            SplitPane node = (SplitPane) createObject();
            node.setDividerPositions(position);

            SplitPane split_pane = new SplitPane();
            for (int i = 0; i < SPLITS_NUM; i++) {
                VBox box = new VBox();
                box.setId(SPLIT_PANE_CONTENT);
                Label label = new Label("Split's " + i + " content");
                label.setMinSize(0, 0);
                box.getChildren().add(label);
                split_pane.getItems().add(box);
            }
            for (int i = 0; i < SPLITS_NUM - 1; i++) {
                split_pane.setDividerPosition(i, 1.0 * (i + 1) / SPLITS_NUM);
            }

            node.setOrientation(orientation);
            if (node.getOrientation() != orientation) {
                reportGetterFailure("getOrientation()");
            }
            return node;
        }
    }

    protected class ResizableWithParentNode extends TestNode {

        protected Orientation orientation;

        public ResizableWithParentNode(Orientation orientation) {
            this.orientation = orientation;
        }

        @Override
        public Node drawNode() {
            SplitPane split_pane = new SplitPane();
            split_pane.setId(PARENT_SPLIT);

            split_pane.getItems().add(createNested());
            split_pane.getItems().add(createNested());

            split_pane.setOrientation(orientation);

            split_pane.setMaxSize(SLOT_WIDTH, SLOT_HEIGHT);
            split_pane.setPrefSize(SLOT_WIDTH, SLOT_HEIGHT);
            split_pane.setMinSize(0, 0);

            return split_pane;
        }

        protected SplitPane createNested() {
            SplitPane nested_split_pane = (SplitPane) createObject();
            nested_split_pane.setId(NESTED_SPLIT);

            boolean resizable = false;
            for (Node node : nested_split_pane.getItems()) {
                SplitPane.setResizableWithParent(node, resizable);
                if (SplitPane.isResizableWithParent(node) != resizable) {
                    reportGetterFailure("SplitPane.isResizableWithParent(node)");
                    break;
                }
                resizable = !resizable;
            }
            nested_split_pane.setOrientation(orientation);

            nested_split_pane.setMaxSize(-1, -1);
            nested_split_pane.setPrefSize(-1, -1);
            nested_split_pane.setMinSize(-1, -1);

            return nested_split_pane;
        }
    }

    @Override
    protected TestNode setup() {

        setupObject(Pages.Orientation.name(), Orientation.values());

        double[][] dividers = {{1.0 / SPLITS_NUM, 2.0 / SPLITS_NUM, 0},
            {-1.0 / SPLITS_NUM, 1 + 1.0 / SPLITS_NUM}};

        PositionsNode divider_nodes[] = new PositionsNode[dividers.length * Orientation.values().length];
        String divider_node_names[] = new String[dividers.length * Orientation.values().length];
        for (int j = 0; j < Orientation.values().length; j++) {
            for (int i = 0; i < dividers.length; i++) {
                divider_nodes[j * dividers.length + i] = new PositionsNode(dividers[i], Orientation.values()[j]);
                divider_node_names[j * dividers.length + i] = Arrays.toString(dividers[i]) + " " + Orientation.values()[j].name();
            }
        }
        pageSetup(Pages.DividersPositions.name(), divider_nodes, divider_node_names);

        ContentOversizeNode oversize_nodes[] = new ContentOversizeNode[Orientation.values().length];
        String oversize_node_names[] = new String[Orientation.values().length];
        for (int j = 0; j < Orientation.values().length; j++) {
            oversize_nodes[j] = new ContentOversizeNode(Orientation.values()[j]);
            oversize_node_names[j] = Orientation.values()[j].name();
        }
        pageSetup(Pages.Oversize.name(), oversize_nodes, oversize_node_names);

        ResizableWithParentNode resizable_nodes[] = new ResizableWithParentNode[Orientation.values().length];
        String resizable_node_names[] = new String[resizable_nodes.length];
        for (int j = 0; j < Orientation.values().length; j++) {
            resizable_nodes[j] = new ResizableWithParentNode(Orientation.values()[j]);
            resizable_node_names[j] = Orientation.values()[j].name();
        }
        pageSetup(Pages.ResizableWithParent.name(), resizable_nodes, resizable_node_names);

        setupObject(Pages.UserInput.name(), Pages.Orientation.name(), Orientation.values());

        return rootTestNode;
    }

    @Override
    protected Object createObject() {
        return createObject(SLOT_WIDTH, SLOT_HEIGHT);
    }

    protected Object createObject(double width, double height) {
        return createObject(width, height, null, null);
    }

    protected Object createObject(double width, double height, Double tab_width, Double tab_height) {
        SplitPane split_pane = new SplitPane();
        for (int i = 0; i < SPLITS_NUM; i++) {
            VBox box = new VBox();
            box.setId(SPLIT_PANE_CONTENT);
            Label label = new Label("Split's " + i + " content");
            label.setMinSize(0, 0);
            box.getChildren().add(label);
            split_pane.getItems().add(box);
        }
        for (int i = 0; i < SPLITS_NUM - 1; i++) {
            split_pane.setDividerPosition(i, 1.0 * (i + 1) / SPLITS_NUM);
        }

        split_pane.setMaxSize(width, height);
        split_pane.setPrefSize(width, height);
        split_pane.setMinSize(0, 0);
        split_pane.setStyle("-fx-border-color: darkgray;");
        return split_pane;
    }

    protected Rectangle createRect(Double tab_width, Double tab_height, Color color) {
        return new Rectangle(tab_width == null ? 10 : tab_width,
                tab_height == null ? 5 : tab_height / 2,
                color);
    }

    public static void main(String[] args) {
        Utils.launch(SplitPaneApp.class, args);
    }
}