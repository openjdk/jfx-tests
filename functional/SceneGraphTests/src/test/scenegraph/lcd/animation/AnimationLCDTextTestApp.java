/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceBoxBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.PaneBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
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


        final TextArea testText = TextAreaBuilder.create()
                .text("Test")
                .prefHeight(50)
                .prefWidth(500)
                .build();

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


        final Text lcdText = TextBuilder.create()
                .x(100)
                .y(100)
                .fontSmoothingType(FontSmoothingType.LCD)
                .build();

        lcdText.textProperty().bind(testText.textProperty());

        final Circle point = CircleBuilder.create()
                .centerX(100)
                .centerY(100)
                .radius(2)
                .fill(Color.RED)
                .build();

        Pane root = VBoxBuilder.create()
                .children(
                    PaneBuilder.create()
                    .minWidth(500)
                    .minHeight(500)
                    .children(
                        lcdText,
                        point)
                    .onMouseClicked(new EventHandler<MouseEvent>() {

                        @Override
                        public void handle(MouseEvent event) {
                            point.setCenterX(event.getX());
                            point.setCenterY(event.getY());

                            TimelineBuilder.create()
                                .keyFrames(
                                    new KeyFrame(Duration.seconds(5),
                                        new KeyValue(lcdText.xProperty(), event.getX(),
                                            interpolatorChoiceBox.getSelectionModel().getSelectedItem())),
                                    new KeyFrame(Duration.seconds(5),
                                        new KeyValue(lcdText.yProperty(), event.getY(),
                                            interpolatorChoiceBox.getSelectionModel().getSelectedItem()))
                                    )
                                .build()
                                .play();
                        }
                    })
                    .build(),
                    testText,
                    interpolatorChoiceBox)
                .build();



        Scene scene = new Scene(root, 500, 575);

        primaryStage.setTitle("Test Animnation LCD Text");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}



