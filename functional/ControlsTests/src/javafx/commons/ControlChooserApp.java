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
package javafx.commons;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.factory.ControlsFactory;
import javafx.factory.NodeFactory;
import javafx.factory.Panes;
import javafx.factory.Shapes;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Dmitry Zinkevich <dmitry.zinkevich@oracle.com>
 */
public class ControlChooserApp extends InteroperabilityApp {
    public static final String NODE_CHOOSER_ID = "nodeChooser";
    public static final String TESTED_CONTROL_ID = "target";

    private VBox spaceForNode;
    private PropertiesTable propertiesTable;
    private Pane propertiesPane;

    public static void main(String[] args) {
        Utils.launch(javafx.commons.ControlChooserApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Scene scene = new Scene(getContent(), 800, 600);
        Utils.addBrowser(scene);
        return scene;
    }

    private Node createNodeChooser() {
        ChoiceBox<NodeFactory> cb = new ChoiceBox<NodeFactory>();
        cb.setId(NODE_CHOOSER_ID);
        cb.getItems().addAll(ControlsFactory.filteredValues());
        /*
         * Remove image view and media view
         */
        cb.getItems().removeAll(ControlsFactory.ImageView, ControlsFactory.MediaView);

        cb.getItems().addAll(Shapes.values());
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NodeFactory>() {

            @Override
            public void changed(ObservableValue<? extends NodeFactory> ov, NodeFactory t, NodeFactory t1) {
                final Node node = t1.createNode();

                node.setId(TESTED_CONTROL_ID);
                spaceForNode.getChildren().clear();
                spaceForNode.getChildren().add(node);

                propertiesTable = new PropertiesTable(node);
                propertiesTable.addBooleanPropertyLine(node.disableProperty());
                propertiesTable.addDoublePropertyLine(node.opacityProperty(), 0, 1, 1);
                propertiesTable.addSimpleListener(node.focusedProperty(), node);

                propertiesPane.getChildren().clear();
                propertiesPane.getChildren().add(propertiesTable);
            }
        });

        VBox vb = new VBox(5);
        vb.getChildren().addAll(new Label("Choose tested control : "), cb);
        return vb;
    }

    private Parent getContent() {
        propertiesPane = new Pane();
        propertiesPane.setStyle("-fx-border-color:BLUE");

        spaceForNode = new VBox(10);
        spaceForNode.setAlignment(Pos.CENTER);
        spaceForNode.setMinWidth(300);
        spaceForNode.setPrefWidth(300);
        spaceForNode.setMaxWidth(300);
        spaceForNode.setMinHeight(300);
        spaceForNode.setPrefHeight(300);
        spaceForNode.setMaxHeight(300);
        spaceForNode.setStyle("-fx-border-color:RED");

        VBox controls = new VBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().add(createNodeChooser());
        controls.setStyle("-fx-border-color:GREEN");

        VBox vBox = new VBox(spaceForNode, controls);
        vBox.setSpacing(10d);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.getChildren().addAll(
                vBox,
                propertiesPane);
        return hBox;
    }
}