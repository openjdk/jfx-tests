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

/**
 *
 * @author Alexander Petrov
 */
public class AnimationLCDTextTestApp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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

        

