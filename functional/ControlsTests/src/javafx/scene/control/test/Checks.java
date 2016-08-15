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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Dmitry Kozorez
 */
public class Checks extends InteroperabilityApp {

    public static final String NO_SELECTION = "no selection yet";

    public static void main(String[] args) {
        Utils.launch(Checks.class, args); // args go to /dev/null/ for the moment
    }

    @Override
    public Scene getScene() {
        return new ChecksScene();
    }

    public class ChecksScene extends Scene {

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
        final Text checkBoxSelection = new Text();

        public ChecksScene() {
            super(new VBox(TS_SPACING), 400, 300);
            initialize();
        }

        protected void initialize() {
            VBox buttonList = (VBox) getRoot();
            buttonList.getChildren().clear();
            //checkbox
            HBox hBox = new HBox(TS_SPACING);
            checkBoxSelection.setId(CHECKBOX_LABEL_ID);
            final CheckBox c2 = new CheckBox(TWO_STATE_CB);
            c2.selectedProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    checkBoxSelection.setText(c2.isSelected() ? SELECTED_LABEL_TEXT
                            : UNSELECTED_LABEL_TEXT);
                }
            });
            final CheckBox c3 = new CheckBox(TRI_STATE_CB);
            c3.setAllowIndeterminate(true);
            InvalidationListener c3Listener = new InvalidationListener() {
                public void invalidated(Observable ov) {
                    checkBoxSelection.setText((!c3.isIndeterminate())
                            ? (c3.isSelected() ? SELECTED_LABEL_TEXT : UNSELECTED_LABEL_TEXT)
                            : UNDEFINED_LABEL_TEXT);
                }
            };
            c3.selectedProperty().addListener(c3Listener);
            c3.indeterminateProperty().addListener(c3Listener);
            final CheckBox cd = new CheckBox(DISABLED_CONTROL);
            cd.setDisable(true);
            cd.selectedProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    checkBoxSelection.setText(cd.isSelected() ? SELECTED_LABEL_TEXT
                            : UNSELECTED_LABEL_TEXT);
                }
            });
            hBox.getChildren().add(c2);
            hBox.getChildren().add(c3);
            hBox.getChildren().add(cd);
            hBox.getChildren().add(checkBoxSelection);
            buttonList.getChildren().add(hBox);

            Button clear = new Button(CLEAR_BTN);
            clear.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    initialize();
                }
            });
            buttonList.getChildren().add(clear);

            checkBoxSelection.setText(NO_SELECTION);
        }
    }
}