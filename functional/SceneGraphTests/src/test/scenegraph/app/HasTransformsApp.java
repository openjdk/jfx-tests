/*
 * Copyright (c) 2009, 2016, Oracle and/or its affiliates. All rights reserved.
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

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.*;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class HasTransformsApp extends InteroperabilityApp {

    private Node node;
    private final Translate translate = new Translate(20, 20);
    private final Rotate rotate = new Rotate(45);
    private final Scale scale = new Scale(1.5, 1.5);
    private final Shear shear = new Shear(0.1, 0.1);

    public static void main(String[] args) {
        Utils.launch(HasTransformsApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new Scene(new HasTransformRoot(), 300, 300);
    }

    public class HasTransformRoot extends BorderPane {

        public HasTransformRoot() {
            CheckBox translateBox = new CheckBox("Translate");
            translateBox.setId("translate");
            CheckBox rotateBox = new CheckBox("Rotate");
            rotateBox.setId("rotate");
            CheckBox scaleBox = new CheckBox("Scale");
            scaleBox.setId("scale");
            CheckBox shearBox = new CheckBox("Shear");
            shearBox.setId("shear");
            Button checkTransforms = new Button("Check tranforms");
            checkTransforms.setId("check_tranforms");
            final Label hasTransforms = new Label();
            hasTransforms.setId("has_transforms");
            final ComboBox<Nodes> nodesCombo = new ComboBox<>(FXCollections.observableArrayList(Nodes.values()));
            nodesCombo.setId("nodes_combo");

            nodesCombo.valueProperty().addListener((obs) -> {
                node = nodesCombo.getValue().getNode();
                setCenter(node);
            });
            nodesCombo.setValue(Nodes.values()[0]);
            checkTransforms.setOnAction((event)
                    -> hasTransforms.setText(String.valueOf(!node.getTransforms().isEmpty())));

            translateBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(translateBox, translate));
            rotateBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(rotateBox, rotate));
            scaleBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(scaleBox, scale));
            shearBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(shearBox, shear));

            setTop(nodesCombo);
            setRight(new VBox(translateBox, rotateBox, scaleBox, shearBox,
                    new HBox(checkTransforms, hasTransforms)));
        }

    }

    private class TransformBoxHandler implements EventHandler<Event> {

        private final Transform transform;
        private final CheckBox checkBox;

        public TransformBoxHandler(CheckBox checkBox, Transform transform) {
            this.checkBox = checkBox;
            this.transform = transform;
        }

        @Override
        public void handle(Event arg0) {
            if (checkBox.isSelected()) {
                node.getTransforms().add(transform);
            } else {
                node.getTransforms().remove(transform);
            }
        }
    }

}

enum Nodes {

    BUTTON(new Button("Button"));

    private final Node node;

    private Nodes(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}
