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
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

        applyIndicator = new Circle(5, Color.RED);
        applyIndicator.setId(APPLY_INDICATOR_ID);

        actionIndicator = new Circle(5, Color.RED);
        actionIndicator.setId(ACTION_INDICATOR_ID);

        Button applyButton = new Button("Apply");
        applyButton.setId(APPLY_BUTTON_ID);
        applyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                        if(testingFactory == null){
                            factoryChoicer.getSelectionModel().selectNext();
                        } else {
                            factoryChoicer.getSelectionModel().select(TransparencyLCDTextTestApp.testingFactory);
                        }
                        applyIndicator.setFill(Color.GREEN);
                    }
        });

        Button actionButton = new Button("Action");
        actionButton.setId(ACTION_BUTTON_ID);
        actionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent t) {
                        Factory currentFactory = TransparencyLCDTextTestApp.this.
                                factoryChoicer.getSelectionModel().getSelectedItem();

                        currentFactory.action(TransparencyLCDTextTestApp.this.rightPane.getChildren().get(0));

                        actionIndicator.setFill(Color.GREEN);
                    }
        });

        //Create panes for testing;
        rightPane = new StackPane();
        rightPane.setId(RIGHT_PANE_ID);
        ((StackPane)rightPane).setAlignment(Pos.CENTER);
        rightPane.setMinHeight(450);
        rightPane.setMinWidth(300);

        //Create root pane.
        VBox root = new VBox();
        root.setId("root");
        HBox tools = new HBox();
        tools.setId("toolsPane");
        tools.setPadding(new Insets(10));
        tools.setSpacing(10);
        tools.setAlignment(Pos.CENTER);
        tools.getChildren().addAll(factoryChoicer, actionButton, applyButton);
        HBox secondone = new HBox(new Text("Apply"), applyIndicator, new Text("Action"), actionIndicator);
        secondone.setPadding(new Insets(10));
        secondone.setSpacing(10);
        HBox testPane = new HBox(rightPane);
        testPane.setId("testPane");
        testPane.setAlignment(Pos.CENTER);
        root.getChildren().addAll(tools, secondone, testPane);
        return root;
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
