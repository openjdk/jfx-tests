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
 * questions.
 */
package org.jemmy.samples.buttons;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This small FX app is used in button JemmyFX samples. It displays a few of
 * buttons of different kind with a visible reaction to any of the buttons been
 * pushed.
 *
 * @author shura
 */
public class ButtonsApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final Label status = new Label();
        status.setId("status");
        Button btn = new Button("button");
        btn.setOnAction(t -> status.setText("button pushed"));
        final CheckBox twoState = new CheckBox("two-state");
        twoState.setAllowIndeterminate(false);
        twoState.setOnAction(t -> status.setText("two-state " + (twoState.isSelected() ? "selected" : "unselected")));
        final CheckBox triState = new CheckBox("tri-state");
        triState.setAllowIndeterminate(true);
        triState.setOnAction(t -> status.setText("tri-state " + (triState.isIndeterminate() ? "unknown" : (triState.isSelected() ? "selected" : "unselected"))));
        ToggleGroup group = new ToggleGroup();
        EventHandler<ActionEvent> radioHandler = t -> status.setText(((ToggleButton)t.getSource()).getText() + " selected");
        RadioButton radio1 = new RadioButton("radio1");
        radio1.setToggleGroup(group);
        radio1.setOnAction(radioHandler);
        RadioButton radio2 = new RadioButton("radio2");
        radio2.setToggleGroup(group);
        radio2.setOnAction(radioHandler);
        RadioButton radio3 = new RadioButton("radio3");
        radio3.setToggleGroup(group);
        radio3.setOnAction(radioHandler);
        HBox radios = new HBox(20);
        radios.getChildren().addAll(radio1, radio2, radio3);
        ToggleButton toggle1 = new ToggleButton("toggle1");
        toggle1.setToggleGroup(group);
        toggle1.setOnAction(radioHandler);
        ToggleButton toggle2 = new ToggleButton("toggle2");
        toggle2.setToggleGroup(group);
        toggle2.setOnAction(radioHandler);
        ToggleButton toggle3 = new ToggleButton("toggle3");
        toggle3.setToggleGroup(group);
        toggle3.setOnAction(radioHandler);
        HBox toggles = new HBox(20);
        toggles.getChildren().addAll(toggle1, toggle2, toggle3);
        VBox content = new VBox(20);
        content.getChildren().addAll(btn, twoState, triState, radios, toggles);
        content.getChildren().add(status);
        stage.setScene(new Scene(content));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
