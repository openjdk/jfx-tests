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
package javafx.scene.control.test.labeleds.newapps;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import test.javaclient.shared.InteroperabilityApp;

/**
 * @author Alexander Kirov
 */
public abstract class LabeledsApp extends InteroperabilityApp {

    public final static String TESTED_LABELED_ID = "TESTED_LABELED_ID";
    public final static String RESET_BUTTON_ID = "RESET_LABELED_BUTTON_ID";

    protected abstract class LabeledScene extends CommonPropertiesScene {

        //Pane which contain tested labeled control.
        Pane vb3;
        PropertiesTable tb;
        //Labeled to be tested.
        Labeled testedControl;

        public LabeledScene() {
            super("Labeled", 800, 600);

            prepareScene();
        }

        protected abstract Labeled getTestedLabeled();

        @Override
        protected void prepareScene() {
            vb3 = new Pane();
            testedControl = getTestedLabeled();
            testedControl.setId(TESTED_LABELED_ID);

            tb = new PropertiesTable(testedControl);
            PropertyTablesFactory.explorePropertiesList(testedControl, tb);
            SpecialTablePropertiesProvider.provideForControl(testedControl, tb);

            vb3.setMinSize(220, 220);
            vb3.setPrefSize(220, 220);
            vb3.setStyle("-fx-border-color : red;");
            vb3.getChildren().add(testedControl);

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

            Button resetButton = new Button("Reset");
            resetButton.setId(RESET_BUTTON_ID);
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareScene();
                }
            });

            setControllersContent(resetButton);
            setPropertiesContent(tb);
        }
    }
}
