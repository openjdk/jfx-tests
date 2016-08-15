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
package javafx.scene.control.test.windows;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class NewStageApp extends InteroperabilityApp {

    public final static String TESTED_STAGE_ID = "TESTED_STAGE_ID";
    public final static String RESET_BUTTON_ID = "RESET_STAGE_BUTTON_ID";
    public final String STAGE_ADD_INDEX_TEXT_FIELD_ID = "STAGE_ADD_INDEX_TEXT_FIELD_ID";

    public static void main(String[] args) {
        Utils.launch(NewStageApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "StageTestApp");
        return new StageScene(stage);
    }

    class StageScene extends Scene {

        //VBox which contain tested Stage.
        Pane pane;
        //Stage to be tested.
        Stage testedStage;

        public StageScene(Stage stage) {
            super(new VBox(), 800, 300);

            prepareScene(stage);
        }

        private void prepareScene(final Stage stage) {
            testedStage = stage;

            PropertiesTable tb = new PropertiesTable(testedStage);
            PropertyTablesFactory.explorePropertiesList(testedStage, tb);

            final ToggleButton toggle = new ToggleButton("Fullscreen state : false");
            toggle.setSelected(false);
            toggle.selectedProperty().addListener(new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    toggle.setText("Fullscreen state : " + t1);
                    stage.setFullScreen(t1);
                }
            });

            VBox vb = (VBox) getRoot();
            vb.setPadding(new Insets(5, 5, 5, 5));
            vb.setStyle("-fx-border-color : green;");

            vb.getChildren().addAll(tb, toggle);
        }
    }
}