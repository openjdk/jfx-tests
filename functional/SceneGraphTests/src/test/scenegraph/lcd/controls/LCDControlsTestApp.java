/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.controls;

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
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Alexander Petrov
 */
public class LCDControlsTestApp extends InteroperabilityApp {
    static {
        System.setProperty("prism.lcdtext", "true");
    }
    
    public static final String BUTTON_APPLY_ID = "buttonApply";
    public static final String RIGHT_PANE_ID = "rightPane";
    public static volatile Factories factory = null;
    public static volatile Action action = null;
    public static Factories testCollection;
    
    private ChoiceBox<Action> actionChoice;
    private ChoiceBox<Factory> controlChoice;
    private Pane rightPane;
    private Parent lcdControl;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Utils.launch(LCDControlsTestApp.class, args);
    }
    
    private Pane createGUI(){
        VBox mainPane = new VBox();
        
        mainPane.getStylesheets().add(
                LCDControlsTestApp.class.getResource("green.css").toExternalForm());
        
        HBox testPane = new HBox();
        testPane.setAlignment(Pos.CENTER);

        rightPane = new StackPane();
        rightPane.setId(RIGHT_PANE_ID);
        rightPane.setPrefSize(300, 480);
        
        HBox controlsPane = new HBox(15);
        controlsPane.setAlignment(Pos.CENTER);
        controlsPane.setPadding(new Insets(5));
        
        actionChoice = new ChoiceBox<Action>(
                FXCollections.observableArrayList((Action[])Actions.values()));
        actionChoice.setPrefWidth(150);
        
        controlChoice = new ChoiceBox<Factory>(FXCollections.observableArrayList((Factory[])Factories.values()));
        controlChoice.setPrefWidth(150);
        controlChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Factory>() {

            public void changed(ObservableValue<? extends Factory> ov, Factory oldValue, Factory newValue) {
                if (newValue != null) {

                    lcdControl = newValue.createControl(true);
                    lcdControl.setId("LCDNode");
                    rightPane.getChildren().clear();
                    rightPane.getChildren().add(lcdControl);
                    
                    actionChoice.getSelectionModel().selectFirst();
                }
            }
        });
        
        
        if(factory != null){
            controlChoice.getSelectionModel().select(factory);
        }
        
        
        Button applyButton = new Button("Apply");
        applyButton.setId(BUTTON_APPLY_ID);
        applyButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent t) {
                apply();
            }
        });
        applyButton.setPrefWidth(75);

        
        controlsPane.getChildren().addAll(controlChoice, actionChoice, 
                applyButton); 
       
        testPane.getChildren().addAll(rightPane);
        
        mainPane.getChildren().addAll(controlsPane, testPane);
        
        return mainPane;
    }
   
    private void checkSelection(){
        if(controlChoice.getSelectionModel().getSelectedItem() == null){
           controlChoice.getSelectionModel().selectFirst(); 
        }
        
        if(actionChoice.getSelectionModel().getSelectedItem() == null){
           actionChoice.getSelectionModel().selectFirst(); 
        }
    }
    
    private void apply(){
        if(LCDControlsTestApp.action != null){
            actionChoice.getSelectionModel().select(LCDControlsTestApp.action);
        }
        
        checkSelection();
        Action CurrentAction = 
                actionChoice.getSelectionModel().getSelectedItem();
        
        CurrentAction.updateControl(lcdControl);
    }

    @Override
    protected Scene getScene() {
        Scene scene = new Scene(createGUI(), 600, 500);
        Utils.addBrowser(scene);
        return scene;
    }
}
