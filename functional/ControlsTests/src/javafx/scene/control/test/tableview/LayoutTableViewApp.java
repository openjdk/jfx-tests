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
package javafx.scene.control.test.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.util.Callback;
import test.javaclient.shared.BasicButtonChooserApp;
import test.javaclient.shared.PageWithSlots;
import test.javaclient.shared.TestNode;
import test.javaclient.shared.Utils;

/**
 * @author Victor Shubov
 */
public class LayoutTableViewApp extends BasicButtonChooserApp {

    public static enum Pages {

        VBox, HBox, TilePane, StackPane, GridPane, FlowPane,
        BorderPane, AnchorPane
    }

    public LayoutTableViewApp() {
        super(800, 800, "LayoutTableView", true); // "true" stands for "additionalActionButton = "
    }
    final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("A Jacob", "Smith", "jacob.smith@example.com"),
            new Person("B Isabella", "Johnson", "isabella.johnson@example.com"),
            new Person("C Ethan", "Williams", "ethan.williams@example.com"),
            new Person("D Emma", "Jones", "emma.jones@example.com"),
            new Person("E Michael", "Brown", "michael.brown@example.com"));

    public static class Person {

        StringProperty firstName;
        StringProperty lastName;
        StringProperty email;

        private Person(String fName, String lName, String email) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);

        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public StringProperty firstNameProperty() {
            if (firstName == null) {
                firstName = new SimpleStringProperty();
            }
            return firstName;
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public StringProperty lastNameProperty() {
            if (lastName == null) {
                lastName = new SimpleStringProperty();
            }
            return lastName;
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }

        public StringProperty emailProperty() {
            if (email == null) {
                email = new SimpleStringProperty();
            }
            return email;
        }
    }
    private static final boolean withStyle = !Boolean.getBoolean("test.javaclient.jcovbackdoor");

    private interface RegionFiller {

        Pane fill(Pane region);
    }

    /**
     * fill region
     */
    private class FillerWithAllNodes implements RegionFiller {

        protected Pane stFill(Pane region) {
            region.getChildren().add(makeTableView());
            return region;
        }

        public Pane fill(Pane region) {
            return fill(region, 2);
        }

        public Pane fill(Pane region, int k) {
            for (int i = 0; i < k; ++i) {
                stFill(region);
            }
            return region;
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

        VBox vbox;
        TableView<? extends Object> tv0 = makeTableView();
        TableView<? extends Object> tv1 = makeTableView();

        @Override
        public Node drawNode() {
            vbox = new VBox();
            vbox.getChildren().add(tv0);
            vbox.getChildren().add(tv1);
            tv0.setMaxWidth(40);
            tv1.setMinWidth(140);
            vbox.setAlignment(Pos.TOP_RIGHT);
            return vbox;
        }

        @Override
        public void additionalAction() {
            tv1.getColumns().get(0).setVisible(false);
            tv1.getColumns().get(1).setMaxWidth(40);
            tv1.getColumns().get(1).setText("done");

            vbox.setPrefSize(40, 240);
            vbox.setSpacing(10);

        }
    }

    private class slotVboxcenter extends slotVboxTestNode {

        TableView<? extends Object> tv0 = makeTableView();
        TableView<Person> tv1 = (TableView<Person>) makeTableView();
        final ObservableList<Person> data3 = FXCollections.observableArrayList();

        @Override
        public Node drawNode() {
            VBox vbox = new VBox();
            tv1.setItems(data);
            vbox.getChildren().add(tv0);
            vbox.getChildren().add(tv1);
            vbox.setAlignment(Pos.TOP_CENTER);
            return vbox;
        }

        @Override
        public void additionalAction() {
            tv1.setPlaceholder(new Circle(8));
            tv1.setItems(data3);

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

            return vbox;
        }
    }

    private class slotVboxSpacing extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox());
            vbox.setSpacing(5);

            return vbox;
        }
    }

    private class slotVboxSpacing2 extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox(10));

            return vbox;
        }
    }

    private class slotVboxNodevbox extends slotVboxTestNode {

        @Override
        public Node drawNode() {
            VBox vbox = baseFill(new VBox());
            vbox.setAlignment(Pos.BOTTOM_CENTER);

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
            hbox.setAlignment(Pos.CENTER);
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

            Node n1 = makeTableView();
            borderPane.setBottom(n1);


            return borderPane;
        }

        @Override
        public void additionalAction() {

            borderPane.setPrefSize(borderPane.getWidth() / 2, borderPane.getHeight() / 2);

        }
    }

    private class slotBorderDefaults2 extends slotBorder { // defaults2

        BorderPane borderPane;

        @Override
        public Node drawNode() {
            borderPane = baseFill(new BorderPane());

            Node n1 = makeTableView();
            borderPane.setRight(n1);

            BorderPane.setAlignment(n1, Pos.BOTTOM_RIGHT);


            return borderPane;
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

    private class slotGrid extends TestNode {

        protected GridPane baseFill(GridPane pane) {
            pane.setPrefSize(130, 130);
            pane.setGridLinesVisible(true);
            (new FillerWithAllNodes()).fill(pane);
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
            sp.setPadding(new Insets(4, 4, 4, 4));
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
            sp.setPadding(new Insets(4, 4, 4, 4));
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

    private class slotFlow extends TestNode {

        protected FlowPane baseFill(FlowPane pane) {
            pane.setPrefSize(140, 140);
            pane.setMinSize(140, 140);
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
                if (Orientation.HORIZONTAL != or && Orientation.VERTICAL != or) {
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
                TilePane.setMargin(n1, new Insets(4, 4, 4, 4));
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
        hboxPage.add(new slotHboxVposTOPHposLeading(), "VposTOP");
        hboxPage.add(new slotHboxVposBOTTOMHposCenter(), "VposBOTTOM");
        hboxPage.add(new slotHboxpadding(), "padding");
        hboxPage.add(new slotHboxspacing(), "spacing");
        hboxPage.add(new slotHboxspacingCtor(), "spacingCtor");
        hboxPage.add(new slotHboxnodevpos(), "nodevpos");

        // ============= BorderPane ====================
        final PageWithSlots borderPage = new PageWithSlots(Pages.BorderPane.name(), heightPageContentPane, widthPageContentPane);
        borderPage.setSlotSize(190, 190);

        borderPage.add(new slotBorderDefaults(), "defaults");
        borderPage.add(new slotBorderDefaults2(), "defaults2");

        // ============= StackPane ====================
        final PageWithSlots stackPage = new PageWithSlots(Pages.StackPane.name(), heightPageContentPane, widthPageContentPane);
        stackPage.setSlotSize(140, 140);

        stackPage.add(new slotStackDefaults(), "defaults");
        stackPage.add(new slotStackPadding(), "padding");

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

        // ============= FlowPane ====================
        final PageWithSlots flowPage = new PageWithSlots(Pages.FlowPane.name(), heightPageContentPane, widthPageContentPane);
        flowPage.setSlotSize(180, 180);

        flowPage.add(new slotFlowdefaults(), "defaults");
        flowPage.add(new slotFlowwrapLen(), "wrapLen");
        flowPage.add(new slotFlowpadding(), "padding");
        flowPage.add(new slotFlownodeVposBottom(), "nodeVposBottom");
        flowPage.add(new slotFlownodeVposTop(), "nodeVposTop");

        flowPage.add(new slotFlowGap5(), "Gap5");
        flowPage.add(new slotFlownodeHposRight(), "nodeHposRight");
        flowPage.add(new slotFlownodeHposRight2(), "nodeHposRight2");
        flowPage.add(new slotFlowvertical_1(), "vertical_1");
        flowPage.add(new slotFlowvertical_2(), "vertical_2");

        flowPage.add(new slotFlowhgapvgapCtor(), "hgap-vgap_Ctor");

        // ============= TilePane  ====================
        final PageWithSlots tilePageLong = new PageWithSlots(Pages.TilePane.name(), heightPageContentPane, widthPageContentPane);
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

        // ============= AnchorPane ====================
        final PageWithSlots anchorPage = new PageWithSlots(Pages.AnchorPane.name(), heightPageContentPane, widthPageContentPane);
        anchorPage.setSlotSize(140, 140);

        anchorPage.add(new slotAnchorDefaults(), "defaults");


        // ========= root tests list ==============
        rootTestNode.add(vboxPage);
        rootTestNode.add(hboxPage);
        rootTestNode.add(borderPage);
        rootTestNode.add(stackPage);
        rootTestNode.add(gridPage);
        rootTestNode.add(flowPage);
        rootTestNode.add(tilePageLong);
        rootTestNode.add(anchorPage);
        return rootTestNode;
    }

    public TableView<? extends Object> makeTableView() {
        TableView<Person> table = new TableView<Person>();
        //TableViewSelectionModel tsm = new TableViewSelectionModel();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<Person, String> lastNameCol = new TableColumn<Person, String>();
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));


        table.setStyle("-fx-base: #b6e7c9;");
        table.setPrefWidth(50);
        table.setMaxWidth(140);
        //table.setMinHeight(40);
        table.setPrefHeight(80);
        table.setMaxHeight(140);
        table.setPlaceholder(new Circle(8) {
            {
                setFill(Color.RED);
            }
        });
        table.setTooltip(new Tooltip("A-a-a-a-a-a-a!"));

        TableColumn<Person, String> firstNameCol = new TableColumn<Person, String>();
        /*
         firstNameCol.setCellFactory(new Callback<TableColumn<String>, TableCell<String>>() {
         public TableCell<String> call(TableColumn<String> p) {
         return new EditingCell();
         }
         });
         */
        firstNameCol.setText("First");
        firstNameCol.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<Person, String> p) {
                return p.getValue().firstNameProperty();
            }
        });

        lastNameCol.setCellFactory(new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            public TableCell<Person, String> call(TableColumn<Person, String> p) {
                return new EditingCell();
            }
        });
        lastNameCol.setText("Last");
        lastNameCol.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<Person, String> p) {
                return p.getValue().lastNameProperty();
            }
        });
        lastNameCol.setVisible(true);

        TableColumn<Person, String> emailCol = new TableColumn<Person, String>();
        emailCol.setText("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellFactory(new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            public TableCell<Person, String> call(TableColumn<Person, String> p) {
                return new EditingCell();
            }
        });
        emailCol.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(final CellDataFeatures<Person, String> p) {
                return p.getValue().emailProperty();
            }
        });

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        return table;
    }

    class EditingCell extends TableCell<Person, String> {

        private TextField textBox;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }

            if (textBox == null) {
                createTextBox();
            } else {
                textBox.setText(getItem());
            }

            setGraphic(textBox);
            setContentDisplay(ContentDisplay.TEXT_ONLY);

            textBox.requestFocus();
            textBox.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        public void commitEdit(String t) {
            super.commitEdit(t);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                if (textBox != null) {
                    textBox.setText(item);
                }
                setText(item);
            }
        }

        private void createTextBox() {
            textBox = new TextField(getItem());
            textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textBox.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
    }

    public static void main(String args[]) {
        Utils.launch(LayoutTableViewApp.class, args);
    }
}