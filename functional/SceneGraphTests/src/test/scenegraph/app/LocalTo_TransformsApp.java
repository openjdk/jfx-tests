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

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author asakharu
 */
public class LocalTo_TransformsApp extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(LocalTo_TransformsApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new LocalTo_TransformsScene();
    }

    public class LocalTo_TransformsScene extends Scene
    {

        public LocalTo_TransformsScene()
        {
            super(hBox, 150, 150, true);
            setCamera(new PerspectiveCamera());

            StackPane firstPane = new StackPane();
            StackPane secondPane = new StackPane();
            StackPane thirdPane = new StackPane();
            StackPane nestedPane = new StackPane();
            StackPane doubleNestedPane = new StackPane();
            StackPane forthPane = new StackPane();

            Circle circle1 = CircleBuilder.create().radius(20).id("circle_one").build();
            Circle circle2 = CircleBuilder.create().radius(20).id("circle_two").build();
            Circle circle3 = CircleBuilder.create().radius(20).id("circle_three").build();
            Circle circle4 = CircleBuilder.create().radius(20).id("circle_four").translateZ(-50).build();

            forthPane.getChildren().add(circle4);
            doubleNestedPane.getChildren().add(circle3);
            nestedPane.getChildren().add(doubleNestedPane);
            thirdPane.getChildren().add(nestedPane);
            secondPane.getChildren().add(circle2);
            firstPane.getChildren().add(circle1);
            hBox.getChildren().addAll(firstPane, secondPane, thirdPane, forthPane);
        }

    }

    private FlowPane hBox = new FlowPane(Orientation.HORIZONTAL, 50, 50);

}
