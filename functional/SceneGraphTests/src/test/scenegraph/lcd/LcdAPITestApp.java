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
