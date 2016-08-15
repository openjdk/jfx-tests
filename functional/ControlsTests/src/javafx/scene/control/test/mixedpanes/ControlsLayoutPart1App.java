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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.factory.ControlsFactory;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class ControlsLayoutPart1App extends BasicButtonChooserApp {

    public static enum Pages {

        VBox, HBox,  TilePane, StackPane, GridPanePart1,GridPanePart2, FlowPane,
        BorderPane, AnchorPane
    }

    public ControlsLayoutPart1App() {
        super(1000, 800, "ControlLayoutPart1", true); // "true" stands for "additionalActionButton = "
    }
    private static final boolean withStyle = !Boolean.getBoolean("test.javaclient.jcovbackdoor");

    // page with VBox layout
    private class slotVboxTestNode extends TestNode {
        VBox vbox;
        ControlsFactory page;
        Node control1;
        Node control2;

        public slotVboxTestNode(ControlsFactory _page) {
            page = _page;
            control1 = page.createNode();
            control2 = page.createNode();
        }

        protected VBox baseFill(VBox vbox) {
            vbox.getChildren().add(control1);
            vbox.getChildren().add(control2);
            vbox.setPrefSize(210, 210);
            if (withStyle) {
                vbox.setStyle("-fx-border-color: darkgray;");
            }
            return vbox;
        }

    }


    private class slotVboxControls extends slotVboxTestNode {
        public slotVboxControls(ControlsFactory _page) {
            super (_page);
        }
        @Override
        public Node drawNode() {
            vbox = baseFill(new VBox());
            return vbox;
        }
        @Override
        public void additionalAction() {
            vbox.setPrefSize(70, 240);
            vbox.setSpacing(10);
        }
    }


    private class slotVboxnodeHpos extends slotVboxTestNode {
        public slotVboxnodeHpos(ControlsFactory _page) {
            super (_page);
        }
        @Override
        public Node drawNode() {
            vbox = baseFill(new VBox());
            //control1.setMaxWidth(40);
            //control2.setMinWidth(140);
            vbox.setAlignment(Pos.TOP_RIGHT);
            return vbox;
        }
        @Override
        public void additionalAction() {
            //tv1.getColumns().get(0).setVisible(false);
            //tv1.getColumns().get(1).setMaxWidth(40);
            //tv1.getColumns().get(1).setText("done");

            vbox.setPrefSize(40, 240);
            vbox.setSpacing(10);

        }
    }

    private class slotVboxcenter extends slotVboxTestNode {
        public slotVboxcenter(ControlsFactory _page) {
            super (_page);
        }

        @Override
        public Node drawNode() {
            vbox = baseFill(new VBox());
            //tv1.setItems(data);
            vbox.setAlignment(Pos.TOP_CENTER);
            return vbox;
        }
        @Override
        public void additionalAction() {
            //tv1.setPlaceholder(new Circle(8));
            //tv1.setItems(data3);

        }
    }

    private class slotVboxPadding extends slotVboxTestNode {
        public slotVboxPadding(ControlsFactory _page) {
            super (_page);
        }

        @Override
        public Node drawNode() {
            vbox = baseFill(new VBox());
            vbox.setPadding(new Insets(15, 15, 15, 15));
            return vbox;
        }
    }


    private class slotVboxSpacing extends slotVboxTestNode {
        public slotVboxSpacing(ControlsFactory _page) {
            super (_page);
        }

        @Override
        public Node drawNode() {
            vbox = baseFill(new VBox());
            vbox.setSpacing(5);

            return vbox;
        }
    }



    // page with HBox layout
    private class slotHboxTestNode extends TestNode {
        HBox hbox;
        ControlsFactory page;
        Node control1;
        Node control2;

        public slotHboxTestNode(ControlsFactory _page) {
            page = _page;
            control1 = page.createNode();
            control2 = page.createNode();
        }
        protected HBox baseFill(HBox hbox) {
            hbox.getChildren().add(control1);
            hbox.getChildren().add(control2);
            hbox.setPrefSize(270, 270);
            if (withStyle) {
                hbox.setStyle("-fx-border-color: darkgray;");
            }
            hbox.setFillHeight(false);
            return hbox;
        }
    }


    private class slotHboxDefault extends slotHboxTestNode {
        public slotHboxDefault(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            hbox = baseFill(new HBox());
            return hbox;
        }
        @Override
        public void additionalAction() {
            hbox.setAlignment(Pos.CENTER);
        }
    }

    private class slotHboxAlignment1 extends slotHboxTestNode {
        public slotHboxAlignment1(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            hbox = baseFill(new HBox());
            hbox.setAlignment(Pos.TOP_LEFT);
            return hbox;
        }
    }

    private class slotHboxAlignment2 extends slotHboxTestNode {
        public slotHboxAlignment2(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            hbox = baseFill(new HBox());
            hbox.setAlignment(Pos.BASELINE_CENTER);

            return hbox;
        }
    }

    private class slotHboxpadding extends slotHboxTestNode { // padding,nodeVPos.CENTER
        public slotHboxpadding(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            hbox = baseFill(new HBox());
            hbox.setPadding(new Insets(15, 15, 15, 15));
            hbox.setAlignment(Pos.CENTER_LEFT);

            return hbox;
        }
    }

    private class slotHboxspacing extends slotHboxTestNode {
        public slotHboxspacing(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            hbox = baseFill(new HBox());
            hbox.setAlignment(Pos.BOTTOM_LEFT);
            hbox.setSpacing(5);
            return hbox;
        }
    }


    private class slotBorderTestNode extends TestNode {
        BorderPane pane;
        ControlsFactory page;
        ObservableList<Node> controls = FXCollections.observableArrayList();

        public slotBorderTestNode(ControlsFactory _page) {
            page = _page;
        }

        protected BorderPane baseFill(BorderPane pane) {
            for ( int k=0; k<2; ++k) {
                controls.add(page.createNode());
            }
            pane.getChildren().addAll(controls);
            pane.setPrefSize(180, 190);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }

            return pane;
        }
    }

    private class slotBorderDefault extends slotBorderTestNode {
        public slotBorderDefault(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new BorderPane());
            pane.getChildren().clear();
            pane.setBottom(controls.get(0));
            pane.setTop(controls.get(1));
            return pane;
        }
        @Override
        public void additionalAction() {

            pane.setPrefSize(pane.getWidth() /2, pane.getHeight() /2);

        }

    }

    private class slotBorderAlignment extends slotBorderTestNode {
        public slotBorderAlignment(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new BorderPane());
            pane.getChildren().clear();

            pane.setRight(controls.get(0));
            BorderPane.setAlignment(controls.get(0), Pos.BOTTOM_RIGHT);

            return pane;
        }
    }
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

*/
    private class slotGridTestNode extends TestNode {
        GridPane pane;
        ControlsFactory page;
        ObservableList<Node> controls = FXCollections.observableArrayList();

        public slotGridTestNode(ControlsFactory _page) {
            page = _page;
        }

        protected GridPane baseFill(GridPane pane) {
            controls.clear();
            for ( int k=0; k<2; ++k) {
                Node n = page.createNode();
                controls.add(n);
                GridPane.setConstraints(n, k, k);
            }
            pane.getChildren().addAll(controls);
            pane.setPrefSize(250, 250);
            pane.setGridLinesVisible(true);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;
        }
    }

    private class slotGridDefault extends slotGridTestNode { // default
        public slotGridDefault(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setGridLinesVisible(false);
            return pane;
        }
    }

    private class slotGridHVgap extends slotGridTestNode { // HVgap
        public slotGridHVgap(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setVgap(10);
            pane.setHgap(5);
            return pane;
        }
    }

    private class slotGridpadding extends slotGridTestNode { // padding
        public slotGridpadding(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setPadding(new Insets(15, 15, 15, 15));
            pane.setAlignment(Pos.TOP_CENTER);
            return pane;
        }
    }

    private class slotGridnodeVpos extends slotGridTestNode { // nodeVpos
        public slotGridnodeVpos(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.BOTTOM_CENTER);
            return pane;
        }
    }

    private class slotGridnodeHposRight extends slotGridTestNode { // nodeHposRight
        public slotGridnodeHposRight(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.CENTER_RIGHT);

            return pane;
        }
    }


    private class slotGridnodeHposTrailing extends slotGridTestNode { // nodeHposTrailing
        public slotGridnodeHposTrailing(ControlsFactory _page) {
            super(_page);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.CENTER_RIGHT);

            StackPane sp = new StackPane();
            sp.setPadding(new Insets(4,4,4,4));
            Rectangle b2 = new Rectangle(0, 0, 14, 16);
            b2.setFill(Color.GREEN);
            sp.getChildren().add(b2);

            GridPane.setRowIndex(sp, 0);
            GridPane.setColumnIndex(sp, 4);
            GridPane.setRowSpan(sp, 2);
            pane.getChildren().add(sp);

            return pane;
        }
    }

/*
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


/*
    private class slotBorderTestNode extends TestNode {
        BorderPane pane;
        ControlsFactory.ControlPage page;
        ObservableList<Node> controls = FXCollections.observableArrayList();

        public slotBorderTestNode(ControlsFactory.ControlPage _page) {
            page = _page;
        }

        protected BorderPane baseFill(BorderPane pane) {
            for ( int k=0; k<2; ++k) {
                controls.add(page.createNode());
            }
            pane.getChildren().addAll(controls);
            pane.setPrefSize(180, 190);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }

            return pane;
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

        protected FlowPane baseFill(FlowPane pane) {
            controls.clear();
            for ( int k=0; k<2; ++k) {
                controls.add(page.createNode());
            }
            pane.getChildren().addAll(controls);
            pane.setPrefSize(240, 240);
            pane.setMinSize(140, 140);
            //(new FillerWithAllNodes()).fill(pane);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;
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

/*
    private class slotTile extends TestNode {

        final RegionFiller filler;

        public slotTile(final RegionFiller a_filler) {
            filler = a_filler;
        }

        protected TilePane baseFill(TilePane pane) {
            filler.fill(pane);
            pane.setPrefSize(160, 160);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;

        }
    }

    private class slotTiledefaults extends slotTile { // defaults

        TilePane tilePane;

        public slotTiledefaults(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane());
            if (Orientation.VERTICAL == tilePane.getOrientation()) {
                reportGetterFailure("tilePane.isVertical()");
            }
            return tilePane;
        }
        @Override
        public void additionalAction() {
            tilePane.setPrefSize(90, 90);
            tilePane.setOrientation(Orientation.VERTICAL);
        }
    }

    private class slotTilegap extends slotTile { // gap

        TilePane tilePane;

        public slotTilegap(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane());
            tilePane.setHgap(5);
            if (5 != tilePane.getHgap()) {
                //TODO: we don't verify here that hgap is applied to graphic
                reportGetterFailure("tilePane.getHgap()");
            }

            tilePane.setVgap(20);
            if (20 != tilePane.getVgap()) {
                //TODO: we don't verify here that vgap is applied to graphic
                reportGetterFailure("tilePane.getVgap()");
            }

            return tilePane;
        }
        @Override
        public void additionalAction() {
            Node n1 = tilePane.getChildren().get(0);

            if (null != n1) {
                TilePane.setMargin(n1, new Insets(4,4,4,4));
            }
        }
    }

    private class slotTilegapCtor extends slotTile { // gapCtor

        TilePane tilePane;

        public slotTilegapCtor(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane(5, 15));
            return tilePane;
        }
    }

    private class slotTilegapCtor2 extends slotTile { // gapCtor2

        TilePane tilePane;

        public slotTilegapCtor2(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane(Orientation.VERTICAL, 5, 15));
            tilePane.setAlignment(Pos.TOP_LEFT);

            return tilePane;
        }
        @Override
        public void additionalAction() {
            tilePane.setHgap(30);
        }

    }

    private class slotTilepaddingVert extends slotTile { // paddingVert

        TilePane tilePane;

        public slotTilepaddingVert(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane());
            tilePane.setPadding(new Insets(15, 15, 15, 15));
            tilePane.setOrientation(Orientation.VERTICAL);

            tilePane.setAlignment(Pos.BOTTOM_LEFT);;
            if (Pos.BOTTOM_LEFT != tilePane.getAlignment()) {
                reportGetterFailure("tilePane.getVpos()");
            }

            tilePane.setPrefRows(2);// was:.setRows(2);
            if (2 != tilePane.getPrefRows()) {
                reportGetterFailure("tilePane.getRows()");
            }

            return tilePane;
        }
    }

    private class slotTileverticalCtor extends slotTile { // vertical Ctor

        TilePane tilePane;

        public slotTileverticalCtor(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane(Orientation.VERTICAL)); // true == vertical
            tilePane.setAlignment(Pos.TOP_LEFT);//.setHpos(HPos.LEADING);
            if (Pos.TOP_LEFT != tilePane.getAlignment()) {
                reportGetterFailure("tilePane.getAlignment()");
            }


            if (Orientation.VERTICAL != tilePane.getOrientation()) {
                reportGetterFailure("tilePane.isVertical() 2nd tst");
            }
            return tilePane;
        }
    }

    private class slotTileVpos extends slotTile { // Vpos

        TilePane tilePane;

        public slotTileVpos(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane());
            tilePane.setAlignment(Pos.BOTTOM_LEFT);
            return tilePane;
        }
    }

    private class slotTileHpos extends slotTile { // Hpos

        TilePane tilePane;

        public slotTileHpos(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane());
            tilePane.setAlignment(Pos.TOP_RIGHT);
            return tilePane;
        }
    }

    private class slotTilecenter extends slotTile { // center

        TilePane tilePane;

        public slotTilecenter(final RegionFiller a_filler) {
            super(a_filler);
        }

        @Override
        public Node drawNode() {
            tilePane = baseFill(new TilePane()); // true == vertical
            tilePane.setPrefColumns(3);
            tilePane.setAlignment(Pos.BOTTOM_CENTER);
            for (Node _n : tilePane.getChildren()) {
                TilePane.setAlignment(_n, Pos.BOTTOM_RIGHT);
            }

            return tilePane;
        }
        @Override
        public void additionalAction() {
        }
    }
*/

    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = 800;
        final int widthPageContentPane = 1000;

        // ======== VBOX =================
        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            final PageWithSlots vboxPage = new PageWithSlots(Pages.VBox.name()+page.name(), heightPageContentPane, widthPageContentPane);
            vboxPage.setSlotSize(220, 220);
            rootTestNode.add(vboxPage);

            vboxPage.add(new slotVboxControls(page), "default");
            vboxPage.add(new slotVboxcenter(page), "center");
            vboxPage.add(new slotVboxPadding(page), "padding");
            vboxPage.add(new slotVboxSpacing(page), "spacing");
            vboxPage.add(new slotVboxnodeHpos(page), "nodeHpos");

        }


        // ======== HBOX =================
        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            final PageWithSlots hboxPage = new PageWithSlots(Pages.HBox.name()+page.name(), heightPageContentPane, widthPageContentPane);
            hboxPage.setSlotSize(310, 310);
            rootTestNode.add(hboxPage);

            hboxPage.add(new slotHboxDefault(page), "default");
            hboxPage.add(new slotHboxAlignment1(page), "Alignment1");
            hboxPage.add(new slotHboxAlignment2(page), "Alignment2");
            hboxPage.add(new slotHboxpadding(page), "padding");
            hboxPage.add(new slotHboxspacing(page), "spacing");

        }

        // ============= BorderPane ====================
        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            final PageWithSlots borderPage = new PageWithSlots(Pages.BorderPane.name()+page.name(), heightPageContentPane, widthPageContentPane);
            borderPage.setSlotSize(310, 310);
            rootTestNode.add(borderPage);

            borderPage.add(new slotBorderDefault(page), "default");
            borderPage.add(new slotBorderAlignment(page), "Alignment");

        }
/*
        // ============= StackPane ====================
        final PageWithSlots stackPage = new PageWithSlots(Pages.StackPane.name(), heightPageContentPane, widthPageContentPane);
        stackPage.setSlotSize(140, 140);

        stackPage.add(new slotStackDefaults(), "defaults");
        stackPage.add(new slotStackPadding(), "padding");
*/
        // ============= GridPane ====================
        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            final PageWithSlots gridPage1 = new PageWithSlots(Pages.GridPanePart1.name()+page.name(), heightPageContentPane, widthPageContentPane);
            gridPage1.setSlotSize(310, 310);
            rootTestNode.add(gridPage1);

            gridPage1.add(new slotGridnodeVpos(page), "Vpos");
            gridPage1.add(new slotGridnodeHposRight(page), "HposRight");
            gridPage1.add(new slotGridnodeHposTrailing(page), "Trailing");
        }
        for (ControlsFactory page : ControlsFactory.filteredValues()) {
            final PageWithSlots gridPage2 = new PageWithSlots(Pages.GridPanePart2.name()+page.name(), heightPageContentPane, widthPageContentPane);
            gridPage2.setSlotSize(310, 310);
            rootTestNode.add(gridPage2);

            gridPage2.add(new slotGridDefault(page), "default");
            gridPage2.add(new slotGridHVgap(page), "HVgap");
            gridPage2.add(new slotGridpadding(page), "padding");
        }
/*


        // ============= AnchorPane ====================
        final PageWithSlots anchorPage = new PageWithSlots(Pages.AnchorPane.name(), heightPageContentPane, widthPageContentPane);
        anchorPage.setSlotSize(140, 140);

        anchorPage.add(new slotAnchorDefaults(),"defaults");

*/
        return rootTestNode;
    }

    public static void main(String args[]) {
        Utils.launch(ControlsLayoutPart1App.class, args);
    }
}
