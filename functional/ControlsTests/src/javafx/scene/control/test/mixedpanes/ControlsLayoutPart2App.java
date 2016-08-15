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

package javafx.scene.control.test.mixedpanes;

import javafx.factory.ControlsFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.FlowPane;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class ControlsLayoutPart2App extends BasicButtonChooserApp {

    public static enum Pages {

        VBox, HBox,  TilePane, StackPane, GridPane, FlowPane,
        BorderPane, AnchorPane
    }

    public ControlsLayoutPart2App() {
        super(1000, 800, "ControlLayoutPart2", true); // "true" stands for "additionalActionButton = "
    }
    private static final boolean withStyle = !Boolean.getBoolean("test.javaclient.jcovbackdoor");

/*
    private class slotStack extends TestNode {

        protected StackPane baseFill(StackPane pane) {
            pane.setPrefSize(130, 130);
            (new FillerWithAllNodes()).fill(pane);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;
        }
    }

    private class slotStackDefaults extends slotStack { // defaults

        StackPane stackPane;

        @Override
        public Node drawNode() {
            stackPane = baseFill(new StackPane());
            return stackPane;
        }

        @Override
        public void additionalAction() {
        }
    }

    private class slotStackPadding extends slotStack { // padding

        StackPane stackPane;

        @Override
        public Node drawNode() {
            stackPane = baseFill(new StackPane());
            stackPane.setPadding(new Insets(15, 15, 15, 15));
            return stackPane;
        }
    }

    private class slotAnchor extends TestNode {
        protected AnchorPane baseFill(AnchorPane pane) {
            pane.setPrefSize(130, 130);
            (new FillerWithAllNodes()).fill(pane);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;
        }
    }

    private class slotAnchorDefaults extends slotAnchor {
        AnchorPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new AnchorPane());
            return pane;
        }
        @Override
        public void additionalAction() {
            pane.setPrefSize(120, 120);
        }
    }


*/
    private class slotFlowTestNode extends TestNode {
        FlowPane pane;
        ControlsFactory page;
        ObservableList<Node> controls = FXCollections.observableArrayList();

        public slotFlowTestNode(ControlsFactory _page) {
            page = _page;
        }

        protected FlowPane baseFill(FlowPane flowPane) {
            controls.clear();
            for ( int k=0; k<2; ++k) {
                controls.add(page.createNode());
            }
            flowPane.getChildren().addAll(controls);
            flowPane.setPrefSize(310, 310);
            flowPane.setMinSize(310, 310);
            if (withStyle) {
                flowPane.setStyle("-fx-border-color: darkgray;");
            }
            return flowPane;
        }
    }

    private class slotFlowDefault extends slotFlowTestNode { // default
        public slotFlowDefault(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            return pane;
        }
    }

    private class slotFlowpadding extends slotFlowTestNode { // padding
        public slotFlowpadding(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setPadding(new Insets(15, 15, 15, 15));
            return pane;
        }
    }

    private class slotFlowVposBottom extends slotFlowTestNode { // nodeVposBottom
        public slotFlowVposBottom(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setRowValignment(VPos.BOTTOM);
            return pane;
        }
    }

    private class slotFlowVposTop extends slotFlowTestNode { // nodeVposTop
        public slotFlowVposTop(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setRowValignment(VPos.TOP);
            return pane;
        }
    }

    private class slotFlowGap5 extends slotFlowTestNode { // Gap5
        public slotFlowGap5(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setHgap(10);

            pane.setColumnHalignment(HPos.LEFT);
            pane.setRowValignment(VPos.TOP);
            pane.setAlignment(Pos.BOTTOM_LEFT);

            return pane;
        }
    }

    private class slotFlowVertical extends slotFlowTestNode { // vertical_1
        public slotFlowVertical(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane(Orientation.VERTICAL));
            pane.setVgap(20);
            pane.setAlignment(Pos.valueOf(Pos.BOTTOM_LEFT.toString()));
            pane.setRowValignment(VPos.valueOf(VPos.BOTTOM.toString()));
            pane.setColumnHalignment(HPos.valueOf(HPos.LEFT.toString()));
            return pane;
        }
    }

    private class slotTileTestNode extends TestNode {
        TilePane pane;
        ControlsFactory page;
        ObservableList<Node> controls = FXCollections.observableArrayList();

        public slotTileTestNode(ControlsFactory _page) {
            page = _page;
        }


        protected TilePane baseFill(TilePane tilePane) {
            controls.clear();
            for ( int k=0; k<2; ++k) {
                controls.add(page.createNode());
            }
            tilePane.getChildren().addAll(controls);
            tilePane.setPrefSize(310, 310);
            tilePane.setMinSize(310, 310);
            if (withStyle) {
                tilePane.setStyle("-fx-border-color: darkgray;");
            }
            return tilePane;
        }
    }

    private class slotTiledefaults extends slotTileTestNode { // defaults
        public slotTiledefaults(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new TilePane());
            return pane;
        }
        @Override
        public void additionalAction() {
            pane.setPrefSize(90, 90);
            pane.setOrientation(Orientation.VERTICAL);
        }
    }

    private class slotTilegap extends slotTileTestNode { // gap
        public slotTilegap(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new TilePane());
            pane.setHgap(5);
            pane.setVgap(20);
            return pane;
        }

        @Override
        public void additionalAction() {
            Node n1 = pane.getChildren().get(0);

            if (null != n1) {
                TilePane.setMargin(n1, new Insets(4,4,4,4));
            }
        }
    }


    private class slotTilegapCtor2 extends slotTileTestNode { // gapCtor2
        public slotTilegapCtor2(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new TilePane(Orientation.VERTICAL, 5, 15));
            pane.setAlignment(Pos.TOP_LEFT);
            return pane;
        }

        @Override
        public void additionalAction() {
            pane.setHgap(30);
        }

    }

    private class slotTilepaddingVert extends slotTileTestNode { // paddingVert
        public slotTilepaddingVert(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new TilePane());
            pane.setPadding(new Insets(15, 15, 15, 15));
            pane.setOrientation(Orientation.VERTICAL);

            pane.setAlignment(Pos.BOTTOM_LEFT);
            pane.setPrefRows(2);

            return pane;
        }
    }

    private class slotTileAlignment extends slotTileTestNode {
        public slotTileAlignment(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new TilePane());
            pane.setAlignment(Pos.TOP_RIGHT);
            return pane;
        }
    }

    private class slotTilecenter extends slotTileTestNode { // center
        public slotTilecenter(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new TilePane());
            pane.setPrefColumns(3);
            pane.setAlignment(Pos.BOTTOM_CENTER);
            for (Node _n : pane.getChildren()) {
                TilePane.setAlignment(_n, Pos.BOTTOM_RIGHT);
            }

            return pane;
        }
        @Override
        public void additionalAction() {
        }
    }

    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = 800;
        final int widthPageContentPane = 1000;

        // ============= FlowPane ====================

        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            final PageWithSlots flowPage = new PageWithSlots(Pages.FlowPane.name()+page.name(), heightPageContentPane, widthPageContentPane);
            flowPage.setSlotSize(320, 320);
            rootTestNode.add(flowPage);

            flowPage.add(new slotFlowDefault(page), "default");
            flowPage.add(new slotFlowpadding(page), "padding");
            flowPage.add(new slotFlowVposBottom(page), "VposBottom");
            flowPage.add(new slotFlowVposTop(page), "VposTop");
            flowPage.add(new slotFlowGap5(page), "Gap5");
            flowPage.add(new slotFlowVertical(page), "Vertical");
        }

        // ============= TilePane  ====================

        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            final PageWithSlots tilePage = new PageWithSlots(Pages.TilePane.name()+page.name(), heightPageContentPane, widthPageContentPane);
            tilePage.setSlotSize(320, 320);
            rootTestNode.add(tilePage);

            tilePage.add(new slotTiledefaults(page), "default");
            tilePage.add(new slotTilegap(page), "gap");
            tilePage.add(new slotTilegapCtor2(page), "gapCtor");
            tilePage.add(new slotTilepaddingVert(page), "paddingVert");
            tilePage.add(new slotTileAlignment(page), "Alignment");
            tilePage.add(new slotTilecenter(page), "center");
        }
/*
        // ============= StackPane ====================
        final PageWithSlots stackPage = new PageWithSlots(Pages.StackPane.name(), heightPageContentPane, widthPageContentPane);
        stackPage.setSlotSize(140, 140);

        stackPage.add(new slotStackDefaults(), "defaults");
        stackPage.add(new slotStackPadding(), "padding");


        // ============= AnchorPane ====================
        final PageWithSlots anchorPage = new PageWithSlots(Pages.AnchorPane.name(), heightPageContentPane, widthPageContentPane);
        anchorPage.setSlotSize(140, 140);

        anchorPage.add(new slotAnchorDefaults(),"defaults");

*/
        return rootTestNode;
    }

    public static void main(String args[]) {
        Utils.launch(ControlsLayoutPart2App.class, args);
    }
}
