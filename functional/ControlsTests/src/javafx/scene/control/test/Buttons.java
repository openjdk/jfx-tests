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

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author shura
 * @author Dmitry Kozorez
 */
public class Buttons extends InteroperabilityApp {

    public static void main(String[] args) {
        Utils.launch(Buttons.class, args); // args go to /dev/null/ for the moment
    }

    @Override
    public Scene getScene() {
        return new ButtonsScene();
    }

    public class ButtonsScene extends Scene {

        public static final String BUTTON_LABEL_ID = "button_label";
        public static final String TOGGLE_BUTTON_LABEL_ID = "toggle_button_label";
        public static final String BUTTON_PUSHED = "enabled pushed";
        public static final String BUTTON_TOGGLED = "enabled toggled";
        public static final String BUTTON_UNTOGGLED = "enabled untoggled";
        public static final String CHECKBOX_LABEL_ID = "checkbox_label";
        public static final String DISABLED_BUTTON_PUSHED = "disabled pushed";
        public static final String DISABLED_BUTTON_TOGGLED = "disabled toggled";
        public static final String DISABLED_CONTROL = "disabled";
        public static final String UNTOGLED = "untogled";
        public static final String ENABLED_BTN = "Enabled";
        public static final String ENABLED_TOGGLE_BTN = "Enabled toggle";
        public static final String RADIO1 = "radio1";
        public static final String RADIO2 = "radio2";
        public static final String RADIO_LABEL_ID = "radio_label";
        public static final String TRI_STATE_CB = "tri-state";
        public static final String TWO_STATE_CB = "two-state";
        public static final String SELECTED_LABEL_TEXT = "selected";
        public static final String UNSELECTED_LABEL_TEXT = "unselected";
        public static final String UNDEFINED_LABEL_TEXT = "undefined";
        public static final String CLEAR_BTN = "clear status";
        public static final double TS_SPACING = 20.0f;
        Text isButtonPushed;
        Text button_press_counter;
        final Text checkBoxSelection = new Text();
        int counter, last_counter;

        public ButtonsScene() {
            super(new VBox(TS_SPACING), 400, 300);
            initialize();
            //clear();
        }

        protected void initialize() {
            counter = 0;
            button_press_counter = new Text("pressed: 0");
            VBox buttonList = (VBox) getRoot();
            buttonList.getChildren().clear();
            //buttons
            HBox hBox = new HBox(TS_SPACING);
            isButtonPushed = new Text();
            isButtonPushed.setId(BUTTON_LABEL_ID);
            Button ebtn = new Button(ENABLED_BTN);
            ebtn.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    isButtonPushed.setText(BUTTON_PUSHED);
                }
            });
            Button cbtn = new Button(button_press_counter.getText());
            cbtn.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    last_counter = counter;
                    counter++;
                    button_press_counter.setText(button_press_counter.getText().replace("" + last_counter, "" + counter));
                }
            });
            cbtn.textProperty().bindBidirectional(button_press_counter.textProperty());

            cbtn.setDefaultButton(true);

            Button dbtn = new Button(DISABLED_CONTROL);
            dbtn.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    isButtonPushed.setText(DISABLED_BUTTON_PUSHED);
                }
            });
            dbtn.setDisable(true);
            hBox.getChildren().add(ebtn);
            hBox.getChildren().add(dbtn);
            hBox.getChildren().add(cbtn);
            hBox.getChildren().add(isButtonPushed);
            buttonList.getChildren().add(hBox);

            Button clear = new Button(CLEAR_BTN);
            clear.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    initialize();
                }
            });
            buttonList.getChildren().add(clear);
            isButtonPushed.setText("button");
        }
    }
}