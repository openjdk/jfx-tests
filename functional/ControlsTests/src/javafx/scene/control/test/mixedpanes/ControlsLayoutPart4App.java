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
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 *
 * @author shubov
 */
public class ControlsLayoutPart4App extends BasicButtonChooserApp {

    public static enum Pages {

        VBox, HBox,  TilePane, StackPane, GridPane, FlowPane,
        BorderPane, AnchorPane
    }

    public ControlsLayoutPart4App() {
        super(1000, 800, "ControlLayoutPart4", true); // "true" stands for "additionalActionButton = "
    }
    private static final boolean withStyle = !Boolean.getBoolean("test.javaclient.jcovbackdoor");

    // page with VBox layout
    private class slotVboxTestNode extends TestNode {
        VBox pane;
        List<ControlsFactory> controlsToUse = new ArrayList();
        ObservableList<Node> controlNodes = FXCollections.observableArrayList();

        public slotVboxTestNode(List<ControlsFactory> _controlsToUse) {
            controlsToUse = _controlsToUse;
        }

        protected VBox baseFill(VBox vbox) {
            controlNodes.clear();
            for (ControlsFactory controlToBeCreated : controlsToUse) {
                controlNodes.add(controlToBeCreated.createNode());
            }
            vbox.getChildren().addAll(controlNodes);
            vbox.setPrefSize(310, 310);
            if (withStyle) {
                vbox.setStyle("-fx-border-color: darkgray;");
            }
            return vbox;
        }

    }


    private class slotVboxControls extends slotVboxTestNode {
        public slotVboxControls(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }
        @Override
        public Node drawNode() {
            pane = baseFill(new VBox());
            return pane;
        }
        @Override
        public void additionalAction() {
            pane.setPrefSize(70, 240);
            pane.setSpacing(10);
        }
    }


    private class slotVboxnodeHpos extends slotVboxTestNode {
        public slotVboxnodeHpos(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }
        @Override
        public Node drawNode() {
            pane = baseFill(new VBox());
            pane.setAlignment(Pos.TOP_RIGHT);
            return pane;
        }
        @Override
        public void additionalAction() {
            //tv1.getColumns().get(0).setVisible(false);
            //tv1.getColumns().get(1).setMaxWidth(40);
            //tv1.getColumns().get(1).setText("done");

            pane.setPrefSize(40, 240);
            pane.setSpacing(10);

        }
    }

    private class slotVboxcenter extends slotVboxTestNode {
        public slotVboxcenter(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new VBox());
            pane.setAlignment(Pos.TOP_CENTER);
            return pane;
        }
        @Override
        public void additionalAction() {
            //tv1.setPlaceholder(new Circle(8));
            //tv1.setItems(data3);

        }
    }

    private class slotVboxPadding extends slotVboxTestNode {
        public slotVboxPadding(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new VBox());
            pane.setPadding(new Insets(15, 15, 15, 15));
            return pane;
        }
    }


    private class slotVboxSpacing extends slotVboxTestNode {
        public slotVboxSpacing(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new VBox());
            pane.setSpacing(5);

            return pane;
        }
    }

    // page with HBox layout
    private class slotHboxTestNode extends TestNode {
        HBox pane;
        List<ControlsFactory> controlsToUse = new ArrayList();
        ObservableList<Node> controlNodes = FXCollections.observableArrayList();

        public slotHboxTestNode(List<ControlsFactory> _controlsToUse) {
            controlsToUse = _controlsToUse;
        }

        protected HBox baseFill(HBox hbox) {
            controlNodes.clear();
            for (ControlsFactory controlToBeCreated : controlsToUse) {
                controlNodes.add(controlToBeCreated.createNode());
            }
            hbox.getChildren().addAll(controlNodes);
            hbox.setPrefSize(450, 140);
            if (withStyle) {
                hbox.setStyle("-fx-border-color: darkgray;");
            }
            return hbox;
        }
    }


    private class slotHboxDefault extends slotHboxTestNode {
        public slotHboxDefault(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new HBox());
            return pane;
        }
        @Override
        public void additionalAction() {
            pane.setAlignment(Pos.CENTER);
        }
    }

    private class slotHboxAlignment1 extends slotHboxTestNode {
        public slotHboxAlignment1(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new HBox());
            pane.setAlignment(Pos.TOP_LEFT);
            return pane;
        }
    }

    private class slotHboxAlignment2 extends slotHboxTestNode {
        public slotHboxAlignment2(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new HBox());
            pane.setAlignment(Pos.BASELINE_CENTER);

            return pane;
        }
    }

    private class slotHboxpadding extends slotHboxTestNode { // padding,nodeVPos.CENTER
        public slotHboxpadding(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new HBox());
            pane.setPadding(new Insets(15, 15, 15, 15));
            pane.setAlignment(Pos.CENTER_LEFT);

            return pane;
        }
    }

    private class slotHboxspacing extends slotHboxTestNode {
        public slotHboxspacing(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new HBox());
            pane.setAlignment(Pos.BOTTOM_LEFT);
            pane.setSpacing(5);
            return pane;
        }
    }

    private class slotBorderTestNode extends TestNode {
        BorderPane pane;
        List<ControlsFactory> controlsToUse = new ArrayList();
        ObservableList<Node> controlNodes = FXCollections.observableArrayList();

        public slotBorderTestNode(List<ControlsFactory> _controlsToUse) {
            controlsToUse = _controlsToUse;
        }

        protected BorderPane baseFill(BorderPane borderPane) {
            controlNodes.clear();
            for (ControlsFactory controlToBeCreated : controlsToUse) {
                controlNodes.add(controlToBeCreated.createNode());
            }
            borderPane.getChildren().addAll(controlNodes);
            borderPane.setPrefSize(310, 310);
            if (withStyle) {
                borderPane.setStyle("-fx-border-color: darkgray;");
            }

            return borderPane;
        }
    }

    private class slotBorderDefault extends slotBorderTestNode {
        public slotBorderDefault(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new BorderPane());
            pane.getChildren().clear();
            pane.setBottom(controlNodes.get(0));
            pane.setTop(controlNodes.get(1));
            return pane;
        }
        @Override
        public void additionalAction() {

            pane.setPrefSize(pane.getWidth() /2, pane.getHeight() /2);

        }

    }

    private class slotBorderAlignment extends slotBorderTestNode {
        public slotBorderAlignment(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new BorderPane());
            pane.getChildren().clear();

            pane.setRight(controlNodes.get(0));
            BorderPane.setAlignment(controlNodes.get(0), Pos.BOTTOM_RIGHT);

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
        List<ControlsFactory> controlsToUse = new ArrayList();
        ObservableList<Node> controlNodes = FXCollections.observableArrayList();

        public slotGridTestNode(List<ControlsFactory> _controlsToUse) {
            controlsToUse = _controlsToUse;
        }
        int locX[] = {0,1,0,1,0,1};
        int locY[] = {0,0,1,1,2,2};

        protected GridPane baseFill(GridPane gridPane) {
            controlNodes.clear();
            int k=0;
            for (ControlsFactory controlToBeCreated : controlsToUse) {
                Node n = controlToBeCreated.createNode();
                controlNodes.add(n);
                GridPane.setConstraints(n, locX[k], locY[k]);
                ++k;
            }
            gridPane.getChildren().addAll(controlNodes);

            gridPane.setPrefSize(310, 310);
            gridPane.setGridLinesVisible(true);
            if (withStyle) {
                gridPane.setStyle("-fx-border-color: darkgray;");
            }
            return gridPane;
        }
    }

    private class slotGridDefault extends slotGridTestNode { // default
        public slotGridDefault(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setGridLinesVisible(false);
            return pane;
        }
    }

    private class slotGridHVgap extends slotGridTestNode { // HVgap
        public slotGridHVgap(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
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
        public slotGridpadding(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
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
        public slotGridnodeVpos(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.BOTTOM_CENTER);
            return pane;
        }
    }

    private class slotGridnodeHposRight extends slotGridTestNode { // nodeHposRight
        public slotGridnodeHposRight(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
        }

        @Override
        public Node drawNode() {
            pane = baseFill(new GridPane());
            pane.setAlignment(Pos.CENTER_RIGHT);

            return pane;
        }
    }


    private class slotGridnodeHposTrailing extends slotGridTestNode { // nodeHposTrailing
        public slotGridnodeHposTrailing(List<ControlsFactory> _controlsToUse) {
            super(_controlsToUse);
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



    public TestNode setup() {
        System.out.println("java.library.path=" + System.getProperty("java.library.path"));
        TestNode rootTestNode = new TestNode();

        final int heightPageContentPane = 800;
        final int widthPageContentPane = 1000;


// prepare control sets

            List<ControlsFactory> controlsSetA = new ArrayList();
            controlsSetA.add(ControlsFactory.Labels);
            controlsSetA.add(ControlsFactory.Accordions);
            controlsSetA.add(ControlsFactory.ListViews);

            List<ControlsFactory> controlsSetB = new ArrayList();
            controlsSetB.add(ControlsFactory.ScrollPanes);
            controlsSetB.add(ControlsFactory.Separators);
            controlsSetB.add(ControlsFactory.CheckBoxes);

            List<ControlsFactory> controlsSetC = new ArrayList();
            controlsSetC.add(ControlsFactory.Toolbars);
            controlsSetC.add(ControlsFactory.ChoiceBoxes);
            controlsSetC.add(ControlsFactory.SplitMenuButtons);
            controlsSetC.add(ControlsFactory.ProgressIndicators);

            List<ControlsFactory> controlsSetD = new ArrayList();
            controlsSetD.add(ControlsFactory.TabPanes);
            controlsSetD.add(ControlsFactory.ScrollBars);
            controlsSetD.add(ControlsFactory.TitledPanes);
            controlsSetD.add(ControlsFactory.ProgressBars);

            List<ControlsFactory> controlsSetE = new ArrayList();
            controlsSetE.add(ControlsFactory.Hyperlinks);
            controlsSetE.add(ControlsFactory.Sliders);
            controlsSetE.add(ControlsFactory.PasswordFields);
            controlsSetE.add(ControlsFactory.TextFields);
            controlsSetE.add(ControlsFactory.RadioButtons);

            List<List<ControlsFactory>> listOfSets = new ArrayList();
            listOfSets.add(controlsSetA);
            listOfSets.add(controlsSetB);
            listOfSets.add(controlsSetC);
            listOfSets.add(controlsSetD);
            listOfSets.add(controlsSetE);


        // ======== VBOX =================
        int k = 0;
        for (List<ControlsFactory> controlsSet : listOfSets) {
            final PageWithSlots vboxPage = new PageWithSlots(Pages.VBox.name()+ "SetN" + (++k), heightPageContentPane, widthPageContentPane);
            vboxPage.setSlotSize(320, 320);
            rootTestNode.add(vboxPage);

            vboxPage.add(new slotVboxControls(controlsSet), "default");
            vboxPage.add(new slotVboxcenter(controlsSet), "center");
            vboxPage.add(new slotVboxPadding(controlsSet), "padding");
            vboxPage.add(new slotVboxSpacing(controlsSet), "spacing");
            vboxPage.add(new slotVboxnodeHpos(controlsSet), "nodeHpos");
        }


        // ======== HBOX =================
        k = 0;
        for (List<ControlsFactory> controlsSet : listOfSets) {
            final PageWithSlots hboxPage = new PageWithSlots(Pages.HBox.name()+"SetN" + (++k), heightPageContentPane, widthPageContentPane);
            hboxPage.setSlotSize(140, 460);
            rootTestNode.add(hboxPage);

            hboxPage.add(new slotHboxDefault(controlsSet), "default");
            hboxPage.add(new slotHboxAlignment1(controlsSet), "Alignment1");
            hboxPage.add(new slotHboxAlignment2(controlsSet), "Alignment2");
            hboxPage.add(new slotHboxpadding(controlsSet), "padding");
            hboxPage.add(new slotHboxspacing(controlsSet), "spacing");

        }

        k = 0;
            final PageWithSlots hboxPage2 = new PageWithSlots(Pages.HBox.name()+"Sxxx", heightPageContentPane, widthPageContentPane);
            hboxPage2.setSlotSize(140, 460);
            rootTestNode.add(hboxPage2);

            hboxPage2.add(new slotHboxAlignment2(controlsSetD), "Alignment2");

        // ============= BorderPane ====================
        k = 0;
        for (List<ControlsFactory> controlsSet : listOfSets) {
            final PageWithSlots borderPage = new PageWithSlots(Pages.BorderPane.name()+"SetN" + (++k), heightPageContentPane, widthPageContentPane);
            borderPage.setSlotSize(330, 330);
            rootTestNode.add(borderPage);

            borderPage.add(new slotBorderDefault(controlsSet), "default");
            borderPage.add(new slotBorderAlignment(controlsSet), "Alignment");

        }
/*
        // ============= StackPane ====================
        final PageWithSlots stackPage = new PageWithSlots(Pages.StackPane.name(), heightPageContentPane, widthPageContentPane);
        stackPage.setSlotSize(140, 140);

        stackPage.add(new slotStackDefaults(), "defaults");
        stackPage.add(new slotStackPadding(), "padding");
*/
        // ============= GridPane ====================
        k = 0;
        for (List<ControlsFactory> controlsSet : listOfSets) {
            final PageWithSlots gridPage = new PageWithSlots(Pages.GridPane.name()+"SetN" + (++k), heightPageContentPane, widthPageContentPane);
            gridPage.setSlotSize(330, 330);
            rootTestNode.add(gridPage);

            gridPage.add(new slotGridDefault(controlsSet), "default");
            gridPage.add(new slotGridHVgap(controlsSet), "HVgap");
            gridPage.add(new slotGridpadding(controlsSet), "padding");
            gridPage.add(new slotGridnodeVpos(controlsSet), "Vpos");
            gridPage.add(new slotGridnodeHposRight(controlsSet), "HposRight");
            gridPage.add(new slotGridnodeHposTrailing(controlsSet), "Trailing");
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
        Utils.launch(ControlsLayoutPart4App.class, args);
    }
}
