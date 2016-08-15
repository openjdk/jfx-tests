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

import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.transform.*;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Aleksandr Sakharuk
 */
public class HasTransformsApp extends InteroperabilityApp
{

    public static void main(String[] args)
    {
         Utils.launch(HasTransformsApp.class, args);
    }

    @Override
    protected Scene getScene()
    {
        return new Scene(new HasTransformRoot(), 300, 300);
    }

    public class HasTransformRoot extends BorderPane
    {

        public HasTransformRoot()
        {
            CheckBox translateBox = CheckBoxBuilder.create().text("Translate").id("translate").build();
            CheckBox rotateBox = CheckBoxBuilder.create().text("Rotate").id("rotate").build();
            CheckBox scaleBox = CheckBoxBuilder.create().text("Scale").id("scale").build();
            CheckBox shearBox = CheckBoxBuilder.create().text("Shear").id("shear").build();

            Button checkTransforms = ButtonBuilder.create().text("Check tranforms").id("check_tranforms").build();
            final Label hasTransforms = LabelBuilder.create().id("has_transforms").text("     ").build();

            final ComboBox<Nodes> nodesCombo = new ComboBox<Nodes>(FXCollections.observableArrayList(Nodes.values()));
            nodesCombo.setId("nodes_combo");

            nodesCombo.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent arg0) {
                    node = nodesCombo.getValue().getNode();
                    setCenter(node);
                }
            });
            nodesCombo.setValue(Nodes.values()[0]);

            checkTransforms.setOnAction(new EventHandler<ActionEvent>() {

                public void handle(ActionEvent arg0) {
                    hasTransforms.setText(String.valueOf(node.impl_hasTransforms()));
                }
            });

            translateBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(translateBox, translate));
            rotateBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(rotateBox, rotate));
            scaleBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(scaleBox, scale));
            shearBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new TransformBoxHandler(shearBox, shear));

            setTop(nodesCombo);
            setRight(VBoxBuilder.create().children(translateBox, rotateBox, scaleBox, shearBox,
                    HBoxBuilder.create().children(checkTransforms, hasTransforms).spacing(10).build()).spacing(10).build());
        }

    }

    private List<Transform> transforms = new LinkedList<Transform>();
    private Node node;
    private Translate translate = new Translate(20, 20);
    private Rotate rotate = new Rotate(45);
    private Scale scale = new Scale(1.5, 1.5);
    private Shear shear = new Shear(0.1, 0.1);

    private class TransformBoxHandler implements EventHandler<Event>
    {

        public TransformBoxHandler(CheckBox checkBox, Transform transform)
        {
            this.checkBox = checkBox;
            this.transform = transform;
        }

        public void handle(Event arg0)
        {
            if(checkBox.isSelected())
            {
                node.getTransforms().add(transform);
                transforms.add(transform);
            }
            else
            {
                node.getTransforms().remove(transform);
                transforms.remove(transform);
            }
        }

        private Transform transform;
        private CheckBox checkBox;

    }

}

enum Nodes
{

    BUTTON(ButtonBuilder.create().text("Button").alignment(Pos.CENTER).build())
    ;

    private Nodes(Node node)
    {
        this.node = node;
    }

    public Node getNode()
    {
        return node;
    }

    private Node node;

}
