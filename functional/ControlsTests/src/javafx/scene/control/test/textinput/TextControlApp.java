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
package javafx.scene.control.test.textinput;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public abstract class TextControlApp extends InteroperabilityApp {

    public final static String ADD_TEXT_BUTTON_ID = "ADD_TEXT_BUTTON_ID";
    public final static String TESTED_TEXT_INPUT_CONTROL_ID = "TESTED_CONTROL_ID";
    public final static String RESET_BUTTON_ID = "RESET_BUTTON_ID";

    public abstract class TextInputScene extends CommonPropertiesScene {

        PropertiesTable tb;
        //Control to be tested.
        TextInputControl testedTextInput;

        public TextInputScene() {
            super("TextInput", 800, 600);

            prepareScene();
        }

        public abstract void setNewControl();

        public abstract void resetControl();

        public abstract void addControlSpecificButtons(Pane pane);

        @Override
        final protected void prepareScene() {
            setNewControl();
            tb = new PropertiesTable(testedTextInput);
            testedTextInput.setId(TESTED_TEXT_INPUT_CONTROL_ID);

            PropertyTablesFactory.explorePropertiesList(testedTextInput, tb);
            SpecialTablePropertiesProvider.provideForControl(testedTextInput, tb);

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

            Button resetButton = new Button("RESET");
            resetButton.setId(RESET_BUTTON_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    tb.refresh();
                    resetControl();
                }
            });
            VBox resetButtonsHBox = new VBox(5);
            resetButtonsHBox.getChildren().addAll(resetButton);
            vb.getChildren().addAll(resetButtonsHBox);
            addControlDependentProperties();
            addControlSpecificButtons(vb);
            setTestedControl(testedTextInput);
            setControllersContent(vb);
            setPropertiesContent(tb);
        }

        protected void addControlDependentProperties() {}
    }

    protected void setTitle() {
        Utils.setTitleToStage(stage, "TextInputTestApp");
    }
}
