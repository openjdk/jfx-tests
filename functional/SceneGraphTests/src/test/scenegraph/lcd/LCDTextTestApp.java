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
package test.scenegraph.lcd;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Alexander Petrov
 */
public class LCDTextTestApp extends InteroperabilityApp {
    static {
        System.setProperty("prism.lcdtext", "true");
    }

    public static TestCollections testCollection = null;
    public static TestAction testAction = null;

    private static final int WIDTH = 300;
    private static final int HEIGHT = 480;
    private ChoiceBox<TestAction> actionChoice;
    private ChoiceBox<TestCollections> collecionChoice;
    private Text grayText;
    private Text lcdText;
    private Pane leftPane;
    private Pane rightPane;

    @Override
    protected Scene getScene() {
        Scene scene = new Scene(createGUI(), 600, 510);
        Utils.addBrowser(scene);
        return scene;
    }

    private Parent createGUI() {
        VBox mainPane = new VBox();

        mainPane.setAlignment(Pos.CENTER);

        HBox controlsPane = new HBox(15);
        controlsPane.setAlignment(Pos.CENTER);
        controlsPane.setPadding(new Insets(5));

        leftPane = new StackPane();
        leftPane.setId("leftPane");
        leftPane.setPrefSize(WIDTH, HEIGHT);
        leftPane.setMaxSize(WIDTH, HEIGHT);
        leftPane.setMinSize(WIDTH, HEIGHT);

        collecionChoice = new ChoiceBox<TestCollections>(
                FXCollections.observableArrayList(TestCollections.values()));
        collecionChoice.setPrefWidth(150);
        actionChoice = new ChoiceBox<TestAction>();
        actionChoice.setPrefWidth(150);

        collecionChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TestCollections>() {

            public void changed(ObservableValue<? extends TestCollections> ov, TestCollections t, TestCollections newValue) {
                actionChoice.setItems(FXCollections.observableArrayList(newValue.getActions()));
                actionChoice.getSelectionModel().selectFirst();
                lcdText = new Text("Test text");
                lcdText.setTextAlignment(TextAlignment.CENTER);
                lcdText.setStyle("-fx-font-size: 16;-fx-font-smoothing-type: lcd;");
                lcdText.setFontSmoothingType(FontSmoothingType.LCD);
                leftPane.getChildren().clear();
                leftPane.getChildren().add(lcdText);
            }
        });

        if(LCDTextTestApp.testCollection != null){
            collecionChoice.getSelectionModel().select(LCDTextTestApp.testCollection);
        }


        Button applyButton = new Button("Apply");
        applyButton.setId("btnApply");
        applyButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                apply();
            }
        });
        applyButton.setPrefWidth(75);

        controlsPane.getChildren().addAll(collecionChoice, actionChoice,
                applyButton);

        mainPane.getChildren().addAll(controlsPane, leftPane);

        return mainPane;
    }

    private void apply() {
        if(LCDTextTestApp.testAction != null){
            actionChoice.getSelectionModel().select(LCDTextTestApp.testAction);
        }


        TestAction action = actionChoice.getSelectionModel().getSelectedItem();

        action.updateNode(lcdText);


    }

    public static void main(String[] args) {
        Utils.launch(LCDTextTestApp.class, args);
    }
}
