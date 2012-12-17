/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */
package test.scenegraph.lcd;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.TextBuilder;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 *
 * @author Alexander Petrov
 */
public class LcdAPITestApp extends InteroperabilityApp {
    static {
        System.setProperty("prism.lcdtext", "true");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Utils.launch(LcdAPITestApp.class, args);
    }
    

    @Override
    protected Scene getScene() {
        VBox root = VBoxBuilder.create()
                .padding(new Insets(10))
                .spacing(10)
                .children(
                    TextBuilder.create()
                        .id("GrayGray")
                        .text("Text")
                        .fontSmoothingType(FontSmoothingType.GRAY)
                        .style("-fx-font-size: 16;-fx-font-smoothing-type: gray; ")
                        .build(),
                    TextBuilder.create()
                        .id("LCDGray")
                        .text("Text")
                        .fontSmoothingType(FontSmoothingType.LCD)
                        .style("-fx-font-size: 16;-fx-font-smoothing-type: gray; ")
                        .build(),
                    TextBuilder.create()
                        .id("GrayLCD")
                        .text("Text")
                        .fontSmoothingType(FontSmoothingType.GRAY)
                        .style("-fx-font-size: 16;-fx-font-smoothing-type: lcd; ")
                        .build(),
                    TextBuilder.create()
                        .id("LCDLCD")
                        .text("Text")
                        .fontSmoothingType(FontSmoothingType.LCD)
                        .style("-fx-font-size: 16;-fx-font-smoothing-type: lcd;")
                        .build()
                    )
                .build();

        return new Scene(root, 200, 200);
    }
}
