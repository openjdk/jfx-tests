/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
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
 */
package test.scenegraph.app;

import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class Layout2App extends BasicButtonChooserApp {

    public static enum Pages {

        VBox, HBox, TileShortSet, TileLongSet, StackPane, GridPane, FlowPane,
        BorderPane, AnchorPane, GridPane2
    }
    public static Color nodeColors[] = {Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.BROWN, Color.MAGENTA};
    private static String IdBlueRegion = "BlueRegion";
    private static String IdGreenRegion = "GreenRegion";
    private static String IdTestButton1 = "IdTestButton1";
    private static String IdRedCircle = "RedCircle";
    private static int zOffset = 0;

    public Layout2App(int width, int height, String title, boolean showAdditionalActionButton) {
        super(width, height, title, showAdditionalActionButton);
    }

    public Layout2App() {
        super(800, 800, "Layout", true); // "true" stands for "additionalActionButton = "
    }

    public void setZOffset(int _offset) {
        zOffset = _offset;
    }
    private static int getZOffset() {
        return zOffset;
    }

    static private interface RegionFiller {

        Pane fill(Pane region);
    }
    private static final boolean withStyle = !Boolean.getBoolean("test.javaclient.jcovbackdoor");

    static Node mylookup(Pane _pane, String _s) {
        for (Node _n : _pane.getChildren()) {
            //System.out.println("compare ["+_s+"] and ["+_n.getId()+"]");
            if (_s.equals(_n.getId())) {
                //System.out.println("return ["+_s+"] , node: ["+_n+"]");
                return _n;
            }
        }
        //System.out.println("return null");
        return null;
    }

    /**
     * fill region with several possible child types:
     *  - empty region (resizeable)
     *  - non-empty region (resizeable)
     *  - circle shape (non-resizeable)
     */
    static private class FillerWithAllNodes implements RegionFiller {

        protected static Pane stFill(Pane region, boolean _withButton) {
            // resizeable node with child
            Pane resizeable = new Pane(); // was: Region x = new Region();
            if (withStyle) {
                resizeable.setStyle("-fx-border-color: blue;");
            }
            resizeable.setId(IdBlueRegion);

            resizeable.getChildren().add(new Circle(4));
            region.getChildren().add(resizeable);

            // resizeable node without child
            resizeable = new Pane();
            if (withStyle) {
                resizeable.setStyle("-fx-border-color: green;");
            }
            resizeable.setId(IdGreenRegion);
            region.getChildren().add(resizeable);

            if (_withButton) {
                Button b1 = new Button("btn");
                b1.setFont(new Font(14));
                b1.setMaxWidth(85);
                b1.setMaxHeight(85);
                b1.setId(IdTestButton1);
                b1.setMaxHeight(200); // 200
                b1.setMaxWidth(200);

                region.getChildren().add(b1);
            }

            FillerWithNonResizeableNodes.stFill(region);

            return region;
        }

        public Pane fill(Pane region) {
            boolean withButton = true;
            return stFill(region, withButton);
        }
    }

    static private class FillerWithAllNodesExceptButton extends FillerWithAllNodes {

        @Override
        public Pane fill(Pane region) {
            boolean withButton = false;
            return stFill(region, withButton);
        }
    }

    /**
     * fill region with non-resizeable childs:
     *  - circle shape (non-resizeable)
     */
    static private class FillerWithNonResizeableNodes implements RegionFiller {

        public static Pane stFill(Pane region) {
            for (int i = 0; i < nodeColors.length; ++i) {
                Circle nonresizeable = new Circle(5 + 3 * ((i) % 4)); //Node
                nonresizeable.setFill(nodeColors[i]);
                region.getChildren().add(nonresizeable);
                nonresizeable.setId("Circle"+nodeColors[i].toString());
                nonresizeable.setTranslateZ(i*getZOffset());

            }
            return region;
        }

        public Pane fill(Pane region) {
            return stFill(region);
        }
    }

    static private class FillerForGrid implements RegionFiller {

        public static Pane stFill(Pane pane) {
            GridPane gridPane = (GridPane) pane;
            for (int i = 1; i < nodeColors.length; ++i) {
                int k = i - 1;
                Circle nonresizeable = new Circle(5 + 3 * ((i) % 4)); //Node
                nonresizeable.setFill(nodeColors[i]);

                GridPane.setColumnIndex(nonresizeable, k % 4);
                GridPane.setRowIndex(nonresizeable, k < 4 ? 0 : 1);
                gridPane.getChildren().add(nonresizeable);
            }

            // add first circle
            Circle nonresizeable0 = new Circle(5 + 3 * ((0) % 4)); //Node
            nonresizeable0.setFill(nodeColors[0]);
            GridPane.setColumnIndex(nonresizeable0, (nodeColors.length - 1) % 4);
            GridPane.setRowIndex(nonresizeable0, (nodeColors.length - 1) < 4 ? 0 : 1);
            gridPane.getChildren().add(nonresizeable0);
            nonresizeable0.setId(IdRedCircle);

            StackPane sp = new StackPane();
            sp.setPadding(new Insets(4,4,4,4));
            Rectangle b2 = new Rectangle(0, 0, 40, 12);
            b2.setFill(Color.GREEN);
            sp.getChildren().add(b2);

            GridPane.setColumnIndex(sp, (nodeColors.length % 4));
            GridPane.setRowIndex(sp, (nodeColors.length < 4)? 0 : 1);
            gridPane.getChildren().add(sp);

            return gridPane;
        }

        public Pane fill(Pane region) {
            return stFill(region);
        }
    }

    // page with VBox layout
    private class slotVboxTestNode extends TestNode {

        protected VBox baseFill(VBox vbox) {
            (new FillerWithAllNodes()).fill(vbox);
            vbox.setPrefSize(80, 240);
            if (withStyle) {
                vbox.setStyle("-fx-border-color: darkgray;");
            }
            return vbox;
        }
    }

    private class slotVboxDefaults extends slotVboxTestNode {
        VBox vbox;
        Region n1;
        @Override
        public Node drawNode() {
            vbox = baseFill(new VBox());

            //Region n1 = (Region)vbox.lookup(IdBlueRegion);
            n1 = (Region)mylookup(vbox,IdBlueRegion);

            // here: first region is resized to it's maximum
            // in this test
            n1.setMaxWidth(10);
            n1.setMaxHeight(20);
            n1.setPrefHeight(10);

            vbox.getChildren().remove(n1);
            HBox hb = new HBox();
            vbox.getChildren().add(0, hb);
            hb.setAlignment(Pos.BOTTOM_RIGHT);
            //hb.setNodeVpos(VPos.BOTTOM);
            hb.getChildren().add(n1);

            // test Priority class
            Priority p[] = Priority.values();
            Priority p2 = Priority.max(p[0], p[1]);
            Priority p3 = Priority.min(p[0], p[1]);
            Priority p4 = Priority.valueOf("SOMETIMES");
            if (p2.equals(p3)) {
                reportGetterFailure("Priority.min/max");
            }
            if (p4 != Priority.SOMETIMES) {
                reportGetterFailure("Priority.valueOf()");
            }
            return vbox;
        }
        @Override
        public void additionalAction() {
            vbox.setPrefSize(70, 240);
            vbox.setSpacing(10);
            Button btn = (Button) mylookup(vbox, IdTestButton1);
            vbox.getChildren().remove(btn);
            vbox.getChildren().add(btn);
            btn.setText("!!!");

            n1.setMaxWidth(30);
            n1.setMaxHeight(20);
            n1.setPrefHeight(20);
        }
    }

    private class slotVboxnodeHpos extends slotVboxTestNode {
        VBox vbox;
        @Override
        public Node drawNode() {
            vbox = baseFill(new VBox());
            vbox.setAlignment(Pos.TOP_RIGHT);
            if (Pos.TOP_RIGHT != vbox.getAlignment()) {
                reportGetterFailure("vboxgetAlignment()");
            }
            return vbox;
        }
        @Override
        public void additionalAction() {
            vbox.setPrefSize(70, 240);
            vbox.setSpacing(10);
            Button btn = (Button) mylookup(vbox, IdTestButton1);
            vbox.getChildren().remove(btn);
            vbox.getChildren().add(btn);
            btn.setText("!!!");

            Region n1 = (Region)mylookup(vbox, IdBlueRegion);
            n1.setMaxWidth(30);
            n1.setMaxHeight(20);
            n1.setPrefHeight(20);
            vbox.getChildren().add(3, new Text(""));
        }
    }

    private class slotVboxcenter extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox());
            vbox.setAlignment(Pos.TOP_CENTER);
            return vbox;
        }
    }

    private class slotVboxFillwidth extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox());
            return vbox;
        }
    }

    private class slotVboxPadding extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox());
            vbox.setPadding(new Insets(15, 15, 15, 15));
            return vbox;
        }
    }

    private class slotVboxSpacingCtor extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox(5));
            if (5 != vbox.getSpacing()) {
                reportGetterFailure("vbox.getSpacing()");
            }

            return vbox;
        }
    }

    private class slotVboxSpacing extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox());
            vbox.setSpacing(5);
            if (5 != vbox.getSpacing()) {
                reportGetterFailure("vbox.getSpacing()");
            }

            return vbox;
        }
    }
    private class slotVboxSpacing2 extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox(10));
            //Region n1 = (Region)vbox.lookup(IdBlueRegion);
            Region n1 = (Region)mylookup(vbox,IdBlueRegion);

            n1.setMaxWidth(10);
            n1.setMaxHeight(20);
            n1.setPrefHeight(10);

            return vbox;
        }
    }
    private class slotVboxNodevbox extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox());
            return vbox;
        }
    }


    // page with HBox layout
    private class slotHboxTestNode extends TestNode {

        protected HBox baseFill(HBox hbox) {
            (new FillerWithAllNodes()).fill(hbox);
            hbox.setPrefSize(200, 70);
            if (withStyle) {
                hbox.setStyle("-fx-border-color: darkgray;");
            }
            return hbox;
        }
    }
    private class slotHboxDefault extends slotHboxTestNode {
        HBox hbox;

        @Override
        public Node drawNode() {
            hbox = baseFill(new HBox());
            return hbox;
        }
        @Override
        public void additionalAction() {
            Circle redCircle = (Circle) mylookup(hbox, "Circle"+nodeColors[2].toString());
            redCircle.setRadius(20);
            HBox.setMargin(redCircle, new Insets(5,5,5,5));

            Circle xCircle = (Circle) mylookup(hbox, "Circle"+nodeColors[3].toString());
            int iPosition = hbox.getChildren().indexOf(xCircle);
            hbox.getChildren().remove(xCircle);
            VBox vb = new VBox();
            hbox.getChildren().add(iPosition, vb);
            vb.setAlignment(Pos.BOTTOM_CENTER);
            vb.getChildren().add(xCircle);
        }
    }

    private class slotHboxVposTOPHposLeading extends slotHboxTestNode { // VposTOPHposLeading

        @Override
        public Node drawNode() {
            HBox hbox = baseFill(new HBox());
            hbox.setAlignment(Pos.TOP_LEFT);
            if (Pos.TOP_LEFT != hbox.getAlignment()) {
                reportGetterFailure("hbox.getNodeVpos()");
            }
            return hbox;
        }
    }

    private class slotHboxVposBOTTOMHposCenter extends slotHboxTestNode { // VposBOTTOMHposCenter

        @Override
        public Node drawNode() {
            HBox hbox = baseFill(new HBox());
            hbox.setAlignment(Pos.BASELINE_CENTER);

            return hbox;
        }
    }

    private class slotHboxpadding extends slotHboxTestNode { // padding,nodeVPos.CENTER

        @Override
        public Node drawNode() {
            HBox hbox = baseFill(new HBox());
            hbox.setPadding(new Insets(15, 15, 15, 15));
            hbox.setAlignment(Pos.CENTER_LEFT);

            return hbox;
        }
    }

    private class slotHboxspacing extends slotHboxTestNode { // spacing,nodeVpos.PAGE_END

        @Override
        public Node drawNode() {
            HBox hbox = baseFill(new HBox());
            hbox.setAlignment(Pos.BOTTOM_LEFT);
            hbox.setSpacing(5);
            if (5 != hbox.getSpacing()) {
                reportGetterFailure("hbox.getSpacing()");
            }

            return hbox;
        }
    }

    private class slotHboxspacingCtor extends slotHboxTestNode { // spacing Ctor

        @Override
        public Node drawNode() {
            HBox hbox = baseFill(new HBox(5));
            return hbox;
        }
    }

    private class slotHboxnodevpos extends slotHboxTestNode { // nodevpos

        @Override
        public Node drawNode() {
            HBox hbox = baseFill(new HBox());
            hbox.setAlignment(Pos.BOTTOM_LEFT);

            return hbox;
        }
    }

    private class slotBorder extends TestNode {

        protected BorderPane baseFill(BorderPane pane) {
            pane.setPrefSize(180, 190);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;
        }
    }
    private class slotBorderDefaults extends slotBorder { // defaults
        BorderPane borderPane;

        @Override
        public Node drawNode() {
            borderPane = baseFill(new BorderPane());

            Circle node1 = new Circle(5 + 3 * ((0) % 4));
            node1.setFill(nodeColors[0]);
            borderPane.setBottom(node1);

            Circle node2 = new Circle(5 + 3 * ((1) % 4));
            node2.setFill(nodeColors[1]);
            borderPane.setTop(node2);

            Circle node3 = new Circle(5 + 3 * ((2) % 4));
            node3.setFill(nodeColors[2]);
            borderPane.setLeft(node3);

            Circle node4 = new Circle(5 + 3 * ((3) % 4));
            node4.setFill(nodeColors[3]);
            borderPane.setRight(node4);

            Circle node5 = new Circle(5 + 3 * ((4) % 4));
            node5.setFill(nodeColors[4]);
            borderPane.setCenter(node5);

            return borderPane;
        }
        @Override
        public void additionalAction() {

            borderPane.setPrefSize(borderPane.getWidth() /2, borderPane.getHeight() /2);

        }

    }

    private class slotBorderDefaults2 extends slotBorder { // defaults2

        BorderPane borderPane;
        @Override
        public Node drawNode() {
            borderPane = baseFill(new BorderPane());

            Circle node1 = new Circle(5 + 3 * ((0) % 4));
            node1.setFill(nodeColors[0]);
            borderPane.setBottom(node1);
            if (node1 != borderPane.getBottom()) {
                reportGetterFailure("borderPane.getBottom()");
            }
            BorderPane.setAlignment(node1, Pos.TOP_CENTER);

            Circle node2 = new Circle(5 + 3 * ((1) % 4));
            node2.setFill(nodeColors[1]);
            borderPane.setTop(node2);
            if (node2 != borderPane.getTop()) {
                reportGetterFailure("borderPane.getTop()");
            }
            BorderPane.setAlignment(node2, Pos.TOP_CENTER);

            Circle node3 = new Circle(5 + 3 * ((2) % 4));
            node3.setFill(nodeColors[2]);
            borderPane.setLeft(node3);
            if (node3 != borderPane.getLeft()) {
                reportGetterFailure("borderPane.getLeft()");
            }
            BorderPane.setAlignment(node3, Pos.BOTTOM_RIGHT);

            Circle node4 = new Circle(5 + 3 * ((3) % 4));
            node4.setFill(nodeColors[3]);
            borderPane.setRight(node4);
            if (node4 != borderPane.getRight()) {
                reportGetterFailure("borderPane.getRight()");
            }
            BorderPane.setAlignment(node4, Pos.BOTTOM_LEFT);

            Circle node5 = new Circle(5 + 3 * ((4) % 4));
            node5.setFill(nodeColors[4]);
            borderPane.setCenter(node5);
            if (node5 != borderPane.getCenter()) {
                reportGetterFailure("borderPane.getCenter()");
            }
            BorderPane.setAlignment(node5, Pos.CENTER_RIGHT);

            return borderPane;
        }
    }

    private class slotBorderWayItWorks extends slotBorder { // wayItWorks

        BorderPane borderPane;
        Button bBottom = new Button("btm");
        Button bTop = new Button("top");
        Button bLeft = new Button("L");
        Button bRight = new Button("R");
        Button bCenter = new Button("c");

        @Override
        public Node drawNode() {
            borderPane = baseFill(new BorderPane());

            for(Button b : new Button[] {bBottom, bTop, bLeft, bRight, bCenter}) {
                b.setMaxSize(200,200);
            }

            BorderPane.clearConstraints(bTop);
            BorderPane.clearConstraints(bBottom);
            BorderPane.clearConstraints(bLeft);
            BorderPane.clearConstraints(bRight);
            BorderPane.clearConstraints(bCenter);

            borderPane.setBottom(bBottom);
            borderPane.setTop(bTop);
            borderPane.setLeft(bLeft);
            borderPane.setRight(bRight);
            borderPane.setCenter(bCenter);

            return borderPane;
        }
        @Override
        public void additionalAction() {

            BorderPane.setMargin(bBottom, new Insets(4,4,4,4));
            BorderPane.setMargin(bTop, new Insets(4,4,4,4));
            BorderPane.setMargin(bLeft, new Insets(4,4,4,4));
            BorderPane.setMargin(bRight, new Insets(4,4,4,4));
            BorderPane.setMargin(bCenter, new Insets(4,4,4,4));

        }
    }

    private class slotBorderchildWithLayout extends slotBorder {

        BorderPane borderPane;
        Button bLeft;

        @Override
        public Node drawNode() {
            borderPane = baseFill(new BorderPane());

            Button bBottom = new Button("btm");
            bBottom.setId(IdTestButton1);
            Button bTop = new Button("top");
            bTop.setId("top");
            bLeft = new Button("L");
            bLeft.setId("left");
            Button bRight = new Button("R");
            bRight.setId("right");
            Button bCenter = new Button("c");

            for (Button btn : new Button[] {bTop, bCenter, bLeft, bRight}) {
                btn.setMaxWidth(150); // 200
                btn.setMinWidth(50); // 200
                btn.setMaxHeight(70);
            }

            bBottom.setMaxWidth(200); // 200
            bBottom.setMaxHeight(200);
            bBottom.setMinHeight(50);

            borderPane.setBottom(bBottom);
            borderPane.setTop(bTop);
            borderPane.setLeft(bLeft);
            borderPane.setRight(bRight);
            borderPane.setCenter(bCenter);

            return borderPane;
        }

        @Override
        public void additionalAction() {
            Button btn = (Button) mylookup(borderPane, IdTestButton1);
            btn.setText("additionalAction");

            btn.setMaxWidth(200); // 200
            btn.setMaxHeight(200);
            btn.setMinHeight(5);

            Button btnTop = (Button) mylookup(borderPane, "top");
            btn.setText("additionalAction");

            btnTop.setMaxWidth(70); // 200
            btnTop.setMinWidth(70); // 200
            BorderPane.setAlignment(bLeft, Pos.BOTTOM_LEFT);
        }
    }

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
            Button btn = (Button) mylookup(stackPane, IdTestButton1);


            // TODO
            btn.setText("\n\n Yess!!!");
            btn.setMaxWidth(70);
            btn.setMaxHeight(40);
            double d1 = btn.getBoundsInLocal().getHeight();
            double d3 = btn.getLayoutY();
            double d4 = btn.getScaleY();
            double d2 = btn.getMaxHeight();
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

    private class slotStacknodeVpos extends slotStack { // nodeVpos

        StackPane stackPane;

        @Override
        public Node drawNode() {
            stackPane = baseFill(new StackPane());
            stackPane.setAlignment(Pos.BOTTOM_CENTER);

            return stackPane;
        }
        @Override
        public void additionalAction() {
            Circle cc2 = (Circle) mylookup(stackPane, "Circle"+nodeColors[2].toString());
            Circle cc3 = (Circle) mylookup(stackPane, "Circle"+nodeColors[3].toString());
            Circle cc4 = (Circle) mylookup(stackPane, "Circle"+nodeColors[4].toString());

            StackPane.setMargin(cc2, new Insets (20,20,20,20));
            StackPane.setMargin(cc3, new Insets (40,40,40,40));
            StackPane.setMargin(cc4, new Insets (10,50,10,50));

        }
    }

    private class slotStacknodeHposRight extends slotStack { // nodeHposRight

        StackPane stackPane;

        @Override
        public Node drawNode() {
            stackPane = baseFill(new StackPane());
            stackPane.setAlignment(Pos.CENTER_RIGHT);
            return stackPane;
        }
    }

    private class slotStacknodeHposLeft extends slotStack { // nodeHposLeft

        StackPane stackPane;

        @Override
        public Node drawNode() {
            stackPane = baseFill(new StackPane());
            stackPane.setAlignment(Pos.CENTER_LEFT);
            /*
            if (HPos.LEFT != stackPane.getNodeHpos()) {
                reportGetterFailure("stackPane.getNodeHpos()");
            }
             *
             */
            return stackPane;
        }
    }

    private class slotStacknodeHposTrailing extends slotStack { // nodeHposTrailing

        StackPane stackPane;

        @Override
        public Node drawNode() {
            stackPane = baseFill(new StackPane());
            stackPane.setAlignment(Pos.BOTTOM_RIGHT);
            /* .setNodeHpos(HPos.TRAILING); TODO
            HPos hpos = stackPane.getNodeHpos();
            if (hpos != HPos.TRAILING) {
                reportGetterFailure("getNodeHpos");
            }
             *
             */
            return stackPane;
        }
    }

    private class slotGrid extends TestNode {

        protected GridPane baseFill(GridPane pane) {
            pane.setPrefSize(130, 130);
            pane.setGridLinesVisible(true);
            FillerForGrid.stFill(pane);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;
        }
    }

    private class slotGriddefaults extends slotGrid { // defaults

        GridPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setGridLinesVisible(false);
            if (pane.isGridLinesVisible()) {
                reportGetterFailure("GridPane.setGridLinesVisible(false)");
            }
            return pane;
        }
    }

    private class slotGridHVgap extends slotGrid { // HVgap

        GridPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setVgap(10);
            if (10 != pane.getVgap()) {
                reportGetterFailure("GridPane.getVgap()");
            }
            pane.setHgap(5);
            if (5 != pane.getHgap()) {
                reportGetterFailure("GridPane.getHgap()");
            }
            return pane;
        }
    }

    private class slotGridpadding extends slotGrid { // padding

        GridPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setPadding(new Insets(15, 15, 15, 15));
            pane.setAlignment(Pos.TOP_CENTER);
            if (Pos.TOP_CENTER != pane.getAlignment()) {
                reportGetterFailure("GridPane.getAlignment()");
            }
            return pane;
        }
    }

    private class slotGridnodeVpos extends slotGrid { // nodeVpos

        GridPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.BOTTOM_CENTER);
            return pane;
        }
    }

    private class slotGridnodeHposRight extends slotGrid { // nodeHposRight

        GridPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.CENTER_RIGHT);

            /* todo
            pane.setNodeHpos(HPos.RIGHT);
            if (HPos.RIGHT != pane.getNodeHpos()) {
                reportGetterFailure("GridPane.getNodeHpos()");
            }
             *
             */
            return pane;
        }
    }

    private class slotGridnodeHposCenter extends slotGrid { // nodeHposCenter

        GridPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.CENTER);
            return pane;
        }
    }

    private class slotGridnodeHposTrailing extends slotGrid { // nodeHposTrailing

        GridPane pane;

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

    private class slotGridnodeColspan1 extends slotGrid { // nodeColspan1

        GridPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());

            StackPane sp = new StackPane();
            sp.setPadding(new Insets(4,4,4,4));
            Rectangle b2 = new Rectangle(0, 0, 60, 16);
            b2.setFill(Color.GREEN);
            sp.getChildren().add(b2);

            GridPane.setRowIndex(sp, 2);
            GridPane.setColumnIndex(sp, 2);
            GridPane.setColumnSpan(sp, 2);
            pane.getChildren().add(sp);

            pane.setAlignment(Pos.TOP_CENTER);
            return pane;
        }
    }

    private class slotGridnodeColspan2 extends slotGrid { // nodeColspan1

        private GridPane pane;

        @Override
        public Node drawNode() {
            pane = new GridPane();
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            pane.setGridLinesVisible(true);

            Circle c1 = new Circle(8);
            GridPane.setHalignment(c1,HPos.LEFT); //.LEADING);
            GridPane.setConstraints(c1, 0, 0);

            Circle c2 = new Circle(8);
            GridPane.setConstraints(c2, 0, 1);
            GridPane.setHalignment(c2,HPos.RIGHT); //.TRAILING

            Circle c3 = new Circle(8);
            GridPane.setConstraints(c3, 2, 2);
            GridPane.setHalignment(c3,HPos.RIGHT);
            GridPane.setValignment(c3,VPos.BOTTOM);

            StackPane sp = new StackPane();
            sp.setPadding(new Insets(4,4,4,4));
            Rectangle b2 = new Rectangle(0, 0, 60, 16);
            b2.setFill(Color.GREEN);
            sp.getChildren().add(b2);
            GridPane.setConstraints(sp, 1, 0,2,2);

            StackPane sp2 = new StackPane();
            sp2.setPadding(new Insets(4,4,4,4));
            Rectangle b2a = new Rectangle(0, 0, 16, 60);
            b2a.setFill(Color.RED);
            sp2.getChildren().add(b2a);
            GridPane.setMargin(sp2, new Insets(5,5,5,5));
            GridPane.setConstraints(sp2, 0, 0,1,3);

            Circle c4 = new Circle(8);
            GridPane.setConstraints(c4, 1, 2);
            GridPane.setValignment(c4,VPos.TOP);

            StackPane sp3 = new StackPane();
            Pane  p0 = new Pane();
            p0.setStyle("-fx-border-color: red;");
            sp3.setPadding(new Insets(2,2,2,2));
            sp3.getChildren().add(p0);
            Circle c5 = new Circle(8);
            sp3.getChildren().add(c5);
            GridPane.setMargin(sp3, new Insets(5,5,5,5));
            GridPane.setConstraints(sp3, 1, 3, 2,1, HPos.CENTER, VPos.BASELINE);

            StackPane sp4 = new StackPane();
            Pane  p1 = new Pane();
            p1.setStyle("-fx-border-color: blue;");
            sp4.setPadding(new Insets(2,2,2,2));
            sp4.getChildren().add(p1);
            Circle c6 = new Circle(8);
            sp4.getChildren().add(c6);
            GridPane.setConstraints(sp4, 0, 4, 2,1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES,Priority.SOMETIMES,new Insets(5,5,5,5));

            Circle c7 = new Circle(8);
            GridPane.setConstraints(c7, 2, 4);
            GridPane.setValignment(c7,VPos.CENTER);

            pane.getChildren().addAll(c1,c2,c3,c4, sp, sp2);
            pane.getChildren().add(sp3);
            pane.getChildren().add(sp4);
            pane.getChildren().add(c7);

            return pane;
        }
    }

    private class slotGridnodeColspan3 extends slotGrid { // nodeColspan1

        private GridPane pane;

        @Override
        public Node drawNode() {
            pane = new GridPane();
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            pane.setGridLinesVisible(true);

            pane.getRowConstraints().add(new RowConstraints() {
                {
                    setMinHeight(35);
                    this.setValignment(VPos.TOP);
                }
            });

            pane.getRowConstraints().add(new RowConstraints() {
                {
                    setMinHeight(35);
                    setValignment(VPos.BOTTOM);
                }
            });

            pane.getRowConstraints().add(new RowConstraints() {
                {
                    setMinHeight(35);
                    setValignment(VPos.BOTTOM);
                }
            });
            pane.getRowConstraints().add(new RowConstraints() {
                {
                    setMinHeight(35);
                    setValignment(VPos.BOTTOM);
                }
            });


            Circle c1 = new Circle(8);
            GridPane.setConstraints(c1, 0, 0);
            c1.setFill(Color.RED);

            Circle c2 = new Circle(8);
            GridPane.setConstraints(c2, 1, 0);
            c2.setFill(Color.BLUE);

            Circle c3 = new Circle(8);
            GridPane.setConstraints(c3, 2, 0);
            c3.setFill(Color.GREEN);

            StackPane sp2 = new StackPane();
            sp2.setPadding(new Insets(4, 4, 4, 4));
            Rectangle b2a = new Rectangle(0, 0, 16, 40);
            b2a.setFill(Color.RED);
            sp2.getChildren().add(b2a);
            GridPane.setMargin(sp2, new Insets(5, 5, 5, 5));
            GridPane.setConstraints(sp2, 3, 0, 2, 2);

            pane.getChildren().addAll(c1, c2, c3, sp2);

            return pane;
        }
    }

    private class slotGridColumnInfo extends slotGrid { // nodeColspan1

        private GridPane pane;

        @Override
        public Node drawNode() {
            pane = new GridPane();
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            pane.setGridLinesVisible(true);
            pane.getColumnConstraints().add(new ColumnConstraints(){{setMinWidth(25);this.setHalignment(HPos.CENTER);}});
            //pane.getColumnInfo().add(new GridColumnInfo(){{setMinWidth(25);this.setHalignment(HPos.CENTER);}});
            pane.getColumnConstraints().add(new ColumnConstraints(){{setMinWidth(25);setHalignment(HPos.LEFT);}});
            pane.getColumnConstraints().add(new ColumnConstraints(){{setMinWidth(25);setHalignment(HPos.RIGHT);}});
            pane.getColumnConstraints().add(new ColumnConstraints(){{setMinWidth(25);setHalignment(HPos.RIGHT);}});

            Circle c1 = new Circle(8);
            GridPane.setConstraints(c1, 0, 0);
            c1.setFill(Color.RED);



            Circle c2 = new Circle(8);
            GridPane.setConstraints(c2, 1, 0);
            c2.setFill(Color.BLUE);

            Circle c3 = new Circle(8);
            GridPane.setConstraints(c3, 2, 0);
            c3.setFill(Color.GREEN);

            StackPane sp2 = new StackPane();
            sp2.setPadding(new Insets(4,4,4,4));
            Rectangle b2a = new Rectangle(0, 0, 40, 16);
            b2a.setFill(Color.RED);
            sp2.getChildren().add(b2a);
            GridPane.setMargin(sp2, new Insets(5,5,5,5));
            GridPane.setConstraints(sp2, 0, 3,2,2);

            pane.getChildren().addAll(c1,c2,c3, sp2);

            return pane;
        }
    }

    private class slotGridPercent1 extends slotGrid { // nodeColspan1

        private GridPane pane;

        @Override
        public Node drawNode() {
            pane = new GridPane();
            if (withStyle) {
                pane.setStyle("-fx-border-color: blue;");
            }
            pane.setGridLinesVisible(true);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setPercentWidth(50);//.setWidthPercent(50);
            RowConstraints row1 = new RowConstraints();
            row1.setPercentHeight(50);
            pane.getColumnConstraints().addAll(column1);
            pane.getRowConstraints().add(row1);

            Circle c1 = new Circle(8);
            GridPane.setConstraints(c1, 0, 0);
            c1.setFill(Color.RED);

            Circle c2 = new Circle(8);
            GridPane.setConstraints(c2, 1, 1);
            c2.setFill(Color.BLUE);

            Circle c3 = new Circle(8);
            GridPane.setConstraints(c3, 2, 2);
            c3.setFill(Color.GREEN);
            pane.getChildren().addAll(c1,c2,c3);
/*
            StackPane sp2 = new StackPane();
            sp2.setPadding(new Insets(4,4,4,4));
            Rectangle b2a = new Rectangle(0, 0, 40, 16);
            b2a.setFill(Color.RED);
            sp2.getChildren().add(b2a);
            GridPane.setMargin(sp2, new Insets(5,5,5,5));
            GridPane.setConstraints(sp2, 0, 3,2,2);

            pane.getChildren().addAll(sp2);
*/

            return pane;
        }
    }


    private class slotGridRowConstraint1 extends slotGrid {

        private GridPane pane;

        @Override
        public Node drawNode() {
            pane = new GridPane();
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            pane.setGridLinesVisible(true);

            pane.getRowConstraints().add(new RowConstraints(40) );
            pane.getRowConstraints().add(new RowConstraints(30,40,60) );
            pane.getRowConstraints().add(new RowConstraints(30,40,60, Priority.ALWAYS, VPos.BOTTOM, true) );

            Circle c1 = new Circle(8);
            GridPane.setConstraints(c1, 0, 0);
            c1.setFill(Color.RED);

            Circle c2 = new Circle(8);
            GridPane.setConstraints(c2, 1, 0);
            c2.setFill(Color.BLUE);

            Circle c3 = new Circle(8);
            GridPane.setConstraints(c3, 2, 0);
            c3.setFill(Color.GREEN);

            Circle c3a = new Circle(8);
            GridPane.setConstraints(c3a, 1, 1);
            c3a.setFill(Color.GREEN);

            Circle c3b = new Circle(8);
            GridPane.setConstraints(c3b, 2, 2);
            c3b.setFill(Color.GREEN);

            pane.getChildren().addAll( c3a, c3b);

            StackPane sp2 = new StackPane();
            sp2.setPadding(new Insets(4, 4, 4, 4));
            Rectangle b2a = new Rectangle(0, 0, 16, 40);
            b2a.setFill(Color.RED);
            sp2.getChildren().add(b2a);
            GridPane.setMargin(sp2, new Insets(5, 5, 5, 5));
            GridPane.setConstraints(sp2, 3, 0, 2, 2);

            pane.getChildren().addAll(c1, c2, c3, sp2);

            return pane;
        }
    }

    private class slotGridRowConstraint2 extends slotGrid {

        private GridPane pane;

        @Override
        public Node drawNode() {
            pane = new GridPane();
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            pane.setPrefSize(130, 130);

            pane.setGridLinesVisible(false);

            RowConstraints rc1 = new RowConstraints();
            rc1.setFillHeight(false);
            pane.getRowConstraints().add(rc1);

            Circle c1 = new Circle(8);
            GridPane.setConstraints(c1, 0, 0);
            c1.setFill(Color.RED);


            StackPane sp2 = new StackPane();
            sp2.setPadding(new Insets(2, 2, 2, 2));
            sp2.setPrefSize(10,10);
            sp2.setMaxSize(50,50);
            sp2.setStyle("-fx-border-color: red;");
            GridPane.setHalignment(sp2,HPos.CENTER);
            GridPane.setValignment(sp2,VPos.CENTER);


            Rectangle r1 = new Rectangle(12,110);
            GridPane.setConstraints(r1, 1, 0);
            r1.setFill(Color.BLUE);

            Rectangle r2 = new Rectangle(110,12);
            GridPane.setConstraints(r2, 0, 1);
            r2.setFill(Color.GREEN);

            pane.getChildren().addAll(sp2, r1, r2);

            return pane;
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

    private class slotAnchor2 extends slotAnchor {

        AnchorPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new AnchorPane());
            Button btn = (Button) mylookup(pane, IdTestButton1);
            Circle cc2 = (Circle) mylookup(pane, "Circle"+nodeColors[2].toString());
            Circle cc3 = (Circle) mylookup(pane, "Circle"+nodeColors[3].toString());
            Circle cc4 = (Circle) mylookup(pane, "Circle"+nodeColors[4].toString());

            AnchorPane.setBottomAnchor(btn, 20d);
            AnchorPane.setTopAnchor(cc2, 10d);
            AnchorPane.setLeftAnchor(cc3, 10d);
            AnchorPane.setRightAnchor(cc4, 20d);

            if (20d != AnchorPane.getBottomAnchor(btn)) {
                reportGetterFailure("AnchorPane.getBottomAnchor()");
            }
            if (10d != AnchorPane.getTopAnchor(cc2)) {
                reportGetterFailure("AnchorPane.getTopAnchor()");
            }
            if (10d != AnchorPane.getLeftAnchor(cc3)) {
                reportGetterFailure("AnchorPane.getLeftAnchor()");
            }
            if (20d != AnchorPane.getRightAnchor(cc4)) {
                reportGetterFailure("AnchorPane.getRightAnchor()");
            }

            AnchorPane.clearConstraints(btn);
            AnchorPane.clearConstraints(cc2);
            AnchorPane.clearConstraints(cc3);
            AnchorPane.clearConstraints(cc4);

            if (null != AnchorPane.getBottomAnchor(btn)) {
                reportGetterFailure("AnchorPane.clearConstraints()");
            }
            if (null != AnchorPane.getTopAnchor(cc2)) {
                reportGetterFailure("AnchorPane.clearConstraints()");
            }
            if (null != AnchorPane.getLeftAnchor(cc3)) {
                reportGetterFailure("AnchorPane.clearConstraints()");
            }
            if (null != AnchorPane.getRightAnchor(cc4)) {
                reportGetterFailure("AnchorPane.clearConstraints()");
            }
            AnchorPane.setBottomAnchor(btn, 20d);
            AnchorPane.setTopAnchor(cc2, 10d);
            AnchorPane.setLeftAnchor(cc3, 10d);
            AnchorPane.setRightAnchor(cc4, 20d);

            VBox vbox = new VBox();
            vbox.getChildren().add(pane);
            return vbox;
        }
        @Override
        public void additionalAction() {
            pane.setPrefSize(70, 150);
            Button btn = (Button) mylookup(pane, IdTestButton1);
            pane.getChildren().remove(btn);
            pane.getChildren().add(btn);
            btn.setManaged(false);
        }
    }

    private class slotFlow extends TestNode {

        protected FlowPane baseFill(FlowPane pane) {
            pane.setPrefSize(130, 130);
            (new FillerWithAllNodes()).fill(pane);
            if (withStyle) {
                pane.setStyle("-fx-border-color: darkgray;");
            }
            return pane;
        }
    }

    private class slotFlowdefaults extends slotFlow { // defaults

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            return pane;
        }
    }

    private class slotFlowwrapLen extends slotFlow { // wrapLen

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setPrefWrapLength(10);
            if (10 != pane.getPrefWrapLength()) {
                reportGetterFailure("FlowPane.getWrapLength()");
            }
            return pane;
        }
    }

    private class slotFlowpadding extends slotFlow { // padding

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setPadding(new Insets(15, 15, 15, 15));
            return pane;
        }
    }

    private class slotFlownodeVposBottom extends slotFlow { // nodeVposBottom

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setRowValignment(VPos.BOTTOM);
            return pane;
        }
    }

    private class slotFlownodeVposTop extends slotFlow { // nodeVposTop

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setRowValignment(VPos.TOP);
            return pane;
        }
    }

    private class slotFlowGap5 extends slotFlow { // Gap5

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setHgap(10);
            if (pane.getHgap() != 10) {
                reportGetterFailure("FlowPane.getHgap()");
            }

            pane.setColumnHalignment(HPos.LEFT);
            pane.setRowValignment(VPos.TOP);
            pane.setAlignment(Pos.BOTTOM_LEFT);//.setVpos(VPos.BOTTOM);
            if (pane.getAlignment() != Pos.BOTTOM_LEFT) {
                reportGetterFailure("FlowPane.getAlignment()");
            }

            return pane;
        }
    }

    private class slotFlownodeHposRight extends slotFlow { // nodeHposRight

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setAlignment(Pos.TOP_CENTER);
            if (pane.getAlignment() != Pos.TOP_CENTER) {
                reportGetterFailure("FlowPane.getAlignment()");
            }
            return pane;
        }
    }

    private class slotFlownodeHposRight2 extends slotFlow { // nodeHposRight2

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane());
            pane.setAlignment(Pos.TOP_RIGHT);
            return pane;
        }
    }

    private class slotFlowvertical_1 extends slotFlow { // vertical_1

        FlowPane pane;

        @Override
        public Node drawNode() {
            for (Orientation or : Orientation.values()) {
                if (Orientation.HORIZONTAL != or && Orientation.VERTICAL != or ) {
                    reportGetterFailure("Orientation.values()");
                }
                if (Orientation.valueOf(or.name().toString()) != or) {
                    reportGetterFailure("Orientation.valueOf()");
                }
            }
            pane = baseFill(new FlowPane(Orientation.VERTICAL));  // "true" here stands for setVertical(true)
            pane.setVgap(20);
            if (pane.getVgap() != 20) {
                reportGetterFailure("FlowPane.getVgap()");
            }
            pane.setAlignment(Pos.valueOf(Pos.BOTTOM_LEFT.toString()));
            pane.setRowValignment(VPos.valueOf(VPos.BOTTOM.toString()));
            pane.setColumnHalignment(HPos.valueOf(HPos.LEFT.toString()));
            /* todo
            pane.setVpos(VPos.BOTTOM);
            pane.setNodeVpos(VPos.TOP);
            if (false == pane.isVertical()) {
                reportGetterFailure("FlowPane.isVertical()");
            }
             *
             */
            return pane;
        }
    }

    private class slotFlowvertical_2 extends slotFlow { // vertical_2

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane(Orientation.VERTICAL, 10, 20));  // "true" here stands for setVertical(true)
            pane.setOrientation(Orientation.VERTICAL);
            pane.setColumnHalignment(HPos.RIGHT);
            /* todo
            pane.setVpos(VPos.BASELINE);
            pane.setNodeHpos(HPos.RIGHT);
            if (pane.getNodeHpos() != HPos.RIGHT) {
                reportGetterFailure("FlowPane.getNodeHpos()");
            }
             *
             */
            return pane;
        }
    }

    private class slotFlowhgapvgapCtor extends slotFlow { // hgap-vgap Ctor

        FlowPane pane;

        @Override
        public Node drawNode() {
            pane = baseFill(new FlowPane(10, 20));
            pane.setOrientation(Orientation.VERTICAL);
            pane.setAlignment(Pos.CENTER_LEFT);
            pane.setRowValignment(VPos.BOTTOM);
            pane.setColumnHalignment(HPos.RIGHT);
            return pane;
        }
    }

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
            Circle cc2 = (Circle) mylookup(tilePane, "Circle"+nodeColors[2].toString());
            Circle cc3 = (Circle) mylookup(tilePane, "Circle"+nodeColors[3].toString());
            Button btn = (Button) mylookup(tilePane, IdTestButton1);

            tilePane.getChildren().remove(cc2);
            tilePane.getChildren().remove(cc3);
            tilePane.getChildren().remove(btn);
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
            Region n1 = (Region) mylookup(tilePane, IdBlueRegion);
            Region n2 = (Region) mylookup(tilePane, IdGreenRegion);
            Button btn = (Button) mylookup(tilePane, IdTestButton1);

            if (null != n1 && null !=n2 && null!= btn) {
                TilePane.setMargin(n1, new Insets(4,4,4,4));
                TilePane.setMargin(n2, new Insets(0,-4,-4,-4));
                tilePane.getChildren().remove(btn);
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
            /* todo
            tilePane.setNodeHpos(HPos.RIGHT);
            if (HPos.RIGHT != tilePane.getNodeHpos()) {
                reportGetterFailure("tilePane.getNodeHpos()");
            }
            tilePane.setNodeVpos(VPos.BOTTOM);
            if (VPos.BOTTOM != tilePane.getNodeVpos()) {
                reportGetterFailure("tilePane.getNodeVpos()");
            }
            */
            tilePane.setPrefColumns(3); // was: .setColumns(3);
            if (3 != tilePane.getPrefColumns()) {
                reportGetterFailure("tilePane.getColumns()");
            }

            return tilePane;
        }
        @Override
        public void additionalAction() {
            TestAsRegion(tilePane);
        }
    }

    void TestAsRegion(Region region) {
        double rHeight = region.getHeight();
        double rWidth = region.getWidth();
        boolean isResizeable = region.isResizable();
        if (isResizeable) {
            region.resize(rWidth-20, rHeight-20);
        }

        Insets insets = region.getPadding();
        region.setPadding(insets);

        boolean isSnapToPixel = region.isSnapToPixel();
        region.setSnapToPixel(isSnapToPixel);
    }

    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        //Button bbb = new Button("button1");
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = 800;
        final int widthPageContentPane = 800;

        // ======== VBOX =================
        final PageWithSlots vboxPage = new PageWithSlots(Pages.VBox.name(), heightPageContentPane, widthPageContentPane);
        vboxPage.setSlotSize(240, 180);

        vboxPage.add(new slotVboxDefaults(), "defaults");
        vboxPage.add(new slotVboxnodeHpos(), "nodeHpos");
        vboxPage.add(new slotVboxcenter(), "center");
        vboxPage.add(new slotVboxFillwidth(), "fillwidth");
        vboxPage.add(new slotVboxPadding(), "padding");
        vboxPage.add(new slotVboxSpacingCtor(), "spacing_Ctor");
        vboxPage.add(new slotVboxSpacing(), "spacing");
        vboxPage.add(new slotVboxSpacing2(), "spacing2");
        vboxPage.add(new slotVboxNodevbox(), "nodevpos");

        // ======== HBOX =================
        final PageWithSlots hboxPage = new PageWithSlots(Pages.HBox.name(), heightPageContentPane, widthPageContentPane);
        hboxPage.setSlotSize(210, 210);

        hboxPage.add(new slotHboxDefault(), "defaults");
        hboxPage.add(new slotHboxVposTOPHposLeading(), "VposTOPHposLeading");
        hboxPage.add(new slotHboxVposBOTTOMHposCenter(), "VposBOTTOMHposCenter");
        hboxPage.add(new slotHboxpadding(), "padding,nodeVPos.CENTER");
        hboxPage.add(new slotHboxspacing(), "spacing,nodeVpos.PAGE_END");
        hboxPage.add(new slotHboxspacingCtor(), "spacing_Ctor");
        hboxPage.add(new slotHboxnodevpos(), "nodevpos");

        // ============= BorderPane ====================
        final PageWithSlots borderPage = new PageWithSlots(Pages.BorderPane.name(), heightPageContentPane, widthPageContentPane);
        borderPage.setSlotSize(190, 190);

        borderPage.add(new slotBorderDefaults(), "defaults");
        borderPage.add(new slotBorderDefaults2(), "defaults2");
        borderPage.add(new slotBorderWayItWorks(), "wayItWorks");
        borderPage.add(new slotBorderchildWithLayout(), "childWithLayout");

        // ============= StackPane ====================
        final PageWithSlots stackPage = new PageWithSlots(Pages.StackPane.name(), heightPageContentPane, widthPageContentPane);
        stackPage.setSlotSize(140, 140);

        stackPage.add(new slotStackDefaults(), "defaults");
        stackPage.add(new slotStackPadding(), "padding");
        stackPage.add(new slotStacknodeVpos(), "nodeVpos");
        stackPage.add(new slotStacknodeHposRight(), "nodeHposRight");
        stackPage.add(new slotStacknodeHposLeft(), "nodeHposLeft");
        stackPage.add(new slotStacknodeHposTrailing(), "nodeHposTrailing");

        // ============= GridPane ====================
        final PageWithSlots gridPage = new PageWithSlots(Pages.GridPane.name(), heightPageContentPane, widthPageContentPane);
        gridPage.setSlotSize(160, 160);

        gridPage.add(new slotGriddefaults(), "defaults");
        gridPage.add(new slotGridHVgap(), "HVgap");
        gridPage.add(new slotGridpadding(), "padding");
        gridPage.add(new slotGridnodeVpos(), "nodeVpos");

        gridPage.add(new slotGridnodeHposRight(), "nodeHposRight");
        gridPage.add(new slotGridnodeHposCenter(), "nodeHposCenter");
        gridPage.add(new slotGridnodeHposTrailing(), "nodeHposTrailing");
        gridPage.add(new slotGridnodeColspan1(), "nodeColspan1");
        gridPage.add(new slotGridnodeColspan2(), "nodeColspan2");
        gridPage.add(new slotGridnodeColspan3(), "nodeColspan3");
        gridPage.add(new slotGridColumnInfo(), "GridColumnInfo");
        gridPage.add(new slotGridPercent1(), "GridPercent1");

        // ============= GridPane page2 ====================
        final PageWithSlots gridPage2 = new PageWithSlots(Pages.GridPane2.name(), heightPageContentPane, widthPageContentPane);
        gridPage2.setSlotSize(160, 160);

        gridPage2.add(new slotGridRowConstraint1(), "RowConstraint1");
        gridPage2.add(new slotGridRowConstraint2(), "RowConstraint2");

        // ============= FlowPane ====================
        final PageWithSlots flowPage = new PageWithSlots(Pages.FlowPane.name(), heightPageContentPane, widthPageContentPane);
        flowPage.setSlotSize(150, 150);

        flowPage.add(new slotFlowdefaults(), "defaults");
        flowPage.add(new slotFlowwrapLen(), "wrapLen");
        flowPage.add(new slotFlowpadding(), "padding");
        flowPage.add(new slotFlownodeVposBottom(), "nodeVposBottom");
        flowPage.add(new slotFlownodeVposTop(), "nodeVposTop");

        flowPage.add(new slotFlowGap5(), "Gap5");
        flowPage.add(new slotFlownodeHposRight(), " nodeHposRight");
        flowPage.add(new slotFlownodeHposRight2(), " nodeHposRight2");
        flowPage.add(new slotFlowvertical_1(), "vertical_1");
        flowPage.add(new slotFlowvertical_2(), "vertical_2");

        flowPage.add(new slotFlowhgapvgapCtor(), "hgap-vgap_Ctor");

        // ============= TilePane with long set of childs ====================
        final PageWithSlots tilePageLong = new PageWithSlots(Pages.TileLongSet.name(), heightPageContentPane, widthPageContentPane);
        tilePageLong.setSlotSize(170, 170);
        tilePageLong.setYGap(40);

        tilePageLong.add(new slotTiledefaults(new FillerWithAllNodes()), "defaults");
        tilePageLong.add(new slotTilegap(new FillerWithAllNodes()), "gap");
        tilePageLong.add(new slotTilegapCtor(new FillerWithAllNodes()), "gapCtor");
        tilePageLong.add(new slotTilegapCtor2(new FillerWithAllNodes()), "gapCtor2");

        tilePageLong.add(new slotTilepaddingVert(new FillerWithAllNodes()), "paddingVert");
        tilePageLong.add(new slotTileverticalCtor(new FillerWithAllNodes()), "vertical_Ctor");
        tilePageLong.add(new slotTileVpos(new FillerWithAllNodes()), "Vpos");
        tilePageLong.add(new slotTileHpos(new FillerWithAllNodes()), "Hpos");
        tilePageLong.add(new slotTilecenter(new FillerWithAllNodes()), "center");
        // ============= TilePane with short set of childs ====================
        final PageWithSlots tilePageShort = new PageWithSlots(Pages.TileShortSet.name(), heightPageContentPane, widthPageContentPane);
        tilePageShort.setSlotSize(170, 170);
        tilePageShort.setYGap(40);

        tilePageShort.add(new slotTiledefaults(new FillerWithNonResizeableNodes()), "defaults");
        tilePageShort.add(new slotTilegap(new FillerWithNonResizeableNodes()), "gap");
        tilePageShort.add(new slotTilegapCtor(new FillerWithNonResizeableNodes()), "gapCtor");
        tilePageShort.add(new slotTilegapCtor2(new FillerWithNonResizeableNodes()), "gapCtor2");

        tilePageShort.add(new slotTilepaddingVert(new FillerWithNonResizeableNodes()), "paddingVert");
        tilePageShort.add(new slotTileverticalCtor(new FillerWithNonResizeableNodes()), "vertical_Ctor");
        tilePageShort.add(new slotTileVpos(new FillerWithNonResizeableNodes()), "Vpos");
        tilePageShort.add(new slotTileHpos(new FillerWithNonResizeableNodes()), "Hpos");
        tilePageShort.add(new slotTilecenter(new FillerWithNonResizeableNodes()), "center");

        // ============= AnchorPane ====================
        final PageWithSlots anchorPage = new PageWithSlots(Pages.AnchorPane.name(), heightPageContentPane, widthPageContentPane);
        anchorPage.setSlotSize(140, 140);

        anchorPage.add(new slotAnchorDefaults(),"defaults");
        anchorPage.add(new slotAnchor2(),"anchors");


        // ========= root tests list ==============
        rootTestNode.add(vboxPage);
        rootTestNode.add(hboxPage);
        rootTestNode.add(borderPage);
        rootTestNode.add(stackPage);
        rootTestNode.add(gridPage);
        rootTestNode.add(gridPage2);
        rootTestNode.add(flowPage);
        rootTestNode.add(tilePageLong);
        rootTestNode.add(tilePageShort);
        rootTestNode.add(anchorPage);
        return rootTestNode;
    }

    public static void main(String args[]) {
        Utils.launch(Layout2App.class, args);
    }

}
