/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import test.javaclient.shared.Utils;

/**
 *
 * @author Alexander Petrov
 */
public class AnimationLCDTextTestApp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        Utils.launch(AnimationLCDTextTestApp.class, args);
    }

    @Override
    public void start(Stage primaryStage) {


        final TextArea testText = new TextArea("Test");
        testText.setPrefHeight(50);
        testText.setPrefWidth(500);

        final ChoiceBox<Interpolator> interpolatorChoiceBox = new ChoiceBox<Interpolator>();
        interpolatorChoiceBox.getItems().addAll(FXCollections.observableArrayList(
                    Interpolator.LINEAR,
                    Interpolator.DISCRETE,
                    Interpolator.EASE_BOTH,
                    Interpolator.EASE_IN,
                    Interpolator.EASE_OUT
                    ));
        interpolatorChoiceBox.setPrefHeight(25);
        interpolatorChoiceBox.setPrefWidth(500);

        interpolatorChoiceBox.getSelectionModel().selectFirst();


        final Text lcdText = new Text();
        lcdText.setX(100);
        lcdText.setY(100);
        lcdText.setFontSmoothingType(FontSmoothingType.LCD);
        lcdText.textProperty().bind(testText.textProperty());

        final Circle point = new Circle(100, 100, 2, Color.RED);

        Pane temp = new Pane(lcdText, point);
        temp.setMinWidth(500);
        temp.setMinHeight(500);
        temp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            point.setCenterX(event.getX());
                            point.setCenterY(event.getY());

                new Timeline(
                                    new KeyFrame(Duration.seconds(5),
                                        new KeyValue(lcdText.xProperty(), event.getX(),
                                            interpolatorChoiceBox.getSelectionModel().getSelectedItem())),
                                    new KeyFrame(Duration.seconds(5),
                                        new KeyValue(lcdText.yProperty(), event.getY(),
                                            interpolatorChoiceBox.getSelectionModel().getSelectedItem()))
                ).play();
                        }
        });
        Pane root = new VBox(temp, testText, interpolatorChoiceBox);
        Scene scene = new Scene(root, 500, 575);

        primaryStage.setTitle("Test Animnation LCD Text");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



