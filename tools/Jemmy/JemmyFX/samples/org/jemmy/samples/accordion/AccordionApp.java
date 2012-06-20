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
 * questions.
 */

package org.jemmy.samples.accordion;

import java.awt.AWTException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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


        accordion.getPanes().add(new TitledPane("First pane", new Label("First pane's content")));
        accordion.getPanes().add(new TitledPane("Second pane", new Label("Second pane's content")));

        box.getChildren().add(accordion);

        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                reset();
            }
        });
        box.getChildren().add(reset);

        stage.setScene(scene);

        stage.setWidth(300);
        stage.setHeight(300);

        stage.show();

        reset();
    }

    private void reset() {
        TitledPane pane = accordion.getExpandedPane();
        if (pane != null) {
            pane.setExpanded(false);
        }
        accordion.getPanes().get(1).setExpanded(true);
    }
}
