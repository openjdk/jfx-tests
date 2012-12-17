/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.transparency;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.text.Text;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Alexander Petrov
 */
public class TransparencyLCDTextTestApp extends InteroperabilityApp {
    static {
        System.setProperty("prism.lcdtext", "true");
    }
    
    public static final String APPLY_BUTTON_ID = "applyButton";
    public static final String ACTION_BUTTON_ID = "actionButton";
    public static final String RIGHT_PANE_ID = "rightPane";
    public static final String APPLY_INDICATOR_ID = "applyIndicator";
    public static final String ACTION_INDICATOR_ID = "actionIndicator";
    
    public static Factory testingFactory = null;
    
    
    private ChoiceBox<Factory> factoryChoicer;

    private Pane rightPane;
    
    private Circle applyIndicator;
    private Circle actionIndicator;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Utils.launch(TransparencyLCDTextTestApp.class, args);
    }
    
    private Parent createGUI(){
        factoryChoicer = new ChoiceBox(
                FXCollections.observableArrayList((Factory[]) Factories.values()));
        factoryChoicer.setMinWidth(200);
        factoryChoicer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Factory>() {

            public void changed(ObservableValue<? extends Factory> ov, Factory t, Factory t1) {
                apply();
            }
        });
        
        applyIndicator = CircleBuilder.create()
                .id(APPLY_INDICATOR_ID)
                .radius(5)
                .fill(Color.RED)
                .build();
        
        actionIndicator = CircleBuilder.create()
                .id(ACTION_INDICATOR_ID)
                .radius(5)
                .fill(Color.RED)
                .build();
        
        Button applyButton = ButtonBuilder.create()
                .id(APPLY_BUTTON_ID)
                .text("Apply")
                .onMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                        if(testingFactory == null){
                            factoryChoicer.getSelectionModel().selectNext();
                        } else {
                            factoryChoicer.getSelectionModel().select(TransparencyLCDTextTestApp.testingFactory);
                        }
                        applyIndicator.setFill(Color.GREEN);
                    }
                })
                .build();
        
        Button actionButton = ButtonBuilder.create()
                .id(ACTION_BUTTON_ID)
                .text("Action")
                .onMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                        Factory currentFactory = TransparencyLCDTextTestApp.this.
                                factoryChoicer.getSelectionModel().getSelectedItem();

                        currentFactory.action(TransparencyLCDTextTestApp.this.rightPane.getChildren().get(0));
                        
                        actionIndicator.setFill(Color.GREEN);
                    }
                })
                .build();
        
        //Create panes for testing;

        
        rightPane = StackPaneBuilder.create()
                .id(RIGHT_PANE_ID)
                .alignment(Pos.CENTER)
                .minHeight(450)
                .minWidth(300)
                .build();
        
        //Create root pane.
        return VBoxBuilder.create()
                .id("root")
                .children(
                    HBoxBuilder.create()
                        .padding(new Insets(10))
                        .spacing(10)
                        .id("toolsPane")
                        .alignment(Pos.CENTER)
                        .children(factoryChoicer, actionButton, applyButton)
                        .build(),
                    HBoxBuilder.create()
                        .padding(new Insets(10))
                        .spacing(10)
                        .alignment(Pos.CENTER)
                        .children(
                            new Text("Apply"),
                            applyIndicator,
                            new Text("Action"),
                            actionIndicator)
                        .build(),
                    HBoxBuilder.create()
                        .id("testPane")
                        .alignment(Pos.CENTER)
                        .children(rightPane)
                        .build())
                .build();
    }

    private void apply() {
        this.rightPane.getChildren().clear();
        
        Factory currentFactory = 
                this.factoryChoicer.getSelectionModel().getSelectedItem();

        this.rightPane.getChildren().add(currentFactory.createNode(true));

    }

    @Override
    protected Scene getScene() {
        return new Scene(createGUI(), 600, 540);
    }
}
