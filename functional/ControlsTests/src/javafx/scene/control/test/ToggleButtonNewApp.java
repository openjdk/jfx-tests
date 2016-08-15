/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
package javafx.scene.control.test;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.RadioButtonBuilder;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleButtonBuilder;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class ToggleButtonNewApp extends InteroperabilityApp {

    public enum ControlType {

        TOGGLE_BUTTON, RADIO_BUTTON
    };
    public static String TOGGLE_BUTTON_1_ID = "toggle button 1";
    public static String TOGGLE_BUTTON_2_ID = "toggle button 2";
    public static String BINDING_SET_TOGGLE_BUTTON_ID = "binding set button";
    public static String GROUPING_TOGGLE_BUTTON_ID = "grouping button";
    public static String RESET_BUTTON_ID = "reset scene button";
    public static String SETTING_CONTROL_TOGGLE_BUTTON_ID = "ToggleButton which sets tested control";
    public static String CHECK_BOX_LISTENING_1_ID = "check box listening to toggle button 1";
    public static String CHECK_BOX_LISTENING_2_ID = "check box listening to toggle button 2";
    public static String CHECK_BOX_SETTING_1_BIDIR_ID = "check box setting toggle button 1 bidirectionaly";
    public static String CHECK_BOX_SETTING_2_UNIDIR_ID = "check box setting toggle button 2 unirectionaly";
    public static String MOUSE_EVENTS_LOGGER_LABEL_ID = "label logs mouse events";

    public static void main(String[] args) {
        Utils.launch(ToggleButtonNewApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new ToggleButtonNewAppScene();
    }

    class ToggleButtonNewAppScene extends Scene {

        ToggleButton toggleButton1;
        ToggleButton toggleButton2;
        CheckBox checkBox1 = CheckBoxBuilder.create().wrapText(true).text("Unidirectionally binded to\ntoggle button 1 selection property").id(CHECK_BOX_LISTENING_1_ID).build();
        CheckBox checkBox2 = CheckBoxBuilder.create().wrapText(true).text("Unidirectionally binded to\ntoggle button 2 selection property").id(CHECK_BOX_LISTENING_2_ID).build();
        CheckBox checkBox3 = CheckBoxBuilder.create().wrapText(true).text("This check box is bidirectionally binded\nto Toggle Button 1 selection property").id(CHECK_BOX_SETTING_1_BIDIR_ID).build();
        CheckBox checkBox4 = CheckBoxBuilder.create().wrapText(true).text("This check box unidirectionally sets\ntoggle button 2 selection property").id(CHECK_BOX_SETTING_2_UNIDIR_ID).build();
        ToggleGroup toggleGroup = new ToggleGroup();
        ToggleButton bindingsSettingButton = ToggleButtonBuilder.create().text("State: Unbinded").id(BINDING_SET_TOGGLE_BUTTON_ID).build();
        ToggleButton groupingButton = ToggleButtonBuilder.create().text("State: Ungrouped").id(GROUPING_TOGGLE_BUTTON_ID).build();
        ToggleButton testedControlChangeButton = ToggleButtonBuilder.create().text("Control: radio button").id(SETTING_CONTROL_TOGGLE_BUTTON_ID).build();
        Button resetButton = ButtonBuilder.create().text("Reset state").id(RESET_BUTTON_ID).build();
        Label mouseEventsLogger = LabelBuilder.create().text("0").id(MOUSE_EVENTS_LOGGER_LABEL_ID).build();
        VBox vb1 = new VBox();

        public ToggleButtonNewAppScene() {
            super(new HBox(), 800, 320);

            setControl(ControlType.RADIO_BUTTON);

            addButtonsListeners();
            bindListeningBindings();
            addToggleButtonActionEventListener();

            checkBox1.setDisable(true);
            checkBox2.setDisable(true);
            Label label1 = new Label("These CheckBoxes listens\n to \"selected\""
                    + " property of according\n toggle button "
                    + "by binding");
            Label label2 = new Label("Next Checkboxes set its\n "
                    + "values to toggle buttons. \n"
                    + "Before using press button for binding");
            label1.setWrapText(true);
            label2.setWrapText(true);

            vb1.setSpacing(10);

            VBox vb2 = new VBox();
            vb2.setSpacing(10);
            vb2.getChildren().addAll(label1, checkBox1, checkBox2, label2, bindingsSettingButton, checkBox3, checkBox4);

            VBox vb3 = new VBox();
            vb3.setSpacing(10);
            vb3.getChildren().addAll(groupingButton, testedControlChangeButton, resetButton);

            HBox hb = (HBox) getRoot();
            hb.setSpacing(10);
            hb.setLayoutX(10);
            hb.setLayoutY(10);
            hb.getChildren().addAll(vb1, vb2, vb3, new Label("Action events handled : "), mouseEventsLogger);
        }

        private void setControl(ControlType type) {
            if (type == ControlType.RADIO_BUTTON) {
                toggleButton1 = RadioButtonBuilder.create().text("Radio button 1").id(TOGGLE_BUTTON_1_ID).build();
                toggleButton2 = RadioButtonBuilder.create().text("Radio button 2").id(TOGGLE_BUTTON_2_ID).build();
            } else {
                toggleButton1 = ToggleButtonBuilder.create().text("Toggle button 1").id(TOGGLE_BUTTON_1_ID).build();
                toggleButton2 = ToggleButtonBuilder.create().text("Toggle button 2").id(TOGGLE_BUTTON_2_ID).build();
            }

            vb1.getChildren().clear();
            vb1.getChildren().addAll(toggleButton1, toggleButton2);
        }

        private void addButtonsListeners() {

            bindingsSettingButton.selectedProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable o) {
                    if (((BooleanProperty) o).getValue() == true) {
                        bindSettingBindings();
                        bindingsSettingButton.setText("State: Binded");
                    } else {
                        removeBindings();
                        bindListeningBindings();
                        bindingsSettingButton.setText("State: Unbinded");
                    }
                }
            });

            groupingButton.selectedProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable o) {
                    if (((BooleanProperty) o).getValue() == true) {
                        toggleButton1.setToggleGroup(toggleGroup);
                        toggleButton2.setToggleGroup(toggleGroup);
                        groupingButton.setText("State: Grouped");
                    } else {
                        toggleButton1.setToggleGroup(null);
                        toggleButton2.setToggleGroup(null);
                        groupingButton.setText("State: Ungrouped");
                    }
                }
            });

            testedControlChangeButton.selectedProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable o) {
                    groupingButton.selectedProperty().setValue(false);
                    bindingsSettingButton.selectedProperty().setValue(false);
                    if (((BooleanProperty) o).getValue() == true) {
                        setControl(ControlType.TOGGLE_BUTTON);
                        testedControlChangeButton.setText("Control: toggle button");
                    } else {
                        setControl(ControlType.RADIO_BUTTON);
                        testedControlChangeButton.setText("Control: radio button");
                    }

                    addToggleButtonActionEventListener();
                    bindListeningBindings();
                }
            });

            resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    resetState();
                }
            });
        }

        private void addToggleButtonActionEventListener() {
            EventHandler<ActionEvent> actionEventHandler = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    mouseEventsLogger.setText(String.valueOf(Integer.parseInt(mouseEventsLogger.getText()) + 1));
                }
            };

            mouseEventsLogger.setText("0");

            toggleButton1.setOnAction(actionEventHandler);
            toggleButton2.setOnAction(actionEventHandler);
        }

        private void bindListeningBindings() {
            checkBox1.selectedProperty().bind(toggleButton1.selectedProperty());
            checkBox2.selectedProperty().bind(toggleButton2.selectedProperty());
        }

        private void bindSettingBindings() {
            checkBox3.selectedProperty().bindBidirectional(toggleButton1.selectedProperty());
            toggleButton2.selectedProperty().bind(checkBox4.selectedProperty());
        }

        ;

        private void removeBindings() {
            checkBox1.selectedProperty().unbind();
            checkBox2.selectedProperty().unbind();
            checkBox3.selectedProperty().unbindBidirectional(toggleButton1.selectedProperty());

            toggleButton2.selectedProperty().unbind();

            setAllSelectionsFalse();
        }

        private void setAllSelectionsFalse() {
            toggleButton1.setSelected(false);
            toggleButton2.setSelected(false);

            checkBox3.setSelected(false);
            checkBox4.setSelected(false);
        }

        private void resetEventLabel() {
            mouseEventsLogger.setText("0");
            addToggleButtonActionEventListener();
        }

        private void resetState() {
            bindingsSettingButton.setSelected(false);
            groupingButton.setSelected(false);

            testedControlChangeButton.setSelected(false);
            setControl(ControlType.RADIO_BUTTON);
            testedControlChangeButton.setText("Control: radio button");

            removeBindings();
            bindListeningBindings();
            setAllSelectionsFalse();
            resetEventLabel();
        }
    }
}