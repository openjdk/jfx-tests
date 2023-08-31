/*
 * Copyright (c) 2009, 2023, Oracle and/or its affiliates. All rights reserved.
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

package org.jemmy.fx.control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

/**
 * This small FX app is used in Accordion JemmyFX samples. It displays an Accordion control that contains two titled panes.
 * Button "reset" is used to return accordion to initial state.
 *
 */

public class AccordionApp extends Application {
    Accordion accordion = new Accordion();

    /**
     *
     * @param args
     * @throws AWTException
     */
    public static void main(String[] args) throws AWTException {
        launch(args);
    }

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        VBox box = new VBox();
        Scene scene = new Scene(box);

        accordion.getPanes().add(new TitledPane("First pane", createTarget()));
        accordion.getPanes().add(new TitledPane("Second pane", createTarget()));

        box.getChildren().add(accordion);

        Button reset = new Button("Reset");
        reset.setOnAction(t -> {
            reset();
        });
        box.getChildren().add(reset);

        stage.setScene(scene);

        stage.setWidth(250);
        stage.setHeight(600);

        stage.show();
    }

    Pane createTarget() {
        Pane pane = new Pane();
        pane.setMinSize(100, 150);
        CheckBox check = new CheckBox();
        check.setTranslateX(100);
        check.setTranslateY(100);
        pane.getChildren().add(check);
        return pane;
    }

    private void reset() {
        TitledPane pane = accordion.getExpandedPane();
        if (pane != null) {
            pane.setExpanded(false);
        }
        pane = accordion.getPanes().get(0);
        pane.setExpanded(true);
    }
}
