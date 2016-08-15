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

import javafx.scene.control.test.utils.ptables.TabPaneWithControl;
import javafx.scene.control.test.utils.ptables.PropertyTablesFactory;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.control.test.utils.ptables.SpecialTablePropertiesProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class TextNodeApp extends InteroperabilityApp {

    public final static String TESTED_TEXTNODE_ID = "TESTED_TEXTNODE_ID";
    public final static String RESET_BUTTON_ID = "RESET_LABELED_BUTTON_ID";

    public static void main(String[] args) {
        Utils.launch(TextNodeApp.class, args);
    }

    @Override
    protected Scene getScene() {
        return new LabeledScene();
    }

    protected class LabeledScene extends Scene {

        //Pane which contain tested text control.
        Pane vb3;
        PropertiesTable tb;
        //Text to be tested.
        Text testedControl;

        public LabeledScene() {
            super(new HBox(), 800, 600);

            prepareScene();
        }

        private void prepareScene() {
            vb3 = new Pane();
            testedControl = new Text();
            testedControl.setId(TESTED_TEXTNODE_ID);

            tb = new PropertiesTable(testedControl);
            tb.addSimpleListener(testedControl.boundsInLocalProperty(), testedControl);
            tb.addSimpleListener(testedControl.boundsInParentProperty(), testedControl);
            PropertyTablesFactory.explorePropertiesList(testedControl, tb);
            SpecialTablePropertiesProvider.provideForControl(testedControl, tb);
            TabPaneWithControl tabPane = new TabPaneWithControl("Text", tb);

            vb3.setMinSize(220, 220);
            vb3.setPrefSize(220, 220);
            vb3.setStyle("-fx-border-color : red;");
            vb3.getChildren().add(testedControl);

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

            Button resetButton = ButtonBuilder.create().id(RESET_BUTTON_ID).text("Reset").build();
            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareScene();
                }
            });

            vb.getChildren().addAll(new Label("Pane with tested Text node"), vb3, resetButton, new Separator(Orientation.HORIZONTAL), getDrawLinesHBox());

            tb.setStyle("-fx-border-color : yellow;");

            //Main scene layout.
            hb.getChildren().addAll(vb, tabPane);
        }

        protected HBox getDrawLinesHBox() {
            CheckBox cb = new CheckBox("Show borders");

            cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    double x = testedControl.getBoundsInParent().getMinX();
                    double y = testedControl.getBoundsInParent().getMinY();

                    double w = testedControl.getBoundsInLocal().getWidth();
                    double h = testedControl.getBoundsInLocal().getHeight();

                    Line line1 = new Line(x, y, x + w, y);
                    Line line2 = new Line(x, y, x, y + h);
                    Line line3 = new Line(x + w, y, x + w, y + h);
                    Line line4 = new Line(x, y + h, x + w, y + h);

                    line1.setVisible(t1);
                    line2.setVisible(t1);
                    line3.setVisible(t1);
                    line4.setVisible(t1);

                    vb3.getChildren().clear();
                    vb3.getChildren().add(testedControl);
                    vb3.getChildren().addAll(line1, line2, line3, line4);
                }
            });

            HBox hb = new HBox();
            hb.getChildren().addAll(cb);
            return hb;
        }
    }
}
