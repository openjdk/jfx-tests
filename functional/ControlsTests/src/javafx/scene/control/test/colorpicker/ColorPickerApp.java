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
package javafx.scene.control.test.colorpicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.test.utils.CommonPropertiesScene;
import javafx.scene.control.test.utils.ptables.PropertiesTable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import test.javaclient.shared.InteroperabilityApp;
import test.javaclient.shared.Utils;

/**
 * @author Alexander Kirov
 */
public class ColorPickerApp extends InteroperabilityApp {

    public final static String TESTED_COLORPICKER_ID = "TESTED_COLORPICKER_ID";
    public final static String HARD_RESET_BUTTON_ID = "HARD_RESET_COLORPICKER_BUTTON_ID";
    public final static String SOFT_RESET_BUTTON_ID = "SOFT_RESET_COLORPICKER_BUTTON_ID";
    public final static String SET_COLOR_TEXT_FIELD_ID = "SET_COLOR_TEXT_FIELD_ID";
    public final static String SET_COLOR_BUTTON_ID = "SET_COLOR_BUTTON_ID";
    public final static List<Color> predefinedColors = new ArrayList<Color>();

    public static void main(String[] args) {
        Utils.launch(ColorPickerApp.class, args);
    }

    @Override
    protected Scene getScene() {
        Utils.setTitleToStage(stage, "ColorPickerTestApp");
        return new ColorPickerScene();
    }

    class ColorPickerScene extends CommonPropertiesScene {

        //VBox which contain tested color picker.
        Pane pane;
        PropertiesTable tb;
        //Color picker to be tested.
        ColorPicker testedColorPicker;

        public ColorPickerScene() {
            super("ColorPicker", 800, 600);

            prepareScene();
        }

        @Override
        final protected void prepareScene() {
            predefinedColors.add(Color.WHITE);
            predefinedColors.add(Color.BLACK);
            predefinedColors.add(Color.BLUE);

            Utils.addBrowser(this);
            pane = new Pane();
            testedColorPicker = new ColorPicker();
            testedColorPicker.setId(TESTED_COLORPICKER_ID);

            tb = new PropertiesTable(testedColorPicker);

            tb.addStringLine(testedColorPicker.promptTextProperty(), "prompt text");

            tb.addBooleanPropertyLine(testedColorPicker.armedProperty());
            tb.addBooleanPropertyLine(testedColorPicker.focusTraversableProperty());

            tb.addBooleanPropertyLine(testedColorPicker.visibleProperty());

            tb.addObjectEnumPropertyLine(testedColorPicker.blendModeProperty(), Arrays.asList(javafx.scene.effect.BlendMode.values()), testedColorPicker);
            tb.addObjectEnumPropertyLine(testedColorPicker.valueProperty(), predefinedColors, testedColorPicker);

            tb.addDoublePropertyLine(testedColorPicker.prefWidthProperty(), -100, 400, 100);
            tb.addDoublePropertyLine(testedColorPicker.prefHeightProperty(), -100, 400, 100);

            tb.addDoublePropertyLine(testedColorPicker.rotateProperty(), -100, 400, 100);

            tb.addDoublePropertyLine(testedColorPicker.translateXProperty(), -1, 15, 0);
            tb.addDoublePropertyLine(testedColorPicker.scaleXProperty(), -1, 4, 1);
            tb.addDoublePropertyLine(testedColorPicker.scaleYProperty(), -1, 4, 1);
            tb.addDoublePropertyLine(testedColorPicker.scaleZProperty(), -1, 4, 1);

            tb.addSimpleListener(testedColorPicker.widthProperty(), testedColorPicker);
            tb.addSimpleListener(testedColorPicker.heightProperty(), testedColorPicker);

            tb.addSimpleListener(testedColorPicker.focusedProperty(), testedColorPicker);
            tb.addSimpleListener(testedColorPicker.pressedProperty(), testedColorPicker);
            tb.addSimpleListener(testedColorPicker.showingProperty(), testedColorPicker);
            tb.addSimpleListener(testedColorPicker.sceneProperty(), testedColorPicker);

            tb.addStringLine(testedColorPicker.promptTextProperty(), "prompt");

            pane.setMinSize(240, 240);
            pane.setPrefSize(240, 240);
            pane.setStyle("-fx-border-color : red;");
            pane.getChildren().add(testedColorPicker);

            VBox vb = new VBox();
            vb.setSpacing(5);

            HBox hb = (HBox) getRoot();
            hb.setPadding(new Insets(5, 5, 5, 5));
            hb.setStyle("-fx-border-color : green;");

            Button hardResetButton = new Button("Hard reset");
            hardResetButton.setId(HARD_RESET_BUTTON_ID);
            hardResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    HBox hb = (HBox) getRoot();
                    hb.getChildren().clear();
                    prepareMainSceneStructure();
                    prepareScene();
                }
            });

            Button softResetButton = new Button("Soft reset");
            softResetButton.setId(SOFT_RESET_BUTTON_ID);
            softResetButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    tb.refresh();
                    ColorPicker newOne = new ColorPicker();
                    testedColorPicker.setValue(newOne.getValue());
                }
            });

            HBox resetButtonsHBox = new HBox();
            resetButtonsHBox.getChildren().addAll(hardResetButton, softResetButton);
            resetButtonsHBox.setAlignment(Pos.CENTER);

            VBox vb1 = new VBox(5);
            vb1.getChildren().addAll(resetButtonsHBox, setColorHbox());

            setTestedControl(testedColorPicker);
            setControllersContent(vb1);
            setPropertiesContent(tb);
        }

        private HBox setColorHbox() {
            HBox hb = new HBox();
            Label lb = new Label("Set color");
            final TextField colorTf = new TextField();
            colorTf.setPrefWidth(100);
            colorTf.setId(SET_COLOR_TEXT_FIELD_ID);
            Button bt = new Button("Set!");
            bt.setId(SET_COLOR_BUTTON_ID);
            bt.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    testedColorPicker.setValue(Color.web(colorTf.getText()));
                }
            });
            hb.getChildren().addAll(lb, colorTf, bt);
            hb.setAlignment(Pos.CENTER);
            return hb;
        }
    }
}
