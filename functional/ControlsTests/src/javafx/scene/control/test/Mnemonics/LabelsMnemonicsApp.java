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
package javafx.scene.control.test.Mnemonics;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

public class LabelsMnemonicsApp extends InteroperabilityApp {

    public static final String BUTTON_STATIC_ID = "button.static.id";
    public static final String BUTTON_STATIC_1_ID = "button.static.1.id";
    public static final String BUTTON_STATIC_2_ID = "button.static.2.id";
    public static final String BUTTON_STATIC_REVERSED_ID = "button.static.reversed.id";
    public static final String LABEL_STATIC_SET_ID = "label.set.id";
    public static final String LABEL_STATIC_RESET_ID = "label.reset.id";
    public static final String LABEL_STATIC_REVERSED_ID = "label.static.reversed.id";

    public static final String BUTTON_DYNAMIC_1_ID = "button.dynamic.1.id";
    public static final String BUTTON_DYNAMIC_2_ID = "button.dynamic.2.id";
    public static final String LABEL_DYNAMIC_1_ID = "label.dynamic.1.id";
    public static final String LABEL_DYNAMIC_2_ID = "label.dynamic.2.id";
    public static final String CHECK_DYNAMIC_PARSE_1_ID = "check.dynamic.parse.1.id";
    public static final String CHECK_DYNAMIC_PARSE_2_ID = "check.dynamic.parse.2.id";
    public static final String CHECK_DYNAMIC_SET_1_ID = "check.dynamic.set.1.id";
    public static final String CHECK_DYNAMIC_SET_2_ID = "check.dynamic.set.2.id";
    public static final String CHECK_DYNAMIC_REVERSED_SET_1_ID = "check.dynamic.reversed.set.1.id";
    public static final String CHECK_DYNAMIC_REVERSED_SET_2_ID = "check.dynamic.reversed.set.2.id";
    public static final String CHECK_MENU_PARSE_ID = "check.menu.parse.id";

    public static void main(String[] args) {
        Utils.launch(LabelsMnemonicsApp.class, args); // args go to /dev/null/ for the moment
    }

    @Override
    public Scene getScene() {
        return new MnemonicsScene();
    }

    protected HBox box = new HBox(10);

    public class MnemonicsScene extends Scene {
        public MnemonicsScene() {
            super(box, 600, 400);
            Utils.addBrowser(this);

            VBox static_box = new VBox(5);
            {
                Label header = new Label("Static scenario");
                Separator separator1 = new Separator(Orientation.HORIZONTAL);

                Label set_label = new Label("Label for _static set");
                set_label.setId(LABEL_STATIC_SET_ID);
                Button button = new Button("_Button");
                button.setId(BUTTON_STATIC_ID);
                set_label.setMnemonicParsing(true);
                set_label.setLabelFor(button);
                Separator separator2 = new Separator(Orientation.HORIZONTAL);

                Label reset_label = new Label("Label for static _reset");
                reset_label.setId(LABEL_STATIC_RESET_ID);
                Button button1 = new Button("Button 1");
                button1.setId(BUTTON_STATIC_1_ID);
                Button button2 = new Button("Button 2");
                button2.setId(BUTTON_STATIC_2_ID);
                reset_label.setMnemonicParsing(true);
                reset_label.setLabelFor(button1);
                reset_label.setLabelFor(button2);
                Separator separator3 = new Separator(Orientation.HORIZONTAL);

                Label reversed_label = new Label("Label with reversed _order set");
                reversed_label.setId(LABEL_STATIC_REVERSED_ID);
                Button reversed_button = new Button("Button reversed set");
                reversed_button.setId(BUTTON_STATIC_REVERSED_ID);
                reversed_label.setLabelFor(reversed_button);
                reversed_label.setMnemonicParsing(true);

                static_box.getChildren().addAll(header, separator1, set_label, button, separator2, reset_label, button1, button2, separator3, reversed_label, reversed_button);
            }

            VBox dynamic_box = new VBox(5);
            {
                Label header = new Label("Dynamic scenario");
                Separator separator1 = new Separator(Orientation.HORIZONTAL);

                HBox box = new HBox(5);

                VBox left_box = new VBox(5);
                final Button button1 = new Button("Button 1");
                button1.setId(BUTTON_DYNAMIC_1_ID);
                final Button button2 = new Button("Button 2");
                button2.setId(BUTTON_DYNAMIC_2_ID);
                final Label label1 = new Label("Label _1");
                label1.setId(LABEL_DYNAMIC_1_ID);
                final Label label2 = new Label("Label _2");
                label2.setId(LABEL_DYNAMIC_2_ID);

                left_box.getChildren().addAll(label1, button1, label2, button2);

                VBox right_box = new VBox(5);

                CheckBox label_for_check1 = new CheckBox("label1.setLabelFor(button1)");
                label_for_check1.setId(CHECK_DYNAMIC_SET_1_ID);
                label_for_check1.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                        if (t1) {
                            label1.setLabelFor(button1);
                        } else {
                            label1.setLabelFor(null);
                        }
                    }
                });

                CheckBox parse_mnemonics_check1 = new CheckBox("label1.mnemonicsParse()");
                parse_mnemonics_check1.setId(CHECK_DYNAMIC_PARSE_1_ID);
                parse_mnemonics_check1.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                        label1.setMnemonicParsing(t1);
                    }
                });

                CheckBox label_for_check2 = new CheckBox("label2.setLabelFor(button2)");
                label_for_check2.setId(CHECK_DYNAMIC_SET_2_ID);
                label_for_check2.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                        if (t1) {
                            label2.setLabelFor(button2);
                        } else {
                            label2.setLabelFor(null);
                        }
                    }
                });

                CheckBox parse_mnemonics_check2 = new CheckBox("label2.mnemonicsParse()");
                parse_mnemonics_check2.setId(CHECK_DYNAMIC_PARSE_2_ID);
                parse_mnemonics_check2.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                        label2.setMnemonicParsing(t1);
                    }
                });

                CheckBox label_for_reversed_check1 = new CheckBox("label1.setLabelFor(button2)");
                label_for_reversed_check1.setId(CHECK_DYNAMIC_REVERSED_SET_1_ID);
                label_for_reversed_check1.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                        if (t1) {
                            label1.setLabelFor(button2);
                        } else {
                            label1.setLabelFor(null);
                        }
                    }
                });

                CheckBox label_for_reversed_check2 = new CheckBox("label2.setLabelFor(button1)");
                label_for_reversed_check2.setId(CHECK_DYNAMIC_REVERSED_SET_2_ID);
                label_for_reversed_check2.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                        if (t1) {
                            label2.setLabelFor(button1);
                        } else {
                            label1.setLabelFor(null);
                        }
                    }
                });

                Separator separator2 = new Separator(Orientation.HORIZONTAL);

                right_box.getChildren().addAll(label_for_check1, parse_mnemonics_check1, label_for_check2, parse_mnemonics_check2, separator2, label_for_reversed_check1, label_for_reversed_check2);

                box.getChildren().addAll(left_box, right_box);
                dynamic_box.getChildren().addAll(header, separator1, box);
            }

            box.getChildren().addAll(static_box, dynamic_box);
        }
    }
}
