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

import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class DepthTestScene extends Scene
{

    public DepthTestScene()
    {
        super(new BorderPane(), 500, 280.25, true);
        BorderPane root = (BorderPane) getRoot();
        final Group depthTestGroup = new Group();
        depthTestGroup.getChildren().addListener(new NodeListener());
        depthTestGroup.getChildren().addAll(Nodes.RECTANGLE.getNode("green"), Nodes.CIRCLE.getNode("red"));

        parentDT.setOnAction(new DepthTestChangeHandler(depthTestGroup));

        GridPane tuneGrid = new GridPane();
        tuneGrid.setVgap(1);
        tuneGrid.setHgap(1);
        tuneGrid.addRow(0, oneLabel, oneDT);
        tuneGrid.addRow(1, anotherLabel, anotherDT);
        tuneGrid.addRow(2, parentLabel, parentDT);

        oneNodeCmb.getSelectionModel().select(Nodes.RECTANGLE);
        oneNodeCmb.setOnAction(new NodeChangeHandler(depthTestGroup.getChildren(), 0));
        anotherNodeCmb.getSelectionModel().select(Nodes.CIRCLE);
        anotherNodeCmb.setOnAction(new NodeChangeHandler(depthTestGroup.getChildren(), 1));

        HBox nodeSwitches = new HBox(1);
        nodeSwitches.getChildren().addAll(oneNodeCmb, anotherNodeCmb);

        oneToFront.setOnAction(new FrontBackChangeHandler(depthTestGroup.getChildren(), 0));
        anotherToFront.setOnAction(new FrontBackChangeHandler(depthTestGroup.getChildren(), 1));

        VBox backFrontRadio = new VBox(1);
        backFrontRadio.getChildren().addAll(oneToFront, anotherToFront);

        VBox switchesAndRadios = new VBox(1);
        switchesAndRadios.getChildren().addAll(tuneGrid, backFrontRadio,
                HBoxBuilder.create().children(indicator, referenceGreen, referenceRed).spacing(10).
                build(), details);

        root.setLeft(depthTestGroup);
        root.setRight(switchesAndRadios);
        root.setTop(nodeSwitches);
        setCamera(new PerspectiveCamera());

        oneDT.setValue(DepthTest.INHERIT);
        anotherDT.setValue(DepthTest.INHERIT);
        parentDT.setValue(DepthTest.DISABLE);
    }

    private Label oneLabel = new Label("First node DepthTest");
    private Label anotherLabel = new Label("Second node DepthTest");
    private Label parentLabel = new Label("Parent node DepthTest");

    private ObservableList<DepthTest> depthTestList = FXCollections.observableArrayList(DepthTest.values());
    private ComboBox<DepthTest> oneDT = new ComboBox<DepthTest>(depthTestList);
    private ComboBox<DepthTest> anotherDT = new ComboBox<DepthTest>(depthTestList);
    private ComboBox<DepthTest> parentDT = new ComboBox<DepthTest>(
            FXCollections.observableArrayList(DepthTest.DISABLE, DepthTest.ENABLE));

    private ObservableList<DepthTestScene.Nodes> nodesList = FXCollections.observableArrayList(DepthTestScene.Nodes.values());
    private ComboBox<DepthTestScene.Nodes> oneNodeCmb = new ComboBox<DepthTestScene.Nodes>(nodesList);
    private ComboBox<DepthTestScene.Nodes> anotherNodeCmb = new ComboBox<DepthTestScene.Nodes>(nodesList);

    private ToggleGroup backFrontSwitch = new ToggleGroup();
    private RadioButton oneToFront = new RadioButton("First node to front");
    private RadioButton anotherToFront = new RadioButton("Second node to front");

    private Circle indicator = new Circle(10);
    private Label details = new Label("");
    private Circle referenceGreen = new Circle(10, Color.GREEN);
    private Circle referenceRed = new Circle(10, Color.RED);

    {
        oneDT.setId("first_depth_test");
        anotherDT.setId("second_depth_test");
        parentDT.setId("parent_depth_test");

        oneNodeCmb.setId("first_node_combo");
        anotherNodeCmb.setId("second_node_combo");

        oneToFront.setId("first_node_to_front");
        anotherToFront.setId("second_node_to_front");

        oneToFront.setToggleGroup(backFrontSwitch);
        anotherToFront.setToggleGroup(backFrontSwitch);

        indicator.setId("indicator");
        //details.setWrapText(true);
        referenceGreen.setId("reference_green");
        referenceRed.setId("reference_red");
    }

    public enum Nodes
    {

        RECTANGLE(){
            public Node create()
            {
                Node node = new Rectangle(100, 100, Color.GREEN);
                return node;
            }
        },
        CIRCLE(){
            public Node create()
            {
                Node node = new Circle(40, Color.RED);
                return node;
            }
        },
        BUTTON(){
            public Node create()
            {
                Node node = new Button("Button");
                return node;
            }
        };

        protected abstract Node create();

        public Node getNode()
        {
            Node node = create();
            node.setId(toString());
            node.setTranslateX(100);
            node.setTranslateY(100);
            return node;
        }

        public Node getNode(String color)
        {
            Node node = create();
            node.setId(toString() + "-" + color);
            if(node instanceof Shape)
            {
                ((Shape) node).setFill(Color.web(color));
            }
            else if(node instanceof Control)
            {
                node.setStyle(String.format("-fx-background-color:%1$s; -fx-text-fill:%1$s", color));
            }
            return node;
        }

        public String getId()
        {
            return id;
        }

        private String id;

    }

    class NodeListener implements ListChangeListener<Node>
    {

        public void onChanged(final ListChangeListener.Change<? extends Node> change)
        {
            while(change.next())
            {
                if(change.wasAdded())
                {
                    final List<? extends Node> added = change.getAddedSubList();
                    for(final Node node: added)
                    {
                        final ObservableList<? extends Node> list = change.getList();
                        final int current = list.indexOf(node);
                        ComboBox<DepthTest> box = boxes.get(current);
                        box.setOnAction(new DepthTestScene.DepthTestChangeHandler(node));
                        node.setDepthTest(box.getValue());
                        labels.get(current).setText(node.getClass().getSimpleName() + " DepthTest");
                        RadioButton radio = radios.get(current);
                        radio.setText(node.getClass().getSimpleName() + " to front");
                        for(RadioButton rb: radios)
                        {
                            if(rb.selectedProperty().get())
                            {
                                ActionEvent.fireEvent(rb, new ActionEvent(rb, rb));
                            }
                        }
                        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                            public void handle(MouseEvent t)
                            {
                                details.setText(node.getId() + " was reached");
                                if(isOnTop())
                                {
                                    indicator.setStyle("-fx-fill: green;");
                                }
                                else
                                {
                                    indicator.setStyle("-fx-fill: red;");
                                }
                            }

                            public boolean isOnTop()
                            {
                                boolean meets = false;
                                boolean onTop = list.indexOf(node) == list.size() - 1;
                                boolean selfDTEnable = isDepthTestEnable(boxes.get(current));
                                boolean otherDTEnable = isDepthTestEnable(boxes.get((current + 1) % boxes.size()));
                                boolean inFront = radios.get(current).isSelected();

                                if((!selfDTEnable || !otherDTEnable) && onTop)
                                    meets = true;
                                if(selfDTEnable && otherDTEnable && inFront)
                                    meets = true;

                                return meets;
                                }

                            private boolean isDepthTestEnable(ComboBox<DepthTest> cb)
                            {
                                boolean enable = cb.getValue().equals(DepthTest.ENABLE) ||
                                        (cb.getValue().equals(DepthTest.INHERIT) &&
                                        parentDT.getValue().equals(DepthTest.ENABLE));
                                return enable;
                            }
                        });
                    }
                }
            }
        }

        private List<ComboBox<DepthTest>> boxes = Arrays.asList(oneDT, anotherDT);
        private List<Label> labels = Arrays.asList(oneLabel, anotherLabel);
        private List<RadioButton> radios = Arrays.asList(oneToFront, anotherToFront);

    }

    class DepthTestChangeHandler implements EventHandler<ActionEvent>
    {

        DepthTestChangeHandler(Node node)
        {
            this.node = node;
        }

        public void handle(ActionEvent t)
        {
            Object source = t.getSource();
            if(source instanceof ComboBox)
            {
                node.setDepthTest(((ComboBox<DepthTest>) source).getValue());
            }
        }

        private Node node;

    }

    class NodeChangeHandler implements EventHandler<ActionEvent>
    {

        NodeChangeHandler(ObservableList<Node> nodes, int nodeIndex)
        {
            this.nodes = nodes;
            this.nodeIndex = nodeIndex;
        }

        public void handle(ActionEvent t)
        {
            Object source = t.getSource();
            if(source instanceof ComboBox)
            {
                DepthTestScene.Nodes nd = ((ComboBox<DepthTestScene.Nodes>) source).getSelectionModel().getSelectedItem();
                nodes.remove(nodeIndex);
                String color = nodeIndex == 0 ? "green" : "red";
                nodes.add(nodeIndex, nd.getNode(color));
            }
        }

        private ObservableList<Node> nodes;
        private int nodeIndex;

    }

    class FrontBackChangeHandler implements EventHandler<ActionEvent>
    {

        FrontBackChangeHandler(ObservableList<Node> nodes, int nodeIndex)
        {
            this.nodes = nodes;
            this.nodeIndex = nodeIndex;
        }

        public void handle(ActionEvent t)
        {
            for(int i = 0; i < nodes.size(); i++)
            {
                if(i == nodeIndex)
                {
                    nodes.get(i).setTranslateZ(-1);
                }
                else
                {
                    nodes.get(i).setTranslateZ(0);
                }
            }
        }

        private ObservableList<Node> nodes;
        private int nodeIndex;

    }

}
