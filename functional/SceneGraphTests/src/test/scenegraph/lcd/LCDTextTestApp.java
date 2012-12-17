/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
import javafx.scene.text.TextBuilder;
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
                lcdText = TextBuilder.create().text("Test text")
                        .textAlignment(TextAlignment.CENTER)
                        .style("-fx-font-size: 16;-fx-font-smoothing-type: lcd;")
                        .fontSmoothingType(FontSmoothingType.LCD)
                        .build();

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
